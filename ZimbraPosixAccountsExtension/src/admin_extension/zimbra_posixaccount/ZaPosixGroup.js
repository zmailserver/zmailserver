if(ZaItem) {
	ZaItem.POSIX_GROUP = "posixGroup";
}

function ZaPosixGroup() {
	//if (arguments.length == 0) return;	
	ZaItem.call(this, "ZaPosixGroup");
	this.type = ZaItem.POSIX_GROUP;
	this.attrs = [];
	this.attrs[ZaItem.A_objectClass] = [];
	this.attrs[ZaPosixGroup.A_memberUid] = [];
	this._init();
}


ZaPosixGroup.prototype = new ZaItem;
ZaPosixGroup.prototype.constructor = ZaPosixGroup;

ZaPosixGroup.A_cn = "cn";
ZaPosixGroup.A_gidNumber = "gidNumber";
ZaPosixGroup.A_description = "description";
ZaPosixGroup.A_memberUid = "memberUid";
ZaPosixGroup.A_userPassword = "userPassword";

ZaItem.loadMethods["ZaPosixGroup"] = new Array();
ZaItem.initMethods["ZaPosixGroup"] = new Array();
ZaItem.modifyMethods["ZaPosixGroup"] = new Array();
ZaItem.createMethods["ZaPosixGroup"] = new Array()

ZaPosixGroup.prototype.loadEffectiveRights = function () {
	this.getAttrs = {all:true};
	this.setAttrs = {all:true};
	this.rights = {};
}

ZaPosixGroup.loadMethod = function(by, val) {
	if(!val)
		return;
		
	var soapDoc = AjxSoapDoc.create("GetLDAPEntriesRequest", "urn:zimbraAdmin", null);	
	soapDoc.set("ldapSearchBase", zimbra_posixaccount_ext.ldapSuffix);
	soapDoc.set("query", "(&(objectClass=posixGroup)(" + by + "="+val+"))");	

	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;

	var reqMgrParams = {} ;
	reqMgrParams.controller = ZaApp.getInstance().getCurrentController();
	reqMgrParams.busyMsg = zimbra_posixaccount.BUSY_GETTING_POSIX_GROUP ;
	var resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.GetLDAPEntriesResponse;
	this.attrs[ZaPosixGroup.A_memberUid] = [];
	if(resp && resp.LDAPEntry) {	
		this.initFromJS(resp.LDAPEntry[0]);
	}
}

if(ZaItem.loadMethods["ZaPosixGroup"]) {
	ZaItem.loadMethods["ZaPosixGroup"].push(ZaPosixGroup.loadMethod);
}

ZaPosixGroup.initMethod = function () {
	this.attrs[ZaItem.A_objectClass].push("posixGroup");
}
if(ZaItem.initMethods["ZaPosixGroup"]) {
	ZaItem.initMethods["ZaPosixGroup"].push(ZaPosixGroup.initMethod);
}

ZaPosixGroup.getNextGid = function () {
	var soapDoc = AjxSoapDoc.create("GetLDAPEntriesRequest", "urn:zimbraAdmin", null);	
	soapDoc.set("ldapSearchBase", zimbra_posixaccount_ext.ldapSuffix);
	soapDoc.set("query", "(objectClass=posixGroup)");	
	soapDoc.set("sortBy", ZaPosixGroup.A_gidNumber);	
	soapDoc.set("sortAscending", "false");		
	soapDoc.set("limit", "1");			
	var getPosixGroupsCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var nextId = !isNaN(zimbra_posixaccount_ext.gidBase) ?  parseInt(zimbra_posixaccount_ext.gidBase) + 1 : 10001;
	try {
		var resp = getPosixGroupsCommand.invoke(params).Body.GetLDAPEntriesResponse.LDAPEntry[0];
		if(resp) {
			var grp = new ZaPosixGroup(new Object());;
			grp.initFromJS(resp);
			nextId = parseInt(grp.attrs[ZaPosixGroup.A_gidNumber])+1;
		}
	} catch (ex) {
		//do nothing - fallback to default id for now, ideally should show a warning
	}
	return 	nextId;
}

ZaPosixGroup.prototype.refresh = function () {
	this.load("cn", this.name,true,false);
}

ZaPosixGroup.prototype.initFromJS = function(posixGroup) {
	this.attrs[ZaPosixGroup.A_memberUid] = [];
	ZaItem.prototype.initFromJS.call(this, posixGroup);
	
	if(this.attrs && this.attrs[ZaPosixGroup.A_memberUid] && !(this.attrs[ZaPosixGroup.A_memberUid] instanceof Array))
		this.attrs[ZaPosixGroup.A_memberUid] = [this.attrs[ZaPosixGroup.A_memberUid]];
	
	if(!this.attrs[ZaPosixGroup.A_memberUid]) {
		this.attrs[ZaPosixGroup.A_memberUid] = [];
	}
	
	if(this.attrs && this.attrs[ZaPosixGroup.A_gidNumber])
		this.id = this.attrs[ZaPosixGroup.A_gidNumber];
		
	if(!this.name && this.attrs && this.attrs[ZaPosixGroup.A_cn])
		this.name = this.attrs[ZaPosixGroup.A_cn];

}

