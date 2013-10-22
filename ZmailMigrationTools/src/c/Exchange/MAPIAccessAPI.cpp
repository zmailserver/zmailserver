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
#include "MAPIAccessAPI.h"
#include "Logger.h"

Zimbra::MAPI::MAPISession *MAPIAccessAPI::m_zmmapisession = NULL;
Zimbra::MAPI::MAPIStore *MAPIAccessAPI::m_defaultStore = NULL;
std::wstring MAPIAccessAPI::m_strTargetProfileName = L"";
std::wstring MAPIAccessAPI::m_strExchangeHostName = L"";
bool MAPIAccessAPI::m_bSingleMailBoxMigration = false;
bool MAPIAccessAPI::m_bHasJoinedDomain = false;

// Initialize with Exchange Sever hostname, Outlook Admin profile name, Exchange mailbox name to be migrated
MAPIAccessAPI::MAPIAccessAPI(wstring strUserName, wstring strUserAccount): m_userStore(NULL), m_rootFolder(NULL)
{
    if (strUserName.empty())
        m_bSingleMailBoxMigration = true;

    else
        m_strUserName = strUserName;
    m_strUserAccount = strUserAccount;

    Zimbra::Mapi::Memory::SetMemAllocRoutines(NULL, MAPIAllocateBuffer, MAPIAllocateMore,
        MAPIFreeBuffer);
	
    InitFoldersToSkip();
}

MAPIAccessAPI::~MAPIAccessAPI()
{
	if ((m_userStore) && (!m_bSingleMailBoxMigration))
        delete m_userStore;
    m_userStore = NULL;
    if (m_rootFolder != NULL)
    {
        delete m_rootFolder;
        m_rootFolder = NULL;
    }
    m_bSingleMailBoxMigration = false;
}

void MAPIAccessAPI::InitFoldersToSkip()
{
    FolderToSkip[TS_JOURNAL] = JOURNAL;
    FolderToSkip[TS_OUTBOX] = OUTBOX;
    FolderToSkip[TS_SYNC_CONFLICTS] = SYNC_CONFLICTS;
    FolderToSkip[TS_SYNC_ISSUES] = SYNC_ISSUES;
    FolderToSkip[TS_SYNC_LOCAL_FAILURES] = SYNC_LOCAL_FAILURES;
    FolderToSkip[TS_SYNC_SERVER_FAILURES] = SYNC_SERVER_FAILURES;
    // FolderToSkip[TS_JUNK_MAIL] = JUNK_MAIL;
}

bool MAPIAccessAPI::SkipFolder(ExchangeSpecialFolderId exfid)
{
    for (int i = TS_JOURNAL; i < TS_FOLDERS_MAX; i++)
    {
        if (FolderToSkip[i] == exfid)
            return true;
    }
    return false;
}

LONG WINAPI MAPIAccessAPI::UnhandledExceptionFilter(LPEXCEPTION_POINTERS pExPtrs)
{
	LPWSTR strOutMessage=NULL;
	LONG lRetVal = Zimbra::Util::MiniDumpGenerator::GenerateCoreDump(pExPtrs,strOutMessage);
	WCHAR pwszTempPath[MAX_PATH];
	GetTempPath(MAX_PATH, pwszTempPath);
    wstring strMsg;
	WCHAR strbuf[256];
	wsprintf(strbuf,L"The application has requested the Runtime to terminate it in an unusual way.\nThe core dump would get generated in %s.",
       pwszTempPath);
	//MessageBox(NULL, strbuf, _T("Runtime Error"), MB_OK);
	dloge(strbuf);
	Zimbra::Util::FreeString(strOutMessage);
    return lRetVal;
}

void MAPIAccessAPI::internalInit()
{
	//Get App dir
	wstring appdir= Zimbra::Util::GetAppDir();
	//instantiate dump generator
	LPWSTR pwszTempPath = new WCHAR[MAX_PATH];
	wcscpy(pwszTempPath,appdir.c_str());
	Zimbra::Util::AppendString(pwszTempPath,L"dbghelp.dll");
   Zimbra::Util::MiniDumpGenerator::Initialize(pwszTempPath);

	SetUnhandledExceptionFilter(UnhandledExceptionFilter);
	delete []pwszTempPath;
}

LPCWSTR MAPIAccessAPI::InitGlobalSessionAndStore(LPCWSTR lpcwstrMigTarget)
{
	internalInit();
	LPWSTR exceptionmsg=NULL;
	__try
	{
		return _InitGlobalSessionAndStore(lpcwstrMigTarget);
	}
	__except(Zimbra::Util::MiniDumpGenerator::GenerateCoreDump(GetExceptionInformation(),exceptionmsg))
	{
		dloge(exceptionmsg);
		Zimbra::Util::FreeString(exceptionmsg);
	}
	return NULL;
}

