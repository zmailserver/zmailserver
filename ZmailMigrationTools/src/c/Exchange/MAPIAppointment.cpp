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
#include "common.h"
#include "Exchange.h"
#include "MAPIMessage.h"
#include "MAPIAppointment.h"
#include "Logger.h"
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// MAPIAppointmentException
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
MAPIAppointmentException::MAPIAppointmentException(HRESULT hrErrCode, LPCWSTR
    lpszDescription): GenericException(hrErrCode, lpszDescription)
{
    //
}

MAPIAppointmentException::MAPIAppointmentException(HRESULT hrErrCode, LPCWSTR lpszDescription, LPCWSTR lpszShortDescription, 
	int nLine, LPCSTR strFile): GenericException(hrErrCode, lpszDescription, lpszShortDescription, nLine, strFile)
{
    //
}

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// MAPIAppointment
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//bool MAPIAppointment::m_bNamedPropsInitialized = false;

MAPIAppointment::MAPIAppointment(Zimbra::MAPI::MAPISession &session, Zimbra::MAPI::MAPIStore &store, Zimbra::MAPI::MAPIMessage &mMessage, int exceptionType)
                                : MAPIRfc2445 (session, mMessage)
{
	m_mapiStore = &store;
    m_iExceptionType = exceptionType;
    SetExceptionType(exceptionType);
	pInvTz = NULL;
	_pTzString = NULL;
	m_pAddrBook = NULL;
	
    //if (MAPIAppointment::m_bNamedPropsInitialized == false)
    //{
	pr_clean_global_objid = 0;
	pr_appt_start = 0;
	pr_appt_end = 0;
	pr_location = 0;
	pr_busystatus = 0;
	pr_allday = 0;
	pr_isrecurring = 0;
	pr_recurstream = 0;
	pr_timezoneid = 0;
	pr_reminderminutes = 0;
        pr_private = 0;
		pr_reminderset =0;
	pr_responsestatus = 0;
        pr_exceptionreplacetime = 0;
	InitNamedPropsForAppt();
    //}

    m_pSubject = L"";
    m_pStartDate = L"";
    m_pStartDateForRecID = L"";
    m_pEndDate = L"";
    m_pInstanceUID = L"";
    m_pLocation = L"";
    m_pBusyStatus = L"";
    m_pAllday = L"";
    m_pTransparency = L"";
    m_pReminderMinutes = L"";
    m_pCurrentStatus = L"";
	m_pResponseStatus = L"";
    m_pOrganizerName = L"";
    m_pOrganizerAddr = L"";
    m_pPrivate = L"";
	m_pReminderSet = L"";
	m_pResponseRequested = L"";
	    
	HRESULT hr=session.OpenAddressBook(&m_pAddrBook);
	if(!SUCCEEDED(hr))
	{
		m_pAddrBook=NULL;
	}
	hr = SetMAPIAppointmentValues();
	if(FAILED(hr))
	{
		dlogw("MapiAppt::SetMAPIAppointmentValues failed");
	}
}

MAPIAppointment::~MAPIAppointment()
{
    if (m_pPropVals)
    {
        MAPIFreeBuffer(m_pPropVals);
    }
    m_pPropVals = NULL;
	if(m_pAddrBook)
		m_pAddrBook->Release();
	m_pAddrBook=NULL;
}

HRESULT MAPIAppointment::InitNamedPropsForAppt()
{
    // init named props
    nameIds[0] = 0x0023;
    nameIds[1] = 0x820D;
    nameIds[2] = 0x820E;
    nameIds[3] = 0x8208;
    nameIds[4] = 0x8205;
    nameIds[5] = 0x8215;
    nameIds[6] = 0x8223;
    nameIds[7] = 0x8216;
    nameIds[8] = 0x8234;
    nameIds[9] = 0x8218;
    nameIds[10] = 0x8228;

    nameIdsC[0] = 0x8501;
    nameIdsC[1] = 0x8506;
	nameIdsC[2] = 0x8503;

    HRESULT hr = S_OK;
    Zimbra::Util::ScopedBuffer<SPropValue> pPropValMsgClass;

    if (FAILED(hr = HrGetOneProp(m_pMessage, PR_MESSAGE_CLASS, pPropValMsgClass.getptr())))
        throw MAPIAppointmentException(hr, L"InitNamedPropsForAppt(): HrGetOneProp Failed.", 
		ERR_MAPI_APPOINTMENT, __LINE__, __FILE__);

    // initialize the MAPINAMEID structure GetIDsFromNames requires
    LPMAPINAMEID ppNames[N_NUMAPPTPROPS] = { 0 };
    for (int i = 0; i < N_NUMAPPTPROPS; i++)
    {
        MAPIAllocateBuffer(sizeof (MAPINAMEID), (LPVOID *)&(ppNames[i]));
        ppNames[i]->ulKind = MNID_ID;
        ppNames[i]->lpguid = (i == N_UID) ? (LPGUID)(&PS_OUTLOOK_MTG) : (LPGUID)(&PS_OUTLOOK_APPT);
        ppNames[i]->Kind.lID = nameIds[i];
    }

    LPMAPINAMEID ppNamesC[N_NUMCOMMONPROPS] = { 0 };
    for (int i = 0; i < N_NUMCOMMONPROPS; i++)
    {
        MAPIAllocateBuffer(sizeof (MAPINAMEID), (LPVOID *)&(ppNamesC[i]));
        ppNamesC[i]->ulKind = MNID_ID;
        ppNamesC[i]->lpguid = (LPGUID)(&PS_OUTLOOK_COMMON);
        ppNamesC[i]->Kind.lID = nameIdsC[i];
    }

    // get the real prop tag ID's
    LPSPropTagArray pAppointmentTags = NULL;
    LPSPropTagArray pAppointmentTagsC = NULL;

    if (FAILED(hr = m_pMessage->GetIDsFromNames(N_NUMAPPTPROPS, ppNames, MAPI_CREATE,
            &pAppointmentTags)))
        throw MAPIAppointmentException(hr, L"Init(): GetIDsFromNames on pAppointmentTags Failed.", 
		ERR_MAPI_APPOINTMENT, __LINE__, __FILE__);

    if (FAILED(hr = m_pMessage->GetIDsFromNames(N_NUMCOMMONPROPS, ppNamesC, MAPI_CREATE,
            &pAppointmentTagsC)))
        throw MAPIAppointmentException(hr, L"Init(): GetIDsFromNames on pAppointmentTagsC Failed.", 
		ERR_MAPI_APPOINTMENT, __LINE__, __FILE__);

    // give the prop tag ID's a type
    pr_clean_global_objid = SetPropType(pAppointmentTags->aulPropTag[N_UID], PT_BINARY);
    pr_appt_start = SetPropType(pAppointmentTags->aulPropTag[N_APPTSTART], PT_SYSTIME);
    pr_appt_end = SetPropType(pAppointmentTags->aulPropTag[N_APPTEND], PT_SYSTIME);
    pr_location = SetPropType(pAppointmentTags->aulPropTag[N_LOCATION], PT_TSTRING);
    pr_busystatus = SetPropType(pAppointmentTags->aulPropTag[N_BUSYSTATUS], PT_LONG);
    pr_allday = SetPropType(pAppointmentTags->aulPropTag[N_ALLDAY], PT_BOOLEAN);
    pr_isrecurring = SetPropType(pAppointmentTags->aulPropTag[N_ISRECUR], PT_BOOLEAN);
    pr_recurstream = SetPropType(pAppointmentTags->aulPropTag[N_RECURSTREAM], PT_BINARY);
    pr_timezoneid = SetPropType(pAppointmentTags->aulPropTag[N_TIMEZONEID], PT_TSTRING);
    pr_responsestatus = SetPropType(pAppointmentTags->aulPropTag[N_RESPONSESTATUS], PT_LONG);
    pr_exceptionreplacetime = SetPropType(pAppointmentTags->aulPropTag[N_EXCEPTIONREPLACETIME], PT_SYSTIME);
    pr_reminderminutes = SetPropType(pAppointmentTagsC->aulPropTag[N_REMINDERMINUTES], PT_LONG);
	
    pr_private = SetPropType(pAppointmentTagsC->aulPropTag[N_PRIVATE], PT_BOOLEAN);
	pr_reminderset = SetPropType(pAppointmentTagsC->aulPropTag[N_REMINDERSET], PT_BOOLEAN);
    // free the memory we allocated on the head
    for (int i = 0; i < N_NUMAPPTPROPS; i++)
    {
        MAPIFreeBuffer(ppNames[i]);
    }
    for (int i = 0; i < N_NUMCOMMONPROPS; i++)
    {
        MAPIFreeBuffer(ppNamesC[i]);
    }
    MAPIFreeBuffer(pAppointmentTags);
    MAPIFreeBuffer(pAppointmentTagsC);

    //MAPIAppointment::m_bNamedPropsInitialized = true;

    return S_OK;
}

