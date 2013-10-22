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

use strict;
use lib '.';

use LWP::UserAgent;
use Getopt::Long;
use XmlDoc;
use Soap;
use ZmailSoapTest;

# specific to this app
my ($threadId, $addr, $nickname, $pass);

#standard options
my ($user, $pw, $host, $help); #standard
GetOptions("u|user=s" => \$user,
           "pw=s" => \$pw,
           "h|host=s" => \$host,
           "help|?" => \$help,
           # add specific params below:
           "t=s", \$threadId,
           "a=s", \$addr,
           "n=s", \$nickname,
           "pass=s", \$pass,
          );



if (!defined($user) || !defined($addr) || defined($help)) {
    my $usage = <<END_OF_USAGE;
    
USAGE: $0 -u USER [-t threadId] -a addr [-n nickname] [-pass room_password]
END_OF_USAGE
    die $usage;
}

my $z = ZmailSoapTest->new($user, $host, $pw);
$z->doStdAuth();

my $d = new XmlDoc;
my $searchName = "SearchRequest";

my %args = ('addr' => $addr, 'thread' => $threadId);

if (defined $nickname) {
  $args{'nick'} = $nickname;
}

if (defined $pass) {
  $args{'password'} = $pass;
}

$d->start("IMJoinConferenceRoomRequest", $Soap::ZIMBRA_IM_NS, \%args);

$d->end(); 

my $response = $z->invokeMail($d->root());

print "REQUEST:\n-------------\n".$z->to_string_simple($d);
print "RESPONSE:\n--------------\n".$z->to_string_simple($response);

