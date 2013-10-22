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

# specific to this app
my ($id, $perm, $granteeType, $granteeName, $inh);

#standard options
my ($user, $pw, $host, $help); #standard
GetOptions("u|user=s" => \$user,
           "pw=s" => \$pw,
           "h|host=s" => \$host,
           "help|?" => \$help,
           # add specific params below:
           "i|id=s" => \$id,
           "r|perm=s" => \$perm,
           "g|gt=s" => \$granteeType,
           "gn|name=s" => \$granteeName,
           "inh=s" => \$inh,
          );

if (!defined($user) || !defined($id) || !defined($perm) || !defined($granteeType) || !defined($granteeName) || !defined($inh) || defined($help)) {
    my $usage = <<END_OF_USAGE;
    
USAGE: $0 -u USER -i ID -r (rwidax) -g (usr|grp|dom|cos|all)
END_OF_USAGE
    die $usage;
}

my $z = ZmailSoapTest->new($user, $host, $pw);
$z->doStdAuth();

my $d = new XmlDoc;
$d->start('FolderActionRequest', $Soap::ZIMBRA_MAIL_NS);
{
  $d->start('action', undef, { 'id' => $id,
                               'op' => "grant"});

  {
    $d->add("grant", undef, { 'perm' => $perm,
                              'gt' => $granteeType,
                              'd' => $granteeName,
                              'inh' => $inh
                            });
  } $d->end(); # action
}

$d->end(); # 'FolderActionRequest'

my $response = $z->invokeMail($d->root());

print "REQUEST:\n-------------\n".$z->to_string_simple($d);
print "RESPONSE:\n--------------\n".$z->to_string_simple($response);
 
