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

use Date::Parse;
use Time::HiRes qw ( time );
use strict;

use lib '.';

use LWP::UserAgent;
use Getopt::Long;
use ZmailSoapTest;
use XmlElement;
use XmlDoc;
use Soap;

#standard options
my ($user, $pw, $host, $help);  #standard

GetOptions("u|user=s" => \$user,
           "pw=s" => \$pw,
           "h|host=s" => \$host,
           "help|?" => \$help,
          );

if (!defined($user)) {
    print "USAGE: $0 -u USER\n";
    exit 1;
}

my $z = ZmailSoapTest->new($user, $host, $pw);
$z->doStdAuth();

my $d = new XmlDoc;
$d->start('IMListConferenceServicesRequest', $Soap::ZIMBRA_IM_NS);
$d->end(); #request

my $response = $z->invokeMail($d->root());

print "REQUEST:\n-------------\n".$z->to_string_simple($d);
print "RESPONSE:\n--------------\n".$z->to_string_simple($response);

