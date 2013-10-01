#!/usr/bin/perl
# 
# ***** BEGIN LICENSE BLOCK *****
# 
# Zimbra Collaboration Suite Server
# Copyright (C) 2008 Zimbra, Inc.
# 
# The contents of this file are subject to the Yahoo! Public License
# Version 1.0 ("License"); you may not use this file except in
# compliance with the License.  You may obtain a copy of the License at
# http://www.zimbra.com/license.
# 
# Software distributed under the License is distributed on an "AS IS"
# basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
# 
# ***** END LICENSE BLOCK *****
# 

use strict;
use lib "./zimbramon/lib";

my @zmodules =qw(Archive::Tar Archive::Zip Array::Compare BerkeleyDB Bit::Vector Cache::FastMmap Carp Carp::Clan Class::Inspector Class::Load Class::Load::XS CPAN::Meta::Requirements Compress::Raw::Bzip2 Compress::Raw::Zlib Compress::Zlib Config::IniFiles Config::Properties Convert::ASN1 Convert::BinHex Convert::TNEF Convert::UUlib Crypt::OpenSSL::Random Crypt::OpenSSL::RSA Crypt::SaltedHash Crypt::SSLeay DB_File DBD::mysql DBD::SQLite DBI Data::UUID Date::Calc Date::Format Date::Manip Date::Parse Devel::GlobalDestruction Devel::Symdump Device::SerialPort Digest::HMAC Digest::HMAC_MD5 Digest::SHA Digest::SHA1 Dist::CheckConflicts Error Eval::Closure ExtUtils::Constant File::Grep File::Tail File::Temp Filesys::Df Geography::Countries Getopt::Easy HTML::Parser HTML::Tagset HTTP::Parser HTTP::Request IO IO::Compress::Base IO::Compress::Gzip IO::File IO::Handle IO::HTML IO::Select IO::Socket IO::Socket::INET IO::Socket::INET6 IO::Socket::IP IO::Socket::SSL IO::Socket::UNIX IO::Stringy IO::Uncompress::Gunzip IO::Wrap IO::Zlib IP::Country List::Compare List::MoreUtils LWP::UserAgent MIME::Entity MIME::Lite MIME::Parser MIME::Types MIME::Words Mail::DKIM Mail::Mailer Mail::SpamAssassin Mail::SpamAssassin::ArchiveIterator Mail::SpamAssassin::Message Mail::SpamAssassin::PerMsgLearner Mail::SpamAssassin::Util::Progress Mail::SPF Math::BigInt Module::Implementation Module::Metadata Module::Runtime MRO::Compat Net::CIDR Net::DNS::Resolver Net::DNS::Resolver::Programmable Net::HTTP Net::IP Net::LDAP Net::LDAP::Entry Net::LDAP::LDIF Net::LDAPapi Net::SMTP Net::SSLeay Net::Server Net::Telnet NetAddr::IP Package::Constants Package::Stash Parallel::ForkManager Params::Util Perl::OSType Pod::Coverage Pod::Escapes Pod::Simple Proc::ProcessTable Scalar::Util SOAP::Lite SOAP::Transport::HTTP Socket Socket::Linux Socket6 Storable Sub::Exporter Sub::Exporter::Progressive Sub::Name Sub::Uplevel Swatch::Actions Swatch::Throttle Task::Weaken Test::Deep Test::Exception Test::Fatal Test::Inter Test::NoWarnings Test::Pod Test::Pod::Coverage Test::Requires Test::Simple Test::Tester Test::Warn Time::HiRes Tree::DAG_Node Try::Tiny Unix::Syslog URI version XML::Parser XML::SAX::Base XML::SAX::Expat XML::Simple ZMQ::Constants ZMQ::LibZMQ3);

my @smodules =qw(AnyDBM_File Benchmark Carp Cwd Data::Dumper Digest::MD5 Encode English Errno Exporter Fcntl File::Basename File::Copy File::Find File::Path File::Spec FileHandle FindBin Getopt::Long Getopt::Std IO::Socket IPC::Open3 MIME::Base64 Math::BigFloat Net::Ping POSIX Pod::Usage Socket Sys::Hostname Term::ReadLine Time::Local bytes constant lib re sigtrap strict subs vars warnings);

my $exit_status=0;
my $pm;

foreach my $m (@zmodules) {
  load_module($m);
  if ($INC{$pm} !~ /zimbramon\/lib/ && $INC{$pm} ne "") {
    print "$INC{$pm}\n";
    $exit_status=1;
  }
}

foreach my $m (@smodules) {
  load_module($m);
}

exit $exit_status;

sub load_module($) {
  my ($m) = @_;
  my $mpath;
  local($_) = $m;
  $_ .= /^auto::/ ? '.al' : '.pm'  if !m{^/} && !m{\.(pm|pl|al|ix)\z};
  s{::}{/}g;
  eval { require $_; } 
  or do {
    my($eval_stat) = $@ ne '' ? $@ =~ /(\S+\s\S+\s\S+)/ : "errno=$!";  chomp $eval_stat;
    print "$m: $eval_stat\n";
    $exit_status=1;
  };
  $pm=$_;
}