HRESULT MAPIAppointment::SetMAPIAppointmentValues()
{
    SizedSPropTagArray(C_NUMALLAPPTPROPS, appointmentProps) = {
	C_NUMALLAPPTPROPS, {
	    PR_MESSAGE_FLAGS, PR_SUBJECT, PR_BODY, PR_HTML, pr_clean_global_objid,
	    pr_appt_start, pr_appt_end, pr_location, pr_busystatus, pr_allday,
	    pr_isrecurring, pr_recurstream, pr_timezoneid, pr_responsestatus,
            PR_RESPONSE_REQUESTED,pr_exceptionreplacetime,
			pr_reminderminutes, pr_private, pr_reminderset
	}
    };

    HRESULT hr = S_OK;
    ULONG cVals = 0;
    bool bAllday = false;
    m_bHasAttachments = false;
    m_bIsRecurring = false;	

	// get the zimbra appt wrapper around the mapi message
 	Zimbra::Mapi::Appt appt(m_pMessage, m_mapiStore->GetInternalMAPIStore());

    // save off the default timezone info for this appointment
	try
	{
		hr = appt.GetTimezone(_olkTz, &_pTzString);
	}
	catch(...)
	{
		hr=E_FAIL;
	}
	if (SUCCEEDED(hr))
    {
		// get the timezone info for this appt
		pInvTz= new Zimbra::Mail::TimeZone(_pTzString);
		pInvTz->Initialize(_olkTz, _pTzString);
	}

    if (FAILED(hr = m_pMessage->GetProps((LPSPropTagArray) & appointmentProps, fMapiUnicode, &cVals,
            &m_pPropVals)))
        throw MAPIAppointmentException(hr, L"SetMAPIAppointmentValues(): GetProps Failed.",
		ERR_MAPI_APPOINTMENT, __LINE__, __FILE__);
    
    if (m_pPropVals[C_MESSAGE_FLAGS].ulPropTag == appointmentProps.aulPropTag[C_MESSAGE_FLAGS])
    {
        m_bHasAttachments = (m_pPropVals[C_MESSAGE_FLAGS].Value.l & MSGFLAG_HASATTACH) != 0;
    }
    if (m_pPropVals[C_ISRECUR].ulPropTag == appointmentProps.aulPropTag[C_ISRECUR]) // do this first to set dates correctly
    {
	m_bIsRecurring = (m_pPropVals[C_ISRECUR].Value.b == 1);
    }
    if (m_pPropVals[C_ALLDAY].ulPropTag == appointmentProps.aulPropTag[C_ALLDAY])
    {
        SetAllday(m_pPropVals[C_ALLDAY].Value.b);
        bAllday = (m_pPropVals[C_ALLDAY].Value.b == 1);
    }
    if (m_pPropVals[C_SUBJECT].ulPropTag == appointmentProps.aulPropTag[C_SUBJECT])
    {
	SetSubject(m_pPropVals[C_SUBJECT].Value.lpszW);
    }
    if (m_pPropVals[C_UID].ulPropTag == appointmentProps.aulPropTag[C_UID])
    {
	SetInstanceUID(&m_pPropVals[C_UID].Value.bin);
    }
    if (m_pPropVals[C_START].ulPropTag == appointmentProps.aulPropTag[C_START])
    {
	SetStartDate(m_pPropVals[C_START].Value.ft);
    }
    if (m_pPropVals[C_END].ulPropTag == appointmentProps.aulPropTag[C_END])
    {
        SetEndDate(m_pPropVals[C_END].Value.ft, bAllday);
    }
    if (m_pPropVals[C_LOCATION].ulPropTag == appointmentProps.aulPropTag[C_LOCATION])
    {
	SetLocation(m_pPropVals[C_LOCATION].Value.lpszW);
    }
    if (m_pPropVals[C_BUSYSTATUS].ulPropTag == appointmentProps.aulPropTag[C_BUSYSTATUS])
    {
	SetBusyStatus(m_pPropVals[C_BUSYSTATUS].Value.l);
    }
    if (m_pPropVals[C_RESPONSESTATUS].ulPropTag == appointmentProps.aulPropTag[C_RESPONSESTATUS])
    {
	SetResponseStatus(m_pPropVals[C_RESPONSESTATUS].Value.l);
    }
	 if (m_pPropVals[C_RESPONSEREQUESTED].ulPropTag == appointmentProps.aulPropTag[C_RESPONSEREQUESTED])
    {
	SetResponseRequested(m_pPropVals[C_RESPONSEREQUESTED].Value.b);
    }
	
	unsigned short usReminderSet=1;
	if (m_pPropVals[C_REMINDERSET].ulPropTag == appointmentProps.aulPropTag[C_REMINDERSET])
    {
		usReminderSet= m_pPropVals[C_REMINDERSET].Value.b;
    }
	if(usReminderSet)
	{
		if (m_pPropVals[C_REMINDERMINUTES].ulPropTag == appointmentProps.aulPropTag[C_REMINDERMINUTES])
		{
		SetReminderMinutes(m_pPropVals[C_REMINDERMINUTES].Value.l);
		}
	}
    if (m_pPropVals[C_PRIVATE].ulPropTag == appointmentProps.aulPropTag[C_PRIVATE])
    {
	SetPrivate(m_pPropVals[C_PRIVATE].Value.b);
    }
	
    SetTransparency(L"O");
    SetPlainTextFileAndContent();
    SetHtmlFileAndContent();

    if (m_bHasAttachments)
    {
        if (FAILED(ExtractAttachments()))
        {
            dlogw(L"Could not extract attachments");
        }
    }

    hr = SetOrganizerAndAttendees();
	if(FAILED(hr))
	{
		 dlogw(L"SetOrganizerAndAttendees failed");
	}

    if ((m_bIsRecurring) && (m_iExceptionType != CANCEL_EXCEPTION))
    {
	if (m_pPropVals[C_RECURSTREAM].ulPropTag == appointmentProps.aulPropTag[C_RECURSTREAM])
	{
	    // special case for timezone id
	    if (m_pPropVals[C_TIMEZONEID].ulPropTag == appointmentProps.aulPropTag[C_TIMEZONEID])
	    {
		SetTimezoneId(m_pPropVals[C_TIMEZONEID].Value.lpszW);
	    }
	    //

	    int numExceptions = SetRecurValues(); // returns null if no exceptions
            if (numExceptions > 0)
            {
                SetExceptions();
            }
	}
    }
    return hr;
}

