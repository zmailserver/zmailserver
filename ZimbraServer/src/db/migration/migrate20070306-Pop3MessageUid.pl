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

Migrate::verifySchemaVersion(36);
foreach my $group (Migrate::getMailboxGroups()) {
    modifyPop3MessageSchema($group);
}
Migrate::updateSchemaVersion(36, 37);

exit(0);

#####################

sub modifyPop3MessageSchema($) {
  my ($group) = @_;

  my $sql = <<MODIFY_POP3_MESSAGE_SCHEMA_EOF;
ALTER TABLE $group.pop3_message
CHANGE uid uid VARCHAR(255) BINARY NOT NULL;
MODIFY_POP3_MESSAGE_SCHEMA_EOF

  Migrate::runSql($sql);
}
