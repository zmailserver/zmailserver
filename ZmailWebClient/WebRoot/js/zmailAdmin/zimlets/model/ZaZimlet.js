/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
/**
* @class ZaZimlet
* @contructor ZaZimlet
* @param ZaApp app
* this class is a model for managing Zimlets
* @author Greg Solovyev
**/
ZaZimlet = function() {
	ZaItem.call(this, "ZaZimlet");
	this.label = "";
	this.type = ZaItem.ZIMLET;
	this[ZaModel.currentStep] = 1;
}
ZaZimlet.prototype = new ZaItem;
ZaZimlet.prototype.constructor = ZaZimlet;
ZaItem.loadMethods["ZaZimlet"] = new Array();
ZaItem.initMethods["ZaZimlet"] = new Array();
ZaZimlet.NULL_ZIMLET="org_zmail_noop";
ZaZimlet.A_name = "name";
ZaZimlet.A_zmailZimletEnabled = "zmailZimletEnabled";
ZaZimlet.A_zmailZimletPriority = "zmailZimletPriority";
ZaZimlet.A_zmailZimletIsExtension = "zmailZimletIsExtension";
ZaZimlet.A_zmailZimletKeyword = "zmailZimletKeyword";
ZaZimlet.A_zmailZimletVersion = "zmailZimletVersion";
ZaZimlet.A_zmailZimletDescription = "zmailZimletDescription";
ZaZimlet.A_zmailZimletIndexingEnabled = "zmailZimletIndexingEnabled";
ZaZimlet.A_zmailZimletStoreMatched = "zmailZimletStoreMatched";
ZaZimlet.A_zmailZimletHandlerClass = "zmailZimletHandlerClass";
ZaZimlet.A_zmailZimletHandlerConfig = "zmailZimletHandlerConfig";
ZaZimlet.A_zmailZimletContentObject = "zmailZimletContentObject";
ZaZimlet.A_zmailZimletPanelItem = "zmailZimletPanelItem";
ZaZimlet.A_zmailCreateTimestamp = "zmailCreateTimestamp";
ZaZimlet.A_zmailZimletScript = "zmailZimletScript";
ZaZimlet.A_zmailZimletServerIndexRegex = "zmailZimletServerIndexRegex";
ZaZimlet.A_zmailAdminExtDisableUIUndeploy = "zmailAdminExtDisableUIUndeploy";
ZaZimlet.A_attachmentId = "attId";
ZaZimlet.A_uploadStatus = "uploadStatus";
ZaZimlet.A_deployStatus = "deployStatus";
ZaZimlet.A_uploadStatusMsg = "uploadStatusMsg";
ZaZimlet.A_deployStatusMsg = "deployStatusMsg";
ZaZimlet.A_statusMsg = "statusMsg";
ZaZimlet.EXCLUDE_MAIL = "mail";
ZaZimlet.EXCLUDE_EXTENSIONS = "extension";
ZaZimlet.EXCLUDE_NONE = "none";	
ZaZimlet.STATUS_FAILED = "failed";
ZaZimlet.STATUS_SUCCEEDED = "succeeded";
ZaZimlet.STATUS_PENDING = "pending";
ZaZimlet.ACTION_DEPLOY_ALL = "deployAll";
ZaZimlet.ACTION_DEPLOY_LOCAL = "deployLocal";
ZaZimlet.ACTION_DEPLOY_STATUS = "status";
ZaZimlet.A_progress = "progress";
ZaZimlet.A_flushCache = "flushCache";
ZaZimlet.prototype.toString = function() {
	return this.name;
}

ZaZimlet.getAll =
function(exclude, callback) {
	var exc = exclude ? exclude : "none";
	var soapDoc = AjxSoapDoc.create("GetAllZimletsRequest", ZaZmailAdmin.URN, null);	
	soapDoc.getMethod().setAttribute("exclude", exc);	
	//var command = new ZmCsfeCommand();
	var params = {
        soapDoc: soapDoc,
        asyncMode: Boolean(callback),
        callback: callback && new AjxCallback(ZaZimlet._handleGetAllResponse, [callback])
    };
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_ZIMLET
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams);
    return resp && ZaZimlet._handleGetAllResponse(null, resp);
}

