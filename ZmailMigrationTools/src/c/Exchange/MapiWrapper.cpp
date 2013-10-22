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
// MapiWrapper.cpp : Implementation of CMapiWrapper

#include "common.h"
#include "Exchange.h"
#include "ExchangeAdmin.h"
#include "MapiWrapper.h"
#include <ATLComTime.h>

// C5E4267B-AE6C-4E31-956A-06D8094D0CBE
const IID UDTVariable_IID = {
    0xC5E4267B, 0xAE6C, 0x4E31, {
        0x95, 0x6A, 0x06, 0xD8, 0x09, 0x4D, 0x0C, 0xBE
    }
};
const IID UDTItem_IID = {
    0xC5E4267A, 0xAE6C, 0x4E31, {
        0x95, 0x6A, 0x06, 0xD8, 0x09, 0x4D, 0x0C, 0xBE
    }
};

std::string format_error(unsigned __int32 hr)
{
  std::stringstream ss;
  ss << "COM call Failed. Error code = 0x" << std::hex << hr << std::endl;
  return ss.str();
}
STDMETHODIMP CMapiWrapper::InterfaceSupportsErrorInfo(REFIID riid)
{
    static const IID *const arr[] = {
        &IID_IMapiWrapper
    };

    for (int i = 0; i < sizeof (arr) / sizeof (arr[0]); i++)
    {
        if (InlineIsEqualGUID(*arr[i], riid))
            return S_OK;
    }
    return S_FALSE;
}

STDMETHODIMP CMapiWrapper::ConnectToServer(BSTR ServerHostName, BSTR Port, BSTR AdminID)
{
    (void)ServerHostName;
    (void)Port;
    (void)AdminID;

    // baseMigrationObj->Connecttoserver();
    dlog.trace(L"Begin Mapiwrapper connectoserver InitGlobalSessionAndStore");
    LPCWSTR status = MAPIAccessAPI::InitGlobalSessionAndStore( /*ServerHostName, */ AdminID);
    if(status != NULL)
    {
        dlog.err("MAPIACCESSAPI->InitGlobalSessionAndStore errors out ",status);
        return S_FALSE;
    }
    dlog.trace(L"End Mapiwrapper connectoserver InitGlobalSessionAndStore");
    return S_OK;
}

STDMETHODIMP CMapiWrapper::GlobalInit(BSTR pMAPITarget, BSTR pAdminUser, BSTR pAdminPassword,
    BSTR *pErrorText)
{
    (void)pMAPITarget;
    (void)pAdminUser;
    (void)pAdminPassword;
    (void)pErrorText;
    dlog.trace(L" Begin Mapiwrapper GlobalInit");
    LPCWSTR lpszErrorText = ExchangeOps::GlobalInit((LPCWSTR)pMAPITarget, (LPCWSTR)pAdminUser,
        (LPCWSTR)pAdminPassword);
    
    *pErrorText = (lpszErrorText) ? CComBSTR(lpszErrorText) : CComBSTR("");
    dlog.trace(L" End Mapiwrapper GlobalInit");
    return S_OK;
}

STDMETHODIMP CMapiWrapper::ImportMailOptions(BSTR OptionsTag)
{
    (void)OptionsTag;
    baseMigrationObj->ImportMail();
    return S_OK;
}

