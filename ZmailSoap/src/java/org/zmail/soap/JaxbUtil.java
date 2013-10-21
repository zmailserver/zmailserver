/*
 * ***** BEGIN LICENSE BLOCK *****
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
 * ***** END LICENSE BLOCK *****
 */

package org.zmail.soap;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.dom4j.Document;
import org.dom4j.Namespace;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.zmail.common.service.ServiceException;
import org.zmail.common.soap.AccountConstants;
import org.zmail.common.soap.AdminConstants;
import org.zmail.common.soap.Element;
import org.zmail.common.soap.MailConstants;
import org.zmail.common.soap.Element.JSONElement;
import org.zmail.common.soap.Element.XMLElement;
import org.zmail.common.util.Log;
import org.zmail.common.util.ZmailLog;
import org.zmail.soap.json.JacksonUtil;
import org.zmail.soap.util.JaxbInfo;

public final class JaxbUtil {

    private static final Log LOG = ZmailLog.soap;
    private static final Class<?>[] MESSAGE_CLASSES;
    private static final String ACCOUNT_JAXB_PACKAGE = "org.zmail.soap.account.message";
    private static final String ADMIN_JAXB_PACKAGE = "org.zmail.soap.admin.message";
    private static final String MAIL_JAXB_PACKAGE = "org.zmail.soap.mail.message";
    private static JAXBContext JAXB_CONTEXT;
    private static Map <Class<?>,JAXBContext> classJaxbContexts;