void MAPIAppointment::SetTimezoneId(LPTSTR pStr)
{
    m_pTimezoneId = pStr;
    size_t nPos = m_pTimezoneId.find(_T(":"), 0);
    if (nPos != std::string::npos)
    {
	m_pTimezoneId.replace(nPos, 1, L".");
    }
}

 int MAPIAppointment::SetRecurValues()
{
    Zimbra::Util::ScopedInterface<IStream> pRecurrenceStream;
    HRESULT hResult = m_pMessage->OpenProperty(pr_recurstream, &IID_IStream, 0, 0,
						(LPUNKNOWN *)pRecurrenceStream.getptr());
    if (FAILED(hResult))
    {
	return 0;
    }
    LPSTREAM pStream = pRecurrenceStream.get();
    Zimbra::Mapi::Appt OlkAppt(m_pMessage, NULL);
    Zimbra::Mapi::COutlookRecurrencePattern &recur = OlkAppt.GetRecurrencePattern();
    hResult = recur.ReadRecurrenceStream(pStream);
    if (FAILED(hResult))
    {
	return 0;
    }

	// Set Timezone info
    SYSTEMTIME stdTime;
    SYSTEMTIME dsTime;
	Zimbra::Mail::TimeZone tmpz =  recur.GetTimeZone();
	if(pInvTz)
		tmpz= *pInvTz;
	const Zimbra::Mail::TimeZone &tzone= tmpz;
	
	m_timezone.id = m_pTimezoneId;  // don't use m_timezone.id = tzone.GetId()
	IntToWstring(tzone.GetStandardOffset(), m_timezone.standardOffset);
    IntToWstring(tzone.GetDaylightOffset(), m_timezone.daylightOffset);
    tzone.GetStandardStart(stdTime);
    tzone.GetDaylightStart(dsTime);
    IntToWstring(stdTime.wDay, m_timezone.standardStartWeek);
    IntToWstring(stdTime.wDayOfWeek + 1, m_timezone.standardStartWeekday);  // note the + 1 -- bumping weekday
    IntToWstring(stdTime.wMonth, m_timezone.standardStartMonth);
    IntToWstring(stdTime.wHour, m_timezone.standardStartHour);
    IntToWstring(stdTime.wMinute, m_timezone.standardStartMinute);
    IntToWstring(stdTime.wSecond, m_timezone.standardStartSecond);
    IntToWstring(dsTime.wDay, m_timezone.daylightStartWeek);
    IntToWstring(dsTime.wDayOfWeek + 1, m_timezone.daylightStartWeekday);   // note the + 1 -- bumping weekday
    IntToWstring(dsTime.wMonth, m_timezone.daylightStartMonth);
    IntToWstring(dsTime.wHour, m_timezone.daylightStartHour);
    IntToWstring(dsTime.wMinute, m_timezone.daylightStartMinute);
    IntToWstring(dsTime.wSecond, m_timezone.daylightStartSecond);
    //

    ULONG ulType = recur.GetRecurrenceType();
    switch (ulType)
    {
	case oRecursDaily:
	    m_pRecurPattern = L"DAI";
	    break;
	case oRecursWeekly:
	    m_pRecurPattern = L"WEE";
	    break;
	case oRecursMonthly:
	case oRecursMonthNth:
	    m_pRecurPattern = L"MON";
	    break;
	case oRecursYearly:
	case oRecursYearNth:
	    m_pRecurPattern = L"YEA";
	    break;
	default: ;
    }
    IntToWstring(recur.GetInterval(), m_pRecurInterval);

    ULONG ulDayOfWeekMask = recur.GetDayOfWeekMask();
    if (ulDayOfWeekMask & wdmSunday)    m_pRecurWkday += L"SU";
    if (ulDayOfWeekMask & wdmMonday)    m_pRecurWkday += L"MO";
    if (ulDayOfWeekMask & wdmTuesday)   m_pRecurWkday += L"TU";
    if (ulDayOfWeekMask & wdmWednesday) m_pRecurWkday += L"WE";
    if (ulDayOfWeekMask & wdmThursday)  m_pRecurWkday += L"TH";
    if (ulDayOfWeekMask & wdmFriday)    m_pRecurWkday += L"FR";
    if (ulDayOfWeekMask & wdmSaturday)  m_pRecurWkday += L"SA";

	//bug 77574. not sure why this condition existed .probabaly server was not handling weekday option.

    /*if ((m_pRecurPattern == L"DAI") && (m_pRecurWkday.length() > 0))	// every weekday
    {
	m_pRecurPattern = L"WEE";
    }*/

    if (m_pRecurPattern == L"MON")
    {
	if (ulType == oRecursMonthly)
	{
	    IntToWstring(recur.GetDayOfMonth(), m_pRecurDayOfMonth);
	}
	else
	if (ulType == oRecursMonthNth)
	{
	    ULONG ulMonthOccurrence = recur.GetInstance();
	    if (ulMonthOccurrence == 5)	    // last
	    {
		m_pRecurMonthOccurrence = L"-1";
	    }
	    else
	    {
		IntToWstring(ulMonthOccurrence, m_pRecurMonthOccurrence);
	    }
	}
    }

    if (m_pRecurPattern == L"YEA")
    {
	ULONG ulMonthOfYear = recur.GetMonthOfYear();
	IntToWstring(ulMonthOfYear, m_pRecurMonthOfYear);
	if (ulType == oRecursYearly)
	{
	    IntToWstring(recur.GetDayOfMonth(), m_pRecurDayOfMonth);
	}
	else
	if (ulType == oRecursYearNth)
	{
	    ULONG ulMonthOccurrence = recur.GetInstance();
	    if (ulMonthOccurrence == 5)	    // last
	    {
		m_pRecurMonthOccurrence = L"-1";
	    }
	    else
	    {
		IntToWstring(ulMonthOccurrence, m_pRecurMonthOccurrence);
	    }
	}
    }

    ULONG ulRecurrenceEndType = recur.GetEndType();
    Zimbra::Mapi::CRecurrenceTime rtEndDate = recur.GetEndDate();
    Zimbra::Mapi::CFileTime ft = (FILETIME)rtEndDate;
    m_pCalFilterDate = Zimbra::MAPI::Util::CommonDateString(ft);
    if (ulRecurrenceEndType == oetEndAfterN)
    {
	IntToWstring(recur.GetOccurrences(), m_pRecurCount);
    }
    else
    if (ulRecurrenceEndType == oetEndDate)
    {
        SYSTEMTIME st;
        FileTimeToSystemTime(&ft, &st);
        wstring temp = Zimbra::Util::FormatSystemTime(st, TRUE, TRUE);
        m_pRecurEndDate = temp.substr(0, 8);
    }
    return recur.GetExceptionCount();  
}

