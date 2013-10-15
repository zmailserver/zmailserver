#!/usr/bin/perl
# vim: ts=2
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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

package zmupgrade;

use strict;
use lib "/opt/zmail/libexec/scripts";
use lib "/opt/zmail/zmailmon/lib";
use Migrate;
use Net::LDAP;
use IPC::Open3;
use FileHandle;
use File::Grep qw (fgrep);
use File::Path;
my $zmlocalconfig="/opt/zmail/bin/zmlocalconfig";
my $type = `${zmlocalconfig} -m nokey convertd_stub_name 2> /dev/null`;
chomp $type;
if ($type eq "") {$type = "FOSS";}
else {$type = "NETWORK";}

my $rundir = `dirname $0`;
chomp $rundir;
my $scriptDir = "/opt/zmail/libexec/scripts";

my $lowVersion = 18;
my $hiVersion = 92; # this should be set to the DB version expected by current server code

# Variables for the combo schema updater
my $comboLowVersion = 20;
my $comboHiVersion  = 27;
my $needSlapIndexing = 0;
my $mysqlcnfUpdated = 0;

my $platform = `/opt/zmail/libexec/get_plat_tag.sh`;
chomp $platform;
my $addr_space = (($platform =~ m/\w+_(\d+)/) ? "$1" : "32");
my $su;
if ($platform =~ /MACOSXx86_10/) {
  $su = "su - zmail -c -l";
} else {
  $su = "su - zmail -c";
}

my $hn = `$su "${zmlocalconfig} -m nokey zmail_server_hostname"`;
chomp $hn;

my $isLdapMaster = `$su "${zmlocalconfig} -m nokey ldap_is_master"`;
chomp($isLdapMaster);
if (lc($isLdapMaster) eq "true" ) {
   $isLdapMaster = 1;
} else {
   $isLdapMaster = 0;
}

my $ZMPROV = "/opt/zmail/bin/zmprov -r -m -l --";

my %updateScripts = (
  'ComboUpdater' => "migrate-ComboUpdater.pl",
  'UniqueVolume' => "migrate20051021-UniqueVolume.pl",
  '18' => "migrate20050916-Volume.pl",
  '19' => "migrate20050920-CompressionThreshold.pl",
  '20' => "migrate20050927-DropRedologSequence.pl",    # 3.1.2
  '21' => "migrate20060412-NotebookFolder.pl",
  '22' => "migrate20060515-AddImapId.pl",
  '23' => "migrate20060518-EmailedContactsFolder.pl",
  '24' => "migrate20060708-FlagCalendarFolder.pl",
  '25' => "migrate20060803-CreateMailboxMetadata.pl",
  '26' => "migrate20060810-PersistFolderCounts.pl",    # 4.0.2
  '27' => "migrate20060911-MailboxGroup.pl",           # 4.5.0_BETA1
  '28' => "migrate20060929-TypedTombstones.pl",
  '29' => "migrate20061101-IMFolder.pl",               # 4.5.0_RC1
  '30' => "migrate20061117-TasksFolder.pl",            # 4.5.0_RC1
  '31' => "migrate20061120-AddNameColumn.pl",          # 4.5.0_RC1
  '32' => "migrate20061204-CreatePop3MessageTable.pl", # 4.5.0_RC1
  '33' => "migrate20061205-UniqueAppointmentIndex.pl", # 4.5.0_RC1
  '34' => "migrate20061212-RepairMutableIndexIds.pl",  # 4.5.0_RC1
  '35' => "migrate20061221-RecalculateFolderSizes.pl", # 4.5.0_GA
  '36' => "migrate20070306-Pop3MessageUid.pl",         # 5.0.0_BETA1
  '37' => "migrate20070606-WidenMetadata.pl",          # 5.0.0_BETA2
  '38' => "migrate20070614-BriefcaseFolder.pl",        # 5.0.0_BETA2
  '39' => "migrate20070627-BackupTime.pl",             # 5.0.0_BETA2
  '40' => "migrate20070629-IMTables.pl",               # 5.0.0_BETA2
  '41' => "migrate20070630-LastSoapAccess.pl",         # 5.0.0_BETA2
  '42' => "migrate20070703-ScheduledTask.pl",          # 5.0.0_BETA2
  '43' => "migrate20070706-DeletedAccount.pl",         # 5.0.0_BETA2
  '44' => "migrate20070725-CreateRevisionTable.pl",     # 5.0.0_BETA3
  '45' => "migrate20070726-ImapDataSource.pl",          # 5.0.0_BETA3
  '46' => "migrate20070921-ImapDataSourceUidValidity.pl", # 5.0.0_RC1
  '47' => "migrate20070928-ScheduledTaskIndex.pl",     # 5.0.0_RC2
  '48' => "migrate20071128-AccountId.pl",              # 5.0.0_RC3
  '49' => "migrate20071206-WidenSizeColumns.pl",       # 5.0.0_GA
  '50' => "migrate20080130-ImapFlags.pl",              # 5.0.3_GA
  '51' => "migrate20080213-IndexDeferredColumn.pl",    # 5.0.3_GA
  '52' => "migrate20080909-DataSourceItemTable.pl",    # 5.0.10_GA
  '53' => "migrate20080930-MucService.pl",             # this upgrades to 60 for 6_0_0 GA
   # 54-59 skipped for possible FRANKLIN use
  '60' => "migrate20090315-MobileDevices.pl",
  '61' => "migrate20090406-DataSourceItemTable.pl",    # 6.0.0_BETA1
  '62' => "migrate20090430-highestindexed.pl",         # 6.0.0_BETA2
  '63' => "migrate20100106-MobileDevices.pl",          # 6.0.5_GA
  '64' => "migrate20100926-Dumpster.pl",               # 7.0.0_BETA1
  #'65' => "migrate20101123-MobileDevices.pl",          # this upgrades to 80 for 8.0.0_BETA1
  # Consolidating the scripts which updates the db.version to 80..90
  '65' => "migrate20120611_7to8_bundle.pl",             # this upgrades to 90 for 8_0_0_BETA
  # 66-79 skipped for possible HELIX use
  '80' => "migrate20110314-MobileDevices.pl",          # 8.0.0_BETA1
  '81' => "migrate20110330-RecipientsColumn.pl",       # 8.0.0_BETA1
  '82' => "migrate20110705-PendingAclPush.pl",         # 8.0.0_BETA1
  '83' => "migrate20110810-TagTable.pl",               # 8.0.0_BETA1
  '84' => "migrate20110928-MobileDevices.pl",          # 8.0.0_BETA2
  '85' => "migrate20110929-VersionColumn.pl",          # 8.0.0_BETA2
  '86' => "migrate20120125-uuidAndDigest.pl",          # 8.0.0_BETA2
  '87' => "migrate20120222-LastPurgeAtColumn.pl",      # 8.0.0_BETA2
  '88' => "migrate20120229-DropIMTables.pl",           # 8.0.0_BETA2
  '89' => "migrate20120319-Name255Chars.pl",
  '90' => "migrate20120410-BlobLocator.pl",
  '91' => "migrate20121009-VolumeBlobs.pl",	       # 8.0.1
);

my %updateFuncs = (
  "3.0.M1" => \&upgradeBM1,
  "3.0.0_M2" => \&upgradeBM2,
  "3.0.0_M3" => \&upgradeBM3,
  "3.0.0_M4" => \&upgradeBM4,
  "3.0.0_GA" => \&upgradeBGA,
  "3.0.1_GA" => \&upgrade301GA,
  "3.1.0_GA" => \&upgrade310GA,
  "3.1.1_GA" => \&upgrade311GA,
  "3.1.2_GA" => \&upgrade312GA,
  "3.1.3_GA" => \&upgrade313GA,
  "3.1.4_GA" => \&upgrade314GA,
  "3.2.0_M1" => \&upgrade32M1,
  "3.2.0_M2" => \&upgrade32M2,
  "4.0.0_RC1" => \&upgrade400RC1,
  "4.0.0_GA" => \&upgrade400GA,
  "4.0.1_GA" => \&upgrade401GA,
  "4.0.2_GA" => \&upgrade402GA,
  "4.0.3_GA" => \&upgrade403GA,
  "4.0.4_GA" => \&upgrade404GA,
  "4.0.5_GA" => \&upgrade405GA,
  "4.1.0_BETA1" => \&upgrade410BETA1,
  "4.5.0_BETA1" => \&upgrade450BETA1,
  "4.5.0_BETA2" => \&upgrade450BETA2,
  "4.5.0_RC1" => \&upgrade450RC1,
  "4.5.0_RC2" => \&upgrade450RC2,
  "4.5.0_GA" => \&upgrade450GA,
  "4.5.1_GA" => \&upgrade451GA,
  "4.5.2_GA" => \&upgrade452GA,
  "4.5.3_GA" => \&upgrade453GA,
  "4.5.4_GA" => \&upgrade454GA,
  "4.5.5_GA" => \&upgrade455GA,
  "4.5.6_GA" => \&upgrade456GA,
  "4.5.7_GA" => \&upgrade457GA,
  "4.5.8_GA" => \&upgrade458GA,
  "4.5.9_GA" => \&upgrade459GA,
  "4.5.10_GA" => \&upgrade4510GA,
  "4.5.11_GA" => \&upgrade4511GA,
  "4.6.0_BETA" => \&upgrade460BETA,
  "4.6.0_RC1" => \&upgrade460RC1,
  "4.6.0_GA" => \&upgrade460GA,
  "4.6.1_RC1" => \&upgrade461RC1,
  "5.0.0_BETA1" => \&upgrade500BETA1,
  "5.0.0_BETA2" => \&upgrade500BETA2,
  "5.0.0_BETA3" => \&upgrade500BETA3,
  "5.0.0_BETA4" => \&upgrade500BETA4,
  "5.0.0_RC1" => \&upgrade500RC1,
  "5.0.0_RC2" => \&upgrade500RC2,
  "5.0.0_RC3" => \&upgrade500RC3,
  "5.0.0_GA" => \&upgrade500GA,
  "5.0.1_GA" => \&upgrade501GA,
  "5.0.2_GA" => \&upgrade502GA,
  "5.0.3_GA" => \&upgrade503GA,
  "5.0.4_GA" => \&upgrade504GA,
  "5.0.5_GA" => \&upgrade505GA,
  "5.0.6_GA" => \&upgrade506GA,
  "5.0.7_GA" => \&upgrade507GA,
  "5.0.8_GA" => \&upgrade508GA,
  "5.0.9_GA" => \&upgrade509GA,
  "5.0.10_GA" => \&upgrade5010GA,
  "5.0.11_GA" => \&upgrade5011GA,
  "5.0.12_GA" => \&upgrade5012GA,
  "5.0.13_GA" => \&upgrade5013GA,
  "5.0.14_GA" => \&upgrade5014GA,
  "5.0.15_GA" => \&upgrade5015GA,
  "5.0.16_GA" => \&upgrade5016GA,
  "5.0.17_GA" => \&upgrade5017GA,
  "5.0.18_GA" => \&upgrade5018GA,
  "5.0.19_GA" => \&upgrade5019GA,
  "5.0.20_GA" => \&upgrade5020GA,
  "5.0.21_GA" => \&upgrade5021GA,
  "5.0.22_GA" => \&upgrade5022GA,
  "5.0.23_GA" => \&upgrade5023GA,
  "5.0.24_GA" => \&upgrade5024GA,
  "5.0.25_GA" => \&upgrade5025GA,
  "5.0.26_GA" => \&upgrade5026GA,
  "5.0.27_GA" => \&upgrade5027GA,
  "6.0.0_BETA1" => \&upgrade600BETA1,
  "6.0.0_BETA2" => \&upgrade600BETA2,
  "6.0.0_RC1" => \&upgrade600RC1,
  "6.0.0_RC2" => \&upgrade600RC2,
  "6.0.0_GA" => \&upgrade600GA,
  "6.0.1_GA" => \&upgrade601GA,
  "6.0.2_GA" => \&upgrade602GA,
  "6.0.3_GA" => \&upgrade603GA,
  "6.0.4_GA" => \&upgrade604GA,
  "6.0.5_GA" => \&upgrade605GA,
  "6.0.6_GA" => \&upgrade606GA,
  "6.0.7_GA" => \&upgrade607GA,
  "6.0.8_GA" => \&upgrade608GA,
  "6.0.9_GA" => \&upgrade609GA,
  "6.0.10_GA" => \&upgrade6010GA,
  "6.0.11_GA" => \&upgrade6011GA,
  "6.0.13_GA" => \&upgrade6013GA,
  "6.0.14_GA" => \&upgrade6014GA,
  "6.0.15_GA" => \&upgrade6015GA,
  "6.0.16_GA" => \&upgrade6016GA,
  "7.0.0_BETA1" => \&upgrade700BETA1,
  "7.0.0_BETA2" => \&upgrade700BETA2,
  "7.0.0_BETA3" => \&upgrade700BETA3,
  "7.0.0_RC1" => \&upgrade700RC1,
  "7.0.0_GA" => \&upgrade700GA,
  "7.0.1_GA" => \&upgrade701GA,
  "7.1.0_GA" => \&upgrade710GA,
  "7.1.1_GA" => \&upgrade711GA,
  "7.1.2_GA" => \&upgrade712GA,
  "7.1.3_GA" => \&upgrade713GA,
  "7.1.4_GA" => \&upgrade714GA,
  "7.2.0_GA" => \&upgrade720GA,
  "7.2.1_GA" => \&upgrade721GA,
  "7.2.2_GA" => \&upgrade722GA,
  "7.2.3_GA" => \&upgrade723GA,
  "7.2.4_GA" => \&upgrade724GA,
  "8.0.0_BETA1" => \&upgrade800BETA1,
  "8.0.0_BETA2" => \&upgrade800BETA2,
  "8.0.0_BETA3" => \&upgrade800BETA3,
  "8.0.0_BETA4" => \&upgrade800BETA4,
  "8.0.0_BETA5" => \&upgrade800BETA5,
  "8.0.0_GA" => \&upgrade800GA,
  "8.0.1_GA" => \&upgrade801GA,
  "8.0.2_GA" => \&upgrade802GA,
  "8.0.3_GA" => \&upgrade803GA,
  "8.0.4_GA" => \&upgrade804GA,
  "9.0.0_BETA1" => \&upgrade900BETA1,
);

my @versionOrder = (
  "3.0.M1", 
  "3.0.0_M2", 
  "3.0.0_M3", 
  "3.0.0_M4", 
  "3.0.0_GA", 
  "3.0.1_GA", 
  "3.1.0_GA", 
  "3.1.1_GA", 
  "3.1.2_GA", 
  "3.1.3_GA", 
  "3.1.4_GA", 
  "3.2.0_M1",
  "3.2.0_M2",
  "4.0.0_RC1",
  "4.0.0_GA",
  "4.0.1_GA",
  "4.0.2_GA",
  "4.0.3_GA",
  "4.0.4_GA",
  "4.0.5_GA",
  "4.1.0_BETA1",
  "4.5.0_BETA1",
  "4.5.0_BETA2",
  "4.5.0_RC1",
  "4.5.0_RC2",
  "4.5.0_GA",
  "4.5.1_GA",
  "4.5.2_GA",
  "4.5.3_GA",
  "4.5.4_GA",
  "4.5.5_GA",
  "4.5.6_GA",
  "4.5.7_GA",
  "4.5.8_GA",
  "4.5.9_GA",
  "4.5.10_GA",
  "4.5.11_GA",
  "5.0.0_BETA1",
  "5.0.0_BETA2",
  "5.0.0_BETA3",
  "5.0.0_BETA4",
  "5.0.0_RC1",
  "5.0.0_RC2",
  "5.0.0_RC3",
  "5.0.0_GA",
  "5.0.1_GA",
  "5.0.2_GA",
  "5.0.3_GA",
  "5.0.4_GA",
  "5.0.5_GA",
  "5.0.6_GA",
  "5.0.7_GA",
  "5.0.8_GA",
  "5.0.9_GA",
  "5.0.10_GA",
  "5.0.11_GA",
  "5.0.12_GA",
  "5.0.13_GA",
  "5.0.14_GA",
  "5.0.15_GA",
  "5.0.16_GA",
  "5.0.17_GA",
  "5.0.18_GA",
  "5.0.19_GA",
  "5.0.20_GA",
  "5.0.21_GA",
  "5.0.22_GA",
  "5.0.23_GA",
  "5.0.24_GA",
  "5.0.25_GA",
  "5.0.26_GA",
  "5.0.27_GA",
  "6.0.0_BETA1",
  "6.0.0_BETA2",
  "6.0.0_RC1",
  "6.0.0_RC2",
  "6.0.0_GA",
  "6.0.1_GA",
  "6.0.2_GA",
  "6.0.3_GA",
  "6.0.4_GA",
  "6.0.5_GA",
  "6.0.6_GA",
  "6.0.7_GA",
  "6.0.8_GA",
  "6.0.9_GA",
  "6.0.10_GA",
  "6.0.11_GA",
  "6.0.13_GA",
  "6.0.14_GA",
  "6.0.15_GA",
  "6.0.16_GA",
  "7.0.0_BETA1",
  "7.0.0_BETA2",
  "7.0.0_BETA3",
  "7.0.0_RC1",
  "7.0.0_GA",
  "7.0.1_GA",
  "7.1.0_GA",
  "7.1.1_GA",
  "7.1.2_GA",
  "7.1.3_GA",
  "7.1.4_GA",
  "7.2.0_GA",
  "7.2.1_GA",
  "7.2.2_GA",
  "7.2.3_GA",
  "7.2.4_GA",
  "8.0.0_BETA1",
  "8.0.0_BETA2",
  "8.0.0_BETA3",
  "8.0.0_BETA4",
  "8.0.0_BETA5",
  "8.0.0_GA",
  "8.0.1_GA",
  "8.0.2_GA",
  "8.0.3_GA",
  "8.0.4_GA",
  "9.0.0_BETA1",
);

my ($startVersion,$startMajor,$startMinor,$startMicro);
my ($targetVersion,$targetMajor,$targetMinor,$targetMicro);

my @packageList = (
  "zmail-core",
  "zmail-ldap",
  "zmail-store",
  "zmail-mta",
  "zmail-snmp",
  "zmail-logger",
  "zmail-apache",
  "zmail-spell",
  );

my %installedPackages = ();

#####################

