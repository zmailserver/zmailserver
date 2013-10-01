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
 * @overview
 * This file contains the task list view classes.
 */

/**
 * Creates the task list view.
 * @class
 * This class represents the task list view.
 * 
 * @param	{DwtComposite}	parent		the parent
 * @param	{ZmTaskController}		controller		the controller
 * @param	{DwtDropTarget}	dropTgt		the drop target	
 * 
 * @extends		ZmListView
 */
ZmTaskListView = function(parent, controller, dropTgt) {

    this._controller = controller;
    
	var headerList = this._getHeaderList(parent);

    var params = {parent:parent, posStyle:Dwt.ABSOLUTE_STYLE, view:ZmId.VIEW_TASKLIST, pageless:false,
				  type:ZmItem.TASK, controller:controller, headerList:headerList, dropTgt:dropTgt}

	ZmListView.call(this, params);

};

ZmTaskListView.prototype = new ZmListView;
ZmTaskListView.prototype.constructor = ZmTaskListView;

ZmTaskListView.prototype.isZmTaskListView = true;
ZmTaskListView.prototype.toString = function() { return "ZmTaskListView"; };



ZmTaskListView.SASH_THRESHOLD = 5;

// Consts
ZmTaskListView.COL_WIDTH_STATUS		= ZmMsg.COLUMN_WIDTH_STATUS_TLV;
ZmTaskListView.COL_WIDTH_PCOMPLETE	= ZmMsg.COLUMN_WIDTH_PCOMPLETE_TLV;
ZmTaskListView.COL_WIDTH_DATE_DUE	= ZmMsg.COLUMN_WIDTH_DATE_DUE_TLV;

//Consts
ZmTaskListView.SEC_UPCOMING = "UPCOMING";
ZmTaskListView.SEC_PASTDUE = "PASTDUE";
ZmTaskListView.SEC_TODAY = "TODAY";
ZmTaskListView.SEC_NODUEDATE = "NODUEDATE";

ZmTaskListView.SEC_MSG_KEY = {};
ZmTaskListView.SEC_MSG_KEY[ZmTaskListView.SEC_UPCOMING] = ZmMsg.taskSecUpcoming;
ZmTaskListView.SEC_MSG_KEY[ZmTaskListView.SEC_PASTDUE] = ZmMsg.taskSecPastDue;
ZmTaskListView.SEC_MSG_KEY[ZmTaskListView.SEC_TODAY] = ZmMsg.taskSecToday;
ZmTaskListView.SEC_MSG_KEY[ZmTaskListView.SEC_NODUEDATE] = ZmMsg.taskSecNoDuedate;

ZmTaskListView.SEC_COLOR = {};
ZmTaskListView.SEC_COLOR[ZmTaskListView.SEC_UPCOMING] = "OrangeC";
ZmTaskListView.SEC_COLOR[ZmTaskListView.SEC_PASTDUE] = "RedC";
ZmTaskListView.SEC_COLOR[ZmTaskListView.SEC_TODAY] = "GreenC";
ZmTaskListView.SEC_COLOR[ZmTaskListView.SEC_NODUEDATE] = "GrayDarkC";

ZmTaskListView.SINGLE_COLUMN_SORT = [
    {field:ZmItem.F_SUBJECT,msg:"subject"},
    {field:ZmItem.F_DATE,	msg:"date"},
    {field:ZmItem.F_PRIORITY, msg:"priority" },
    {field:ZmItem.F_STATUS, msg:"status" },
    {field:ZmItem.F_PCOMPLETE, msg:"pComplete" },
    {field:ZmItem.F_ATTACHMENT, msg:"attachment" }
];

ZmTaskListView.SORTBY_HASH = [];
ZmTaskListView.SORTBY_HASH[ZmSearch.SUBJ_ASC] = {field:ZmItem.F_SUBJECT, msg:"subject"};
ZmTaskListView.SORTBY_HASH[ZmSearch.SUBJ_DESC] = {field:ZmItem.F_SUBJECT, msg:"subject"};
ZmTaskListView.SORTBY_HASH[ZmSearch.DUE_DATE_ASC ] = {field:ZmItem.F_DATE, msg:"date"};
ZmTaskListView.SORTBY_HASH[ZmSearch.DUE_DATE_DESC ] = {field:ZmItem.F_DATE, msg:"date"};
ZmTaskListView.SORTBY_HASH[ZmSearch.PCOMPLETE_ASC] = {field:ZmItem.F_PCOMPLETE, msg:"pComplete"};
ZmTaskListView.SORTBY_HASH[ZmSearch.PCOMPLETE_DESC] = {field:ZmItem.F_PCOMPLETE, msg:"pComplete"};
ZmTaskListView.SORTBY_HASH[ZmSearch.STATUS_ASC] = {field:ZmItem.F_STATUS, msg:"status"};
ZmTaskListView.SORTBY_HASH[ZmSearch.STATUS_DESC] = {field:ZmItem.F_STATUS, msg:"status"};
ZmTaskListView.SORTBY_HASH[ZmSearch.PRIORITY_ASC] = {field:ZmItem.F_PRIORITY, msg:"priority"};
ZmTaskListView.SORTBY_HASH[ZmSearch.PRIORITY_DESC] = {field:ZmItem.F_PRIORITY, msg:"priority"};
ZmTaskListView.SORTBY_HASH[ZmSearch.ATTACH_ASC] = {field:ZmItem.F_ATTACHMENT, msg:"attachment"};
ZmTaskListView.SORTBY_HASH[ZmSearch.ATTACH_DESC] = {field:ZmItem.F_ATTACHMENT, msg:"attachment"};
ZmTaskListView.SORTBY_HASH[ZmSearch.FLAG_ASC] = {field:ZmItem.F_TAG, msg:"tag"};
ZmTaskListView.SORTBY_HASH[ZmSearch.FLAG_DESC] = {field:ZmItem.F_TAG, msg:"tag"};