    static {
        MESSAGE_CLASSES = new Class<?>[] {
            // zmailAccount
            org.zmail.soap.account.message.AuthRequest.class,
            org.zmail.soap.account.message.AuthResponse.class,
            org.zmail.soap.account.message.AutoCompleteGalRequest.class,
            org.zmail.soap.account.message.AutoCompleteGalResponse.class,
            org.zmail.soap.account.message.ChangePasswordRequest.class,
            org.zmail.soap.account.message.ChangePasswordResponse.class,
            org.zmail.soap.account.message.CheckLicenseRequest.class,
            org.zmail.soap.account.message.CheckLicenseResponse.class,
            org.zmail.soap.account.message.CheckRightsRequest.class,
            org.zmail.soap.account.message.CheckRightsResponse.class,
            org.zmail.soap.account.message.CreateDistributionListRequest.class,
            org.zmail.soap.account.message.CreateDistributionListResponse.class,
            org.zmail.soap.account.message.CreateIdentityRequest.class,
            org.zmail.soap.account.message.CreateIdentityResponse.class,
            org.zmail.soap.account.message.CreateSignatureRequest.class,
            org.zmail.soap.account.message.CreateSignatureResponse.class,
            org.zmail.soap.account.message.DeleteIdentityRequest.class,
            org.zmail.soap.account.message.DeleteIdentityResponse.class,
            org.zmail.soap.account.message.DeleteSignatureRequest.class,
            org.zmail.soap.account.message.DeleteSignatureResponse.class,
            org.zmail.soap.account.message.DiscoverRightsRequest.class,
            org.zmail.soap.account.message.DiscoverRightsResponse.class,
            org.zmail.soap.account.message.DistributionListActionRequest.class,
            org.zmail.soap.account.message.DistributionListActionResponse.class,
            org.zmail.soap.account.message.EndSessionRequest.class,
            org.zmail.soap.account.message.EndSessionResponse.class,
            org.zmail.soap.account.message.GetAccountDistributionListsRequest.class,
            org.zmail.soap.account.message.GetAccountDistributionListsResponse.class,
            org.zmail.soap.account.message.GetAccountInfoRequest.class,
            org.zmail.soap.account.message.GetAccountInfoResponse.class,
            org.zmail.soap.account.message.GetAllLocalesRequest.class,
            org.zmail.soap.account.message.GetAllLocalesResponse.class,
            org.zmail.soap.account.message.GetAvailableCsvFormatsRequest.class,
            org.zmail.soap.account.message.GetAvailableCsvFormatsResponse.class,
            org.zmail.soap.account.message.GetAvailableLocalesRequest.class,
            org.zmail.soap.account.message.GetAvailableLocalesResponse.class,
            org.zmail.soap.account.message.GetAvailableSkinsRequest.class,
            org.zmail.soap.account.message.GetAvailableSkinsResponse.class,
            org.zmail.soap.account.message.GetDistributionListMembersRequest.class,
            org.zmail.soap.account.message.GetDistributionListMembersResponse.class,
            org.zmail.soap.account.message.GetDistributionListRequest.class,
            org.zmail.soap.account.message.GetDistributionListResponse.class,
            org.zmail.soap.account.message.GetIdentitiesRequest.class,
            org.zmail.soap.account.message.GetIdentitiesResponse.class,
            org.zmail.soap.account.message.GetInfoRequest.class,
            org.zmail.soap.account.message.GetInfoResponse.class,
            org.zmail.soap.account.message.GetPrefsRequest.class,
            org.zmail.soap.account.message.GetPrefsResponse.class,
            org.zmail.soap.account.message.GetRightsRequest.class,
            org.zmail.soap.account.message.GetRightsResponse.class,
            org.zmail.soap.account.message.GetSMIMEPublicCertsRequest.class,
            org.zmail.soap.account.message.GetSMIMEPublicCertsResponse.class,
            org.zmail.soap.account.message.GetShareInfoRequest.class,
            org.zmail.soap.account.message.GetShareInfoResponse.class,
            org.zmail.soap.account.message.GetSignaturesRequest.class,
            org.zmail.soap.account.message.GetSignaturesResponse.class,
            org.zmail.soap.account.message.GetVersionInfoRequest.class,
            org.zmail.soap.account.message.GetVersionInfoResponse.class,
            org.zmail.soap.account.message.GetWhiteBlackListRequest.class,
            org.zmail.soap.account.message.GetWhiteBlackListResponse.class,
            org.zmail.soap.account.message.GrantRightsRequest.class,
            org.zmail.soap.account.message.GrantRightsResponse.class,
            org.zmail.soap.account.message.ModifyIdentityRequest.class,
            org.zmail.soap.account.message.ModifyIdentityResponse.class,
            org.zmail.soap.account.message.ModifyPrefsRequest.class,
            org.zmail.soap.account.message.ModifyPrefsResponse.class,
            org.zmail.soap.account.message.ModifyPropertiesRequest.class,
            org.zmail.soap.account.message.ModifyPropertiesResponse.class,
            org.zmail.soap.account.message.ModifySignatureRequest.class,
            org.zmail.soap.account.message.ModifySignatureResponse.class,
            org.zmail.soap.account.message.ModifyWhiteBlackListRequest.class,
            org.zmail.soap.account.message.ModifyWhiteBlackListResponse.class,
            org.zmail.soap.account.message.ModifyZimletPrefsRequest.class,
            org.zmail.soap.account.message.ModifyZimletPrefsResponse.class,
            org.zmail.soap.account.message.RevokeRightsRequest.class,
            org.zmail.soap.account.message.RevokeRightsResponse.class,
            org.zmail.soap.account.message.SearchCalendarResourcesRequest.class,
            org.zmail.soap.account.message.SearchCalendarResourcesResponse.class,
            org.zmail.soap.account.message.SearchGalRequest.class,
            org.zmail.soap.account.message.SearchGalResponse.class,
            org.zmail.soap.account.message.SubscribeDistributionListRequest.class,
            org.zmail.soap.account.message.SubscribeDistributionListResponse.class,
            org.zmail.soap.account.message.SyncGalRequest.class,
            org.zmail.soap.account.message.SyncGalResponse.class,

            // zmailMail
            org.zmail.soap.mail.message.AddAppointmentInviteRequest.class,
            org.zmail.soap.mail.message.AddAppointmentInviteResponse.class,
            org.zmail.soap.mail.message.AddCommentRequest.class,
            org.zmail.soap.mail.message.AddCommentResponse.class,
            org.zmail.soap.mail.message.AddMsgRequest.class,
            org.zmail.soap.mail.message.AddMsgResponse.class,
            org.zmail.soap.mail.message.AddTaskInviteRequest.class,
            org.zmail.soap.mail.message.AddTaskInviteResponse.class,
            org.zmail.soap.mail.message.AnnounceOrganizerChangeRequest.class,
            org.zmail.soap.mail.message.AnnounceOrganizerChangeResponse.class,
            org.zmail.soap.mail.message.ApplyFilterRulesRequest.class,
            org.zmail.soap.mail.message.ApplyFilterRulesResponse.class,
            org.zmail.soap.mail.message.ApplyOutgoingFilterRulesRequest.class,
            org.zmail.soap.mail.message.ApplyOutgoingFilterRulesResponse.class,
            org.zmail.soap.mail.message.AutoCompleteRequest.class,
            org.zmail.soap.mail.message.AutoCompleteResponse.class,
            org.zmail.soap.mail.message.BounceMsgRequest.class,
            org.zmail.soap.mail.message.BounceMsgResponse.class,
            org.zmail.soap.mail.message.BrowseRequest.class,
            org.zmail.soap.mail.message.BrowseResponse.class,
            org.zmail.soap.mail.message.CancelAppointmentRequest.class,
            org.zmail.soap.mail.message.CancelAppointmentResponse.class,
            org.zmail.soap.mail.message.CancelTaskRequest.class,
            org.zmail.soap.mail.message.CancelTaskResponse.class,
            org.zmail.soap.mail.message.CheckDeviceStatusRequest.class,
            org.zmail.soap.mail.message.CheckDeviceStatusResponse.class,
            org.zmail.soap.mail.message.CheckPermissionRequest.class,
            org.zmail.soap.mail.message.CheckPermissionResponse.class,
            org.zmail.soap.mail.message.CheckRecurConflictsRequest.class,
            org.zmail.soap.mail.message.CheckRecurConflictsResponse.class,
            org.zmail.soap.mail.message.CheckSpellingRequest.class,
            org.zmail.soap.mail.message.CheckSpellingResponse.class,
            org.zmail.soap.mail.message.CompleteTaskInstanceRequest.class,
            org.zmail.soap.mail.message.CompleteTaskInstanceResponse.class,
            org.zmail.soap.mail.message.ContactActionRequest.class,
            org.zmail.soap.mail.message.ContactActionResponse.class,
            org.zmail.soap.mail.message.ConvActionRequest.class,
            org.zmail.soap.mail.message.ConvActionResponse.class,
            org.zmail.soap.mail.message.CounterAppointmentRequest.class,
            org.zmail.soap.mail.message.CounterAppointmentResponse.class,
            org.zmail.soap.mail.message.CreateAppointmentExceptionRequest.class,
            org.zmail.soap.mail.message.CreateAppointmentExceptionResponse.class,
            org.zmail.soap.mail.message.CreateAppointmentRequest.class,
            org.zmail.soap.mail.message.CreateAppointmentResponse.class,
            org.zmail.soap.mail.message.CreateContactRequest.class,
            org.zmail.soap.mail.message.CreateContactResponse.class,
            org.zmail.soap.mail.message.CreateDataSourceRequest.class,
            org.zmail.soap.mail.message.CreateDataSourceResponse.class,
            org.zmail.soap.mail.message.CreateFolderRequest.class,
            org.zmail.soap.mail.message.CreateFolderResponse.class,
            org.zmail.soap.mail.message.CreateMountpointRequest.class,
            org.zmail.soap.mail.message.CreateMountpointResponse.class,
            org.zmail.soap.mail.message.CreateNoteRequest.class,
            org.zmail.soap.mail.message.CreateNoteResponse.class,
            org.zmail.soap.mail.message.CreateSearchFolderRequest.class,
            org.zmail.soap.mail.message.CreateSearchFolderResponse.class,
            org.zmail.soap.mail.message.CreateTagRequest.class,
            org.zmail.soap.mail.message.CreateTagResponse.class,
            org.zmail.soap.mail.message.CreateTaskExceptionRequest.class,
            org.zmail.soap.mail.message.CreateTaskExceptionResponse.class,
            org.zmail.soap.mail.message.CreateTaskRequest.class,
            org.zmail.soap.mail.message.CreateTaskResponse.class,
            org.zmail.soap.mail.message.CreateWaitSetRequest.class,
            org.zmail.soap.mail.message.CreateWaitSetResponse.class,
            org.zmail.soap.mail.message.DeclineCounterAppointmentRequest.class,
            org.zmail.soap.mail.message.DeclineCounterAppointmentResponse.class,
            org.zmail.soap.mail.message.DeleteDataSourceRequest.class,
            org.zmail.soap.mail.message.DeleteDataSourceResponse.class,
            org.zmail.soap.mail.message.DeleteDeviceRequest.class,
            org.zmail.soap.mail.message.DeleteDeviceResponse.class,
            org.zmail.soap.mail.message.DestroyWaitSetRequest.class,
            org.zmail.soap.mail.message.DestroyWaitSetResponse.class,
            org.zmail.soap.mail.message.DiffDocumentRequest.class,
            org.zmail.soap.mail.message.DiffDocumentResponse.class,
            org.zmail.soap.mail.message.DismissCalendarItemAlarmRequest.class,
            org.zmail.soap.mail.message.DismissCalendarItemAlarmResponse.class,
            org.zmail.soap.mail.message.DocumentActionRequest.class,
            org.zmail.soap.mail.message.DocumentActionResponse.class,
            org.zmail.soap.mail.message.EmptyDumpsterRequest.class,
            org.zmail.soap.mail.message.EmptyDumpsterResponse.class,
            org.zmail.soap.mail.message.EnableSharedReminderRequest.class,
            org.zmail.soap.mail.message.EnableSharedReminderResponse.class,
            org.zmail.soap.mail.message.ExpandRecurRequest.class,
            org.zmail.soap.mail.message.ExpandRecurResponse.class,
            org.zmail.soap.mail.message.ExportContactsRequest.class,
            org.zmail.soap.mail.message.ExportContactsResponse.class,
            org.zmail.soap.mail.message.FolderActionRequest.class,
            org.zmail.soap.mail.message.FolderActionResponse.class,
            org.zmail.soap.mail.message.ForwardAppointmentInviteRequest.class,
            org.zmail.soap.mail.message.ForwardAppointmentInviteResponse.class,
            org.zmail.soap.mail.message.ForwardAppointmentRequest.class,
            org.zmail.soap.mail.message.ForwardAppointmentResponse.class,
            org.zmail.soap.mail.message.GenerateUUIDRequest.class,
            org.zmail.soap.mail.message.GenerateUUIDResponse.class,
            org.zmail.soap.mail.message.GetActivityStreamRequest.class,
            org.zmail.soap.mail.message.GetActivityStreamResponse.class,
            org.zmail.soap.mail.message.GetAllDevicesRequest.class,
            org.zmail.soap.mail.message.GetAllDevicesResponse.class,
            org.zmail.soap.mail.message.GetAppointmentRequest.class,
            org.zmail.soap.mail.message.GetAppointmentResponse.class,
            org.zmail.soap.mail.message.GetApptSummariesRequest.class,
            org.zmail.soap.mail.message.GetApptSummariesResponse.class,
            org.zmail.soap.mail.message.GetCalendarItemSummariesRequest.class,
            org.zmail.soap.mail.message.GetCalendarItemSummariesResponse.class,
            org.zmail.soap.mail.message.GetCommentsRequest.class,
            org.zmail.soap.mail.message.GetCommentsResponse.class,
            org.zmail.soap.mail.message.GetContactsRequest.class,
            org.zmail.soap.mail.message.GetContactsResponse.class,
            org.zmail.soap.mail.message.GetConvRequest.class,
            org.zmail.soap.mail.message.GetConvResponse.class,
            org.zmail.soap.mail.message.GetCustomMetadataRequest.class,
            org.zmail.soap.mail.message.GetCustomMetadataResponse.class,
            org.zmail.soap.mail.message.GetDataSourcesRequest.class,
            org.zmail.soap.mail.message.GetDataSourcesResponse.class,
            org.zmail.soap.mail.message.GetDocumentShareURLRequest.class,
            org.zmail.soap.mail.message.GetDocumentShareURLResponse.class,
            org.zmail.soap.mail.message.GetEffectiveFolderPermsRequest.class,
            org.zmail.soap.mail.message.GetEffectiveFolderPermsResponse.class,
            org.zmail.soap.mail.message.GetFilterRulesRequest.class,
            org.zmail.soap.mail.message.GetFilterRulesResponse.class,
            org.zmail.soap.mail.message.GetFolderRequest.class,
            org.zmail.soap.mail.message.GetFolderResponse.class,
            org.zmail.soap.mail.message.GetFreeBusyRequest.class,
            org.zmail.soap.mail.message.GetFreeBusyResponse.class,
            org.zmail.soap.mail.message.GetICalRequest.class,
            org.zmail.soap.mail.message.GetICalResponse.class,
            org.zmail.soap.mail.message.GetImportStatusRequest.class,
            org.zmail.soap.mail.message.GetImportStatusResponse.class,
            org.zmail.soap.mail.message.GetItemRequest.class,
            org.zmail.soap.mail.message.GetItemResponse.class,
            org.zmail.soap.mail.message.GetMailboxMetadataRequest.class,
            org.zmail.soap.mail.message.GetMailboxMetadataResponse.class,
            org.zmail.soap.mail.message.GetMiniCalRequest.class,
            org.zmail.soap.mail.message.GetMiniCalResponse.class,
            org.zmail.soap.mail.message.GetMsgMetadataRequest.class,
            org.zmail.soap.mail.message.GetMsgMetadataResponse.class,
            org.zmail.soap.mail.message.GetMsgRequest.class,
            org.zmail.soap.mail.message.GetMsgResponse.class,
            org.zmail.soap.mail.message.GetNoteRequest.class,
            org.zmail.soap.mail.message.GetNoteResponse.class,
            org.zmail.soap.mail.message.GetNotificationsRequest.class,
            org.zmail.soap.mail.message.GetNotificationsResponse.class,
            org.zmail.soap.mail.message.GetOutgoingFilterRulesRequest.class,
            org.zmail.soap.mail.message.GetOutgoingFilterRulesResponse.class,
            org.zmail.soap.mail.message.GetPermissionRequest.class,
            org.zmail.soap.mail.message.GetPermissionResponse.class,
            org.zmail.soap.mail.message.GetRecurRequest.class,
            org.zmail.soap.mail.message.GetRecurResponse.class,
            org.zmail.soap.mail.message.GetSearchFolderRequest.class,
            org.zmail.soap.mail.message.GetSearchFolderResponse.class,
            org.zmail.soap.mail.message.GetShareDetailsRequest.class,
            org.zmail.soap.mail.message.GetShareDetailsResponse.class,
            org.zmail.soap.mail.message.GetShareNotificationsRequest.class,
            org.zmail.soap.mail.message.GetShareNotificationsResponse.class,
            org.zmail.soap.mail.message.GetSpellDictionariesRequest.class,
            org.zmail.soap.mail.message.GetSpellDictionariesResponse.class,
            org.zmail.soap.mail.message.GetSystemRetentionPolicyRequest.class,
            org.zmail.soap.mail.message.GetSystemRetentionPolicyResponse.class,
            org.zmail.soap.mail.message.GetTagRequest.class,
            org.zmail.soap.mail.message.GetTagResponse.class,
            org.zmail.soap.mail.message.GetTaskRequest.class,
            org.zmail.soap.mail.message.GetTaskResponse.class,
            org.zmail.soap.mail.message.GetTaskSummariesRequest.class,
            org.zmail.soap.mail.message.GetTaskSummariesResponse.class,
            org.zmail.soap.mail.message.GetWatchersRequest.class,
            org.zmail.soap.mail.message.GetWatchersResponse.class,
            org.zmail.soap.mail.message.GetWatchingItemsRequest.class,
            org.zmail.soap.mail.message.GetWatchingItemsResponse.class,
            org.zmail.soap.mail.message.GetWorkingHoursRequest.class,
            org.zmail.soap.mail.message.GetWorkingHoursResponse.class,
            org.zmail.soap.mail.message.GetYahooAuthTokenRequest.class,
            org.zmail.soap.mail.message.GetYahooAuthTokenResponse.class,
            org.zmail.soap.mail.message.GetYahooCookieRequest.class,
            org.zmail.soap.mail.message.GetYahooCookieResponse.class,
            org.zmail.soap.mail.message.GrantPermissionRequest.class,
            org.zmail.soap.mail.message.GrantPermissionResponse.class,
            org.zmail.soap.mail.message.ICalReplyRequest.class,
            org.zmail.soap.mail.message.ICalReplyResponse.class,
            org.zmail.soap.mail.message.ImportAppointmentsRequest.class,
            org.zmail.soap.mail.message.ImportAppointmentsResponse.class,
            org.zmail.soap.mail.message.ImportContactsRequest.class,
            org.zmail.soap.mail.message.ImportContactsResponse.class,
            org.zmail.soap.mail.message.ImportDataRequest.class,
            org.zmail.soap.mail.message.ImportDataResponse.class,
            org.zmail.soap.mail.message.InvalidateReminderDeviceRequest.class,
            org.zmail.soap.mail.message.InvalidateReminderDeviceResponse.class,
            org.zmail.soap.mail.message.ItemActionRequest.class,
            org.zmail.soap.mail.message.ItemActionResponse.class,
            org.zmail.soap.mail.message.ListDocumentRevisionsRequest.class,
            org.zmail.soap.mail.message.ListDocumentRevisionsResponse.class,
            org.zmail.soap.mail.message.ModifyAppointmentRequest.class,
            org.zmail.soap.mail.message.ModifyAppointmentResponse.class,
            org.zmail.soap.mail.message.ModifyContactRequest.class,
            org.zmail.soap.mail.message.ModifyContactResponse.class,
            org.zmail.soap.mail.message.ModifyDataSourceRequest.class,
            org.zmail.soap.mail.message.ModifyDataSourceResponse.class,
            org.zmail.soap.mail.message.ModifyFilterRulesRequest.class,
            org.zmail.soap.mail.message.ModifyFilterRulesResponse.class,
            org.zmail.soap.mail.message.ModifyMailboxMetadataRequest.class,
            org.zmail.soap.mail.message.ModifyMailboxMetadataResponse.class,
            org.zmail.soap.mail.message.ModifyOutgoingFilterRulesRequest.class,
            org.zmail.soap.mail.message.ModifyOutgoingFilterRulesResponse.class,
            org.zmail.soap.mail.message.ModifySearchFolderRequest.class,
            org.zmail.soap.mail.message.ModifySearchFolderResponse.class,
            org.zmail.soap.mail.message.ModifyTaskRequest.class,
            org.zmail.soap.mail.message.ModifyTaskResponse.class,
            org.zmail.soap.mail.message.MsgActionRequest.class,
            org.zmail.soap.mail.message.MsgActionResponse.class,
            org.zmail.soap.mail.message.NoOpRequest.class,
            org.zmail.soap.mail.message.NoOpResponse.class,
            org.zmail.soap.mail.message.NoteActionRequest.class,
            org.zmail.soap.mail.message.NoteActionResponse.class,
            org.zmail.soap.mail.message.PurgeRevisionRequest.class,
            org.zmail.soap.mail.message.PurgeRevisionResponse.class,
            org.zmail.soap.mail.message.RankingActionRequest.class,
            org.zmail.soap.mail.message.RankingActionResponse.class,
            org.zmail.soap.mail.message.RegisterDeviceRequest.class,
            org.zmail.soap.mail.message.RegisterDeviceResponse.class,
            org.zmail.soap.mail.message.RemoveAttachmentsRequest.class,
            org.zmail.soap.mail.message.RemoveAttachmentsResponse.class,
            org.zmail.soap.mail.message.RevokePermissionRequest.class,
            org.zmail.soap.mail.message.RevokePermissionResponse.class,
            org.zmail.soap.mail.message.SaveDocumentRequest.class,
            org.zmail.soap.mail.message.SaveDocumentResponse.class,
            org.zmail.soap.mail.message.SaveDraftRequest.class,
            org.zmail.soap.mail.message.SaveDraftResponse.class,
            org.zmail.soap.mail.message.SearchConvRequest.class,
            org.zmail.soap.mail.message.SearchConvResponse.class,
            org.zmail.soap.mail.message.SearchRequest.class,
            org.zmail.soap.mail.message.SearchResponse.class,
            org.zmail.soap.mail.message.SendDeliveryReportRequest.class,
            org.zmail.soap.mail.message.SendDeliveryReportResponse.class,
            org.zmail.soap.mail.message.SendInviteReplyRequest.class,
            org.zmail.soap.mail.message.SendInviteReplyResponse.class,
            org.zmail.soap.mail.message.SendMsgRequest.class,
            org.zmail.soap.mail.message.SendMsgResponse.class,
            org.zmail.soap.mail.message.SendShareNotificationRequest.class,
            org.zmail.soap.mail.message.SendShareNotificationResponse.class,
            org.zmail.soap.mail.message.SendVerificationCodeRequest.class,
            org.zmail.soap.mail.message.SendVerificationCodeResponse.class,
            org.zmail.soap.mail.message.SetAppointmentRequest.class,
            org.zmail.soap.mail.message.SetAppointmentResponse.class,
            org.zmail.soap.mail.message.SetCustomMetadataRequest.class,
            org.zmail.soap.mail.message.SetCustomMetadataResponse.class,
            org.zmail.soap.mail.message.SetMailboxMetadataRequest.class,
            org.zmail.soap.mail.message.SetMailboxMetadataResponse.class,
            org.zmail.soap.mail.message.SetTaskRequest.class,
            org.zmail.soap.mail.message.SetTaskResponse.class,
            org.zmail.soap.mail.message.SnoozeCalendarItemAlarmRequest.class,
            org.zmail.soap.mail.message.SnoozeCalendarItemAlarmResponse.class,
            org.zmail.soap.mail.message.SyncRequest.class,
            org.zmail.soap.mail.message.SyncResponse.class,
            org.zmail.soap.mail.message.TagActionRequest.class,
            org.zmail.soap.mail.message.TagActionResponse.class,
            org.zmail.soap.mail.message.TestDataSourceRequest.class,
            org.zmail.soap.mail.message.TestDataSourceResponse.class,
            org.zmail.soap.mail.message.UpdateDeviceStatusRequest.class,
            org.zmail.soap.mail.message.UpdateDeviceStatusResponse.class,
            org.zmail.soap.mail.message.VerifyCodeRequest.class,
            org.zmail.soap.mail.message.VerifyCodeResponse.class,
            org.zmail.soap.mail.message.WaitSetRequest.class,
            org.zmail.soap.mail.message.WaitSetResponse.class,

            // zmailAdmin
            org.zmail.soap.admin.message.AbortHsmRequest.class,
            org.zmail.soap.admin.message.AbortHsmResponse.class,
            org.zmail.soap.admin.message.AbortXMbxSearchRequest.class,
            org.zmail.soap.admin.message.AbortXMbxSearchResponse.class,
            org.zmail.soap.admin.message.ActivateLicenseRequest.class,
            org.zmail.soap.admin.message.ActivateLicenseResponse.class,
            org.zmail.soap.admin.message.AddAccountAliasRequest.class,
            org.zmail.soap.admin.message.AddAccountAliasResponse.class,
            org.zmail.soap.admin.message.AddAccountLoggerRequest.class,
            org.zmail.soap.admin.message.AddAccountLoggerResponse.class,
            org.zmail.soap.admin.message.AddDistributionListAliasRequest.class,
            org.zmail.soap.admin.message.AddDistributionListAliasResponse.class,
            org.zmail.soap.admin.message.AddDistributionListMemberRequest.class,
            org.zmail.soap.admin.message.AddDistributionListMemberResponse.class,
            org.zmail.soap.admin.message.AddGalSyncDataSourceRequest.class,
            org.zmail.soap.admin.message.AddGalSyncDataSourceResponse.class,
            org.zmail.soap.admin.message.AdminCreateWaitSetRequest.class,
            org.zmail.soap.admin.message.AdminCreateWaitSetResponse.class,
            org.zmail.soap.admin.message.AdminDestroyWaitSetRequest.class,
            org.zmail.soap.admin.message.AdminDestroyWaitSetResponse.class,
            org.zmail.soap.admin.message.AdminWaitSetRequest.class,
            org.zmail.soap.admin.message.AdminWaitSetResponse.class,
            org.zmail.soap.admin.message.AuthRequest.class,
            org.zmail.soap.admin.message.AuthResponse.class,
            org.zmail.soap.admin.message.AutoCompleteGalRequest.class,
            org.zmail.soap.admin.message.AutoCompleteGalResponse.class,
            org.zmail.soap.admin.message.AutoProvAccountRequest.class,
            org.zmail.soap.admin.message.AutoProvAccountResponse.class,
            org.zmail.soap.admin.message.AutoProvTaskControlRequest.class,
            org.zmail.soap.admin.message.AutoProvTaskControlResponse.class,
            org.zmail.soap.admin.message.BackupAccountQueryRequest.class,
            org.zmail.soap.admin.message.BackupAccountQueryResponse.class,
            org.zmail.soap.admin.message.BackupQueryRequest.class,
            org.zmail.soap.admin.message.BackupQueryResponse.class,
            org.zmail.soap.admin.message.BackupRequest.class,
            org.zmail.soap.admin.message.BackupResponse.class,
            org.zmail.soap.admin.message.CancelPendingRemoteWipeRequest.class,
            org.zmail.soap.admin.message.CancelPendingRemoteWipeResponse.class,
            org.zmail.soap.admin.message.CheckAuthConfigRequest.class,
            org.zmail.soap.admin.message.CheckAuthConfigResponse.class,
            org.zmail.soap.admin.message.CheckBlobConsistencyRequest.class,
            org.zmail.soap.admin.message.CheckBlobConsistencyResponse.class,
            org.zmail.soap.admin.message.CheckDirectoryRequest.class,
            org.zmail.soap.admin.message.CheckDirectoryResponse.class,
            org.zmail.soap.admin.message.CheckDomainMXRecordRequest.class,
            org.zmail.soap.admin.message.CheckDomainMXRecordResponse.class,
            org.zmail.soap.admin.message.CheckExchangeAuthRequest.class,
            org.zmail.soap.admin.message.CheckExchangeAuthResponse.class,
            org.zmail.soap.admin.message.CheckGalConfigRequest.class,
            org.zmail.soap.admin.message.CheckGalConfigResponse.class,
            org.zmail.soap.admin.message.CheckHealthRequest.class,
            org.zmail.soap.admin.message.CheckHealthResponse.class,
            org.zmail.soap.admin.message.CheckHostnameResolveRequest.class,
            org.zmail.soap.admin.message.CheckHostnameResolveResponse.class,
            org.zmail.soap.admin.message.CheckPasswordStrengthRequest.class,
            org.zmail.soap.admin.message.CheckPasswordStrengthResponse.class,
            org.zmail.soap.admin.message.CheckRightRequest.class,
            org.zmail.soap.admin.message.CheckRightResponse.class,
            org.zmail.soap.admin.message.ClearCookieRequest.class,
            org.zmail.soap.admin.message.ClearCookieResponse.class,
            org.zmail.soap.admin.message.CompactIndexRequest.class,
            org.zmail.soap.admin.message.CompactIndexResponse.class,
            org.zmail.soap.admin.message.ComputeAggregateQuotaUsageRequest.class,
            org.zmail.soap.admin.message.ComputeAggregateQuotaUsageResponse.class,
            org.zmail.soap.admin.message.ConfigureZimletRequest.class,
            org.zmail.soap.admin.message.ConfigureZimletResponse.class,
            org.zmail.soap.admin.message.CopyCosRequest.class,
            org.zmail.soap.admin.message.CopyCosResponse.class,
            org.zmail.soap.admin.message.CountAccountRequest.class,
            org.zmail.soap.admin.message.CountAccountResponse.class,
            org.zmail.soap.admin.message.CountObjectsRequest.class,
            org.zmail.soap.admin.message.CountObjectsResponse.class,
            org.zmail.soap.admin.message.CreateAccountRequest.class,
            org.zmail.soap.admin.message.CreateAccountResponse.class,
            org.zmail.soap.admin.message.CreateArchiveRequest.class,
            org.zmail.soap.admin.message.CreateArchiveResponse.class,
            org.zmail.soap.admin.message.CreateCalendarResourceRequest.class,
            org.zmail.soap.admin.message.CreateCalendarResourceResponse.class,
            org.zmail.soap.admin.message.CreateCosRequest.class,
            org.zmail.soap.admin.message.CreateCosResponse.class,
            org.zmail.soap.admin.message.CreateDataSourceRequest.class,
            org.zmail.soap.admin.message.CreateDataSourceResponse.class,
            org.zmail.soap.admin.message.CreateDistributionListRequest.class,
            org.zmail.soap.admin.message.CreateDistributionListResponse.class,
            org.zmail.soap.admin.message.CreateDomainRequest.class,
            org.zmail.soap.admin.message.CreateDomainResponse.class,
            org.zmail.soap.admin.message.CreateGalSyncAccountRequest.class,
            org.zmail.soap.admin.message.CreateGalSyncAccountResponse.class,
            org.zmail.soap.admin.message.CreateLDAPEntryRequest.class,
            org.zmail.soap.admin.message.CreateLDAPEntryResponse.class,
            org.zmail.soap.admin.message.CreateServerRequest.class,
            org.zmail.soap.admin.message.CreateServerResponse.class,
            org.zmail.soap.admin.message.CreateSystemRetentionPolicyRequest.class,
            org.zmail.soap.admin.message.CreateSystemRetentionPolicyResponse.class,
            org.zmail.soap.admin.message.CreateUCServiceRequest.class,
            org.zmail.soap.admin.message.CreateUCServiceResponse.class,
            org.zmail.soap.admin.message.CreateVolumeRequest.class,
            org.zmail.soap.admin.message.CreateVolumeResponse.class,
            org.zmail.soap.admin.message.CreateXMPPComponentRequest.class,
            org.zmail.soap.admin.message.CreateXMPPComponentResponse.class,
            org.zmail.soap.admin.message.CreateXMbxSearchRequest.class,
            org.zmail.soap.admin.message.CreateXMbxSearchResponse.class,
            org.zmail.soap.admin.message.CreateZimletRequest.class,
            org.zmail.soap.admin.message.CreateZimletResponse.class,
            org.zmail.soap.admin.message.DedupeBlobsRequest.class,
            org.zmail.soap.admin.message.DedupeBlobsResponse.class,
            org.zmail.soap.admin.message.DelegateAuthRequest.class,
            org.zmail.soap.admin.message.DelegateAuthResponse.class,
            org.zmail.soap.admin.message.DeleteAccountRequest.class,
            org.zmail.soap.admin.message.DeleteAccountResponse.class,
            org.zmail.soap.admin.message.DeleteCalendarResourceRequest.class,
            org.zmail.soap.admin.message.DeleteCalendarResourceResponse.class,
            org.zmail.soap.admin.message.DeleteCosRequest.class,
            org.zmail.soap.admin.message.DeleteCosResponse.class,
            org.zmail.soap.admin.message.DeleteDataSourceRequest.class,
            org.zmail.soap.admin.message.DeleteDataSourceResponse.class,
            org.zmail.soap.admin.message.DeleteDistributionListRequest.class,
            org.zmail.soap.admin.message.DeleteDistributionListResponse.class,
            org.zmail.soap.admin.message.DeleteDomainRequest.class,
            org.zmail.soap.admin.message.DeleteDomainResponse.class,
            org.zmail.soap.admin.message.DeleteGalSyncAccountRequest.class,
            org.zmail.soap.admin.message.DeleteGalSyncAccountResponse.class,
            org.zmail.soap.admin.message.DeleteLDAPEntryRequest.class,
            org.zmail.soap.admin.message.DeleteLDAPEntryResponse.class,
            org.zmail.soap.admin.message.DeleteMailboxRequest.class,
            org.zmail.soap.admin.message.DeleteMailboxResponse.class,
            org.zmail.soap.admin.message.DeleteServerRequest.class,
            org.zmail.soap.admin.message.DeleteServerResponse.class,
            org.zmail.soap.admin.message.DeleteSystemRetentionPolicyRequest.class,
            org.zmail.soap.admin.message.DeleteSystemRetentionPolicyResponse.class,
            org.zmail.soap.admin.message.DeleteUCServiceRequest.class,
            org.zmail.soap.admin.message.DeleteUCServiceResponse.class,
            org.zmail.soap.admin.message.DeleteVolumeRequest.class,
            org.zmail.soap.admin.message.DeleteVolumeResponse.class,
            org.zmail.soap.admin.message.DeleteXMPPComponentRequest.class,
            org.zmail.soap.admin.message.DeleteXMPPComponentResponse.class,
            org.zmail.soap.admin.message.DeleteXMbxSearchRequest.class,
            org.zmail.soap.admin.message.DeleteXMbxSearchResponse.class,
            org.zmail.soap.admin.message.DeleteZimletRequest.class,
            org.zmail.soap.admin.message.DeleteZimletResponse.class,
            org.zmail.soap.admin.message.DeployZimletRequest.class,
            org.zmail.soap.admin.message.DeployZimletResponse.class,
            org.zmail.soap.admin.message.DisableArchiveRequest.class,
            org.zmail.soap.admin.message.DisableArchiveResponse.class,
            org.zmail.soap.admin.message.DumpSessionsRequest.class,
            org.zmail.soap.admin.message.DumpSessionsResponse.class,
            org.zmail.soap.admin.message.EnableArchiveRequest.class,
            org.zmail.soap.admin.message.EnableArchiveResponse.class,
            org.zmail.soap.admin.message.ExportAndDeleteItemsRequest.class,
            org.zmail.soap.admin.message.ExportAndDeleteItemsResponse.class,
            org.zmail.soap.admin.message.ExportMailboxRequest.class,
            org.zmail.soap.admin.message.ExportMailboxResponse.class,
            org.zmail.soap.admin.message.FailoverClusterServiceRequest.class,
            org.zmail.soap.admin.message.FailoverClusterServiceResponse.class,
            org.zmail.soap.admin.message.FixCalendarEndTimeRequest.class,
            org.zmail.soap.admin.message.FixCalendarEndTimeResponse.class,
            org.zmail.soap.admin.message.FixCalendarPriorityRequest.class,
            org.zmail.soap.admin.message.FixCalendarPriorityResponse.class,
            org.zmail.soap.admin.message.FixCalendarTZRequest.class,
            org.zmail.soap.admin.message.FixCalendarTZResponse.class,
            org.zmail.soap.admin.message.FlushCacheRequest.class,
            org.zmail.soap.admin.message.FlushCacheResponse.class,
            org.zmail.soap.admin.message.GenCSRRequest.class,
            org.zmail.soap.admin.message.GenCSRResponse.class,
            org.zmail.soap.admin.message.GetAccountInfoRequest.class,
            org.zmail.soap.admin.message.GetAccountInfoResponse.class,
            org.zmail.soap.admin.message.GetAccountLoggersRequest.class,
            org.zmail.soap.admin.message.GetAccountLoggersResponse.class,
            org.zmail.soap.admin.message.GetAccountMembershipRequest.class,
            org.zmail.soap.admin.message.GetAccountMembershipResponse.class,
            org.zmail.soap.admin.message.GetAccountRequest.class,
            org.zmail.soap.admin.message.GetAccountResponse.class,
            org.zmail.soap.admin.message.GetAdminConsoleUICompRequest.class,
            org.zmail.soap.admin.message.GetAdminConsoleUICompResponse.class,
            org.zmail.soap.admin.message.GetAdminExtensionZimletsRequest.class,
            org.zmail.soap.admin.message.GetAdminExtensionZimletsResponse.class,
            org.zmail.soap.admin.message.GetAdminSavedSearchesRequest.class,
            org.zmail.soap.admin.message.GetAdminSavedSearchesResponse.class,
            org.zmail.soap.admin.message.GetAggregateQuotaUsageOnServerRequest.class,
            org.zmail.soap.admin.message.GetAggregateQuotaUsageOnServerResponse.class,
            org.zmail.soap.admin.message.GetAllAccountLoggersRequest.class,
            org.zmail.soap.admin.message.GetAllAccountLoggersResponse.class,
            org.zmail.soap.admin.message.GetAllAccountsRequest.class,
            org.zmail.soap.admin.message.GetAllAccountsResponse.class,
            org.zmail.soap.admin.message.GetAllAdminAccountsRequest.class,
            org.zmail.soap.admin.message.GetAllAdminAccountsResponse.class,
            org.zmail.soap.admin.message.GetAllCalendarResourcesRequest.class,
            org.zmail.soap.admin.message.GetAllCalendarResourcesResponse.class,
            org.zmail.soap.admin.message.GetAllConfigRequest.class,
            org.zmail.soap.admin.message.GetAllConfigResponse.class,
            org.zmail.soap.admin.message.GetAllCosRequest.class,
            org.zmail.soap.admin.message.GetAllCosResponse.class,
            org.zmail.soap.admin.message.GetAllDistributionListsRequest.class,
            org.zmail.soap.admin.message.GetAllDistributionListsResponse.class,
            org.zmail.soap.admin.message.GetAllDomainsRequest.class,
            org.zmail.soap.admin.message.GetAllDomainsResponse.class,
            org.zmail.soap.admin.message.GetAllEffectiveRightsRequest.class,
            org.zmail.soap.admin.message.GetAllEffectiveRightsResponse.class,
            org.zmail.soap.admin.message.GetAllFreeBusyProvidersRequest.class,
            org.zmail.soap.admin.message.GetAllFreeBusyProvidersResponse.class,
            org.zmail.soap.admin.message.GetAllLocalesRequest.class,
            org.zmail.soap.admin.message.GetAllLocalesResponse.class,
            org.zmail.soap.admin.message.GetAllMailboxesRequest.class,
            org.zmail.soap.admin.message.GetAllMailboxesResponse.class,
            org.zmail.soap.admin.message.GetAllRightsRequest.class,
            org.zmail.soap.admin.message.GetAllRightsResponse.class,
            org.zmail.soap.admin.message.GetAllServersRequest.class,
            org.zmail.soap.admin.message.GetAllServersResponse.class,
            org.zmail.soap.admin.message.GetAllSkinsRequest.class,
            org.zmail.soap.admin.message.GetAllSkinsResponse.class,
            org.zmail.soap.admin.message.GetAllUCProvidersRequest.class,
            org.zmail.soap.admin.message.GetAllUCProvidersResponse.class,
            org.zmail.soap.admin.message.GetAllUCServicesRequest.class,
            org.zmail.soap.admin.message.GetAllUCServicesResponse.class,
            org.zmail.soap.admin.message.GetAllVolumesRequest.class,
            org.zmail.soap.admin.message.GetAllVolumesResponse.class,
            org.zmail.soap.admin.message.GetAllXMPPComponentsRequest.class,
            org.zmail.soap.admin.message.GetAllXMPPComponentsResponse.class,
            org.zmail.soap.admin.message.GetAllZimletsRequest.class,
            org.zmail.soap.admin.message.GetAllZimletsResponse.class,
            org.zmail.soap.admin.message.GetApplianceHSMFSRequest.class,
            org.zmail.soap.admin.message.GetApplianceHSMFSResponse.class,
            org.zmail.soap.admin.message.GetAttributeInfoRequest.class,
            org.zmail.soap.admin.message.GetAttributeInfoResponse.class,
            org.zmail.soap.admin.message.GetCSRRequest.class,
            org.zmail.soap.admin.message.GetCSRResponse.class,
            org.zmail.soap.admin.message.GetCalendarResourceRequest.class,
            org.zmail.soap.admin.message.GetCalendarResourceResponse.class,
            org.zmail.soap.admin.message.GetCertRequest.class,
            org.zmail.soap.admin.message.GetCertResponse.class,
            org.zmail.soap.admin.message.GetClusterStatusRequest.class,
            org.zmail.soap.admin.message.GetClusterStatusResponse.class,
            org.zmail.soap.admin.message.GetConfigRequest.class,
            org.zmail.soap.admin.message.GetConfigResponse.class,
            org.zmail.soap.admin.message.GetCosRequest.class,
            org.zmail.soap.admin.message.GetCosResponse.class,
            org.zmail.soap.admin.message.GetCreateObjectAttrsRequest.class,
            org.zmail.soap.admin.message.GetCreateObjectAttrsResponse.class,
            org.zmail.soap.admin.message.GetCurrentVolumesRequest.class,
            org.zmail.soap.admin.message.GetCurrentVolumesResponse.class,
            org.zmail.soap.admin.message.GetDataSourcesRequest.class,
            org.zmail.soap.admin.message.GetDataSourcesResponse.class,
            org.zmail.soap.admin.message.GetDelegatedAdminConstraintsRequest.class,
            org.zmail.soap.admin.message.GetDelegatedAdminConstraintsResponse.class,
            org.zmail.soap.admin.message.GetDeviceStatusRequest.class,
            org.zmail.soap.admin.message.GetDeviceStatusResponse.class,
            org.zmail.soap.admin.message.GetDevicesCountRequest.class,
            org.zmail.soap.admin.message.GetDevicesCountResponse.class,
            org.zmail.soap.admin.message.GetDevicesCountSinceLastUsedRequest.class,
            org.zmail.soap.admin.message.GetDevicesCountSinceLastUsedResponse.class,
            org.zmail.soap.admin.message.GetDevicesCountUsedTodayRequest.class,
            org.zmail.soap.admin.message.GetDevicesCountUsedTodayResponse.class,
            org.zmail.soap.admin.message.GetDevicesRequest.class,
            org.zmail.soap.admin.message.GetDevicesResponse.class,
            org.zmail.soap.admin.message.GetDistributionListMembershipRequest.class,
            org.zmail.soap.admin.message.GetDistributionListMembershipResponse.class,
            org.zmail.soap.admin.message.GetDistributionListRequest.class,
            org.zmail.soap.admin.message.GetDistributionListResponse.class,
            org.zmail.soap.admin.message.GetDomainInfoRequest.class,
            org.zmail.soap.admin.message.GetDomainInfoResponse.class,
            org.zmail.soap.admin.message.GetDomainRequest.class,
            org.zmail.soap.admin.message.GetDomainResponse.class,
            org.zmail.soap.admin.message.GetEffectiveRightsRequest.class,
            org.zmail.soap.admin.message.GetEffectiveRightsResponse.class,
            org.zmail.soap.admin.message.GetFreeBusyQueueInfoRequest.class,
            org.zmail.soap.admin.message.GetFreeBusyQueueInfoResponse.class,
            org.zmail.soap.admin.message.GetGrantsRequest.class,
            org.zmail.soap.admin.message.GetGrantsResponse.class,
            org.zmail.soap.admin.message.GetHsmStatusRequest.class,
            org.zmail.soap.admin.message.GetHsmStatusResponse.class,
            org.zmail.soap.admin.message.GetIndexStatsRequest.class,
            org.zmail.soap.admin.message.GetIndexStatsResponse.class,
            org.zmail.soap.admin.message.GetLDAPEntriesRequest.class,
            org.zmail.soap.admin.message.GetLDAPEntriesResponse.class,
            org.zmail.soap.admin.message.GetLicenseInfoRequest.class,
            org.zmail.soap.admin.message.GetLicenseInfoResponse.class,
            org.zmail.soap.admin.message.GetLicenseRequest.class,
            org.zmail.soap.admin.message.GetLicenseResponse.class,
            org.zmail.soap.admin.message.GetLoggerStatsRequest.class,
            org.zmail.soap.admin.message.GetLoggerStatsResponse.class,
            org.zmail.soap.admin.message.GetMailQueueInfoRequest.class,
            org.zmail.soap.admin.message.GetMailQueueInfoResponse.class,
            org.zmail.soap.admin.message.GetMailQueueRequest.class,
            org.zmail.soap.admin.message.GetMailQueueResponse.class,
            org.zmail.soap.admin.message.GetMailboxRequest.class,
            org.zmail.soap.admin.message.GetMailboxResponse.class,
            org.zmail.soap.admin.message.GetMailboxStatsRequest.class,
            org.zmail.soap.admin.message.GetMailboxStatsResponse.class,
            org.zmail.soap.admin.message.GetMailboxVersionRequest.class,
            org.zmail.soap.admin.message.GetMailboxVersionResponse.class,
            org.zmail.soap.admin.message.GetMailboxVolumesRequest.class,
            org.zmail.soap.admin.message.GetMailboxVolumesResponse.class,
            org.zmail.soap.admin.message.GetMemcachedClientConfigRequest.class,
            org.zmail.soap.admin.message.GetMemcachedClientConfigResponse.class,
            org.zmail.soap.admin.message.GetQuotaUsageRequest.class,
            org.zmail.soap.admin.message.GetQuotaUsageResponse.class,
            org.zmail.soap.admin.message.GetRightRequest.class,
            org.zmail.soap.admin.message.GetRightResponse.class,
            org.zmail.soap.admin.message.GetRightsDocRequest.class,
            org.zmail.soap.admin.message.GetRightsDocResponse.class,
            org.zmail.soap.admin.message.GetSMIMEConfigRequest.class,
            org.zmail.soap.admin.message.GetSMIMEConfigResponse.class,
            org.zmail.soap.admin.message.GetServerNIfsRequest.class,
            org.zmail.soap.admin.message.GetServerNIfsResponse.class,
            org.zmail.soap.admin.message.GetServerRequest.class,
            org.zmail.soap.admin.message.GetServerResponse.class,
            org.zmail.soap.admin.message.GetServerStatsRequest.class,
            org.zmail.soap.admin.message.GetServerStatsResponse.class,
            org.zmail.soap.admin.message.GetServiceStatusRequest.class,
            org.zmail.soap.admin.message.GetServiceStatusResponse.class,
            org.zmail.soap.admin.message.GetSessionsRequest.class,
            org.zmail.soap.admin.message.GetSessionsResponse.class,
            org.zmail.soap.admin.message.GetShareInfoRequest.class,
            org.zmail.soap.admin.message.GetShareInfoResponse.class,
            org.zmail.soap.admin.message.GetSystemRetentionPolicyRequest.class,
            org.zmail.soap.admin.message.GetSystemRetentionPolicyResponse.class,
            org.zmail.soap.admin.message.GetUCServiceRequest.class,
            org.zmail.soap.admin.message.GetUCServiceResponse.class,
            org.zmail.soap.admin.message.GetVersionInfoRequest.class,
            org.zmail.soap.admin.message.GetVersionInfoResponse.class,
            org.zmail.soap.admin.message.GetVolumeRequest.class,
            org.zmail.soap.admin.message.GetVolumeResponse.class,
            org.zmail.soap.admin.message.GetXMPPComponentRequest.class,
            org.zmail.soap.admin.message.GetXMPPComponentResponse.class,
            org.zmail.soap.admin.message.GetXMbxSearchesListRequest.class,
            org.zmail.soap.admin.message.GetXMbxSearchesListResponse.class,
            org.zmail.soap.admin.message.GetZimletRequest.class,
            org.zmail.soap.admin.message.GetZimletResponse.class,
            org.zmail.soap.admin.message.GetZimletStatusRequest.class,
            org.zmail.soap.admin.message.GetZimletStatusResponse.class,
            org.zmail.soap.admin.message.GrantRightRequest.class,
            org.zmail.soap.admin.message.GrantRightResponse.class,
            org.zmail.soap.admin.message.HsmRequest.class,
            org.zmail.soap.admin.message.HsmResponse.class,
            org.zmail.soap.admin.message.InstallCertRequest.class,
            org.zmail.soap.admin.message.InstallCertResponse.class,
            org.zmail.soap.admin.message.InstallLicenseRequest.class,
            org.zmail.soap.admin.message.InstallLicenseResponse.class,
            org.zmail.soap.admin.message.MailQueueActionRequest.class,
            org.zmail.soap.admin.message.MailQueueActionResponse.class,
            org.zmail.soap.admin.message.MailQueueFlushRequest.class,
            org.zmail.soap.admin.message.MailQueueFlushResponse.class,
            org.zmail.soap.admin.message.MigrateAccountRequest.class,
            org.zmail.soap.admin.message.MigrateAccountResponse.class,
            org.zmail.soap.admin.message.ModifyAccountRequest.class,
            org.zmail.soap.admin.message.ModifyAccountResponse.class,
            org.zmail.soap.admin.message.ModifyAdminSavedSearchesRequest.class,
            org.zmail.soap.admin.message.ModifyAdminSavedSearchesResponse.class,
            org.zmail.soap.admin.message.ModifyCalendarResourceRequest.class,
            org.zmail.soap.admin.message.ModifyCalendarResourceResponse.class,
            org.zmail.soap.admin.message.ModifyConfigRequest.class,
            org.zmail.soap.admin.message.ModifyConfigResponse.class,
            org.zmail.soap.admin.message.ModifyCosRequest.class,
            org.zmail.soap.admin.message.ModifyCosResponse.class,
            org.zmail.soap.admin.message.ModifyDataSourceRequest.class,
            org.zmail.soap.admin.message.ModifyDataSourceResponse.class,
            org.zmail.soap.admin.message.ModifyDelegatedAdminConstraintsRequest.class,
            org.zmail.soap.admin.message.ModifyDelegatedAdminConstraintsResponse.class,
            org.zmail.soap.admin.message.ModifyDistributionListRequest.class,
            org.zmail.soap.admin.message.ModifyDistributionListResponse.class,
            org.zmail.soap.admin.message.ModifyDomainRequest.class,
            org.zmail.soap.admin.message.ModifyDomainResponse.class,
            org.zmail.soap.admin.message.ModifyLDAPEntryRequest.class,
            org.zmail.soap.admin.message.ModifyLDAPEntryResponse.class,
            org.zmail.soap.admin.message.ModifySMIMEConfigRequest.class,
            org.zmail.soap.admin.message.ModifySMIMEConfigResponse.class,
            org.zmail.soap.admin.message.ModifyServerRequest.class,
            org.zmail.soap.admin.message.ModifyServerResponse.class,
            org.zmail.soap.admin.message.ModifySystemRetentionPolicyRequest.class,
            org.zmail.soap.admin.message.ModifySystemRetentionPolicyResponse.class,
            org.zmail.soap.admin.message.ModifyUCServiceRequest.class,
            org.zmail.soap.admin.message.ModifyUCServiceResponse.class,
            org.zmail.soap.admin.message.ModifyVolumeRequest.class,
            org.zmail.soap.admin.message.ModifyVolumeResponse.class,
            org.zmail.soap.admin.message.ModifyZimletRequest.class,
            org.zmail.soap.admin.message.ModifyZimletResponse.class,
            org.zmail.soap.admin.message.MoveBlobsRequest.class,
            org.zmail.soap.admin.message.MoveBlobsResponse.class,
            org.zmail.soap.admin.message.MoveMailboxRequest.class,
            org.zmail.soap.admin.message.MoveMailboxResponse.class,
            org.zmail.soap.admin.message.NoOpRequest.class,
            org.zmail.soap.admin.message.NoOpResponse.class,
            org.zmail.soap.admin.message.PingRequest.class,
            org.zmail.soap.admin.message.PingResponse.class,
            org.zmail.soap.admin.message.PurgeAccountCalendarCacheRequest.class,
            org.zmail.soap.admin.message.PurgeAccountCalendarCacheResponse.class,
            org.zmail.soap.admin.message.PurgeFreeBusyQueueRequest.class,
            org.zmail.soap.admin.message.PurgeFreeBusyQueueResponse.class,
            org.zmail.soap.admin.message.PurgeMessagesRequest.class,
            org.zmail.soap.admin.message.PurgeMessagesResponse.class,
            org.zmail.soap.admin.message.PurgeMovedMailboxRequest.class,
            org.zmail.soap.admin.message.PurgeMovedMailboxResponse.class,
            org.zmail.soap.admin.message.PushFreeBusyRequest.class,
            org.zmail.soap.admin.message.PushFreeBusyResponse.class,
            org.zmail.soap.admin.message.QueryMailboxMoveRequest.class,
            org.zmail.soap.admin.message.QueryMailboxMoveResponse.class,
            org.zmail.soap.admin.message.QueryWaitSetRequest.class,
            org.zmail.soap.admin.message.QueryWaitSetResponse.class,
            org.zmail.soap.admin.message.ReIndexRequest.class,
            org.zmail.soap.admin.message.ReIndexResponse.class,
            org.zmail.soap.admin.message.RecalculateMailboxCountsRequest.class,
            org.zmail.soap.admin.message.RecalculateMailboxCountsResponse.class,
            org.zmail.soap.admin.message.RegisterMailboxMoveOutRequest.class,
            org.zmail.soap.admin.message.RegisterMailboxMoveOutResponse.class,
            org.zmail.soap.admin.message.ReloadAccountRequest.class,
            org.zmail.soap.admin.message.ReloadAccountResponse.class,
            org.zmail.soap.admin.message.ReloadLocalConfigRequest.class,
            org.zmail.soap.admin.message.ReloadLocalConfigResponse.class,
            org.zmail.soap.admin.message.ReloadMemcachedClientConfigRequest.class,
            org.zmail.soap.admin.message.ReloadMemcachedClientConfigResponse.class,
            org.zmail.soap.admin.message.RemoteWipeRequest.class,
            org.zmail.soap.admin.message.RemoteWipeResponse.class,
            org.zmail.soap.admin.message.RemoveAccountAliasRequest.class,
            org.zmail.soap.admin.message.RemoveAccountAliasResponse.class,
            org.zmail.soap.admin.message.RemoveAccountLoggerRequest.class,
            org.zmail.soap.admin.message.RemoveAccountLoggerResponse.class,
            org.zmail.soap.admin.message.RemoveDeviceRequest.class,
            org.zmail.soap.admin.message.RemoveDeviceResponse.class,
            org.zmail.soap.admin.message.RemoveDistributionListAliasRequest.class,
            org.zmail.soap.admin.message.RemoveDistributionListAliasResponse.class,
            org.zmail.soap.admin.message.RemoveDistributionListMemberRequest.class,
            org.zmail.soap.admin.message.RemoveDistributionListMemberResponse.class,
            org.zmail.soap.admin.message.RenameAccountRequest.class,
            org.zmail.soap.admin.message.RenameAccountResponse.class,
            org.zmail.soap.admin.message.RenameCalendarResourceRequest.class,
            org.zmail.soap.admin.message.RenameCalendarResourceResponse.class,
            org.zmail.soap.admin.message.RenameCosRequest.class,
            org.zmail.soap.admin.message.RenameCosResponse.class,
            org.zmail.soap.admin.message.RenameDistributionListRequest.class,
            org.zmail.soap.admin.message.RenameDistributionListResponse.class,
            org.zmail.soap.admin.message.RenameLDAPEntryRequest.class,
            org.zmail.soap.admin.message.RenameLDAPEntryResponse.class,
            org.zmail.soap.admin.message.RenameUCServiceRequest.class,
            org.zmail.soap.admin.message.RenameUCServiceResponse.class,
            org.zmail.soap.admin.message.ResetAllLoggersRequest.class,
            org.zmail.soap.admin.message.ResetAllLoggersResponse.class,
            org.zmail.soap.admin.message.RestoreRequest.class,
            org.zmail.soap.admin.message.RestoreResponse.class,
            org.zmail.soap.admin.message.ResumeDeviceRequest.class,
            org.zmail.soap.admin.message.ResumeDeviceResponse.class,
            org.zmail.soap.admin.message.RevokeRightRequest.class,
            org.zmail.soap.admin.message.RevokeRightResponse.class,
            org.zmail.soap.admin.message.RolloverRedoLogRequest.class,
            org.zmail.soap.admin.message.RolloverRedoLogResponse.class,
            org.zmail.soap.admin.message.RunUnitTestsRequest.class,
            org.zmail.soap.admin.message.RunUnitTestsResponse.class,
            org.zmail.soap.admin.message.ScheduleBackupsRequest.class,
            org.zmail.soap.admin.message.ScheduleBackupsResponse.class,
            org.zmail.soap.admin.message.SearchAccountsRequest.class,
            org.zmail.soap.admin.message.SearchAccountsResponse.class,
            org.zmail.soap.admin.message.SearchAutoProvDirectoryRequest.class,
            org.zmail.soap.admin.message.SearchAutoProvDirectoryResponse.class,
            org.zmail.soap.admin.message.SearchCalendarResourcesRequest.class,
            org.zmail.soap.admin.message.SearchCalendarResourcesResponse.class,
            org.zmail.soap.admin.message.SearchDirectoryRequest.class,
            org.zmail.soap.admin.message.SearchDirectoryResponse.class,
            org.zmail.soap.admin.message.SearchGalRequest.class,
            org.zmail.soap.admin.message.SearchGalResponse.class,
            org.zmail.soap.admin.message.SearchMultiMailboxRequest.class,
            org.zmail.soap.admin.message.SearchMultiMailboxResponse.class,
            org.zmail.soap.admin.message.SetCurrentVolumeRequest.class,
            org.zmail.soap.admin.message.SetCurrentVolumeResponse.class,
            org.zmail.soap.admin.message.SetPasswordRequest.class,
            org.zmail.soap.admin.message.SetPasswordResponse.class,
            org.zmail.soap.admin.message.SuspendDeviceRequest.class,
            org.zmail.soap.admin.message.SuspendDeviceResponse.class,
            org.zmail.soap.admin.message.SyncGalAccountRequest.class,
            org.zmail.soap.admin.message.SyncGalAccountResponse.class,
            org.zmail.soap.admin.message.UndeployZimletRequest.class,
            org.zmail.soap.admin.message.UndeployZimletResponse.class,
            org.zmail.soap.admin.message.UnloadMailboxRequest.class,
            org.zmail.soap.admin.message.UnloadMailboxResponse.class,
            org.zmail.soap.admin.message.UnregisterMailboxMoveOutRequest.class,
            org.zmail.soap.admin.message.UnregisterMailboxMoveOutResponse.class,
            org.zmail.soap.admin.message.UpdateDeviceStatusRequest.class,
            org.zmail.soap.admin.message.UpdateDeviceStatusResponse.class,
            org.zmail.soap.admin.message.UpdatePresenceSessionIdRequest.class,
            org.zmail.soap.admin.message.UpdatePresenceSessionIdResponse.class,
            org.zmail.soap.admin.message.UploadDomCertRequest.class,
            org.zmail.soap.admin.message.UploadDomCertResponse.class,
            org.zmail.soap.admin.message.UploadProxyCARequest.class,
            org.zmail.soap.admin.message.UploadProxyCAResponse.class,
            org.zmail.soap.admin.message.VerifyCertKeyRequest.class,
            org.zmail.soap.admin.message.VerifyCertKeyResponse.class,
            org.zmail.soap.admin.message.VerifyIndexRequest.class,
            org.zmail.soap.admin.message.VerifyIndexResponse.class,
            org.zmail.soap.admin.message.VerifyStoreManagerRequest.class,
            org.zmail.soap.admin.message.VerifyStoreManagerResponse.class,
            org.zmail.soap.admin.message.VersionCheckRequest.class,
            org.zmail.soap.admin.message.VersionCheckResponse.class,

            // zmailAdminExt
            org.zmail.soap.adminext.message.BulkIMAPDataImportRequest.class,
            org.zmail.soap.adminext.message.BulkIMAPDataImportResponse.class,
            org.zmail.soap.adminext.message.BulkImportAccountsRequest.class,
            org.zmail.soap.adminext.message.BulkImportAccountsResponse.class,
            org.zmail.soap.adminext.message.GenerateBulkProvisionFileFromLDAPRequest.class,
            org.zmail.soap.adminext.message.GenerateBulkProvisionFileFromLDAPResponse.class,
            org.zmail.soap.adminext.message.GetBulkIMAPImportTaskListRequest.class,
            org.zmail.soap.adminext.message.GetBulkIMAPImportTaskListResponse.class,
            org.zmail.soap.adminext.message.PurgeBulkIMAPImportTasksRequest.class,
            org.zmail.soap.adminext.message.PurgeBulkIMAPImportTasksResponse.class,

            // zmailRepl
            org.zmail.soap.replication.message.BecomeMasterRequest.class,
            org.zmail.soap.replication.message.BecomeMasterResponse.class,
            org.zmail.soap.replication.message.BringDownServiceIPRequest.class,
            org.zmail.soap.replication.message.BringDownServiceIPResponse.class,
            org.zmail.soap.replication.message.BringUpServiceIPRequest.class,
            org.zmail.soap.replication.message.BringUpServiceIPResponse.class,
            org.zmail.soap.replication.message.ReplicationStatusRequest.class,
            org.zmail.soap.replication.message.ReplicationStatusResponse.class,
            org.zmail.soap.replication.message.StartCatchupRequest.class,
            org.zmail.soap.replication.message.StartCatchupResponse.class,
            org.zmail.soap.replication.message.StartFailoverClientRequest.class,
            org.zmail.soap.replication.message.StartFailoverClientResponse.class,
            org.zmail.soap.replication.message.StartFailoverDaemonRequest.class,
            org.zmail.soap.replication.message.StartFailoverDaemonResponse.class,
            org.zmail.soap.replication.message.StopFailoverClientRequest.class,
            org.zmail.soap.replication.message.StopFailoverClientResponse.class,
            org.zmail.soap.replication.message.StopFailoverDaemonRequest.class,
            org.zmail.soap.replication.message.StopFailoverDaemonResponse.class,

            // zmailSync
            org.zmail.soap.sync.message.CancelPendingRemoteWipeRequest.class,
            org.zmail.soap.sync.message.CancelPendingRemoteWipeResponse.class,
            org.zmail.soap.sync.message.GetDeviceStatusRequest.class,
            org.zmail.soap.sync.message.GetDeviceStatusResponse.class,
            org.zmail.soap.sync.message.RemoteWipeRequest.class,
            org.zmail.soap.sync.message.RemoteWipeResponse.class,
            org.zmail.soap.sync.message.RemoveDeviceRequest.class,
            org.zmail.soap.sync.message.RemoveDeviceResponse.class,
            org.zmail.soap.sync.message.ResumeDeviceRequest.class,
            org.zmail.soap.sync.message.ResumeDeviceResponse.class,
            org.zmail.soap.sync.message.SuspendDeviceRequest.class,
            org.zmail.soap.sync.message.SuspendDeviceResponse.class,

            // zmailVoice
            org.zmail.soap.voice.message.ChangeUCPasswordRequest.class,
            org.zmail.soap.voice.message.ChangeUCPasswordResponse.class,
            org.zmail.soap.voice.message.GetUCInfoRequest.class,
            org.zmail.soap.voice.message.GetUCInfoResponse.class,
            org.zmail.soap.voice.message.GetVoiceFeaturesRequest.class,
            org.zmail.soap.voice.message.GetVoiceFeaturesResponse.class,
            org.zmail.soap.voice.message.GetVoiceFolderRequest.class,
            org.zmail.soap.voice.message.GetVoiceFolderResponse.class,
            org.zmail.soap.voice.message.GetVoiceInfoRequest.class,
            org.zmail.soap.voice.message.GetVoiceInfoResponse.class,
            org.zmail.soap.voice.message.GetVoiceMailPrefsRequest.class,
            org.zmail.soap.voice.message.GetVoiceMailPrefsResponse.class,
            org.zmail.soap.voice.message.ModifyFromNumRequest.class,
            org.zmail.soap.voice.message.ModifyFromNumResponse.class,
            org.zmail.soap.voice.message.ModifyVoiceFeaturesRequest.class,
            org.zmail.soap.voice.message.ModifyVoiceFeaturesResponse.class,
            org.zmail.soap.voice.message.ModifyVoiceMailPinRequest.class,
            org.zmail.soap.voice.message.ModifyVoiceMailPinResponse.class,
            org.zmail.soap.voice.message.ModifyVoiceMailPrefsRequest.class,
            org.zmail.soap.voice.message.ModifyVoiceMailPrefsResponse.class,
            org.zmail.soap.voice.message.ResetVoiceFeaturesRequest.class,
            org.zmail.soap.voice.message.ResetVoiceFeaturesResponse.class,
            org.zmail.soap.voice.message.SearchVoiceRequest.class,
            org.zmail.soap.voice.message.SearchVoiceResponse.class,
            org.zmail.soap.voice.message.UploadVoiceMailRequest.class,
            org.zmail.soap.voice.message.UploadVoiceMailResponse.class,
            org.zmail.soap.voice.message.VoiceMsgActionRequest.class,
            org.zmail.soap.voice.message.VoiceMsgActionResponse.class
        };

        try {
            JAXB_CONTEXT = JAXBContext.newInstance(MESSAGE_CLASSES);
        } catch (JAXBException e) {
            throw new RuntimeException("Unable to initialize JAXB", e);
        }
    }

