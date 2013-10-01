/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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
 * Html Editor
 *
 * @author Ross Dargahi
 * @class
 * @constructor
 */
ZmHtmlEditor = function(parent, posStyle, content, mode, withAce) {
	if (arguments.length == 0) return;
	this._toolbars = [];

	// ACE?
	this.ACE_ENABLED = !!withAce;
	this.ACE_DEBUG = false;

	if (this.ACE_ENABLED) {
		this._ace_componentsLoading = 0;
    }

	DwtHtmlEditor.call(this, {parent:parent, className:"ZmHtmlEditor", posStyle:posStyle,
							  content:content, mode:mode});

	this.addStateChangeListener(new AjxListener(this, this._rteStateChangeListener));

	var settings = appCtxt.getSettings();
	var listener = new AjxListener(this, this._settingChangeListener);
	settings.getSetting(ZmSetting.COMPOSE_INIT_FONT_COLOR).addChangeListener(listener);
	settings.getSetting(ZmSetting.COMPOSE_INIT_FONT_FAMILY).addChangeListener(listener);
	settings.getSetting(ZmSetting.COMPOSE_INIT_FONT_SIZE).addChangeListener(listener);

	this._ignoreWords = {};
};

ZmHtmlEditor.prototype = new DwtHtmlEditor;
ZmHtmlEditor.prototype.constructor = ZmHtmlEditor;


// Consts
ZmHtmlEditor._VALUE = "value";
ZmHtmlEditor.FONT_SIZE_VALUES = ["8pt", "10pt", "12pt", "14pt", "18pt", "24pt", "36pt"];
ZmHtmlEditor.__makeFontName = function(value) {
	return value.replace(/,.*/,"").replace(/[']/g,"").replace(/\b[a-z]/g, ZmHtmlEditor.__toUpperCase);
};
ZmHtmlEditor.__toUpperCase = function(s) {
	return s.toUpperCase();
};

// Big ugly RegExp, looking for iframe tags where the id starts with "ACE-"
ZmHtmlEditor.ACE_IFRAME_RE = new RegExp("<iframe\\s+.*?\\bid\\s*=\\s*[\"']?(ace-[^\"'\\s]*).*?>.*?</iframe(\\s.*?)?>", "ig");



// Public methods

ZmHtmlEditor.prototype.toString =
function() {
	return "ZmHtmlEditor";
};

ZmHtmlEditor.prototype.isHtmlEditingSupported =
function() {
	var isSupported = DwtHtmlEditor.prototype.isHtmlEditingSupported.call(this);
	if (isSupported) {
		// browser supports html edit but check if user pref allows it
		isSupported = appCtxt.get(ZmSetting.HTML_COMPOSE_ENABLED);
	}

	return isSupported;
};

ZmHtmlEditor.prototype.setContent = function(content) {
	DwtHtmlEditor.prototype.setContent.apply(this, arguments);
	this._ignoreWords = {};
};

ZmHtmlEditor.prototype.setMode =
function(mode, convert, convertor) {
	this.discardMisspelledWords();

	DwtHtmlEditor.prototype.setMode.call(this, mode, convert, convertor);

	if (mode == DwtHtmlEditor.HTML) {
		this._createToolbars();
	}

	// show/hide toolbars based on mode
	for (var i = 0; i < this._toolbars.length; i++) {
		var toolbar = this._toolbars[i];
		toolbar.setVisible(mode == DwtHtmlEditor.HTML);
	}

	appCtxt.notifyZimlets("on_htmlEditor_setMode", [this, mode]);
};

ZmHtmlEditor.prototype.getBodyFieldId =
function() {
	return this._mode == DwtHtmlEditor.HTML ? this._iFrameId : this._textAreaId;
};

// returns the text version of the html message
ZmHtmlEditor.prototype.getTextVersion =
function(convertor) {
	return this._mode == DwtHtmlEditor.HTML
		? this._convertHtml2Text(convertor)
		: this.getContent();
};

// Re-sets design mode for buggy gecko-based browser
ZmHtmlEditor.prototype.reEnableDesignMode =
function() {
	if (AjxEnv.isGeckoBased || AjxEnv.isSafari) {
		this._enableDesignMode(this._getIframeDoc());
	}
};

ZmHtmlEditor.prototype.addEventCallback =
function(callback) {
	this._eventCallback = callback;
};

ZmHtmlEditor.prototype._onContentInitialized =
function() {
	DwtHtmlEditor.prototype._onContentInitialized.call(this);
	this._loadExternalStyle("/css/editor.css");
	this._setFontStyles();
	if (this.ACE_ENABLED && this._mode == DwtHtmlEditor.HTML) {
		setTimeout(AjxCallback.simpleClosure(this._deserializeAceObjects, this), 100);
	}
	if (this._onContentInitializeCallback) {
		AjxDebug.println(AjxDebug.REPLY, "ZmHtmlEditor::_onContentInitialized - run callback");
		this._onContentInitializeCallback.run();
	}
};

ZmHtmlEditor.prototype.addOnContentInitializedListener =
function(callback) {
	this._onContentInitializeCallback = callback;
};

ZmHtmlEditor.prototype.removeOnContentInitializedListener =
function() {
	this._onContentInitializeCallback = null;
};

ZmHtmlEditor.prototype.getContent =
function(insertFontStyle, onlyInnerContent ) {
	this.discardMisspelledWords();

	// NOTE: this code is same as base class except we use insertFontStyle
	// (which shouldnt be in base).
	var content;
	if (this._mode == DwtHtmlEditor.HTML) {
		AjxDebug.println(AjxDebug.REPLY, "ZmHtmlEditor::getContent - in HTML mode");
		var iframeDoc = this._getIframeDoc();

		var html = (iframeDoc && iframeDoc.body && iframeDoc.body.innerHTML) || "";
		AjxDebug.println(AjxDebug.REPLY, "content from iframe body: " + AjxStringUtil.htmlEncode(html.substr(0,200)));
		this._blankDiv = this._blankDiv || this._getFontStyle("");
		this._blankHtml = this._blankHtml || this._embedHtmlContent("", true);
		if (this._pendingContent && (!html || html == this._blankDiv || html == this._blankHtml)) {
			html = this._pendingContent;
			AjxDebug.println(AjxDebug.REPLY, "using pending content: " + AjxStringUtil.htmlEncode(html.substr(0,200)));
		}
		content = this._embedHtmlContent(html, insertFontStyle, onlyInnerContent);
		if (this.ACE_ENABLED) {
			content = this._serializeAceObjects(content);
		}
	} else {
		content = document.getElementById(this._textAreaId).value;
	}

	return content;
};

ZmHtmlEditor.prototype.checkMisspelledWords =
function(callback, onExitCallback, errCallback){
	var text = this.getTextVersion();
	if (/\S/.test(text)) {
		AjxDispatcher.require("Extras");
		this._spellChecker = new ZmSpellChecker(this);
		this._spellCheck = null;
		this._spellCheckSuggestionListenerObj = new AjxListener(this, this._spellCheckSuggestionListener);
		if (!this.onExitSpellChecker) {
			this.onExitSpellChecker = onExitCallback;
		}
		var params = {
			text: text,
			ignore: AjxUtil.keys(this._ignoreWords).join()
		};
		this._spellChecker.check(params, callback, errCallback);
		return true;
	}

	return false;
};

ZmHtmlEditor.prototype.spellCheck =
function(callback) {
	var text = this.getTextVersion();

	if (/\S/.test(text)) {
		AjxDispatcher.require("Extras");
		this._spellChecker = new ZmSpellChecker(this);
		this._spellCheck = null;
		this._spellCheckSuggestionListenerObj = new AjxListener(this, this._spellCheckSuggestionListener);
		if (!this.onExitSpellChecker) {
			this.onExitSpellChecker = callback;
		}
		var params = {
			text: text,
			ignore: AjxUtil.keys(this._ignoreWords).join()
		};
		this._spellChecker.check(params, new AjxCallback(this, this._spellCheckCallback));
		return true;
	}

	return false;
};

ZmHtmlEditor.prototype.resetSpellCheck =
function(){
	if (!this._spellCheck) { return; }
	this.discardMisspelledWords();
	this._spellCheckHideModeDiv();
};

ZmHtmlEditor.prototype.discardMisspelledWords =
function(keepModeDiv) {
	if (!this._spellCheck) { return; }

	if (this._mode == DwtHtmlEditor.HTML) {
		var doc = this._getIframeDoc();
		doc.body.style.display = "none";

		var p = null;
		var spanIds = this._spellCheck.spanIds;
		for (var i in spanIds) {
			var span = doc.getElementById(i);
			if (!span) continue;

			p = span.parentNode;
			while (span.firstChild) {
				p.insertBefore(span.firstChild, span);
			}
			p.removeChild(span);
		}

		if (!AjxEnv.isIE) {
			doc.body.normalize(); // IE crashes here.
		} else {
			doc.body.innerHTML = doc.body.innerHTML; // WTF.
		}

		// remove the spell check styles
		p = doc.getElementById("ZM-SPELLCHECK-STYLE");
		if (p) {
			p.parentNode.removeChild(p);
		}

		doc.body.style.display = "";
		this._unregisterEditorEventHandler(doc, "contextmenu");

	} else if (this._spellCheckDivId != null) {
		var div = document.getElementById(this._spellCheckDivId);
		var scrollTop = div.scrollTop;
		var textArea = document.getElementById(this._textAreaId);
		// bug: 41760 - HACK. Convert the nbsps back to spaces since Gecko seems
		// to return control characters for HTML entities.
		if (AjxEnv.isGeckoBased) {
			div.innerHTML = AjxStringUtil.htmlDecode(div.innerHTML, true);
		}
		textArea.value = AjxUtil.getInnerText(div);

		// avoid mem. leaks, hopefully
		div.onclick = null;
		div.oncontextmenu = null;
		div.onmousedown = null;
		div.parentNode.removeChild(div);
		textArea.style.display = "";
		textArea.scrollTop = scrollTop;
	}

	this._spellCheckDivId = this._spellCheck = null;
	window.status = "";

	if (!keepModeDiv) {
		this._spellCheckHideModeDiv();
	}

	if (this.onExitSpellChecker) {
		this.onExitSpellChecker.run();
	}
};

ZmHtmlEditor.prototype._resetFormatControls =
function() {

	this._resetFormatControlDefaults();

	setTimeout(AjxCallback.simpleClosure(this._loadExternalStyle, this, "/css/editor.css"), 250);
	setTimeout(AjxCallback.simpleClosure(this._setFontStyles, this), 250);
};

ZmHtmlEditor.prototype._resetFormatControlDefaults =
function() {
	this._fontFamilyButton && this._fontFamilyButton.setText(DwtHtmlEditor._normalizeFontName(appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_FAMILY)));
	this._fontSizeButton && this._fontSizeButton.setText(this._getFontSizeLabel(appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_SIZE)));
	this._fontColorButton && this._fontColorButton.setColor(appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_COLOR));
	this._styleMenu && this._styleMenu.checkItem(ZmHtmlEditor._VALUE, DwtHtmlEditor.PARAGRAPH, true);
	this._justifyMenu && this._justifyMenu.checkItem(ZmHtmlEditor._VALUE, DwtHtmlEditor.JUSTIFY_LEFT, true);
};

