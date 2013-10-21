/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.soap.admin.type;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.util.StringUtil;
import org.zmail.soap.type.KeyValuePair;
import org.zmail.soap.type.KeyValuePairs;
import org.zmail.soap.json.jackson.annotate.ZmailKeyValuePairs;

/*
 * Used for JAXB objects representing elements which have child node(s) of form:
 *     <a n="{key}">{value}</a>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class AdminKeyValuePairs implements KeyValuePairs {

    /**
     * @zm-api-field-description Key Value pairs
     */
    @ZmailKeyValuePairs
    @XmlElement(name=AdminConstants.E_A /* a */, required=false)
    private List<KeyValuePair> keyValuePairs;

    public AdminKeyValuePairs() {
    }

    public AdminKeyValuePairs(Iterable<KeyValuePair> keyValuePairs) {
        setKeyValuePairs(keyValuePairs);
    }

    public AdminKeyValuePairs (Map<String, ? extends Object> keyValuePairs)
    throws ServiceException {
        setKeyValuePairs(keyValuePairs);
    }

    public void setKeyValuePairs(
                    List<KeyValuePair> keyValuePairs) {
        if (this.keyValuePairs == null) {
            this.keyValuePairs = Lists.newArrayList();
        }
        this.keyValuePairs.clear();
        if (keyValuePairs != null) {
            Iterables.addAll(this.keyValuePairs, keyValuePairs);
        }
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        if (keyValuePairs == null) {
            keyValuePairs = Lists.newArrayList();
        }
        // Making the return of this unmodifiable causes
        // "UnsupportedOperationException" on unmarshalling - see Bug 62187.
        //     return Collections.unmodifiableList(keyValuePairs);
        return keyValuePairs;
    }

    @Override
    public void setKeyValuePairs(Iterable<KeyValuePair> keyValues) {
        if (this.keyValuePairs == null) {
            this.keyValuePairs = Lists.newArrayList();
        }
        this.keyValuePairs.clear();
        if (keyValues != null) {
            Iterables.addAll(this.keyValuePairs, keyValues);
        }
    }

    @Override
    public void setKeyValuePairs(Map<String, ? extends Object> keyValues)
            throws ServiceException {
        this.setKeyValuePairs(KeyValuePair.fromMap(keyValues));
    }

    @Override
    public void addKeyValuePair(KeyValuePair keyValue) {
        if (this.keyValuePairs == null) {
            this.keyValuePairs = Lists.newArrayList();
        }
        keyValuePairs.add(keyValue);
    }

    @Override
    public Multimap<String, String> getKeyValuePairsMultimap() {
        return KeyValuePair.toMultimap(keyValuePairs);
    }

    @Override
    public Map<String, Object> getKeyValuePairsAsOldMultimap() {
        return StringUtil.toOldMultimap(getKeyValuePairsMultimap());
    }

    /**
     * Returns the first value matching {@link key} or null if {@link key} not found.
     */
    @Override
    public String firstValueForKey(String key) {
        for (KeyValuePair kvp : keyValuePairs) {
            if (key.equals(kvp.getKey())) {
                return kvp.getValue();
            }
        }
        return null;
    }

    @Override
    public List<String> valuesForKey(String key) {
        List<String> values = Lists.newArrayList();
        for (KeyValuePair kvp : keyValuePairs) {
            if (key.equals(kvp.getKey())) {
                values.add(kvp.getValue());
            }
        }
        return Collections.unmodifiableList(values);
    }

    public Objects.ToStringHelper addToStringInfo(
                Objects.ToStringHelper helper) {
        return helper
            .add("keyValuePairs", keyValuePairs);
    }
}