void MAPIAppointment::SetExceptions()
{
    Zimbra::Util::ScopedInterface<IStream> pRecurrenceStream;
    HRESULT hResult = m_pMessage->OpenProperty(pr_recurstream, &IID_IStream, 0, 0,
						(LPUNKNOWN *)pRecurrenceStream.getptr());
    if (FAILED(hResult))
    {
	return;
    }
    LPSTREAM pStream = pRecurrenceStream.get();
    Zimbra::Mapi::Appt OlkAppt(m_pMessage, NULL);
    Zimbra::Mapi::COutlookRecurrencePattern &recur = OlkAppt.GetRecurrencePattern();
    hResult = recur.ReadRecurrenceStream(pStream);
    if (FAILED(hResult))
    {
	return;
    }
    LONG lExceptionCount = recur.GetExceptionCount();

    for (LONG i = 0; i < lExceptionCount; i++)
    {
        Zimbra::Mapi::CRecurrenceTime rtDate = recur.GetExceptionOriginalDate(i);
        Zimbra::Mapi::CFileTime ftOrigDate = (FILETIME)rtDate;

        Zimbra::Mapi::COutlookRecurrenceException *lpException = recur.GetException(i);
        if (lpException != NULL)    
        {
            Zimbra::Util::ScopedInterface<IMessage> lpExceptionMessage;
            Zimbra::Util::ScopedInterface<IAttach> lpExceptionAttach;

            // FBS bug 70987 -- 5/27/12 -- since Exchange provider doesn't seem to support restriction on
            // attachment table, call OpenApptNR instead of OpenAppointment
            HRESULT hResult = lpException->OpenApptNR((LPMESSAGE)OlkAppt.MapiMsg(),
                lpExceptionMessage.getptr(), lpExceptionAttach.getptr(), pr_exceptionreplacetime);

            if (FAILED(hResult))
            {
                //dlogd(L"could not open appointment message for this occurrence"));
                return;
            }

            // We have everything for the object
            Zimbra::Mapi::Appt pOccurrence(lpExceptionMessage.get(), OlkAppt.GetStore(),
                                           lpException, lpExceptionAttach.get(),
                                           OlkAppt.MapiMsg());
            MAPIMessage exMAPIMsg;
            if (lpExceptionMessage.get() != NULL)
            {
                exMAPIMsg.Initialize(lpExceptionMessage.get(), *m_session);
                MAPIAppointment* pEx = new MAPIAppointment(*m_session, *m_mapiStore, exMAPIMsg, NORMAL_EXCEPTION);   // delete done in CMapiAccessWrap::GetData
                FillInExceptionAppt(pEx, lpException);
                m_vExceptions.push_back(pEx);
            }
        }
        else
        {
            MAPIAppointment* pEx = new MAPIAppointment(*m_session, *m_mapiStore, *m_mapiMessage, CANCEL_EXCEPTION);
            FillInCancelException(pEx, ftOrigDate);
            m_vExceptions.push_back(pEx);
        }
    }
}

