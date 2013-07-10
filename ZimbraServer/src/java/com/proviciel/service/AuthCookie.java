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