ZmHtmlEditor.prototype._loadExternalStyle =
function(path) {
	var doc = this._getIframeDoc();
	// check if already loaded
	var style = doc.getElementById(path);
	if (!style) {
		style = doc.createElement("link");
		style.id = path;
		style.rel = "stylesheet";
		style.type = "text/css";
		var style_url = appContextPath + path + "?v=" + cacheKillerVersion;
		if (AjxEnv.isGeckoBased || AjxEnv.isSafari) {
			style_url = document.baseURI.replace(
					/^(https?:\x2f\x2f[^\x2f]+).*$/, "$1") + style_url;
		}
		// servlet caches CSS unless there's a debug param
		var debugLevel = DBG && DBG.getDebugLevel();
		if (debugLevel) {
			style_url = style_url + "&debug=" + debugLevel;
		}
		style.href = style_url;
		var head = doc.getElementsByTagName("head")[0];
		if (!head) {
			head = doc.createElement("head");
			var docEl = doc.documentElement;
			if (docEl) {
				docEl.insertBefore(head, docEl.firstChild);
			}
		}
		head.appendChild(style);
	}
};

ZmHtmlEditor.prototype._setFontStyles =
function() {
	var doc = this._getIframeDoc();
	var style = doc.body && doc.body.style;

	if (style) {
		style.fontFamily = DwtHtmlEditor._normalizeFontValue(appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_FAMILY));
		style.fontSize = appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_SIZE);
		style.color = appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_COLOR);
	}
};

ZmHtmlEditor.prototype.highlightMisspelledWords =
function(words, keepModeDiv) {
	this.discardMisspelledWords(keepModeDiv);

	var word, style, doc, body, self = this,
		spanIds     = {},
		wordIds     = {},
		regexp      = [ "([^A-Za-z0-9']|^)(" ],
		suggestions = {};

	// preparations: initialize some variables that we then save in
	// this._spellCheck (the current spell checker context).
	for (var i = 0; i < words.length; ++i) {
		word = words[i].word;
		if (!suggestions[word]) {
			i && regexp.push("|");
			regexp.push(word);
			var a = words[i].suggestions.split(/\s*,\s*/);
			if (!a[a.length-1])
				a.pop();
			suggestions[word] = a;
			if (suggestions[word].length > 5)
				suggestions[word].length = 5;
		}
	}
	regexp.push(")([^A-Za-z0-9']|$)");
	regexp = new RegExp(regexp.join(""), "gm");

	function hiliteWords(text, textWhiteSpace) {
		text = textWhiteSpace
			? AjxStringUtil.convertToHtml(text)
			: AjxStringUtil.htmlEncode(text);

		var m;

		regexp.lastIndex = 0;
		while (m = regexp.exec(text)) {
			var str = m[0];
			var prefix = m[1];
			var word = m[2];
			var suffix = m[3];

			var id = Dwt.getNextId();
			spanIds[id] = word;
			if (!wordIds[word])
				wordIds[word] = [];
			wordIds[word].push(id);

			var repl = [
				prefix,
				'<span word="',
				word, '" id="', id, '" class="ZM-SPELLCHECK-MISSPELLED">',
				word, '</span>',
				suffix
				].join("");
			text = [
				text.substr(0, m.index),
				repl,
				text.substr(m.index + str.length)
			].join("");

			// All this crap necessary because the suffix
			// must be taken into account at the next
			// match and JS regexps don't have look-ahead
			// constructs (except \b, which sucks).  Oh well.
			regexp.lastIndex = m.index + repl.length - suffix.length;
		}
		return text;
	};

	var doc;

	// having the data, this function will parse the DOM and replace
	// occurrences of the misspelled words with <span
	// class="ZM-SPELLCHECK-MISSPELLED">word</span>
	rec = function(node) {
		switch (node.nodeType) {
			case 1: /* ELEMENT */
				for (var i = node.firstChild; i; i = rec(i));
				node = node.nextSibling;
				break;
			case 3: /* TEXT */
				if (!/[^\s\xA0]/.test(node.data)) {
					node = node.nextSibling;
					break;
				}
				// for correct handling of whitespace we should
				// not mess ourselves with leading/trailing
				// whitespace, thus we save it in 2 text nodes.
				var a = null, b = null;

				var result = /^[\s\xA0]+/.exec(node.data);
				if (result) {
					// a will contain the leading space
					a = node;
					node = node.splitText(result[0].length);
				}
				result = /[\s\xA0]+$/.exec(node.data);
				if (result) {
					// and b will contain the trailing space
					b = node.splitText(node.data.length - result[0].length);
				}

				var text = hiliteWords(node.data, false);
				text = text.replace(/^ +/, "&nbsp;").replace(/ +$/, "&nbsp;");
				var div = doc.createElement("div");
				div.innerHTML = text;

				// restore whitespace now
				if (a) {
					div.insertBefore(a, div.firstChild);
				}
				if (b) {
					div.appendChild(b);
				}

				var p = node.parentNode;
				while (div.firstChild) {
					p.insertBefore(div.firstChild, node);
				}
				div = node.nextSibling;
				p.removeChild(node);
				node = div;
				break;
			default :
				node = node.nextSibling;
		}
		return node;
	};

	if (this._mode == DwtHtmlEditor.HTML) {
		// HTML mode; See the "else" branch for the TEXT mode--code differs
		// quite a lot.  We should probably implement separate functions as
		// this already becomes long.

		doc = this._getIframeDoc();
		body = doc.body;

		// load the spell check styles, if not already there.
		this._loadExternalStyle("/css/spellcheck.css");

		body.style.display = "none";	// seems to have a good impact on speed,
										// since we may modify a lot of the DOM
		if (!AjxEnv.isIE) {
			body.normalize();
		} else {
			body.innerHTML = body.innerHTML;
		}
		rec(body);
		if (!AjxEnv.isIE) {
			body.normalize();
		} else {
			body.innerHTML = body.innerHTML;
		}
		body.style.display = ""; // redisplay the body
		this._registerEditorEventHandler(doc, "contextmenu");
	}
	else { // TEXT mode
		var textArea = document.getElementById(this._textAreaId);
		var scrollTop = textArea.scrollTop;
		var size = Dwt.getSize(textArea);
		textArea.style.display = "none";
		var div = document.createElement("div");
		div.className = "TextSpellChecker";
		this._spellCheckDivId = div.id = Dwt.getNextId();
		div.style.overflow = "auto";
		if (!AjxEnv.isIE) {
			// FIXME: we substract borders/padding here.  this sucks.
			size.x -= 4;
			size.y -= 6;
		}
		div.style.width = size.x + "px";
		div.style.height = size.y + "px";

		div.innerHTML = AjxStringUtil.convertToHtml(this.getContent());
		doc = document;
		rec(div);

		textArea.parentNode.insertBefore(div, textArea);
		div.scrollTop = scrollTop;
		div.oncontextmenu = div.onclick
			= function(ev) { self._handleSpellCheckerEvents(ev || window.event); };
	}

	this._spellCheckShowModeDiv();

	// save the spell checker context
	this._spellCheck = {
		suggestions: suggestions,
		spanIds: spanIds,
		wordIds: wordIds
	};
};

ZmHtmlEditor.prototype.setSize =
function(x, y) {
	var div = this._spellCheckDivId && document.getElementById(this._spellCheckDivId);
	var main = document.getElementById(this.getBodyFieldId());

	// FUDGE: we must substract borders and paddings - yuck.
	var delta = this._mode == DwtHtmlEditor.HTML ? 8 : 10;


	if (x == Dwt.CLEAR) {
		main.style.width = null;
		if (div) div.style.width = null;
	} else if (x == Dwt.DEFAULT) {
		main.style.width = "auto";
		if (div) div.style.width = "auto";
	} else if (typeof(x) == "number") {
		x -= delta + 4;

		// bug fix #6786 - normalize width/height if less than zero
		if (x < 0) x = 0;

		main.style.width = x + 5 + "px";
		if (div) {
			if (!AjxEnv.isIE) {
				x = x > 4 ? (x-4) : x;
			}
			div.style.width = x + "px";
		}
	}


	if (y == Dwt.CLEAR) {
		main.style.height = null;
		if (div) div.style.height = null;
	} else if (y == Dwt.DEFAULT) {
		main.style.height = "auto";
		if (div) div.style.height = "auto";
	} else if (typeof(y) == "number") {

		// subtract spellchecker DIV if applicable
		if (this._spellCheckModeDivId) {
			var spellCheckDivHeight = document.getElementById(this._spellCheckModeDivId).offsetHeight;
			y -= (isNaN(spellCheckDivHeight) ? 0 : spellCheckDivHeight);
		}
		if (this._mode == DwtHtmlEditor.HTML && this._toolbars.length > 0) {
			for (var i = 0; i < this._toolbars.length; i++) {
				var toolbar = this._toolbars[i];
				y -= toolbar.getHtmlElement().offsetHeight;
			}
		}

		// subtract fudge factor
		y -= delta;
		if (y < 0) y = 0;

		main.style.height = y + "px";
		if (div) {
			if (!AjxEnv.isIE) {
				y = y > 4 ? (y-4) : y;
			} else {
				y += 2;
			}
			div.style.height = y + "px";
		}
	}
};


// Private / protected methods

ZmHtmlEditor.prototype._initialize =
function() {
	if (this._mode == DwtHtmlEditor.HTML) {
		this._createToolbars();
	}
	DwtHtmlEditor.prototype._initialize.call(this);
};

ZmHtmlEditor.prototype._styleListener =
function(ev) {
	this.setStyle(ev.item.getData(ZmHtmlEditor._VALUE));
};

ZmHtmlEditor.prototype._fontFamilyListener =
function(ev) {
	this.setFont(DwtHtmlEditor._normalizeFontValue(ev.item.getData(ZmHtmlEditor._VALUE)));
	//this._fontFamilyButton.setText(DwtHtmlEditor.FONT_FAMILY[id] && DwtHtmlEditor.FONT_FAMILY[id].name || ZmHtmlEditor.__makeFontName(id));
};

ZmHtmlEditor.prototype._fontSizeListener =
function(ev) {
	this.setFont(null, null, ev.item.getData(ZmHtmlEditor._VALUE));
};

ZmHtmlEditor.prototype._indentListener =
function(ev) {
	this.setIndent(ev.item.getData(ZmHtmlEditor._VALUE));
};

