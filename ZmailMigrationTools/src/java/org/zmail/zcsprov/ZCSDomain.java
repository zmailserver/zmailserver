/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2009, 2012 VMware, Inc.
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
package org.zmail.zcsprov;

import java.util.logging.*;
import java.util.HashMap;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.Name;
import org.zmail.common.DomainInfo;
import org.zmail.utils.*;


public class ZCSDomain
{
    private Logger dm_logger;
    ZMSoapSession zmsession;
    private String Domain_id;
    public static final int GET_DOMAIN_ERROR=0;
    public static final int NO_SUCH_DOMAIN = 1;
    public static final int DOMAIN_FOUND = 2;
    public ZCSDomain(ZMSoapSession session, Logger logger)
    {
        zmsession = session;
        dm_logger= logger;
        zmsession.check_auth();
        Domain_id=null;
    }
    
    public String get_domain_id(String stDomain)
    {
        String domainid=null;
        if (get_domain_info(stDomain)==DOMAIN_FOUND)
        {
            domainid=Domain_id;
        }
        return domainid;
    }
    
    public boolean modify_domain(String domain, HashMap<String, String> attrs)
    {
        boolean retval=false;
        String tdmid=null;
        if (get_domain_id(domain)!=null)
        {
            tdmid= Domain_id;
        }
        else
        {
            dm_logger.log(Level.SEVERE,"get_domain_id failed skipping modify_domain");
            return false;
        }
        
        try
        {
            SOAPMessage request=zmsession.get_requestObjectWithZmHeader();
            SOAPPart sp = request.getSOAPPart();
            SOAPEnvelope se = (SOAPEnvelope)sp.getEnvelope();
            SOAPBody body = se.getBody();
            
            //create SOAP Body
            Name bodyName = se.createName("ModifyDomainRequest", "","urn:zmailAdmin");
            SOAPElement bodyElement = body.addBodyElement(bodyName);
            
            //<id>2c2d5329-7881-4897-b89f-7d70fc2c4fd7</id>
            SOAPElement nidelement=bodyElement.addChildElement("id");
            nidelement.setValue(tdmid);
            
            zmsession.AddAttributes(se, bodyElement, attrs);
            
            //Save the message
            request.saveChanges();

            //Get Response
            if (zmsession.send_request(request))
            {
                String sfault=zmsession.FindNodeValue("faultstring");
                if (sfault!=null)
                {
                    dm_logger.log(Level.SEVERE,"modify_domain SOAP fault: "+sfault);
                }
                else
                {
                    retval=true;
                }
            }
            else
            {
                zmsession.dump_response("modify_domain Failed ");
            }
            
        }
        catch(Exception e)
        {
            retval= false;
            dm_logger.log(Level.SEVERE,"modify_domain Excpetion: "+e.getMessage());
            dm_logger.log(Level.SEVERE, ZCSUtils.stack2string(e));
        }
        return retval;
    }
    
    public int get_domain_info(String stDomain)
    {
        int retval= DOMAIN_FOUND;
        try
        {
            SOAPMessage request=zmsession.get_requestObjectWithZmHeader();
            SOAPPart sp = request.getSOAPPart();
            SOAPEnvelope se = (SOAPEnvelope)sp.getEnvelope();
            SOAPBody body = se.getBody();
            
            //create SOAP Body
            Name bodyName = se.createName("GetDomainRequest", "","urn:zmailAdmin");
            SOAPElement bodyElement = body.addBodyElement(bodyName);
            //<domain by="name">amitabh-zmail</domain>
            Name ne = se.createName("by");
            SOAPElement sea = bodyElement.addChildElement("domain");
            sea.addAttribute(ne, "name");
            sea.addTextNode(stDomain);
            
            //Save the message
            request.saveChanges();

            //Get Response
            if (zmsession.send_request(request))
            {
                String sfault=zmsession.FindNodeValue("faultstring");
                if (sfault!=null)
                {
                    dm_logger.log(Level.SEVERE,"GetDomain SOAP fault: "+sfault);
                    String sfcode=zmsession.FindNodeValue("Code");
                    if (sfcode.indexOf("NO_SUCH_DOMAIN", 0)!= -1)
                        retval= NO_SUCH_DOMAIN;
                    else
                        retval= GET_DOMAIN_ERROR;
                }
                else
                {
                    Domain_id= zmsession.FindAttributeValue("domain","id");
                    dm_logger.log(Level.INFO,"Domain Found: "+stDomain);
                    retval= DOMAIN_FOUND;
                }
            }
            else
            {
                zmsession.dump_response("get_domain_info Failed ");
            }
        }
        catch(Exception e)
        {
            retval= GET_DOMAIN_ERROR;
            dm_logger.log(Level.SEVERE,"get_domain Excpetion: "+e.getMessage());
            dm_logger.log(Level.SEVERE, ZCSUtils.stack2string(e));
        }
        
        return retval;
    }
    
