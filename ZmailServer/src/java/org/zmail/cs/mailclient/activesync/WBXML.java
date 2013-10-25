/* Copyright 2010 Matthew Brace
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zimbra.cs.mailclient.activesync;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.xml.bind.DatatypeConverter;

import com.zimbra.cs.mailclient.activesync.CodePage;
import com.zimbra.cs.mailclient.activesync.codepages.*;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.apache.log4j.Logger;

/*
 * This class represents an entity for converting between WBXML and XML. The process uses
 * subclasses of the CodePage class that contain data for each code page potentially
 * referenced in the document.
 *
 * @version .1
 * @author  Matthew Brace
 */

public class WBXML {

    static Logger logger = Logger.getLogger(WBXML.class);

    /* WBXML ActiveSync specific Code Pages */
    public static final int WBXML_VERSION11 = 0x01; /* WBXML 1.1 */
    public static final int WBXML_VERSION13 = 0x03; /* WBXML 1.3 */
    public static final int WBXML_UNKNOWN_PI = 0x01; /* Unknown public identifier */
    public static final int WBXML_UTF8_ENCODING = 0x6A; /* UTF-8 encoding */
    private static final String TAG = "WBXML";
    private enum bufType { None, Base64, Str }

    private CodePage[] pageList;
    private Stack<String> xmlStack;
    /**
     * Initializes the object to a state ready for converting.
     *
     * @param codePages  Array of CodePage objects, their index in the array corresponds to page id
     */
    public WBXML() {        

        pageList = new CodePage[25];

        pageList[0] = new AirSyncCodePage();
        pageList[1] = new ContactsCodePage();
        pageList[2] = new EmailCodePage();
        pageList[3] = new AirNotifyCodePage();
        pageList[4] = new CalendarCodePage();
        pageList[5] = new MoveCodePage();
        pageList[6] = new ItemEstimateCodePage();
        pageList[7] = new FolderHierarchyCodePage();
        pageList[8] = new MeetingResponseCodePage();
        pageList[9] = new TasksCodePage();
        pageList[10] = new ResolveRecipientsCodePage();
        pageList[11] = new ValidateCertCodePage();
        pageList[12] = new Contacts2CodePage();
        pageList[13] = new PingCodePage();
        pageList[14] = new ProvisionCodePage();
        pageList[15] = new SearchCodePage();
        pageList[16] = new GALCodePage();
        pageList[17] = new AirSyncBaseCodePage();
        pageList[18] = new SettingsCodePage();
        pageList[19] = new DocumentLibraryCodePage();
        pageList[20] = new ItemOperationsCodePage();
        pageList[21] = new ComposeMailCodePage();
        pageList[22] = new Email2CodePage();
        pageList[23] = new NotesCodePage();
        pageList[24] = new RightsManagementCodePage();

        // Set the SAX2 parser
        //System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
    }


    /**
     * Sets the associated array of CodePage objects to be used for converting formats.
     *
     * @param codePages  Array of CodePage objects, their index in the array corresponds to page id
     */
    public void setCodePage(CodePage[] codePages) {
        pageList = codePages;
    }

    /**
     * Converts a WBXML input stream to an XML output stream
     *
     * @param in  The WBXML stream to read from
     * @param out The XML stream to write to
     */
    public void convertWbxmlToXml(InputStream in, OutputStream out) {
        BufferedInputStream istream = new BufferedInputStream(in);
        BufferedOutputStream ostream = new BufferedOutputStream(out);
        CodePage codepage = pageList[0];
        xmlStack = new Stack<String>();
        String buffer = new String();
        @SuppressWarnings("unused") int majorVersion = 0;
        @SuppressWarnings("unused") int minorVersion = 0;
        @SuppressWarnings("unused") int publicIdentifier = 0;
        int charset = 0;

        try {
            /* Populate the header information */
            int streamByte = istream.read();

            /* Major version is the high 4 bits + 1 */
            majorVersion = (streamByte >>> 4) + 1;
            /* Minor version is the low 4 bits */
            minorVersion = (streamByte & 15);
            publicIdentifier = istream.read();
            charset = istream.read();

            /* Next is the string table length.  ActiveSync doesn't use the string table */
            streamByte = istream.read();

            /* Send the header information to the output stream */
            if (charset == 0x6a) {
                /* ActiveSync only uses UTF-8, so we only support UTF-8 for now */
                buffer = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>";
                ostream.write(buffer.getBytes(), 0, buffer.length());
            } else {
                throw new IOException("Unknown charset encoding");
            }

            /* process the tags (recursive, state changes in recursion */
            processTagState(istream, ostream, codepage);

        } catch (IOException ioe) {
            logger.error(TAG + "IOException, doh: ", ioe);
            return;
        } catch (Exception e) {
            logger.error(TAG + "Exception e:", e);
            return;
        }
    }