LPCWSTR MAPIAccessAPI::_InitGlobalSessionAndStore(LPCWSTR lpcwstrMigTarget)
{
    LPWSTR lpwstrStatus = NULL;
	LPWSTR lpwstrRetVal= NULL;
	// If part of domain, get domain name
    m_bHasJoinedDomain = Zimbra::MAPI::Util::GetDomainName(m_strExchangeHostName);
	try
    {
        // Logon into target profile
        m_zmmapisession = new Zimbra::MAPI::MAPISession();

        // Detreming if its a profile or PST by extension
        wstring strMigTarget = lpcwstrMigTarget;
        std::transform(strMigTarget.begin(), strMigTarget.end(), strMigTarget.begin(),
            ::toupper);

        // if pst file, create associated MAPI profile for migration
        if (strMigTarget.find(L".PST") != std::wstring::npos)
        {
            LPSTR lpstrMigTarget;

            WtoA((LPWSTR)lpcwstrMigTarget, lpstrMigTarget);

            // delete any left over profiles from previous migration
            Zimbra::MAPI::Util::DeleteAlikeProfiles(
                Zimbra::MAPI::Util::PSTMIG_PROFILE_PREFIX.c_str());
            string strPSTProfileName = Zimbra::MAPI::Util::PSTMIG_PROFILE_PREFIX;

            // Add timestamp to profile to make it unique
            char timeStr[9];

            _strtime(timeStr);

            string strTmpProfile(timeStr);

            replace(strTmpProfile.begin(), strTmpProfile.end(), ':', '_');
            strPSTProfileName += strTmpProfile;
            // create PST profile
            if (!Zimbra::MAPI::Util::CreatePSTProfile((LPSTR)strPSTProfileName.c_str(),
                lpstrMigTarget))
            {
                SafeDelete(lpstrMigTarget);
                lpwstrStatus = FormatExceptionInfo(E_FAIL, ERR_CREATE_PSTPROFILE,
                    __FILE__, __LINE__);
				dloge(lpwstrStatus);
				Zimbra::Util::CopyString(lpwstrRetVal,(LPWSTR)ERR_CREATE_PSTPROFILE);
                goto CLEAN_UP;
            }
            SafeDelete(lpstrMigTarget);

            LPWSTR wstrProfileName;

            AtoW((LPSTR)strPSTProfileName.c_str(), wstrProfileName);
            m_strTargetProfileName = wstrProfileName;
            SafeDelete(wstrProfileName);
        }
        else
        {
            m_strTargetProfileName = lpcwstrMigTarget;
        }

        HRESULT hr = m_zmmapisession->Logon((LPWSTR)m_strTargetProfileName.c_str());

        if (hr != S_OK)
            goto CLEAN_UP;
        m_defaultStore = new Zimbra::MAPI::MAPIStore();

        // Open target default store
        hr = m_zmmapisession->OpenDefaultStore(*m_defaultStore);
        if (hr != S_OK)
            goto CLEAN_UP;
    }
    catch (MAPISessionException &msse)
    {
        lpwstrStatus = FormatExceptionInfo(msse.ErrCode(), (LPWSTR)msse.Description().c_str(),
            (LPSTR)msse.SrcFile().c_str(), msse.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,msse.ShortDescription().c_str());
    }
    catch (MAPIStoreException &mste)
    {
        lpwstrStatus = FormatExceptionInfo(mste.ErrCode(), (LPWSTR)mste.Description().c_str(),
            (LPSTR)mste.SrcFile().c_str(), mste.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,mste.ShortDescription().c_str());
    }
    catch (Util::MapiUtilsException &muex)
    {
        lpwstrStatus = FormatExceptionInfo(muex.ErrCode(), (LPWSTR)muex.Description().c_str(),
            (LPSTR)muex.SrcFile().c_str(), muex.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,muex.ShortDescription().c_str());
    }
	
    // Create Temporary dir for temp files
    Zimbra::MAPI::Util::CreateAppTemporaryDirectory();

CLEAN_UP: if (lpwstrStatus)
    {
        if (m_zmmapisession)
            delete m_zmmapisession;
        m_zmmapisession = NULL;
        if (m_defaultStore)
            delete m_defaultStore;
        m_defaultStore = NULL;
		Zimbra::Util::FreeString(lpwstrStatus);
    }
	return lpwstrRetVal;
}

void MAPIAccessAPI::UnInitGlobalSessionAndStore()
{
	Zimbra::Util::MiniDumpGenerator::UnInit();
    // Delete any PST migration profiles
    Zimbra::MAPI::Util::DeleteAlikeProfiles(Zimbra::MAPI::Util::PSTMIG_PROFILE_PREFIX.c_str());

    if (m_defaultStore)
        delete m_defaultStore;
    m_defaultStore = NULL;
    if (m_zmmapisession)
        delete m_zmmapisession;
    m_zmmapisession = NULL;
}

wstring GetCNName(LPWSTR pstrUserDN)
{
	wstring strUserDN = pstrUserDN;
	size_t npos=strUserDN.find_last_of(L"/");
	if(npos != string::npos)
	{
		strUserDN=strUserDN.substr(npos+1);
		npos=strUserDN.find_first_of(L"cn=");
		if(npos != string::npos)
		{
			strUserDN=strUserDN.substr(3);
		}
	}
	else
		strUserDN=L"";
	return strUserDN;
}

// Open MAPI sessiona and Open Stores
LPCWSTR MAPIAccessAPI::OpenUserStore()
{
    LPWSTR lpwstrStatus = NULL;
	LPWSTR lpwstrRetVal=NULL;
    HRESULT hr = S_OK;
    wstring wstruserdn;
    wstring legacyName;
    LPSTR ExchangeServerDN = NULL;
    LPSTR ExchangeUserDN = NULL;
	LPSTR ExchangeHostName = NULL;
    LPWSTR pwstrExchangeServerDN = NULL;
	LPWSTR pwstrExchangeHostName = NULL;
	LPWSTR pwstrExchangeUserDN = NULL;
	
    try
    {
        // user store
        m_userStore = new Zimbra::MAPI::MAPIStore();
        // Get Exchange Server DN
        hr = Zimbra::MAPI::Util::GetUserDnAndServerDnFromProfile(
            m_zmmapisession->GetMAPISessionObject(), ExchangeServerDN, ExchangeUserDN, ExchangeHostName);
        if (hr != S_OK)
            goto CLEAN_UP;
        AtoW(ExchangeServerDN, pwstrExchangeServerDN);
		AtoW(ExchangeHostName, pwstrExchangeHostName);
		AtoW(ExchangeUserDN, pwstrExchangeUserDN);
		
		//Get CN name from user DN
		wstring wstrCNName=GetCNName(pwstrExchangeUserDN);

		//if logged in user and user to migrate are same. Assume that its a profile migration.
		//No need to get user DN again from LDAP. Most probably, machine is not part of any domain.
		if(wstrCNName == m_strUserName)
		{
			m_userStore = m_defaultStore;
			m_bSingleMailBoxMigration = true;
		}
		else
		{
			//if part of domain, use domain to query LDAP
			//else use Exchange server host name from profile
			//NOTE:If not part of domain and AD is on different host than Exchange Server
			//It will not work. m_strExchangeHostName MUST point to AD.
			//TODO: Find a way to get AD from profile or some other means.
			if(!m_bHasJoinedDomain)
			{
				m_strExchangeHostName = pwstrExchangeHostName;
			}
				
			// Get DN of user to be migrated
			Zimbra::MAPI::Util::GetUserDNAndLegacyName(m_strExchangeHostName.c_str(),
				m_strUserName.c_str(), NULL, wstruserdn, legacyName);
			hr = m_zmmapisession->OpenOtherStore(m_defaultStore->GetInternalMAPIStore(),
				pwstrExchangeServerDN, (LPWSTR)legacyName.c_str(), *m_userStore);
			if (hr != S_OK)
				goto CLEAN_UP;
		}
		
    }
    catch (MAPISessionException &msse)
    {
        lpwstrStatus = FormatExceptionInfo(msse.ErrCode(), (LPWSTR)msse.Description().c_str(),
            (LPSTR)msse.SrcFile().c_str(), msse.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, msse.ShortDescription().c_str());
    }
    catch (MAPIStoreException &mste)
    {
        lpwstrStatus = FormatExceptionInfo(mste.ErrCode(), (LPWSTR)mste.Description().c_str(),
            (LPSTR)mste.SrcFile().c_str(), mste.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, mste.ShortDescription().c_str());
    }
    catch (Util::MapiUtilsException &muex)
    {
        lpwstrStatus = FormatExceptionInfo(muex.ErrCode(), (LPWSTR)muex.Description().c_str(),
            (LPSTR)muex.SrcFile().c_str(), muex.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, muex.ShortDescription().c_str());
    }
CLEAN_UP: SafeDelete(ExchangeServerDN);
    SafeDelete(ExchangeUserDN);
    SafeDelete(pwstrExchangeServerDN);
	SafeDelete(pwstrExchangeHostName);
	SafeDelete(pwstrExchangeUserDN);
	if ((hr != S_OK)&&(!lpwstrStatus))
	{
        lpwstrStatus = FormatExceptionInfo(hr, L"MAPIAccessAPI::OpenSessionAndStore() Failed",
            __FILE__, __LINE__);
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,  L"MAPIAccessAPI::OpenSessionAndStore() Failed");
	}
	if(lpwstrStatus)
		Zimbra::Util::FreeString(lpwstrStatus);
    return lpwstrRetVal;
}

