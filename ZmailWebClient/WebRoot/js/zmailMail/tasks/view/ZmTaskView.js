/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2010, 2011, 2012 VMware, Inc.
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
* Creates an empty task view.
* @constructor
* @class
* Simple read-only view of a task. It looks more or less like a message -
* the notes have their own area at the bottom, and everything else goes into a
* header section at the top.
*
* @author Parag Shah
*
* @param parent		[DwtComposite]	parent widget
* @param posStyle	[constant]		positioning style
* @param controller	[ZmController]	owning controller
*/
ZmTaskView = function(parent, posStyle, controller) {

	ZmCalItemView.call(this, parent, posStyle, controller);
};

ZmTaskView.prototype = new ZmCalItemView;
ZmTaskView.prototype.constructor = ZmTaskView;
ZmTaskView.prototype.isZmTaskView = true;

// Public methods

ZmTaskView.prototype.toString =
function() {
	return "ZmTaskView";
};

ZmTaskView.prototype.getTitle =
function() {
	return [ZmMsg.zimbraTitle, this._calItem.getName()].join(": ");
};

ZmTaskView.prototype.close =
function() {
	this._controller._app.popView();
};

ZmTaskView.prototype.setSelectionHdrCbox = function(check) {};

ZmTaskView.prototype._getSubs =
function(calItem) {
	var subject = calItem.getName();
	var location = calItem.location;
	var isException = calItem._orig ? calItem._orig.isException : calItem.isException;
	var startDate = calItem.startDate ? AjxDateFormat.getDateInstance().format(calItem.startDate) : null;
	var dueDate = calItem.endDate ? AjxDateFormat.getDateInstance().format(calItem.endDate) : null;
	var priority = calItem.priority ? ZmCalItem.getLabelForPriority(calItem.priority) : null;
	var status = calItem.status ? ZmCalItem.getLabelForStatus(calItem.status) : null;
	var pComplete = calItem.pComplete;
	var recurStr = calItem.isRecurring() ? calItem.getRecurBlurb() : null;
	var attachStr = ZmCalItemView._getAttachString(calItem);
    var alarm = calItem.alarm;
    var remindDate = calItem.remindDate ? AjxDateFormat.getDateInstance().format(calItem.remindDate) : null;
    var remindTime = calItem.remindDate ? AjxDateFormat.getTimeInstance(AjxDateFormat.SHORT).format(calItem.remindDate) : "";

	if (this._objectManager) {
		this._objectManager.setHandlerAttr(ZmObjectManager.DATE,
											ZmObjectManager.ATTR_CURRENT_DATE,
											calItem.startDate);

		subject = this._objectManager.findObjects(subject, true);
		if (location) location = this._objectManager.findObjects(location, true);
		if (startDate) startDate = this._objectManager.findObjects(startDate, true);
		if (dueDate) dueDate = this._objectManager.findObjects(dueDate, true);
	}

	return {
		id: this._htmlElId,
		subject: subject,
		location: location,
		isException: isException,
		startDate: startDate,
		dueDate: dueDate,
		priority: priority,
		status: status,
		pComplete: pComplete,
		recurStr: recurStr,
		attachStr: attachStr,
        remindDate: remindDate,
        remindTime: remindTime,
        alarm: alarm,
		folder: appCtxt.getTree(ZmOrganizer.TASKS).getById(calItem.folderId),
		folderLabel: ZmMsg.folder,
        isTask:true,
        _infoBarId:this._infoBarId
	};
};

// Private / protected methods

ZmTaskView.prototype._renderCalItem =
function(calItem) {

   if(this._controller.isReadingPaneOn() && !this._newTab) {
	this._lazyCreateObjectManager();

	var subs = this._getSubs(calItem);
	var editBtnCellId = this._htmlElId + "_editBtnCell";
	this._hdrTableId = this._htmlElId + "_hdrTable";

    var el = this.getHtmlElement();
	el.innerHTML = AjxTemplate.expand("tasks.Tasks#ReadOnlyView", subs);
    this._setTags(calItem);

	// content/body
	var hasHtmlPart = (calItem.notesTopPart && calItem.notesTopPart.getContentType() == ZmMimeTable.MULTI_ALT);
	var mode = (hasHtmlPart && appCtxt.get(ZmSetting.VIEW_AS_HTML))
		? ZmMimeTable.TEXT_HTML : ZmMimeTable.TEXT_PLAIN;

	var bodyPart = calItem.getNotesPart(mode);

    if (!bodyPart && calItem.message){
        bodyPart = calItem.message.getInviteDescriptionContentValue(ZmMimeTable.TEXT_PLAIN);
    }

	if (bodyPart) {
		this._msg = this._msg || this._calItem._currentlyLoaded;
        if (mode === ZmMimeTable.TEXT_PLAIN) {
            bodyPart = AjxStringUtil.convertToHtml(bodyPart);
        }
        this._makeIframeProxy({container: el, html:bodyPart, isTextMsg:(mode == ZmMimeTable.TEXT_PLAIN)});
	}
   } else {
     ZmCalItemView.prototype._renderCalItem.call(this, calItem);
   }
   Dwt.setLoadedTime("ZmTaskItem");
   calItem.addChangeListener(this._taskChangeListener.bind(this));

};

ZmTaskView.prototype._taskChangeListener =
function(ev){
    if(ev.event == ZmEvent.E_TAGS || ev.type == ZmEvent.S_TAG) {
        this._setTags(this._calItem);
    }
};

ZmTaskView.prototype._tagChangeListener =
function(ev){
    if(ev.event == ZmEvent.E_TAGS || ev.type == ZmEvent.S_TAG) {
        this._setTags(this._calItem);
    }
};


ZmTaskView.prototype._getTagHtml =
function(tag, html, i) {
    var tagClick = ['ZmMailMsgView._tagClick("', this._htmlElId, '","', AjxStringUtil.encodeQuotes(tag.name), '");'].join("");
    var removeClick = ['ZmTaskView._removeTagClick("', this._htmlElId, '","', AjxStringUtil.encodeQuotes(tag.name), '");'].join("");
    return this._getTagHtmlElements(tag, html, i, tagClick, removeClick);
};

ZmTaskView._removeTagClick =
function(myId, tagName) {
        var tag = ZmMailMsgView._getTagClicked(tagName);
        var dwtObj = DwtControl.fromElementId(myId);
        ZmListController.prototype._doTag.call(dwtObj._controller, dwtObj._calItem, tag, false);
};
