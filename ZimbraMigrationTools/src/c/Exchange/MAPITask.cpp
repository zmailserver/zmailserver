/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2012 VMware, Inc.
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
#include "MAPIRfc2445.h"
#include "MAPITask.h"
#include "Logger.h"

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// MAPIAppointmentException
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
MAPITaskException::MAPITaskException(HRESULT hrErrCode, LPCWSTR
    lpszDescription): GenericException(hrErrCode, lpszDescription)
{
    //
}

MAPITaskException::MAPITaskException(HRESULT hrErrCode, LPCWSTR lpszDescription, LPCWSTR lpszShortDescription, 
	int nLine, LPCSTR strFile): GenericException(hrErrCode, lpszDescription, lpszShortDescription, nLine, strFile)
{
    //
}

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// MAPITask
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//bool MAPITask::m_bNamedPropsInitialized = false;

MAPITask::MAPITask(Zimbra::MAPI::MAPISession &session, Zimbra::MAPI::MAPIMessage &mMessage)
                  : MAPIRfc2445 (session, mMessage)
{
    //if (MAPITask::m_bNamedPropsInitialized == false)
    //{
        pr_isrecurringt = 0;
        pr_recurstreamt = 0;
	pr_status = 0;
	pr_percentcomplete = 0;
	pr_taskstart = 0;
	pr_taskdue = 0;
	pr_totalwork = 0;
	pr_actualwork = 0;
	pr_companies = 0;
	pr_mileage = 0;
	pr_billinginfo = 0;
        pr_taskreminderset = 0;
        pr_taskflagdueby = 0;
        pr_private = 0;
	InitNamedPropsForTask();
    //}

    m_pSubject = L"";
    m_pImportance = L"";
    m_pTaskStart = L"";
    m_pTaskDue = L"";
    m_pStatus = L"";
    m_pPercentComplete = L"";
    m_pTotalWork = L"";
    m_pActualWork = L"";
    m_pCompanies = L"";
    m_pMileage = L"";
    m_pBillingInfo = L"";
    m_pTaskFlagDueBy = L"";
    m_pPrivate = L"";

    SetMAPITaskValues();
}

MAPITask::~MAPITask()
{
    if (m_pPropVals)
    {
        MAPIFreeBuffer(m_pPropVals);
    }
    m_pPropVals = NULL;
}