LPCWSTR MAPIAccessAPI::InitializeUser()
{
	LPWSTR exceptionmsg=NULL;
	__try
	{
		return _InitializeUser();
	}
	__except(Zimbra::Util::MiniDumpGenerator::GenerateCoreDump(GetExceptionInformation(),exceptionmsg))
	{
		dloge(exceptionmsg);		
	}
	return exceptionmsg;
}

// Get root folders
LPCWSTR MAPIAccessAPI::_InitializeUser()
{
    LPWSTR lpwstrStatus = NULL;
	LPWSTR lpwstrRetVal=NULL;
    HRESULT hr = S_OK;

    try
    {
        if (!m_bSingleMailBoxMigration)
        {
            lpwstrStatus = (LPWSTR)OpenUserStore();
            if (lpwstrStatus)
                return lpwstrStatus;
        }
        else
        {
            // if profile to be migrated
            m_userStore = m_defaultStore;
        }
        // Get root folder from user store
        m_rootFolder = new Zimbra::MAPI::MAPIFolder(*m_zmmapisession, *m_userStore);
        if (FAILED(hr = m_userStore->GetRootFolder(*m_rootFolder)))
		{
            lpwstrStatus = FormatExceptionInfo(hr, L"MAPIAccessAPI::Initialize() Failed",
                __FILE__, __LINE__);
			Zimbra::Util::CopyString(lpwstrRetVal, L"MAPIAccessAPI::Initialize() Failed");
		}
		Zimbra::Mapi::NamedPropsManager::SetNamedProps(m_userStore->GetInternalMAPIStore());
    }
    catch (GenericException &ge)
    {
        lpwstrStatus = FormatExceptionInfo(ge.ErrCode(), (LPWSTR)ge.Description().c_str(),
            (LPSTR)ge.SrcFile().c_str(), ge.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,ge.ShortDescription().c_str());
    }
	if(lpwstrStatus)
		Zimbra::Util::FreeString(lpwstrStatus);
    return lpwstrRetVal;
}


LPCWSTR MAPIAccessAPI::GetRootFolderHierarchy(vector<Folder_Data> &vfolderlist)
{
	LPWSTR exceptionmsg=NULL;
	__try
	{
		return _GetRootFolderHierarchy(vfolderlist);
	}
	__except(Zimbra::Util::MiniDumpGenerator::GenerateCoreDump(GetExceptionInformation(),exceptionmsg))
	{
		dloge(exceptionmsg);		
	}
	return exceptionmsg;
}