sub upgrade {
  $startVersion = shift;
  $targetVersion = shift;
  $main::config{HOSTNAME}=$hn;
  my ($startBuild,$targetBuild);
  ($startVersion,$startBuild) = $startVersion =~ /(\d\.\d\.\d+_[^_]*)_(\d+)/;  
  ($targetVersion,$targetBuild) = $targetVersion =~ m/(\d\.\d\.\d+_[^_]*)_(\d+)/;
  ($startMajor,$startMinor,$startMicro) =
    $startVersion =~ /(\d+)\.(\d+)\.(\d+_[^_]*)/;
  ($targetMajor,$targetMinor,$targetMicro) =
    $targetVersion =~ /(\d+)\.(\d+)\.(\d+_[^_]*)/;

  my $needVolumeHack = 0;
  my $needMysqlTableCheck = 0;
  my $needMysqlUpgrade = 0;

  getInstalledPackages();

  # Bug #73840 - need to delete /opt/zmail/keyview before we try stopping services
  if ((! main::isInstalled("zmail-convertd")) && (-l "/opt/zmail/keyview")) {
    unlink("/opt/zmail/keyview");
  }

  if (stopZmail()) { return 1; }

  my $curSchemaVersion;

  if (main::isInstalled("zmail-store")) {

    my $found = 0;
    foreach my $v (@versionOrder) {
      $found = 1 if ($v eq $startVersion);
      if ($found) {
        &doMysql51Upgrade if ($v eq "7.0.0_BETA1");
        &doMysql55Upgrade if ($v eq "8.0.0_BETA1");
      }
      last if ($v eq $targetVersion);
    }

    if (startSql()) { return 1; };

    $curSchemaVersion = Migrate::getSchemaVersion();
  }
 
  if ($startVersion eq "3.0.0_GA") {
    main::progress("This appears to be 3.0.0_GA\n");
  } elsif ($startVersion eq "3.0.1_GA") {
    main::progress("This appears to be 3.0.1_GA\n");
  } elsif ($startVersion eq "3.1.0_GA") {
    main::progress("This appears to be 3.1.0_GA\n");
    #$needVolumeHack = 1;
  } elsif ($startVersion eq "3.1.1_GA") {
    main::progress("This appears to be 3.1.1_GA\n");
  } elsif ($startVersion eq "3.1.2_GA") {
    main::progress("This appears to be 3.1.2_GA\n");
  } elsif ($startVersion eq "3.1.3_GA") {
    main::progress("This appears to be 3.1.3_GA\n");
  } elsif ($startVersion eq "3.1.4_GA") {
    main::progress("This appears to be 3.1.4_GA\n");
  } elsif ($startVersion eq "3.2.0_M1") {
    main::progress("This appears to be 3.2.0_M1\n");
  } elsif ($startVersion eq "3.2.0_M2") {
    main::progress("This appears to be 3.2.0_M2\n");
  } elsif ($startVersion eq "4.0.0_RC1") {
    main::progress("This appears to be 4.0.0_RC1\n");
  } elsif ($startVersion eq "4.0.0_GA") {
    main::progress("This appears to be 4.0.0_GA\n");
  } elsif ($startVersion eq "4.0.1_GA") {
    main::progress("This appears to be 4.0.1_GA\n");
  } elsif ($startVersion eq "4.0.2_GA") {
    main::progress("This appears to be 4.0.2_GA\n");
  } elsif ($startVersion eq "4.0.3_GA") {
    main::progress("This appears to be 4.0.3_GA\n");
  } elsif ($startVersion eq "4.0.4_GA") {
    main::progress("This appears to be 4.0.4_GA\n");
  } elsif ($startVersion eq "4.0.5_GA") {
    main::progress("This appears to be 4.0.5_GA\n");
  } elsif ($startVersion eq "4.1.0_BETA1") {
    main::progress("This appears to be 4.1.0_BETA1\n");
  } elsif ($startVersion eq "4.5.0_BETA1") {
    main::progress("This appears to be 4.5.0_BETA1\n");
  } elsif ($startVersion eq "4.5.0_BETA2") {
    main::progress("This appears to be 4.5.0_BETA2\n");
  } elsif ($startVersion eq "4.5.0_RC1") {
    main::progress("This appears to be 4.5.0_RC1\n");
  } elsif ($startVersion eq "4.5.0_RC2") {
    main::progress("This appears to be 4.5.0_RC2\n");
  } elsif ($startVersion eq "4.5.0_GA") {
    main::progress("This appears to be 4.5.0_GA\n");
  } elsif ($startVersion eq "4.5.1_GA") {
    main::progress("This appears to be 4.5.1_GA\n");
  } elsif ($startVersion eq "4.5.2_GA") {
    main::progress("This appears to be 4.5.2_GA\n");
  } elsif ($startVersion eq "4.5.3_GA") {
    main::progress("This appears to be 4.5.3_GA\n");
  } elsif ($startVersion eq "4.5.4_GA") {
    main::progress("This appears to be 4.5.4_GA\n");
  } elsif ($startVersion eq "4.5.5_GA") {
    main::progress("This appears to be 4.5.5_GA\n");
  } elsif ($startVersion eq "4.5.6_GA") {
    main::progress("This appears to be 4.5.6_GA\n");
  } elsif ($startVersion eq "4.5.7_GA") {
    main::progress("This appears to be 4.5.7_GA\n");
  } elsif ($startVersion eq "4.5.8_GA") {
    main::progress("This appears to be 4.5.8_GA\n");
  } elsif ($startVersion eq "4.5.9_GA") {
    main::progress("This appears to be 4.5.9_GA\n");
  } elsif ($startVersion eq "4.5.10_GA") {
    main::progress("This appears to be 4.5.10_GA\n");
  } elsif ($startVersion eq "4.5.11_GA") {
    main::progress("This appears to be 4.5.11_GA\n");
  } elsif ($startVersion eq "4.6.0_BETA") {
    main::progress("This appears to be 4.6.0_BETA\n");
  } elsif ($startVersion eq "4.6.0_RC1") {
    main::progress("This appears to be 4.6.0_RC1\n");
  } elsif ($startVersion eq "4.6.0_GA") {
    main::progress("This appears to be 4.6.0_GA\n");
  } elsif ($startVersion eq "5.0.0_BETA1") {
    main::progress("This appears to be 5.0.0_BETA1\n");
  } elsif ($startVersion eq "5.0.0_BETA2") {
    main::progress("This appears to be 5.0.0_BETA2\n");
  } elsif ($startVersion eq "5.0.0_BETA3") {
    main::progress("This appears to be 5.0.0_BETA3\n");
  } elsif ($startVersion eq "5.0.0_BETA4") {
    main::progress("This appears to be 5.0.0_BETA4\n");
  } elsif ($startVersion eq "5.0.0_RC1") {
    main::progress("This appears to be 5.0.0_RC1\n");
  } elsif ($startVersion eq "5.0.0_RC2") {
    main::progress("This appears to be 5.0.0_RC2\n");
  } elsif ($startVersion eq "5.0.0_RC3") {
    main::progress("This appears to be 5.0.0_RC3\n");
  } elsif ($startVersion eq "5.0.0_GA") {
    main::progress("This appears to be 5.0.0_GA\n");
  } elsif ($startVersion eq "5.0.1_GA") {
    main::progress("This appears to be 5.0.1_GA\n");
  } elsif ($startVersion eq "5.0.2_GA") {
    main::progress("This appears to be 5.0.2_GA\n");
  } elsif ($startVersion eq "5.0.3_GA") {
    main::progress("This appears to be 5.0.3_GA\n");
  } elsif ($startVersion eq "5.0.4_GA") {
    main::progress("This appears to be 5.0.4_GA\n");
  } elsif ($startVersion eq "5.0.5_GA") {
    main::progress("This appears to be 5.0.5_GA\n");
  } elsif ($startVersion eq "5.0.6_GA") {
    main::progress("This appears to be 5.0.6_GA\n");
  } elsif ($startVersion eq "5.0.7_GA") {
    main::progress("This appears to be 5.0.7_GA\n");
  } elsif ($startVersion eq "5.0.8_GA") {
    main::progress("This appears to be 5.0.8_GA\n");
  } elsif ($startVersion eq "5.0.9_GA") {
    main::progress("This appears to be 5.0.9_GA\n");
  } elsif ($startVersion eq "5.0.10_GA") {
    main::progress("This appears to be 5.0.10_GA\n");
  } elsif ($startVersion eq "5.0.11_GA") {
    main::progress("This appears to be 5.0.11_GA\n");
  } elsif ($startVersion eq "5.0.12_GA") {
    main::progress("This appears to be 5.0.12_GA\n");
  } elsif ($startVersion eq "5.0.13_GA") {
    main::progress("This appears to be 5.0.13_GA\n");
  } elsif ($startVersion eq "5.0.14_GA") {
    main::progress("This appears to be 5.0.14_GA\n");
  } elsif ($startVersion eq "5.0.15_GA") {
    main::progress("This appears to be 5.0.15_GA\n");
  } elsif ($startVersion eq "5.0.16_GA") {
    main::progress("This appears to be 5.0.16_GA\n");
  } elsif ($startVersion eq "5.0.17_GA") {
    main::progress("This appears to be 5.0.17_GA\n");
  } elsif ($startVersion eq "5.0.18_GA") {
    main::progress("This appears to be 5.0.18_GA\n");
  } elsif ($startVersion eq "5.0.19_GA") {
    main::progress("This appears to be 5.0.19_GA\n");
  } elsif ($startVersion eq "5.0.20_GA") {
    main::progress("This appears to be 5.0.20_GA\n");
  } elsif ($startVersion eq "5.0.21_GA") {
    main::progress("This appears to be 5.0.21_GA\n");
  } elsif ($startVersion eq "5.0.22_GA") {
    main::progress("This appears to be 5.0.22_GA\n");
  } elsif ($startVersion eq "5.0.23_GA") {
    main::progress("This appears to be 5.0.23_GA\n");
  } elsif ($startVersion eq "5.0.24_GA") {
    main::progress("This appears to be 5.0.24_GA\n");
  } elsif ($startVersion eq "5.0.25_GA") {
    main::progress("This appears to be 5.0.25_GA\n");
  } elsif ($startVersion eq "5.0.26_GA") {
    main::progress("This appears to be 5.0.26_GA\n");
  } elsif ($startVersion eq "5.0.27_GA") {
    main::progress("This appears to be 5.0.27_GA\n");
  } elsif ($startVersion eq "6.0.0_BETA1") {
    main::progress("This appears to be 6.0.0_BETA1\n");
  } elsif ($startVersion eq "6.0.0_BETA2") {
    main::progress("This appears to be 6.0.0_BETA2\n");
  } elsif ($startVersion eq "6.0.0_RC1") {
    main::progress("This appears to be 6.0.0_RC1\n");
  } elsif ($startVersion eq "6.0.0_RC2") {
    main::progress("This appears to be 6.0.0_RC2\n");
  } elsif ($startVersion eq "6.0.0_GA") {
    main::progress("This appears to be 6.0.0_GA\n");
  } elsif ($startVersion eq "6.0.1_GA") {
    main::progress("This appears to be 6.0.1_GA\n");
  } elsif ($startVersion eq "6.0.2_GA") {
    main::progress("This appears to be 6.0.2_GA\n");
  } elsif ($startVersion eq "6.0.3_GA") {
    main::progress("This appears to be 6.0.3_GA\n");
  } elsif ($startVersion eq "6.0.4_GA") {
    main::progress("This appears to be 6.0.4_GA\n");
  } elsif ($startVersion eq "6.0.5_GA") {
    main::progress("This appears to be 6.0.5_GA\n");
  } elsif ($startVersion eq "6.0.6_GA") {
    main::progress("This appears to be 6.0.6_GA\n");
  } elsif ($startVersion eq "6.0.7_GA") {
    main::progress("This appears to be 6.0.7_GA\n");
  } elsif ($startVersion eq "6.0.8_GA") {
    main::progress("This appears to be 6.0.8_GA\n");
  } elsif ($startVersion eq "6.0.9_GA") {
    main::progress("This appears to be 6.0.9_GA\n");
  } elsif ($startVersion eq "6.0.10_GA") {
    main::progress("This appears to be 6.0.10_GA\n");
  } elsif ($startVersion eq "6.0.11_GA") {
    main::progress("This appears to be 6.0.11_GA\n");
  } elsif ($startVersion eq "6.0.13_GA") {
    main::progress("This appears to be 6.0.13_GA\n");
  } elsif ($startVersion eq "6.0.14_GA") {
    main::progress("This appears to be 6.0.14_GA\n");
  } elsif ($startVersion eq "6.0.15_GA") {
    main::progress("This appears to be 6.0.15_GA\n");
  } elsif ($startVersion eq "6.0.16_GA") {
    main::progress("This appears to be 6.0.16_GA\n");
  } elsif ($startVersion eq "7.0.0_BETA1") {
    main::progress("This appears to be 7.0.0_BETA1\n");
  } elsif ($startVersion eq "7.0.0_BETA2") {
    main::progress("This appears to be 7.0.0_BETA2\n");
  } elsif ($startVersion eq "7.0.0_BETA3") {
    main::progress("This appears to be 7.0.0_BETA3\n");
  } elsif ($startVersion eq "7.0.0_RC1") {
    main::progress("This appears to be 7.0.0_RC1\n");
  } elsif ($startVersion eq "7.0.0_GA") {
    main::progress("This appears to be 7.0.0_GA\n");
  } elsif ($startVersion eq "7.0.1_GA") {
    main::progress("This appears to be 7.0.1_GA\n");
  } elsif ($startVersion eq "7.1.0_GA") {
    main::progress("This appears to be 7.1.0_GA\n");
  } elsif ($startVersion eq "7.1.1_GA") {
    main::progress("This appears to be 7.1.1_GA\n");
  } elsif ($startVersion eq "7.1.2_GA") {
    main::progress("This appears to be 7.1.2_GA\n");
  } elsif ($startVersion eq "7.1.3_GA") {
    main::progress("This appears to be 7.1.3_GA\n");
  } elsif ($startVersion eq "7.1.4_GA") {
    main::progress("This appears to be 7.1.4_GA\n");
  } elsif ($startVersion eq "7.2.0_GA") {
    main::progress("This appears to be 7.2.0_GA\n");
  } elsif ($startVersion eq "7.2.1_GA") {
    main::progress("This appears to be 7.2.1_GA\n");
  } elsif ($startVersion eq "7.2.2_GA") {
    main::progress("This appears to be 7.2.2_GA\n");
  } elsif ($startVersion eq "7.2.3_GA") {
    main::progress("This appears to be 7.2.3_GA\n");
  } elsif ($startVersion eq "7.2.4_GA") {
    main::progress("This appears to be 7.2.4_GA\n");
  } elsif ($startVersion eq "8.0.0_BETA1") {
    main::progress("This appears to be 8.0.0_BETA1\n");
  } elsif ($startVersion eq "8.0.0_BETA2") {
    main::progress("This appears to be 8.0.0_BETA2\n");
  } elsif ($startVersion eq "8.0.0_BETA3") {
    main::progress("This appears to be 8.0.0_BETA3\n");
  } elsif ($startVersion eq "8.0.0_BETA4") {
    main::progress("This appears to be 8.0.0_BETA4\n");
  } elsif ($startVersion eq "8.0.0_BETA5") {
    main::progress("This appears to be 8.0.0_BETA5\n");
  } elsif ($startVersion eq "8.0.0_GA") {
    main::progress("This appears to be 8.0.0_GA\n");
  } elsif ($startVersion eq "8.0.1_GA") {
    main::progress("This appears to be 8.0.1_GA\n");
  } elsif ($startVersion eq "8.0.2_GA") {
    main::progress("This appears to be 8.0.2_GA\n");
  } elsif ($startVersion eq "8.0.3_GA") {
    main::progress("This appears to be 8.0.3_GA\n");
  } elsif ($startVersion eq "8.0.4_GA") {
    main::progress("This appears to be 8.0.4_GA\n");
  } elsif ($startVersion eq "9.0.0_BETA1") {
    main::progress("This appears to be 9.0.0_BETA1\n");
  } else {
    main::progress("I can't upgrade version $startVersion\n\n");
    return 1;
  }

  
  my $found = 0;
  foreach my $v (@versionOrder) {
    $found = 1 if ($v eq $startVersion);
    if ($found) {
      $needMysqlTableCheck=1 if ($v eq "4.5.2_GA");
      $needMysqlUpgrade=1 if ($v eq "7.0.0_BETA1");
      $needMysqlUpgrade=1 if ($v eq "8.0.0_GA");
    }
    last if ($v eq $targetVersion);
  }
  main::setLocalConfig("ssl_allow_untrusted_certs", "true") if ($startMajor <= 7 && $targetMajor >= 8);
  # start ldap
  if (main::isInstalled ("zmail-ldap")) {
    if($startMajor < 6 && $targetMajor >= 6) {
      my $rc=&migrateLdap("8.0.0_BETA3");
      if ($rc) { return 1; }
    } elsif($startMajor < 8) {
      my $rc=&upgradeLdap("8.0.0_BETA3");
      if ($rc) { return 1; }
    } elsif ($startMajor == 8 && $startMinor == 0 && $startMicro < 3) {
      my $rc=&reloadLdap("8.0.3_GA");
      if ($rc) { return 1; }
    }
    if (startLdap()) {return 1;} 
  }

  if (main::isInstalled("zmail-store")) {

    doMysqlTableCheck() if ($needMysqlTableCheck);
    doMysqlUpgrade() if ($needMysqlUpgrade);
  
    doBackupRestoreVersionUpdate($startVersion);

    if ($curSchemaVersion < $hiVersion) {
      main::progress("Schema upgrade required from version $curSchemaVersion to $hiVersion.\n");
    }

    # fast tracked updater (ie invoke mysql once)
    if ($curSchemaVersion >= $comboLowVersion && $curSchemaVersion < $comboHiVersion) {
      if (runSchemaUpgrade("ComboUpdater")) { return 1; }
      $curSchemaVersion = Migrate::getSchemaVersion();
    }

    # the old slow painful way (ie lots of mysql invocations)
    while ($curSchemaVersion >= $lowVersion && $curSchemaVersion < $hiVersion) {
      if (($curSchemaVersion == 21) && $needVolumeHack) {
        if (runSchemaUpgrade ("UniqueVolume")) { return 1; }
      } 
      if (runSchemaUpgrade ($curSchemaVersion)) { return 1; }
      $curSchemaVersion = Migrate::getSchemaVersion();
    }
     if ( $startMajor = 7 && $targetMajor >= 8) {
       # Bug #78297
       my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
       my $imap_cache_data_files = $zmail_home . "/data/mailboxd/imap-*";
       system("/bin/rm -f ${imap_cache_data_files} 2> /dev/null");
     }
    stopSql();
  }

  $found = 0;
  foreach my $v (@versionOrder) {
    #main::progress("Checking $v\n");
    if ($v eq $startVersion) {
      $found = 1;
      # skip startVersion func unless we are on the same version and build increments
      next unless ($startVersion eq $targetVersion && $targetBuild > $startBuild);
    }
    if ($found) {
      if (defined ($updateFuncs{$v}) ) {
        if (&{$updateFuncs{$v}}($startBuild, $targetVersion, $targetBuild)) {
          return 1;
        }
      } else {
        main::progress("I don't know how to update $v - exiting\n");
        return 1;
      }
    }
    if ($v eq $targetVersion) {
      last;
    }
  }
  if ($isLdapMaster) {
    main::progress("Updating global config and COS's with attributes introduced after $startVersion...");
    main::progress((&runAttributeUpgrade($startVersion)) ? "failed.\n" : "done.\n");
    main::setLdapGlobalConfig("zmailVersionCheckLastResponse", "");
  }
  if ($needSlapIndexing) {
    main::detail("Updating slapd indices\n");
    &indexLdap();
        }
  if (main::isInstalled ("zmail-ldap")) {
    stopLdap();
  }

  return 0;
}

sub upgradeBM1 {
  main::progress("Updating from 3.0.M1\n");

  my $t = time()+(60*60*24*60);
  my @d = localtime($t);
  my $expiry = sprintf ("%04d%02d%02d",$d[5]+1900,$d[4]+1,$d[3]);
  main::runAsZmail("zmlocalconfig -e trial_expiration_date=$expiry");

  my $ldh = main::runAsZmail("zmlocalconfig -m nokey ldap_host");
  chomp $ldh;
  my $ldp = main::runAsZmail("zmlocalconfig -m nokey ldap_port");
  chomp $ldp;

  main::progress("Updating ldap url configuration\n");
  main::runAsZmail("zmlocalconfig -e ldap_url=ldap://${ldh}:${ldp}");
  main::runAsZmail("zmlocalconfig -e ldap_master_url=ldap://${ldh}:${ldp}");

  if ($hn eq $ldh) {
    main::progress("Setting ldap master to true\n");
    main::runAsZmail("zmlocalconfig -e ldap_is_master=true");
  }

  main::progress("Updating index configuration\n");
  main::runAsZmail("zmlocalconfig -e zmail_index_idle_flush_time=600");
  main::runAsZmail("zmlocalconfig -e zmail_index_lru_size=100");
  main::runAsZmail("zmlocalconfig -e zmail_index_max_uncommitted_operations=200");
  main::runAsZmail("zmlocalconfig -e logger_mysql_port=7307");

  main::progress("Updating zmail user configuration\n");
  main::runAsZmail("zmlocalconfig -e zmail_user=zmail");
  my $UID = `id -u zmail`;
  chomp $UID;
  my $GID = `id -g zmail`;
  chomp $GID;
  main::runAsZmail("zmlocalconfig -e zmail_uid=${UID}");
  main::runAsZmail("zmlocalconfig -e zmail_gid=${GID}");
  main::runAsZmail("zmcreatecert");

  return 0;
}

sub upgradeBM2 {
  main::progress("Updating from 3.0.0_M2\n");

  movePostfixQueue ("2.2.3","2.2.5");

  return 0;
}

sub upgradeBM3 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.0.0_M3\n");

  # $startBuild -> $targetBuild
  if ($startVersion eq "3.0.0_M2" || $startVersion eq "3.0.M1" || $startBuild <= 346) {
    # Set mode and authhost
    main::runAsZmail("$ZMPROV ms $hn zmailMailMode http");
    main::runAsZmail("$ZMPROV ms $hn zmailMtaAuthHost $hn");
  }
  if (($startVersion eq "3.0.0_M2" || $startVersion eq "3.0.M1" || $startBuild <= 427) &&
    main::isInstalled ("zmail-ldap")) {

    main::progress ("Updating ldap GAL attributes\n");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap zmailId=zmailId +zmailGalLdapAttrMap objectClass=objectClass +zmailGalLdapAttrMap zmailMailForwardingAddress=zmailMailForwardingAddress");

    main::progress ("Updating ldap CLIENT attributes\n");
    main::runAsZmail("$ZMPROV mcf +zmailAccountClientAttr zmailIsDomainAdminAccount +zmailAccountClientAttr zmailFeatureIMEnabled");
    main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailFeatureIMEnabled");
    main::progress ("Updating ldap domain admin attributes\n");
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAccountStatus");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr company");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr cn");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr co");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr displayName");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr gn");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr description");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr initials");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr l");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAttachmentsBlocked");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAttachmentsIndexingEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAttachmentsViewInHtmlOnly");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAuthTokenLifetime");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAuthLdapExternalDn");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailAdminAuthTokenLifetime");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailContactMaxNumEntries");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureContactsEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureGalEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureHtmlComposeEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureCalendarEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureIMEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureTaggingEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureAdvancedSearchEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureSavedSearchesEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureConversationsEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureChangePasswordEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureInitialSearchPreferenceEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureFiltersEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailForeignPrincipal");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailImapEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailIsDomainAdminAccount");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailIdleSessionTimeout");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailMessageLifetime");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailMinPollingInterval");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailSpamLifetime");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailTrashLifetime");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailNotes");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordLocked");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordMinLength");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordMaxLength");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordMinAge");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordMaxAge");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordEnforceHistory");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPasswordMustChange");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPop3Enabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefTimeZoneId");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefUseTimeZoneListInCalendar");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefComposeInNewWindow");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefComposeFormat");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefHtmlEditorDefaultFontColor");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefHtmlEditorDefaultFontFamily");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefHtmlEditorDefaultFontSize");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefForwardReplyInOriginalFormat");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefAutoAddAddressEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefShowFragments");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefShowSearchString");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarFirstDayOfWeek");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarInitialView");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarInitialCheckedCalendars");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarUseQuickAdd");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarAlwaysShowMiniCal");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarNotifyDelegatedChanges");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefContactsInitialView");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefDedupeMessagesSentToSelf");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefForwardIncludeOriginalText");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefForwardReplyPrefixChar");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefGroupMailBy");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefImapSearchFoldersEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefIncludeSpamInSearch");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefIncludeTrashInSearch");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailInitialSearch");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailItemsPerPage");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefContactsPerPage");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMessageViewHtmlPreferred");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailPollingInterval");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailSignature");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailSignatureEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailSignatureStyle");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefNewMailNotificationAddress");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefNewMailNotificationEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefOutOfOfficeReply");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefOutOfOfficeReplyEnabled");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefReplyIncludeOriginalText");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefReplyToAddress");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefSaveToSent");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefSentMailFolder");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefUseKeyboardShortcuts");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailZimletAvailableZimlets");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailZimletUserProperties");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr o");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr ou");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr physicalDeliveryOfficeName");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr postalAddress");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr postalCode");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr sn");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr st");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr telephoneNumber");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr title");
    print ".";
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailStatus");
    print "\n";

    main::progress ("Updating ldap server attributes\n");

    main::runAsZmail("$ZMPROV mcf zmailLmtpNumThreads 20 ");
    main::runAsZmail("$ZMPROV mcf zmailMessageCacheSize 1671168 ");
    main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailMessageCacheSize +zmailServerInheritedAttr zmailMtaAuthHost +zmailServerInheritedAttr zmailMtaAuthURL +zmailServerInheritedAttr zmailMailMode");
    main::runAsZmail("$ZMPROV mcf -zmailMtaRestriction reject_non_fqdn_hostname");
  }
  if ($startVersion eq "3.0.0_M2" || $startVersion eq "3.0.M1" || $startBuild <= 436) {
    if (main::isInstalled("zmail-store")) {
      if (startSql()) { return 1; }
      main::runAsZmail("perl -I${scriptDir} ${scriptDir}/fixConversationCounts.pl");
      stopSql();
    }

    if (main::isInstalled("zmail-ldap")) {
      main::progress ("Updating ldap domain admin attributes\n");
      main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr givenName");
      main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailForwardingAddress");
      main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailNewMailNotificationSubject");
      main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailNewMailNotificationFrom");
      main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailNewMailNotificationBody");
      main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailMtaMyNetworks");
    }
  }
  return 0;
}

sub upgradeBM4 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.0.0_M4\n");
  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailMailStatus");
    if ($startVersion eq "3.0.0_M2" || $startVersion eq "3.0.M1" || $startVersion eq "3.0.0_M3" ||
      $startBuild <= 41) {
      main::runAsZmail("$ZMPROV mcf +zmailAccountClientAttr zmailFeatureViewInHtmlEnabled");
      main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureViewInHtmlEnabled");
      main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailFeatureViewInHtmlEnabled");
      main::runAsZmail("$ZMPROV mc default zmailFeatureViewInHtmlEnabled FALSE");
    }
  }
  if ($startVersion eq "3.0.0_M4" && $startBuild == 41) {
    if (main::isInstalled("zmail-store")) {
      if (startSql()) { return 1; }
      main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20060120-Appointment.pl");
      stopSql();
    }
  }

  return 0;
}

sub upgradeBGA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.0.0_GA\n");
  return 0;

  if ( -d "/opt/zmail/clamav-0.87.1/db" && -d "/opt/zmail/clamav-0.88" &&
    ! -d "/opt/zmail/clamav-0.88/db" )  {
      `cp -fR /opt/zmail/clamav-0.87.1/db /opt/zmail/clamav-0.88`;
  }

  movePostfixQueue ("2.2.5","2.2.8");


}

sub upgrade301GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.0.1_GA\n");

  unless(open (G, "$ZMPROV gcf zmailGalLdapFilterDef |")) {
    Migrate::myquit(1,"Can't open zmprov: $!");
  }
  `$ZMPROV mcf zmailGalLdapFilterDef ''`;
  while (<G>) {
    chomp;
    s/\(zmailMailAddress=\*%s\*\)//;
    s/zmailGalLdapFilterDef: //;
    `$ZMPROV mcf +zmailGalLdapFilterDef \'$_\'`;
  }

  # This change was made in both main and CRAY
  # CRAY build 202
  # MAIN build 223
  if ( ($startVersion eq "3.0.0_GA" && $startBuild <= 202) ||
    ($startVersion eq "3.0.0_M2" || $startVersion eq "3.0.M1" || 
    $startVersion eq "3.0.0_M3" || $startVersion eq "3.0.0_M4")
    ) {
    main::runAsZmail("zmlocalconfig -e postfix_version=2.2.9");
    movePostfixQueue ("2.2.8","2.2.9");

  }
  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailDomainInheritedAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mc default zmailFeatureSharingEnabled TRUE");

  return 0;
}

sub upgrade310GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.1.0_GA\n");
  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailDomainInheritedAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailAccountClientAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mc default zmailFeatureSharingEnabled TRUE");

  main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmail:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList)))'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailAccounts:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*)(zmailMailAddress=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResources:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*)(zmailMailAddress=*%s*))(objectclass=zmailCalendarResource))'");

  # Bug 6077
  main::runAsZmail("$ZMPROV mcf -zmailGalLdapAttrMap 'givenName=firstName'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'gn=firstName'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'description=notes'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'zmailCalResType=zmailCalResType'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'zmailCalResLocationDisplayName=zmailCalResLocationDisplayName'");

  # bug: 2799
  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailPrefCalendarApptReminderWarningTime");
  main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarApptReminderWarningTime");
  main::runAsZmail("$ZMPROV mc default zmailPrefCalendarApptReminderWarningTime 5");

  main::runAsZmail("$ZMPROV mcf +zmailAccountClientAttr zmailFeatureMailForwardingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailFeatureMailForwardingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailFeatureMailForwardingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailForwardingAddress");
  main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefMailLocalDeliveryDisabled");
  main::runAsZmail("$ZMPROV mc default zmailFeatureMailForwardingEnabled TRUE");

  # bug 6077
  main::runAsZmail("$ZMPROV mcf +zmailAccountClientAttr zmailLocale");
  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailLocale");
  main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailLocale");
  main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailLocale");

  # bug 6834
  main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailRemoteManagementCommand");
  main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailRemoteManagementUser");
  main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailRemoteManagementPrivateKeyPath");
  main::runAsZmail("$ZMPROV mcf +zmailServerInheritedAttr zmailRemoteManagementPort");
  main::runAsZmail("$ZMPROV ms $hn zmailRemoteManagementCommand /opt/zmail/libexec/zmrcd");
  main::runAsZmail("$ZMPROV ms $hn zmailRemoteManagementUser zmail");
  main::runAsZmail("$ZMPROV ms $hn zmailRemoteManagementPrivateKeyPath /opt/zmail/.ssh/zmail_identity");
  main::runAsZmail("$ZMPROV ms $hn zmailRemoteManagementPort 22");

  # bug: 6828
  main::runAsZmail("$ZMPROV mcf -zmailGalLdapAttrMap zmailMailAlias=email2");

  if ( ($startVersion eq "3.1.0_GA" && $startBuild <= 303) ||
    ($startVersion eq "3.0.0_GA" || $startVersion eq "3.0.0_M2" || $startVersion eq "3.0.M1" || 
    $startVersion eq "3.0.0_M3" || $startVersion eq "3.0.0_M4")
    ) {
    if (-f "/opt/zmail/redolog/redo.log") {
      `mv /opt/zmail/redolog/redo.log /opt/zmail/redolog/redo.log.preupgrade`;
    }
    if (-d "/opt/zmail/redolog/archive") {
      `mv /opt/zmail/redolog/archive /opt/zmail/redolog/archive.preupgrade`;
    }
  }

  # bug 7241
  main::runAsZmail("/opt/zmail/bin/zmsshkeygen");

  return 0;
}

sub upgrade311GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.1.1_GA\n");

  return 0;
}

sub upgrade312GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.1.2_GA\n");
  return 0;
}

sub upgrade313GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.1.3_GA\n");

  # removing this per bug 10901
  #my @accounts = `$su "$ZMPROV gaa"`;
  #open (G, "| $ZMPROV ") or die "Can't open zmprov: $!";
  #foreach (@accounts) {
  # chomp;
  # print G "ma $_ zmailPrefMailLocalDeliveryDisabled FALSE\n";
  #}
  #close G;

  return 0;
}

sub upgrade314GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.1.4_GA\n");
  if (main::isInstalled ("zmail-ldap")) {
  my $a = <<EOF;
# parse text/plain internally
dn: cn=text/plain,cn=mime,cn=config,cn=zmail
changetype: add
zmailMimeType: text/plain
cn: text/plain
objectClass: zmailMimeEntry
zmailMimeIndexingEnabled: TRUE
zmailMimeHandlerClass: TextPlainHandler
zmailMimeFileExtension: text
zmailMimeFileExtension: txt
description: Plain Text Document
EOF

  open L, ">/tmp/text-plain.ldif";
  print L $a;
  close L;
  my $ldap_pass = `$su "zmlocalconfig -s -m nokey zmail_ldap_password"`;
  my $ldap_url = `$su "zmlocalconfig -s -m nokey ldap_url"`;
  chomp $ldap_pass;
  chomp $ldap_url;
  main::runAsZmail("ldapmodify -c -H $ldap_url -D uid=zmail,cn=admins,cn=zmail -x -w $ldap_pass -f /tmp/text-plain.ldif");
  }
  return 0;
}

sub upgrade32M1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.2.0_M1\n");

  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mcf +zmailDomainInheritedAttr zmailFeatureSharingEnabled");
  main::runAsZmail("$ZMPROV mc default zmailFeatureSharingEnabled TRUE");

  main::runAsZmail("$ZMPROV mcf zmailGalLdapFilterDef 'zmailAccounts:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*)(zmailMailAddress=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResources:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*)(zmailMailAddress=*%s*))(objectclass=zmailCalendarResource))'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'ad:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*))(!(msExchHideFromAddressLists=TRUE))(mailnickname=*)(|(&(objectCategory=person)(objectClass=user)(!(homeMDB=*))(!(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=user)(|(homeMDB=*)(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=contact))(objectCategory=group)(objectCategory=publicFolder)(objectCategory=msExchDynamicDistributionList)))'");

  # Bug 6077
  main::runAsZmail("$ZMPROV mcf -zmailGalLdapAttrMap 'givenName=firstName'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'gn=firstName'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'description=notes'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'zmailCalResType=zmailCalResType'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'zmailCalResLocationDisplayName=zmailCalResLocationDisplayName'");

  # bug: 2799
  main::runAsZmail("$ZMPROV mcf +zmailCOSInheritedAttr zmailPrefCalendarApptReminderWarningTime");
  main::runAsZmail("$ZMPROV mcf +zmailDomainAdminModifiableAttr zmailPrefCalendarApptReminderWarningTime");
  main::runAsZmail("$ZMPROV mc default zmailPrefCalendarApptReminderWarningTime 5");

  # Bug 7590
  my @coses = `$su "$ZMPROV gac"`;
  foreach my $cos (@coses) {
    chomp $cos;
    main::runAsZmail("$ZMPROV mc $cos zmailFeatureSkinChangeEnabled TRUE zmailPrefSkin steel zmailFeatureNotebookEnabled TRUE");
  }

  # Bug 7590
  # The existing one whose default we flipped, someone else who cares about it
  # should yes/no the flip.  The attribute is zmailPrefAutoAddAddressEnabled which
  # used to be FALSE by default and as of Edison we are going TRUE by default for
  # all new installs.

  # bug 7588

  main::runAsZmail("$ZMPROV mcf -zmailGalLdapAttrMap gn=firstName");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap givenName,gn=firstName ");

  # Bug 5466
  my $acct;
  $acct = (split(/\s+/, `$su "$ZMPROV gcf zmailSpamIsSpamAccount"`))[-1];
  main::runAsZmail("$ZMPROV ma $acct zmailHideInGal TRUE")
    if ($acct ne "");

  $acct = (split(/\s+/, `$su "$ZMPROV gcf zmailSpamIsNotSpamAccount"`))[-1];
  main::runAsZmail("$ZMPROV ma $acct zmailHideInGal TRUE")
    if ($acct ne "");

  # Bug 7723
  main::runAsZmail("$ZMPROV mcf -zmailGalLdapAttrMap zmailMailDeliveryAddress,mail=email");

  main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap zmailMailDeliveryAddress,zmailMailAlias,mail=email,email2,email3,email4,email5,email6");


  if ( -d "/opt/zmail/amavisd-new-2.3.3/db" && -d "/opt/zmail/amavisd-new-2.4.1" && ! -d "/opt/zmail/amavisd-new-2.4.1/db" ) {
    `mv /opt/zmail/amavisd-new-2.3.3/db /opt/zmail/amavisd-new-2.4.1/db`;
    `chown -R zmail:zmail /opt/zmail/amavisd-new-2.4.1/db`;
  }
  if ( -d "/opt/zmail/amavisd-new-2.3.3/.spamassassin" && -d "/opt/zmail/amavisd-new-2.4.1" && ! -d "/opt/zmail/amavisd-new-2.4.1/.spamassassin" ) {
    `mv /opt/zmail/amavisd-new-2.3.3/.spamassassin /opt/zmail/amavisd-new-2.4.1/.spamassassin`;
    `chown -R zmail:zmail /opt/zmail/amavisd-new-2.4.1/.spamassassin`;
  }


  return 0;
}