ZaZimlet._handleGetAllResponse = function(callback, resp) {
    var list = new ZaItemList(ZaZimlet);
    resp = resp instanceof ZmCsfeResult ? resp.getResponse() : resp;
    list.loadFromJS(resp.Body.GetAllZimletsResponse);
    
    // cache all the zimlets information, so we can them in other pages
    // format: zimlet-name --> ZaZimlet object
    if (ZaZimlet.zimlets == null) {
    	ZaZimlet.zimlets = new Object();
    }
    var newZimlets = new Object();
    var zimlets = list.getVector()._array;
    for(var i in zimlets) {
    	var z = zimlets[i];
    	newZimlets[z[ZaZimlet.A_name]] = z;
    }
    
    // compare and decide which zimlets to include
    var incList;

	incList = new ZaItemList(ZaZimlet);
	for (var zimletName in newZimlets) {
		var oz = ZaZimlet.zimlets[zimletName];
		var nz = newZimlets[zimletName];
		
		if(!oz) {
			// put the new zimlet into cache
			ZaZimlet.zimlets[zimletName] = nz;
			incList.add(nz);
		} else {
			if (nz.attrs[ZaZimlet.A_zmailCreateTimestamp] !=
    			oz.attrs[ZaZimlet.A_zmailCreateTimestamp]) {
    			// the zimlet has been updated
				ZaZimlet.zimlets[zimletName] = nz;
				incList.add(nz);
    		}
		}
	}
    
    if (callback) {
    	var args = callback.args;
        args = args ? (args instanceof Array ? args : [args]) : [];
   		callback = new AjxCallback(callback.obj, callback.func, args.concat(list));
    	if (incList.size() == 0) {
    		callback.run();
    	} else {
    		  // callback need to know the whole list, 
    		  // but _handleGetAllResouce need only know what are to be included
    	    ZaZimlet._handleGetAllResources(incList, callback);
    	}     
    } else {
    	if (incList.size() > 0) {
    		ZaZimlet._handleGetAllResources(incList);
    	}
    }
    return list;
};

ZaZimlet._handleGetAllResources = function(list, callback) {
    var includes = [];
    for (var id in list.getIdHash()) {
        var zimlet = list.getItemById(id);
        // NOTE: Setting an ID on the includes will replace the old SCRIPT
        // NOTE: tags with the new ones when the resources are requested
        // NOTE: again.
        includes.push( { src:["/res/",zimlet.name,".js?v=",appVers,ZaZmailAdmin.LOCALE_QS].join(""),id:"res_"+zimlet.name } );
    }
    var baseurl = appContextPath;
    var proxy = null;
    AjxInclude(includes, baseurl, callback, proxy);
};

ZaZimlet.prototype.isEnabled =
function () {
	var status = this.attrs[ZaZimlet.A_zmailZimletEnabled];
	if (status != null && status == "TRUE") {
		return true;
	}else {
		return false ;
	}
}

ZaZimlet.__RE_MSG = /\$\{msg\.(.*?)\}/g;
ZaZimlet.prototype.getDescription = 
function () {
	var des = null;
	var name = null;
	if(this.attrs && this.attrs[ZaZimlet.A_zmailZimletDescription]){
		name =this[ZaZimlet.A_name];
		des = this.attrs[ZaZimlet.A_zmailZimletDescription];
		des = (des ||"").replace(ZaZimlet.__RE_MSG, function($0, $1) {
        		var res = window[name];
        		return (res && res[$1]) || $0;
    			});	
	}
	return des;
}

ZaZimlet.prototype.getLabel = 
function (){
	var label = null;
	var name = this[ZaZimlet.A_name];

	if(this.label){
		label = this.label;
                label = (label ||"").replace(ZaZimlet.__RE_MSG, function($0, $1) {
                        var res = window[name];
                        return (res && res[$1]) || $0;
                        });

	}
	
	if(!label){
		var res = window[name];
		label = (res && (res["label"] || res["zimletLabel"])) || name;
	}
	return label;
}

ZaZimlet.prototype.enable = function (enabled, callback) {
	var soapDoc = AjxSoapDoc.create("ModifyZimletRequest", ZaZmailAdmin.URN, null);
	var zimletEl = soapDoc.set("zimlet", "");
	zimletEl.setAttribute("name", this.name);
	var statusEl = soapDoc.set("status", "",zimletEl);	
	if(enabled)	 {
		statusEl.setAttribute("value","enabled");
		this.attrs[ZaZimlet.A_zmailZimletEnabled] = "TRUE";
	} else {
		statusEl.setAttribute("value","disabled");
		this.attrs[ZaZimlet.A_zmailZimletEnabled] = "FALSE";		
	}
	//var asynCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_ZIMLET
	}
	ZaRequestMgr.invoke(params, reqMgrParams);	
}

