/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2009, 2010, 2012 VMware, Inc.
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

ZmSpreadSheetApp = function(){

    appCtxt.setAppController(this);

    this._init();
    this.startup();

    window.app = this;
    
};

ZmSpreadSheetApp.prototype.constructor = ZmSpreadSheetApp;

ZmSpreadSheetApp.APP_ZIMBRA_XLS = "application/x-zimbra-xls";


ZmSpreadSheetApp.prototype.toString = function(){
    return "ZmSpreadSheetApp";
};

ZmSpreadSheetApp.prototype._init = function(){
    this._controller = new ZmSpreadSheetController(appCtxt.getShell());  
};

ZmSpreadSheetApp.prototype.startup = function(){
    this._controller.show();  
};


ZmSpreadSheetApp.launch = function(){

    window.appCtxt = new ZmAppCtxt();
    appCtxt.rememberMe = false;

    window.skin = null;
    
    // Create and initialize settings
    var settings = new ZmSettings();
    appCtxt.setSettings(settings);

    var shell = new DwtShell({className:"MainShell"});
    appCtxt.setShell(shell);

    shell.getKeyboardMgr().registerKeyMap(new DwtKeyMap(true));

    //Removing all the arguments
    var rest = location.href;
    ZmSpreadSheetApp.restUrl = rest.replace(/\?.*/,'');

    new ZmSpreadSheetApp();

};

ZmSpreadSheetApp.setItemInfo = function(item){
    ZmSpreadSheetApp.fileInfo = item;
    ZmSpreadSheetApp.fileInfo.loaded = true;
};

ZmSpreadSheetApp.setFile = function(fileId, fileName, folderId){

    if(!fileId || fileId == ""){
       fileId = null;
   }

   if(!fileName || fileName == ""){
       fileName = fileId ? null : ZmMsg.untitled
   }

   folderId = (!folderId || folderId == "") ? ZmOrganizer.ID_BRIEFCASE : folderId; 

   ZmSpreadSheetApp.fileInfo = {
       folderId: folderId,
       contentType: ZmSpreadSheetApp.APP_ZIMBRA_XLS,
       name:    fileName,
       id:      fileId,
       version: 1
   };
};

ZmSpreadSheetApp.prototype.setStatusMsg = function(){
    if(!this.statusView){
        this.statusView = new ZmStatusView(appCtxt.getShell(), "ZmStatus", Dwt.ABSOLUTE_STYLE, ZmId.STATUS_VIEW);
    }
    params = Dwt.getParams(arguments, ZmStatusView.MSG_PARAMS);
    params.transitions = ZmToast.DEFAULT_TRANSITIONS;
	this.statusView.setStatusMsg(params);
};

ZmSpreadSheetApp.prototype.exit = function(){
    return this._controller.checkForChanges();
};

window.onbeforeunload = function() {
    return appCtxt.getAppController().exit();
};

window.onload = function() {
    setTimeout(function() {
            ZmSpreadSheetApp.launch();
    }, 200);
};