    private long input_mbint(BufferedInputStream istream) throws IOException {
        long l = 0;
        byte b;
        do {
            b = (byte)istream.read();
            l <<= 7;
            l = l | ((long)b & 0x7f);
        } while ((b & 0x80) != 0);
        return l;
    }

    private void processTagState(BufferedInputStream istream,
                                 BufferedOutputStream ostream,
                                 CodePage codepage) throws IOException {
        int streamByte = istream.read();

        while (streamByte != -1) {
            int attribute = 0;
            String currentNamespace = codepage.getCodePageName();
            String outputBuffer = new String();

            /* Process WBXML tokens */
            if ((streamByte & 15) <= 0x4 && ((streamByte >>> 4) % 4) == 0) {
                /* Can't switch on a string, so switch on the raw value */
                switch (streamByte) {
                case 0x00: /* switch_page */
                    /* Change the current code page based on the next byte */
                    int nextByte = istream.read();
                    //if (pageList.length <= nextByte) {
                    // System.out.println("dog: nextByte="+nextByte+"  pageList.length="+pageList.length);
                    // System.out.println("pageList="+pageList);
                    //}
                    if (pageList[nextByte] != null) {
                        codepage = pageList[nextByte];
                    } 
                    break;
                case 0x01: /* end */
                    /* Pop the latest entry off the xml stack and close the tag */
                    if (!xmlStack.empty()) {
                        String tagName = (String) xmlStack.pop();
                        outputBuffer = "</"+tagName+">";
                    }
                    break;
                case 0x02: /* entity */
                    break;
                case 0x03: /* str_i */
                    ArrayList <Byte> inlineString = new ArrayList <Byte> (); 
                    //StringBuffer inlineString = new StringBuffer(1024);
                    byte stringByte = 0x00;
                    /* We need to process an indefinitely long string.  The terminator is
                     * based upon the charset encoding.  We only handle utf-8 right now,
                     * so our terminator is null (ie, 0x00) */
                    while ((stringByte = (byte) istream.read()) != 0) {
                        inlineString.add(stringByte);
                    }

                    //outputBuffer = inlineString.toString();
                    byte [] a = new byte[inlineString.size()];
                    int pos = 0;

                    for(Byte b : inlineString)
                        a[pos++] = b.byteValue();

                    outputBuffer = new String(a, "UTF-8");
                    outputBuffer = outputBuffer.replaceAll("<","WBXML_lt").replaceAll(">","WBXML_gt");

                    //logger.info("outputBuffer="+outputBuffer);
                    break;
                case 0x04: /* literal */
                    break;
                case 0x40: /* ext_i_0 */
                    break;
                case 0x41: /* ext_i_1 */
                    break;
                case 0x42: /* ext_i_2 */
                    break;
                case 0x43: /* pi */
                    break;
                case 0x44: /* literal_c */
                    break;
                case 0x80: /* ext_t_0 */
                    break;
                case 0x81: /* ext_t_1 */
                    break;
                case 0x82: /* ext_t_2 */
                    break;
                case 0x83: /* str_t */
                    break;
                case 0x84: /* literal_a */
                    break;
                case 0xc0: /* ext_0 */
                    break;
                case 0xc1: /* ext_1 */
                    break;
                case 0xc2: /* ext_2 */
                    break;
                case 0xc3: /* opaque */
                    /* If raw binary data is written to the output buffer, it can invalidate the XML document.
                     * Instead, append BASE64 to signify the data is base 64 encoded.
                     */
                    /* Opaque binary data.  Next byte is the length of the data */
                    long dataLength = input_mbint(istream);
                    byte[] data = new byte[(int) dataLength];
                    for (int i = 0; i < dataLength; i++) {
                        data[i] = (byte)istream.read();
                    }
                    /* Write the data we have to the output buffer */
                    outputBuffer = new String("BASE64");
                    outputBuffer = outputBuffer + DatatypeConverter.printBase64Binary(data);
                    break;
                case 0xc4: /* literal_ac */
                    break;

                }
            } else {
                /* Process tokens from the code page */
                String elementName = new String();
                /* If bit 6 is set, there is content */
                byte content = (byte)(streamByte & 64);

                if (content > 0) {
                    /* Remove the content flag */
                    streamByte = (streamByte ^ 64);
                }

                /* If bit 7 is set, there are attributes */
                attribute = (streamByte & 128);
                if (attribute > 0) {
                    /* Remove the attribute flag */
                    streamByte = (streamByte ^ 128);
                }
                elementName = codepage.getCodePageString(streamByte);
                outputBuffer = "<"+elementName;
                if (xmlStack.empty()) {
                    outputBuffer = outputBuffer + " xmlns=\""+currentNamespace+"\"";
                }
                //outputBuffer = "<"+currentNamespace+":"+elementName;

                /* If bit 6 is set, it has content */
                if (content > 0) {
                    //xmlStack.push(currentNamespace+":"+elementName);
                    xmlStack.push(elementName);
                }

                if (content > 0 && attribute == 0) {
                    outputBuffer = outputBuffer + ">";
                } else if (content == 0 && attribute == 0) {
                    outputBuffer = outputBuffer + "/>";
                }
            }

            if (outputBuffer.length() > 0) {
                ostream.write(outputBuffer.getBytes("UTF-8"));
                ostream.flush();
            }

            if (attribute > 0) {
                processAttributeState(istream, ostream, codepage);
            }

            streamByte = istream.read();
        }
    }