// Consts
ZmTaskListView.ROW_DOUBLE_CLASS	= "RowDouble";

ZmTaskListView._NEW_TASK_ROW_ID = "_newTaskBannerId";

// Public Methods


ZmTaskListView.prototype.setSize =
function(width, height) {
	ZmListView.prototype.setSize.call(this, width, height);
	this._resetColWidth();
};

/**
 * Saves the new task.
 * 
 * @param	{Boolean}	keepFocus		if <code>true</code>, keep focus after the save
 */
ZmTaskListView.prototype.saveNewTask =
function(keepFocus) {
	if (this._newTaskInputEl && Dwt.getVisibility(this._newTaskInputEl)) {
		var name = AjxStringUtil.trim(this._newTaskInputEl.value);
		if (name != "") {
			var respCallback = new AjxCallback(this, this._saveNewTaskResponse, [keepFocus]);
            var errorCallback = new AjxCallback(this, this._handleNewTaskError);
			this._controller.quickSave(name, respCallback, errorCallback);
		} else {
			this._saveNewTaskResponse(keepFocus);
		}
	}
};

ZmTaskListView.prototype._saveNewTaskResponse =
function(keepFocus) {
	if (keepFocus) {
		this._newTaskInputEl.value = "";
		this._newTaskInputEl.focus();
	} else {
		Dwt.setVisibility(this._newTaskInputEl, false);
	}
};

ZmTaskListView.prototype._handleNewTaskError =
function(ex) {
    if(ex) {
        this.discardNewTask();   
    }
};

ZmTaskListView.prototype.handleKeyAction =
function(actionCode, ev) {
	if (this._editing) {
		switch (actionCode) {
			case DwtKeyMap.SELECT_NEXT:		this.discardNewTask(); break;
			case DwtKeyMap.DBLCLICK:		break;
			default: DwtListView.prototype.handleKeyAction.call(this,actionCode,ev);
		}
	} else {
		DwtListView.prototype.handleKeyAction.call(this,actionCode,ev);
	}
};

/**
 * Discards the task.
 * 
 */
ZmTaskListView.prototype.discardNewTask =
function() {
	if (this._newTaskInputEl && Dwt.getVisibility(this._newTaskInputEl)) {
		this._newTaskInputEl.value = "";
		Dwt.setVisibility(this._newTaskInputEl, false);
		this.focus();
		this._editing =  false;
	}
};

/**
 * Gets the title.
 * 
 * @return	{String}		the title
 */
ZmTaskListView.prototype.getTitle =
function() {
	return [ZmMsg.zimbraTitle, this._controller.getApp().getDisplayName()].join(": ");
};

ZmTaskListView.prototype._renderTaskListItemHdr = 
function(sechdr) {
    if(!this._newSecHdrHtml[sechdr]) {

        var htmlArr = [];
        var idx = 0;

        htmlArr[idx++] = "<div id='_upComingTaskListHdr'>";
        htmlArr[idx++] = "<table width=100% class='DwtListView-Column'><tr>";
        this.dId = Dwt.getNextId();
        htmlArr[idx++] = "<td><div class='DwtListHeaderItem-label ";
        htmlArr[idx++] = ZmTaskListView.SEC_COLOR[sechdr];
        htmlArr[idx++] = "' style='padding:0px 0px 2px 2px; font-weight:bold;' id='";
        htmlArr[idx++] = this.dId;	// bug: 17653 - for QA
        htmlArr[idx++] = "'>";
        htmlArr[idx++] = ZmTaskListView.SEC_MSG_KEY[sechdr];
        htmlArr[idx++] = "</div></td>";
        htmlArr[idx++] = "</tr></table></div>";
        return this._newSecHdrHtml[sechdr] = htmlArr.join("");
   } else {
        return null;     
   }
};

ZmTaskListView.prototype.setTaskInputVisible = function(visible) {
    var el = document.getElementById(ZmTaskListView._NEW_TASK_ROW_ID);
    if (el) {
        Dwt.setVisible(el, visible);
    }
};

