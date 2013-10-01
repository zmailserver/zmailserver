/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Creates an empty list of mail items.
 * @constructor
 * @class
 * This class represents a list of mail items (conversations, messages, or
 * attachments). We retain a handle to the search that generated the list for
 * two reasons: so that we can redo the search if necessary, and so that we
 * can get the folder ID if this list represents folder contents.
 *
 * @author Conrad Damon
 * 
 * @param type		type of mail item (see ZmItem for constants)
 * @param search	the search that generated this list
 */
ZmMailList = function(type, search) {

	ZmList.call(this, type, search);

	this.convId = null; // for msg list within a conv

	// mail list can be changed via folder or tag action (eg "Mark All Read")
	var folderTree = appCtxt.getFolderTree();
	if (folderTree) {
		this._folderChangeListener = new AjxListener(this, this._folderTreeChangeListener);
		folderTree.addChangeListener(this._folderChangeListener);
	}
};

ZmMailList.prototype = new ZmList;
ZmMailList.prototype.constructor = ZmMailList;

ZmMailList.prototype.isZmMailList = true;
ZmMailList.prototype.toString = function() { return "ZmMailList"; };

/**
 * Override so that we can specify "tcon" attribute for conv move - we don't want
 * to move messages in certain system folders as a side effect. Also, we need to
 * update the UI based on the response if we're moving convs, since the 
 * notifications only tell us about moved messages. This method should be called
 * only in response to explicit action by the user, in which case we want to
 * remove the conv row(s) from the list view (even if the conv still matches the
 * search).
 *
 * @param {Hash}	params		a hash of parameters
 *        items			[array]			a list of items to move
 *        folder		[ZmFolder]		destination folder
 *        attrs			[hash]			additional attrs for SOAP command
 *        callback		[AjxCallback]*	callback to run after each sub-request
 *        finalCallback	[closure]*		callback to run after all items have been processed
 *        count			[int]*			starting count for number of items processed
 *        fromFolderId  [String]*       optional folder to represent when calculating tcon. If unspecified, use current search folder nId
 *        
 * @private
 */
ZmMailList.prototype.moveItems =
function(params) {

	if (this.type != ZmItem.CONV) {
		return ZmList.prototype.moveItems.apply(this, arguments);
	}

	params = Dwt.getParams(arguments, ["items", "folder", "attrs", "callback", "finalCallback", "noUndo", "actionText", "fromFolderId"]);
	params.items = AjxUtil.toArray(params.items);

	var params1 = AjxUtil.hashCopy(params);
	delete params1.fromFolderId;

	params1.attrs = {};
	var tcon = this._getTcon(params.items, params.fromFolderId);
	if (tcon) {
		params1.attrs.tcon = tcon;
	}
	params1.attrs.l = params.folder.id;
	params1.action = (params.folder.id == ZmFolder.ID_TRASH) ? "trash" : "move";
    if (params1.folder.id == ZmFolder.ID_TRASH) {
        params1.actionText = params.actionText || ZmMsg.actionTrash;
    } else {
        params1.actionText = params.actionText || ZmMsg.actionMove;
        params1.actionArg = params1.folder.getName(false, false, true);
    }
	params1.callback = new AjxCallback(this, this._handleResponseMoveItems, [params]);

	if (appCtxt.multiAccounts) {
		// Reset accountName for multi-account to be the respective account if we're
		// moving a draft out of Trash.
		// OR,
		// check if we're moving to or from a shared folder, in which case, always send
		// request on-behalf-of the account the item originally belongs to.
        var folderId = params.items[0].getFolderId && params.items[0].getFolderId();

        // on bulk delete, when the second chunk loads try to get folderId from the item id.
        if (!folderId) {
            var itemId = params.items[0] && params.items[0].id;
            folderId = itemId && appCtxt.getById(itemId) && appCtxt.getById(itemId).folderId;
        }
        var fromFolder = folderId && appCtxt.getById(folderId);
		if ((params.items[0].isDraft && params.folder.id == ZmFolder.ID_DRAFTS) ||
			(params.folder.isRemote()) || (fromFolder && fromFolder.isRemote()))
		{
			params1.accountName = params.items[0].getAccount().name;
		}
	}

	if (this._handleDeleteFromSharedFolder(params, params1)) {
		return;
	}
	
	this._itemAction(params1);
};

