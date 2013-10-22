#!/usr/bin/perl -w
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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

#standard options
my ($sessionId, $authToken, $notSeq, $user, $pw, $host, $help, $verbose);  #standard
my ($wait, $one);

GetOptions("u|user=s" => \$user,
           "pw=s" => \$pw,
           "h|host=s" => \$host,
           "help|?" => \$help,
           "w" => \$wait,
           "v" => \$verbose,
           "sessionId=s" => \$sessionId,
           "at=s" => \$authToken,
           "ns=s" => \$notSeq,
           "one" => \$one,
          );

if (!defined($user)) {
  my $usage = <<END_OF_USAGE;
    
USAGE: $0 -u USER [-w] [-v] [-ns notseq] [-at authToken] [-one]
END_OF_USAGE
  die $usage;
}

my %soapargs;
$soapargs{ 'NOTIFY'} = 1;

if (defined($sessionId)) {
  $soapargs{'SESSIONID'} = $sessionId;
}
if (defined($notSeq)) {
  $soapargs{'NOTSEQ'} = $notSeq;
}

my $z = ZmailSoapTest->new($user, $host, $pw, \%soapargs);
$z->verbose(3);

if (defined($sessionId) && defined($authToken)) {
  $z->setAuthContext($authToken, $sessionId, \%soapargs);
} else {
  print "AUTH REQUEST:\n--------------------";
  $z->doStdAuth();
}

my $d = new XmlDoc;

my %args = ( );

if (defined($wait)) {
  $args{'wait'} = '1';
}

if (defined($one)) {
  $args{'limitToOneBlocked'} = 1;
}

$d->add('NoOpRequest', $Soap::ZIMBRA_MAIL_NS, \%args);

print "\n\nNOP:\n--------------------";
my $response = $z->invokeMail($d->root());

#print "REQUEST:\n-------------\n".$z->to_string_simple($d);
#print "RESPONSE:\n--------------\n".$z->to_string_simple($response);