// Private Methods
ZmTaskListView.prototype._renderList =
function(list, noResultsOk, doAdd) {
	// call base class first
	//ZmListView.prototype._renderList.apply(this, arguments);
    this._newSecHdrHtml = {};

    if (list instanceof AjxVector && list.size()) {
		var now = new Date();
		var size = list.size();
		var htmlArr = [];
        var currentSec = null;

        var htmlUpcomingArr = [];
        var htmlPastDueArr = [];
        var htmlTodayArr = [];
        var htmlNoDueArr = [];
        
		for (var i = 0; i < size; i++) {
			var item = list.get(i);

            var today = new Date();
            today.setHours(0,0,0,0);
            today = today.getTime();

            var dueDate = item.endDate;
            if(dueDate != null) {
                dueDate.setHours(0,0,0,0);
                dueDate = dueDate.getTime();
            } else {
                dueDate = null;
            }
            
            if(dueDate != null && dueDate > today) {
               var newSecHdrHtml = this._renderTaskListItemHdr(ZmTaskListView.SEC_UPCOMING);
               if(newSecHdrHtml) htmlUpcomingArr.push(newSecHdrHtml);
                currentSec = ZmTaskListView.SEC_UPCOMING;
            } else if(dueDate != null && dueDate == today) {
                var newSecHdrHtml = this._renderTaskListItemHdr(ZmTaskListView.SEC_TODAY);
                if(newSecHdrHtml) htmlTodayArr.push(newSecHdrHtml);
                currentSec = ZmTaskListView.SEC_TODAY;
            } else if(dueDate != null && dueDate < today) {
                var newSecHdrHtml = this._renderTaskListItemHdr(ZmTaskListView.SEC_PASTDUE);
                if(newSecHdrHtml) htmlPastDueArr.push(newSecHdrHtml);
                currentSec = ZmTaskListView.SEC_PASTDUE; 
            } else if(dueDate == null) {
                var newSecHdrHtml = this._renderTaskListItemHdr(ZmTaskListView.SEC_NODUEDATE);
                if(newSecHdrHtml) htmlNoDueArr.push(newSecHdrHtml);
                currentSec = ZmTaskListView.SEC_NODUEDATE;
            } else {
                currentSec = null;
            }

            var taskStatusClass = this._normalClass;

            if(item.status == ZmCalendarApp.STATUS_COMP) {
               taskStatusClass += " ZmCompletedtask";
            } else if(item.status != ZmCalendarApp.STATUS_COMP && currentSec == ZmTaskListView.SEC_PASTDUE) {
               taskStatusClass += " ZmOverduetask";
            }

			var div = this._createItemHtml(item, {now:now,divClass:taskStatusClass}, true, i);
            if (div) {
				if (div instanceof Array) {
					for (var j = 0; j < div.length; j++){
						this._addRow(div[j]);
					}
				} else if (div.tagName || doAdd) {
					this._addRow(div);
				} else {
                    //bug:47781
					if(this._controller.getAllowableTaskStatus() == ZmTaskListController.SOAP_STATUS[ZmId.VIEW_TASK_TODO] && item.status == ZmCalendarApp.STATUS_WAIT) {
							if(currentSec == ZmTaskListView.SEC_PASTDUE) {
								htmlPastDueArr.push(div);
							}
							continue;
					}
					
                    if(currentSec == ZmTaskListView.SEC_UPCOMING) {
					    htmlUpcomingArr.push(div);
                    } else if(currentSec == ZmTaskListView.SEC_TODAY) {
                        htmlTodayArr.push(div);
                    } else if(currentSec == ZmTaskListView.SEC_PASTDUE) {
                        htmlPastDueArr.push(div);
                    } else if(currentSec == ZmTaskListView.SEC_NODUEDATE) {
                        htmlNoDueArr.push(div); 
                    } else {
                        htmlArr.push(div);
                    }
				}
			}
		}
        
        //bug:50890 in chronological order
        var sortBy = appCtxt.get(ZmSetting.SORTING_PREF, this.view);

        if(sortBy == ZmSearch.DUE_DATE_DESC) {
            if(htmlUpcomingArr.length) htmlArr.push(htmlUpcomingArr.join(""));
            if(htmlTodayArr.length) htmlArr.push(htmlTodayArr.join(""));
            if(htmlPastDueArr.length) htmlArr.push(htmlPastDueArr.join(""));
            if(htmlNoDueArr.length) htmlArr.push(htmlNoDueArr.join(""));
        } else {
            if(htmlPastDueArr.length) htmlArr.push(htmlPastDueArr.join(""));
            if(htmlTodayArr.length) htmlArr.push(htmlTodayArr.join(""));
            if(htmlUpcomingArr.length) htmlArr.push(htmlUpcomingArr.join(""));
            if(htmlNoDueArr.length) htmlArr.push(htmlNoDueArr.join(""));
        }

		if (htmlArr.length) {
			this._parentEl.innerHTML = htmlArr.join("");
		}
	} else if (!noResultsOk) {
		this._setNoResultsHtml();
	}

    if (doAdd || (this._controller && this._controller.isReadOnly())) { return; }

	// add custom row to allow user to quickly enter tasks from w/in listview
	div = document.createElement("DIV");
	div.id = ZmTaskListView._NEW_TASK_ROW_ID;

	htmlArr = [];
	var idx = 0;

	htmlArr[idx++] = "<table width=100% class='newTaskBannerSep'><tr>";
	for (var i = 0; i < this._headerList.length; i++) {
		var hdr = this._headerList[i];
		if (!hdr._visible) { continue; }

		if (hdr._field == ZmItem.F_SUBJECT || hdr._field == ZmItem.F_SORTED_BY) {
			this.dId = Dwt.getNextId();
			htmlArr[idx++] = "<td><div class='newTaskBanner' onclick='ZmTaskListView._handleOnClick(this)' id='";
			htmlArr[idx++] = this.dId;	// bug: 17653 - for QA
			htmlArr[idx++] = "'>";
			htmlArr[idx++] = ZmMsg.createNewTaskHint;
			htmlArr[idx++] = "</div></td>";
		} else {
			htmlArr[idx++] = "<td width=";
			htmlArr[idx++] = hdr._width;
			htmlArr[idx++] = ">&nbsp;</td>";
		}
	}
	htmlArr[idx++] = "</tr></table>";
	div.innerHTML = htmlArr.join("");
	this._addRow(div, 0);
    //this._renderTaskListItemHdr();
};

