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
package org.zmail.cs.index.query;

import org.apache.lucene.analysis.Analyzer;

import org.zmail.cs.index.DBQueryOperation;
import org.zmail.cs.index.LuceneFields;
import org.zmail.cs.index.QueryOperation;
import org.zmail.cs.mailbox.Mailbox;

/**
 * Query by subject.
 *
 * @author tim
 * @author ysasaki
 */
public final class SubjectQuery extends Query {
    private String subject;
    private boolean lt;
    private boolean inclusive;

    /**
     * This is only used for subject queries that start with {@code <} or {@code >}, otherwise we just use the normal
     * {@link TextQuery}.
     */
    private SubjectQuery(String text) {
        lt = (text.charAt(0) == '<');
        inclusive = false;
        subject = text.substring(1);

        if (subject.charAt(0) == '=') {
            inclusive = true;
            subject = subject.substring(1);
        }
    }

    public static Query create(Analyzer analyzer, String text) {
        if (text.length() > 1 && (text.startsWith("<") || text.startsWith(">"))) {
            // real subject query!
            return new SubjectQuery(text);
        } else {
            return new TextQuery(analyzer, LuceneFields.L_H_SUBJECT, text);
        }
    }

    @Override
    public boolean hasTextOperation() {
        return false;
    }

    @Override
    public QueryOperation compile(Mailbox mbox, boolean bool) {
        DBQueryOperation op = new DBQueryOperation();
        if (lt) {
            op.addSubjectRange(null, false, subject, inclusive, evalBool(bool));
        } else {
            op.addSubjectRange(subject, inclusive, null, false, evalBool(bool));
        }
        return op;
    }

    @Override
    public void dump(StringBuilder out) {
        out.append("SUBJECT:");
        out.append(lt ? '<' : '>');
        if (inclusive) {
            out.append('=');
        }
        out.append(subject);
    }

    @Override
    public void sanitizedDump(StringBuilder out) {
        out.append("SUBJECT:");
        out.append(lt ? '<' : '>');
        if (inclusive) {
            out.append('=');
        }
        out.append("$TEXT");
    }
}