sub upgrade32M2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 3.2.0_M2\n");

  # bug 8121
  updateMySQLcnf();

  # Bug 9096
  my $acct = `$su "$ZMPROV gcf zmailSpamIsSpamAccount"`;
  chomp $acct;
  $acct =~ s/.* //;
  if ($acct ne "") {
    main::runAsZmail("$ZMPROV ma $acct zmailIsSystemResource TRUE");
  }
  $acct = `$su "$ZMPROV gcf zmailSpamIsNotSpamAccount"`;
  chomp $acct;
  $acct =~ s/.* //;
  if ($acct ne "") {
    main::runAsZmail("$ZMPROV ma $acct zmailIsSystemResource TRUE");
  }

  # Bug 7850
  my @coses = `$su "$ZMPROV gac"`;
  foreach my $cos (@coses) {
    chomp $cos;
    main::runAsZmail("$ZMPROV mc $cos zmailFeatureNewMailNotificationEnabled TRUE zmailFeatureOutOfOfficeReplyEnabled TRUE");
  }

  return 0;
}

sub upgrade400RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.0_RC1\n");

  # Bug 9504
  if (-d "/opt/zmail/redolog" && ! -e "/opt/zmail/redolog-pre-4.0") {
    `mv /opt/zmail/redolog /opt/zmail/redolog-pre-4.0`;
    `mkdir /opt/zmail/redolog`;
    `chown zmail:zmail /opt/zmail/redolog`;
  }

  if (-e "/opt/zmail/backup" && ! -e "/opt/zmail/backup-pre-4.0") {
    `mv /opt/zmail/backup /opt/zmail/backup-pre-4.0`;
    `mkdir /opt/zmail/backup`;
    `chown zmail:zmail /opt/zmail/backup`;
  }

  # Bug 9419
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'adAutoComplete:(&(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*))(!(msExchHideFromAddressLists=TRUE))(mailnickname=*)(|(&(objectCategory=person)(objectClass=user)(!(homeMDB=*))(!(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=user)(|(homeMDB=*)(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=contact))(objectCategory=group)(objectCategory=publicFolder)(objectCategory=msExchDynamicDistributionList)))'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'externalLdapAutoComplete:(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailAccountAutoComplete:(&(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");
  main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResourceAutoComplete:(&(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(objectclass=zmailCalendarResource))'");

  # Bug 9693
  if ($startVersion eq "3.2.0_M1" || $startVersion eq "3.2.0_M2") {
    if (main::isInstalled("zmail-store")) {
      if (startSql()) { return 1; }
      main::runAsZmail("sh ${scriptDir}/migrate20060807-WikiDigestFixup.sh");
      stopSql();
    }
  }
  
  return 0;
}

sub upgrade400GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.0_GA\n");
  return 0;
}

sub upgrade401GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.1_GA\n");

  # bug 10346
  my $globalWikiAcct = main::getLdapConfigValue("zmailNotebookAccount");
  next unless $globalWikiAcct;
  main::runAsZmail("/opt/zmail/bin/zmprov ma $globalWikiAcct zmailFeatureNotebookEnabled TRUE");
  
  # bug 10388
  clearTomcatWorkDir();

  return 0;
}

sub upgrade402GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.2_GA\n");

  if (main::isInstalled("zmail-ldap")) {
    # bug 10401
    my @coses = `$su "$ZMPROV gac"`;
    foreach my $cos (@coses) {
      chomp $cos;
      my $cur_value = 
        main::getLdapCOSValue("zmailFeatureMobileSyncEnabled",$cos);

      main::runAsZmail("$ZMPROV mc $cos zmailFeatureMobileSyncEnabled FALSE")
        if ($cur_value ne "TRUE");
    }

    # bug 10845
    main::runAsZmail("$ZMPROV mcf zmailMailURL /zmail"); 
    
  }

  return 0;
}

sub upgrade403GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.3_GA\n");

  #8081 remove amavis tmpfs
  if ( -f "/etc/fstab" ) {
    my $mount = (split(/\s+/, `egrep -e '^/dev/shm.*amavisd.*tmpfs' /etc/fstab`))[1];
    if ($mount ne "" ) {
      `umount $mount > /dev/null 2>&1`;
      `sed -i.zmail -e 's:\\(^/dev/shm.*amavis.*\\):#\\1:' /etc/fstab`;
      if ($? != 0) {
        `mv /etc/fstab.zmail /etc/fstab`;
      }
    }
  }

  if (main::isInstalled("zmail-ldap")) {
    # bug 11315
    my $remoteManagementUser = 
      main::getLdapConfigValue("zmailRemoteManagementUser");
    main::runAsZmail("$ZMPROV mcf zmailRemoteManagementUser zmail") 
      if ($remoteManagementUser eq "");

    my $remoteManagementPort = 
      main::getLdapConfigValue("zmailRemoteManagementPort");
    main::runAsZmail("$ZMPROV mcf zmailRemoteManagementPort 22") 
     if ($remoteManagementPort eq "");

    my $remoteManagementPrivateKeyPath = 
      main::getLdapConfigValue("zmailRemoteManagementPrivateKeyPath");
    main::runAsZmail("$ZMPROV mcf zmailRemoteManagementPrivateKeyPath /opt/zmail/.ssh/zmail_identity") 
      if ($remoteManagementPrivateKeyPath eq "");

    my $remoteManagementCommand = 
      main::getLdapConfigValue("zmailRemoteManagementCommand");
    main::runAsZmail("$ZMPROV mcf zmailRemoteManagementCommand /opt/zmail/libexec/zmrcd") 
      if ($remoteManagementCommand eq "");
  }

  return 0;
}

sub upgrade404GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.4_GA\n");
  return 0;
}

sub upgrade405GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.0.5_GA\n");
  return 0;
}

sub upgrade410BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.1.0_BETA1\n");

  # bug 9622
  clearRedologDir("/opt/zmail/redolog", $targetVersion);
  clearBackupDir("/opt/zmail/backup", $targetVersion);

  # migrate amavis data 
  migrateAmavisDB("2.4.3");

  return 0;
}

sub upgrade450BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.0_BETA1\n");
  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mc default zmailPrefUseKeyboardShortcuts TRUE");
  }
  return 0;
}

sub upgrade450BETA2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.0_BETA2\n");
  return 0;
}

sub upgrade450RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.0_RC1\n");
  if (main::isInstalled("zmail-ldap")) {
    # bug 12031
    my @coses = `$su "$ZMPROV gac"`;
    foreach my $cos (@coses) {
      chomp $cos;
      main::runAsZmail("$ZMPROV mc $cos zmailFeaturePop3DataSourceEnabled TRUE zmailPrefReadingPaneEnabled TRUE zmailPrefUseRfc2231 FALSE zmailFeatureIdentitiesEnabled TRUE zmailPasswordLockoutDuration 1h zmailPasswordLockoutEnabled FALSE zmailPasswordLockoutFailureLifetime 1h zmailPasswordLockoutMaxFailures 10");
    }

    # bah-bye timezones
    # replaced by /opt/zmail/conf/timezones.ics
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey zmail_ldap_password"`;
    my $ldap_master_url = `$su "zmlocalconfig -s -m nokey ldap_master_url"`;
    my $ldap; 
    chomp($ldap_master_url);
    chomp($ldap_pass);
    unless($ldap = Net::LDAP->new($ldap_master_url)) { 
      main::progress("Unable to contact $ldap_master_url: $!\n"); 
      return 1;
    }
    my $dn = 'cn=timezones,cn=config,cn=zmail';
    my $result = $ldap->bind("uid=zmail,cn=admins,cn=zmail", password => $ldap_pass);
    unless($result->code()) {
      $result = DeleteLdapTree($ldap,$dn);
      main::progress($result->code() ? "Failed to delete $dn: ".$result->error()."\n" : "Deleted $dn\n");
    }
    $result = $ldap->unbind;
  }
  return 0;
}

sub upgrade450RC2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.0_RC2\n");
  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mcf zmailSmtpSendAddOriginatingIP TRUE");
  }

  if (main::isInstalled("zmail-logger")) {
    main::setLocalConfig("stats_img_folder", "/opt/zmail/logger/db/work");
  }
    
  return 0;
}

sub upgrade450GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.0_GA\n");
  return 0;
}
sub upgrade451GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.1_GA\n");
  if (main::isInstalled("zmail-store")) {
    my $tomcat_java_options = main::getLocalConfig("tomcat_java_options");
    $tomcat_java_options .= " -Djava.awt.headless=true"
      unless ($tomcat_java_options =~ /java\.awt\.headless/);
    main::detail("Modified tomcat_java_options=$tomcat_java_options");
    main::setLocalConfig("tomcat_java_options", "$tomcat_java_options");
  }
  return 0;
}
sub upgrade452GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.2_GA\n");
  return 0;
}
sub upgrade453GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.3_GA\n");
  if (main::isInstalled("zmail-store")) {
    # bug 14160
    my ($maxMessageSize, $zmailMessageCacheSize, $systemMemorySize, $newcache);
    my $tomcatHeapPercent = main::getLocalConfig("tomcat_java_heap_memory_percent");
    $tomcatHeapPercent = 40 if ($tomcatHeapPercent eq "");
    $maxMessageSize = main::getLdapConfigValue("zmailMtaMaxMessageSize");
    $zmailMessageCacheSize = main::getLdapConfigValue("zmailMessageCacheSize");
    $systemMemorySize = main::getSystemMemory();

    my $tomcatHeapSize = ($systemMemorySize*($tomcatHeapPercent/100));
    $newcache = int($tomcatHeapSize*.05*1024*1024*1024);
   
    main::runAsZmail("$ZMPROV mcf zmailMessageCacheSize $newcache")
      if ($newcache > $zmailMessageCacheSize);
    
  }
  return 0;
}
sub upgrade454GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.4_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    main::setLocalConfig("ldap_log_level", "32768");
  }
  return 0;
}
sub upgrade455GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.5_GA\n");
  return 0;
}
sub upgrade456GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.6_GA\n");
  # bug 16425 rewrite default perms on localconfig.xml
  main::setLocalConfig("upgrade_dummy", "1");
  main::deleteLocalConfig("upgrade_dummy");

  # bug 17879
  if (main::isInstalled("zmail-store")) {
    updateMySQLcnf();
  }

  return 0;
}
sub upgrade457GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.7_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    #bug 17887
    main::runAsZmail("$ZMPROV mcf zmailHttpNumThreads 100");
    main::runAsZmail("$ZMPROV mcf zmailHttpSSLNumThreads 50");
    #bug 17794
    main::runAsZmail("$ZMPROV mcf zmailMtaMyDestination localhost");
    #bug 18388
    my $threads = (split(/\s+/, `$su "$ZMPROV gcf zmailPop3NumThreads"`))[-1];
    main::runAsZmail("$ZMPROV mcf zmailPop3NumThreads 100")
      if ($threads eq "20");
  }
  if (main::isInstalled("zmail-mta")) {
    # migrate amavis data 
    migrateAmavisDB("2.5.2");
  }

  if (main::isInstalled("zmail-store")) {
    # 19749
    updateMySQLcnf();
    if (startSql()) { return 1; }
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrateLargeMetadata.pl -a");
    stopSql();
  }

  if (main::isInstalled("zmail-logger")) {
    updateLoggerMySQLcnf();
  }
  return 0;
}

sub upgrade458GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.8_GA\n");
  return 0;
}

sub upgrade459GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.9_GA\n");
  if (main::isInstalled("zmail-store")) {
    main::setLocalConfig("zmail_mailbox_purgeable", "true");
  }
  return 0;
}

sub upgrade4510GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.5.10_GA\n");
  if (main::isInstalled("zmail-store")) {
    main::setLocalConfig("tomcat_thread_stack_size", "256k");
  }
  return 0;
}

sub upgrade4511GA {
  main::progress("Updating from 4.5.11_GA\n");
  return 0;
}

sub upgrade460BETA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.6.0_BETA\n");
  return 0;
}
sub upgrade460RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.6.0_RC1\n");
  return 0;
}
sub upgrade460GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.6.0_GA\n");
  if (main::isInstalled("zmail-store")) {
    # 19749
    updateMySQLcnf();
  }
  if (main::isInstalled("zmail-logger")) {
    updateLoggerMySQLcnf();
  }
  return 0;
}
sub upgrade461RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 4.6.1_RC1\n");
  return 0;
}

sub upgrade500BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_BETA1\n");

  my $zmail_home = main::getLocalConfig("zmail_home");
  $zmail_home = "/opt/zmail" if ($zmail_home eq "");

  if (main::isInstalled("zmail-store")) {
    if (startSql()) { return 1; }
    Migrate::log("Executing ${scriptDir}/migrate20070302-NullContactVolumeId.pl"); 
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20070302-NullContactVolumeId.pl");
    stopSql();

    my $mailboxd_java_options = main::getLocalConfig("mailboxd_java_options");
    if ($mailboxd_java_options eq "") {
      my $tomcat_java_options = main::getLocalConfig("tomcat_java_options");
      main::setLocalConfig("mailboxd_java_options", "$tomcat_java_options");
      main::deleteLocalConfig("tomcat_java_options");
    }


    my $mailboxd_directory = main::getLocalConfig("mailboxd_directory");
    if ($mailboxd_directory eq "") {
      main::setLocalConfig("mailboxd_directory", "${zmail_home}/mailboxd");
      $main::config{mailboxd_directory} = $mailboxd_directory;
      main::deleteLocalConfig("tomcat_directory");
    }

    my $mailboxd_keystore = main::getLocalConfig("mailboxd_keystore");
    if ($mailboxd_keystore eq "" || -f "${zmail_home}/mailboxd/etc/jettyrc") {
      $mailboxd_keystore="${zmail_home}/mailboxd/etc/keystore";
      main::deleteLocalConfig("tomcat_keystore");
    } elsif ( -f "${zmail_home}/mailboxd/conf/server.xml.in") {
      $mailboxd_keystore="${zmail_home}/mailboxd/conf/keystore";
    }
    $main::config{mailboxd_keystore} = $mailboxd_keystore;
    main::setLocalConfig("mailboxd_keystore", "${mailboxd_keystore}");

    my $mailboxd_java_heap_memory_percent = 
      main::getLocalConfig("mailboxd_java_heap_memory_percent");

    if ($mailboxd_java_heap_memory_percent eq "") {
      my $tomcat_java_heap_memory_percent  = 
        main::getLocalConfig("tomcat_java_heap_memory_percent");
      $tomcat_java_heap_memory_percent = 40 
        if ($tomcat_java_heap_memory_percent eq "");
      main::setLocalConfig("mailboxd_java_heap_memory_percent", 
        "$tomcat_java_heap_memory_percent");
      main::deleteLocalConfig("tomcat_java_heap_memory_percent");
    }

    my $mailboxd_java_home = main::getLocalConfig("mailboxd_java_home");
    if ($mailboxd_java_home eq "") {
      my $tomcat_java_home = main::getLocalConfig("tomcat_java_home");
      main::setLocalConfig("mailboxd_java_home", "$tomcat_java_home");
      main::deleteLocalConfig("tomcat_java_home");
    }

    my $zimlet_directory = "${zmail_home}/mailboxd/webapps/service/zimlet";
    main::setLocalConfig("zimlet_directory", "$zimlet_directory");

    

    # convert tomcat keystore to jetty keystore
    if (!-f "${mailboxd_keystore}" && -f "/opt/zmail/tomcat/conf/keystore") { 
      Migrate::log("Migrating tomcat keystore to ${mailboxd_keystore}");
      my $keystore_pass = main::getLocalConfig("tomcat_keystore_password");
      if ($keystore_pass ne "") {
        main::setLocalConfig("mailboxd_keystore_password", "$keystore_pass");
        main::deleteLocalConfig("tomcat_keystore_password");
      } else {
        $keystore_pass = main::getLocalConfig("mailboxd_keystore_password");
      }
      main::runAsZmail("mkdir -p `dirname ${mailboxd_keystore}`; cp -f /opt/zmail/tomcat/conf/keystore ${mailboxd_keystore}; /opt/zmail/java/bin/keytool -keystore ${mailboxd_keystore} -keyclone -alias tomcat -dest jetty -storepass ${keystore_pass} -new ${keystore_pass}");
    }

  }

  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mc default zmailFeatureTasksEnabled TRUE");
    # bug add zmailBackupTarget 
    my $zmailBackupTarget = main::getLdapConfigValue("zmailBackupTarget");
    if ($zmailBackupTarget eq "") {
      $zmailBackupTarget = "${zmail_home}/backup";
      Migrate::log("Setting global ldap config zmailBackupTarget=$zmailBackupTarget");
      main::runAsZmail("$ZMPROV mcf zmailBackupTarget $zmailBackupTarget");
    }

    # bug 15452 add zmailSpamSenderHeader and zmailSpamTypeHeader
    my $zmailSpamReportSenderHeader = main::getLdapConfigValue("zmailSpamReportSenderHeader");
    if ($zmailSpamReportSenderHeader eq "") {
      $zmailSpamReportSenderHeader = "X-Zmail-Spam-Report-Sender";
      Migrate::log("Setting global ldap config zmailSpamReportSenderHeader=$zmailSpamReportSenderHeader");
      main::runAsZmail("$ZMPROV mcf zmailSpamReportSenderHeader $zmailSpamReportSenderHeader");
    }
    my $zmailSpamReportTypeHeader = main::getLdapConfigValue("zmailSpamReportTypeHeader");
    if ($zmailSpamReportTypeHeader eq "") {
      $zmailSpamReportTypeHeader = "X-Zmail-Spam-Report-Type";
      Migrate::log("Setting global ldap config zmailSpamReportTypeHeader=$zmailSpamReportTypeHeader");
      main::runAsZmail("$ZMPROV mcf zmailSpamReportTypeHeader $zmailSpamReportTypeHeader");
    }

    my $zmailSpamReportTypeSpam = main::getLdapConfigValue("zmailSpamReportTypeSpam");
    if ($zmailSpamReportTypeSpam eq "") {
      $zmailSpamReportTypeSpam = "spam";
      Migrate::log("Setting global ldap config zmailSpamReportTypeSpam=$zmailSpamReportTypeSpam");
      main::runAsZmail("$ZMPROV mcf zmailSpamReportTypeSpam $zmailSpamReportTypeSpam");
    }

    my $zmailSpamReportTypeHam = main::getLdapConfigValue("zmailSpamReportTypeHam");
    if ($zmailSpamReportTypeHam eq "") {
      $zmailSpamReportTypeHam = "ham";
      Migrate::log("Setting global ldap config zmailSpamReportTypeHam=$zmailSpamReportTypeHam");
      main::runAsZmail("$ZMPROV mcf zmailSpamReportTypeHam $zmailSpamReportTypeHam");
    }

  }
  return 0;
}

sub upgrade500BETA2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_BETA2\n");

  # bug 16425 rewrite default perms on localconfig.xml
  main::setLocalConfig("upgrade_dummy", "1");
  main::deleteLocalConfig("upgrade_dummy");

  if (main::isInstalled("zmail-store")) {
    my $zmail_home = main::getLocalConfig("zmail_home");
    $zmail_home = "/opt/zmail" if ($zmail_home eq "");
    # clean up tomcat localconfig if they are still hanging around
    if (-f "${zmail_home}/mailboxd/etc/jettyrc") {
      main::deleteLocalConfig("tomcat_java_options");
      main::deleteLocalConfig("tomcat_directory");
      main::deleteLocalConfig("tomcat_keystore");
      main::deleteLocalConfig("tomcat_java_heap_memory_percent");
      main::deleteLocalConfig("tomcat_java_home");
      main::deleteLocalConfig("tomcat_pidfile");
    }

  }

  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mcf zmailAdminURL /zmailAdmin");  
    main::runAsZmail("$ZMPROV mc default zmailFeatureBriefcasesEnabled FALSE");
  }

  return 0;
}

sub upgrade500BETA3 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_BETA3\n");

  if (main::isInstalled("zmail-store")) {
    # 17495
    if (startSql()) { return 1; }
    Migrate::log("Executing ${scriptDir}/migrate20070713-NullContactBlobDigest.pl"); 
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20070713-NullContactBlobDigest.pl");
    stopSql();
  }

  if (main::isInstalled("zmail-ldap")) {
    #bug 17794
    main::runAsZmail("$ZMPROV mcf zmailMtaMyDestination localhost");

    #bug 14643
    my @coses = `$su "$ZMPROV gac"`;
    foreach my $cos (@coses) {
      chomp $cos;
      main::runAsZmail("$ZMPROV mc $cos zmailFeatureGroupCalendarEnabled TRUE zmailFeatureMailEnabled TRUE");
    }
    #bug 17320
    Migrate::log("Executing ${scriptDir}/migrate20070809-Signatures.pl"); 
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20070809-Signatures.pl");
  }

  if (main::isInstalled("zmail-mta")) {
    movePostfixQueue("2.2.9","2.4.3.3");
  }

  if (main::isInstalled("zmail-proxy")) {
     if (! (-f "/opt/zmail/conf/nginx.key" ||
        -f "/opt/zmail/conf/nginx.crt" )) {
        if ( -x "/opt/zmail/bin/zmcertinstall") {
          main::runAsZmail("cd /opt/zmail; zmcertinstall proxy ".
          "/opt/zmail/ssl/ssl/server/server.crt ".
          "/opt/zmail/ssl/ssl/server/server.key");
        }
     }
  }

  return 0;
}

sub upgrade500BETA4 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_BETA4\n");
  # migrate amavis data
  migrateAmavisDB("2.5.2");

  if (main::isInstalled("zmail-store")) {
    # 18545
    my $mailboxd_java_options = main::getLocalConfig("mailboxd_java_options");
    $mailboxd_java_options .= " -XX:MaxPermSize=128m"
      unless ($mailboxd_java_options =~ /MaxPermSize/);
    main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
    main::setLocalConfig("mailboxd_java_options", "$mailboxd_java_options");
  }

  # 20456  
  my $tomcat_keystore_password = main::getLocalConfig("tomcat_keystore_password");
  if ($tomcat_keystore_password ne "") {
    main::setLocalConfig("mailboxd_keystore_password", "$tomcat_keystore_password");
    main::deleteLocalConfig("tomcat_keystore_password");
  }

  my $tomcat_truststore_password = main::getLocalConfig("tomcat_truststore_password");
  if ($tomcat_truststore_password ne "") {
    main::setLocalConfig("mailboxd_truststore_password", "$tomcat_truststore_password");
    main::deleteLocalConfig("tomcat_truststore_password");
  }

  if (main::isInstalled("zmail-ldap")) {
    # 19517
    main::runAsZmail("$ZMPROV mcf zmailBackupAutoGroupedInterval 1d zmailBackupAutoGroupedNumGroups 7 zmailBackupAutoGroupedThrottled FALSE zmailBackupMode Standard");

    # 19826
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailQuotaWarnPercent => "90",
               zmailQuotaWarnInterval => "1d",
               zmailQuotaWarnMessage  => 'From: Postmaster <postmaster@\${RECIPIENT_DOMAIN}>\${NEWLINE}To: \${RECIPIENT_NAME} <\${RECIPIENT_ADDRESS}>\${NEWLINE}Subject: Quota warning\${NEWLINE}Date: \${DATE}\${NEWLINE}Content-Type: text/plain\${NEWLINE}\${NEWLINE}Your mailbox size has reached \${MBOX_SIZE_MB}MB, which is over \${WARN_PERCENT}% of your \${QUOTA_MB}MB quota.\${NEWLINE}Please delete some messages to avoid exceeding your quota.\${NEWLINE}');
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        my $cur_value = main::getLdapCOSValue($attr,$cos);
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'")
          if ($cur_value eq "");
      }
    }

    # 20009
    main::runAsZmail("$ZMPROV mcf +zmailAccountExtraObjectClass amavisAccount");
  }

  # migrate certs to work with the new zmail_cert_manager admin ui
  main::runAsRoot("/opt/zmail/bin/zmcertmgr migrate");
    
  return 0;
}

sub upgrade500RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_RC1\n");
  return 0;
}

sub upgrade500RC2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_RC2\n");
  if (main::isInstalled("zmail-store")) {
    main::setLocalConfig("zmail_mailbox_purgeable", "true");
    migrateTomcatLCKey("thread_stack_size", "256k"); 
    # 20111
    main::runAsZmail("$ZMPROV mcf zmailHttpNumThreads 100");
  }
  if (main::isInstalled("zmail-ldap")) {
          $needSlapIndexing = 1;
  }
  return 0;
}

