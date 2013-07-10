package com.proviciel.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.zimbra.cs.mailbox.Flag;


public class MarkMessagesOpts {
	
	private enum MType {
		READ, UNREAD, FLAG, UNFLAG;
	}
	
	public int[] ids;
	
	public Flag.FlagInfo flag;
	
	public boolean value;
	
	public MarkMessagesOpts(HttpServletRequest req, HttpServletResponse resp) throws ParamsException, JSONException, IOException{
		this.ids = ServletUtils.getIds(req, resp);
		switch(MType.valueOf(req.getParameter("state").toUpperCase())){
		case UNREAD:
			this.flag = Flag.FlagInfo.UNREAD;
			this.value = true;
			break;
		case READ: 
			this.flag = Flag.FlagInfo.UNREAD;
			this.value = false;
			break;
		case FLAG:
			this.flag = Flag.FlagInfo.FLAGGED;
			this.value = true;
			break;
		case UNFLAG:
			this.flag = Flag.FlagInfo.FLAGGED;
			this.value = false;
			break;
		}
	}
}
