/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2012 VMware, Inc.
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
package com.zimbra.common.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author pshao
 */
public class CsvWriter {

    private BufferedWriter writer;
    
    public CsvWriter(Writer writer) throws IOException {
        this.writer = new BufferedWriter(writer);
    }
    
    public void writeRow(String... values) throws IOException {
        StringBuilder line = new StringBuilder();
        
        boolean first = true;
        for (String value : values) {
            if (!first) {
                line.append(","); 
            } else {
                first = false;
            }
            line.append(value);
        }
        line.append("\n");
        writer.write(line.toString());
    }
    
    public void close() throws IOException {
        writer.close();
    }
}
