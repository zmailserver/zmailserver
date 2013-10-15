/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Zimlets
 * Copyright (C) 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 *@Author Raja Rao DV
 */

function org_zmail_searchrefiner() {
}

org_zmail_searchrefiner.prototype = new ZmZimletBase();
org_zmail_searchrefiner.prototype.constructor = org_zmail_searchrefiner;
org_zmail_searchrefiner.limit = 100;
org_zmail_searchrefiner.initialOffset = 0;

org_zmail_searchrefiner.prototype.init =
function() {
	this.turnSearchRefinerON = this.getUserProperty("turnSearchRefinerON") == "true";
};

//------------------------------------------------------------------------------------------
//			HANDLE SEARCH BUTTON CLICK
//------------------------------------------------------------------------------------------
org_zmail_searchrefiner.prototype.onKeyPressSearchField =
function() {
	this.onSearchButtonClick();
};

org_zmail_searchrefiner.prototype.onSearchButtonClick =
function() {
	//if we are not searching for mail/conv, dont do anything
	if (appCtxt.getSearchController()._searchFor != ZmId.SEARCH_MAIL)
		return;
	if (!this.turnSearchRefinerON)//return if zimlet is off
		return;

	//initialize..
	this._currentRequestNumber = 0;
	this._totalMsgArray = [];
	this._initializeOnce();
	this.initBaseView();


	this.wasTriggeredBySearchBtn = true;
	this._refineMore = false;
	setTimeout(AjxCallback.simpleClosure(this.doInternalSearch, this, org_zmail_searchrefiner.limit, 0), 1000);
};

org_zmail_searchrefiner.prototype._initializeOnce =
function() {
	if(this._initializeOnceCalled != undefined)
		return;
	this.refinerSearchSize = this.getUserProperty("refinerSearchSize");
	this._totalNumberOfSearchRequests = parseInt(this.refinerSearchSize) / org_zmail_searchrefiner.limit;
	this._originalPageSize = appCtxt.get(ZmSetting.PAGE_SIZE);
	this._initializeOnceCalled = true;
};

org_zmail_searchrefiner.prototype.doInternalSearch =
function(_limit, _offset) {
	if (!this.wasTriggeredBySearchBtn) {
		return;
	}

	if (this._totalNumberOfSearchRequests == this._currentRequestNumber)
		return;

	this._searchField = appCtxt.getSearchController().getSearchToolbar().getSearchField();
	var getHtml = appCtxt.get(ZmSetting.VIEW_AS_HTML);
	var callbck = new AjxCallback(this, this._handleInternalSrcResponse);
	var _types = new AjxVector();
	_types.add(ZmId.ITEM_MSG);

	this._currentRequestNumber = this._currentRequestNumber + 1;
	appCtxt.getSearchController().search({query: this._searchField.value, userText: true, limit:parseInt(_limit),  offset:parseInt(_offset), types:_types, noRender:true, getHtml: getHtml, callback:callbck});
};

org_zmail_searchrefiner.prototype._handleInternalSrcResponse =
function(result) {
	if (!this.wasTriggeredBySearchBtn) {
		return;
	}
	var array = result.getResponse().getResults(ZmId.ITEM_MSG).getVector().getArray();
	this._totalMsgArray = this._totalMsgArray.concat(array);
	if (this._totalMsgArray.length == 0) {//no results
		this.hide();
		return;
	}
	//if we need to do further search with diferent offset go ahead. BUT, if #results is < total expected, start processing(ignore further processing)
	if (this._totalNumberOfSearchRequests > this._currentRequestNumber && (this._totalMsgArray.length == this._currentRequestNumber * org_zmail_searchrefiner.limit)) {
		//this._processResult(this._totalMsgArray, true);
		setTimeout(AjxCallback.simpleClosure(this.doInternalSearch, this, org_zmail_searchrefiner.limit, this._currentRequestNumber * org_zmail_searchrefiner.limit), 1000);
		return;
	}
	//done with searching, now process..
	this._currentRequestNumber = 0;//reset
	this._processResult(this._totalMsgArray, false);
	this._totalMsgArray = [];

};

