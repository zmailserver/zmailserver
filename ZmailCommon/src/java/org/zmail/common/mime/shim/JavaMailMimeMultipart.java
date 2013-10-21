/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.common.mime.shim;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;

import org.zmail.common.util.ByteUtil;
import org.zmail.common.util.ZmailLog;

public class JavaMailMimeMultipart extends MimeMultipart implements JavaMailShim {
    private static final boolean ZPARSER = JavaMailMimeMessage.ZPARSER;

    private org.zmail.common.mime.MimeMultipart zmultipart;
    private Map<org.zmail.common.mime.MimePart, JavaMailMimeBodyPart> partMap = new HashMap<org.zmail.common.mime.MimePart, JavaMailMimeBodyPart>();

    JavaMailMimeMultipart(org.zmail.common.mime.MimeMultipart multi) {
        this(multi, null);
    }

    JavaMailMimeMultipart(org.zmail.common.mime.MimeMultipart multi, MimePart jmparent) {
        zmultipart = multi;
        parent = jmparent;
    }

    public JavaMailMimeMultipart() {
        this("mixed");
    }

    public JavaMailMimeMultipart(String subtype) {
        super(subtype);
        zmultipart = new org.zmail.common.mime.MimeMultipart(subtype);
    }

    public JavaMailMimeMultipart(DataSource ds) throws MessagingException {
        super(new org.zmail.common.mime.ContentType(ds.getContentType()).getSubType());
        if (ZPARSER) {
            zmultipart = (org.zmail.common.mime.MimeMultipart) JavaMailMimeBodyPart.parsePart(ds);
        } else {
            parsed = false;
            this.ds = ds;
            contentType = ds.getContentType();
        }
    }

    org.zmail.common.mime.MimePart getZmailMimeMultipart() {
        return zmultipart;
    }

    @Override
    public synchronized void setSubType(String subtype) throws MessagingException {
        if (ZPARSER) {
            zmultipart.setContentType(zmultipart.getContentType().setSubType(subtype));
        } else {
            super.setSubType(subtype);
        }
    }

    @Override
    public synchronized int getCount() throws MessagingException {
        if (ZPARSER) {
            return zmultipart.getCount();
        } else {
            return super.getCount();
        }
    }

    private JavaMailMimeBodyPart partWrapper(org.zmail.common.mime.MimePart mp) {
        JavaMailMimeBodyPart jmpart = partMap.get(mp);
        if (jmpart == null) {
            partMap.put(mp, jmpart = new JavaMailMimeBodyPart(mp, this));
        }
        return jmpart;
    }

    @Override
    public synchronized BodyPart getBodyPart(int index) throws MessagingException {
        if (ZPARSER) {
            return partWrapper(zmultipart.getSubpart(index));
        } else {
            return super.getBodyPart(index);
        }
    }

