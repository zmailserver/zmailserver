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
#include "common.h"
#include "Exchange.h"
#include "MAPISession.h"
#include "MAPIStore.h"

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// Exception class
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
MAPISessionException::MAPISessionException(HRESULT hrErrCode, LPCWSTR
    lpszDescription): GenericException(hrErrCode, lpszDescription)
{
    //
}

MAPISessionException::MAPISessionException(HRESULT hrErrCode, LPCWSTR lpszDescription, LPCWSTR lpszShortDescription, 
	int nLine, LPCSTR strFile): GenericException(hrErrCode, lpszDescription, lpszShortDescription, nLine, strFile)
{
    //
}

// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
// MAPI Session Class
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

MAPISession::MAPISession(): m_Session(NULL)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);

    MAPIINIT_0 MAPIInit;
   MAPIInit.ulFlags = MAPI_NO_COINIT | 0;
   MAPIInit.ulVersion = MAPI_INIT_VERSION;
   HRESULT hr= MAPIInitialize(&MAPIInit);
       
    UNREFERENCED_PARAMETER(hr);
    Zimbra::Mapi::Memory::SetMemAllocRoutines(NULL, MAPIAllocateBuffer, MAPIAllocateMore,
        MAPIFreeBuffer);
}

MAPISession::~MAPISession()
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);

    if (m_Session != NULL)
    {
        m_Session->Logoff(NULL, 0, 0);
        UlRelease(m_Session);
        m_Session = NULL;
    }
    MAPIUninitialize();
}

HRESULT MAPISession::_mapiLogon(LPWSTR strProfile, DWORD dwFlags, LPMAPISESSION &session)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);
    HRESULT hr = S_OK;

    if (FAILED(hr = MAPILogonEx(0, strProfile, NULL, dwFlags, &session)))
        throw MAPISessionException(hr, L"_mapiLogon(): MAPILogonEx Failed.", 
		ERR_MAPI_LOGON, __LINE__, __FILE__);
    return hr;
}

HRESULT MAPISession::Logon(LPWSTR strProfile)
{
    DWORD dwFlags = MAPI_EXTENDED | MAPI_NEW_SESSION | MAPI_EXPLICIT_PROFILE | MAPI_NO_MAIL |
        MAPI_LOGON_UI | fMapiUnicode;

    return _mapiLogon(strProfile, dwFlags, m_Session);
}

HRESULT MAPISession::Logon(bool bDefaultProfile)
{
    DWORD dwFlags = MAPI_EXTENDED | MAPI_NEW_SESSION | fMapiUnicode;

    if (bDefaultProfile)
        dwFlags |= MAPI_USE_DEFAULT;
    else
        dwFlags |= MAPI_LOGON_UI;
    return _mapiLogon(NULL, dwFlags, m_Session);
}

HRESULT MAPISession::OpenDefaultStore(MAPIStore &Store)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);
    HRESULT hr = E_FAIL;
    SBinary defMsgStoreEID;
    LPMDB pDefaultMDB = NULL;

    if (m_Session == NULL)
        throw MAPISessionException(hr, L"OpenDefaultStore(): m_mapiSession is NULL.", 
		ERR_STORE_ERR, __LINE__, __FILE__);
    if (FAILED(hr = Zimbra::MAPI::Util::HrMAPIFindDefaultMsgStore(m_Session, defMsgStoreEID)))
    {
        throw MAPISessionException(hr, L"OpenDefaultStore(): HrMAPIFindDefaultMsgStore Failed.",
            ERR_STORE_ERR, __LINE__, __FILE__);
    }

    Zimbra::Util::ScopedBuffer<BYTE> autoDeletePtr(defMsgStoreEID.lpb);

    hr = m_Session->OpenMsgStore(NULL, defMsgStoreEID.cb, (LPENTRYID)defMsgStoreEID.lpb, NULL,
        MDB_ONLINE | MAPI_BEST_ACCESS | MDB_NO_MAIL | MDB_TEMPORARY | MDB_NO_DIALOG,
        &pDefaultMDB);
    if (hr == MAPI_E_FAILONEPROVIDER)
    {
        hr = m_Session->OpenMsgStore(NULL, defMsgStoreEID.cb, (LPENTRYID)defMsgStoreEID.lpb,
            NULL, MDB_ONLINE | MAPI_BEST_ACCESS | MDB_NO_MAIL | MDB_TEMPORARY, &pDefaultMDB);
    }
    else if (hr == MAPI_E_UNKNOWN_FLAGS)
    {
        hr = m_Session->OpenMsgStore(NULL, defMsgStoreEID.cb, (LPENTRYID)defMsgStoreEID.lpb,
            NULL, MAPI_BEST_ACCESS | MDB_NO_MAIL | MDB_TEMPORARY | MDB_NO_DIALOG, &pDefaultMDB);
    }
    if (FAILED(hr))
        throw MAPISessionException(hr, L"OpenDefaultStore(): OpenMsgStore Failed.",
		ERR_STORE_ERR, __LINE__, __FILE__);
    Store.Initialize(m_Session, pDefaultMDB);
    return S_OK;
}

HRESULT MAPISession::OpenOtherStore(LPMDB OpenedStore, LPWSTR pServerDn, LPWSTR pUserDn,
    MAPIStore &OtherStore)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);
    HRESULT hr = E_FAIL;

    if (m_Session == NULL)
        throw MAPISessionException(hr, L"OpenDefaultStore(): m_mapiSession is NULL.", 
		ERR_STORE_ERR, __LINE__, __FILE__);

    // build the dn of the store to open
    LPWSTR pszSuffix = L"/cn=Microsoft Private MDB";
    size_t iLen = wcslen(pServerDn) + wcslen(pszSuffix) + 1;
    LPWSTR pszStoreDN = new WCHAR[iLen];

    swprintf(pszStoreDN, iLen, L"%s%s", pServerDn, pszSuffix);

    LPMDB pMdb = NULL;

    hr = Zimbra::MAPI::Util::MailboxLogon(m_Session, OpenedStore, pszStoreDN, pUserDn, &pMdb);
    delete[] pszStoreDN;
    if (FAILED(hr))
        throw MAPISessionException(hr, L"OpenDefaultStore(): MailboxLogon Failed.", 
		ERR_STORE_ERR, __LINE__,  __FILE__);
    OtherStore.Initialize(m_Session, pMdb);

    return S_OK;
}

HRESULT MAPISession::OpenAddressBook(LPADRBOOK *ppAddrBook)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);
    HRESULT hr = E_FAIL;

    if (m_Session)
        hr = m_Session->OpenAddressBook(NULL, NULL, AB_NO_DIALOG, ppAddrBook);
    return hr;
}

HRESULT MAPISession::OpenEntry(ULONG cbEntryID, LPENTRYID lpEntryID, LPCIID lpInterface, ULONG
    ulFlags, ULONG FAR *lpulObjType, LPUNKNOWN FAR *lppUnk)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);

    return m_Session->OpenEntry(cbEntryID, lpEntryID, lpInterface, ulFlags, lpulObjType,
        lppUnk);
}

HRESULT MAPISession::CompareEntryIDs(SBinary *pBin1, SBinary *pBin2, ULONG &lpulResult)
{
    Zimbra::Util::AutoCriticalSection autocriticalsection(cs);
    HRESULT hr = S_OK;

    hr = m_Session->CompareEntryIDs(pBin1->cb, (LPENTRYID)(pBin1->lpb), pBin2->cb,
        (LPENTRYID)(pBin2->lpb), 0, &lpulResult);
    return hr;
}