void MAPIAppointment::FillInExceptionAppt(MAPIAppointment* pEx, Zimbra::Mapi::COutlookRecurrenceException* lpException)
{
    // FBS 4/12/12 -- set this up no matter what (so exceptId will be set)
    // FBS bug 71050 -- 4/9/12 -- recurrence id needs the original occurrence date
    Zimbra::Mapi::CRecurrenceTime rtOriginalDate = lpException->GetOriginalDateTime();  
    Zimbra::Mapi::CFileTime ftOriginalDate = (FILETIME)rtOriginalDate;
    pEx->m_pStartDateForRecID = MakeDateFromExPtr(rtOriginalDate);
    //

    if (pEx->m_pStartDate.length() == 0)
    {
        Zimbra::Mapi::CRecurrenceTime rtStartDate = lpException->GetStartDateTime();
        Zimbra::Mapi::CFileTime ftStartDate = (FILETIME)rtStartDate;
        pEx->m_pStartDate = MakeDateFromExPtr(ftStartDate);
        pEx->m_pCalFilterDate = Zimbra::MAPI::Util::CommonDateString(ftStartDate);
    }
    if (pEx->m_pEndDate.length() == 0)
    {
        Zimbra::Mapi::CRecurrenceTime rtEndDate = lpException->GetEndDateTime();
        if (lpException->GetAllDay())
        {
            rtEndDate = rtEndDate - 1440;  // for Zimbra
        }
        Zimbra::Mapi::CFileTime ftEndDate = (FILETIME)rtEndDate;
        pEx->m_pEndDate = MakeDateFromExPtr(ftEndDate);
    }
    if (lpException->GetAllDay())   // FBS bug 71053 -- 4/12/12
    {
        pEx->m_pStartDate = pEx->m_pStartDate.substr(0, 8);
        pEx->m_pEndDate = pEx->m_pEndDate.substr(0, 8);
        if (pEx->m_pStartDateForRecID.length() > 0)
        {
            pEx->m_pStartDateForRecID = pEx->m_pStartDateForRecID.substr(0, 8);
        }
    }
    if (pEx->m_pSubject.length() == 0)
    {
        pEx->m_pSubject = (wcslen(lpException->GetSubject()) > 0) ? lpException->GetSubject() : m_pSubject;
    }
    if (pEx->m_pLocation.length() == 0)
    {
        pEx->m_pLocation = (wcslen(lpException->GetLocation()) > 0) ? lpException->GetLocation() : m_pLocation;
    }
    if (pEx->m_pBusyStatus.length() == 0)
    {
        if (this->InterpretBusyStatus() !=  lpException->GetBusyStatus())
        {
            pEx->SetBusyStatus(lpException->GetBusyStatus());
        }
        else
        {
            pEx->m_pBusyStatus = m_pBusyStatus;
        }
    }
    if (pEx->m_pAllday.length() == 0)
    {
        if (this->InterpretAllday() != lpException->GetAllDay())
        {
            pEx->SetAllday((unsigned short)lpException->GetAllDay());
        }
        else
        {
            pEx->m_pAllday = m_pAllday;
        }
    }
    if (pEx->m_pResponseStatus.length() == 0)
    {
        pEx->m_pResponseStatus = m_pResponseStatus;
    }
	if (pEx->m_pCurrentStatus.length() == 0)
    {
        pEx->m_pCurrentStatus = m_pCurrentStatus;
    }
	if (pEx->m_pOrganizerName.length() == 0)
    {
        pEx->m_pOrganizerName = m_pOrganizerName;
    }
    if (pEx->m_pOrganizerAddr.length() == 0)
    {
        pEx->m_pOrganizerAddr = m_pOrganizerAddr;
    }

    if (pEx->m_pReminderMinutes.length() == 0)
    {
        pEx->m_pReminderMinutes = m_pReminderMinutes;
    }
    if (pEx->m_pPrivate.length() == 0)
    {
        pEx->m_pPrivate = m_pPrivate;
    }
	if (pEx->m_pReminderSet.length() == 0)
    {
        pEx->m_pReminderSet = m_pReminderSet;
    }
	if (pEx->m_pResponseRequested.length() == 0)
    {
        pEx->m_pResponseRequested = m_pResponseRequested;
    }
    if (pEx->m_pPlainTextFile.length() == 0)
    {
        pEx->m_pPlainTextFile = m_pPlainTextFile;
    }
    if (pEx->m_pHtmlFile.length() == 0)
    {
        pEx->m_pHtmlFile = m_pHtmlFile;
    }
}

void MAPIAppointment::FillInCancelException(MAPIAppointment* pEx, Zimbra::Mapi::CFileTime cancelDate)
{
    // should really use a copy constructor
    pEx->m_pStartDate = MakeDateFromExPtr(cancelDate);
    pEx->m_pSubject = m_pSubject;
    pEx->m_pLocation = m_pLocation;
    pEx->m_pBusyStatus = m_pBusyStatus;
    pEx->m_pAllday = m_pAllday;
    pEx->m_pResponseStatus = m_pResponseStatus;
	pEx->m_pCurrentStatus = m_pCurrentStatus;
    pEx->m_pOrganizerName = m_pOrganizerName;
    pEx->m_pOrganizerAddr = m_pOrganizerAddr;
    pEx->m_pReminderMinutes = m_pReminderMinutes;
    pEx->m_pPrivate = m_pPrivate;
	pEx->m_pReminderSet = m_pReminderSet;
	pEx->m_pResponseRequested = m_pResponseRequested;
    pEx->m_pPlainTextFile = m_pPlainTextFile;
    pEx->m_pHtmlFile = m_pHtmlFile;
}

void MAPIAppointment::SetSubject(LPTSTR pStr)
{
    m_pSubject = pStr;
}

