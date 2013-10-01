#!/usr/bin/perl
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

#############

my $MYSQL = "mysql";
my $ROOT_USER = "root";
my $ROOT_PASSWORD = "liquid";
my $LIQUID_USER = "liquid";
my $LIQUID_PASSWORD = "liquid";
my $PASSWORD = "liquid";
my $DATABASE = "liquid";

#############

my @mailboxIds = runSql($LIQUID_USER,
			$LIQUID_PASSWORD,
			"SELECT id FROM mailbox ORDER BY id");

printLog("Found " . scalar(@mailboxIds) . " mailbox databases.");
my $unreadColumn = "unread";

my $id;
foreach $id (@mailboxIds) {
    addUnreadColumn($id);
    updateUnread($id);
}

exit(0);

#############


sub addUnreadColumn($)
{
    my ($mailboxId) = @_;
    my $dbName = "mailbox" . $mailboxId;
	
    my $sql = <<ADD_UNREAD_COLUMN_EOF;

ALTER TABLE $dbName.mail_item
ADD COLUMN $unreadColumn BOOLEAN NULL,
ADD INDEX i_$unreadColumn ($unreadColumn);

ADD_UNREAD_COLUMN_EOF

    printLog("Adding $unreadColumn column to $dbName.mail_item.");
    runSql($ROOT_USER, $ROOT_PASSWORD, $sql);
}

sub updateUnread($)
{
    my ($mailboxId) = @_;
    my $dbName = "mailbox" . $mailboxId;
    my $sql = <<UPDATE_UNREAD_EOF;

UPDATE $dbName.mail_item
SET $unreadColumn = (flags & 16) >> 4
WHERE type IN (5, 7);

UPDATE $dbName.mail_item
SET flags = flags & ~16;

UPDATE_UNREAD_EOF

    printLog("Updating flags and $unreadColumn columns in $dbName.mail_item.");
    runSql($ROOT_USER, $ROOT_PASSWORD, $sql);
}	

sub runSql($$$)
{
    my ($user, $password, $script) = @_;

    # Write the last script to a text file for debugging
    # open(LASTSCRIPT, ">lastScript.sql") || die "Could not open lastScript.sql";
    # print(LASTSCRIPT $script);
    # close(LASTSCRIPT);

    # Run the mysql command and redirect output to a temp file
    my $tempFile = "mysql.out";
    my $command = "$MYSQL --user=$user --password=$password " .
        "--database=$DATABASE --batch --skip-column-names";
    open(MYSQL, "| $command > $tempFile") || die "Unable to run $command";
    print(MYSQL $script);
    close(MYSQL);

    if ($? != 0) {
        die "Error while running '$command'.";
    }

    # Process output
    open(OUTPUT, $tempFile) || die "Could not open $tempFile";
    my @output;
    while (<OUTPUT>) {
        s/\s+$//;
        push(@output, $_);
    }

    return @output;
}

sub printLog
{
    print scalar(localtime()), ": ", @_, "\n";
}
