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
import generated.zcsclient.account.testAuthRequest;
import generated.zcsclient.account.testAuthResponse;
import generated.zcsclient.account.testAutoCompleteGalRequest;
import generated.zcsclient.account.testAutoCompleteGalResponse;
import generated.zcsclient.account.testChangePasswordRequest;
import generated.zcsclient.account.testChangePasswordResponse;
import generated.zcsclient.account.testCheckLicenseRequest;
import generated.zcsclient.account.testCheckLicenseResponse;
import generated.zcsclient.account.testCheckRightsRequest;
import generated.zcsclient.account.testCheckRightsResponse;
import generated.zcsclient.account.testCreateDistributionListRequest;
import generated.zcsclient.account.testCreateDistributionListResponse;
import generated.zcsclient.account.testCreateIdentityRequest;
import generated.zcsclient.account.testCreateIdentityResponse;
import generated.zcsclient.account.testCreateSignatureRequest;
import generated.zcsclient.account.testCreateSignatureResponse;
import generated.zcsclient.account.testDeleteIdentityRequest;
import generated.zcsclient.account.testDeleteIdentityResponse;
import generated.zcsclient.account.testDeleteSignatureRequest;
import generated.zcsclient.account.testDeleteSignatureResponse;
import generated.zcsclient.account.testDiscoverRightsRequest;
import generated.zcsclient.account.testDiscoverRightsResponse;
import generated.zcsclient.account.testDistributionListActionRequest;
import generated.zcsclient.account.testDistributionListActionResponse;
import generated.zcsclient.account.testEndSessionRequest;
import generated.zcsclient.account.testEndSessionResponse;
import generated.zcsclient.account.testGetAccountDistributionListsRequest;
import generated.zcsclient.account.testGetAccountDistributionListsResponse;
import generated.zcsclient.account.testGetAccountInfoRequest;
import generated.zcsclient.account.testGetAccountInfoResponse;
import generated.zcsclient.account.testGetAllLocalesRequest;
import generated.zcsclient.account.testGetAllLocalesResponse;
import generated.zcsclient.account.testGetAvailableCsvFormatsRequest;
import generated.zcsclient.account.testGetAvailableCsvFormatsResponse;
import generated.zcsclient.account.testGetAvailableLocalesRequest;
import generated.zcsclient.account.testGetAvailableLocalesResponse;
import generated.zcsclient.account.testGetAvailableSkinsRequest;
import generated.zcsclient.account.testGetAvailableSkinsResponse;
import generated.zcsclient.account.testGetDistributionListMembersRequest;
import generated.zcsclient.account.testGetDistributionListMembersResponse;
import generated.zcsclient.account.testGetDistributionListRequest;
import generated.zcsclient.account.testGetDistributionListResponse;
import generated.zcsclient.account.testGetIdentitiesRequest;
import generated.zcsclient.account.testGetIdentitiesResponse;
import generated.zcsclient.account.testGetInfoRequest;
import generated.zcsclient.account.testGetInfoResponse;
import generated.zcsclient.account.testGetPrefsRequest;
import generated.zcsclient.account.testGetPrefsResponse;
import generated.zcsclient.account.testGetRightsRequest;
import generated.zcsclient.account.testGetRightsResponse;
import generated.zcsclient.account.testGetSMIMEPublicCertsRequest;
import generated.zcsclient.account.testGetSMIMEPublicCertsResponse;
import generated.zcsclient.account.testGetShareInfoRequest;
import generated.zcsclient.account.testGetShareInfoResponse;
import generated.zcsclient.account.testGetSignaturesRequest;
import generated.zcsclient.account.testGetSignaturesResponse;
import generated.zcsclient.account.testGetVersionInfoRequest;
import generated.zcsclient.account.testGetVersionInfoResponse;
import generated.zcsclient.account.testGetWhiteBlackListRequest;
import generated.zcsclient.account.testGetWhiteBlackListResponse;
import generated.zcsclient.account.testGrantRightsRequest;
import generated.zcsclient.account.testGrantRightsResponse;
import generated.zcsclient.account.testModifyIdentityRequest;
import generated.zcsclient.account.testModifyIdentityResponse;
import generated.zcsclient.account.testModifyPrefsRequest;
import generated.zcsclient.account.testModifyPrefsResponse;
import generated.zcsclient.account.testModifyPropertiesRequest;
import generated.zcsclient.account.testModifyPropertiesResponse;
import generated.zcsclient.account.testModifySignatureRequest;
import generated.zcsclient.account.testModifySignatureResponse;
import generated.zcsclient.account.testModifyWhiteBlackListRequest;
import generated.zcsclient.account.testModifyWhiteBlackListResponse;
import generated.zcsclient.account.testModifyZimletPrefsRequest;
import generated.zcsclient.account.testModifyZimletPrefsResponse;
import generated.zcsclient.account.testRevokeRightsRequest;
import generated.zcsclient.account.testRevokeRightsResponse;
import generated.zcsclient.account.testSearchCalendarResourcesRequest;
import generated.zcsclient.account.testSearchCalendarResourcesResponse;
import generated.zcsclient.account.testSearchGalRequest;
import generated.zcsclient.account.testSearchGalResponse;
import generated.zcsclient.account.testSubscribeDistributionListRequest;
import generated.zcsclient.account.testSubscribeDistributionListResponse;
import generated.zcsclient.account.testSyncGalRequest;
import generated.zcsclient.account.testSyncGalResponse;
import generated.zcsclient.mail.testAddAppointmentInviteRequest;
import generated.zcsclient.mail.testAddAppointmentInviteResponse;
import generated.zcsclient.mail.testAddCommentRequest;
import generated.zcsclient.mail.testAddCommentResponse;
import generated.zcsclient.mail.testAddMsgRequest;
import generated.zcsclient.mail.testAddMsgResponse;
import generated.zcsclient.mail.testAddTaskInviteRequest;
import generated.zcsclient.mail.testAddTaskInviteResponse;
import generated.zcsclient.mail.testAnnounceOrganizerChangeRequest;
import generated.zcsclient.mail.testAnnounceOrganizerChangeResponse;
import generated.zcsclient.mail.testApplyFilterRulesRequest;
import generated.zcsclient.mail.testApplyFilterRulesResponse;
import generated.zcsclient.mail.testApplyOutgoingFilterRulesRequest;
import generated.zcsclient.mail.testApplyOutgoingFilterRulesResponse;
import generated.zcsclient.mail.testAutoCompleteRequest;
import generated.zcsclient.mail.testAutoCompleteResponse;
import generated.zcsclient.mail.testBounceMsgRequest;
import generated.zcsclient.mail.testBounceMsgResponse;
import generated.zcsclient.mail.testBrowseRequest;
import generated.zcsclient.mail.testBrowseResponse;
import generated.zcsclient.mail.testCancelAppointmentRequest;
import generated.zcsclient.mail.testCancelAppointmentResponse;
import generated.zcsclient.mail.testCancelTaskRequest;
import generated.zcsclient.mail.testCancelTaskResponse;
import generated.zcsclient.mail.testCheckDeviceStatusRequest;
import generated.zcsclient.mail.testCheckDeviceStatusResponse;
import generated.zcsclient.mail.testCheckPermissionRequest;
import generated.zcsclient.mail.testCheckPermissionResponse;
import generated.zcsclient.mail.testCheckRecurConflictsRequest;
import generated.zcsclient.mail.testCheckRecurConflictsResponse;
import generated.zcsclient.mail.testCheckSpellingRequest;
import generated.zcsclient.mail.testCheckSpellingResponse;
import generated.zcsclient.mail.testCompleteTaskInstanceRequest;
import generated.zcsclient.mail.testCompleteTaskInstanceResponse;
import generated.zcsclient.mail.testContactActionRequest;
import generated.zcsclient.mail.testContactActionResponse;
import generated.zcsclient.mail.testConvActionRequest;
import generated.zcsclient.mail.testConvActionResponse;
import generated.zcsclient.mail.testCounterAppointmentRequest;
import generated.zcsclient.mail.testCounterAppointmentResponse;
import generated.zcsclient.mail.testCreateAppointmentExceptionRequest;
import generated.zcsclient.mail.testCreateAppointmentExceptionResponse;
import generated.zcsclient.mail.testCreateAppointmentRequest;
import generated.zcsclient.mail.testCreateAppointmentResponse;
import generated.zcsclient.mail.testCreateContactRequest;
import generated.zcsclient.mail.testCreateContactResponse;
import generated.zcsclient.mail.testCreateDataSourceRequest;
import generated.zcsclient.mail.testCreateDataSourceResponse;
import generated.zcsclient.mail.testCreateFolderRequest;
import generated.zcsclient.mail.testCreateFolderResponse;
import generated.zcsclient.mail.testCreateMountpointRequest;
import generated.zcsclient.mail.testCreateMountpointResponse;
import generated.zcsclient.mail.testCreateNoteRequest;
import generated.zcsclient.mail.testCreateNoteResponse;
import generated.zcsclient.mail.testCreateSearchFolderRequest;
import generated.zcsclient.mail.testCreateSearchFolderResponse;
import generated.zcsclient.mail.testCreateTagRequest;
import generated.zcsclient.mail.testCreateTagResponse;
import generated.zcsclient.mail.testCreateTaskExceptionRequest;
import generated.zcsclient.mail.testCreateTaskExceptionResponse;
import generated.zcsclient.mail.testCreateTaskRequest;
import generated.zcsclient.mail.testCreateTaskResponse;
import generated.zcsclient.mail.testCreateWaitSetRequest;
import generated.zcsclient.mail.testCreateWaitSetResponse;
import generated.zcsclient.mail.testDeclineCounterAppointmentRequest;
import generated.zcsclient.mail.testDeclineCounterAppointmentResponse;
import generated.zcsclient.mail.testDeleteDataSourceRequest;
import generated.zcsclient.mail.testDeleteDataSourceResponse;
import generated.zcsclient.mail.testDeleteDeviceRequest;
import generated.zcsclient.mail.testDeleteDeviceResponse;
import generated.zcsclient.mail.testDestroyWaitSetRequest;
import generated.zcsclient.mail.testDestroyWaitSetResponse;
import generated.zcsclient.mail.testDiffDocumentRequest;
import generated.zcsclient.mail.testDiffDocumentResponse;
import generated.zcsclient.mail.testDismissCalendarItemAlarmRequest;
import generated.zcsclient.mail.testDismissCalendarItemAlarmResponse;
import generated.zcsclient.mail.testDocumentActionRequest;
import generated.zcsclient.mail.testDocumentActionResponse;
import generated.zcsclient.mail.testEmptyDumpsterRequest;
import generated.zcsclient.mail.testEmptyDumpsterResponse;
import generated.zcsclient.mail.testEnableSharedReminderRequest;
import generated.zcsclient.mail.testEnableSharedReminderResponse;
import generated.zcsclient.mail.testExpandRecurRequest;
import generated.zcsclient.mail.testExpandRecurResponse;
import generated.zcsclient.mail.testExportContactsRequest;
import generated.zcsclient.mail.testExportContactsResponse;
import generated.zcsclient.mail.testFolderActionRequest;
import generated.zcsclient.mail.testFolderActionResponse;
import generated.zcsclient.mail.testForwardAppointmentInviteRequest;
import generated.zcsclient.mail.testForwardAppointmentInviteResponse;
import generated.zcsclient.mail.testForwardAppointmentRequest;
import generated.zcsclient.mail.testForwardAppointmentResponse;
import generated.zcsclient.mail.testGenerateUUIDRequest;
import generated.zcsclient.mail.testGetActivityStreamRequest;
import generated.zcsclient.mail.testGetActivityStreamResponse;
import generated.zcsclient.mail.testGetAllDevicesRequest;
import generated.zcsclient.mail.testGetAllDevicesResponse;
import generated.zcsclient.mail.testGetAppointmentRequest;
import generated.zcsclient.mail.testGetAppointmentResponse;
import generated.zcsclient.mail.testGetApptSummariesRequest;
import generated.zcsclient.mail.testGetApptSummariesResponse;
import generated.zcsclient.mail.testGetCalendarItemSummariesRequest;
import generated.zcsclient.mail.testGetCalendarItemSummariesResponse;
import generated.zcsclient.mail.testGetCommentsRequest;
import generated.zcsclient.mail.testGetCommentsResponse;
import generated.zcsclient.mail.testGetContactsRequest;
import generated.zcsclient.mail.testGetContactsResponse;
import generated.zcsclient.mail.testGetConvRequest;
import generated.zcsclient.mail.testGetConvResponse;
import generated.zcsclient.mail.testGetCustomMetadataRequest;
import generated.zcsclient.mail.testGetCustomMetadataResponse;
import generated.zcsclient.mail.testGetDataSourcesRequest;
import generated.zcsclient.mail.testGetDataSourcesResponse;
import generated.zcsclient.mail.testGetDocumentShareURLRequest;
import generated.zcsclient.mail.testGetEffectiveFolderPermsRequest;
import generated.zcsclient.mail.testGetEffectiveFolderPermsResponse;
import generated.zcsclient.mail.testGetFilterRulesRequest;
import generated.zcsclient.mail.testGetFilterRulesResponse;
import generated.zcsclient.mail.testGetFolderRequest;
import generated.zcsclient.mail.testGetFolderResponse;
import generated.zcsclient.mail.testGetFreeBusyRequest;
import generated.zcsclient.mail.testGetFreeBusyResponse;
import generated.zcsclient.mail.testGetICalRequest;
import generated.zcsclient.mail.testGetICalResponse;
import generated.zcsclient.mail.testGetImportStatusRequest;
import generated.zcsclient.mail.testGetImportStatusResponse;
import generated.zcsclient.mail.testGetItemRequest;
import generated.zcsclient.mail.testGetItemResponse;
import generated.zcsclient.mail.testGetMailboxMetadataRequest;
import generated.zcsclient.mail.testGetMailboxMetadataResponse;
import generated.zcsclient.mail.testGetMiniCalRequest;
import generated.zcsclient.mail.testGetMiniCalResponse;
import generated.zcsclient.mail.testGetMsgMetadataRequest;
import generated.zcsclient.mail.testGetMsgMetadataResponse;
import generated.zcsclient.mail.testGetMsgRequest;
import generated.zcsclient.mail.testGetMsgResponse;
import generated.zcsclient.mail.testGetNoteRequest;
import generated.zcsclient.mail.testGetNoteResponse;
import generated.zcsclient.mail.testGetNotificationsRequest;
import generated.zcsclient.mail.testGetNotificationsResponse;
import generated.zcsclient.mail.testGetOutgoingFilterRulesRequest;
import generated.zcsclient.mail.testGetOutgoingFilterRulesResponse;
import generated.zcsclient.mail.testGetPermissionRequest;
import generated.zcsclient.mail.testGetPermissionResponse;
import generated.zcsclient.mail.testGetRecurRequest;
import generated.zcsclient.mail.testGetRecurResponse;
import generated.zcsclient.mail.testGetSearchFolderRequest;
import generated.zcsclient.mail.testGetSearchFolderResponse;
import generated.zcsclient.mail.testGetShareDetailsRequest;
import generated.zcsclient.mail.testGetShareDetailsResponse;
import generated.zcsclient.mail.testGetShareNotificationsRequest;
import generated.zcsclient.mail.testGetShareNotificationsResponse;
import generated.zcsclient.mail.testGetSpellDictionariesRequest;
import generated.zcsclient.mail.testGetSpellDictionariesResponse;
import generated.zcsclient.mail.testGetSystemRetentionPolicyRequest;
import generated.zcsclient.mail.testGetSystemRetentionPolicyResponse;
import generated.zcsclient.mail.testGetTagRequest;
import generated.zcsclient.mail.testGetTagResponse;
import generated.zcsclient.mail.testGetTaskRequest;
import generated.zcsclient.mail.testGetTaskResponse;
import generated.zcsclient.mail.testGetTaskSummariesRequest;
import generated.zcsclient.mail.testGetTaskSummariesResponse;
import generated.zcsclient.mail.testGetWatchersRequest;
import generated.zcsclient.mail.testGetWatchersResponse;
import generated.zcsclient.mail.testGetWatchingItemsRequest;
import generated.zcsclient.mail.testGetWatchingItemsResponse;
import generated.zcsclient.mail.testGetWorkingHoursRequest;
import generated.zcsclient.mail.testGetWorkingHoursResponse;
import generated.zcsclient.mail.testGetYahooAuthTokenRequest;
import generated.zcsclient.mail.testGetYahooAuthTokenResponse;
import generated.zcsclient.mail.testGetYahooCookieRequest;
import generated.zcsclient.mail.testGetYahooCookieResponse;
import generated.zcsclient.mail.testGrantPermissionRequest;
import generated.zcsclient.mail.testGrantPermissionResponse;
import generated.zcsclient.mail.testICalReplyRequest;
import generated.zcsclient.mail.testICalReplyResponse;
import generated.zcsclient.mail.testImportAppointmentsRequest;
import generated.zcsclient.mail.testImportAppointmentsResponse;
import generated.zcsclient.mail.testImportContactsRequest;
import generated.zcsclient.mail.testImportContactsResponse;
import generated.zcsclient.mail.testImportDataRequest;
import generated.zcsclient.mail.testImportDataResponse;
import generated.zcsclient.mail.testInvalidateReminderDeviceRequest;
import generated.zcsclient.mail.testInvalidateReminderDeviceResponse;
import generated.zcsclient.mail.testItemActionRequest;
import generated.zcsclient.mail.testItemActionResponse;
import generated.zcsclient.mail.testListDocumentRevisionsRequest;
import generated.zcsclient.mail.testListDocumentRevisionsResponse;
import generated.zcsclient.mail.testModifyAppointmentRequest;
import generated.zcsclient.mail.testModifyAppointmentResponse;
import generated.zcsclient.mail.testModifyContactRequest;
import generated.zcsclient.mail.testModifyContactResponse;
import generated.zcsclient.mail.testModifyDataSourceRequest;
import generated.zcsclient.mail.testModifyDataSourceResponse;
import generated.zcsclient.mail.testModifyFilterRulesRequest;
import generated.zcsclient.mail.testModifyFilterRulesResponse;
import generated.zcsclient.mail.testModifyMailboxMetadataRequest;
import generated.zcsclient.mail.testModifyMailboxMetadataResponse;
import generated.zcsclient.mail.testModifyOutgoingFilterRulesRequest;
import generated.zcsclient.mail.testModifyOutgoingFilterRulesResponse;
import generated.zcsclient.mail.testModifySearchFolderRequest;
import generated.zcsclient.mail.testModifySearchFolderResponse;
import generated.zcsclient.mail.testModifyTaskRequest;
import generated.zcsclient.mail.testModifyTaskResponse;
import generated.zcsclient.mail.testMsgActionRequest;
import generated.zcsclient.mail.testMsgActionResponse;
import generated.zcsclient.mail.testNoOpRequest;
import generated.zcsclient.mail.testNoOpResponse;
import generated.zcsclient.mail.testNoteActionRequest;
import generated.zcsclient.mail.testNoteActionResponse;
import generated.zcsclient.mail.testPurgeRevisionRequest;
import generated.zcsclient.mail.testPurgeRevisionResponse;
import generated.zcsclient.mail.testRankingActionRequest;
import generated.zcsclient.mail.testRankingActionResponse;
import generated.zcsclient.mail.testRegisterDeviceRequest;
import generated.zcsclient.mail.testRegisterDeviceResponse;
import generated.zcsclient.mail.testRemoveAttachmentsRequest;
import generated.zcsclient.mail.testRemoveAttachmentsResponse;
import generated.zcsclient.mail.testRevokePermissionRequest;
import generated.zcsclient.mail.testRevokePermissionResponse;
import generated.zcsclient.mail.testSaveDocumentRequest;
import generated.zcsclient.mail.testSaveDocumentResponse;
import generated.zcsclient.mail.testSaveDraftRequest;
import generated.zcsclient.mail.testSaveDraftResponse;
import generated.zcsclient.mail.testSearchConvRequest;
import generated.zcsclient.mail.testSearchConvResponse;
import generated.zcsclient.mail.testSearchRequest;
import generated.zcsclient.mail.testSearchResponse;
import generated.zcsclient.mail.testSendDeliveryReportRequest;
import generated.zcsclient.mail.testSendDeliveryReportResponse;
import generated.zcsclient.mail.testSendInviteReplyRequest;
import generated.zcsclient.mail.testSendInviteReplyResponse;
import generated.zcsclient.mail.testSendMsgRequest;
import generated.zcsclient.mail.testSendMsgResponse;
import generated.zcsclient.mail.testSendShareNotificationRequest;
import generated.zcsclient.mail.testSendShareNotificationResponse;
import generated.zcsclient.mail.testSendVerificationCodeRequest;
import generated.zcsclient.mail.testSendVerificationCodeResponse;
import generated.zcsclient.mail.testSetAppointmentRequest;
import generated.zcsclient.mail.testSetAppointmentResponse;
import generated.zcsclient.mail.testSetCustomMetadataRequest;
import generated.zcsclient.mail.testSetCustomMetadataResponse;
import generated.zcsclient.mail.testSetMailboxMetadataRequest;
import generated.zcsclient.mail.testSetMailboxMetadataResponse;
import generated.zcsclient.mail.testSetTaskRequest;
import generated.zcsclient.mail.testSetTaskResponse;
import generated.zcsclient.mail.testSnoozeCalendarItemAlarmRequest;
import generated.zcsclient.mail.testSnoozeCalendarItemAlarmResponse;
import generated.zcsclient.mail.testSyncRequest;
import generated.zcsclient.mail.testSyncResponse;
import generated.zcsclient.mail.testTagActionRequest;
import generated.zcsclient.mail.testTagActionResponse;
import generated.zcsclient.mail.testTestDataSourceRequest;
import generated.zcsclient.mail.testTestDataSourceResponse;
import generated.zcsclient.mail.testUpdateDeviceStatusRequest;
import generated.zcsclient.mail.testUpdateDeviceStatusResponse;
import generated.zcsclient.mail.testVerifyCodeRequest;
import generated.zcsclient.mail.testVerifyCodeResponse;
import generated.zcsclient.mail.testWaitSetRequest;
import generated.zcsclient.mail.testWaitSetResponse;
import generated.zcsclient.replication.testBecomeMasterRequest;
import generated.zcsclient.replication.testBecomeMasterResponse;
import generated.zcsclient.replication.testBringDownServiceIPRequest;
import generated.zcsclient.replication.testBringDownServiceIPResponse;
import generated.zcsclient.replication.testBringUpServiceIPRequest;
import generated.zcsclient.replication.testBringUpServiceIPResponse;
import generated.zcsclient.replication.testReplicationStatusRequest;
import generated.zcsclient.replication.testReplicationStatusResponse;
import generated.zcsclient.replication.testStartCatchupRequest;
import generated.zcsclient.replication.testStartCatchupResponse;
import generated.zcsclient.replication.testStartFailoverClientRequest;
import generated.zcsclient.replication.testStartFailoverClientResponse;
import generated.zcsclient.replication.testStartFailoverDaemonRequest;
import generated.zcsclient.replication.testStartFailoverDaemonResponse;
import generated.zcsclient.replication.testStopFailoverClientRequest;
import generated.zcsclient.replication.testStopFailoverClientResponse;
import generated.zcsclient.replication.testStopFailoverDaemonRequest;
import generated.zcsclient.replication.testStopFailoverDaemonResponse;
import generated.zcsclient.sync.testCancelPendingRemoteWipeRequest;
import generated.zcsclient.sync.testCancelPendingRemoteWipeResponse;
import generated.zcsclient.sync.testGetDeviceStatusRequest;
import generated.zcsclient.sync.testGetDeviceStatusResponse;
import generated.zcsclient.sync.testRemoteWipeRequest;
import generated.zcsclient.sync.testRemoteWipeResponse;
import generated.zcsclient.sync.testRemoveDeviceRequest;
import generated.zcsclient.sync.testRemoveDeviceResponse;
import generated.zcsclient.sync.testResumeDeviceRequest;
import generated.zcsclient.sync.testResumeDeviceResponse;
import generated.zcsclient.sync.testSuspendDeviceRequest;
import generated.zcsclient.sync.testSuspendDeviceResponse;
import generated.zcsclient.voice.testChangeUCPasswordRequest;
import generated.zcsclient.voice.testChangeUCPasswordResponse;
import generated.zcsclient.voice.testGetUCInfoRequest;
import generated.zcsclient.voice.testGetUCInfoResponse;
import generated.zcsclient.voice.testGetVoiceFeaturesRequest;
import generated.zcsclient.voice.testGetVoiceFeaturesResponse;
import generated.zcsclient.voice.testGetVoiceFolderRequest;
import generated.zcsclient.voice.testGetVoiceFolderResponse;
import generated.zcsclient.voice.testGetVoiceInfoRequest;
import generated.zcsclient.voice.testGetVoiceInfoResponse;
import generated.zcsclient.voice.testGetVoiceMailPrefsRequest;
import generated.zcsclient.voice.testGetVoiceMailPrefsResponse;
import generated.zcsclient.voice.testModifyFromNumRequest;
import generated.zcsclient.voice.testModifyFromNumResponse;
import generated.zcsclient.voice.testModifyVoiceFeaturesRequest;
import generated.zcsclient.voice.testModifyVoiceFeaturesResponse;
import generated.zcsclient.voice.testModifyVoiceMailPinRequest;
import generated.zcsclient.voice.testModifyVoiceMailPinResponse;
import generated.zcsclient.voice.testModifyVoiceMailPrefsRequest;
import generated.zcsclient.voice.testModifyVoiceMailPrefsResponse;
import generated.zcsclient.voice.testResetVoiceFeaturesRequest;
import generated.zcsclient.voice.testResetVoiceFeaturesResponse;
import generated.zcsclient.voice.testSearchVoiceRequest;
import generated.zcsclient.voice.testSearchVoiceResponse;
import generated.zcsclient.voice.testUploadVoiceMailRequest;
import generated.zcsclient.voice.testUploadVoiceMailResponse;
import generated.zcsclient.voice.testVoiceMsgActionRequest;
import generated.zcsclient.voice.testVoiceMsgActionResponse;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-hudson-48-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "zcsPortType", targetNamespace = "http://www.zmail.com/wsdl/ZmailService.wsdl")
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
public interface ZcsPortType {


    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testAuthResponse
     */
    @WebMethod(action = "urn:zmailAccount/Auth")
    @WebResult(name = "AuthResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testAuthResponse authRequest(
        @WebParam(name = "AuthRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testAuthRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testAutoCompleteGalResponse
     */
    @WebMethod(action = "urn:zmailAccount/AutoCompleteGal")
    @WebResult(name = "AutoCompleteGalResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testAutoCompleteGalResponse autoCompleteGalRequest(
        @WebParam(name = "AutoCompleteGalRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testAutoCompleteGalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testChangePasswordResponse
     */
    @WebMethod(action = "urn:zmailAccount/ChangePassword")
    @WebResult(name = "ChangePasswordResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testChangePasswordResponse changePasswordRequest(
        @WebParam(name = "ChangePasswordRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testChangePasswordRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testCheckLicenseResponse
     */
    @WebMethod(action = "urn:zmailAccount/CheckLicense")
    @WebResult(name = "CheckLicenseResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testCheckLicenseResponse checkLicenseRequest(
        @WebParam(name = "CheckLicenseRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testCheckLicenseRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testCheckRightsResponse
     */
    @WebMethod(action = "urn:zmailAccount/CheckRights")
    @WebResult(name = "CheckRightsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testCheckRightsResponse checkRightsRequest(
        @WebParam(name = "CheckRightsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testCheckRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testCreateDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAccount/CreateDistributionList")
    @WebResult(name = "CreateDistributionListResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testCreateDistributionListResponse createDistributionListRequest(
        @WebParam(name = "CreateDistributionListRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testCreateDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testCreateIdentityResponse
     */
    @WebMethod(action = "urn:zmailAccount/CreateIdentity")
    @WebResult(name = "CreateIdentityResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testCreateIdentityResponse createIdentityRequest(
        @WebParam(name = "CreateIdentityRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testCreateIdentityRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testCreateSignatureResponse
     */
    @WebMethod(action = "urn:zmailAccount/CreateSignature")
    @WebResult(name = "CreateSignatureResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testCreateSignatureResponse createSignatureRequest(
        @WebParam(name = "CreateSignatureRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testCreateSignatureRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testDeleteIdentityResponse
     */
    @WebMethod(action = "urn:zmailAccount/DeleteIdentity")
    @WebResult(name = "DeleteIdentityResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testDeleteIdentityResponse deleteIdentityRequest(
        @WebParam(name = "DeleteIdentityRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testDeleteIdentityRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testDeleteSignatureResponse
     */
    @WebMethod(action = "urn:zmailAccount/DeleteSignature")
    @WebResult(name = "DeleteSignatureResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testDeleteSignatureResponse deleteSignatureRequest(
        @WebParam(name = "DeleteSignatureRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testDeleteSignatureRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testDiscoverRightsResponse
     */
    @WebMethod(action = "urn:zmailAccount/DiscoverRights")
    @WebResult(name = "DiscoverRightsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testDiscoverRightsResponse discoverRightsRequest(
        @WebParam(name = "DiscoverRightsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testDiscoverRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testDistributionListActionResponse
     */
    @WebMethod(action = "urn:zmailAccount/DistributionListAction")
    @WebResult(name = "DistributionListActionResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testDistributionListActionResponse distributionListActionRequest(
        @WebParam(name = "DistributionListActionRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testDistributionListActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testEndSessionResponse
     */
    @WebMethod(action = "urn:zmailAccount/EndSession")
    @WebResult(name = "EndSessionResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testEndSessionResponse endSessionRequest(
        @WebParam(name = "EndSessionRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testEndSessionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetAccountDistributionListsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetAccountDistributionLists")
    @WebResult(name = "GetAccountDistributionListsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetAccountDistributionListsResponse getAccountDistributionListsRequest(
        @WebParam(name = "GetAccountDistributionListsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetAccountDistributionListsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetAccountInfoResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetAccountInfo")
    @WebResult(name = "GetAccountInfoResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetAccountInfoResponse getAccountInfoRequest(
        @WebParam(name = "GetAccountInfoRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetAccountInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetAllLocalesResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetAllLocales")
    @WebResult(name = "GetAllLocalesResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetAllLocalesResponse getAllLocalesRequest(
        @WebParam(name = "GetAllLocalesRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetAllLocalesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetAvailableCsvFormatsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetAvailableCsvFormats")
    @WebResult(name = "GetAvailableCsvFormatsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetAvailableCsvFormatsResponse getAvailableCsvFormatsRequest(
        @WebParam(name = "GetAvailableCsvFormatsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetAvailableCsvFormatsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetAvailableLocalesResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetAvailableLocales")
    @WebResult(name = "GetAvailableLocalesResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetAvailableLocalesResponse getAvailableLocalesRequest(
        @WebParam(name = "GetAvailableLocalesRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetAvailableLocalesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetAvailableSkinsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetAvailableSkins")
    @WebResult(name = "GetAvailableSkinsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetAvailableSkinsResponse getAvailableSkinsRequest(
        @WebParam(name = "GetAvailableSkinsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetAvailableSkinsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetDistributionListMembersResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetDistributionListMembers")
    @WebResult(name = "GetDistributionListMembersResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetDistributionListMembersResponse getDistributionListMembersRequest(
        @WebParam(name = "GetDistributionListMembersRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetDistributionListMembersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetDistributionList")
    @WebResult(name = "GetDistributionListResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetDistributionListResponse getDistributionListRequest(
        @WebParam(name = "GetDistributionListRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetIdentitiesResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetIdentities")
    @WebResult(name = "GetIdentitiesResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetIdentitiesResponse getIdentitiesRequest(
        @WebParam(name = "GetIdentitiesRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetIdentitiesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetInfoResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetInfo")
    @WebResult(name = "GetInfoResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetInfoResponse getInfoRequest(
        @WebParam(name = "GetInfoRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetPrefsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetPrefs")
    @WebResult(name = "GetPrefsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetPrefsResponse getPrefsRequest(
        @WebParam(name = "GetPrefsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetPrefsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetRightsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetRights")
    @WebResult(name = "GetRightsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetRightsResponse getRightsRequest(
        @WebParam(name = "GetRightsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetSMIMEPublicCertsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetSMIMEPublicCerts")
    @WebResult(name = "GetSMIMEPublicCertsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetSMIMEPublicCertsResponse getSMIMEPublicCertsRequest(
        @WebParam(name = "GetSMIMEPublicCertsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetSMIMEPublicCertsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetShareInfoResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetShareInfo")
    @WebResult(name = "GetShareInfoResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetShareInfoResponse getShareInfoRequest(
        @WebParam(name = "GetShareInfoRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetShareInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetSignaturesResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetSignatures")
    @WebResult(name = "GetSignaturesResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetSignaturesResponse getSignaturesRequest(
        @WebParam(name = "GetSignaturesRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetSignaturesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetVersionInfoResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetVersionInfo")
    @WebResult(name = "GetVersionInfoResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetVersionInfoResponse getVersionInfoRequest(
        @WebParam(name = "GetVersionInfoRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetVersionInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGetWhiteBlackListResponse
     */
    @WebMethod(action = "urn:zmailAccount/GetWhiteBlackList")
    @WebResult(name = "GetWhiteBlackListResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGetWhiteBlackListResponse getWhiteBlackListRequest(
        @WebParam(name = "GetWhiteBlackListRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGetWhiteBlackListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testGrantRightsResponse
     */
    @WebMethod(action = "urn:zmailAccount/GrantRights")
    @WebResult(name = "GrantRightsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testGrantRightsResponse grantRightsRequest(
        @WebParam(name = "GrantRightsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testGrantRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testModifyIdentityResponse
     */
    @WebMethod(action = "urn:zmailAccount/ModifyIdentity")
    @WebResult(name = "ModifyIdentityResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testModifyIdentityResponse modifyIdentityRequest(
        @WebParam(name = "ModifyIdentityRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testModifyIdentityRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testModifyPrefsResponse
     */
    @WebMethod(action = "urn:zmailAccount/ModifyPrefs")
    @WebResult(name = "ModifyPrefsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testModifyPrefsResponse modifyPrefsRequest(
        @WebParam(name = "ModifyPrefsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testModifyPrefsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testModifyPropertiesResponse
     */
    @WebMethod(action = "urn:zmailAccount/ModifyProperties")
    @WebResult(name = "ModifyPropertiesResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testModifyPropertiesResponse modifyPropertiesRequest(
        @WebParam(name = "ModifyPropertiesRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testModifyPropertiesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testModifySignatureResponse
     */
    @WebMethod(action = "urn:zmailAccount/ModifySignature")
    @WebResult(name = "ModifySignatureResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testModifySignatureResponse modifySignatureRequest(
        @WebParam(name = "ModifySignatureRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testModifySignatureRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testModifyWhiteBlackListResponse
     */
    @WebMethod(action = "urn:zmailAccount/ModifyWhiteBlackList")
    @WebResult(name = "ModifyWhiteBlackListResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testModifyWhiteBlackListResponse modifyWhiteBlackListRequest(
        @WebParam(name = "ModifyWhiteBlackListRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testModifyWhiteBlackListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testModifyZimletPrefsResponse
     */
    @WebMethod(action = "urn:zmailAccount/ModifyZimletPrefs")
    @WebResult(name = "ModifyZimletPrefsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testModifyZimletPrefsResponse modifyZimletPrefsRequest(
        @WebParam(name = "ModifyZimletPrefsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testModifyZimletPrefsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testRevokeRightsResponse
     */
    @WebMethod(action = "urn:zmailAccount/RevokeRights")
    @WebResult(name = "RevokeRightsResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testRevokeRightsResponse revokeRightsRequest(
        @WebParam(name = "RevokeRightsRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testRevokeRightsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testSearchCalendarResourcesResponse
     */
    @WebMethod(action = "urn:zmailAccount/SearchCalendarResources")
    @WebResult(name = "SearchCalendarResourcesResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testSearchCalendarResourcesResponse searchCalendarResourcesRequest(
        @WebParam(name = "SearchCalendarResourcesRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testSearchCalendarResourcesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testSearchGalResponse
     */
    @WebMethod(action = "urn:zmailAccount/SearchGal")
    @WebResult(name = "SearchGalResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testSearchGalResponse searchGalRequest(
        @WebParam(name = "SearchGalRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testSearchGalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testSubscribeDistributionListResponse
     */
    @WebMethod(action = "urn:zmailAccount/SubscribeDistributionList")
    @WebResult(name = "SubscribeDistributionListResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testSubscribeDistributionListResponse subscribeDistributionListRequest(
        @WebParam(name = "SubscribeDistributionListRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testSubscribeDistributionListRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.account.testSyncGalResponse
     */
    @WebMethod(action = "urn:zmailAccount/SyncGal")
    @WebResult(name = "SyncGalResponse", targetNamespace = "urn:zmailAccount", partName = "parameters")
    public testSyncGalResponse syncGalRequest(
        @WebParam(name = "SyncGalRequest", targetNamespace = "urn:zmailAccount", partName = "parameters")
        testSyncGalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testAddAppointmentInviteResponse
     */
    @WebMethod(action = "urn:zmailMail/AddAppointmentInvite")
    @WebResult(name = "AddAppointmentInviteResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testAddAppointmentInviteResponse addAppointmentInviteRequest(
        @WebParam(name = "AddAppointmentInviteRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testAddAppointmentInviteRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testAddCommentResponse
     */
    @WebMethod(action = "urn:zmailMail/AddComment")
    @WebResult(name = "AddCommentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testAddCommentResponse addCommentRequest(
        @WebParam(name = "AddCommentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testAddCommentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testAddMsgResponse
     */
    @WebMethod(action = "urn:zmailMail/AddMsg")
    @WebResult(name = "AddMsgResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testAddMsgResponse addMsgRequest(
        @WebParam(name = "AddMsgRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testAddMsgRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testAddTaskInviteResponse
     */
    @WebMethod(action = "urn:zmailMail/AddTaskInvite")
    @WebResult(name = "AddTaskInviteResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testAddTaskInviteResponse addTaskInviteRequest(
        @WebParam(name = "AddTaskInviteRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testAddTaskInviteRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testAnnounceOrganizerChangeResponse
     */
    @WebMethod(action = "urn:zmailMail/AnnounceOrganizerChange")
    @WebResult(name = "AnnounceOrganizerChangeResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testAnnounceOrganizerChangeResponse announceOrganizerChangeRequest(
        @WebParam(name = "AnnounceOrganizerChangeRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testAnnounceOrganizerChangeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testApplyFilterRulesResponse
     */
    @WebMethod(action = "urn:zmailMail/ApplyFilterRules")
    @WebResult(name = "ApplyFilterRulesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testApplyFilterRulesResponse applyFilterRulesRequest(
        @WebParam(name = "ApplyFilterRulesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testApplyFilterRulesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testApplyOutgoingFilterRulesResponse
     */
    @WebMethod(action = "urn:zmailMail/ApplyOutgoingFilterRules")
    @WebResult(name = "ApplyOutgoingFilterRulesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testApplyOutgoingFilterRulesResponse applyOutgoingFilterRulesRequest(
        @WebParam(name = "ApplyOutgoingFilterRulesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testApplyOutgoingFilterRulesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testAutoCompleteResponse
     */
    @WebMethod(action = "urn:zmailMail/AutoComplete")
    @WebResult(name = "AutoCompleteResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testAutoCompleteResponse autoCompleteRequest(
        @WebParam(name = "AutoCompleteRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testAutoCompleteRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testBounceMsgResponse
     */
    @WebMethod(action = "urn:zmailMail/BounceMsg")
    @WebResult(name = "BounceMsgResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testBounceMsgResponse bounceMsgRequest(
        @WebParam(name = "BounceMsgRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testBounceMsgRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testBrowseResponse
     */
    @WebMethod(action = "urn:zmailMail/Browse")
    @WebResult(name = "BrowseResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testBrowseResponse browseRequest(
        @WebParam(name = "BrowseRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testBrowseRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCancelAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/CancelAppointment")
    @WebResult(name = "CancelAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCancelAppointmentResponse cancelAppointmentRequest(
        @WebParam(name = "CancelAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCancelAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCancelTaskResponse
     */
    @WebMethod(action = "urn:zmailMail/CancelTask")
    @WebResult(name = "CancelTaskResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCancelTaskResponse cancelTaskRequest(
        @WebParam(name = "CancelTaskRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCancelTaskRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCheckDeviceStatusResponse
     */
    @WebMethod(action = "urn:zmailMail/CheckDeviceStatus")
    @WebResult(name = "CheckDeviceStatusResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCheckDeviceStatusResponse checkDeviceStatusRequest(
        @WebParam(name = "CheckDeviceStatusRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCheckDeviceStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCheckPermissionResponse
     */
    @WebMethod(action = "urn:zmailMail/CheckPermission")
    @WebResult(name = "CheckPermissionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCheckPermissionResponse checkPermissionRequest(
        @WebParam(name = "CheckPermissionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCheckPermissionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCheckRecurConflictsResponse
     */
    @WebMethod(action = "urn:zmailMail/CheckRecurConflicts")
    @WebResult(name = "CheckRecurConflictsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCheckRecurConflictsResponse checkRecurConflictsRequest(
        @WebParam(name = "CheckRecurConflictsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCheckRecurConflictsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCheckSpellingResponse
     */
    @WebMethod(action = "urn:zmailMail/CheckSpelling")
    @WebResult(name = "CheckSpellingResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCheckSpellingResponse checkSpellingRequest(
        @WebParam(name = "CheckSpellingRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCheckSpellingRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCompleteTaskInstanceResponse
     */
    @WebMethod(action = "urn:zmailMail/CompleteTaskInstance")
    @WebResult(name = "CompleteTaskInstanceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCompleteTaskInstanceResponse completeTaskInstanceRequest(
        @WebParam(name = "CompleteTaskInstanceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCompleteTaskInstanceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testContactActionResponse
     */
    @WebMethod(action = "urn:zmailMail/ContactAction")
    @WebResult(name = "ContactActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testContactActionResponse contactActionRequest(
        @WebParam(name = "ContactActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testContactActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testConvActionResponse
     */
    @WebMethod(action = "urn:zmailMail/ConvAction")
    @WebResult(name = "ConvActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testConvActionResponse convActionRequest(
        @WebParam(name = "ConvActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testConvActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCounterAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/CounterAppointment")
    @WebResult(name = "CounterAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCounterAppointmentResponse counterAppointmentRequest(
        @WebParam(name = "CounterAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCounterAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateAppointmentExceptionResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateAppointmentException")
    @WebResult(name = "CreateAppointmentExceptionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateAppointmentExceptionResponse createAppointmentExceptionRequest(
        @WebParam(name = "CreateAppointmentExceptionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateAppointmentExceptionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateAppointment")
    @WebResult(name = "CreateAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateAppointmentResponse createAppointmentRequest(
        @WebParam(name = "CreateAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateContactResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateContact")
    @WebResult(name = "CreateContactResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateContactResponse createContactRequest(
        @WebParam(name = "CreateContactRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateContactRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateDataSourceResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateDataSource")
    @WebResult(name = "CreateDataSourceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateDataSourceResponse createDataSourceRequest(
        @WebParam(name = "CreateDataSourceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateFolderResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateFolder")
    @WebResult(name = "CreateFolderResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateFolderResponse createFolderRequest(
        @WebParam(name = "CreateFolderRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateFolderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateMountpointResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateMountpoint")
    @WebResult(name = "CreateMountpointResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateMountpointResponse createMountpointRequest(
        @WebParam(name = "CreateMountpointRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateMountpointRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateNoteResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateNote")
    @WebResult(name = "CreateNoteResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateNoteResponse createNoteRequest(
        @WebParam(name = "CreateNoteRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateNoteRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateSearchFolderResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateSearchFolder")
    @WebResult(name = "CreateSearchFolderResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateSearchFolderResponse createSearchFolderRequest(
        @WebParam(name = "CreateSearchFolderRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateSearchFolderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateTagResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateTag")
    @WebResult(name = "CreateTagResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateTagResponse createTagRequest(
        @WebParam(name = "CreateTagRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateTagRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateTaskExceptionResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateTaskException")
    @WebResult(name = "CreateTaskExceptionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateTaskExceptionResponse createTaskExceptionRequest(
        @WebParam(name = "CreateTaskExceptionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateTaskExceptionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateTaskResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateTask")
    @WebResult(name = "CreateTaskResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateTaskResponse createTaskRequest(
        @WebParam(name = "CreateTaskRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateTaskRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testCreateWaitSetResponse
     */
    @WebMethod(action = "urn:zmailMail/CreateWaitSet")
    @WebResult(name = "CreateWaitSetResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testCreateWaitSetResponse createWaitSetRequest(
        @WebParam(name = "CreateWaitSetRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testCreateWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDeclineCounterAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/DeclineCounterAppointment")
    @WebResult(name = "DeclineCounterAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDeclineCounterAppointmentResponse declineCounterAppointmentRequest(
        @WebParam(name = "DeclineCounterAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDeclineCounterAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDeleteDataSourceResponse
     */
    @WebMethod(action = "urn:zmailMail/DeleteDataSource")
    @WebResult(name = "DeleteDataSourceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDeleteDataSourceResponse deleteDataSourceRequest(
        @WebParam(name = "DeleteDataSourceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDeleteDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDeleteDeviceResponse
     */
    @WebMethod(action = "urn:zmailMail/DeleteDevice")
    @WebResult(name = "DeleteDeviceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDeleteDeviceResponse deleteDeviceRequest(
        @WebParam(name = "DeleteDeviceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDeleteDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDestroyWaitSetResponse
     */
    @WebMethod(action = "urn:zmailMail/DestroyWaitSet")
    @WebResult(name = "DestroyWaitSetResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDestroyWaitSetResponse destroyWaitSetRequest(
        @WebParam(name = "DestroyWaitSetRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDestroyWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDiffDocumentResponse
     */
    @WebMethod(action = "urn:zmailMail/DiffDocument")
    @WebResult(name = "DiffDocumentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDiffDocumentResponse diffDocumentRequest(
        @WebParam(name = "DiffDocumentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDiffDocumentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDismissCalendarItemAlarmResponse
     */
    @WebMethod(action = "urn:zmailMail/DismissCalendarItemAlarm")
    @WebResult(name = "DismissCalendarItemAlarmResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDismissCalendarItemAlarmResponse dismissCalendarItemAlarmRequest(
        @WebParam(name = "DismissCalendarItemAlarmRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDismissCalendarItemAlarmRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testDocumentActionResponse
     */
    @WebMethod(action = "urn:zmailMail/DocumentAction")
    @WebResult(name = "DocumentActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testDocumentActionResponse documentActionRequest(
        @WebParam(name = "DocumentActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testDocumentActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testEmptyDumpsterResponse
     */
    @WebMethod(action = "urn:zmailMail/EmptyDumpster")
    @WebResult(name = "EmptyDumpsterResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testEmptyDumpsterResponse emptyDumpsterRequest(
        @WebParam(name = "EmptyDumpsterRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testEmptyDumpsterRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testEnableSharedReminderResponse
     */
    @WebMethod(action = "urn:zmailMail/EnableSharedReminder")
    @WebResult(name = "EnableSharedReminderResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testEnableSharedReminderResponse enableSharedReminderRequest(
        @WebParam(name = "EnableSharedReminderRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testEnableSharedReminderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testExpandRecurResponse
     */
    @WebMethod(action = "urn:zmailMail/ExpandRecur")
    @WebResult(name = "ExpandRecurResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testExpandRecurResponse expandRecurRequest(
        @WebParam(name = "ExpandRecurRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testExpandRecurRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testExportContactsResponse
     */
    @WebMethod(action = "urn:zmailMail/ExportContacts")
    @WebResult(name = "ExportContactsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testExportContactsResponse exportContactsRequest(
        @WebParam(name = "ExportContactsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testExportContactsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testFolderActionResponse
     */
    @WebMethod(action = "urn:zmailMail/FolderAction")
    @WebResult(name = "FolderActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testFolderActionResponse folderActionRequest(
        @WebParam(name = "FolderActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testFolderActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testForwardAppointmentInviteResponse
     */
    @WebMethod(action = "urn:zmailMail/ForwardAppointmentInvite")
    @WebResult(name = "ForwardAppointmentInviteResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testForwardAppointmentInviteResponse forwardAppointmentInviteRequest(
        @WebParam(name = "ForwardAppointmentInviteRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testForwardAppointmentInviteRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testForwardAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/ForwardAppointment")
    @WebResult(name = "ForwardAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testForwardAppointmentResponse forwardAppointmentRequest(
        @WebParam(name = "ForwardAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testForwardAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "urn:zmailMail/GenerateUUID")
    @WebResult(name = "GenerateUUIDResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public String generateUUIDRequest(
        @WebParam(name = "GenerateUUIDRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGenerateUUIDRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetActivityStreamResponse
     */
    @WebMethod(action = "urn:zmailMail/GetActivityStream")
    @WebResult(name = "GetActivityStreamResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetActivityStreamResponse getActivityStreamRequest(
        @WebParam(name = "GetActivityStreamRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetActivityStreamRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetAllDevicesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetAllDevices")
    @WebResult(name = "GetAllDevicesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetAllDevicesResponse getAllDevicesRequest(
        @WebParam(name = "GetAllDevicesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetAllDevicesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/GetAppointment")
    @WebResult(name = "GetAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetAppointmentResponse getAppointmentRequest(
        @WebParam(name = "GetAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetApptSummariesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetApptSummaries")
    @WebResult(name = "GetApptSummariesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetApptSummariesResponse getApptSummariesRequest(
        @WebParam(name = "GetApptSummariesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetApptSummariesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetCalendarItemSummariesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetCalendarItemSummaries")
    @WebResult(name = "GetCalendarItemSummariesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetCalendarItemSummariesResponse getCalendarItemSummariesRequest(
        @WebParam(name = "GetCalendarItemSummariesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetCalendarItemSummariesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetCommentsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetComments")
    @WebResult(name = "GetCommentsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetCommentsResponse getCommentsRequest(
        @WebParam(name = "GetCommentsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetCommentsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetContactsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetContacts")
    @WebResult(name = "GetContactsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetContactsResponse getContactsRequest(
        @WebParam(name = "GetContactsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetContactsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetConvResponse
     */
    @WebMethod(action = "urn:zmailMail/GetConv")
    @WebResult(name = "GetConvResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetConvResponse getConvRequest(
        @WebParam(name = "GetConvRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetConvRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetCustomMetadataResponse
     */
    @WebMethod(action = "urn:zmailMail/GetCustomMetadata")
    @WebResult(name = "GetCustomMetadataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetCustomMetadataResponse getCustomMetadataRequest(
        @WebParam(name = "GetCustomMetadataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetCustomMetadataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetDataSourcesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetDataSources")
    @WebResult(name = "GetDataSourcesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetDataSourcesResponse getDataSourcesRequest(
        @WebParam(name = "GetDataSourcesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetDataSourcesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "urn:zmailMail/GetDocumentShareURL")
    @WebResult(name = "GetDocumentShareURLResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public String getDocumentShareURLRequest(
        @WebParam(name = "GetDocumentShareURLRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetDocumentShareURLRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetEffectiveFolderPermsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetEffectiveFolderPerms")
    @WebResult(name = "GetEffectiveFolderPermsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetEffectiveFolderPermsResponse getEffectiveFolderPermsRequest(
        @WebParam(name = "GetEffectiveFolderPermsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetEffectiveFolderPermsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetFilterRulesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetFilterRules")
    @WebResult(name = "GetFilterRulesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetFilterRulesResponse getFilterRulesRequest(
        @WebParam(name = "GetFilterRulesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetFilterRulesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetFolderResponse
     */
    @WebMethod(action = "urn:zmailMail/GetFolder")
    @WebResult(name = "GetFolderResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetFolderResponse getFolderRequest(
        @WebParam(name = "GetFolderRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetFolderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetFreeBusyResponse
     */
    @WebMethod(action = "urn:zmailMail/GetFreeBusy")
    @WebResult(name = "GetFreeBusyResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetFreeBusyResponse getFreeBusyRequest(
        @WebParam(name = "GetFreeBusyRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetFreeBusyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetICalResponse
     */
    @WebMethod(action = "urn:zmailMail/GetICal")
    @WebResult(name = "GetICalResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetICalResponse getICalRequest(
        @WebParam(name = "GetICalRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetICalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetImportStatusResponse
     */
    @WebMethod(action = "urn:zmailMail/GetImportStatus")
    @WebResult(name = "GetImportStatusResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetImportStatusResponse getImportStatusRequest(
        @WebParam(name = "GetImportStatusRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetImportStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetItemResponse
     */
    @WebMethod(action = "urn:zmailMail/GetItem")
    @WebResult(name = "GetItemResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetItemResponse getItemRequest(
        @WebParam(name = "GetItemRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetItemRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetMailboxMetadataResponse
     */
    @WebMethod(action = "urn:zmailMail/GetMailboxMetadata")
    @WebResult(name = "GetMailboxMetadataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetMailboxMetadataResponse getMailboxMetadataRequest(
        @WebParam(name = "GetMailboxMetadataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetMailboxMetadataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetMiniCalResponse
     */
    @WebMethod(action = "urn:zmailMail/GetMiniCal")
    @WebResult(name = "GetMiniCalResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetMiniCalResponse getMiniCalRequest(
        @WebParam(name = "GetMiniCalRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetMiniCalRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetMsgMetadataResponse
     */
    @WebMethod(action = "urn:zmailMail/GetMsgMetadata")
    @WebResult(name = "GetMsgMetadataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetMsgMetadataResponse getMsgMetadataRequest(
        @WebParam(name = "GetMsgMetadataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetMsgMetadataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetMsgResponse
     */
    @WebMethod(action = "urn:zmailMail/GetMsg")
    @WebResult(name = "GetMsgResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetMsgResponse getMsgRequest(
        @WebParam(name = "GetMsgRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetMsgRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetNoteResponse
     */
    @WebMethod(action = "urn:zmailMail/GetNote")
    @WebResult(name = "GetNoteResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetNoteResponse getNoteRequest(
        @WebParam(name = "GetNoteRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetNoteRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetNotificationsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetNotifications")
    @WebResult(name = "GetNotificationsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetNotificationsResponse getNotificationsRequest(
        @WebParam(name = "GetNotificationsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetNotificationsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetOutgoingFilterRulesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetOutgoingFilterRules")
    @WebResult(name = "GetOutgoingFilterRulesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetOutgoingFilterRulesResponse getOutgoingFilterRulesRequest(
        @WebParam(name = "GetOutgoingFilterRulesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetOutgoingFilterRulesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetPermissionResponse
     */
    @WebMethod(action = "urn:zmailMail/GetPermission")
    @WebResult(name = "GetPermissionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetPermissionResponse getPermissionRequest(
        @WebParam(name = "GetPermissionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetPermissionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetRecurResponse
     */
    @WebMethod(action = "urn:zmailMail/GetRecur")
    @WebResult(name = "GetRecurResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetRecurResponse getRecurRequest(
        @WebParam(name = "GetRecurRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetRecurRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetSearchFolderResponse
     */
    @WebMethod(action = "urn:zmailMail/GetSearchFolder")
    @WebResult(name = "GetSearchFolderResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetSearchFolderResponse getSearchFolderRequest(
        @WebParam(name = "GetSearchFolderRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetSearchFolderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetShareDetailsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetShareDetails")
    @WebResult(name = "GetShareDetailsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetShareDetailsResponse getShareDetailsRequest(
        @WebParam(name = "GetShareDetailsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetShareDetailsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetShareNotificationsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetShareNotifications")
    @WebResult(name = "GetShareNotificationsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetShareNotificationsResponse getShareNotificationsRequest(
        @WebParam(name = "GetShareNotificationsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetShareNotificationsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetSpellDictionariesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetSpellDictionaries")
    @WebResult(name = "GetSpellDictionariesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetSpellDictionariesResponse getSpellDictionariesRequest(
        @WebParam(name = "GetSpellDictionariesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetSpellDictionariesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetSystemRetentionPolicyResponse
     */
    @WebMethod(action = "urn:zmailMail/GetSystemRetentionPolicy")
    @WebResult(name = "GetSystemRetentionPolicyResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetSystemRetentionPolicyResponse getSystemRetentionPolicyRequest(
        @WebParam(name = "GetSystemRetentionPolicyRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetSystemRetentionPolicyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetTagResponse
     */
    @WebMethod(action = "urn:zmailMail/GetTag")
    @WebResult(name = "GetTagResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetTagResponse getTagRequest(
        @WebParam(name = "GetTagRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetTagRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetTaskResponse
     */
    @WebMethod(action = "urn:zmailMail/GetTask")
    @WebResult(name = "GetTaskResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetTaskResponse getTaskRequest(
        @WebParam(name = "GetTaskRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetTaskRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetTaskSummariesResponse
     */
    @WebMethod(action = "urn:zmailMail/GetTaskSummaries")
    @WebResult(name = "GetTaskSummariesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetTaskSummariesResponse getTaskSummariesRequest(
        @WebParam(name = "GetTaskSummariesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetTaskSummariesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetWatchersResponse
     */
    @WebMethod(action = "urn:zmailMail/GetWatchers")
    @WebResult(name = "GetWatchersResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetWatchersResponse getWatchersRequest(
        @WebParam(name = "GetWatchersRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetWatchersRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetWatchingItemsResponse
     */
    @WebMethod(action = "urn:zmailMail/GetWatchingItems")
    @WebResult(name = "GetWatchingItemsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetWatchingItemsResponse getWatchingItemsRequest(
        @WebParam(name = "GetWatchingItemsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetWatchingItemsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetWorkingHoursResponse
     */
    @WebMethod(action = "urn:zmailMail/GetWorkingHours")
    @WebResult(name = "GetWorkingHoursResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetWorkingHoursResponse getWorkingHoursRequest(
        @WebParam(name = "GetWorkingHoursRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetWorkingHoursRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetYahooAuthTokenResponse
     */
    @WebMethod(action = "urn:zmailMail/GetYahooAuthToken")
    @WebResult(name = "GetYahooAuthTokenResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetYahooAuthTokenResponse getYahooAuthTokenRequest(
        @WebParam(name = "GetYahooAuthTokenRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetYahooAuthTokenRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGetYahooCookieResponse
     */
    @WebMethod(action = "urn:zmailMail/GetYahooCookie")
    @WebResult(name = "GetYahooCookieResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGetYahooCookieResponse getYahooCookieRequest(
        @WebParam(name = "GetYahooCookieRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGetYahooCookieRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testGrantPermissionResponse
     */
    @WebMethod(action = "urn:zmailMail/GrantPermission")
    @WebResult(name = "GrantPermissionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testGrantPermissionResponse grantPermissionRequest(
        @WebParam(name = "GrantPermissionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testGrantPermissionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testICalReplyResponse
     */
    @WebMethod(action = "urn:zmailMail/ICalReply")
    @WebResult(name = "ICalReplyResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testICalReplyResponse iCalReplyRequest(
        @WebParam(name = "ICalReplyRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testICalReplyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testImportAppointmentsResponse
     */
    @WebMethod(action = "urn:zmailMail/ImportAppointments")
    @WebResult(name = "ImportAppointmentsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testImportAppointmentsResponse importAppointmentsRequest(
        @WebParam(name = "ImportAppointmentsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testImportAppointmentsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testImportContactsResponse
     */
    @WebMethod(action = "urn:zmailMail/ImportContacts")
    @WebResult(name = "ImportContactsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testImportContactsResponse importContactsRequest(
        @WebParam(name = "ImportContactsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testImportContactsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testImportDataResponse
     */
    @WebMethod(action = "urn:zmailMail/ImportData")
    @WebResult(name = "ImportDataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testImportDataResponse importDataRequest(
        @WebParam(name = "ImportDataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testImportDataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testInvalidateReminderDeviceResponse
     */
    @WebMethod(action = "urn:zmailMail/InvalidateReminderDevice")
    @WebResult(name = "InvalidateReminderDeviceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testInvalidateReminderDeviceResponse invalidateReminderDeviceRequest(
        @WebParam(name = "InvalidateReminderDeviceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testInvalidateReminderDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testItemActionResponse
     */
    @WebMethod(action = "urn:zmailMail/ItemAction")
    @WebResult(name = "ItemActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testItemActionResponse itemActionRequest(
        @WebParam(name = "ItemActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testItemActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testListDocumentRevisionsResponse
     */
    @WebMethod(action = "urn:zmailMail/ListDocumentRevisions")
    @WebResult(name = "ListDocumentRevisionsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testListDocumentRevisionsResponse listDocumentRevisionsRequest(
        @WebParam(name = "ListDocumentRevisionsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testListDocumentRevisionsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyAppointment")
    @WebResult(name = "ModifyAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyAppointmentResponse modifyAppointmentRequest(
        @WebParam(name = "ModifyAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyContactResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyContact")
    @WebResult(name = "ModifyContactResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyContactResponse modifyContactRequest(
        @WebParam(name = "ModifyContactRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyContactRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyDataSourceResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyDataSource")
    @WebResult(name = "ModifyDataSourceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyDataSourceResponse modifyDataSourceRequest(
        @WebParam(name = "ModifyDataSourceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyFilterRulesResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyFilterRules")
    @WebResult(name = "ModifyFilterRulesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyFilterRulesResponse modifyFilterRulesRequest(
        @WebParam(name = "ModifyFilterRulesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyFilterRulesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyMailboxMetadataResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyMailboxMetadata")
    @WebResult(name = "ModifyMailboxMetadataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyMailboxMetadataResponse modifyMailboxMetadataRequest(
        @WebParam(name = "ModifyMailboxMetadataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyMailboxMetadataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyOutgoingFilterRulesResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyOutgoingFilterRules")
    @WebResult(name = "ModifyOutgoingFilterRulesResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyOutgoingFilterRulesResponse modifyOutgoingFilterRulesRequest(
        @WebParam(name = "ModifyOutgoingFilterRulesRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyOutgoingFilterRulesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifySearchFolderResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifySearchFolder")
    @WebResult(name = "ModifySearchFolderResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifySearchFolderResponse modifySearchFolderRequest(
        @WebParam(name = "ModifySearchFolderRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifySearchFolderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testModifyTaskResponse
     */
    @WebMethod(action = "urn:zmailMail/ModifyTask")
    @WebResult(name = "ModifyTaskResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testModifyTaskResponse modifyTaskRequest(
        @WebParam(name = "ModifyTaskRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testModifyTaskRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testMsgActionResponse
     */
    @WebMethod(action = "urn:zmailMail/MsgAction")
    @WebResult(name = "MsgActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testMsgActionResponse msgActionRequest(
        @WebParam(name = "MsgActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testMsgActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testNoOpResponse
     */
    @WebMethod(action = "urn:zmailMail/NoOp")
    @WebResult(name = "NoOpResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testNoOpResponse noOpRequest(
        @WebParam(name = "NoOpRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testNoOpRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testNoteActionResponse
     */
    @WebMethod(action = "urn:zmailMail/NoteAction")
    @WebResult(name = "NoteActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testNoteActionResponse noteActionRequest(
        @WebParam(name = "NoteActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testNoteActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testPurgeRevisionResponse
     */
    @WebMethod(action = "urn:zmailMail/PurgeRevision")
    @WebResult(name = "PurgeRevisionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testPurgeRevisionResponse purgeRevisionRequest(
        @WebParam(name = "PurgeRevisionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testPurgeRevisionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testRankingActionResponse
     */
    @WebMethod(action = "urn:zmailMail/RankingAction")
    @WebResult(name = "RankingActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testRankingActionResponse rankingActionRequest(
        @WebParam(name = "RankingActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testRankingActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testRegisterDeviceResponse
     */
    @WebMethod(action = "urn:zmailMail/RegisterDevice")
    @WebResult(name = "RegisterDeviceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testRegisterDeviceResponse registerDeviceRequest(
        @WebParam(name = "RegisterDeviceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testRegisterDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testRemoveAttachmentsResponse
     */
    @WebMethod(action = "urn:zmailMail/RemoveAttachments")
    @WebResult(name = "RemoveAttachmentsResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testRemoveAttachmentsResponse removeAttachmentsRequest(
        @WebParam(name = "RemoveAttachmentsRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testRemoveAttachmentsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testRevokePermissionResponse
     */
    @WebMethod(action = "urn:zmailMail/RevokePermission")
    @WebResult(name = "RevokePermissionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testRevokePermissionResponse revokePermissionRequest(
        @WebParam(name = "RevokePermissionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testRevokePermissionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSaveDocumentResponse
     */
    @WebMethod(action = "urn:zmailMail/SaveDocument")
    @WebResult(name = "SaveDocumentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSaveDocumentResponse saveDocumentRequest(
        @WebParam(name = "SaveDocumentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSaveDocumentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSaveDraftResponse
     */
    @WebMethod(action = "urn:zmailMail/SaveDraft")
    @WebResult(name = "SaveDraftResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSaveDraftResponse saveDraftRequest(
        @WebParam(name = "SaveDraftRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSaveDraftRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSearchConvResponse
     */
    @WebMethod(action = "urn:zmailMail/SearchConv")
    @WebResult(name = "SearchConvResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSearchConvResponse searchConvRequest(
        @WebParam(name = "SearchConvRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSearchConvRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSearchResponse
     */
    @WebMethod(action = "urn:zmailMail/Search")
    @WebResult(name = "SearchResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSearchResponse searchRequest(
        @WebParam(name = "SearchRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSearchRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSendDeliveryReportResponse
     */
    @WebMethod(action = "urn:zmailMail/SendDeliveryReport")
    @WebResult(name = "SendDeliveryReportResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSendDeliveryReportResponse sendDeliveryReportRequest(
        @WebParam(name = "SendDeliveryReportRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSendDeliveryReportRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSendInviteReplyResponse
     */
    @WebMethod(action = "urn:zmailMail/SendInviteReply")
    @WebResult(name = "SendInviteReplyResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSendInviteReplyResponse sendInviteReplyRequest(
        @WebParam(name = "SendInviteReplyRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSendInviteReplyRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSendMsgResponse
     */
    @WebMethod(action = "urn:zmailMail/SendMsg")
    @WebResult(name = "SendMsgResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSendMsgResponse sendMsgRequest(
        @WebParam(name = "SendMsgRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSendMsgRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSendShareNotificationResponse
     */
    @WebMethod(action = "urn:zmailMail/SendShareNotification")
    @WebResult(name = "SendShareNotificationResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSendShareNotificationResponse sendShareNotificationRequest(
        @WebParam(name = "SendShareNotificationRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSendShareNotificationRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSendVerificationCodeResponse
     */
    @WebMethod(action = "urn:zmailMail/SendVerificationCode")
    @WebResult(name = "SendVerificationCodeResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSendVerificationCodeResponse sendVerificationCodeRequest(
        @WebParam(name = "SendVerificationCodeRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSendVerificationCodeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSetAppointmentResponse
     */
    @WebMethod(action = "urn:zmailMail/SetAppointment")
    @WebResult(name = "SetAppointmentResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSetAppointmentResponse setAppointmentRequest(
        @WebParam(name = "SetAppointmentRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSetAppointmentRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSetCustomMetadataResponse
     */
    @WebMethod(action = "urn:zmailMail/SetCustomMetadata")
    @WebResult(name = "SetCustomMetadataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSetCustomMetadataResponse setCustomMetadataRequest(
        @WebParam(name = "SetCustomMetadataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSetCustomMetadataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSetMailboxMetadataResponse
     */
    @WebMethod(action = "urn:zmailMail/SetMailboxMetadata")
    @WebResult(name = "SetMailboxMetadataResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSetMailboxMetadataResponse setMailboxMetadataRequest(
        @WebParam(name = "SetMailboxMetadataRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSetMailboxMetadataRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSetTaskResponse
     */
    @WebMethod(action = "urn:zmailMail/SetTask")
    @WebResult(name = "SetTaskResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSetTaskResponse setTaskRequest(
        @WebParam(name = "SetTaskRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSetTaskRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSnoozeCalendarItemAlarmResponse
     */
    @WebMethod(action = "urn:zmailMail/SnoozeCalendarItemAlarm")
    @WebResult(name = "SnoozeCalendarItemAlarmResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSnoozeCalendarItemAlarmResponse snoozeCalendarItemAlarmRequest(
        @WebParam(name = "SnoozeCalendarItemAlarmRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSnoozeCalendarItemAlarmRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testSyncResponse
     */
    @WebMethod(action = "urn:zmailMail/Sync")
    @WebResult(name = "SyncResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testSyncResponse syncRequest(
        @WebParam(name = "SyncRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testSyncRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testTagActionResponse
     */
    @WebMethod(action = "urn:zmailMail/TagAction")
    @WebResult(name = "TagActionResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testTagActionResponse tagActionRequest(
        @WebParam(name = "TagActionRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testTagActionRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testTestDataSourceResponse
     */
    @WebMethod(action = "urn:zmailMail/TestDataSource")
    @WebResult(name = "TestDataSourceResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testTestDataSourceResponse testDataSourceRequest(
        @WebParam(name = "TestDataSourceRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testTestDataSourceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testUpdateDeviceStatusResponse
     */
    @WebMethod(action = "urn:zmailMail/UpdateDeviceStatus")
    @WebResult(name = "UpdateDeviceStatusResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testUpdateDeviceStatusResponse updateDeviceStatusRequest(
        @WebParam(name = "UpdateDeviceStatusRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testUpdateDeviceStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testVerifyCodeResponse
     */
    @WebMethod(action = "urn:zmailMail/VerifyCode")
    @WebResult(name = "VerifyCodeResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testVerifyCodeResponse verifyCodeRequest(
        @WebParam(name = "VerifyCodeRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testVerifyCodeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.mail.testWaitSetResponse
     */
    @WebMethod(action = "urn:zmailMail/WaitSet")
    @WebResult(name = "WaitSetResponse", targetNamespace = "urn:zmailMail", partName = "parameters")
    public testWaitSetResponse waitSetRequest(
        @WebParam(name = "WaitSetRequest", targetNamespace = "urn:zmailMail", partName = "parameters")
        testWaitSetRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testBecomeMasterResponse
     */
    @WebMethod(action = "urn:zmailRepl/BecomeMaster")
    @WebResult(name = "BecomeMasterResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testBecomeMasterResponse becomeMasterRequest(
        @WebParam(name = "BecomeMasterRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testBecomeMasterRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testBringDownServiceIPResponse
     */
    @WebMethod(action = "urn:zmailRepl/BringDownServiceIP")
    @WebResult(name = "BringDownServiceIPResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testBringDownServiceIPResponse bringDownServiceIPRequest(
        @WebParam(name = "BringDownServiceIPRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testBringDownServiceIPRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testBringUpServiceIPResponse
     */
    @WebMethod(action = "urn:zmailRepl/BringUpServiceIP")
    @WebResult(name = "BringUpServiceIPResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testBringUpServiceIPResponse bringUpServiceIPRequest(
        @WebParam(name = "BringUpServiceIPRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testBringUpServiceIPRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testReplicationStatusResponse
     */
    @WebMethod(action = "urn:zmailRepl/ReplicationStatus")
    @WebResult(name = "ReplicationStatusResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testReplicationStatusResponse replicationStatusRequest(
        @WebParam(name = "ReplicationStatusRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testReplicationStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testStartCatchupResponse
     */
    @WebMethod(action = "urn:zmailRepl/StartCatchup")
    @WebResult(name = "StartCatchupResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testStartCatchupResponse startCatchupRequest(
        @WebParam(name = "StartCatchupRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testStartCatchupRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testStartFailoverClientResponse
     */
    @WebMethod(action = "urn:zmailRepl/StartFailoverClient")
    @WebResult(name = "StartFailoverClientResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testStartFailoverClientResponse startFailoverClientRequest(
        @WebParam(name = "StartFailoverClientRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testStartFailoverClientRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testStartFailoverDaemonResponse
     */
    @WebMethod(action = "urn:zmailRepl/StartFailoverDaemon")
    @WebResult(name = "StartFailoverDaemonResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testStartFailoverDaemonResponse startFailoverDaemonRequest(
        @WebParam(name = "StartFailoverDaemonRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testStartFailoverDaemonRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testStopFailoverClientResponse
     */
    @WebMethod(action = "urn:zmailRepl/StopFailoverClient")
    @WebResult(name = "StopFailoverClientResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testStopFailoverClientResponse stopFailoverClientRequest(
        @WebParam(name = "StopFailoverClientRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testStopFailoverClientRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.replication.testStopFailoverDaemonResponse
     */
    @WebMethod(action = "urn:zmailRepl/StopFailoverDaemon")
    @WebResult(name = "StopFailoverDaemonResponse", targetNamespace = "urn:zmailRepl", partName = "parameters")
    public testStopFailoverDaemonResponse stopFailoverDaemonRequest(
        @WebParam(name = "StopFailoverDaemonRequest", targetNamespace = "urn:zmailRepl", partName = "parameters")
        testStopFailoverDaemonRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.sync.testCancelPendingRemoteWipeResponse
     */
    @WebMethod(action = "urn:zmailSync/CancelPendingRemoteWipe")
    @WebResult(name = "CancelPendingRemoteWipeResponse", targetNamespace = "urn:zmailSync", partName = "parameters")
    public testCancelPendingRemoteWipeResponse cancelPendingRemoteWipeRequest(
        @WebParam(name = "CancelPendingRemoteWipeRequest", targetNamespace = "urn:zmailSync", partName = "parameters")
        testCancelPendingRemoteWipeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.sync.testGetDeviceStatusResponse
     */
    @WebMethod(action = "urn:zmailSync/GetDeviceStatus")
    @WebResult(name = "GetDeviceStatusResponse", targetNamespace = "urn:zmailSync", partName = "parameters")
    public testGetDeviceStatusResponse getDeviceStatusRequest(
        @WebParam(name = "GetDeviceStatusRequest", targetNamespace = "urn:zmailSync", partName = "parameters")
        testGetDeviceStatusRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.sync.testRemoteWipeResponse
     */
    @WebMethod(action = "urn:zmailSync/RemoteWipe")
    @WebResult(name = "RemoteWipeResponse", targetNamespace = "urn:zmailSync", partName = "parameters")
    public testRemoteWipeResponse remoteWipeRequest(
        @WebParam(name = "RemoteWipeRequest", targetNamespace = "urn:zmailSync", partName = "parameters")
        testRemoteWipeRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.sync.testRemoveDeviceResponse
     */
    @WebMethod(action = "urn:zmailSync/RemoveDevice")
    @WebResult(name = "RemoveDeviceResponse", targetNamespace = "urn:zmailSync", partName = "parameters")
    public testRemoveDeviceResponse removeDeviceRequest(
        @WebParam(name = "RemoveDeviceRequest", targetNamespace = "urn:zmailSync", partName = "parameters")
        testRemoveDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.sync.testResumeDeviceResponse
     */
    @WebMethod(action = "urn:zmailSync/ResumeDevice")
    @WebResult(name = "ResumeDeviceResponse", targetNamespace = "urn:zmailSync", partName = "parameters")
    public testResumeDeviceResponse resumeDeviceRequest(
        @WebParam(name = "ResumeDeviceRequest", targetNamespace = "urn:zmailSync", partName = "parameters")
        testResumeDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.sync.testSuspendDeviceResponse
     */
    @WebMethod(action = "urn:zmailSync/SuspendDevice")
    @WebResult(name = "SuspendDeviceResponse", targetNamespace = "urn:zmailSync", partName = "parameters")
    public testSuspendDeviceResponse suspendDeviceRequest(
        @WebParam(name = "SuspendDeviceRequest", targetNamespace = "urn:zmailSync", partName = "parameters")
        testSuspendDeviceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testChangeUCPasswordResponse
     */
    @WebMethod(action = "urn:zmailVoice/ChangeUCPassword")
    @WebResult(name = "ChangeUCPasswordResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testChangeUCPasswordResponse changeUCPasswordRequest(
        @WebParam(name = "ChangeUCPasswordRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testChangeUCPasswordRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testGetUCInfoResponse
     */
    @WebMethod(action = "urn:zmailVoice/GetUCInfo")
    @WebResult(name = "GetUCInfoResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testGetUCInfoResponse getUCInfoRequest(
        @WebParam(name = "GetUCInfoRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testGetUCInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testGetVoiceFeaturesResponse
     */
    @WebMethod(action = "urn:zmailVoice/GetVoiceFeatures")
    @WebResult(name = "GetVoiceFeaturesResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testGetVoiceFeaturesResponse getVoiceFeaturesRequest(
        @WebParam(name = "GetVoiceFeaturesRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testGetVoiceFeaturesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testGetVoiceFolderResponse
     */
    @WebMethod(action = "urn:zmailVoice/GetVoiceFolder")
    @WebResult(name = "GetVoiceFolderResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testGetVoiceFolderResponse getVoiceFolderRequest(
        @WebParam(name = "GetVoiceFolderRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testGetVoiceFolderRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testGetVoiceInfoResponse
     */
    @WebMethod(action = "urn:zmailVoice/GetVoiceInfo")
    @WebResult(name = "GetVoiceInfoResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testGetVoiceInfoResponse getVoiceInfoRequest(
        @WebParam(name = "GetVoiceInfoRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testGetVoiceInfoRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testGetVoiceMailPrefsResponse
     */
    @WebMethod(action = "urn:zmailVoice/GetVoiceMailPrefs")
    @WebResult(name = "GetVoiceMailPrefsResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testGetVoiceMailPrefsResponse getVoiceMailPrefsRequest(
        @WebParam(name = "GetVoiceMailPrefsRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testGetVoiceMailPrefsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testModifyFromNumResponse
     */
    @WebMethod(action = "urn:zmailVoice/ModifyFromNum")
    @WebResult(name = "ModifyFromNumResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testModifyFromNumResponse modifyFromNumRequest(
        @WebParam(name = "ModifyFromNumRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testModifyFromNumRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testModifyVoiceFeaturesResponse
     */
    @WebMethod(action = "urn:zmailVoice/ModifyVoiceFeatures")
    @WebResult(name = "ModifyVoiceFeaturesResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testModifyVoiceFeaturesResponse modifyVoiceFeaturesRequest(
        @WebParam(name = "ModifyVoiceFeaturesRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testModifyVoiceFeaturesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testModifyVoiceMailPinResponse
     */
    @WebMethod(action = "urn:zmailVoice/ModifyVoiceMailPin")
    @WebResult(name = "ModifyVoiceMailPinResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testModifyVoiceMailPinResponse modifyVoiceMailPinRequest(
        @WebParam(name = "ModifyVoiceMailPinRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testModifyVoiceMailPinRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testModifyVoiceMailPrefsResponse
     */
    @WebMethod(action = "urn:zmailVoice/ModifyVoiceMailPrefs")
    @WebResult(name = "ModifyVoiceMailPrefsResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testModifyVoiceMailPrefsResponse modifyVoiceMailPrefsRequest(
        @WebParam(name = "ModifyVoiceMailPrefsRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testModifyVoiceMailPrefsRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testResetVoiceFeaturesResponse
     */
    @WebMethod(action = "urn:zmailVoice/ResetVoiceFeatures")
    @WebResult(name = "ResetVoiceFeaturesResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testResetVoiceFeaturesResponse resetVoiceFeaturesRequest(
        @WebParam(name = "ResetVoiceFeaturesRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testResetVoiceFeaturesRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testSearchVoiceResponse
     */
    @WebMethod(action = "urn:zmailVoice/SearchVoice")
    @WebResult(name = "SearchVoiceResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testSearchVoiceResponse searchVoiceRequest(
        @WebParam(name = "SearchVoiceRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testSearchVoiceRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testUploadVoiceMailResponse
     */
    @WebMethod(action = "urn:zmailVoice/UploadVoiceMail")
    @WebResult(name = "UploadVoiceMailResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testUploadVoiceMailResponse uploadVoiceMailRequest(
        @WebParam(name = "UploadVoiceMailRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testUploadVoiceMailRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns generated.zcsclient.voice.testVoiceMsgActionResponse
     */
    @WebMethod(action = "urn:zmailVoice/VoiceMsgAction")
    @WebResult(name = "VoiceMsgActionResponse", targetNamespace = "urn:zmailVoice", partName = "parameters")
    public testVoiceMsgActionResponse voiceMsgActionRequest(
        @WebParam(name = "VoiceMsgActionRequest", targetNamespace = "urn:zmailVoice", partName = "parameters")
        testVoiceMsgActionRequest parameters);

}
