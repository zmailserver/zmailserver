/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

package org.zmail.cs.account.soap;

import java.util.Map;

import org.zmail.common.account.Key;
import org.zmail.common.account.Key.CosBy;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AdminConstants;
import org.zmail.cs.account.Cos;
import org.zmail.cs.account.Provisioning;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.soap.admin.type.Attr;
import org.zmail.soap.admin.type.CosInfo;

class SoapCos extends Cos implements SoapEntry {
    
    SoapCos(String name, String id, Map<String, Object> attrs, Provisioning prov) {
        super(name, id, attrs, prov);
    }

    SoapCos(CosInfo cosInfo, Provisioning prov) throws ServiceException {
        super(cosInfo.getName(), cosInfo.getId(),
                Attr.collectionToMap(cosInfo.getAttrList()), prov);
    }

    SoapCos(Element e, Provisioning prov) throws ServiceException {
        super(e.getAttribute(AdminConstants.A_NAME), e.getAttribute(AdminConstants.A_ID), SoapProvisioning.getAttrs(e), prov);
    }

    public void modifyAttrs(SoapProvisioning prov, Map<String, ? extends Object> attrs, boolean checkImmutable) throws ServiceException {
        XMLElement req = new XMLElement(AdminConstants.MODIFY_COS_REQUEST);
        req.addElement(AdminConstants.E_ID).setText(getId());
        SoapProvisioning.addAttrElements(req, attrs);
        setAttrs(SoapProvisioning.getAttrs(prov.invoke(req).getElement(AdminConstants.E_COS)));
    }

    public void reload(SoapProvisioning prov) throws ServiceException {
        XMLElement req = new XMLElement(AdminConstants.GET_COS_REQUEST);
        Element a = req.addElement(AdminConstants.E_COS);
        a.setText(getId());
        a.addAttribute(AdminConstants.A_BY, Key.CosBy.id.name());
        setAttrs(SoapProvisioning.getAttrs(prov.invoke(req).getElement(AdminConstants.E_COS)));
    }
}