    // NOTE: ActiveSync doesn't use attributes.
    private void processAttributeState(BufferedInputStream istream,
                                       BufferedOutputStream ostream,
                                       CodePage codepage) throws IOException {
        boolean attributeDone = false;
        int streamByte = istream.read();
        String outputBuffer = new String();

        if (streamByte == -1) {
            return;
        }

        /* Process WBXML tokens */
        if ((streamByte & 15) <= 0x4 && ((streamByte >>> 4) % 4) == 0) {
            /* Can't switch on a string, so switch on the raw value */
            switch (streamByte) {
            case 0x00: /* switch_page */
                /* Change the current code page based on the next byte */
                int nextByte = istream.read();
                if (pageList[nextByte] != null) {
                    codepage = pageList[nextByte];
                } 
                break;
            case 0x01: /* end */
                /* End to attributes means the current tag is done */
                outputBuffer = ">";
                attributeDone = true;
                break;
            case 0x02: /* entity */
                break;
            case 0x03: /* str_i */
                StringBuffer inlineString = new StringBuffer(1024);
                int stringByte = 0x00;
                /* We need to process an indefinitely long string.  The terminator is
                 * based upon the charset encoding.  We only handle utf-8 right now,
                 * so our terminator is null (ie, 0x00) */
                while ((stringByte = istream.read()) > 0) {
                    inlineString.append((char) stringByte);
                }
                outputBuffer = inlineString.toString();
                break;
            case 0x04: /* literal */
                break;
            case 0x40: /* ext_i_0 */
                break;
            case 0x41: /* ext_i_1 */
                break;
            case 0x42: /* ext_i_2 */
                break;
            case 0x43: /* pi */
                break;
            case 0x44: /* literal_c */
                break;
            case 0x80: /* ext_t_0 */
                break;
            case 0x81: /* ext_t_1 */
                break;
            case 0x82: /* ext_t_2 */
                break;
            case 0x83: /* str_t */
                break;
            case 0x84: /* literal_a */
                break;
            case 0xc0: /* ext_0 */
                break;
            case 0xc1: /* ext_1 */
                break;
            case 0xc2: /* ext_2 */
                break;
            case 0xc3: /* opaque */
                /* If raw binary data is written to the output buffer, it can invalidate the XML document.
                 * Instead, append BASE64 to signify the data is base 64 encoded.
                 */
                /* Opaque binary data.  Next byte is the length of the data */
                byte dataLength = (byte)istream.read();
                byte[] data = new byte[dataLength];
                for (int i = 0; i < dataLength; i++) {
                    data[i] = (byte)istream.read();
                }
                /* Write the data we have to the output buffer */
                outputBuffer = new String("BASE64");
                outputBuffer = outputBuffer +  DatatypeConverter.printBase64Binary(data);
                break;
            case 0xc4: /* literal_ac */
                break;

            }
        } else {
            /* Process tokens from the code page */
            String element = new String();

            /* We only support single attribute statements.
             * This means we can't do fieldnametoken fieldvaluetoken,
             * only token = name="token"
             */
            element = codepage.getAttributeString(streamByte);
            outputBuffer = " " + element;
        }

        ostream.write(outputBuffer.getBytes(), 0, outputBuffer.length());
        ostream.flush();

        if (!attributeDone) {
            processAttributeState(istream, ostream, codepage);
        }
    }

