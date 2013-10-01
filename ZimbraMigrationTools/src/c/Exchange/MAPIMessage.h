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

#include <mime/mimepp/mimepp.h>

namespace Zimbra
{
namespace MAPI
{
#define PR_URL_NAME                     PROP_TAG(PT_TSTRING, 0x6707)

#define EXCHIVERB_OPEN                  0
#define EXCHIVERB_RESERVED_COMPOSE      100
#define EXCHIVERB_RESERVED_OPEN         101
#define EXCHIVERB_REPLYTOSENDER         102
#define EXCHIVERB_REPLYTOALL            103
#define EXCHIVERB_FORWARD               104
#define EXCHIVERB_PRINT                 105
#define EXCHIVERB_SAVEAS                106
#define EXCHIVERB_RESERVED_DELIVERY     107
#define EXCHIVERB_REPLYTOFOLDER         108

typedef enum _ZM_ITEM_TYPE
{
    ZT_NONE = 0, ZT_MAIL, ZT_CONTACTS, ZT_APPOINTMENTS, ZT_TASKS, ZT_MEETREQ, ZTMAX
} ZM_ITEM_TYPE;

// MAPIMessageException class
class MAPIMessageException: public GenericException
{
public:
    MAPIMessageException(HRESULT hrErrCode, LPCWSTR lpszDescription);
    MAPIMessageException(HRESULT hrErrCode, LPCWSTR lpszDescription, LPCWSTR lpszShortDescription, int nLine, LPCSTR strFile);
    virtual ~MAPIMessageException() {}
};

// MAPIMessage Class
class MAPIMessage
{
private:
    // order of the message properties in _pMessagePropVals
    typedef enum _MessagePropIndex
    {
        MESSAGE_CLASS, MESSAGE_FLAGS, MESSAGE_DATE, SENDER_ADDRTYPE, SENDER_EMAIL_ADDR,
        SENDER_NAME, SENDER_ENTRYID, SUBJECT, TEXT_BODY, HTML_BODY, INTERNET_CPID,
        MESSAGE_CODEPAGE, LAST_VERB_EXECUTED, FLAG_STATUS, ENTRYID, SENT_ADDRTYPE,
        SENT_ENTRYID, SENT_EMAIL_ADDR, SENT_NAME, REPLY_NAMES, REPLY_ENTRIES,
        MIME_HEADERS, IMPORTANCE, INTERNET_MESSAGE_ID, DELIVERY_DATE, URL_NAME,
        MESSAGE_SIZE, STORE_SUPPORT_MASK, RTF_IN_SYNC, NMSGPROPS
    } MessagePropIndex;

    // defined so a static variable can hold the message props to retrieve
    typedef struct _MessagePropTags
    {
        ULONG cValues;
        ULONG aulPropTags[NMSGPROPS];
    } MessagePropTags;

    // order of the recipient properties in each row of _pRecipRows
    typedef enum _RecipientPropIndex
    {
        RDISPLAY_NAME, RENTRYID, RADDRTYPE, REMAIL_ADDRESS, RRECIPIENT_TYPE, RNPROPS
    } RecipientPropIndex;

    // defined so a static variable can hold the recipient properties to retrieve
    typedef struct _RecipientPropTags
    {
        ULONG cValues;
        ULONG aulPropTags[RNPROPS];
    } RecipientPropTags;

    // order of the recipient properties in each row of _pRecipRows
    typedef enum _ReplyToPropIndex
    {
        REPLYTO_DISPLAY_NAME, REPLYTO_ENTRYID, REPLYTO_ADDRTYPE, REPLYTO_EMAIL_ADDRESS,
        NREPLYTOPROPS
    } ReplyToPropIndex;

    // defined so a static variable can hold the recipient properties to retrieve
    typedef struct _ReplyToPropTags
    {
        ULONG cValues;
        ULONG aulPropTags[NREPLYTOPROPS];
    } ReplyToPropTags;

    MAPISession *m_session;
    LPMESSAGE m_pMessage;
    LPSPropValue m_pMessagePropVals;
    LPSRowSet m_pRecipientRows;
    SBinary m_EntryID;
    CHAR m_pDateTimeStr[32];
    CHAR m_pDeliveryDateTimeStr[32];
    CHAR m_pDeliveryUnixDateTimeStr[32];
    std::vector<std::string> RTFElement;
    enum EnumRTFElement
    {
        NOTFOUND = -1, OPENBRACE = 0, CLOSEBRACE, HTMLTAG, MHTMLTAG, PAR, TAB, LI, FI, HEXCHAR,
        PNTEXT, HTMLRTF, OPENBRACEESC, CLOSEBRACEESC, END, HTMLRTF0
    };

