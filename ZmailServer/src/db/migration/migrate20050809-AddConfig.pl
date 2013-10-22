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

addConfigColumn();
Migrate::updateSchemaVersion(13);

exit(0);

#####################

sub addConfigColumn() {
    my $sql = <<EOF;
ALTER TABLE liquid.mailbox
ADD COLUMN config TEXT AFTER tracking_sync;

EOF
    
    Migrate::log("Adding CONFIG column to liquid.mailbox.");
    Migrate::runSql($sql);
}