ZmTaskListView.prototype._resetListView =
function() {
	// explicitly remove each child (setting innerHTML causes mem leak)
	var cDiv;
	while (this._parentEl.hasChildNodes()) {
		if (this._parentEl.lastChild.id == "_newTaskBannerId") { break; }
		cDiv = this._parentEl.removeChild(this._parentEl.lastChild);
		this._data[cDiv.id] = null;
	}
	this._selectedItems.removeAll();
	this._rightSelItems = null;
};

ZmTaskListView.prototype._getCellId =
function(item, field) {
    if(field == ZmItem.F_PRIORITY || field == ZmItem.F_SUBJECT || field == ZmItem.F_STATUS || field == ZmItem.F_PCOMPLETE || field == ZmItem.F_DATE) {
	    return this._getFieldId(item, field)
    } else if (field == ZmItem.F_SELECTION) {
		return this._getFieldId(item, ZmItem.F_SELECTION_CELL);
	} else {
		return DwtListView.prototype._getCellId.apply(this, arguments);
	}
};

ZmTaskListView.prototype.setTask =
function(task) {
	this._taskReadOnlyView.set(task);
};

ZmTaskListView.prototype._getAbridgedCell =
function(htmlArr, idx, item, field, colIdx, width, attr) {
	var params = {};

	htmlArr[idx++] = "<td";
	if (width) {
		htmlArr[idx++] = " width='";
		htmlArr[idx++] = width;
		htmlArr[idx++] = "'";
	}
	htmlArr[idx++] = " id='";
	htmlArr[idx++] = this._getCellId(item, field, params);
	htmlArr[idx++] = "'";
	var className = this._getCellClass(item, field, params);
	if (className) {
		htmlArr[idx++] = " class='";
		htmlArr[idx++] = className;
		htmlArr[idx++] = "'";
	}
	if (attr) {
		htmlArr[idx++] = " ";
		htmlArr[idx++] = attr;
	}
	htmlArr[idx++] = ">";
	idx = this._getCellContents(htmlArr, idx, item, field, colIdx, params);
	htmlArr[idx++] = "</td>";

	return idx;
};

ZmTaskListView.prototype.getColorForStatus =
function(status) {
    switch (status) {
		case ZmCalendarApp.STATUS_CANC: return "YellowDark";
		case ZmCalendarApp.STATUS_COMP: return "Green";
		case ZmCalendarApp.STATUS_DEFR: return "Red";
		case ZmCalendarApp.STATUS_INPR: return "Blue";
		case ZmCalendarApp.STATUS_NEED: return "";
		case ZmCalendarApp.STATUS_WAIT: return "Orange";
	}
	return "";
};

ZmTaskListView.prototype._getAbridgedContent =
function(task, colIdx) {
	var htmlArr = [];
	var idx = 0;
	var width = (AjxEnv.isIE || AjxEnv.isSafari) ? "22" : "16";

	// first row
	htmlArr[idx++] = "<table width=100% class='TopRow'>";
	htmlArr[idx++] = "<tr id='";
	htmlArr[idx++] = DwtId.getListViewItemId(DwtId.WIDGET_ITEM_FIELD, this._view, task.id, ZmItem.F_ITEM_ROW_3PANE);
	htmlArr[idx++] = "'>";

    idx = this._getAbridgedCell(htmlArr, idx, task, ZmItem.F_SUBJECT, colIdx);

    idx = this._getAbridgedCell(htmlArr, idx, task, ZmItem.F_DATE, colIdx, ZmMsg.COLUMN_WIDTH_DATE, "align=right");

	htmlArr[idx++] = "</tr></table>";

    // second row
    htmlArr[idx++] = "<table width=100% class='BottomRow'><tr>";
    htmlArr[idx++] = "<td><div style='height:10px; width:80px; border:1px solid #c5c5c5;'><div";
    htmlArr[idx++] = " class='";
    htmlArr[idx++] = this.getColorForStatus(task.status);
    htmlArr[idx++] = "' style='height:10px; width:"+ task.pComplete + "%;'></div></div></td>";
    htmlArr[idx++] = "<td width=60 align=right><table><tr>";

    idx = this._getAbridgedCell(htmlArr, idx, task, ZmItem.F_TAG, colIdx, width);
    if(task.priority == ZmCalItem.PRIORITY_HIGH || task.priority == ZmCalItem.PRIORITY_LOW) {
        idx = this._getAbridgedCell(htmlArr, idx, task, ZmItem.F_PRIORITY, colIdx, width, "align=right");
    }
    if (task.hasAttach) {
        idx = this._getAbridgedCell(htmlArr, idx, task, ZmItem.F_ATTACHMENT, colIdx, width);
    }
    htmlArr[idx++] = "</tr></table></td>";
    htmlArr[idx++] = "</tr></table>";

	return htmlArr.join("");

};


