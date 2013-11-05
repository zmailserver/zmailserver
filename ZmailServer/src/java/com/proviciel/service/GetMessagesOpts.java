/*
    Copyright 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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
