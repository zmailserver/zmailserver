/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
package org.zmail.cs.account.accesscontrol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zmail.common.account.Key;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.util.DateUtil;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.account.Account;
import org.zmail.cs.account.AttributeManager;
import org.zmail.cs.account.AttributeType;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Domain;
import org.zmail.cs.account.Entry;
import org.zmail.cs.account.Provisioning;
import org.zmail.cs.account.Server;
import org.zmail.soap.admin.type.ConstraintInfo;

public class AttributeConstraint {
    private static final String CONSTRAINT_CACHE_KEY = "CONSTRAINT_CACHE";
    private static final String PARTS_DELIMITER = ":";
    private static final String VALUES_DELIMITER = ",";
    
    private String mAttrName;
    private Set<String> mValues;
    
    AttributeConstraint(String attrName) {
        mAttrName = attrName;
    }
    
    private String getAttrName() {
        return mAttrName;
    }
    
    protected void setMin(String min) throws ServiceException {
        throw ServiceException.PARSE_ERROR("min constraint not supported for the attribute type", null);
    }
    
    protected void setMax(String max) throws ServiceException {
        throw ServiceException.PARSE_ERROR("max constraint not supported for attribute type", null);
    }
    
    private void addValue(String value) {
        if (mValues == null)
            mValues = new HashSet<String>();
        mValues.add(value);
    }
    
    private boolean isEmpty() {
        return getMin() == null && getMax() == null && getValues() == null;
    }
    
    private Set<String> getValues() {
        return mValues;
    }
    
    protected String getMin() {
        return null;
    }
    
    protected String getMax() {
        return null;
    }
    
    protected boolean violateMinMax(String value) throws ServiceException {
        return false;
    }
    
    private boolean violateValues(String value) {
        return (mValues != null && !mValues.contains(value));
    }
    
    protected boolean violated(Object value) throws ServiceException {
        if (value instanceof String) {
            if (violateValues((String)value))
                return true;
            if (violateMinMax((String)value))
                return true;
            
        } else if (value instanceof String[]) {
            for (String v : (String[])value) {
                if (violateValues(v))
                    return true;
                if (violateMinMax(v))
                    return true;
            }
        } else {
            throw ServiceException.FAILURE("internal error", null); // should not happen
        }
        
        return false;
    }
    
    /*
    boolean.....TRUE|FALSE
    binary......binary data
    duration....^\d+[hmsd]?$.  If [hmsd] is not specified, the default
                is seconds.
    gentime.....time expressed as \d{14}[zZ]
    enum........value attr is comma-separated list of valid values
    email.......valid email address. must have a "@" and no personal
                part.
    emailp......valid email address. must have a "@" and personal part
                is optional.
    id..........^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$
    integer.....32 bit signed, min/max checked
    port........0-65535
    regex.......value attr is a regular expression. Should explicitly
                add ^ to front and $ at the end
    string......currently just checks max length if specified
    astring.....IA5 string (almost ascii)
    cstring.....case sensitive string
    ostring.....octet string defined in LDAP
    */
    
    private static class IntegerConstraint extends AttributeConstraint {
        private Integer mMin;
        private Integer mMax;
        
        IntegerConstraint(String attrName) {
            super(attrName);
        }
        
        @Override
        protected void setMin(String min) {
            try {
                mMin = Integer.valueOf(min);
            } catch (NumberFormatException e) {
                // bad constraint, treat it as there is no min constraint  TODO: log?
            }
        }
        
        @Override
        protected void setMax(String max) {
            try {
                mMax = Integer.valueOf(max);
            } catch (NumberFormatException e) {
                // bad constraint, treat it as there is no max constraint TODO: log?
            }
        }
        
        @Override
        protected String getMin() {
            return (mMin == null)? null : mMin.toString();
        }
        
        @Override
        protected String getMax() {
            return (mMax == null)? null : mMax.toString();
        }
        
        @Override
        protected boolean violateMinMax(String valueStr) throws ServiceException {
            try {
                Integer value = Integer.valueOf(valueStr);
                if (mMin != null && value < mMin)
                    return true;
                
                if (mMax != null && value > mMax)
                    return true;
            } catch (NumberFormatException e) { 
                return true; // not a valid integer, bad
            }
            return false;
        }
    }
    
    private static class LongConstraint extends AttributeConstraint {
        private Long mMin;
        private Long mMax;
        
        LongConstraint(String attrName) {
            super(attrName);
        }
        
