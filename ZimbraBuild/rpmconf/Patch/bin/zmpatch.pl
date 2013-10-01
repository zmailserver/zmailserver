#!/usr/bin/perl -w
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2010, 2011, 2012, 2013 VMware, Inc.
# 
# The contents of this file are subject to the Zimbra Public License
# Version 1.3 ("License"); you may not use this file except in
# compliance with the License.  You may obtain a copy of the License at
# http://www.zimbra.com/license.
# 
# Software distributed under the License is distributed on an "AS IS"
# basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
# ***** END LICENSE BLOCK *****
#  

use strict;
use lib qw(/opt/zimbra/zimbramon/lib lib corebuild/opt/zimbra/zimbramon/lib);
use Zimbra::Util::Common;
use XML::SAX;
use XML::Simple;
use Data::Dumper;
use Getopt::Long;
use File::Path;
use File::Copy;
use File::Basename;
use Time::localtime qw(ctime);

my %options = ();

sub getDateStamp();
sub progress($$$);
sub detail($);
sub debugLog($);
sub getInstalledVersion();
sub getInstalledType();
sub usage;
sub doPatchBuild();
sub doPatchDeploy();
sub doIncrementJSVersion();

GetOptions ("config=s"  => \$options{config},
            "verbose|v+"  => \$options{verbose},
            "debug|d+"    => \$options{debug},
            "help"      => sub { usage(); },
            "dryrun|n"  => \$options{dryrun},
            "force"  => \$options{force},
            "build|b"   => \$options{build},
            "source=s"  => \$options{build_source},
            "target=s"  => \$options{build_target},
            "version=s" => \$options{build_version});

($>) and usage() unless $options{build};

my $progName = "zmpatch";
my $patchBuildNumber;
my $zimbra_home = "/opt/zimbra";
my %versionInfo;
my ($platform, $zmtype, $config);
$options{verbose} = 0 unless $options{verbose};

if ($options{build}) {
  usage() unless ($options{build_source} && $options{build_version});
  $options{build_target}="$options{build_source}/ZimbraBuild/zcs-patch-$options{build_version}" unless $options{build_target};
}

my $logfile;
if($options{build_source}) {
  $logfile = "$options{build_source}/logs/${progName}.".getDateStamp().".log";
} else {
  $logfile = "/tmp/${progName}.".getDateStamp().".log";
}
open LOGFILE, ">$logfile" or die "Can't open $logfile: $!\n";

if($options{build_source}) {
  unlink("$options{build_source}/log/${progName}.log") if (-e "$options{build_source}/log/${progName}.log");
  symlink($logfile, "$options{build_source}/log/${progName}.log");
} else {
  unlink("/tmp/${progName}.log") if (-e "/tmp/${progName}.log");
  symlink($logfile, "/tmp/${progName}.log");
}

if ($options{build}) {
  if (-f "$options{build_source}/ZimbraBuild/RE/BUILD") {
    open(BUILD, "$options{build_source}/ZimbraBuild/RE/BUILD");
  } else {
    progress("Unable to determine build number.\n", 0, 0);
    exit 1;
  }
} else {
  $zmtype=getInstalledType();
  debugLog("Install type: $zmtype\n");
  unless (-x "${zimbra_home}/libexec/get_plat_tag.sh") {
    print "ZCS Install not found.\n";
    exit 1;
  }
  $platform = `${zimbra_home}/libexec/get_plat_tag.sh`;
  chomp($platform);
  if (-f "source/build") {
    open(BUILD, "source/build");
  } else {
    progress("Unable to determine build number.\n", 0, 0);
    exit 1;
  }
}

$patchBuildNumber = <BUILD>;
chomp($patchBuildNumber);
close(BUILD);

unless ($options{config} && -f $options{config}) {
  progress("Missing config file.\n", 0, 0);
  usage();
  exit 1;
}

if ($options{config} && -f $options{config}) {
  $config = XMLin($options{config}, ForceArray => [ 'name', 'patch', 'package', 'file', 'deletefile', 'zimlet','target', 'preinstall', 'postinstall'], KeyAttr => [ 'name', 'patch', 'package', 'source', 'version', 'postinstall', 'preinstall' ], SuppressEmpty => 1);
  my $debug_text = Dumper($config);
  debugLog($debug_text);
  
}

doPatchBuild() if ($options{build});

doPatchDeploy();



close(LOGFILE);
exit(0);
################################################################
# Subroutines
################################################################