void MAPIAppointment::SetStartDate(FILETIME ft)
{
    SYSTEMTIME st, localst;
    BOOL bUseLocal = false;

    FileTimeToSystemTime(&ft, &st);
    if ((m_bIsRecurring) || (m_iExceptionType == NORMAL_EXCEPTION))
    {
	TIME_ZONE_INFORMATION localTimeZone = {0};
	pInvTz->PopulateTimeZoneInfo(localTimeZone);
	//GetTimeZoneInformation(&localTimeZone);	
	bUseLocal = SystemTimeToTzSpecificLocalTime(&localTimeZone, &st, &localst);
    }
    m_pStartDate = (bUseLocal) ? Zimbra::Util::FormatSystemTime(localst, FALSE, TRUE)
			       : Zimbra::Util::FormatSystemTime(st, TRUE, TRUE);
    m_pCalFilterDate = Zimbra::MAPI::Util::CommonDateString(m_pPropVals[C_START].Value.ft);   // may have issue with recur/local
}

LPWSTR MAPIAppointment::MakeDateFromExPtr(FILETIME ft)
{
    SYSTEMTIME st;

    FileTimeToSystemTime(&ft, &st);
    return Zimbra::Util::FormatSystemTime(st, FALSE, TRUE);			       
}

void MAPIAppointment::SetEndDate(FILETIME ft, bool bAllday)
{
    SYSTEMTIME st, localst;
    BOOL bUseLocal = false;

    FileTimeToSystemTime(&ft, &st);

    if (bAllday)    // if AllDay appt, subtract one from the end date for Zimbra friendliness
    {
	double dat = -1;
	if (SystemTimeToVariantTime(&st, &dat))
	{
	    dat -= 1;
	    VariantTimeToSystemTime(dat, &st);
	}
    }
    else
    {
	if ((m_bIsRecurring) || (m_iExceptionType == NORMAL_EXCEPTION))
	{
	    TIME_ZONE_INFORMATION localTimeZone = {0};
	    //GetTimeZoneInformation(&localTimeZone);	
		pInvTz->PopulateTimeZoneInfo(localTimeZone);
	    bUseLocal = SystemTimeToTzSpecificLocalTime(&localTimeZone, &st, &localst);
	}
    }
    m_pEndDate = (bUseLocal) ? Zimbra::Util::FormatSystemTime(localst, FALSE, TRUE)
			     : Zimbra::Util::FormatSystemTime(st, TRUE, TRUE);
}

void MAPIAppointment::SetInstanceUID(LPSBinary bin)
{
    Zimbra::Util::ScopedArray<CHAR> spUid(new CHAR[(bin->cb * 2) + 1]);
    if (spUid.get() != NULL)
    {
	Zimbra::Util::HexFromBin(bin->lpb, bin->cb, spUid.get());
    }
    m_pInstanceUID = Zimbra::Util::AnsiiToUnicode(spUid.get());
}

void MAPIAppointment::SetLocation(LPTSTR pStr)
{
    m_pLocation = pStr;
}

void MAPIAppointment::SetBusyStatus(long busystatus)
{
    switch (busystatus)
    {
	case oFree:		m_pBusyStatus = L"F";	break;
	case oTentative:	m_pBusyStatus = L"T";	break;
	case oBusy:		m_pBusyStatus = L"B";	break;
	case oOutOfOffice:	m_pBusyStatus = L"O";	break;
	default:		m_pBusyStatus = L"T";
    }
}

ULONG MAPIAppointment::InterpretBusyStatus()
{
    long retval;
    if (m_pBusyStatus == L"F") 
    {
        retval = oFree;
    }
    if (m_pBusyStatus == L"T") 
    {
        retval = oTentative;
    }
    if (m_pBusyStatus == L"B") 
    {
        retval = oBusy;
    }
    if (m_pBusyStatus == L"O") 
    {
        retval = oOutOfOffice;
    }
    else
    {
        retval = oFree;
    }
    return retval;
}

wstring MAPIAppointment::ConvertValueToRole(long role)
{
    wstring retval = L"REQ";
    switch (role)
    {
	case oOrganizer:    retval = L"CHAIR";	break;
	case oRequired:	    retval = L"REQ";	break;
	case oOptional:	    retval = L"OPT";	break;
	case oResource:	    retval = L"NON";	break;
	default:	    ;
    }
    return retval;
}

wstring MAPIAppointment::ConvertValueToPartStat(long ps)
{
    wstring retval = L"NE";
    switch (ps)
    {
	case oResponseNone:	    retval = L"NE";	break;
	case oResponseOrganized:    retval = L"OR";	break;
	case oResponseTentative:    retval = L"TE";	break;
	case oResponseAccepted:	    retval = L"AC";	break;
	case oResponseDeclined:	    retval = L"DE";	break;
	case oResponseNotResponded: retval = L"NE";	break;
	default:		    ;
    }
    return retval;
}

void MAPIAppointment::SetAllday(unsigned short usAllday)
{
    m_pAllday = (usAllday == 1) ? L"1" : L"0";
}

BOOL MAPIAppointment::InterpretAllday()
{
    return (m_pAllday == L"1");
}

void MAPIAppointment::SetTransparency(LPTSTR pStr)
{
    m_pTransparency = pStr;
}

void MAPIAppointment::SetResponseStatus(long responsestatus)
{
    switch (responsestatus)
    {
	case oResponseNone:		m_pResponseStatus = L"NE";	break;
	case oResponseOrganized:	m_pResponseStatus = L"OR";	break;	    // OR????  -- temporary
	case oResponseTentative:	m_pResponseStatus = L"TE";	break;
	case oResponseAccepted:		m_pResponseStatus = L"AC";	break;
	case oResponseDeclined:		m_pResponseStatus = L"DE";	break;
	case oResponseNotResponded:	m_pResponseStatus = L"NE";	break;
	default:			m_pResponseStatus = L"NE";
    }
	m_pCurrentStatus = m_pResponseStatus;
}

void MAPIAppointment::SetReminderMinutes(long reminderminutes)
{
    WCHAR pwszTemp[10];
    _ltow(reminderminutes, pwszTemp, 10);
    m_pReminderMinutes = pwszTemp;
}

void MAPIAppointment::SetPrivate(unsigned short usPrivate)
{
    m_pPrivate = (usPrivate == 1) ? L"1" : L"0";
}

void MAPIAppointment::SetReminderSet(unsigned short usReminderset)
{
	m_pReminderSet = (usReminderset == 1) ? L"1" : L"0";
}

void MAPIAppointment::SetResponseRequested(unsigned short usPrivate)
{
    m_pResponseRequested = (usPrivate == 1) ? L"1" : L"0";
}
void MAPIAppointment::SetPlainTextFileAndContent()
{
    m_pPlainTextFile = Zimbra::MAPI::Util::SetPlainText(m_pMessage, &m_pPropVals[C_BODY]);
}