        @Override
        protected void setMin(String min) {
            try {
                mMin = Long.valueOf(min);
            } catch (NumberFormatException e) {
                // bad constraint, treat it as there is no min constraint  TODO: log?
            }
        }
        
        @Override
        protected void setMax(String max) {
            try {
                mMax = Long.valueOf(max);
            } catch (NumberFormatException e) {
                // bad constraint, treat it as there is no max constraint TODO: log?
            }
        }
        
        @Override
        protected String getMin() {
            return (mMin == null)? null : mMin.toString();
        }
        
        @Override
        protected String getMax() {
            return (mMax == null)? null : mMax.toString();
        }
        
        @Override
        protected boolean violateMinMax(String valueStr) throws ServiceException {
            try {
                Long value = Long.valueOf(valueStr);
                if (mMin != null && value < mMin)
                    return true;
                
                if (mMax != null && value > mMax)
                    return true;
            } catch (NumberFormatException e) { 
                return true; // not a valid integer, bad
            }
            return false;
        }
    }
    
    private static class DurationConstraint extends AttributeConstraint {
        private Long mMin;
        private Long mMax;
        
        DurationConstraint(String attrName) {
            super(attrName);
        }
        
        @Override
        protected void setMin(String min) {
            try {
                mMin = DateUtil.getTimeInterval(min);
            } catch (ServiceException e) {
                // bad constraint, treat it as there is no min constraint TODO: log?
            }
        }
        
        @Override
        protected void setMax(String max) {
            try {
                mMax = DateUtil.getTimeInterval(max);
            } catch (ServiceException e) {
                // bad constraint, treat it as there is no max constraint TODO: log?
            }
        }
        
        @Override
        protected String getMin() {
            return (mMin == null)? null : mMin.toString();
        }
        
        @Override
        protected String getMax() {
            return (mMax == null)? null : mMax.toString();
        }
        
        @Override
        protected boolean violateMinMax(String valueStr) throws ServiceException {
            try {
                Long value = DateUtil.getTimeInterval(valueStr);
                if (mMin != null && value < mMin)
                    return true;
                
                if (mMax != null && value > mMax)
                    return true;
            } catch (ServiceException e) { 
                return true; // not a valid interval, bad
            }
            return false;
        }
    }
    
    private static class GentimeConstraint extends AttributeConstraint {
        // milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object. 
        private Long mMin;  
        private Long mMax;
        
        GentimeConstraint(String attrName) {
            super(attrName);
        }
        
        @Override
        protected void setMin(String min) {
            // if cannot parse null will be returned, => no min constraint
            Date date = DateUtil.parseGeneralizedTime(min);
            if (date != null)
                mMin = date.getTime();
        }
        
        @Override
        protected void setMax(String max) {
            // if cannot parse null will be returned, => no max constraint
            Date date = DateUtil.parseGeneralizedTime(max);
            if (date != null)
                mMax = date.getTime();
        }
        
        @Override
        protected String getMin() {
            return (mMin == null)? null : mMin.toString();
        }
        
        @Override
        protected String getMax() {
            return (mMax == null)? null : mMax.toString();
        }
        
        @Override
        protected boolean violateMinMax(String valueStr) throws ServiceException {
            Date value = DateUtil.parseGeneralizedTime(valueStr);
            if (value == null)
                return true; // not a valid gentime, bad
            
            long mSecs = value.getTime();    
            if (mMin != null && mSecs < mMin)
                return true;
                
            if (mMax != null && mSecs > mMax)
                return true;
        
            return false;
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(getAttrName());
        
        String min = getMin();
        if (min != null)
            sb.append(PARTS_DELIMITER + "min=" + min);
        
        String max = getMax();
        if (max != null)
            sb.append(PARTS_DELIMITER + "max=" + max);
        
        Set<String> values = getValues();
        if (values != null && values.size() > 0) {
            sb.append(PARTS_DELIMITER + "values=");
            boolean first = true;
            for (String value : values) {
                if (!first)
                    sb.append(VALUES_DELIMITER);
                else
                    first = false;
                sb.append(value);
            }
        }
        
        return sb.toString();
    }
    