    //iscustom will be used to create domain without some options e.g. without
    //authmech which hinders the IMAPSync.
    public boolean Create_Domain(DomainInfo dmInfo, boolean IsCustom)
    {
        HashMap<String,String> attrs= new HashMap<String, String>();
        boolean retval= false;
        try
        {
            SOAPMessage request=zmsession.get_requestObjectWithZmHeader();
            SOAPPart sp = request.getSOAPPart();
            SOAPEnvelope se = (SOAPEnvelope)sp.getEnvelope();
            SOAPBody body = se.getBody();
            
            //create SOAP Body
            Name bodyName = se.createName("CreateDomainRequest", "","urn:zmailAdmin");
            SOAPElement bodyElement = body.addBodyElement(bodyName);
            //<name>test_domain1</name>
            SOAPElement nelement=bodyElement.addChildElement("name");
            nelement.setValue(dmInfo.name);
            //add <a n="zmailGalMode">ldap</a>
            attrs.put("zmailGalMode", dmInfo.GalMode);            
            //add <a n="zmailGalMaxResults">100</a>
            attrs.put("zmailGalMaxResults", dmInfo.GalMaxresults);            
            //<a n="zmailNotes">test_domain description here</a>
            attrs.put("zmailNotes", dmInfo.Notes);            
            //<a n="description">test domain</a>
            attrs.put("description", dmInfo.Description);            
            //<a n="zmailDomainDefaultCOSId">3378d8f9-2517-44d8-89c5-85b18016e1a5</a>
            attrs.put("zmailDomainDefaultCOSId", dmInfo.DoaminCosID);            
            //<a n="zmailPublicServiceHostname">public service host name here</a>
            attrs.put("zmailPublicServiceHostname", dmInfo.PublicServiceHostName);            
            //<a n="zmailDomainStatus">active</a>
            attrs.put("zmailDomainStatus", dmInfo.DomainStatus);            
                    
/*  //should be part of Global config     
 *          attrs.put("zmailWebClientLogoutURL", dmInfo.ZmailLogOutUrl);                   
*/          
            attrs.put("zmailWebClientLoginURL", dmInfo.ZmailLoginUrl);                   
            attrs.put("zmailDomainMaxAccounts", dmInfo.zmailDomainMaxAccounts);                   
            //preauth key
            attrs.put("zmailPreAuthKey", dmInfo.preAuthkey);                   
                         
            //<a n="zmailVirtualHostname">VHtest_doamin1</a>
            if (dmInfo.VirtualHosts !=null)
            {
                for (int i=0; i<dmInfo.VirtualHosts.size();i++)
                {
                    attrs.put("zmailVirtualHostname", (String)dmInfo.VirtualHosts.get(i));                   
                }
            }
            //if galmode is ldap or both then add following elements
            if ((dmInfo.GalMode.compareTo("ldap")==0) ||
                (dmInfo.GalMode.compareTo("both")==0))
            {
                //<a n="zmailGalLdapURL">ldaps://10.66.118.114:636</a>
                attrs.put("zmailGalLdapURL", dmInfo.GalLDAPUrl);                   
                //<a n="zmailGalLdapSearchBase">dc=test_doamin2</a>
                attrs.put("zmailGalLdapSearchBase", dmInfo.GalLDAPSearchBase);                   
                //<a n="zmailGalLdapBindDn">dn</a>
                attrs.put("zmailGalLdapBindDn", dmInfo.GalLDAPBindDN);                   
                //<a n="zmailGalLdapBindPassword">test123</a>
                attrs.put("zmailGalLdapBindPassword", dmInfo.GalLDAPBindPwd);                   
                //<a n="zmailGalLdapFilter">*</a>
                attrs.put("zmailGalLdapFilter", dmInfo.GalLDAPFilter);                   
                //<a n="zmailGalAutoCompleteLdapFilter">*</a>
                attrs.put("zmailGalAutoCompleteLdapFilter", dmInfo.GalAutoCLDAPFilter);                   
            }
            
            if (!IsCustom)
            {
                //zmailAuthMech
                attrs.put("zmailAuthMech", dmInfo.zmailAuthMech);  
            }
            zmsession.AddAttributes(se, bodyElement, attrs);
            //Save the message
            request.saveChanges();
                
            //Get Response
            if (zmsession.send_request(request))
            {
                String sfault=zmsession.FindNodeValue("faultstring");
                if (sfault!=null)
                {
                    dm_logger.log(Level.SEVERE,"CreateDomain SOAP fault: "+sfault);
                }
                else
                {
                    retval= true;
                }
            }
            else
            {
                zmsession.dump_response("Create_Domain Failed ");
            }    
        }
        catch(Exception e)
        {
            dm_logger.log(Level.SEVERE,"create_Domain Excpetion: "+e.getMessage());
            dm_logger.log(Level.SEVERE, ZCSUtils.stack2string(e));
        }
        return retval;
    }
    
