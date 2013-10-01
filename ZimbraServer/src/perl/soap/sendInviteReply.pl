#!/usr/bin/perl -w
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2005, 2007, 2009, 2010, 2012 VMware, Inc.
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

use XmlElement;
use XmlDoc;
use Soap;

my $userId;
my $msgID = 0;

if (defined($ARGV[1]) && $ARGV[1] ne "") {
    $userId = $ARGV[0];
    $msgID = $ARGV[1];
} else {
    die "USAGE: sendInviteReply USER INVITE-MESSAGE-ID";
}

my $ACCTNS = "urn:zimbraAccount";
my $MAILNS = "urn:zimbraMail";

my $url = "http://localhost:7070/service/soap/";

my $SOAP = $Soap::Soap12;
my $d = new XmlDoc;
$d->start('AuthRequest', $ACCTNS);
$d->add('account', undef, { by => "name"}, $userId);
$d->add('password', undef, undef, "test123");
$d->end();

my $authResponse = $SOAP->invoke($url, $d->root());

print "AuthResponse = ".$authResponse->to_string("pretty")."\n";

my $authToken = $authResponse->find_child('authToken')->content;
print "authToken($authToken)\n";

my $sessionId = $authResponse->find_child('sessionId')->content;
print "sessionId = $sessionId\n";

my $context = $SOAP->zimbraContext($authToken, $sessionId);

my $contextStr = $context->to_string("pretty");
print("Context = $contextStr\n");

$d = new XmlDoc;
$d->start('SendInviteReplyRequest', $MAILNS,
          {
              'id' => $msgID,
              'compNum' => "0",
#              'verb' => "TENTATIVE"
                  'verb' => "DECLINE"
              }

          );

$d->start('exceptId', undef, { 'd' => "20051012T090000" });
$d->start('m', undef, { 'l' => "/INBOX" }, undef);

$d->add('e', undef,
        {
            'a' => "user1\@timbre.example.zimbra.com",
            't' => "t"
            } );

$d->add('su', undef, undef, "ACCEPTED: $userId");

$d->start('mp', undef, { 'ct' => "text/plain" });
$d->add('content', undef, undef, "$userId is TENTATIVE for your request!  Hmm.");
$d->end(); #mp (text/plain )

$d->end(); # m

$d->end(); # SendInviteReplyRequest

print "\nOUTGOING XML:\n-------------\n";
my $out =  $d->to_string("pretty")."\n";
$out =~ s/ns0\://g;
print $out."\n";

my $start = time;
my $firstStart = time;
my $response;

my $i = 0;
my $end;
my $avg;
my $elapsed;

$start = time;
$response = $SOAP->invoke($url, $d->root(), $context);
print "\nRESPONSE:\n--------------\n";
$out =  $response->to_string("pretty")."\n";
$out =~ s/ns0\://g;
print $out."\n";