    private static AttributeConstraint fromString(AttributeManager am, String s) throws ServiceException  {
        String[] parts = s.split(PARTS_DELIMITER);
        if (parts.length < 2)
            throw ServiceException.PARSE_ERROR("invalid constraint: " + s, null);
            
        String attrName = parts[0];
        AttributeConstraint constraint = newConstratint(am, attrName);
        
        for (int i=1; i<parts.length; i++) {
            String part = parts[i];
            if (part.startsWith("min="))
                constraint.setMin(part.substring(4));
            else if (part.startsWith("max="))
                constraint.setMax(part.substring(4));
            else if (part.startsWith("values=")) {
                String values = part.substring(7);
                String[] vs = values.split(VALUES_DELIMITER);
                for (String v : vs)
                    constraint.addValue(v);
            }
        }
        
        return constraint;
    }

    /**
     * Returns an {@code AttributeConstraint} corresponding to the supplied
     * {@code attrName) and {@code eConstraint}
     * Note: returns null if {@code eConstraint} is null
     */
    public static AttributeConstraint fromJaxb(AttributeManager am,
            String attrName, ConstraintInfo eConstraint)
    throws ServiceException {
        
        if (eConstraint == null)
            return null;
        AttributeConstraint constraint =
            AttributeConstraint.newConstratint(am, attrName);
        constraint.setMin(eConstraint.getMin());
        constraint.setMax(eConstraint.getMax());
        for (String value : eConstraint.getValues())
            constraint.addValue(value);
        return constraint;
    }

    public static AttributeConstraint fromXML(AttributeManager am, String attrName, Element eConstraint) throws ServiceException {
        
        AttributeConstraint constraint = AttributeConstraint.newConstratint(am, attrName);
            
        Element eMin = eConstraint.getOptionalElement(AdminConstants.E_MIN);
        if (eMin != null)
            constraint.setMin(eMin.getText());
                
        Element eMax = eConstraint.getOptionalElement(AdminConstants.E_MAX);
        if (eMax != null)
            constraint.setMax(eMax.getText());
          
        Element eValues = eConstraint.getOptionalElement(AdminConstants.E_VALUES);
        if (eValues != null) {
            for (Element eValue : eValues.listElements(AdminConstants.E_VALUE))
                constraint.addValue(eValue.getText());
        }
            
        return constraint;     
    }
    
    public void toXML(Element eParent) {
        Element eConstraint = eParent.addElement(AdminConstants.E_CONSTRAINT);
        
        String min = getMin();
        if (min != null)
            eConstraint.addElement(AdminConstants.E_MIN).setText(min);
        
        String max = getMax();
        if (max != null)
            eConstraint.addElement(AdminConstants.E_MAX).setText(max);
        
        Set<String> values = getValues();
        if (values != null) {
            Element eValues = eConstraint.addElement(AdminConstants.E_VALUES);
            for (String v : values)
                eValues.addElement(AdminConstants.E_VALUE).setText(v);
        }
    }
        
    static AttributeConstraint newConstratint(AttributeManager am, String attrName) throws ServiceException {
        AttributeType at = am.getAttributeType(attrName);
        
        switch (at) {
        case TYPE_BOOLEAN:
            return new AttributeConstraint(attrName);
        case TYPE_DURATION:
            return new DurationConstraint(attrName);
        case TYPE_GENTIME:
            return new GentimeConstraint(attrName);
        case TYPE_EMAIL:
        case TYPE_EMAILP:
        case TYPE_CS_EMAILP:
        case TYPE_ENUM:
        case TYPE_ID:
            return new AttributeConstraint(attrName);
        case TYPE_INTEGER:
            return new IntegerConstraint(attrName);
        case TYPE_PORT:
            return new IntegerConstraint(attrName);
        case TYPE_PHONE:
        case TYPE_STRING:
        case TYPE_ASTRING:
        case TYPE_OSTRING:
        case TYPE_CSTRING:
        case TYPE_REGEX:
        case TYPE_BINARY:
        case TYPE_CERTIFICATE:      
            return new AttributeConstraint(attrName);
        case TYPE_LONG:
            return new LongConstraint(attrName);
        }
        
        throw ServiceException.FAILURE("internal error", null);
    }
    
    static Entry getConstraintEntry(Entry entry) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        Entry constraintEntry = null;
        
        if (entry instanceof Account)
            constraintEntry = prov.getCOS((Account)entry);
        else if (entry instanceof Domain || entry instanceof Server)
            constraintEntry = prov.getConfig();
        
        return constraintEntry;
    }
    