    public boolean DoDirectorySearch(String type,String attrs,String query, String domain)
    {
        boolean retval=false;
        try
        {
            SOAPMessage request=zmsession.get_requestObjectWithZmHeader();
            SOAPPart sp = request.getSOAPPart();
            SOAPEnvelope se = (SOAPEnvelope)sp.getEnvelope();
            SOAPBody body = se.getBody();
            
            //create SOAP Body
            Name bodyName = se.createName("SearchDirectoryRequest", "","urn:zmailAdmin");
            SOAPElement bodyElement = body.addBodyElement(bodyName);
            //if (!domain.isEmpty())
            if(!(domain != null && domain.length() == 0))
            {
                Name dmnm=se.createName("domain");
                bodyElement.addAttribute(dmnm, domain);
            }
            Name onm=se.createName("offset");
            bodyElement.addAttribute(onm, "0");
            Name lmtnm=se.createName("limit");
            bodyElement.addAttribute(lmtnm, "50");
            //Name sortnm= se.createName("sortBy");
            //bodyElement.addAttribute(sortnm, "zmailDomainName");
            Name sortAnm= se.createName("sortAscending");
            bodyElement.addAttribute(sortAnm, "1");
            Name attrnm= se.createName("attrs");
            bodyElement.addAttribute(attrnm, attrs);            
            Name typenm= se.createName("types");
            bodyElement.addAttribute(typenm, type);
            
            //<query xmlns=""></query>
            Name nqe = se.createName("xmlns",""," ");
            SOAPElement sea = bodyElement.addChildElement("query");
            sea.addAttribute(nqe, " ");  
            sea.addTextNode(query);
            
            //Save the message
            request.saveChanges();
            zmsession.enable_dump_all();    
            //Get Response
            if (zmsession.send_request(request))
            {
                String sfault=zmsession.FindNodeValue("faultstring");
                if (sfault!=null)
                {
                    dm_logger.log(Level.SEVERE,"DoDirectorySearch SOAP fault: "+sfault);
                }
                else
                {
                    retval= true;
                }
            }
            else
            {
                zmsession.dump_response("DoDirectorySearch Failed ");
            }    
        }
        catch(Exception e)
        {
            dm_logger.log(Level.SEVERE,"DoDirectorySearch Excpetion: "+e.getMessage());
            dm_logger.log(Level.SEVERE, ZCSUtils.stack2string(e));
        }
        return retval;
    }
}
