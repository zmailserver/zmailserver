package com.proviciel.service;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class DeleteServicesOpts {

	public String email;
	
	public DeleteServicesOpts(HttpServletRequest req, HttpServletResponse resp) throws ParamsException, JSONException, IOException{
		JSONTokener parser = new JSONTokener(new InputStreamReader(req.getInputStream()));
		JSONObject json = new JSONObject(parser);
		this.email = json.getString("email");
	}
}