ZmTaskListView.prototype._getCellContents =
function(htmlArr, idx, task, field, colIdx, params) {

	if (field == ZmItem.F_SELECTION) {
		var icon = params.bContained ? "CheckboxChecked" : "CheckboxUnchecked";
		idx = this._getImageHtml(htmlArr, idx, icon, this._getFieldId(task, field));

	} else if (field == ZmItem.F_PRIORITY) {
		htmlArr[idx++] = "<center>";
		htmlArr[idx++] = ZmCalItem.getImageForPriority(task, params.fieldId);
		htmlArr[idx++] = "</center>";

	} else if (field == ZmItem.F_SUBJECT) {
		htmlArr[idx++] = AjxStringUtil.htmlEncode(task.getName(), true);

	} else if (field == ZmItem.F_STATUS) {
		htmlArr[idx++] = ZmCalItem.getLabelForStatus(task.status);

	} else if (field == ZmItem.F_PCOMPLETE) {	// percent complete
        var formatter = new AjxMessageFormat(AjxMsg.percentageString);
		htmlArr[idx++] = formatter.format(task.pComplete || 0);
	} else if (field == ZmItem.F_DATE) {
		// due date - dont call base class since we *always* want to show date (not time)
		htmlArr[idx++] = task.endDate != null
			? AjxDateUtil.simpleComputeDateStr(task.endDate)
			: "&nbsp;";
	} else if (field == ZmItem.F_SORTED_BY) {
        htmlArr[idx++] = this._getAbridgedContent(task, colIdx);
    } else if (field == ZmItem.F_TAG) {
        idx = this._getImageHtml(htmlArr, idx, task.getTagImageInfo(), this._getFieldId(task, field));
    } else {
		idx = ZmListView.prototype._getCellContents.apply(this, arguments);
	}
	
	return idx;
};

ZmTaskListView.prototype._getHeaderToolTip =
function(field, itemIdx) {
	switch (field) {
		case ZmItem.F_STATUS:		return ZmMsg.sortByStatus;
		case ZmItem.F_PCOMPLETE:	return ZmMsg.sortByPComplete;
		case ZmItem.F_DATE:			return ZmMsg.sortByDueDate;
        case ZmItem.F_PRIORITY:	    return ZmMsg.sortByPriority;
        case ZmItem.F_ATTACHMENT:	return ZmMsg.sortByAttachment;
        case ZmItem.F_TAG:	        return ZmMsg.sortByTag;
	}
	return ZmListView.prototype._getHeaderToolTip.call(this, field, itemIdx);
};

ZmTaskListView.prototype._sortColumn =
function(columnItem, bSortAsc) {
	// change the sort preference for this view in the settings
	var sortBy;
	switch (columnItem._sortable) {
		case ZmItem.F_SUBJECT:		sortBy = bSortAsc ? ZmSearch.SUBJ_ASC : ZmSearch.SUBJ_DESC; break;
		case ZmItem.F_STATUS:		sortBy = bSortAsc ? ZmSearch.STATUS_ASC : ZmSearch.STATUS_DESC; break;
		case ZmItem.F_PCOMPLETE:	sortBy = bSortAsc ? ZmSearch.PCOMPLETE_ASC : ZmSearch.PCOMPLETE_DESC; break;
		case ZmItem.F_DATE:			sortBy = bSortAsc ? ZmSearch.DUE_DATE_ASC : ZmSearch.DUE_DATE_DESC;	break; //bug:50890 changed the default order
        case ZmItem.F_PRIORITY:     sortBy = bSortAsc ? ZmSearch.PRIORITY_ASC : ZmSearch.PRIORITY_DESC;	break;
        case ZmItem.F_ATTACHMENT:   sortBy = bSortAsc ? ZmSearch.ATTACH_ASC : ZmSearch.ATTACH_DESC;	break;
        case ZmItem.F_TAG:          sortBy = bSortAsc ? ZmSearch.FLAG_ASC : ZmSearch.FLAG_DESC;	break;
        case ZmItem.F_SORTED_BY:    sortBy = bSortAsc ? ZmSearch.DUE_DATE_ASC : ZmSearch.DUE_DATE_DESC;	break;
	}

    if (sortBy) {
		this._sortByString = sortBy;
        if (!appCtxt.isExternalAccount()) {
		    appCtxt.set(ZmSetting.SORTING_PREF, sortBy, this.view);
        }
	}

	var list = this.getList();
	var size = list ? list.size() : 0;
	if (size > 0 && this._sortByString) {
		var params = {
			query: this._controller.getSearchString(),
			queryHint: this._controller.getSearchStringHint(),
			types: [ZmItem.TASK],
			sortBy: this._sortByString,
			limit: this.getLimit()
		};
		appCtxt.getSearchController().search(params);
	}
};

ZmTaskListView.prototype._handleNewTaskClick =
function(el) {
    if  (appCtxt.isExternalAccount()) {
        return;
    }
	if (!this._newTaskInputEl) {
		this._newTaskInputEl = document.createElement("INPUT");
		this._newTaskInputEl.type = "text";
		this._newTaskInputEl.className = "InlineWidget";
		this._newTaskInputEl.style.position = "absolute";
		this._newTaskInputEl.id = Dwt.getNextId();								// bug: 17653 - for QA

		Dwt.setHandler(this._newTaskInputEl, DwtEvent.ONBLUR, ZmTaskListView._handleOnBlur);
		Dwt.setHandler(this._newTaskInputEl, DwtEvent.ONKEYPRESS, ZmTaskListView._handleKeyPress);
		this.shell.getHtmlElement().appendChild(this._newTaskInputEl);

		this._resetInputSize(el);
	} else {
		this._newTaskInputEl.value = "";
	}
	Dwt.setVisibility(this._newTaskInputEl, true);
	this._newTaskInputEl.focus();
	this._editing =  true;
};


