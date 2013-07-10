package com.proviciel.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;


public class MoveMessagesOpts {
	
	int[] ids;
	
	public String src;
	
	public String dst;
	
	public boolean copy;

	public MoveMessagesOpts(HttpServletRequest req, HttpServletResponse resp) throws ParamsException, JSONException, IOException{
		String tmp;
		ParamsException e = null;
		tmp = req.getParameter("src");
		if (tmp == null) {
			e = e==null ? new ParamsException() : e;
			e.addMissing("src");
		} else {
			this.src = tmp;
		}
		tmp = req.getParameter("dst");
		if (tmp == null) {
			e = e==null ? new ParamsException() : e;
			e.addMissing("dst");
		} else {
			this.dst = tmp;
		}
		if(e != null) throw new ParamsException();
		tmp = req.getParameter("copy");
		if(tmp == null) this.copy = false;
		else this.copy=Boolean.parseBoolean(tmp);
		this.ids = ServletUtils.getIds(req, resp);
	}
}
