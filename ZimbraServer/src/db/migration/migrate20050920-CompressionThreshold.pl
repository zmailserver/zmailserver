#!/usr/bin/perl
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2005, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
use Migrate;

Migrate::verifySchemaVersion(19);

alterVolume();

Migrate::updateSchemaVersion(19, 20);

exit(0);

#####################

sub alterVolume() {
    my $sql = "ALTER TABLE volume" .
	" ADD compression_threshold BIGINT NOT NULL;";
    Migrate::runSql($sql);

    Migrate::runSql("UPDATE volume SET compression_threshold = 4096;");
    Migrate::runSql("DELETE FROM config WHERE name = 'store.compressBlobs'");
}