void MAPIAppointment::SetHtmlFileAndContent()
{
    m_pHtmlFile = Zimbra::MAPI::Util::SetHtml(m_pMessage, &m_pPropVals[C_HTMLBODY]);
}

void MAPIAppointment::SetExceptionType(int type)
{
    if (type == NORMAL_EXCEPTION)
    {
        m_pExceptionType = L"except";
    }
    else
    if (type == CANCEL_EXCEPTION)
    {
        m_pExceptionType = L"cancel";
    }
    else
    {
        m_pExceptionType = L"none";
    }
}

HRESULT MAPIAppointment::UpdateAttendeeFromEntryId(Attendee &pAttendee,
    SBinary &eid)
{
    Zimbra::Util::ScopedInterface<IMailUser> pUser;
    ULONG ulObjType = 0;
	if(!m_pAddrBook)
		return E_FAIL;
    HRESULT hr = m_pAddrBook->OpenEntry(eid.cb, (LPENTRYID)eid.lpb, NULL, MAPI_BEST_ACCESS,
        &ulObjType, (LPUNKNOWN *)pUser.getptr());

    if (FAILED(hr) || (ulObjType != MAPI_MAILUSER))
        return E_FAIL;
    SizedSPropTagArray(3, tags) = {
        3, { PR_ADDRTYPE_W, PR_DISPLAY_NAME_W, PR_EMAIL_ADDRESS_W }
    };

    ULONG cVals = 0;
    Zimbra::Util::ScopedBuffer<SPropValue> pVals;

    hr = pUser->GetProps((LPSPropTagArray) & tags, MAPI_UNICODE, &cVals, pVals.getptr());
    if (FAILED(hr))
        return hr;
    // no smtp address, bail
    if ((pVals->ulPropTag != PR_ADDRTYPE_W) || (wcsicmp(pVals->Value.lpszW, L"SMTP") != 0))
        return E_FAIL;
    if ((pVals[1].ulPropTag == PR_DISPLAY_NAME_W) && ((pAttendee.nam == L"")))
        pAttendee.nam = pVals[1].Value.lpszW;
    if ((pVals[2].ulPropTag == PR_EMAIL_ADDRESS_W) && (pAttendee.addr == L""))
        pAttendee.addr=pVals[2].Value.lpszW;

	return S_OK;
}