LPCWSTR MAPIAccessAPI::_GetRootFolderHierarchy(vector<Folder_Data> &vfolderlist)
{
    LPWSTR lpwstrStatus = NULL;
    HRESULT hr = S_OK;

    try
    {
        hr = Iterate_folders(*m_rootFolder, vfolderlist);
    }
    catch (GenericException &ge)
    {
        lpwstrStatus = FormatExceptionInfo(ge.ErrCode(), (LPWSTR)ge.Description().c_str(),
            (LPSTR)ge.SrcFile().c_str(), ge.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::FreeString(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrStatus, ge.ShortDescription().c_str());
    }
    return lpwstrStatus;
}

HRESULT MAPIAccessAPI::Iterate_folders(Zimbra::MAPI::MAPIFolder &folder,
    vector<Folder_Data> &fd)
{
    Zimbra::MAPI::FolderIterator *folderIter = new Zimbra::MAPI::FolderIterator;

    folder.GetFolderIterator(*folderIter);

    BOOL bMore = TRUE;

    while (bMore)
    {
        ULONG itemCount = 0;

        // delete them while clearing the tree nodes
        Zimbra::MAPI::MAPIFolder *childFolder = new Zimbra::MAPI::MAPIFolder(*m_zmmapisession,
            *m_userStore);

        bMore = folderIter->GetNext(*childFolder);

        bool bSkipFolder = false;
        ExchangeSpecialFolderId exfid = childFolder->GetExchangeFolderId();
        wstring wstrContainerClass;

        childFolder->ContainerClass(wstrContainerClass);
        // skip folders in exclusion list, hidden folders and non-standard type folders
        if (SkipFolder(exfid) || childFolder->HiddenFolder() || (((wstrContainerClass !=
            L"IPF.Note") && (wstrContainerClass != L"IPF.Contact") && (wstrContainerClass !=
            L"IPF.Appointment") && (wstrContainerClass != L"IPF.Task") && (wstrContainerClass !=
            L"IPF.StickyNote") && (wstrContainerClass != L"IPF.Imap") 
			&& (wstrContainerClass != L"")) && (exfid ==
            SPECIAL_FOLDER_ID_NONE)))
            bSkipFolder = true;
        if (bMore && !bSkipFolder)
        {
            childFolder->GetItemCount(itemCount);

            // store foldername
            Folder_Data flderdata;

            flderdata.name = childFolder->Name();

            // folder item count
            flderdata.itemcount = itemCount;

            // store Folder EntryID
            SBinary sbin = childFolder->EntryID();

            CopyEntryID(sbin, flderdata.sbin);

            // folder path
            flderdata.folderpath = childFolder->GetFolderPath();

            // container class
            flderdata.containerclass = wstrContainerClass;

            // ExchangeFolderID
            flderdata.zimbraid = (long)childFolder->GetZimbraFolderId();

            // append
            fd.push_back(flderdata);
        }
        if (bMore && !bSkipFolder)
            Iterate_folders(*childFolder, fd);
        delete childFolder;
        childFolder = NULL;
    }
    delete folderIter;
    folderIter = NULL;
    return S_OK;
}

HRESULT MAPIAccessAPI::GetInternalFolder(SBinary sbFolderEID, MAPIFolder &folder)
{
    LPMAPIFOLDER pFolder = NULL;
    HRESULT hr = S_OK;
    ULONG objtype;

    if ((hr = m_userStore->OpenEntry(sbFolderEID.cb, (LPENTRYID)sbFolderEID.lpb, NULL,
            MAPI_BEST_ACCESS, &objtype, (LPUNKNOWN *)&pFolder)) != S_OK)
        throw GenericException(hr, L"MAPIAccessAPI::GetInternalFolder OpenEntry Failed.", ERR_OPEN_ENTRYID, 
		__LINE__, __FILE__);

    Zimbra::Util::ScopedBuffer<SPropValue> pPropValues;

    // Get PR_DISPLAY_NAME
    if (FAILED(hr = HrGetOneProp(pFolder, PR_DISPLAY_NAME, pPropValues.getptr())))
        throw GenericException(hr, L"MAPIAccessAPI::GetInternalFolder HrGetOneProp() Failed.", ERR_OPEN_PROPERTY, 
		__LINE__, __FILE__);
    folder.Initialize(pFolder, pPropValues->Value.LPSZ, &sbFolderEID);
    return hr;
}


LPCWSTR MAPIAccessAPI::GetFolderItemsList(SBinary sbFolderEID, vector<Item_Data> &ItemList)
{
	LPWSTR exceptionmsg=NULL;
	__try
	{
		_GetFolderItemsList(sbFolderEID,ItemList);
	}
	__except(Zimbra::Util::MiniDumpGenerator::GenerateCoreDump(GetExceptionInformation(),exceptionmsg))
	{
		dloge(exceptionmsg);		
	}
	return exceptionmsg;
}

LPCWSTR MAPIAccessAPI::_GetFolderItemsList(SBinary sbFolderEID, vector<Item_Data> &ItemList)
{
    LPWSTR lpwstrStatus = NULL;
	LPWSTR lpwstrRetVal=NULL;
    HRESULT hr = S_OK;
    MAPIFolder folder;

    try
    {
        if (FAILED(hr = GetInternalFolder(sbFolderEID, folder) != S_OK))
        {
            lpwstrStatus = FormatExceptionInfo(hr,
                L"MAPIAccessAPI::GetFolderItemsList() Failed", __FILE__, __LINE__);
			Zimbra::Util::CopyString(lpwstrRetVal,L"MAPIAccessAPI::GetFolderItemsList() Failed");
            goto ZM_EXIT;
        }

        Zimbra::MAPI::MessageIterator *msgIter = new Zimbra::MAPI::MessageIterator();

        folder.GetMessageIterator(*msgIter);

        BOOL bContinue = true;
		BOOL skip = false;

        while (bContinue)
        {
			skip = false;
            Zimbra::MAPI::MAPIMessage *msg = new Zimbra::MAPI::MAPIMessage();

			try
			{
            bContinue = msgIter->GetNext(*msg);
			}
			catch (MAPIMessageException &msgex)
			{
				lpwstrStatus = FormatExceptionInfo(msgex.ErrCode(), (LPWSTR)msgex.Description().c_str(),
					(LPSTR)msgex.SrcFile().c_str(), msgex.SrcLine());
				dloge(lpwstrStatus);
				Zimbra::Util::CopyString(lpwstrRetVal, msgex.ShortDescription().c_str());
				bContinue = true;
				skip = true;
			}
            if (bContinue && !(skip))
            {
                Item_Data itemdata;

                //
                itemdata.lItemType = msg->ItemType();

                SBinary sbin = msg->EntryID();

                CopyEntryID(sbin, itemdata.sbMessageID);
                //Add eid in string format
                itemdata.strMsgEntryId = "";
                Zimbra::Util::ScopedArray<CHAR> spUid(new CHAR[(sbin.cb * 2) + 1]);
                if (spUid.get() != NULL)
                {
	            Zimbra::Util::HexFromBin(sbin.lpb, sbin.cb, spUid.get());
                    itemdata.strMsgEntryId = spUid.getref();
                }
                itemdata.MessageDate = msg->Date();
                ItemList.push_back(itemdata);
            }
            delete msg;
        }
        delete msgIter;
    }
    catch (MAPISessionException &mssex)
    {
        lpwstrStatus = FormatExceptionInfo(mssex.ErrCode(), (LPWSTR)mssex.Description().c_str(),
            (LPSTR)mssex.SrcFile().c_str(), mssex.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, mssex.ShortDescription().c_str());
    }
    catch (MAPIFolderException &mfex)
    {
        lpwstrStatus = FormatExceptionInfo(mfex.ErrCode(), (LPWSTR)mfex.Description().c_str(),
            (LPSTR)mfex.SrcFile().c_str(), mfex.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, mfex.ShortDescription().c_str());
    }
    catch (MAPIMessageException &msgex)
    {
        lpwstrStatus = FormatExceptionInfo(msgex.ErrCode(), (LPWSTR)msgex.Description().c_str(),
            (LPSTR)msgex.SrcFile().c_str(), msgex.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, msgex.ShortDescription().c_str());
	
    }
    catch (GenericException &genex)
    {
        lpwstrStatus = FormatExceptionInfo(genex.ErrCode(), (LPWSTR)genex.Description().c_str(),
            (LPSTR)genex.SrcFile().c_str(), genex.SrcLine());
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, genex.ShortDescription().c_str());
    }
	if(lpwstrStatus)
		Zimbra::Util::FreeString(lpwstrStatus);
ZM_EXIT: return lpwstrRetVal;
}

LPCWSTR MAPIAccessAPI::GetItem(SBinary sbItemEID, BaseItemData &itemData)
{
	LPWSTR exceptionmsg=NULL;
	__try
	{
		exceptionmsg=(LPWSTR)_GetItem(sbItemEID,itemData);
	}
	__except(Zimbra::Util::MiniDumpGenerator::GenerateCoreDump(GetExceptionInformation(),exceptionmsg))
	{
		dloge(exceptionmsg);		
	}
	return exceptionmsg;
}