STDMETHODIMP CMapiWrapper::GetProfilelist(VARIANT *Profiles,BSTR *statusmessage)
{
    // TODO: Add your implementation code here
    dlog.trace(L" Begin Mapiwrapper GetProfilelist");
    HRESULT hr = S_OK;
	CComBSTR status = L"";

    hr = MAPIInitialize(NULL);
	if( hr != S_OK)
	{
            
		
		LPCSTR temp = format_error(hr).c_str();
		
		status.AppendBSTR(L" MapiInitialize error ");
		status.AppendBSTR(A2BSTR(temp));
		
                dlog.err(status);
		*statusmessage =  status;

                return hr;
	}

    Zimbra::Mapi::Memory::SetMemAllocRoutines(NULL, MAPIAllocateBuffer, MAPIAllocateMore,
        MAPIFreeBuffer);

    vector<string> vProfileList;
     hr = exchadmin->GetAllProfiles(vProfileList);

	 if( hr != S_OK)
	{
		
		LPCSTR temp = format_error(hr).c_str();
		
		status.AppendBSTR(L" GetAllProfiles error ");
		status.AppendBSTR(A2BSTR(temp));
		dlog.err(status);
		*statusmessage =  status;

     return hr;
	}
         if(vProfileList.size() == 0)
         {
            dlog.err(L"No profiles returned for GetAllProfiles");
			status = L"No profiles";
            *statusmessage =  status;
			status.Detach();
            return S_OK;

         }
    vector<CComBSTR> tempvectors;

    std::vector<string>::iterator its;

    for (its = (vProfileList.begin()); its != vProfileList.end(); its++)
    {
        string str = (*its).c_str();
        CComBSTR temp = SysAllocString(str_to_wstr(str).c_str());

        tempvectors.push_back(temp);
    }
    VariantInit(Profiles);
    Profiles->vt = VT_ARRAY | VT_BSTR;

    SAFEARRAY *psa;
    SAFEARRAYBOUND bounds = { (ULONG)vProfileList.size(), 0 };

    psa = SafeArrayCreate(VT_BSTR, 1, &bounds);

    BSTR *bstrArray;

    SafeArrayAccessData(psa, (void **)&bstrArray);

    std::vector<CComBSTR>::iterator it;
    int i = 0;

    for (it = (tempvectors.begin()); it != tempvectors.end(); it++, i++)
        bstrArray[i] = SysAllocString((*it).m_str);
    SafeArrayUnaccessData(psa);
    Profiles->parray = psa;

	*statusmessage =  status;
        status.Detach();

	MAPIUninitialize();

        dlog.trace(L" End Mapiwrapper GetProfilelist");
    return hr;
}

std::wstring CMapiWrapper::str_to_wstr(const std::string &str)
{
    std::wstring wstr(str.length() + 1, 0);

    MultiByteToWideChar(CP_ACP, 0, str.c_str(), (int)str.length(), &wstr[0], (int)str.length());
    return wstr;
}

STDMETHODIMP CMapiWrapper::GetFolderObjects(VARIANT *vObjects)
{
    HRESULT hr = S_OK;

    VariantInit(vObjects);
    /*vObjects->vt = VT_ARRAY | VT_DISPATCH;

    SAFEARRAY *psa;
    SAFEARRAYBOUND bounds = { 2, 0 };

    psa = SafeArrayCreate(VT_DISPATCH, 1, &bounds);

    IFolderObject **pfolders;

    SafeArrayAccessData(psa, (void **)&pfolders);
    for (int i = 0; i < 2; i++)
    {
        CComPtr<IFolderObject> pIFolderObject;
        // Isampleobj* pIStatistics;
        hr = CoCreateInstance(CLSID_FolderObject, NULL, CLSCTX_ALL, IID_IFolderObject,
            reinterpret_cast<void **>(&pIFolderObject));
        if (SUCCEEDED(hr))
        {
            pIFolderObject->put_Name(L"testoing");      // so far so good
            pIFolderObject->put_Id(12222);
            pIFolderObject->put_FolderPath(L"\\Inbox\\personal\\mine");
        }
        if (FAILED(hr))
            return S_FALSE;
        pIFolderObject.CopyTo(&pfolders[i]);
    }
    SafeArrayUnaccessData(psa);
    vObjects->parray = psa;
    *///not been used remove it from idl 
    return hr;
}

STDMETHODIMP CMapiWrapper::GlobalUninit(BSTR *pErrorText)
{
    (void)pErrorText;
    dlog.trace(L"Begin Mapiwrapper::GlobalUninit");

    LPCWSTR lpszErrorText = ExchangeOps::GlobalUninit();

    *pErrorText = (lpszErrorText) ? CComBSTR(lpszErrorText) : CComBSTR("");
    dlog.trace(L"End Mapiwrapper::GlobalUninit");
    return S_OK;
}

STDMETHODIMP CMapiWrapper::AvoidInternalErrors(BSTR lpToCmp, LONG *lRetval)
{
    *lRetval=(LONG)ExchangeOps::AvoidInternalErrors((LPCWSTR)lpToCmp);
    return S_OK;
}

