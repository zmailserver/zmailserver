/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2011, 2012, 2013 VMware, Inc.
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

#define NO_EXCEPTION        0           
#define NORMAL_EXCEPTION    1
#define CANCEL_EXCEPTION    2

// MAPIAppointmentException class
class MAPIAppointmentException: public GenericException
{
public:
    MAPIAppointmentException(HRESULT hrErrCode, LPCWSTR lpszDescription);
    MAPIAppointmentException(HRESULT hrErrCode, LPCWSTR lpszDescription, LPCWSTR lpszShortDescription, int nLine, LPCSTR strFile);
    virtual ~MAPIAppointmentException() {}
};

// MAPIAppointment class
class MAPIAppointment : public MAPIRfc2445
{
private:
    //static bool m_bNamedPropsInitialized;

    // prop tags for named properties
    ULONG pr_clean_global_objid, pr_appt_start, pr_appt_end, pr_location, pr_busystatus, pr_allday, pr_isrecurring,
	  pr_recurstream, pr_timezoneid, pr_responsestatus, pr_exceptionreplacetime, pr_reminderminutes, pr_private, pr_reminderset;

    // index of props
    typedef enum _AppointmentPropIdx
    {
        N_UID, N_APPTSTART, N_APPTEND, N_LOCATION, N_BUSYSTATUS, N_ALLDAY, N_ISRECUR, N_RECURSTREAM, N_TIMEZONEID,
	N_RESPONSESTATUS, N_EXCEPTIONREPLACETIME, N_NUMAPPTPROPS
    } AppointmentPropIdx;

    typedef enum _CommonPropIdx
    {
        N_REMINDERMINUTES, N_PRIVATE, N_REMINDERSET, N_NUMCOMMONPROPS
    } CommonPropIdx;

    // this enum lists all the props
    enum
    {
        C_MESSAGE_FLAGS, C_SUBJECT, C_BODY, C_HTMLBODY, C_UID, C_START, C_END, C_LOCATION, C_BUSYSTATUS, C_ALLDAY, C_ISRECUR, C_RECURSTREAM,
	C_TIMEZONEID, C_RESPONSESTATUS,C_RESPONSEREQUESTED, C_EXCEPTIONREPLACETIME, C_REMINDERMINUTES, C_PRIVATE, C_REMINDERSET, C_NUMALLAPPTPROPS
	//org stuff later
    };

    // these are the named property id's
    LONG nameIds[N_NUMAPPTPROPS];
    LONG nameIdsC[N_NUMCOMMONPROPS];

    int m_iExceptionType;

    // appointment data members (represented both by regular and named props
    wstring m_pSubject;
    wstring m_pInstanceUID;
    wstring m_pLocation;
    wstring m_pStartDate;
    wstring m_pCalFilterDate;
    wstring m_pStartDateForRecID;
    wstring m_pEndDate;
    wstring m_pBusyStatus;
    wstring m_pAllday;
    wstring m_pTransparency;
    wstring m_pResponseStatus;
	wstring m_pCurrentStatus;
    wstring m_pOrganizerName;
    wstring m_pOrganizerAddr;
    vector<Attendee*> m_vAttendees;
    wstring m_pReminderMinutes;
    wstring m_pPrivate;
	wstring m_pReminderSet;
	wstring m_pResponseRequested;
    wstring m_pPlainTextFile;
    wstring m_pHtmlFile;
    vector<MAPIAppointment*> m_vExceptions;
    wstring m_pExceptionType;
	Zimbra::Mail::TimeZone::OlkTimeZone _olkTz;
    LPWSTR _pTzString;
	Zimbra::MAPI::MAPIStore *m_mapiStore;
	Zimbra::Mail::TimeZone *pInvTz;

	IAddrBook *m_pAddrBook;
	HRESULT UpdateAttendeeFromEntryId(Attendee &pAttendee,SBinary &eid);
public:
    MAPIAppointment(Zimbra::MAPI::MAPISession &session, Zimbra::MAPI::MAPIStore &store, Zimbra::MAPI::MAPIMessage &mMessage, int exceptionType);
    ~MAPIAppointment();
    HRESULT InitNamedPropsForAppt();
    HRESULT SetMAPIAppointmentValues();
    void SetSubject(LPTSTR pStr);
    void SetStartDate(FILETIME ft);
    LPWSTR MakeDateFromExPtr(FILETIME ft);
    void SetEndDate(FILETIME ft, bool bAllday);
    void SetInstanceUID(LPSBinary bin);
    void SetLocation(LPTSTR pStr);
    void SetBusyStatus(long busystatus);
    ULONG InterpretBusyStatus();
    void SetAllday(unsigned short usAllday);
    BOOL InterpretAllday();
    void SetTransparency(LPTSTR pStr);
    void SetResponseStatus(long responsestatus);
    wstring ConvertValueToRole(long role);
    wstring ConvertValueToPartStat(long ps);
    HRESULT SetOrganizerAndAttendees();
    void SetReminderMinutes(long reminderminutes);
    void SetPrivate(unsigned short usPrivate);
	void SetReminderSet(unsigned short usReminderset);
	void SetResponseRequested(unsigned short usPrivate);
    void SetPlainTextFileAndContent();
    void SetHtmlFileAndContent();
    void SetTimezoneId(LPTSTR pStr);
    int SetRecurValues();
    void SetExceptions();
    void SetExceptionType(int type);
    void FillInExceptionAppt(MAPIAppointment* ex, Zimbra::Mapi::COutlookRecurrenceException* lpException);
    void FillInCancelException(MAPIAppointment* pEx, Zimbra::Mapi::CFileTime cancelDate);

    wstring GetSubject();
    wstring GetStartDate();
    wstring GetCalFilterDate();
    wstring GetStartDateForRecID();
    wstring GetEndDate();
    wstring GetInstanceUID();
    wstring GetLocation();
    wstring GetBusyStatus();
    wstring GetAllday();
    wstring GetTransparency();
    wstring GetReminderMinutes();
    wstring GetResponseStatus();
	wstring GetCurrentStatus();
	wstring GetResponseRequested();
    wstring GetOrganizerName();
    wstring GetOrganizerAddr();
    wstring GetPrivate();
	wstring GetReminderSet();
    wstring GetPlainTextFileAndContent();
    wstring GetHtmlFileAndContent();
    vector<Attendee*> GetAttendees();
    vector<MAPIAppointment*> GetExceptions();
    wstring GetExceptionType();
};