LPCWSTR MAPIAccessAPI::_GetItem(SBinary sbItemEID, BaseItemData &itemData)
{
    LPWSTR lpwstrStatus = NULL;
	LPWSTR lpwstrRetVal=NULL;
    HRESULT hr = S_OK;
    LPMESSAGE pMessage = NULL;
    ULONG objtype;

    if (FAILED(hr = m_userStore->OpenEntry(sbItemEID.cb, (LPENTRYID)sbItemEID.lpb, NULL,
            MAPI_BEST_ACCESS, &objtype, (LPUNKNOWN *)&pMessage)))
    {
        lpwstrStatus = FormatExceptionInfo(hr, L"MAPIAccessAPI::GetItem() Failed", __FILE__,
            __LINE__);
		dloge("MAPIAccessAPI -- User Store OpenEntry failed");
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal, ERR_OPEN_ENTRYID);
        goto ZM_EXIT;
    }

	/* in case we want some retry logic sometime
	hr = 0x80040115;
	int tries = 10;
	int num = 0;
	while ((hr == 0x80040115) && (num < tries))
	{
		hr = m_userStore->OpenEntry(sbItemEID.cb, (LPENTRYID)sbItemEID.lpb, NULL,
			                        MAPI_BEST_ACCESS, &objtype, (LPUNKNOWN *)&pMessage);
		if (FAILED(hr))
			dlogi("got an 80040115");
		num++;
	}
	if (FAILED(hr))
	{
		lpwstrStatus = FormatExceptionInfo(hr, L"MAPIAccessAPI::GetItem() Failed", __FILE__, __LINE__);
		dlogi("MAPIAccessAPI -- m_userStore->OpenEntry failed");
		dlogi(lpwstrStatus);
		goto ZM_EXIT;
	}
	*/

    try
    {
        MAPIMessage msg;

        msg.Initialize(pMessage, *m_zmmapisession);
        std::vector<LPWSTR>* pKeywords = msg.SetKeywords();
        if ((msg.ItemType() == ZT_MAIL) || (msg.ItemType() == ZT_MEETREQ))
        {
           // printf("ITEM TYPE: ZT_MAIL \n");

            MessageItemData *msgdata = (MessageItemData *)&itemData;

            // subject
            msgdata->Subject = L"";

            LPTSTR lpstrsubject;

            if (msg.Subject(&lpstrsubject))
            {
                msgdata->Subject = lpstrsubject;
                SafeDelete(lpstrsubject);
            }
            msgdata->IsFlagged = msg.IsFlagged();

            msgdata->Urlname = L"";

            LPTSTR lpstrUrlName;

            if (msg.GetURLName(&lpstrUrlName))
            {
                msgdata->Urlname = lpstrUrlName;
                SafeDelete(lpstrUrlName);
            }
            msgdata->IsDraft = msg.IsDraft();
            msgdata->IsFromMe = (msg.IsFromMe() == TRUE);
            msgdata->IsUnread = (msg.IsUnread() == TRUE);
            msgdata->IsForwared = (msg.Forwarded() == TRUE);
            msgdata->RepliedTo = msg.RepliedTo() == TRUE;
            msgdata->HasAttachments = msg.HasAttach();
            msgdata->IsUnsent = msg.IsUnsent() == TRUE;
            msgdata->HasHtml = msg.HasHtmlPart();
            msgdata->HasText = msg.HasTextPart();

            msgdata->Date = msg.Date();

            LPWSTR wstrDateString;

            AtoW(msg.DateString(), wstrDateString);
            msgdata->DateString = wstrDateString;
            SafeDelete(wstrDateString);

            msgdata->deliveryDate = msg.DeliveryDate();

            LPWSTR wstrDelivUnixString;

            AtoW(msg.DeliveryUnixString(), wstrDelivUnixString);
            msgdata->DeliveryUnixString = wstrDelivUnixString;
            SafeDelete(wstrDelivUnixString);

            LPWSTR wstrDelivDateString;

            AtoW(msg.DeliveryDateString(), wstrDelivDateString);
            msgdata->DeliveryDateString = wstrDelivDateString;
            SafeDelete(wstrDelivDateString);

            msgdata->vTags = pKeywords;

/*
 *          if (msgdata->HasText)
 *          {
 *              LPTSTR textMsgBuffer;
 *              unsigned int nTextchars;
 *              msg.TextBody(&textMsgBuffer, nTextchars);
 *              msgdata->textbody.buffer = textMsgBuffer;
 *              msgdata->textbody.size = nTextchars;
 *          }
 *          if (msgdata->HasHtml)
 *          {
 *              LPVOID pHtmlBodyBuffer = NULL;
 *              unsigned int nHtmlchars;
 *              msg.HtmlBody(&pHtmlBodyBuffer, nHtmlchars);
 *              msgdata->htmlbody.buffer = (LPTSTR)pHtmlBodyBuffer;
 *              msgdata->htmlbody.size = nHtmlchars;
 *          }
 */
            mimepp::Message mimeMsg;
			try
			{
				msg.ToMimePPMessage(mimeMsg);
			}
			catch(...)
			{
				lpwstrStatus = FormatExceptionInfo(hr, L"ToMimePPMessage Failed",
                    __FILE__, __LINE__);
				dloge("MAPIAccessAPI -- exception");
				dloge(lpwstrStatus);
				Zimbra::Util::CopyString(lpwstrRetVal,L"MimePP conversion Failed.");
                goto ZM_EXIT;
			}

/*
			// Save mime to file in temp dir
            HRESULT hr = S_OK;
            ULONG nBytesWritten = 0;
            ULONG nTotalBytesWritten = 0;
            wstring wstrTempAppDirPath;
            char *lpszDirName = NULL;
            char *lpszUniqueName = NULL;
            Zimbra::Util::ScopedInterface<IStream> pStream;
            LPCSTR pDes = mimeMsg.getString().c_str();
            int nBytesToBeWritten = (int)(mimeMsg.getString().size());

            if (!Zimbra::MAPI::Util::GetAppTemporaryDirectory(wstrTempAppDirPath))
            {
                lpwstrStatus = FormatExceptionInfo(hr, L"GetAppTemporaryDirectory Failed",
                    __FILE__, __LINE__);
                goto ZM_EXIT;
            }
            WtoA((LPWSTR)wstrTempAppDirPath.c_str(), lpszDirName);

            string strFQFileName = lpszDirName;

            WtoA((LPWSTR)Zimbra::MAPI::Util::GetUniqueName().c_str(), lpszUniqueName);
            strFQFileName += "\\";
            strFQFileName += lpszUniqueName;
            SafeDelete(lpszDirName);
            SafeDelete(lpszUniqueName);

			//disable to write in file. Use buffer now.	
            // Open stream on file
            if (FAILED(hr = OpenStreamOnFile(MAPIAllocateBuffer, MAPIFreeBuffer, STGM_CREATE |
                    STGM_READWRITE, (LPTSTR)strFQFileName.c_str(), NULL, pStream.getptr())))
            {
                lpwstrStatus = FormatExceptionInfo(hr,
                    L"Message Error: OpenStreamOnFile Failed.", __FILE__, __LINE__);
                goto ZM_EXIT;
            }

			hr = OpenStreamOnFile(MAPIAllocateBuffer, MAPIFreeBuffer, STGM_READWRITE | STGM_CREATE |
                  SOF_UNIQUEFILENAME | STGM_DELETEONRELEASE, NULL, (LPWSTR)L"zco", pStream.getptr());

            // write to buffer
            while (!FAILED(hr) && nBytesToBeWritten > 0)
            {
                hr = pStream->Write(pDes, nBytesToBeWritten, &nBytesWritten);
                pDes += nBytesWritten;
                nBytesToBeWritten -= nBytesWritten;
                nTotalBytesWritten += nBytesWritten;
                nBytesWritten = 0;
            }
            if (FAILED(hr = pStream->Commit(0)))
                return L"Message Error: Stream Commit Failed.";
                         // mime file path
*/
			LPTSTR pwDes = NULL;
            AtoW((LPSTR)mimeMsg.getString().c_str(),pwDes);   
            msgdata->wstrmimeBuffer = pwDes;
            delete[] pwDes;

/*
            LPWSTR lpwstrFQFileName = NULL;

            AtoW((LPSTR)strFQFileName.c_str(), lpwstrFQFileName);
            msgdata->MimeFile = lpwstrFQFileName;
            SafeDelete(lpwstrFQFileName);
*/
        }
        else if (msg.ItemType() == ZT_CONTACTS)
        {
           // printf("ITEM TYPE: ZT_CONTACTS \n");
			MAPIContact mapicontact(*m_zmmapisession, msg);
			ContactItemData *cd = (ContactItemData *)&itemData;

			cd->Birthday = mapicontact.Birthday();
			cd->CallbackPhone = mapicontact.CallbackPhone();
			cd->CarPhone = mapicontact.CarPhone();
			cd->Company = mapicontact.Company();
			cd->Email1 = mapicontact.Email();
			cd->Email2 = mapicontact.Email2();
			cd->Email3 = mapicontact.Email3();
			cd->FileAs = mapicontact.FileAs();
			cd->FirstName = mapicontact.FirstName();
			cd->HomeCity = mapicontact.HomeCity();
			cd->HomeCountry = mapicontact.HomeCountry();
			cd->HomeFax = mapicontact.HomeFax();
			cd->HomePhone = mapicontact.HomePhone();
			cd->HomePhone2 = mapicontact.HomePhone2();
			cd->HomePostalCode = mapicontact.HomePostalCode();
			cd->HomeState = mapicontact.HomeState();
			cd->HomeStreet = mapicontact.HomeStreet();
			cd->HomeURL = mapicontact.HomeURL();
			cd->IMAddress1 = mapicontact.IMAddress1();
			cd->JobTitle = mapicontact.JobTitle();
			cd->LastName = mapicontact.LastName();
			cd->MiddleName = mapicontact.MiddleName();
			cd->MobilePhone = mapicontact.MobilePhone();
			cd->NamePrefix = mapicontact.NamePrefix();
			cd->NameSuffix = mapicontact.NameSuffix();
			cd->NickName = mapicontact.NickName();
			cd->Notes = mapicontact.Notes();
			cd->OtherCity = mapicontact.OtherCity();
			cd->OtherCountry = mapicontact.OtherCountry();
			cd->OtherFax = mapicontact.OtherFax();
			cd->OtherPhone = mapicontact.OtherPhone();
			cd->OtherPostalCode = mapicontact.OtherPostalCode();
			cd->OtherState = mapicontact.OtherState();
			cd->OtherStreet = mapicontact.OtherStreet();
			cd->OtherURL = mapicontact.OtherURL();
			cd->Pager = mapicontact.Pager();
			cd->pDList = mapicontact.DList();   
			cd->PictureID = mapicontact.Picture();
			cd->Type = mapicontact.Type();
			cd->UserField1 = mapicontact.UserField1();
			cd->UserField2 = mapicontact.UserField2();
			cd->UserField3 = mapicontact.UserField3();
			cd->UserField4 = mapicontact.UserField4();
			cd->WorkCity = mapicontact.WorkCity();
			cd->WorkCountry = mapicontact.WorkCountry();
			cd->WorkFax = mapicontact.WorkFax();
			cd->WorkPhone = mapicontact.WorkPhone();
			cd->WorkPostalCode = mapicontact.WorkPostalCode();
			cd->WorkState = mapicontact.WorkState();
			cd->WorkStreet = mapicontact.WorkStreet();
			cd->WorkURL = mapicontact.WorkURL();
			cd->ContactImagePath = mapicontact.ContactImagePath();
			cd->ImageContenttype = mapicontact.ContactImageType();
			cd->ImageContentdisp = mapicontact.ContactImageDisp();
			cd->vTags = pKeywords;
			cd->Anniversary = mapicontact.Anniversary();
			vector<ContactUDFields>::iterator it;
			for (it= mapicontact.UserDefinedFields()->begin();it != mapicontact.UserDefinedFields()->end();it++)
				cd->UserDefinedFields.push_back(*it);
        }
        else if (msg.ItemType() == ZT_APPOINTMENTS)
        {
            MAPIAppointment mapiappointment(*m_zmmapisession, *m_userStore ,msg, NO_EXCEPTION);
            ApptItemData *ad = (ApptItemData *)&itemData;
            ad->Subject = mapiappointment.GetSubject();
            ad->Name = mapiappointment.GetSubject();
			dlogi(L"Subject: ",ad->Subject.c_str());
            ad->StartDate = mapiappointment.GetStartDate();
			dlogi(L"StartDate: ",ad->StartDate.c_str());
            ad->CalFilterDate = mapiappointment.GetCalFilterDate();
            ad->EndDate = mapiappointment.GetEndDate();
			dlogi("EndDate: ",ad->EndDate.c_str());
            ad->Location = mapiappointment.GetLocation();
            ad->PartStat = mapiappointment.GetResponseStatus();
			ad->CurrStat = mapiappointment.GetCurrentStatus();
			ad->RSVP = mapiappointment.GetResponseRequested();
            ad->FreeBusy = mapiappointment.GetBusyStatus();
            ad->AllDay = mapiappointment.GetAllday();
            ad->Transparency = mapiappointment.GetTransparency();
            ad->ApptClass = mapiappointment.GetPrivate();
            ad->AlarmTrigger = mapiappointment.GetReminderMinutes();
            ad->organizer.nam = mapiappointment.GetOrganizerName();
            ad->organizer.addr = mapiappointment.GetOrganizerAddr();
            ad->Uid = mapiappointment.GetInstanceUID();
            ad->vTags = pKeywords;
	    
            // fill in attendees
            vector<Attendee*> v = mapiappointment.GetAttendees();
            for (size_t i = 0; i < v.size(); i++)
            {
                ad->vAttendees.push_back((Attendee*)v[i]);
            }

	    if (mapiappointment.IsRecurring())
	    {
		ad->tz = mapiappointment.GetRecurTimezone();
		ad->recurPattern = mapiappointment.GetRecurPattern();
		if (mapiappointment.GetRecurWkday().length() > 0)
		{
		    ad->recurWkday = mapiappointment.GetRecurWkday();
		}
		ad->recurInterval = mapiappointment.GetRecurInterval();
		ad->recurCount = mapiappointment.GetRecurCount();
		ad->recurEndDate = mapiappointment.GetRecurEndDate();
		ad->recurDayOfMonth = mapiappointment.GetRecurDayOfMonth();
		ad->recurMonthOccurrence = mapiappointment.GetRecurMonthOccurrence();
		ad->recurMonthOfYear = mapiappointment.GetRecurMonthOfYear();

                // if there are exceptions, deal with them
                vector<MAPIAppointment*> ex = mapiappointment.GetExceptions();
                for (size_t i = 0; i < ex.size(); i++)
                {
                    ad->vExceptions.push_back((MAPIAppointment*)ex[i]);
                }
	    }

            ad->ExceptionType = mapiappointment.GetExceptionType();

            // attachment stuff
            vector<AttachmentInfo*> va = mapiappointment.GetAttachmentInfo();
            for (size_t i = 0; i < va.size(); i++)
            {
                ad->vAttachments.push_back((AttachmentInfo*)va[i]);
            }

            MessagePart mp;
            mp.contentType = L"text/plain";
            mp.content = mapiappointment.GetPlainTextFileAndContent();
            ad->vMessageParts.push_back(mp);
            mp.contentType = L"text/html";
            mp.content = mapiappointment.GetHtmlFileAndContent();
            ad->vMessageParts.push_back(mp);
        }
        else if (msg.ItemType() == ZT_TASKS)
        {
            MAPITask mapitask(*m_zmmapisession, msg);
            TaskItemData *td = (TaskItemData *)&itemData;
            td->Subject = mapitask.GetSubject();
            td->Importance = mapitask.GetImportance();
            td->TaskStart = mapitask.GetTaskStart();
            td->TaskFilterDate = mapitask.GetTaskFilterDate();
            td->TaskDue = mapitask.GetTaskDue();
            td->Status = mapitask.GetTaskStatus();
            td->PercentComplete = mapitask.GetPercentComplete();
            td->TotalWork = mapitask.GetTotalWork();
            td->ActualWork = mapitask.GetActualWork();
            td->Companies = mapitask.GetCompanies();
            td->Mileage = mapitask.GetMileage();
            td->BillingInfo = mapitask.GetBillingInfo();
            td->ApptClass = mapitask.GetPrivate();
            td->vTags = pKeywords;
            if (mapitask.IsTaskReminderSet())
            {
                td->TaskFlagDueBy = mapitask.GetTaskFlagDueBy();
            }
	    if (mapitask.IsRecurring())
	    {
		//td->tz = mapitask.GetRecurTimezone();
		td->recurPattern = mapitask.GetRecurPattern();
		if (mapitask.GetRecurWkday().length() > 0)
		{
		    td->recurWkday = mapitask.GetRecurWkday();
		}
		td->recurInterval = mapitask.GetRecurInterval();
		td->recurCount = mapitask.GetRecurCount();
		td->recurEndDate = mapitask.GetRecurEndDate();
		td->recurDayOfMonth = mapitask.GetRecurDayOfMonth();
		td->recurMonthOccurrence = mapitask.GetRecurMonthOccurrence();
		td->recurMonthOfYear = mapitask.GetRecurMonthOfYear();
	    }

            // attachment stuff
            vector<AttachmentInfo*> va = mapitask.GetAttachmentInfo();
            for (size_t i = 0; i < va.size(); i++)
            {
                td->vAttachments.push_back((AttachmentInfo*)va[i]);
            }

            MessagePart mp;
            mp.contentType = L"text/plain";
            mp.content = mapitask.GetPlainTextFileAndContent();
            td->vMessageParts.push_back(mp);
            mp.contentType = L"text/html";
            mp.content = mapitask.GetHtmlFileAndContent();
            td->vMessageParts.push_back(mp);
        }
    }
    catch (MAPIMessageException &mex)
    {
        lpwstrStatus = FormatExceptionInfo(mex.ErrCode(), (LPWSTR)mex.Description().c_str(),
            (LPSTR)mex.SrcFile().c_str(), mex.SrcLine());
		dloge("MAPIAccessAPI -- MAPIMessageException");
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,mex.ShortDescription().c_str());
    }
    catch (MAPIContactException &cex)
    {
        lpwstrStatus = FormatExceptionInfo(cex.ErrCode(), (LPWSTR)cex.Description().c_str(),
            (LPSTR)cex.SrcFile().c_str(), cex.SrcLine());
		dloge("MAPIAccessAPI -- MAPIMessageException");
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,cex.ShortDescription().c_str());
    }
	catch (MAPIAppointmentException &aex)
    {
        lpwstrStatus = FormatExceptionInfo(aex.ErrCode(), (LPWSTR)aex.Description().c_str(),
            (LPSTR)aex.SrcFile().c_str(), aex.SrcLine());
		dloge("MAPIAccessAPI -- MAPIAppointmentException");
		dloge(lpwstrStatus);
		Zimbra::Util::CopyString(lpwstrRetVal,aex.ShortDescription().c_str());
    }
