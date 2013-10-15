<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
--%>
<%@ tag body-content="empty" %>
<%@ attribute name="mailbox" rtexprvalue="true" required="true" type="org.zmail.cs.taglib.bean.ZMailboxBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="org.zmail.i18n" %>
<%@ taglib prefix="zm" uri="org.zmail.zm" %>
<%@ taglib prefix="app" uri="org.zmail.htmlclient" %>

<table width="100%">
    <tr>
        <td>
            <table class="ZOptionsSectionTable" width="100%">
                <tr class="ZOptionsHeaderRow">
                    <td class="ImgPrefsHeader_L">&nbsp;</td>
                    <td class='ZOptionsHeader ImgPrefsHeader' >
                        <fmt:message key="optionsAccounts"/>
                    </td>
                    <td class="ImgPrefsHeader_R">&nbsp;</td>
                </tr>
            </table>
            <table width="100%" class="ZOptionsSectionMain" cellspacing="6">
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <tr>
                    <td width="10%">&nbsp;</td>
                    <td class='List' valign='top'>
                        <table width="100%">
                            <tr>
                                <th nowrap>&nbsp;
                                <th nowrap><fmt:message key="optionsAccountName"/>
                                <th nowrap><fmt:message key="optionsAccountEmail"/>
                                <th nowrap><fmt:message key="optionsAccountType"/>
                            </tr>
                            <tr>
                                <td nowrap>&nbsp;</td>
                                <td>${fn:escapeXml(not empty mailbox.defaultIdentity.name ? mailbox.defaultIdentity.name : mailbox.name)}</td>
                                <td>${fn:escapeXml(mailbox.name)}</td>
                                <td>
                                    <fmt:message key="optionsAccountTypePrimary"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="10%">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
            </table>
            <br/>
            <table class="ZOptionsSectionTable" width="100%">
                <tr class="ZOptionsHeaderRow">
                    <td class="ImgPrefsHeader_L">&nbsp;</td>
                    <td class='ZOptionsHeader ImgPrefsHeader' >
                        <fmt:message key="optionsAccountPrimarySettings"/>
                    </td>
                    <td class="ImgPrefsHeader_R">&nbsp;</td>
                </tr>
             </table>
            <table width="100%" class="ZOptionsSectionMain" cellspacing="6">
            <tr>
                <td class='ZOptionsTableLabel'>
                    <label><fmt:message key="optionsAccountEmail"/>:</label>
                </td>
                <td class='ZOptionsHint'>
                    ${fn:escapeXml(mailbox.name)}
                </td>
            </tr>
            <tr>
                <td class='ZOptionsTableLabel'>
                    <label><fmt:message key="optionsAccountName"/>:</label>
                </td>
                <td>
                    <input id="zmailPrefIdentityName" size="40" type="text" name='zmailPrefIdentityName' 
						value="${fn:escapeXml(mailbox.defaultIdentity.name)}">
                </td>
            </tr>
            <tr><td colspan="2"><hr></td></tr>
            <tr>
                <td class='ZOptionsTableLabel'>
                    <label><fmt:message key="optionsAccountFrom"/>:</label>
                </td>
                <td>
                    <label><fmt:message key="optionsAccountFromChoose"/>:</label>
                </td>
            </tr>
            <tr>
                <td class='ZOptionsTableLabel'>&nbsp;</td>
                <td style='padding-left:20px'>
                    <table>
                        <tr>
                            <td>
                                <input id="mailFrom" size="30" type="text" name='zmailPrefFromDisplay' 
									value="${fn:escapeXml(mailbox.defaultIdentity.fromDisplay)}">
                            </td>
                            <td style='padding-left:10px'>
                                <select name="zmailPrefFromAddress">
                                    <c:set var="fromAddr" value="${fn:toLowerCase(mailbox.defaultIdentity.fromAddress)}"/>
                                    <c:forEach var="address" items="${mailbox.accountInfo.emailAddresses}">
                                        <option value="${fn:escapeXml(address)}"
											<c:if test="${fn:toLowerCase(address) eq fromAddr}"> selected</c:if> >
											${fn:escapeXml(address)}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style='padding-left:5px' class='ZOptionsHint'>
                                <fmt:message key="optionsEmailPersonalHint"/>
                            </td>
                            <td style='padding-left:15px' class='ZOptionsHint'>
                                &nbsp;
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class='ZOptionsTableLabel'>
                    <fmt:message key="optionsAccountReplyTo"/>:
                </td>
                <td>
                    <table>
                        <tr>
                            <td>
								<input type="checkbox" id="REPLYCHECKED" name='zmailPrefReplyToEnabled' value="TRUE" 
									<c:if test="${mailbox.defaultIdentity.replyToEnabled}">checked</c:if>>
							</td>
                            <td style='padding-left:5px' nowrap align=right>
								<label for="REPLYCHECKED"><fmt:message key="optionsAccountReplyToSet"/>:</label>
							</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class='ZOptionsTableLabel'>&nbsp;</td>
                <td style='padding-left:20px'>
                    <table>
                        <tr>
                            <td>
                                <input id="replyToDisplay" size="30" type="text" name='zmailPrefReplyToDisplay' 
									value="${fn:escapeXml(mailbox.defaultIdentity.replyToDisplay)}">
                            </td>
                            <td style='padding-left:10px'>
                                <input id="replyToAddress" size="30" type="text" name='zmailPrefReplyToAddress' 
									value="${fn:escapeXml(mailbox.defaultIdentity.replyToAddress)}">
                            </td>
                        </tr>
                        <tr>
                            <td style='padding-left:5px' class='ZOptionsHint'>
                                <fmt:message key="optionsEmailPersonalHint"/>
                            </td>
                            <td style='padding-left:15px' class='ZOptionsHint'>
                                <fmt:message key="optionsEmailAddressHint"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:set var="maxSigs" value="${mailbox.accountInfo.attrs.zmailSignatureMaxNumEntries[0]}"/>
            <c:if test="${maxSigs ne 0}">
                <tr><td colspan="2"><hr></td></tr>
                <tr>
                    <td class='ZOptionsTableLabel'>
                        <label for="signatureSelect"><fmt:message key="optionsAccountSignature"/>:</label>
                    </td>
                    <td>
                        <select name="zmailPrefDefaultSignatureId" id="signatureSelect">
                            <c:set var="signatureId" value="${mailbox.defaultIdentity.signatureId}"/>
                            <option value="">
                                <fmt:message key="optionsAccountNoSignature"/>
                            </option>
                            <zm:forEachSignature var="sig">
                                <!-- ${signatureId} = ${sig.id}  -->
                                <option
                                        <c:if test="${signatureId eq sig.id}">selected</c:if> value="${sig.id}">
                                        ${fn:escapeXml(sig.name)}
                                </option>
                            </zm:forEachSignature>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class='ZOptionsTableLabel'>&nbsp;</td>
                    <td>
                        <fmt:message key="optionsManageSignatures">
                            <fmt:param><fmt:message key="optionsManageSignaturesPre"/></fmt:param>
                            <fmt:param><a href="options?selected=signatures"><fmt:message key="optionsManageSignaturesLink"/></a></fmt:param>
                            <fmt:param><fmt:message key="optionsManageSignaturesPost"/></fmt:param>
                        </fmt:message>
                    </td>
                </tr>
            </c:if>
            <tr><td colspan="2">&nbsp;</td></tr>
            </table>
		</td>
	</tr>
</table>