org_zmail_searchrefiner.prototype._processResult = function(array, isPartialResult) {
	this.senderArry = [];
	this.propArry = [];
	this.fldrArry = [];
	var fldrTree = appCtxt.getFolderTree();
	for (var i = 0; i < array.length; i++) {
		var eml = array[i];
		this.senderArry.push(eml._addrs.FROM._array[0].address);
		this.fldrArry.push(fldrTree.getById(eml.folderId).getPath());
		this._collectAddnlProperties(eml);
	}
	this.createRefinerView();

	this.show();

	if(!isPartialResult)
		this.wasTriggeredBySearchBtn = false;
};
//------------------------------------------------------------------------------------------
//			CONTROL NARROWED-BY(PRE-REFINED) SECTION'S VIEW
//------------------------------------------------------------------------------------------
org_zmail_searchrefiner.prototype.updateNarrowedBy = function(hideIfEmpty) {
	var html = new Array();
	var i = 0;
	if (hideIfEmpty) {//when we undo all refined items..
		var zeroItms = true;
		for (var el in this._narrowedItems) {
			zeroItms = false;
			break;
		}
		if (zeroItms) {
			this._hideNarrowedBy();
			return;
		}
	}

	html[i++] = "<table width='100%'>";
	for (var el in this._narrowedItems) {
		html[i++] = "<tr id='" + el + "_narrow' class='sr_item'><td width='5%'><img   src=\"" + this.getResource("sr_closNrw.gif") + "\"  /></td><td>" + this._narrowedItems[el] + "</td></tr>";
	}
	html[i++] = "</table>";

	document.getElementById("sr_narrowByContentDiv").innerHTML = html.join("");
	this._addNarrowListeners("sr_narrowByContentDiv");
	this._showNarrowedBy();

};

org_zmail_searchrefiner.prototype._showNarrowedBy = function() {
	document.getElementById("sr_narrowedByMainDiv").style.display = "block";
};

org_zmail_searchrefiner.prototype._hideNarrowedBy = function() {
	document.getElementById("sr_narrowByContentDiv").innerHTML = "";
	document.getElementById("sr_narrowedByMainDiv").style.display = "none";
	this._narrowedItems = [];
};


//------------------------------------------------------------------------------------------
//			HANDLE CLICKING ON ALREADY-REFINED-LINKS
//------------------------------------------------------------------------------------------
org_zmail_searchrefiner.prototype._addNarrowListeners =
function(id) {
	var rows = document.getElementById(id).getElementsByTagName("tr");
	for (var i = 0; i < rows.length; i++) {
		var itm = rows[i];
		var id = (itm.id).replace("sr_tr", "sr_td");
		itm.onclick = AjxCallback.simpleClosure(this._onNarrowedByclick, this, id);
	}
};

org_zmail_searchrefiner.prototype._onNarrowedByclick =
function(id) {
	this._removeNarrowArryEl(id);
	this.updateNarrowedBy(true);
	id = id.replace("_narrow", "");
	this._searchField.value = this._searchField.value.replace(this._narrowedSearch[id], "");
	this.searchAgain();
	this._hideOrShowSections();
};

org_zmail_searchrefiner.prototype._removeNarrowArryEl =
function(id) {
	id = id.replace("_narrow", "");
	var tmp = new Array();
	for (var el in this._narrowedItems) {
		if (el != id) {
			tmp[el] = this._narrowedItems[el];
		}
	}
	this._narrowedItems = tmp;
};

//------------------------------------------------------------------------------------------
//			HANDLE CLICKING ON REFINEMENT-LINKS
//------------------------------------------------------------------------------------------

org_zmail_searchrefiner.prototype._addListeners =
function(id) {
	var rows = document.getElementById(id).getElementsByTagName("tr");
	for (var i = 0; i < rows.length; i++) {
		var itm = rows[i];
		var id = (itm.id).replace("sr_tr", "sr_td");
		itm.onclick = AjxCallback.simpleClosure(this._onclick, this, id);
	}
};

org_zmail_searchrefiner.prototype._onclick =
function(id) {
	var value = document.getElementById(id).innerHTML;
	this._narrowedItems[id] = value;
	this.updateNarrowedBy(false);
	var searchVal = "";
	if (id.indexOf("sender") >= 0) {
		searchVal = " from:\"" + value + "\"";
	} else if (id.indexOf("fldr") >= 0) {
		searchVal = " in:\"" + value + "\"";
	} else if (id.indexOf("prop") >= 0) {
		var tmp = value.split(" ");
		searchVal = " " + tmp[0] + ":" + tmp[1];
	}
	this._searchField.value = this._searchField.value + searchVal;
	this._narrowedSearch[id] = searchVal;
	this._hideOrShowSections();
	this.searchAgain();
};

