#!/usr/bin/perl -w
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2004, 2005, 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

use XmlElement;
use XmlDoc;
use Soap;

my $ACCTNS = "urn:zmailAccount";
my $MAILNS = "urn:zmailMail";

my $url = "http://localhost:7070/service/soap/";

my $user;
my $convId;
my $searchString;

if (defined $ARGV[2] && $ARGV[2] ne "") {
    $user = $ARGV[0];
    $convId = $ARGV[1];
    $searchString = $ARGV[2];
} else {
    die "Usage search USER CONVID QUERYSTR";
}

my $SOAP = $Soap::Soap12;
my $d = new XmlDoc;
$d->start('AuthRequest', $ACCTNS);
$d->add('account', undef, { by => "name"}, $user);
$d->add('password', undef, undef, "test123");
$d->end();

my $authResponse = $SOAP->invoke($url, $d->root());

print "AuthResponse = ".$authResponse->to_string("pretty")."\n";

my $authToken = $authResponse->find_child('authToken')->content;

print "authToken($authToken)\n";

my $context = $SOAP->zmailContext($authToken);

#
#<SearchRequest xmlns="urn:zmailMail">
# <query>tag:\unseen</query>
#</SearchRequest>

my %msgAttrs;
#$msgAttrs{'l'} = "/sent mail";
#$msgAttrs{'t'} = "\\unseen ,34 , \\FLAGGED";

$d = new XmlDoc;
my %queryAttrs;
$queryAttrs{'cid'} = $convId;
$queryAttrs{'sortby'} = "datedesc";
$queryAttrs{'groupBy'} = "none";
$queryAttrs{'fetch'} = "1";
$d->start('SearchConvRequest', $MAILNS, \%queryAttrs);

$d->start('query', undef, undef, $searchString);

$d->end(); # 'query'
$d->end(); # 'SearchRequest'

print "\nOUTGOING XML:\n-------------\n";
print $d->to_string("pretty")."\n";

my $response = $SOAP->invoke($url, $d->root(), $context);


print "\nRESPONSE:\n--------------\n";
my $str = $response->to_string("pretty")."\n";
$str =~ s/<\/?ns0\:/</g;
print $str;

