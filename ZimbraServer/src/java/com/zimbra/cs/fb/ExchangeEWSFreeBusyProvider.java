/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.fb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.microsoft.schemas.exchange.services._2006.messages.CreateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemType;
import com.microsoft.schemas.exchange.services._2006.messages.ExchangeServicePortType;
import com.microsoft.schemas.exchange.services._2006.messages.ExchangeWebService;
import com.microsoft.schemas.exchange.services._2006.messages.FindFolderResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.FindFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.FindFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.FindItemType;
import com.microsoft.schemas.exchange.services._2006.messages.FolderInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.FreeBusyResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.GetFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.GetUserAvailabilityRequestType;
import com.microsoft.schemas.exchange.services._2006.messages.GetUserAvailabilityResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.ResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.UpdateItemType;
import com.microsoft.schemas.exchange.services._2006.types.*;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.Constants;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Config;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.AccountBy;
import com.zimbra.cs.fb.ExchangeFreeBusyProvider.AuthScheme;
import com.zimbra.cs.fb.ExchangeFreeBusyProvider.ExchangeUserResolver;
import com.zimbra.cs.fb.ExchangeFreeBusyProvider.ServerInfo;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.MailItem.Type;

public class ExchangeEWSFreeBusyProvider extends FreeBusyProvider {
    public static final int FB_INTERVAL = 30;
	public static final String TYPE_EWS = "ews";
    private ExchangeServicePortType service = null;
    private static ExchangeWebService factory = null;
    
    static {
        ZimbraLog.fb.debug("Setting MailcapCommandMap handlers back to default");
        MailcapCommandMap mc = (MailcapCommandMap)CommandMap.getDefaultCommandMap();
        mc.addMailcap("application/xml;;x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/xml;;x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;;x-java-content-handler=com.sun.mail.handlers.text_plain");
        CommandMap.setDefaultCommandMap(mc);
        ZimbraLog.fb.debug("Done Setting MailcapCommandMap handlers");
        
        URL wsdlUrl = ExchangeWebService.class.getResource("/Services.wsdl");
        factory = new ExchangeWebService(wsdlUrl,
                new QName("http://schemas.microsoft.com/exchange/services/2006/messages",
                    "ExchangeWebService"));
    }

    boolean initService(ServerInfo info) throws MalformedURLException {
        service = factory.getExchangeWebPort();

        ((BindingProvider)service).getRequestContext()
            .put(BindingProvider.USERNAME_PROPERTY, info.authUsername);
        ((BindingProvider)service).getRequestContext()
            .put(BindingProvider.PASSWORD_PROPERTY, info.authPassword);
        ((BindingProvider)service).getRequestContext()
            .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, info.url);