org_zmail_searchrefiner.prototype.searchAgain =
function() {
	this._refineMore = true;
	//redo the search with new query
	var getHtml = appCtxt.get(ZmSetting.VIEW_AS_HTML);
	appCtxt.getSearchController().search({query: this._searchField.value, userText: true, getHtml: getHtml});

	//update search-refiner
	this.wasTriggeredBySearchBtn = true;//to emulate new-search
	setTimeout(AjxCallback.simpleClosure(this.doInternalSearch, this, org_zmail_searchrefiner.limit, 0), 1000);
};

org_zmail_searchrefiner.prototype._hideOrShowSections =
function() {
	for (var id in this._narrowedItems) {
		if (id.indexOf("sender") >= 0) {
			document.getElementById("sr_senderSectionId").parentNode.style.display = "none";
		} else if (id.indexOf("fldr") >= 0) {
			document.getElementById("sr_fldrSectionId").parentNode.style.display = "none";
		}
	}
};

//------------------------------------------------------------------------------------------
//			CREATE SEARCH-REFINER VIEW
//------------------------------------------------------------------------------------------

org_zmail_searchrefiner.prototype.createRefinerView =
function() {
	if (!this._refineMore) {
		this._hideNarrowedBy();
		this._narrowedItems = new Array();
		this._narrowedSearch = new Array();
	}
	document.getElementById("sr_senderSectionId").innerHTML = this.getSenderSection();
	document.getElementById("sr_fldrSectionId").innerHTML = this.getFldrSection();
	document.getElementById("sr_propSectionId").innerHTML = this.getPropSection();
	document.getElementById("sr_discSectionId").innerHTML = this.getDisclaimer();
	this._addListeners("sr_senderSectionId");
	this._addListeners("sr_fldrSectionId");
	this._addListeners("sr_propSectionId");
	this._hideOrShowSections();
};

org_zmail_searchrefiner.prototype.initBaseView =
function() {
	if (this.isZmletUIInitialized)
		return;

	this.overviewTreeHtml = appCtxt.getAppViewMgr().getCurrentViewComponent(ZmAppViewMgr.C_TREE).getHtmlElement();
	var treeSkinDiv = document.getElementById("skin_container_tree");
	this._refineMainDiv = document.createElement('div');
	treeSkinDiv.appendChild(this._refineMainDiv);
	this.wasTriggeredBySearchBtn = false;
	this._refineMainDiv.id = "sr_mainDivId";
	this._refineMainDiv.style.position = "absolute";
	this._refineMainDiv.style.overflow = "auto";
	this._refineMainDiv.style.backgroundColor = "white";
	this._refineMainDiv.style.width = "100%";
	this._refineMainDiv.style.height =  "100%";
	this._refineMainDiv.style.display = "none";

	this._refineMainDiv.innerHTML = this._headerHTML() + this._narrowedByHTML() + this._getAllSectionsHTML();
	document.getElementById("sr_closeButtonDiv").onclick = AjxCallback.simpleClosure(this.hide, this);
	this.isZmletUIInitialized = true;//set this to true
};

org_zmail_searchrefiner.prototype._headerHTML = function() {
	return "<div class='overviewHeader'><TABLE><TD width=90%><B>Narrow Results<\B></TD><TD  align=\"right\"><DIV  id='sr_closeButtonDiv' class='ImgClose'></DIV></TD></TABLE></div>";
};

org_zmail_searchrefiner.prototype._narrowedByHTML = function() {
	return "<DIV id='sr_narrowedByMainDiv' ><DIV class='sr_header'>Narrowed By:</DIV><DIV id='sr_narrowByContentDiv'></DIV></DIV>";
};

org_zmail_searchrefiner.prototype._collectAddnlProperties =
function(eml) {
	if (eml.hasAttach) {
		this.propArry.push("has Attachment");
	}

	if (eml.isUnread) {
		this.propArry.push("is Unread");
	} else {
		this.propArry.push("is Read");
	}
	if (eml.isFlagged) {
		this.propArry.push("is Flagged");
	}
	if (eml.isHighPriority) {
		this.propArry.push("is HighPriority");
	}
	if (eml.isLowPriority) {
		this.propArry.push("is LowPriority");
	}

};

org_zmail_searchrefiner.prototype._getAllSectionsHTML =
function() {
	var html = new Array();
	var i = 0;
	html[i++] = this.getSection("By Sender:", "sr_senderSectionId");
	html[i++] = this.getSection("By Folder:", "sr_fldrSectionId");
	html[i++] = this.getSection("By Status:", "sr_propSectionId");
	html[i++] = this.getSection("Note:", "sr_discSectionId");
	return html.join("");
};

