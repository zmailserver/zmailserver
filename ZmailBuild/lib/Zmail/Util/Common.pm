#!/usr/bin/perl
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

package Zmail::Util::Common; 
use strict;


# Zmail Specfic library locations
use lib "/opt/zmail/zmailmon/lib";
use lib "/opt/zmail/zmailmon/lib/Zmail/SOAP";
use lib "/opt/zmail/zmailmon/lib/Zmail/Mon";
use lib "/opt/zmail/zmailmon/lib/Zmail/DB";
foreach my $arch (qw(i386 x86_64 i486 i586 i686 darwin)) {
  foreach my $type (qw(linux-thread-multi linux-gnu-thread-multi linux thread-multi thread-multi-2level)) {
    my $dir = "/opt/zmail/zmailmon/lib/${arch}-${type}";
    unshift(@INC, "$dir") 
      if (-d "$dir");
  }
}

1
