#!/usr/bin/perl
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
# Date2Time:
# Pass in an ascii date string and it prints out seconds-since-epoch
# and milliseconds-since-epoch
use Date::Parse;
use strict;

if ($ARGV[0] eq "") {
    print "USAGE: d2t DATE_STRING\n";
    exit(1);
}

my $argStr;
# there must be some extra-special easy perl way to do this...
my $i = 0;
do {
    $argStr = $argStr . $ARGV[$i] . " ";
    $i++;
} while($ARGV[$i] ne "");

my $val = str2time($argStr);
my $back = localtime($val);
my $msval = $val * 1000;
print "$val\n$msval\n$back\n";