sub upgrade500RC3 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.0_RC3\n");
  if (main::isInstalled("zmail-store")) {
    # 21179
    my $zmail_java_home = main::getLocalConfig("zmail_java_home");
    if ( -f "${zmail_java_home}/lib/security/cacerts") {
      main::setLocalConfig("mailboxd_truststore", "${zmail_java_home}/lib/security/cacerts"); 
    } else {
      main::setLocalConfig("mailboxd_truststore", "${zmail_java_home}/jre/lib/security/cacerts"); 
    }
  }
  # 21707
  if (main::isInstalled("zmail-proxy")) {
      my $query = "\(\|\(zmailMailDeliveryAddress=\${USER}\)\(zmailMailAlias=\${USER}\)\)";
      # We have to use a pipe to write out the Query, otherwise ${USER} gets interpreted
      open(ZMPROV, "|$su 'zmprov -m -l'");
      print ZMPROV "mcf zmailReverseProxyMailHostQuery $query\n";
      close ZMPROV;
  }
  if (main::isInstalled("zmail-ldap") && $platform !~ /MACOSX/ ) {
    my $ldap_master = `$su "zmlocalconfig -s -m nokey ldap_is_master"`;
    chomp($ldap_master);
    if (lc($ldap_master) eq "true") {
      my $ldap_pass = `$su "zmlocalconfig -s -m nokey zmail_ldap_password"`;
      my $ldap_master_url = `$su "zmlocalconfig -s -m nokey ldap_master_url"`;
      my $ldap; 
      chomp($ldap_master_url);
      chomp($ldap_pass);
      unless($ldap = Net::LDAP->new($ldap_master_url)) { 
        main::progress("Unable to contact $ldap_master_url: $!\n"); 
        return 1;
      }
      if ($ldap_master_url !~ /^ldaps/i) {
        my $result = $ldap->start_tls(verify=>'none');
        if ($result->code()) {
          main::progress("Unable to startTLS: $!\n"); 
          return 1;
        }
      }
      my $dn = 'cn=mime,cn=config,cn=zmail';
      my $result = $ldap->bind("uid=zmail,cn=admins,cn=zmail", password => $ldap_pass);
      unless($result->code()) {
        $result = DeleteLdapTree($ldap,$dn);
        main::progress($result->code() ? "Failed to delete $dn: ".$result->error()."\n" : "Deleted $dn\n");
      }
      $result = $ldap->unbind;
    }
  }
  if (main::isInstalled("zmail-mta")) {
    movePostfixQueue("2.4.3.3","2.4.3.3z");
  }
  return 0;
}

sub upgrade500GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);

  main::progress("Updating from 5.0.0_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    startLdap();
    #bug 19466
    Migrate::log("Executing ${scriptDir}/migrate20071204-deleteOldLDAPUsers.pl"); 
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20071204-deleteOldLDAPUsers.pl");

    # 22666
    main::runAsZmail("$ZMPROV mcf -zmailGalLdapAttrMap 'zmailMailDeliveryAddress,zmailMailAlias,mail=email,email2,email3,email4,email5,email6'");

    main::runAsZmail("$ZMPROV mcf +zmailGalLdapAttrMap 'zmailMailDeliveryAddress,zmailMailAlias,mail=email,email2,email3,email4,email5,email6,email7,email8,email9,email10,email11,email12,email13,email14,email15,email16'");

    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmailAccountAutoComplete:(&(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");

    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailAccountAutoComplete:(&(|(displayName=*%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");

    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmailAccounts:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");

    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailAccounts:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");

    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmailResourceAutoComplete:(&(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(objectclass=zmailCalendarResource))'");

    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResourceAutoComplete:(&(|(displayName=*%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(objectclass=zmailCalendarResource))'");

    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmailResources:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(objectclass=zmailCalendarResource))'");

    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResources:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(objectclass=zmailCalendarResource))'");

    my %attrs = (
      zmailDataSourceMinPollingInterval => "1m",
      zmailFeatureCalendarUpsellEnabled => "FALSE",
      zmailFeatureContactsUpsellEnabled => "FALSE",
      zmailFeatureFlaggingEnabled => "TRUE",
      zmailFeatureImapDataSourceEnabled => "TRUE",
      zmailFeatureMailPollingIntervalPreferenceEnabled => "TRUE",
      zmailFeatureMailPriorityEnabled => "TRUE",
      zmailFeatureMailUpsellEnabled => "FALSE",
      zmailFeatureOptionsEnabled => "TRUE",
      zmailFeaturePortalEnabled => "FALSE",
      zmailFeatureShortcutAliasesEnabled => "TRUE",
      zmailFeatureSignaturesEnabled => "TRUE",
      zmailFeatureVoiceEnabled => "FALSE",
      zmailFeatureVoiceUpsellEnabled => "FALSE",
      zmailFeatureZmailAssistantEnabled => "TRUE",
      zmailMailSignatureMaxLength => "1024",
      zmailNotebookMaxRevisions => "0",
      zmailPortalName => "example",
      zmailPrefAutoSaveDraftInterval => "30s",
      zmailPrefCalendarDayHourEnd => "18",
      zmailPrefCalendarDayHourStart => "8",
      zmailPrefClientType => "advanced",
      zmailPrefDeleteInviteOnReply => "TRUE",
      zmailPrefDisplayExternalImages => "FALSE",
      zmailPrefIMAutoLogin => "FALSE",
      zmailPrefIMFlashIcon => "TRUE",
      zmailPrefIMIdleStatus => "away",
      zmailPrefIMIdleTimeout => "10",
      zmailPrefIMInstantNotify => "TRUE",
      zmailPrefIMLogChatsEnabled => "TRUE",
      zmailPrefIMLogChats => "TRUE",
      zmailPrefIMNotifyPresence => "TRUE",
      zmailPrefIMNotifyStatus => "TRUE",
      zmailPrefIMReportIdle => "TRUE",
      zmailPrefIMSoundsEnabled => "TRUE",
      zmailPrefInboxReadLifetime => "0",
      zmailPrefInboxUnreadLifetime => "0",
      zmailPrefJunkLifetime => "0",
      zmailPrefOpenMailInNewWindow => "FALSE",
      zmailPrefSentLifetime => "0",
      zmailPrefShowSelectionCheckbox => "TRUE",
      zmailPrefTrashLifetime => "0",
      zmailPrefVoiceItemsPerPage => "25",
      zmailPrefWarnOnExit => "TRUE",
      zmailSignatureMaxNumEntries => "20",
      zmailSignatureMinNumEntries => "1",
      zmailJunkMessagesIndexingEnabled => "TRUE",
    );
    my @coses = `$su "$ZMPROV gac"`;
    foreach my $cos (@coses) {
      chomp $cos;
      main::progress("Updating attributes for $cos COS...");
      my $attrs = "";
      foreach my $attr (keys %attrs) {
        my $cur_value = main::getLdapCOSValue($attr,$cos);
        $attrs .= "$attr $attrs{$attr} "
          if ($cur_value eq "");
      }
      main::runAsZmail("$ZMPROV mc $cos $attrs")
        unless ($attrs eq "");;
      
      main::progress("done.\n");
    }
      #bug 22746
      my $ldap_pass = `$su "zmlocalconfig -s -m nokey zmail_ldap_password"`;
      my $ldap_master_url = `$su "zmlocalconfig -s -m nokey ldap_master_url"`;
      my $ldap;
      chomp($ldap_master_url);    chomp($ldap_pass);
      unless($ldap = Net::LDAP->new($ldap_master_url)) {      main::progress("Unable to contact $ldap_master_url: $!\n");
        return 1;    }
      if ($ldap_master_url !~ /^ldaps/i) {
        my $result = $ldap->start_tls(verify=>'none');
        if ($result->code()) {
          main::progress("Unable to startTLS: $!\n");
          return 1;
        }
      }
      my $dn = 'cn=config,cn=zmail';
      my $result = $ldap->bind("uid=zmail,cn=admins,cn=zmail", password => $ldap_pass);
      unless($result->code()) {
        $result = $ldap->modify( $dn, delete => { 'zmailMtaCommonBlockedExtension' => 'hta '});
        main::progress($result->code() ? "Failed to delete zmailMtaCommonBlockedExtension:hta ".$result->error()."\n" : "Deleted zmailMtaCommonBlockedExtension: hta \n");
        $result = $ldap->modify( $dn, add => { 'zmailMtaCommonBlockedExtension' => 'hta'});
        main::progress($result->code() ? "Failed to add zmailMtaCommonBlockedExtension:hta ".$result->error()."\n" : "Added zmailMtaCommonBlockedExtension:hta\n");
      }
      $result = $ldap->unbind;
  }

  if (main::isInstalled("zmail-proxy")) {
    main::runAsZmail("$ZMPROV mcf zmailMemcachedBindPort 11211");

    my $zmailReverseProxyMailHostQuery = 
      "\(\|\(zmailMailDeliveryAddress=\${USER}\)\(zmailMailAlias=\${USER}\)\(zmailId=\${USER}\)\)";
    my $zmailReverseProxyDomainNameQuery = 
      "\(\&\(zmailVirtualIPAddress=\${IPADDR}\)\(objectClass=zmailDomain\)\)";
    my $zmailReverseProxyPortQuery = 
      '\(\&\(zmailServiceHostname=\${MAILHOST}\)\(objectClass=zmailServer\)\)';

    # We have to use a pipe to write out the Query, otherwise ${USER} gets interpreted
    open(ZMPROV, "|$su 'zmprov -m -l'");
    print ZMPROV "mcf zmailReverseProxyMailHostQuery $zmailReverseProxyMailHostQuery\n";
    print ZMPROV "mcf zmailReverseProxyPortQuery $zmailReverseProxyPortQuery\n";
    print ZMPROV "mcf zmailReverseProxyDomainNameQuery $zmailReverseProxyDomainNameQuery\n";
    close ZMPROV;

    main::runAsZmail("$ZMPROV mcf zmailReverseProxyMailHostAttribute zmailMailHost");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyPop3PortAttribute zmailPop3BindPort");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyPop3SSLPortAttribute zmailPop3SSLBindPort");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyImapPortAttribute zmailImapBindPort");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyImapSSLPortAttribute zmailImapSSLBindPort");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyDomainNameAttribute zmailDomainName");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyAuthWaitInterval 10s");
  }

  if (main::isInstalled("zmail-store")) {
    main::runAsZmail("$ZMPROV mcf zmailLogToSyslog FALSE");
    main::runAsZmail("$ZMPROV mcf zmailMailDiskStreamingThreshold 1048576");
    main::runAsZmail("$ZMPROV mcf zmailMailPurgeSleepInterval 0");
    main::runAsZmail("$ZMPROV mcf zmailMtaAuthTarget TRUE");
    main::runAsZmail("$ZMPROV mcf zmailPop3SaslGssapiEnabled FALSE");
    main::runAsZmail("$ZMPROV mcf zmailImapSaslGssapiEnabled FALSE");
    main::runAsZmail("$ZMPROV mcf zmailScheduledTaskNumThreads 20");
    main::runAsZmail("$ZMPROV mcf zmailSoapRequestMaxSize 15360000");
    main::runAsZmail("$ZMPROV mcf zmailHttpNumThreads 250");
    main::setLocalConfig("localized_client_msgs_directory", '\${mailboxd_directory}/webapps/zmail/WEB-INF/classes/messages');

    # 22602
    my $mailboxd_java_options = main::getLocalConfig("mailboxd_java_options");
    $mailboxd_java_options .= " -XX:SoftRefLRUPolicyMSPerMB=1"
      unless ($mailboxd_java_options =~ /SoftRefLRUPolicyMSPerMB/);
    main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
    main::setLocalConfig("mailboxd_java_options", "$mailboxd_java_options");
  }

  return 0;
}

sub upgrade501GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.1_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mcf zmailGalLdapPageSize 0");
    my %attrs = (
      zmailPrefCalendarReminderDuration1     => "-PT15",
      zmailPrefCalendarReminderSendEmail     => "FALSE",
      zmailPrefCalendarReminderMobile        => "FALSE",
      zmailPrefCalendarReminderYMessenger    => "FALSE",
      zmailFeatureComposeInNewWindowEnabled  => "TRUE",
      zmailFeatureOpenMailInNewWindowEnabled => "TRUE",
    );
    my @coses = `$su "$ZMPROV gac"`;
    foreach my $cos (@coses) {
      chomp $cos;
      main::progress("Updating attributes for $cos COS...\n");
      my $attrs = "";
      foreach my $attr (keys %attrs) {
        my $cur_value = main::getLdapCOSValue($attr,$cos);
        $attrs .= "$attr $attrs{$attr} "
          if ($cur_value eq "");
      }
      main::runAsZmail("$ZMPROV mc $cos $attrs")
        unless ($attrs eq "");;
    }
  }
  return 0;
}

sub upgrade502GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.2_GA\n");

  if (main::isInstalled("zmail-store")) {
    my $mailboxd_keystore = main::getLocalConfig("mailboxd_keystore");
    if ( -f "${mailboxd_keystore}") {
      my $keystore_pass = main::getLocalConfig("mailboxd_keystore_password");
      chmod 0644, "${mailboxd_keystore}";
      my $rc = main::runAsZmail("/opt/zmail/java/bin/keytool -list -alias tomcat -keystore ${mailboxd_keystore} -storepass ${keystore_pass} > /dev/null 2>&1");
      if ($rc == 0) {
        my $rc = main::runAsZmail("/opt/zmail/java/bin/keytool -list -alias jetty -keystore ${mailboxd_keystore} -storepass ${keystore_pass} > /dev/null 2>&1");
        if ($rc != 0) {
          main::runAsZmail("/opt/zmail/java/bin/keytool -keystore ${mailboxd_keystore} -keyclone -alias tomcat -dest jetty -storepass ${keystore_pass} -new ${keystore_pass}");
        }
        main::runAsZmail("/opt/zmail/java/bin/keytool -delete -alias tomcat -keystore ${mailboxd_keystore} -storepass ${keystore_pass}");
      }
    }
  }
  if (main::isInstalled("zmail-ldap")) {
    #bug 23616
    $needSlapIndexing = 1;
    #bug 18503
    main::runAsZmail("$ZMPROV mcf zmailGalLdapPageSize 1000");
    main::runAsZmail("$ZMPROV mcf zmailGalSyncLdapPageSize 1000");
    #bug 23840
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyLookupTarget FALSE");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailAccountSync:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResourceSync:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*)(zmailMailDeliveryAddress=*%s*)(zmailMailAlias=*%s*))(objectclass=zmailCalendarResource))'");
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailSpamApplyUserFilters => "FALSE");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        my $cur_value = main::getLdapCOSValue($attr,$cos);
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'")
          if ($cur_value eq "");
      }
    }
  }
  if (main::isInstalled("zmail-mta")) {
    movePostfixQueue("2.4.3.3z","2.4.3.4z");
  }
  return 0;
}
sub upgrade503GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.3_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmailAccountAutoComplete:(&(|(displayName=*%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");
    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'zmailResourceAutoComplete:(&(|(displayName=*%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(objectclass=zmailCalendarResource))'");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailAccountAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(|(objectclass=zmailAccount)(objectclass=zmailDistributionList))(!(objectclass=zmailCalendarResource)))'");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'zmailResourceAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*)(zmailMailDeliveryAddress=%s*)(zmailMailAlias=%s*))(objectclass=zmailCalendarResource))'");
          #bug 9469 - Add ZCS Proxy defaults
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyIPLoginLimit 0");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyIPLoginLimitTime 3600");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyUserLoginLimit 0");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyUserLoginLimitTime 3600");
    main::runAsZmail("$ZMPROV mcf zmailMailProxyPort 0");
    main::runAsZmail("$ZMPROV mcf zmailMailSSLProxyPort 0");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyHttpEnabled FALSE");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyMailEnabled TRUE");

    my @coses = `$su "$ZMPROV gac"`;
    foreach my $cos (@coses) {
      chomp $cos;
      main::runAsZmail("$ZMPROV mc $cos zmailBatchedIndexingSize 0");
                  main::runAsZmail("$ZMPROV mc $cos zmailPrefMailDefaultCharset UTF-8");
    }
  }

  #bug 25051  -  Anand says always set, regardless of what is installed.
  my $refer = main::getLocalConfig("zmail_auth_always_send_refer");
  main::runAsZmail("$ZMPROV ms $hn zmailMailReferMode always")
    if (uc($refer) eq "TRUE");

  if (main::isInstalled("zmail-store")) {
    updateMySQLcnf();
    main::runAsZmail("$ZMPROV mcf zmailMailPurgeSleepInterval 1m");
  }

  if (main::isInstalled("zmail-mta")) {
    main::runAsZmail("zmmtactl stop");
    main::runAsZmail("zmantivirusctl stop");
    if ($main::configStatus{"AmavisMigrated"} ne "CONFIGURED") {
        &relocateAmavisDB();
    }
    if($main::configStatus{"PostfixMigrated"} ne "CONFIGURED") {
        &relocatePostfixQueue();
    }
    main::setLocalConfig("postfix_in_flow_delay", "1s");
    main::setLocalConfig("postfix_queue_directory", "/opt/zmail/data/postfix/spool");
  }

  main::setLocalConfig("zmail_class_accessmanager", "org.zmail.cs.account.DomainAccessManager"); 
  return 0;
}

sub upgrade504GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.4_GA\n");
  return 0;
}

sub upgrade505GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.5_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailPrefCalendarReminderDuration1 => "-PT15M",
               zmailFeatureNewAddrBookEnabled => "TRUE",
               zmailPrefFolderTreeOpen => "TRUE",
               zmailPrefZimletTreeOpen => "TRUE",
               zmailPrefTagTreeOpen => "TRUE",
               zmailPrefSearchTreeOpen => "TRUE",
               zmailPrefGalSearchEnabled => "TRUE",
               zmailInterceptSendHeadersOnly => "FALSE",
               zmailInterceptFrom => 'Postmaster <postmaster@\${ACCOUNT_DOMAIN}>',
               zmailInterceptSubject => 'Intercepted message for \${ACCOUNT_ADDRESS}: \${MESSAGE_SUBJECT}',
               zmailInterceptBody => 'Intercepted message for \${ACCOUNT_ADDRESS}.\${NEWLINE}Operation=\${OPERATION}, folder=\${FOLDER_NAME}, folder ID=\${FOLDER_ID}.');
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
    }
    main::runAsZmail("$ZMPROV mcf zmailSSLExcludeCipherSuites SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
    main::runAsZmail("$ZMPROV mcf +zmailSSLExcludeCipherSuites SSL_DHE_DSS_WITH_DES_CBC_SHA");
    main::runAsZmail("$ZMPROV mcf +zmailSSLExcludeCipherSuites SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA");
    main::runAsZmail("$ZMPROV mcf +zmailSSLExcludeCipherSuites SSL_DHE_RSA_WITH_DES_CBC_SHA");
    main::runAsZmail("$ZMPROV mcf +zmailSSLExcludeCipherSuites SSL_RSA_EXPORT_WITH_DES40_CBC_SHA");
    main::runAsZmail("$ZMPROV mcf +zmailSSLExcludeCipherSuites SSL_RSA_EXPORT_WITH_RC4_40_MD5");
    main::runAsZmail("$ZMPROV mcf +zmailSSLExcludeCipherSuites SSL_RSA_WITH_DES_CBC_SHA");
    # 24757
    main::runAsZmail("$ZMPROV mcf zmailReverseProxySSLCiphers '!SSLv2:!MD5:HIGH'");
    # 24153
    main::runAsZmail("$ZMPROV mcf zmailSmtpSendAddMailer TRUE");
    #bug 26602
    my $proxy = main::getLdapConfigValue("zmailMailReferMode");
    main::runAsZmail("$ZMPROV mcf zmailMailReferMode reverse-proxied")
    if (uc($proxy) eq "NEVER");
    #bug 27003
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyImapStartTlsMode only");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyPop3StartTlsMode only");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyImapSaslGssapiEnabled FALSE");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyPop3SaslGssapiEnabled FALSE");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyHttpPortAttribute zmailMailPort");
  }
  #bug 24827,26544
  if (main::isInstalled("zmail-mta")) {
    &updatePostfixLC("2.4.3.4z", "2.4.7.5z");
  }
  #bug 26602
  if (main::isInstalled("zmail-store")) {
     my $proxy = main::getLdapServerValue("zmailMailReferMode");
     main::runAsZmail("$ZMPROV ms $hn zmailMailReferMode reverse-proxied")
     if (uc($proxy) eq "NEVER");
     main::runAsZmail("$ZMPROV ms $hn zmailReverseProxyLookupTarget TRUE");
  }
  return 0;
}
sub upgrade506GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.6_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailPrefZimletTreeOpen => "FALSE",
                  zmailPrefMarkMsgRead => "0");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
      # bug 22010
      my $cur_value = main::getLdapCOSValue("zmailNotebookSanitizeHtml",$cos);
      main::runAsZmail("$ZMPROV mc $cos zmailNotebookSanitizeHtml TRUE")
          if ($cur_value eq "");
    }

    # set global defaults if these were not defined. 27507
    my $lockmethod = main::getLdapConfigValue("zmailMtaAntiSpamLockMethod");
    main::runAsZmail("$ZMPROV mcf zmailMtaAntiSpamLockMethod flock")
      if ($lockmethod eq "");
    
    my $cacheint = main::getLdapConfigValue("zmailFreebusyExchangeCachedInterval");
    main::runAsZmail("$ZMPROV mcf zmailFreebusyExchangeCachedInterval 60d")
      if ($cacheint eq "");

    my $start= main::getLdapConfigValue("zmailFreebusyExchangeCachedIntervalStart");
    main::runAsZmail("$ZMPROV mcf zmailFreebusyExchangeCachedIntervalStart 7d")
      if ($start eq ""); 

    my $lmtp = main::getLdapConfigValue("zmailLmtpServerEnabled");
    main::runAsZmail("$ZMPROV mcf zmailLmtpServerEnabled TRUE")
      if ($lmtp eq "");

    my $dedupe = main::getLdapConfigValue("zmailMessageIdDedupeCacheSize");
    main::runAsZmail("$ZMPROV mcf zmailMessageIdDedupeCacheSize 3000")
      if ($dedupe eq "" || $dedupe eq "1000");

    my $refer = main::getLdapConfigValue("zmailMailReferMode");
    main::runAsZmail("$ZMPROV mcf zmailMailReferMode wronghost")
      if ($refer eq "");

    upgradeLdapConfigValue("zmailClusterType", "none", "");
    upgradeLdapConfigValue("zmailAttachmentsIndexedTextLimit", "1048576", "");
    # commented out #28280
    #upgradeLdapConfigValue("zmailXMPPEnabled", "TRUE", "FALSE");
    upgradeLdapConfigValue("zmailReverseProxySendPop3Xoip", "TRUE", "");
    upgradeLdapConfigValue("zmailReverseProxySendImapId", "TRUE", "");
    upgradeLdapConfigValue("zmailCalendarCalDavDisableScheduling", "FALSE", "");
    upgradeLdapConfigValue("zmailMtaAuthTarget", "FALSE", "TRUE");
    upgradeLdapConfigValue("zmailLmtpPermanentFailureWhenOverQuota", "FALSE", "");

    # bug 27123, upgrade query
    my $query = "\(\|\(zmailMailDeliveryAddress=\${USER}\)\(zmailMailAlias=\${USER}\)\(zmailId=\${USER}\)\)";
    # We have to use a pipe to write out the Query, otherwise ${USER} gets interpreted
    open(ZMPROV, "|$su 'zmprov -m -l'");
    print ZMPROV "mcf zmailReverseProxyMailHostQuery $query\n";
    close ZMPROV;

    #bug 27699, update log level
    my $ldap_log_level = main::getLocalConfig("ldap_log_level");
    main::setLocalConfig("ldap_log_level", "49152")
      if ($ldap_log_level == 32768); 
  }
  #bug 24827,26544
  if (main::isInstalled("zmail-mta")) {
    my $zmail_home = main::getLocalConfig("zmail_home");
    $zmail_home = "/opt/zmail" if ($zmail_home eq "");

    &updatePostfixLC("2.4.3.4z", "2.4.7.5z");
    #bug 27165
    if ( -d "${zmail_home}/data/clamav/db/daily.inc" ) {
     unlink("${zmail_home}/data/clamav/db/daily.inc");
    }
    if ( -d "${zmail_home}/data/clamav/db/main.inc" ) {
     unlink("${zmail_home}/data/clamav/db/main.inc");
    } 
  }
  #bug 27342
  if (!(main::isEnabled("zmail-store"))) {
    main::runAsZmail("$ZMPROV ms $hn zmailMtaAuthTarget FALSE\n");
  } else {
    main::runAsZmail("$ZMPROV ms $hn zmailMtaAuthTarget TRUE\n");
  }

  return 0;
}

sub upgrade507GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.7_GA\n");
  my $zmail_home = main::getLocalConfig("zmail_home");
  $zmail_home = "/opt/zmail" if ($zmail_home eq "");

  # 22913
  main::setLocalConfig("zmail_class_accessmanager", "org.zmail.cs.account.accesscontrol.AclAccessManager");

  if (main::isInstalled("zmail-ldap")) {

    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailPrefIMFlashTitle                    => "TRUE",
                  zmailPrefMailFlashIcon                   => "FALSE",
                  zmailPrefMailFlashTitle                  => "FALSE",
                  zmailPrefMailSoundsEnabled               => "FALSE",
                  zmailPrefAdvancedClientEnforceMinDisplay => "TRUE",
                  zmailPrefCalendarReminderFlashTitle      => "TRUE",
                  zmailPrefCalendarReminderSoundsEnabled   => "TRUE");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
    }

    #24926
    upgradeLdapConfigValue("zmailCalendarRecurrenceMaxInstances", "0", "");
    upgradeLdapConfigValue("zmailCalendarRecurrenceDailyMaxDays", "730", "");
    upgradeLdapConfigValue("zmailCalendarRecurrenceWeeklyMaxWeeks", "520", "");
    upgradeLdapConfigValue("zmailCalendarRecurrenceMonthlyMaxMonths", "360", "");
    upgradeLdapConfigValue("zmailCalendarRecurrenceYearlyMaxYears", "100", "");
    upgradeLdapConfigValue("zmailCalendarRecurrenceOtherFrequencyMaxYears", "1", "");
  }

  if (main::isInstalled("zmail-store")) {
    my $old_mysql_errlogfile="${zmail_home}/db/data/${hn}.err";
    my $mysql_errlogfile="${zmail_home}/log/mysql_error.log";
    rename(${old_mysql_errlogfile}, ${mysql_errlogfile})
      if (-f ${old_mysql_errlogfile});
    # 29092
    upgradeLocalConfigValue("zmail_waitset_nodata_sleep_time", "3000", "3");
    upgradeLocalConfigValue("zmail_waitset_initial_sleep_time", "1000", "1");
  }

  if (main::isInstalled("zmail-logger")) {
    my $old_logger_mysql_errlogfile="${zmail_home}/db/data/${hn}.err";
    my $logger_mysql_errlogfile="${zmail_home}/log/logger_mysql_error.log";
    rename(${old_logger_mysql_errlogfile}, ${logger_mysql_errlogfile})
      if (-f ${old_logger_mysql_errlogfile});
  } 

  #bug 27342
  if (!(main::isEnabled("zmail-store"))) {
    main::runAsZmail("$ZMPROV ms $hn zmailMtaAuthTarget FALSE\n");
  } else {
    main::runAsZmail("$ZMPROV ms $hn zmailMtaAuthTarget TRUE\n");
  }
  return 0;
}

sub upgrade508GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.8_GA\n");
  return 0;
}

