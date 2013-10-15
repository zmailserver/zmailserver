/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
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

/**
* @class ZaDataSource
* @contructor ZaDataSource
* @param ZaApp app
* this class is a model for zmailDataSource and zmailGalDataSource ldap objects
* @author Greg Solovyev
**/
ZaDataSource = function(noInit) {
	if (noInit) return;	
	ZaItem.call(this, "ZaDataSource");
	this._init();
	this.type = ZaItem.DATASOURCE;
}

ZaDataSource.prototype = new ZaItem;
ZaDataSource.prototype.constructor = ZaDataSource;

ZaDataSource.DS_TYPE_GAL = "gal";
ZaDataSource.GAL_TYPE_ZIMBRA = "zmail";
ZaDataSource.GAL_TYPE_LDAP = "ldap";

ZaDataSource.A_zmailGalLdapAttrMap = "zmailGalLdapAttrMap";
ZaDataSource.A_zmailGalSyncLdapURL = "zmailGalSyncLdapURL";
ZaDataSource.A_zmailGalSyncLdapSearchBase = "zmailGalSyncLdapSearchBase";
ZaDataSource.A_zmailGalSyncLdapFilter = "zmailGalSyncLdapFilter";
ZaDataSource.A_zmailGalSyncLdapAuthMech = "zmailGalSyncLdapAuthMech";
ZaDataSource.A_zmailGalSyncLdapBindDn = "zmailGalSyncLdapBindDn";
ZaDataSource.A_zmailGalSyncLdapBindPassword = "zmailGalSyncLdapBindPassword";
ZaDataSource.A_zmailGalSyncLdapKerberos5Principal = "zmailGalSyncLdapKerberos5Principal";
ZaDataSource.A_zmailGalSyncLdapKerberos5Keytab = "zmailGalSyncLdapKerberos5Keytab";
ZaDataSource.A_zmailGalSyncLdapPageSize = "zmailGalSyncLdapPageSize";
ZaDataSource.A_zmailGalSyncInternalSearchBase = "zmailGalSyncInternalSearchBase";
ZaDataSource.A_zmailGalSyncLdapStartTlsEnabled = "zmailGalSyncLdapStartTlsEnabled";
ZaDataSource.A_zmailGalLastSuccessfulSyncTimestamp = "zmailGalLastSuccessfulSyncTimestamp";
ZaDataSource.A_zmailGalLastFailedSyncTimestamp = "zmailGalLastFailedSyncTimestamp";
ZaDataSource.A_zmailGalStatus = "zmailGalStatus";
ZaDataSource.A_zmailGalType = "zmailGalType";
ZaDataSource.A_zmailGalSyncTimestampFormat = "zmailGalSyncTimestampFormat";
ZaDataSource.A_zmailDataSourceType = "zmailDataSourceType";
ZaDataSource.A_zmailDataSourcePollingInterval = "zmailDataSourcePollingInterval";

ZaDataSource.myXModel = {
    items: [
    	{id:ZaDataSource.A_zmailDataSourceType, type:_STRING_, ref:"attrs/" + ZaDataSource.A_zmailDataSourceType},
    	{id:ZaDataSource.A_zmailGalType, type:_STRING_, ref:"attrs/" + ZaDataSource.A_zmailGalType},
    	{id:ZaDataSource.A_zmailGalSyncLdapURL, type:_LIST_,  listItem:{type:_SHORT_URL_},  ref:"attrs/" + ZaDataSource.A_zmailGalSyncLdapURL},
    	{id:ZaDataSource.A_zmailGalSyncLdapFilter, type:_STRING_, ref:"attrs/" + ZaDataSource.A_zmailGalSyncLdapFilter,required:true},
    	{id:ZaDataSource.A_zmailGalSyncInternalSearchBase, type:_STRING_, ref:"attrs/" + ZaDataSource.A_zmailGalSyncInternalSearchBase},
		{id:ZaDataSource.A_zmailGalSyncLdapBindDn, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDataSource.A_zmailGalSyncLdapBindDn},
		{id:ZaDataSource.A_zmailGalSyncLdapBindPassword, type:_STRING_, ref:"attrs/" + ZaDataSource.A_zmailGalSyncLdapBindPassword},
    	{id:ZaDataSource.A_zmailGalSyncLdapStartTlsEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/" + ZaDataSource.A_zmailGalSyncLdapStartTlsEnabled},
    	{id:ZaDataSource.A_zmailGalSyncLdapAuthMech, type:_STRING_, ref:"attrs/" + ZaDataSource.A_zmailGalSyncLdapAuthMech},
    	{id:ZaDataSource.A_zmailDataSourcePollingInterval,type:_MLIFETIME_, ref:"attrs/" + ZaDataSource.A_zmailDataSourcePollingInterval}
    ]
};