ZmTaskListView.prototype._handleColHeaderResize =
function(ev) {
	ZmListView.prototype._handleColHeaderResize.call(this, ev);
	this._newTaskInputEl = null;
};

ZmTaskListView.prototype._getHeaderList =
function(parent) {

	var hList = [];
    var sortBy = "date";
    var activeSortBy = this.getActiveSearchSortBy();
    if (activeSortBy && ZmTaskListView.SORTBY_HASH[activeSortBy]) {
			sortBy = ZmTaskListView.SORTBY_HASH[activeSortBy].msg;
	}

    if (appCtxt.get(ZmSetting.SHOW_SELECTION_CHECKBOX)) {
        hList.push(new DwtListHeaderItem({field:ZmItem.F_SELECTION, icon:"CheckboxUnchecked", width:ZmListView.COL_WIDTH_ICON, name:ZmMsg.selection}));
    }
    if (this.isMultiColumn()) {
        if (appCtxt.get(ZmSetting.TAGGING_ENABLED)) {
            hList.push(new DwtListHeaderItem({field:ZmItem.F_TAG, icon:"Tag", width:ZmListView.COL_WIDTH_ICON, name:ZmMsg.tag, sortable:ZmItem.F_TAG}));
        }
        hList.push(new DwtListHeaderItem({field:ZmItem.F_PRIORITY, icon:"PriorityHigh_list", width:ZmListView.COL_WIDTH_ICON, name:ZmMsg.priority, sortable:ZmItem.F_PRIORITY}));
        hList.push(new DwtListHeaderItem({field:ZmItem.F_ATTACHMENT, icon:"Attachment", width:ZmListView.COL_WIDTH_ICON, name:ZmMsg.attachment, sortable:ZmItem.F_ATTACHMENT}));
        hList.push(new DwtListHeaderItem({field:ZmItem.F_SUBJECT, text:ZmMsg.subject, sortable:ZmItem.F_SUBJECT, resizeable:true, noRemove:true}));
        hList.push(new DwtListHeaderItem({field:ZmItem.F_STATUS, text:ZmMsg.status, width:ZmTaskListView.COL_WIDTH_STATUS, resizeable:true, sortable:ZmItem.F_STATUS}));
        hList.push(new DwtListHeaderItem({field:ZmItem.F_PCOMPLETE, text:ZmMsg.pComplete, width:ZmTaskListView.COL_WIDTH_PCOMPLETE, sortable:ZmItem.F_PCOMPLETE}));
        hList.push(new DwtListHeaderItem({field:ZmItem.F_DATE, text:ZmMsg.dateDue, width:ZmTaskListView.COL_WIDTH_DATE_DUE, sortable:ZmItem.F_DATE}));
    }
	else {
        hList.push(new DwtListHeaderItem({field:ZmItem.F_SORTED_BY, text:AjxMessageFormat.format(ZmMsg.arrangedBy, ZmMsg[sortBy]), sortable:ZmItem.F_SORTED_BY, resizeable:false}));
	}
	return hList;
};

ZmTaskListView.prototype._createHeader =
function(htmlArr, idx, headerCol, i, numCols, id, defaultColumnSort) {
    if (headerCol._field == ZmItem.F_SORTED_BY) {
		var field = headerCol._field;
		var textTdId = this._itemCountTextTdId = DwtId.makeId(this.view, ZmSetting.RP_RIGHT, "td");
		htmlArr[idx++] = "<td id='";
		htmlArr[idx++] = id;
		htmlArr[idx++] = "' class='";
		htmlArr[idx++] = (id == this._currentColId)	? "DwtListView-Column DwtListView-ColumnActive'" :
													  "DwtListView-Column'";
		htmlArr[idx++] = " width='auto'><table width='100%'><tr><td id='";
		htmlArr[idx++] = DwtId.getListViewHdrId(DwtId.WIDGET_HDR_LABEL, this._view, field);
		htmlArr[idx++] = "' class='DwtListHeaderItem-label'>";
		htmlArr[idx++] = headerCol._label;
		htmlArr[idx++] = "</td>";

		// sort icon
		htmlArr[idx++] = "<td class='itemSortIcon' id='";
		htmlArr[idx++] = DwtId.getListViewHdrId(DwtId.WIDGET_HDR_ARROW, this._view, field);
		htmlArr[idx++] = "'>";
		htmlArr[idx++] = AjxImg.getImageHtml(this._bSortAsc ? "ColumnUpArrow" : "ColumnDownArrow");
		htmlArr[idx++] = "</td>";

		// item count text
		htmlArr[idx++] = "<td align=right class='itemCountText' id='";
		htmlArr[idx++] = textTdId;
		htmlArr[idx++] = "'></td></tr></table></div></td>";
	} else {
        return DwtListView.prototype._createHeader.apply(this, arguments);
	}
};


// Listeners
// this method simply appends the given list to this current one
ZmTaskListView.prototype.replenish =
function(list) {
	this._list.addList(list);
	this._renderList(this.getList(),true,false);
};

ZmTaskListView.prototype.checkTaskReplenishListView = function() {
    this._controller._app._checkReplenishListView = this;
};