/**
 * Marks items as "spam" or "not spam". If they're marked as "not spam", a target folder
 * may be provided.
 * @param {Hash}	params		a hash of parameters
 *        items			[array]			a list of items
 *        markAsSpam	[boolean]		if true, mark as "spam"
 *        folder		[ZmFolder]		destination folder
 *        childWin		[window]*		the child window this action is happening in
 *        closeChildWin	[boolean]*		is the child window closed at the end of the action?
 *        callback		[AjxCallback]*	callback to run after each sub-request
 *        finalCallback	[closure]*		callback to run after all items have been processed
 *        count			[int]*			starting count for number of items processed
 * @private
 */
ZmMailList.prototype.spamItems = 
function(params) {

	var items = params.items = AjxUtil.toArray(params.items);

	if (appCtxt.multiAccounts) {
		var accounts = this._filterItemsByAccount(items);
		this._spamAccountItems(accounts, params);
	} else {
		this._spamItems(params);
	}
};

ZmMailList.prototype._spamAccountItems =
function(accounts, params) {
	var items;
	for (var i in accounts) {
		items = accounts[i];
		break;
	}

	if (items) {
		delete accounts[i];

		params.accountName = appCtxt.accountList.getAccount(i).name;
		params.items = items;
		params.callback = new AjxCallback(this, this._spamAccountItems, [accounts, params]);

		this._spamItems(params);
	}
};

ZmMailList.prototype._spamItems =
function(params) {
	params = Dwt.getParams(arguments, ["items", "markAsSpam", "folder", "childWin"]);

	var params1 = AjxUtil.hashCopy(params);

	params1.action = params.markAsSpam ? "spam" : "!spam";
	params1.attrs = {};
	var tcon = this._getTcon(params.items);
	if (tcon) {
		params1.attrs.tcon = tcon;
	}
	if (params.folder) {
		params1.attrs.l = params.folder.id;
	}
	params1.actionText = params.markAsSpam ? ZmMsg.actionMarkAsJunk : ZmMsg.actionMarkAsNotJunk;

	params1.callback = new AjxCallback(this, this._handleResponseSpamItems, params);
	this._itemAction(params1);
};

ZmMailList.prototype._handleResponseSpamItems =
function(params, result) {

	var movedItems = result.getResponse();
	var summary;
	if (movedItems && movedItems.length) {
		var folderId = params.markAsSpam ? ZmFolder.ID_SPAM : (params.folder ? params.folder.id : ZmFolder.ID_INBOX);
		this.moveLocal(movedItems, folderId);
		var convs = {};
		for (var i = 0; i < movedItems.length; i++) {
			var item = movedItems[i];
			if (item.cid) {
				var conv = appCtxt.getById(item.cid);
				if (conv) {
					if (!convs[conv.id])
						convs[conv.id] = {conv:conv,msgs:[]};
					convs[conv.id].msgs.push(item);
				}
			}
			var details = {oldFolderId:item.folderId, fields:{}};
			details.fields[ZmItem.F_FRAGMENT] = true;
			item.moveLocal(folderId);
		}

		for (var id in convs) {
			if (convs.hasOwnProperty(id)) {
				var conv = convs[id].conv;
				var msgs = convs[id].msgs;
				conv.updateFragment(msgs);
			}
		}
		//ZmModel.notifyEach(movedItems, ZmEvent.E_MOVE);
		
		var item = movedItems[0];
		var list = item.list;
		if (list) {
			list._evt.batchMode = true;
			list._evt.item = item;	// placeholder
			list._evt.items = movedItems;
			list._notify(ZmEvent.E_MOVE, details);
		}
		if (params.actionText) {
			summary = ZmList.getActionSummary(params.actionText, params.numItems, params.type, params.actionArg);
		}

		if (params.childWin) {
			params.childWin.close();
		}
	}
	params.actionSummary = summary;
	if (params.callback) {
		params.callback.run(result);
	}
};

