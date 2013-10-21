/*
    Copyright © 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.proviciel.db.JournalDbMailItem;
import com.proviciel.db.JournalItem;
import com.proviciel.utils.ProvicielLog;
import org.zmail.common.service.ServiceException;
import org.zmail.common.zmime.ZMimeMultipart;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AuthTokenException;
import org.zmail.cs.db.DbMailItem.SearchOpts;
import org.zmail.cs.index.SortBy;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.MailItem;
import org.zmail.cs.mailbox.MailItem.Type;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.MailboxManager;
import org.zmail.cs.mailbox.OperationContext;
import org.zmail.cs.mime.Mime;
import org.zmail.cs.mime.ParsedMessage;
import org.zmail.cs.service.util.ItemId;
import org.zmail.cs.util.JMSession;


public class MessagesServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2000849125300874778L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		try {
			String path = req.getPathInfo();
			path = path == null ? "" : path;
			Account account = AuthCookie.getAccount(req, resp);
			OperationContext octxt = new OperationContext(account);
			Mailbox mailbox = MailboxManager.getInstance().getMailboxByAccount(account);
			path = path == null ? "" : path;
			if(path.equals("")){
				this.doIds(req, resp, mailbox, octxt);
			} else if(path.equals("/init")){
				this.doInit(req, resp, mailbox, octxt);
			} else if(path.equals("/count")){
				this.doCount(req, resp, mailbox, octxt);
			} else if(path.equals("/folders")){
				this.doFolders(req, resp, mailbox, octxt);
			} else if(path.equals("/journal")){
				this.doJournal(req, resp, mailbox, octxt);
			} else {	
				ServletUtils.jsonError(req, resp, HttpServletResponse.SC_NOT_FOUND, path);
			}
		} catch (AuthTokenException e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_UNAUTHORIZED, e);
			return;
		} catch (JSONException | ParamsException e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_BAD_REQUEST, e);
			return;
		} catch (Exception e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
			return;
		} 
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		try {
			Account account = AuthCookie.getAccount(req, resp);
			OperationContext octxt = new OperationContext(account);
			Mailbox mailbox = MailboxManager.getInstance().getMailboxByAccount(account);
			String path = req.getPathInfo();
			path = path == null ? "" : path;
			if(path.equals("/get")) {
				this.doMessages(req, resp, mailbox, octxt);
			} else if(path.equals("/move")) {
				this.doMove(req, resp, mailbox, octxt);
			} else if(path.equals("/delete")) {
				this.doDelete(req, resp, mailbox, octxt);
			} else if(path.equals("/mark")) {
				this.doMark(req, resp, mailbox, octxt);
			} else if(path.equals("/mime")) {
				this.doSend(req, resp, mailbox, octxt);
			} else {
				ServletUtils.jsonError(req, resp, HttpServletResponse.SC_NOT_FOUND, path);
			}
		} catch (AuthTokenException e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_UNAUTHORIZED, e);
			return;
		} catch (ParamsException | JSONException e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_BAD_REQUEST, e);
			return;
		} catch (MessagingException |ServiceException e){
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
			return;
		}
	}
	

	////////////////////////////
	// Some utils

	private Folder getFolder(HttpServletRequest req, HttpServletResponse resp, Mailbox mailbox, OperationContext octxt) throws ServiceException{
		String boxname = req.getParameter("box");
		if(boxname == null) return null;
		return mailbox.getFolderByPath(octxt, boxname);
	}
	
	private SearchOpts getSearchOpts(HttpServletRequest req, HttpServletResponse resp){
		String offset = req.getParameter("offset");
		String before = req.getParameter("before");
		String limit = req.getParameter("limit");
		SearchOpts opt;
		if(before == null) {
			opt = new SearchOpts(false);
		} else {			
			opt = new SearchOpts(0L, Long.parseLong(before), true);
		}
		if (offset != null) opt.setOffset(Integer.parseInt(offset));
		if (limit != null) opt.setLimit(Integer.parseInt(limit));
		return opt;
	}
	
	private String createAttachmentId(int mail_id, int part_index){
		return String.format("@%d:%d", mail_id, part_index);
	}
	
	////////////////////////////
	// Perform request
	
	private void doCount(HttpServletRequest req, HttpServletResponse resp, Mailbox mailbox, OperationContext octxt) throws ServiceException, IOException{
		Folder folder = this.getFolder(req, resp, mailbox, octxt);
		FolderCount c = new FolderCount(req.getParameter("type"));
		long count = 0;
		if (folder == null){
			for (Folder tmp : mailbox.getFolderList(octxt, SortBy.NONE)){
				count += c.count(tmp);
			}
		} else {
			count = c.count(folder);
		}
		jsonCount(req, resp, count);
	}
	
	private void doFolders(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws ServiceException, JSONException, IOException {
		JSONObject json = new JSONObject();
		for(Folder folder: mailbox.getFolderList(octxt, SortBy.NONE)){
			if(folder.getDefaultView().equals(Type.MESSAGE)){
				json.append("folders", folder.getName());
			}
		}
		ServletUtils.success(req, resp, json);
	}
	
	private void doInit(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws JSONException, ServiceException, IOException {
		JSONObject json = new JSONObject();
		json.put("jid", JournalDbMailItem.getLastId(mailbox, octxt));
		json.put("folders", new JSONArray());
		List<Integer> ids = new ArrayList<>();
		String tmp = req.getParameter("limit");
		SearchOpts searchOpts = new SearchOpts(true);
		searchOpts.setLimit(tmp == null ? 10 : Integer.parseInt(tmp));
		for (Folder folder: mailbox.getFolderList(octxt, SortBy.NONE)){
			if(folder.getDefaultView().equals(Type.MESSAGE)){
				json.append("folders", folder.getName());
				List<Integer> i = mailbox.getItemIdList(octxt, Type.MESSAGE, folder.getId(), searchOpts);
				ids.addAll(i);
			}
		}
		json.put("ids", ids);
		ServletUtils.success(req, resp, json);
	}
	
	private void doIds(HttpServletRequest req, HttpServletResponse resp, 
			Mailbox mailbox, OperationContext octxt
			) throws ServiceException, IOException{
		Folder folder = this.getFolder(req, resp, mailbox, octxt);
		SearchOpts searchOpts = getSearchOpts(req, resp);
		List<Integer> ids;
		if(folder == null){
			ids = new ArrayList<Integer>();
			for(Folder tmp : mailbox.getFolderList(octxt, SortBy.NONE)){
				ids.addAll(mailbox.getItemIdList(octxt, Type.MESSAGE, tmp.getId(), searchOpts));
			}
		} else {
			ids = mailbox.getItemIdList(octxt, Type.MESSAGE, folder.getId(), searchOpts);
		}
		this.jsonIds(req, resp, ids);
	}
	
	private void doMessages(HttpServletRequest req, HttpServletResponse resp, 
			Mailbox mailbox, OperationContext octxt
			) throws JSONException, IOException, ServiceException, MessagingException {
		GetMessagesOpts opts = new GetMessagesOpts(req, resp);
		MailItem[] messages = mailbox.getItemById(octxt, opts.ids, Type.MESSAGE);
		this.jsonMails(req, resp, mailbox, octxt, opts, opts.ids, messages);
	}
	
	private void doMove(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws ParamsException, JSONException, IOException, ServiceException {
		MoveMessagesOpts opts = new MoveMessagesOpts(req, resp);
		// TODO - Check source folder ?
		Folder folder = mailbox.getFolderByPath(octxt, opts.dst);
		if(opts.copy){
			List<MailItem> news = mailbox.imapCopy(octxt, opts.ids, Type.MESSAGE, folder.getId());
			this.jsonCopy(req, resp, opts.ids, news);
		} else {
			mailbox.move(octxt, opts.ids, Type.MESSAGE, folder.getId(), null);
			this.jsonCopy(req, resp, null, null);
		}
	}

	private void doDelete(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws IOException, ServiceException, ParamsException, JSONException {
		DeleteMessagesOpts opts = new DeleteMessagesOpts(req, resp);
		mailbox.delete(octxt, opts.ids, Type.MESSAGE, null);
		this.jsonVoid(req, resp);
	}
	

	private void doMark(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws ParamsException, JSONException, IOException, ServiceException {
		MarkMessagesOpts opts = new MarkMessagesOpts(req, resp);
		mailbox.alterTag(octxt, opts.ids, Type.MESSAGE, opts.flag, opts.value, null);
		this.jsonVoid(req, resp);
	}
	
	private void doJournal(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws ParamsException, ServiceException, IOException {
		JournalMessagesOpts opt = new JournalMessagesOpts(req, resp, mailbox, octxt);
		List<JournalItem> j = JournalDbMailItem.get(mailbox, octxt, opt.from, opt.to);
		this.jsonJournal(req, resp, j, mailbox, octxt);
	}

	private void doSend(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws MessagingException, ServiceException, JSONException, IOException, ParamsException {
		JSONTokener parser = new JSONTokener(new InputStreamReader(req.getInputStream()));
		JSONObject json = new JSONObject(parser);
		String mime = json.getString("mime");
		String personal = json.getString("personal");
		if(mime == null | personal == null){
			throw new ParamsException();
		} else {
			InputStream  is = new ByteArrayInputStream(mime.getBytes());
			MimeMessage mm = new Mime.FixedMimeMessage(JMSession.getSmtpSession(mailbox.getAccount()), is);
			ItemId id = mailbox.getMailSender().sendMimeMessage(octxt, mailbox, mm);
			this.jsonId(req, resp, id.getId());
		}
		
	}

	////////////////////////////
	// Format replies

	private JSONObject mailItemToJson(Mailbox mailbox, OperationContext octxt,
			MailItem mail, GetMessagesOpts opt) throws JSONException, ServiceException, IOException, MessagingException{
		JSONObject json = new JSONObject();
		json.put("id", Integer.toString(mail.getId()));
		json.put("creation_date", mail.getDate());
		json.put("reception_date", mail.getDate());
		json.put("subject", mail.getSubject());
		JSONObject body = new JSONObject();
		ParsedMessage msg = new ParsedMessage(mail.getContent(), false);;
		json.put("from", msg.getSender());
		String rec = msg.getRecipients();
		json.put("to", rec == null ? new String[0] : rec.split(","));
		String text = null;
		String extract = null;
		String html = null;
		ArrayList<JSONObject> attachments = new ArrayList<JSONObject>();
		if (opt.with_body || opt.with_extract){
			Object tmp = msg.getMimeMessage().getContent();
			if (tmp instanceof String){
				text = (String) msg.getMimeMessage().getContent();
			} else if(tmp instanceof ZMimeMultipart){
				ZMimeMultipart m = (ZMimeMultipart) tmp;
				for(int i = 0; i < m.getCount(); i++){
					BodyPart b = m.getBodyPart(i);
					String disp = b.getDisposition(); 
					if (disp != null && disp.equalsIgnoreCase(Part.ATTACHMENT)){
						JSONObject att = new JSONObject();
						att.put("id", this.createAttachmentId(mail.getId(), i));
						att.put("name", b.getFileName());
						att.put("mimetype", b.getContentType());
						att.put("size", b.getSize());
						att.put("inline", false);
						attachments.add(att);
					} else {
						if(b.isMimeType("text/plain")){
							text = (String) b.getContent();
						} else if(b.isMimeType("text/html")){
							html = (String) b.getContent();
						} else {
							json.append("debug", b.getContentType());
						}
					}
				}
			}
		}
		if(opt.with_extract){
			String tmp = text == null ? "" : text;
			extract = AppConfig.EXTRACT_LENGTH < tmp.length() ? tmp.substring(0, AppConfig.EXTRACT_LENGTH) : tmp;
		}
		body.put("extract", extract == null || ! opt.with_extract ? "" : extract);
		body.put("text", text == null || ! opt.with_body ? "" : text);
		body.put("html", html == null || ! opt.with_body ? "" : html);
		json.put("body", body);
		json.put("size", mail.getSize());
		json.put("attachments", attachments);
		json.put("box", mailbox.getFolderById(octxt, mail.getFolderId()).getName());
		json.put("read", !mail.isUnread());
		json.put("flagged", mail.isFlagged());
		return json;
	}
	
	private void jsonMails(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt, GetMessagesOpts opt,
			int[] ids,
			MailItem[] mails) throws ServiceException, IOException, MessagingException {
		try{
			JSONObject json = new JSONObject();
			int count = 0;
			for (int i=0; i < mails.length; i++) {
				if (mails[i] == null){
					ProvicielLog.rest.error("missing message "+ ids[i]);
				} else {
					count++;
					json.append("messages", this.mailItemToJson(mailbox, octxt, mails[i], opt));
				}
			}
			if(count == 0) json.put("messages", new JSONArray());
			ServletUtils.success(req, resp, json);
		} catch(JSONException e){
			ServletUtils.jsonError(req, resp, e);
		}
	}

	private void jsonIds(HttpServletRequest req, HttpServletResponse resp, List<Integer> ids) throws IOException{
		try {
			JSONObject json = new JSONObject();
			json.put("ids", ids);
			ServletUtils.success(req, resp, json);
		} catch (JSONException e){
			ServletUtils.jsonError(req, resp, e);
		}
	}
	
	
	private void jsonId(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException{
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			ServletUtils.success(req, resp, json);
		} catch (JSONException e){
			ServletUtils.jsonError(req, resp, e);
		}		
	}
	
	private void jsonCount(HttpServletRequest req, HttpServletResponse resp, long count) throws IOException{
		try {
			JSONObject json = new JSONObject();
			json.put("count", count);
			ServletUtils.success(req, resp, json);
		} catch (JSONException e) {
			ServletUtils.jsonError(req, resp, e);
		}
	}

	
	private void jsonVoid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JSONObject json = new JSONObject();
		ServletUtils.success(req, resp, json);
	}
	
	private void jsonJournal(HttpServletRequest req, HttpServletResponse resp, List<JournalItem> list, Mailbox mailbox, OperationContext octxt) throws IOException, ServiceException {
		try {
			JSONObject json = new JSONObject();
			JSONObject tmp;
			if (list.isEmpty()) json.put("journal", new JSONArray());
			for(JournalItem item: list){
				tmp = new JSONObject();
				tmp.put("event", item.event.toString().toLowerCase());
				tmp.put("mid", item.item_id);
				tmp.put("jid", item.jid);
				switch(item.event){
				case MOVE:
					tmp.put("more", mailbox.getFolderById(octxt, item.more).getName());
					break;
				default:
					break;
				}
				json.append("journal", tmp);
			}
			ServletUtils.success(req, resp, json);
		} catch(JSONException e) {
			ServletUtils.jsonError(req, resp, e);
		}
	}
	
	
	private void jsonCopy(HttpServletRequest req, HttpServletResponse resp,
			int[] ids, List<MailItem> news) throws IOException {
		try {
			JSONObject json = new JSONObject();
			json.put("copymoves", new JSONArray());
			if(news != null){
				JSONObject tmp;
				int i = 0;
				for(MailItem item: news){
					tmp = new JSONObject();
					tmp.put("id", ids[i]);
					tmp.put("ok", item.getId());
					i++;
				}
			}
			ServletUtils.success(req, resp, json);
		} catch(JSONException e) {
			ServletUtils.jsonError(req, resp, e);
		}		
	}
}