ZmTaskListView.prototype._changeListener =
function(ev) {
	if (ev.type != this.type)
		return;

    //TODO: Optimize ChangeListener logic
	var items = ev.getDetail("items") || ev.items;
    var filter = this._controller.getAllowableTaskStatus();
    items = AjxUtil.toArray(items);
    if (ev.event == ZmEvent.E_CREATE || (ev.event == ZmEvent.E_MODIFY && !this._getElFromItem(items[0]))) {
		for (var i = 0; i < items.length; i++) {
			var item = items[i];

			// skip if this item does not belong in this list.
			var folderId = this._controller.getList().search.folderId;
			if (appCtxt.getById(folderId) &&
				appCtxt.getById(folderId).isRemote())
			{
				folderId = appCtxt.getById(folderId).getRemoteId();
			}

			if (appCtxt.isOffline) {
				folderId = ZmOrganizer.getSystemId(folderId);
			}

			if (folderId && folderId != item.folderId) { continue; }			// does not belong to this folder
			if (this._list && this._list.contains(item)) { continue; }			// skip if we already have it


			if (!this._list) {
				this._list = new AjxVector();
			}
			// clear the "no results" message before adding!
			if (this._list.size() == 0) {
				this._resetList();
			}
			// Check if the item is part of current view
            if (!filter || filter.indexOf(item.status) != -1){
                // add new item at the beg. of list view's internal list
                this._list.add(item, 0);
                this._renderList(this.getList(),true,false);
                if(this._list && this._list.size() == 1) { this.setSelection(this._list.get(0)); }
            }
		}
	} else if (ev.event == ZmEvent.E_MODIFY) {
		var task = items[0];
        var div = this._getElFromItem(task);
        if (this._list) {
            var origTaskIndex = this._list.indexOfLike(task, task.getId);
            if (origTaskIndex != -1) this._list.replace(origTaskIndex, task);
        }
		if (div) {
            if (filter && filter.indexOf(task.status) == -1){
                // If task status is modified and item is not part of current view
                var parentNode = div.parentNode;
                parentNode && parentNode.removeChild(div);
                this._controller._resetToolbarOperations();
                if(this._controller.isReadingPaneOn()) {
                    this._controller.getTaskMultiView().getTaskView().reset();
                }
            } else{
                var bContained = this._selectedItems.contains(div);
                this._createItemHtml(task, {div:div, bContained:bContained});
                this.associateItemWithElement(task, div);
                if(this._controller.isReadingPaneOn()) {
                    task.message = null;
			        task.getDetails(ZmCalItem.MODE_EDIT, new AjxCallback(this._controller, this._controller._showTaskReadOnlyView, [task, false]))
                }
                this.checkTaskReplenishListView();
            }
		}
	} else if (ev.event == ZmEvent.E_DELETE || ev.event == ZmEvent.E_MOVE) {
        var needsSort = false;
        for (var i = 0, len = items.length; i < len; i++) {
			var item = items[i];
            var movedHere = item.type == ZmId.ITEM_CONV ? item.folders[this._folderId] : item.folderId == this._folderId;
			if (movedHere && ev.event == ZmEvent.E_MOVE) {
				// We've moved the item into this folder
				if (this._getRowIndex(item) === null) { // Not already here
					this.addItem(item);
					needsSort = true;
				}
			} else {
				this.removeItem(item, true, ev.batchMode);
				// if we've removed it from the view, we should remove it from the reference
				// list as well so it doesn't get resurrected via replenishment *unless*
				// we're dealing with a canonical list (i.e. contacts)
				var itemList = this.getItemList();
				if (ev.event != ZmEvent.E_MOVE || !itemList.isCanonical) {
					itemList.remove(item);
				}
			}
		}
        if(needsSort) {
            this.checkTaskReplenishListView();
        }
		this._controller._resetToolbarOperations();
		if(this._controller.isReadingPaneOn()) {
			this._controller.getTaskMultiView().getTaskView().reset();
		}
	} else {
		ZmListView.prototype._changeListener.call(this, ev);
	}

    //Handle Create Notification
    if(ev.event == ZmEvent.E_MOVE){
        var folderId = this._controller._folderId || this.folderId || this._folderId;
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            if(item && item.folderId == folderId && this._getRowIndex(item) === null){
                this.addItem(item, null, true);
            }
        }
    }

	if (ev.event == ZmEvent.E_CREATE ||
		ev.event == ZmEvent.E_DELETE ||
		ev.event == ZmEvent.E_MOVE)
	{
		this._resetColWidth();
	}

    //this.reRenderListView();
};


// Static Methods

ZmTaskListView._handleOnClick =
function(div) {
	var appCtxt = window.parentAppCtxt || window.appCtxt;
	var tlv = appCtxt.getApp(ZmApp.TASKS).getTaskListController().getListView();
	tlv._handleNewTaskClick(div);
};

ZmTaskListView._handleOnBlur =
function(ev) {
	var appCtxt = window.parentAppCtxt || window.appCtxt;
	var tlv = appCtxt.getApp(ZmApp.TASKS).getTaskListController().getListView();
	tlv.saveNewTask();
};

ZmTaskListView.prototype._selectItem =
function(next, addSelect, kbNavEvent) {
	if (!next) {
		var itemDiv = (this._kbAnchor)
		? this._getSiblingElement(this._kbAnchor, next)
		: this._parentEl.firstChild;
		if (itemDiv && itemDiv.id == "_newTaskBannerId") {
			document.getElementById(this.dId).onclick();
			return;
		}
	}
	DwtListView.prototype._selectItem.call(this,next,addSelect,kbNavEvent);
};