    public static Map<String, AttributeConstraint> getConstraint(Entry constraintEntry) throws ServiceException {
        
        Map<String, AttributeConstraint> constraints = (Map<String, AttributeConstraint>)constraintEntry.getCachedData(CONSTRAINT_CACHE_KEY);
        if (constraints == null) {
            //
            // if there is no zmailConstraint, we get an empty Set from getMultiAttrSet
            // and we will cache and return an empty set of AttributeConstraint.  
            // This is good because we do not want to repeatedly read LDAP if zmailConstraint
            // is not set
            //
            constraints = loadConstraints(constraintEntry);
            constraintEntry.setCachedData(CONSTRAINT_CACHE_KEY, constraints);
        }
        
        return constraints;
    }
    
    private static Map<String, AttributeConstraint> loadConstraints(Entry constraintEntry) throws ServiceException {
        Map<String, AttributeConstraint> constraints = new HashMap<String, AttributeConstraint>();

        Set<String> cstrnts = constraintEntry.getMultiAttrSet(Provisioning.A_zmailConstraint);
        
        AttributeManager am = AttributeManager.getInstance();
        for (String c : cstrnts) {
            AttributeConstraint constraint = AttributeConstraint.fromString(am, c);
            constraints.put(constraint.getAttrName(), constraint);
        }
            
        return constraints;
    }
    
    public static void modifyConstraint(Entry constraintEntry, List<AttributeConstraint> newConstraints) throws ServiceException {
        
        // current constraints
        Map<String, AttributeConstraint> curConstraints = loadConstraints(constraintEntry);
        
        for (AttributeConstraint newConstraintsForAttr : newConstraints) {
            String attrName = newConstraintsForAttr.getAttrName();
            AttributeConstraint curConstraintsForAttr = curConstraints.get(attrName);
            
            if (curConstraintsForAttr != null) {
                // currently there are constraints for the attr
                if (newConstraintsForAttr.isEmpty()) {
                    // new constraints for the attr is empty, remove the current constraints for the attr
                    curConstraints.remove(attrName);
                } else {
                    // new constraints for the attr is not empty, replace with the new constraints
                    curConstraints.put(attrName, newConstraintsForAttr);
                }
            } else {
                // currently there is no constraint for the attr
                // add the constraints for the attr if it is not empty
                if (!newConstraintsForAttr.isEmpty()) {
                    curConstraints.put(attrName, newConstraintsForAttr);
                }
            }
            
        }
        
        // curConstraints now contains the new values
        // update LDAP
        Map<String, Object> newAttrValues = new HashMap<String, Object>();
        if (curConstraints.size() == 0)
            newAttrValues.put(Provisioning.A_zmailConstraint, null);
        else {
            List<String> newValues = new ArrayList<String>();
            for (AttributeConstraint at : curConstraints.values()) {
                newValues.add(at.toString());
            }
            
            newAttrValues.put(Provisioning.A_zmailConstraint, newValues.toArray(new String[newValues.size()]));
        }
        Provisioning.getInstance().modifyAttrs(constraintEntry, newAttrValues);
    }
    
    private static boolean ignoreConstraint(String attrName) {
        return (Provisioning.A_zmailCOSId.equals(attrName) ||
                Provisioning.A_zmailDomainDefaultCOSId.equals(attrName));
    }

    static boolean violateConstraint(Map<String, AttributeConstraint> constraints, String attrName, Object value) throws ServiceException {
        AttributeConstraint constraint = constraints.get(attrName);
        if (constraint == null)
            return false;
        
        if (ignoreConstraint(attrName)) {
            ZmailLog.acl.warn("Constraint for " + attrName + " is not suported and is ignored.");
            return false;
        }
        
        // throw exception if constraint is violated so it can be bubbled all the way up to 
        // the admin, otherwise it's too much pain to find out why PERM_DENIED. 
        // this is a bit deviated from rest the ACL checking code, which return false instead
        // of throwing Exception.
        
        boolean violated = constraint.violated(value);  
        if (violated)
            throw ServiceException.PERM_DENIED("constraint violated: " + constraint.getAttrName());
        return violated;
    }
    
