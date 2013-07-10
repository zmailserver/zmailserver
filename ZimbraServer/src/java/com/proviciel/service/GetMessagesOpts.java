package com.proviciel.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;


public class GetMessagesOpts {
	
	public int[] ids = null;
	
	public boolean with_body;
	
	public boolean with_extract;

	public GetMessagesOpts(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException {
		String tmp;
		tmp = req.getParameter("with_body");
		if (tmp == null) this.with_body = true;
		else this.with_body = Boolean.parseBoolean(tmp);
		tmp = req.getParameter("with_extract");
		if(tmp == null) this.with_extract = true;
		else this.with_extract = Boolean.parseBoolean(tmp);
		ids = ServletUtils.getIds(req, resp);
	}
}