ZM_EXIT: 
	if(lpwstrStatus)
		Zimbra::Util::FreeString(lpwstrStatus);
	return lpwstrRetVal;
}

LPWSTR MAPIAccessAPI::GetOOOStateAndMsg()
{
    HRESULT hr;
    BOOL bIsOOO = FALSE;
    Zimbra::Util::ScopedInterface<IMAPIFolder> spInbox;
    ULONG objtype;

    LPWSTR lpwstrOOOInfo = new WCHAR[3];
    lstrcpy(lpwstrOOOInfo, L"0:");

    // first get the OOO state -- if TRUE, put a 1: in the return val, else put 0:
    Zimbra::Util::ScopedBuffer<SPropValue> pPropValues;
    hr = HrGetOneProp(m_userStore->GetInternalMAPIStore(), PR_OOF_STATE, pPropValues.getptr());
    if (SUCCEEDED(hr))
    {
        bIsOOO = pPropValues->Value.b;
    }

    Zimbra::Util::ScopedInterface<IMAPITable> pContents;
    SBinaryArray specialFolderIds = m_userStore->GetSpecialFolderIds();
    SBinary sbin = specialFolderIds.lpbin[INBOX];
    hr = m_userStore->OpenEntry(sbin.cb, (LPENTRYID)sbin.lpb, NULL, MAPI_BEST_ACCESS,
                                &objtype, (LPUNKNOWN *)spInbox.getptr());
    if (FAILED(hr))
    {
        return lpwstrOOOInfo;
    }
    
    hr = spInbox->GetContentsTable(MAPI_ASSOCIATED | fMapiUnicode, pContents.getptr());
    if (FAILED(hr))
    {
        //LOG_ERROR(_T("could not get the contents table %x"), hr);
        return lpwstrOOOInfo;
    }
    // set the columns because we are only interested in the body
    SizedSPropTagArray(1, tags) = {1, { PR_BODY }};
 
    pContents->SetColumns((LPSPropTagArray) &tags, 0);

    // restrict the table only to IPM.Note.Rules.OofTemplate.Microsoft
    SPropValue propVal;

    propVal.dwAlignPad = 0;
    propVal.ulPropTag = PR_MESSAGE_CLASS;
    propVal.Value.lpszW = L"IPM.Note.Rules.OofTemplate.Microsoft";

    SRestriction r;

    r.rt = RES_PROPERTY;
    r.res.resProperty.lpProp = &propVal;
    r.res.resProperty.relop = RELOP_EQ;
    r.res.resProperty.ulPropTag = propVal.ulPropTag;

    // lets get the instance key for the store provider
    pContents->Restrict(&r, 0);

    // iterate over the rows looking for the folder in question
    ULONG ulRows = 0;

    pContents->GetRowCount(0, &ulRows);
    if (ulRows == 0)
    {
        return lpwstrOOOInfo;
    }

    Zimbra::Util::ScopedRowSet pRows;

    pContents->QueryRows(ulRows, 0, pRows.getptr());
    for (unsigned int i = 0; i < pRows->cRows; i++)
    {
        if (pRows->aRow[i].lpProps[0].ulPropTag == PR_BODY)
        {
            LPWSTR pwszOOOMsg = pRows->aRow[i].lpProps[0].Value.lpszW;
            int iOOOLen = lstrlen(pwszOOOMsg);
            delete lpwstrOOOInfo;
            lpwstrOOOInfo = new WCHAR[iOOOLen + 3];  // for the 0: or 1: and null terminator
            if (bIsOOO)
            {
                lstrcpy(lpwstrOOOInfo, L"1:");
            }
            else
            {
                lstrcpy(lpwstrOOOInfo, L"0:");
            }           
            lstrcat(lpwstrOOOInfo, pwszOOOMsg);
            break;
        }
    }
    return lpwstrOOOInfo;
}