org_zmail_searchrefiner.prototype.getSenderSection =
function() {
	var html = new Array();
	var i = 0;
	document.getElementById("sr_senderSectionId").parentNode.style.display = "none";
	var uniqueArry = org_zmail_searchrefiner.unique(this.senderArry);
	if (uniqueArry.length == 0)
		return "";
	var arr = this.getitemsWithCount(uniqueArry, this.senderArry);
	html[i++] = "<table width='100%'>";
	for (var itm in arr) {
		html[i++] = "<tr id='sr_tr_sender" + i + "'><td class='sr_num'>" + arr[itm] + "</td><td id='sr_td_sender" + i + "' class='sr_item'>" + itm + "</td></tr>";
	}
	html[i++] = "</table>";
	document.getElementById("sr_senderSectionId").parentNode.style.display = "block";
	return html.join("");
};

org_zmail_searchrefiner.prototype.getFldrSection =
function() {
	var html = new Array();
	var i = 0;
	document.getElementById("sr_fldrSectionId").parentNode.style.display = "none";
	var uniqueArry = org_zmail_searchrefiner.unique(this.fldrArry);
	if (uniqueArry.length == 0)
		return "";
	var arr = this.getitemsWithCount(uniqueArry, this.fldrArry);
	html[i++] = "<table width='100%'>";
	for (var itm in arr) {
		html[i++] = "<tr id='sr_tr_fldr" + i + "'><td class='sr_num'>" + arr[itm] + "</td><td id='sr_td_fldr" + i + "' class='sr_item'>" + itm + "</td></tr>";
	}
	html[i++] = "</table>";
	document.getElementById("sr_fldrSectionId").parentNode.style.display = "block";

	return html.join("");
};

org_zmail_searchrefiner.prototype.getPropSection =
function() {
	var html = new Array();
	var i = 0;
	document.getElementById("sr_propSectionId").parentNode.style.display = "none";
	var uniqueArry = org_zmail_searchrefiner.unique(this.propArry);
	if (uniqueArry.length == 0)
		return "";
	var arr = this.getitemsWithCount(uniqueArry, this.propArry);
	html[i++] = "<table width='100%'>";
	for (var itm in arr) {
		html[i++] = "<tr id='sr_tr_prop" + i + "'><td class='sr_num'>" + arr[itm] + "</td><td id='sr_td_prop" + i + "' class='sr_item'>" + itm + "</td></tr>";
	}
	html[i++] = "</table>";
	document.getElementById("sr_propSectionId").parentNode.style.display = "block";
	return html.join("");
};

org_zmail_searchrefiner.prototype.getSection =
function(sectionName, sectionId) {
	var html = new Array();
	var i = 0;
	html[i++] = "<DIV>";
	html[i++] = "<DIV class='sr_header'>" + sectionName + "</DIV>";
	html[i++] = "<DIV id='" + sectionId + "'>";
	html[i++] = "</DIV>";
	html[i++] = "</DIV>";
	return html.join("");
};

org_zmail_searchrefiner.prototype.getDisclaimer =
function() {
	document.getElementById("sr_discSectionId").parentNode.style.display = "block";
	return  "<span class='sr_disclaimer'>* Valid for first " + this.refinerSearchSize + " matches due to performance reasons</span>";
};

org_zmail_searchrefiner.prototype.getitemsWithCount =
function(uniqueArry, origArry) {
	var arry = [];
	for (var i = 0; i < uniqueArry.length; i++) {
		var item = uniqueArry[i];
		var count = 0;
		for (var j = 0; j < origArry.length; j++) {
			if (item == origArry[j]) {
				count++;
			}
		}
		arry[item] = count;
	}
	return arry;
};

//------------------------------------------------------------------------------------------
//			SHOW/HIDE FOLDER,TAG ETC FOLDER-HEADERS
//------------------------------------------------------------------------------------------
org_zmail_searchrefiner.prototype.onShowView =
function(viewId, isNewView) {
	var viewType = appCtxt.getViewTypeFromId(viewId);
	if (viewType != ZmId.VIEW_COMPOSE) {
		this.hide();
	}
};

org_zmail_searchrefiner.prototype.show =
function(ev) {	
	var sr = document.getElementById("sr_mainDivId");
	sr.style.display = "block";
	sr.style.zIndex = 500;
	this.overviewTreeHtml.style.display = "none";

};

