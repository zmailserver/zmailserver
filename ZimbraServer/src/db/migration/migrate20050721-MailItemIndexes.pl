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

#############

my $MYSQL = "mysql";
my $DB_USER = "liquid";
my $DB_PASSWORD = "liquid";
my $DATABASE = "liquid";

if (-f "/opt/liquid/bin/lqlocalconfig") {
    $DB_PASSWORD = `lqlocalconfig -s -m nokey liquid_mysql_password`;
    chomp $DB_PASSWORD;
    $DB_USER = `lqlocalconfig -m nokey liquid_mysql_user`;
    chomp $DB_USER;
}

#############

my @mailboxIds = runSql("SELECT id FROM mailbox ORDER BY id");

printLog("Found " . scalar(@mailboxIds) . " mailbox databases.");

updateSchemaVersion(9);

my $id;
foreach $id (@mailboxIds) {
    updateIndexes($id);
}

exit(0);

#############

sub updateSchemaVersion($)
{
    my ($dbVersion) = @_;
    if (!defined($dbVersion)) {
	print("dbVersion not specified.\n");
	exit(1);
    }

    my $sql = <<SET_SCHEMA_VERSION_EOF;

UPDATE $DATABASE.config SET value = '$dbVersion' WHERE name = 'db.version';

SET_SCHEMA_VERSION_EOF

    printLog("Updating DB schema version to $dbVersion.");
    runSql($sql);
}

sub updateIndexes()
{
    my ($mailboxId) = @_;
    my $dbName = "mailbox" . $mailboxId;
    my $sql = <<UPDATE_FOLDER_INDEX_EOF;

ALTER TABLE $dbName.mail_item
DROP INDEX i_folder_id,
DROP INDEX i_sender,
DROP INDEX i_subject,
ADD INDEX i_folder_id_date (folder_id, date),
ADD INDEX i_mod_metadata (mod_metadata);

UPDATE_FOLDER_INDEX_EOF

    printLog("Updating indexes on $dbName.mail_item.");
    runSql($sql);
}

#############

sub runSql($$$)
{
    my ($script) = @_;

    # Write the last script to a text file for debugging
    # open(LASTSCRIPT, ">lastScript.sql") || die "Could not open lastScript.sql";
    # print(LASTSCRIPT $script);
    # close(LASTSCRIPT);

    # Run the mysql command and redirect output to a temp file
    my $tempFile = "mysql.out";
    my $command = "$MYSQL --user=$DB_USER --password=$DB_PASSWORD " .
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

    unlink($tempFile);
    return @output;
}

sub printLog
{
    print scalar(localtime()), ": ", @_, "\n";
}