/**
 * Override so that delete of a conv in Trash doesn't hard-delete its msgs in
 * other folders. If we're in conv mode in Trash, we add a constraint of "t",
 * meaning that the action is only applied to items (msgs) in the Trash.
 *
 * @param {Hash}		params		a hash of parameters
 * @param  {Array}     params.items			list of items to delete
 * @param {Boolean}      params.hardDelete	whether to force physical removal of items
 * @param {Object}      params.attrs			additional attrs for SOAP command
 * @param {window}       params.childWin		the child window this action is happening in
 * @param	{Boolean}	params.confirmDelete		the user confirmed hard delete
 *
 * @private
 */
ZmMailList.prototype.deleteItems =
function(params) {

	params = Dwt.getParams(arguments, ["items", "hardDelete", "attrs", "childWin"]);

	if (this.type == ZmItem.CONV) {
		var searchFolder = this.search ? appCtxt.getById(this.search.folderId) : null;
		if (searchFolder && searchFolder.isHardDelete()) {

			if (!params.confirmDelete) {
				params.confirmDelete = true;
				var callback = ZmMailList.prototype.deleteItems.bind(this, params);
				this._popupDeleteWarningDialog(callback, false, params.items.length);
				return;
			}

			var instantOn = appCtxt.getAppController().getInstantNotify();
			if (instantOn) {
				// bug fix #32005 - disable instant notify for ops that might take awhile
				appCtxt.getAppController().setInstantNotify(false);
				params.errorCallback = new AjxCallback(this, this._handleErrorDeleteItems);
			}

			params.attrs = params.attrs || {};
			params.attrs.tcon = ZmFolder.TCON_CODE[searchFolder.nId];
			params.action = "delete";
            params.actionText = ZmMsg.actionDelete;
			params.callback = new AjxCallback(this, this._handleResponseDeleteItems, instantOn);
			return this._itemAction(params);
		}
	}
	ZmList.prototype.deleteItems.call(this, params);
};

ZmMailList.prototype._handleResponseDeleteItems =
function(instantOn, result) {
	var deletedItems = result.getResponse();
	if (deletedItems && deletedItems.length) {
		this.deleteLocal(deletedItems);
		for (var i = 0; i < deletedItems.length; i++) {
			deletedItems[i].deleteLocal();
		}
		// note: this happens before we process real notifications
		ZmModel.notifyEach(deletedItems, ZmEvent.E_DELETE);
	}

	if (instantOn) {
		appCtxt.getAppController().setInstantNotify(true);
	}
};

ZmMailList.prototype._handleErrorDeleteItems =
function() {
	appCtxt.getAppController().setInstantNotify(true);
};

/**
 * Only make the request for items whose state will be changed.
 *
 * @param {Hash}		params		a hash of parameters
 *
 *        items			[array]				a list of items to mark read/unread
 *        value			[boolean]			if true, mark items read
 *        callback		[AjxCallback]*		callback to run after each sub-request
 *        finalCallback	[closure]*			callback to run after all items have been processed
 *        count			[int]*				starting count for number of items processed
 *        
 * @private
 */
ZmMailList.prototype.markRead =
function(params) {

	var items = AjxUtil.toArray(params.items);

	var items1;
	if (items[0] && items[0] instanceof ZmItem) {
		items1 = [];
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			if ((item.type == ZmItem.CONV && item.hasFlag(ZmItem.FLAG_UNREAD, params.value)) || (item.isUnread == params.value)) {
				items1.push(item);
			}
		}
	} else {
		items1 = items;
	}

	if (items1.length) {
		params.items = items1;
		params.op = "read";
		if (items1.length > 1) {
        	params.actionText = params.value ? ZmMsg.actionMarkRead : ZmMsg.actionMarkUnread;
		}
		this.flagItems(params);
	}
    else if(params.forceCallback) {
        if (params.callback) {
			params.callback.run(new ZmCsfeResult([]));
		}
		if (params.finalCallback) {
			params.finalCallback(params);
		}
		return;
    }
};

/**
 * Only make the request for items whose state will be changed.
 *
 * @param {Hash}		params		a hash of parameters
 *
 *        items			[array]				a list of items to mark read/unread
 *        value			[boolean]			if true, mark items read
 *        callback		[AjxCallback]*		callback to run after each sub-request
 *        finalCallback	[closure]*			callback to run after all items have been processed
 *        count			[int]*				starting count for number of items processed
 *
 * @private
 */
