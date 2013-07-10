package com.proviciel.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.zmime.ZMimeMultipart;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AuthTokenException;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.mailbox.OperationContext;
import com.zimbra.cs.mailbox.MailItem.Type;
import com.zimbra.cs.mime.ParsedMessage;

public class FilesServlet  extends HttpServlet {

	private static final long serialVersionUID = -2000849125300874968L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		try {
			Account account = AuthCookie.getAccount(req, resp);
			OperationContext octxt = new OperationContext(account);
			Mailbox mailbox = MailboxManager.getInstance().getMailboxByAccount(account);
			String path = req.getPathInfo();
			path = path == null ? "" : path;
			if(path.equals("/download")){
				this.doDownload(req, resp, mailbox, octxt);
			}
		} catch (ServiceException | AuthTokenException e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_UNAUTHORIZED, e);
			return;
		} catch (ParamsException e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_BAD_REQUEST, e);
			return;
		} catch (Exception e) {
			ServletUtils.jsonError(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
			return;
		} 
	}

	private void doDownload(HttpServletRequest req, HttpServletResponse resp,
			Mailbox mailbox, OperationContext octxt) throws ServiceException, IOException, MessagingException, ParamsException {
		String id = req.getParameter("id");
		if(id == null) throw new ParamsException();
		Pattern pattern = Pattern.compile("@(\\d+):(\\d+)");
		Matcher matcher = pattern.matcher(id);
		if (matcher.matches()){
			int mail_id = Integer.parseInt(matcher.group(1));
			int pos = Integer.parseInt(matcher.group(2));
			MailItem mail = mailbox.getItemById(octxt, mail_id, Type.MESSAGE);
			Object tmp = (new ParsedMessage(mail.getContent(), false)).getMimeMessage().getContent();
			if (tmp instanceof ZMimeMultipart){
				ZMimeMultipart mime = (ZMimeMultipart) tmp;
				BodyPart part = mime.getBodyPart(pos);
				InputStream is = part.getInputStream();
				Writer writer = resp.getWriter();
				IOUtils.copy(is, writer);
			} else {
				throw ServiceException.FORBIDDEN("No attachement on mail id="+id);
			}
		} else {
			throw new ParamsException("bad formatted id="+id);
		}
	}
}