        // TODO: make sure we're passing authentication
        return true;
    }

    private static TrustManager[] trustAllCerts =
        new TrustManager[] { new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                String authType) throws CertificateException {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                String authType) throws CertificateException {

            }

        } };

    private static HostnameVerifier hv = new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {

            return true;// accept all
        }
    };

    /* Enable and call the following static block in initService() to accept self signed certificates */
    /* private static void setSSLConfig() throws Exception {
         SSLContext context = SSLContext.getInstance("SSL");
         context.init(null, trustAllCerts, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
         HttpsURLConnection.setDefaultHostnameVerifier(hv);
     }
    */

    private static class BasicUserResolver implements ExchangeUserResolver {
        @Override
        public ServerInfo getServerInfo(String emailAddr) {
            String url =
                getAttr(Provisioning.A_zimbraFreebusyExchangeURL, emailAddr);
            String user =
                getAttr(Provisioning.A_zimbraFreebusyExchangeAuthUsername,
                    emailAddr);
            String pass =
                getAttr(Provisioning.A_zimbraFreebusyExchangeAuthPassword,
                    emailAddr);
            String scheme =
                getAttr(Provisioning.A_zimbraFreebusyExchangeAuthScheme,
                    emailAddr);
            if (url == null || user == null || pass == null || scheme == null)
                return null;

            ServerInfo info = new ServerInfo();
            info.url = url;
            info.authUsername = user;
            info.authPassword = pass;
            info.scheme = AuthScheme.valueOf(scheme);
            info.org =
                getAttr(Provisioning.A_zimbraFreebusyExchangeUserOrg, emailAddr);
            try {
                Account acct =
                    Provisioning.getInstance().get(AccountBy.name, emailAddr);
                if (acct != null) {
                    String fps[] =
                        acct.getMultiAttr(Provisioning.A_zimbraForeignPrincipal);
                    if (fps != null && fps.length > 0) {
                        for (String fp : fps) {
                            if (fp.startsWith(Provisioning.FP_PREFIX_AD)) {
                                int idx = fp.indexOf(':');
                                if (idx != -1) {
                                    info.cn = fp.substring(idx + 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (ServiceException se) {
                info.cn = null;
            }
			String exchangeType = getAttr(Provisioning.A_zimbraFreebusyExchangeServerType, emailAddr);
			info.enabled = TYPE_EWS.equals(exchangeType);
            return info;
        }

        // first lookup account/cos, then domain, then globalConfig.
        static String getAttr(String attr, String emailAddr) {
            String val = null;
            if (attr == null)
                return val;
            try {
                Provisioning prov = Provisioning.getInstance();
                if (emailAddr != null) {
                    Account acct = prov.get(AccountBy.name, emailAddr);
                    if (acct != null) {
                        val = acct.getAttr(attr, null);
                        if (val != null)
                            return val;
                        Domain dom = prov.getDomain(acct);
                        if (dom != null)
                            val = dom.getAttr(attr, null);
                        if (val != null)
                            return val;
                    }
                }
                val = prov.getConfig().getAttr(attr, null);
            } catch (ServiceException se) {
                ZimbraLog.fb.error("can't get attr " + attr, se);
            }
            return val;
        }
    }

    public ExchangeEWSFreeBusyProvider() {
        mRequests = new HashMap<String, ArrayList<Request>>();
    }

    public boolean registerForMailboxChanges() {
        if (sRESOLVERS.size() > 1)
            return true;
        Config config = null;
        try {
            config = Provisioning.getInstance().getConfig();
        } catch (ServiceException se) {
            ZimbraLog.fb.warn("cannot fetch config", se);
            return false;
        }
        String url =
            config.getAttr(Provisioning.A_zimbraFreebusyExchangeURL, null);
        String user =
            config.getAttr(Provisioning.A_zimbraFreebusyExchangeAuthUsername,
                null);
        String pass =
            config.getAttr(Provisioning.A_zimbraFreebusyExchangeAuthPassword,
                null);
        String scheme =
            config.getAttr(Provisioning.A_zimbraFreebusyExchangeAuthScheme,
                null);
        return (url != null && user != null && pass != null && scheme != null);
    }

    BaseFolderType bindFolder(
        DistinguishedFolderIdNameType distinguishedFolderId,
        DefaultShapeNamesType shapeType) {
        final DistinguishedFolderIdType distinguishedFolderIdType =
            new DistinguishedFolderIdType();
        distinguishedFolderIdType.setId(distinguishedFolderId);
        final NonEmptyArrayOfBaseFolderIdsType nonEmptyArrayOfBaseFolderIdsType =
            new NonEmptyArrayOfBaseFolderIdsType();
        nonEmptyArrayOfBaseFolderIdsType.getFolderIdOrDistinguishedFolderId()
            .add(distinguishedFolderIdType);
        GetFolderType getFolderRequest = new GetFolderType();
        getFolderRequest.setFolderIds(nonEmptyArrayOfBaseFolderIdsType);
        FolderResponseShapeType stResp = new FolderResponseShapeType();
        stResp.setBaseShape(shapeType);
        getFolderRequest.setFolderShape(stResp);
        RequestServerVersion serverVersion = new RequestServerVersion();
        serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);
        Holder<ServerVersionInfo> gfversionInfo =
            new Holder<ServerVersionInfo>();
        Holder<GetFolderResponseType> gfresponseHolder =
            new Holder<GetFolderResponseType>();
        service.getFolder(getFolderRequest,
            serverVersion,
            gfresponseHolder,
            gfversionInfo);
        FolderInfoResponseMessageType firmtResp =
            (FolderInfoResponseMessageType)gfresponseHolder.value.getResponseMessages()
                .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
                .get(0)
                .getValue();

        if (firmtResp.getFolders()
            .getFolderOrCalendarFolderOrContactsFolder()
            .size() > 0) {
            return firmtResp.getFolders()
                .getFolderOrCalendarFolderOrContactsFolder()
                .get(0);
        } else {
            ZimbraLog.fb.error("Could not find the folder in Exchange : " + distinguishedFolderId.toString());
        }

        return null;
    }

    List<BaseFolderType> findFolderByProp(FolderIdType id,
        UnindexedFieldURIType prop, String val) {
        FindFolderType findFolderRequest = new FindFolderType();

        findFolderRequest.setTraversal(FolderQueryTraversalType.SHALLOW);
        final NonEmptyArrayOfBaseFolderIdsType ffEmptyArrayOfBaseFolderIdsType =
            new NonEmptyArrayOfBaseFolderIdsType();
        ffEmptyArrayOfBaseFolderIdsType.getFolderIdOrDistinguishedFolderId()
            .add(id);
        FolderResponseShapeType stResp = new FolderResponseShapeType();
        stResp.setBaseShape(DefaultShapeNamesType.ID_ONLY);
        findFolderRequest.setParentFolderIds(ffEmptyArrayOfBaseFolderIdsType);
        findFolderRequest.setFolderShape(stResp);

        RestrictionType rtRestriction = new RestrictionType();

        IsEqualToType ieq = new IsEqualToType();

        PathToUnindexedFieldType pix = new PathToUnindexedFieldType();
        pix.setFieldURI(prop);
        ieq.setPath(new JAXBElement<PathToUnindexedFieldType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
            "FieldURI"),
            PathToUnindexedFieldType.class,
            pix));

        FieldURIOrConstantType ct = new FieldURIOrConstantType();
        ConstantValueType cv = new ConstantValueType();
        cv.setValue(val);
        ct.setConstant(cv);

        ieq.setFieldURIOrConstant(ct);

        rtRestriction.setSearchExpression(new JAXBElement<SearchExpressionType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
            "IsEqualTo"),
            SearchExpressionType.class,
            (SearchExpressionType)ieq));

        findFolderRequest.setRestriction(rtRestriction);

        Holder<FindFolderResponseType> findFolderResponse =
            new Holder<FindFolderResponseType>();
        RequestServerVersion serverVersion = new RequestServerVersion();
        serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);
        Holder<ServerVersionInfo> gfversionInfo =
            new Holder<ServerVersionInfo>();

        service.findFolder(findFolderRequest,
            serverVersion,
            findFolderResponse,
            gfversionInfo);
        FindFolderResponseMessageType ffRespMessage =
            (FindFolderResponseMessageType)findFolderResponse.value.getResponseMessages()
                .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
                .get(0)
                .getValue();
        if (ResponseClassType.SUCCESS == ffRespMessage.getResponseClass()) {
            return ffRespMessage.getRootFolder()
                .getFolders()
                .getFolderOrCalendarFolderOrContactsFolder();
        }
        ZimbraLog.fb.warn("findFolderByProp " + ffRespMessage.getResponseCode());
        return null;
    }

    List<BaseFolderType> findFolderByPartialProp(FolderIdType id,
        UnindexedFieldURIType prop, String val) {
        FindFolderType findFolderRequest = new FindFolderType();

        findFolderRequest.setTraversal(FolderQueryTraversalType.SHALLOW);
        final NonEmptyArrayOfBaseFolderIdsType ffEmptyArrayOfBaseFolderIdsType =
            new NonEmptyArrayOfBaseFolderIdsType();
        ffEmptyArrayOfBaseFolderIdsType.getFolderIdOrDistinguishedFolderId()
            .add(id);
        FolderResponseShapeType stResp = new FolderResponseShapeType();
        stResp.setBaseShape(DefaultShapeNamesType.ID_ONLY);
        findFolderRequest.setParentFolderIds(ffEmptyArrayOfBaseFolderIdsType);
        findFolderRequest.setFolderShape(stResp);

        RestrictionType rtRestriction = new RestrictionType();

        ContainsExpressionType contains = new ContainsExpressionType();
        PathToUnindexedFieldType pix = new PathToUnindexedFieldType();
        pix.setFieldURI(prop);
        contains.setPath(new JAXBElement<PathToUnindexedFieldType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
            "FieldURI"),
            PathToUnindexedFieldType.class,
            pix));

        FieldURIOrConstantType ct = new FieldURIOrConstantType();
        ConstantValueType cv = new ConstantValueType();
        cv.setValue(val);
        ct.setConstant(cv);

        contains.setConstant(cv);
        contains.setContainmentMode(ContainmentModeType.SUBSTRING);

        rtRestriction.setSearchExpression(new JAXBElement<SearchExpressionType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
            "Contains"),
            SearchExpressionType.class,
            (SearchExpressionType)contains));

        findFolderRequest.setRestriction(rtRestriction);

        Holder<FindFolderResponseType> findFolderResponse =
            new Holder<FindFolderResponseType>();
        RequestServerVersion serverVersion = new RequestServerVersion();
        serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);
        Holder<ServerVersionInfo> gfversionInfo =
            new Holder<ServerVersionInfo>();

        service.findFolder(findFolderRequest,
            serverVersion,
            findFolderResponse,
            gfversionInfo);
        FindFolderResponseMessageType ffRespMessage =
            (FindFolderResponseMessageType)findFolderResponse.value.getResponseMessages()
                .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
                .get(0)
                .getValue();
        if (ResponseClassType.SUCCESS == ffRespMessage.getResponseClass()) {
            return ffRespMessage.getRootFolder()
                .getFolders()
                .getFolderOrCalendarFolderOrContactsFolder();
        }
        ZimbraLog.fb.warn("findFolderByPartialProp " +
            ffRespMessage.getResponseCode());
        return null;
    }

    List<ItemType> findItemByProp(FolderIdType id, UnindexedFieldURIType prop,
        String val, DefaultShapeNamesType shapeType) {
        FindItemType findItemRequest = new FindItemType();

        RestrictionType rtRestriction = new RestrictionType();

        IsEqualToType ieq = new IsEqualToType();

        PathToUnindexedFieldType pix = new PathToUnindexedFieldType();
        pix.setFieldURI(prop);
        ieq.setPath(new JAXBElement<PathToUnindexedFieldType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
            "FieldURI"),
            PathToUnindexedFieldType.class,
            pix));

        FieldURIOrConstantType ct = new FieldURIOrConstantType();
        ConstantValueType cv = new ConstantValueType();
        cv.setValue(val);
        ct.setConstant(cv);

        ieq.setFieldURIOrConstant(ct);

        rtRestriction.setSearchExpression(new JAXBElement<SearchExpressionType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
            "IsEqualTo"),
            SearchExpressionType.class,
            (SearchExpressionType)ieq));

        findItemRequest.setRestriction(rtRestriction);

        ItemResponseShapeType stShape = new ItemResponseShapeType();
        stShape.setBaseShape(shapeType);
        findItemRequest.setItemShape(stShape);
        NonEmptyArrayOfBaseFolderIdsType ids =
            new NonEmptyArrayOfBaseFolderIdsType();
        ids.getFolderIdOrDistinguishedFolderId().add(id);
        findItemRequest.setParentFolderIds(ids);
        findItemRequest.setTraversal(ItemQueryTraversalType.SHALLOW);

        RequestServerVersion serverVersion = new RequestServerVersion();
        serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);

        Holder<FindItemResponseType> fiResponse =
            new Holder<FindItemResponseType>();
        Holder<ServerVersionInfo> gfversionInfo =
            new Holder<ServerVersionInfo>();
        service.findItem(findItemRequest,
            serverVersion,
            fiResponse,
            gfversionInfo);

        FindItemResponseMessageType fiRespMessage =
            (FindItemResponseMessageType)fiResponse.value.getResponseMessages()
                .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
                .get(0)
                .getValue();

        if (ResponseClassType.SUCCESS == fiRespMessage.getResponseClass()) {
            return fiRespMessage.getRootFolder()
                .getItems()
                .getItemOrMessageOrCalendarItem();
        }
        ZimbraLog.fb.warn("findItemByProp " + fiRespMessage.getResponseCode());
        return null;
    }

    public boolean handleMailboxChange(String accountId) {
        ZimbraLog.fb.debug("Entering handleMailboxChange() for account : " + accountId);
        String email = getEmailAddress(accountId);
		ServerInfo serverInfo = getServerInfo(email);
		if (email == null || !serverInfo.enabled) {
		    ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
			return true;  // no retry
		}
		
        FreeBusy fb;
        try {
            fb = getFreeBusy(accountId, FreeBusyQuery.CALENDAR_FOLDER_ALL);
        } catch (ServiceException se) {
            ZimbraLog.fb.warn("can't get freebusy for account " + accountId, se);
            ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
            // retry the request if it's receivers fault.
            return !se.isReceiversFault();
        }
        if (email == null || fb == null) {
            ZimbraLog.fb.warn("account not found / incorrect / wrong host: " +
                accountId);
            ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
            return true; // no retry
        }

        if (serverInfo == null || serverInfo.org == null ||
            serverInfo.cn == null) {
            ZimbraLog.fb.warn("no exchange server info for user " + email);
            ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
            return true; // no retry
        }
        if (null == service) {
            try {
                if (!initService(serverInfo)) {
                    ZimbraLog.fb.error("failed to initialize exchange service object " +
                        serverInfo.url);
                    ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
                    return true;
                }
            } catch (MalformedURLException e) {
                ZimbraLog.fb.error("exception while trying to initialize exchange service object " +
                    serverInfo.url);
                ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
                return true;
            }
        }
        ExchangeEWSMessage msg =
            new ExchangeEWSMessage(serverInfo.org, serverInfo.cn, email);

        try {
            FolderType publicFolderRoot =
                (FolderType)bindFolder(DistinguishedFolderIdNameType.PUBLICFOLDERSROOT,
                    DefaultShapeNamesType.ALL_PROPERTIES);
            if (publicFolderRoot == null) {
                ZimbraLog.fb.error("Could not find the public root folder on exchange");
                return true;
            }
            List<BaseFolderType> resultsNonIpm =
                findFolderByProp(publicFolderRoot.getParentFolderId(),
                    UnindexedFieldURIType.FOLDER_DISPLAY_NAME,
                    "NON_IPM_SUBTREE");

            if (resultsNonIpm != null && resultsNonIpm.size() > 0) {
                FolderType folderNonIPM = (FolderType)resultsNonIpm.get(0);

                List<BaseFolderType> resultSchedulePlus =
                    findFolderByProp(folderNonIPM.getFolderId(),
                        UnindexedFieldURIType.FOLDER_DISPLAY_NAME,
                        "SCHEDULE+ FREE BUSY");
                if (resultSchedulePlus != null && resultSchedulePlus.size() > 0) {
                    FolderType folderSchedulePlus =
                        (FolderType)resultSchedulePlus.get(0);

                    List<BaseFolderType> resultFBFolder =
                        findFolderByPartialProp(folderSchedulePlus.getFolderId(),
                            UnindexedFieldURIType.FOLDER_DISPLAY_NAME,
                            serverInfo.org);// TODO: check here for partial name
                    // search
                    if (resultFBFolder != null && resultFBFolder.size() > 0) {
                        FolderType folderFB = (FolderType)resultFBFolder.get(0);

                        List<ItemType> resultMessage =
                            findItemByProp(folderFB.getFolderId(),
                                UnindexedFieldURIType.ITEM_SUBJECT,
                                "USER-/CN=RECIPIENTS/CN=" +
                                getForeignPrincipal(accountId),
                                DefaultShapeNamesType.ALL_PROPERTIES);
                        if (resultMessage != null && resultMessage.size() > 0) {
                            // edit message
                            ItemType itemMessage = resultMessage.get(0);

                            Map<PathToExtendedFieldType, NonEmptyArrayOfPropertyValuesType> props =
                                msg.GetFreeBusyProperties(fb);

                            final NonEmptyArrayOfItemChangeDescriptionsType cdExPropArr =
                                new NonEmptyArrayOfItemChangeDescriptionsType();
                            for (PathToExtendedFieldType pathExProp : props.keySet()) {
                                ItemType itemEmptyMessage = new ItemType();
                                SetItemFieldType sifItem =
                                    new SetItemFieldType();
                                sifItem.setPath(new JAXBElement<PathToExtendedFieldType>(new QName("http://schemas.microsoft.com/exchange/services/2006/types",
                                    "Path"),
                                    PathToExtendedFieldType.class,
                                    pathExProp));
                                ExtendedPropertyType exProp =
                                    new ExtendedPropertyType();
                                exProp.setExtendedFieldURI(pathExProp);
                                if (pathExProp.getPropertyType() == MapiPropertyTypeType.APPLICATION_TIME_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.BINARY_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.CLSID_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.CURRENCY_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.DOUBLE_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.FLOAT_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.INTEGER_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.LONG_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.OBJECT_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.SHORT_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.STRING_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.SYSTEM_TIME_ARRAY) {
                                    exProp.setValues(props.get(pathExProp));
                                } else {
                                    if (props.get(pathExProp).getValue().size() > 0) {
                                        exProp.setValue(props.get(pathExProp)
                                            .getValue()
                                            .get(0));
                                    }
                                }
                                itemEmptyMessage.getExtendedProperty()
                                    .add(exProp);
                                sifItem.setItem(itemEmptyMessage);
                                cdExPropArr.getAppendToItemFieldOrSetItemFieldOrDeleteItemField()
                                    .add(sifItem);

                            }
                            UpdateItemType updateItemRequest =
                                new UpdateItemType();
                            updateItemRequest.setMessageDisposition(MessageDispositionType.SAVE_ONLY);
                            updateItemRequest.setConflictResolution(ConflictResolutionType.ALWAYS_OVERWRITE);
                            RequestServerVersion serverVersion =
                                new RequestServerVersion();
                            serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);

                            ItemChangeType itemExpropChange =
                                new ItemChangeType();
                            itemExpropChange.setItemId(itemMessage.getItemId());
                            itemExpropChange.setUpdates(cdExPropArr);
                            final NonEmptyArrayOfItemChangesType ctExPropArr =
                                new NonEmptyArrayOfItemChangesType();
                            ctExPropArr.getItemChange().add(itemExpropChange);
                            updateItemRequest.setItemChanges(ctExPropArr);

                            Holder<UpdateItemResponseType> updateItemResponse =
                                new Holder<UpdateItemResponseType>();
                            Holder<ServerVersionInfo> gfversionInfo =
                                new Holder<ServerVersionInfo>();
                            service.updateItem(updateItemRequest,
                                serverVersion,
                                updateItemResponse,
                                gfversionInfo);
                            ResponseMessageType updateItemResponseMessage =
                                updateItemResponse.value.getResponseMessages()
                                    .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
                                    .get(0)
                                    .getValue();

                        } else {
                            // create message
                            PostItemType itemMessage = new PostItemType();

                            itemMessage.setSubject("USER-/CN=RECIPIENTS/CN=" +
                            	getForeignPrincipal(accountId));
                            itemMessage.setItemClass("IPM.Post");

                            Map<PathToExtendedFieldType, NonEmptyArrayOfPropertyValuesType> props =
                                msg.GetFreeBusyProperties(fb);

                            for (PathToExtendedFieldType pathExProp : props.keySet()) {
                                ExtendedPropertyType exProp =
                                    new ExtendedPropertyType();
                                exProp.setExtendedFieldURI(pathExProp);
                                if (pathExProp.getPropertyType() == MapiPropertyTypeType.APPLICATION_TIME_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.BINARY_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.CLSID_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.CURRENCY_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.DOUBLE_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.FLOAT_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.INTEGER_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.LONG_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.OBJECT_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.SHORT_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.STRING_ARRAY ||
                                    pathExProp.getPropertyType() == MapiPropertyTypeType.SYSTEM_TIME_ARRAY) {
                                    exProp.setValues(props.get(pathExProp));
                                } else {
                                    if (props.get(pathExProp).getValue().size() > 0) {
                                        exProp.setValue(props.get(pathExProp)
                                            .getValue()
                                            .get(0));
                                    }
                                }
                                itemMessage.getExtendedProperty().add(exProp);
                            }

                            CreateItemType createItemRequest =
                                new CreateItemType();
                            RequestServerVersion serverVersion =
                                new RequestServerVersion();
                            serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);
                            createItemRequest.setMessageDisposition(MessageDispositionType.SAVE_ONLY);
                            TargetFolderIdType idTargetFolder =
                                new TargetFolderIdType();
                            idTargetFolder.setFolderId(folderFB.getFolderId());
                            createItemRequest.setSavedItemFolderId(idTargetFolder);
                            NonEmptyArrayOfAllItemsType createItems =
                                new NonEmptyArrayOfAllItemsType();
                            createItems.getItemOrMessageOrCalendarItem()
                                .add(itemMessage);
                            createItemRequest.setItems(createItems);
                            Holder<CreateItemResponseType> createItemResponse =
                                new Holder<CreateItemResponseType>();
                            Holder<ServerVersionInfo> gfversionInfo =
                                new Holder<ServerVersionInfo>();
                            service.createItem(createItemRequest,
                                serverVersion,
                                createItemResponse,
                                gfversionInfo);
                            ResponseMessageType createItemResponseMessage =
                                createItemResponse.value.getResponseMessages()
                                    .getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()
                                    .get(0)
                                    .getValue();

                        }
                    } else {
                        ZimbraLog.fb.error("Could not find the Exchange folder containing '" + serverInfo.org + 
                                "'. Make sure zimbraFreebusyExchangeUserOrg is configured correctly and it exists on Exchange");
                    }
                } else {
                    ZimbraLog.fb.error("Could not find the Exchange folder 'SCHEDULE+ FREE BUSY'");
                }
            } else {
                ZimbraLog.fb.error("Could not find the Exchange folder 'NON_IPM_SUBTREE'");
            }

            return true;

        } catch (Exception e) {
            ZimbraLog.fb.error("error commucating to " + serverInfo.url, e);
        } finally {
            ZimbraLog.fb.debug("Exiting handleMailboxChange() for account : " + accountId);
        }

        return false;// retry
    }

    public List<FreeBusy>
        getFreeBusyForHost(String host, ArrayList<Request> req)
            throws IOException {
        List<FreeBusyResponseType> results = null;
        ArrayList<FreeBusy> ret = new ArrayList<FreeBusy>();

		Request r = req.get(0);
		ServerInfo serverInfo = (ServerInfo) r.data;
		if (serverInfo == null) {
			ZimbraLog.fb.warn("no exchange server info for user "+r.email);
			return ret;
		}
		
		if (!serverInfo.enabled)
			return ret;
		
        ArrayOfMailboxData attendees = new ArrayOfMailboxData();

        for (Request request : req) {
            EmailAddress email = new EmailAddress();
            email.setAddress(request.email);
            MailboxData mailbox = new MailboxData();
            mailbox.setEmail(email);
            mailbox.setAttendeeType(MeetingAttendeeType.REQUIRED);
            attendees.getMailboxData().add(mailbox);
        }
        try {
            Duration duration = new Duration();
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

            GregorianCalendar gregorianCalStart = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            gregorianCalStart.setTimeInMillis(req.get(0).start);
            duration.setStartTime(datatypeFactory.newXMLGregorianCalendar(gregorianCalStart));

            GregorianCalendar gregorianCalEnd = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            gregorianCalEnd.setTimeInMillis(req.get(0).end);
            duration.setEndTime(datatypeFactory.newXMLGregorianCalendar(gregorianCalEnd));

            FreeBusyViewOptionsType availabilityOpts =
                new FreeBusyViewOptionsType();
            availabilityOpts.setMergedFreeBusyIntervalInMinutes(FB_INTERVAL);

            availabilityOpts.getRequestedView().add("MergedOnly");
            availabilityOpts.setTimeWindow(duration);
            GetUserAvailabilityRequestType availabilityRequest =
                new GetUserAvailabilityRequestType();
            // TODO: check if we need to set request timezone
            SerializableTimeZone timezone = new SerializableTimeZone();
            timezone.setBias(0);
            SerializableTimeZoneTime standardTime =
                new SerializableTimeZoneTime();
            standardTime.setTime("00:00:00");
            standardTime.setDayOrder((short)1);
            standardTime.setDayOfWeek(DayOfWeekType.SUNDAY);
            timezone.setStandardTime(standardTime);
            timezone.setDaylightTime(standardTime);
            availabilityRequest.setTimeZone(timezone);

            availabilityRequest.setFreeBusyViewOptions(availabilityOpts);
            availabilityRequest.setMailboxDataArray(attendees);
            RequestServerVersion serverVersion = new RequestServerVersion();
            serverVersion.setVersion(ExchangeVersionType.EXCHANGE_2010_SP_1);
            Holder<GetUserAvailabilityResponseType> availabilityResponse =
                new Holder<GetUserAvailabilityResponseType>();
            Holder<ServerVersionInfo> gfversionInfo =
                new Holder<ServerVersionInfo>();

            service.getUserAvailability(availabilityRequest,
                serverVersion,
                availabilityResponse,
                gfversionInfo);
            results = availabilityResponse.value.getFreeBusyResponseArray()
                    .getFreeBusyResponse();

        } catch (DatatypeConfigurationException dce) {
            ZimbraLog.fb.warn("getFreeBusyForHost DatatypeConfiguration failure",
                dce);
            return getEmptyList(req);
        } catch (Exception e) {
            ZimbraLog.fb.warn("getFreeBusyForHost failure", e);
            return getEmptyList(req);
        }
        
		for (Request re : req) {
			String fb = "";
			int i = 0;
			for (FreeBusyResponseType attendeeAvailability : results) {
				if (re.email == attendees.getMailboxData().get(i).getEmail()
						.getAddress()) {
					if (ResponseClassType.SUCCESS != attendeeAvailability
							.getResponseMessage().getResponseClass()) {
						ZimbraLog.fb
								.warn("Error in response. continuing to next one");
						i++;
						continue;
					}
					ZimbraLog.fb.debug("Availability for "
							+ attendees.getMailboxData().get(i).getEmail()
									.getAddress()
							+ " ["
							+ attendeeAvailability.getFreeBusyView()
									.getMergedFreeBusy() + "]");

					fb = attendeeAvailability.getFreeBusyView().getMergedFreeBusy();
					break;
				}
				
				i++;
			}

			ret.add(new ExchangeFreeBusyProvider.ExchangeUserFreeBusy(fb,
					re.email, FB_INTERVAL, req.get(0).start, req.get(0).end));
		}  

        return ret;
    }

    public static int checkAuth(ServerInfo info, Account requestor)
        throws ServiceException, IOException {
        ExchangeEWSFreeBusyProvider provider = new ExchangeEWSFreeBusyProvider();
        provider.initService(info);
        FolderType publicFolderRoot =
            (FolderType)provider.bindFolder(DistinguishedFolderIdNameType.PUBLICFOLDERSROOT,
                DefaultShapeNamesType.ALL_PROPERTIES);
        if (publicFolderRoot == null) {
            return 400;
        }
        return 200;
    }

    public ExchangeEWSFreeBusyProvider getInstance() {
        return new ExchangeEWSFreeBusyProvider();
    }

    public void addFreeBusyRequest(Request req) throws FreeBusyUserNotFoundException {
        ServerInfo info = null;
        for (ExchangeUserResolver resolver : sRESOLVERS) {
            String email = req.email;
            if (req.requestor != null)
                email = req.requestor.getName();
            info = resolver.getServerInfo(email);
            if (info != null) {
                if (!info.enabled)
                    throw new FreeBusyUserNotFoundException();
                if (null == service) {
                    try {
                        initService(info);
                    } catch (MalformedURLException e) {
                        ZimbraLog.fb.warn("failed to initialize provider", e);
                    }
                }
                break;
            }
        }
        if (info == null)
			throw new FreeBusyUserNotFoundException();
        addRequest(info, req);
    }

    public static void registerResolver(ExchangeUserResolver r, int priority) {
        synchronized (sRESOLVERS) {         
            sRESOLVERS.ensureCapacity(priority + 1);
            sRESOLVERS.add(priority, r);
        }
    }

    private static ArrayList<ExchangeUserResolver> sRESOLVERS;
    static {
        sRESOLVERS = new ArrayList<ExchangeUserResolver>();

        registerResolver(new BasicUserResolver(), 0);
        register(new ExchangeEWSFreeBusyProvider());
    }

    private HashMap<String, ArrayList<Request>> mRequests;

    public List<FreeBusy> getResults() {
        ArrayList<FreeBusy> ret = new ArrayList<FreeBusy>();
        for (Map.Entry<String, ArrayList<Request>> entry : mRequests.entrySet()) {
            try {
                ret.addAll(this.getFreeBusyForHost(entry.getKey(),
                    entry.getValue()));
            } catch (IOException e) {
                ZimbraLog.fb.error("error communicating to " + entry.getKey(),
                    e);
            }
        }
        return ret;
    }

    protected void addRequest(ServerInfo info, Request req) {
        ArrayList<Request> r = mRequests.get(info.url);
        if (r == null) {
            r = new ArrayList<Request>();
            mRequests.put(info.url, r);
        }
        req.data = info;
        r.add(req);
    }

    public ServerInfo getServerInfo(String emailAddr) {
        ServerInfo serverInfo = null;
        for (ExchangeUserResolver r : sRESOLVERS) {
            serverInfo = r.getServerInfo(emailAddr);
            if (serverInfo != null)
                break;
        }
        return serverInfo;
    }

    private static final String EXCHANGE_EWS = "EXCHANGE2010";

    @Override
    public String getName() {
        return EXCHANGE_EWS;
    }

    @Override
    public Set<Type> registerForItemTypes() {
        return EnumSet.of(MailItem.Type.APPOINTMENT);
    }
    
    private long getTimeInterval(String attr, String accountId, long defaultValue) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        if (accountId != null) {
            Account acct = prov.get(AccountBy.id, accountId);
            if (acct != null) {
                return acct.getTimeInterval(attr, defaultValue);
            }
        }
        return prov.getConfig().getTimeInterval(attr, defaultValue);
    }

    @Override
    public long cachedFreeBusyStartTime(String accountId) {
        Calendar cal = GregorianCalendar.getInstance();
        int curYear = cal.get(Calendar.YEAR);
        try {
            long dur = getTimeInterval(Provisioning.A_zimbraFreebusyExchangeCachedIntervalStart, accountId, 0);
            cal.setTimeInMillis(System.currentTimeMillis() - dur);
        } catch (ServiceException se) {
            // set to 1 week ago
            cal.setTimeInMillis(System.currentTimeMillis() -
                Constants.MILLIS_PER_WEEK);
        }
        // normalize the time to 00:00:00
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        if (cal.get(Calendar.YEAR) < curYear) {
            // Exchange accepts FB info for only one calendar year. If the start date falls in the previous year
            // change it to beginning of the current year.
            cal.set(curYear, 0, 1);
        }
        return cal.getTimeInMillis();
    }

    @Override
    public long cachedFreeBusyEndTime(String accountId) {
        long duration = Constants.MILLIS_PER_MONTH * 2;
        Calendar cal = GregorianCalendar.getInstance();
        try {
            duration = getTimeInterval(Provisioning.A_zimbraFreebusyExchangeCachedInterval, accountId, duration);
        } catch (ServiceException se) {}
        cal.setTimeInMillis(cachedFreeBusyStartTime(accountId) + duration);
        // normalize the time to 00:00:00
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis();
    }
    
    @Override
    public long cachedFreeBusyStartTime() {
        return cachedFreeBusyStartTime(null);
    }

    @Override
    public long cachedFreeBusyEndTime() {
        return cachedFreeBusyEndTime(null);
    }

    @Override
    public String foreignPrincipalPrefix() {
        return Provisioning.FP_PREFIX_AD;
    }

    protected String getForeignPrincipal(String accountId) throws ServiceException {
    	String ret = null;
        Account acct =
            Provisioning.getInstance()
                .get(Key.AccountBy.id, accountId);
        if (acct == null)
            return null;
        String[] fps = acct.getForeignPrincipal();
        for (String fp : fps) {
            if (fp.startsWith(Provisioning.FP_PREFIX_AD)) {
                int idx = fp.indexOf(':');
                if (idx != -1) {
                    ret = fp.substring(idx + 1);
                    break;
                }
            }
        }        
        return ret;
    }

    @Override
    public boolean registerForMailboxChanges(String accountId) {
        return registerForMailboxChanges();
    }
}