    private JaxbUtil() {
    }

    public static ImmutableList<Class<?>> getJaxbRequestAndResponseClasses() {
        return ImmutableList.<Class<?>>builder().add(MESSAGE_CLASSES).build();
    }

    /**
     * For use with JAXB Request or Response objects listed in {@code MESSAGE_CLASSES} only
     *
     * @param o - associated JAXB class.  <b>MUST</b> have an @XmlRootElement annotation
     * @param factory - e.g. XmlElement.mFactory or JSONElement.mFactory
     * @param removePrefixes - If true then remove namespace prefixes from unmarshalled XML.
     */
    public static Element jaxbToElement(Object o, Element.ElementFactory factory, boolean removePrefixes)
    throws ServiceException {
        return jaxbToElement(o, factory, removePrefixes, true /* useContextMarshaller */);
    }

    /**
     * JAXB marshaling creates XML which makes heavy use of namespace prefixes.  Historical Zmail SOAP XML
     * has generally not used them inside the SOAP body.  JAXB uses randomly assigned prefixes such as "ns2" which
     * makes the XML ugly and verbose.  If {@link removePrefixes} is set then all namespace prefix usage is
     * expunged from the XML.
     *
     * @param o - associated JAXB class.  <b>MUST</b> have an @XmlRootElement annotation
     * @param factory - e.g. XmlElement.mFactory or JSONElement.mFactory
     * @param removePrefixes - If true then remove namespace prefixes from unmarshalled XML.
     * @param useContextMarshaller - Set true if Object is a JAXB Request or Response listed in {@code MESSAGE_CLASSES}
     */
    public static Element jaxbToElement(Object o, Element.ElementFactory factory, boolean removePrefixes,
            boolean useContextMarshaller)
    throws ServiceException {
        if (o == null) {
            return null;
        }
        if (Element.JSONElement.mFactory.equals(factory)) {
            return JacksonUtil.jaxbToJSONElement(o);
        }
        try {
            Marshaller marshaller;
            if (useContextMarshaller) {
                marshaller = getContext().createMarshaller();
            } else {
                marshaller = createMarshaller(o.getClass());
            }
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DocumentResult dr = new DocumentResult();
            marshaller.marshal(o, dr);
            Document theDoc = dr.getDocument();
            org.dom4j.Element rootElem = theDoc.getRootElement();
            if (removePrefixes) {
                    JaxbUtil.removeNamespacePrefixes(rootElem);
            }
            return Element.convertDOM(rootElem, factory);
        } catch (Exception e) {
            throw ServiceException.FAILURE("Unable to convert " +
                    o.getClass().getName() + " to Element", e);
        }
    }