ZmHtmlEditor.prototype._insertLinkListener =
function() {
	var dlg = this._insertLinkDialog;
	if (!dlg) {
		dlg = this._insertLinkDialog = new DwtDialog({parent:DwtShell.getShell(window), title:ZmMsg.linkProperties});
		var id = dlg.base_id = Dwt.getNextId();
		var html = AjxTemplate.expand("share.Dialogs#EditorInsertLink", {id:id});
		dlg.setContent(html);

		dlg.linkText = new DwtInputField({ parent        : dlg,
										   size          : 40,
										   parentElement : id + "_linkTextCont" });

		dlg.linkTarget = new DwtInputField({ parent        : dlg,
											 size          : 40,
											 parentElement : id + "_linkTargetCont" });

		function getURL() {
				var url = dlg.linkTarget.getValue();
                if (url && !/^(https?|ftp|notes|cid):\x2f\x2f/i.test(url) && !/^mailto:([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/i.test(url)){
					url = "http://" + url;
					dlg.linkTarget.setValue(url);
				}
				return url;
		};

		var btn = new DwtButton({ parent: dlg, parentElement: id + "_testBtnCont" });
		btn.setText(ZmMsg.testUrl);
		btn.setToolTipContent(ZmMsg.testUrlTooltip);
		btn.addSelectionListener(new AjxListener(this, function() {
			var url = getURL();
			if (url) {
				window.open(url);
			}
		}));

		dlg._tabGroup.addMember(dlg.linkText, 0);
		dlg._tabGroup.addMember(dlg.linkTarget, 1);
		dlg._tabGroup.addMember(btn, 2);

		dlg.registerCallback(DwtDialog.OK_BUTTON, new AjxListener(this, function(){
			var url = getURL();
            var img = this.getNearestElement("img");
            var text = dlg.linkText.getValue() || url;
            if(img) {
                var text = dlg.linkText.getValue();
            }
			dlg.popdown();
			this.insertLink({ text : text, url  : url });
		}));
	}
	var link = this.getLinkProps();
	dlg.linkText.setValue(link.text || "");
	dlg.linkTarget.setValue(link.url || "");
	dlg.popup();
	if (/\S/.test(link.text)) {
		dlg.linkTarget.focus();
	} else {
		dlg.linkText.focus();
	}
};

ZmHtmlEditor.prototype._insElementListener =
function(ev) {
	this.insertElement(ev.item.getData(ZmHtmlEditor._VALUE));
};

ZmHtmlEditor.prototype._justificationListener =
function(ev) {
	this.setJustification(ev.item.getData(ZmHtmlEditor._VALUE));
};

ZmHtmlEditor.prototype._fontStyleListener =
function(ev) {
	this.setFont(null, ev.item.getData(ZmHtmlEditor._VALUE));
};

ZmHtmlEditor.prototype._fontColorListener =
function(ev) {
	this.setFont(null, null, null, ev.detail || "#000000");
};

ZmHtmlEditor.prototype._fontHiliteListener =
function(ev) {
	this.setFont(null, null, null, null, ev.detail || "#ffffff");
};

ZmHtmlEditor.prototype._createToolbars =
function(dontCreateButtons) {
	// NOTE: overload this method to place toolbars differently.
	if (!this._toolbar1) {
		var tb = this._toolbar1 = new DwtToolBar({parent:this, className:"ZToolbar",
												  posStyle:DwtControl.RELATIVE_STYLE, cellSpacing:2, index:0});
		tb.setVisible(this._mode == DwtHtmlEditor.HTML);

		// Default is to have ONE toolbar now
		if (!dontCreateButtons) {
			this._createToolBar1(tb);
			new DwtControl({parent:tb, className:"vertSep"});
			this._createToolBar2(tb);
			this._resetFormatControls();
		}

		this._toolbars.push(tb);
	}
};

ZmHtmlEditor.prototype._createToolBar1 =
function(tb) {
	this._createFontFamilyMenu(tb);
	this._createFontSizeMenu(tb);
	this._createStyleMenu(tb);
	this._createJustifyMenu(tb);
	new DwtControl({parent:tb, className:"vertSep"});
	this._createListMenu(tb);
	this._createIndentMenu(tb);
	new DwtControl({parent:tb, className:"vertSep"});
	this._createBUIButtons(tb);

	appCtxt.notifyZimlets("on_htmlEditor_createToolbar1", [this, tb]);
};

ZmHtmlEditor.prototype._createToolBar2 =
function(tb) {
	this._createFontColorButtons(tb);
	new DwtControl({parent:tb, className:"vertSep"});
	this._createHorizRuleButton(tb);
	this._createUrlButton(tb);
	this._createTableMenu(tb);
	if (this.ACE_ENABLED) {
		this._createSpreadSheetButton(tb);
	}

	appCtxt.notifyZimlets("on_htmlEditor_createToolbar2", [this, tb]);
};

ZmHtmlEditor.prototype.__createTableOperationItems =
function(menu) {
	var tblListener = new AjxListener(this, this._tableOperationsListener);
	var tblCommands = [ "tableProperties...", "cellProperties...", null,
			    "insertRowAbove", "insertRowUnder", "deleteRow", null,
			    "insertColumnBefore", "insertColumnAfter", "deleteColumn", null ];
	if (AjxEnv.isGeckoBased)
		tblCommands.push("mergeCells", "splitCells", null);
	tblCommands.push("deleteTable");
	var tblIcons = [ "TableProperties", "CellProperties", null,
			 "InsertRowBefore", "InsertRowAfter", "DeleteRow", null,
			 "InsertColBefore", "InsertColAfter", "DeleteCol", null,
			 "MergeCells", "SplitCells", null,
			 "DeleteTable" ];
	menu._tblItems = {};
	for (var i = 0; i < tblCommands.length; ++i) {
		var cmd = tblCommands[i];
		if (cmd == null)
			new DwtMenuItem({parent:menu, style:DwtMenuItem.SEPARATOR_STYLE});
		else {
			var dots = "";
			if (/\.\.\.$/.test(cmd)) {
				cmd = cmd.substr(0, cmd.length - 3);
				dots = "&hellip;";
			}
			var item = new DwtMenuItem({parent:menu});
			menu._tblItems[cmd] = item;
			var txt = ZmMsg[cmd] || cmd;
			item.setText(txt + dots);
			if (tblIcons[i])
				item.setImage(tblIcons[i]);
			item.setData("TableOperations", cmd);
			item.addSelectionListener(tblListener);
		}
	}
	menu.addPopupListener(new AjxListener(this, this.__onTableOperationsPopup));
};

ZmHtmlEditor.prototype.__onTableOperationsPopup =
function(menu) {
	this.focus();
	var table = this.getNearestElement("table");
	var items = menu._tblItems;
	for (var i in items) {
		items[i].setEnabled(!!table);
	}
	if (!table) { return; }

	menu.setData("table", table);

	if (!AjxEnv.isIE) {
		// Can we split? (the cell has to be a merged cell)
		var td = this.getNearestElement("td");
		var splitEnabled = td && ((td.colSpan && td.colSpan > 1) || (td.rowSpan && td.rowSpan > 1));
		if (items.splitCells) { //this is only if geckoBased
			items.splitCells.setEnabled(splitEnabled);
		}

		// Can we merge? (multiple cells are selected and none of them is previously merged)
		var a = this.getSelectedCells();
		var canMerge = true;
		var howMany = 0;
		try {
			for (var i = a.length; --i >= 0;) {
				var r = a[i];
				for (var j = r.length; --j >= 0;) {
					var td = r[j];
					++howMany;
					if (td.rowSpan > 1 || td.colSpan > 1) {
						throw "can't merge";
					}
				}
			}
		} catch(ex) {
			canMerge = false;
		}
		if (howMany < 2)
			canMerge = false;
		if (items.mergeCells) { //this is only if geckoBased
			items.mergeCells.setEnabled(canMerge);
		}
	}
};

ZmHtmlEditor.prototype._tableOperationsListener =
function(ev) {
	var item = ev.item;
	var table = item.parent.getData("table");
	var data = item.getData("TableOperations");
	this.focus();
	switch (data) {
		case "tableProperties":
			AjxDispatcher.require("Extras");
			var dlg = ZmTableEditor.getTablePropsDialog(this, this.getNearestElement("table"));
			dlg.popup();
		break;

		case "cellProperties":
			AjxDispatcher.require("Extras");
			var dlg = ZmTableEditor.getCellPropsDialog(this, this.getNearestElement("table"), this.getSelectedCells());
			dlg.popup();
			break;

		default:
			this.doTableOperation(data, { table: table, cells: this.getSelectedCells() });
	}
};

ZmHtmlEditor.prototype._createTableListener =
function(ev) {
	var size = ev.detail;
	this.insertTable(size.rows, size.cols, "90%", null, 3, "center");
};

ZmHtmlEditor.prototype._menu_insertObject =
function(ev){
	var item = ev.item;
	var data = item.getData("ACE");
	this.insertObject(data);
};

ZmHtmlEditor.prototype.insertObject =
function(name, target, data) {
	var toplevel_url = document.URL
		.replace(/^(https?:\x2f\x2f[^\x2f]+\x2f?).*$/i, "$1")
		.replace(/\x2f*$/, "");
	var component_url = null;

	// REVISIT: object factory needed when there'll be many components to chose from.
	switch (name) {
		case "ZmSpreadSheet":
			component_url = [toplevel_url, appContextPath, "/public/Spreadsheet.jsp?localeId=", AjxEnv.DEFAULT_LOCALE].join("");
			break;
	}

	if (component_url) {
		// var outer = this.getIframe();
		// outer.style.display = "none";
		var doc = this._getIframeDoc();
		this.focus();
		++this._ace_componentsLoading;
		if (AjxEnv.isGeckoBased) {
			Dwt.enableDesignMode(doc, false);
		}
		var ifr = doc.createElement("iframe");
		ifr.id = "ACE-" + Dwt.getNextId();
		ifr.frameBorder = 0;
		ifr.src = component_url;
		ifr.style.width = "100%";
		ifr.style.height = "400px";
		// Avoid bug 8523 in IE.
		ifr.ondragstart = AjxCallback.returnFalse;
		if (!target) {
			// embed it into 2 paragraphs to make it easy
			// to type text before or after the
			// spreadsheet
			var p = doc.createElement("br");
			var df = doc.createDocumentFragment();
			df.appendChild(p);
			df.appendChild(ifr);
			df.appendChild(p.cloneNode(true));
			this._insertNodeAtSelection(df);
		} else {
			target.parentNode.replaceChild(ifr, target);
		}
		var handler = AjxCallback.simpleClosure(this._ace_finishedLoading, this, ifr, name, data);
		if (AjxEnv.isIE) {
			ifr.onreadystatechange = handler;
		} else {
			ifr.addEventListener("load", handler, true);
		}
	}
};

ZmHtmlEditor.prototype._ace_finishedLoading = function(ifr, name, data) {
	// We have to delay execution (bug 12870).  Seems to affect
	// Firefox only.  10ms should be quite enough.
	var self = this;
	if (!AjxEnv.isIE || ifr.readyState == "complete") {
		setTimeout(function() {
			try {
				var win = Dwt.getIframeWindow(ifr);
				win.ZmACE = true;
				win.ZmACE_COMPONENT_NAME = name;
				ifr.onload = null;
				ifr.onreadystatechange = null;
				win.create(data);
				--self._ace_componentsLoading;
			} catch(ex) {
				--self._ace_componentsLoading;
				// throw new DwtException("Can't deserialize ALE component", DwtException.INTERNAL_ERROR, ex);
				var dlg = appCtxt.getErrorDialog();
				dlg.setMessage(ZmMsg.aleError, ex.msg || ex.toString(), DwtMessageDialog.WARNING_STYLE, "ALE error");
				dlg.popup(null, true);
			}
		}, 10);
	}
};

// Returns an array of embedded objects (each one is a reference to its containing IFRAME)
ZmHtmlEditor.prototype._getAceObjects =
function() {
	var tmp = this._getIframeDoc().getElementsByTagName("iframe");
	var a = new Array(tmp.length);
	for (var i = tmp.length; --i >= 0;) {
		a[i] = tmp[i];
	}
	return a;
};

ZmHtmlEditor.prototype._embedHtmlContent =
function(html, insertFontStyle, onlyInnerContent) {
	if (!insertFontStyle && !onlyInnerContent) {
		if (!(this.ACE_ENABLED && this._headContent))
			return DwtHtmlEditor.prototype._embedHtmlContent.call(this, html);
	}

	if (insertFontStyle) {
		html = this._getFontStyle(html);
	}

	if (onlyInnerContent) {
		return html;
	}

	var headContent = this._headContent ? this._headContent.join("") : "";

	var p_style = "<style type='text/css'>p { margin: 0; }</style>"; // bug 3264

	return [
		"<html><head>",
		p_style, headContent,
		"</head><body>",
		html,
		"</body></html>"
	].join("");
};

ZmHtmlEditor.prototype._getFontStyle =
function(html) {
	var a = [], i = 0;
	a[i++] = "<div style='font-family: ";
	a[i++] = DwtHtmlEditor._normalizeFontValue(appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_FAMILY));
	a[i++] = "; font-size: ";
	a[i++] = appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_SIZE);
	a[i++] = "; color: ";
	a[i++] = appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_COLOR);
	a[i++] = "'>";
	a[i++] = html;
	a[i++] = "</div>";
	return a.join("");
};

