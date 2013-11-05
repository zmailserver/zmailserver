/*
    Copyright 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.proviciel.db.JournalItem.Event;
import org.zmail.common.service.ServiceException;
import org.zmail.cs.db.DbMailbox;
import org.zmail.cs.db.DbPool.DbConnection;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailItem.Type;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;

public class JournalDbMailItem {
	
	private static String JOURNAL_NAME = "journal";
	
	private static String getJournalTableName(Mailbox mbox){
		return DbMailbox.qualifyTableName(mbox, JOURNAL_NAME);
	}
	
	private static void add(Mailbox mbox, Collection<Integer> ids, byte type, Event event, int more) throws ServiceException{
		if (ids.isEmpty()) return;
		DbConnection conn = mbox.getOperationConnection();

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" INSERT INTO " + getJournalTableName(mbox));
			sb.append(" (mailbox_id, item_id, type, event");
			if(more != -1) sb.append(", more");
			sb.append(") VALUES ");
			boolean first = true; 
			int size = ids.size();
			for(int i = 0; i < size; i++){
				if (first) first = false;
				else sb.append(",");
				sb.append("(?, ?, ?, ?");
				if(more!=-1) sb.append(", ?");
				sb.append(")");
			}
			PreparedStatement st = conn.prepareStatement(sb.toString());
			int pos = 1;
			for (Integer item_id: ids){
				st.setInt(pos++, mbox.getId());
				st.setInt(pos++, item_id);
				st.setByte(pos++, type);
				st.setByte(pos++, event.toByte());
				if(more != -1) st.setInt(pos++, more);
			}
			st.executeUpdate();
		} catch (SQLException e) {
			throw ServiceException.FAILURE(e.getMessage()+"\nwriting to journal table: MID=" + mbox.getId(), e);
		}
	}
	
	private static void add(Mailbox mbox, int item_id, byte type, Event event) throws ServiceException{
		add(mbox, ImmutableList.of(item_id), type, event, -1);
	}
	
	private static void add(Mailbox mbox, Collection<Integer> ids, MailItem.Type type, Event event) throws ServiceException{
		add(mbox, ids, type.toByte(), event, -1);
	}
	
	private static void add(MailItem item, Event event) throws ServiceException{
		add(item.getMailbox(), item.getId(), item.getType(), event);
	}
	
	private static void add(Mailbox mailbox, int id, Type type, Event event) throws ServiceException {
		add(mailbox, ImmutableList.of(id), type, event);
	}
	
	
	
	public static void create(Mailbox mbox, MailItem.UnderlyingData data) throws ServiceException{
		add(mbox, data.id, data.type, Event.NEW);
	}
	
	public static void alterUnread(Mailbox mbox, Collection<Integer> ids, boolean unread) throws ServiceException{
		add(mbox, ids, Type.UNKNOWN, unread ? Event.UNREAD : Event.READ);
	}	

	public static void copy(MailItem item, int id, String uuid, Folder folder, int indexId, int parentId, String locator, String metadata, boolean fromDumpster) throws ServiceException{
		add(item, Event.COPY);
	}
	
	public static void delete(Mailbox mbox, Collection<Integer> ids) throws ServiceException{
		add(mbox, ids, Type.UNKNOWN, Event.DELETE);
	}
	
	public static void move(Mailbox mbox, MailItem item, Folder folder) throws ServiceException{
		add(mbox, ImmutableList.of(item.getId()), item.getType().toByte(), Event.MOVE, folder.getId());
	}
	
	
	
	public static List<JournalItem> get(Mailbox mbox, OperationContext octxt, int from, int to) throws ServiceException{
		boolean success = false;
		try {
			mbox.beginTransaction("journalGet", octxt, null);
			DbConnection conn = mbox.getOperationConnection();
			List<JournalItem> res = new ArrayList<JournalItem>();
			ResultSet rs = null; PreparedStatement st = null;
			try {
				st = conn.prepareStatement(
					"SELECT journal_id, item_id, date, event, more FROM " + getJournalTableName(mbox) +
					" WHERE (type = ? OR type = ?) AND mailbox_id = ? AND journal_id > ?" +
					(to == 0 ? "" : " AND journal_id < ?") +
					" ORDER BY date DESC"
					);
				int pos = 1;
				st.setByte(pos++, Type.UNKNOWN.toByte());
				st.setByte(pos++, MailItem.Type.MESSAGE.toByte());
				st.setInt(pos++, mbox.getId());
				st.setInt(pos++, from);
				if (to != 0) st.setInt(pos++, to);
				rs = st.executeQuery();
				while (rs.next()){
					res.add(new JournalItem(
							rs.getInt(1),
							rs.getInt(2), 
							rs.getTimestamp(3).getTime(), 
							rs.getByte(4),
							rs.getInt(5)));
				}
			} catch (SQLException e) {
				throw ServiceException.FAILURE(e.getMessage() + "\nwriting to journal table: MID=" + mbox.getId(), e);
			} finally {
				conn.closeQuietly(rs);
				conn.closeQuietly(st);
			}
			success = true;
			return res;
		} finally {
			mbox.endTransaction(success);
		}
	}
	
	public static int getLastId(Mailbox mbox, OperationContext octxt) throws ServiceException{
		boolean success = false;
		try {
			mbox.beginTransaction("getLastId", octxt, null);
			DbConnection conn = mbox.getOperationConnection();
			ResultSet rs = null; PreparedStatement st = null;
			int jid = 0;
			try {
				st = conn.prepareStatement(
					"SELECT journal_id FROM " + getJournalTableName(mbox) +
					" ORDER BY journal_id DESC LIMIT 1"
					);
				rs = st.executeQuery();
				while (rs.next()){
					jid = rs.getInt(1);
				}
			} catch (SQLException e) {
				throw ServiceException.FAILURE(e.getMessage() + "getLastId: MID=" + mbox.getId(), e);
			} finally {
				conn.closeQuietly(rs);
				conn.closeQuietly(st);
			}
			success = true;
			return jid;
		} finally {
			mbox.endTransaction(success);
		}
	}
	
}
