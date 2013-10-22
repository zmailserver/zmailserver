/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2011, 2012 VMware, Inc.
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
 * @class
 * This class represents a search toolbar that shows up above search results. It can be
 * used to refine the search results. Each search term is contained within a bubble that
 * can easily be removed.
 * 
 * @param {hash}			params		a hash of parameters:
 * @param {DwtComposite}	parent		the parent widget
 * @param {string}			id			an explicit ID to use for the control's HTML element
 * 
 * @extends		ZmSearchToolBar
 * 
 * @author Conrad Damon
 */
ZmSearchResultsToolBar = function(params) {

	params.posStyle = Dwt.ABSOLUTE_STYLE;
	params.className = "ZmSearchResultsToolBar";
	this._controller = params.controller;

	ZmSearchToolBar.apply(this, arguments);
	
	this._bubbleId = {};
	this._origin = ZmId.SEARCHRESULTS;
	
	this.initAutocomplete();
};

ZmSearchResultsToolBar.prototype = new ZmSearchToolBar;
ZmSearchResultsToolBar.prototype.constructor = ZmSearchResultsToolBar;

ZmSearchResultsToolBar.prototype.isZmSearchResultsToolBar = true;
ZmSearchResultsToolBar.prototype.toString = function() { return "ZmSearchResultsToolBar"; };


ZmSearchResultsToolBar.prototype.TEMPLATE = "share.Widgets#ZmSearchResultsToolBar";

ZmSearchResultsToolBar.prototype._createHtml =
function() {

	this.getHtmlElement().innerHTML = AjxTemplate.expand(this.TEMPLATE, {id:this._htmlElId});

	var idContext = this._controller.getCurrentViewId();
	
	this._label = document.getElementById(this._htmlElId + "_label");
	this._iconDiv = document.getElementById(this._htmlElId + "_icon");
	
	// add search input field
	var inputFieldCellId = this._htmlElId + "_inputFieldCell";
	var inputFieldCell = document.getElementById(inputFieldCellId);
	if (inputFieldCell) {
		var aifParams = {
			parent:					this,
			strictMode:				false,
			id:						DwtId.makeId(ZmId.WIDGET_INPUT, idContext),
			bubbleAddedCallback:	this._bubbleChange.bind(this),
			bubbleRemovedCallback:	this._bubbleChange.bind(this),
			noOutsideListening:		true
		}
		var aif = this._searchField = new ZmAddressInputField(aifParams);
		aif.reparentHtmlElement(inputFieldCell);
		
		var inputEl = this._searchField.getInputElement();
		inputEl.className = "search_results_input";
	}
	
	// add search button
	this._button[ZmSearchToolBar.SEARCH_BUTTON] = ZmToolBar.addButton({
				parent:		this, 
				tdId:		"_searchButton",
				buttonId:	ZmId.getButtonId(idContext, ZmId.SEARCHRESULTS_SEARCH),
				lbl:		ZmMsg.search,
				tooltip:	ZmMsg.searchTooltip
			});

	// add save search button if saved-searches enabled
	this._button[ZmSearchToolBar.SAVE_BUTTON] = ZmToolBar.addButton({
				parent:		this, 
				setting:	ZmSetting.SAVED_SEARCHES_ENABLED,
				tdId:		"_saveButton",
				buttonId:	ZmId.getButtonId(idContext, ZmId.SEARCHRESULTS_SAVE),
				lbl:		ZmMsg.save,
				tooltip:	ZmMsg.saveSearchTooltip
			});
};

// TODO: use the main search toolbar's autocomplete list - need to manage location callback
ZmSearchResultsToolBar.prototype.initAutocomplete =
function() {
	if (!this._acList) {
		this._acList = new ZmAutocompleteListView(this._getAutocompleteParams());
		this._acList.handle(this.getSearchField(), this._searchField._htmlElId);
	}
	this._searchField.setAutocompleteListView(this._acList);
};

ZmSearchResultsToolBar.prototype._getAutocompleteParams =
function() {
	var params = ZmSearchToolBar.prototype._getAutocompleteParams.apply(this, arguments);
	params.options = { addrBubbles: true, noBubbleParse: true };
	return params;
};

ZmSearchResultsToolBar.prototype.setSearch =
function(search) {
	this._settingSearch = true;
	this._searchField.clear(true);
	var tokens = search.getTokens();
	if (tokens && tokens.length) {
		for (var i = 0, len = tokens.length; i < len; i++) {
			var token = tokens[i], prevToken = tokens[i - 1], nextToken = tokens[i + 1];
			var showAnd = (prevToken && prevToken.op == ZmParsedQuery.GROUP_CLOSE) || (nextToken && nextToken.op == ZmParsedQuery.GROUP_OPEN);
			var text = token.toString(showAnd);
			if (text) {
				var bubble = this._searchField.addBubble({address:text, noParse:true});
				this._bubbleId[text] = bubble.id;
			}
		}
	}
	this._settingSearch = false;
};

ZmSearchResultsToolBar.prototype.setLabel =
function(text, showError) {
	this._label.innerHTML = text;
	this._iconDiv.style.display = showError ? "inline-block" : "none";
};

// Don't let the removal or addition of a bubble when we're setting up trigger a search loop.
ZmSearchResultsToolBar.prototype._bubbleChange =
function(bubble, added) {
	if (!this._settingSearch) {
		var pq = new ZmParsedQuery(bubble.address);
		var tokens = pq.getTokens();
		// don't run search if a conditional was added or removed
		if (!(tokens && tokens[0] && tokens[0].type == ZmParsedQuery.COND)) {
			// use timer to let content of search bar get set - if a search term is autocompleted,
			// the bubble is added before the text it replaces is removed
			setTimeout(this._handleEnterKeyPress.bind(this), 10);
		}
	}
};

ZmSearchResultsToolBar.prototype._handleEnterKeyPress =
function(ev) {
	this.setLabel(ZmMsg.searching);
	ZmSearchToolBar.prototype._handleEnterKeyPress.apply(this, arguments);
};

/**
 * Adds a bubble for the given search term.
 * 
 * @param {ZmSearchToken}	term		search term
 * @param {boolean}			skipNotify	if true, don't trigger a search
 * @param {boolean}			addingCond	if true, user clicked to add a conditional term
 */
ZmSearchResultsToolBar.prototype.addSearchTerm =
function(term, skipNotify, addingCond) {

	var text = term.toString(addingCond);
	var index;
	if (addingCond) {
		var bubbleList = this._searchField._getBubbleList();
		var bubbles = bubbleList.getArray();
		for (var i = 0; i < bubbles.length; i++) {
			if (bubbleList.isSelected(bubbles[i])) {
				index = i;
				break;
			}
		}
	}

	var bubble = this._searchField.addBubble({
				address:	text,
				addClass:	term.type,
				skipNotify:	skipNotify,
				index:		index
			});
	if (bubble) {
		this._bubbleId[text] = bubble.id;
		return bubble.id;
	}
};

/**
 * Removes the bubble with the given search term.
 * 
 * @param {ZmSearchToken|string}	term		search term or bubble ID
 * @param {boolean}					skipNotify	if true, don't trigger a search
 */
ZmSearchResultsToolBar.prototype.removeSearchTerm =
function(term, skipNotify) {
	if (!term) { return; }
	var text = term.toString();
	var id = term.isZmSearchToken ? this._bubbleId[text] : term;
	if (id) {
		this._searchField.removeBubble(id, skipNotify);
		delete this._bubbleId[text];
	}
};