HRESULT MAPITask::InitNamedPropsForTask()
{
    // init named props
    nameIds[0] = 0x8126;
    nameIds[1] = 0x8116;
    nameIds[2] = 0x8101;
    nameIds[3] = 0x8102;
    nameIds[4] = 0x8104;
    nameIds[5] = 0x8105;
    nameIds[6] = 0x8111;
    nameIds[7] = 0x8110;

    nameIdsC[0] = 0x8539;
    nameIdsC[1] = 0x8534;
    nameIdsC[2] = 0x8535;
    nameIdsC[3] = 0x8503;
    nameIdsC[4] = 0x8560;
    nameIdsC[5] = 0x8506;

    HRESULT hr = S_OK;
    Zimbra::Util::ScopedBuffer<SPropValue> pPropValMsgClass;

    if (FAILED(hr = HrGetOneProp(m_pMessage, PR_MESSAGE_CLASS, pPropValMsgClass.getptr())))
        throw MAPITaskException(hr, L"InitNamedPropsForTask(): HrGetOneProp Failed.", 
		ERR_MAPI_TASK, __LINE__, __FILE__);

    // initialize the MAPINAMEID structure GetIDsFromNames requires
    LPMAPINAMEID ppNames[N_NUMTASKPROPS] = { 0 };
    for (int i = 0; i < N_NUMTASKPROPS; i++)
    {
        MAPIAllocateBuffer(sizeof (MAPINAMEID), (LPVOID *)&(ppNames[i]));
        ppNames[i]->ulKind = MNID_ID;
        ppNames[i]->lpguid = (LPGUID)(&PS_OUTLOOK_TASK);
        ppNames[i]->Kind.lID = nameIds[i];
    }

    LPMAPINAMEID ppNamesC[N_NUMCOMMONTPROPS] = { 0 };
    for (int i = 0; i < N_NUMCOMMONTPROPS; i++)
    {
        MAPIAllocateBuffer(sizeof (MAPINAMEID), (LPVOID *)&(ppNamesC[i]));
        ppNamesC[i]->ulKind = MNID_ID;
        ppNamesC[i]->lpguid = (LPGUID)(&PS_OUTLOOK_COMMON);
        ppNamesC[i]->Kind.lID = nameIdsC[i];
    }

    // get the real prop tag ID's
    LPSPropTagArray pTaskTags  = NULL;
    LPSPropTagArray pTaskTagsC = NULL;

    if (FAILED(hr = m_pMessage->GetIDsFromNames(N_NUMTASKPROPS, ppNames, MAPI_CREATE,
            &pTaskTags)))
        throw MAPITaskException(hr, L"Init(): GetIDsFromNames on pTaskTags Failed.", ERR_MAPI_TASK, 
		__LINE__, __FILE__);

    if (FAILED(hr = m_pMessage->GetIDsFromNames(N_NUMCOMMONTPROPS, ppNamesC, MAPI_CREATE,
            &pTaskTagsC)))
        throw MAPITaskException(hr, L"Init(): GetIDsFromNames on pAppointmentTagsC Failed.", ERR_MAPI_TASK, 
		__LINE__, __FILE__);

    // give the prop tag ID's a type
    pr_isrecurringt = SetPropType(pTaskTags->aulPropTag[N_ISRECURT], PT_BOOLEAN);
    pr_recurstreamt = SetPropType(pTaskTags->aulPropTag[N_RECURSTREAMT], PT_BINARY);
    pr_status = SetPropType(pTaskTags->aulPropTag[N_STATUS], PT_LONG);
    pr_percentcomplete = SetPropType(pTaskTags->aulPropTag[N_PERCENTCOMPLETE], PT_DOUBLE);
    pr_taskstart = SetPropType(pTaskTags->aulPropTag[N_TASKSTART], PT_SYSTIME);
    pr_taskdue = SetPropType(pTaskTags->aulPropTag[N_TASKDUE], PT_SYSTIME);
    pr_totalwork = SetPropType(pTaskTags->aulPropTag[N_TOTALWORK], PT_LONG);
    pr_actualwork = SetPropType(pTaskTags->aulPropTag[N_ACTUALWORK], PT_LONG);
    pr_companies = SetPropType(pTaskTagsC->aulPropTag[N_COMPANIES], PT_MV_TSTRING);
    pr_mileage = SetPropType(pTaskTagsC->aulPropTag[N_MILEAGE], PT_TSTRING);
    pr_billinginfo = SetPropType(pTaskTagsC->aulPropTag[N_BILLING], PT_TSTRING);
    pr_taskreminderset = SetPropType(pTaskTagsC->aulPropTag[N_TASKREMINDERSET], PT_BOOLEAN);
    pr_taskflagdueby = SetPropType(pTaskTagsC->aulPropTag[N_TASKFLAGDUEBY], PT_SYSTIME);
    pr_private = SetPropType(pTaskTagsC->aulPropTag[N_TPRIVATE], PT_BOOLEAN);

    // free the memory we allocated on the head
    for (int i = 0; i < N_NUMTASKPROPS; i++)
    {
        MAPIFreeBuffer(ppNames[i]);
    }
    for (int i = 0; i < N_NUMCOMMONTPROPS; i++)
    {
        MAPIFreeBuffer(ppNamesC[i]);
    }
    MAPIFreeBuffer(pTaskTags);
    MAPIFreeBuffer(pTaskTagsC);

    //MAPITask::m_bNamedPropsInitialized = true;

    return S_OK;
}