HRESULT MAPIAppointment::SetOrganizerAndAttendees()
{
    Zimbra::Util::ScopedInterface<IMAPITable> pRecipTable;
    HRESULT hr = 0;

    hr = m_pMessage->GetRecipientTable(fMapiUnicode, pRecipTable.getptr());
    if (FAILED(hr))
    {
        return hr;
    }

    typedef enum _AttendeePropTagIdx
    {
        AT_ADDRTYPE, AT_ENTRYID, AT_DISPLAY_NAME, AT_SMTP_ADDR, AT_RECIPIENT_FLAGS, AT_RECIPIENT_TYPE, AT_RECIPIENT_TRACKSTATUS,AT_EMAIL_ADDRESS, AT_NPROPS
    } AttendeePropTagIdx;

    SizedSPropTagArray(AT_NPROPS, reciptags) = {
        AT_NPROPS, { PR_ADDRTYPE, PR_ENTRYID, PR_DISPLAY_NAME_W, PR_SMTP_ADDRESS_W, PR_RECIPIENT_FLAGS, PR_RECIPIENT_TYPE, PR_RECIPIENT_TRACKSTATUS,PR_EMAIL_ADDRESS }
    };

    ULONG ulRows = 0;
    Zimbra::Util::ScopedRowSet pRecipRows;

    hr = pRecipTable->SetColumns((LPSPropTagArray) & reciptags, 0);
    if (FAILED(hr))
    {
	//LOG_ERROR(_T("could not get the recipient table, hr: %x"), hr);
        return hr;
    }
    hr = pRecipTable->GetRowCount(0, &ulRows);
    if (FAILED(hr))
    {
	//LOG_ERROR(_T("could not get the recipient table row count, hr: %x"), hr);
        return hr;
    }
    hr = pRecipTable->QueryRows(ulRows, 0, pRecipRows.getptr());
    if (FAILED(hr))
    {
        //LOG_ERROR(_T("Failed to query table rows. hr: %x"), hr);
        return hr;
    }
    if (pRecipRows != NULL)
    {
	for (ULONG iRow = 0; iRow < pRecipRows->cRows; iRow++)
        {
	    if (pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_FLAGS].ulPropTag ==
                reciptags.aulPropTag[AT_RECIPIENT_FLAGS])
            {
                if (pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_FLAGS].Value.l == 3)
		{
                    if (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_DISPLAY_NAME].ulPropTag) != PT_ERROR)
                    {
						m_pOrganizerName = pRecipRows->aRow[iRow].lpProps[AT_DISPLAY_NAME].Value.lpszW;
                    }
                    if (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_SMTP_ADDR].ulPropTag) != PT_ERROR)
                    {
						 m_pOrganizerAddr = pRecipRows->aRow[iRow].lpProps[AT_SMTP_ADDR].Value.lpszW;
                    }
					if((lstrcmpiW(m_pOrganizerAddr.c_str(),L"") == 0) ||(m_pOrganizerName ==L""))
					{
						Attendee* pAttendee = new Attendee(); 
						dlogi("Going to Update organizer from EID...");
						if(UpdateAttendeeFromEntryId(*pAttendee,pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].Value.bin) !=S_OK)
						{
							dlogi("Going to update organizer from AD");
							RECIP_INFO tempRecip;

							tempRecip.pAddrType = NULL;
							tempRecip.pEmailAddr = NULL;
							tempRecip.cbEid = 0;
							tempRecip.pEid = NULL;

							tempRecip.pAddrType =
								pRecipRows->aRow[iRow].lpProps[AT_ADDRTYPE].Value.lpszW;
							tempRecip.pEmailAddr =
								pRecipRows->aRow[iRow].lpProps[AT_EMAIL_ADDRESS].Value.lpszW;
							if (pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].ulPropTag != PT_ERROR)
							{
								tempRecip.cbEid =
									pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].Value.bin.cb;
								tempRecip.pEid =
									(LPENTRYID)pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].Value
									.bin.lpb;
							}
							std::wstring wstrEmailAddress;
							try
							{

							Zimbra::MAPI::Util::GetSMTPFromAD(*m_session, tempRecip,L"" , L"",wstrEmailAddress);
							}
							catch(...)
							{
								dlogw("mapiappointment::exception from MAPi::util::GetSMTPFromAD ");
							}
							pAttendee->addr = wstrEmailAddress;
							dlogi("Email address(AD):",wstrEmailAddress);
							dlogi("AD update end.");
						}
						dlogi("EID update end.");
						if(m_pOrganizerAddr==L"") 
							m_pOrganizerAddr = pAttendee->addr;
						if(m_pOrganizerName==L"")
							m_pOrganizerName = pAttendee->nam;
					}
					dlogi("OrganizerAddr: ",m_pOrganizerAddr, "  OrganizerName: ",m_pOrganizerName);
		}
		else
		{
                    if (!(RECIP_FLAG_EXCEP_DELETED & pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_FLAGS].Value.l)) // make sure attendee wasn't deleted
                    {
		        Attendee* pAttendee = new Attendee();   // delete done in CMapiAccessWrap::GetData after we allocate dict string for ZimbraAPI
			    if (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_DISPLAY_NAME].ulPropTag) != PT_ERROR)
				    pAttendee->nam = pRecipRows->aRow[iRow].lpProps[AT_DISPLAY_NAME].Value.lpszW;
				
			    if (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_SMTP_ADDR].ulPropTag) != PT_ERROR)
				    pAttendee->addr = pRecipRows->aRow[iRow].lpProps[AT_SMTP_ADDR].Value.lpszW;
				wstring attaddress = pAttendee->addr;
				if(lstrcmpiW(attaddress.c_str(),L"") == 0) 
				{
					if (((pAttendee->nam==L"") || (pAttendee->addr==L"")) && (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].ulPropTag) != PT_ERROR))
					{
						dlogi("Going to Update attendee from EID...");
					 if(UpdateAttendeeFromEntryId(*pAttendee,pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].Value.bin)!=S_OK)
					 {
						dlogi("Going to update attendee from AD");				
						RECIP_INFO tempRecip;
						tempRecip.pAddrType = NULL;
						tempRecip.pEmailAddr = NULL;
						tempRecip.cbEid = 0;
						tempRecip.pEid = NULL;

						tempRecip.pAddrType =
							pRecipRows->aRow[iRow].lpProps[AT_ADDRTYPE].Value.lpszW;
						tempRecip.pEmailAddr =
							pRecipRows->aRow[iRow].lpProps[AT_EMAIL_ADDRESS].Value.lpszW;
						if (pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].ulPropTag != PT_ERROR)
						{
							tempRecip.cbEid =
								pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].Value.bin.cb;
							tempRecip.pEid =
								(LPENTRYID)pRecipRows->aRow[iRow].lpProps[AT_ENTRYID].Value
								.bin.lpb;
						}
						std::wstring wstrEmailAddress;
						try
						{
						Zimbra::MAPI::Util::GetSMTPFromAD(*m_session, tempRecip,L"" , L"",wstrEmailAddress);
						}
						catch(...)
						{
							dlogw(" Mapiappoinemtn::Exception in MAPI::Util::GetSMTPFromAD");
						}
						pAttendee->addr = wstrEmailAddress;
						dlogi("Email address(AD):",wstrEmailAddress);
						dlogi("AD update end.");
					}
					 dlogi("EID update end.");
					}
				}
				dlogi("AttendeeAddr: ",pAttendee->addr,"  AttendeeName :",pAttendee->nam);
			    if (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_TYPE].ulPropTag) != PT_ERROR)
				    pAttendee->role = ConvertValueToRole(pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_TYPE].Value.l);
				if (PROP_TYPE(pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_TRACKSTATUS].ulPropTag) != PT_ERROR)
				    pAttendee->partstat = ConvertValueToPartStat(pRecipRows->aRow[iRow].lpProps[AT_RECIPIENT_TRACKSTATUS].Value.l);
						m_vAttendees.push_back(pAttendee);
                    }
		}
	    }
        }
    }
    return hr;
}

wstring MAPIAppointment::GetSubject() { return m_pSubject; }
wstring MAPIAppointment::GetStartDate() { return m_pStartDate; }
wstring MAPIAppointment::GetCalFilterDate() { return m_pCalFilterDate; }
wstring MAPIAppointment::GetStartDateForRecID() { return m_pStartDateForRecID; }
wstring MAPIAppointment::GetEndDate() { return m_pEndDate; }
wstring MAPIAppointment::GetInstanceUID() { return m_pInstanceUID; }
wstring MAPIAppointment::GetLocation() { return m_pLocation; }
wstring MAPIAppointment::GetBusyStatus() { return m_pBusyStatus; }
wstring MAPIAppointment::GetAllday() { return m_pAllday; }
wstring MAPIAppointment::GetTransparency() { return m_pTransparency; }
wstring MAPIAppointment::GetReminderMinutes() { return m_pReminderMinutes; }
wstring MAPIAppointment::GetResponseStatus() { return m_pResponseStatus; }
wstring MAPIAppointment::GetCurrentStatus() { return m_pCurrentStatus; }
wstring MAPIAppointment::GetResponseRequested() { return m_pResponseRequested; }
wstring MAPIAppointment::GetOrganizerName() { return m_pOrganizerName; }
wstring MAPIAppointment::GetOrganizerAddr() { return m_pOrganizerAddr; }
wstring MAPIAppointment::GetPrivate() { return m_pPrivate; }
wstring MAPIAppointment::GetReminderSet() {return m_pReminderSet;}
wstring MAPIAppointment::GetPlainTextFileAndContent() { return m_pPlainTextFile; }
wstring MAPIAppointment::GetHtmlFileAndContent() { return m_pHtmlFile; }
vector<Attendee*> MAPIAppointment::GetAttendees() { return m_vAttendees; }
vector<MAPIAppointment*> MAPIAppointment::GetExceptions() { return m_vExceptions; }
wstring MAPIAppointment::GetExceptionType() { return m_pExceptionType; }