sub initPatchDir() { 
  my $bin_dir=$options{build_target}."/bin";
  my $conf_dir=$options{build_target}."/conf";
  my $src_dir=$options{build_target}."/source";
  make_path($bin_dir);
  make_path($conf_dir);
  make_path($src_dir);
  copy("$options{build_source}/ZimbraBuild/rpmconf/Patch/bin/zmpatch.pl", $bin_dir);
  copy("$options{build_source}/ZimbraBuild/rpmconf/Patch/conf/zmpatch.xml", $conf_dir);
  copy("$options{build_source}/ZimbraBuild/rpmconf/Patch/installPatch.sh", $options{build_target});
  system("chmod 755 $options{build_target}/installPatch.sh");
  open(B, ">${src_dir}/build");
  print B "$patchBuildNumber\n";
  close(B);
}

sub doPatchBuild() {
  progress("Building patch for release $options{build_version} in $options{build_target}\n", 0, 0);

  initPatchDir();
  
  my $buildStatus=0;
  foreach my $patch (keys %{$config->{patch}}) {
    progress("$config->{patch}->{$patch}->{version}\n",1,1);

    my $patchName=$config->{patch}->{$patch}->{version};

    foreach my $package (keys %{$config->{patch}->{$patch}->{package}}) {
      my $pref = $config->{patch}->{$patch}->{package}->{$package};
      progress("$package\n",2,1);

      foreach my $zimlet (keys %{$pref->{zimlet}}) {
        my $zref = $pref->{zimlet}->{$zimlet};
        progress("$zref->{target}[0]\n", 3,1);
        my $zpath="$options{build_target}/source/$patchName/$package/" . dirname($zref->{target}[0]);
        unless (make_path($zpath)) {
          progress("Failed to make_path $zpath.\n",3,0);
          $buildStatus++;
          next;
        }
        my $source="$options{build_source}/$zimlet";
        if (-f $source) {
          copy($source,$zpath);
        } else {
          progress("Failed to copy $source\n",3,0);
          $buildStatus++;
        }
      }

      foreach my $file (keys %{$pref->{file}}) {
        my $fref=$pref->{file}->{$file};
        
        foreach my $target (@{$fref->{target}}) {
          my $fpath="$options{build_target}/source/$patchName/$package/" . dirname($target);
          progress("$target\n",3,1);
          unless (make_path($fpath)) {
            progress("Failed to make_path $fpath.\n",3,0);
            $buildStatus++;
            next;
          }
          my $source="$options{build_source}/$file";
          if (-f $source) {
            copy($source,$fpath);
          } else {
            progress("Failed to copy $source\n",3,0);
            $buildStatus++;
          }
        }
      }
      # future
      #foreach my $file (keys %{$pref->{dir}}) {
        #print "$file\n";
      #}
    }

  }
  if(!$buildStatus) {
    # Clean up zmpatch files on successful build
    unlink("$logfile");
    unlink("/tmp/${progName}.log");
  }
  exit($buildStatus);
}

sub doPatchDeploy() {
  getInstalledVersion();
  my $currentRelease = getReleaseString($versionInfo{current});
  my $currentBuild = getVersionComponent($versionInfo{current},"build");
  progress("Current Version: $versionInfo{current}\n", 0, 1);

  if ($currentBuild > $patchBuildNumber) {
    progress("Current install $currentRelease is newer then patch version.\n", 0, 1);
    return undef;
  }
  if ($currentBuild == $patchBuildNumber && !$options{force}) {
    progress("Current install $currentRelease is the same as patch version.\n", 0, 1);
    return undef;
  }
  while () {
    if ($config->{patch}->{$currentRelease}) {
      print "Found Patch for $currentRelease called $config->{patch}->{$currentRelease}->{version}\n";
      deployPatch($currentRelease);
      $currentRelease = $config->{patch}->{$currentRelease}->{version};
    } else {
      last;
    }
  }
}

