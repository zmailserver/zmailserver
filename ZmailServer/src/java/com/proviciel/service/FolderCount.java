/*
    Copyright © 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zmail.cs.mailbox.Folder;

public class FolderCount {

	private enum CType {
		ALL, READ, UNREAD, FLAGGED, UNFLAGGED;
	}
		
	private CType type;
	
	public FolderCount(HttpServletRequest req, HttpServletResponse resp){
		this(req.getParameter("type"));
	}
		
	public FolderCount(String type){
		if(type==null|| type.equals("") || type.equalsIgnoreCase("all")) 
			this.type = CType.ALL;
		else 
			this.type = CType.valueOf(type.toUpperCase());
	}
		
	public int count(Folder folder){
		switch(this.type){
		case ALL: return (int) folder.getItemCount();
		case READ: return (int) (folder.getItemCount() - folder.getUnreadCount());
		case UNREAD: return folder.getUnreadCount();
		case FLAGGED: return 0; //TODO
		case UNFLAGGED: return 0; //TODO
		}
		return 0;
	}
}
