/*
    Copyright © 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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