ZmHtmlEditor.prototype._serializeAceObjects =
function(content) {
	this._headContent = [];
	var done = {};
	var replaceCallback = AjxCallback.simpleClosure(this._replaceAceIframes, this, done);
	return content.replace(ZmHtmlEditor.ACE_IFRAME_RE, replaceCallback);
};

ZmHtmlEditor.prototype._replaceAceIframes =
function(done, match, iframeId) {
	var iframe = this._getIframeDoc().getElementById(iframeId);
	var win = Dwt.getIframeWindow(iframe);
	var html = win.getHTML();
	var data = win.serialize()
		.replace(/&/g, "&amp;")
		.replace(/>/g, "&gt;");
	var component_name = win.ZmACE_COMPONENT_NAME;
	if (!done[component_name] && typeof win.getHeadHTML == "function") {
		done[component_name] = true;
		this._headContent.push(win.getHeadHTML());
	}
	return [
		"<div class=\"ACE ",
		component_name,
		"\">",
		html,
		"<!--",
		"ACE[",
		component_name,
		"]:",
		data,
		"-->",
		"</div>"
	].join("");
};

ZmHtmlEditor.prototype._deserializeAceObjects =
function() {
	var divs = this._getIframeDoc().getElementsByTagName("div");
	var tmp = new Array(divs.length);
	for (var i = 0; i < divs.length; ++i) {
		tmp[i] = divs.item(i);
	}
	divs = tmp;
	for (var i = 0; i < divs.length; ++i) {
		var holder = divs[i];
		if (/^ACE\s+([^\s]+)/.test(holder.className)) {
			var component_name = RegExp.$1;
			var data = holder.lastChild;
			if (data.nodeType == 8 /* Node.COMMENT_NODE */) {
				data = data.data;
				var header = "ACE[" + component_name + "]:";
				if (data.indexOf(header) == 0) {
					data = data.substr(header.length)
						.replace(/&gt;/g, ">")
						.replace(/&amp;/g, "&");
					this.insertObject(component_name, holder, data);
				}
			}
		}
	}
};

//Editor Buttons

ZmHtmlEditor.prototype._createBUIButtons =
function(tb) {
	var params = {parent:tb, style:DwtButton.TOGGLE_STYLE};
	var listener = new AjxListener(this, this._fontStyleListener);
	this._boldButton = new DwtToolBarButton(params);
	this._boldButton.setImage("Bold");
	this._boldButton.setToolTipContent(appCtxt.getShortcutHint("editor", DwtKeyMap.TEXT_BOLD));
	this._boldButton.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.BOLD_STYLE);
	this._boldButton.addSelectionListener(listener);

	this._italicButton = new DwtToolBarButton(params);
	this._italicButton.setImage("Italics");
	this._italicButton.setToolTipContent(appCtxt.getShortcutHint("editor", DwtKeyMap.TEXT_ITALIC));
	this._italicButton.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.ITALIC_STYLE);
	this._italicButton.addSelectionListener(listener);

	this._underlineButton = new DwtToolBarButton(params);
	this._underlineButton.setImage("Underline");
	this._underlineButton.setToolTipContent(appCtxt.getShortcutHint("editor", DwtKeyMap.TEXT_UNDERLINE));
	this._underlineButton.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.UNDERLINE_STYLE);
	this._underlineButton.addSelectionListener(listener);

};

ZmHtmlEditor.prototype._createFontColorButtons =
function(tb) {
	this._fontColorButton = new ZmHtmlEditorColorPicker(tb,null,"ZToolbarButton");
	this._fontColorButton.dontStealFocus();
	this._fontColorButton.setImage("FontColor");
	this._fontColorButton.showColorDisplay(true);
	this._fontColorButton.setToolTipContent(ZmMsg.fontColor);
	this._fontColorButton.addSelectionListener(new AjxListener(this, this._fontColorListener));

	this._fontBackgroundButton = new ZmHtmlEditorColorPicker(tb, null, "ZToolbarButton");
	this._fontBackgroundButton.dontStealFocus();
	this._fontBackgroundButton.setImage("FontBackground");
	this._fontBackgroundButton.showColorDisplay(true);
	this._fontBackgroundButton.setToolTipContent(ZmMsg.fontBackground);
	this._fontBackgroundButton.addSelectionListener(new AjxListener(this, this._fontHiliteListener));
};

ZmHtmlEditor.prototype._createHorizRuleButton =
function(tb) {
	var params = {parent:tb};
	this._horizRuleButton = new DwtToolBarButton(params);
	this._horizRuleButton.setImage("HorizRule");
	this._horizRuleButton.setToolTipContent(ZmMsg.horizRule);
	this._horizRuleButton.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.HORIZ_RULE);
	this._horizRuleButton.addSelectionListener(new AjxListener(this, this._insElementListener));
};

ZmHtmlEditor.prototype._createUrlButton =
function(tb) {
	var params = {parent:tb};
	this._insertLinkButton = new DwtToolBarButton(params);
	this._insertLinkButton.setImage("URL");
	this._insertLinkButton.setToolTipContent(ZmMsg.insertLink);
	this._insertLinkButton.addSelectionListener(new AjxListener(this, this._insertLinkListener));
};

ZmHtmlEditor.prototype._createTableMenu =
function(tb) {
	var params = {parent:tb};
	// BEGIN: Table operations
	var b = this._tableMenu = new DwtToolBarButton(params);
	b.setToolTipContent(ZmMsg.insertTable);
	b.dontStealFocus();
	b.setImage("Table");
	var menu = new DwtMenu({parent:b});
	b.setMenu(menu);

	var item = new DwtMenuItem({parent:menu});
	item.setText(ZmMsg.insertTable);
	var grid_menu = new DwtMenu({parent:item, style:DwtMenu.GENERIC_WIDGET_STYLE});
	var grid = new DwtGridSizePicker(grid_menu, ZmMsg.tableSize);
	grid.addSelectionListener(new AjxListener(this, this._createTableListener));
	item.setMenu(grid_menu);
	item.setImage("InsertTable");

	new DwtMenuItem({parent:menu, style:DwtMenuItem.SEPARATOR_STYLE});
	this.__createTableOperationItems(menu);
	// END: table operations
};

ZmHtmlEditor.prototype._createSpreadSheetButton =
function(tb) {
	new DwtControl({parent:tb, className:"vertSep"});

	var params = {parent:tb, style:0};
	var b = new DwtToolBarButton(params);
	b.setImage("SpreadSheet");
	b.setData("ACE", "ZmSpreadSheet");
	b.setToolTipContent(ZmMsg.insertSpreadsheet);
	b.addSelectionListener(new AjxListener(this, this._menu_insertObject));
};

ZmHtmlEditor.prototype._createStyleMenu =
function(tb) {
	var s = new DwtToolBarButton({parent:tb});
	// minor hack to set section symbol - avoids d/l'ing an icon :]
	s.setText("x");
	s._textEl.innerHTML = "<span style='font-size:13px'>&sect;</span>";
	s.setToolTipContent(ZmMsg.sections);
	s.dontStealFocus();
	var menu = this._styleMenu = new ZmPopupMenu(s);
	var listener = new AjxListener(this, this._styleListener);
	var menuItems = [
		{ label:ZmMsg.normal,		id:DwtHtmlEditor.PARAGRAPH},
		{ label:ZmMsg.heading1, 	id:DwtHtmlEditor.H1},
		{ label:ZmMsg.heading2, 	id:DwtHtmlEditor.H2},
		{ label:ZmMsg.heading3, 	id:DwtHtmlEditor.H3},
		{ label:ZmMsg.heading4, 	id:DwtHtmlEditor.H4},
		{ label:ZmMsg.heading5, 	id:DwtHtmlEditor.H5},
		{ label:ZmMsg.heading6, 	id:DwtHtmlEditor.H6},
		{ label:ZmMsg.address,		id:DwtHtmlEditor.ADDRESS},
		{ label:ZmMsg.preformatted,	id:DwtHtmlEditor.PREFORMATTED} ];

	for (var i = 0; i < menuItems.length; i++) {
		var item = menuItems[i];
		var mi = menu.createMenuItem(item.id, {text:item.label, style:DwtMenuItem.RADIO_STYLE});
		mi.addSelectionListener(listener);
		mi.setData(ZmHtmlEditor._VALUE, item.id);
		if (i == 0)
			mi.setChecked(true, true);
	}

	s.setMenu(menu);
};