ZmTaskListView._handleKeyPress =
function(ev) {
	var key = DwtKeyEvent.getCharCode(ev);

	var appCtxt = window.parentAppCtxt || window.appCtxt;
	var tlv = appCtxt.getApp(ZmApp.TASKS).getTaskListController().getListView();

	if (key == DwtKeyEvent.KEY_ENTER) {
		tlv.saveNewTask(true);
	}
	else {
		// bug fix #31778 - down arrow and left paren. have same key code!
		var isDownArrow = (!AjxEnv.isIE)
			? (ev.charCode == 0 && ev.keyCode == 40) : false;
		if (key == DwtKeyEvent.KEY_ESCAPE || isDownArrow) {
			tlv.discardNewTask();
		}
	}
};

/**
 * Returns true if the reading pane is turned off or set to bottom. We use this
 * call to tell the UI whether to re-render the listview with multiple columns
 * or a single column (for right-pane).
 */
ZmTaskListView.prototype.isMultiColumn =
function(controller) {
	var ctlr = controller || this._controller;
	return !ctlr.isReadingPaneOnRight();
};


ZmTaskListView.prototype.updateListViewEl = 
function(task) {
	var div = this._getElFromItem(task);
	if (div) {
        if (this._controller.isHiddenTask(task)){
            this.removeItem(task, true);
            if(this._controller.isReadingPaneOn()) {
			    this._controller.getTaskMultiView().getTaskView().reset();
		    }
        }else{
		    var bContained = this._selectedItems.contains(div);
		    this._createItemHtml(task, {div:div, bContained:bContained});
		    this.associateItemWithElement(task, div);
        }

	}
};


/**
 * Called by the controller whenever the reading pane preference changes
 *
 * @private
 */
ZmTaskListView.prototype.reRenderListView =
function() {
	var isMultiColumn = this.isMultiColumn();
	if (isMultiColumn != this._isMultiColumn) {
		this._saveState({selection:true, focus:true, scroll:true, expansion:true});
		this._isMultiColumn = isMultiColumn;
		this.headerColCreated = false;
		this._headerList = this._getHeaderList();
		this._rowHeight = null;
		this._normalClass = isMultiColumn ? DwtListView.ROW_CLASS : ZmTaskListView.ROW_DOUBLE_CLASS;
		var list = this.getList() || (new AjxVector());
		this.set(list.clone(), this.getActiveSearchSortBy());
        this._restoreState();
	}
};

ZmTaskListView.prototype.resetSize =
function(newWidth, newHeight) {
	this.setSize(newWidth, newHeight);
	var height = (newHeight == Dwt.DEFAULT) ? newHeight : newHeight - DwtListView.HEADERITEM_HEIGHT;
	Dwt.setSize(this._parentEl, newWidth, height);
	this._resetInputSize();
};

ZmTaskListView.prototype._resetColWidth =
function() {

	if (!this.headerColCreated) { return; }

	var lastColIdx = this._getLastColumnIndex();
    if (lastColIdx) {
        var lastCol = this._headerList[lastColIdx];
		if (lastCol._field != ZmItem.F_SORTED_BY) {
			DwtListView.prototype._resetColWidth.apply(this, arguments);
		}
	}
};

ZmTaskListView.prototype._resetInputSize =
function(el) {

	if (this._newTaskInputEl) {
		el = el || document.getElementById(this.dId);
		if (el) {
			var bounds = Dwt.getBounds(el);
			Dwt.setBounds(this._newTaskInputEl, bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
};

ZmTaskListView.prototype._getSingleColumnSortFields =
function() {
    var sortFields = (appCtxt.get(ZmSetting.TAGGING_ENABLED)) ?
                    ZmTaskListView.SINGLE_COLUMN_SORT.concat({field:ZmItem.F_TAG, msg:"tag" }) : ZmTaskListView.SINGLE_COLUMN_SORT;
	return sortFields;
};

/**
 * return the active search sortby value
 * @return {String} sortby value or null
 */
ZmTaskListView.prototype.getActiveSearchSortBy =
function() {
	var sortBy = AjxUtil.get(this._controller, "_activeSearch", "search", "sortBy") || null;
	return sortBy;
};

ZmTaskListView.prototype._getPrefSortField =
function(){
var activeSortBy = this.getActiveSearchSortBy();
return activeSortBy && ZmTaskListView.SORTBY_HASH[activeSortBy] ?
       ZmTaskListView.SORTBY_HASH[activeSortBy].field : ZmItem.F_DATE;
};


ZmTaskListView.prototype._getActionMenuForColHeader =
function(force) {
	if (!this.isMultiColumn()) {
		if (!this._colHeaderActionMenu || force) {
			this._colHeaderActionMenu = this._getSortMenu(this._getSingleColumnSortFields(), this._getPrefSortField());
		}
        return this._colHeaderActionMenu;
	}

	var menu = ZmListView.prototype._getActionMenuForColHeader.call(this, force);

	return menu;
};


ZmTaskListView.prototype._colHeaderActionListener =
function(ev) {
	if (!this.isMultiColumn()) {
		this._sortMenuListener(ev);
	}
	else {
		ZmListView.prototype._colHeaderActionListener.apply(this, arguments);
	}
};