sub deployPatch($) {
  my ($v) = @_;
  progress("Deploying patch for $v\n",1,1);
  my $currentBuild=getVersionComponent($versionInfo{current},"build");
  my $currentRelease = getReleaseString($versionInfo{current});
  my $patch = $config->{patch}->{$v};
  # check the build number range for validity
  my $min = $patch->{buildnum}->{min};
  my $max = $patch->{buildnum}->{max};
  if (defined($min) && $currentBuild < $min) {
    progress("Current install build is too low is not patchabled.\n",1,0);
    progress("Min: $min Max: $max Current: $currentBuild\n",1,1);
    return undef;
  }

  if (defined($max) && $currentBuild > $max) {
    progress("Patch can not be applied. Current build $currentBuild exceeds max build value $max for patch.\n",1,0);
    progress("Min: $min Max: $max Current: $currentBuild\n",1,1);
    return undef;
  } 


  # log an install session start
  logSession("INSTALL SESSION START");
  # force this so the build number is always updated
  # not all patches will have zimbra-core components
  logSession("UPGRADED zimbra-core-${currentRelease}_${patchBuildNumber}");

  # do preinstall tasks 

  # loop through each package 
  foreach my $package (keys %{$patch->{package}}) {
    # make sure package is installed
    my $pref = $patch->{package}->{$package};
    next unless (isInstalled($package)); 
    # deploy the zimlets
    foreach my $zimlet (keys %{$pref->{zimlet}}) {
      my $zref = $pref->{zimlet}->{$zimlet};
      next if ($zmtype eq "FOSS" && lc($zref->{type}) eq "network");
      my $zimletname=basename($zref->{target}[0]);
      $zimletname =~ s/\.zip//;
      progress("$zimletname...",2,1);

      my $srcfile="./source/$patch->{version}/$package/$zref->{target}[0]";
      my $dstfile=$zref->{target}[0];
      if (-f "$srcfile") {
        debugLog("cp $srcfile $dstfile");
        copy($srcfile, $dstfile) unless $options{dryrun};
      } else {
        progress("skipped. No such file $srcfile.\n",0,1);
      }
      unless ($options{dryrun}) {
        if (lc($zref->{deploy}) eq "true") {
          my $rc;
          progress("undeployed...",0,1)
            unless (runAsZimbra("zmzimletctl -l undeploy $zimletname"));
          progress("deployed...",0,1) 
            unless (runAsZimbra("zmzimletctl -l deploy $dstfile"));
          runAsZimbra("zmprov flushcache zimlet") 
           if (lc($zref->{flushcache}) eq "true");
        }
      }
      progress("updated.\n", 0, 1);
    }
    progress("Updating files for package $package\n", 1, 0);
    # deploy the files
    foreach my $file (keys %{$pref->{file}}) {
      my $fref = $pref->{file}->{$file};
      next if ($zmtype eq "FOSS" && lc($fref->{type}) eq "network");
      foreach my $dstfile (@{$fref->{target}}) {
        progress("$dstfile...",2,1); 
        my $srcfile="./source/$patch->{version}/$package/$dstfile";
        unless (-f "$srcfile") {
          progress("No such source file $srcfile\n",0,1);
          next;
        }
        debugLog("cp $srcfile $dstfile");
        copy($srcfile, $dstfile) unless $options{dryrun};
        # verify the perms/ownershiA
        my ($owner,$group,$mode);
        unless ($options{dryrun}) {
          chown($owner,$dstfile)
            if ($owner = $fref->{perms}->{owner});
          system("chgrp $group $dstfile")
            if ($group = $fref->{perms}->{group});
          system("chmod $mode $dstfile")
            if ($mode = $fref->{perms}->{mode});
        }
        progress("copied.\n", 1, 1);
      }
    }
    # Delete files if necessary
    foreach my $deleteFile ($pref->{deletefile}) {
      foreach my $dhash (@{$deleteFile}) {
        foreach my $dstfile (@{$dhash->{target}}) {
          progress("$dstfile...",2,1);
          debugLog("rm $dstfile");
          unlink($dstfile) unless $options{dryrun};
          progress("deleted.\n", 1, 1);
        }
      }
    }
    # update the installed version of package
    if ($package eq "zimbra-store") {
      &doIncrementJSVersion();
    }
    logSession("UPGRADED $package-${currentRelease}_${patchBuildNumber}")
      unless ($package eq "zimbra-core");
  }

  # do postinstall tasks
  foreach my $task (@{$patch->{postinstall}}) {
    progress("Running $task...",1,0);
    if (runAsZimbra($task)) {
      progress("failed.\n",1,0);
    } else {
      progress("done.\n",1,0);
    }
  }
  
  

  # log an install session complete
  logSession("INSTALL SESSION COMPLETE");
  logSession("CONFIG SESSION START");
  logSession("CONFIGURED BEGIN");
  logSession("CONFIGURED patch$patch->{version}");
  logSession("CONFIGURED END");
  logSession("CONFIG SESSION COMPLETE");

}
sub logSession($) {
  my ($msg) = @_;
  return if $options{dryrun};
  my $date = `date +%s`;
  chomp($date);
  open(SESS, ">>${zimbra_home}/.install_history");
  print SESS "$date: $msg\n";
  close(SESS);
}

