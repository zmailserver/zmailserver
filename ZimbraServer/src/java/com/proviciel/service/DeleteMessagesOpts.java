package com.proviciel.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;


public class DeleteMessagesOpts {
	
	int[] ids;
	
	public DeleteMessagesOpts(HttpServletRequest req, HttpServletResponse resp) throws ParamsException, JSONException, IOException{
		this.ids = ServletUtils.getIds(req, resp);
	}
}