ZmHtmlEditor.prototype._createListMenu =
function(tb) {
	var b = this._listMenuButton = new DwtToolBarButton({parent:tb});
	b.dontStealFocus();
	b.setImage("BulletedList");
	b.setToolTipContent(ZmMsg.bulletedList);

	var listListener = new AjxListener(this, this._insElementListener);
	var menu = this._listMenu = new ZmPopupMenu(b);
	var bulletListMenu = this._bulletListMenuItem = menu.createMenuItem(DwtHtmlEditor.UNORDERED_LIST, {image:"BulletedList", style:DwtMenuItem.SELECT_STYLE});
	bulletListMenu.addSelectionListener(listListener);
	bulletListMenu.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.UNORDERED_LIST);

	var numberListMenu = this._numberListMenuItem = menu.createMenuItem(DwtHtmlEditor.ORDERED_LIST, {image:"NumberedList", style:DwtMenuItem.SELECT_STYLE});
	numberListMenu.addSelectionListener(listListener);
	numberListMenu.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.ORDERED_LIST);

	b.setMenu(menu);
};

ZmHtmlEditor.prototype._createIndentMenu =
function(tb) {
	var b = this._indentMenuButton = new DwtToolBarButton({parent:tb});
	b.dontStealFocus();
	b.setImage("Outdent");
	b.setToolTipContent(ZmMsg.indentTooltip);

	var listener = new AjxListener(this, this._indentListener);
	var menu = this._indentMenu = new ZmPopupMenu(b);

	var indentMenu = this._indentMenuItem = menu.createMenuItem(DwtHtmlEditor.OUTDENT, {image:"Outdent", style:DwtMenuItem.SELECT_STYLE});
	indentMenu.addSelectionListener(listener);
	indentMenu.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.OUTDENT);

	var outdentMenu = this._outdentMenuItem = menu.createMenuItem(DwtHtmlEditor.INDENT, {image:"Indent", style:DwtMenuItem.SELECT_STYLE});
	outdentMenu.addSelectionListener(listener);
	outdentMenu.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.INDENT);

	b.setMenu(menu);
};

ZmHtmlEditor.prototype._createJustifyMenu =
function(tb) {
	var b = new DwtToolBarButton({parent:tb});
	b.dontStealFocus();
	b.setImage("LeftJustify");
	b.setToolTipContent(ZmMsg.alignment);
	var menu = this._justifyMenu = new ZmPopupMenu(b);
	var listener = new AjxListener(this, this._justificationListener);
	var menuItems = [
		{ image:"LeftJustify",		id:DwtHtmlEditor.JUSTIFY_LEFT},
		{ image:"CenterJustify",	id:DwtHtmlEditor.JUSTIFY_CENTER},
		{ image:"RightJustify",		id:DwtHtmlEditor.JUSTIFY_RIGHT},
		{ image:"FullJustify",		id:DwtHtmlEditor.JUSTIFY_FULL} ];

	for (var i = 0; i < menuItems.length; i++) {
		var item = menuItems[i];
		var mi = menu.createMenuItem(item.id, {image:item.image, style:DwtMenuItem.RADIO_STYLE});
		mi.addSelectionListener(listener);
		mi.setData(ZmHtmlEditor._VALUE, item.id);
		if (i == 0) {
			mi.setChecked(true, true);
		}
	}

	b.setMenu(menu, false, DwtMenuItem.RADIO_STYLE);
};

ZmHtmlEditor.prototype._createFontFamilyMenu =
function(tb) {
	this._fontFamilyButton = new DwtToolBarButton({parent:tb});
	this._fontFamilyButton.dontStealFocus();
	this._fontFamilyButton.setSize(Dwt.DEFAULT);
	this._fontFamilyButton.setAlign(DwtLabel.ALIGN_LEFT);
	var menu = new ZmPopupMenu(this._fontFamilyButton);
	var listener = new AjxListener(this, this._fontFamilyListener);

	for (var id in DwtHtmlEditor.FONT_FAMILY) {
		var name = DwtHtmlEditor.FONT_FAMILY[id] && DwtHtmlEditor.FONT_FAMILY[id].name || ZmHtmlEditor.__makeFontName(id);
		var mi = menu.createMenuItem(name, {text:name});
		mi.addSelectionListener(listener);
		mi.setData(ZmHtmlEditor._VALUE, DwtHtmlEditor.FONT_FAMILY[id] && DwtHtmlEditor.FONT_FAMILY[id].value || ZmHtmlEditor.__makeFontName(id));
	}

	this._fontFamilyButton.setMenu(menu);
};

ZmHtmlEditor.prototype._createFontSizeMenu =
function(tb) {
	this._fontSizeButton = new DwtToolBarButton({parent:tb});
	this._fontSizeButton.dontStealFocus();
	var menu = new ZmPopupMenu(this._fontSizeButton);
	var listener = new AjxListener(this, this._fontSizeListener);

	for (var i = 0; i < ZmHtmlEditor.FONT_SIZE_VALUES.length; i++) {
		var item = ZmHtmlEditor.FONT_SIZE_VALUES[i];
		var num = i+1;
		var text = num + " (" + item + ")";
		var mi = menu.createMenuItem(i, {text:text});
		mi.addSelectionListener(listener);
		mi.setData(ZmHtmlEditor._VALUE, num);
	}

	this._fontSizeButton.setMenu(menu);
};

ZmHtmlEditor.prototype._getFontSizeLabel =
function(fontSize) {
	for (var i = 0; i < ZmHtmlEditor.FONT_SIZE_VALUES.length; i++) {
		var item = ZmHtmlEditor.FONT_SIZE_VALUES[i];
		if (fontSize == item) {
			return ((i+1) + " (" + item + ")");
		}
	}
	// return "12pt" by default (yuck)
	return "3 (12pt)";
};

ZmHtmlEditor.prototype._rteStateChangeListener =
function(ev) {
	this._boldButton.setSelected(ev.isBold);
	this._underlineButton.setSelected(ev.isUnderline);
	this._italicButton.setSelected(ev.isItalic);
	if (this._strikeThruButton) {
		this._strikeThruButton.setSelected(ev.isStrikeThru);
	}
	if (this._subscriptButton) {
		this._subscriptButton.setSelected(ev.isSubscript);
	}
	if (this._superscriptButton) {
		this._superscriptButton.setSelected(ev.isSuperscript);
	}
	if (ev.color) {
		this._fontColorButton.setColor(ev.color);
	}
	if (ev.backgroundColor) {
		this._fontBackgroundButton.setColor(ev.backgroundColor);
	}
	if (ev.style) {
		this._styleMenu.checkItem(ZmHtmlEditor._VALUE, ev.style, true);
	}

	if (!AjxEnv.isIE) {
		// Bug 20171
		// For reasons not known to humanity, the following code resets the undo stack in IE.
		// It seems to have something to do with modifying the DOM.  The setText() calls use
		// innerHTML, but it's not about innerHTML, since I tried using DOM methods as well
		// to modify the text (createTextNode/removeChild/appendChild).  Nothing works.
		// I therefore disable this code for IE, trusting it's better to have working undo
		// and an un-updated toolbar, rather than the other way around.

		if (ev.fontFamily) {
			this._fontFamilyButton.setText(DwtHtmlEditor._normalizeFontName(ev.fontFamily));
		}

		if (ev.fontSize) {
			var mi = this._fontSizeButton.getMenu().getItem(parseInt(ev.fontSize)-1);
			if (mi)
				this._fontSizeButton.setText(mi.getText());
		}
	}

	this._justifyMenu.checkItem(ZmHtmlEditor._VALUE, ev.justification, true);
};

ZmHtmlEditor.prototype._settingChangeListener =
function(ev) {
	if (ev.type != ZmEvent.S_SETTING) { return; }

	var id = ev.source.id;
	if (id == ZmSetting.COMPOSE_INIT_FONT_COLOR ||
		id == ZmSetting.COMPOSE_INIT_FONT_FAMILY ||
		id == ZmSetting.COMPOSE_INIT_FONT_SIZE)
	{
		this._resetFormatControlDefaults();
		this._fontStyle = null;
	}
};

ZmHtmlEditor.prototype._handleEditorEvent =
function(ev) {
	var rv = this._eventCallback ? this._eventCallback.run(ev) : true;
	if (rv) {
		rv = DwtHtmlEditor.prototype._handleEditorEvent.call(this, ev);
	}

	if (this._TIMER_spell) {
		clearTimeout(this._TIMER_spell);
	}
	var self = this;
	if (this._spellCheck) {
		var dw;
		// This probably sucks.
		if (/mouse|context|click|select/i.test(ev.type)) {
			dw = new DwtMouseEvent(true);
		} else {
			dw = new DwtUiEvent(true);
		}
		dw.setFromDhtmlEvent(ev);
		this._TIMER_spell = setTimeout(function() {
			self._handleSpellCheckerEvents(dw);
			this._TIMER_spell = null;
		}, 100);
	}

	if (/keydown/i.test(ev.type)) {
		if (ev.keyCode==13) { // Enter
			var element = this._getParentElement();
			var ancestor = element;

			while (ancestor) {
				var tagname = Dwt.getAttr(ancestor, "tagName");
				if (tagname && tagname.toLowerCase() === "blockquote")
					break;
				ancestor = ancestor.parentNode;
			}

			if (ancestor) {
				this._splitBlockquote(ancestor, element);
				rv = false;
			}
		}

		if (ev.keyCode==8 || ev.keyCode==46) { // Backspace or delete
			setTimeout(AjxCallback.simpleClosure(function() { // Waiting for the other event handlers (including the browser's own) to finish tends to clean up the DOM for us
				var blockquote1, blockquote2;
				for (var child=this._getIframeDoc().body.firstChild; child && child.nextSibling; child=child.nextSibling) {
					var child2 = child.nextSibling;
					if (child2 && child2.tagName && child2.tagName.toLowerCase()=="p" && this._elementIsIEFiller(child2.firstChild))
						child2 = child2.nextSibling;

					if (child2 && child.tagName && child2.tagName && child.tagName.toLowerCase()=="blockquote" && child2.tagName.toLowerCase()=="blockquote") {
						this._mergeBlockquotes(child, child2);
					}
				}
			}, this), 5);
			if (ev.keyCode==46) {
				if (!AjxEnv.isIE) {
					var range = this._getRange();
					var el = range.startContainer.childNodes[range.startOffset];
					if (el && el.tagName && el.tagName.toLowerCase()=="br") {
						this._removeElement(el);
                        if (ev.preventDefault)
                                ev.preventDefault();
                        rv=false;
					}
				}
			}

		}
	}

	return rv;
};