sub runAsZimbra {
  my $cmd = shift;
  my $SU;
  if ($platform =~ /MACOSXx86_10/) {
    $SU = "su - zimbra -c -l ";
  } else {
    $SU = "su - zimbra -c ";
  }
  detail ( "*** Running as zimbra user: $cmd\n" );
  my $rc;
  $rc = 0xffff & system("$SU \"$cmd\" >> $logfile 2>&1");
  return $rc;
}

sub make_path($) {
  my ($dir) = @_;
  eval { mkpath($dir) };
  debugLog("Couldn't create $dir: $@") if ($@);
  return (($@) ? undef : 1);
}

sub debugLog($) {
  print "@_\n" if $options{debug};
}  

sub usage {
  ($>) and print STDERR "Warning: $0 must be run as root!\n\n";
  print STDERR "Usage: $0 [-h] -c <config file> -build\n";

  print STDERR "\t--help|-h:          display this help message\n";
  print STDERR "\t--config|-c <file>: patch configuration file.\n";
  print STDERR "\t--verbose|-v:       verbose output.\n";
  print STDERR "\t--debug|-d:         debug output.\n";
  print STDERR "\t--dryrun|-n:        patch dryrun does not apply changes.\n\n";

  print STDERR "   Developer Use Only\n";
  print STDERR "\t--build|-b:                   build patch from source.\n";
  print STDERR "\t--source <patch source>:      path to patch source.\n";
  print STDERR "\t--target <patch destination>: destination path for patch.\n";
  print STDERR "\t--version <patch version>:    target version of patch.\n\n";

  exit 1;
}

sub detail($) {
  my $msg = shift;
  my ($sub,$line) = (caller(1))[3,2];
  my $date = ctime();
  $msg =~ s/\n$//;
  $msg = "$sub:$line $msg" if $options{debug};
  open(LOG, ">>$logfile");
  print LOG "$date $msg\n";
  close(LOG);
  #`echo "$date $msg" >> $logfile`;
}

sub progress($$$) {
  my ($msg,$lvl,$verbosity) = @_;
  $lvl=0 unless $lvl;
  $verbosity=0 unless $verbosity;
  #print "$msg lvl: $lvl verbosity: $verbosity opt_verbose: $options{verbose}\n";
  print " "x$lvl. "$msg" if ($options{verbose} >= $verbosity);
  my ($sub,$line) = (caller(1))[3,2];
  $msg = "$sub:$line $msg" if $options{debug};
  detail ($msg);
}

sub getDateStamp() {
  my ($sec,$min,$hour,$mday,$mon,$year) = localtime(time());
  $year = 1900+$year;
  $sec = sprintf("%02d", $sec);
  $min = sprintf("%02d", $min);
  $hour = sprintf("%02d", $hour);
  $mday = sprintf("%02d", $mday);
  $mon = sprintf("%02d", $mon+1);
  my $stamp = "$mon$mday$year-$hour$min$sec";
  return $stamp;
}


sub getInstalledVersion() {
  my %configStatus;
  my %installStatus;
  if (open H, "${zimbra_home}/.install_history") {

    my @history = <H>;
    close H;
    foreach my $h (@history) {
      next if ($h =~ /CONFIG SESSION COMPLETE/);
      if ($h =~ /CONFIG SESSION START/) {
        %configStatus = ();
        next;
      }
      next if ($h =~ /INSTALL SESSION COMPLETE/);
      if ($h =~ /INSTALL SESSION START/) {
        %installStatus = ();
        %configStatus = ();
        next;
      }
      my ($d, $op, $stage) = split ' ', $h;
      if ($op eq "INSTALLED" || $op eq "UPGRADED") {
        my $v = $stage;
        $stage =~ s/[-_]\d.*//;
        if ($stage eq "zimbra-core") {
          $v =~ s/_HEAD.*//;
          $v =~ s/^zimbra-core[-_]//;
          if ($v =~ /\.deb$/) {
            my $orig_v=$v;
            $v =~ s/^(\d+\.\d+\.\d+\.\w+\.\w+)\..*/$1/;
            $v = reverse($v);
            $v =~ s/\./_/;
            $v =~ s/\./_/;
            $v = reverse($v);
            if ($v =~ /\_deb$/) {
              $v = $orig_v;
              $v =~ s/^(\d+\.\d+\.[^_]*_[^_]+_[^.]+).*/$1/;
            }
          } else {
            $v =~ s/^(\d+\.\d+\.[^_]*_[^_]+_[^.]+).*/$1/;
          }
          $versionInfo{current} = $v;
        }
      } elsif ($op eq "CONFIGURED") {
        if ($stage eq "END") {
          $versionInfo{previous} = $versionInfo{current};
        }
      }
    }

  }
  progress("Previous version: $versionInfo{previous}\n",0,2);
  progress("Current  version: $versionInfo{current}\n",0,2);
  
  return $versionInfo{current};
}