HRESULT MAPITask::SetMAPITaskValues()
{
    SizedSPropTagArray(T_NUMALLTASKPROPS, taskProps) = {
	T_NUMALLTASKPROPS, {
	    PR_MESSAGE_FLAGS, PR_SUBJECT, PR_BODY, PR_HTML, PR_IMPORTANCE, pr_isrecurringt, pr_recurstreamt, pr_status,
	    pr_percentcomplete, pr_taskstart, pr_taskdue, pr_totalwork,
	    pr_actualwork, pr_companies, pr_mileage, pr_billinginfo,
            pr_taskreminderset, pr_taskflagdueby, pr_private
	}
    };

    HRESULT hr = S_OK;
    ULONG cVals = 0;
    m_bHasAttachments = false;
    m_bIsRecurring = false;
    m_bIsTaskReminderSet = false;

    if (FAILED(hr = m_pMessage->GetProps((LPSPropTagArray) & taskProps, fMapiUnicode, &cVals,
            &m_pPropVals)))
        throw MAPITaskException(hr, L"SetMAPITaskValues(): GetProps Failed.", 
		ERR_MAPI_TASK, __LINE__, __FILE__);

    if (m_pPropVals[T_MESSAGE_FLAGS].ulPropTag == taskProps.aulPropTag[T_MESSAGE_FLAGS])
    {
        m_bHasAttachments = (m_pPropVals[T_MESSAGE_FLAGS].Value.l & MSGFLAG_HASATTACH) != 0;
    }
    if (m_pPropVals[T_ISRECURT].ulPropTag == taskProps.aulPropTag[T_ISRECURT]) // do this first to set dates correctly
    {
	m_bIsRecurring = (m_pPropVals[T_ISRECURT].Value.b == 1);
    }
    if (m_pPropVals[T_TASKREMINDERSET].ulPropTag == taskProps.aulPropTag[T_TASKREMINDERSET]) // do this first to set dates correctly
    {
	m_bIsTaskReminderSet = (m_pPropVals[T_TASKREMINDERSET].Value.b == 1);
    }
    if (m_pPropVals[T_SUBJECT].ulPropTag == taskProps.aulPropTag[T_SUBJECT])
    {
	SetSubject(m_pPropVals[T_SUBJECT].Value.lpszW);
    }
    if (m_pPropVals[T_IMPORTANCE].ulPropTag == taskProps.aulPropTag[T_IMPORTANCE])
    {
	SetImportance(m_pPropVals[T_IMPORTANCE].Value.l);
    }
    if (m_pPropVals[T_STATUS].ulPropTag == taskProps.aulPropTag[T_STATUS])
    {
	SetTaskStatus(m_pPropVals[T_STATUS].Value.l);
    }
    if (m_pPropVals[T_PERCENTCOMPLETE].ulPropTag == taskProps.aulPropTag[T_PERCENTCOMPLETE])
    {
	SetPercentComplete(m_pPropVals[T_PERCENTCOMPLETE].Value.dbl);
    }
    if (m_pPropVals[T_TASKSTART].ulPropTag == taskProps.aulPropTag[T_TASKSTART])
    {
	SetTaskStart(m_pPropVals[T_TASKSTART].Value.ft);
    }
    if (m_pPropVals[T_TASKDUE].ulPropTag == taskProps.aulPropTag[T_TASKDUE])
    {
        SetTaskDue(m_pPropVals[T_TASKDUE].Value.ft);
    }
    if (m_pPropVals[T_TOTALWORK].ulPropTag == taskProps.aulPropTag[T_TOTALWORK])
    {
	SetTotalWork(m_pPropVals[T_TOTALWORK].Value.l);
    }
    if (m_pPropVals[T_ACTUALWORK].ulPropTag == taskProps.aulPropTag[T_ACTUALWORK])
    {
	SetActualWork(m_pPropVals[T_ACTUALWORK].Value.l);
    }
    if (m_pPropVals[T_COMPANIES].ulPropTag == taskProps.aulPropTag[T_COMPANIES])
    {
	SetCompanies(m_pPropVals[T_COMPANIES].Value.MVszW.lppszW[0]);	// get first one for now
    }
    if (m_pPropVals[T_MILEAGE].ulPropTag == taskProps.aulPropTag[T_MILEAGE])
    {
	SetMileage(m_pPropVals[T_MILEAGE].Value.lpszW);
    }
    if (m_pPropVals[T_BILLING].ulPropTag == taskProps.aulPropTag[T_BILLING])
    {
	SetBillingInfo(m_pPropVals[T_BILLING].Value.lpszW);
    }
    if (m_pPropVals[T_TASKFLAGDUEBY].ulPropTag == taskProps.aulPropTag[T_TASKFLAGDUEBY])
    {
        SetTaskFlagDueBy(m_pPropVals[T_TASKFLAGDUEBY].Value.ft);
    }
    if (m_pPropVals[T_PRIVATE].ulPropTag == taskProps.aulPropTag[T_PRIVATE])
    {
	SetPrivate(m_pPropVals[T_PRIVATE].Value.b);
    }

    SetPlainTextFileAndContent();
    SetHtmlFileAndContent();

    if (m_bHasAttachments)
    {
        if (FAILED(ExtractAttachments()))
        {
            dlogw(L"Could not extract attachments");
        }
    }

    if (m_bIsRecurring)
    {
	if (m_pPropVals[T_RECURSTREAMT].ulPropTag == taskProps.aulPropTag[T_RECURSTREAMT])
	{
	    SetRecurValues();
	}
    }

    return hr;
}

