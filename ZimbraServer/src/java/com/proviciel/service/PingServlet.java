package com.proviciel.service;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class PingServlet extends HttpServlet {

	private static final long serialVersionUID = -2000849125300874998L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		ServletUtils.success(req, resp, new JSONObject());
	}

}