ZmHtmlEditor.prototype._splitBlockquote =
function(blockquote, element) {
	var range, el, offset=null, coffset=null;
	var text1,text2;

	// Get range, el, text and offset
	if (AjxEnv.isIE) {
		var iFrameDoc = this._getIframeDoc();
		range = iFrameDoc.selection.createRange();
		range.collapse(false);
		el = element;

		// IE doesn't let us get the offset directly, so we count the number of times we can use moveStart() until we're out of the containing element
		var dummy = "###"+Dwt.getNextId()+"###";
		range.pasteHTML(dummy);
		offset = el.innerHTML.indexOf(dummy);
		el.innerHTML = el.innerHTML.replace(dummy,"");
		text1 = offset ? el.innerHTML.substring(0, offset) : ""; // Extract text before and after breakpoint
		text2 = el.innerHTML.substring(offset);

	} else {
		range = this._getRange();
		range.collapse(false);
		var type = range.startContainer.nodeType;
		if (type==3 || type==4 || type==8) {
			offset = range.startOffset;
			el = range.startContainer.parentNode;
			text1 = offset ? range.startContainer.textContent.substring(0, offset) : ""; // Extract text before and after breakpoint
			text2 = range.startContainer.textContent.substring(offset);
			var _el = range.startContainer.previousSibling;
			while (_el) {
				text1 = this._getElementHTML(_el) + text1; // offset is relative to local TextNode, but we need to use the entire text of the surrounding element
				_el = _el.previousSibling; // so we extract the text from all siblings and put it together
			}
			_el = range.startContainer.nextSibling;
			while (_el) {
				text2 = text2 + this._getElementHTML(_el); // Same for text after the breakpoint
				_el = _el.nextSibling;
			}
		} else {
			coffset = range.startOffset; // Breakpoint is not inside a TextNode, store the element offset instead
			el = range.startContainer;
		}
	}


	var id = el.id = el.id || Dwt.getNextId();
	var blockquote2 = blockquote.cloneNode(true); // Create an orphaned clone of the blockquote. This will be meddled with before getting attached to the DOM tree
	var el1 = el;
	var el2 = Dwt.byId(id, blockquote2); // Can't use document.getElementById on orphaned trees
	el2.removeAttribute("id");

	if (offset!==null) {
		el1.innerHTML = text1; // Insert the text we extracted earlier
		el2.innerHTML = text2;
	} else if (coffset!==null) {
		this._removeNextSiblings(el1.childNodes[coffset]); // cut away all siblings after breakpoint for el1
		var c2 = el2.childNodes[coffset];
		this._removePreviousSiblings(c2); // and all sibling before breakpoint for el2
		this._removeElement(c2);
	}

	// Prune off all "later" siblings in the blockquote tree
	while (el1 != blockquote) {
		this._removeNextSiblings(el1);
		el1 = el1.parentNode;
	}

	// Prune off all "prior" siblings in the blockquote2 tree
	while (el2 != blockquote2) {
		this._removePreviousSiblings(el2);
		el2 = el2.parentNode;
	}

	// Now we've effectively cut the original blockquote in half, with the second half present in blockquote2
	if (blockquote.nextSibling) {
		blockquote.parentNode.insertBefore(blockquote2, blockquote.nextSibling);
	} else {
		blockquote.parentNode.appendChild(blockquote2);
	}

	if (AjxEnv.isIE) {
		// Hack to get IE to properly place the cursor between the two blockquotes
		var p = document.createElement("p");
		var span1 = document.createElement("span");
		var span2 = document.createElement("span");
		var br = document.createElement("br");
		span1.appendChild(span2);
		span1.appendChild(br);
		p.appendChild(span1);
		blockquote2.parentNode.insertBefore(p, blockquote2);
		this._setCursor(span2);
		this._setIEFiller(span1); // We need to remove this element when we want to reconnect the blockquotes, so give it something we can find again
		span2.parentNode.removeChild(span2);
	} else {
		if (AjxEnv.isSafari) {
			var t = this;
			setTimeout(function(){
				var p;
				if (blockquote.lastChild && blockquote.lastChild.childElementCount==1 && blockquote.lastChild.firstChild instanceof HTMLBRElement)
					p = blockquote.lastChild;
				else if (blockquote2.firstChild && blockquote2.firstChild.childElementCount==1 && blockquote2.firstChild.firstChild instanceof HTMLBRElement)
					p = blockquote2.firstChild;
				if (p) {
					blockquote.parentNode.insertBefore(p, blockquote2);
					range.setStart(p,0);
					var sel = t._getSelection();
					sel.removeAllRanges();
					sel.addRange(range);
				}
			},5);
		}
		range.setStartAfter(blockquote); // Set the breakpoint between the blockquotes (which is immediately after the first one, duh)
		var sel = this._getSelection();
		sel.removeAllRanges();
		sel.addRange(range);
	}
};

// Basically yields the same as el.outerHTML, but for all browsers
ZmHtmlEditor.prototype._getElementHTML = function(el) {
	if (AjxEnv.isIE) return el.outerHTML;
	var parent = document.createElement(el.parentNode && el.parentNode.tagName || "span");
	parent.appendChild(el.cloneNode(true));
	return parent.innerHTML;
};

// Removes the element from its parent
ZmHtmlEditor.prototype._removeElement = function(el) {
	if (el && el.parentNode) {
		el.parentNode.removeChild(el);
	}
};

// Removes all sibling elements coming before el
ZmHtmlEditor.prototype._removePreviousSiblings = function(el) {
	if (el && el.parentNode) {
		while (el.previousSibling) {
			this._removeElement(el.previousSibling);
		}
	}
};
// Removes all sibling elements coming after el
ZmHtmlEditor.prototype._removeNextSiblings = function(el) {
	if (el && el.parentNode) {
		while (el.nextSibling) {
			this._removeElement(el.nextSibling);
		}
	}
};

ZmHtmlEditor.prototype._selectNode = function(el) {
	var set;
	var range = this._getRange();
	while (!set && el) {
		try {
			range.setStartBefore(el);
			set = true;
		} catch (ex) {
			el = el.parentNode;
			set = false;
		}
	}
};

ZmHtmlEditor.prototype._nextElement = function(el) {
	if (el.childNodes && el.childNodes.length)
		return el.childNodes[0];
	if (el.nextSibling)
		return el.nextSibling;
	var p = el.parentNode;
	while (!el.nextSibling) {
		el = el.parentNode;
		if (!el) return null;
	}
	return el.nextSibling;
}

ZmHtmlEditor.prototype._elementIsIEFiller =
function(el) {
	if (el && el.attributes) {
		for (var i=0; i<el.attributes.length; i++) {
			if (el.attributes[i].name=="_ieFiller") {
				return true;
			}
		}
	}
};

ZmHtmlEditor.prototype._setIEFiller = function(el) {
	if (el) el.setAttribute("_ieFiller","");
};

ZmHtmlEditor.prototype._removeIEFiller = function(el) {
	if (el) {
		if (this._elementIsIEFiller(el)) {
			el.parentNode.removeChild(el);
		}
		for (var i=0; i<el.children.length; i++) {
			this._removeIEFiller(el.children[i]);
		}
	}
};

ZmHtmlEditor.prototype._mergeBlockquotes =
function(blockquote1, blockquote2) {

	if (AjxEnv.isIE) {
		this._removeIEFiller(blockquote1.lastChild);
	}

	var el1 = blockquote1;
	var depth1 = 0;
	while (el1.childNodes.length) { // Descend into blockquote1, finding the very last leaf node in the tree
		el1 = el1.childNodes[el1.childNodes.length-1];
		while (el1.previousSibling && el1.tagName=="BR")
			el1 = el1.previousSibling;
		depth1++;
	}

	var el2 = blockquote2;
	var depth2 = 0;
	while (el2.childNodes.length) { // Descend into blockquote2, finding the very first leaf node in the tree
		el2 = el2.childNodes[0];
		depth2++;
	}

	if (el1.nodeType==3) { // If it's a TextNode, go one up
		el1 = el1.parentNode;
		depth1--;
	}
	if (el2.nodeType==3) {
		el2 = el2.parentNode;
		depth2--;
	}
	var range = (AjxEnv.isIE) ? this._getIframeDoc().selection.createRange() : this._getRange();

	if (depth1==depth2) { // Simplest case, just append the contents of el2 to el1.
		var text = [el1.innerHTML, el2.innerHTML];
		var dummy = "###"+Dwt.getNextId()+"###";
		el1.innerHTML = text.join(dummy);
		var offset = el1.innerHTML.replace(/<br>/ig," ").replace(/<\/?[^>]+>/g,"").indexOf(dummy);
		el1.innerHTML = text.join("");
		if (AjxEnv.isIE) {
			range.moveToElementText(el1);
			range.moveStart("character", offset);
		} else {
			var p;
			for (var p = el1; p != null && offset > 0; p = this._nextElement(p)) { // Walk through elements, decrementing offset as we go, and set the range when we find an element where the remaining offset fits
				var type = p.nodeType;
				var textContent = p.textContent || p.innerText || p.innerHTML || "";

				if (type==3 || type==4 || type==8) {
					if (offset <= textContent.length)
						break;
					offset -= textContent.length;
				} else {
					if (p.tagName.toLowerCase()=="br")
						offset--;
					if (offset==0)
						break;
				}
			}
			range.setStart(p, offset);
		}
	} else if (depth1==depth2+1) { // We're merging at a node border, append all children of el2 to el1's parent (making them siblings of el1)
		while (el2.firstChild) {
			el1.parentNode.appendChild(el2.firstChild);
		}
		if (AjxEnv.isIE) {
			var type = p.nodeType;
			var offset = 1;
			if (type==3 || type==4 || type==8) {
				offset = p.length;
				p = p.parentNode;
			}
			range.moveToElementText(p);
			range.moveStart("character",offset);
		} else {
			if (el1.tagName && el1.tagName.toLowerCase()=="br")
				range.setStartBefore(el1);
			else
				range.setStartAfter(el1);
		}
		el1 = el1.parentNode;
	} else if (depth1+1==depth2) { // We're merging at a node border, append el2 and all its siblings to el1 (making them children of el1)
		var p = el1.childNodes.length ? el1.childNodes[el1.childNodes.length-1] : el1;
		el2 = el2.parentNode;
		while (el2.firstChild) {
			el1.appendChild(el2.firstChild);
		}
		if (AjxEnv.isIE) {
			var type = p.nodeType;
			var offset = 1;
			if (type==3 || type==4 || type==8) {
				offset = p.length;
				p = p.parentNode;
			}
			range.moveToElementText(p);
			range.moveStart("character",offset);
		} else {
			if (p.tagName && p.tagName.toLowerCase()=="br")
				range.setStartBefore(p);
			else
				range.setStartAfter(p);
		}
	} else { // We don't handle nodes that are further apart
		return;
	}
	while (el1 != blockquote1 && el2 != blockquote2) { // Ascend up the tree, appending nodes from blockquote2's tree to blockquotes1's tree
		while (el2.nextSibling) {
			el1.parentNode.appendChild(el2.nextSibling);
		}
		el1 = el1.parentNode;
		el2 = el2.parentNode;
	}
	this._removeElement(blockquote2); // All significant contents have been transferred, kill blockquote2

	range.collapse(true);
	if (AjxEnv.isIE) {
		range.select();
	} else {
		var sel = this._getSelection();
		sel.removeAllRanges();
		sel.addRange(range);
	}
};

