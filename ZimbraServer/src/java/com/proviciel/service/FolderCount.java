package com.proviciel.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zimbra.cs.mailbox.Folder;

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