    static MessagePropTags m_messagePropTags;
    static RecipientPropTags m_recipientPropTags;
    static ReplyToPropTags m_replyToPropTags;
    unsigned int CodePageId();

    EnumRTFElement MatchRTFElement(const char *psz);

    const char *Advance(const char *psz, const char *pszCharSet);

public:
    MAPIMessage();
    ~MAPIMessage();
    void Initialize(LPMESSAGE pMessage, MAPISession &session, bool bPartial=false);
    void InternalFree();

    LPMESSAGE InternalMessageObject() { return m_pMessage; }
    bool Subject(LPTSTR *ppSubject);
    ZM_ITEM_TYPE ItemType();
    bool IsFlagged();
    bool GetURLName(LPTSTR *pstrUrlName);
    bool IsDraft();
    BOOL IsFromMe();
    BOOL IsUnread();
    BOOL Forwarded();
    BOOL RepliedTo();
    bool HasAttach();
    BOOL IsUnsent();
    bool HasHtmlPart();
    bool HasTextPart();

    SBinary &UniqueId();

    __int64 DeliveryDate();
    LPSTR DateString();
    __int64 Date();
    DWORD Size();
    LPSTR DeliveryDateString();
    LPSTR DeliveryUnixString();

    std::vector<LPWSTR>* SetKeywords();

    SBinary EntryID() { return m_EntryID; }
    bool TextBody(LPTSTR *ppBody, unsigned int &nTextChars);

    // reads the utf8 body and retruns it with accented chararcters
    bool UTF8EncBody(LPTSTR *ppBody, unsigned int &nTextChars);

    // return the html body of the message
    bool HtmlBody(LPVOID *ppBody, unsigned int &nHtmlBodyLen);
    bool DecodeRTF2HTML(char *buf, unsigned int *len);
    bool IsRTFHTML(const char *buf);
    void ToMimePPMessage(mimepp::Message &msg);
};

class MIRestriction;

// Message Iterator class
class MessageIterator: public MAPITableIterator
{
private:
    typedef enum _MessageIterPropTagIdx
    {
        MI_ENTRYID, MI_LONGTERM_ENTRYID_FROM_TABLE, MI_DATE, MI_MESSAGE_CLASS, NMSGPROPS
    } MessageIterPropTagIdx;
    typedef struct _MessageIterPropTags
    {
        ULONG cValues;
        ULONG aulPropTags[NMSGPROPS];
    } MessageIterPropTags;
    typedef struct _MessageIterSort
    {
        ULONG cSorts;
        ULONG cCategories;
        ULONG cExpanded;
        SSortOrder aSort[1];
    } MessageIterSortOrder;

public:
    MessageIterator();
    virtual ~MessageIterator();

    virtual LPSPropTagArray GetProps();
    virtual LPSSortOrderSet GetSortOrder();
    virtual LPSRestriction GetRestriction(ULONG TypeMask, FILETIME startDate);

    BOOL GetNext(MAPIMessage &msg);
    BOOL GetNext(__int64 &date, SBinary &bin);

protected:
    static MessageIterPropTags m_props;
    static MessageIterSortOrder m_sortOrder;
    static MIRestriction m_restriction;
};

// Restriction class
class MIRestriction
{
public:
    MIRestriction();
    ~MIRestriction();
    LPSRestriction GetRestriction(ULONG TypeMask, FILETIME startDate);

private:
    SRestriction pR[25];
    SPropValue _propValCont;
    SPropValue _propValMail;
    SPropValue _propValCTime;
    SPropValue _propValSTime;
    SPropValue _propValCanbeMail;
    SPropValue _propValCanbeMailPost;
    SPropValue _propValAppt;
    LPWSTR _pApptClass;
    SPropValue _propValTask;
    LPWSTR _pTaskClass;
    SPropValue _propValReqAndRes;
    LPWSTR _pReqAndResClass;
    SPropValue _propValDistList;
    LPWSTR _pDistListClass;
    LPWSTR _pContactClass;
    LPWSTR _pMailClass;
    SPropValue _propValIMAPHeaderOnly;
};

mimepp::Mailbox *MakeMimePPMailbox(LPTSTR pDisplayName, LPTSTR pSmtpAddress);
}
}