/**
* @param mods - map of modified attributes
* modifies object's information in the database
**/
ZaZimlet.prototype.modify =
function(mods) {
	/*var soapDoc = AjxSoapDoc.create("ModifyZimletRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	for (var aname in mods) {
		if (mods[aname] instanceof Array) {
			var array = mods[aname];
			if (array.length > 0) {
				for (var i = 0; i < array.length; i++) {
					var attr = soapDoc.set("a", array[i]);
					attr.setAttribute("n", aname);
				}
			}
			else {
				var attr = soapDoc.set("a");
				attr.setAttribute("n", aname);
			}
		}
		else {
			var attr = soapDoc.set("a", mods[aname]);
			attr.setAttribute("n", aname);
		}
	}
	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var resp = command.invoke(params).Body.ModifyZimletResponse;*/		
}

/**
* Returns HTML for a tool tip for this domain.
*/
ZaZimlet.prototype.getToolTip =
function() {
	// update/null if modified
	if (!this._toolTip) {
		var html = new Array(20);
		var idx = 0;
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0'>";
		html[idx++] = "<tr valign='center'><td colspan='2' align='left'>";
		html[idx++] = "<div style='border-bottom: 1px solid black; white-space:nowrap; overflow:hidden;width:350'>";
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>";
		html[idx++] = "<tr valign='center'>";
		html[idx++] = "<td><b>" + AjxStringUtil.htmlEncode(this.name) + "</b></td>";
		html[idx++] = "<td align='right'>";
		html[idx++] = AjxImg.getImageHtml("ZaZimlet");		
		html[idx++] = "</td>";
		html[idx++] = "</table></div></td></tr>";
		html[idx++] = "<tr></tr>";
		idx = this._addAttrRow(ZaItem.A_description, html, idx);		
		idx = this._addAttrRow(ZaItem.A_zmailId, html, idx);
		html[idx++] = "</table>";
		this._toolTip = html.join("");
	}
	return this._toolTip;
}

ZaZimlet.prototype.remove = 
function() {
	var soapDoc = AjxSoapDoc.create("UndeployZimletRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("name", this.name);	
	//soapDoc.set("id", this.id);
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_UNDEPLOY_ZIMLET
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams);	
}

ZaZimlet.prototype.refresh = 
function() {
	this.load();	
}
/**
 * @param attrs {action:deployall|deploylocal|status,attId:"",flushCache:true|false}
 */
ZaZimlet.deploy = function (params,callback) {
	var action = params.action ? params.action : ZaZimlet.ACTION_DEPLOY_ALL;
	var attId = params.attId ? params.attId : null;
	var flushCache = params.flushCache ? params.flushCache : "0";
	var soapDoc = AjxSoapDoc.create("DeployZimletRequest", ZaZmailAdmin.URN, null);
	soapDoc.getMethod().setAttribute("action", action);	
	
	if(action != ZaZimlet.ACTION_DEPLOY_STATUS)	
		soapDoc.getMethod().setAttribute("flush", flushCache);
	
	var contentEl = soapDoc.set("content", "");
	if(attId) {
		contentEl.setAttribute("aid", attId);
	}
	//var asynCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_DEPLOY_ZIMLET
	}
	
	ZaRequestMgr.invoke(params, reqMgrParams);	
}

ZaZimlet.loadMethod = 
function(by, val) {
	var _val = val ? val : this.name ;
	var soapDoc = AjxSoapDoc.create("GetZimletRequest", ZaZmailAdmin.URN, null);
	var elZimlet = soapDoc.set("zimlet", "");
	elZimlet.setAttribute("name", _val);
	if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
		soapDoc.setMethodAttribute("attrs", this.attrsToGet.join(","));
	}	
	var params = {};
	params.soapDoc = soapDoc;	
	params.asyncMode = false;
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_ZIMLET
	}
	resp = ZaRequestMgr.invoke(params, reqMgrParams);
	this.initFromJS(resp.Body.GetZimletResponse.zimlet[0]);
}
ZaItem.loadMethods["ZaZimlet"].push(ZaZimlet.loadMethod);

ZaZimlet.myXModel = { 
	items:[
        { id:ZaZimlet.A_name, ref:ZaZimlet.A_name, type: _STRING_ },
        {id:ZaItem.A_zmailId, type:_STRING_, ref:"attrs/" + ZaItem.A_zmailId},
        {id:ZaItem.A_zmailCreateTimestamp, ref:"attrs/" + ZaItem.A_zmailCreateTimestamp},
        { id:ZaZimlet.A_flushCache,ref:ZaZimlet.A_flushCache,type: _ENUM_, choices:ZaModel.BOOLEAN_CHOICES2},
        { id:ZaZimlet.A_zmailZimletDescription, ref:"attrs/" + ZaZimlet.A_zmailZimletDescription, type: _STRING_ },
        { id:ZaZimlet.A_zmailZimletEnabled, ref:"attrs/" + ZaZimlet.A_zmailZimletEnabled, type: _ENUM_,  choices:ZaModel.BOOLEAN_CHOICES} 
    ]
}
