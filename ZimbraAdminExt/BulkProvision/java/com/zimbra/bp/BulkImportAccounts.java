/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
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
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.bp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.zimbra.common.account.Key;
import com.zimbra.common.account.Key.DomainBy;
import com.zimbra.common.localconfig.LC;
import com.zimbra.common.mailbox.ContactConstants;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminExtConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.EmailUtil;
import com.zimbra.common.util.StringUtil;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.Domain;
import com.zimbra.cs.account.GalContact;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Provisioning.GalMode;
import com.zimbra.cs.account.Provisioning.SearchGalResult;
import com.zimbra.cs.account.accesscontrol.AdminRight;
import com.zimbra.cs.account.accesscontrol.PseudoTarget;
import com.zimbra.cs.account.accesscontrol.Rights.Admin;
import com.zimbra.cs.account.accesscontrol.TargetType;
import com.zimbra.cs.account.gal.GalOp;
import com.zimbra.cs.account.gal.GalParams;
import com.zimbra.cs.account.ldap.LdapGalMapRules;
import com.zimbra.cs.account.ldap.LdapGalSearch;
import com.zimbra.cs.ldap.LdapConstants;
import com.zimbra.cs.mailbox.ACL;
import com.zimbra.cs.mailbox.Folder;
import com.zimbra.cs.mailbox.MailItem;
import com.zimbra.cs.mailbox.MailServiceException;
import com.zimbra.cs.mailbox.Mailbox;
import com.zimbra.cs.mailbox.MailboxManager;
import com.zimbra.cs.service.FileUploadServlet;
import com.zimbra.cs.service.admin.AdminDocumentHandler;
import com.zimbra.cs.service.admin.AdminFileDownload;
import com.zimbra.cs.service.admin.AdminService;
import com.zimbra.soap.ZimbraSoapContext;
import com.zimbra.soap.admin.type.DataSourceType;

/**
 * @author Greg Solovyev
 */
public class BulkImportAccounts extends AdminDocumentHandler {

    public static final String ERROR_INVALID_ACCOUNT_NAME = "Invalid account name. ";
    private static final int DEFAULT_PWD_LENGTH = 8;

    @Override
    public Element handle(Element request, Map<String, Object> context) throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Map attrs = AdminService.getAttrs(request, true);
        String op = request.getAttribute(AdminExtConstants.A_op);
        Element response = zsc.createElement(AdminExtConstants.BULK_IMPORT_ACCOUNTS_RESPONSE);
        Provisioning prov = Provisioning.getInstance();
        if (ZimbraBulkProvisionExt.OP_PREVIEW.equalsIgnoreCase(op) || ZimbraBulkProvisionExt.OP_START_IMPORT.equalsIgnoreCase(op)) {
            int totalAccounts = 0;
            int totalExistingAccounts = 0;
            String SMTPHost = "";
            String SMTPPort = "";
            String zimbraMailTransport = "";
            boolean createDomains = "TRUE".equalsIgnoreCase(request.getElement(AdminExtConstants.E_createDomains).getTextTrim());

            Element eSMTPHost = request.getOptionalElement(AdminExtConstants.E_SMTPHost);
            if (eSMTPHost != null) {
                SMTPHost = eSMTPHost.getTextTrim();
            }
            Element eSMTPPort = request.getOptionalElement(AdminExtConstants.E_SMTPPort);
            if (eSMTPPort != null) {
                SMTPPort = eSMTPPort.getTextTrim();
            }
            if (SMTPPort.length() > 0 && SMTPHost.length() > 0) {
                zimbraMailTransport = "smtp:" + SMTPHost + ":" + SMTPPort;
            }

            /**
             * list of entries found in the source (CSV file, XML file or directory)
             */
            List<Map<String, Object>> sourceEntries = new ArrayList<Map<String, Object>>();

            String sourceType = request.getElement(AdminExtConstants.A_sourceType).getTextTrim();
            if (sourceType.equalsIgnoreCase(AdminFileDownload.FILE_FORMAT_BULK_CSV)) {
                String aid = request.getElement(AdminExtConstants.E_attachmentID).getTextTrim();
                ZimbraLog.extensions.debug("Uploaded CSV file id = " + aid);
                // response.addElement(E_attachmentID).addText(aid);
                FileUploadServlet.Upload up = FileUploadServlet.fetchUpload(zsc.getAuthtokenAccountId(), aid, zsc.getAuthToken());
                if (up == null) {
                    throw ServiceException.FAILURE("Uploaded CSV file with id " + aid + " was not found.", null);
                }
                InputStream in = null;
                CSVReader reader = null;
                try {
                    in = up.getInputStream();
                    reader = new CSVReader(new InputStreamReader(in));
                    String[] nextLine;

                    List<String[]> allEntries = reader.readAll();
                    int totalNumberOfEntries = allEntries.size();

                    checkAccountLimits(allEntries, zsc, prov);

                    /**
                     * Iterate through records obtained from CSV file and add
                     * each record to sourceEntries
                     */
                    for (int i = 0; i < totalNumberOfEntries; i++) {
                        nextLine = allEntries.get(i);
                        boolean isValidEntry = false;
                        try {
                            isValidEntry = validEntry(nextLine, zsc);
                        } catch (ServiceException e) {
                            ZimbraLog.extensions.error(e);
                            throw e;
                        }

                        if (!isValidEntry) {
                            throw ServiceException.INVALID_REQUEST(String.format("Entry %d is not valid (%s %s %s %s %s %s)", i,
                                    nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5]), null);
                        }
                        String userEmail = nextLine[0].trim();
                        String parts[] = EmailUtil.getLocalPartAndDomain(userEmail);
                        if (parts == null) {
                            throw ServiceException.INVALID_REQUEST("must be valid email address: " + userEmail, null);
                        }

                        Domain domain = prov.getDomainByName(parts[1]);
                        if (domain != null) {
                            checkDomainRight(zsc, domain, Admin.R_createAccount);
                        } else if (createDomains) {
                            if (ZimbraBulkProvisionExt.OP_START_IMPORT.equalsIgnoreCase(op)) {
                                domain = createMissingDomain(parts[1], zsc, context);
                            }
                            checkDomainRight(zsc, domain, Admin.R_createAccount);
                        } else {
                            throw AccountServiceException.NO_SUCH_DOMAIN(parts[1]);
                        }
                        Account acct = Provisioning.getInstance().getAccountByName(userEmail);
                        if (acct != null) {
                            totalExistingAccounts++;
                        } else {
                            Map<String, Object> accAttrs = new HashMap<String, Object>();
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_displayName, nextLine[1].trim());
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_givenName, nextLine[2].trim());
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_sn, nextLine[3].trim());
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_zimbraPasswordMustChange, nextLine[5].trim());

