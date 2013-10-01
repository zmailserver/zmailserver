/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.taglib.tag;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.taglib.bean.ZUserAgentBean;
import com.zimbra.cs.taglib.tag.i18n.I18nUtil;
import com.zimbra.client.ZMailbox;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BindKeyTag extends ZimbraSimpleTag {

    // Key names
     public static final String CTRL	= "Ctrl+";
     public static final String META = "Meta+";
     public static final String ALT = "Alt+";
     public static final String SHIFT = "Shift+";
     public static final String ARROW_DOWN = "ArrowDown";
     public static final String ARROW_LEFT = "ArrowLeft";
     public static final String ARROW_RIGHT = "ArrowRight";
     public static final String ARROW_UP = "ArrowUp";
     public static final String BACKSPACE = "Backspace";
     public static final String COMMA = "Comma";
     public static final String SEMICOLON = "Semicolon";
     public static final String DELETE = "Del";
     public static final String END = "End";
     public static final String ENTER = "Enter";
     public static final String ESC = "Esc";
     public static final String HOME = "Home";
     public static final String PAGE_DOWN = "PgDown";
     public static final String PAGE_UP = "PgUp";
     public static final String SPACE = "Space";
     public static final String BACKSLASH = "Backslash";

    private static final String BINDKEY_CACHE = "BindKeyTag.CACHE";

    private static final String A_GOTO_TAG = "gototag";
    private static final String A_TAG = "tag";
    private static final String A_GOTO_FOLDER = "gotofolder";
    private static final String A_FOLDER = "folder";
    private static final String A_SEARCH = "search";

    private String mKey;
    private String mMessage;
    private String mBasename = "/keys/ZhKeys";
    private String mId;
    private String mFunc;
    private String mUrl;
    private String mAlias;

    public void setId(String id) { mId = id; }
    public void setKey(String key) { mKey = key; }
    public void setMessage(String message) { mMessage = message; }
    public void setFunc(String func) { mFunc = func; }
    public void setUrl(String url) { mUrl = url; }
    public void setBasename(String basename) { mBasename = basename; }
    public void setAlias(String alias) { mAlias = alias; }

    public void doTag() throws JspException, IOException {
        JspContext jctxt = getJspContext();

        if (mKey == null && mMessage == null) {
            throw new JspTagException("The bindKey tag must have either a key or a message attribute");
        }

        String js = null;
        try {
            if (mAlias == null) {
                if (mId == null && mFunc == null && mUrl == null)
                    throw new JspTagException("The bindKey tag must have either a function, url, or an id");
                js = mMessage != null ? getJavaScriptForMessage(jctxt, mMessage, true) : getJavaScriptForKey(mKey);
            } else if (A_GOTO_TAG.equals(mAlias)) {
                js = generateGoToTagAliases(jctxt, NumericShortCutType.tag);
            } else if (A_GOTO_FOLDER.equals(mAlias)) {
                js = generateGoToTagAliases(jctxt, NumericShortCutType.folder);
            } else if (A_SEARCH.equals(mAlias)) {
                js = generateGoToTagAliases(jctxt, NumericShortCutType.search);
            } else if (A_TAG.equals(mAlias)) {
                js = generateTagAliases(jctxt);
            } else if (A_FOLDER.equals(mAlias)) {
                js = generateFolderMoveAliases(jctxt);                
            }
            if (js != null) {
                JspWriter out = jctxt.getOut();
                out.write(js);
            }

        } catch (ServiceException e) {
            throw new JspTagException(e.getMessage(), e);
        }
    }

    private String resolveMessageKey(JspContext ctxt, String message) {
        ZUserAgentBean ua = GetUserAgentTag.getUserAgent(ctxt,true);

        String prop = message + ".keycode";
        if (ua != null) {
            String os = ua.getIsOsWindows() ? ".win" : ua.getIsOsMac() ? ".mac" : ua.getIsOsLinux() ? ".linux" : null;
            if (os != null) {
                String key = I18nUtil.getLocalizedMessage((PageContext)ctxt, prop + os, mBasename);
                if (!key.startsWith("???")) return key;
            }
        }
        return I18nUtil.getLocalizedMessage((PageContext)ctxt, prop, mBasename);
    }

    private String generateGoToTagAliases(JspContext ctxt, NumericShortCutType type) throws JspException, ServiceException, IOException {
        ZMailbox mailbox = getMailbox();
        String pref = mailbox.getPrefs().getShortcuts();
        if (pref == null)
            return null;
        String seq = resolveMessageKey(ctxt, mMessage);
        if (seq == null || seq.indexOf("NNN") == -1)
            return null;
        List<NumericShortCut> shortCuts = getNumericShortCuts(pref);
        StringBuilder sb = new StringBuilder();
        for (NumericShortCut shortcut : shortCuts) {
            if (shortcut.getType() == type) {
                String msg = seq.replace("NNN", shortcut.getNumber());
                mId = (type == NumericShortCutType.tag ? "TAG" : type == NumericShortCutType.folder ? "FLDR" : "SRCH") + shortcut.getId();
                String js = getJavaScriptForMessage(ctxt, msg, false);
                if (js != null)
                    sb.append(js);
            }
        }
        return sb.toString();
    }
    
    private String generateTagAliases(JspContext ctxt) throws JspException, ServiceException, IOException {
        ZMailbox mailbox = getMailbox();
        String pref = mailbox.getPrefs().getShortcuts();
        if (pref == null)
            return null;
        String seq = resolveMessageKey(ctxt, mMessage);
        if (seq == null || seq.indexOf("NNN") == -1)
            return null;
        List<NumericShortCut> shortCuts = getNumericShortCuts(pref);
        StringBuilder sb = new StringBuilder();
        String func = mFunc;
        for (NumericShortCut shortcut : shortCuts) {
            if (shortcut.getType() == NumericShortCutType.tag) {
                String msg = seq.replace("NNN", shortcut.getNumber());
                mFunc = func.replace("{TAGID}", shortcut.getId());
                String js = getJavaScriptForMessage(ctxt, msg, false);
                if (js != null)
                    sb.append(js);
            }
        }
        return sb.toString();
    }

    private String generateFolderMoveAliases(JspContext ctxt) throws JspException, ServiceException, IOException {
        ZMailbox mailbox = getMailbox();
        String pref = mailbox.getPrefs().getShortcuts();
        if (pref == null)
            return null;
        String seq = resolveMessageKey(ctxt, mMessage);
        if (seq == null || seq.indexOf("NNN") == -1)
            return null;
        List<NumericShortCut> shortCuts = getNumericShortCuts(pref);
        StringBuilder sb = new StringBuilder();
        String func = mFunc;
        for (NumericShortCut shortcut : shortCuts) {
            if (shortcut.getType() == NumericShortCutType.folder) {
                String msg = seq.replace("NNN", shortcut.getNumber());
                mFunc = func.replace("{FOLDERID}", shortcut.getId());
                String js = getJavaScriptForMessage(ctxt, msg, false);
                if (js != null)
                    sb.append(js);
            }
        }
        return sb.toString();
    }
    
    private String getJavaScriptForMessage(JspContext ctxt, String message, boolean resolve) throws JspTagException {
        Map<String,String> cache = (Map<String,String>) ctxt.getAttribute(BINDKEY_CACHE, PageContext.SESSION_SCOPE);
        if (cache == null) {
            cache = Collections.synchronizedMap(new HashMap<String,String>());
            ctxt.setAttribute(BINDKEY_CACHE, cache, PageContext.SESSION_SCOPE);
        }

        String cacheKey = message+"/"+ ((mFunc != null) ? mFunc : mUrl != null ? mUrl : mId);
        String js = cache.get(cacheKey);
        if (js == null) {
            //System.out.println("bindKey: cache miss for: "+cacheKey);
            String key = resolve ? resolveMessageKey(ctxt, message) : message;
            if (key.startsWith("???")) {
                System.err.println("bindKey: unresolved prop: "+message);
                return null;
            } else {
                js = getJavaScriptForKey(key);
                cache.put(cacheKey, js);
            }
        } else {
            //System.out.println("bindKey: cache hit for: "+cacheKey);
        }
        return js;
    }

    private String getJavaScriptForKey(String k) throws JspTagException {
        StringBuilder js = new StringBuilder();
        if (k == null) return "";
        k = k.trim();
        if (k.length() == 0) return "";
        
        for (String keySeq: k.split(";")) {
            String keys[] = keySeq.trim().split(",");
            if (keys.length == 0)
                throw new JspTagException("invalid key binding: "+k);
            StringBuilder sb = new StringBuilder();
            for (String key : keys) {
                sb.append(':').append(getCode(key.trim()));
            }
            if (mFunc != null)
                js.append(String.format("bindKey('%s', %s);%n", sb.toString(), mFunc));
            else if (mUrl != null)
                js.append(String.format("bindKey('%s', function(){ window.location=\"%s\";});%n", sb.toString(), mUrl));
            else
                js.append(String.format("bindKey('%s', '%s');%n", sb.toString(), mId));
        }
        return js.toString();
    }

    private String getCode(String seq) throws JspTagException {
        StringBuilder code = new StringBuilder();
        if (seq.contains(ALT)) code.append('a');
        if (seq.contains(CTRL)) code.append('c');
        if (seq.contains(META)) code.append('m');
        if (seq.contains(SHIFT)) code.append('s');
        String kc = seq.substring(seq.lastIndexOf('+') + 1);
        if (kc == null || kc == "") throw new JspTagException("invalid key binding: "+seq);
        code.append(kc);
        return code.toString();
    }

    // global.GoToTag1.65=Y,1|global.GoToTag2.64=Y,2|global.Tag1.65=T,1|global.Tag2.64=T,2
    // T,65,1|S,546,
    /*
     global.GoToTag1.65=Y,1|
     global.GoToTag2.64=Y,2|
     global.SavedSearch99.283=S,9,9|
     global.Tag1.65=T,1|
     global.Tag2.64=T,2|
     mail.GoToFolder3.5=V,3|
     mail.MoveToFolder3.5=.,3|
     mail.MoveToFolder3.5=Shift+.,3
     */

    private static final Pattern sOLD_SHORTCUT = Pattern.compile("^(global\\.GoToTag|global\\.SavedSearch|mail.GoToFolder)(\\d+)\\.(\\d+)=.*");
    private static final Pattern sSHORTCUT = Pattern.compile("^(F|T|S),(\\d+),(\\d+)$");
    private static final Pattern sSEP = Pattern.compile("\\|");


    public enum NumericShortCutType { search, folder, tag }

    public static class NumericShortCut {
        private NumericShortCutType mType;
        private String mNumber;
        private String mId;

        public NumericShortCut(NumericShortCutType type, String number, String id) {
            mType = type;
            if (number.length() > 1) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < number.length(); i++) {
                    if (sb.length() > 0) sb.append(",");
                    sb.append(number.charAt(i));
                }
                mNumber = sb.toString();
            } else {
                mNumber = number;
            }

            mId = id;
        }

        public NumericShortCutType getType() { return mType; }
        public String getNumber() { return mNumber; }
        public String getId() { return mId; }

        public String toString() {
            return String.format("{type=%s, id=%s, number=%s}", mType.name(), mId, mNumber);
        }
    }

    public static List<NumericShortCut> getNumericShortCuts(String pref) {
        List<NumericShortCut> result = new ArrayList<NumericShortCut>();
        if (pref == null) return result;
        if (pref.indexOf('=') == -1) {
            for (String s : sSEP.split(pref)) {
                Matcher matcher = sSHORTCUT.matcher(s);
                if (matcher.matches()) {
                    String t = matcher.group(1);
                    String id = matcher.group(2);
                    String n = matcher.group(3);
                    if (t.equals("T"))
                        result.add(new NumericShortCut(NumericShortCutType.tag, n, id));
                    else if (t.equals("S"))
                        result.add(new NumericShortCut(NumericShortCutType.search, n, id));
                    else if (t.equals("F"))
                        result.add(new NumericShortCut(NumericShortCutType.folder, n, id));
                }
            }
        } else {
            for (String s : sSEP.split(pref)) {
                Matcher matcher = sOLD_SHORTCUT.matcher(s);
                if (matcher.matches()) {
                    String t = matcher.group(1);
                    String n = matcher.group(2);
                    String id = matcher.group(3);
                    if (t.equals("global.GoToTag"))
                        result.add(new NumericShortCut(NumericShortCutType.tag, n, id));
                    else if (t.equals("global.SavedSearch"))
                        result.add(new NumericShortCut(NumericShortCutType.search, n, id));
                    else if (t.equals("mail.GoToFolder"))
                        result.add(new NumericShortCut(NumericShortCutType.folder, n, id));
                }
            }
        }
        return result;
    }

    public static void main(String args[]) {
        List<NumericShortCut> result = getNumericShortCuts("global.GoToTag1.65=Y,1|global.GoToTag2.64=Y,2|global.SavedSearch99.283=S,9,9|global.Tag1.65=T,1|global.Tag2.64=T,2|mail.GoToFolder1.2=V,1|mail.GoToFolder3.5=V,3|mail.MoveToFolder1.2=.,1|mail.MoveToFolder1.2=Shift+.,1|mail.MoveToFolder3.5=.,3|mail.MoveToFolder3.5=Shift+.,3");
        for (NumericShortCut nsc : result) {
            System.out.println(nsc);
        }
        result = getNumericShortCuts("F,2,1|F,5,3|S,283,99|S,284,1|T,64,2|T,65,1");
        for (NumericShortCut nsc : result) {
            System.out.println(nsc);
        }
    }
}