sub upgrade509GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.9_GA\n");

  # 29725
  if (main::isInstalled("zmail-store")) {
    updateMySQLcnf();
  }
  if (main::isInstalled("zmail-logger")) {
    updateLoggerMySQLcnf();
  }

  if (main::isInstalled("zmail-ldap")) {
  if($isLdapMaster) {
    upgradeLdapConfigValue("zmailCalendarCalDavDisableFreebusy", "FALSE", "");
    upgradeLdapConfigValue("zmailImapExposeVersionOnBanner", "FALSE", "");
    upgradeLdapConfigValue("zmailLmtpExposeVersionOnBanner", "FALSE", "");
    upgradeLdapConfigValue("zmailPop3ExposeVersionOnBanner", "FALSE", "");
    upgradeLdapConfigValue("zmailLmtpPermanentFailureWhenOverQuota", "FALSE", "");
    upgradeLdapConfigValue("zmailReverseProxyAdminPortAttribute", "zmailAdminPort", "");
    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'ad:(&(|(cn=*%s*)(sn=*%s*)(gn=*%s*)(mail=*%s*))(!(msExchHideFromAddressLists=TRUE))(mailnickname=*)(|(&(objectCategory=person)(objectClass=user)(!(homeMDB=*))(!(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=user)(|(homeMDB=*)(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=contact))(objectCategory=group)(objectCategory=publicFolder)(objectCategory=msExchDynamicDistributionList)))'");
    main::runAsZmail("$ZMPROV mcf -zmailGalLdapFilterDef 'adAutoComplete:(&(|(cn=%s*)(sn=%s*)(gn=%s*)(mail=%s*))(!(msExchHideFromAddressLists=TRUE))(mailnickname=*)(|(&(objectCategory=person)(objectClass=user)(!(homeMDB=*))(!(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=user)(|(homeMDB=*)(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=contact))(objectCategory=group)(objectCategory=publicFolder)(objectCategory=msExchDynamicDistributionList)))'");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'ad:(&(|(displayName=*%s*)(cn=*%s*)(sn=*%s*)(givenName=*%s*)(mail=*%s*))(!(msExchHideFromAddressLists=TRUE))(mailnickname=*)(|(&(objectCategory=person)(objectClass=user)(!(homeMDB=*))(!(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=user)(|(homeMDB=*)(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=contact))(objectCategory=group)(objectCategory=publicFolder)(objectCategory=msExchDynamicDistributionList)))
'");
    main::runAsZmail("$ZMPROV mcf +zmailGalLdapFilterDef 'adAutoComplete:(&(|(displayName=%s*)(cn=%s*)(sn=%s*)(givenName=%s*)(mail=%s*))(!(msExchHideFromAddressLists=TRUE))(mailnickname=*)(|(&(objectCategory=person)(objectClass=user)(!(homeMDB=*))(!(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=user)(|(homeMDB=*)(msExchHomeServerName=*)))(&(objectCategory=person)(objectClass=contact))(objectCategory=group)(objectCategory=publicFolder)(objectCategory=msExchDynamicDistributionList)))
'");
    # bug 29978
    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 29978 -v");

    # bug 29777
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailPrefCalendarAllowCancelEmailToSelf                    => "FALSE");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
      main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
    }
  }
  }

  if (main::isInstalled("zmail-mta")) {
    my @maps = ("postfix_sender_canonical_maps", "postfix_transport_maps",
                "postfix_virtual_alias_domains", "postfix_virtual_alias_maps",
                "postfix_virtual_mailbox_domains", "postfix_virtual_mailbox_maps");
    foreach my $map (@maps) {
      my $mapValue=main::getLocalConfig($map);
      if ($mapValue =~ /^ldap:/) {
        $mapValue = "proxy:".$mapValue;
        main::setLocalConfig($map, $mapValue);
      } 
    }
  }
  return 0;
}

sub upgrade5010GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.10_GA\n");
    #bug 31177
    upgradeLocalConfigValue("zmmtaconfig_enable_config_restarts", "true", "");

  if (main::isInstalled("zmail-store")) {
    updateMySQLcnf();
    my $conns=main::getLocalConfig("zmail_mysql_connector_maxActive");
    upgradeLocalConfigValue("zmail_mysql_connector_maxActive", "100", "$conns")
      if ($conns < 100);
  }

  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyIpThrottleMsg 'Login rejected from this IP'");
    main::runAsZmail("$ZMPROV mcf zmailReverseProxyUserThrottleMsg 'Login rejected for this user'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyPop3EnabledCapability 'EXPIRE 31 USER'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyPop3EnabledCapability TOP");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyPop3EnabledCapability UIDL");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyPop3EnabledCapability USER");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyPop3EnabledCapability XOIP");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability ACL");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability BINARY");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability CATENATE");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability CHILDREN");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability CONDSTORE");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability ENABLE");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability ESEARCH");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability ID");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability IDLE");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability IMAP4rev1");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability 'LIST-EXTENDED'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability 'LITERAL+'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability MULTIAPPEND");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability NAMESPACE");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability QRESYNC");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability QUOTA");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability 'RIGHTS=ektx'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability 'SASL-IR'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability SEARCHRES");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability UIDPLUS");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability UNSELECT");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability WITHIN");
    upgradeLdapConfigValue("zmailReverseProxyImapExposeVersionOnBanner", "FALSE", "");
    upgradeLdapConfigValue("zmailReverseProxyPop3ExposeVersionOnBanner", "FALSE", "");
    upgradeLdapConfigValue("zmailSoapExposeVersion", "FALSE", "");
    upgradeLdapConfigValue("zmailReverseProxyDefaultRealm", "", "EXAMPLE.COM");
    upgradeLdapConfigValue("zmailReverseProxyWorkerConnections", "10240", "");
    upgradeLdapConfigValue("zmailReverseProxyLogLevel", "info", "");
    upgradeLdapConfigValue("zmailReverseProxyCacheFetchTimeout", "3s", "");
    upgradeLdapConfigValue("zmailReverseProxyWorkerProcesses", "4", "");
    upgradeLdapConfigValue("zmailReverseProxyInactivityTimeout", "1h", "");
    upgradeLdapConfigValue("zmailReverseProxyRouteLookupTimeout", "15s", "");
    upgradeLdapConfigValue("zmailReverseProxyCacheEntryTTL", "1h", "");
    upgradeLdapConfigValue("zmailReverseProxyCacheReconnectInterval", "1m", "");
    upgradeLdapConfigValue("zmailReverseProxyPassErrors", "TRUE", "");
    upgradeLdapConfigValue("zmailReverseProxyImapSaslPlainEnabled", "TRUE", "");
    upgradeLdapConfigValue("zmailReverseProxyPop3SaslPlainEnabled", "TRUE", "");
    upgradeLdapConfigValue("zmailSmtpSendAddAuthenticatedUser", "FALSE", "");
    upgradeLdapConfigValue("zmailAdminConsoleCatchAllAddressEnabled", "FALSE", "");
    upgradeLdapConfigValue("zmailAdminConsoleDNSCheckEnabled", "FALSE", "");
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailFeatureMailForwardingInFiltersEnabled => "TRUE",
                  zmailContactMaxNumEntries => "10000",
                  zmailPrefIMHideOfflineBuddies => "FALSE",
                  zmailFeatureGalSyncEnabled => "TRUE",
                  zmailPrefIMHideBlockedBuddies => "FALSE",
                  zmailCalendarMaxRevisions => "1" );
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
    }
  }
  return 0;
}

sub upgrade5011GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.11_GA\n");
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    upgradeLdapConfigValue("zmailAdminConsoleSkinEnabled", "FALSE", "");
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailFreebusyLocalMailboxNotActive => "FALSE");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
    }
  }
  return 0;
}

sub upgrade5012GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.12_GA\n");
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    # 31353
    upgradeLdapConfigValue("zmailAdminConsoleLDAPAuthEnabled", "FALSE", "");
    # 31557
    upgradeLdapConfigValue("zmailReverseProxyRouteLookupTimeoutCache", "60s", "");
    # 30787
    upgradeLdapConfigValue("zmailCalendarCalDavUseDistinctAppointmentAndToDoCollection", "FALSE", "");

    my $ldap_pass = `$su "zmlocalconfig -s -m nokey zmail_ldap_password"`;
    my $ldap_master_url = `$su "zmlocalconfig -s -m nokey ldap_master_url"`;
    my $start_tls_supported = `$su "zmlocalconfig -s -m nokey ldap_starttls_supported"`;
    my $ldap; 
    chomp($ldap_master_url);
    chomp($ldap_pass);
    chomp($start_tls_supported);
    unless($ldap = Net::LDAP->new($ldap_master_url)) {
      main::progress("Unable to contact $ldap_master_url: $!\n");
      return 1;
    }
    if ($start_tls_supported) {
      my $result = $ldap->start_tls(verify=>'none');
      if ($result->code()) {
        main::progress("Unable to startTLS: $!\n");
        return 1;
      }
    }
    my $result = $ldap->bind("uid=zmail,cn=admins,cn=zmail", password => $ldap_pass);
    unless($result->code()) {
        $result = $ldap->modify( "uid=zmail,cn=admins,cn=zmail", add => { 'zmailIsSystemResource' => 'TRUE'});
        $result = $ldap->modify( "uid=zmreplica,cn=admins,cn=zmail", add => { 'zmailIsSystemResource' => 'TRUE'});
        $result = $ldap->modify( "uid=zmreplica,cn=admins,cn=zmail", delete => [ 'zmailIsAdminAccount' ]);
        $result = $ldap->modify( "uid=zmnginx,cn=appaccts,cn=zmail", add => { 'zmailIsSystemResource' => 'TRUE'});
        $result = $ldap->modify( "uid=zmpostfix,cn=appaccts,cn=zmail", add => { 'zmailIsSystemResource' => 'TRUE'});
        $result = $ldap->modify( "uid=zmpostfix,cn=appaccts,cn=zmail", delete => [ 'zmailIsAdminAccount' ]);
        $result = $ldap->modify( "uid=zmamavis,cn=appaccts,cn=zmail", add => { 'zmailIsSystemResource' => 'TRUE'});
        $result = $ldap->modify( "uid=zmamavis,cn=appaccts,cn=zmail", delete => [ 'zmailIsAdminAccount' ]);
    }
    $result = $ldap->unbind;
  }
  return 0;
}

sub upgrade5013GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.13_GA\n");
  return 0;
}

sub upgrade5014GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.14_GA\n");
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    # 35448
    upgradeLdapConfigValue("zmailCalendarCalDavClearTextPasswordEnabled", "TRUE", "");
    # 35259
    my @calres = `$su "$ZMPROV gacr"`;
    my %attrs = ( zmailCalResMaxNumConflictsAllowed => "0",
                  zmailCalResMaxPercentConflictsAllowed => "0");
    foreach my $resource (@calres) {
      chomp $resource;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mcr $resource $attr \'$attrs{$attr}\'");
      }
    }
    # 34899
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailCalendarCalDavSharedFolderCacheDuration => "1m");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
      }
    }
  }
  return 0;
}

sub upgrade5015GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.15_GA\n");
  return 0;
}

sub upgrade5016GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.16_GA\n");
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailBatchedIndexingSize => "20");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        if ($attr = "zmailBatchedIndexingSize") {
          my $value = main::getLdapCOSValue($attr,$cos);
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'")
            if ($value eq "0" || $value eq "");
        } else {
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
        }
      }
    }
  }
  return 0;
}

sub upgrade5017GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.17_GA\n");
  return 0;
}

sub upgrade5018GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.18_GA\n");
  return 0;
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    # 37683
    upgradeLdapConfigValue("zmailMemcachedClientExpirySeconds", "86400", "");
    upgradeLdapConfigValue("zmailMemcachedClientBinaryProtocolEnabled ", "FALSE", "");
    upgradeLdapConfigValue("zmailMemcachedClientTimeoutMillis", "10000", "");
    upgradeLdapConfigValue("zmailMemcachedClientHashAlgorithm", "KETAMA_HASH", "");
    # 37817
    upgradeLdapConfigValue("zmailRedoLogRolloverMinFileAge", "60", "");
    upgradeLdapConfigValue("zmailRedoLogRolloverHardMaxFileSizeKB", "4194304", "");

    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailMailPurgeUseChangeDateForTrash => "TRUE");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'")
      }
    }
  }
}

sub upgrade5019GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.19_GA\n");
  return 0;
}

sub upgrade5020GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.20_GA\n");
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailBatchedIndexingSize => "20");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        if ($attr = "zmailBatchedIndexingSize") {
          my $value = main::getLdapCOSValue($attr,$cos);
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'")
            if ($value eq "0" || $value eq "");
        } else {
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
        }
      }
    }
  }
  return 0;
}

sub upgrade5021GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.21_GA\n");
  return 0;
}

sub upgrade5022GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.22_GA\n");
  return 0;
}

sub upgrade5023GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.23_GA\n");
  return 0;
}

sub upgrade5024GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.24_GA\n");
  return 0;
}

sub upgrade5025GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.25_GA\n");
  return 0;
}

sub upgrade5026GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.26_GA\n");
  return 0;
}

sub upgrade5027GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 5.0.27_GA\n");
  return 0;
}

sub upgrade600BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.0_BETA1\n");

  # Convert access manager to new ACL based manager
  main::setLocalConfig("zmail_class_accessmanager", "org.zmail.cs.account.accesscontrol.ACLAccessManager");

  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    # 34679 replaced by 18277 in 6.0.1
    #upgradeAllGlobalAdminAccounts();

    main::configInitDomainAdminGroups() if (main::isNetwork());

    main::progress("Migrating all domain admins to ACL based access manager...");
    my $rc = main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 18277 -v");
    main::progress(($rc == 0) ? "done.\n" : "failed.\n");

    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 33814 -v");
    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 32557 -v");
    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 31694 -v");
    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 14531 -v");

    # this touches all accounts so only run it by default on small sites.
    # releasenotes to indicate larger deployments run it by hand after upgrade.
    #main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 31284 -v");
      #if ($main::countUsers < 500);

 
    upgradeLdapConfigValue("zmailRedoLogRolloverFileSizeKB", "1048576", "102400");

    #33405
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability ESORT");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability 'I18NLEVEL=1'");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability SORT");
    main::runAsZmail("$ZMPROV mcf +zmailReverseProxyImapEnabledCapability 'THREAD=ORDEREDSUBJECT'");
    # 33359
    my $rc;
    my $zmailDefaultDomainName = main::getLdapConfigValue("zmailDefaultDomainName");
    my @mbs = main::getAllServers("mailbox");
    unless ($mbs[0] eq "" || $zmailDefaultDomainName eq "") {
      main::progress("Checking for default IM conference room...");
      $rc = main::runAsZmail("$ZMPROV gxc conference.$zmailDefaultDomainName");
      main::progress (($rc != 0) ? "not present.\n" : "already initialized.\n");
      if ($rc != 0) {
        main::progress("Initializing default IM conference room...");
        $rc = main::runAsZmail("$ZMPROV cxc conference ${zmailDefaultDomainName} $mbs[0] org.jivesoftware.wildfire.muc.spi.MultiUserChatServerImpl conference text");
        main::progress (($rc == 0) ? "done.\n" : "failed.\n");
      }
    }

    my $ldap_pass = main::getLocalConfig("zmail_ldap_password");
    my $ldap_master_url = main::getLocalConfig("ldap_master_url");
    my $start_tls_supported = main::getLocalConfig("ldap_starttls_supported");
    my $ldap; 
    chomp($ldap_master_url);
    chomp($ldap_pass);
    chomp($start_tls_supported);
    unless($ldap = Net::LDAP->new($ldap_master_url)) {
      main::progress("Unable to contact $ldap_master_url: $!\n");
      return 1;
    }
    if ($ldap_master_url !~ /^ldaps/i) {
      if ($start_tls_supported) {
        my $result = $ldap->start_tls(verify=>'none');
        if ($result->code()) {
          main::progress("Unable to startTLS: $!\n");
          return 1;
        }
      }
    }
    my $result = $ldap->bind("uid=zmail,cn=admins,cn=zmail", password => $ldap_pass);
    unless($result->code()) {
      my $dn = 'cn=mime,cn=config,cn=zmail';
      $result = DeleteLdapTree($ldap,$dn);
      main::progress($result->code() ? "Failed to delete $dn: ".$result->error()."\n" : "Deleted $dn\n");
    }
    $result = $ldap->unbind;
  }

  if (main::isInstalled("zmail-store")) {
    #35284
    my $mailboxd_java_options=main::getLocalConfig("mailboxd_java_options");
    my $new_mailboxd_options;
    foreach my $option (split(/\s+/, $mailboxd_java_options)) {
      $new_mailboxd_options.=" $option" if ($option !~ /^-Xss/); 
    }
    $new_mailboxd_options =~ s/^\s+//;
    main::setLocalConfig("mailboxd_java_options", $new_mailboxd_options)
      if ($new_mailboxd_options ne "");
  }

  if (main::isInstalled("zmail-store") && main::isInstalled("zmail-convertd")) {
    #28851
    main::setLdapServerConfig($hn, 'zmailConvertdURL', 'http://localhost:7047/convert\n');
  }

  if (main::isInstalled("zmail-logger")) {
    # clean up old logger database and work directory
    my $logger_data_directory = main::getLocalConfig("logger_data_directory") || "/opt/zmail/logger";
    my $stats_img_directory = main::getLocalConfig("stats_img_directory") || "/opt/zmail/logger/db/work";
    my $logger_mysql_data_directory = main::getLocalConfig("logger_mysql_data_directory") || "${logger_data_directory}/db/data";
    my $logger_mysql_mycnf = main::getLocalConfig("logger_mysql_mycnf") || "/opt/zmail/conf/my.logger.cnf";
    my $logger_mysql_errlogfile = main::getLocalConfig("logger_mysql_errlogfile") || "/opt/zmail/log/my.logger.cnf";
    my $logger_mysql_pidfile = main::getLocalConfig("logger_mysql_pidfile") || "${logger_data_directory}/db/mysql.pid";

    system("rm -rf ${logger_mysql_data_directory} 2> /dev/null")
      if ( -d "${logger_mysql_data_directory}/");
    system("rm -rf ${stats_img_directory} 2> /dev/null")
      if ( -d "${stats_img_directory}");
    unlink("$logger_data_directory/mysql") if (-l "$logger_data_directory/mysql");
    unlink($logger_mysql_mycnf) if (-f $logger_mysql_mycnf);
    unlink($logger_mysql_errlogfile) if (-f $logger_mysql_errlogfile);
    unlink($logger_mysql_pidfile) if (-f $logger_mysql_pidfile);

    # clean up localconfig  
    main::deleteLocalConfig("logger_mysql_bind_address");
    main::deleteLocalConfig("logger_mysql_data_directory");
    main::deleteLocalConfig("logger_mysql_directory");
    main::deleteLocalConfig("logger_mysql_errlogfile");
    main::deleteLocalConfig("logger_mysql_mycnf");
    main::deleteLocalConfig("logger_mysql_pidfile");
    main::deleteLocalConfig("logger_mysql_port");
    main::deleteLocalConfig("logger_mysql_socket");
    main::deleteLocalConfig("mysql_logger_root_password");
    main::deleteLocalConfig("stats_img_directory");
    main::deleteLocalConfig("zmail_logger_mysql_password");
  }

  #33648
  main::deleteLocalConfig("ldap_require_tls");
  main::deleteLocalConfig("calendar_canonical_tzid");
  main::deleteLocalConfig("debug_update_config_use_old_scheme");

  if (main::isInstalled("zmail-ldap")) {
    my $ldap_loglevel=main::getLocalConfig("ldap_log_level");
    main::setLocalConfig("ldap_common_loglevel", $ldap_loglevel)
      if ($ldap_loglevel ne "");
    main::runAsZmail("/opt/zmail/libexec/zmldapanon -e");
  }
  main::deleteLocalConfig("ldap_log_level");
  upgradeLocalConfigValue("javamail_imap_timeout", "20", "60");
  upgradeLocalConfigValue("javamail_pop3_timeout", "20", "60");
  upgradeLocalConfigValue("mysql_table_cache", "1200", "500");
   
  return 0;
}

sub upgrade600BETA2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.0_BETA2\n");

  my $zmail_tmp_directory=main::getLocalConfig("zmail_tmp_directory");
  if ($zmail_tmp_directory eq "/tmp/zmail") {
    my $zmail_home = main::getLocalConfig("zmail_home");
    main::setLocalConfig("zmail_tmp_directory", "$zmail_home/data/tmp");
  }

  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    # an unfortunate affair because the default didn't get 
    # changed properly in 5.0.16 so we have to redo it here.
    my @coses = `$su "$ZMPROV gac"`;
    my %attrs = ( zmailBatchedIndexingSize => "20");
    foreach my $cos (@coses) {
      chomp $cos;
      foreach my $attr (keys %attrs) {
        if ($attr = "zmailBatchedIndexingSize") {
          my $value = main::getLdapCOSValue($attr,$cos);
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'")
            if ($value eq "0" || $value eq "");
        } else {
          main::runAsZmail("$ZMPROV mc $cos $attr \'$attrs{$attr}\'");
        }
      }
    }

    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 32719 -v");
  }
  if (main::isInstalled("zmail-store")) {
    # 36598
    my $mailboxd_java_options = main::getLocalConfig("mailboxd_java_options");
    $mailboxd_java_options .= " -verbose:gc"
      unless ($mailboxd_java_options =~ /verbose:gc/);
    $mailboxd_java_options .= " -XX:+PrintGCDetails"
      unless ($mailboxd_java_options =~ /PrintGCDetails/);
    $mailboxd_java_options .= " -XX:+PrintGCTimeStamps"
      unless ($mailboxd_java_options =~ /PrintGCTimeStamps/);
    $mailboxd_java_options .= " -XX:+PrintGCApplicationStoppedTime"
      unless ($mailboxd_java_options =~ /PrintGCApplicationStoppedTime/);
    main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
    main::setLocalConfig("mailboxd_java_options", "$mailboxd_java_options");
    #26022
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-tmpdir-fixup --section=mysqld --key=tmpdir --set --value=/opt/zmail/data/tmp /opt/zmail/conf/my.cnf");

    # 32897
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-table_cache-fixup --section=mysqld --key=table_cache --setmin --value=1200 /opt/zmail/conf/my.cnf");
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-innodb_open_files-fixup --section=mysqld --key=innodb_open_files --setmin --value=2710 /opt/zmail/conf/my.cnf");
    # 32413
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-innodb_flush_log-fixup --section=mysqld --key=innodb_flush_log_at_trx_commit --set --value=0 /opt/zmail/conf/my.cnf");
  }
  if (main::isInstalled("zmail-convertd")) {
    my $convertd_version=main::getLocalConfig("convertd_version");
    if ($convertd_version eq "1" && !(main::isEnabled("zmail-convertd"))) {
      main::setLdapServerConfig($hn, '+zmailServiceEnabled', 'convertd');
    }
  }
  main::deleteLocalConfig("convertd_version");
  &cleanPostfixLC;
  main::deleteLocalConfig("postfix_version");
  main::deleteLocalConfig("mysql_memory_percent");
  main::deleteLocalConfig("mysql_innodb_log_buffer_size");
  main::deleteLocalConfig("mysql_innodb_log_file_size");
  main::deleteLocalConfig("mysql_sort_buffer_size");
  main::deleteLocalConfig("mysql_read_buffer_size");
  main::deleteLocalConfig("mysql_table_cache");

  upgradeLocalConfigValue("zmail_http_originating_ip_header", "X-Forwarded-For", "X-Originating-IP"); #31633

  return 0;
}

sub upgrade600RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.0_RC1\n");

  
  main::runAsZmail("zmjava org.zmail.common.localconfig.LocalConfigUpgrade --bug 37842 --bug 37844 --bug 37802 --tag .pre.${targetVersion}");

  if (main::isInstalled("zmail-store")) {
    # 35835
    my $zmail_home=main::getLocalConfig("zmail_home");
    system("mv ${zmail_home}/store/calcache ${zmail_home}/data/tmp 2> /dev/null")
      if ( -d "${zmail_home}/store/calcache");

    # 39085
    system("mv ${zmail_home}/jetty/webapps/service/zimlet/* ${zmail_home}/zimlets-deployed/")
      if ( -d "${zmail_home}/jetty/webapps/service/zimlet");
    main::setLocalConfig("zimlet_directory", "${zmail_home}/zimlets-deployed");
    main::setLocalConfig("zimlet_properties_directory", "${zmail_home}/zimlets-properties");
  }
  main::deleteLocalConfig("soap_max_in_memory_buffer_size");

  return 0;
}

sub upgrade600RC2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.0_RC2\n");
  return 0;
}

sub upgrade600GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.0_GA\n");
  if (main::isInstalled("zmail-mta")) {
    my @mtalist = main::getAllServers("mta");
    my $servername = main::getLocalConfig("zmail_server_hostname");
    main::setLocalConfig("zmtrainsa_cleanup_host", "true")
      if ("$servername" eq "$mtalist[0]");
  }
  return 0;
}

sub upgrade601GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.1_GA\n");
  return 0;
}

sub upgrade602GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.2_GA\n");
  if (main::isInstalled("zmail-ldap") && $isLdapMaster) {
    main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 41000 -v");
    main::setLdapGlobalConfig("zmailHttpDebugHandlerEnabled", "TRUE");
  }
  if (main::isInstalled("zmail-store")) {
    # 40536
    my $zmail_home=main::getLocalConfig("zmail_home");
    system("rm -rf ${zmail_home}/zimlets-deployed/zimlet")
      if ( -d "${zmail_home}/zimlets-deployed/zimlet");
    system("rm -rf ${zmail_home}/mailboxd/webapps/service/zimlet")
      if ( -d "${zmail_home}/mailboxd/webapps/service/zimlet");
    # 40839
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-pid-file-fixup --section=mysqld_safe --key=pid-file --unset /opt/zmail/conf/my.cnf");
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.post-${targetVersion}-pid-file-fixup --section=mysqld_safe --key=pid-file --set --value=/opt/zmail/db/mysql.pid /opt/zmail/conf/my.cnf");
  }
  return 0;
}

sub upgrade603GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.3_GA\n");
  return 0;
}

sub upgrade604GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.4_GA\n");
  return 0;
}

sub upgrade605GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.5_GA\n");
  &cleanPostfixLC;
  if (main::isInstalled("zmail-store")) {
    my $servername = main::getLocalConfig("zmail_server_hostname");
    my $serverId = main::getLdapServerValue("zmailId", $servername);
    upgradeLdapConfigValue("zmailVersionCheckServer", $serverId, "");
  }

  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 42877 -v");
      main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 43147 -v");
    }
    # 43040, must be done on all LDAP servers
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    my $ldap;
    chomp($ldap_pass);
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    unless($result->code()) {
      $result = $ldap->modify( "cn=config", add => { 'olcWriteTimeout' => '0'});
    }
    # 43701, replica's only
    if (!$isLdapMaster) {
      $result = $ldap->search(
        base => "olcDatabase={2}mdb,cn=config",
        filter => "(olcSyncrepl=*)",
        attrs => ['olcSyncrepl']
      );
      my $entry=$result->entry(0);
      my $attr = $entry->get_value("olcSyncrepl");
      if ($attr !~ /tls_cacertdir/) {
        $attr =  $attr . " tls_cacertdir=/opt/zmail/conf/ca";
      }

      $result = $ldap->modify(
        $entry->dn,
        replace => {
          olcSyncrepl => "$attr",
        }
      );
    }
    $result = $ldap->unbind;
  }
  return 0;
}

