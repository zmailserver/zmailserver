/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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

import java.util.HashMap;
import java.util.ArrayList;
import java.util.logging.*;
import org.zmail.common.user_info;
import org.zmail.common.ZCSProvParams;
import org.zmail.common.DomainInfo;
import org.zmail.utils.*;
import org.zmail.auth.*;

public class ZCSACProvision 
{
    private String soapuri;
    private String zcsurl;
    private HashMap domainmap;
    private ZCSProvParams acprovprms;
    private Logger prog_logger;
    private ZCSAccounts zcsaccounts;
    private ZCSDomain zcsdomain;
    private boolean dump_all;
    private boolean IsInit;
    private ZMSoapSession acprov_session;
    public ZCSACProvision(ZCSProvParams provprms, Logger provLogger)
    {
        acprovprms=provprms;
        prog_logger=provLogger;
        soapuri="/service/admin/soap";
        if (acprovprms.zcsport!=null)
            zcsurl="https://"+acprovprms.zcsurl+":"+acprovprms.zcsport+soapuri;
        else
            zcsurl="https://"+acprovprms.zcsurl+soapuri;
        domainmap = new HashMap();
        dump_all=false;
        IsInit=false;
        acprov_session = new ZMSoapSession(zcsurl, acprovprms.adminname, acprovprms.adminpwd, ZMSoapSession.AUTH_TYPE_ADMIN,provLogger);
    }
    
    public void enable_dump_all()
    {
        dump_all=true;
    }
    
    public void disable_dump_all()
    {
        dump_all=false;
    }
    
    public boolean Init()
    {
        boolean retval=false;
        if (!IsInit)
        {
            try
            {
                ZCSUtils.set_logger(prog_logger);
                if (dump_all)
                    acprov_session.enable_dump_all();
                if (acprov_session.check_auth())
                {
                    zcsdomain =new ZCSDomain(acprov_session,prog_logger);
                    zcsaccounts =new ZCSAccounts(acprov_session,prog_logger);
                    retval=true;
                    IsInit=true;
                }
                else
                {
                    prog_logger.log(Level.WARNING,"AuthenticateToZCS Failed.");
                }
            }
            catch (Exception ex)
            {
                prog_logger.log(Level.SEVERE, "Init Error: "+ ex.getMessage());
                prog_logger.log(Level.SEVERE,ZCSUtils.stack2string(ex));
            }
        }
        else
        {
            retval=true;
        }
        return retval;
    }
    
    public void get_doamin_info(String domain)
    {
        boolean retval=true;
        if (!IsInit)
        {
            retval=Init();
        }
        if (retval)
        {
            zcsdomain.get_domain_info(domain);
        }
    }
    
    public boolean ModifyDomainAttribute(String domain, String attr, String value)
    {
        boolean retval=false;
        HashMap<String,String> attrs= new HashMap<String, String>();
        try
        {
            prog_logger.log(Level.INFO,"Adding/Modifying attribute "+attr+": "+value);
            attrs.put(attr, value);
            retval=zcsdomain.modify_domain(domain, attrs);
        }
        catch(Exception ex)
        {
            prog_logger.log(Level.SEVERE,"ModifyDomainAttribute Excpetion: "+ex.getMessage());
        }
        return retval;
    }
    
    public void SearchDirectory(String type, String attrs,String query, String Domain)
    {
        zcsdomain.DoDirectorySearch(type, attrs,query, Domain);
    }
    
    public boolean CheckDomain(String stDomain,String max_accounts,boolean custommode)
    {
        boolean bretval=true;
        if (domainmap.containsKey(stDomain))
            return true;
        
        int dmstatus= zcsdomain.get_domain_info(stDomain);
        if (dmstatus==ZCSDomain.DOMAIN_FOUND)
        {
            domainmap.put(stDomain, true);
        }
        else if (dmstatus==ZCSDomain.NO_SUCH_DOMAIN)
        {
            //create domian
            prog_logger.log(Level.INFO, "Creating Domain: "+stDomain);
            DomainInfo dmInfo = new DomainInfo();
            dmInfo.name=stDomain;
            dmInfo.GalMode="zmail";
            dmInfo.GalMaxresults="100";
            dmInfo.Description="";
            dmInfo.Notes="";
            dmInfo.AuthMechanism="zmail";
            dmInfo.DoaminCosID ="";
            dmInfo.PublicServiceHostName ="";
            dmInfo.DomainStatus="active";
            dmInfo.VirtualHosts = new ArrayList();
            //Add Virtual domains here
            //dmInfo.VirtualHosts.add("virtual_domain");
            dmInfo.ZmailLogOutUrl="http://mail."+stDomain;//Not used
            dmInfo.ZmailLoginUrl="http://mail."+stDomain;
            dmInfo.zmailDomainMaxAccounts=max_accounts;
            dmInfo.zmailAuthMech = acprovprms.zmailAuthMech;
            //preauth key
            try
            {
                dmInfo.preAuthkey= PreAuthKey.generateRandomPreAuthKey();
                prog_logger.log(Level.INFO,"PAK: "+dmInfo.name+": "+dmInfo.preAuthkey);
            }
            catch(AuthenticationException ex)
            {
                prog_logger.log(Level.SEVERE,"generateRandomPreAuthKey Excpetion: "+ex.getMessage());
            }
            
            /*create domain in custom mode by setting true.
             *  what it does:
                    --without authmech
            */ 
            if (zcsdomain.Create_Domain(dmInfo,custommode))
            {
                prog_logger.log(Level.INFO, "Domain "+stDomain +" created successfully");
                domainmap.put(stDomain, true);
            }
            else
            {
                prog_logger.log(Level.WARNING, "Domain "+stDomain +" creation Failed");
            }
        }
        else
        {
            //error
            prog_logger.log(Level.WARNING, "Domain "+stDomain +" Information error");
        }
            
        return bretval;
    }
    