// Spell checker methods

ZmHtmlEditor._spellCheckResumeEditing =
function() {
	var editor = Dwt.getObjectFromElement(this);
	editor.discardMisspelledWords();

    if (AjxEnv.isIE && editor._currInsPtBm && editor._getRange().type=="None") {
        var range = editor._getRange().createRange();
        range.moveToBookmark(editor._currInsPtBm);
        range.collapse(false);
    }
};

ZmHtmlEditor._spellCheckAgain =
function() {
	var editor = Dwt.getObjectFromElement(this);
	editor.discardMisspelledWords();
	editor.spellCheck();
};

ZmHtmlEditor.prototype._spellCheckShowModeDiv =
function() {
	var size = this.getSize();

	if (!this._spellCheckModeDivId) {
		var div = document.createElement("div");
		div.className = "SpellCheckModeDiv";
		div.id = this._spellCheckModeDivId = Dwt.getNextId();
		var html = new Array();
		var i = 0;
		html[i++] = "<table border=0 cellpadding=0 cellspacing=0><tr><td style='width:25'>";
		html[i++] = AjxImg.getImageHtml("SpellCheck");
		html[i++] = "</td><td style='white-space:nowrap'><span class='SpellCheckLink'>";
		html[i++] = ZmMsg.resumeEditing;
		html[i++] = "</span> | <span class='SpellCheckLink'>";
		html[i++] = ZmMsg.checkAgain;
		html[i++] = "</span></td></tr></table>";
		div.innerHTML = html.join("");

		var editable = document.getElementById((this._spellCheckDivId || this.getBodyFieldId()));
		editable.parentNode.insertBefore(div, editable);

		var el = div.getElementsByTagName("span");
		Dwt.associateElementWithObject(el[0], this);
		Dwt.setHandler(el[0], "onclick", ZmHtmlEditor._spellCheckResumeEditing);
		Dwt.associateElementWithObject(el[1], this);
		Dwt.setHandler(el[1], "onclick", ZmHtmlEditor._spellCheckAgain);
	}
	else {
		document.getElementById(this._spellCheckModeDivId).style.display = "";
	}
	this.setSize(size.x, size.y + (this._mode == DwtHtmlEditor.TEXT ? 1 : 2));
};

ZmHtmlEditor.prototype._spellCheckHideModeDiv =
function() {
	var size = this.getSize();
	if (this._spellCheckModeDivId) {
		document.getElementById(this._spellCheckModeDivId).style.display = "none";
	}
	this.setSize(size.x, size.y + (this._mode == DwtHtmlEditor.TEXT ? 1 : 2));
};

ZmHtmlEditor.prototype._spellCheckSuggestionListener =
function(ev) {
	var item = ev.item;
	var orig = item.getData("orig");
	if (!orig) { return; }

	var val = item.getData("value");
	var plainText = this._mode == DwtHtmlEditor.TEXT;
	var fixall = item.getData("fixall");
	var doc = plainText ? document : this._getIframeDoc();
	var spanEl = doc.getElementById(item.getData("spanId"));

	var action = item.getData(ZmPopupMenu.MENU_ITEM_ID_KEY);
	switch (action) {
		case "ignore":
			val = orig;
			this._ignoreWords[val] = true;
			if (fixall) {
				// TODO: visually "correct" all of them
			}
			break;
		case "add":
			val = orig;
			// add word to user's personal dictionary
			var soapDoc = AjxSoapDoc.create("ModifyPrefsRequest", "urn:zimbraAccount");
			var prefEl = soapDoc.set("pref", val);
			prefEl.setAttribute("name", "+zimbraPrefSpellIgnoreWord");
			var params = {
				soapDoc: soapDoc,
				asyncMode: true,
				callback: new AjxCallback(appCtxt, appCtxt.setStatusMsg, [ZmMsg.wordAddedToDictionary])
			};
			appCtxt.getAppController().sendRequest(params);
			this._ignoreWords[val] = true;
			break;
		default: break;
	}

	if (plainText && val == null) {
		this._editWord(fixall, spanEl);
	}
	else {
		var spanEls = fixall ? this._spellCheck.wordIds[orig] : spanEl;
		this._editWordFix(spanEls, val);
	}
	this._handleSpellCheckerEvents(null);
};

ZmHtmlEditor.prototype._getEditorDocument = function() {
	var plainText = this._mode == DwtHtmlEditor.TEXT;
	return plainText ? document : this._getIframeDoc();
};

ZmHtmlEditor.prototype._editWord = function(fixall, spanEl) {
	// edit clicked
	var doc = this._getEditorDocument();
	var input = doc.createElement("input");
	input.type = "text";
	input.value = AjxUtil.getInnerText(spanEl);
	input.className = "SpellCheckInputField";
	input.style.left = spanEl.offsetLeft - 2 + "px";
	input.style.top = spanEl.offsetTop - 2 + "px";
	input.style.width = spanEl.offsetWidth + 4 + "px";
	var div = doc.getElementById(this._spellCheckDivId);
	var scrollTop = div.scrollTop;
	div.appendChild(input);
	div.scrollTop = scrollTop; // this gets resetted when we add an input field (at least Gecko)
	input.setAttribute("autocomplete", "off");
	input.focus();
	if (!AjxEnv.isGeckoBased)
		input.select();
	else
		input.setSelectionRange(0, input.value.length);
	var inputListener = AjxCallback.simpleClosure(this._editWordHandler, this, fixall, spanEl);
	input.onblur = inputListener;
	input.onkeydown = inputListener;
};

ZmHtmlEditor.prototype._editWordHandler = function(fixall, spanEl, ev) {
	// the event gets lost after 20 milliseconds so we need
	// to save the following :(
    // bug 48471: IE loses the window.event object outside this call scope. So we fake it.
    ev = DwtUiEvent.getEvent(ev);
    ev = { type:ev.type, keyCode:ev.keyCode, ctrlKey:ev.ctrlKey, target:DwtUiEvent.getTarget(ev) };
	setTimeout(AjxCallback.simpleClosure(this._editWordHandler2, this, fixall, spanEl, ev), 20);
};
ZmHtmlEditor.prototype._editWordHandler2 = function(fixall, spanEl, ev) {
	ev = DwtUiEvent.getEvent(ev);
	var evType = ev.type;
	var evKeyCode = ev.keyCode;
	var evCtrlKey = ev.ctrlKey;
	var input = DwtUiEvent.getTarget(ev);
	var keyEvent = /key/.test(evType);
	var removeInput = true;
	if (/blur/.test(evType) || (keyEvent && evKeyCode == 13)) {
		if (evCtrlKey)
			fixall =! fixall;
		var orig = AjxUtil.getInnerText(spanEl);
		var spanEls = fixall ? this._spellCheck.wordIds[orig] : spanEl;
		this._editWordFix(spanEls, input.value);
	} else if (keyEvent && evKeyCode == 27 /* ESC */) {
		this._editWordFix(spanEl, AjxUtil.getInnerText(spanEl));
	} else {
		removeInput = false;
	}
	if (removeInput) {
		input.onblur = null;
		input.onkeydown = null;
		if (input.parentNode) {
			input.parentNode.removeChild(input);
		}
	}
	this._handleSpellCheckerEvents(null);
};

ZmHtmlEditor.prototype._editWordFix = function(spanEls, value) {
	spanEls = spanEls instanceof Array ? spanEls : [ spanEls ];
	var doc = this._getEditorDocument();
	for (var i = spanEls.length - 1; i >= 0; i--) {
		var spanEl = spanEls[i];
		if (typeof spanEl == "string") {
			spanEl = doc.getElementById(spanEl);
		}
		if (spanEl) {
			spanEl.innerHTML = value;
		}
	}
};

ZmHtmlEditor.prototype._handleSpellCheckerEvents =
function(ev) {
	var plainText = this._mode == DwtHtmlEditor.TEXT;
	var p = plainText ? (ev ? DwtUiEvent.getTarget(ev) : null) : this._getParentElement(),
		span, ids, i, suggestions,
		sc = this._spellCheck,
		doc = this._getEditorDocument(),
		modified = false,
		word = "";
	if (ev && /^span$/i.test(p.tagName) && /ZM-SPELLCHECK/.test(p.className)) {
		// stuff.
		word = p.getAttribute("word");
		// FIXME: not sure this is OK.
//		window.status = "Suggestions: " + sc.suggestions[word].join(", ");
		modified = word != AjxUtil.getInnerText(p);
	}

	// <FIXME: there's plenty of room for optimization here>
	ids = sc.spanIds;
	for (i in ids) {
		span = doc.getElementById(i);
		if (span) {
			if (ids[i] != AjxUtil.getInnerText(span) || this._ignoreWords[ids[i]])
				span.className = "ZM-SPELLCHECK-FIXED";
			else if (ids[i] == word)
				span.className = "ZM-SPELLCHECK-MISSPELLED2";
			else
				span.className = "ZM-SPELLCHECK-MISSPELLED";
		}
	}
	// </FIXME>

	// Dismiss the menu if it is present AND:
	//   - we have no event, OR
	//   - it's a mouse(down|up) event, OR
	//   - it's a KEY event AND there's no word under the caret, OR the word was modified.
	// I know, it's ugly.
	if (sc.menu &&
	    (!ev || ( /click|mousedown|mouseup|contextmenu/.test(ev.type)
		      || ( /key/.test(ev.type)
			   && (!word || modified) )
		    )))
	{
		// sc.menu.popdown();
		// FIXME: menu.dispose() should remove any submenus that may be
		//        present in its children; fix should go directly in DwtMenu.js
		if (sc.menu._menuItems.fixall)
			sc.menu._menuItems.fixall.getMenu().dispose();
		sc.menu.dispose();
		sc.menu = null;
		window.status = "";
	}
	// but that's even uglier:
	if (ev && word && (suggestions = sc.suggestions[word]) &&
	    (/mouseup|contextmenu/i.test(ev.type) ||
	     (plainText && /(click|mousedown|contextmenu)/i.test(ev.type))) &&
		(word == AjxUtil.getInnerText(p) && !this._ignoreWords[word]))
	{
		sc.menu = this._spellCheckCreateMenu(this, 0, suggestions, word, p.id, modified);

		var pos, ms = sc.menu.getSize(), ws = this.shell.getSize();
		if (!plainText) {
			// bug fix #5857 - use Dwt.toWindow instead of Dwt.getLocation so we can turn off dontIncScrollTop
			pos = Dwt.toWindow(document.getElementById(this._iFrameId), 0, 0, null, true);
			var pos2 = Dwt.toWindow(p, 0, 0, null, true);
			pos.x += pos2.x
				- (doc.documentElement.scrollLeft || doc.body.scrollLeft);
			pos.y += pos2.y
				- (doc.documentElement.scrollTop || doc.body.scrollTop);
		} else {
			// bug fix #5857
			pos = Dwt.toWindow(p, 0, 0, null, true);
			var div = document.getElementById(this._spellCheckDivId);
			pos.x -= div.scrollLeft;
			pos.y -= div.scrollTop;
		}
		pos.y += p.offsetHeight;
		// let's make sure we look nice, shall we.
		if (pos.y + ms.y > ws.y)
			pos.y -= ms.y + p.offsetHeight;
		sc.menu.popup(0, pos.x, pos.y);
		ev._stopPropagation = true;
		ev._returnValue = false;
	}
};

