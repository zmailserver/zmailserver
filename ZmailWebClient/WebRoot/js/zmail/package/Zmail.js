/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
/*
 * Support for a generic login page, error dialog,
 * splash screen, and for sending requests to the server.
 */
AjxPackage.require("zmail.csfe.ZmBatchCommand");
AjxPackage.require("zmail.csfe.ZmCsfeCommand");
AjxPackage.require("zmail.csfe.ZmCsfeException");
AjxPackage.require("zmail.csfe.ZmCsfeResult");
AjxPackage.require("zmail.common.ZmBaseSplashScreen");
AjxPackage.require("zmail.common.ZmErrorDialog");
AjxPackage.require("zmail.common.ZLoginFactory");
