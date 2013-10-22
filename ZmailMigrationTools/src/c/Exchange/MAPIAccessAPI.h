/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite CSharp Client
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
#pragma once
#include "logger.h"
#include "Util.h"
#include "MAPIRules.h"

namespace Zimbra
{
namespace MAPI
{
typedef struct _Folder_Data
{
    wstring name;
    SBinary sbin;
    wstring folderpath;
    wstring containerclass;
    long zimbraid;
    unsigned long itemcount;
} Folder_Data;
typedef struct _Item_Data
{
    SBinary sbMessageID;
    long lItemType;
    __int64 MessageDate;
    string strMsgEntryId;
    // parent folder
} Item_Data;

typedef struct _MessagePart
{
    wstring contentType;
    wstring content;
} MessagePart;

// base item data
typedef struct _BaseItemData
{
    __int64 MessageDate;
} BaseItemData;

// folders to skip
enum
{
    TS_JOURNAL = 0, TS_OUTBOX, TS_SYNC_CONFLICTS, TS_SYNC_ISSUES, TS_SYNC_LOCAL_FAILURES,
    TS_SYNC_SERVER_FAILURES, TS_FOLDERS_MAX
};


// contact item data
typedef struct _ContactItemData: BaseItemData
{
    wstring CallbackPhone;
    wstring CarPhone;
    wstring Company;
    wstring Email1;
    wstring Email2;
    wstring Email3;
    wstring FileAs;
    wstring FirstName;
    wstring HomeCity;
    wstring HomeCountry;
    wstring HomeFax;
    wstring HomePhone;
    wstring HomePhone2;
    wstring HomePostalCode;
    wstring HomeState;
    wstring HomeStreet;
    wstring HomeURL;
    wstring JobTitle;
    wstring LastName;
    wstring MiddleName;
    wstring MobilePhone;
    wstring NamePrefix;
    wstring NameSuffix;
    wstring Notes;
    wstring OtherCity;
    wstring OtherCountry;
    wstring OtherFax;
    wstring OtherPhone;
    wstring OtherPostalCode;
    wstring OtherState;
    wstring OtherStreet;
    wstring OtherURL;
    wstring Pager;
    wstring WorkCity;
    wstring WorkCountry;
    wstring WorkFax;
    wstring WorkPhone;
    wstring WorkPostalCode;
    wstring WorkState;
    wstring WorkStreet;
    wstring WorkURL;
    wstring Birthday;
    wstring UserField1;
    wstring UserField2;
    wstring UserField3;
    wstring UserField4;
    wstring NickName;
    wstring pDList;
    wstring Type;
    wstring PictureID;
    wstring IMAddress1;
    wstring Anniversary;
    wstring ContactImagePath;
	wstring ImageContenttype;
	wstring ImageContentdisp;
    vector<ContactUDFields> UserDefinedFields;
    vector<LPWSTR>* vTags;
} ContactItemData;

typedef struct
{
    LPTSTR buffer;
    unsigned long size;
} data_buffer;

typedef struct _MessageItemData: BaseItemData
{
    wstring Subject;
    bool IsFlagged;
    wstring Urlname;
    bool IsDraft;
    bool IsFromMe;
    bool IsUnread;
    bool IsForwared;
    bool RepliedTo;
    bool HasAttachments;
    bool IsUnsent;
    bool HasHtml;
    bool HasText;
    __int64 deliveryDate;
    wstring DeliveryDateString;
    wstring DeliveryUnixString;
    __int64 Date;
    wstring DateString;
    vector<LPWSTR>* vTags;
    data_buffer textbody;
    data_buffer htmlbody;
    wstring MimeFile;
	wstring wstrmimeBuffer;
} MessageItemData;

typedef struct _ApptItemData: BaseItemData
{
    wstring Subject;
    wstring Name;
    wstring Location;
    wstring Uid;
    wstring PartStat;
	wstring CurrStat;
    wstring FreeBusy;
    wstring Transparency;
    wstring AllDay;
    wstring StartDate;
    wstring CalFilterDate;
    wstring EndDate;
    wstring ApptClass;
    wstring AlarmTrigger;
	wstring RSVP;
    Organizer organizer;
    Tz tz;
    vector<Attendee*> vAttendees;
    vector<AttachmentInfo*> vAttachments;
    vector<MessagePart> vMessageParts;
    vector<LPWSTR>* vTags;

    // recurrence stuff
    wstring recurPattern;
    wstring recurInterval;
    wstring recurWkday;
    wstring recurEndType;
    wstring recurCount;
    wstring recurEndDate;
    wstring recurDayOfMonth;
    wstring recurMonthOccurrence;
    wstring recurMonthOfYear;
    vector<MAPIAppointment*> vExceptions;
    //

    // exception
    wstring ExceptionType;

    //data_buffer textbody;
    //data_buffer htmlbody;
} ApptItemData;

typedef struct _TaskItemData: BaseItemData
{
    wstring Subject;
    wstring Importance;
    wstring TaskStart;
    wstring TaskFilterDate;
    wstring TaskDue;
    wstring Status;
    wstring PercentComplete;
    wstring TotalWork;
    wstring ActualWork;
    wstring Companies;
    wstring Mileage;
    wstring BillingInfo;
    wstring TaskFlagDueBy;
    wstring ApptClass;
    vector<AttachmentInfo*> vAttachments;
    vector<MessagePart> vMessageParts;
    vector<LPWSTR>* vTags;

    // recurrence stuff
    wstring recurPattern;
    wstring recurInterval;
    wstring recurWkday;
    wstring recurEndType;
    wstring recurCount;
    wstring recurEndDate;
    wstring recurDayOfMonth;
    wstring recurMonthOccurrence;
    wstring recurMonthOfYear;
} TaskItemData;

class MAPIAccessAPI
{
private:
    static std::wstring m_strTargetProfileName;
    static std::wstring m_strExchangeHostName;
    static Zimbra::MAPI::MAPISession *m_zmmapisession;
    static Zimbra::MAPI::MAPIStore *m_defaultStore;
    static bool m_bSingleMailBoxMigration;
	static bool m_bHasJoinedDomain;
	std::wstring m_strUserName;
        std::wstring m_strUserAccount;
    Zimbra::MAPI::MAPIStore *m_userStore;
    Zimbra::MAPI::MAPIFolder *m_rootFolder;
    ExchangeSpecialFolderId FolderToSkip[TS_FOLDERS_MAX];

