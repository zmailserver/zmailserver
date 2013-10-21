/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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

package generated.zcsclient.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import generated.zcsclient.admin.testAbortHsmRequest;
import generated.zcsclient.admin.testAbortHsmResponse;
import generated.zcsclient.admin.testAbortXMbxSearchRequest;
import generated.zcsclient.admin.testAbortXMbxSearchResponse;
import generated.zcsclient.admin.testActivateLicenseRequest;
import generated.zcsclient.admin.testActivateLicenseResponse;
import generated.zcsclient.admin.testAddAccountAliasRequest;
import generated.zcsclient.admin.testAddAccountAliasResponse;
import generated.zcsclient.admin.testAddAccountLoggerRequest;
import generated.zcsclient.admin.testAddAccountLoggerResponse;
import generated.zcsclient.admin.testAddDistributionListAliasRequest;
import generated.zcsclient.admin.testAddDistributionListAliasResponse;
import generated.zcsclient.admin.testAddDistributionListMemberRequest;
import generated.zcsclient.admin.testAddDistributionListMemberResponse;
import generated.zcsclient.admin.testAddGalSyncDataSourceRequest;
import generated.zcsclient.admin.testAddGalSyncDataSourceResponse;
import generated.zcsclient.admin.testAdminCreateWaitSetRequest;
import generated.zcsclient.admin.testAdminCreateWaitSetResponse;
import generated.zcsclient.admin.testAdminDestroyWaitSetRequest;
import generated.zcsclient.admin.testAdminDestroyWaitSetResponse;
import generated.zcsclient.admin.testAdminWaitSetRequest;
import generated.zcsclient.admin.testAdminWaitSetResponse;
import generated.zcsclient.admin.testAuthRequest;
import generated.zcsclient.admin.testAuthResponse;
import generated.zcsclient.admin.testAutoCompleteGalRequest;
import generated.zcsclient.admin.testAutoCompleteGalResponse;
import generated.zcsclient.admin.testAutoProvAccountRequest;
import generated.zcsclient.admin.testAutoProvAccountResponse;
import generated.zcsclient.admin.testAutoProvTaskControlRequest;
import generated.zcsclient.admin.testAutoProvTaskControlResponse;
import generated.zcsclient.admin.testBackupAccountQueryRequest;
import generated.zcsclient.admin.testBackupAccountQueryResponse;
import generated.zcsclient.admin.testBackupQueryRequest;
import generated.zcsclient.admin.testBackupQueryResponse;
import generated.zcsclient.admin.testBackupRequest;
import generated.zcsclient.admin.testBackupResponse;
import generated.zcsclient.admin.testCancelPendingRemoteWipeRequest;
import generated.zcsclient.admin.testCancelPendingRemoteWipeResponse;
import generated.zcsclient.admin.testCheckAuthConfigRequest;
import generated.zcsclient.admin.testCheckAuthConfigResponse;
import generated.zcsclient.admin.testCheckBlobConsistencyRequest;
import generated.zcsclient.admin.testCheckBlobConsistencyResponse;
import generated.zcsclient.admin.testCheckDirectoryRequest;
import generated.zcsclient.admin.testCheckDirectoryResponse;
import generated.zcsclient.admin.testCheckDomainMXRecordRequest;
import generated.zcsclient.admin.testCheckDomainMXRecordResponse;
import generated.zcsclient.admin.testCheckExchangeAuthRequest;
import generated.zcsclient.admin.testCheckExchangeAuthResponse;
import generated.zcsclient.admin.testCheckGalConfigRequest;
import generated.zcsclient.admin.testCheckGalConfigResponse;
import generated.zcsclient.admin.testCheckHealthRequest;
import generated.zcsclient.admin.testCheckHealthResponse;
import generated.zcsclient.admin.testCheckHostnameResolveRequest;
import generated.zcsclient.admin.testCheckHostnameResolveResponse;
import generated.zcsclient.admin.testCheckPasswordStrengthRequest;
import generated.zcsclient.admin.testCheckPasswordStrengthResponse;
import generated.zcsclient.admin.testCheckRightRequest;
import generated.zcsclient.admin.testCheckRightResponse;
import generated.zcsclient.admin.testClearCookieRequest;
import generated.zcsclient.admin.testClearCookieResponse;
import generated.zcsclient.admin.testComputeAggregateQuotaUsageRequest;
import generated.zcsclient.admin.testComputeAggregateQuotaUsageResponse;
import generated.zcsclient.admin.testConfigureZimletRequest;
import generated.zcsclient.admin.testConfigureZimletResponse;
import generated.zcsclient.admin.testCopyCosRequest;
import generated.zcsclient.admin.testCopyCosResponse;
import generated.zcsclient.admin.testCountAccountRequest;
import generated.zcsclient.admin.testCountAccountResponse;
import generated.zcsclient.admin.testCountObjectsRequest;
import generated.zcsclient.admin.testCountObjectsResponse;
import generated.zcsclient.admin.testCreateAccountRequest;
import generated.zcsclient.admin.testCreateAccountResponse;
import generated.zcsclient.admin.testCreateArchiveRequest;
import generated.zcsclient.admin.testCreateArchiveResponse;
import generated.zcsclient.admin.testCreateCalendarResourceRequest;
import generated.zcsclient.admin.testCreateCalendarResourceResponse;
import generated.zcsclient.admin.testCreateCosRequest;
import generated.zcsclient.admin.testCreateCosResponse;
import generated.zcsclient.admin.testCreateDataSourceRequest;
import generated.zcsclient.admin.testCreateDataSourceResponse;
import generated.zcsclient.admin.testCreateDistributionListRequest;
import generated.zcsclient.admin.testCreateDistributionListResponse;
import generated.zcsclient.admin.testCreateDomainRequest;
import generated.zcsclient.admin.testCreateDomainResponse;
import generated.zcsclient.admin.testCreateGalSyncAccountRequest;
import generated.zcsclient.admin.testCreateGalSyncAccountResponse;
import generated.zcsclient.admin.testCreateLDAPEntryRequest;
import generated.zcsclient.admin.testCreateLDAPEntryResponse;
import generated.zcsclient.admin.testCreateServerRequest;
import generated.zcsclient.admin.testCreateServerResponse;
import generated.zcsclient.admin.testCreateSystemRetentionPolicyRequest;
import generated.zcsclient.admin.testCreateSystemRetentionPolicyResponse;
import generated.zcsclient.admin.testCreateUCServiceRequest;
import generated.zcsclient.admin.testCreateUCServiceResponse;
import generated.zcsclient.admin.testCreateVolumeRequest;
import generated.zcsclient.admin.testCreateVolumeResponse;
import generated.zcsclient.admin.testCreateXMPPComponentRequest;
import generated.zcsclient.admin.testCreateXMPPComponentResponse;
import generated.zcsclient.admin.testCreateXMbxSearchRequest;
import generated.zcsclient.admin.testCreateXMbxSearchResponse;
import generated.zcsclient.admin.testCreateZimletRequest;
import generated.zcsclient.admin.testCreateZimletResponse;
import generated.zcsclient.admin.testDelegateAuthRequest;
import generated.zcsclient.admin.testDelegateAuthResponse;
import generated.zcsclient.admin.testDeleteAccountRequest;
import generated.zcsclient.admin.testDeleteAccountResponse;
import generated.zcsclient.admin.testDeleteCalendarResourceRequest;
import generated.zcsclient.admin.testDeleteCalendarResourceResponse;
import generated.zcsclient.admin.testDeleteCosRequest;
import generated.zcsclient.admin.testDeleteCosResponse;
import generated.zcsclient.admin.testDeleteDataSourceRequest;
import generated.zcsclient.admin.testDeleteDataSourceResponse;
import generated.zcsclient.admin.testDeleteDistributionListRequest;
import generated.zcsclient.admin.testDeleteDistributionListResponse;
import generated.zcsclient.admin.testDeleteDomainRequest;
import generated.zcsclient.admin.testDeleteDomainResponse;
import generated.zcsclient.admin.testDeleteGalSyncAccountRequest;
import generated.zcsclient.admin.testDeleteGalSyncAccountResponse;
import generated.zcsclient.admin.testDeleteLDAPEntryRequest;
import generated.zcsclient.admin.testDeleteLDAPEntryResponse;
import generated.zcsclient.admin.testDeleteMailboxRequest;
import generated.zcsclient.admin.testDeleteMailboxResponse;
import generated.zcsclient.admin.testDeleteServerRequest;
import generated.zcsclient.admin.testDeleteServerResponse;
import generated.zcsclient.admin.testDeleteSystemRetentionPolicyRequest;
import generated.zcsclient.admin.testDeleteSystemRetentionPolicyResponse;
import generated.zcsclient.admin.testDeleteUCServiceRequest;
import generated.zcsclient.admin.testDeleteUCServiceResponse;
import generated.zcsclient.admin.testDeleteVolumeRequest;
import generated.zcsclient.admin.testDeleteVolumeResponse;
import generated.zcsclient.admin.testDeleteXMPPComponentRequest;
import generated.zcsclient.admin.testDeleteXMPPComponentResponse;
import generated.zcsclient.admin.testDeleteXMbxSearchRequest;
import generated.zcsclient.admin.testDeleteXMbxSearchResponse;
import generated.zcsclient.admin.testDeleteZimletRequest;
import generated.zcsclient.admin.testDeleteZimletResponse;
import generated.zcsclient.admin.testDeployZimletRequest;
import generated.zcsclient.admin.testDeployZimletResponse;
import generated.zcsclient.admin.testDisableArchiveRequest;
import generated.zcsclient.admin.testDisableArchiveResponse;
import generated.zcsclient.admin.testDumpSessionsRequest;
import generated.zcsclient.admin.testDumpSessionsResponse;
import generated.zcsclient.admin.testEnableArchiveRequest;
import generated.zcsclient.admin.testEnableArchiveResponse;
import generated.zcsclient.admin.testExportAndDeleteItemsRequest;
import generated.zcsclient.admin.testExportAndDeleteItemsResponse;
import generated.zcsclient.admin.testExportMailboxRequest;
import generated.zcsclient.admin.testExportMailboxResponse;
import generated.zcsclient.admin.testFailoverClusterServiceRequest;
import generated.zcsclient.admin.testFailoverClusterServiceResponse;
import generated.zcsclient.admin.testFixCalendarEndTimeRequest;
import generated.zcsclient.admin.testFixCalendarEndTimeResponse;
import generated.zcsclient.admin.testFixCalendarPriorityRequest;
import generated.zcsclient.admin.testFixCalendarPriorityResponse;
import generated.zcsclient.admin.testFixCalendarTZRequest;
import generated.zcsclient.admin.testFixCalendarTZResponse;
import generated.zcsclient.admin.testFlushCacheRequest;
import generated.zcsclient.admin.testFlushCacheResponse;
import generated.zcsclient.admin.testGenCSRRequest;
import generated.zcsclient.admin.testGenCSRResponse;
import generated.zcsclient.admin.testGetAccountInfoRequest;
import generated.zcsclient.admin.testGetAccountInfoResponse;
import generated.zcsclient.admin.testGetAccountLoggersRequest;
import generated.zcsclient.admin.testGetAccountLoggersResponse;
import generated.zcsclient.admin.testGetAccountMembershipRequest;
import generated.zcsclient.admin.testGetAccountMembershipResponse;
import generated.zcsclient.admin.testGetAccountRequest;
import generated.zcsclient.admin.testGetAccountResponse;
import generated.zcsclient.admin.testGetAdminConsoleUICompRequest;
import generated.zcsclient.admin.testGetAdminConsoleUICompResponse;
import generated.zcsclient.admin.testGetAdminExtensionZimletsRequest;
import generated.zcsclient.admin.testGetAdminExtensionZimletsResponse;
import generated.zcsclient.admin.testGetAdminSavedSearchesRequest;
import generated.zcsclient.admin.testGetAdminSavedSearchesResponse;
import generated.zcsclient.admin.testGetAggregateQuotaUsageOnServerRequest;
import generated.zcsclient.admin.testGetAggregateQuotaUsageOnServerResponse;
import generated.zcsclient.admin.testGetAllAccountLoggersRequest;
import generated.zcsclient.admin.testGetAllAccountLoggersResponse;
import generated.zcsclient.admin.testGetAllAccountsRequest;
import generated.zcsclient.admin.testGetAllAccountsResponse;
import generated.zcsclient.admin.testGetAllAdminAccountsRequest;
import generated.zcsclient.admin.testGetAllAdminAccountsResponse;
import generated.zcsclient.admin.testGetAllCalendarResourcesRequest;
import generated.zcsclient.admin.testGetAllCalendarResourcesResponse;
import generated.zcsclient.admin.testGetAllConfigRequest;
import generated.zcsclient.admin.testGetAllConfigResponse;
import generated.zcsclient.admin.testGetAllCosRequest;
import generated.zcsclient.admin.testGetAllCosResponse;
import generated.zcsclient.admin.testGetAllDistributionListsRequest;
import generated.zcsclient.admin.testGetAllDistributionListsResponse;
import generated.zcsclient.admin.testGetAllDomainsRequest;
import generated.zcsclient.admin.testGetAllDomainsResponse;
import generated.zcsclient.admin.testGetAllEffectiveRightsRequest;
import generated.zcsclient.admin.testGetAllEffectiveRightsResponse;
import generated.zcsclient.admin.testGetAllFreeBusyProvidersRequest;
import generated.zcsclient.admin.testGetAllFreeBusyProvidersResponse;
import generated.zcsclient.admin.testGetAllLocalesRequest;
import generated.zcsclient.admin.testGetAllLocalesResponse;
import generated.zcsclient.admin.testGetAllMailboxesRequest;
import generated.zcsclient.admin.testGetAllMailboxesResponse;
import generated.zcsclient.admin.testGetAllRightsRequest;
import generated.zcsclient.admin.testGetAllRightsResponse;
import generated.zcsclient.admin.testGetAllServersRequest;
import generated.zcsclient.admin.testGetAllServersResponse;
import generated.zcsclient.admin.testGetAllSkinsRequest;
import generated.zcsclient.admin.testGetAllSkinsResponse;
import generated.zcsclient.admin.testGetAllUCProvidersRequest;
import generated.zcsclient.admin.testGetAllUCProvidersResponse;
import generated.zcsclient.admin.testGetAllUCServicesRequest;
import generated.zcsclient.admin.testGetAllUCServicesResponse;
import generated.zcsclient.admin.testGetAllVolumesRequest;
import generated.zcsclient.admin.testGetAllVolumesResponse;
import generated.zcsclient.admin.testGetAllXMPPComponentsRequest;
import generated.zcsclient.admin.testGetAllXMPPComponentsResponse;
import generated.zcsclient.admin.testGetAllZimletsRequest;
import generated.zcsclient.admin.testGetAllZimletsResponse;
import generated.zcsclient.admin.testGetApplianceHSMFSRequest;
import generated.zcsclient.admin.testGetApplianceHSMFSResponse;
import generated.zcsclient.admin.testGetAttributeInfoRequest;
import generated.zcsclient.admin.testGetAttributeInfoResponse;
import generated.zcsclient.admin.testGetCSRRequest;
import generated.zcsclient.admin.testGetCSRResponse;
import generated.zcsclient.admin.testGetCalendarResourceRequest;
import generated.zcsclient.admin.testGetCalendarResourceResponse;
import generated.zcsclient.admin.testGetCertRequest;
import generated.zcsclient.admin.testGetCertResponse;
import generated.zcsclient.admin.testGetClusterStatusRequest;
import generated.zcsclient.admin.testGetClusterStatusResponse;
import generated.zcsclient.admin.testGetConfigRequest;
import generated.zcsclient.admin.testGetConfigResponse;
import generated.zcsclient.admin.testGetCosRequest;
import generated.zcsclient.admin.testGetCosResponse;
import generated.zcsclient.admin.testGetCreateObjectAttrsRequest;
import generated.zcsclient.admin.testGetCreateObjectAttrsResponse;
import generated.zcsclient.admin.testGetCurrentVolumesRequest;
import generated.zcsclient.admin.testGetCurrentVolumesResponse;
import generated.zcsclient.admin.testGetDataSourcesRequest;
import generated.zcsclient.admin.testGetDataSourcesResponse;
import generated.zcsclient.admin.testGetDelegatedAdminConstraintsRequest;
import generated.zcsclient.admin.testGetDelegatedAdminConstraintsResponse;
import generated.zcsclient.admin.testGetDeviceStatusRequest;
import generated.zcsclient.admin.testGetDeviceStatusResponse;
import generated.zcsclient.admin.testGetDevicesCountRequest;
import generated.zcsclient.admin.testGetDevicesCountResponse;
import generated.zcsclient.admin.testGetDevicesCountSinceLastUsedRequest;
import generated.zcsclient.admin.testGetDevicesCountSinceLastUsedResponse;
import generated.zcsclient.admin.testGetDevicesCountUsedTodayRequest;
import generated.zcsclient.admin.testGetDevicesCountUsedTodayResponse;
import generated.zcsclient.admin.testGetDevicesRequest;
import generated.zcsclient.admin.testGetDevicesResponse;
import generated.zcsclient.admin.testGetDistributionListMembershipRequest;
import generated.zcsclient.admin.testGetDistributionListMembershipResponse;
import generated.zcsclient.admin.testGetDistributionListRequest;
import generated.zcsclient.admin.testGetDistributionListResponse;
import generated.zcsclient.admin.testGetDomainInfoRequest;
import generated.zcsclient.admin.testGetDomainInfoResponse;
import generated.zcsclient.admin.testGetDomainRequest;
import generated.zcsclient.admin.testGetDomainResponse;
import generated.zcsclient.admin.testGetEffectiveRightsRequest;
import generated.zcsclient.admin.testGetEffectiveRightsResponse;
import generated.zcsclient.admin.testGetFreeBusyQueueInfoRequest;
import generated.zcsclient.admin.testGetFreeBusyQueueInfoResponse;
import generated.zcsclient.admin.testGetGrantsRequest;
import generated.zcsclient.admin.testGetGrantsResponse;
import generated.zcsclient.admin.testGetHsmStatusRequest;
import generated.zcsclient.admin.testGetHsmStatusResponse;
import generated.zcsclient.admin.testGetLDAPEntriesRequest;
import generated.zcsclient.admin.testGetLDAPEntriesResponse;
import generated.zcsclient.admin.testGetLicenseInfoRequest;
import generated.zcsclient.admin.testGetLicenseInfoResponse;
import generated.zcsclient.admin.testGetLicenseRequest;
import generated.zcsclient.admin.testGetLicenseResponse;
import generated.zcsclient.admin.testGetLoggerStatsRequest;
import generated.zcsclient.admin.testGetLoggerStatsResponse;
import generated.zcsclient.admin.testGetMailQueueInfoRequest;
import generated.zcsclient.admin.testGetMailQueueInfoResponse;
import generated.zcsclient.admin.testGetMailQueueRequest;
import generated.zcsclient.admin.testGetMailQueueResponse;
import generated.zcsclient.admin.testGetMailboxRequest;
import generated.zcsclient.admin.testGetMailboxResponse;
import generated.zcsclient.admin.testGetMailboxStatsRequest;
import generated.zcsclient.admin.testGetMailboxStatsResponse;
import generated.zcsclient.admin.testGetMailboxVersionRequest;
import generated.zcsclient.admin.testGetMailboxVersionResponse;
import generated.zcsclient.admin.testGetMailboxVolumesRequest;
import generated.zcsclient.admin.testGetMailboxVolumesResponse;
import generated.zcsclient.admin.testGetMemcachedClientConfigRequest;
import generated.zcsclient.admin.testGetMemcachedClientConfigResponse;
import generated.zcsclient.admin.testGetQuotaUsageRequest;
import generated.zcsclient.admin.testGetQuotaUsageResponse;
import generated.zcsclient.admin.testGetRightRequest;
import generated.zcsclient.admin.testGetRightResponse;
import generated.zcsclient.admin.testGetRightsDocRequest;
import generated.zcsclient.admin.testGetRightsDocResponse;
import generated.zcsclient.admin.testGetSMIMEConfigRequest;
import generated.zcsclient.admin.testGetSMIMEConfigResponse;
import generated.zcsclient.admin.testGetServerNIfsRequest;
import generated.zcsclient.admin.testGetServerNIfsResponse;
import generated.zcsclient.admin.testGetServerRequest;
import generated.zcsclient.admin.testGetServerResponse;
import generated.zcsclient.admin.testGetServerStatsRequest;
import generated.zcsclient.admin.testGetServerStatsResponse;
import generated.zcsclient.admin.testGetServiceStatusRequest;
import generated.zcsclient.admin.testGetServiceStatusResponse;
import generated.zcsclient.admin.testGetSessionsRequest;
import generated.zcsclient.admin.testGetSessionsResponse;
import generated.zcsclient.admin.testGetShareInfoRequest;
import generated.zcsclient.admin.testGetShareInfoResponse;
import generated.zcsclient.admin.testGetSystemRetentionPolicyRequest;
import generated.zcsclient.admin.testGetSystemRetentionPolicyResponse;
import generated.zcsclient.admin.testGetUCServiceRequest;
import generated.zcsclient.admin.testGetUCServiceResponse;
import generated.zcsclient.admin.testGetVersionInfoRequest;
import generated.zcsclient.admin.testGetVersionInfoResponse;
import generated.zcsclient.admin.testGetVolumeRequest;
import generated.zcsclient.admin.testGetVolumeResponse;
import generated.zcsclient.admin.testGetXMPPComponentRequest;
import generated.zcsclient.admin.testGetXMPPComponentResponse;
import generated.zcsclient.admin.testGetXMbxSearchesListRequest;
import generated.zcsclient.admin.testGetXMbxSearchesListResponse;
import generated.zcsclient.admin.testGetZimletRequest;
import generated.zcsclient.admin.testGetZimletResponse;
import generated.zcsclient.admin.testGetZimletStatusRequest;
import generated.zcsclient.admin.testGetZimletStatusResponse;
import generated.zcsclient.admin.testGrantRightRequest;
import generated.zcsclient.admin.testGrantRightResponse;
import generated.zcsclient.admin.testHsmRequest;
import generated.zcsclient.admin.testHsmResponse;
import generated.zcsclient.admin.testInstallCertRequest;
import generated.zcsclient.admin.testInstallCertResponse;
import generated.zcsclient.admin.testInstallLicenseRequest;
import generated.zcsclient.admin.testInstallLicenseResponse;
import generated.zcsclient.admin.testMailQueueActionRequest;
import generated.zcsclient.admin.testMailQueueActionResponse;
import generated.zcsclient.admin.testMailQueueFlushRequest;
import generated.zcsclient.admin.testMailQueueFlushResponse;
import generated.zcsclient.admin.testMigrateAccountRequest;
import generated.zcsclient.admin.testMigrateAccountResponse;
import generated.zcsclient.admin.testModifyAccountRequest;
import generated.zcsclient.admin.testModifyAccountResponse;
import generated.zcsclient.admin.testModifyAdminSavedSearchesRequest;
import generated.zcsclient.admin.testModifyAdminSavedSearchesResponse;
import generated.zcsclient.admin.testModifyCalendarResourceRequest;
import generated.zcsclient.admin.testModifyCalendarResourceResponse;
import generated.zcsclient.admin.testModifyConfigRequest;
import generated.zcsclient.admin.testModifyConfigResponse;
import generated.zcsclient.admin.testModifyCosRequest;
import generated.zcsclient.admin.testModifyCosResponse;
import generated.zcsclient.admin.testModifyDataSourceRequest;
import generated.zcsclient.admin.testModifyDataSourceResponse;
import generated.zcsclient.admin.testModifyDelegatedAdminConstraintsRequest;
import generated.zcsclient.admin.testModifyDelegatedAdminConstraintsResponse;
import generated.zcsclient.admin.testModifyDistributionListRequest;
import generated.zcsclient.admin.testModifyDistributionListResponse;
import generated.zcsclient.admin.testModifyDomainRequest;
import generated.zcsclient.admin.testModifyDomainResponse;
import generated.zcsclient.admin.testModifyLDAPEntryRequest;
import generated.zcsclient.admin.testModifyLDAPEntryResponse;
import generated.zcsclient.admin.testModifySMIMEConfigRequest;
import generated.zcsclient.admin.testModifySMIMEConfigResponse;
import generated.zcsclient.admin.testModifyServerRequest;
import generated.zcsclient.admin.testModifyServerResponse;
import generated.zcsclient.admin.testModifySystemRetentionPolicyRequest;
import generated.zcsclient.admin.testModifySystemRetentionPolicyResponse;
import generated.zcsclient.admin.testModifyUCServiceRequest;
import generated.zcsclient.admin.testModifyUCServiceResponse;
import generated.zcsclient.admin.testModifyVolumeRequest;
import generated.zcsclient.admin.testModifyVolumeResponse;
import generated.zcsclient.admin.testModifyZimletRequest;
import generated.zcsclient.admin.testModifyZimletResponse;
import generated.zcsclient.admin.testMoveBlobsRequest;
import generated.zcsclient.admin.testMoveBlobsResponse;
import generated.zcsclient.admin.testMoveMailboxRequest;
import generated.zcsclient.admin.testMoveMailboxResponse;
import generated.zcsclient.admin.testNoOpRequest;
import generated.zcsclient.admin.testNoOpResponse;
import generated.zcsclient.admin.testPingRequest;
import generated.zcsclient.admin.testPingResponse;
import generated.zcsclient.admin.testPurgeAccountCalendarCacheRequest;
import generated.zcsclient.admin.testPurgeAccountCalendarCacheResponse;
import generated.zcsclient.admin.testPurgeFreeBusyQueueRequest;
import generated.zcsclient.admin.testPurgeFreeBusyQueueResponse;
import generated.zcsclient.admin.testPurgeMessagesRequest;
import generated.zcsclient.admin.testPurgeMessagesResponse;
import generated.zcsclient.admin.testPurgeMovedMailboxRequest;
import generated.zcsclient.admin.testPurgeMovedMailboxResponse;
import generated.zcsclient.admin.testPushFreeBusyRequest;
import generated.zcsclient.admin.testPushFreeBusyResponse;
import generated.zcsclient.admin.testQueryMailboxMoveRequest;
import generated.zcsclient.admin.testQueryMailboxMoveResponse;
import generated.zcsclient.admin.testQueryWaitSetRequest;
import generated.zcsclient.admin.testQueryWaitSetResponse;
import generated.zcsclient.admin.testReIndexRequest;
import generated.zcsclient.admin.testReIndexResponse;
import generated.zcsclient.admin.testRecalculateMailboxCountsRequest;
import generated.zcsclient.admin.testRecalculateMailboxCountsResponse;
import generated.zcsclient.admin.testRegisterMailboxMoveOutRequest;
import generated.zcsclient.admin.testRegisterMailboxMoveOutResponse;
import generated.zcsclient.admin.testReloadAccountRequest;
import generated.zcsclient.admin.testReloadAccountResponse;
import generated.zcsclient.admin.testReloadLocalConfigRequest;
import generated.zcsclient.admin.testReloadLocalConfigResponse;
import generated.zcsclient.admin.testReloadMemcachedClientConfigRequest;
import generated.zcsclient.admin.testReloadMemcachedClientConfigResponse;
import generated.zcsclient.admin.testRemoteWipeRequest;
import generated.zcsclient.admin.testRemoteWipeResponse;
import generated.zcsclient.admin.testRemoveAccountAliasRequest;
import generated.zcsclient.admin.testRemoveAccountAliasResponse;
import generated.zcsclient.admin.testRemoveAccountLoggerRequest;
import generated.zcsclient.admin.testRemoveAccountLoggerResponse;
import generated.zcsclient.admin.testRemoveDeviceRequest;
import generated.zcsclient.admin.testRemoveDeviceResponse;
import generated.zcsclient.admin.testRemoveDistributionListAliasRequest;
import generated.zcsclient.admin.testRemoveDistributionListAliasResponse;
import generated.zcsclient.admin.testRemoveDistributionListMemberRequest;
import generated.zcsclient.admin.testRemoveDistributionListMemberResponse;
import generated.zcsclient.admin.testRenameAccountRequest;
import generated.zcsclient.admin.testRenameAccountResponse;
import generated.zcsclient.admin.testRenameCalendarResourceRequest;
import generated.zcsclient.admin.testRenameCalendarResourceResponse;
import generated.zcsclient.admin.testRenameCosRequest;
import generated.zcsclient.admin.testRenameCosResponse;
import generated.zcsclient.admin.testRenameDistributionListRequest;
import generated.zcsclient.admin.testRenameDistributionListResponse;
import generated.zcsclient.admin.testRenameLDAPEntryRequest;
import generated.zcsclient.admin.testRenameLDAPEntryResponse;
import generated.zcsclient.admin.testRenameUCServiceRequest;
import generated.zcsclient.admin.testRenameUCServiceResponse;
import generated.zcsclient.admin.testResetAllLoggersRequest;
import generated.zcsclient.admin.testResetAllLoggersResponse;
import generated.zcsclient.admin.testRestoreRequest;
import generated.zcsclient.admin.testRestoreResponse;
import generated.zcsclient.admin.testResumeDeviceRequest;
import generated.zcsclient.admin.testResumeDeviceResponse;
import generated.zcsclient.admin.testRevokeRightRequest;
import generated.zcsclient.admin.testRevokeRightResponse;
import generated.zcsclient.admin.testRolloverRedoLogRequest;
import generated.zcsclient.admin.testRolloverRedoLogResponse;
import generated.zcsclient.admin.testRunUnitTestsRequest;
import generated.zcsclient.admin.testRunUnitTestsResponse;
import generated.zcsclient.admin.testScheduleBackupsRequest;
import generated.zcsclient.admin.testScheduleBackupsResponse;
import generated.zcsclient.admin.testSearchAccountsRequest;
import generated.zcsclient.admin.testSearchAccountsResponse;
import generated.zcsclient.admin.testSearchAutoProvDirectoryRequest;
import generated.zcsclient.admin.testSearchAutoProvDirectoryResponse;
import generated.zcsclient.admin.testSearchCalendarResourcesRequest;
import generated.zcsclient.admin.testSearchCalendarResourcesResponse;
import generated.zcsclient.admin.testSearchDirectoryRequest;
import generated.zcsclient.admin.testSearchDirectoryResponse;
import generated.zcsclient.admin.testSearchGalRequest;
import generated.zcsclient.admin.testSearchGalResponse;
import generated.zcsclient.admin.testSearchMultiMailboxRequest;
import generated.zcsclient.admin.testSearchMultiMailboxResponse;
import generated.zcsclient.admin.testSetCurrentVolumeRequest;
import generated.zcsclient.admin.testSetCurrentVolumeResponse;
import generated.zcsclient.admin.testSetPasswordRequest;
import generated.zcsclient.admin.testSetPasswordResponse;
import generated.zcsclient.admin.testSuspendDeviceRequest;
import generated.zcsclient.admin.testSuspendDeviceResponse;
import generated.zcsclient.admin.testSyncGalAccountRequest;
import generated.zcsclient.admin.testSyncGalAccountResponse;
import generated.zcsclient.admin.testUndeployZimletRequest;
import generated.zcsclient.admin.testUndeployZimletResponse;
import generated.zcsclient.admin.testUnloadMailboxRequest;
import generated.zcsclient.admin.testUnloadMailboxResponse;
import generated.zcsclient.admin.testUnregisterMailboxMoveOutRequest;
import generated.zcsclient.admin.testUnregisterMailboxMoveOutResponse;
import generated.zcsclient.admin.testUpdateDeviceStatusRequest;
import generated.zcsclient.admin.testUpdateDeviceStatusResponse;
import generated.zcsclient.admin.testUpdatePresenceSessionIdRequest;
import generated.zcsclient.admin.testUpdatePresenceSessionIdResponse;
import generated.zcsclient.admin.testUploadDomCertRequest;
import generated.zcsclient.admin.testUploadDomCertResponse;
import generated.zcsclient.admin.testUploadProxyCARequest;
import generated.zcsclient.admin.testUploadProxyCAResponse;
import generated.zcsclient.admin.testVerifyCertKeyRequest;
import generated.zcsclient.admin.testVerifyCertKeyResponse;
import generated.zcsclient.admin.testVerifyIndexRequest;
import generated.zcsclient.admin.testVerifyIndexResponse;
import generated.zcsclient.admin.testVerifyStoreManagerRequest;
import generated.zcsclient.admin.testVerifyStoreManagerResponse;
import generated.zcsclient.admin.testVersionCheckRequest;
import generated.zcsclient.admin.testVersionCheckResponse;
import generated.zcsclient.adminext.testBulkIMAPDataImportRequest;
import generated.zcsclient.adminext.testBulkIMAPDataImportResponse;
import generated.zcsclient.adminext.testBulkImportAccountsRequest;
import generated.zcsclient.adminext.testBulkImportAccountsResponse;
import generated.zcsclient.adminext.testGenerateBulkProvisionFileFromLDAPRequest;
import generated.zcsclient.adminext.testGenerateBulkProvisionFileFromLDAPResponse;
import generated.zcsclient.adminext.testGetBulkIMAPImportTaskListRequest;
import generated.zcsclient.adminext.testGetBulkIMAPImportTaskListResponse;
import generated.zcsclient.adminext.testPurgeBulkIMAPImportTasksRequest;
import generated.zcsclient.adminext.testPurgeBulkIMAPImportTasksResponse;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "zcsAdminPortType", targetNamespace = "http://www.zmail.com/wsdl/ZmailService.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    generated.zcsclient.mail.ObjectFactory.class,
    generated.zcsclient.account.ObjectFactory.class,
    generated.zcsclient.adminext.ObjectFactory.class,
    generated.zcsclient.admin.ObjectFactory.class,
    generated.zcsclient.zm.ObjectFactory.class,
    generated.zcsclient.sync.ObjectFactory.class,
    generated.zcsclient.replication.ObjectFactory.class,
    generated.zcsclient.voice.ObjectFactory.class
})
public interface ZcsAdminPortType {


    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAbortHsmResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AbortHsm")
    @WebResult(name = "AbortHsmResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAbortHsmResponse abortHsmRequest(
        @WebParam(name = "AbortHsmRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAbortHsmRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAbortXMbxSearchResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AbortXMbxSearch")
    @WebResult(name = "AbortXMbxSearchResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAbortXMbxSearchResponse abortXMbxSearchRequest(
        @WebParam(name = "AbortXMbxSearchRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAbortXMbxSearchRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testActivateLicenseResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ActivateLicense")
    @WebResult(name = "ActivateLicenseResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testActivateLicenseResponse activateLicenseRequest(
        @WebParam(name = "ActivateLicenseRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testActivateLicenseRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAddAccountAliasResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AddAccountAlias")
    @WebResult(name = "AddAccountAliasResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAddAccountAliasResponse addAccountAliasRequest(
        @WebParam(name = "AddAccountAliasRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAddAccountAliasRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAddAccountLoggerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AddAccountLogger")
    @WebResult(name = "AddAccountLoggerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAddAccountLoggerResponse addAccountLoggerRequest(
        @WebParam(name = "AddAccountLoggerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAddAccountLoggerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAddDistributionListAliasResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AddDistributionListAlias")
    @WebResult(name = "AddDistributionListAliasResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAddDistributionListAliasResponse addDistributionListAliasRequest(
        @WebParam(name = "AddDistributionListAliasRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAddDistributionListAliasRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAddDistributionListMemberResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AddDistributionListMember")
    @WebResult(name = "AddDistributionListMemberResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAddDistributionListMemberResponse addDistributionListMemberRequest(
        @WebParam(name = "AddDistributionListMemberRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAddDistributionListMemberRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAddGalSyncDataSourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AddGalSyncDataSource")
    @WebResult(name = "AddGalSyncDataSourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAddGalSyncDataSourceResponse addGalSyncDataSourceRequest(
        @WebParam(name = "AddGalSyncDataSourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAddGalSyncDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAdminCreateWaitSetResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AdminCreateWaitSet")
    @WebResult(name = "AdminCreateWaitSetResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAdminCreateWaitSetResponse adminCreateWaitSetRequest(
        @WebParam(name = "AdminCreateWaitSetRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAdminCreateWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAdminDestroyWaitSetResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AdminDestroyWaitSet")
    @WebResult(name = "AdminDestroyWaitSetResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAdminDestroyWaitSetResponse adminDestroyWaitSetRequest(
        @WebParam(name = "AdminDestroyWaitSetRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAdminDestroyWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAdminWaitSetResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AdminWaitSet")
    @WebResult(name = "AdminWaitSetResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAdminWaitSetResponse adminWaitSetRequest(
        @WebParam(name = "AdminWaitSetRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAdminWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAuthResponse
     */
    @WebMethod(action = "urn:zmailAdmin/Auth")
    @WebResult(name = "AuthResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAuthResponse authRequest(
        @WebParam(name = "AuthRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAuthRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAutoCompleteGalResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AutoCompleteGal")
    @WebResult(name = "AutoCompleteGalResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAutoCompleteGalResponse autoCompleteGalRequest(
        @WebParam(name = "AutoCompleteGalRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAutoCompleteGalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAutoProvAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AutoProvAccount")
    @WebResult(name = "AutoProvAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAutoProvAccountResponse autoProvAccountRequest(
        @WebParam(name = "AutoProvAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAutoProvAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testAutoProvTaskControlResponse
     */
    @WebMethod(action = "urn:zmailAdmin/AutoProvTaskControl")
    @WebResult(name = "AutoProvTaskControlResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testAutoProvTaskControlResponse autoProvTaskControlRequest(
        @WebParam(name = "AutoProvTaskControlRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testAutoProvTaskControlRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testBackupAccountQueryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/BackupAccountQuery")
    @WebResult(name = "BackupAccountQueryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testBackupAccountQueryResponse backupAccountQueryRequest(
        @WebParam(name = "BackupAccountQueryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testBackupAccountQueryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testBackupQueryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/BackupQuery")
    @WebResult(name = "BackupQueryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testBackupQueryResponse backupQueryRequest(
        @WebParam(name = "BackupQueryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testBackupQueryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testBackupResponse
     */
    @WebMethod(action = "urn:zmailAdmin/Backup")
    @WebResult(name = "BackupResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testBackupResponse backupRequest(
        @WebParam(name = "BackupRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testBackupRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCancelPendingRemoteWipeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CancelPendingRemoteWipe")
    @WebResult(name = "CancelPendingRemoteWipeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCancelPendingRemoteWipeResponse cancelPendingRemoteWipeRequest(
        @WebParam(name = "CancelPendingRemoteWipeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCancelPendingRemoteWipeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckAuthConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckAuthConfig")
    @WebResult(name = "CheckAuthConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckAuthConfigResponse checkAuthConfigRequest(
        @WebParam(name = "CheckAuthConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckAuthConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckBlobConsistencyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckBlobConsistency")
    @WebResult(name = "CheckBlobConsistencyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckBlobConsistencyResponse checkBlobConsistencyRequest(
        @WebParam(name = "CheckBlobConsistencyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckBlobConsistencyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckDirectoryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckDirectory")
    @WebResult(name = "CheckDirectoryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckDirectoryResponse checkDirectoryRequest(
        @WebParam(name = "CheckDirectoryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckDirectoryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckDomainMXRecordResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckDomainMXRecord")
    @WebResult(name = "CheckDomainMXRecordResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckDomainMXRecordResponse checkDomainMXRecordRequest(
        @WebParam(name = "CheckDomainMXRecordRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckDomainMXRecordRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckExchangeAuthResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckExchangeAuth")
    @WebResult(name = "CheckExchangeAuthResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckExchangeAuthResponse checkExchangeAuthRequest(
        @WebParam(name = "CheckExchangeAuthRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckExchangeAuthRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckGalConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckGalConfig")
    @WebResult(name = "CheckGalConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckGalConfigResponse checkGalConfigRequest(
        @WebParam(name = "CheckGalConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckGalConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckHealthResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckHealth")
    @WebResult(name = "CheckHealthResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckHealthResponse checkHealthRequest(
        @WebParam(name = "CheckHealthRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckHealthRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckHostnameResolveResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckHostnameResolve")
    @WebResult(name = "CheckHostnameResolveResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckHostnameResolveResponse checkHostnameResolveRequest(
        @WebParam(name = "CheckHostnameResolveRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckHostnameResolveRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckPasswordStrengthResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckPasswordStrength")
    @WebResult(name = "CheckPasswordStrengthResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckPasswordStrengthResponse checkPasswordStrengthRequest(
        @WebParam(name = "CheckPasswordStrengthRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckPasswordStrengthRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCheckRightResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CheckRight")
    @WebResult(name = "CheckRightResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCheckRightResponse checkRightRequest(
        @WebParam(name = "CheckRightRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCheckRightRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testClearCookieResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ClearCookie")
    @WebResult(name = "ClearCookieResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testClearCookieResponse clearCookieRequest(
        @WebParam(name = "ClearCookieRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testClearCookieRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testComputeAggregateQuotaUsageResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ComputeAggregateQuotaUsage")
    @WebResult(name = "ComputeAggregateQuotaUsageResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testComputeAggregateQuotaUsageResponse computeAggregateQuotaUsageRequest(
        @WebParam(name = "ComputeAggregateQuotaUsageRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testComputeAggregateQuotaUsageRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testConfigureZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ConfigureZimlet")
    @WebResult(name = "ConfigureZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testConfigureZimletResponse configureZimletRequest(
        @WebParam(name = "ConfigureZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testConfigureZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCopyCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CopyCos")
    @WebResult(name = "CopyCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCopyCosResponse copyCosRequest(
        @WebParam(name = "CopyCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCopyCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCountAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CountAccount")
    @WebResult(name = "CountAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCountAccountResponse countAccountRequest(
        @WebParam(name = "CountAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCountAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCountObjectsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CountObjects")
    @WebResult(name = "CountObjectsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCountObjectsResponse countObjectsRequest(
        @WebParam(name = "CountObjectsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCountObjectsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateAccount")
    @WebResult(name = "CreateAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateAccountResponse createAccountRequest(
        @WebParam(name = "CreateAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateArchiveResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateArchive")
    @WebResult(name = "CreateArchiveResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateArchiveResponse createArchiveRequest(
        @WebParam(name = "CreateArchiveRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateArchiveRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateCalendarResourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateCalendarResource")
    @WebResult(name = "CreateCalendarResourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateCalendarResourceResponse createCalendarResourceRequest(
        @WebParam(name = "CreateCalendarResourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateCalendarResourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateCos")
    @WebResult(name = "CreateCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateCosResponse createCosRequest(
        @WebParam(name = "CreateCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateDataSourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateDataSource")
    @WebResult(name = "CreateDataSourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateDataSourceResponse createDataSourceRequest(
        @WebParam(name = "CreateDataSourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateDistributionList")
    @WebResult(name = "CreateDistributionListResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateDistributionListResponse createDistributionListRequest(
        @WebParam(name = "CreateDistributionListRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateDomainResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateDomain")
    @WebResult(name = "CreateDomainResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateDomainResponse createDomainRequest(
        @WebParam(name = "CreateDomainRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateDomainRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateGalSyncAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateGalSyncAccount")
    @WebResult(name = "CreateGalSyncAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateGalSyncAccountResponse createGalSyncAccountRequest(
        @WebParam(name = "CreateGalSyncAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateGalSyncAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateLDAPEntryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateLDAPEntry")
    @WebResult(name = "CreateLDAPEntryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateLDAPEntryResponse createLDAPEntryRequest(
        @WebParam(name = "CreateLDAPEntryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateLDAPEntryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateServerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateServer")
    @WebResult(name = "CreateServerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateServerResponse createServerRequest(
        @WebParam(name = "CreateServerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateServerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateSystemRetentionPolicyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateSystemRetentionPolicy")
    @WebResult(name = "CreateSystemRetentionPolicyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateSystemRetentionPolicyResponse createSystemRetentionPolicyRequest(
        @WebParam(name = "CreateSystemRetentionPolicyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateSystemRetentionPolicyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateUCServiceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateUCService")
    @WebResult(name = "CreateUCServiceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateUCServiceResponse createUCServiceRequest(
        @WebParam(name = "CreateUCServiceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateUCServiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateVolumeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateVolume")
    @WebResult(name = "CreateVolumeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateVolumeResponse createVolumeRequest(
        @WebParam(name = "CreateVolumeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateVolumeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateXMPPComponentResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateXMPPComponent")
    @WebResult(name = "CreateXMPPComponentResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateXMPPComponentResponse createXMPPComponentRequest(
        @WebParam(name = "CreateXMPPComponentRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateXMPPComponentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateXMbxSearchResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateXMbxSearch")
    @WebResult(name = "CreateXMbxSearchResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateXMbxSearchResponse createXMbxSearchRequest(
        @WebParam(name = "CreateXMbxSearchRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateXMbxSearchRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testCreateZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/CreateZimlet")
    @WebResult(name = "CreateZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testCreateZimletResponse createZimletRequest(
        @WebParam(name = "CreateZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testCreateZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDelegateAuthResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DelegateAuth")
    @WebResult(name = "DelegateAuthResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDelegateAuthResponse delegateAuthRequest(
        @WebParam(name = "DelegateAuthRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDelegateAuthRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteAccount")
    @WebResult(name = "DeleteAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteAccountResponse deleteAccountRequest(
        @WebParam(name = "DeleteAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteCalendarResourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteCalendarResource")
    @WebResult(name = "DeleteCalendarResourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteCalendarResourceResponse deleteCalendarResourceRequest(
        @WebParam(name = "DeleteCalendarResourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteCalendarResourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteCos")
    @WebResult(name = "DeleteCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteCosResponse deleteCosRequest(
        @WebParam(name = "DeleteCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteDataSourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteDataSource")
    @WebResult(name = "DeleteDataSourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteDataSourceResponse deleteDataSourceRequest(
        @WebParam(name = "DeleteDataSourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteDistributionList")
    @WebResult(name = "DeleteDistributionListResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteDistributionListResponse deleteDistributionListRequest(
        @WebParam(name = "DeleteDistributionListRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteDomainResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteDomain")
    @WebResult(name = "DeleteDomainResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteDomainResponse deleteDomainRequest(
        @WebParam(name = "DeleteDomainRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteDomainRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteGalSyncAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteGalSyncAccount")
    @WebResult(name = "DeleteGalSyncAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteGalSyncAccountResponse deleteGalSyncAccountRequest(
        @WebParam(name = "DeleteGalSyncAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteGalSyncAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteLDAPEntryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteLDAPEntry")
    @WebResult(name = "DeleteLDAPEntryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteLDAPEntryResponse deleteLDAPEntryRequest(
        @WebParam(name = "DeleteLDAPEntryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteLDAPEntryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteMailbox")
    @WebResult(name = "DeleteMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteMailboxResponse deleteMailboxRequest(
        @WebParam(name = "DeleteMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteServerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteServer")
    @WebResult(name = "DeleteServerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteServerResponse deleteServerRequest(
        @WebParam(name = "DeleteServerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteServerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteSystemRetentionPolicyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteSystemRetentionPolicy")
    @WebResult(name = "DeleteSystemRetentionPolicyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteSystemRetentionPolicyResponse deleteSystemRetentionPolicyRequest(
        @WebParam(name = "DeleteSystemRetentionPolicyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteSystemRetentionPolicyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteUCServiceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteUCService")
    @WebResult(name = "DeleteUCServiceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteUCServiceResponse deleteUCServiceRequest(
        @WebParam(name = "DeleteUCServiceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteUCServiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteVolumeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteVolume")
    @WebResult(name = "DeleteVolumeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteVolumeResponse deleteVolumeRequest(
        @WebParam(name = "DeleteVolumeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteVolumeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteXMPPComponentResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteXMPPComponent")
    @WebResult(name = "DeleteXMPPComponentResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteXMPPComponentResponse deleteXMPPComponentRequest(
        @WebParam(name = "DeleteXMPPComponentRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteXMPPComponentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteXMbxSearchResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteXMbxSearch")
    @WebResult(name = "DeleteXMbxSearchResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteXMbxSearchResponse deleteXMbxSearchRequest(
        @WebParam(name = "DeleteXMbxSearchRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteXMbxSearchRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeleteZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeleteZimlet")
    @WebResult(name = "DeleteZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeleteZimletResponse deleteZimletRequest(
        @WebParam(name = "DeleteZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeleteZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDeployZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DeployZimlet")
    @WebResult(name = "DeployZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDeployZimletResponse deployZimletRequest(
        @WebParam(name = "DeployZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDeployZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDisableArchiveResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DisableArchive")
    @WebResult(name = "DisableArchiveResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDisableArchiveResponse disableArchiveRequest(
        @WebParam(name = "DisableArchiveRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDisableArchiveRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testDumpSessionsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/DumpSessions")
    @WebResult(name = "DumpSessionsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testDumpSessionsResponse dumpSessionsRequest(
        @WebParam(name = "DumpSessionsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testDumpSessionsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testEnableArchiveResponse
     */
    @WebMethod(action = "urn:zmailAdmin/EnableArchive")
    @WebResult(name = "EnableArchiveResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testEnableArchiveResponse enableArchiveRequest(
        @WebParam(name = "EnableArchiveRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testEnableArchiveRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testExportAndDeleteItemsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ExportAndDeleteItems")
    @WebResult(name = "ExportAndDeleteItemsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testExportAndDeleteItemsResponse exportAndDeleteItemsRequest(
        @WebParam(name = "ExportAndDeleteItemsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testExportAndDeleteItemsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testExportMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ExportMailbox")
    @WebResult(name = "ExportMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testExportMailboxResponse exportMailboxRequest(
        @WebParam(name = "ExportMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testExportMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testFailoverClusterServiceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/FailoverClusterService")
    @WebResult(name = "FailoverClusterServiceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testFailoverClusterServiceResponse failoverClusterServiceRequest(
        @WebParam(name = "FailoverClusterServiceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testFailoverClusterServiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testFixCalendarEndTimeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/FixCalendarEndTime")
    @WebResult(name = "FixCalendarEndTimeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testFixCalendarEndTimeResponse fixCalendarEndTimeRequest(
        @WebParam(name = "FixCalendarEndTimeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testFixCalendarEndTimeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testFixCalendarPriorityResponse
     */
    @WebMethod(action = "urn:zmailAdmin/FixCalendarPriority")
    @WebResult(name = "FixCalendarPriorityResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testFixCalendarPriorityResponse fixCalendarPriorityRequest(
        @WebParam(name = "FixCalendarPriorityRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testFixCalendarPriorityRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testFixCalendarTZResponse
     */
    @WebMethod(action = "urn:zmailAdmin/FixCalendarTZ")
    @WebResult(name = "FixCalendarTZResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testFixCalendarTZResponse fixCalendarTZRequest(
        @WebParam(name = "FixCalendarTZRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testFixCalendarTZRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testFlushCacheResponse
     */
    @WebMethod(action = "urn:zmailAdmin/FlushCache")
    @WebResult(name = "FlushCacheResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testFlushCacheResponse flushCacheRequest(
        @WebParam(name = "FlushCacheRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testFlushCacheRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGenCSRResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GenCSR")
    @WebResult(name = "GenCSRResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGenCSRResponse genCSRRequest(
        @WebParam(name = "GenCSRRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGenCSRRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAccountInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAccountInfo")
    @WebResult(name = "GetAccountInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAccountInfoResponse getAccountInfoRequest(
        @WebParam(name = "GetAccountInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAccountInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAccountLoggersResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAccountLoggers")
    @WebResult(name = "GetAccountLoggersResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAccountLoggersResponse getAccountLoggersRequest(
        @WebParam(name = "GetAccountLoggersRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAccountLoggersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAccountMembershipResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAccountMembership")
    @WebResult(name = "GetAccountMembershipResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAccountMembershipResponse getAccountMembershipRequest(
        @WebParam(name = "GetAccountMembershipRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAccountMembershipRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAccount")
    @WebResult(name = "GetAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAccountResponse getAccountRequest(
        @WebParam(name = "GetAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAdminConsoleUICompResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAdminConsoleUIComp")
    @WebResult(name = "GetAdminConsoleUICompResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAdminConsoleUICompResponse getAdminConsoleUICompRequest(
        @WebParam(name = "GetAdminConsoleUICompRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAdminConsoleUICompRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAdminExtensionZimletsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAdminExtensionZimlets")
    @WebResult(name = "GetAdminExtensionZimletsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAdminExtensionZimletsResponse getAdminExtensionZimletsRequest(
        @WebParam(name = "GetAdminExtensionZimletsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAdminExtensionZimletsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAdminSavedSearchesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAdminSavedSearches")
    @WebResult(name = "GetAdminSavedSearchesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAdminSavedSearchesResponse getAdminSavedSearchesRequest(
        @WebParam(name = "GetAdminSavedSearchesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAdminSavedSearchesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAggregateQuotaUsageOnServerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAggregateQuotaUsageOnServer")
    @WebResult(name = "GetAggregateQuotaUsageOnServerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAggregateQuotaUsageOnServerResponse getAggregateQuotaUsageOnServerRequest(
        @WebParam(name = "GetAggregateQuotaUsageOnServerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAggregateQuotaUsageOnServerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllAccountLoggersResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllAccountLoggers")
    @WebResult(name = "GetAllAccountLoggersResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllAccountLoggersResponse getAllAccountLoggersRequest(
        @WebParam(name = "GetAllAccountLoggersRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllAccountLoggersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllAccountsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllAccounts")
    @WebResult(name = "GetAllAccountsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllAccountsResponse getAllAccountsRequest(
        @WebParam(name = "GetAllAccountsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllAccountsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllAdminAccountsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllAdminAccounts")
    @WebResult(name = "GetAllAdminAccountsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllAdminAccountsResponse getAllAdminAccountsRequest(
        @WebParam(name = "GetAllAdminAccountsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllAdminAccountsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllCalendarResourcesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllCalendarResources")
    @WebResult(name = "GetAllCalendarResourcesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllCalendarResourcesResponse getAllCalendarResourcesRequest(
        @WebParam(name = "GetAllCalendarResourcesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllCalendarResourcesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllConfig")
    @WebResult(name = "GetAllConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllConfigResponse getAllConfigRequest(
        @WebParam(name = "GetAllConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllCos")
    @WebResult(name = "GetAllCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllCosResponse getAllCosRequest(
        @WebParam(name = "GetAllCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllDistributionListsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllDistributionLists")
    @WebResult(name = "GetAllDistributionListsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllDistributionListsResponse getAllDistributionListsRequest(
        @WebParam(name = "GetAllDistributionListsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllDistributionListsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllDomainsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllDomains")
    @WebResult(name = "GetAllDomainsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllDomainsResponse getAllDomainsRequest(
        @WebParam(name = "GetAllDomainsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllDomainsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllEffectiveRightsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllEffectiveRights")
    @WebResult(name = "GetAllEffectiveRightsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllEffectiveRightsResponse getAllEffectiveRightsRequest(
        @WebParam(name = "GetAllEffectiveRightsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllEffectiveRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllFreeBusyProvidersResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllFreeBusyProviders")
    @WebResult(name = "GetAllFreeBusyProvidersResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllFreeBusyProvidersResponse getAllFreeBusyProvidersRequest(
        @WebParam(name = "GetAllFreeBusyProvidersRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllFreeBusyProvidersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllLocalesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllLocales")
    @WebResult(name = "GetAllLocalesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllLocalesResponse getAllLocalesRequest(
        @WebParam(name = "GetAllLocalesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllLocalesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllMailboxesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllMailboxes")
    @WebResult(name = "GetAllMailboxesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllMailboxesResponse getAllMailboxesRequest(
        @WebParam(name = "GetAllMailboxesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllMailboxesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllRightsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllRights")
    @WebResult(name = "GetAllRightsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllRightsResponse getAllRightsRequest(
        @WebParam(name = "GetAllRightsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllServersResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllServers")
    @WebResult(name = "GetAllServersResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllServersResponse getAllServersRequest(
        @WebParam(name = "GetAllServersRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllServersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllSkinsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllSkins")
    @WebResult(name = "GetAllSkinsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllSkinsResponse getAllSkinsRequest(
        @WebParam(name = "GetAllSkinsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllSkinsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllUCProvidersResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllUCProviders")
    @WebResult(name = "GetAllUCProvidersResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllUCProvidersResponse getAllUCProvidersRequest(
        @WebParam(name = "GetAllUCProvidersRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllUCProvidersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllUCServicesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllUCServices")
    @WebResult(name = "GetAllUCServicesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllUCServicesResponse getAllUCServicesRequest(
        @WebParam(name = "GetAllUCServicesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllUCServicesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllVolumesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllVolumes")
    @WebResult(name = "GetAllVolumesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllVolumesResponse getAllVolumesRequest(
        @WebParam(name = "GetAllVolumesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllVolumesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllXMPPComponentsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllXMPPComponents")
    @WebResult(name = "GetAllXMPPComponentsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllXMPPComponentsResponse getAllXMPPComponentsRequest(
        @WebParam(name = "GetAllXMPPComponentsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllXMPPComponentsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAllZimletsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAllZimlets")
    @WebResult(name = "GetAllZimletsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAllZimletsResponse getAllZimletsRequest(
        @WebParam(name = "GetAllZimletsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAllZimletsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetApplianceHSMFSResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetApplianceHSMFS")
    @WebResult(name = "GetApplianceHSMFSResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetApplianceHSMFSResponse getApplianceHSMFSRequest(
        @WebParam(name = "GetApplianceHSMFSRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetApplianceHSMFSRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetAttributeInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetAttributeInfo")
    @WebResult(name = "GetAttributeInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetAttributeInfoResponse getAttributeInfoRequest(
        @WebParam(name = "GetAttributeInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetAttributeInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetCSRResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetCSR")
    @WebResult(name = "GetCSRResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetCSRResponse getCSRRequest(
        @WebParam(name = "GetCSRRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetCSRRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetCalendarResourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetCalendarResource")
    @WebResult(name = "GetCalendarResourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetCalendarResourceResponse getCalendarResourceRequest(
        @WebParam(name = "GetCalendarResourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetCalendarResourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetCertResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetCert")
    @WebResult(name = "GetCertResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetCertResponse getCertRequest(
        @WebParam(name = "GetCertRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetCertRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetClusterStatusResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetClusterStatus")
    @WebResult(name = "GetClusterStatusResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetClusterStatusResponse getClusterStatusRequest(
        @WebParam(name = "GetClusterStatusRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetClusterStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetConfig")
    @WebResult(name = "GetConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetConfigResponse getConfigRequest(
        @WebParam(name = "GetConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetCos")
    @WebResult(name = "GetCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetCosResponse getCosRequest(
        @WebParam(name = "GetCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetCreateObjectAttrsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetCreateObjectAttrs")
    @WebResult(name = "GetCreateObjectAttrsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetCreateObjectAttrsResponse getCreateObjectAttrsRequest(
        @WebParam(name = "GetCreateObjectAttrsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetCreateObjectAttrsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetCurrentVolumesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetCurrentVolumes")
    @WebResult(name = "GetCurrentVolumesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetCurrentVolumesResponse getCurrentVolumesRequest(
        @WebParam(name = "GetCurrentVolumesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetCurrentVolumesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDataSourcesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDataSources")
    @WebResult(name = "GetDataSourcesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDataSourcesResponse getDataSourcesRequest(
        @WebParam(name = "GetDataSourcesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDataSourcesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDelegatedAdminConstraintsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDelegatedAdminConstraints")
    @WebResult(name = "GetDelegatedAdminConstraintsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDelegatedAdminConstraintsResponse getDelegatedAdminConstraintsRequest(
        @WebParam(name = "GetDelegatedAdminConstraintsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDelegatedAdminConstraintsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDeviceStatusResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDeviceStatus")
    @WebResult(name = "GetDeviceStatusResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDeviceStatusResponse getDeviceStatusRequest(
        @WebParam(name = "GetDeviceStatusRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDeviceStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDevicesCountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDevicesCount")
    @WebResult(name = "GetDevicesCountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDevicesCountResponse getDevicesCountRequest(
        @WebParam(name = "GetDevicesCountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDevicesCountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDevicesCountSinceLastUsedResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDevicesCountSinceLastUsed")
    @WebResult(name = "GetDevicesCountSinceLastUsedResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDevicesCountSinceLastUsedResponse getDevicesCountSinceLastUsedRequest(
        @WebParam(name = "GetDevicesCountSinceLastUsedRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDevicesCountSinceLastUsedRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDevicesCountUsedTodayResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDevicesCountUsedToday")
    @WebResult(name = "GetDevicesCountUsedTodayResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDevicesCountUsedTodayResponse getDevicesCountUsedTodayRequest(
        @WebParam(name = "GetDevicesCountUsedTodayRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDevicesCountUsedTodayRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDevicesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDevices")
    @WebResult(name = "GetDevicesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDevicesResponse getDevicesRequest(
        @WebParam(name = "GetDevicesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDevicesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDistributionListMembershipResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDistributionListMembership")
    @WebResult(name = "GetDistributionListMembershipResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDistributionListMembershipResponse getDistributionListMembershipRequest(
        @WebParam(name = "GetDistributionListMembershipRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDistributionListMembershipRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDistributionList")
    @WebResult(name = "GetDistributionListResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDistributionListResponse getDistributionListRequest(
        @WebParam(name = "GetDistributionListRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDomainInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDomainInfo")
    @WebResult(name = "GetDomainInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDomainInfoResponse getDomainInfoRequest(
        @WebParam(name = "GetDomainInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDomainInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetDomainResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetDomain")
    @WebResult(name = "GetDomainResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetDomainResponse getDomainRequest(
        @WebParam(name = "GetDomainRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetDomainRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetEffectiveRightsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetEffectiveRights")
    @WebResult(name = "GetEffectiveRightsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetEffectiveRightsResponse getEffectiveRightsRequest(
        @WebParam(name = "GetEffectiveRightsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetEffectiveRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetFreeBusyQueueInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetFreeBusyQueueInfo")
    @WebResult(name = "GetFreeBusyQueueInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetFreeBusyQueueInfoResponse getFreeBusyQueueInfoRequest(
        @WebParam(name = "GetFreeBusyQueueInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetFreeBusyQueueInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetGrantsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetGrants")
    @WebResult(name = "GetGrantsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetGrantsResponse getGrantsRequest(
        @WebParam(name = "GetGrantsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetGrantsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetHsmStatusResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetHsmStatus")
    @WebResult(name = "GetHsmStatusResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetHsmStatusResponse getHsmStatusRequest(
        @WebParam(name = "GetHsmStatusRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetHsmStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetLDAPEntriesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetLDAPEntries")
    @WebResult(name = "GetLDAPEntriesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetLDAPEntriesResponse getLDAPEntriesRequest(
        @WebParam(name = "GetLDAPEntriesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetLDAPEntriesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetLicenseInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetLicenseInfo")
    @WebResult(name = "GetLicenseInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetLicenseInfoResponse getLicenseInfoRequest(
        @WebParam(name = "GetLicenseInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetLicenseInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetLicenseResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetLicense")
    @WebResult(name = "GetLicenseResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetLicenseResponse getLicenseRequest(
        @WebParam(name = "GetLicenseRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetLicenseRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetLoggerStatsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetLoggerStats")
    @WebResult(name = "GetLoggerStatsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetLoggerStatsResponse getLoggerStatsRequest(
        @WebParam(name = "GetLoggerStatsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetLoggerStatsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMailQueueInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMailQueueInfo")
    @WebResult(name = "GetMailQueueInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMailQueueInfoResponse getMailQueueInfoRequest(
        @WebParam(name = "GetMailQueueInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMailQueueInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMailQueueResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMailQueue")
    @WebResult(name = "GetMailQueueResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMailQueueResponse getMailQueueRequest(
        @WebParam(name = "GetMailQueueRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMailQueueRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMailbox")
    @WebResult(name = "GetMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMailboxResponse getMailboxRequest(
        @WebParam(name = "GetMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMailboxStatsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMailboxStats")
    @WebResult(name = "GetMailboxStatsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMailboxStatsResponse getMailboxStatsRequest(
        @WebParam(name = "GetMailboxStatsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMailboxStatsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMailboxVersionResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMailboxVersion")
    @WebResult(name = "GetMailboxVersionResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMailboxVersionResponse getMailboxVersionRequest(
        @WebParam(name = "GetMailboxVersionRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMailboxVersionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMailboxVolumesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMailboxVolumes")
    @WebResult(name = "GetMailboxVolumesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMailboxVolumesResponse getMailboxVolumesRequest(
        @WebParam(name = "GetMailboxVolumesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMailboxVolumesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetMemcachedClientConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetMemcachedClientConfig")
    @WebResult(name = "GetMemcachedClientConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetMemcachedClientConfigResponse getMemcachedClientConfigRequest(
        @WebParam(name = "GetMemcachedClientConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetMemcachedClientConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetQuotaUsageResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetQuotaUsage")
    @WebResult(name = "GetQuotaUsageResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetQuotaUsageResponse getQuotaUsageRequest(
        @WebParam(name = "GetQuotaUsageRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetQuotaUsageRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetRightResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetRight")
    @WebResult(name = "GetRightResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetRightResponse getRightRequest(
        @WebParam(name = "GetRightRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetRightRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetRightsDocResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetRightsDoc")
    @WebResult(name = "GetRightsDocResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetRightsDocResponse getRightsDocRequest(
        @WebParam(name = "GetRightsDocRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetRightsDocRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetSMIMEConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetSMIMEConfig")
    @WebResult(name = "GetSMIMEConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetSMIMEConfigResponse getSMIMEConfigRequest(
        @WebParam(name = "GetSMIMEConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetSMIMEConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetServerNIfsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetServerNIfs")
    @WebResult(name = "GetServerNIfsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetServerNIfsResponse getServerNIfsRequest(
        @WebParam(name = "GetServerNIfsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetServerNIfsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetServerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetServer")
    @WebResult(name = "GetServerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetServerResponse getServerRequest(
        @WebParam(name = "GetServerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetServerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetServerStatsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetServerStats")
    @WebResult(name = "GetServerStatsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetServerStatsResponse getServerStatsRequest(
        @WebParam(name = "GetServerStatsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetServerStatsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetServiceStatusResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetServiceStatus")
    @WebResult(name = "GetServiceStatusResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetServiceStatusResponse getServiceStatusRequest(
        @WebParam(name = "GetServiceStatusRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetServiceStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetSessionsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetSessions")
    @WebResult(name = "GetSessionsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetSessionsResponse getSessionsRequest(
        @WebParam(name = "GetSessionsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetSessionsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetShareInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetShareInfo")
    @WebResult(name = "GetShareInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetShareInfoResponse getShareInfoRequest(
        @WebParam(name = "GetShareInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetShareInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetSystemRetentionPolicyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetSystemRetentionPolicy")
    @WebResult(name = "GetSystemRetentionPolicyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetSystemRetentionPolicyResponse getSystemRetentionPolicyRequest(
        @WebParam(name = "GetSystemRetentionPolicyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetSystemRetentionPolicyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetUCServiceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetUCService")
    @WebResult(name = "GetUCServiceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetUCServiceResponse getUCServiceRequest(
        @WebParam(name = "GetUCServiceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetUCServiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetVersionInfoResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetVersionInfo")
    @WebResult(name = "GetVersionInfoResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetVersionInfoResponse getVersionInfoRequest(
        @WebParam(name = "GetVersionInfoRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetVersionInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetVolumeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetVolume")
    @WebResult(name = "GetVolumeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetVolumeResponse getVolumeRequest(
        @WebParam(name = "GetVolumeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetVolumeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetXMPPComponentResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetXMPPComponent")
    @WebResult(name = "GetXMPPComponentResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetXMPPComponentResponse getXMPPComponentRequest(
        @WebParam(name = "GetXMPPComponentRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetXMPPComponentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetXMbxSearchesListResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetXMbxSearchesList")
    @WebResult(name = "GetXMbxSearchesListResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetXMbxSearchesListResponse getXMbxSearchesListRequest(
        @WebParam(name = "GetXMbxSearchesListRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetXMbxSearchesListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetZimlet")
    @WebResult(name = "GetZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetZimletResponse getZimletRequest(
        @WebParam(name = "GetZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGetZimletStatusResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GetZimletStatus")
    @WebResult(name = "GetZimletStatusResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGetZimletStatusResponse getZimletStatusRequest(
        @WebParam(name = "GetZimletStatusRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGetZimletStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testGrantRightResponse
     */
    @WebMethod(action = "urn:zmailAdmin/GrantRight")
    @WebResult(name = "GrantRightResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testGrantRightResponse grantRightRequest(
        @WebParam(name = "GrantRightRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testGrantRightRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testHsmResponse
     */
    @WebMethod(action = "urn:zmailAdmin/Hsm")
    @WebResult(name = "HsmResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testHsmResponse hsmRequest(
        @WebParam(name = "HsmRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testHsmRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testInstallCertResponse
     */
    @WebMethod(action = "urn:zmailAdmin/InstallCert")
    @WebResult(name = "InstallCertResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testInstallCertResponse installCertRequest(
        @WebParam(name = "InstallCertRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testInstallCertRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testInstallLicenseResponse
     */
    @WebMethod(action = "urn:zmailAdmin/InstallLicense")
    @WebResult(name = "InstallLicenseResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testInstallLicenseResponse installLicenseRequest(
        @WebParam(name = "InstallLicenseRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testInstallLicenseRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testMailQueueActionResponse
     */
    @WebMethod(action = "urn:zmailAdmin/MailQueueAction")
    @WebResult(name = "MailQueueActionResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testMailQueueActionResponse mailQueueActionRequest(
        @WebParam(name = "MailQueueActionRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testMailQueueActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testMailQueueFlushResponse
     */
    @WebMethod(action = "urn:zmailAdmin/MailQueueFlush")
    @WebResult(name = "MailQueueFlushResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testMailQueueFlushResponse mailQueueFlushRequest(
        @WebParam(name = "MailQueueFlushRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testMailQueueFlushRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testMigrateAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/MigrateAccount")
    @WebResult(name = "MigrateAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testMigrateAccountResponse migrateAccountRequest(
        @WebParam(name = "MigrateAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testMigrateAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyAccount")
    @WebResult(name = "ModifyAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyAccountResponse modifyAccountRequest(
        @WebParam(name = "ModifyAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyAdminSavedSearchesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyAdminSavedSearches")
    @WebResult(name = "ModifyAdminSavedSearchesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyAdminSavedSearchesResponse modifyAdminSavedSearchesRequest(
        @WebParam(name = "ModifyAdminSavedSearchesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyAdminSavedSearchesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyCalendarResourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyCalendarResource")
    @WebResult(name = "ModifyCalendarResourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyCalendarResourceResponse modifyCalendarResourceRequest(
        @WebParam(name = "ModifyCalendarResourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyCalendarResourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyConfig")
    @WebResult(name = "ModifyConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyConfigResponse modifyConfigRequest(
        @WebParam(name = "ModifyConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyCos")
    @WebResult(name = "ModifyCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyCosResponse modifyCosRequest(
        @WebParam(name = "ModifyCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyDataSourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyDataSource")
    @WebResult(name = "ModifyDataSourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyDataSourceResponse modifyDataSourceRequest(
        @WebParam(name = "ModifyDataSourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyDelegatedAdminConstraintsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyDelegatedAdminConstraints")
    @WebResult(name = "ModifyDelegatedAdminConstraintsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyDelegatedAdminConstraintsResponse modifyDelegatedAdminConstraintsRequest(
        @WebParam(name = "ModifyDelegatedAdminConstraintsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyDelegatedAdminConstraintsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyDistributionList")
    @WebResult(name = "ModifyDistributionListResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyDistributionListResponse modifyDistributionListRequest(
        @WebParam(name = "ModifyDistributionListRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyDomainResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyDomain")
    @WebResult(name = "ModifyDomainResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyDomainResponse modifyDomainRequest(
        @WebParam(name = "ModifyDomainRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyDomainRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyLDAPEntryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyLDAPEntry")
    @WebResult(name = "ModifyLDAPEntryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyLDAPEntryResponse modifyLDAPEntryRequest(
        @WebParam(name = "ModifyLDAPEntryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyLDAPEntryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifySMIMEConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifySMIMEConfig")
    @WebResult(name = "ModifySMIMEConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifySMIMEConfigResponse modifySMIMEConfigRequest(
        @WebParam(name = "ModifySMIMEConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifySMIMEConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyServerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyServer")
    @WebResult(name = "ModifyServerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyServerResponse modifyServerRequest(
        @WebParam(name = "ModifyServerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyServerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifySystemRetentionPolicyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifySystemRetentionPolicy")
    @WebResult(name = "ModifySystemRetentionPolicyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifySystemRetentionPolicyResponse modifySystemRetentionPolicyRequest(
        @WebParam(name = "ModifySystemRetentionPolicyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifySystemRetentionPolicyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyUCServiceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyUCService")
    @WebResult(name = "ModifyUCServiceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyUCServiceResponse modifyUCServiceRequest(
        @WebParam(name = "ModifyUCServiceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyUCServiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyVolumeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyVolume")
    @WebResult(name = "ModifyVolumeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyVolumeResponse modifyVolumeRequest(
        @WebParam(name = "ModifyVolumeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyVolumeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testModifyZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ModifyZimlet")
    @WebResult(name = "ModifyZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testModifyZimletResponse modifyZimletRequest(
        @WebParam(name = "ModifyZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testModifyZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testMoveBlobsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/MoveBlobs")
    @WebResult(name = "MoveBlobsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testMoveBlobsResponse moveBlobsRequest(
        @WebParam(name = "MoveBlobsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testMoveBlobsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testMoveMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/MoveMailbox")
    @WebResult(name = "MoveMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testMoveMailboxResponse moveMailboxRequest(
        @WebParam(name = "MoveMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testMoveMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testNoOpResponse
     */
    @WebMethod(action = "urn:zmailAdmin/NoOp")
    @WebResult(name = "NoOpResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testNoOpResponse noOpRequest(
        @WebParam(name = "NoOpRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testNoOpRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testPingResponse
     */
    @WebMethod(action = "urn:zmailAdmin/Ping")
    @WebResult(name = "PingResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testPingResponse pingRequest(
        @WebParam(name = "PingRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testPingRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testPurgeAccountCalendarCacheResponse
     */
    @WebMethod(action = "urn:zmailAdmin/PurgeAccountCalendarCache")
    @WebResult(name = "PurgeAccountCalendarCacheResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testPurgeAccountCalendarCacheResponse purgeAccountCalendarCacheRequest(
        @WebParam(name = "PurgeAccountCalendarCacheRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testPurgeAccountCalendarCacheRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testPurgeFreeBusyQueueResponse
     */
    @WebMethod(action = "urn:zmailAdmin/PurgeFreeBusyQueue")
    @WebResult(name = "PurgeFreeBusyQueueResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testPurgeFreeBusyQueueResponse purgeFreeBusyQueueRequest(
        @WebParam(name = "PurgeFreeBusyQueueRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testPurgeFreeBusyQueueRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testPurgeMessagesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/PurgeMessages")
    @WebResult(name = "PurgeMessagesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testPurgeMessagesResponse purgeMessagesRequest(
        @WebParam(name = "PurgeMessagesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testPurgeMessagesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testPurgeMovedMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/PurgeMovedMailbox")
    @WebResult(name = "PurgeMovedMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testPurgeMovedMailboxResponse purgeMovedMailboxRequest(
        @WebParam(name = "PurgeMovedMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testPurgeMovedMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testPushFreeBusyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/PushFreeBusy")
    @WebResult(name = "PushFreeBusyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testPushFreeBusyResponse pushFreeBusyRequest(
        @WebParam(name = "PushFreeBusyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testPushFreeBusyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testQueryMailboxMoveResponse
     */
    @WebMethod(action = "urn:zmailAdmin/QueryMailboxMove")
    @WebResult(name = "QueryMailboxMoveResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testQueryMailboxMoveResponse queryMailboxMoveRequest(
        @WebParam(name = "QueryMailboxMoveRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testQueryMailboxMoveRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testQueryWaitSetResponse
     */
    @WebMethod(action = "urn:zmailAdmin/QueryWaitSet")
    @WebResult(name = "QueryWaitSetResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testQueryWaitSetResponse queryWaitSetRequest(
        @WebParam(name = "QueryWaitSetRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testQueryWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testReIndexResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ReIndex")
    @WebResult(name = "ReIndexResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testReIndexResponse reIndexRequest(
        @WebParam(name = "ReIndexRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testReIndexRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRecalculateMailboxCountsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RecalculateMailboxCounts")
    @WebResult(name = "RecalculateMailboxCountsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRecalculateMailboxCountsResponse recalculateMailboxCountsRequest(
        @WebParam(name = "RecalculateMailboxCountsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRecalculateMailboxCountsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRegisterMailboxMoveOutResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RegisterMailboxMoveOut")
    @WebResult(name = "RegisterMailboxMoveOutResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRegisterMailboxMoveOutResponse registerMailboxMoveOutRequest(
        @WebParam(name = "RegisterMailboxMoveOutRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRegisterMailboxMoveOutRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testReloadAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ReloadAccount")
    @WebResult(name = "ReloadAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testReloadAccountResponse reloadAccountRequest(
        @WebParam(name = "ReloadAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testReloadAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testReloadLocalConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ReloadLocalConfig")
    @WebResult(name = "ReloadLocalConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testReloadLocalConfigResponse reloadLocalConfigRequest(
        @WebParam(name = "ReloadLocalConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testReloadLocalConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testReloadMemcachedClientConfigResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ReloadMemcachedClientConfig")
    @WebResult(name = "ReloadMemcachedClientConfigResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testReloadMemcachedClientConfigResponse reloadMemcachedClientConfigRequest(
        @WebParam(name = "ReloadMemcachedClientConfigRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testReloadMemcachedClientConfigRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRemoteWipeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RemoteWipe")
    @WebResult(name = "RemoteWipeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRemoteWipeResponse remoteWipeRequest(
        @WebParam(name = "RemoteWipeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRemoteWipeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRemoveAccountAliasResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RemoveAccountAlias")
    @WebResult(name = "RemoveAccountAliasResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRemoveAccountAliasResponse removeAccountAliasRequest(
        @WebParam(name = "RemoveAccountAliasRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRemoveAccountAliasRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRemoveAccountLoggerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RemoveAccountLogger")
    @WebResult(name = "RemoveAccountLoggerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRemoveAccountLoggerResponse removeAccountLoggerRequest(
        @WebParam(name = "RemoveAccountLoggerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRemoveAccountLoggerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRemoveDeviceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RemoveDevice")
    @WebResult(name = "RemoveDeviceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRemoveDeviceResponse removeDeviceRequest(
        @WebParam(name = "RemoveDeviceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRemoveDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRemoveDistributionListAliasResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RemoveDistributionListAlias")
    @WebResult(name = "RemoveDistributionListAliasResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRemoveDistributionListAliasResponse removeDistributionListAliasRequest(
        @WebParam(name = "RemoveDistributionListAliasRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRemoveDistributionListAliasRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRemoveDistributionListMemberResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RemoveDistributionListMember")
    @WebResult(name = "RemoveDistributionListMemberResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRemoveDistributionListMemberResponse removeDistributionListMemberRequest(
        @WebParam(name = "RemoveDistributionListMemberRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRemoveDistributionListMemberRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRenameAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RenameAccount")
    @WebResult(name = "RenameAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRenameAccountResponse renameAccountRequest(
        @WebParam(name = "RenameAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRenameAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRenameCalendarResourceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RenameCalendarResource")
    @WebResult(name = "RenameCalendarResourceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRenameCalendarResourceResponse renameCalendarResourceRequest(
        @WebParam(name = "RenameCalendarResourceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRenameCalendarResourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRenameCosResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RenameCos")
    @WebResult(name = "RenameCosResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRenameCosResponse renameCosRequest(
        @WebParam(name = "RenameCosRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRenameCosRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRenameDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RenameDistributionList")
    @WebResult(name = "RenameDistributionListResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRenameDistributionListResponse renameDistributionListRequest(
        @WebParam(name = "RenameDistributionListRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRenameDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRenameLDAPEntryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RenameLDAPEntry")
    @WebResult(name = "RenameLDAPEntryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRenameLDAPEntryResponse renameLDAPEntryRequest(
        @WebParam(name = "RenameLDAPEntryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRenameLDAPEntryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRenameUCServiceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RenameUCService")
    @WebResult(name = "RenameUCServiceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRenameUCServiceResponse renameUCServiceRequest(
        @WebParam(name = "RenameUCServiceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRenameUCServiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testResetAllLoggersResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ResetAllLoggers")
    @WebResult(name = "ResetAllLoggersResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testResetAllLoggersResponse resetAllLoggersRequest(
        @WebParam(name = "ResetAllLoggersRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testResetAllLoggersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRestoreResponse
     */
    @WebMethod(action = "urn:zmailAdmin/Restore")
    @WebResult(name = "RestoreResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRestoreResponse restoreRequest(
        @WebParam(name = "RestoreRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRestoreRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testResumeDeviceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ResumeDevice")
    @WebResult(name = "ResumeDeviceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testResumeDeviceResponse resumeDeviceRequest(
        @WebParam(name = "ResumeDeviceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testResumeDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRevokeRightResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RevokeRight")
    @WebResult(name = "RevokeRightResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRevokeRightResponse revokeRightRequest(
        @WebParam(name = "RevokeRightRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRevokeRightRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRolloverRedoLogResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RolloverRedoLog")
    @WebResult(name = "RolloverRedoLogResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRolloverRedoLogResponse rolloverRedoLogRequest(
        @WebParam(name = "RolloverRedoLogRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRolloverRedoLogRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testRunUnitTestsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/RunUnitTests")
    @WebResult(name = "RunUnitTestsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testRunUnitTestsResponse runUnitTestsRequest(
        @WebParam(name = "RunUnitTestsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testRunUnitTestsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testScheduleBackupsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/ScheduleBackups")
    @WebResult(name = "ScheduleBackupsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testScheduleBackupsResponse scheduleBackupsRequest(
        @WebParam(name = "ScheduleBackupsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testScheduleBackupsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSearchAccountsResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SearchAccounts")
    @WebResult(name = "SearchAccountsResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSearchAccountsResponse searchAccountsRequest(
        @WebParam(name = "SearchAccountsRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSearchAccountsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSearchAutoProvDirectoryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SearchAutoProvDirectory")
    @WebResult(name = "SearchAutoProvDirectoryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSearchAutoProvDirectoryResponse searchAutoProvDirectoryRequest(
        @WebParam(name = "SearchAutoProvDirectoryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSearchAutoProvDirectoryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSearchCalendarResourcesResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SearchCalendarResources")
    @WebResult(name = "SearchCalendarResourcesResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSearchCalendarResourcesResponse searchCalendarResourcesRequest(
        @WebParam(name = "SearchCalendarResourcesRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSearchCalendarResourcesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSearchDirectoryResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SearchDirectory")
    @WebResult(name = "SearchDirectoryResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSearchDirectoryResponse searchDirectoryRequest(
        @WebParam(name = "SearchDirectoryRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSearchDirectoryRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSearchGalResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SearchGal")
    @WebResult(name = "SearchGalResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSearchGalResponse searchGalRequest(
        @WebParam(name = "SearchGalRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSearchGalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSearchMultiMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SearchMultiMailbox")
    @WebResult(name = "SearchMultiMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSearchMultiMailboxResponse searchMultiMailboxRequest(
        @WebParam(name = "SearchMultiMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSearchMultiMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSetCurrentVolumeResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SetCurrentVolume")
    @WebResult(name = "SetCurrentVolumeResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSetCurrentVolumeResponse setCurrentVolumeRequest(
        @WebParam(name = "SetCurrentVolumeRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSetCurrentVolumeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSetPasswordResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SetPassword")
    @WebResult(name = "SetPasswordResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSetPasswordResponse setPasswordRequest(
        @WebParam(name = "SetPasswordRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSetPasswordRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSuspendDeviceResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SuspendDevice")
    @WebResult(name = "SuspendDeviceResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSuspendDeviceResponse suspendDeviceRequest(
        @WebParam(name = "SuspendDeviceRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSuspendDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testSyncGalAccountResponse
     */
    @WebMethod(action = "urn:zmailAdmin/SyncGalAccount")
    @WebResult(name = "SyncGalAccountResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testSyncGalAccountResponse syncGalAccountRequest(
        @WebParam(name = "SyncGalAccountRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testSyncGalAccountRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUndeployZimletResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UndeployZimlet")
    @WebResult(name = "UndeployZimletResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUndeployZimletResponse undeployZimletRequest(
        @WebParam(name = "UndeployZimletRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUndeployZimletRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUnloadMailboxResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UnloadMailbox")
    @WebResult(name = "UnloadMailboxResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUnloadMailboxResponse unloadMailboxRequest(
        @WebParam(name = "UnloadMailboxRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUnloadMailboxRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUnregisterMailboxMoveOutResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UnregisterMailboxMoveOut")
    @WebResult(name = "UnregisterMailboxMoveOutResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUnregisterMailboxMoveOutResponse unregisterMailboxMoveOutRequest(
        @WebParam(name = "UnregisterMailboxMoveOutRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUnregisterMailboxMoveOutRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUpdateDeviceStatusResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UpdateDeviceStatus")
    @WebResult(name = "UpdateDeviceStatusResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUpdateDeviceStatusResponse updateDeviceStatusRequest(
        @WebParam(name = "UpdateDeviceStatusRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUpdateDeviceStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUpdatePresenceSessionIdResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UpdatePresenceSessionId")
    @WebResult(name = "UpdatePresenceSessionIdResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUpdatePresenceSessionIdResponse updatePresenceSessionIdRequest(
        @WebParam(name = "UpdatePresenceSessionIdRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUpdatePresenceSessionIdRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUploadDomCertResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UploadDomCert")
    @WebResult(name = "UploadDomCertResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUploadDomCertResponse uploadDomCertRequest(
        @WebParam(name = "UploadDomCertRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUploadDomCertRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testUploadProxyCAResponse
     */
    @WebMethod(action = "urn:zmailAdmin/UploadProxyCA")
    @WebResult(name = "UploadProxyCAResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testUploadProxyCAResponse uploadProxyCARequest(
        @WebParam(name = "UploadProxyCARequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testUploadProxyCARequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testVerifyCertKeyResponse
     */
    @WebMethod(action = "urn:zmailAdmin/VerifyCertKey")
    @WebResult(name = "VerifyCertKeyResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testVerifyCertKeyResponse verifyCertKeyRequest(
        @WebParam(name = "VerifyCertKeyRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testVerifyCertKeyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testVerifyIndexResponse
     */
    @WebMethod(action = "urn:zmailAdmin/VerifyIndex")
    @WebResult(name = "VerifyIndexResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testVerifyIndexResponse verifyIndexRequest(
        @WebParam(name = "VerifyIndexRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testVerifyIndexRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testVerifyStoreManagerResponse
     */
    @WebMethod(action = "urn:zmailAdmin/VerifyStoreManager")
    @WebResult(name = "VerifyStoreManagerResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testVerifyStoreManagerResponse verifyStoreManagerRequest(
        @WebParam(name = "VerifyStoreManagerRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testVerifyStoreManagerRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.admin.testVersionCheckResponse
     */
    @WebMethod(action = "urn:zmailAdmin/VersionCheck")
    @WebResult(name = "VersionCheckResponse", targetNamespace = "urn:zmailAdmin", partName = "parameters")
    public testVersionCheckResponse versionCheckRequest(
        @WebParam(name = "VersionCheckRequest", targetNamespace = "urn:zmailAdmin", partName = "parameters")
        testVersionCheckRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.adminext.testBulkIMAPDataImportResponse
     */
    @WebMethod(action = "urn:zmailAdminExt/BulkIMAPDataImport")
    @WebResult(name = "BulkIMAPDataImportResponse", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
    public testBulkIMAPDataImportResponse bulkIMAPDataImportRequest(
        @WebParam(name = "BulkIMAPDataImportRequest", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
        testBulkIMAPDataImportRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.adminext.testBulkImportAccountsResponse
     */
    @WebMethod(action = "urn:zmailAdminExt/BulkImportAccounts")
    @WebResult(name = "BulkImportAccountsResponse", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
    public testBulkImportAccountsResponse bulkImportAccountsRequest(
        @WebParam(name = "BulkImportAccountsRequest", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
        testBulkImportAccountsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.adminext.testGenerateBulkProvisionFileFromLDAPResponse
     */
    @WebMethod(action = "urn:zmailAdminExt/GenerateBulkProvisionFileFromLDAP")
    @WebResult(name = "GenerateBulkProvisionFileFromLDAPResponse", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
    public testGenerateBulkProvisionFileFromLDAPResponse generateBulkProvisionFileFromLDAPRequest(
        @WebParam(name = "GenerateBulkProvisionFileFromLDAPRequest", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
        testGenerateBulkProvisionFileFromLDAPRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.adminext.testGetBulkIMAPImportTaskListResponse
     */
    @WebMethod(action = "urn:zmailAdminExt/GetBulkIMAPImportTaskList")
    @WebResult(name = "GetBulkIMAPImportTaskListResponse", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
    public testGetBulkIMAPImportTaskListResponse getBulkIMAPImportTaskListRequest(
        @WebParam(name = "GetBulkIMAPImportTaskListRequest", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
        testGetBulkIMAPImportTaskListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.adminext.testPurgeBulkIMAPImportTasksResponse
     */
    @WebMethod(action = "urn:zmailAdminExt/PurgeBulkIMAPImportTasks")
    @WebResult(name = "PurgeBulkIMAPImportTasksResponse", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
    public testPurgeBulkIMAPImportTasksResponse purgeBulkIMAPImportTasksRequest(
        @WebParam(name = "PurgeBulkIMAPImportTasksRequest", targetNamespace = "urn:zmailAdminExt", partName = "parameters")
        testPurgeBulkIMAPImportTasksRequest parameters);

}
