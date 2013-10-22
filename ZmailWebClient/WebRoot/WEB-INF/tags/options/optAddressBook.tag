<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
<%@ attribute name="mailbox" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.bean.ZMailboxBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>

<table width="100%">
    <tr>
        <td>
            <table class="ZOptionsSectionTable" width="100%">
                <tr class="ZOptionsHeaderRow">
                    <td class="ImgPrefsHeader_L">
                        &nbsp;
                    </td>
                    <td class='ZOptionsHeader ImgPrefsHeader' >
                        <fmt:message key="optionsContacts"/>
                    </td>
                    <td class="ImgPrefsHeader_R">
                        &nbsp;
                    </td>
                </tr>
            </table>
            <table width="100%" class="ZOptionsSectionMain" cellspacing="6">
                <tr>
                    <td>
                        <table width=100% class="ZPropertySheet" cellspacing="6">
                            <tr>
                                <td class='ZOptionsTableLabel'>
                                    <label><fmt:message key="optionsDisplay"/>:</label> 
                                </td>
                                <td>
                                    <table>
                                        <tr>
                                            <td>
                                                <select name="zimbraPrefContactsPerPage" id="itemsPP">
                                                    <c:set var="pageSize" value="${mailbox.prefs.contactsPerPage}"/>
                                                    <option
                                                            <c:if test="${pageSize eq 10}"> selected</c:if>
                                                            >10
                                                    </option>
                                                    <option
                                                            <c:if test="${pageSize eq 25}"> selected</c:if>
                                                            >25
                                                    </option>
                                                    <option
                                                            <c:if test="${pageSize eq 50}"> selected</c:if>
                                                            >50
                                                    </option>
                                                    <option
                                                            <c:if test="${pageSize eq 100}"> selected</c:if>
                                                            >100
                                                    </option>
                                                </select>
                                            </td>
                                            <td style='padding-left:5px'>
                                                <label for="itemsPP"><fmt:message key="optionsContactsPerPage"/></label>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <app:optSeparator/>

                <tr>
                    <td>
                        <table class="ZPropertySheet" cellspacing="6">
                            <tr>
                                <td class='ZOptionsTableLabel'>
                                    <label><fmt:message key="optionsAutoAdd"/>:</label>
                                </td>
                                <td>
                                    <app:optCheckbox boxfirst="true" trailingcolon="false" label="autoAddContacts" 
										pref="zimbraPrefAutoAddAddressEnabled" checked="${mailbox.prefs.autoAddAddressEnabled}"/>
                                </td>
                            </tr>
						</table>
					</td>
                </tr>
                <app:optSeparator/>
                
                <tr>
                    <td>
                        <table width="100%">
                            <tr>
								<td class='ZOptionsTableField' style='text-align:center;font-weight:bold;width:auto;'>
                                    <fmt:message key="optionsManageAddressBooks">
                                        <fmt:param><fmt:message key="optionsManageAddressBooksPre"/></fmt:param>
                                        <fmt:param><a href="maddrbooks"><fmt:message key="optionsManageAddressBooksLink"/></a></fmt:param>
                                        <fmt:param><fmt:message key="optionsManageAddressBooksPost"/></fmt:param>
                                    </fmt:message>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>