    public boolean CreateAccount(user_info uinfo, final StringBuffer sb,boolean custommode)
    {
        boolean retval=true;
        zcsaccounts.set_user_info(uinfo);
        if (!zcsaccounts.create_Account(custommode, sb))
        {
            retval=false;
        }
        else
        {
            prog_logger.log(Level.INFO, "Created user: "+uinfo.username);
        }
        return retval;
    }
    
    public String GetAccountIDByName(String name)
    {
        String retval=null;
        if ((retval=zcsaccounts.GetAccountIDByName(name))==null)
        {
            prog_logger.log(Level.WARNING, "GetAccountInfoByName Failed: "+name);
        }
        if((retval !=null)&&(dump_all))
        {
            prog_logger.log(Level.INFO, "Success in GetAccountInfoByName: "+name);
        }
        return retval;
    }

    public boolean ModifyAccountMailTransport(String zmailid, String MailTransport)
    {
        boolean retval=false;
        if (!(retval=zcsaccounts.ModifyAccountMailTransport(zmailid,MailTransport)))
        {
            prog_logger.log(Level.WARNING, "ModifyAccountMailTransport Failed: "+zmailid);
        }
        if(dump_all)
        {
            prog_logger.log(Level.INFO, "Success in ModifyAccountMailTransport: "+zmailid);
        }
        return retval;
    }

    public boolean ModifyAccountStatus(String zmailid, String status)
    {
        boolean retval=false;
        if (!(retval=zcsaccounts.ModifyAccountStatus(zmailid,status)))
        {
            prog_logger.log(Level.WARNING, "ModifyAccountStatus Failed: "+zmailid);
        }
        if(dump_all)
        {
            prog_logger.log(Level.INFO, "Success in ModifyAccountStatus: "+zmailid);
        }
        return retval;
    }
    
    public boolean AddAccountAliasRequest(String zmailid, String alias)
    {
        boolean retval=false;
        if (!(retval=zcsaccounts.AddAccountAliasRequest(zmailid,alias)))
        {
            prog_logger.log(Level.WARNING, "AddAccountAliasRequest Failed: "+alias);
        }
        if(dump_all)
        {
            prog_logger.log(Level.INFO, "Success in AddAccountAliasRequest: "+alias);
        }

        return retval;
    }
    
    public boolean RemovePassword(String zmailid)
    {
        boolean retval=false;
        //set to empty pwd
        if (!(retval=zcsaccounts.SetPassword(zmailid,"")))
        {
            prog_logger.log(Level.WARNING, "RemovePassword Failed: "+zmailid);
        }
        if(dump_all)
        {
            prog_logger.log(Level.INFO, "Success in RemovePassword: "+zmailid);
        }
        return retval;
    }
    
    public boolean AddZMAuthMech(String Domain)
    {
        boolean retval=false;
        if (!(retval=ModifyDomainAttribute(Domain,"zmailAuthMech" ,acprovprms.zmailAuthMech)))
        {
            prog_logger.log(Level.WARNING, "AddZMAuthMech Failed: "+Domain);
        }
        if(dump_all)
        {
            prog_logger.log(Level.INFO, "Success in AddZMAuthMech: "+Domain);
        }
        return retval;
    }

    public ArrayList<String> GetDomainAllAccountList(String stDomain,String restURL,String adminAuthToken)
    {
        ArrayList<String> iArList=null;
        //Don't use it.SearchDirectoryRequest throws exception for large numbers of accounts. 
        //iArList=zcsaccounts.GetAllAccountsList(stDomain);
        //rest URL= https://<SourceZCSServer:port>/service/afd/?action=getSR&types=accounts
        iArList=zcsaccounts.GetAllAccountsListByREST(restURL,adminAuthToken);
        return iArList;
    }
}

