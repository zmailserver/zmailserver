/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.offline.util;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Various XML utility methods.
 */
public final class Xml {
    public static Element appendText(Element e, Object text) {
        e.appendChild(e.getOwnerDocument().createTextNode(text.toString()));
        return e;
    }

    public static Element createElement(Document doc, String name, Object value) {
        return appendText(doc.createElement(name), value);
    }

    public static Element appendElement(Element e, String name, Object value) {
        e.appendChild(createElement(e.getOwnerDocument(), name, value));
        return e;
    }

    public static String getTextValue(Element e) {
        NodeList nl = e.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Text) {
                return ((Text) node).getWholeText();
            }
        }
        return null;
    }

    public static int getIntValue(Element e) {
        String s = getTextValue(e);
        return s != null ? parseInt(s) : -1;
    }

    public static int getIntAttribute(Element e, String name) {
        String s = e.getAttribute(name);
        return s != null ? parseInt(s) : -1;
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public static List<Element> getChildren(Element e) {
        NodeList nl = e.getChildNodes();
        List<Element> nodes = new ArrayList<Element>(nl.getLength());
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                nodes.add((Element) node);
            }
        }
        return nodes;
    }

    public static DocumentBuilder newDocumentBuilder() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String pid, String sid) {
                    return new InputSource(new StringReader(""));
                }
            });
            return db;
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("Unable to create DocumentBuilder", e);
        }
    }

    public static Document newDocument() {
        return newDocumentBuilder().newDocument();
    }
    
    public static String toString(Node node) {
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", 2);
        StringWriter sw = new StringWriter();
        try {
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException e) {
            // This should never happen
            throw new IllegalArgumentException("Unable to serialize node", e);
        }
        return sw.toString();
    }

    public static String prettyPrint(String xml) {
        DocumentBuilder db = Xml.newDocumentBuilder();
        try {
            return toString(db.parse(new InputSource(new StringReader(xml))));
        } catch (SAXException e) {
            throw new IllegalArgumentException("Not a valid XML document");
        } catch (IOException e) {
            throw new AssertionError();
        }
    }
}