STDMETHODIMP CMapiWrapper::SelectExchangeUsers(VARIANT *Users, BSTR *pErrorText)
{
    dlog.trace(L"Begin Mapiwrapper::SelectExchangeUsers");
    vector<ObjectPickerData> vUserList;

    LPCWSTR lpszErrorText = ExchangeOps::SelectExchangeUsers(vUserList);
    if(lpszErrorText != NULL)
    {
        dlog.err(L"Mapiwrapper ExchangeOps::SelectExchangeUsers errors out", lpszErrorText);
        return S_FALSE;


    }

    if(vUserList.size() == 0)
    {

        dlog.err(L"Mapiwrapper ExchangeOps::SelectExchangeUsers returns no users ");
        return S_OK;


    }
    vector<CComBSTR> tempvectors;

    std::vector<ObjectPickerData>::iterator its;

    wstring str = L"", strDisplayName = L"", strGivenName = L"", strSN = L"", strZFP = L"";
    for (its = (vUserList.begin()); its != vUserList.end(); its++)
    {
        ObjectPickerData obj = (*its);

        // FBS bug 71646 -- 3/26/12
        str = (*its).wstrUsername;
        for (size_t j = 0; j < (*its).pAttributeList.size(); j++)
        {
            if ((*its).pAttributeList[j].first == L"displayName")
            {
                strDisplayName = (*its).pAttributeList[j].second;
            }
            if ((*its).pAttributeList[j].first == L"givenName")
            {
                strGivenName = (*its).pAttributeList[j].second;
            }
            if ((*its).pAttributeList[j].first == L"sn")
            {
                strSN = (*its).pAttributeList[j].second;
            }
            if ((*its).pAttributeList[j].first == L"zimbraForeignPrincipal")
            {
                strZFP = (*its).pAttributeList[j].second;
            }
        }

        str += L"~";
        str += strDisplayName;
        str += L"~";
        str += strGivenName;
        str += L"~";
        str += strSN;
        str += L"~";
        str += strZFP;
        //
        CComBSTR temp = SysAllocString(str.c_str());
        tempvectors.push_back(temp);
    }
    VariantInit(Users);
    Users->vt = VT_ARRAY | VT_BSTR;

    SAFEARRAY *psa;
    SAFEARRAYBOUND bounds = { (ULONG)vUserList.size(), 0 };

    psa = SafeArrayCreate(VT_BSTR, 1, &bounds);

    BSTR *bstrArray;

    SafeArrayAccessData(psa, (void **)&bstrArray);

    std::vector<CComBSTR>::iterator it;
    int i = 0;

    for (it = (tempvectors.begin()); it != tempvectors.end(); it++, i++)
        bstrArray[i] = SysAllocString((*it).m_str);
    SafeArrayUnaccessData(psa);

    Users->parray = psa;
    *pErrorText = (lpszErrorText) ? CComBSTR(lpszErrorText) : CComBSTR("");
    dlog.trace(L"End Mapiwrapper::SelectExchangeUsers");
    return S_OK;
}

/*STDMETHODIMP CMapiWrapper::GetFolderObjects(long start, long length, SAFEARRAY **SequenceArr )
 * {
 *
 *              SAFEARRAYBOUND dimensions[1];
 *              dimensions[0].cElements = length;
 *              dimensions[0].lLbound = start;
 *
 *
 * //		*SequenceArr = SafeArrayCreate(VT_DISPATCH, 1, dimensions);
 * *SequenceArr = SafeArrayCreate(VT_UNKNOWN, 1, dimensions);
 *
 *
 * long result ;
 *              for (long i = 0; i < length; i++)
 *              {
 *                      long indices[1];
 *                      indices[0] = 0;
 *
 *                      CComPtr<IfolderObject> pIFolder;
 *                      CComPtr<IUnknown> pUnk;
 *                       //HRESULT hr = CoCreateInstance(CLSID_folderObject, NULL, CLSCTX_ALL, IID_IfolderObject, reinterpret_cast<void **>(&pIFolder));
 *                      HRESULT hr = CoCreateInstance(CLSID_folderObject, NULL, CLSCTX_ALL, _uuidof(IUnknown), reinterpret_cast<void **>(&pUnk));
 *          if (SUCCEEDED(hr))
 *
 *       pUnk->QueryInterface(IID_IfolderObject,pIFolder);
 *
 *
 *                      pIFolder->put_Name(L"testoing"); // so far so good
 *
 *                      //long result = SafeArrayPutElement(pEquationsStatistics, indices, pIStatistics);
 *              result = SafeArrayPutElement(*SequenceArr, indices, pIFolder);
 *
 *
 *                      indices[0]++;
 *              }
 *
 * SafeArrayUnaccessData(*SequenceArr);
 *
 *
 * return S_OK;
 * }*/

