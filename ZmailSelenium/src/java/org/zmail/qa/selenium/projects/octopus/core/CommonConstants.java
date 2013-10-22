/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
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
package org.zmail.qa.selenium.projects.octopus.core;

public interface CommonConstants {
	String JPG_FILE = "samplejpg.jpg";
	String PPT_FILE = "testpptfile.ppt";
	String LOG_FILE = "putty.log";
	String EXCEL_FILE = "testexcelFile.xls";
	String BMP_FILE = "testbitmapfile.bmp";
	String WAV_FILE = "testsoundfile.wav";
	String TEXT_FILE= "testtextfile.txt";
	String WORD_FILE= "testwordfile.doc";
	String XML_FILE ="testxmlfile.xml";
	String ZIP_FILE = "org_zmail_ymaps.zip";
	
	String SHARE_AS_READ = "r";
	String SHARE_AS_READWRITE = "rwidx";
	String SHARE_AS_ADMIN = "rwidxa";

	String OPTION_RENAME="Rename";
	String OPTION_MOVE="Move";
	String OPTION_DELETE="Delete";
	String OPTION_SHARE="Share";
	String OPTION_FAVORITE="Favorite";
	String OPTION_NOT_FAVORITE="Not Favorite";
	String OPTION_LEAVE_SHARED_FOLDER="Leave this Shared Folder";
	String OPTION_UPLOAD="Upload";
	String OPTION_NEW_FOLDER="New Folder";
	String OPTION_SHARED_VIA_PARENT="Shared via Parent Folder";

	enum SHARE_PERMISSION {SHARE_AS_READ, SHARE_AS_READWRITE, SHARE_AS_ADMIN};

	//TODO: add full list of filename charactesrs
	String REGEXP_FILENAME = "[0-9a-zA-Z_ ]+.?[0-9a-zA-Z_ ]*";


	//user name can also be an email address if display name is missing
	String REGEXP_USER = "[0-9a-zA-Z_]+(@[a-zA-Z\\-]+.[a-zA-Z]+)?";
}