    /**
     * Converts an XML input stream to a WBXML output stream
     *
     * @param in  The XML stream to read from
     * @param out The WBXML stream to write to
     */
    public void convertXmlToWbxml(InputStream in, OutputStream out) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            XMLHandler handler = new XMLHandler(out);
            xr.setContentHandler(handler);
            Reader reader = new InputStreamReader(in, "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            xr.parse(is);
        } 
        catch (SAXException se) {
            logger.error("WBXML SAXException in convertXmlToWbxml: ", se);
        } catch (IOException ioe) {
            logger.error("WBXML IOException in convertXmlToWbxml: ", ioe);
        }
    }

    /**
     * Handle parsing the XML data stream to convert to WBXML
     */
    public class XMLHandler extends DefaultHandler {
        private CodePage codepage = pageList[0];
        private BufferedOutputStream ostream;
        private ArrayList<Integer> pendingBuffer;
        private ArrayList<byte[]> binaryBuffer = new ArrayList<byte[]>();

        private class StackEl {
            int tagPos;
            boolean hasContent;
            public StackEl(int tagPos) {
                this.tagPos = tagPos;
                this.hasContent = false;
            }
            public void setHasContent() {
                this.hasContent = true;
            }
        }
        private Stack<StackEl> where = new Stack<StackEl>();
        private void hasContent(int n) {
            if (where.size() >= n) {
                where.get(where.size()-n).setHasContent();
            }
        }

        public XMLHandler(OutputStream out) {
            ostream = new BufferedOutputStream(out);
            pendingBuffer = new ArrayList<Integer>();
        }

        @Override
        public void startDocument() throws SAXException {
            /* Write our standard document header information */
            try {
                /* Version */
                ostream.write(0x03);
                /* Unkown public identifier */
                ostream.write(0x01);
                /* Only charset we currently use is UTF-8 */
                ostream.write(0x6a);
                /* We don't use string tables */
                ostream.write(0x00);
            } catch (IOException ioe) {
                throw new SAXException("IOException writing header: " + ioe);
            }
        }

        @Override
        public void endDocument() throws SAXException {
            /* Make sure the buffer's been written and is empty */
            if (pendingBuffer.size() > 0) {
                for (Integer i : pendingBuffer) {
                    try {
                        ostream.write(i.byteValue());
                    } catch (IOException ioe) {
                        throw new SAXException("IOException in writing buffer: " + ioe);
                    }
                }

                /* The buffer needs to be cleared */
                pendingBuffer = new ArrayList<Integer>();
            }

            /* Flush the stream so nothing's pending */
            try {
                ostream.flush();
            } catch (IOException ioe) {
                throw new SAXException("IOException flushing output stream: " + ioe);
            }
        }

        private void writePendingBuffer() throws SAXException {
            if (pendingBuffer.size() > 0) {
                for (Integer i : pendingBuffer) {
                    try {
                        Integer j = i >> 24;
                        if (j > 0) ostream.write(j);
                        j = i >> 16;
                        if (j > 0) ostream.write(j);
                        j = i >> 8;
                        if (j > 0) ostream.write(j);
                        ostream.write(i.byteValue());
                    } catch (IOException ioe) {
                        throw new SAXException("IOException writing buffer: " + ioe);
                    }
                }
                // The buffer needs to be cleared for the next set
                pendingBuffer = new ArrayList<Integer>();
            }
        }

        @Override
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) throws SAXException {
            where.push(new StackEl(pendingBuffer.size()));
            hasContent(2);
            if (namespaceURI.endsWith(":")) {
                namespaceURI = namespaceURI.substring(0, namespaceURI.length() - 1);
            }
            if (localName.equals("")) {
                if (!qName.equals("")) {
                    localName = qName.substring(qName.lastIndexOf(":")+1,qName.length());
                }
            }
            if (namespaceURI.equals("")) {
                if (!qName.equals("")) {
                    namespaceURI = qName.substring(0,qName.lastIndexOf(":")+1);
                }
            }

            int startToken = 0;

            /* The codepage needs to match the namespace so the correct bytes are written.
             * Unfortunately, there isn't a better way than just iterating over all of
             * the codepages.
             */
            if (!codepage.getCodePageName().equals(namespaceURI)) {
                for (int i = 0, count = pageList.length; i < count; i++) {
                    if (pageList[i].getCodePageName().equals(namespaceURI)) {
                        codepage = pageList[i];
                        /* Write the code page change to the stream */
                        try {
                            ostream.write(0x00);
                            ostream.write(codepage.getCodePageIndex());
                        } catch (IOException ioe) {
                            throw new SAXException("IOException writing page change: " + ioe);
                        }
                        i = count;
                    }
                }
            }

            startToken = codepage.getCodePageToken(localName);
            pendingBuffer.add(startToken);

            /* This is the only location where the attribute information is available */
            if (atts.getLength() > 0) {

                /* 7th bit represents attributes (128) */
                startToken = pendingBuffer.get(0);
                startToken |= 128;
                pendingBuffer.set(0, startToken);
                /* Each attribute information needs to be set in the buffer */
                for (int i = 0, count = atts.getLength(); i < count; i++) {
                    String attNamespace = atts.getURI(i);
                    String attLocalName = atts.getLocalName(i);
                    String attValue = atts.getValue(i);
                    /* We don't support name/value pairs yet, so lookup by full thing */
                    String fullValue = attLocalName + "=\"" + attValue +"\"";
                    Integer attToken = 0;

                    if (attNamespace.endsWith(":")) {
                        attNamespace = attNamespace.substring(0, attNamespace.length() - 1);
                    }

                    if (attNamespace.equals("")) {
                        attNamespace = namespaceURI;
                    }

                    /* It's possible to change namespaces mid attribute...stupid XML */
                    if (!attNamespace.equals("") &&
                        !attNamespace.equals(codepage.getCodePageName())) {

                        for (int j = 0, jcount = pageList.length - 1; j < jcount; j++) {
                            if (pageList[j].getCodePageName().equals(attNamespace)) {
                                codepage = pageList[j];
                                /* Add the page change to the buffer */
                                pendingBuffer.add(0x00);
                                pendingBuffer.add(j);

                                j = jcount;
                            }
                        }
                    }

                    attToken = codepage.getAttributeToken(fullValue);

                    if (attToken != -1) {
                        pendingBuffer.add(attToken);
                    }

                    /* End of attribute, add end tag */
                    pendingBuffer.add(0x01);
                }
            }
        }

        @Override
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if (binaryBuffer.size() > 0) {
                if (currentBuftype == bufType.Base64) {
                    int size = 0;
                    for (byte[] b : binaryBuffer) { size += b.length; }
                    byte[] encodedData = new byte[size];
                    int pos = 0;
                    for (byte[] b : binaryBuffer) {
                        for (int i = 0; i < b.length; i++) {
                            encodedData[pos++] = b[i];
                        }
                    }
                    byte[] decodedData = DatatypeConverter.parseBase64Binary(new String(encodedData));

                    /* Add the tag saying opaque data follows */
                    pendingBuffer.add(0xc3);

                    /* Add the length of opaque data */
                    long llength = decodedData.length;
                    byte[] len = of_long(llength);
                    for (int i = 0, count = len.length; i < count; i++) {
                        pendingBuffer.add((int) len[i]);
                    }
                    for (int i = 0, count = decodedData.length; i < count; i++) {
                        pendingBuffer.add((int) decodedData[i]);
                    }
                } else if (currentBuftype == bufType.Str) {
                    pendingBuffer.add(0x03);
                    for (byte[] b : binaryBuffer) {
                        for (int i = 0; i < b.length; i++) {
                            pendingBuffer.add((int)b[i]);
                        }
                    }
                    pendingBuffer.add(0x00);
                }
                binaryBuffer = new ArrayList<byte[]>();
            }
            currentBuftype = bufType.None;
            StackEl se;
            // Write the end tag
            try {
                se = where.peek();
                if (se.hasContent) pendingBuffer.add(0x01);
            } catch (EmptyStackException e) {
                pendingBuffer.add(0x01);
            }
            while (!where.empty()) {
                se = where.pop();
                //System.out.println(se.localName+": "+se.hasContent);
                if (se.hasContent) {
                    Integer tagByte = pendingBuffer.get(se.tagPos);
                    //logger.info("tagging byte (element) "+Integer.toString(tagByte,16)+" for "+se.localName);
                    pendingBuffer.set(se.tagPos, tagByte|64);
                }
            }
            writePendingBuffer();
        }

        private byte[] of_long(long i) {
            if (i == 0) {
                byte[] b = new byte[1];
                b[0] = 0;
                return b;
            }
            int j = 0;
            byte[] b = new byte[32];
            while (i > 0) {
                b[j++] = (byte) (i & 0x7f);
                i = i >>> 7;
            }
            byte[] mb = new byte[j];
            for (int k = 0; k < j; k++) {
                mb[j-k-1] = (k!=0)?(byte)((long) b[k] | 0x80):b[k];
            }
            return mb;
        }

        private bufType currentBuftype = bufType.None;

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {

            hasContent(1);

            // Check for whitespace
            String s = new String(ch, start, length);

            boolean isWhitespaceOnly = s.matches("\\s*");
            if (isWhitespaceOnly) return;

            String hexString = new String();

            if (length > 6) {
                hexString = new String(ch, start, 6);
            }

            /* If it needs to be opaque data, we use a cheap hack.  A string starting
             * with BASE64 is a base 64 encoded string and should be opaque data.
             * Really need to find a better way to deal with this.
             */
            // TODO: eliminate this dreadful hack.
            if (currentBuftype == bufType.None) {
                if (hexString.equals("BASE64")) {
                    String encodedData = new String(ch, start + 6, length - 6);
                    binaryBuffer.add(encodedData.getBytes());
                    currentBuftype = bufType.Base64;
                } else {
                    currentBuftype = bufType.Str;
                    byte[] bytes = s.getBytes();
                    binaryBuffer.add(bytes);
                }
            } else {
                binaryBuffer.add(s.getBytes());
            }

        }       
    }
}
