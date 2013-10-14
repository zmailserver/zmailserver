/*
    Copyright © 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AuthTokenException;
import com.zimbra.cs.account.ZimbraAuthToken;

public class AuthCookie {
	
	public static String NAME = "auth_token";
	
	public static String authCookie(HttpServletRequest req, HttpServletResponse resp, Account account) throws AuthTokenException, ServiceException{
		ZimbraAuthToken token = new ZimbraAuthToken(account);
		String encoded = token.getEncoded();
		if (ZimbraAuthToken.getAuthToken(encoded).getAccount() == null)
			throw new IllegalArgumentException();
		setCookie(req, resp, encoded);
		return encoded;
	}

	private static void setCookie(HttpServletRequest req, HttpServletResponse resp, String token){
		resp.addCookie(new Cookie(NAME, token));
	}
	
	public static Account getAccount(HttpServletRequest req, HttpServletResponse resp) throws ServiceException, AuthTokenException, IOException{
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies){
			if (cookie.getName().equals(NAME)) {
				String value = cookie.getValue();
				Account account = ZimbraAuthToken.getAuthToken(value).getAccount();
				if (account == null)
					throw new AuthTokenException("No account for token: "+value);
				return account;
			}
		}
		throw new AuthTokenException("Cookie is missing");
	}
}
