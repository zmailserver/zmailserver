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
// ItemObject.cpp : Implementation of CItemObject

#include "common.h"
#include "ItemObject.h"


// CItemObject

STDMETHODIMP CItemObject::InterfaceSupportsErrorInfo(REFIID riid)
{
	static const IID* const arr[] = 
	{
		&IID_IItemObject
	};

	for (int i=0; i < sizeof(arr) / sizeof(arr[0]); i++)
	{
		if (InlineIsEqualGUID(*arr[i],riid))
			return S_OK;
	}
	return S_FALSE;
}


STDMETHODIMP CItemObject::get_ID(BSTR *pVal)
{
    // TODO: Add your implementation code here
    CComBSTR str(ID);

    *pVal = str.m_str;
    return S_OK;
}

STDMETHODIMP CItemObject::put_ID(BSTR newVal)
{
    // TODO: Add your implementation code here
    ID = newVal;
    return S_OK;
}

STDMETHODIMP CItemObject::get_Type(FolderType *pVal)
{
    // TODO: Add your implementation code here

    /*CComBSTR str(TYPE);
     * *pVal = str.m_str;
     * return S_OK;*/
    *pVal = TYPE;
    return S_OK;
}

STDMETHODIMP CItemObject::put_Type(FolderType newVal)
{
    // TODO: Add your implementation code here
    TYPE = newVal;
    return S_OK;
}

STDMETHODIMP CItemObject::get_CreationDate(VARIANT *pVal)
{
    // TODO: Add your implementation code here
    _variant_t vt;

    *pVal = vt;
    return S_OK;
}

STDMETHODIMP CItemObject::put_CreationDate(VARIANT newVal)
{
    // TODO: Add your implementation code here
    _variant_t vt = newVal;

    return S_OK;
}

STDMETHODIMP CItemObject::get_Parentfolder(IFolderObject **pVal)
{
    // TODO: Add your implementation code here
    *pVal = parentObj;
    (*pVal)->AddRef();

    return S_OK;
}

STDMETHODIMP CItemObject::put_Parentfolder(IFolderObject *newVal)
{
    // TODO: Add your implementation code here
    parentObj = newVal;
    return S_OK;
}

STDMETHODIMP CItemObject::get_IDasString(BSTR *pVal)
{
    // TODO: Add your implementation code here
    /*Zimbra::Util::ScopedArray<CHAR> pszEid(NULL);
    Zimbra::Util::HexFromBin(ItemID->lpb, ItemID->cb, pszEid.get());
    LPTSTR pszCount = ULongToString(ItemID.cb);
    LPTSTR pRetVal = NULL;*/
    //LPTSTR pszHexEncoded = HexEncode(ItemID.cb, ItemID.lpb);


   *pVal = IDasString;

    return S_OK;
}

STDMETHODIMP CItemObject::put_IDasString(BSTR newVal)
{
    // TODO: Add your implementation code here
    IDasString = newVal;
    return S_OK;
}


STDMETHODIMP CItemObject::GetDataForItemID(IUserObject *Userobj,VARIANT ItemId, FolderType type, VARIANT *pVal)
{
    HRESULT hr = S_OK;
    dlog.trace(L"Begin ItemObject::GetDataForItemID");
    std::map<BSTR, BSTR> pIt;
    std::map<BSTR, BSTR>::iterator it;
    //SBinary ItemID;
    FolderType ft;

    if (type == NULL)
    {
        get_Type(&ft);
    }
    else
    {
        ft = type;
    }

	CComPtr<IMapiAccessWrap> Mapi;
	dlog.trace(L"GetMapiAccessObject for itemobject->getData ");

	hr = Userobj->GetMapiAccessObject(L"",&Mapi);
        if(FAILED(hr))
        {
        dlog.err(L"GetMapiAccessObject failed for itemobject->getData with hresult",hr);
        return hr;
        }

		if(Mapi != NULL)
		{
	hr = Mapi->GetData(L"",ItemId,ft,pVal);
        if(FAILED(hr))
        {
        dlog.err(L"Mapiobj->GetData failed for itemobject->getData with hresult",hr);
        return hr;
        }
		}
		else
			dlog.err("GetMapiAccessObject is null for itemobject->getData");
  
 dlog.trace(L"End ItemObject::GetDataForItemID");
    return hr;
}

STDMETHODIMP CItemObject::put_ItemID(VARIANT id)
{
    // FolderId = id;
// Binary data is stored in the variant as an array of unsigned char
    if (id.vt == (VT_ARRAY | VT_UI1))           // (OLE SAFEARRAY)
    {
        // Retrieve size of array
        ItemID.cb = id.parray->rgsabound[0].cElements;
        ItemID.lpb = new BYTE[ItemID.cb];       // Allocate a buffer to store the data
        if (ItemID.lpb != NULL)
        {
            void *pArrayData;

            // Obtain safe pointer to the array
            SafeArrayAccessData(id.parray, &pArrayData);
            // Copy the bitmap into our buffer
            memcpy(ItemID.lpb, pArrayData, ItemID.cb);  // Unlock the variant data
            SafeArrayUnaccessData(id.parray);
        }
    }
    return S_OK;
}

STDMETHODIMP CItemObject::get_ItemID(VARIANT *id)
{
    HRESULT hr = S_OK;

    VariantInit(id);
    id->vt = VT_ARRAY | VT_UI1;

    SAFEARRAY *psa;
    SAFEARRAYBOUND bounds[1];                   // ={1,0};

    bounds[0].cElements = ItemID.cb;
    bounds[0].lLbound = 0;

    psa = SafeArrayCreate(VT_UI1, 1, bounds);
    if (psa != NULL)
    {
        void *pArrayData = NULL;

        SafeArrayAccessData(psa, &pArrayData);
        memcpy(pArrayData, ItemID.lpb, ItemID.cb);
        // Unlock the variant data
        // SafeArrayUnaccessData(var.parray);
        SafeArrayUnaccessData(psa);
        id->parray = psa;
    }
    return hr;
}