sub upgrade606GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.6_GA\n");
  
  # 42877 - Fix ACLs for new attrs for local GAL access
  if (main::isInstalled("zmail-ldap")) {
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcAccess'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcAccess");
    my $aclNumber=-1;
    my $attrMod="";

    foreach my $attr (@attrvals) {
      if ($attr =~ /telephoneNumber/) {
        if ($attr !~ /homePhone/) {
          ($aclNumber) = $attr =~ /^\{(\d+)\}*/;
          $attrMod=$attr;
        }
      }
    }

    if ($aclNumber != -1 && $attrMod ne "") {
      $attrMod =~ s/uid/uid,homePhone,pager,mobile/;
      $result = $ldap->modify(
          $dn,
          delete => {olcAccess => "{$aclNumber}"},
      );
      $result = $ldap->modify(
          $dn,
          add =>{olcAccess=>"$attrMod"},
      );
    }
    $ldap->unbind;
  }
  return 0;
}

sub upgrade607GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.7_GA\n");

  if (main::isInstalled("zmail-core")) {
    #46801
    my ($micro) = $startMicro =~ /(\d+)_.*/;
    if ($startMajor < 6 || ($startMajor == 6 && $micro < 5) ) {
      main::setLocalConfig("migrate_user_zimlet_prefs", "true");
    } else {
      main::setLocalConfig("migrate_user_zimlet_prefs", "false");
    }
    # 46840
    upgradeLocalConfigValue("ldap_cache_group_maxsize", "2000", "200");
  }

  if (main::isInstalled("zmail-mta")) {
    my $zmail_home = main::getLocalConfig("zmail_home");
    $zmail_home = "/opt/zmail" if ($zmail_home eq "");
    #bug 27165
    if ( -f "${zmail_home}/data/clamav/db/daily.cvd" ) {
     unlink("${zmail_home}/data/clamav/db/daily.cvd");
    }
    if ( -f "${zmail_home}/data/clamav/db/main.cvd" ) {
     unlink("${zmail_home}/data/clamav/db/main.cvd");
    } 
    # bug 47066
    main::setLocalConfig("postfix_always_add_missing_headers", "yes");
  }
  if (main::isInstalled("zmail-ldap")) {
    if (!$isLdapMaster) {
      # 46508 upgrade step for keepalive setting
      my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
      my $ldap;
      chomp($ldap_pass);
      unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
         main::progress("Unable to contact to ldapi: $!\n");
      }
      my $result = $ldap->bind("cn=config", password => $ldap_pass);
      $result = $ldap->search(
        base => "olcDatabase={2}mdb,cn=config",
        filter => "(olcSyncrepl=*)",
        attrs => ['olcSyncrepl']
      );
      my $entry=$result->entry(0);
      my $attr = $entry->get_value("olcSyncrepl");
      if ($attr !~ /keepalive=/) {
        $attr =  $attr . " keepalive=240:10:30";
      }

      $result = $ldap->modify(
        $entry->dn,
        replace => {
          olcSyncrepl => "$attr",
        }
      );
      $result = $ldap->unbind;
    } else {
      runLdapAttributeUpgrade("46297");
    }
  }
  if (main::isInstalled("zmail-store")) {
    my $mailboxd_java_options = main::getLocalConfig("mailboxd_java_options");
    $mailboxd_java_options .= " -Dsun.net.inetaddr.ttl=\${networkaddress_cache_ttl}"
      unless ($mailboxd_java_options =~ /sun.net.inetaddr.ttl/);
    main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
    main::setLocalConfig("mailboxd_java_options", "$mailboxd_java_options");
    #45891
    my $imap_max_request_size = main::getLocalConfig("imap_max_request_size");
    if ($imap_max_request_size ne "" and $imap_max_request_size ne "10240") {
      main::runAsZmail("$ZMPROV ms $hn zmailImapMaxRequestSize $imap_max_request_size");
    }
  }
  return 0;
}

sub upgrade608GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.8_GA\n");
  main::deleteLocalConfig("zimlet_properties_directory"); 
  if ($isLdapMaster) {
    runLdapAttributeUpgrade("46883");
    runLdapAttributeUpgrade("46961");
  }
  return 0;
}

sub upgrade609GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.9_GA\n");
  return 0;
}

sub upgrade6010GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.10_GA\n");
  return 0;
}

sub upgrade6011GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.11_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    if($isLdapMaster) {
      runLdapAttributeUpgrade("50458");
    }
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcDbIndex'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcDbIndex");
    my $needModify=1;

    foreach my $attr (@attrvals) {
      if ($attr =~ /zmailMailHost/) {
        $needModify=0;
      }
    }

    if ($needModify) {
      $result = $ldap->modify(
          $dn,
          add =>{olcDbIndex=>"zmailMailHost eq"},
      );
    }
    $ldap->unbind;
    if ($needModify) {
      &indexLdapAttribute("zmailMailHost");
    }
  }
  if (main::isInstalled("zmail-store")) {
    my $mailboxd_java_options=main::getLocalConfig("mailboxd_java_options");
    if ($mailboxd_java_options =~ /-Dsun.net.inetaddr.ttl=$/) {
      my $new_mailboxd_options;
      foreach my $option (split(/\s+/, $mailboxd_java_options)) {
        $new_mailboxd_options.=" $option" if ($option !~ /^-Dsun.net.inetaddr.ttl=/); 
      }
      $new_mailboxd_options =~ s/^\s+//;
      main::setLocalConfig("mailboxd_java_options", $new_mailboxd_options)
        if ($new_mailboxd_options ne "");
    }
    main::setLocalConfig("calendar_outlook_compatible_allday_events", "false");

    #56318
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-allowed-packet --section=mysqld --key=max_allowed_packet --set --value=16777216 /opt/zmail/conf/my.cnf");
  }
  return 0;
}

sub upgrade6012GA {                                                                                                                                                                                                   
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.12_GA\n");                                                                                                                                                                        
  return 0;
}                                                                                                                                                                                                                     

sub upgrade6013GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.13_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    if($isLdapMaster) {
      runLdapAttributeUpgrade("58084");
    }
  }
  return 0;
}

sub upgrade6014GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.14_GA\n");
  return 0;
}

sub upgrade6015GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.15_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    # 43040, must be done on all LDAP servers
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    my $ldap;
    chomp($ldap_pass);
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    unless($result->code()) {
      $result = $ldap->modify( "cn=config", add => { 'olcTLSCACertificatePath' => '/opt/zmail/conf/ca'});
    }
    $result = $ldap->unbind;
  }
  return 0;
}

sub upgrade6016GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 6.0.16_GA\n");
  main::setLocalConfig("ldap_read_timeout", "0"); #70437
  return 0;
}

sub upgrade700BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.0.0_BETA1\n");
  if($isLdapMaster) {
    #runLdapAttributeUpgrade("10287");
    runLdapAttributeUpgrade("42828");
    runLdapAttributeUpgrade("43779");
    runLdapAttributeUpgrade("50258");
    runLdapAttributeUpgrade("50465");
  }
  if (main::isInstalled("zmail-store")) {
    # 43140
    my $mailboxd_java_heap_memory_percent =
      main::getLocalConfig("mailboxd_java_heap_memory_percent");
    $mailboxd_java_heap_memory_percent = 30
      if ($mailboxd_java_heap_memory_percent eq "");
    my $systemMemorySize = main::getSystemMemory();
    main::setLocalConfig("mailboxd_java_heap_size",
      int($systemMemorySize*1024*$mailboxd_java_heap_memory_percent/100));
    main::deleteLocalConfig("mailboxd_java_heap_memory_percent"); 
  }
  return 0;
}

sub upgrade700BETA2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.0.0_BETA2\n");
  if (main::isInstalled("zmail-ldap")) {
    if($isLdapMaster) {
      runLdapAttributeUpgrade("50458");
    }

    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcAccess'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcAccess");
    my $aclNumber=-1;
    my $attrMod="";

    foreach my $attr (@attrvals) {
      if ($attr =~ /zmailDomainName/) {
        ($aclNumber) = $attr =~ /^\{(\d+)\}*/;
        if ($attr !~ /uid=zmamavis,cn=appaccts,cn=zmail/) {
          $attrMod=$attr;
          $attrMod =~ s/by \* none/by dn.base="uid=zmamavis,cn=appaccts,cn=zmail" read  by \* none/;
        }
      }
    }

    if ($aclNumber != -1 && $attrMod ne "") {
      $result = $ldap->modify(
          $dn,
          delete => {olcAccess => "{$aclNumber}"},
      );
      $result = $ldap->modify(
          $dn,
          add =>{olcAccess=>"$attrMod"},
      );
    }
    $ldap->unbind;
  }
  return 0;
}

sub upgrade700BETA3 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.0.0_BETA3\n");
  if (main::isInstalled("zmail-ldap")) {
    runLdapAttributeUpgrade("47934") if ($isLdapMaster);
  }
  if (main::isInstalled("zmail-store")) {
    my $mailboxd_java_options=main::getLocalConfig("mailboxd_java_options");
    if ($mailboxd_java_options =~ /-Dsun.net.inetaddr.ttl=$/) {
      my $new_mailboxd_options;
      foreach my $option (split(/\s+/, $mailboxd_java_options)) {
        $new_mailboxd_options.=" $option" if ($option !~ /^-Dsun.net.inetaddr.ttl=/); 
      }
      $new_mailboxd_options =~ s/^\s+//;
      main::setLocalConfig("mailboxd_java_options", $new_mailboxd_options)
        if ($new_mailboxd_options ne "");
    }
  }
  return 0;
}

sub upgrade700RC1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.0.0_RC1\n");

  if (main::isInstalled("zmail-ldap")) {
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcDbIndex'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcDbIndex");
    my $needModify=1;

    foreach my $attr (@attrvals) {
      if ($attr =~ /zmailMailHost/) {
        $needModify=0;
      }
    }

    if ($needModify) {
      $result = $ldap->modify(
          $dn,
          add =>{olcDbIndex=>"zmailMailHost eq"},
      );
    }
    $ldap->unbind;
    if ($needModify) {
      &indexLdapAttribute("zmailMailHost");
    }
  }
  if (main::isInstalled("zmail-store")) {
    # Bug #53821
    my $mailboxd_java_options=main::getLocalConfig("mailboxd_java_options");

    $mailboxd_java_options .= " -XX:-OmitStackTraceInFastThrow"
      unless ($mailboxd_java_options =~ /OmitStackTraceInFastThrow/);
    main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
    main::setLocalConfig("mailboxd_java_options", "$mailboxd_java_options");
    
    
  }

  return 0;
}

sub upgrade700GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.0.0_GA\n");
  if (main::isInstalled("zmail-store")) {
    main::deleteLocalConfig("calendar_outlook_compatible_allday_events");
  }
  return 0;
}

sub upgrade701GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.0.1_GA\n");
  if (main::isInstalled("zmail-store")) {
    #56318
    my $mysql_mycnf = main::getLocalConfig("mysql_mycnf"); 
    if (!fgrep { /^max_allowed_packet/ } ${mysql_mycnf}) {
      main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-allowed-packet --section=mysqld --key=max_allowed_packet --set --value=16777216 ${mysql_mycnf}");
    }
    if ( -d "/opt/zmail/data/mailboxd/imap/cache" ) {
      system("/bin/rm -rf /opt/zmail/data/mailboxd/imap/cache/* 2> /dev/null");
    }
  }
  return 0;
}

sub upgrade710GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.1.0_GA\n");
  my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
  my $mysql_data_directory = 
    main::getLocalConfig("mysql_data_directory") || "${zmail_home}/db/data";
  my $zmail_tmp_directory = 
    main::getLocalConfig("zmail_tmp_directory") || "${zmail_home}/data/tmp";
  my $mysql_mycnf = 
    main::getLocalConfig("mysql_mycnf") || "${zmail_home}/conf/my.cnf";

  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      runLdapAttributeUpgrade("53745");
      runLdapAttributeUpgrade("55649");
      runLdapAttributeUpgrade("57039");
      runLdapAttributeUpgrade("57425");
    }
  }
  if (main::isInstalled("zmail-store")) {
    foreach my $i (qw(ib_logfile0 ib_logfile1)) {
      my $dbfile="${mysql_data_directory}/${i}";
      main::detail("Moving $dbfile to ${zmail_tmp_directory}/$i");
      system("mv -f ${dbfile} ${zmail_tmp_directory}/$i")
        if (-f ${dbfile});
    }
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-log_file_size --section=mysqld --key=innodb_log_file_size --set --value=524288000 ${mysql_mycnf}");
    main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-dirty-pages --section=mysqld --key=innodb_max_dirty_pages_pct --set --value=30 ${mysql_mycnf}");
     
  }
  return 0;
}

sub upgrade711GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.1.1_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      runLdapAttributeUpgrade("57855");
      runLdapAttributeUpgrade("58084");
      runLdapAttributeUpgrade("58481");
      runLdapAttributeUpgrade("58514");
      runLdapAttributeUpgrade("59720");
    }
  }
  if (main::isInstalled("zmail-store")) {
    # 53272
    if (-d "/opt/zmail/jetty/webapps/spnego") {
      system("rm -rf /opt/zmail/jetty/webapps/spnego");
    }
    if (-d "/opt/zmail/jetty/work/spnego") {
      system("rm -rf /opt/zmail/jetty/work/spnego");
    }
  }
  return 0;
}

sub upgrade712GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.1.2_GA\n");
  return 0;
}

sub upgrade713GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.1.3_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      runLdapAttributeUpgrade("11562");
      runLdapAttributeUpgrade("63475");
    }
    # 53301 - Fix ACLs for userCertificate for BES user and general usage
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcAccess'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcAccess");
    my $aclNumber=-1;
    my $attrMod="";

    foreach my $attr (@attrvals) {
      if ($attr =~ /zmailDomainName/) {
        ($aclNumber) = $attr =~ /^\{(\d+)\}*/;
        if ($attr !~ /uid=zmamavis,cn=appaccts,cn=zmail/) {
          $attrMod=$attr;
          if ($attrMod =~ /by \* read/) {
            $attrMod =~ s/by \* read/by dn.base="uid=zmamavis,cn=appaccts,cn=zmail" read  by \* read/;
          } else {
            $attrMod =~ s/by \* none/by dn.base="uid=zmamavis,cn=appaccts,cn=zmail" read  by \* none/;
          }
        }
      }
    }

    if ($aclNumber != -1 && $attrMod ne "") {
      $result = $ldap->modify(
          $dn,
          delete => {olcAccess => "{$aclNumber}"},
      );
      $result = $ldap->modify(
          $dn,
          add =>{olcAccess=>"$attrMod"},
      );
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcAccess'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcAccess");
    my $aclNumber=-1;
    my $attrMod="";

    my $fixup=0;
    foreach my $attr (@attrvals) {
      if ($attr =~ /homePhone,pager,mobile/) {
        if ($attr !~ /userCertificate/) {
          ($aclNumber) = $attr =~ /^\{(\d+)\}*/;
          $attrMod=$attr;
        }
      }
      if ($attr =~ /homePhone,mobile,pager/) {
        if ($attr !~ /userCertificate/) {
          ($aclNumber) = $attr =~ /^\{(\d+)\}*/;
          $attrMod=$attr;
          $fixup=1;
        }
      }
    }

    if ($aclNumber != -1 && $attrMod ne "") {
      if ($fixup) {
        $attrMod =~ s/homePhone,mobile,pager/homePhone,pager,mobile,userCertificate/;
      } else {
        $attrMod =~ s/homePhone,pager,mobile/homePhone,pager,mobile,userCertificate/;
      }
      $result = $ldap->modify(
          $dn,
          delete => {olcAccess => "{$aclNumber}"},
      );
      $result = $ldap->modify(
          $dn,
          add =>{olcAccess=>"$attrMod"},
      );
    }
    $ldap->unbind;
  }
  if (main::isInstalled("zmail-mta")) {
    my $mtaNetworks=main::getLdapServerValue("zmailMtaMyNetworks");
    $mtaNetworks =~ s/,/ /g;
    if ($mtaNetworks =~ m/127\.0\.0\.0\/8/) {
      $mtaNetworks =~ s/ $//;
      $mtaNetworks=$mtaNetworks . " [::1]/128";
    }
    main::setLdapServerConfig("zmailMtaMyNetworks", "$mtaNetworks");
  }
  return 0;
}

sub upgrade714GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.1.4_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    # 43040, must be done on all LDAP servers
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    my $ldap;
    chomp($ldap_pass);
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    unless($result->code()) {
      $result = $ldap->modify( "cn=config", add => { 'olcTLSCACertificatePath' => '/opt/zmail/conf/ca'});
    }
    $result = $ldap->unbind;
  }
  if (main::isInstalled("zmail-mta")) {
    my @zmailMtaRestriction = `$su "$ZMPROV gacf zmailMtaRestriction"`;
    foreach my $restriction (@zmailMtaRestriction) {
      $restriction =~ s/zmailMtaRestriction: //;
      chomp $restriction;
      if ($restriction =~ /^reject_invalid_hostname$/) {
        main::runAsZmail("$ZMPROV mcf -zmailMtaRestriction reject_invalid_hostname");
        main::runAsZmail("$ZMPROV mcf +zmailMtaRestriction reject_invalid_helo_hostname");
      }
      if ($restriction =~ /^reject_non_fqdn_hostname$/) {
        main::runAsZmail("$ZMPROV mcf -zmailMtaRestriction reject_non_fqdn_hostname");
        main::runAsZmail("$ZMPROV mcf +zmailMtaRestriction reject_non_fqdn_helo_hostname");
      }
      if ($restriction =~ /^reject_unknown_client$/) {
        main::runAsZmail("$ZMPROV mcf -zmailMtaRestriction reject_unknown_client");
        main::runAsZmail("$ZMPROV mcf +zmailMtaRestriction reject_unknown_client_hostname");
      }
      if ($restriction =~ /^reject_unknown_hostname$/) {
        main::runAsZmail("$ZMPROV mcf -zmailMtaRestriction reject_unknown_hostname");
        main::runAsZmail("$ZMPROV mcf +zmailMtaRestriction reject_unknown_helo_hostname");
      }
    }
  }
  if (main::isInstalled("zmail-store")) {
    main::setLocalConfig("calendar_cache_enabled", "true"); #66307
  }
  return 0;
}

sub upgrade720GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.2.0_GA\n");
  main::setLocalConfig("ldap_read_timeout", "0"); #70437
  if (main::isInstalled("zmail-store")) {
    # Bug #64466
    my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
    my $imap_cache_data_directory = $zmail_home . "/data/mailboxd/imap";
    rmtree("${imap_cache_data_directory}")
      if ( -d "${imap_cache_data_directory}/");
  }
  return 0;
}

sub upgrade721GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.2.1_GA\n");
  return 0;
}

sub upgrade722GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.2.2_GA\n");
  return 0;
}

sub upgrade723GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.2.3_GA\n");
  return 0;
}

sub upgrade724GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 7.2.4_GA\n");
  return 0;
}

sub upgrade800BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.0_BETA1\n");
  # bug 59607 - migrate old zmmtaconfig variables to zmconfigd
  foreach my $lc_var (qw(enable_config_restarts interval log_level listen_port debug watchdog watchdog_services)) {
    my $val = main::getLocalConfig("zmmtaconfig_${lc_var}");
    if ($val ne "") {
      main::setLocalConfig("zmconfigd_${lc_var}", "$val");
      main::deleteLocalConfig("zmmtaconfig_${lc_var}");
    }
  }
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      runLdapAttributeUpgrade("57866");
      runLdapAttributeUpgrade("57205");
      runLdapAttributeUpgrade("57875");
    }
    # 3884
    main::progress("Adding dynamic group configuration\n");
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20110615-AddDynlist.pl");
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20110721-AddUnique.pl");
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcDbIndex'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcDbIndex");
    my $MzmailMemberOf=1;
    my $MzmailSharedItem=1;

    foreach my $attr (@attrvals) {
      if ($attr =~ /zmailMemberOf/) {
        $MzmailMemberOf=0;
      }
      if ($attr =~ /zmailSharedItem/) {
        $MzmailSharedItem=0;
      }
    }

    if ($MzmailMemberOf) {
      $result = $ldap->modify(
          $dn,
          add =>{olcDbIndex=>"zmailMemberOf eq"},
      );
    }
    if ($MzmailSharedItem) {
      $result = $ldap->modify(
          $dn,
          add =>{olcDbIndex=>"zmailSharedItem eq,sub"},
      );
    }
    $ldap->unbind;
    if ($MzmailMemberOf) {
      &indexLdapAttribute("zmailMemberOf");
    }
    if ($MzmailSharedItem) {
      &indexLdapAttribute("zmailSharedItem");
    }
  }
  return 0;
}

sub upgrade800BETA2 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.0_BETA2\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      runLdapAttributeUpgrade("63722");
      runLdapAttributeUpgrade("64380");
      runLdapAttributeUpgrade("65070");
      runLdapAttributeUpgrade("66001");
      runLdapAttributeUpgrade("60640");
    }
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20111019-UniqueZmailId.pl");
  }
  if (main::isEnabled("zmail-store")) {
    if (startSql()) { return 1; }
      main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20111005-ItemIdCheckpoint.pl");

    # Bug: 60011
    my $mysql_root_password=`/opt/zmail/bin/zmlocalconfig -s -x -m nokey mysql_root_password`;
    my $mysql_socket=`/opt/zmail/bin/zmlocalconfig -s -x -m nokey mysql_socket`;
    my $host=`hostname`;
    chomp $mysql_root_password;
    chomp $mysql_socket;
    chomp $host;

    my $sql = <<FIX_RIGHTS_EOF;
      SET PASSWORD FOR 'root'\@'localhost' = PASSWORD('${mysql_root_password}');
      SET PASSWORD FOR 'root'\@'${host}' = PASSWORD('${mysql_root_password}');
      SET PASSWORD FOR 'root'\@'127.0.0.1' = PASSWORD('${mysql_root_password}');
      SET PASSWORD FOR 'root'\@'localhost.localdomain' = PASSWORD('${mysql_root_password}');
FIX_RIGHTS_EOF

    `/opt/zmail/mysql/bin/mysql -S '$mysql_socket' -u root --password='$mysql_root_password' -e "$sql"`;
    `/opt/zmail/mysql/bin/mysql -S '$mysql_socket' -u root --password='$mysql_root_password' -e "DROP USER ''\@'localhost'; DROP USER ''\@'${host}'"`;
    stopSql();

    # 66663
    my $cache_dir = main::getLocalConfig("calendar_cache_directory");
    system("rm -rf ${cache_dir}/* 2> /dev/null")
      if (-d ${cache_dir});
  }
  if (main::isInstalled("zmail-proxy")) {
      main::runAsZmail("$ZMPROV ms $hn -zmailServiceInstalled imapproxy");
      main::runAsZmail("$ZMPROV ms $hn +zmailServiceInstalled proxy");
    if (main::isEnabled("zmail-proxy")) {
      main::setLdapServerConfig($hn, '-zmailServiceEnabled', 'imapproxy');
      main::setLdapServerConfig($hn, '+zmailServiceEnabled', 'proxy');
    }
  }

  return 0;
}

sub upgrade800BETA3 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.0_BETA3\n");
  main::setLocalConfig("ldap_read_timeout", "0"); #70437
  main::detail("Removing /opt/zmail/ssl/zmail/{ca,server} to force creation or download of new ca and certificates.");
  system("rm -rf /opt/zmail/ssl/zmail/ca > /dev/null 2>&1");
  system("rm -rf /opt/zmail/ssl/zmail/server > /dev/null 2>&1");
  main::setLocalConfig("ssl_allow_untrusted_certs", "true");
  if (main::isInstalled("zmail-ldap")) {
    # Delete unused BDB DB keys
    foreach my $lc_var (qw(ldap_db_cachefree ldap_db_cachesize ldap_db_dncachesize ldap_db_idlcachesize ldap_db_shmkey ldap_overlay_syncprov_sessionlog)) {
      my $val = main::getLocalConfig("${lc_var}");
      if ($val ne "") {
        main::deleteLocalConfig("${lc_var}");
      }
    }
    foreach my $lc_var (qw(ldap_accesslog_cachefree ldap_accesslog_cachesize ldap_accesslog_dncachesize ldap_accesslog_idlcachesize ldap_accesslog_shmkey)) {
      my $val = main::getLocalConfig("${lc_var}");
      if ($val ne "") {
        main::deleteLocalConfig("${lc_var}");
      }
    }
    if ($isLdapMaster) {
      runLdapAttributeUpgrade("68831");
      runLdapAttributeUpgrade("68891");
    }
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20120210-AddSearchNoOp.pl");
  }
  if (main::isInstalled("zmail-store")) {
    my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
    if (-e "${zmail_home}/jetty-6.1.22.z6/etc/jetty.keytab") {
      `mkdir -p ${zmail_home}/data/mailboxd/spnego`;
      `cp -pf ${zmail_home}/jetty-6.1.22.z6/etc/jetty.keytab ${zmail_home}/data/mailboxd/spnego/jetty.keytab`;
    }
  }
  if (main::isInstalled("zmail-octopus")) {
    if (startSql()) { return 1; }
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20120209-octopusEvent.pl");
    stopSql();
  }
    
  return 0;
}

sub upgrade800BETA4 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.0_BETA4\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
        runLdapAttributeUpgrade("68190");
        runLdapAttributeUpgrade("68394");
        runLdapAttributeUpgrade("72007");
    }
    my $doIndex = &addLdapIndex("zmailDomainAliasTargetID","eq");
    if ($doIndex) {
      &indexLdapAttribute("zmailDomainAliasTargetID");
    }
    $doIndex = &addLdapIndex("zmailUCServiceId","eq");
    if ($doIndex) {
      &indexLdapAttribute("zmailUCServiceId");
    }
    $doIndex = &addLdapIndex("DKIMIdentity", "eq");
    if ($doIndex) {
      &indexLdapAttribute("DKIMIdentity");
    }
    $doIndex = &addLdapIndex("DKIMSelector", "eq");
    if ($doIndex) {
      &indexLdapAttribute("DKIMSelector");
    }
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcAccess'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcAccess");
    my $aclNumber=-1;
    my $attrMod="";

    foreach my $attr (@attrvals) {
      if ($attr =~ /zmailAllowFromAddress/) {
        if ($attr !~ /DKIMIdentity/) {
          ($aclNumber) = $attr =~ /^\{(\d+)\}*/;
          $attrMod=$attr;
        }
      }
    }

    if ($aclNumber != -1 && $attrMod ne "") {
      $attrMod =~ s/zmailAllowFromAddress/zmailAllowFromAddress,DKIMIdentity,DKIMSelector,DKIMDomain,DKIMKey/;
      $result = $ldap->modify(
          $dn,
          delete => {olcAccess => "{$aclNumber}"},
      );
      $result = $ldap->modify(
          $dn,
          add =>{olcAccess=>"$attrMod"},
      );
    }
    $ldap->unbind;

    my $toolthreads = main::getLocalConfig("ldap_common_toolthreads");
    if ($toolthreads == 1) {
       main::setLocalConfig("ldap_common_toolthreads", "2");
    }
    main::runAsZmail("perl -I${scriptDir} ${scriptDir}/migrate20120507-UniqueDKIMSelector.pl");
  }
  if (main::isInstalled("zmail-proxy")) {
    # bug 32683
    main::setLdapGlobalConfig("zmailReverseProxySSLToUpstreamEnabled", "FALSE");
  }
  foreach my $lc_var (qw(cbpolicyd_bind_host logger_mysql_bind_address logger_mysql_directory logger_mysql_data_directory logger_mysql_socket logger_mysql_pidfile logger_mysql_mycnf logger_mysql_errlogfile logger_mysql_port zmail_logger_mysql_password)) {
    main::deleteLocalConfig("$lc_var");
  }
  return 0;
}