ZaPosixGroup.prototype.remove = 
function(callback) {
	var soapDoc = AjxSoapDoc.create("DeleteLDAPEntryRequest", "urn:zimbraAdmin", null);

	var dn = [["cn=",this.attrs["cn"]].join("")];

	if(zimbra_posixaccount_ext.ldapGroupSuffix)
		dn.push(zimbra_posixaccount_ext.ldapGroupSuffix);
		
	if(zimbra_posixaccount_ext.ldapSuffix)
		dn.push(zimbra_posixaccount_ext.ldapSuffix);
		

	soapDoc.set("dn", dn.join(","));	

	this.deleteCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	if(callback) {
		params.asyncMode = true;
		params.callback = callback;
	}
	this.deleteCommand.invoke(params);		
}

ZaPosixGroup.getAll =
function() {
	var soapDoc = AjxSoapDoc.create("GetLDAPEntriesRequest", "urn:zimbraAdmin", null);	
	soapDoc.set("ldapSearchBase", zimbra_posixaccount_ext.ldapSuffix);
	soapDoc.set("query", "objectClass=posixGroup");	
	var getSambaDomainsCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	var resp = getSambaDomainsCommand.invoke(params).Body.GetLDAPEntriesResponse;
	var list = new ZaItemList(ZaPosixGroup);
	list.loadFromJS(resp);		
	return list;
}

ZaPosixGroup.createMethod = function(tmpObj, group) {
	//test
	var soapDoc = AjxSoapDoc.create("CreateLDAPEntryRequest", "urn:zimbraAdmin", null);
	var dn = [["cn=",tmpObj.attrs["cn"]].join("")];

	if(zimbra_posixaccount_ext.ldapGroupSuffix)
		dn.push(zimbra_posixaccount_ext.ldapGroupSuffix);
		
	if(zimbra_posixaccount_ext.ldapSuffix)
		dn.push(zimbra_posixaccount_ext.ldapSuffix);
		

	soapDoc.set("dn", dn.join(","));	
	for (var aname in tmpObj.attrs) {
		if(tmpObj.attrs[aname] instanceof Array) {
			var cnt = tmpObj.attrs[aname].length;
			if(cnt) {
				for(var ix=0; ix <cnt; ix++) {
					if(typeof(tmpObj.attrs[aname][ix])=="object") {
						var attr = soapDoc.set("a", tmpObj.attrs[aname][ix].toString());
						attr.setAttribute("n", aname);
					} else {
						var attr = soapDoc.set("a", tmpObj.attrs[aname][ix]);
						attr.setAttribute("n", aname);						
					}
				}
			} 
		} else {	
			if(tmpObj.attrs[aname] != null) {
				if(typeof(tmpObj.attrs[aname]) == "object") {				
					var attr = soapDoc.set("a", tmpObj.attrs[aname].toString());
					attr.setAttribute("n", aname);
				} else {
					var attr = soapDoc.set("a", tmpObj.attrs[aname]);
					attr.setAttribute("n", aname);					
				}
			}
		}
	}
	

	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;	
	var reqMgrParams = {} ;
	reqMgrParams.controller = ZaApp.getInstance().getCurrentController();
	reqMgrParams.busyMsg = zimbra_posixaccount.BUSY_CREATING_POSIX_GROUP ;
	resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.CreateLDAPEntryResponse;
	
	if(resp.LDAPEntry)		
		group.initFromJS(resp.LDAPEntry[0]);
}


if(ZaItem.createMethods["ZaPosixGroup"]) {
	ZaItem.createMethods["ZaPosixGroup"].push(ZaPosixGroup.createMethod);
}


