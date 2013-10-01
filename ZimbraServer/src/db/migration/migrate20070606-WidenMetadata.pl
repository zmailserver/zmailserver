#!/usr/bin/perl
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
my $concurrent = 10;

my $sqlGroupsWithSmallMetadata = <<_SQL_;
SELECT table_schema FROM information_schema.columns
WHERE table_name = 'mail_item' AND column_name = 'metadata' AND data_type = 'text'
ORDER BY table_schema;
_SQL_
my @groups = Migrate::runSql($sqlGroupsWithSmallMetadata);

Migrate::verifySchemaVersion(37);

my @sql = ();
foreach my $group (@groups) {
    my $sql = <<_SQL_;
ALTER TABLE $group.mail_item MODIFY COLUMN metadata MEDIUMTEXT;
_SQL_
    push(@sql, $sql);
}
Migrate::runSqlParallel($concurrent, @sql);

Migrate::updateSchemaVersion(37, 38);

exit(0);