LPCWSTR MAPIAccessAPI::GetExchangeRules(vector<CRule> &vRuleList)
{
    LPCWSTR lpwstrStatus = NULL;
    HRESULT hr;
    ULONG objtype;
    Zimbra::Util::ScopedInterface<IMAPIFolder> spInbox;
    LPEXCHANGEMODIFYTABLE lpExchangeTable;
    Zimbra::Util::ScopedInterface<IMAPITable> spMAPIRulesTable;
    ULONG lpulCount = NULL;
    LPSRowSet lppRows = NULL;

    SBinaryArray specialFolderIds = m_userStore->GetSpecialFolderIds();
    SBinary sbin = specialFolderIds.lpbin[INBOX];
    hr = m_userStore->OpenEntry(sbin.cb, (LPENTRYID)sbin.lpb, NULL, MAPI_BEST_ACCESS,
                                &objtype, (LPUNKNOWN *)spInbox.getptr());
    if (FAILED(hr))
    {
        lpwstrStatus = FormatExceptionInfo(hr, L"Unable to get Inbox.", __FILE__, __LINE__);
        return lpwstrStatus;                                  
    }
    hr = spInbox->OpenProperty(PR_RULES_TABLE, (LPGUID)&IID_IExchangeModifyTable, 0,
            MAPI_DEFERRED_ERRORS, (LPUNKNOWN FAR *)&lpExchangeTable);
    if (FAILED(hr))
    {
        lpwstrStatus = FormatExceptionInfo(hr, L"Unable to get Rules table property.", __FILE__, __LINE__);
        return lpwstrStatus;                                  
    }
    hr = lpExchangeTable->GetTable(0, spMAPIRulesTable.getptr());
    if (FAILED(hr))
    {
        lpExchangeTable->Release();
        lpwstrStatus = FormatExceptionInfo(hr, L"Unable to get Rules table.", __FILE__, __LINE__);
        return lpwstrStatus;                                  
    }
    hr = spMAPIRulesTable->GetRowCount(0, &lpulCount);
    if (FAILED(hr))
    {
        lpExchangeTable->Release();
        lpwstrStatus = FormatExceptionInfo(hr, L"Unable to get Rules table row count.", __FILE__, __LINE__);
        return lpwstrStatus;                                  
    }
    hr = HrQueryAllRows(spMAPIRulesTable.get(), NULL, NULL, NULL, lpulCount, &lppRows);
    if (SUCCEEDED(hr))
    {
        ULONG lPos = 0;
        LPSRestriction pRestriction = 0;
        LPACTIONS pActions = 0;
        ULONG ulWarnings = 0;

        dlogi(L"Begin Rules Migration");

        CRuleProcessor* pRuleProcessor = new CRuleProcessor(m_zmmapisession, m_userStore, m_strUserAccount);

        vRuleList.clear();
        while (lPos < lpulCount)
        {
            CRule rule;

            pRestriction = 0;
            pActions = 0;

            bool bRes = true;
            bool bAct = true;

            rule.Clear();

            std::wstring rulename = CA2W(lppRows->aRow[lPos].lpProps[8].Value.lpszA);

            rule.SetName(rulename.c_str());
            rule.SetActive(1);                      // if it was inactive in Exchange, we'd never see it
            if (rulename.length() == 0)
            {
                dlogw(L"Rule", lPos, L"has no name");
                lPos++;
                continue;
            }
 
            dlogi(L"Processing rule", rulename.c_str());

            pRestriction = (LPSRestriction)lppRows->aRow[lPos].lpProps[5].Value.x;

            // conditions are restrictions - call a recursive method to process them
            // 99 means this is not being called by another restriction
            bRes = pRuleProcessor->ProcessRestrictions(rule, pRestriction, FALSE, 99);

            // check if there were restrictions we couldn't support, possibly causing there to be no conditions
            CListRuleConditions listRuleConditions;

            rule.GetConditions(listRuleConditions);
            if (listRuleConditions.size() == 0)
            {
                dlogw(L"Rule", rulename.c_str(), L"has no conditions supported by Zimbra -- skipping");
                bRes = false;
            }

            int ulRuleState = lppRows->aRow[lPos].lpProps[3].Value.l;

            if (ulRuleState & ST_EXIT_LEVEL)        // weird looking code to get around compiler warning C4800
                rule.ProcessAdditionalRules(false);
            else
                rule.ProcessAdditionalRules(true);
            pActions = (LPACTIONS)lppRows->aRow[lPos].lpProps[6].Value.x;
            bAct = pRuleProcessor->ProcessActions(rule, pActions);

            // check if there were actions we couldn't support, possibly causing there to be none
            CListRuleActions listRuleActions;

            rule.GetActions(listRuleActions);
            if (listRuleActions.size() == 0)
            {
                dlogw(L"Rule", rulename.c_str(), L"has no actions supported by Zimbra -- skipping");
                bAct = false;
            }
            if (bRes && bAct)
            {
                vRuleList.push_back(rule);
                dlogd(L"Processed rule", rulename.c_str());
            }
            else
            {
                dlogw(L"Unable to import rule", rulename.c_str());
                ulWarnings++;
            }
            lPos++;
        }
        delete pRuleProcessor;
        MAPIFreeBuffer(pRestriction);
        MAPIFreeBuffer(pActions);

        // Fix up dict based on vRuleList

        //dlogd(L"Begin Rules Migration");
    }

    return lpwstrStatus;
}

// Access MAPI folder items
void MAPIAccessAPI::traverse_folder(Zimbra::MAPI::MAPIFolder &folder)
{
    Zimbra::MAPI::MessageIterator *msgIter = new Zimbra::MAPI::MessageIterator();

    folder.GetMessageIterator(*msgIter);

    BOOL bContinue = true;

    while (bContinue)
    {
        Zimbra::MAPI::MAPIMessage *msg = new Zimbra::MAPI::MAPIMessage();

        bContinue = msgIter->GetNext(*msg);
        if (bContinue)
        {
            Zimbra::Util::ScopedBuffer<WCHAR> subject;

            if (msg->Subject(subject.getptr()))
                printf("\tsubject--%S\n", subject.get());
        }
        delete msg;
    }
    delete msgIter;
}