    private static void test(Map<String, AttributeConstraint> constraints, String attrName, Object value, boolean expected) throws ServiceException {
        boolean violated = false;
        try {
            violated = violateConstraint(constraints, attrName, value);
        } catch (ServiceException e) {
            if (ServiceException.PERM_DENIED.equals(e.getCode()))
                violated = true;
            else
                throw e;
        }
        
        StringBuilder sb = new StringBuilder();
        if (value instanceof String[]) {
            for (String s : (String[])value)
                sb.append(s + " ");
        } else
            sb.append(value.toString());
            
            
        System.out.println("Setting " + attrName + " to " + "[" + sb.toString() + "]" + " => " + (violated?"denied":"allowed"));
        if (violated != expected)
            System.out.println("failed\n");
    }
    /**
     * @param args
     */
    public static void main(String[] args) throws ServiceException  {
        Provisioning prov = Provisioning.getInstance();
        AttributeManager am = AttributeManager.getInstance();
        
        AttributeConstraint.fromString(am, "zmailPasswordMinLength:min=6");
        AttributeConstraint.fromString(am, "zmailPasswordMaxLength:min=64");
        AttributeConstraint.fromString(am, "zmailPasswordMinLength:min=6:max=64:values=1,2,3");
        AttributeConstraint.fromString(am, "zmailFeatureMailEnabled:values=FALSE,TRUE");
        
        Account acct = prov.get(Key.AccountBy.name, "user1@phoebe.mac");
        Cos cos = prov.getCOS(acct);
        cos.unsetConstraint();
        
        Map<String, Object> cosConstraints = new HashMap<String,Object>();
        
        // integer
        cos.addConstraint("zmailPasswordMinLength:min=6:max=10:values=8,9", cosConstraints);
        
        // long
        long longMax = Long.MAX_VALUE;
        long longMaxMinusOne = longMax - 1;
        cos.addConstraint("zmailMailQuota:max="+longMaxMinusOne, cosConstraints);
        
        // duration
        cos.addConstraint("zmailPasswordLockoutDuration:min=5h:max=1d", cosConstraints);
        
        // gentime
        cos.addConstraint("zmailPrefPop3DownloadSince:min=20060315023000Z", cosConstraints);
        
        // string
        cos.addConstraint("zmailPrefGroupMailBy:values=conversation", cosConstraints);
        
        // bad constraint, invalid min constraint
        cos.addConstraint("zmailCalendarMaxRevisions:min=zz:max=10", cosConstraints);
        
        // multi-value
        cos.addConstraint("zmailZimletAvailableZimlets:values=A,B,C", cosConstraints);
        
        
        prov.modifyAttrs(cos, cosConstraints);
        
        Map<String, AttributeConstraint> constraints = getConstraint(cos);
        // integer
        test(constraints, "zmailPasswordMinLength", "5", true);
        test(constraints, "zmailPasswordMinLength", "6", true);
        test(constraints, "zmailPasswordMinLength", "7", true);
        test(constraints, "zmailPasswordMinLength", "8", false);
        test(constraints, "zmailPasswordMinLength", "9", false);
        test(constraints, "zmailPasswordMinLength", "10", true);
        test(constraints, "zmailPasswordMinLength", "11", true);
        
        // long 
        test(constraints, "zmailMailQuota", "" + longMaxMinusOne, false);
        test(constraints, "zmailMailQuota", "" + longMax, true);
        
        // duration
        test(constraints, "zmailPasswordLockoutDuration", "3h", true);
        test(constraints, "zmailPasswordLockoutDuration", "25h", true);
        test(constraints, "zmailPasswordLockoutDuration", "30m", true);
        test(constraints, "zmailPasswordLockoutDuration", "5h", false);
        test(constraints, "zmailPasswordLockoutDuration", "600m", false);
        test(constraints, "zmailPasswordLockoutDuration", "24h", false);
        
        // gentime 
        test(constraints, "zmailPrefPop3DownloadSince", "20050315023000Z", true);
        test(constraints, "zmailPrefPop3DownloadSince", "20060315023000Z", false);
        test(constraints, "zmailPrefPop3DownloadSince", "20060315023001Z", false);
        
        // string, enum, ...
        test(constraints, "zmailPrefGroupMailBy", "message", true);
        test(constraints, "zmailPrefGroupMailBy", "conversation", false);
        
        // bad constraint
        test(constraints, "zmailCalendarMaxRevisions", "1", false);
        test(constraints, "zmailCalendarMaxRevisions", "10", false);
        test(constraints, "zmailCalendarMaxRevisions", "11", true);
        
        // multi-value
        test(constraints, "zmailZimletAvailableZimlets", new String[]{"A","B"}, false);
        test(constraints, "zmailZimletAvailableZimlets", new String[]{"A","X"}, true);
        
        test(constraints, "zmailPasswordMaxLength", "100", false); // no constraint
    }

}
