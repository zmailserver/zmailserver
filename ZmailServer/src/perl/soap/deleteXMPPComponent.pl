#!/usr/bin/perl -w
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

#
# Simple SOAP test-harness for the AddMsg API
#

use strict;

use lib '.';

use LWP::UserAgent;
use Getopt::Long;
use XmlElement;
use XmlDoc;
use Soap;
use ZmailSoapTest;

#standard options
my ($admin, $user, $pw, $host, $help, $adminHost); #standard
my ($name);

GetOptions("u|user=s" => \$user,
           "admin" => \$admin,
           "ah|adminHost=s" => \$adminHost,
           "pw=s" => \$pw,
           "h|host=s" => \$host,
           "help|?" => \$help,
           "name=s" => \$name,
          );

if (!defined($user)) {
  $user = "admin";
}

if (!defined($user) || defined($help) || !defined($name) ) {
  my $usage = <<END_OF_USAGE;

USAGE: $0 -n NAME 
END_OF_USAGE
  die $usage;
}

my $z = ZmailSoapTest->new($user, $host, $pw, undef, $adminHost);
$z->doAdminAuth();

my $SOAP = $Soap::Soap12;

my $d = new XmlDoc;
$d->start('DeleteXMPPComponentRequest', $Soap::ZIMBRA_ADMIN_NS); {
  $d->add('xmppcomponent', undef, { "by" => "name"}, $name);
} $d->end();

my $response = $z->invokeAdmin($d->root());

print "REQUEST:\n-------------\n".$z->to_string_simple($d);
print "RESPONSE:\n--------------\n".$z->to_string_simple($response);