org_zmail_searchrefiner.prototype.hide =
function(ev) {
	if (!this.isZmletUIInitialized)
		return;

	var sr = document.getElementById("sr_mainDivId");
	sr.style.display = "none";
	sr.style.zIndex = 100;
	this.overviewTreeHtml.style.display = "block";

};

//------------------------------------------------------------------------------------------
//			SHOW PREFERENCES DIALOG
//------------------------------------------------------------------------------------------

org_zmail_searchrefiner.prototype.doubleClicked = function() {
	this.singleClicked();
};

org_zmail_searchrefiner.prototype.singleClicked = function() {
	this.showPrefDialog();
};

org_zmail_searchrefiner.prototype.showPrefDialog =
function() {
	//if zimlet dialog already exists...
	if (this.pbDialog) {
		this.pbDialog.popup();
		return;
	}
	this.pView = new DwtComposite(this.getShell());
	this.pView.getHtmlElement().innerHTML = this.createPrefView();

	if (this.getUserProperty("turnSearchRefinerON") == "true") {
		document.getElementById("turnSearchRefinerON_chkbx").checked = true;
	}
	this._updateSearchSize();


	this.pbDialog = this._createDialog({title:"Search Refiner Zimlet Preferences", view:this.pView, standardButtons:[DwtDialog.OK_BUTTON]});
	this.pbDialog.setButtonListener(DwtDialog.OK_BUTTON, new AjxListener(this, this._okBtnListner));
	this.pbDialog.popup();

};

org_zmail_searchrefiner.prototype._updateSearchSize =
function() {
	var optn = document.getElementById("sr_refinerSearchSize_menu").options;
	for (var i = 0; i < optn.length; i++) {
		if (optn[i].value == this.refinerSearchSize) {
			optn[i].selected = true;
			break;
		}
	}
};


org_zmail_searchrefiner.prototype.createPrefView =
function() {
	var html = new Array();
	var i = 0;
	html[i++] = "<DIV>";
	html[i++] = "<input id='turnSearchRefinerON_chkbx'  type='checkbox'/>Enable Search Refiner Zimlet (Changing this would refresh browser)";
	html[i++] = "</DIV>";
	html[i++] = "<BR>";
	html[i++] = "<DIV>";
	html[i++] = "<span>Collect search details for first </span>";
	html[i++] = this._getSearchCntMenu("sr_refinerSearchSize_menu");
	html[i++] = "<span> results</span>";
	html[i++] = "</DIV>";
	return html.join("");

};

org_zmail_searchrefiner.prototype._getSearchCntMenu =
function(id) {
	var rs = this.getUserProperty("refinerSearchSize");
	var html = new Array();
	var i = 0;
	html[i++] = "<select id=\"" + id + "\">";
	for (var j = 1; j <= 5; j++) {
		var val = j * 100;
		if (rs == val)
			html[i++] = "<option value=\"" + val + "\" selected>" + val + "</option>";
		else
			html[i++] = "<option value=\"" + val + "\">" + val + "</option>";
	}
	html[i++] = "</select>";
	return html.join("");
};

org_zmail_searchrefiner.prototype._okBtnListner =
function() {
	this._reloadRequired = false;
	if (document.getElementById("turnSearchRefinerON_chkbx").checked) {
		if (!this.turnSearchRefinerON) {
			this._reloadRequired = true;
		}
		this.setUserProperty("turnSearchRefinerON", "true", true);
	} else {
		this.setUserProperty("turnSearchRefinerON", "false", true);
		if (this.turnSearchRefinerON)
			this._reloadRequired = true;
	}

	this.setUserProperty("refinerSearchSize", document.getElementById("sr_refinerSearchSize_menu").options[document.getElementById("sr_refinerSearchSize_menu").selectedIndex].value, true);
	this.pbDialog.popdown();
	if (this._reloadRequired) {
		window.onbeforeunload = null;
		var url = AjxUtil.formatUrl({});
		ZmZmailMail.sendRedirect(url);
	}

};

//------------------------------------------------------------------------------------------
//			HELPERS...
//------------------------------------------------------------------------------------------
org_zmail_searchrefiner.arrayContainsElement =
function(array, val) {
	for (var i = 0; i < array.length; i++) {
		if (array[i] == val) {
			return true;
		}
	}
	return false;
};

org_zmail_searchrefiner.unique =
function(b) {
	var a = [], i, l = b.length;
	for (i = 0; i < l; i++) {
		if (!org_zmail_searchrefiner.arrayContainsElement(a, b[i])) {
			a.push(b[i]);
		}
	}
	return a;
};