ZmMailList.prototype.markMute =
function(params) {

	var items = AjxUtil.toArray(params.items);

	var items1;
	if (items[0] && items[0] instanceof ZmItem) {
		items1 = [];
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			if (params.value != item.isMute) {
				items1.push(item);
			}
		}
	} else {
		items1 = items;
	}

	if (items1.length) {
		params.items = items1;
		params.op = "mute";
        params.actionText = params.value ? ZmMsg.actionMarkMute : ZmMsg.actionMarkUnmute;
		this.flagItems(params);
	}
    else if(params.forceCallback) {
        if (params.callback) {
			params.callback.run(new ZmCsfeResult([]));
		}
		if (params.finalCallback) {
			params.finalCallback(params);
		}
		return;
    }
};

// set "force" flag to true on actual hard deletes, so that msgs
// in a conv list are removed
ZmMailList.prototype.deleteLocal =
function(items) {
	for (var i = 0; i < items.length; i++) {
		this.remove(items[i], true);
	}
};

// When a conv or msg is moved to Trash, it is marked read by the server.
ZmMailList.prototype.moveLocal =
function(items, folderId) {
	ZmList.prototype.moveLocal.call(this, items, folderId);
	if (folderId != ZmFolder.ID_TRASH) { return; }

	var flaggedItems = [];
	for (var i = 0; i < items.length; i++) {
		if (items[i].isUnread) {
			items[i].flagLocal(ZmItem.FLAG_UNREAD, false);
			flaggedItems.push(items[i]);
		}
	}
	ZmModel.notifyEach(flaggedItems, ZmEvent.E_FLAGS, {flags:[ZmItem.FLAG_UNREAD]});
};