sub getInstalledType() {
  return ((-f "${zimbra_home}/bin/zmbackupquery") ? "NETWORK" : "FOSS");
}

sub getReleaseString($) {
  my ($version) = @_;
  my ($major,$minor,$micro,$stage,$build) = 
    $version =~ /(\d+)\.(\d+)\.(\d+)_([^_]*)_(\d+)/;

  return "${major}.${minor}.${micro}_${stage}";
}

  

sub getVersionComponent($$) {
  my ($version, $component) = @_;
  my ($major,$minor,$micro,$stage,$build) = 
    $version =~ /(\d+)\.(\d+)\.(\d+)_([^_]*)_(\d+)/;
  return $major if ($component eq "major");
  return $minor if ($component eq "minor");
  return $micro if ($component eq "micro");
  return $stage if ($component eq "stage");
  return $build if ($component eq "build");
}

sub isInstalled {
  my $pkg = shift;

  my $pkgQuery;

  my $good = 0;
  if ($platform =~ /^DEBIAN/ || $platform =~ /^UBUNTU/) {
    $pkgQuery = "dpkg -s $pkg";
  } elsif ($platform eq "MACOSXx86_10.6" || $platform eq "MACOSXx86_10.7") {
    $pkg =~ s/zimbra-//;
    $pkgQuery = "pkgutil --pkg-info com.zimbra.zcs.${pkg}";
  } elsif ($platform =~ /MACOSX/) {
    my @l = sort glob ("/Library/Receipts/${pkg}*");
    if ( $#l < 0 ) { return 0; }
    $pkgQuery = "test -d $l[$#l]";
  } elsif ($platform =~ /RPL/) {
    $pkgQuery = "conary q $pkg";
  } else {
    $pkgQuery = "rpm -q $pkg";
  }

  my $rc = 0xffff & system ("$pkgQuery > /dev/null 2>&1");
  $rc >>= 8;
  if (($platform =~ /^DEBIAN/ || $platform =~ /^UBUNTU/) && $rc == 0 ) {
    $good = 1;
    $pkgQuery = "dpkg -s $pkg | egrep '^Status: ' | grep 'not-installed'";
    $rc = 0xffff & system ("$pkgQuery > /dev/null 2>&1");
    $rc >>= 8;
    return ($rc == $good);
  } else {
    return ($rc == $good);
  }
}

sub doIncrementJSVersion() {
  my $infile="$zimbra_home/jetty/etc/zimbra.web.xml.in";
  my $outfile="$zimbra_home/data/tmp/zimbra.web.xml.in.new";
  unlink("$outfile") if (-e "$outfile");
  open (IN, "<$infile");
  open (OUT, ">$outfile");
  my $next=0;
  
  while (<IN>) {
    if ($next == 0) {
      if ($_ =~ m/<param-name>jsVersion<\/param-name>/) {
        $next = 1;
      }
      print OUT $_;
    }
    else {
      my ($oldVersion) = $_ =~ /<param-value>(\d+)<\/param-value>/;
      my $newVersion=$oldVersion+1;
      $_ =~ s/$oldVersion/$newVersion/;
      print OUT $_;
      $next = 0;
    }
  }
  close(IN);
  close(OUT);
  copy($outfile, $infile);
  $infile="$zimbra_home/jetty/etc/zimbraAdmin.web.xml.in";
  $outfile="$zimbra_home/data/tmp/zimbraAdmin.web.xml.in.new";
  unlink("$outfile") if (-e "$outfile");
  open (IN, "<$infile");
  open (OUT, ">$outfile");
  $next=0;
  
  while (<IN>) {
    if ($next == 0) {
      if ($_ =~ m/<param-name>jsVersion<\/param-name>/) {
        $next = 1;
      }
      print OUT $_;
    }
    else {
      my ($oldVersion) = $_ =~ /<param-value>(\d+)<\/param-value>/;
      my $newVersion=$oldVersion+1;
      $_ =~ s/$oldVersion/$newVersion/;
      print OUT $_;
      $next = 0;
    }
  }
  close(IN);
  close(OUT);
  copy($outfile, $infile);
}