                            checkSetAttrsOnCreate(zsc, TargetType.account, userEmail, accAttrs);

                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_mail, userEmail);
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_userPassword, nextLine[4].trim());
                            if (zimbraMailTransport.length() > 0) {
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_zimbraMailTransport, zimbraMailTransport);
                            }
                            sourceEntries.add(accAttrs);
                        }
                        totalAccounts++;
                    }

                    in.close();

                } catch (IOException e) {
                    throw ServiceException.FAILURE("", e);
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            ZimbraLog.extensions.error(e);
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            ZimbraLog.extensions.error(e);
                        }
                    }

                }
            } else if (sourceType.equalsIgnoreCase(AdminFileDownload.FILE_FORMAT_BULK_XML)) {
                String aid = request.getElement(AdminExtConstants.E_attachmentID).getTextTrim();
                ZimbraLog.extensions.debug("Uploaded XML file id = " + aid);
                FileUploadServlet.Upload up = FileUploadServlet.fetchUpload(zsc.getAuthtokenAccountId(), aid, zsc.getAuthToken());
                if (up == null) {
                    throw ServiceException.FAILURE("Uploaded CSV file with id " + aid + " was not found.", null);
                }
                SAXReader reader = new SAXReader();
                try {
                    Document doc = reader.read(up.getInputStream());
                    org.dom4j.Element root = doc.getRootElement();
                    if (!root.getName().equals(AdminExtConstants.E_ZCSImport)) {
                        throw new DocumentException("Bulk provisioning XML file's root element must be "
                                + AdminExtConstants.E_ZCSImport);
                    }
                    Iterator iter = root.elementIterator(AdminExtConstants.E_ImportUsers);
                    if (!iter.hasNext()) {
                        throw new DocumentException("Cannot find element " + AdminExtConstants.E_ImportUsers
                                + " in uploaded bulk provisioning XML file");
                    }

                    /**
                     * Settings from SOAP request take preference over settings in the XML file
                     */
                    if (SMTPHost.length() == 0) {
                        Iterator iterMTPHost = root.elementIterator(AdminExtConstants.E_SMTPHost);
                        if (iterMTPHost.hasNext()) {
                            org.dom4j.Element elSMTPHost = (org.dom4j.Element) iterMTPHost.next();
                            SMTPHost = elSMTPHost.getTextTrim();
                        }
                    }
                    if (SMTPPort.length() == 0) {
                        Iterator iterSMTPort = root.elementIterator(AdminExtConstants.E_SMTPPort);
                        if (iterSMTPort.hasNext()) {
                            org.dom4j.Element elSMTPPort = (org.dom4j.Element) iterSMTPort.next();
                            SMTPPort = elSMTPPort.getTextTrim();
                        }
                    }
                    if (zimbraMailTransport.length() == 0) {
                        if (SMTPPort.length() > 0 && SMTPHost.length() > 0) {
                            zimbraMailTransport = "smtp:" + SMTPHost + ":" + SMTPPort;
                        }
                    }
                    org.dom4j.Element elImportUsers = (org.dom4j.Element) iter.next();
                    for (Iterator userIter = elImportUsers.elementIterator(AdminExtConstants.E_User); userIter.hasNext();) {
                        org.dom4j.Element elUser = (org.dom4j.Element) userIter.next();
                        String userEmail = "";
                        String userFN = "";
                        String userLN = "";
                        String userDN = "";
                        String userPassword = "";
                        String userPwdMustChange = "FALSE";
                        for (Iterator userPropsIter = elUser.elementIterator(); userPropsIter.hasNext();) {
                            org.dom4j.Element el = (org.dom4j.Element) userPropsIter.next();
                            /*
                             * We support <ExchangeMail> element for
                             * compatibility with desktop based Exchange
                             * Migration utility <RemoteEmailAddress> takes
                             * prevalence over <ExchangeMail> element
                             */
                            if (userEmail == "" && AdminExtConstants.E_ExchangeMail.equalsIgnoreCase(el.getName())) {
                                userEmail = el.getTextTrim();
                            }
                            if (AdminExtConstants.E_remoteEmail.equalsIgnoreCase(el.getName())) {
                                userEmail = el.getTextTrim();
                            }
                            if (Provisioning.A_displayName.equalsIgnoreCase(el.getName())) {
                                userDN = el.getTextTrim();
                            }

                            if (Provisioning.A_givenName.equalsIgnoreCase(el.getName())) {
                                userFN = el.getTextTrim();
                            }

                            if (Provisioning.A_sn.equalsIgnoreCase(el.getName())) {
                                userLN = el.getTextTrim();
                            }

                            if (AdminExtConstants.A_password.equalsIgnoreCase(el.getName())) {
                                userPassword = el.getTextTrim();
                            }

                            if (Provisioning.A_zimbraPasswordMustChange.equalsIgnoreCase(el.getName())) {
                                userPwdMustChange = el.getTextTrim();
                            }
                        }
                        String parts[] = EmailUtil.getLocalPartAndDomain(userEmail);
                        if (parts == null) {
                            throw ServiceException.INVALID_REQUEST("must be valid email address: " + userEmail, null);
                        }

                        Domain domain = prov.getDomainByName(parts[1]);
                        if (domain != null) {
                            checkDomainRight(zsc, domain, Admin.R_createAccount);
                        } else if (createDomains) {
                            domain = createMissingDomain(parts[1], zsc, context);
                            checkDomainRight(zsc, domain, Admin.R_createAccount);
                        } else {
                            throw AccountServiceException.NO_SUCH_DOMAIN(parts[1]);
                        }

                        Account acct = Provisioning.getInstance().getAccountByName(userEmail);
                        if (acct != null) {
                            totalExistingAccounts++;
                        } else {
                            Map<String, Object> accAttrs = new HashMap<String, Object>();
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_givenName, userFN);
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_displayName, userDN);
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_sn, userLN);
                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_zimbraPasswordMustChange, userPwdMustChange);

                            checkSetAttrsOnCreate(zsc, TargetType.account, userEmail, accAttrs);

                            StringUtil.addToMultiMap(accAttrs, Provisioning.A_mail, userEmail);
                            if (userPassword != null) {
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_userPassword, userPassword);
                            }
                            if (zimbraMailTransport.length() > 0) {
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_zimbraMailTransport, zimbraMailTransport);
                            }
                            sourceEntries.add(accAttrs);
                        }
                        totalAccounts++;
                    }

                } catch (DocumentException e) {
                    throw ServiceException.FAILURE("Bulk provisioning failed to read uploaded XML document.", e);
                } catch (IOException e) {
                    throw ServiceException.FAILURE("Bulk provisioning failed to read uploaded XML document.", e);
                }
            } else if (sourceType.equalsIgnoreCase(ZimbraBulkProvisionExt.FILE_FORMAT_BULK_LDAP)) {
                GalParams.ExternalGalParams galParams = new GalParams.ExternalGalParams(attrs, GalOp.search);
                LdapGalMapRules rules = new LdapGalMapRules(Provisioning.getInstance().getConfig(), true);

                Element elPassword = request.getOptionalElement(AdminExtConstants.A_password);
                Element elPasswordLength = request.getOptionalElement(AdminExtConstants.A_genPasswordLength);

                String generatePwd = request.getElement(AdminExtConstants.A_generatePassword).getTextTrim();

                int genPwdLength = 0;
                if (generatePwd == null) {
                    generatePwd = "false";
                } else if (generatePwd.equalsIgnoreCase("true")) {
                    if (elPasswordLength != null) {
                        genPwdLength = Integer.valueOf(elPasswordLength.getTextTrim());
                    } else {
                        genPwdLength = DEFAULT_PWD_LENGTH;
                    }
                    if (genPwdLength < 1) {
                        genPwdLength = DEFAULT_PWD_LENGTH;
                    }
                }

                int maxResults = 0;
                Element elMaxResults = request.getOptionalElement(AdminExtConstants.A_maxResults);
                if (elMaxResults != null) {
                    maxResults = Integer.parseInt(elMaxResults.getTextTrim());
                }

                String password = null;
                if (elPassword != null) {
                    password = elPassword.getTextTrim();
                }
                String mustChangePassword = request.getElement(AdminExtConstants.E_mustChangePassword).getTextTrim();
                if (SMTPPort.length() > 0 && SMTPHost.length() > 0) {
                    zimbraMailTransport = "smtp:" + SMTPHost + ":" + SMTPPort;
                }
                try {
                    SearchGalResult result = LdapGalSearch.searchLdapGal(galParams, GalOp.search, "*", maxResults, rules, null,
                            null);
                    List<GalContact> entries = result.getMatches();

                    if (entries != null) {
                        for (GalContact entry : entries) {
                            String emailAddress = entry.getSingleAttr(ContactConstants.A_email);
                            if (emailAddress == null) {
                                continue;
                            }

                            String parts[] = EmailUtil.getLocalPartAndDomain(emailAddress);
                            if (parts == null) {
                                throw ServiceException.INVALID_REQUEST("must be valid email address: " + emailAddress, null);
                            }

                            Domain domain = prov.getDomainByName(parts[1]);
                            if (domain != null) {
                                checkDomainRight(zsc, domain, Admin.R_createAccount);
                            } else if (createDomains) {
                                if (ZimbraBulkProvisionExt.OP_START_IMPORT.equalsIgnoreCase(op)) {
                                    domain = createMissingDomain(parts[1], zsc, context);
                                }
                                checkDomainRight(zsc, domain, Admin.R_createAccount);
                            } else {
                                throw AccountServiceException.NO_SUCH_DOMAIN(parts[1]);
                            }
                            if (domain != null) {
                                checkDomainRight(zsc, domain, Admin.R_createAccount);
                            } else {
                                throw AccountServiceException.NO_SUCH_DOMAIN(parts[1]);
                            }
                            Account acct = Provisioning.getInstance().getAccountByName(emailAddress);
                            if (acct != null) {
                                totalExistingAccounts++;
                            } else {
                                Map<String, Object> accAttrs = new HashMap<String, Object>();
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_givenName,
                                        entry.getSingleAttr(ContactConstants.A_firstName));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_displayName,
                                        entry.getSingleAttr(ContactConstants.A_fullName));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_sn,
                                        entry.getSingleAttr(ContactConstants.A_lastName));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_initials,
                                        entry.getSingleAttr(ContactConstants.A_initials));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_description,
                                        entry.getSingleAttr(ContactConstants.A_notes));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_telephoneNumber,
                                        entry.getSingleAttr(ContactConstants.A_homePhone));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_mobile,
                                        entry.getSingleAttr(ContactConstants.A_mobilePhone));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_homePhone,
                                        entry.getSingleAttr(ContactConstants.A_homePhone));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_l,
                                        entry.getSingleAttr(ContactConstants.A_homeCity));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_st,
                                        entry.getSingleAttr(ContactConstants.A_homeState));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_co,
                                        entry.getSingleAttr(ContactConstants.A_homeCountry));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_postalCode,
                                        entry.getSingleAttr(ContactConstants.A_homePostalCode));
                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_street,
                                        entry.getSingleAttr(ContactConstants.A_homeAddress));
                                if (zimbraMailTransport.length() > 0) {
                                    StringUtil.addToMultiMap(accAttrs, Provisioning.A_zimbraMailTransport, zimbraMailTransport);
                                }
                                if ("true".equalsIgnoreCase(mustChangePassword)) {
                                    StringUtil.addToMultiMap(accAttrs, Provisioning.A_zimbraPasswordMustChange, "TRUE");
                                }
                                checkSetAttrsOnCreate(zsc, TargetType.account, emailAddress, accAttrs);

                                StringUtil.addToMultiMap(accAttrs, Provisioning.A_mail,
                                        entry.getSingleAttr(ContactConstants.A_email));
                                if (password != null) {
                                    StringUtil.addToMultiMap(accAttrs, Provisioning.A_userPassword, password);
                                } else if (generatePwd.equalsIgnoreCase("true")) {
                                    StringUtil.addToMultiMap(accAttrs, Provisioning.A_userPassword,
                                            String.valueOf(generateStrongPassword(genPwdLength)));
                                }

                                sourceEntries.add(accAttrs);
                            }
                            totalAccounts++;
                        }

                    }
                } catch (ServiceException e) {
                    throw ServiceException.FAILURE("", e);
                }
            } else {
                throw ServiceException.INVALID_REQUEST(sourceType + " is not a valid value for parameter "
                        + AdminExtConstants.A_sourceType, null);
            }
            if (ZimbraBulkProvisionExt.OP_PREVIEW.equalsIgnoreCase(op)) {
                /*
                 * Do not start the import. Just generate a preview.
                 */
                response.addElement(AdminExtConstants.E_totalCount).setText(Integer.toString(totalAccounts));
                response.addElement(AdminExtConstants.E_skippedAccountCount).setText(Integer.toString(totalExistingAccounts));
                response.addElement(AdminExtConstants.E_SMTPHost).setText(SMTPHost);
                response.addElement(AdminExtConstants.E_SMTPPort).setText(SMTPPort);

            } else if (ZimbraBulkProvisionExt.OP_START_IMPORT.equalsIgnoreCase(op)) {
                /*
                 * Spin off a provisioning thread
                 */
                if (sourceEntries.size() > 0) {
                    BulkProvisioningThread thread = BulkProvisioningThread.getThreadInstance(zsc.getAuthtokenAccountId(), true);
                    int status = thread.getStatus();
                    if (status == BulkProvisioningThread.iSTATUS_FINISHED) {
                        BulkProvisioningThread.deleteThreadInstance(zsc.getAuthtokenAccountId());
                        thread = BulkProvisioningThread.getThreadInstance(zsc.getAuthtokenAccountId(), true);
                        status = thread.getStatus();
                    }
                    if (status != BulkProvisioningThread.iSTATUS_IDLE) {
                        throw BulkProvisionException.BP_IMPORT_ALREADY_RUNNING();
                    }
                    thread.setSourceAccounts(sourceEntries);
                    thread.start();
                    response.addElement(AdminExtConstants.E_status).setText(Integer.toString(thread.getStatus()));
                } else {
                    throw BulkProvisionException.BP_NO_ACCOUNTS_TO_IMPORT();
                }
            }
        }

        if (ZimbraBulkProvisionExt.OP_ABORT_IMPORT.equalsIgnoreCase(op)) {
            BulkProvisioningThread thread = BulkProvisioningThread.getThreadInstance(zsc.getAuthtokenAccountId(), false);
            if (thread != null) {
                int status = thread.getStatus();
                if (status != BulkProvisioningThread.iSTATUS_FINISHED) {
                    thread.abort();
                    response.addElement(AdminExtConstants.E_status).setText(Integer.toString(thread.getStatus()));
                    response.addElement(AdminExtConstants.E_provisionedCount).setText(
                            Integer.toString(thread.getProvisionedCounter()));
                    response.addElement(AdminExtConstants.E_skippedCount).setText(Integer.toString(thread.getSkippedCounter()));
                    response.addElement(AdminExtConstants.E_totalCount).setText(Integer.toString(thread.getTotalCount()));
                    if (thread.getWithErrors()) {
                        response.addElement(AdminExtConstants.E_errorCount).addText(Integer.toString(thread.getFailCounter()));
                    }
                    status = thread.getStatus();
                    if (status == BulkProvisioningThread.iSTATUS_ABORTED) {
                        BulkProvisioningThread.deleteThreadInstance(zsc.getAuthtokenAccountId());
                    }
                } else {
                    response.addElement(AdminExtConstants.E_status).setText(Integer.toString(status));
                }
            } else {
                response.addElement(AdminExtConstants.E_status).setText(
                        Integer.toString(BulkProvisioningThread.iSTATUS_NOT_RUNNING));
            }
        } else if (ZimbraBulkProvisionExt.OP_GET_STATUS.equalsIgnoreCase(op)) {
            BulkProvisioningThread thread = BulkProvisioningThread.getThreadInstance(zsc.getAuthtokenAccountId(), false);
            if (thread != null) {
                int status = thread.getStatus();
                response.addElement(AdminExtConstants.E_status).setText(Integer.toString(status));
                response.addElement(AdminExtConstants.E_provisionedCount).setText(
                        Integer.toString(thread.getProvisionedCounter()));
                response.addElement(AdminExtConstants.E_skippedCount).setText(Integer.toString(thread.getSkippedCounter()));
                response.addElement(AdminExtConstants.E_totalCount).setText(Integer.toString(thread.getTotalCount()));
                if (thread.getWithErrors()) {
                    response.addElement(AdminExtConstants.E_errorCount).addText(Integer.toString(thread.getFailCounter()));
                }
                if (status == BulkProvisioningThread.iSTATUS_FINISHED || status == BulkProvisioningThread.iSTATUS_ABORTED
                        || status == BulkProvisioningThread.iSTATUS_ERROR) {
                    String fileToken = Double.toString(Math.random() * 100);
                    String outSuccessFileName = String.format("%s%s_bulk_report_%s_%s.csv", LC.zimbra_tmp_directory.value(),
                            File.separator, zsc.getAuthtokenAccountId(), fileToken);
                    FileOutputStream outReport = null;
                    CSVWriter reportWriter = null;
                    try {
                        outReport = new FileOutputStream(outSuccessFileName);
                        reportWriter = new CSVWriter(new OutputStreamWriter(outReport));
                        for (String completedAccount : thread.getCompletedAccounts().keySet()) {
                            String[] line = new String[2];
                            line[0] = completedAccount; // account name
                            line[1] = thread.getCompletedAccounts().get(completedAccount); // account password
                            reportWriter.writeNext(line);
                        }
                        reportWriter.close();
                    } catch (FileNotFoundException e) {
                        throw ServiceException.FAILURE("Failed to create CSV file with a list of provisioned accounts", e);
                    } catch (IOException e) {
                        throw ServiceException.FAILURE("Failed to create CSV file with a list of provisioned accounts", e);
                    } finally {
                        if (reportWriter != null) {
                            try {
                                reportWriter.close();
                            } catch (IOException e) {
                                ZimbraLog.extensions.error(e);
                            }
                        }

                        if (outReport != null) {
                            try {
                                outReport.close();
                            } catch (IOException e) {
                                ZimbraLog.extensions.error(e);
                            }
                        }
                    }
                    response.addElement(AdminExtConstants.E_reportFileToken).addText(fileToken);
                    /**
                     * if thread is done for whichever reason and there are
                     * errors, generate an error report
                     */
                    if (thread.getWithErrors()) {
                        FileOutputStream out = null;
                        CSVWriter errorWriter = null;
                        try {
                            String outErrorsFileName = String.format("%s%s_bulk_errors_%s_%s.csv",
                                    LC.zimbra_tmp_directory.value(), File.separator, zsc.getAuthtokenAccountId(), fileToken);
                            out = new FileOutputStream(outErrorsFileName);
                            errorWriter = new CSVWriter(new OutputStreamWriter(out));
                            for (String failedAccount : thread.getfailedAccounts().keySet()) {
                                String[] line = new String[2];
                                line[0] = failedAccount;
                                line[1] = thread.getfailedAccounts().get(failedAccount).getMessage();
                                errorWriter.writeNext(line);
                            }
                            errorWriter.close();
                        } catch (FileNotFoundException e) {
                            throw ServiceException.FAILURE("Failed to create CSV file with error report", e);
                        } catch (IOException e) {
                            throw ServiceException.FAILURE("Failed to create CSV file with error report", e);
                        } finally {
                            if (errorWriter != null) {
                                try {
                                    errorWriter.close();
                                } catch (IOException e) {
                                    ZimbraLog.extensions.error(e);
                                }
                            }
                            if (out != null) {
                                try {
                                    out.close();
                                } catch (IOException e) {
                                    ZimbraLog.extensions.error(e);
                                }
                            }
                        }
                    }
                    BulkProvisioningThread.deleteThreadInstance(zsc.getAuthtokenAccountId());
                }
            } else {
                response.addElement(AdminExtConstants.E_status).setText(
                        Integer.toString(BulkProvisioningThread.iSTATUS_NOT_RUNNING));
            }
        }
        return response;
    }

    private static char[] pwdChars = "abcdefghijklmnopqrstuvqxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*().,;:`<>?-+|}{[]'\""
            .toCharArray();

    private static int getIntFromByte(byte[] bytes) {
        int returnNumber = 0;
        int pos = 0;
        returnNumber += byteToInt(bytes[pos++]) << 24;
        returnNumber += byteToInt(bytes[pos++]) << 16;
        returnNumber += byteToInt(bytes[pos++]) << 8;
        returnNumber += byteToInt(bytes[pos++]) << 0;
        return returnNumber;
    }

    private static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public static char[] generateStrongPassword(int length) {
        char[] pwd = new char[length];
        try {
            java.security.SecureRandom random = java.security.SecureRandom.getInstance("SHA1PRNG");

            byte[] intbytes = new byte[4];

            for (int i = 0; i < length; i++) {
                random.nextBytes(intbytes);
                pwd[i] = pwdChars[Math.abs(getIntFromByte(intbytes) % pwdChars.length)];
            }
        } catch (Exception ex) {
            // Don't really worry, we won't be using this if we can't use securerandom anyway
        }
        return pwd;
    }

    /**
     * The account limits are decided by the following factors: 1) Hard limit:
     * MAX_ACCOUNTS_LIMIT or accountLimit - License Account Limit (whichever is
     * smaller) 2) zimbraDomainMaxAccounts
     *
     * @param allEntries
     * @throws ServiceException
     */
    private void checkAccountLimits(List<String[]> allEntries, ZimbraSoapContext zsc, Provisioning prov) throws ServiceException {
        ZimbraLog.extensions.debug("Check the account limits ...");
        int numberOfEntries = allEntries.size();

        // check against zimbraDomainMaxAccounts
        Hashtable<String, Integer> h = new Hashtable<String, Integer>();

        for (int i = 0; i < numberOfEntries; i++) {
            String[] entry = allEntries.get(i);
            String accountName = null;
            String parts[] = null;
            String domainName = null;

            if (entry != null) {
                accountName = entry[0];
            }
            if (accountName != null) {
                parts = accountName.trim().split("@");
            }
            if (parts != null && parts.length > 0) {
                domainName = parts[1];
            }
            if (domainName != null) {
                int count = 0;
                if (h.containsKey(domainName)) {
                    count = h.get(domainName).intValue();
                }
                h.put(domainName, count + 1);
            }
        }

        for (Enumeration<String> keys = h.keys(); keys.hasMoreElements();) {
            String domainName = keys.nextElement();
            Domain domain = prov.get(Key.DomainBy.name, domainName);
            if (domain != null) {
                String domainMaxAccounts = domain.getAttr(Provisioning.A_zimbraDomainMaxAccounts);
                if (domainMaxAccounts != null && domainMaxAccounts.length() > 0) {
                    int limit = Integer.parseInt(domainMaxAccounts);
                    int used = 0;
                    Provisioning.CountAccountResult result = prov.countAccount(domain);
                    for (Provisioning.CountAccountResult.CountAccountByCos c : result.getCountAccountByCos()) {
                        used += c.getCount();
                    }

                    int newAccounts = h.get(domainName).intValue();
                    int available = limit - used;
                    /*
                     * ZimbraLog.extensions.debug("For domain " + domainName +
                     * " : csv entry = " + newAccounts +
                     * ", zimbraDomainMaxAccounts = " + limit +
                     * ", used accounts = " + used) ;
                     */
                    if (newAccounts > available) {
                        throw BulkProvisionException
                                .BP_TOO_MANY_ACCOUNTS("the maximum accounts you can bulk provisioning for domain: " + domainName
                                        + " is " + available + "\n");
                    }
                }
            }
        }
    }

    private boolean validEntry(String[] entries, ZimbraSoapContext lc) throws ServiceException {
        Provisioning prov = Provisioning.getInstance();
        String errorMsg = "";
        if (entries.length != 6) {
            errorMsg = "Invalid number of columns.";
            throw ServiceException.PARSE_ERROR(errorMsg, new Exception(errorMsg));
        }

        String accountName = entries[0];

        // 1. account name is specified and can be accessed by current
        // admin/domain admin user
        if (accountName == null || accountName.length() <= 0) {
            throw ServiceException.PARSE_ERROR(ERROR_INVALID_ACCOUNT_NAME, new Exception(ERROR_INVALID_ACCOUNT_NAME));
        }
        accountName = accountName.trim();
        String parts[] = accountName.split("@");

        if (parts.length != 2) {
            throw ServiceException.PARSE_ERROR(ERROR_INVALID_ACCOUNT_NAME, new Exception(ERROR_INVALID_ACCOUNT_NAME));
        }

        return true;
    }

    private Domain createMissingDomain(String name, ZimbraSoapContext zsc, Map<String, Object> context) throws ServiceException {
        //
        // TODO: check permission has to be consolidated with the one in
        // CraeteDomain
        //

        // check permission
        if (name.indexOf('.') == -1) {
            // is a top domain
            checkRight(zsc, context, null, Admin.R_createTopDomain);
        } else {
            // go up the domain hierarchy see if any of the parent domains
            // exist.
            // If yes, check the createSubDomain right on the lowest existing
            // parent domain.
            // If not, allow it if the admin has both the createTopDomain on
            // globalgrant; and
            // use a pseudo Domain object as the target to check the
            // createSubDomain right
            // (because createSubDomain is a domain right, we cannot use
            // globalgrant for the target).
            String domainName = name;
            Domain parentDomain = null;
            while (parentDomain == null) {
                int nextDot = domainName.indexOf('.');
                if (nextDot == -1) {
                    // reached the top, check if the admin has the
                    // createTopDomain right on globalgrant
                    checkRight(zsc, context, null, Admin.R_createTopDomain);

                    // then create a pseudo domain for checking the
                    // createSubDomain right
                    parentDomain = PseudoTarget.createPseudoDomain(Provisioning.getInstance());
                    break;
                } else {
                    domainName = domainName.substring(nextDot + 1);
                    parentDomain = Provisioning.getInstance().get(DomainBy.name, domainName);
                }
            }
            checkRight(zsc, context, parentDomain, Admin.R_createSubDomain);
        }

        // create domain
        Map<String, Object> attrs = new HashMap<String, Object>();
        StringUtil.addToMultiMap(attrs, Provisioning.A_zimbraGalMode, "zimbra");
        StringUtil.addToMultiMap(attrs, Provisioning.A_zimbraAuthMech, "zimbra");
        StringUtil.addToMultiMap(attrs, Provisioning.A_zimbraGalMaxResults, "100");
        StringUtil.addToMultiMap(attrs, Provisioning.A_zimbraNotes, "automatically created by bulk provisioning");
        checkSetAttrsOnCreate(zsc, TargetType.domain, name, attrs);
        Domain domain = Provisioning.getInstance().createDomain(name, attrs);
        String acctValue = String.format("%s@%s", "galsync", name);

        // create galsync account
        Map<String, Object> accountAttrs = new HashMap<String, Object>();
        StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zimbraIsSystemResource, LdapConstants.LDAP_TRUE);
        StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zimbraHideInGal, LdapConstants.LDAP_TRUE);
        StringUtil.addToMultiMap(accountAttrs, Provisioning.A_zimbraContactMaxNumEntries, "0");
        checkSetAttrsOnCreate(zsc, TargetType.account, acctValue, accountAttrs);
        Account galSyncAccount = Provisioning.getInstance().createAccount(acctValue, null, accountAttrs);

        String acctId = galSyncAccount.getId();
        HashSet<String> galAcctIds = new HashSet<String>();
        galAcctIds.add(acctId);
        domain.setGalAccountId(galAcctIds.toArray(new String[0]));

        // create folder if not already exists.
        String folder = "/_zimbra";
        Mailbox mbox = MailboxManager.getInstance().getMailboxByAccount(galSyncAccount);
        Folder contactFolder = null;
        try {
            contactFolder = mbox.getFolderByPath(null, folder);
        } catch (MailServiceException.NoSuchItemException e) {
            Folder.FolderOptions fopt = new Folder.FolderOptions().setDefaultView(MailItem.Type.CONTACT);
            contactFolder = mbox.createFolder(null, folder, fopt);
        }

        int folderId = contactFolder.getId();

        mbox.grantAccess(null, folderId, domain.getId(), ACL.GRANTEE_DOMAIN, ACL.stringToRights("r"), null);

        // create datasource
        Map<String, Object> dsAttrs = new HashMap<String, Object>();
        try {
            dsAttrs.put(Provisioning.A_zimbraGalType, GalMode.zimbra.name());
            dsAttrs.put(Provisioning.A_zimbraDataSourcePollingInterval, "1d");
            dsAttrs.put(Provisioning.A_zimbraDataSourceFolderId, "" + folderId);
            dsAttrs.put(Provisioning.A_zimbraDataSourceEnabled, LdapConstants.LDAP_TRUE);
            dsAttrs.put(Provisioning.A_zimbraGalStatus, "enabled");
            Provisioning.getInstance().createDataSource(galSyncAccount, DataSourceType.gal, "zimbra", dsAttrs);
        } catch (ServiceException e) {
            ZimbraLog.extensions.error("error creating datasource for GalSyncAccount", e);
            throw e;
        }
        return domain;
    }

    @Override
    public void docRights(List<AdminRight> relatedRights, List<String> notes) {
        relatedRights.add(Admin.R_createAccount);
        relatedRights.add(Admin.R_listAccount);
        relatedRights.add(Admin.R_createTopDomain);
        relatedRights.add(Admin.R_createSubDomain);

        notes.add("Only accounts on which the authed admin has " + Admin.R_listAccount.getName() + " right will be provisioned.");
        notes.add(Admin.R_createTopDomain + " right is required in order to automatically create top level domains.");
        notes.add(Admin.R_createSubDomain + " right is required in order to automatically create sub-domains.");
    }
}
