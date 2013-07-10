package com.proviciel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.mailbox.Folder;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.OperationContext;


public class JournalMessagesOpts {
	
	public Folder folder;
	
	public int from;
	
	public int to;

	public JournalMessagesOpts(HttpServletRequest req, HttpServletResponse resp, Mailbox mailbox, OperationContext octxt) throws ParamsException, ServiceException {
		String tmp;
		tmp = req.getParameter("box");
		if(tmp == null) this.folder = null;
		else this.folder =  mailbox.getFolderByPath(octxt, tmp);
		
		tmp = req.getParameter("from");
		if(tmp == null) {
			ParamsException e = new ParamsException();
			e.addMissing("from");
			throw e;
		}
		this.from = Integer.parseInt(tmp);

		tmp = req.getParameter("to");
		if(tmp == null) this.to = 0;
		else this.to = Integer.parseInt(tmp);
	}
}
