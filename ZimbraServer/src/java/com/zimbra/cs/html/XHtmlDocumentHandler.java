/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.html;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A basic xhtml handler that deletes unfriendly tags and attributes.
 * 
 * The functionality is based mostly off the DefangFilter that's used for html.
 * 
 * There was also a little influence from here:
 * http://download.oracle.com/javaee/1.4/tutorial/doc/JAXPSAX3.html
 * 
 * 
 * @author jpowers
 *
 */
public class XHtmlDocumentHandler extends DefaultHandler {
    /**
     * The list of tags that should always be removed
     */
    private static Set<String> removeTags = new HashSet<String>();
    
    private static Set<String> removeAttributes = new HashSet<String>(); 
    // fills in the static sets
    static {
        removeTags.add("applet");
        removeTags.add("frame");
        removeTags.add("frameset");
        removeTags.add("iframe");
        removeTags.add("object");
        removeTags.add("script");
        removeTags.add("style");
        
        removeAttributes.add("onclick");
        removeAttributes.add("ondblclick");
        removeAttributes.add("onmousedown");
        removeAttributes.add("onmouseup");
        removeAttributes.add("onmouseover");
        removeAttributes.add("onmousemove");
        removeAttributes.add("onmouseout");
        removeAttributes.add("onkeypress");
        removeAttributes.add("onkeydown");
        removeAttributes.add("onkeyup");
    }

    
    /*
     * The writer that we'll write the sanitzed output to  
     */
    private Writer out;

    /**
     * A stack that keeps track of the elements that
     * have been removed. It lets us keep track of what tags
     * need their end tag removed.
     */
    private Stack<String> removedElements = new Stack<String>();
    
    /**
     * This buffer keeps track of the text between tags
     */
    private StringBuffer textBuffer = new StringBuffer();
    
    /***
     * Creates a new handler that writes
     * @param out
     */
    public XHtmlDocumentHandler(Writer out){
        this.out = out;
    }
    
    @Override
    public void characters(char[] buf, int start, int len) throws SAXException {
        // if we're removing tags, remove all of the text between them as well.
        if(!removedElements.isEmpty()){
            // TODO check to see if we can't allow some text, but in reality it probably isn't needed
            return;
        }
        textBuffer.append(new String(buf, start, len));        
    }
    

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        try {
            out.append("<?").append(target).append(" ").append(data).append("?>");
        } catch (IOException e) {
           throw new SAXException(e);
        }
    }

    

    @Override
    public void startDocument() throws SAXException {
         try {
            out.write("<?xml version='1.0' encoding='UTF-8'?>\n");
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        try {
            out.flush();
        } catch (IOException e) {
            throw new SAXException(e);
        }
        
    }

    @Override
    public void startElement(String namespaceURI,
            String sName, // simple name
            String qName, // qualified name
            Attributes attrs) throws SAXException {
        
        String eName = "".equals(sName)? qName: sName; // element name


        // check to see if we're removing this tag
        if(removeTags.contains(eName.toLowerCase())) {
            removedElements.push(eName.toLowerCase());
            return;
        }
        try{ 
            // output any text that might need outputing
            if(textBuffer.length() > 0)
            {
                out.append(textBuffer);
                textBuffer = new StringBuffer();
            }
            out.append("<");
            out.append(eName);
            if (attrs != null) {
              for (int i = 0; i < attrs.getLength(); i++) {
                String aName = "".equals(attrs.getLocalName(i))? attrs.getQName(i) : attrs.getLocalName(i); // Attr name
                if(removeAttributes.contains(aName.toLowerCase())) {
                    // just skip this attribute
                    continue;
                }
                out.append(" ");
                out.append(aName+"=\""+attrs.getValue(i)+"\"");
              }
            }
            out.append(">");
        }
        catch(IOException e) {
            throw new SAXException(e);
        }
    }
    @Override
    public void endElement(String namespaceURI,
            String sName, // simple name
            String qName)  // qualified name
            throws SAXException {
        // first, check to see if this element is one of the ones that we are removing
        String eName = "".equals(sName)? qName: sName; // element name

        if(!removedElements.isEmpty() && removedElements.peek().equals(eName.toLowerCase())) {
            // this is one of the tags we're getting rid of, pop and return
            removedElements.pop();
            return;
        }
        
        try {
            // output any text that might need outputting
            if(textBuffer.length() > 0)
            {
                out.append(textBuffer);
                textBuffer = new StringBuffer();
            }
            out.append("</").append(eName).append(">");
        } catch (IOException e) {
            throw new SAXException(e);
        }

    }

}
