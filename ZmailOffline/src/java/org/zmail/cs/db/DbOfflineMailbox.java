/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zimbra.common.localconfig.DebugConfig;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Pair;
import com.zimbra.cs.db.DbPool.DbConnection;
import com.zimbra.cs.mailbox.ChangeTrackingMailbox;
import com.zimbra.cs.mailbox.Flag;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.util.TypedIdList;
import com.zimbra.cs.session.PendingModifications.Change;

public class DbOfflineMailbox {

    public static void renumberItem(MailItem item, int newId) throws ServiceException {
        if (Db.supports(Db.Capability.ON_UPDATE_CASCADE))
            renumberItemCascade(item, newId);
        else
            renumberItemManual(item, newId);
    }

    public static void renumberItemManual(MailItem item, int newId) throws ServiceException {
        Mailbox mbox = item.getMailbox();
        DbConnection conn = mbox.getOperationConnection();
        MailItem.Type type = item.getType();

        PreparedStatement stmt = null;
        try {
            // first, duplicate the original row with the new ID
            String table = DbMailItem.getMailItemTableName(mbox);
            String mailbox_id = DebugConfig.disableMailboxGroups ? "" : "mailbox_id, ";
            stmt = conn.prepareStatement("INSERT INTO " + table +
                    " (" + mailbox_id + "id, type, parent_id, folder_id, index_id, imap_id, date, size, locator, blob_digest," +
                    " unread, flags, tags, sender, subject, name, metadata, mod_metadata, change_date, mod_content, change_mask)" +
                    " SELECT " + mailbox_id + "?, type, parent_id, folder_id, index_id, imap_id, date, size, locator, blob_digest," +
                    " unread, flags, tags, sender, subject, name, metadata, mod_metadata, change_date, mod_content, change_mask" +
                    " FROM " + table + " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            int pos = 1;
            stmt.setInt(pos++, newId);
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());
            stmt.executeUpdate();
            stmt.close();

            // then update all the dependent rows (foreign keys)
            if (item.isTagged(Flag.FlagInfo.VERSIONED)) {
                // update REVISION.ITEM_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getRevisionTableName(mbox) +
                        " SET item_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "item_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }

            if (type == MailItem.Type.MESSAGE || type == MailItem.Type.CHAT || type == MailItem.Type.CONVERSATION) {
                // update OPEN_CONVERSATION.CONV_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getConversationTableName(mbox) +
                        " SET conv_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "conv_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }

            if (type == MailItem.Type.APPOINTMENT || type == MailItem.Type.TASK) {
                // update APPOINTMENT.ITEM_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getCalendarItemTableName(mbox) +
                        " SET item_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "item_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }

            if (type == MailItem.Type.FOLDER || type == MailItem.Type.SEARCHFOLDER) {
                // update MAIL_ITEM.FOLDER_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(mbox) +
                        " SET folder_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "folder_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }

            if (type == MailItem.Type.FOLDER || type == MailItem.Type.SEARCHFOLDER ||
                    type == MailItem.Type.CONVERSATION) {
                // update MAIL_ITEM.PARENT_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(mbox) +
                        " SET parent_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "parent_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }

            // now we can delete the original row with no foreign key conflicts
            stmt = conn.prepareStatement("DELETE FROM " + DbMailItem.getMailItemTableName(mbox) +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            pos = 1;
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("renumbering " + type + " (" + item.getId() + " => " + newId + ")", e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void renumberItemCascade(MailItem item, int newId) throws ServiceException {
        Mailbox mbox = item.getMailbox();
        DbConnection conn = mbox.getOperationConnection();
        MailItem.Type type = item.getType();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(item) +
                    " SET id = ?" +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            int pos = 1;
            stmt.setInt(pos++, newId);
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());
            stmt.executeUpdate();
            stmt.close();

            if (type == MailItem.Type.FOLDER || type == MailItem.Type.SEARCHFOLDER) {
                // update MAIL_ITEM.FOLDER_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(mbox) +
                        " SET folder_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "folder_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }

            if (type == MailItem.Type.FOLDER || type == MailItem.Type.SEARCHFOLDER ||
                    type == MailItem.Type.CONVERSATION) {
                // update MAIL_ITEM.PARENT_ID
                stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(mbox) +
                        " SET parent_id = ?" +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "parent_id = ?");
                pos = 1;
                stmt.setInt(pos++, newId);
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                stmt.executeUpdate();
                stmt.close();
            }
        } catch (SQLException e) {
            throw ServiceException.FAILURE("renumbering " + type + " (" + item.getId() + " => " + newId + ")", e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void setDate(MailItem item, int date) throws ServiceException {
        Mailbox mbox = item.getMailbox();
        DbConnection conn = mbox.getOperationConnection();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(item) +
                    " SET date = ?" +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            int pos = 1;
            stmt.setInt(pos++, date);
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("setting content change IDs for item " + item.getId(), e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static TypedIdList getChangedItems(ChangeTrackingMailbox ombx) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();

        TypedIdList result = new TypedIdList();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT id, type, uuid" +
                    " FROM " + DbMailItem.getMailItemTableName(ombx) + Db.forceIndex("i_change_mask") +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "change_mask > 0");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, ombx, pos);

            rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(MailItem.Type.of(rs.getByte(2)), rs.getInt(1), rs.getString(3));
            }
            return result;
        } catch (SQLException e) {
            throw ServiceException.FAILURE("getting changed item ids for ombx " + ombx.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static Map<Integer, Pair<Integer, Integer>> getChangeMasksAndFolders(ChangeTrackingMailbox ombx)
            throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();

        Map<Integer, Pair<Integer, Integer>> result = new HashMap<Integer, Pair<Integer, Integer>>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT id, change_mask, folder_id" +
                    " FROM " + DbMailItem.getMailItemTableName(ombx) + Db.forceIndex("i_change_mask") +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "change_mask > 0 AND NOT type=?");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, ombx, pos);
            stmt.setByte(pos++, MailItem.Type.FOLDER.toByte());

            rs = stmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getInt(1), new Pair<Integer, Integer>(rs.getInt(2), rs.getInt(3)));
            }
            return result;
        } catch (SQLException e) {
            throw ServiceException.FAILURE("getting changed item ids and folders for ombx " + ombx.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static Map<Integer, Pair<Integer, Integer>> getChangeMasksAndFlags(ChangeTrackingMailbox ombx)
            throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();

        Map<Integer, Pair<Integer, Integer>> result = new HashMap<Integer, Pair<Integer, Integer>>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT id, change_mask, flags" +
                    " FROM " + DbMailItem.getMailItemTableName(ombx) + Db.forceIndex("i_change_mask") +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "change_mask > 0");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, ombx, pos);

            rs = stmt.executeQuery();
            while (rs.next())
                result.put(rs.getInt(1), new Pair<Integer, Integer>(rs.getInt(2), rs.getInt(3)));
            return result;
        } catch (SQLException e) {
            throw ServiceException.FAILURE("getting changed item ids and flags for ombx " + ombx.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static List<Pair<Integer, Integer>> getSimpleUnreadChanges(ChangeTrackingMailbox ombx, boolean isUnread) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();
        List<Pair<Integer, Integer>> readList = new ArrayList<Pair<Integer, Integer>>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT id, mod_metadata" +
                    " FROM " + DbMailItem.getMailItemTableName(ombx) +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "type IN (?, ?) AND change_mask=? AND unread=?");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, ombx, pos);
            stmt.setShort(pos++, MailItem.Type.MESSAGE.toByte());
            stmt.setShort(pos++, MailItem.Type.CHAT.toByte());
            stmt.setInt(pos++, Change.UNREAD);
            stmt.setInt(pos++, isUnread ? 1 : 0);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int modSequence = rs.getInt(2);
                readList.add(new Pair<Integer, Integer>(id, modSequence));
            }
            return readList;
        } catch (SQLException e) {
            throw ServiceException.FAILURE("getting items with simple read/unread change in mailbox " + ombx.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static Map<Integer, List<Pair<Integer, Integer>>> getFolderMoveChanges(ChangeTrackingMailbox ombx) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();
        Map<Integer, List<Pair<Integer, Integer>>> changes = new HashMap<Integer, List<Pair<Integer, Integer>>>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT id, folder_id, mod_metadata" +
                    " FROM " + DbMailItem.getMailItemTableName(ombx) +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "type IN (?, ?, ?, ?, ?, ?) AND change_mask=?");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, ombx, pos);
            stmt.setShort(pos++, MailItem.Type.CONTACT.toByte());
            stmt.setShort(pos++, MailItem.Type.MESSAGE.toByte());
            stmt.setShort(pos++, MailItem.Type.CHAT.toByte());
            stmt.setShort(pos++, MailItem.Type.APPOINTMENT.toByte());
            stmt.setShort(pos++, MailItem.Type.TASK.toByte());
            stmt.setShort(pos++, MailItem.Type.DOCUMENT.toByte());
            stmt.setInt(pos++, Change.FOLDER);

            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int folderId = rs.getInt(2);
                int modSequence = rs.getInt(3);
                List<Pair<Integer, Integer>> batch = changes.get(folderId);
                if (batch == null) {
                    batch = new ArrayList<Pair<Integer, Integer>>();
                    changes.put(folderId, batch);
                }
                batch.add(new Pair<Integer, Integer>(id, modSequence));
            }

            return changes;
        } catch (SQLException e) {
            throw ServiceException.FAILURE("getting items with simple folder moves in mailbox " + ombx.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static Map<Integer, Integer> getItemModSequences(ChangeTrackingMailbox ombx, int[] ids) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();
        Map<Integer, Integer> changes = new HashMap<Integer, Integer>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        for (int i = 0; i < ids.length; i += Db.getINClauseBatchSize()) {
            try {
                int count = Math.min(Db.getINClauseBatchSize(), ids.length - i);
                stmt = conn.prepareStatement("SELECT id, mod_metadata" +
                        " FROM " + DbMailItem.getMailItemTableName(ombx) +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + DbUtil.whereIn("id", count));
                int pos = 1;
                pos = DbMailItem.setMailboxId(stmt, ombx, pos);
                for (int index = i; index < i + count; index++)
                    stmt.setInt(pos++, ids[i]);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int modSequence = rs.getInt(2);
                    changes.put(id, modSequence);
                }
            } catch (SQLException e) {
                throw ServiceException.FAILURE("getting mod sequence of given items " + ombx.getId(), e);
            } finally {
                DbPool.closeResults(rs);
                DbPool.closeStatement(stmt);
            }
        }
        return changes;
    }

    public static Map<Integer, Integer> getItemFolderIds(ChangeTrackingMailbox ombx, int[] ids) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        for (int i = 0; i < ids.length; i += Db.getINClauseBatchSize()) {
            try {
                int count = Math.min(Db.getINClauseBatchSize(), ids.length - i);
                stmt = conn.prepareStatement("SELECT id, folder_id" +
                        " FROM " + DbMailItem.getMailItemTableName(ombx) +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + DbUtil.whereIn("id", count));
                int pos = 1;
                pos = DbMailItem.setMailboxId(stmt, ombx, pos);
                for (int index = i; index < i + count; index++)
                    stmt.setInt(pos++, ids[i]);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int folderId = rs.getInt(2);
                    result.put(id, folderId);
                }
            } catch (SQLException e) {
                throw ServiceException.FAILURE("getting folder ids of given items " + ombx.getId(), e);
            } finally {
                DbPool.closeResults(rs);
                DbPool.closeStatement(stmt);
            }
        }
        return result;
    }

    public static int getChangeMask(MailItem item) throws ServiceException {
        Mailbox mbox = item.getMailbox();
        DbConnection conn = mbox.getOperationConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT change_mask" +
                    " FROM " + DbMailItem.getMailItemTableName(mbox) +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());

            rs = stmt.executeQuery();
            if (!rs.next())
                throw MailItem.noSuchItem(item.getId(), item.getType());
            return rs.getInt(1);
        } catch (SQLException e) {
            throw ServiceException.FAILURE("getting change record for item " + item.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static void setChangeMask(MailItem item, int mask) throws ServiceException {
        Mailbox mbox = item.getMailbox();
        DbConnection conn = mbox.getOperationConnection();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(mbox) +
                    " SET change_mask = ?" +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            int pos = 1;
            if (mask == 0)
                stmt.setNull(pos++, Types.INTEGER);
            else
                stmt.setInt(pos++, mask);
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("setting change bitmask for item " + item.getId(), e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void updateChangeRecord(MailItem item, int mask) throws ServiceException {
        Mailbox mbox = item.getMailbox();
        DbConnection conn = mbox.getOperationConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (!Db.supports(Db.Capability.BITWISE_OPERATIONS)) {
                stmt = conn.prepareStatement("SELECT change_mask FROM " + DbMailItem.getMailItemTableName(item) +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
                int pos = 1;
                pos = DbMailItem.setMailboxId(stmt, mbox, pos);
                stmt.setInt(pos++, item.getId());
                rs = stmt.executeQuery();
                if (rs.next())
                    mask |= rs.getInt(1);
                rs.close();
                stmt.close();
            }

            String newMask = (Db.supports(Db.Capability.BITWISE_OPERATIONS) ? "CASE WHEN change_mask IS NULL THEN ? ELSE change_mask | ? END" : "?");
            stmt = conn.prepareStatement("UPDATE " + DbMailItem.getMailItemTableName(item) +
                    " SET change_mask = " + newMask +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "id = ?");
            int pos = 1;
            stmt.setInt(pos++, mask);
            if (Db.supports(Db.Capability.BITWISE_OPERATIONS))
                stmt.setInt(pos++, mask);
            pos = DbMailItem.setMailboxId(stmt, mbox, pos);
            stmt.setInt(pos++, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("setting change record for item " + item.getId(), e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static boolean isTombstone(ChangeTrackingMailbox ombx, int id, MailItem.Type type) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();
        return !getMatchingTombstones(conn, ombx, id, type).isEmpty();
    }

    private static List<Pair<Integer, String>> getMatchingTombstones(DbConnection conn, ChangeTrackingMailbox ombx,
            int id, MailItem.Type type) throws ServiceException {
        List<Pair<Integer, String>> matches = new ArrayList<Pair<Integer, String>>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if (Db.supports(Db.Capability.CLOB_COMPARISON)) {
                // FIXME: oh, this is not pretty
                stmt = conn.prepareStatement("SELECT sequence, ids FROM " + DbMailItem.getTombstoneTableName(ombx) +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "type = ? AND (ids = ? OR ids LIKE ? OR ids LIKE ? OR ids LIKE ?)");
                int pos = 1;
                pos = DbMailItem.setMailboxId(stmt, ombx, pos);
                stmt.setByte(pos++, type.toByte());
                stmt.setString(pos++, "" + id);
                stmt.setString(pos++, "%," + id);
                stmt.setString(pos++, id + ",%");
                stmt.setString(pos++, "%," + id + ",%");
                rs = stmt.executeQuery();
                while (rs.next())
                    matches.add(new Pair<Integer, String>(rs.getInt(1), rs.getString(2)));
            } else {
                stmt = conn.prepareStatement("SELECT sequence, ids FROM " + DbMailItem.getTombstoneTableName(ombx) +
                        " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "type = ?");
                int pos = 1;
                pos = DbMailItem.setMailboxId(stmt, ombx, pos);
                stmt.setByte(pos++, type.toByte());
                rs = stmt.executeQuery();

                String idStr = Integer.toString(id);
                while (rs.next()) {
                    String ids = rs.getString(2);
                    if (ids.equals(idStr) || ids.startsWith(idStr + ',') || ids.endsWith(',' + idStr) || ids.indexOf(',' + idStr + ',') != -1)
                        matches.add(new Pair<Integer, String>(rs.getInt(1), ids));
                }
            }
            return matches;
        } catch (SQLException e) {
            throw ServiceException.FAILURE("searching TOMBSTONE table for " + type + " " + id, e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

    public static void removeTombstone(ChangeTrackingMailbox ombx, int id, MailItem.Type type) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();
        String itemId = Integer.toString(id);

        PreparedStatement stmt = null;
        try {
            for (Pair<Integer, String> tombstone : getMatchingTombstones(conn, ombx, id, type)) {
                int sequence = tombstone.getFirst();
                String ids = tombstone.getSecond();

                if (ids.equals(itemId)) {
                    stmt = conn.prepareStatement("DELETE FROM " + DbMailItem.getTombstoneTableName(ombx) +
                            " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "sequence = ? AND type = ?");
                    int pos = 1;
                    pos = DbMailItem.setMailboxId(stmt, ombx, pos);
                    stmt.setInt(pos++, sequence);
                    stmt.setByte(pos++, type.toByte());
                    stmt.executeUpdate();
                    stmt.close();
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String deletedId : ids.split(",")) {
                        if (!deletedId.equals(itemId)) {
                            sb.append(sb.length() == 0 ? "" : ",").append(deletedId);
                        }
                    }
                    stmt = conn.prepareStatement("UPDATE " + DbMailItem.getTombstoneTableName(ombx) + " SET ids = ?" +
                            " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "sequence = ? AND type = ?");
                    int pos = 1;
                    stmt.setString(pos++, sb.toString());
                    pos = DbMailItem.setMailboxId(stmt, ombx, pos);
                    stmt.setInt(pos++, sequence);
                    stmt.setByte(pos++, type.toByte());
                    stmt.executeUpdate();
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            throw ServiceException.FAILURE("removing entry from TOMBSTONE table for " + type + " " + id, e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void clearTombstones(ChangeTrackingMailbox ombx, int token) throws ServiceException {
        DbConnection conn = ombx.getOperationConnection();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("DELETE FROM " + DbMailItem.getTombstoneTableName(ombx) +
                    " WHERE " + DbMailItem.IN_THIS_MAILBOX_AND + "sequence <= ?");
            int pos = 1;
            pos = DbMailItem.setMailboxId(stmt, ombx, pos);
            stmt.setInt(pos++, token);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("clearing tombstones up to change " + token, e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void replaceAccountId(Mailbox mbox, String newAccountId) throws ServiceException {
        DbConnection conn = mbox.getOperationConnection();

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("UPDATE mailbox SET account_id = ? WHERE id = ?");
            int pos = 1;
            stmt.setString(pos++, newAccountId);
            stmt.setInt(pos++, mbox.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("failed to replace account ID on mailbox " + mbox.getId() , e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void forceDeleteMailbox(DbConnection conn, int id) throws ServiceException {
        PreparedStatement stmt = null;
        try {
            // remove entry from mailbox table
            if (!DebugConfig.externalMailboxDirectory) {
                stmt = conn.prepareStatement("DELETE FROM mailbox WHERE id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw ServiceException.FAILURE("deleting mailbox " +id, e);
        } finally {
            DbPool.closeStatement(stmt);
        }
    }

    public static void forceUidUpperCase(Mailbox mbox, String uid) throws ServiceException {
        DbConnection conn = mbox.getOperationConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("UPDATE " + DbMailItem.getCalendarItemTableName(mbox) + " SET uid = ? where uid = ?");
            int pos = 1;
            stmt.setString(pos++, uid.toUpperCase());
            stmt.setString(pos++, uid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw ServiceException.FAILURE("unable to force upper case UID in mbox:" + mbox.getId(), e);
        } finally {
            DbPool.closeResults(rs);
            DbPool.closeStatement(stmt);
        }
    }

}
