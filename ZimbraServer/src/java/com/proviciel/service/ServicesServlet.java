/*
    Copyright © 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.service;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.proviciel.utils.ProvicielLog;
import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AuthTokenException;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.ZimbraAuthToken;
import com.zimbra.cs.account.auth.AuthContext.Protocol;


public class ServicesServlet extends HttpServlet {
	private static final long serialVersionUID = -2000849125300874778L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		try{
			ProvicielLog.rest.info("Services POST", req.getPathInfo());
			Provisioning prov = Provisioning.getInstance();
			String path = req.getPathInfo();
			if(path == null || path.equals("")){
				this.doAddService(req, resp, prov);
			} else if (path.equals("/delete")){
				this.doDeleteService(req, resp, prov);
			}
		} catch (JSONException | ParamsException e) {
			badFormatted(req, resp, e.getMessage());
		} catch (ServiceException | AuthTokenException e) {
			accountError(req, resp, e.getMessage());
		}
	}

	private void doAddService(HttpServletRequest req, HttpServletResponse resp,
			Provisioning prov) throws ParamsException, JSONException, IOException, ServiceException, AuthTokenException {
		AddServicesOpts opt = new AddServicesOpts(req, resp);
		Account account = prov.getAccountByName(opt.email);
		String token;
		if(account == null){
			account = prov.createAccount(opt.email, opt.password, new HashMap<String, Object>());
		} else {
			try{
				prov.authAccount(account, opt.password, Protocol.imap);
			}catch(Exception e){
				accountError(req, resp, e.getMessage());
				return;
			}
		}
		token = AuthCookie.authCookie(req, resp, account);
		if(ZimbraAuthToken.getAuthToken(token).getAccount() == null){
			throw new AuthTokenException("No account associated with token");
		} else {
			JSONObject json = new JSONObject();
			json.put("token", token);
			ServletUtils.success(req, resp, json);
		}
		
	}
	
	private void doDeleteService(HttpServletRequest req,
			HttpServletResponse resp, Provisioning prov) throws ParamsException, JSONException, IOException, ServiceException, AuthTokenException {
		DeleteServicesOpts opt = new DeleteServicesOpts(req, resp);
		Account account = AuthCookie.getAccount(req, resp);
		if(account == null){
			throw new AuthTokenException("No account associated with token");
		} else if (account.getName().equals(opt.email)){
			account.deleteAccount();
		} else {
			throw new ParamsException("You try to delete '"+opt.email
					+"' while your token is associated with "+account.getName());
		}
	}

	private void accountError(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
		ServletUtils.jsonError(req, resp, HttpServletResponse.SC_UNAUTHORIZED, msg);
	}

	private void badFormatted(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
		ServletUtils.jsonError(req, resp, HttpServletResponse.SC_BAD_REQUEST, msg);
	}
}