void MAPITask::SetRecurValues()
{
    Zimbra::Util::ScopedInterface<IStream> pRecurrenceStream;
    HRESULT hResult = m_pMessage->OpenProperty(pr_recurstreamt, &IID_IStream, 0, 0,
						(LPUNKNOWN *)pRecurrenceStream.getptr());
    if (FAILED(hResult))
    {
	return;
    }
    LPSTREAM pStream = pRecurrenceStream.get();
    Zimbra::Mapi::Task OlkTask(m_pMessage, NULL);
    Zimbra::Mapi::COutlookRecurrencePattern &recur = OlkTask.GetRecurrencePattern();
    hResult = recur.ReadRecurrenceStream(pStream);
    if (FAILED(hResult))
    {
	return;
    }

    /*
    // Set Timezone info
    SYSTEMTIME stdTime;
    SYSTEMTIME dsTime;
    const Zimbra::Mail::TimeZone &tzone = recur.GetTimeZone();
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
    */

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
    m_pTaskFilterDate = Zimbra::MAPI::Util::CommonDateString(ft);
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
}

void MAPITask::SetSubject(LPTSTR pStr)
{
    m_pSubject = pStr;
}

void MAPITask::SetImportance(long importance)
{
    switch (importance)
    {
        case oImportanceLow:    m_pImportance = L"9";   break;
        case oImportanceNormal: m_pImportance = L"5";   break;
        case oImportanceHigh:   m_pImportance = L"1";   break;
        default:                m_pImportance = L"5";
    }
}

void MAPITask::SetTaskStatus(long taskstatus)
{
    switch (taskstatus)
    {
	case oTaskNotStarted:	m_pStatus = L"NEED";	    break;
	case oTaskInProgress:	m_pStatus = L"INPR";	    break;
	case oTaskComplete:	m_pStatus = L"COMP";	    break;
	case oTaskWaiting:	m_pStatus = L"WAITING";	    break;
	case oTaskDeferred:	m_pStatus = L"DEFERRED";    break;
	default:		m_pStatus = L"NEED";
    }
}