    void InitFoldersToSkip();
    bool SkipFolder(ExchangeSpecialFolderId exfid);
    LPCWSTR OpenUserStore();
    HRESULT Iterate_folders(Zimbra::MAPI::MAPIFolder &folder, vector<Folder_Data> &fd);
    void traverse_folder(Zimbra::MAPI::MAPIFolder &folder);
    HRESULT GetInternalFolder(SBinary sbFolderEID, MAPIFolder &folder);
	static LONG WINAPI UnhandledExceptionFilter(LPEXCEPTION_POINTERS pExPtrs);
	static void internalInit();
public:
	// static methods to be used by all mailboxes/profile/PST
    // lpcwstrMigTarget -> Exchange Admin Profile for Exchange mailboxes migration
    // lpcwstrMigTarget -> Local Exchange profile migration
    // lpcwstrMigTarget -> PST file path for PST migration
    static LPCWSTR InitGlobalSessionAndStore(LPCWSTR lpcwstrMigTarget);
	static LPCWSTR _InitGlobalSessionAndStore(LPCWSTR lpcwstrMigTarget);	
    static void UnInitGlobalSessionAndStore();

    // Per mailbox methods.
    MAPIAccessAPI(wstring strUserName, wstring strUserAccount);

    ~MAPIAccessAPI();
    LPCWSTR InitializeUser();
	LPCWSTR _InitializeUser();
    LPCWSTR GetRootFolderHierarchy(vector<Folder_Data> &vfolderlist);
	LPCWSTR _GetRootFolderHierarchy(vector<Folder_Data> &vfolderlist);
    LPCWSTR GetFolderItemsList(SBinary sbFolderEID, vector<Item_Data> &ItemList);
	LPCWSTR _GetFolderItemsList(SBinary sbFolderEID, vector<Item_Data> &ItemList);
    LPCWSTR GetItem(SBinary sbItemEID, BaseItemData &itemData);
	LPCWSTR _GetItem(SBinary sbItemEID, BaseItemData &itemData);
    LPWSTR  GetOOOStateAndMsg();
    LPCWSTR GetExchangeRules(vector<CRule> &vRuleList);
};
}
}