sub upgrade800BETA5 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.0_BETA5\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
        runLdapAttributeUpgrade("67237");
    }
  }
  if (main::isInstalled("zmail-mta")) {
    if (-f "/opt/zmail/conf/sauser.cf") {
      `mv /opt/zmail/conf/sauser.cf /opt/zmail/conf/sa/sauser.cf`;
    }
  }
  return 0;
}

sub upgrade800GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.0_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
        runLdapAttributeUpgrade("75450");
        runLdapAttributeUpgrade("76427");
    }
  }
  if (main::isInstalled("zmail-mta")) {
    my $cbpdb="/opt/zmail/data/cbpolicyd/db/cbpolicyd.sqlitedb";
    if (-f $cbpdb) {
      main::runAsZmail("sqlite3 $cbpdb < ${scriptDir}/migrate20130227-UpgradeCBPolicyDSchema.sql >/dev/null 2>&1");
    }
  }
  return 0;
}

sub upgrade801GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.1_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    my $dn="olcDatabase={2}mdb,cn=config";
    my $alog=0;
    if ($isLdapMaster) {
      $result = $ldap->search(
                        base=> "cn=accesslog",
                        filter=>"(objectClass=*)",
                        scope => "base",
                        attrs => ['1.1'],
      );
      my $size = $result->count;
      if ($size > 0 ) {
        $dn="olcDatabase={3}mdb,cn=config";
        $alog=1;
      }
    }
    $result = $ldap->search(
      base=> "$dn",
      filter=>"(objectClass=*)",
      scope => "base",
      attrs => ['olcDbEnvFlags'],
    );
    my $entry=$result->entry($result->count-1);
    my @attrvals=$entry->get_value("olcDbEnvFlags");

    if (!(@attrvals)) {
      $result = $ldap->modify(
          $dn,
          add =>{olcDbEnvFlags=>["writemap","nometasync"]},
      );
    }
    if ($isLdapMaster && $alog == 1) {
      $result = $ldap->search(
        base=> "olcDatabase={2}mdb,cn=config",
        filter=>"(objectClass=*)",
        scope => "base",
        attrs => ['olcDbEnvFlags'],
      );
      my $entry=$result->entry($result->count-1);
      my @attrvals=$entry->get_value("olcDbEnvFlags");
  
      if (!(@attrvals)) {
        $result = $ldap->modify(
            "olcDatabase={2}mdb,cn=config",
            add =>{olcDbEnvFlags=>["writemap","nometasync"]},
        );
      }
    }
    $ldap->unbind;
  }
  return 0;
}

sub upgrade802GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.2_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    if ($isLdapMaster) {
      my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
      chomp($ldap_pass);
      my $ldap;
      unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
         main::progress("Unable to contact to ldapi: $!\n");
      }
      my $result = $ldap->bind("cn=config", password => $ldap_pass);
      $result = $ldap->modify(
        "uid=zmpostfix,cn=appaccts,cn=zmail",
        replace => {
          zmailId => "a8255e5f-142b-4aa0-8aab-f8591b6455ba",
        }
      );
      $ldap->unbind;
    }
  }

  if (main::isInstalled("zmail-mta")) {
    doAntiSpamMysql55Upgrade();
    my $mtamilter = main::getLdapServerValue("zmailMtaSmtpdMilters");
    my $miltervalue="inet:localhost:8465";
    if ($mtamilter ne "")  {
      if ($mtamilter =~ /$miltervalue/) {
        $mtamilter =~ s/$miltervalue//;
        main::setLdapServerConfig("zmailMtaSmtpdMilters", "$mtamilter");
      }
    }
  }
  return 0;
}

sub upgrade803GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.3_GA\n");
  if (main::isInstalled("zmail-ldap")) {
     main::setLocalConfig("ldap_common_toolthreads", "2");
  }
  if (main::isInstalled("zmail-store")) {
    my $mailboxd_java_options=main::getLocalConfig("mailboxd_java_options");
    if ($mailboxd_java_options =~ /-XX:MaxPermSize=128m/) {
      $mailboxd_java_options =~ s/-XX:MaxPermSize=128m/-XX:MaxPermSize=350m/;
      main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
      main::setLocalConfig("mailboxd_java_options", $mailboxd_java_options)
    }
  }
  main::deleteLocalConfig("zmail_dos_filter_max_requests_per_sec");
  return 0;
}

sub upgrade804GA {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 8.0.4_GA\n");
  if (main::isInstalled("zmail-ldap")) {
    my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
    chomp($ldap_pass);
    my $ldap;
    unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
       main::progress("Unable to contact to ldapi: $!\n");
    }
    my $result = $ldap->bind("cn=config", password => $ldap_pass);
    $result = $ldap->modify(
      "olcDatabase={2}mdb,cn=config",
      replace => {
        olcDbCheckpoint => "0 0",
      }
    );
    if ($isLdapMaster) {
      $result = $ldap->modify(
        "olcDatabase={3}mdb,cn=config",
        replace => {
          olcDbCheckpoint => "0 0",
        }
      );
    }
    $ldap->unbind;
    main::deleteLocalConfig("ldap_db_checkpoint");
    main::deleteLocalConfig("ldap_accesslog_checkpoint");
    if ($isLdapMaster) {
        runLdapAttributeUpgrade("75650");
    }
  }
  if (main::isInstalled("zmail-mta")) {
    main::setLdapServerConfig($hn, '+zmailServiceInstalled', 'opendkim');
    main::setLdapServerConfig($hn, '+zmailServiceEnabled', 'opendkim');
    main::deleteLocalConfig("cbpolicyd_timeout");
  }
  if (main::isInstalled("zmail-store")) {
    my $zmailIPMode=main::getLdapServerValue("zmailIPMode");
    my $mysql_mycnf = main::getLocalConfig("mysql_mycnf"); 
    if ($zmailIPMode eq "ipv4") {
        main::setLocalConfig("mysql_bind_address", "127.0.0.1");
        main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-bind --section=mysqld --key=bind-address --unset ${mysql_mycnf}");
        main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-bind --section=mysqld --key=bind-address --set --value=127.0.0.1 ${mysql_mycnf}");
    } elsif ($zmailIPMode eq "both") {
        main::setLocalConfig("mysql_bind_address", "::1");
        main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-bind --section=mysqld --key=bind-address --unset ${mysql_mycnf}");
        main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-bind --section=mysqld --key=bind-address --set --value=::1 ${mysql_mycnf}");
    } elsif ($zmailIPMode eq "ipv6") {
        main::setLocalConfig("mysql_bind_address", "::1");
        main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-bind --section=mysqld --key=bind-address --unset ${mysql_mycnf}");
        main::runAsZmail("/opt/zmail/libexec/zminiutil --backup=.pre-${targetVersion}-bind --section=mysqld --key=bind-address --set --value=::1 ${mysql_mycnf}");
    }
    my $mailboxd_java_options=main::getLocalConfig("mailboxd_java_options");
    if ($mailboxd_java_options !~ /-Dorg.apache.jasper.compiler.disablejsr199/) {
      $mailboxd_java_options = $mailboxd_java_options." -Dorg.apache.jasper.compiler.disablejsr199=true";
      main::detail("Modified mailboxd_java_options=$mailboxd_java_options");
      main::setLocalConfig("mailboxd_java_options", $mailboxd_java_options)
    }
  }
  return 0;
}

sub upgrade900BETA1 {
  my ($startBuild, $targetVersion, $targetBuild) = (@_);
  main::progress("Updating from 9.0.0_BETA1\n");
  return 0;
}

sub stopZmail {
  main::progress("Stopping zmail services...");
  my $rc = main::runAsZmail("/opt/zmail/bin/zmcontrol stop");
  main::progress(($rc == 0) ? "done.\n" : "failed. exiting.\n");
  return $rc;
}

sub startLdap {
  main::progress("Checking ldap status...");
  my $rc = main::runAsZmail("/opt/zmail/bin/ldap status");
  main::progress(($rc == 0) ? "already running.\n" : "not running.\n");

  if ($rc) {
    main::progress("Running zmldapapplyldif...");
    $rc = main::runAsZmail("/opt/zmail/libexec/zmldapapplyldif");
    main::progress(($rc == 0) ? "done.\n" : "failed.\n");

    main::progress("Checking ldap status...");
    $rc = main::runAsZmail("/opt/zmail/bin/ldap status");
    main::progress(($rc == 0) ? "already running.\n" : "not running.\n");

    if ($rc) {
      main::progress("Starting ldap...");
      my $rc = main::runAsZmail("/opt/zmail/bin/ldap start");
      main::progress(($rc == 0) ? "done.\n" : "failed with exit code: $rc.\n");
      if ($rc) {
        system("$su \"/opt/zmail/bin/ldap start 2>&1 | grep failed\"");
        return $rc;
      }
    }
  }
  return 0;
}

sub stopLdap {
  main::progress("Stopping ldap...");
  my $rc = main::runAsZmail("/opt/zmail/bin/ldap stop");
  main::progress(($rc == 0) ? "done.\n" : "failed. ldap had exit status: $rc.\n");
  sleep 5 unless $rc; # give it a chance to shutdown.
  return $rc;
}

sub isSqlRunning {
  my $rc = 0xffff & system("$su \"/opt/zmail/bin/mysqladmin status > /dev/null 2>&1\"");
  $rc = $rc >> 8;
  return($rc ? undef : 1);
}

sub startSql {

  unless (isSqlRunning()) {
    main::progress("Starting mysql...");
    my $rc = main::runAsZmail("/opt/zmail/bin/mysql.server start");
    my $timeout = sleep 10;
    while (!isSqlRunning() && $timeout <= 1200 ) {
      $rc = main::runAsZmail("/opt/zmail/bin/mysql.server start");
      $timeout += sleep 10;
    }
    main::progress(($rc == 0) ? "done.\n" : "failed.\n");
    return $rc if $rc;
  }
  return(isSqlRunning() ? 0 : 1);
}

sub stopSql {
  if (isSqlRunning()) {
    main::progress("Stopping mysql...");
    my $rc = main::runAsZmail("/opt/zmail/bin/mysql.server stop");
    my $timeout = sleep 10;
    while (isSqlRunning() && $timeout <= 120 ) {
      $rc = main::runAsZmail("/opt/zmail/bin/mysql.server stop");
      $timeout += sleep 10;
    }
    main::progress(($rc == 0) ? "done.\n" : "failed.\n");
    return $rc if $rc;
  }
  return(isSqlRunning() ? 1 : 0);
}

sub isLoggerSqlRunning {
  my $rc = main::runAsZmail("/opt/zmail/bin/logmysqladmin status > /dev/null 2>&1");
  return($rc ? undef : 1);
}

sub startLoggerSql {
  unless (isLoggerSqlRunning()) {
    main::progress("Starting logger mysql...");
    my $rc = main::runAsZmail("/opt/zmail/bin/logmysql.server start");
    my $timeout = sleep 10;
    while (!isLoggerSqlRunning() && $timeout <= 120 ) {
      $rc = main::runAsZmail("/opt/zmail/bin/logmysql.server start");
      $timeout += sleep 10;
    }
    main::progress(($rc == 0) ? "done.\n" : "failed.\n");
    return $rc if $rc;
  }
  return(isLoggerSqlRunning() ? 0 : 1);
}

sub stopLoggerSql {
  if (isLoggerSqlRunning()) {
    main::progress("Stopping logger mysql...");
    my $rc = main::runAsZmail("/opt/zmail/bin/logmysql.server stop");
    my $timeout = sleep 10;
    while (isLoggerSqlRunning() && $timeout <= 120 ) {
      $rc = main::runAsZmail("/opt/zmail/bin/logmysql.server stop");
      $timeout += sleep 10;
    }
    main::progress(($rc == 0) ? "done.\n" : "failed.\n");
    return $rc if $rc;
  }
  return(isLoggerSqlRunning() ? 1 : 0);
}


sub runSchemaUpgrade {
  my $curVersion = shift;

  if (! defined ($updateScripts{$curVersion})) {
    main::progress ("Can't upgrade from version $curVersion - no script!\n");
    return 1;
  }

  if (! -x "${scriptDir}/$updateScripts{$curVersion}" ) {
    main::progress ("Can't run ${scriptDir}/$updateScripts{$curVersion} - not executable!\n");
    return 1;
  }

  main::progress ("Running ${scriptDir}/$updateScripts{$curVersion}\n");
  open(MIG, "$su \"/usr/bin/perl -I${scriptDir} ${scriptDir}/$updateScripts{$curVersion}\" 2>&1|");
  while (<MIG>) {
    main::progress($_);
  }
  close(MIG);
  my $rc = $?;
  if ($rc != 0) {
    main::progress ("Script failed with code $rc: $! - exiting\n");
    return $rc;
  }
  return 0;
}

sub getInstalledPackages {

  foreach my $p (@packageList) {
    if (main::isInstalled($p)) {
      $installedPackages{$p} = $p;
    }
  }

}
sub cleanPostfixLC {

  my ($var,$val);
  foreach $var (qw(command_directory daemon_directory mailq_path manpage_directory newaliases_path queue_directory sendmail_path)) {

    $val = main::getLocalConfig("postfix_${var}");
    if ($val =~ /postfix-(\d.*)\//) {
      main::detail("Removing $1 from postfix_${var}");
      $val =~ s/postfix-\d.*\//postfix\//;
      main::setLocalConfig("postfix_${var}", "$val");
    }
  }
}

sub updatePostfixLC {
  my ($fromVersion, $toVersion) = @_;

  # update localconfig vars
  my ($var,$val);
  foreach $var (qw(version command_directory daemon_directory mailq_path manpage_directory newaliases_path queue_directory sendmail_path)) {
    if ($var eq "version") {
      $val = $toVersion;
      main::setLocalConfig("postfix_${var}", "$val");
      next;
    }

    $val = main::getLocalConfig("postfix_${var}");
    $val =~ s/postfix-$fromVersion/postfix/;
    $val =~ s/postfix-$toVersion/postfix/;
    main::setLocalConfig("postfix_${var}", "$val");
  }
}

sub movePostfixQueue {
  my ($fromVersion,$toVersion) = @_;

  # update localconfig vars
  my ($var,$val);
  foreach $var (qw(version command_directory daemon_directory mailq_path manpage_directory newaliases_path queue_directory sendmail_path)) {
    $val = main::getLocalConfig("postfix_${var}");
    if ($val eq $toVersion) {
      next;
    }
    if ($val =~ m/postfix-$toVersion/) {
      next;
    }
    $val =~ s/$fromVersion/$toVersion/;
    $val = $toVersion if ($var eq "version");
    main::setLocalConfig("postfix_${var}", "$val"); 
  }

  # move the spool files
  if ( -d "/opt/zmail/postfix-${fromVersion}/spool" ) {
    main::progress("Moving postfix queues from $fromVersion to $toVersion\n");
    my @dirs = qw /active bounce corrupt defer deferred flush hold incoming maildrop/;
    `mkdir -p /opt/zmail/postfix-${toVersion}/spool`;
    foreach my $d (@dirs) {
      if (-d "/opt/zmail/postfix-${fromVersion}/spool/${d}/") {
        main::progress("Moving $d\n");
        `mkdir -p /opt/zmail/postfix-${toVersion}/spool/${d}`;
        `cp -Rf /opt/zmail/postfix-${fromVersion}/spool/${d}/* /opt/zmail/postfix-${toVersion}/spool/${d}`;
        `chown -R postfix:postdrop /opt/zmail/postfix-${toVersion}/spool/${d}`;
      }
    }
  }

  main::runAsRoot("/opt/zmail/libexec/zmfixperms");
}

sub relocatePostfixQueue {
  my $toDir="/opt/zmail/data/postfix";
  my $fromDir="/opt/zmail/postfix-2.4.3.4z";
  my $curDir=main::getcwd();

  main::progress("Migrating Postfix spool directory\n");
  mkdir -p "$toDir/spool";
  if ( -d "$fromDir/spool" && ! -d "$toDir/spool/active") {
    chdir($fromDir);
    `tar cf - spool 1>/dev/null 2>&1 | (cd $toDir; tar xfp -) >/dev/null 2>&1`;
    chdir($curDir);
  }
  main::runAsRoot("/opt/zmail/libexec/zmfixperms");
}

sub updateLoggerMySQLcnf {

  my $mycnf = "/opt/zmail/conf/my.logger.cnf";

  return unless (-f $mycnf);
  my $mysql_pidfile = main::getLocalConfig("logger_mysql_pidfile");
  $mysql_pidfile = "/opt/zmail/logger/db/mysql.pid" if ($mysql_pidfile eq "");
  if (-e "$mycnf") {
    unless (open(MYCNF, "$mycnf")) {
      Migrate::myquit(1, "${mycnf}: $!\n");
    }
    my @CNF = <MYCNF>;
    close(MYCNF);
    my $i=0;
    my $mycnfChanged = 0;
    my $tmpfile = "/tmp/my.cnf.$$";;
    my $zmail_user = `${zmlocalconfig} -m nokey zmail_user 2> /dev/null` || "zmbra";;
    open(TMP, ">$tmpfile");
    foreach (@CNF) {
      if (/^port/ && $CNF[$i+1] !~ m/^user/) {
        print TMP;
        print TMP "user         = $zmail_user\n";
        $mycnfChanged=1;
        next;
      } elsif (/^err-log/ && $CNF[$i+1] !~ m/^pid-file/) {
        print TMP;
        print TMP "pid-file = ${mysql_pidfile}\n";
        $mycnfChanged=1;
        next;
      } elsif (/^thread_cache\s/) {
        # 29475 fix thread_cache_size
        s/^thread_cache/thread_cache_size/g;
        print TMP;
        $mycnfChanged=1;
        next;
      } elsif (/^skip-external-locking/) {
        # 19749 remove skip-external-locking
        print TMP "external-locking\n";
        $mycnfChanged=1;
        next;
      }
      print TMP;
      $i++;
    }
    close(TMP);
  
    if ($mycnfChanged) {
      `mv $mycnf ${mycnf}.${startVersion}`;
      `cp -f $tmpfile $mycnf`;
      `chmod 644 $mycnf`;
    } 
  }
}
sub updateMySQLcnf {

  return if ($mysqlcnfUpdated == 1);
  my $mycnf = "/opt/zmail/conf/my.cnf";
  my $mysql_pidfile = main::getLocalConfig("mysql_pidfile");
  $mysql_pidfile = "/opt/zmail/db/mysql.pid" if ($mysql_pidfile eq "");
  if (-e "$mycnf") {
    unless (open(MYCNF, "$mycnf")) {
      Migrate::myquit(1, "${mycnf}: $!\n");
    }
    my @CNF = <MYCNF>;
    close(MYCNF);
    my $i=0;
    my $mycnfChanged = 0;
    my $tmpfile = "/tmp/my.cnf.$$";;
    my $zmail_user = `${zmlocalconfig} -m nokey zmail_user 2> /dev/null` || "zmail";;
    my $zmail_tmp_directory = `${zmlocalconfig} -m nokey zmail_tmp_directory 2> /dev/null` || "zmail";;
    open(TMP, ">$tmpfile");
    foreach (@CNF) {
      if (/^port/ && $CNF[$i+1] !~ m/^user/) {
        print TMP;
        print TMP "user         = $zmail_user\n";
        $mycnfChanged=1;
        next;
      } elsif (/^err-log/ && $CNF[$i+1] !~ m/^pid-file/) {
        print TMP;
        print TMP "pid-file = ${mysql_pidfile}\n";
        $mycnfChanged=1;
        next;
      } elsif (/^thread_cache\s+=\s+(\d+)$/) {
        # 29475 fix thread_cache_size
        if ($1 > 110) {
          s/^thread_cache/thread_cache_size/g;
          print TMP;
        } else {
          print TMP "thread_cache_size = 110\n";
          next;
        }
        $mycnfChanged=1;
        next;
      } elsif (/^thread_cache_size\s+=\s+(\d+)$/) {
        if ($1 < 110) {
          $mycnfChanged=1;
          print TMP "thread_cache_size = 110\n";
          next;
        }
      } elsif (/^max_connections\s+=\s+(\d+)$/) {
        if ($1 < 110) {
          $mycnfChanged=1;
          print TMP "max_connections = 110\n";
          next;
        }
      } elsif (/^skip-external-locking/) {
        # 19749 remove skip-external-locking
        print TMP "external-locking\n";
        $mycnfChanged=1;
        next;
     } elsif (/^innodb_open_files/) {
        # 24906
        print TMP;
        print TMP "innodb_max_dirty_pages_pct = 10\n"
          unless(grep(/^innodb_max_dirty_pages_pct/, @CNF));
        print TMP "innodb_flush_method = O_DIRECT\n"
          unless(grep(/^innodb_flush_method/, @CNF));
        $mycnfChanged=1;
        next;
      } elsif (/^user/ && $CNF[$i+1] !~ m/^tmpdir/) {
        print TMP;
        print TMP "tmpdir       = $zmail_tmp_directory\n";
        $mycnfChanged=1;
        next;
      }
      print TMP;
      $i++;
    }
    close(TMP);
  
    if ($mycnfChanged) {
      `mv $mycnf ${mycnf}.${startVersion}`;
      `cp -f $tmpfile $mycnf`;
      `chmod 644 $mycnf`;
    } 
  }
}

sub clearTomcatWorkDir {

  my $workDir = "/opt/zmail/tomcat/work";
  return unless (-d "$workDir");
  system("find $workDir -type f -exec rm -f {} \\\;");

}

sub clearRedologDir($$) {
  my ($redologDir, $version) = @_;
  if (-d "$redologDir" && ! -e "${redologDir}/${version}") {
    `mkdir ${redologDir}/${version}`;
    `mv ${redologDir}/* ${redologDir}/${version}/ > /dev/null 2>&1`;
    `chown zmail:zmail $redologDir > /dev/null 2>&1`;
  }
  return;
}

sub clearBackupDir($$) {
  my ($backupDir, $version) = @_;
  if (-e "$backupDir" && ! -e "${backupDir}/${version}") {
    `mkdir ${backupDir}/${version}`;
    `mv ${backupDir}/* ${backupDir}/${version} > /dev/null 2>&1`;
    `chown zmail:zmail $backupDir > /dev/null 2>&1`;
  }
  return;
}

sub doMysqlTableCheck {

  my $updateSQL = "/opt/zmail/mysql/share/mysql/mysql_fix_privilege_tables.sql";
  if (-e "$updateSQL") {
    main::progress("Verifying mysql tables\n");
    my $db_pass = main::getLocalConfig("mysql_root_password");
    my $mysql = "/opt/zmail/bin/mysql";
    my $cmd = "$mysql --force --user=root --password=$db_pass --database=mysql --batch < $updateSQL";
    main::progress("Executing $cmd\n");
    main::runAsZmail("$cmd > /tmp/mysql_fix_perms.out 2>&1");
  }
}

sub doMysql51Upgrade {
    my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
    my $mysql_mycnf = main::getLocalConfig("mysql_mycnf"); 
    my $zmail_log_directory = main::getLocalConfig("zmail_log_directory") || "${zmail_home}/log"; 

    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --key=ignore-builtin-innodb --set ${mysql_mycnf}");
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --set --key=plugin-load --value='innodb=ha_innodb_plugin.so;innodb_trx=ha_innodb_plugin.so;innodb_locks=ha_innodb_plugin.so;innodb_lock_waits=ha_innodb_plugin.so;innodb_cmp=ha_innodb_plugin.so;innodb_cmp_reset=ha_innodb_plugin.so;innodb_cmpmem=ha_innodb_plugin.so;innodb_cmpmem_reset=ha_innodb_plugin.so' ${mysql_mycnf}");
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --unset --key=log-long-format ${mysql_mycnf}");
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --unset --key=log-slow-queries ${mysql_mycnf}");
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --set --key=slow_query_log --value=1 ${mysql_mycnf}");
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --set --key=slow_query_log_file --value=${zmail_log_directory}/myslow.log ${mysql_mycnf}");
    if (fgrep { /^log-bin/ } ${mysql_mycnf}) {
      main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --set --key=binlog-format --value=MIXED ${mysql_mycnf}");
    }
}

sub doMysql55Upgrade {
    my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
    my $mysql_mycnf = main::getLocalConfig("mysql_mycnf"); 
    my $zmail_log_directory = main::getLocalConfig("zmail_log_directory") || "${zmail_home}/log"; 
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --unset --key=ignore-builtin-innodb ${mysql_mycnf}");
    main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --unset --key=plugin-load ${mysql_mycnf}");
}

sub doAntiSpamMysql55Upgrade {
    my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
    my $antispam_mysql_mycnf = main::getLocalConfig("antispam_mysql_mycnf"); 
    my $zmail_log_directory = main::getLocalConfig("zmail_log_directory") || "${zmail_home}/log"; 
    if ( -e ${antispam_mysql_mycnf} ) {
        main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --unset --key=ignore-builtin-innodb ${antispam_mysql_mycnf}");
        main::runAsZmail("${zmail_home}/libexec/zminiutil --backup=.pre-${targetVersion} --section=mysqld --unset --key=plugin-load ${antispam_mysql_mycnf}");
    }
}

sub doMysqlUpgrade {
    my $db_pass = main::getLocalConfig("mysql_root_password");
    my $zmail_tmp = main::getLocalConfig("zmail_tmp_directory") || "/tmp";
    my $zmail_home = main::getLocalConfig("zmail_home") || "/opt/zmail";
    my $mysql_socket = main::getLocalConfig("mysql_socket");
    my $mysql_mycnf = main::getLocalConfig("mysql_mycnf"); 
    my $mysqlUpgrade = "${zmail_home}/mysql/bin/mysql_upgrade";
    my $cmd = "$mysqlUpgrade --defaults-file=$mysql_mycnf -S $mysql_socket --user=root --password=$db_pass";
    main::progress("Running mysql_upgrade...");
    main::runAsZmail("$cmd > ${zmail_tmp}/mysql_upgrade.out 2>&1");
    main::progress("done.\n");
}