ZmMailList.prototype.notifyCreate = 
function(convs, msgs) {

	var createdItems = [];
	var newConvs = [];
	var newMsgs = [];
	var flaggedItems = [];
	var modifiedItems = [];
	var newConvId = {};
	var fields = {};
	var sortBy = this.search ? this.search.sortBy : null;
	var sortIndex = {};
	if (this.type == ZmItem.CONV) {
		// handle new convs first so we can set their fragments later from new msgs
		for (var id in convs) {
			AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: handling conv create " + id);
			if (this.getById(id)) {
				AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: conv already exists " + id);
				continue;
			}
			newConvId[id] = convs[id];
			var conv = convs[id];
			var convMatches =  this.search && this.search.matches(conv) && !conv.ignoreJunkTrash();
			if (convMatches) {
				if (!appCtxt.multiAccounts ||
					(appCtxt.multiAccounts && (this.search.isMultiAccount() || conv.getAccount() == appCtxt.getActiveAccount()))) 
				{
					// a new msg for this conv matches current search
					conv.list = this;
					newConvs.push(conv);
					AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: conv added " + id);
				}
				else {
					AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: conv failed account checks " + id);
				}
			}
			else {
				// debug info for bug 47589
				var query = this.search ? this.search.query : "";
				var ignore = conv.ignoreJunkTrash();
				AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: conv does not match search '" + query + "' or was ignored (" + ignore + "); match function:");
				if (!conv) {
					AjxDebug.println(AjxDebug.NOTIFY, "conv is null!");
				}
				else {
					var folders = AjxUtil.keys(conv.folders) || "";
					AjxDebug.println(AjxDebug.NOTIFY, "conv folders: " + folders.join(" "));
				}
			}
		}

		// a new msg can hand us a new conv, and update a conv's info
		for (var id in msgs) {
			var msg = msgs[id];
			AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: CLV handling msg create " + id);
			var cid = msg.cid;
			var msgMatches =  this.search && this.search.matches(msg) && !msg.ignoreJunkTrash();
			AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: CLV msg matches: " + msgMatches);
			var isActiveAccount = (!appCtxt.multiAccounts || (appCtxt.multiAccounts && msg.getAccount() == appCtxt.getActiveAccount()));
			var conv = newConvId[cid] || this.getById(cid);
			if (msgMatches && isActiveAccount) {
				if (!conv) {
					// msg will have _convCreateNode if it is 2nd msg and caused promotion of virtual conv;
					// the conv node will have proper count and subject
					var args = {list:this};
					if (msg._convCreateNode) {
						if (msg._convCreateNode._newId) {
							msg._convCreateNode.id = msg._convCreateNode._newId;
						}
						conv = ZmConv.createFromDom(msg._convCreateNode, args);
					} else {
						conv = appCtxt.getById(cid) || ZmConv.createFromMsg(msg, args);
					}
					newConvId[cid] = conv;
					conv.folders[msg.folderId] = true;
					newConvs.push(conv);
				}
				conv.list = this;
			}
			// make sure conv's msg list is up to date
			if (conv && !(conv.msgs && conv.msgs.getById(id))) {
				if (!conv.msgs) {
					conv.msgs = new ZmMailList(ZmItem.MSG);
					conv.msgs.addChangeListener(conv._listChangeListener);
				}
				msg.list = conv.msgs;
				if (!msg.isSent && msg.isUnread) {
					conv.isUnread = true;
					flaggedItems.push(conv);
				}
				// if the new msg matches current search, update conv date, fragment, and sort order
				if (msgMatches) {
					msg.inHitList = true;
				}
				if (msgMatches || ((msgMatches === null) && !msg.isSent)) {
					if (conv.fragment != msg.fragment) {
						conv.fragment = msg.fragment;
						fields[ZmItem.F_FRAGMENT] = true;
					}
					if (conv.date != msg.date) {
						conv.date = msg.date;
						// recalculate conv's sort position since we changed its date
						fields[ZmItem.F_DATE] = true;
					}
					// conv gained a msg, may need to be moved to top/bottom
					if (!newConvId[conv.id] && this._vector.contains(conv)) {
						fields[ZmItem.F_INDEX] = true;
					}
					modifiedItems.push(conv);
				}
				AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: conv list accepted msg " + id);
				newMsgs.push(msg);
			}
		}
	} else if (this.type == ZmItem.MSG) {
		// add new msg to list
		for (var id in msgs) {
			var msg = msgs[id];
			var msgMatches =  this.search && this.search.matches(msg) && !msg.ignoreJunkTrash();
			AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: handling msg create " + id);
			if (this.getById(id)) {
				if (msgMatches) {
					var query = this.search ? this.search.query : "";
					var ignore = msg.ignoreJunkTrash();
					AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: msg does not match search '" + query + "' or was ignored (" + ignore + ")");
					msg.list = this; // Even though we have the msg in the list, it sometimes has its list wrong.
				}
				continue;
			}
			if (this.convId) { // MLV within CV
				if (msg.cid == this.convId && !this.getById(msg.id)) {
					msg.list = this;
					AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: msg list (CV) accepted msg " + id);
					newMsgs.push(msg);
				}
			} else { // MLV (traditional)
				if (msgMatches) {
					msg.list = this;
					AjxDebug.println(AjxDebug.NOTIFY, "ZmMailList: msg list (TV) accepted msg " + id);
					newMsgs.push(msg);
				}
			}
		}
	}

	// sort item list in reverse so they show up in correct order when processed (oldest appears first)
	if (newConvs.length > 1) {
		ZmMailItem.sortBy = sortBy;
		newConvs.sort(ZmMailItem.sortCompare);
		newConvs.reverse();
	}

	this._sortAndNotify(newConvs, sortBy, ZmEvent.E_CREATE);
	this._sortAndNotify(newMsgs, sortBy, ZmEvent.E_CREATE);
	ZmModel.notifyEach(flaggedItems, ZmEvent.E_FLAGS, {flags:[ZmItem.FLAG_UNREAD]});
	this._sortAndNotify(modifiedItems, sortBy, ZmEvent.E_MODIFY, {fields:fields});
	this._sortAndNotify(newMsgs, sortBy, ZmEvent.E_MODIFY, {fields:fields});
};

/**
* Convenience method for adding messages to a conv on the fly. The specific use case for
* this is when a virtual conv becomes real. We basically add the new message(s) to the
* old (virtual) conv's message list.
*
* @param msgs		hash of messages to add
*/
ZmMailList.prototype.addMsgs =
function(msgs) {
	var addedMsgs = [];
	for (var id in msgs) {
		var msg = msgs[id];
		if (msg.cid == this.convId) {
			this.add(msg, 0);
			msg.list = this;
			addedMsgs.push(msg);
		}
	}
	ZmModel.notifyEach(addedMsgs, ZmEvent.E_CREATE);
};


