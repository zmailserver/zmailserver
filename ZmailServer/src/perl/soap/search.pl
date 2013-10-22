#!/usr/bin/perl -w
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
my ($searchString, $offset, $prevId, $prevSortVal, $endSortVal, $limit, $fetch, $sortBy, $types, $convId, $tz, $locale, $field);
my ($calExpandInstStart, $calExpandInstEnd, $allowableTaskStatus);
$offset = 0;
$limit = 5;
$fetch = 0;
$sortBy = "dateDesc";
$types = "message";

#standard options
my ($admin, $user, $pw, $host, $help, $adminHost); #standard
GetOptions("u|user=s" => \$user,
           "admin" => \$admin,
           "ah|adminHost=s" => \$adminHost,
           "pw=s" => \$pw,
           "h|host=s" => \$host,
           "help|?" => \$help,
           # add specific params below:
           "t|types=s" => \$types,
           "conv=s" => \$convId,
           "query=s" => \$searchString,
           "sort=s" => \$sortBy,
           "offset=i" => \$offset,
           "limit=i" => \$limit,
           "fetch=s" => \$fetch,
           "pi=s" => \$prevId,
           "ps=s" => \$prevSortVal,
           "es=s" => \$endSortVal,
           "tz=s" => \$tz,
           "locale=s" => \$locale,
           "field=s" => \$field,
           "calExpandInstStart=s" => \$calExpandInstStart,
           "calExpandInstEnd=s" => \$calExpandInstEnd,
           "allowableTaskStatus=s" => \$allowableTaskStatus,
          );



if (!defined($user) || !defined($searchString) || defined($help)) {
    my $usage = <<END_OF_USAGE;
    
USAGE: $0 -u USER -q QUERYSTR [-s SORT] [-t TYPES] [-o OFFSET] [-l LIMIT] [-fetch FETCH] [-pi PREV-ITEM-ID -ps PREV-SORT-VALUE] [-es END-SORT-VALUE] [-conv CONVID] [-tz TZID] [-calExpandInstStart STARTTIME -calExpandInstEnd ENDTIME] [-locale LOCALE_STR] [-allowableTaskStatus=INPR,NEED,DEFERRED,WAITING,COMP]
    SORT = dateDesc|dateAsc|subjDesc|subjAsc|nameDesc|nameAsc|score|none
    TYPES = message|conversation|contact|appointment
END_OF_USAGE
    die $usage;
}

if (defined($adminHost)) {
  $admin = 1;
}

my $z = ZmailSoapTest->new($user, $host, $pw, undef, $adminHost);
if (defined($admin)) {
  $z->doAdminAuth();
} else {
  $z->doStdAuth();
}

my $d = new XmlDoc;
my $searchName = "SearchRequest";

my %args =  ( 'types' => $types,
              'sortBy' => $sortBy,
              'offset' => $offset,
              'limit' => $limit,
              'fetch' => $fetch
            );

if (defined($calExpandInstStart)) {
  $args{'calExpandInstStart'} = $calExpandInstStart;
  $args{'calExpandInstEnd'} = $calExpandInstEnd;
}

if (defined($allowableTaskStatus)) {
  $args{'allowableTaskStatus'} = $allowableTaskStatus;
}

if (defined($convId)) {
  $searchName = "SearchConvRequest";
  $args{'cid'} = $convId;
}

if (defined($field)) {
  $args{'field'} = $field;
}
 
$d->start($searchName, $Soap::ZIMBRA_MAIL_NS, \%args);
{
    if (defined $prevId) {
      if (defined $endSortVal) {
        $d->add("cursor", undef, { "id" => $prevId, "sortVal" => $prevSortVal, "endSortVal" => $endSortVal });
      } else {
        $d->add("cursor", undef, { "id" => $prevId, "sortVal" => $prevSortVal });
      }
    }

    $d->add('query', undef, undef, $searchString);

    if (defined $tz) {
      $d->add('tz', undef, {"id" => $tz });
    }

    if (defined $locale) {
      $d->add('locale', undef, undef, $locale);
    }
    
} $d->end(); # 'SearchRequest'

my $response = $z->invokeMail($d->root());

print "REQUEST:\n-------------\n".$z->to_string_simple($d);
print "RESPONSE:\n--------------\n".$z->to_string_simple($response);

