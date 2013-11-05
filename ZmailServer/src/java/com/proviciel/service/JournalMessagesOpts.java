/*
    Copyright 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.common.service.ServiceException;
import org.zmail.cs.mailbox.Folder;
import org.zmail.cs.mailbox.Mailbox;
import org.zmail.cs.mailbox.OperationContext;


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