    /**
     * @param o - associated JAXB class must have an @XmlRootElement annotation
     * @param factory - e.g. XmlElement.mFactory or JSONElement.mFactory
     * @return
     * @throws ServiceException
     */
    public static Element jaxbToElement(Object o, Element.ElementFactory factory)
    throws ServiceException {
        return JaxbUtil.jaxbToElement(o, factory, true);
    }

    public static Element jaxbToElement(Object o) throws ServiceException {
        return jaxbToElement(o, XMLElement.mFactory);
    }

    /**
     * Use namespace inheritance in preference to prefixes
     * @param elem
     * @param defaultNs
     */
    private static void removeNamespacePrefixes(org.dom4j.Element elem) {
        Namespace elemNs = elem.getNamespace();
        if (elemNs != null) {
            if (! Strings.isNullOrEmpty(elemNs.getPrefix())) {
                Namespace newNs = Namespace.get(elemNs.getURI());
                org.dom4j.QName newQName = new org.dom4j.QName(elem.getName(), newNs);
                elem.setQName(newQName);
            }
        }
        Iterator<?> elemIter = elem.elementIterator();
        while (elemIter.hasNext()) {
            JaxbUtil.removeNamespacePrefixes((org.dom4j.Element) elemIter.next());
        }
    }

    private static JAXBContext getJaxbContext(Class<?> klass)
    throws JAXBException {
        JAXBContext jaxb = null;
        if (JaxbUtil.classJaxbContexts == null) {
            JaxbUtil.classJaxbContexts = Maps.newHashMap();
        }
        if (JaxbUtil.classJaxbContexts.containsKey(klass)) {
            jaxb = JaxbUtil.classJaxbContexts.get(klass);
        } else {
            jaxb = JAXBContext.newInstance(klass);
        }
        return jaxb;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Element jaxbToNamedElement(String name, String namespace, Object o, Element.ElementFactory factory)
    throws ServiceException {
        if (Element.JSONElement.mFactory.equals(factory)) {
            return JacksonUtil.jaxbToJSONElement(o, org.dom4j.QName.get(name, namespace));
        }
        try {
            Marshaller marshaller = createMarshaller(o.getClass());
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DocumentResult dr = new DocumentResult();
            marshaller.marshal(new JAXBElement(new QName(namespace, name),
                    o.getClass(), o) , dr);
            Document theDoc = dr.getDocument();
            org.dom4j.Element rootElem = theDoc.getRootElement();
            return Element.convertDOM(rootElem, factory);
        } catch (Exception e) {
            throw ServiceException.FAILURE("Unable to convert " +
                    o.getClass().getName() + " to Element", e);
        }
    }

    public static Element addChildElementFromJaxb(Element parent, String name, String namespace, Object o) {
        Element.ElementFactory factory;
        if (parent instanceof XMLElement) {
            factory = XMLElement.mFactory;
        } else {
            factory = JSONElement.mFactory;
        }
        Element child = null;
        try {
            child = jaxbToNamedElement(name, namespace, o, factory);
        } catch (ServiceException e) {
            ZmailLog.misc.info("JAXB Problem making " + name + " element", e);
        }
        parent.addElement(child);
        return child;
    }

    /**
     * This appears to be safe but is fairly slow.
     * Note that this method does NOT support Zmail's greater flexibility
     * for Xml structure.  Something similar to {@link fixupStructureForJaxb}
     * will be needed to add such support.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public static <T> T elementToJaxbUsingByteArray(Element e)
    throws ServiceException {
        try {
            Unmarshaller unmarshaller = getContext().createUnmarshaller();
            org.dom4j.Element rootElem = e.toXML();
            return (T) unmarshaller.unmarshal(new ByteArrayInputStream(
                    rootElem.asXML().getBytes(Charsets.UTF_8)));
        } catch (JAXBException ex) {
            throw ServiceException.FAILURE(
                    "Unable to unmarshal response for " + e.getName(), ex);
        }
    }

    /**
     * Method left in place to discourage future attempts to use dom4j as a basis for Element to JAXB - unless the
     * underlying issue is understood and resolved.
     * This sometimes fails badly whether e is a JSONElement or an XMLElement - get:
     *    "javax.xml.bind.UnmarshalException: Namespace URIs and local names to the unmarshaller needs to be interned."
     * and that seems to make the unmarshaller unstable from then on :-(
     * Note that this method does NOT support Zmail's greater flexibility for Xml structure.
     * Something similar to {@link fixupStructureForJaxb} would be needed to add such support.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public static <T> T elementToJaxbUsingDom4j(Element e)
    throws ServiceException {
        try {
            Unmarshaller unmarshaller = getContext().createUnmarshaller();
            org.dom4j.Element rootElem = e.toXML();
            DocumentSource docSrc = new DocumentSource(rootElem);
            return (T) unmarshaller.unmarshal(docSrc);
        } catch (JAXBException ex) {
            throw ServiceException.FAILURE(
                    "Unable to unmarshal response for " + e.getName(), ex);
        }
    }

    public static boolean isJaxbType(Class<?> klass) {
        if ( (klass == null) || klass.isPrimitive()) {
            return false;
        }
        return klass.getName().startsWith("org.zmail");
    }

    /**
     * Manipulates a structure under {@link elem} which obeys Zmail's SOAP XML structure rules to comply with more
     * stringent JAXB rules.
     * <ol>
     * <li>Zmail allows attributes to be specified as elements.
     * <p>One scenario where this happens - {@link XMLElement}'s {@code getAttribute(String key, String defaultValue)}
     * will look for an attribute with "key" as the name.
     * If it fails to find that, it looks for an element with "key" as the name and returns the elements text.</p></li>
     * <li>Zmail allows elements to be specified as attributes.
     * <p>One scenario where this happens.
     * <pre>
     *      elem.addAttribute("xml-elem-json-attr", "XML elem but JSON attribute", Element.Disposition.CONTENT);
     * </pre>
     * Will be serialized to this JSON (i.e. treated as an attribute in JSON) :
     * <pre>
     *       "xml-elem-json-attr": "XML elem but JSON attribute"
     * </pre>
     * or to this XML (i.e. treated as an element in XML) :
     * <pre>
     *       &lt;xml-elem-json-attr>XML elem but JSON attribute&lt;/xml-elem-json-attr>
     * </pre>
     * In JAXB, we typically use {@link XmlElement} for the associated field.  Round tripping from XML will result in
     * an element but round tripping from JSON will result in an attribute.
     * </ol>
     * @param klass is the JAXB class for {@code elem} which must be under the "org.zmail" package hierarchy.
     */
    private static void fixupStructureForJaxb(org.w3c.dom.Element elem, Class<?> klass) {
        if (elem == null) {
            return;
        }
        if (klass == null) {
            LOG.debug("JAXB no class associated with " + elem.getLocalName());
            return;
        }
        if (!isJaxbType(klass)) {
            return;
        }

        JaxbInfo jaxbInfo = JaxbInfo.getFromCache(klass);
        NamedNodeMap attrs = elem.getAttributes();
        int numAttrs = attrs.getLength();
        List<String> orphanAttrs = null;

        // Process each attribute
        for (int i=0; i<numAttrs; i++) {
            Attr attr = (Attr)attrs.item(i);
            // Get attribute name and value
            String attrName = attr.getNodeName();
            if (!jaxbInfo.hasAttribute(attrName) && jaxbInfo.hasElement(attrName)) {
                if (orphanAttrs == null) {
                    orphanAttrs = Lists.newArrayList();
                }
                orphanAttrs.add(attrName);
                String attrValue = attr.getNodeValue();
                elem.getNamespaceURI();
                org.w3c.dom.Element newElem = elem.getOwnerDocument().createElementNS(elem.getNamespaceURI(), attrName);
                newElem.setTextContent(attrValue);
                elem.appendChild(newElem);
            }
        }
        if (orphanAttrs != null) {
            for (String orphan : orphanAttrs) {
                attrs.removeNamedItem(orphan);
            }
        }

        NodeList list = elem.getChildNodes();
        List<org.w3c.dom.Element> orphans = null;
        for (int i=0; i < list.getLength(); i++) {
            Node subnode = list.item(i);
            if (subnode.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element child = (org.w3c.dom.Element) subnode;
                String childName = child.getLocalName();
                if (jaxbInfo.hasWrapperElement(childName)) {
                    NodeList wrappedList = child.getChildNodes();
                    for (int j=0; j < wrappedList.getLength(); j++) {
                        Node wSubnode = wrappedList.item(j);
                        if (wSubnode.getNodeType() == Node.ELEMENT_NODE) {
                            org.w3c.dom.Element wChild = (org.w3c.dom.Element) wSubnode;
                            fixupStructureForJaxb(wChild,
                                    jaxbInfo.getClassForWrappedElement(childName, wChild.getLocalName()));
                        }
                    }
                } else if (jaxbInfo.hasElement(childName))  {
                    fixupStructureForJaxb(child, jaxbInfo.getClassForElement(childName));
                } else if (jaxbInfo.hasAttribute(childName)) {
                    elem.setAttribute(childName, child.getTextContent());
                    // Don't remove pre-existing child until later pass to avoid changing the list of child elements
                    if (orphans == null) {
                        orphans = Lists.newArrayList();
                    }
                    orphans.add(child);
                } else {
                    LOG.debug("JAXB class " + klass.getName() + " does NOT recognise element named:" + childName);
                }
            }
        }
        // Prune the promoted elements from the list of children
        if (orphans != null) {
            for (org.w3c.dom.Element orphan : orphans) {
                elem.removeChild(orphan);
            }
        }
    }

    private static Class<?> classForTopLevelElem(Element elem) {
        String className = null;
        try {
            String ns = elem.getQName().getNamespaceURI();
            if (AdminConstants.NAMESPACE_STR.equals(ns)) {
                className = ADMIN_JAXB_PACKAGE  + "." + elem.getName();
            } else if (AccountConstants.NAMESPACE_STR.equals(ns)) {
                className = ACCOUNT_JAXB_PACKAGE  + "." + elem.getName();
            } else if (MailConstants.NAMESPACE_STR.equals(ns)) {
                className = MAIL_JAXB_PACKAGE  + "." + elem.getName();
            } else {
                LOG.info("Unexpected namespace[" + ns + "]");
                return null;
            }
            Class<?> klass = Class.forName(className);
            if (klass == null) {
                LOG.info("Failed to find CLASS for classname=[" + className + "]");
                return null;
            }
            return klass;
        } catch (NullPointerException npe) {
            LOG.info("Problem finding JAXB package", npe);
            return null;
        } catch (ClassNotFoundException cnfe) {
            LOG.info("Problem finding JAXB class", cnfe);
            return null;
        }
    }

    /**
     * @param elem represents a structure which may only match Zmail's more
     * relaxed rules rather than stringent JAXB rules.
     * e.g. Zmail allows attributes to be specified as elements.
     * @param klass is the JAXB class for {@link elem}
     * @return a JAXB object
     */
    @SuppressWarnings("unchecked")
    private static <T> T w3cDomDocToJaxb(org.w3c.dom.Document doc,
            Class<?> klass, boolean jaxbClassInContext)
    throws ServiceException {
        fixupStructureForJaxb(doc.getDocumentElement(), klass);
        return (T) rawW3cDomDocToJaxb(doc, klass, jaxbClassInContext);
    }

    /**
     * Return a JAXB object.  This implementation uses a org.w3c.dom.Document
     * as an intermediate representation.  This appears to be more reliable
     * than using a DocumentSource based on org.dom4j.Element
     * @param klass is the JAXB class for {@link doc}
     * @param jaxbClassInContext is true if {@link klass} is the JAXB class
     * for a request or response object that is in {@link JaxbUtil.MESSAGE_CLASSES}
     */
    @SuppressWarnings("unchecked")
    private static <T> T rawW3cDomDocToJaxb(org.w3c.dom.Document doc,
            Class<?> klass, boolean jaxbClassInContext)
    throws ServiceException {
        if ((doc == null || doc.getDocumentElement() == null)) {
            return null;
        }
        try {
            // LOG.warn("Dom to Xml:\n" + W3cDomUtil.asXML(document);
            Unmarshaller unmarshaller;
            if (jaxbClassInContext) {
                unmarshaller = getContext().createUnmarshaller();
                return (T) unmarshaller.unmarshal(doc);
            } else {
                org.w3c.dom.Element docElem = doc.getDocumentElement();
                unmarshaller = createUnmarshaller(klass);
                JAXBElement<T> ret =
                    (JAXBElement<T>) unmarshaller.unmarshal(docElem, klass);
                return ret.getValue();
            }
        } catch (JAXBException ex) {
            throw ServiceException.FAILURE("Unable to unmarshal response for " +
                    doc.getDocumentElement().getNodeName(), ex);
        }
    }

    /**
     * Return a JAXB object.  This implementation uses a org.w3c.dom.Document
     * as an intermediate representation.  This appears to be more reliable
     * than using a DocumentSource based on org.dom4j.Element
     * @param klass is the JAXB class for {@link elem}
     */
    @SuppressWarnings("unchecked")
    public static <T> T elementToJaxb(Element elem, Class<?> klass)
    throws ServiceException {
        return (T) w3cDomDocToJaxb(elem.toW3cDom(), klass, false);
    }

    /**
     * Return a JAXB object corresponding to {@link e} which is the Xml for
     * a Request or Response.
     * @param e MUST be a top level Request or Response element whose
     * corresponding JAXB object is in {@link JaxbUtil.MESSAGE_CLASSES}
     */
    @SuppressWarnings("unchecked")
    public static <T> T elementToJaxb(Element e) throws ServiceException {
        Class<?> klass = classForTopLevelElem(e);
        if (klass == null) {
            LOG.info("Failed to find CLASS for name=[" + e.getName() +
                    "]  Is it a Request or Response node?");
            return null;
        }
        return (T) w3cDomDocToJaxb(e.toW3cDom(), klass, true);
    }

    /**
     * Only for use when marshalling request or response objects.
     */
    public static Marshaller createMarshaller() {
        try {
            return getContext().createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Only for use when unmarshalling request or response objects.
     */
    public static Unmarshaller createUnmarshaller() {
        try {
            return getContext().createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static Marshaller createMarshaller(Class<?> klass) {
        try {
            JAXBContext jaxb = getJaxbContext(klass);
            return jaxb.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static Unmarshaller createUnmarshaller(Class<?> klass) {
        try {
            JAXBContext jaxb = getJaxbContext(klass);
            return jaxb.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private static JAXBContext getContext() {
        if (JAXB_CONTEXT == null) {
            throw new IllegalStateException("JAXB has not been initialized");
        }
        return JAXB_CONTEXT;
    }
}