ZmHtmlEditor.prototype._spellCheckCreateMenu = function(parent, fixall, suggestions, word, spanId, modified) {
	var menu = new ZmPopupMenu(parent);
//	menu.dontStealFocus();

	if (modified) {
		var txt = "<b>" + word + "</b>";
		this._spellCheckCreateMenuItem(menu, "orig", {text:txt}, fixall, word, word, spanId);
	}

	if (suggestions.length > 0) {
		for (var i = 0; i < suggestions.length; ++i) {
			this._spellCheckCreateMenuItem(
				menu, "sug-"+i, {text:suggestions[i], className: ""},
				fixall, suggestions[i], word, spanId
			);
		}
		if (!(parent instanceof DwtMenuItem) && this._spellCheck.wordIds[word].length > 1) {
			if (!this._replaceAllFormatter) {
				this._replaceAllFormatter = new AjxMessageFormat(ZmMsg.replaceAllMenu);
			}
			var txt = "<i>"+this._replaceAllFormatter.format(this._spellCheck.wordIds[word].length)+"</i>";
			var item = menu.createMenuItem("fixall", {text:txt});
			var submenu = this._spellCheckCreateMenu(item, 1, suggestions, word, spanId, modified);
			item.setMenu(submenu);
		}
	}
	else {
		var item = this._spellCheckCreateMenuItem(menu, "noop", {text:ZmMsg.noSuggestions}, fixall, "", word, spanId);
		item.setEnabled(false);
		this._spellCheckCreateMenuItem(menu, "clear", {text:"<i>"+ZmMsg.clearText+"</i>" }, fixall, "", word, spanId);
	}

	menu.createSeparator();

	var plainText = this._mode == DwtHtmlEditor.TEXT;
	if (plainText) {
		// in plain text mode we want to be able to edit misspelled words
		var txt = fixall ? ZmMsg.editAll : ZmMsg.edit;
		this._spellCheckCreateMenuItem(menu, "edit", {text:txt}, fixall, null, word, spanId);
	}

	if (!fixall) {
		this._spellCheckCreateMenuItem(menu, "ignore", {text:ZmMsg.ignoreWord}, 0, null, word, spanId);
//		this._spellCheckCreateMenuItem(menu, "ignore", {text:ZmMsg.ignoreWordAll}, 1, null, word, spanId);
	}

	if (!fixall && appCtxt.get(ZmSetting.SPELL_CHECK_ADD_WORD_ENABLED)) {
		this._spellCheckCreateMenuItem(menu, "add", {text:ZmMsg.addWord}, fixall, null, word, spanId);
	}

	return menu;
};

ZmHtmlEditor.prototype._spellCheckCreateMenuItem =
function(menu, id, params, fixall, value, word, spanId, listener) {
	if (params.className == null) {
		params.className = "ZMenuItem ZmSpellMenuItem";
	}
	var item = menu.createMenuItem(id, params);
	item.setData("fixall", fixall);
	item.setData("value", value);
	item.setData("orig", word);
	item.setData("spanId", spanId);
	item.addSelectionListener(listener || this._spellCheckSuggestionListenerObj);
	return item;
};

ZmHtmlEditor.prototype._spellCheckCallback =
function(words) {
	var wordsFound = false;

	if (words && words.available) {
		var misspelled = words.misspelled;
		if (misspelled == null || misspelled.length == 0) {
			appCtxt.setStatusMsg(ZmMsg.noMisspellingsFound, ZmStatusView.LEVEL_INFO);
		} else {
			var msg = AjxMessageFormat.format(ZmMsg.misspellingsResult, misspelled.length);
			appCtxt.setStatusMsg(msg, ZmStatusView.LEVEL_WARNING);

			this.highlightMisspelledWords(misspelled);
			wordsFound = true;
		}
	} else {
		appCtxt.setStatusMsg(ZmMsg.spellCheckUnavailable, ZmStatusView.LEVEL_CRITICAL);
	}

	if (AjxEnv.isGeckoBased && this._mode == DwtHtmlEditor.HTML)
		setTimeout(AjxCallback.simpleClosure(this.focus, this), 10);

	if (this.onExitSpellChecker)
		this.onExitSpellChecker.run(wordsFound);
};

/**
 * Returns true if editor content is spell checked
 */
ZmHtmlEditor.prototype.isSpellCheckMode = function() {
    return Boolean( this._spellCheck );
};

// overwrites the base class' _enableDesignMode in order to work around Gecko problems
ZmHtmlEditor.prototype._enableDesignMode =
function(doc) {
	if (!doc) { return; }

	if (!(AjxEnv.isGeckoBased && this.ACE_ENABLED)) {
		return DwtHtmlEditor.prototype._enableDesignMode.call(this, doc);
	}
	// Gecko needs special attention here. (https://bugzilla.mozilla.org/show_bug.cgi?id=326600)

	if (!this._hasGeckoFocusHacks) {
		this.__enableGeckoFocusHacks();
	}

	// -- findings suggest that Firefox loses these events on certain
	//    occasions (i.e. iframe.style.display = "none"), so we DO need to
	//    add them multiple times.  Crap.
	this._getIframeWin().addEventListener("blur", this._designModeHack_blur, true);
	this._getIframeWin().addEventListener("focus", this._designModeHack_focus, true);
};

// this should be called ONLY ONCE (if !this._hasGeckoFocusHacks)
ZmHtmlEditor.prototype.__enableGeckoFocusHacks = function() {
	var bookmark = null;
	var state = 0;
	this._hasGeckoFocusHacks = true;

	function enableToolbars(enable) {
		var a = [];
		for (var i = 0; i < this._toolbars.length; i++)
			a = a.concat(this._toolbars[i].getChildren());
		for (var i = 0; i < a.length; ++i)
			a[i].setEnabled(enable);
	};

	this._designModeHack_blur = AjxCallback.simpleClosure(
		function(ev) {
			if (state < 0) { return; }

			//console.log("BLUR!");
			var enableFocus = false;
			var dwtev = DwtShell.mouseEvent;
			dwtev.setFromDhtmlEvent(ev, true);

			//bug: 24782 - we dont have option to get info related to toolbar button selection
			var kbMgr = appCtxt.getKeyboardMgr();
			if (kbMgr && kbMgr.__focusObj) {
				for (var i = 0; i < this._toolbars.length; i++) {
					if ((kbMgr.__focusObj == this._toolbars[i]) ||
						(kbMgr.__focusObj.parent == this._toolbars[i]))
					{
						enableFocus = true;
						break;
					}
				}
			}
			enableToolbars.call(this, enableFocus);

			var doc = this._getIframeDoc();
			Dwt.enableDesignMode(doc, false);
			state = -1;
			if (this._ace_componentsLoading > 0) { return; }

			try {
				var sel = this._getIframeWin().getSelection();
				var i = 0, r;
				try {
					bookmark = [];
					while (r = sel.getRangeAt(i++))
						bookmark.push(r);
				} catch(ex) {};
				sel.removeAllRanges();
			} catch(ex) {
				bookmark = null;
			}
		}, this);

	// bug 8508 - start off with disabled toolbars
	enableToolbars.call(this, false);

	this._designModeHack_focus = AjxCallback.simpleClosure(
		function(ev) {
			if (state > 0)
				return;
			// console.log("FOCUS!");
			var doc = this._getIframeDoc();
			var sel = this._getIframeWin().getSelection();
			enableToolbars.call(this, true);
			if (this._ace_componentsLoading > 0) { return; }

			Dwt.enableDesignMode(doc, true);
			if (!bookmark || bookmark.length == 0) {
				r = doc.createRange();
				r.selectNodeContents(doc.body);
				r.collapse(true);
				bookmark = [ r ];
			}
			sel.removeAllRanges();
			for (var i = 0; i < bookmark.length; ++i) {
				sel.addRange(bookmark[i]);
			}
			bookmark = null;
			state = 1;
		}, this);
};

ZmHtmlEditor.WRAP_LENGTH		= 80;
ZmHtmlEditor.HTML_QUOTE_PREFIX_PRE		= '<blockquote style="border-left:2px solid ' +
									 AjxStringUtil.HTML_QUOTE_COLOR +
									 ';margin-left:5px;padding-left:5px;'+
									 AjxStringUtil.HTML_QUOTE_STYLE +
									 '">';
ZmHtmlEditor.HTML_QUOTE_PREFIX_POST	= '</blockquote><br/>';
ZmHtmlEditor.HTML_QUOTE_NONPREFIX_PRE		= '<div style="' +
									 AjxStringUtil.HTML_QUOTE_STYLE +
									 '">';
ZmHtmlEditor.HTML_QUOTE_NONPREFIX_POST	= '</div><br/>';

// returns a standard set of params for wrapping text of HTML content
ZmHtmlEditor.getWrapParams =
function(htmlMode, incOptions) {

	incOptions = incOptions || {};
	var params = {};

	params.htmlMode	= htmlMode;
	params.len		= ZmHtmlEditor.WRAP_LENGTH;
	params.eol		= htmlMode ? '<br/>' : '\n';
	params.pre		= (htmlMode || !incOptions.prefix) ? "" : appCtxt.get(ZmSetting.REPLY_PREFIX) + " ";
	params.before	= (htmlMode) ? ((incOptions.prefix) ? ZmHtmlEditor.HTML_QUOTE_PREFIX_PRE : ZmHtmlEditor.HTML_QUOTE_NONPREFIX_PRE) : "";
	params.after	= (htmlMode) ? ((incOptions.prefix) ? ZmHtmlEditor.HTML_QUOTE_PREFIX_POST : ZmHtmlEditor.HTML_QUOTE_NONPREFIX_POST) : "";

	return params;
};

ZmHtmlEditor.prototype._updateState =
function() {
	var defaultFontSize = appCtxt.get(ZmSetting.COMPOSE_INIT_FONT_SIZE);
	DwtHtmlEditor.prototype._updateState.call(this, defaultFontSize);
};

ZmHtmlEditorColorPicker = function(parent,style,className) {
	DwtButtonColorPicker.call(this, parent,style,className);
};
ZmHtmlEditorColorPicker.prototype = new DwtButtonColorPicker;
ZmHtmlEditorColorPicker.prototype.constructor = ZmHtmlEditorColorPicker;

ZmHtmlEditorColorPicker.prototype.TEMPLATE = "dwt.Widgets#ZToolbarButton";
