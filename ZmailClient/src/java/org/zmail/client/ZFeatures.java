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

package org.zmail.client;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Iterables;
import org.zmail.common.account.ProvisioningConstants;
import org.zmail.common.account.ZAttrProvisioning;

public class ZFeatures {

    private Map<String, Collection<String>> mAttrs;

    public ZFeatures(Map<String, Collection<String>> attrs) {
        mAttrs = attrs;
    }

    /**
     * @param name name of attr to get
     * @return null if unset, or first value in list
     */
    private String get(String name) {
        Collection<String> value = mAttrs.get(name);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Iterables.get(value, 0);

    }

    public boolean getBool(String name) {
        return ProvisioningConstants.TRUE.equals(get(name));
    }

    public Map<String, Collection<String>> getAttrs() { return mAttrs; }
    
    public boolean getContacts() { return getBool(ZAttrProvisioning.A_zmailFeatureContactsEnabled); }

    public boolean getMail() { return getBool(ZAttrProvisioning.A_zmailFeatureMailEnabled); }

    public boolean getAdminMail() {return getBool(ZAttrProvisioning.A_zmailFeatureAdminMailEnabled); }

    public boolean getVoice() { return getBool(ZAttrProvisioning.A_zmailFeatureVoiceEnabled); }

    public boolean getCalendar() { return getBool(ZAttrProvisioning.A_zmailFeatureCalendarEnabled); }

    public boolean getCalendarUpsell() { return getBool(ZAttrProvisioning.A_zmailFeatureCalendarUpsellEnabled); }

    public String getCalendarUpsellURL() { return get(ZAttrProvisioning.A_zmailFeatureCalendarUpsellURL); }    

    public boolean getTasks() { return getBool(ZAttrProvisioning.A_zmailFeatureTasksEnabled); }

    public boolean getTagging() { return getBool(ZAttrProvisioning.A_zmailFeatureTaggingEnabled); }

    public boolean getOptions() { return getBool(ZAttrProvisioning.A_zmailFeatureOptionsEnabled); }
    
    public boolean getAdvancedSearch() { return getBool(ZAttrProvisioning.A_zmailFeatureAdvancedSearchEnabled); }

    public boolean getSavedSearches() { return getBool(ZAttrProvisioning.A_zmailFeatureSavedSearchesEnabled); }

    public boolean getConversations() { return getBool(ZAttrProvisioning.A_zmailFeatureConversationsEnabled); }

    public boolean getChangePassword() { return getBool(ZAttrProvisioning.A_zmailFeatureChangePasswordEnabled); }

    public boolean getInitialSearchPreference() { return getBool(ZAttrProvisioning.A_zmailFeatureInitialSearchPreferenceEnabled); }

    public boolean getFilters() { return getBool(ZAttrProvisioning.A_zmailFeatureFiltersEnabled); }

    public boolean getGal() { return getBool(ZAttrProvisioning.A_zmailFeatureGalEnabled); }

    public boolean getHtmlCompose() { return getBool(ZAttrProvisioning.A_zmailFeatureHtmlComposeEnabled); }

    public boolean getIM() { return getBool(ZAttrProvisioning.A_zmailFeatureIMEnabled); }

    public boolean getViewInHtml() { return getBool(ZAttrProvisioning.A_zmailFeatureViewInHtmlEnabled); }

    public boolean getSharing() { return getBool(ZAttrProvisioning.A_zmailFeatureSharingEnabled); }

    public boolean getMailForwarding() { return getBool(ZAttrProvisioning.A_zmailFeatureMailForwardingEnabled); }

    public boolean getMailForwardingInFilter() { return getBool(ZAttrProvisioning.A_zmailFeatureMailForwardingInFiltersEnabled); }

    public boolean getMobileSync() { return getBool(ZAttrProvisioning.A_zmailFeatureMobileSyncEnabled); }

    public boolean getSkinChange() { return getBool(ZAttrProvisioning.A_zmailFeatureSkinChangeEnabled); }

    public boolean getNotebook() { return false; } //bug:56196 getBool(ZAttrProvisioning.A_zmailFeatureNotebookEnabled);

    public boolean getBriefcases() { return getBool(ZAttrProvisioning.A_zmailFeatureBriefcasesEnabled); }

    public boolean getGalAutoComplete() { return getBool(ZAttrProvisioning.A_zmailFeatureGalAutoCompleteEnabled); }

    public boolean getOutOfOfficeReply() { return getBool(ZAttrProvisioning.A_zmailFeatureOutOfOfficeReplyEnabled); }

    public boolean getNewMailNotification() { return getBool(ZAttrProvisioning.A_zmailFeatureNewMailNotificationEnabled); }

    public boolean getIdentities() { return getBool(ZAttrProvisioning.A_zmailFeatureIdentitiesEnabled); }

    public boolean getPop3DataSource() { return getBool(ZAttrProvisioning.A_zmailFeaturePop3DataSourceEnabled); }
    
    public boolean getGroupcalendarEnabled() { return getBool(ZAttrProvisioning.A_zmailFeatureGroupCalendarEnabled); }

    public boolean getDataSourceImportOnLogin() { return getBool(ZAttrProvisioning.A_zmailDataSourceImportOnLogin); }

    public boolean getFlagging() { return getBool(ZAttrProvisioning.A_zmailFeatureFlaggingEnabled); }

    public boolean getMailPriority() { return getBool(ZAttrProvisioning.A_zmailFeatureMailPriorityEnabled); }

    public boolean getPortalEnabled() { return getBool(ZAttrProvisioning.A_zmailFeaturePortalEnabled); }

    public boolean getContactsDetailedSearch() { return getBool(ZAttrProvisioning.A_zmailFeatureContactsDetailedSearchEnabled); }

    public boolean getDiscardFilterEnabled() { return getBool(ZAttrProvisioning.A_zmailFeatureDiscardInFiltersEnabled); }
    
    // defaults to TRUE
    public boolean getWebSearchEnabled() { return get(ZAttrProvisioning.A_zmailFeatureWebSearchEnabled) == null ||
    											  getBool(ZAttrProvisioning.A_zmailFeatureWebSearchEnabled); }

    // defaults to TRUE
    public boolean getWebClientShowOfflineLink() { return get(ZAttrProvisioning.A_zmailWebClientShowOfflineLink) == null ||
                                                          getBool(ZAttrProvisioning.A_zmailWebClientShowOfflineLink); }

	// defaults to TRUE
	public boolean getNewAddrBookEnabled() { return get(ZAttrProvisioning.A_zmailFeatureNewAddrBookEnabled) == null ||
													getBool(ZAttrProvisioning.A_zmailFeatureNewAddrBookEnabled); }
	// defaults to TRUE
	public boolean getPop3Enabled() { return	get(ZAttrProvisioning.A_zmailPop3Enabled) == null ||
												getBool(ZAttrProvisioning.A_zmailPop3Enabled); }
	// defaults to TRUE
	public boolean getSpam() { return	get(ZAttrProvisioning.A_zmailFeatureAntispamEnabled) == null ||
												getBool(ZAttrProvisioning.A_zmailFeatureAntispamEnabled); }
}