/**
* @method modify
* Updates ZaPosixGroup attributes (SOAP)
* @param mods set of modified attributes and their new values
*/
ZaPosixGroup.modifyMethod =
function(mods) {
	var cn = this.attrs["cn"];
	if(mods["cn"]) {
		cn = mods["cn"];
		var soapDoc = AjxSoapDoc.create("RenameLDAPEntryRequest", "urn:zimbraAdmin", null);			
		var dn = [["cn=",this.attrs["cn"]].join("")];		
		var new_dn = [["cn=",mods["cn"]].join("")];				
		
		if(zimbra_posixaccount_ext.ldapGroupSuffix) {
			dn.push(zimbra_posixaccount_ext.ldapGroupSuffix);
			new_dn.push(zimbra_posixaccount_ext.ldapGroupSuffix);			
		}	
		if(zimbra_posixaccount_ext.ldapSuffix) {
			dn.push(zimbra_posixaccount_ext.ldapSuffix);
			new_dn.push(zimbra_posixaccount_ext.ldapSuffix);		
		}
		soapDoc.set("dn", dn.join(","));
		soapDoc.set("new_dn", new_dn.join(","));
		var renameLDAPEntryCommand = new ZmCsfeCommand();
		var params = new Object();
		params.soapDoc = soapDoc;	
		resp = renameLDAPEntryCommand.invoke(params).Body.RenameLDAPEntryResponse;
		this.initFromJS(resp.LDAPEntry[0]);
		this._toolTip = null ;
	}

	var needToMidify = false;
	for(var a in mods) {
		if(a == "cn") {
			continue;
		} else {
			needToMidify = true;
			this._toolTip = null;			
			break;
		}
	}
	if(!needToMidify)
		return;
		
	var soapDoc = AjxSoapDoc.create("ModifyLDAPEntryRequest", "urn:zimbraAdmin", null);	
	var dn = [["cn=",this.attrs["cn"]].join("")];

	if(zimbra_posixaccount_ext.ldapGroupSuffix)
		dn.push(zimbra_posixaccount_ext.ldapGroupSuffix);
		
	if(zimbra_posixaccount_ext.ldapSuffix)
		dn.push(zimbra_posixaccount_ext.ldapSuffix);
		

	soapDoc.set("dn", dn.join(","));	
	
	
	for (var aname in mods) {
		//multy value attribute
		if(mods[aname] instanceof Array) {
			var cnt = mods[aname].length;
			if(cnt) {
				for(var ix=0; ix <cnt; ix++) {
					var attr = null;
					if(mods[aname][ix] instanceof String)
						var attr = soapDoc.set("a", mods[aname][ix].toString());
					else if(mods[aname][ix] instanceof Object)
						var attr = soapDoc.set("a", mods[aname][ix].toString());
					else if(mods[aname][ix])
						var attr = soapDoc.set("a", mods[aname][ix]);
	
					if(attr)
						attr.setAttribute("n", aname);
				}
			} else {
				var attr = soapDoc.set("a", "");
				attr.setAttribute("n", aname);
			}
		} else {
			var attr = soapDoc.set("a", mods[aname]);
			attr.setAttribute("n", aname);
		}
	}
	
	/*var modifyLDAPEntryCommand = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	resp = modifyLDAPEntryCommand.invoke(params).Body.ModifyLDAPEntryResponse;*/
	
	var csfeParams = new Object();
	csfeParams.soapDoc = soapDoc;	
	var reqMgrParams = {} ;
	reqMgrParams.controller = ZaApp.getInstance().getCurrentController();
	reqMgrParams.busyMsg = zimbra_posixaccount.BUSY_UPDATING_POSIX_GROUP ;
	resp = ZaRequestMgr.invoke(csfeParams, reqMgrParams ).Body.ModifyLDAPEntryResponse;
	
	if(resp.LDAPEntry[0])	
		this.initFromJS(resp.LDAPEntry[0]);
	//invalidate the original tooltip

	return;
}
ZaItem.modifyMethods["ZaPosixGroup"].push(ZaPosixGroup.modifyMethod);



ZaApp.prototype.getPosixGroupIdListChoices =
function(refresh) {
	if (refresh || ZaApp.PosixGroupList == null) {
		ZaApp.PosixGroupList = ZaPosixGroup.getAll(this);
	}
	if(refresh || ZaApp.PosixGroupIdChoices == null) {
		var arr = ZaApp.PosixGroupList.getArray();
		var posixGroupArr = [];
		for (var i = 0 ; i < arr.length; ++i) {
			var obj = new Object();
			obj.name = arr[i].name;
			obj.id = arr[i].id;
			posixGroupArr.push(obj);
		}
		if(ZaApp.PosixGroupIdChoices == null) {
			ZaApp.PosixGroupIdChoices = new XFormChoices(posixGroupArr, XFormChoices.OBJECT_LIST, "id", "name");
		} else {	
			ZaApp.PosixGroupIdChoices.setChoices(posixGroupArr);
			ZaApp.PosixGroupIdChoices.dirtyChoices();
		}
	}
	return ZaApp.PosixGroupIdChoices;	
}

ZaPosixGroup.myXModel = {
	items: [
		{id:"id", type:_STRING_, ref:"id"},			
		{id:"name", type:_STRING_, ref:"name"},		
		{id:ZaPosixGroup.A_cn, type:_STRING_, ref:"attrs/" + ZaPosixGroup.A_cn},	
		{id:ZaPosixGroup.A_gidNumber, type:_NUMBER_, ref:"attrs/" + ZaPosixGroup.A_gidNumber},
		{id:ZaPosixGroup.A_description, type:_STRING_, ref:"attrs/" + ZaPosixGroup.A_description},
		{id:ZaPosixGroup.A_memberUid, type:_LIST_, ref:"attrs/"+ZaPosixGroup.A_memberUid, listItem:{type:_STRING_}}
	]
};
