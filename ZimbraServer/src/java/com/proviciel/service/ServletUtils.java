package com.proviciel.service;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.proviciel.utils.ProvicielLog;

public class ServletUtils {
	
	/**
	 * Reply with the given code a json error ({"error": msg})
	 */
	public static void jsonError(HttpServletRequest req, HttpServletResponse resp, int code, String msg) throws IOException{
		try{
			JSONObject json = new JSONObject();
			json.put("error", msg);
			resp.setStatus(code);
			ProvicielLog.rest.info(json.toString());
			json.write(resp.getWriter());
		} catch (JSONException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	public static void jsonError(HttpServletRequest req,  HttpServletResponse resp, String msg) throws IOException{
		jsonError(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static void jsonError(HttpServletRequest req,  HttpServletResponse resp, Exception e) throws IOException{
		jsonError(req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
	}
	
	public static void success(HttpServletRequest req, HttpServletResponse resp, JSONObject json) throws IOException {
		try {
			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_OK);
			ProvicielLog.rest.info(json.toString());
			json.write(resp.getWriter());
		} catch(JSONException e){
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().print(e.getMessage());
		}
	}

	public static void jsonError(HttpServletRequest req, HttpServletResponse resp, int code, Exception e) throws IOException {
		resp.setStatus(code);
		e.printStackTrace(resp.getWriter());
	}
	
	public static int[] getIds(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONTokener parser = new JSONTokener(new InputStreamReader(req.getInputStream()));
		JSONObject json = new JSONObject(parser);
		ProvicielLog.rest.info(json.toString());
		JSONArray array = json.getJSONArray("ids");
		int length = array.length();
		int[] ids = new int[length];
		for(int i =0; i < array.length(); i++){
			ids[i] = array.getInt(i);
		}
		return ids;
	}
}