ZmMailList.prototype.removeAllItems = 
function() {
	this._vector = new AjxVector();
	this._idHash = {};
};


ZmMailList.prototype.remove = 
function(item, force) {
	// Don't really remove an item if this is a list of msgs of a conv b/c a
	// msg is always going to be part of a conv unless it's a hard delete!
	if (!this.convId || force) {
		ZmList.prototype.remove.call(this, item);
	}
};

ZmMailList.prototype.clear =
function() {
	// remove listeners for this list from folder tree and tag list
	if (this._folderChangeListener) {
		var folderTree = appCtxt.getFolderTree();
		if (folderTree) {
			folderTree.removeChangeListener(this._folderChangeListener);
		}
	}
	if (this._tagChangeListener) {
		var tagTree = appCtxt.getTagTree();
		if (tagTree) {
			tagTree.removeChangeListener(this._tagChangeListener);
		}
	}

	ZmList.prototype.clear.call(this);
};

/**
 * Gets the first msg in the list which was marked by the server as matching
 * the search used to create the list.
 * 
 * @param {int}	offset	the starting point within list
 * @param {int}	limit		the ending point within list
 * @return	{ZmMailMsg}		the message
 */
ZmMailList.prototype.getFirstHit =
function(offset, limit) {
	if (this.type != ZmItem.MSG) { return null; }

	var msg = null;	
	offset = offset || 0;
	limit = limit || appCtxt.get(ZmSetting.CONVERSATION_PAGE_SIZE);
	var numMsgs = this.size();

	if (numMsgs > 0 && offset >= 0 && offset < numMsgs) {
		var end = (offset + limit > numMsgs) ? numMsgs : offset + limit;
		var list = this.getArray();
		for (var i = offset; i < end; i++) {
			if (list[i].inHitList) {
				msg = list[i];
				break;
			}
		}
		if (!msg) {
			msg = list[0];	// no hot messages, use first msg
		}
	}
	
	return msg;
};

/**
 * Returns the insertion point for the given item into this list. If we're not sorting by
 * date, returns 0 (the item will be inserted at the top of the list).
 *
 * @param item		[ZmMailItem]	a mail item
 * @param sortBy	[constant]		sort order
 */
ZmMailList.prototype._getSortIndex =
function(item, sortBy) {
	if (!sortBy || (sortBy != ZmSearch.DATE_DESC && sortBy != ZmSearch.DATE_ASC)) {
		return 0;
	}
	
	var itemDate = parseInt(item.date);
	var a = this.getArray();
	// server always orders conv's msg list as DATE_DESC
	if (this.convId && sortBy == ZmSearch.DATE_ASC) {
		//create a temp array with reverse index and date
		var temp = [];
		for(var j = a.length - 1;j >=0;j--) {
			temp.push({date:a[j].date});
		}
		a = temp;
	}
	for (var i = 0; i < a.length; i++) {
		var date = parseInt(a[i].date);
		if ((sortBy == ZmSearch.DATE_DESC && (itemDate >= date)) ||
			(sortBy == ZmSearch.DATE_ASC && (itemDate <= date))) {
			return i;
		}
	}
	return i;
};

ZmMailList.prototype._sortAndNotify =
function(items, sortBy, event, details) {

	if (!(items && items.length)) { return; }

	var itemType = items[0] && items[0].type;
	if ((this.type == ZmItem.MSG) && (itemType == ZmItem.CONV)) { return; }

	details = details || {};
	var doSort = ((event == ZmEvent.E_CREATE) || (details.fields && details.fields[ZmItem.F_DATE]));
	for (var i = 0; i < items.length; i++) {
		var item = items[i];
		if (doSort) {
			var doAdd = (itemType == this.type);
			var listSortIndex = 0, viewSortIndex = 0;
			if (this.type == ZmItem.CONV && itemType == ZmItem.MSG) {
				var conv = this.getById(item.cid);
				if (conv) {
					// server always orders msgs within a conv by DATE_DESC, so maintain that
					listSortIndex = conv.msgs._getSortIndex(item, ZmSearch.DATE_DESC);
					viewSortIndex = conv.msgs._getSortIndex(item, appCtxt.get(ZmSetting.CONVERSATION_ORDER));
					if (event == ZmEvent.E_CREATE) {
						conv.addMsg(item, listSortIndex);
					}
				}
			} else {
				viewSortIndex = listSortIndex = this._getSortIndex(item, sortBy);
			}
			if (event != ZmEvent.E_CREATE) {
				// if date changed, re-insert item into correct slot
				if (listSortIndex != this.indexOf(item)) {
					this.remove(item);
				} else {
					doAdd = false;
				}
			}
			if (doAdd) {
				this.add(item, listSortIndex);
			}
			details.sortIndex = viewSortIndex;
		}
		item._notify(event, details);
	}
};