void MAPITask::SetPercentComplete(double percentcomplete)
{
    long lPercent = (long)(percentcomplete * 100);
    WCHAR pwszTemp[10];
    _ltow(lPercent, pwszTemp, 10);
    m_pPercentComplete = pwszTemp;
}

void MAPITask::SetTaskStart(FILETIME ft)
{
    SYSTEMTIME st;

    FileTimeToSystemTime(&ft, &st);
    m_pTaskStart = Zimbra::Util::FormatSystemTime(st, FALSE, FALSE);
    m_pTaskFilterDate = Zimbra::MAPI::Util::CommonDateString(m_pPropVals[T_TASKDUE].Value.ft);
}

void MAPITask::SetTaskDue(FILETIME ft)
{
    SYSTEMTIME st;

    FileTimeToSystemTime(&ft, &st);
    m_pTaskDue = Zimbra::Util::FormatSystemTime(st, FALSE, FALSE);
}

void MAPITask::SetTotalWork(long totalwork)
{
    WCHAR pwszTemp[10];
    _ltow(totalwork, pwszTemp, 10);
    m_pTotalWork = pwszTemp;
}

void MAPITask::SetActualWork(long actualwork)
{
    WCHAR pwszTemp[10];
    _ltow(actualwork, pwszTemp, 10);
    m_pActualWork = pwszTemp;
}

void MAPITask::SetCompanies(LPTSTR pStr)    // deal with more than one at some point
{
    m_pCompanies = pStr;
}

void MAPITask::SetMileage(LPTSTR pStr)
{
    m_pMileage = pStr;
}

void MAPITask::SetBillingInfo(LPTSTR pStr)
{
    m_pBillingInfo = pStr;
}

void MAPITask::SetTaskFlagDueBy(FILETIME ft)
{
    SYSTEMTIME st;

    FileTimeToSystemTime(&ft, &st);
    m_pTaskFlagDueBy = (st.wYear == 1601) ? L"" : Zimbra::Util::FormatSystemTime(st, TRUE, TRUE);
}

void MAPITask::SetPrivate(unsigned short usPrivate)
{
    m_pPrivate = (usPrivate == 1) ? L"1" : L"0";
}

void MAPITask::SetPlainTextFileAndContent()
{
    m_pPlainTextFile = Zimbra::MAPI::Util::SetPlainText(m_pMessage, &m_pPropVals[T_BODY]);
}

void MAPITask::SetHtmlFileAndContent()
{
    m_pHtmlFile = Zimbra::MAPI::Util::SetHtml(m_pMessage, &m_pPropVals[T_HTMLBODY]);
}

bool    MAPITask::IsTaskReminderSet() {return m_bIsTaskReminderSet; }
wstring MAPITask::GetSubject() { return m_pSubject; }
wstring MAPITask::GetImportance() { return m_pImportance; }
wstring MAPITask::GetTaskStatus() { return m_pStatus; }
wstring MAPITask::GetPercentComplete() { return m_pPercentComplete; }
wstring MAPITask::GetTaskStart() { return m_pTaskStart; }
wstring MAPITask::GetTaskFilterDate() { return m_pTaskFilterDate; }
wstring MAPITask::GetTaskDue() { return m_pTaskDue; }
wstring MAPITask::GetTotalWork() { return m_pTotalWork; }
wstring MAPITask::GetActualWork() { return m_pActualWork; }
wstring MAPITask::GetMileage() { return m_pMileage; }
wstring MAPITask::GetCompanies() { return m_pCompanies; }
wstring MAPITask::GetBillingInfo() { return m_pBillingInfo; }
wstring MAPITask::GetTaskFlagDueBy() { return m_pTaskFlagDueBy; }
wstring MAPITask::GetPrivate() { return m_pPrivate; }
wstring MAPITask::GetPlainTextFileAndContent() { return m_pPlainTextFile; }
wstring MAPITask::GetHtmlFileAndContent() { return m_pHtmlFile; }