sub doBackupRestoreVersionUpdate($) {
  my ($startVersion) = @_;

  my ($prevRedologVersion,$currentRedologVersion,$prevBackupVersion,$currentBackupVersion);
  $prevRedologVersion = &Migrate::getRedologVersion;
  $currentRedologVersion = `$su "zmjava org.zmail.cs.redolog.util.GetVersion"`;
  chomp($currentRedologVersion);

  return unless ($currentRedologVersion);

  Migrate::insertRedologVersion($currentRedologVersion)
    if ($prevRedologVersion eq "");

  if ($prevRedologVersion != $currentRedologVersion) {
    main::progress("Redolog version update required.\n");
    Migrate::updateRedologVersion($prevRedologVersion,$currentRedologVersion);
    main::progress("Redolog version update finished.\n");
  }

  if (-f "/opt/zmail/lib/ext/backup/zmailbackup.jar") {
    $prevBackupVersion = &Migrate::getBackupVersion; 
    $currentBackupVersion = `$su "zmjava org.zmail.cs.backup.util.GetVersion"`;
    chomp($currentBackupVersion);

    return unless ($currentBackupVersion);

    Migrate::insertBackupVersion($currentBackupVersion)
      if ($prevBackupVersion eq "");

    if ($prevBackupVersion != $currentBackupVersion) {
      main::progress("Backup version update required.\n");
      Migrate::updateBackupVersion($prevBackupVersion,$currentBackupVersion);
      main::progress("Backup version update finished.\n");
    }
  }
  my ($currentMajorBackupVersion,$currentMinorBackupVersion) = split(/\./, $currentBackupVersion);
  my ($prevMajorBackupVersion,$prevMinorBackupVersion) = split(/\./, $prevBackupVersion);

  # clear both directories only if the major backup version changed.  
  # backups are backwards compatible between minor versions
  return if ($prevBackupVersion == $currentBackupVersion);
  return if ($prevMajorBackupVersion >= $currentMajorBackupVersion);

  main::progress("Moving /opt/zmail/backup/* to /opt/zmail/backup/${startVersion}-${currentBackupVersion}.\n");
  clearBackupDir("/opt/zmail/backup", "${startVersion}-${currentBackupVersion}");
  main::progress("Moving /opt/zmail/redolog/* to /opt/zmail/redolog/${startVersion}-${currentRedologVersion}.\n");
  clearRedologDir("/opt/zmail/redolog", "${startVersion}-${currentRedologVersion}");

}

sub migrateTomcatLCKey {
  my ($key,$defVal) = @_;
  $defVal="" unless $defVal;
  my ($oldKey,$newKey,$oldVal); 
  $oldKey="tomcat_${key}";
  $newKey="mailboxd_${key}";
  $oldVal = main::getLocalConfig($oldKey);
  if ($oldVal ne "") {
    main::setLocalConfig("$newKey", "$oldVal");
  } elsif ($defVal ne "") {
    main::setLocalConfig("$newKey", "$defVal");
  }
  main::deleteLocalConfig("$oldKey");
}

sub indexLdap {
  if (main::isInstalled ("zmail-ldap")) {
    stopLdap();
    main::runAsZmail ("/opt/zmail/libexec/zmslapindex");
    if (startLdap()) {return 1;}
  }
  return;
}

sub indexLdapAttribute {
  my ($key) = @_;
  if (main::isInstalled ("zmail-ldap")) {
    stopLdap();
    main::runAsZmail ("/opt/zmail/libexec/zmslapindex $key");
    if (startLdap()) {return 1;}
  }
  return;
}

sub reloadLdap($) {
  my ($upgradeVersion) = @_;
  if (main::isInstalled ("zmail-ldap")) {
    if($main::migratedStatus{"LdapReloaded$upgradeVersion"} ne "CONFIGURED") {
      my $ldifFile="/opt/zmail/data/ldap/ldap-accesslog.bak";
      if (-d '/opt/zmail/data/ldap/config/cn=config/olcDatabase={3}mdb') {
        if (-f $ldifFile && -s $ldifFile) {
          if (-d "/opt/zmail/data/ldap/accesslog") { 
            main::progress("Loading accesslog DB..."); 
            if (-d "/opt/zmail/data/ldap/accesslog.prev") {
              `mv /opt/zmail/data/ldap/accesslog.prev /opt/zmail/data/ldap/accesslog.prev.$$`;
            }
            `mv /opt/zmail/data/ldap/accesslog /opt/zmail/data/ldap/accesslog.prev`;
            `mkdir -p /opt/zmail/data/ldap/accesslog/db`;
            `chown -R zmail:zmail /opt/zmail/data/ldap`;
            my $rc;
            $rc=main::runAsZmail("/opt/zmail/libexec/zmslapadd -a $ldifFile");
            if ($rc != 0) {
              main::progress("slapadd import of accesslog db failed.\n");
              return 1;
            }
            main::progress("done.\n");
          }
        } else {
          main::progress("Creating new accesslog DB...");
          if (-d "/opt/zmail/data/ldap/accesslog.prev") {
            `mv /opt/zmail/data/ldap/accesslog.prev /opt/zmail/data/ldap/accesslog.prev.$$`;
          }
          `mv /opt/zmail/data/ldap/accesslog /opt/zmail/data/ldap/accesslog.prev`;
          `mkdir -p /opt/zmail/data/ldap/accesslog/db`;
          `chown -R zmail:zmail /opt/zmail/data/ldap`;
          main::progress("done.\n");
        }
      }
      $ldifFile="/opt/zmail/data/ldap/ldap.bak";
      if (-f $ldifFile && -s $ldifFile) {
        main::progress("Loading database..."); 
        if (-d "/opt/zmail/data/ldap/mdb.prev") {
          `mv /opt/zmail/data/ldap/mdb.prev /opt/zmail/data/ldap/mdb.prev.$$`;
        }
        `mv /opt/zmail/data/ldap/mdb /opt/zmail/data/ldap/mdb.prev`;
        `mkdir -p /opt/zmail/data/ldap/mdb/db`;
        `chown -R zmail:zmail /opt/zmail/data/ldap`;
        my $rc;
        $rc=main::runAsZmail("/opt/zmail/libexec/zmslapadd $ldifFile");
        if ($rc != 0) {
          main::progress("slapadd import failed.\n");
          return 1;
        }
	chmod 0640, $ldifFile;
        main::progress("done.\n");
      } else {
        if (! -f $ldifFile) {
          main::progress("Error: Unable to find /opt/zmail/data/ldap/ldap.bak\n");
        } else {
          main::progress("Error: /opt/zmail/data/ldap/ldap.bak is empty\n");
        }
        return 1;
      }
      main::configLog("LdapUpgraded$upgradeVersion");
    }
    if (startLdap()) {return 1;} 
  }
  return 0;
}

sub upgradeLdap($) {
  my ($upgradeVersion) = @_;
  if (main::isInstalled ("zmail-ldap")) {
    if($main::migratedStatus{"LdapUpgraded$upgradeVersion"} ne "CONFIGURED") {
      # Fix LDAP schema for bug#62443
      unlink("/opt/zmail/data/ldap/config/cn\=config/cn\=schema/cn\=\{3\}zmail.ldif");
      unlink("/opt/zmail/data/ldap/config/cn\=config/cn\=schema/cn\=\{4\}amavisd.ldif");
      my $ldifFile="/opt/zmail/data/ldap/ldap.bak";
      if (-f $ldifFile && -s $ldifFile) {
        chmod 0644, $ldifFile;
        my $slapinfile = "$ldifFile";
        my $slapoutfile = "/opt/zmail/data/ldap/ldap.80";
        main::progress("Upgrading ldap data...");
        open(IN,"<$slapinfile");
        open(OUT,">$slapoutfile");
        while(<IN>) {
          if ($_ =~ /^zmailChildAccount:/) {next;}
          if ($_ =~ /^zmailChildVisibleAccount:/) {next;}
          if ($_ =~ /^zmailPrefChildVisibleAccount:/) {next;}
          if ($_ =~ /^zmailPrefStandardClientAccessilbityMode:/) {next;}
          if ($_ =~ /^objectClass: zmailHsmGlobalConfig/) {next;}
          if ($_ =~ /^objectClass: zmailHsmServer/) {next;}
          if ($_ =~ /^objectClass: organizationalPerson/) {
            print OUT $_;
            print OUT "objectClass: inetOrgPerson\n";
            next;
          }
          if ($_ =~ /^structuralObjectClass: organizationalPerson/) {
            $_ =~ s/organizationalPerson/inetOrgPerson/;
          }
          print OUT $_;
        }
        close(IN);
        close(OUT);
        main::progress("done.\n");
        my $infile;
        my $outfile;
        main::progress("Upgrading LDAP configuration database...");
        if (-d '/opt/zmail/data/ldap/config/cn=config/olcDatabase={2}hdb') {
          `mv /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{2\}hdb /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{2\}mdb`;
        }
        if (-d '/opt/zmail/data/ldap/config/cn=config/olcDatabase={3}hdb') {
          `mv /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{3\}hdb /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{3\}mdb`;
          $infile=glob("/opt/zmail/data/ldap/config/cn=config/olcDatabase=\\{3\\}mdb/olcOverlay=\\{*\\}syncprov.ldif");
          $outfile="/tmp/3syncprov.ldif.$$";
          open(IN,"<$infile");
          open(OUT,">$outfile");
          while(<IN>) {
            if ($_ =~ /olcSpSessionlog:/) {
              next;
            }
            print OUT $_;
          }
          close(OUT);
          close(IN);
          `mv $outfile $infile`;
        }
        if (-f '/opt/zmail/data/ldap/config/cn=config/cn=module{0}.ldif') {
          $infile="/opt/zmail/data/ldap/config/cn\=config/cn\=module\{0\}.ldif";
          $outfile="/tmp/mod0.ldif.$$";
          open(IN,"<$infile");
          open(OUT,">$outfile");
          while(<IN>) {
            if ($_ =~ /^olcModuleLoad: \{0\}back_hdb.la/) {
              print OUT "olcModuleLoad: {0}back_mdb.la\n";
              next;
            }
            print OUT $_;
          }
          close(OUT);
          close(IN);
          `mv $outfile $infile`;
        }
        if (-f '/opt/zmail/data/ldap/config/cn=config.ldif') {
          $infile="/opt/zmail/data/ldap/config/cn\=config.ldif";
          $outfile="/tmp/config.ldif.$$";
          open(IN,"<$infile");
          open(OUT,">$outfile");
          while(<IN>) {
            if ($_ =~ /^olcToolThreads: /) {
              print OUT "olcToolThreads: 2\n";
              next;
            }
            print OUT $_;
          }
          close(OUT);
          close(IN);
          `mv $outfile $infile`;
        }
        if (-f '/opt/zmail/data/ldap/config/cn=config/olcDatabase={3}hdb.ldif') {
          `mv /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{3\}hdb.ldif /opt/zmail/data/ldap/config/cn\=config/olcDatabase=\{3\}mdb.ldif`;
          $infile="/opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{3\}mdb.ldif";
          $outfile="/tmp/3mdb.ldif.$$";
          open(IN,"<$infile");
          open(OUT,">$outfile");
          while(<IN>) {
            if ($_ =~ /^dn: olcDatabase=\{3\}hdb/) {
              print OUT "dn: olcDatabase={3}mdb\n";
              next;
            }
            if ($_ =~ /^objectClass: olcHdbConfig/) {
              print OUT "objectClass: olcMdbConfig\n";
              next;
            }
            if ($_ =~ /^olcDatabase: \{3\}hdb/) {
              print OUT "olcDatabase: {3}mdb\n";
              next;
            }
            if ($_ =~ /^olcDbDirectory: \/opt\/zmail\/data\/ldap\/hdb\/db/) {
              print OUT "olcDbDirectory: /opt/zmail/data/ldap/mdb/db\n";
              next;
            }
            if ($_ =~ /^structuralObjectClass: olcHdbConfig/) {
              print OUT "structuralObjectClass: olcMdbConfig\n";
              next;
            }
            if ($_ =~ /^olcDbMode:/) {
              print OUT $_;
              print OUT "olcDbMaxsize: 85899345920\n";
              next;
            }
            if ($_ =~ /^olcDbCheckpoint:/) {
              print OUT "olcDbCheckpoint: 0 0\n";
              print OUT "olcDbEnvFlags: writemap\n";
              print OUT "olcDbEnvFlags: nometasync\n";
              next;
            }
            if ($_ =~ /olcDbNoSync:/) {
              print OUT "olcDbNoSync: TRUE\n";
              next;
            }
            if ($_ =~ /olcDbCacheSize:/) {
              next;
            }
            if ($_ =~ /^olcDbConfig:/) {
              next;
            }
            if ($_ =~ /^olcDbDirtyRead:/) {
              next;
            }
            if ($_ =~ /^olcDbIDLcacheSize:/) {
              next;
            }
            if ($_ =~ /^olcDbLinearIndex:/) {
              next;
            }
            if ($_ =~ /^olcDbShmKey:/) {
              next;
            }
            if ($_ =~ /^olcDbCacheFree:/) {
              next;
            }
            if ($_ =~ /^olcDbDNcacheSize:/) {
              next;
            }
            print OUT $_;
          }
          close(OUT);
          close(IN);
          `mv $outfile $infile`;
        }
        if (-f '/opt/zmail/data/ldap/config/cn=config/olcDatabase={2}hdb.ldif') {
          `mv /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{2\}hdb.ldif /opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{2\}mdb.ldif`;
          $infile="/opt/zmail/data/ldap/config/cn\=config/olcDatabase\=\{2\}mdb.ldif";
          $outfile="/tmp/2mdb.ldif.$$";
          open(IN,"<$infile");
          open(OUT,">$outfile");
          while(<IN>) {
            if ($_ =~ /^dn: olcDatabase=\{2\}hdb/) {
              print OUT "dn: olcDatabase={2}mdb\n";
              next;
            }
            if ($_ =~ /^objectClass: olcHdbConfig/) {
              print OUT "objectClass: olcMdbConfig\n";
              next;
            }
            if ($_ =~ /^olcDatabase: \{2\}hdb/) {
              print OUT "olcDatabase: {2}mdb\n";
              next;
            }
            if ($_ =~ /^olcDbDirectory: \/opt\/zmail\/data\/ldap\/hdb\/db/) {
              print OUT "olcDbDirectory: /opt/zmail/data/ldap/mdb/db\n";
              next;
            }
            if ($_ =~ /^structuralObjectClass: olcHdbConfig/) {
              print OUT "structuralObjectClass: olcMdbConfig\n";
              next;
            }
            if ($_ =~ /^olcDbMode:/) {
              print OUT $_;
              print OUT "olcDbMaxsize: 85899345920\n";
              next;
            }
            if ($_ =~ /^olcDbCheckpoint:/) {
              print OUT "olcDbCheckpoint: 0 0\n";
              print OUT "olcDbEnvFlags: writemap\n";
              print OUT "olcDbEnvFlags: nometasync\n";
              next;
            }
            if ($_ =~ /olcDbNoSync:/) {
              print OUT "olcDbNoSync: TRUE\n";
              next;
            }
            if ($_ =~ /olcDbCacheSize:/) {
              next;
            }
            if ($_ =~ /^olcDbConfig:/) {
              next;
            }
            if ($_ =~ /^olcDbDirtyRead:/) {
              next;
            }
            if ($_ =~ /^olcDbIDLcacheSize:/) {
              next;
            }
            if ($_ =~ /^olcDbLinearIndex:/) {
              next;
            }
            if ($_ =~ /^olcDbShmKey:/) {
              next;
            }
            if ($_ =~ /^olcDbCacheFree:/) {
              next;
            }
            if ($_ =~ /^olcDbDNcacheSize:/) {
              next;
            }
            print OUT $_;
          }
          close(OUT);
          close(IN);
          `mv $outfile $infile`;
        }
        main::progress("done.\n");

        if (-d "/opt/zmail/data/ldap/accesslog") { 
          main::progress("Creating new accesslog DB..."); 
          if (-d "/opt/zmail/data/ldap/accesslog.prev") {
            `mv /opt/zmail/data/ldap/accesslog.prev /opt/zmail/data/ldap/accesslog.prev.$$`;
          }
          `mv /opt/zmail/data/ldap/accesslog /opt/zmail/data/ldap/accesslog.prev`;
          `mkdir -p /opt/zmail/data/ldap/accesslog/db`;
          `chown -R zmail:zmail /opt/zmail/data/ldap`;
          main::progress("done.\n");
        }

        main::progress("Loading database..."); 
        if (-d "/opt/zmail/data/ldap/mdb.prev") {
          `mv /opt/zmail/data/ldap/mdb.prev /opt/zmail/data/ldap/mdb.prev.$$`;
        }
        `mv /opt/zmail/data/ldap/mdb /opt/zmail/data/ldap/mdb.prev`;
        `mkdir -p /opt/zmail/data/ldap/mdb/db`;
        `chown -R zmail:zmail /opt/zmail/data/ldap`;
        my $rc;
        $rc=main::runAsZmail("/opt/zmail/libexec/zmslapadd $slapoutfile");
        if ($rc != 0) {
          main::progress("slapadd import failed.\n");
          return 1;
        }
	chmod 0640, $ldifFile;
        main::progress("done.\n");
      } else {
        if (! -f $ldifFile) {
          main::progress("Error: Unable to find /opt/zmail/data/ldap/ldap.bak\n");
        } else {
          main::progress("Error: /opt/zmail/data/ldap/ldap.bak is empty\n");
        }
        return 1;
      }
      main::configLog("LdapUpgraded$upgradeVersion");
    }
    if (startLdap()) {return 1;} 
  }
  return 0;
}

sub migrateLdap($) {
  my ($migrateVersion) = @_;
  if (main::isInstalled ("zmail-ldap")) {
    if($main::migratedStatus{"LdapUpgraded$migrateVersion"} ne "CONFIGURED") {
      if (-f "/opt/zmail/data/ldap/ldap.bak") {
        my $infile = "/opt/zmail/data/ldap/ldap.bak";
        my $outfile = "/opt/zmail/data/ldap/ldap.80";
        if ( -s $infile ) {
          open(IN,"<$infile");
          open(OUT,">$outfile");
          while(<IN>) {
            if ($_ =~ /^zmailChildAccount:/) {next;}
            if ($_ =~ /^zmailChildVisibleAccount:/) {next;}
            if ($_ =~ /^zmailPrefChildVisibleAccount:/) {next;}
            if ($_ =~ /^zmailPrefStandardClientAccessilbityMode:/) {next;}
            if ($_ =~ /^objectClass: zmailHsmGlobalConfig/) {next;}
            if ($_ =~ /^objectClass: zmailHsmServer/) {next;}
            if ($_ =~ /^objectClass: organizationalPerson/) {
              print OUT $_;
              print OUT "objectClass: inetOrgPerson\n";
              next;
            }
            if ($_ =~ /^structuralObjectClass: organizationalPerson/) {
              $_ =~ s/organizationalPerson/inetOrgPerson/;
            }
            print OUT $_;
          }
          close(IN);
          close(OUT);
        } else {
          main::progress("LDAP backup file /opt/zmail/data/ldap/ldap.bak is empty.\n");
          main::progress("Valid LDAP backup file not found, exiting.\n");
          return 1;
        }
        chmod 0644, $outfile if ( -s $outfile );

        main::installLdapConfig();

        main::progress("Migrating ldap data...");
        if (-d "/opt/zmail/data/ldap/mdb.prev") {
          `mv /opt/zmail/data/ldap/mdb.prev /opt/zmail/data/ldap/mdb.prev.$$`;
        }

        `mv /opt/zmail/data/ldap/mdb /opt/zmail/data/ldap/mdb.prev`;
        `mkdir -p /opt/zmail/data/ldap/mdb/db`;
        `chown -R zmail:zmail /opt/zmail/data/ldap`;
        my $rc;
        $rc=main::runAsZmail("/opt/zmail/libexec/zmslapadd $outfile");
        if ($rc != 0) {
          main::progress("slapadd import failed.\n");
          return 1;
        }
        chmod 0640, "/opt/zmail/data/ldap/ldap.bak";
        main::progress("done.\n");
      } else {
        stopLdap();
        main::progress("Running slapindex...");
        my $rc = main::runAsZmail("/opt/zmail/libexec/zmslapindex");
        main::progress(($rc == 0) ? "done.\n" : "failed.\n");
      }
      main::configLog("LdapUpgraded$migrateVersion");
    }
    if (startLdap()) {return 1;} 
  }
  return 0;
}

# DeleteLdapTree
# Requires Net::LDAP ref and DN
# Returns Net::LDAP::Search ref
sub DeleteLdapTree {
  my ($handle, $dn) = @_;
    
  # make sure it exists and get all the entries
  my $result = $handle->search( base => $dn, scope => 'one', filter => "(objectclass=*)");
  return $result if ($result->code());

  # loop through the entries and recursively delete them
  foreach my $entry($result->all_entries) {
    my $ref = DeleteLdapTree($handle, $entry->dn());
    return $ref if ($ref->code());
  }

  $result = $handle->delete($dn);
  return $result;
}

sub migrateAmavisDB($) {
  my ($toVersion) = @_;
  my $amavisdBase = "/opt/zmail/amavisd-new";
  my $toDir = "${amavisdBase}-$toVersion";
  main::progress("Migrating amavisd-new to version $toVersion\n");
  foreach my $fromVersion (qw(2.5.2 2.4.3 2.4.1 2.3.3 2.3.1)) {
    next if ($toVersion eq $fromVersion);
    my $fromDir = "${amavisdBase}-$fromVersion";
    main::progress("Checking $fromDir/db\n");
    if ( -d "$fromDir/db" && -d "$toDir" && ! -e "$toDir/db/cache.db") {
      main::progress("Migrating amavis-new db from version $fromVersion to $toVersion\n");
      `rm -rf $toDir/db > /dev/null 2>&1`;
      `mv $fromDir/db $toDir/db`;
      `chown zmail:zmail $toDir/db`; 
    }
    main::progress("Checking $fromDir/.spamassassin\n");
    if (-d "$fromDir/.spamassassin/" && -d "$toDir" && ! -e "$toDir/.spamassassin/bayes_toks" ) {
      main::progress("Migrating amavis-new .spamassassin from version $fromVersion to $toVersion\n");
      `rm -rf $toDir/.spamassassin > /dev/null 2>&1`;
      `mv $fromDir/.spamassassin $toDir/.spamassassin`;
      `chown zmail:zmail $toDir/.spamassassin`; 
    }
  }
}

sub relocateAmavisDB() {
  my $toDir = "/opt/zmail/data/amavisd";
  my $fromDir = "/opt/zmail/amavisd-new-2.5.2";
  main::progress("Migrating Amavis database directory\n");
  if ( -d "$fromDir/db" && -d "$toDir" && ! -e "$toDir/db/cache.db") {
    `rm -rf $toDir/db > /dev/null 2>&1`;
    `mv $fromDir/db $toDir/db`;
    `chown zmail:zmail $toDir/db`; 
  } 
  if (-d "$fromDir/.spamassassin/" && -d "$toDir" && ! -e "$toDir/.spamassassain/bayes_toks" ) {
    `rm -rf $toDir/.spamassassin > /dev/null 2>&1`;
    `mv $fromDir/.spamassassin $toDir/.spamassassin`;
    `chown zmail:zmail $toDir/.spamassassin`; 
  }
}

sub verifyDatabaseIntegrity {
  if (-x "/opt/zmail/libexec/zmdbintegrityreport") {
    main::progress("Verifying integrity of databases.\n");
    main::runAsZmail("/opt/zmail/libexec/zmdbintegrityreport -v -r");
  }
  return;
}

sub upgradeAllGlobalAdminAccounts {

  my @admins = `$su "$ZMPROV gaaa"`;
  main::detail("Upgrading ACLs for all admin accounts.\n");
  my @adminUpgrades;
  foreach my $admin (@admins) {
    chomp $admin;
    my $val = main::getLdapAccountValue("zmailIsAdminAccount",$admin);
    if (lc($val) eq "true") {
      push(@adminUpgrades,$admin);
      next;
    }
  }
  main::progress("Upgrading global admin accounts...");
  my $wfh= new FileHandle;
  my $efh= new FileHandle;
  my @errors;
  main::detail("Executing $su $ZMPROV");
  if (my $pid = open3($wfh,undef,$efh,"$su \"$ZMPROV\"")) {
    foreach my $admin (@adminUpgrades) {
      main::detail("$ZMPROV ma $admin zmailAdminConsoleUIComponents cartBlancheUI");
      print $wfh "ma $admin zmailAdminConsoleUIComponents cartBlancheUI\n";
    }
    print $wfh "exit\n";
    @errors = <$efh>;
    main::detail("@errors") if (scalar(@errors) != 0) ;
    close($wfh);
    close($efh);
    waitpid $pid, 0;
  }
  main::progress(($? == 0 && scalar(@errors) == 0) ? "done.\n" : "failed.\n");
}

sub upgradeLdapConfigValue($$$) {
  my ($key,$new_value,$cmp_value) = @_;
  my $current_value = main::getLdapConfigValue($key);
  if ($new_value eq "") {
      $new_value="\'\'";
  }
  main::setLdapGlobalConfig($key, $new_value)
    if ($current_value eq $cmp_value);
}

sub addLdapIndex($$$) {
  my ($index, $type) = @_;
  my $ldap_pass = `$su "zmlocalconfig -s -m nokey ldap_root_password"`;
  chomp($ldap_pass);
  my $ldap;
  unless($ldap = Net::LDAP->new('ldapi://%2fopt%2fzmail%2fopenldap%2fvar%2frun%2fldapi/')) {
    main::progress("Unable to contact to ldapi: $!\n");
  }
  my $result = $ldap->bind("cn=config", password => $ldap_pass);
  my $dn="olcDatabase={2}mdb,cn=config";
  if ($isLdapMaster) {
    $result = $ldap->search(
                      base=> "cn=accesslog",
                      filter=>"(objectClass=*)",
                      scope => "base",
                      attrs => ['1.1'],
    );
    my $size = $result->count;
    if ($size > 0 ) {
      $dn="olcDatabase={3}mdb,cn=config";
    }
  }
  $result = $ldap->search(
    base=> "$dn",
    filter=>"(objectClass=*)",
    scope => "base",
    attrs => ['olcDbIndex'],
  );
  my $entry=$result->entry($result->count-1);
  my @attrvals=$entry->get_value("olcDbIndex");
  my $hasIndex=0;

  foreach my $attr (@attrvals) {
    if ($attr =~ /$index/) {
      $hasIndex=1;
    }
  }
  if (!$hasIndex) {
    $result = $ldap->modify(
        $dn,
        add =>{olcDbIndex=>"$index $type"},
    );
  }
  $ldap->unbind;
  return !$hasIndex;
}

sub upgradeLocalConfigValue($$$) {
  my ($key,$new_value,$cmp_value) = @_;
  my $current_value = main::getLocalConfig($key);
  main::setLocalConfig("$key", "$new_value")
    if ($current_value eq $cmp_value);
}

sub runAttributeUpgrade($) {
  my ($startVersion) = @_;
  my $rc = main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b 27075 -v $startVersion");
  return $rc;
}

sub runLdapAttributeUpgrade($) {
  my ($bug) = @_;
  return if ($bug eq "");
  my $rc = main::runAsZmail("zmjava org.zmail.cs.account.ldap.upgrade.LdapUpgrade -b $bug -v");
  return $rc;
}
    

1