// ////////////////////////////////////////////////////////////////////////////
// ////
// ////////////////////////////////////////////////////////////////////////////

/*HRESULT CMapiWrapper::IsUDTFolderArray( SAFEARRAY *pUDTArr, bool &isDynamic )
 * {
 * /*     if( !pUDTArr ) {
 *      return( S_FALSE );  //we may create it
 *      //isDynamic = true;
 *  }
 *
 *  //CHECK DIMENTIONS IF IN LIKE
 *  HRESULT hr = S_OK;
 *  long dims = SafeArrayGetDim( pUDTArr );
 *  if( dims != 1 ) {
 *      hr = Error( _T("Not Implemented for multidimentional arrays") );
 *      return( hr );
 *  }
 *
 *  unsigned short feats = pUDTArr->fFeatures; //== 0x0020;
 *  if( (feats & FADF_RECORD) != FADF_RECORD ) {
 *      hr = Error( _T("Array is expected to hold structures") );
 *      return( hr );
 *  }
 *
 *  //check the actual structure type
 *  IRecordInfo *pUDTRecInfo = NULL;
 *  hr = ::SafeArrayGetRecordInfo( pUDTArr, &pUDTRecInfo );
 *  if( FAILED( hr ) &&  !pUDTRecInfo )
 *      return( hr );
 *
 *  BSTR  udtName = ::SysAllocString( L"UDTVariable" );
 *  BSTR  bstrUDTName = NULL; //if not null. we are going to have problem
 *  hr = pUDTRecInfo->GetName( &bstrUDTName);
 *  if( VarBstrCmp( udtName, bstrUDTName, 0, GetUserDefaultLCID()) != VARCMP_EQ ) {
 *      ::SysFreeString( bstrUDTName );
 *      ::SysFreeString( udtName );
 *      //hr = Error(_T("Object Does Only support [UDTVariable] Structures") );
 *      return( hr );
 *  }
 *
 *
 *
 *
 * #ifndef NDEBUG
 *  { //debug block to see other information
 *      ULONG recsize, fieldcount = 1;
 *      BSTR  pBstr = NULL;
 *      CComBSTR   str;
 *
 *      HRESULT hr1 = pUDTRecInfo->GetSize( &recsize );
 *      hr1 = pUDTRecInfo->GetName( &pBstr);
 *      str = pBstr;
 *
 *      //Fields are 1 based
 *      hr1 = pUDTRecInfo->GetFieldNames( &fieldcount, &pBstr );
 *      str = pBstr;    //retrieve one field
 *      fieldcount++;
 *      hr1 = pUDTRecInfo->GetFieldNames( &fieldcount, &pBstr );
 *      str = pBstr; //retrieve second fields
 *      fieldcount++;
 *      hr1 = pUDTRecInfo->GetFieldNames( &fieldcount, &pBstr );
 *      str = pBstr; //retrieve third fields
 *
 *      ITypeInfo  *pInfo;
 *      hr1 = pUDTRecInfo->GetTypeInfo( &pInfo );
 *      if( pInfo )
 *          pInfo->Release();
 *
 * //        bool isAuto = (feats & FADF_AUTO) != 0; //do not care so much
 *
 *     // bool isStatic = (feats & FADF_STATIC) != 0;   //these are contradicory
 *      isDynamic = !((feats & FADF_FIXEDSIZE) != 0);
 *  }
 * #endif
 *
 *
 *  isDynamic = !((feats & FADF_FIXEDSIZE) != 0);
 *
 *  pUDTRecInfo->Release();
 *  ::SysFreeString( bstrUDTName );
 *  ::SysFreeString( udtName );
 *
 *  return( S_OK );
 * }*/