ZmMailList.prototype._getTcon =
function(items, nId) {
	var chars = [];
	var folders = [ZmFolder.ID_DRAFTS, ZmFolder.ID_TRASH, ZmFolder.ID_SPAM, ZmFolder.ID_SENT];
    var id;
    if(!nId){
        var searchFolder = this.search && appCtxt.getById(this.search.folderId);
        if(searchFolder){
            nId = searchFolder.isRemote() ? searchFolder.rid : searchFolder.nId;
            id = searchFolder.id;
        }
    }

	for (var i = 0; i < folders.length; i++) {
		var folderId = folders[i];
        var folder;
        // get folder object from qualified Ids for multi-account
        if (appCtxt.multiAccounts) {
            var acct  = items && items[0].getAccount && items[0].getAccount();
            var acctId = acct ? acct.id : appCtxt.getActiveAccount().id;
            var fId = [acctId, ":", folderId].join("");
            folder = appCtxt.getById(fId);
        } else {
            folder = appCtxt.getById(folderId);
        }
        var nFolder = (id) ? appCtxt.getById(id) : appCtxt.getById(nId);
        // if nId is undefined send the default tcon [-tjs].
		if (!nId || (nId != folderId && folder && nFolder && !nFolder.isChildOf(folder))) {
			chars.push(ZmFolder.TCON_CODE[folderId]);
		}
	}
	return (chars.length) ?  ("-" + chars.join("")) : "";
};

// If this list is the result of a search that is constrained by the read
// status, and the user has marked all read in a folder, redo the search.
ZmMailList.prototype._folderTreeChangeListener = 
function(ev) {
	if (this.size() == 0) { return; }

	var flag = ev.getDetail("flag");
	var view = appCtxt.getCurrentViewId();
	var ctlr = appCtxt.getCurrentController();

	if (ev.event == ZmEvent.E_FLAGS && (flag == ZmItem.FLAG_UNREAD)) {
		if (this.type == ZmItem.CONV) {
			if ((view == ZmId.VIEW_CONVLIST) && ctlr._currentSearch.hasUnreadTerm()) {
				this._redoSearch(ctlr);
			}
		} else if (this.type == ZmItem.MSG) {
			if (view == ZmId.VIEW_TRAD && ctlr._currentSearch.hasUnreadTerm()) {
				this._redoSearch(ctlr);
			} else {
				var on = ev.getDetail("state");
				var organizer = ev.getDetail("item");
				var flaggedItems = [];
				var list = this.getArray();
				for (var i = 0; i < list.length; i++) {
					var msg = list[i];
					if ((organizer.type == ZmOrganizer.FOLDER && msg.folderId == organizer.id) ||
						(organizer.type == ZmOrganizer.TAG && msg.hasTag(organizer.id))) {
						msg.isUnread = on;
						flaggedItems.push(msg);
					}
				}
				ZmModel.notifyEach(flaggedItems, ZmEvent.E_FLAGS, {flags:[flag]});
			}
		}
	} else {
		ZmList.prototype._folderTreeChangeListener.call(this, ev);
	}
};

ZmMailList.prototype._tagTreeChangeListener = 
function(ev) {
	if (this.size() == 0) return;

	var flag = ev.getDetail("flag");
	if (ev.event == ZmEvent.E_FLAGS && (flag == ZmItem.FLAG_UNREAD)) {
		this._folderTreeChangeListener(ev);
	} else {
		ZmList.prototype._tagTreeChangeListener.call(this, ev);
	}
};