    int getBodyPartIndex(JavaMailMimeBodyPart jmpart) {
        org.zmail.common.mime.MimePart mp = jmpart.getZmailMimePart();
        for (int i = 1; i <= zmultipart.getCount(); i++) {
            if (zmultipart.getSubpart(i) == mp) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public synchronized BodyPart getBodyPart(String cid) throws MessagingException {
        if (ZPARSER) {
            if (cid != null) {
                for (org.zmail.common.mime.MimePart mp : zmultipart) {
                    if (cid.equals(mp.getMimeHeader("Content-ID"))) {
                        return partWrapper(mp);
                    }
                }
            }
            return null;
        } else {
            return super.getBodyPart(cid);
        }
    }

    @Override
    public boolean removeBodyPart(BodyPart part) throws MessagingException {
        if (ZPARSER) {
            if (part instanceof JavaMailMimeBodyPart) {
                JavaMailMimeBodyPart jmmbp = (JavaMailMimeBodyPart) part;
                org.zmail.common.mime.MimePart mp = jmmbp.getZmailMimePart();
                partMap.remove(mp);

                boolean removed = zmultipart.removePart(mp);
                if (removed) {
                    jmmbp.setParent(null);
                }
                return removed;
            } else {
                return false;
            }
        } else {
            return super.removeBodyPart(part);
        }
    }

    org.zmail.common.mime.MimePart removePart(int index) {
        org.zmail.common.mime.MimePart mp = zmultipart.removePart(index);
        JavaMailMimeBodyPart jmmbp = partMap.remove(mp);
        if (jmmbp != null) {
            jmmbp.setParent(null);
        }
        return mp;
    }

    @Override
    public void removeBodyPart(int index) throws MessagingException {
        if (ZPARSER) {
            removePart(index);
        } else {
            super.removeBodyPart(index);
        }
    }

    @Override
    public synchronized void addBodyPart(BodyPart part) throws MessagingException {
        if (ZPARSER) {
            addBodyPart(part, zmultipart.getCount());
        } else {
            super.addBodyPart(part);
        }
    }

    @Override
    public synchronized void addBodyPart(BodyPart part, int index) throws MessagingException {
        if (ZPARSER) {
            if (part.getParent() != null) {
                ZmailLog.misc.warn("adding part that already has a parent");
            }
            if (part instanceof JavaMailMimeBodyPart) {
                JavaMailMimeBodyPart jmpart = (JavaMailMimeBodyPart) part;
                org.zmail.common.mime.MimePart mp = jmpart.getZmailMimePart();
                zmultipart.addPart(mp, index);
                partMap.put(mp, jmpart);
                jmpart.setParent(this);
            } else {
                // FIXME: turn the non-shim body part into a shim body part
                throw new IllegalArgumentException("must use JavaMailMimeBodyPart instance as body part");
            }
        } else {
            super.addBodyPart(part, index);
        }
    }

    @Override
    public synchronized boolean isComplete() throws MessagingException {
        if (ZPARSER) {
            // TODO Auto-generated method stub
            return super.isComplete();
        } else {
            return super.isComplete();
        }
    }

    @Override
    public synchronized String getPreamble() throws MessagingException {
        if (ZPARSER) {
            org.zmail.common.mime.MimeBodyPart part = zmultipart.getPreamble();
            try {
                return part == null ? null : part.getText();
            } catch (IOException ioe) {
                throw new MessagingException("error getting preamble", ioe);
            }
        } else {
            return super.getPreamble();
        }
    }

    @Override
    public synchronized void setPreamble(String preamble) throws MessagingException {
        if (ZPARSER) {
            org.zmail.common.mime.MimeBodyPart part = null;
            if (preamble != null) {
                part = new org.zmail.common.mime.MimeBodyPart(null);
                try {
                    part.setText(preamble);
                } catch (IOException ioe) {
                    throw new MessagingException("error converting preamble to byte[]", ioe);
                }
            }
            zmultipart.setPreamble(part);
        } else {
            super.setPreamble(preamble);
        }
    }

    @Override
    protected synchronized void updateHeaders() throws MessagingException {
        if (ZPARSER) {
            for (int i = 0; i < getCount(); i++) {
                ((JavaMailMimeBodyPart) getBodyPart(i)).updateHeaders();
            }
        } else {
            super.updateHeaders();
        }
    }

    @Override
    public synchronized void writeTo(OutputStream os) throws IOException, MessagingException {
        if (ZPARSER) {
            InputStream is = parent instanceof JavaMailShim ? zmultipart.getInputStream() : zmultipart.getRawContentStream();
            JavaMailMimeBodyPart.writeTo(is, os);
        } else {
            super.writeTo(os);
        }
    }

    @Override
    protected synchronized void parse() throws MessagingException {
        if (ZPARSER) {
            // we parse on create, so this is a no-op for us
        } else {
            super.parse();
        }
    }

    @Override
    protected JavaMailInternetHeaders createInternetHeaders(InputStream is) throws MessagingException {
        return new JavaMailInternetHeaders(is);
    }

    @Override
    protected JavaMailMimeBodyPart createMimeBodyPart(InternetHeaders headers, byte[] content) throws MessagingException {
        return new JavaMailMimeBodyPart(headers, content);
    }

    @Override
    protected MimeBodyPart createMimeBodyPart(InputStream is) throws MessagingException {
        return new JavaMailMimeBodyPart(is);
    }

    @Override
    protected synchronized void setMultipartDataSource(MultipartDataSource mpds) throws MessagingException {
        if (ZPARSER) {
            org.zmail.common.mime.ContentType ctype = new org.zmail.common.mime.ContentType(mpds.getContentType(), "multipart/mixed");
            if (!ctype.getPrimaryType().equals("multipart")) {
                throw new MessagingException("invalid (non-multipart) Content-Type: " + mpds.getContentType());
            }
            // clear out the old contents
            while (zmultipart.getCount() > 0) {
                zmultipart.removePart(0);
            }
            // need to parse the data source ourselves so that our offsets match up with the stream
            org.zmail.common.mime.MimeHeaderBlock headers = new org.zmail.common.mime.MimeHeaderBlock(ctype);
            InputStream is = null;
            try {
                is = mpds.getInputStream();
                org.zmail.common.mime.MimeParserInputStream mpis = new org.zmail.common.mime.MimeParserInputStream(is, headers).setSource(mpds);
                JavaMailMimeBodyPart.writeTo(mpis, null);
                org.zmail.common.mime.MimePart mp = mpis.getPart();
                if (mp instanceof org.zmail.common.mime.MimeMultipart) {
//                    if (parent instanceof )
                    zmultipart = (org.zmail.common.mime.MimeMultipart) mp;
                } else {
                    throw new MessagingException("multipart data source did not contain multipart");
                }
            } catch (IOException ioe) {
                throw new MessagingException("error reading multipart data source", ioe);
            } finally {
                ByteUtil.closeStream(is);
            }
        } else {
            super.setMultipartDataSource(mpds);
        }
    }

    @Override
    public String getContentType() {
        if (ZPARSER) {
            return zmultipart.getContentType().toString();
        } else {
            return super.getContentType();
        }
    }

    @Override
    public String toString() {
        if (ZPARSER) {
            return zmultipart.toString();
        } else {
            return super.toString();
        }
    }
}
