/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013 VMware, Inc.
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
 * Creates a new compose view. The view does not display itself on construction.
 * @constructor
 * @class
 * This class provides a form for composing a message.
 *
 * @author Conrad Damon
 * 
 * @param {DwtControl}		parent			the element that created this view
 * @param {ZmController}	controller		the controller managing this view
 * @param {constant}		composeMode 	passed in so detached window knows which mode to be in on startup
 * 
 * @extends		DwtComposite
 * 
 * @private
 */
ZmComposeView = function(parent, controller, composeMode) {

	if (arguments.length == 0) { return; }
	
	this.TEMPLATE = "mail.Message#Compose";
	this._view = controller.getCurrentViewId();
	this._sessionId = controller.getSessionId();

	DwtComposite.call(this, {parent:parent, className:"ZmComposeView", posStyle:Dwt.ABSOLUTE_STYLE,
							 id:ZmId.getViewId(this._view)});

	ZmComposeView.ADDR_SETTING[AjxEmailAddress.BCC]	= ZmSetting.SHOW_BCC;

	ZmComposeView.NOTIFY_ACTION_MAP = {};
	ZmComposeView.NOTIFY_ACTION_MAP[ZmOperation.REPLY_ACCEPT]		= ZmOperation.REPLY_ACCEPT_NOTIFY;
	ZmComposeView.NOTIFY_ACTION_MAP[ZmOperation.REPLY_DECLINE]		= ZmOperation.REPLY_DECLINE_NOTIFY;
	ZmComposeView.NOTIFY_ACTION_MAP[ZmOperation.REPLY_TENTATIVE]	= ZmOperation.REPLY_TENTATIVE_NOTIFY;

	ZmComposeView.MOVE_TO_FIELD = {};
	ZmComposeView.MOVE_TO_FIELD[ZmOperation.MOVE_TO_TO]		= AjxEmailAddress.TO;
	ZmComposeView.MOVE_TO_FIELD[ZmOperation.MOVE_TO_CC]		= AjxEmailAddress.CC;
	ZmComposeView.MOVE_TO_FIELD[ZmOperation.MOVE_TO_BCC]	= AjxEmailAddress.BCC;
	
	this._onMsgDataChange = new AjxCallback(this, this._onMsgDataChange);
	this._useAcAddrBubbles = appCtxt.get(ZmSetting.USE_ADDR_BUBBLES) && appCtxt.get(ZmSetting.CONTACTS_ENABLED);

	this._controller = controller;

	var recipParams = {};
	recipParams.resetContainerSizeMethod	= this._resetBodySize.bind(this);
	recipParams.enableContainerInputs		= this.enableInputs.bind(this);
	recipParams.reenter						= this.reEnableDesignMode.bind(this);
	recipParams.contactPopdownListener		= this._controller._dialogPopdownListener;
	recipParams.contextId					= this._controller.getCurrentViewId();

    this._recipients = new ZmRecipients(recipParams);

	this._initialize(composeMode);

	// make sure no unnecessary scrollbars show up
	this.getHtmlElement().style.overflow = "hidden";
};

ZmComposeView.prototype = new DwtComposite;
ZmComposeView.prototype.constructor = ZmComposeView;

ZmComposeView.prototype.isZmComposeView = true;
ZmComposeView.prototype.toString = function() {	return "ZmComposeView"; };

//
// Constants
//

// Consts related to compose fields
ZmComposeView.QUOTED_HDRS = [
		ZmMailMsg.HDR_FROM,
		ZmMailMsg.HDR_TO,
		ZmMailMsg.HDR_CC,
		ZmMailMsg.HDR_DATE,
		ZmMailMsg.HDR_SUBJECT
];

ZmComposeView.BAD						= "_bad_addrs_";

// Message dialog placement
ZmComposeView.DIALOG_X 					= 50;
ZmComposeView.DIALOG_Y 					= 100;

// Attachment related
ZmComposeView.UPLOAD_FIELD_NAME			= "attUpload";
ZmComposeView.FORWARD_ATT_NAME			= "ZmComposeView_forAttName";
ZmComposeView.FORWARD_MSG_NAME			= "ZmComposeView_forMsgName";
ZmComposeView.ADD_ORIG_MSG_ATTS			= "add_original_attachments";

// max # of attachments to show
ZmComposeView.SHOW_MAX_ATTACHMENTS		= AjxEnv.is800x600orLower ? 2 : 3;
ZmComposeView.MAX_ATTACHMENT_HEIGHT 	= (ZmComposeView.SHOW_MAX_ATTACHMENTS * 23) + "px";

// Reply/forward stuff
ZmComposeView.EMPTY_FORM_RE				= /^[\s\|]*$/;
ZmComposeView.HTML_TAG_RE				= /(<[^>]+>)/g;
ZmComposeView.QUOTED_CONTENT_RE			= new RegExp("^----- ", "m");
ZmComposeView.HTML_QUOTED_CONTENT_RE	= new RegExp("<br>----- ", "i");
ZmComposeView.ADDR_SETTING				= {}; // XXX: may not be necessary anymore?

ZmComposeView.OP = {};
ZmComposeView.OP[AjxEmailAddress.TO]	= ZmId.CMP_TO;
ZmComposeView.OP[AjxEmailAddress.CC]	= ZmId.CMP_CC;
ZmComposeView.OP[AjxEmailAddress.BCC]	= ZmId.CMP_BCC;


// Public methods

/**
 * Sets the current view, based on the given action. The compose form is
 * created and laid out and everything is set up for interaction with the user.
 *
 * @param {Hash}		params			a hash of parameters:
 * @param {constant}	action				new message, reply, forward, or an invite action
 * @param {ZmMailMsg}	msg					the original message (reply/forward), or address (new message)
 * @param {ZmIdentity}	identity			identity of sender
 * @param {String}		toOverride			To: addresses (optional)
 * @param {String}		ccOverride			Cc: addresses (optional)
 * @param {String}		subjectOverride		subject for new msg (optional)
 * @param {String}		extraBodyText		canned text to prepend to body (invites)
 * @param {Array}		msgIds				list of msg Id's to be added as attachments
 * @param {String}		accountName			on-behalf-of From address
 */
ZmComposeView.prototype.set =
function(params) {

	var action = this._action = params.action;
	if (this._msg) {
		this._msg.onChange = null;
	}

	this._isIncludingOriginalAttachments = false;
	this._originalAttachmentsInitialized = false;

	this._acceptFolderId = params.acceptFolderId;
	var msg = this._msg = this._addressesMsg = params.msg;
    var oboMsg = msg || (params.selectedMessages && params.selectedMessages.length && params.selectedMessages[0]);
	var obo = this._getObo(params.accountName, oboMsg);
	if (msg) {
		msg.onChange = this._onMsgDataChange;
	}

	// list of msg Id's to add as attachments
	this._msgIds = params.msgIds;

	AjxDebug.println(AjxDebug.REPLY, "ZmComposeView::set - Reset compose view");
	this.reset(true);

	this._setFromSelect(msg);

	if (params.identity) {
		if (this.identitySelect) {
			this.identitySelect.setSelectedValue(params.identity.id);
		}
		if (appCtxt.get(ZmSetting.SIGNATURES_ENABLED) || appCtxt.multiAccounts) {
			var selected = this._getSignatureIdForAction(params.identity) || "";
			var account = appCtxt.multiAccounts && this.getFromAccount();
			this._controller.resetSignatureToolbar(selected, account);
		}
	}

    this._recipients.setup();

	if (!ZmComposeController.IS_FORWARD[action]) {
		// populate fields based on the action and user prefs
		this._setAddresses(action, AjxEmailAddress.TO, params.toOverride);
		if (params.ccOverride) {
			this._setAddresses(action, AjxEmailAddress.CC, params.ccOverride);
		}
		if (params.bccOverride) {
			this._setAddresses(action, AjxEmailAddress.BCC, params.bccOverride);
		}
	}

	if (obo) {
        this.identitySelect.setSelectedValue(obo);
	}
	this._setSubject(action, msg, params.subjOverride);
	this._setBody(action, msg, params.extraBodyText);

	if (appCtxt.get(ZmSetting.MAIL_PRIORITY_ENABLED)) {
		var priority = "";
		if (msg && (action == ZmOperation.DRAFT)) {
			if (msg.isHighPriority) {
				priority = ZmItem.FLAG_HIGH_PRIORITY;
			} else if (msg.isLowPriority) {
				priority = ZmItem.FLAG_LOW_PRIORITY;
			}
		}
		this._setPriority(priority);
	}

	this._moveCaretOnTimer(true, params.extraBodyText ? params.extraBodyText.length : 0);

	if (action != ZmOperation.FORWARD_ATT) {
		this._saveExtraMimeParts();
	}

	// save form state (to check for change later)
	if (this._composeMode == DwtHtmlEditor.HTML) {
		var ta = new AjxTimedAction(this, this._setFormValue);
		AjxTimedAction.scheduleAction(ta, 10);
	} else {
		this._setFormValue();
	}
	// Force focus on the TO field
	if (!ZmComposeController.IS_REPLY[action]) {
		appCtxt.getKeyboardMgr().grabFocus(this._recipients.getField(AjxEmailAddress.TO));
	}
};

ZmComposeView.prototype._getObo =
function(obo, msg) {
	if (msg) {
		var folder = (!obo) ? appCtxt.getById(msg.folderId) : null;
		obo = (folder && folder.isRemote()) ? folder.getOwner() : null;

		// check if this is a draft that was originally composed obo
		var isFromDataSource = msg.identity && msg.identity.isFromDataSource;
		if (!obo && msg.isDraft && !appCtxt.multiAccounts && !isFromDataSource && !appCtxt.get(ZmSetting.ALLOW_ANY_FROM_ADDRESS)) {
			var ac = window.parentAppCtxt || window.appCtxt;
			var from = msg.getAddresses(AjxEmailAddress.FROM).get(0);
			if (from && from.address.toLowerCase() != ac.accountList.mainAccount.getEmail().toLowerCase() && !appCtxt.isMyAddress(from.address.toLowerCase())) {
				obo = from.address;
			}
		}
	}
	return obo;
};

ZmComposeView.prototype._saveExtraMimeParts =
function() {
	
	var bodyParts = this._msg ? this._msg.getBodyParts() : [];
	for (var i = 0; i < bodyParts.length; i++) {
		var bodyPart = bodyParts[i];
		var contentType = bodyPart.contentType;

		if (contentType == ZmMimeTable.TEXT_PLAIN) { continue; }
		if (contentType == ZmMimeTable.TEXT_HTML) { continue; }
		if (ZmMimeTable.isRenderableImage(contentType) && bodyPart.contentDisposition == "inline") { continue; } // bug: 28741

		var mimePart = new ZmMimePart();
		mimePart.setContentType(contentType);
		mimePart.setContent(bodyPart.getContent());
		this.addMimePart(mimePart);
	}
};

/**
 * Called automatically by the attached ZmMailMsg object when data is
 * changed, in order to support Zimlets modify subject or other values
 * (bug: 10540)
 * 
 * @private
 */
ZmComposeView.prototype._onMsgDataChange =
function(what, val) {
	if (what == "subject") {
		this._subjectField.value = val;
		this.updateTabTitle();
	}
};

ZmComposeView.prototype.getComposeMode =
function() {
	return this._composeMode;
};

ZmComposeView.prototype.getController =
function() {
	return this._controller;
};

ZmComposeView.prototype.getHtmlEditor =
function() {
	return this._htmlEditor;
};

/**
 * Gets the title.
 * 
 * @return	{String}	the title
 */
ZmComposeView.prototype.getTitle =
function() {
	var text;
	if (ZmComposeController.IS_REPLY[this._action]) {
		text = ZmMsg.reply;
	} else if (ZmComposeController.IS_FORWARD[this._action]) {
		text = ZmMsg.forward;
	} else {
		text = ZmMsg.compose;
	}
	return [ZmMsg.zimbraTitle, text].join(": ");
};

/**
 * Gets the field values for each of the addr fields.
 * 
 * @return	{Array}	an array of addresses
 */
ZmComposeView.prototype.getRawAddrFields =
function() {
    return this._recipients.getRawAddrFields();
};

// returns address fields that are currently visible
ZmComposeView.prototype.getAddrFields =
function() {
    return this._recipients.getAddrFields();
};

ZmComposeView.prototype.getAddrInputField =
function(type) {
    return this._recipients.getAddrInputField(type);
};

ZmComposeView.prototype.getRecipientField =
function(type) {
    return this._recipients.getField(type);
};

ZmComposeView.prototype.getAddressButtonListener =
function(ev, addrType) {
    return this._recipients.addressButtonListener(ev, addrType);
};

ZmComposeView.prototype.setAddress =
function(type, addr) {
    return this._recipients.setAddress(type, addr);
};

ZmComposeView.prototype.collectAddrs =
function() {
    return this._recipients.collectAddrs();
};

// returns list of attachment field values (used by detachCompose)
ZmComposeView.prototype.getAttFieldValues =
function() {
	var attList = [];
	var atts = document.getElementsByName(ZmComposeView.UPLOAD_FIELD_NAME);

	for (var i = 0; i < atts.length; i++)
		attList.push(atts[i].value);

	return attList;
};

ZmComposeView.prototype.setBackupForm =
function() {
	this.backupForm = this._backupForm();
};

/**
* Saves *ALL* form value data to test against whether user made any changes
* since canceling SendMsgRequest. If user attempts to send again, we compare
* form data with this value and if not equal, send a new UID otherwise, re-use.
* 
* @private
*/
ZmComposeView.prototype._backupForm =
function() {
	var val = this._formValue(true, true);

	// keep track of attachments as well
	var atts = document.getElementsByName(ZmComposeView.UPLOAD_FIELD_NAME);
	for (var i = 0; i < atts.length; i++) {
		if (atts[i].value.length) {
			val += atts[i].value;
		}
	}

	// keep track of "uploaded" attachments as well :/
	val += this._getForwardAttIds(ZmComposeView.FORWARD_ATT_NAME + this._sessionId).join("");
	val += this._getForwardAttIds(ZmComposeView.FORWARD_MSG_NAME + this._sessionId).join("");

	return val;
};

ZmComposeView.prototype._setAttInline =
function(opt){
  this._isAttachInline = (opt === true);
};

ZmComposeView.prototype._getIsAttInline =
function(opt){
  return(this._isAttachInline);
};

ZmComposeView.prototype._checkIsOnBehalfOf =
function(){
    var oboCheck = appCtxt.getUsername() + " " + ZmMsg.sendOnBehalfOf + " ";
    var opt = this.identitySelect.getSelectedOption();
    var optDisplayName = opt && opt.getDisplayValue();
    if (!optDisplayName) return false;
    return !(optDisplayName.indexOf(oboCheck));
};

ZmComposeView.prototype._isInline =
function(msg) {

	if (this._attachDialog) {
		return this._attachDialog.isInline();
	}

	msg = msg || this._msg;

	if (msg && this._msgAttId && msg.id == this._msgAttId) {
		return false;
	}

	if (msg && msg.attachments) {
		var atts = msg.attachments;
		for (var i = 0; i < atts.length; i++) {
			if (atts[i].contentId) {
				return true;
			}
		}
	}

	return false;
};


ZmComposeView.prototype._addReplyAttachments =
function(){
    this._showForwardField(this._msg, ZmComposeView.ADD_ORIG_MSG_ATTS);
};

ZmComposeView.prototype._handleInlineAtts =
function(msg, handleInlineDocs){

	var handled = false, ci, cid, dfsrc, inlineAtt, attached = {};

	var idoc = this._htmlEditor._getIframeDoc();
	var images = idoc ? idoc.getElementsByTagName("img"):[];
	for (var i = 0; i < images.length; i++) {
		dfsrc = images[i].getAttribute("dfsrc") || images[i].getAttribute("mce_src") || images[i].src;
		if (dfsrc) {
			if (dfsrc.substring(0,4) == "cid:") {
				cid = dfsrc.substring(4).replace("%40","@");
				var docpath = images[i].getAttribute("doc");
				if (docpath){
					msg.addInlineDocAttachment(cid, null, docpath);
					handled = true;
				} else {
					ci = "<" + cid + ">";
					inlineAtt = msg.findInlineAtt(ci);
					if (!inlineAtt && this._msg) {
						inlineAtt = this._msg.findInlineAtt(ci);
					}
                    if (inlineAtt) {
						var id = [cid, inlineAtt.part].join("_");
                        if (!attached[id]){
                            msg.addInlineAttachmentId(cid, null, inlineAtt.part);
                            handled = true;
                            attached[id] = true;
                        }
					}
				}
			}
		}
	}

	return handled;
};

ZmComposeView.prototype._mergeInlineAndForwardAtts =
function(msg, forwardAttIds) {

	var newFwdAttIds = [];
	var atts = this._msg.attachments;

	function checkFwdAttExists(part) {
		for (var j = 0; j < forwardAttIds.length; j++) {
			if (forwardAttIds[j] == part) {
				return true;
			}
		}
		return false;
	}

	for (var i = 0; i < atts.length; i++) {
		var att = atts[i];
		if (att.ci && !checkFwdAttExists(att.part)) {
			newFwdAttIds.push(att.part);
		}
	}

	return [].concat(forwardAttIds, newFwdAttIds);
};

ZmComposeView.prototype._generateCid =
function() {
	var timeStr = ""+new Date().getTime();
	var hash = AjxSHA1.hex_sha1(timeStr + Dwt.getNextId());
	return hash + "@zimbra";
}

/**
* Returns the message from the form, after some basic input validation.
*/
ZmComposeView.prototype.getMsg =
function(attId, isDraft, dummyMsg, forceBail, contactId) {

	// Check destination addresses.
	var addrs = this._recipients.collectAddrs();

	// Any addresses at all provided? If not, bail.
	if ((!isDraft || forceBail) && !addrs.gotAddress) {
		this.enableInputs(false);
		var msgDialog = appCtxt.getMsgDialog();
		msgDialog.setMessage(ZmMsg.noAddresses, DwtMessageDialog.CRITICAL_STYLE);
		msgDialog.popup(this._getDialogXY());
		msgDialog.registerCallback(DwtDialog.OK_BUTTON, this._okCallback, this);
		this.enableInputs(true);
		return;
	}

	var cd = appCtxt.getOkCancelMsgDialog();
	cd.reset();

	// Is there a subject? If not, ask the user if they want to send anyway.
	var subject = AjxStringUtil.trim(this._subjectField.value);
	if ((!isDraft || forceBail) && subject.length == 0 && !this._noSubjectOkay) {
		this.enableInputs(false);
		cd.setMessage(ZmMsg.compSubjectMissing, DwtMessageDialog.WARNING_STYLE);
		cd.registerCallback(DwtDialog.OK_BUTTON, this._noSubjectOkCallback, this, cd);
		cd.registerCallback(DwtDialog.CANCEL_BUTTON, this._noSubjectCancelCallback, this, cd);
		cd.popup(this._getDialogXY());
		return;
	}

	// Any bad addresses?  If there are bad ones, ask the user if they want to send anyway.
	if ((!isDraft || forceBail) && addrs[ZmComposeView.BAD].size() && !this._badAddrsOkay) {
		this.enableInputs(false);
		var bad = AjxStringUtil.htmlEncode(addrs[ZmComposeView.BAD].toString(AjxEmailAddress.SEPARATOR));
		var msg = AjxMessageFormat.format(ZmMsg.compBadAddresses, bad);
		cd.setMessage(msg, DwtMessageDialog.WARNING_STYLE);
		cd.registerCallback(DwtDialog.OK_BUTTON, this._badAddrsOkCallback, this, cd);
		cd.registerCallback(DwtDialog.CANCEL_BUTTON, this._badAddrsCancelCallback, this, [addrs.badType, cd]);
		cd.setVisible(true); // per fix for bug 3209
		cd.popup(this._getDialogXY());
		return;
	} else {
		this._badAddrsOkay = false;
	}

	// Mandatory Spell Check
	if ((!isDraft || forceBail) && appCtxt.get(ZmSetting.SPELL_CHECK_ENABLED) && 
		appCtxt.get(ZmSetting.MAIL_MANDATORY_SPELLCHECK) && !this._spellCheckOkay) {
		if (this._htmlEditor.checkMisspelledWords(new AjxCallback(this, this._spellCheckShield), null, new AjxCallback(this, this._spellCheckErrorShield))) {
			return;
		}
	} else {
		this._spellCheckOkay = false;
	}

	// Create Msg Object - use dummy if provided
	var msg = dummyMsg || (new ZmMailMsg());
	msg.setSubject(subject);

	var zeroSizedAttachments = false;
	// handle Inline Attachments
    if (attId && ( this._getIsAttInline() || (this._attachDialog && this._attachDialog.isInline()) || attId.clipboardPaste) ){
        for (var i = 0; i < attId.length; i++) {
			var att = attId[i];
			if (att.s == 0) {
				zeroSizedAttachments = true;
				continue;
			}
			var contentType = att.ct;
			if (contentType && contentType.indexOf("image") != -1) {
				var cid = this._generateCid();
                if( att.hasOwnProperty("id") ){
                    this._htmlEditor.replaceImage(att.id, "cid:" + cid);
                }
                else{
                    this._htmlEditor.insertImage("cid:" + cid, AjxEnv.isIE);
                }
				msg.addInlineAttachmentId(cid, att.aid);
			} else {
				msg.addAttachmentId(att.aid);
			}
		}
	} else if (attId && typeof attId != "string") {
		for (var i = 0; i < attId.length; i++) {
			if (attId[i].s == 0) {
				zeroSizedAttachments = true;
				continue;
			}
			msg.addAttachmentId(attId[i].aid);
		}
	} else if (attId) {
		msg.addAttachmentId(attId);
	}

	if (zeroSizedAttachments) {
		appCtxt.setStatusMsg(ZmMsg.zeroSizedAtts);
	}

	// check if this is a resend
	if (this.sendUID && this.backupForm) {
		// if so, check if user changed anything since canceling the send
		if (isDraft || this._backupForm() != this.backupForm) {
			this.sendUID = (new Date()).getTime();
		}
	} else {
		this.sendUID = (new Date()).getTime();
	}

	// get list of message part id's for any forwarded attachements
	var forwardAttIds = this._getForwardAttIds(ZmComposeView.FORWARD_ATT_NAME + this._sessionId, !isDraft && this._hideOriginalAttachments);
	var forwardMsgIds = [];
	if (this._msgIds) {
		// Get any message ids added via the attachment dialog (See
		// _attsDoneCallback which adds new forwarded attachments to msgIds)
		forwardMsgIds = this._msgIds;
		this._msgIds = null;
	} else if (this._msgAttId) {
		// Forward one message or Reply as attachment
		forwardMsgIds.push(this._msgAttId);
	}

	// --------------------------------------------
	// Passed validation checks, message ok to send
	// --------------------------------------------

	// build MIME
	var top = this._getTopPart(msg, isDraft);

	msg.setTopPart(top);
	msg.setSubject(subject);
	msg.setForwardAttIds(forwardAttIds);
    msg.setForwardAttObjs(this._getForwardAttObjs(forwardAttIds));
    if (!contactId) {
    //contactId not passed in, but vcard signature may be set
        if (this._msg && this._msg._contactAttIds){
            contactId = this._msg._contactAttIds;
            this._msg.setContactAttIds([]);
        }
    }
	msg.setContactAttIds(contactId);
	for (var i = 0; i < ZmMailMsg.COMPOSE_ADDRS.length; i++) {
		var type = ZmMailMsg.COMPOSE_ADDRS[i];
		if (addrs[type] && addrs[type].all.size() > 0) {
			msg.setAddresses(type, addrs[type].all);
		}
	}
	msg.identity = this.getIdentity();
	msg.sendUID = this.sendUID;

    if(!msg.identity){
      msg.delegatedSenderAddr = this.identitySelect.getValue();
      msg.isOnBehalfOf = this._checkIsOnBehalfOf();
    }
	// save a reference to the original message
	msg._origMsg = this._msg;
	if (this._msg && this._msg._instanceDate) {
		msg._instanceDate = this._msg._instanceDate;
	}

	this._setMessageFlags(msg);

	if (this._action == ZmOperation.DRAFT && this._origAcctMsgId) {
		msg.origAcctMsgId = this._origAcctMsgId;
	}

	// replied/forw msg or draft shouldn't have att ID (a repl/forw voicemail mail msg may)
	if (this._msg && this._msg.attId) {
		msg.addAttachmentId(this._msg.attId);
	}

	if (this._msgAttId) {
		if (forwardMsgIds.length > 0) {
			// Check if the MsgId is already present in the fwdMsgIds list.
			var i = 0;
			while (forwardMsgIds[i] && forwardMsgIds[i] != this._msgAttId) {
				i++;
			}
			if (i == forwardMsgIds.length) {
				forwardMsgIds.push(this._msgAttId);
			}
			delete i;
		} else {
			forwardMsgIds.push(this._msgAttId);
		}
	}

	msg.setMessageAttachmentId(forwardMsgIds);

	var priority = this._getPriority();
	if (priority) {
		msg.flagLocal(priority, true);
	}

	if (this._fromSelect) {
		msg.fromSelectValue = this._fromSelect.getSelectedOption();
	}

    if (!this._zimletCheck(msg, isDraft, forceBail)) return;
	
	return msg;
};

ZmComposeView.prototype._getTopPart =
function(msg, isDraft, bodyContent) {
	
	// set up message parts as necessary
	var top = new ZmMimePart();
	var textContent;

	if (this._composeMode == DwtHtmlEditor.HTML) {
		top.setContentType(ZmMimeTable.MULTI_ALT);

		// create two more mp's for text and html content types
		var textPart = new ZmMimePart();
		textPart.setContentType(ZmMimeTable.TEXT_PLAIN);
		var self = this;
		var convertor = {
			"hr": function(el) {
				return ZmComposeView._convertHtmlPreface(self, el);
			},
			"blockquote": function(el) {
				return "\n<blockquote>\n";
			},
			"/blockquote": function(el) {
				return "\n</blockquote>\n";
			},
			"_after": AjxCallback.simpleClosure(this._applyHtmlPrefix, this, "<blockquote>", "</blockquote>")
		}
		textContent = AjxStringUtil.convertHtml2Text(bodyContent || (this._htmlEditor && this._htmlEditor.getContent()) || "", convertor);
		textPart.setContent(textContent);
		top.children.add(textPart);

		var htmlPart = new ZmMimePart();
		htmlPart.setContentType(ZmMimeTable.TEXT_HTML);		

		if (!this.isHidden) {
			var idoc = this._htmlEditor._getIframeDoc();
			this._cleanupFileRefImages(idoc);
			this._restoreMultipartRelatedImages(idoc);
			if (!isDraft) {
				this._cleanupSignatureIds(idoc);
			}
			var defangedContent = this._htmlEditor.getContent(!isDraft);
	
			// Bug 27422 - Firefox and Safari implementation of execCommand("bold")
			// etc use styles, and some email clients (Entourage) don't process the
			// styles and the text remains plain. So we post-process and convert
			// those to the tags (which are what the IE version of execCommand() does).
			if (AjxEnv.isFirefox) {
				defangedContent = defangedContent.replace(/<span style="font-weight: bold;">(.+?)<\/span>/, "<strong>$1</strong>");
				defangedContent = defangedContent.replace(/<span style="font-style: italic;">(.+?)<\/span>/, "<em>$1</em>");
				defangedContent = defangedContent.replace(/<span style="text-decoration: underline;">(.+?)<\/span>/, "<u>$1</u>");
				defangedContent = defangedContent.replace(/<span style="text-decoration: line-through;">(.+?)<\/span>/, "<strike>$1</strike>");
			} else if (AjxEnv.isSafari) {
				defangedContent = defangedContent.replace(/<span class="Apple-style-span" style="font-weight: bold;">(.+?)<\/span>/, "<strong>$1</strong>");
				defangedContent = defangedContent.replace(/<span class="Apple-style-span" style="font-style: italic;">(.+?)<\/span>/, "<em>$1</em>");
				defangedContent = defangedContent.replace(/<span class="Apple-style-span" style="text-decoration: underline;">(.+?)<\/span>/, "<u>$1</u>");
				defangedContent = defangedContent.replace(/<span class="Apple-style-span" style="text-decoration: line-through;">(.+?)<\/span>/, "<strike>$1</strike>");
			}
	
			htmlPart.setContent(defangedContent);
		}
		else {
			htmlPart.setContent(bodyContent);
		}

        var content = AjxStringUtil.defangHtmlContent(htmlPart.getContent());

        htmlPart.setContent(content);

		if (!this.isHidden) {
			this._handleInlineAtts(msg, true);
		}
		var inlineAtts = msg.getInlineAttachments();
		var inlineDocAtts = msg.getInlineDocAttachments();
		var iAtts = [].concat(inlineAtts, inlineDocAtts);
		if ( iAtts &&  iAtts.length > 0 ) {
			var relatedPart = new ZmMimePart();
			relatedPart.setContentType(ZmMimeTable.MULTI_RELATED);
			relatedPart.children.add(htmlPart);
			top.children.add(relatedPart);
		} else {
			top.children.add(htmlPart);
		}
	}
	else {
		var inline = this._isInline();

		var textPart = (this._extraParts || inline) ? new ZmMimePart() : top;
		textPart.setContentType(ZmMimeTable.TEXT_PLAIN);
		textContent = bodyContent || (this._htmlEditor && this._htmlEditor.getContent()) || "";
		textPart.setContent(textContent);

		if (inline) {
			top.setContentType(ZmMimeTable.MULTI_ALT);
			var relatedPart = new ZmMimePart();
			relatedPart.setContentType(ZmMimeTable.MULTI_RELATED);
			relatedPart.children.add(textPart);
			top.children.add(relatedPart);
            // bug: 43156 
            // Commented as we now show inline attachments as part of forward attachments.
			//forwardAttIds = this._mergeInlineAndForwardAtts(msg, forwardAttIds);
		} else {
			if (this._extraParts) {
				top.setContentType(ZmMimeTable.MULTI_ALT);
				top.children.add(textPart);
			}
		}
	}

	// add extra message parts
	if (this._extraParts) {
		for (var i = 0; i < this._extraParts.length; i++) {
			var mimePart = this._extraParts[i];
			top.children.add(mimePart);
		}
	}

	// store text-content of the current email for zimlets to work with
	// TODO: zimlets are being lazy here, and text content could be large; zimlets should get content from parts
	msg.textBodyContent = this.isHidden ? textContent : (this._composeMode == DwtHtmlEditor.HTML)
		? this._htmlEditor.getTextVersion()
		: this._htmlEditor.getContent();
	
	return top;
};

ZmComposeView.prototype._setMessageFlags =
function(msg) {
	
	if (this._action != ZmOperation.NEW_MESSAGE && this._msg) {
		var isInviteReply = ZmComposeController.IS_INVITE_REPLY[this._action];
		if (this._action == ZmOperation.DRAFT) {
			msg.isReplied = (this._msg.rt == "r");
			msg.isForwarded = (this._msg.rt == "w");
			msg.isDraft = this._msg.isDraft;
			// check if we're resaving a draft that was originally a reply/forward
			if (msg.isDraft) {
				// if so, set both origId and the draft id
				msg.origId = msg.isReplied || msg.isForwarded ? this._msg.origId : null;
				msg.id = this._msg.id;
				msg.nId = this._msg.nId;
			}
		} else {
			msg.isReplied = ZmComposeController.IS_REPLY[this._action];
			msg.isForwarded = ZmComposeController.IS_FORWARD[this._action];
			msg.origId = this._msg.id;
		}
		msg.isInviteReply = isInviteReply;
		msg.acceptFolderId = this._acceptFolderId;
		var notifyActionMap = ZmComposeView.NOTIFY_ACTION_MAP || {};
		var inviteMode = notifyActionMap[this._action] ? notifyActionMap[this._action] : this._action;
		msg.inviteMode = isInviteReply ? inviteMode : null;
		msg.irtMessageId = this._msg.irtMessageId || this._msg.messageId;
		msg.folderId = this._msg.folderId;
	}
};

ZmComposeView.prototype._zimletCheck =
function(msg, isDraft, forceBail) {
	
	/*
	* finally, check for any errors via zimlets..
	* A Zimlet can listen to emailErrorCheck action to perform further check and
	* alert user about the error just before sending email. We will be showing
	* yes/no dialog. This zimlet must return an object {hasError:<true or false>,
	* errorMsg:<Some Error msg>, zimletName:<zimletName>} e.g: {hasError:true,
	* errorMsg:"you might have forgotten attaching an attachment, do you want to
	* continue?", zimletName:"com_zimbra_attachmentAlert"}
	**/
	if ((!isDraft || forceBail) && appCtxt.areZimletsLoaded()) {
		var boolAndErrorMsgArray = [];
		var showErrorDlg = false;
		var errorMsg = "";
		var zimletName = "";
		appCtxt.notifyZimlets("emailErrorCheck", [msg, boolAndErrorMsgArray]);
		var blen =  boolAndErrorMsgArray.length;
		for (var k = 0; k < blen; k++) {
			var obj = boolAndErrorMsgArray[k];
			if (obj == null || obj == undefined) { continue; }

			var hasError = obj.hasError;
			zimletName = obj.zimletName;
			if (Boolean(hasError)) {
				if (this._ignoredZimlets) {
					if (this._ignoredZimlets[zimletName]) { // if we should ignore this zimlet
						delete this._ignoredZimlets[zimletName];
						continue; // skip
					}
				}
				showErrorDlg = true;
				errorMsg = obj.errorMsg;
				break;
			}
		}
		if (showErrorDlg) {
			this.enableInputs(false);
			var cd = appCtxt.getOkCancelMsgDialog();
			cd.setMessage(errorMsg, DwtMessageDialog.WARNING_STYLE);
			var params = {errDialog:cd, zimletName:zimletName};
			cd.registerCallback(DwtDialog.OK_BUTTON, this._errViaZimletOkCallback, this, params);
			cd.registerCallback(DwtDialog.CANCEL_BUTTON, this._errViaZimletCancelCallback, this, params);
			cd.popup(this._getDialogXY());
			return false;
		}
	}
    
    return true;
};

ZmComposeView.prototype.setDocAttachments =
function(msg, docIds) {
	if (!docIds) { return; }

	var zeroSizedAttachments = false;
	var inline = this._isInline();
	for (var i = 0; i < docIds.length; i++) {
		var docAtt = docIds[i];
		var contentType = docAtt.ct;
		if (docAtt.s == 0) {
			zeroSizedAttachments = true;
			continue;
		}
		if (this._attachDialog && inline) {
			if (contentType && contentType.indexOf("image") != -1) {
				var cid = this._generateCid();
				this._htmlEditor.insertImage("cid:" + cid, AjxEnv.isIE);
				msg.addInlineDocAttachment(cid, docAtt.id);
			} else {
				msg.addDocumentAttachment(docAtt);
			}
		}else {
			msg.addDocumentAttachment(docAtt);
		}
	}
	if (zeroSizedAttachments){
		appCtxt.setStatusMsg(ZmMsg.zeroSizedAtts);
	}
};


// Sets the mode ZmHtmlEditor should be in.
ZmComposeView.prototype.setComposeMode =
function(composeMode, switchPreface, dontReplaceContent) {

	if (composeMode == this._composeMode) { return; }
	
	AjxDebug.println(AjxDebug.REPLY, "ZmComposeView::setComposeMode (new mode) - " + composeMode);
	var htmlMode = (composeMode == DwtHtmlEditor.HTML);
	if (!htmlMode || appCtxt.get(ZmSetting.HTML_COMPOSE_ENABLED)) {

		var curMember = (this._composeMode == DwtHtmlEditor.TEXT) ? this._bodyField : this._htmlEditor.getEditorContainer();
		// get these before we change mode so we can find them in current body
		var sig = this.getSignatureContent(sigId);
		var sigSep = this._getSignatureSeparator();
		var sigId = this._controller._currentSignatureId;
		var account = appCtxt.multiAccounts && this.getFromAccount();
		
		this.applySignature(this._htmlEditor.getContent(), sigId, account, null, true); // Remove the signature before switching
		
		this._composeMode = composeMode;
		if (!htmlMode && switchPreface) {
			this._switchPreface();
		}

		var content = this._htmlEditor.getContent();

		if (htmlMode) {

			var anyChar = "[\\s\\S]"; // Includes ALL chars, even newlines
			var baseContent = content || "";

			// Strip away included message
			if (this._msg) {
				var preface = this._getPreface(DwtHtmlEditor.TEXT);
				if (preface) {
					var incMsgRe = new RegExp(AjxStringUtil.regExEscape(preface) + anyChar + "*$");
					baseContent = content.replace(incMsgRe, "");
				}
			}

			baseContent = AjxStringUtil.trim(baseContent);
			
			if (this._action == ZmOperation.DRAFT) { //see below why this is only in case of draft
				baseContent = baseContent.replace(/\n/g, "<br/>");
			}

			// Do the mode switch
			this._htmlEditor.setMode(composeMode, true);
			
			if (this._action != ZmOperation.DRAFT && !dontReplaceContent) {
				baseContent = AjxStringUtil.convertToHtml(baseContent, true);
				baseContent = baseContent.replace(/\n/g,"<br/>");
				// Re-set the whole body, with optional replied/forwarded msg and signature automatically added.
				// baseContent is the text that the user may have written before switching
				this._setBody(this._action, this._msg || null, baseContent, null, true);
			}
		} else {

			var self = this;
			var convertor = {
				"hr": function(el) {
					return ZmComposeView._convertHtmlPreface(self, el);
				},
				"blockquote": function(el) {
					return "\n<blockquote>\n";
				},
				"/blockquote": function(el) {
					return "\n</blockquote>\n";
				},
				"_after": AjxCallback.simpleClosure(this._applyHtmlPrefix, this, "<blockquote>", "</blockquote>")
			};

			this._htmlEditor.setMode(composeMode, true, convertor); // Do the mode switch
			content = this._htmlEditor.getContent(); // Get the content in the new mode
			this.applySignature(content, null, account, sigId, false); // Reapply the signature after switching

		}

		if (htmlMode && switchPreface) {
			this._switchPreface();
		}

		// reset the body field Id and object ref
		this._bodyFieldId = this._htmlEditor.getBodyFieldId();
		this._bodyField = Dwt.byId(this._bodyFieldId);
		if (this._bodyField.disabled) {
			this._bodyField.disabled = false;
		}

		// for now, always reset message body size
		this._resetBodySize();

		// recalculate form value since HTML mode inserts HTML tags
		this._setFormValue();

		// swap new body field into tab group
        var newMember = (composeMode == DwtHtmlEditor.TEXT) ? this._bodyField : this._htmlEditor.getEditorContainer();
		if (curMember && newMember && (curMember != newMember) && this._controller._tabGroup) {
			this._controller._tabGroup.replaceMember(curMember, newMember);
			// focus via replaceMember() doesn't take, try again
			if (composeMode == DwtHtmlEditor.HTML) {
				this._retryHtmlEditorFocus();
			}
		}
		if (!htmlMode) {
			this._moveCaretOnTimer();
		}
	}

	if (this._msg && this._isInline() && composeMode == DwtHtmlEditor.TEXT) {
		this._showForwardField(this._msg, this._action, null, true);
	}
};

ZmComposeView.prototype._retryHtmlEditorFocus =
function() {
	if (this._htmlEditor.hasFocus()) {
		var ta = new AjxTimedAction(this, this._focusHtmlEditor);
		AjxTimedAction.scheduleAction(ta, 10);
	}
};

ZmComposeView.prototype.setDetach =
function(params) {

	this._action = params.action;
	this._msg = params.msg;

	// set the addr fields as populated
	for (var type in params.addrs) {
		this._recipients.setAddress(type, "");
		var addrs = AjxUtil.toArray(params.addrs[type]);
		this._recipients.addAddresses(type, AjxVector.fromArray(addrs));
	}

	this._subjectField.value = params.subj || "";
	this._setPriority(params.priority);
	this.updateTabTitle();

	var content = params.body || "";
	if((content == "") && (this.getComposeMode() == DwtHtmlEditor.HTML)) {
		content	= "<br>";
	}
	this._htmlEditor.setContent(content);


	if (params.forwardHtml) {
		this._attcDiv.innerHTML = params.forwardHtml;
		this._msgAttId = params.msgAttId;
	}
	if (params.identityId && this.identitySelect) {
        var opt = this.identitySelect.getOptionAtIndex(params.identityId);
		this.identitySelect.setSelectedOption(opt);
	}

	this.backupForm = params.backupForm;
	this.sendUID = params.sendUID;

	// bug 14322 -- in Windows Firefox, DEL/BACKSPACE don't work
	// when composing in new window until we (1) enter some text
	// or (2) resize the window (!).  I chose the latter.
	if (AjxEnv.isGeckoBased && AjxEnv.isWindows) {
		window.resizeBy(1, 1);
	}
};

ZmComposeView.prototype.reEnableDesignMode =
function() {
	if (this._composeMode == DwtHtmlEditor.HTML) {
		this._htmlEditor.reEnableDesignMode();
	}
};

// user just saved draft, update compose view as necessary
ZmComposeView.prototype.processMsgDraft =
function(msgDraft) {
	if (this._isInline(msgDraft)) {
		this._handleInline(msgDraft);
	}
	this.reEnableDesignMode();
	this._action = ZmOperation.DRAFT;
	this._msg = msgDraft;
	this._msgAttId = null;
	// always redo att links since user couldve removed att before saving draft
	this.cleanupAttachments(true);
	this._showForwardField(msgDraft, ZmOperation.DRAFT);
	this._resetBodySize();
	// save form state (to check for change later)
	this._setFormValue();
};

ZmComposeView.prototype._handleInline =
function(msgObj) {
	var msg = (msgObj) ? msgObj : this._msg;
	var iDoc = this._htmlEditor._getIframeDoc();
	return (this._fixMultipartRelatedImages(msg,iDoc));
};

ZmComposeView.prototype._fixMultipartRelatedImages_onTimer =
function(msg, account) {
	// first time the editor is initialized, idoc.getElementsByTagName("img") is empty
	// Instead of waiting for 500ms, trying to add this callback. Risky but works.
	if (!this._firstTimeFixImages) {
		this._htmlEditor.addOnContentInitializedListener(new AjxCallback(this, this._fixMultipartRelatedImages, [msg, this._htmlEditor._getIframeDoc(), account]));
	} else {
		this._fixMultipartRelatedImages(msg, this._htmlEditor._getIframeDoc(), account);
	}
};

/**
 * Twiddle the img tags so that the HTML editor can display the images. Instead of
 * a cid (which is relevant only within the MIME msg), point to the img with a URL.
 * 
 * @private
 */
ZmComposeView.prototype._fixMultipartRelatedImages =
function(msg, idoc, account) {
	if (!this._firstTimeFixImages) {
		this._htmlEditor.removeOnContentInitializedListener();
		var self = this; // Fix possible hiccups during compose in new window
		setTimeout(function() {
				self._fixMultipartRelatedImages(msg, self._htmlEditor._getIframeDoc(), account);
		}, 10);
		this._firstTimeFixImages = true;
		return;
	}

	idoc = idoc || this._htmlEditor._getIframeDoc();
	if (!idoc) { return; }

    var showImages = false;
    if(msg) {
        var addr = msg.getAddress(AjxEmailAddress.FROM) || ZmMsg.unknown;
        var sender = msg.getAddress(AjxEmailAddress.SENDER); // bug fix #10652 - check invite if sentBy is set (means on-behalf-of)
        var sentBy = (sender && sender.address) ? sender : addr;
        var sentByAddr = sentBy && sentBy != ZmMsg.unknown ? sentBy.getAddress() : null;
        if (sentByAddr) {
            msg.sentByAddr = sentByAddr;
            msg.sentByDomain = sentByAddr.substr(sentByAddr.indexOf("@")+1);
            showImages = this._isTrustedSender(msg);
        }
    }
    
	var images = idoc.getElementsByTagName("img");
	var num = 0;
	for (var i = 0; i < images.length; i++) {
		var dfsrc = images[i].getAttribute("dfsrc") || images[i].getAttribute("mce_src") || images[i].src;
		if (dfsrc) {
			if (dfsrc.substring(0,4) == "cid:") {
				num++;
				var cid = "<" + dfsrc.substring(4).replace("%40","@") + ">";
				var src = msg && msg.getContentPartAttachUrl(ZmMailMsg.CONTENT_PART_ID, cid);
				if (src) {
                    //Cache cleared, becoz part id's may change.
				    src = src + "&t=" + (new Date()).getTime();
					images[i].src = src;
					images[i].setAttribute("dfsrc", dfsrc);
				}
			} else if (dfsrc.substring(0,4) == "doc:") {
				images[i].src = [appCtxt.get(ZmSetting.REST_URL, null, account), ZmFolder.SEP, dfsrc.substring(4)].join('');
			} else if (dfsrc.indexOf("//") == -1) { // check for content-location verison
				var src = msg && msg.getContentPartAttachUrl(ZmMailMsg.CONTENT_PART_LOCATION, dfsrc);
				if (src) {
                    //Cache cleared, becoz part id's may change.
					src = src + "&t=" + (new Date()).getTime();
					num++;
					images[i].src = src;
					images[i].setAttribute("dfsrc", dfsrc);
				}
			}
            else if(showImages) {
                var src = dfsrc;//x + "&t=" + (new Date()).getTime();
                num++;
                images[i].src = src;
                images[i].setAttribute("dfsrc", dfsrc);
            }
		}
	}
	AjxDebug.println(AjxDebug.REPLY, "ZmComposeView::_fixMultipartRelatedImages - num images: " + images.length);
	return (num == images.length);
};

ZmComposeView.prototype._isTrustedSender =
function(msg) {
    var trustedList = this.getTrustedSendersList();
    if (trustedList.contains(msg.sentByAddr.toLowerCase()) || trustedList.contains(msg.sentByDomain.toLowerCase())){
        return true;
    }
    return false;
};

ZmComposeView.prototype.getTrustedSendersList =
function() {
    return this._controller.getApp().getTrustedSendersList();
};

ZmComposeView.prototype._cleanupFileRefImages =
function(idoc){

    function removeImg(img){
        var parent = img.parentNode;
        parent.removeChild(img);
    }

    if (idoc) {
		var images = idoc.getElementsByTagName("img");
		var len = images.length, fileImgs=[], img, src;
        for (var i = 0; i < images.length; i++) {
            img = images[i];
	        try {
                src = img.src;
	        } catch(e) {
		        //IE8 throws invalid pointer exception for src attribute when src is a data uri
	        }
            if(img && src && src.indexOf('file://') == 0){
                removeImg(img);
                i--; //removeImg reduces the images.length by 1.
            }

        }
    }
};

/**
 * Change the src tags on inline img's to point to cid's, which is what we
 * want for an outbound MIME msg.
 */
ZmComposeView.prototype._restoreMultipartRelatedImages =
function(idoc) {
	if (idoc) {
		var images = idoc.getElementsByTagName("img");
		var num = 0;
		for (var i = 0; i < images.length; i++) {
			var img = images[i];
			var cid = "";
			var dfsrc = img.getAttribute("dfsrc") || img.getAttribute("mce_src");
			if (dfsrc && dfsrc.indexOf("cid:") == 0) {
				cid = dfsrc;
				img.removeAttribute("dfsrc");
			} else if (img.src && img.src.indexOf("cid:") == 0) {
				cid = img.src;
			} else if ( dfsrc && dfsrc.substring(0,4) == "doc:"){
				cid = "cid:"+this._generateCid();
				img.removeAttribute("dfsrc");
				img.setAttribute("doc", dfsrc.substring(4, dfsrc.length));
			} else {
				// If "Display External Images" is false then handle Reply/Forward
				if (dfsrc && (!this._msg || this._msg.showImages))
					//IE: Over HTTPS, http src urls for images might cause an issue.
					try{ img.src = dfsrc; }catch(ex){};
				}
			if (cid) {
				img.setAttribute("dfsrc", cid);
			}
		}
	}
};

ZmComposeView.prototype._cleanupSignatureIds =
function(idoc){
	var signatureEl = idoc && idoc.getElementById(this._controller._currentSignatureId);
	if (signatureEl) {
		signatureEl.removeAttribute("id");
	}
};

ZmComposeView.prototype.showAttachmentDialog =
function(val) {
    if (this._disableAttachments) return;
	var attachDialog = this._attachDialog = appCtxt.getAttachDialog();
	if (val == ZmMsg.myComputer){
		attachDialog.getMyComputerView();
	}else if (val == ZmMsg.briefcase){
		attachDialog.getBriefcaseView();
    }else {
		return;
	}
	var callback = new AjxCallback(this, this._attsDoneCallback, [true]);
	attachDialog.setUploadCallback(callback);
	attachDialog.popup();
	attachDialog.enableInlineOption(this._composeMode == DwtHtmlEditor.HTML);
};

/**
 * Revert compose view to a clean state (usually called before popping compose view).
 * 
 * @param	{Boolean}	bEnableInputs		if <code>true</code>, enable the input fields
 */
ZmComposeView.prototype.reset =
function(bEnableInputs) {

	this.backupForm = null;
	this.sendUID = null;

	// reset autocomplete list
	if (this._acAddrSelectList) {
		this._acAddrSelectList.reset();
		this._acAddrSelectList.show(false);
	}

    this._recipients.reset();

	// reset subject / body fields
	this._subjectField.value = this._subject = "";
	this.updateTabTitle();

	this._htmlEditor.resetSpellCheck();
	this._htmlEditor.clear();

    // this._htmlEditor.clear() resets html editor body filed.
    // Setting this._bodyField to its latest value
    this._bodyField = this._htmlEditor.getBodyField();
	this._bodyContent = "";

	// the div that holds the attc.table and null out innerHTML
	this.cleanupAttachments(true);

	this._resetBodySize();
	this._controller._curIncOptions = null;
	this._msgAttId = null;
	this._clearFormValue();

	// reset dirty shields
	this._noSubjectOkay = this._badAddrsOkay = this._spellCheckOkay = false;

	Dwt.setVisible(this._oboRow, false);

    // Resetting Add attachments from original link option
    Dwt.setVisible(ZmId.getViewId(this._view, ZmId.CMP_REPLY_ATT_ROW), false);

	// remove extra mime parts
	this._extraParts = null;

	// enable/disable input fields
	this.enableInputs(bEnableInputs);

	// reset state of the spell check button
	this._controller.toggleSpellCheckButton(false);

	//reset state of previous Signature cache variable.
	this._previousSignature = null;
	this._previousSignatureMode = null;

	// used by drafts handling in multi-account
	this._origAcctMsgId = null;
};

ZmComposeView.prototype.enableInputs =
function(bEnable) {
	if (this.isHidden) { return; }
    this._recipients.enableInputs(bEnable);
	this._subjectField.disabled = this._bodyField.disabled = !bEnable;
};

/**
 * Adds an extra MIME part to the message. The extra parts will be
 * added, in order, to the end of the parts after the primary message
 * part.
 * 
 * @private
 */
ZmComposeView.prototype.addMimePart =
function(mimePart) {
	if (!this._extraParts) {
		this._extraParts = [];
	}
	this._extraParts.push(mimePart);
};

ZmComposeView.prototype.getSignatureContentSpan =
function(signature, sigContent, account) {
	signature = signature || this.getSignatureById(this._controller.getSelectedSignature(), account);
	if (!signature) { return ""; }

	var signatureId = signature.id;
	sigContent = sigContent || this.getSignatureContent(signatureId);
	if (this._composeMode == DwtHtmlEditor.HTML) {
		sigContent = ["<div id=\"", signatureId, "\">", sigContent, "</div>"].join('');
	}

	return this._getSignatureSeparator() + sigContent;
};

ZmComposeView.prototype._attachSignatureVcard =
function(signatureId) {

	var signature = this.getSignatureById(signatureId);
	if (signature && signature.contactId) {
        if (!this._msg){
            this._msg = new ZmMailMsg();
        }
        if (this._msg._contactAttIds)
            this._msg._contactAttIds.push(signature.contactId);
        else {
            this._msg.setContactAttIds(signature.contactId);
		}
        
        //come back later and see if we need to save the draft
        AjxTimedAction.scheduleAction(new AjxTimedAction(this, this._checkSaveDraft), 500);
	}
};

ZmComposeView.prototype._checkSaveDraft =
function(){
    if (this._msg && this._msg._contactAttIds && this._msg._contactAttIds.length > 0) {
        this._controller.saveDraft(ZmComposeController.DRAFT_TYPE_MANUAL, null, null, null, this._msg._contactAttIds);
    }
};

/**
 * Called when the user selects something from the Signature menu.
 *
 * @param {String}	content				the content
 * @param {String}	replaceSignatureId	the signature id
 * @param {ZmZimbraAccount}	account				the account
 * 
 * @private
 */
ZmComposeView.prototype.applySignature =
function(content, oldSignatureId, account, newSignatureId, skipSave) {

	content = content || "";
	var ac = window.parentAppCtxt || window.appCtxt;
	var acct = account || (appCtxt.multiAccounts && this.getFromAccount());
	var signature = (newSignatureId !== null) ? this.getSignatureById(newSignatureId || this._controller.getSelectedSignature(), acct) : null;
	var isHtml = (this._composeMode == DwtHtmlEditor.HTML);
	var newLine = this._getSignatureNewLine();
	var isAbove = ac.get(ZmSetting.SIGNATURE_STYLE, null, acct) == ZmSetting.SIG_OUTLOOK;
	var done = false, donotsetcontent = false;
	var noSignature = !signature;

	var sigContent, replaceSignature;
	var newSig = signature ? this.getSignatureContent(signature.id) : "";

	if (oldSignatureId) {
		if (isHtml) {
			var idoc = this.getHtmlEditor()._getIframeDoc();
			var sigEl = idoc && idoc.getElementById(oldSignatureId);
			if (sigEl) {
				var newSigContent = this._replaceSignature(sigEl.innerHTML, newSig || "");
				if (newSigContent) {
					sigEl.innerHTML = newSigContent;

					if (signature) {
						sigEl.id = signature.id;
					} else {
						sigEl.removeAttribute("id");
					}
					done = true;
					donotsetcontent = true;
				}
			}
		} else {
			var sigContent = this.getSignatureContent(oldSignatureId);
			var oldSignature = this.getSignatureById(oldSignatureId);
			replaceSignature = (oldSignature && (oldSignature.getContentType() == ZmMimeTable.TEXT_HTML)) ?
				AjxStringUtil.convertHtml2Text(oldSignature.value) : sigContent;
			var sigIndex = content.indexOf(replaceSignature);
			if (sigIndex == -1 && AjxEnv.isWindows) {
				replaceSignature = replaceSignature.replace(/\n/g, "\r\n");
				sigIndex = content.indexOf(replaceSignature);
			}
			var sigLength = replaceSignature && replaceSignature.length || 0;

			if (replaceSignature && (sigIndex != -1)) {
				var contentBefore = content.substring(0, sigIndex).replace(/\s+$/,""); // Get the message content before the signature and cut off any trailing whitespace
				var contentAfter = content.substring(sigIndex + sigLength).replace(/^\s+/,""); // Get the message content after the signature and cut off any leading whitespace

				if (contentAfter) {
					newSig += "\n";
				}
				content = contentBefore + this._getSignatureSeparator() + (newSig || "\n") + contentAfter;
				done = true;
			}
			else {
				if (isAbove) {
					content = this._getSignatureSeparator() + (newSig || "\n") + content;
				}
				else {
					content = content + this._getSignatureSeparator() + (newSig || "\n");
				}
				done = true;
			}
		}
	}
	if (!done && signature) {
		sigContent = this.getSignatureContentSpan(signature);
		content = this._insertSignature(content, ac.get(ZmSetting.SIGNATURE_STYLE, null, acct), sigContent, newLine, signature.getContentType());
	}

	if (!isHtml) {
		this._moveCaretOnTimer();
	}

	if (!donotsetcontent) {
		this._htmlEditor.setContent(content);
	}
	this._fixMultipartRelatedImages_onTimer(this._msg, account);

	//Caching previous Signature state.
	this._previousSignature = signature;
	this._previousSignatureMode = this.getComposeMode();

	var hadVcard = false;
	if (oldSignatureId) {
		// uncheck box for previous vcard att so it gets removed
		var oldSig = this.getSignatureById(oldSignatureId);
		if (oldSig && oldSig.contactId) {
			var vcardPart;
			var atts = this._msg && this._msg.attachments;
			if (atts && atts.length) {
               //we need to figure out what input to uncheck
               if (appCtxt.cacheGet(oldSig.contactId) && appCtxt.cacheGet(oldSig.contactId) instanceof ZmContact) {
                   var sigContact = appCtxt.cacheGet(oldSig.contactId);

                }
				for (var i = 0; i < atts.length && !vcardPart; i++) {
					if (atts[i].contentType == ZmMimeTable.TEXT_VCARD) {
                        //we may have multiple vcards, determine which one to remove based on signature in cache
                        if (sigContact) {
                            var name = atts[i].fileName.substring(0, atts[i].fileName.length - 4)
                            if (name == sigContact._fileAs)
                                vcardPart = atts[i].part
                        }
                        else {
						    vcardPart = atts[i].part;
                        }
					}
				}
			}
			var inputs = document.getElementsByName(ZmComposeView.FORWARD_ATT_NAME + this._sessionId);
			if (inputs && inputs.length) {
				for (var i = 0; i < inputs.length; i++) {
					if (inputs[i].value == vcardPart) {
                        var span = inputs[i].parentNode && inputs[i].parentNode.parentNode;
                        if (span && span.id) {
                            this._removeAttachedMessage(span.id, vcardPart);
                            hadVcard = true;
                        }
					}
				}
			}
		}
	}
	if (signature && signature.contactId) {
		this._attachSignatureVcard(signature.id);
	}
	else if (hadVcard && !skipSave) {
		this._controller.saveDraft(ZmComposeController.DRAFT_TYPE_MANUAL);
	}
};

/*
 * Replaces the contents of a HTML signature block with the contents of another
*/
ZmComposeView.prototype._replaceSignature = 
function(oldSigContent, newSigContent) {
	// find old sig via delimiters, so we preserve any user content that made it into sig span
	var idx = oldSigContent.indexOf(ZmComposeView.SIG_KEY);
	var lastIdx = oldSigContent.lastIndexOf(ZmComposeView.SIG_KEY);
	if (idx == -1 || lastIdx == -1) {
		idx = oldSigContent.indexOf(ZmComposeView.SIG_KEY_LC);
		lastIdx = oldSigContent.lastIndexOf(ZmComposeView.SIG_KEY_LC);
	}
	if (idx != -1 && lastIdx != -1) {
		var nIdx = newSigContent.indexOf(ZmComposeView.SIG_KEY);
		if (nIdx != -1)
			newSigContent = newSigContent.substring(nIdx);
		var nLastIdx = newSigContent.lastIndexOf(ZmComposeView.SIG_KEY);
		if (nLastIdx != -1)
			newSigContent = newSigContent.substring(0, nLastIdx + ZmComposeView.SIG_KEY.length);
		
		newSigContent = oldSigContent.substring(0, idx) + newSigContent +
						oldSigContent.substring(lastIdx + ZmComposeView.SIG_KEY.length);
	}
	return newSigContent;
};

ZmComposeView.prototype.getSignatureContent =
function(signatureId) {
	return this._getSignature(signatureId) || "";
};

/**
 * Adds the user's signature to the message body. An "internet" style signature
 * is prefixed by a special line and added to the bottom. An "outlook" style
 * signature is added before quoted content.
 *
 * This method is only used to add an
 *
 * @content 			optional content to use
 * 
 * @private
 */
ZmComposeView.prototype.addSignature =
function(content) {
	// bug fix #6821 - we need to pass in "content" param
	// since HTML composing in new window doesnt guarantee the html editor
	// widget will be initialized when this code is running.
	content = content || "";
	var ac = window.parentAppCtxt || window.appCtxt;
	var sigContent = this.getSignatureContentSpan();
	var account = appCtxt.multiAccounts && this.getFromAccount();
	content = this._insertSignature(content, ac.get(ZmSetting.SIGNATURE_STYLE, null, account),
									sigContent,
									this._getSignatureNewLine());

	if (this._htmlEditor) {
		this._htmlEditor.setContent(content);
	}

	this._previousSignature = sigContent;
	this._previousSignatureMode = this.getComposeMode();
	
	return content;
};

ZmComposeView.prototype._insertSignature =
function(content, sigStyle, sig, newLine, sigFormat) {

	var re_newlines = "(" + AjxStringUtil.regExEscape(newLine) + ")*";
	// get rid of all trailing newlines
	var re = re_newlines;
	var isHtml = (this.getComposeMode() == DwtHtmlEditor.HTML);
	if (isHtml) {
		re += "</body></html>";
	}
	re += "$";
	re = new RegExp(re, "i");
	content = content.replace(re, '');

	var what = this._controller._curIncOptions && this._controller._curIncOptions.what;
	var hasQuotedContent = (what != ZmSetting.INC_ATTACH && what != ZmSetting.INC_NONE);

	if (sigStyle == ZmSetting.SIG_OUTLOOK && hasQuotedContent) {
		var preface = this._getPreface();
		var re_preface = AjxStringUtil.regExEscape(preface);
		if (isHtml) {
			re_preface = re_preface.replace(/\\\"/g, "\\\"?"); // IE sometimes omits quotes around attrs
			re_preface = re_preface.replace("\\>", "\\s*\\\/?\\>"); // some browsers may put space and / before closing >
		}
		if (isHtml || sigFormat != ZmMimeTable.TEXT_HTML) {
			sig = sig + newLine;
		}
		var regexp = new RegExp(re_newlines + re_preface, "i");
		if (content.match(regexp)) {
			content = content.replace(regexp, [sig, preface].join(""));
		} else {
			// new message
			content = [content, sig].join("");
		}
	} else {
		content = [content, sig].join("");
	}

	return content;
};

ZmComposeView.prototype.dispose =
function() {
	if (this._identityChangeListenerObj) {
		var collection = appCtxt.getIdentityCollection();
		collection.removeChangeListener(this._identityChangeListenerObj);
	}
	DwtComposite.prototype.dispose.call(this);
};

ZmComposeView.prototype.getSignatureById =
function(signatureId, account) {
	signatureId = signatureId || this._controller.getSelectedSignature();
	return appCtxt.getSignatureCollection(account).getById(signatureId);
};

// So we can delimit the actual sig content when switching sigs (bug 46871)
ZmComposeView.SIG_KEY = '<SPAN name="x"></SPAN>';
ZmComposeView.SIG_KEY_LC = ZmComposeView.SIG_KEY.toLowerCase();

ZmComposeView.prototype._getSignature =
function(signatureId) {
	var extraSignature = this._getExtraSignature();
	signatureId = signatureId || this._controller.getSelectedSignature();

	if (!signatureId && extraSignature == "") { return; }

	var signature;

	// for multi-account, search all accounts for this signature ID
	if (appCtxt.multiAccounts) {
		var ac = window.parentAppCtxt || window.appCtxt;
		var list = ac.accountList.visibleAccounts;
		for (var i = 0; i < list.length; i++) {
			var collection = appCtxt.getSignatureCollection(list[i]);
			if (collection) {
				signature = collection.getById(signatureId);
				if (signature) {
					break;
				}
			}
		}
	} else {
		signature = appCtxt.getSignatureCollection().getById(signatureId);
	}

	if (!signature && extraSignature == "") { return; }

	var sigString = "";
	if (signature) {
		var htmlMode = (this._composeMode == DwtHtmlEditor.HTML);
		var mode = htmlMode ? ZmMimeTable.TEXT_HTML : ZmMimeTable.TEXT_PLAIN;
		var sig = signature.getValue(mode);
		var sig1 = htmlMode ? ZmComposeView.SIG_KEY + sig + ZmComposeView.SIG_KEY : sig;
		sigString = sig1 + this._getSignatureNewLine();
	}
	return (sigString + extraSignature);
};

/**
 * Returns "" or extra signature(like a quote or legal disclaimer) via zimlet
 */
ZmComposeView.prototype._getExtraSignature =
function() {
	var extraSignature = "";
	if (appCtxt.zimletsPresent()) {
		var buffer = [];
		appCtxt.notifyZimlets("appendExtraSignature", [buffer]);
		extraSignature = buffer.join(this._getSignatureNewLine());
		if (extraSignature != "") {
			extraSignature = this._getSignatureNewLine() + extraSignature;
		}
	}
	return extraSignature;
};

ZmComposeView.prototype._getSignatureSeparator =
function() {
	var ac = window.parentAppCtxt || window.appCtxt;
	var newLine = this._getSignatureNewLine();
	var sep = newLine + newLine;
	var account = appCtxt.multiAccounts && this.getFromAccount();
	if (ac.get(ZmSetting.SIGNATURE_STYLE, null, account) == ZmSetting.SIG_INTERNET) {
		sep += "-- " + newLine;
	}
	return sep;
};

ZmComposeView.prototype._getSignatureNewLine =
function() {
	return ((this._composeMode == DwtHtmlEditor.HTML) ? "<br>" : "\n");
};

ZmComposeView.prototype._getSignatureIdForAction =
function(identity, action) {

	identity = identity || this.getIdentity();
	action = action || this._action;
	var field = (ZmComposeController.IS_REPLY[action] || ZmComposeController.IS_FORWARD[action]) ? ZmIdentity.REPLY_SIGNATURE : ZmIdentity.SIGNATURE;
	return identity && identity.getField(field);
};

/**
* Returns true if form contents have changed, or if they are empty.
*
* @param incAddrs		takes addresses into consideration
* @param incSubject		takes subject into consideration
* 
* @private
*/
ZmComposeView.prototype.isDirty =
function(incAddrs, incSubject) {
	// reply/forward and empty body => not dirty
	if ((this._action != ZmOperation.NEW_MESSAGE) && (this._htmlEditor.getContent().match(ZmComposeView.EMPTY_FORM_RE))) {
		return false;
	}

	if (this._isDirty) { return true; }

	var curFormValue = this._formValue(incAddrs, incSubject);

	// empty subject and body => not dirty
    if (curFormValue.match(ZmComposeView.EMPTY_FORM_RE) || (this._composeMode == DwtHtmlEditor.HTML && !this._htmlEditor.isDirty()) )
	{
		return false;
	}

	if (this._composeMode == DwtHtmlEditor.HTML) {
		var lower = function(match) {
			return match.toLowerCase();
		};

		var origFormValue = AjxStringUtil.trim(this._origFormValue.replace(ZmComposeView.HTML_TAG_RE, lower)); // Make sure HTML tag names & parameters are lowercase for both values in the comparison
		curFormValue = AjxStringUtil.trim(curFormValue.replace(ZmComposeView.HTML_TAG_RE, lower)); // so that <SPAN> and <span> are still equal, but <span>FOO</span> and <span>foo</span> are not.

		return (curFormValue != origFormValue);
	}
	return (curFormValue != this._origFormValue);
};

ZmComposeView.prototype._removeAttachedFile  =
function(spanId, attachmentPart){
	var node = document.getElementById(spanId)
	var parent = node && node.parentNode;
    this._attachCount--;

    if (parent){
		parent.removeChild(node);
    }

    if (attachmentPart){
        for (var i =0; i < this._msg.attachments.length; i++){
            if (this._msg.attachments[i].part == attachmentPart){
               this._msg.attachments.splice(i, 1);
               break;
            }
        }
    }

    var dndTooltip = document.getElementById(ZmId.getViewId(this._view, ZmId.CMP_DND_TOOLTIP));
    if (!parent.childNodes.length){
        this._attcDiv.innerHTML = "";
        if (dndTooltip){
            dndTooltip.innerHTML = ZmMsg.dndTooltip;
            dndTooltip.style.display = "block";
        }
    }else{
        if (dndTooltip){
            dndTooltip.style.display = "none";
        }
    }
};

ZmComposeView.prototype._removeAttachedMessage =
function(spanId, id){
    if (!id){// Forward/Reply one message
        this._msgAttId = null;
    } else if (this._msgIds && this._msgIds.length && (this._msgIds.indexOf(id) != -1)){
        this._msgIds.splice(this._msgIds.indexOf(id), 1); // Remove message frm attached messages
    }

    this._removeAttachedFile(spanId);
};

ZmComposeView.prototype.cleanupAttachments =
function(all) {
	var attachDialog = this._attachDialog;
	if (attachDialog && attachDialog.isPoppedUp()) {
		attachDialog.popdown();
	}

	if (all) {
		this._attcDiv.innerHTML = "";
		this._attcDiv.style.height = "";
		this._attachCount = 0;
	}

	// make sure att IDs don't get reused
	if (this._msg) {
		this._msg.attId = null;
        this._msg._contactAttIds = [];
	}
};

ZmComposeView.prototype.sendMsgOboIsOK =
function() {
	return (Dwt.getVisible(this._oboRow)) ? this._oboCheckbox.checked : false;
};

ZmComposeView.prototype.updateTabTitle =
function() {
	var buttonText = this._subjectField.value
		? this._subjectField.value.substr(0, ZmAppViewMgr.TAB_BUTTON_MAX_TEXT)
		: ZmComposeController.DEFAULT_TAB_TEXT;
	appCtxt.getAppViewMgr().setTabTitle(this._controller.getCurrentViewId(), buttonText);
};

/**
 * Used in multi-account mode to determine which account this composer is
 * belongs to.
 */
ZmComposeView.prototype.getFromAccount =
function() {
	var ac = window.parentAppCtxt || window.appCtxt;
	return this._fromSelect
		? (ac.accountList.getAccount(this._fromSelect.getSelectedOption().accountId))
		: (ac.accountList.defaultAccount || ac.accountList.activeAccount || ac.accountList.mainAccount);
};

// Private / protected methods

// Consistent spot to locate various dialogs
ZmComposeView.prototype._getDialogXY =
function() {
	var loc = Dwt.toWindow(this.getHtmlElement(), 0, 0);
	return new DwtPoint(loc.x + ZmComposeView.DIALOG_X, loc.y + ZmComposeView.DIALOG_Y);
};

ZmComposeView.prototype._getForwardAttObjs =
function(parts) {
    var forAttObjs = [];
    for (var i = 0; i < this._partToAttachmentMap.length; i++) {
        for (var j = 0; j < parts.length; j++) {
            if (this._partToAttachmentMap[i].part === parts[j]) {
                forAttObjs.push( { part : parts[j], mid : this._partToAttachmentMap[i].mid } );
                break;
            }
        }
    }
    return forAttObjs;
};

ZmComposeView.prototype._getForwardAttIds =
function(name, removeOriginalAttachments) {
	var forAttIds = [];
	var forAttList = document.getElementsByName(name);

	// walk collection of input elements
	for (var i = 0; i < forAttList.length; i++) {
			var part = forAttList[i].value;
            if (this._partToAttachmentMap.length && removeOriginalAttachments){
			    var att = this._partToAttachmentMap[i].part;
			    var original = this._originalAttachments[att.label];
			    original = original && [att.sizeInBytes];
                if (removeOriginalAttachments && original) {
				    continue;
			    }
            }
			forAttIds.push(part);
	}

	return forAttIds;
};

/*
 * Set various address headers based on the original message and the mode we're in.
 * Make sure not to duplicate any addresses, even across fields. Figures out what
 * addresses to put in To: and Cc: unless the caller passes addresses to use (along
 * with their type).
 * 
 * @param {string}				action		compose action
 * @param {string}				type		address type
 * @param {AjxVector|array}		override	addresses to use
 */
ZmComposeView.prototype._setAddresses =
function(action, type, override) {

	this._action = action;

	if (override) {
		this._recipients.addAddresses(type, override);
	}
	else {
		var identityId = this.identitySelect && this.identitySelect.getValue();
		var addresses = ZmComposeView.getReplyAddresses(action, this._msg, this._addressesMsg, identityId);
		if (addresses) {
			var toAddrs = addresses[AjxEmailAddress.TO];
			if (!(toAddrs && toAddrs.length)) {
				// make sure we have at least one TO address if possible
				var addrVec = this._addressesMsg.getAddresses(AjxEmailAddress.TO);
				addresses[AjxEmailAddress.TO] = addrVec.getArray().slice(0, 1);
			}
			for (var i = 0; i < ZmMailMsg.COMPOSE_ADDRS.length; i++) {
				var type = ZmMailMsg.COMPOSE_ADDRS[i];
				this._recipients.addAddresses(type, addresses[type]);
			}
		}
	}
};

ZmComposeView.getReplyAddresses =
function(action, msg, addrsMsg, identityId) {
	
	addrsMsg = addrsMsg || msg;
	var addresses = {};
	if ((action == ZmOperation.NEW_MESSAGE) || !msg || !addrsMsg) {
		return null;
	}
	
	ZmComposeController._setStatics();
	if (ZmComposeController.IS_REPLY[action]) {
		var ac = window.parentAppCtxt || window.appCtxt;

		// Prevent user's login name and aliases from becoming recipient addresses
		var userAddrs = {};
		var account = appCtxt.multiAccounts && msg.getAccount();
		var uname = ac.get(ZmSetting.USERNAME, null, account);
		if (uname) {
			userAddrs[uname.toLowerCase()] = true;
		}
		var aliases = ac.get(ZmSetting.MAIL_ALIASES, null, account);
		for (var i = 0, count = aliases.length; i < count; i++) {
			userAddrs[aliases[i].toLowerCase()] = true;
		}

		// Check for canonical addresses
		var defaultIdentity = ac.getIdentityCollection(account).defaultIdentity;
		if (defaultIdentity && defaultIdentity.sendFromAddress) {
			// Note: sendFromAddress is same as appCtxt.get(ZmSetting.USERNAME)
			// if the account does not have any canonical address assigned.
			userAddrs[defaultIdentity.sendFromAddress.toLowerCase()] = true;
		}

		// When updating address lists, use addresses msg instead of msg, because
		// msg changes after a draft is saved.
		var isDefaultIdentity = !identityId || (identityId && (defaultIdentity.id == identityId)); 
		var addrVec = addrsMsg.getReplyAddresses(action, userAddrs, isDefaultIdentity, true);
		addresses[AjxEmailAddress.TO] = addrVec ? addrVec.getArray() : [];
		if (action == ZmOperation.REPLY_ALL || action == ZmOperation.CAL_REPLY_ALL) {
			var toAddrs = addrsMsg.getAddresses(AjxEmailAddress.TO, userAddrs, false, true);
			var ccAddrs = addrsMsg.getAddresses(AjxEmailAddress.CC, userAddrs, false, true);
			toAddrs.addList(ccAddrs);
			addresses[AjxEmailAddress.CC] = toAddrs.getArray();
		}
	} else if (action == ZmOperation.DRAFT || action == ZmOperation.SHARE) {
		for (var i = 0; i < ZmMailMsg.COMPOSE_ADDRS.length; i++) {
			var type = ZmMailMsg.COMPOSE_ADDRS[i];
			var addrs = msg.getAddresses(type);
			addresses[type] = addrs ? addrs.getArray() : [];
		}
	} else if (action == ZmOperation.DECLINE_PROPOSAL) {
        var toAddrs = addrsMsg.getAddresses(AjxEmailAddress.FROM);
		addresses[AjxEmailAddress.TO] = toAddrs ? toAddrs.getArray() : [];
    }
	
	// Make a pass to remove duplicate addresses
	var addresses1 = {}, used = {};
	for (var i = 0; i < ZmMailMsg.COMPOSE_ADDRS.length; i++) {
		var type = ZmMailMsg.COMPOSE_ADDRS[i];
		var addrs1 = addresses1[type] = [];
		var addrs = addresses[type];
		if (addrs && addrs.length) {
			for (var j = 0, len = addrs.length; j < len; j++) {
				var addr = addrs[j];
				if (!used[addr.address]) {
					addrs1.push(addr);
				}
				used[addr.address] = true;
			}
		}
	}
	return addresses1;
};

ZmComposeView.prototype._setSubject =
function(action, msg, subjOverride) {

	if ((action == ZmOperation.NEW_MESSAGE && subjOverride == null)) {
		return;
	}

	var subj = subjOverride || ( (msg) ? msg.subject : "" );

	if (action == ZmOperation.REPLY_CANCEL && !subj) {
		var inv = (msg) ? msg.invite : null;
		if (inv) {
			subj = inv.getName();
		}
	}

	if (action != ZmOperation.DRAFT && subj) {
		subj = ZmMailMsg.stripSubjectPrefixes(subj);
	}

	var prefix = "";
	switch (action) {
		case ZmOperation.CAL_REPLY:
		case ZmOperation.CAL_REPLY_ALL:
		case ZmOperation.REPLY:
		case ZmOperation.REPLY_ALL: 		prefix = "Re: "; break;
		case ZmOperation.REPLY_CANCEL: 		prefix = ZmMsg.cancelled + ": "; break;
		case ZmOperation.FORWARD_INLINE:
		case ZmOperation.FORWARD_ATT: 		prefix = "Fwd: "; break;
		case ZmOperation.REPLY_ACCEPT:		prefix = ZmMsg.subjectAccept + ": "; break;
		case ZmOperation.REPLY_DECLINE:		prefix = ZmMsg.subjectDecline + ": "; break;
		case ZmOperation.REPLY_TENTATIVE:	prefix = ZmMsg.subjectTentative + ": "; break;
		case ZmOperation.REPLY_NEW_TIME:	prefix = ZmMsg.subjectNewTime + ": "; break;
	}
	
	subj = this._subject = prefix + (subj || "");
	if (this._subjectField) {
		this._subjectField.value = subj;
		this.updateTabTitle();
	}
};

ZmComposeView.prototype._setBody =
function(action, msg, extraBodyText) {
	
	AjxDebug.println(AjxDebug.REPLY, "ZmComposeView::_setBody");
	var htmlMode = (this._composeMode == DwtHtmlEditor.HTML);

	var isDraft = (action == ZmOperation.DRAFT);
	if (msg && msg.isInvite() && ZmComposeController.IS_FORWARD[action]) {
		action = this._action = ZmOperation.FORWARD_INLINE;
	}

	// get reply/forward prefs as necessary
	var incOptions = this._controller._curIncOptions;
    var ac = window.parentAppCtxt || window.appCtxt;
	if (!incOptions) {
		if (ZmComposeController.IS_REPLY[action]) {
			incOptions = {what:		ac.get(ZmSetting.REPLY_INCLUDE_WHAT),
						  prefix:	ac.get(ZmSetting.REPLY_USE_PREFIX),
						  headers:	ac.get(ZmSetting.REPLY_INCLUDE_HEADERS)};
		} else if (isDraft) {
			incOptions = {what:		ZmSetting.INC_BODY};
		} else if (action == ZmOperation.FORWARD_INLINE) {
			incOptions = {what:		ZmSetting.INC_BODY,
						  prefix:	ac.get(ZmSetting.FORWARD_USE_PREFIX),
						  headers:	ac.get(ZmSetting.FORWARD_INCLUDE_HEADERS)};
		} else if (action == ZmOperation.FORWARD_ATT) {
			incOptions = {what:		ZmSetting.INC_ATTACH};
		} else if (action == ZmOperation.DECLINE_PROPOSAL) {
			incOptions = {what:		ZmSetting.INC_BODY};
		} else if (action == ZmOperation.NEW_MESSAGE) {
			incOptions = {what:		ZmSetting.INC_NONE};
		} else {
			incOptions = {};
		}
		this._controller._curIncOptions = incOptions;	// pointer, not a copy
	}
	if (incOptions.what == ZmSetting.INC_ATTACH && !this._msg) {
		incOptions.what = ZmSetting.INC_NONE;
	}
	AjxDebug.println(AjxDebug.REPLY, "Inc options: " + [incOptions.what, incOptions.prefix, incOptions.headers].join(" / ")); 
	
	// make sure we've loaded the part with the type we want to reply in, if it's available
	if (msg && (incOptions.what == ZmSetting.INC_BODY || incOptions.what == ZmSetting.INC_SMART)) {
		var desiredPartType = htmlMode ? ZmMimeTable.TEXT_HTML : ZmMimeTable.TEXT_PLAIN;
		msg.getBodyPart(desiredPartType, this._setBody1.bind(this, action, msg, extraBodyText));
	}
	else {
		this._setBody1(action, msg, extraBodyText);
	}
};

ZmComposeView.prototype._setBody1 =
function(action, msg, extraBodyText) {

	var htmlMode = (this._composeMode == DwtHtmlEditor.HTML);
	var isDraft = (action == ZmOperation.DRAFT);
	var incOptions = this._controller._curIncOptions;
    var ac = window.parentAppCtxt || window.appCtxt;
	
	var crlf = htmlMode ? "<br>" : ZmMsg.CRLF;
	var crlf2 = htmlMode ? "<br><br>" : ZmMsg.CRLF2;

	var sigPre = "", body = "", headers = [], preface = "", value = "";

	var bodyInfo = {};
	var what = incOptions.what;
	if (msg && (what == ZmSetting.INC_BODY || what == ZmSetting.INC_SMART)) {
		bodyInfo = this._getBodyContent(msg, htmlMode, what);
		body = bodyInfo.body;
		AjxDebug.println(AjxDebug.REPLY, "Body length: " + body.length);
		// Bug 7160: Strip off the ~*~*~*~ from invite replies.
		if (ZmComposeController.IS_INVITE_REPLY[action]) {
			body = body.replace(ZmItem.NOTES_SEPARATOR, "");
		}
	}

	var sigStyle, sig, sigId, sigFormat;
	var account = ac.multiAccounts && this.getFromAccount();
	if (ac.get(ZmSetting.SIGNATURES_ENABLED, null, account)) {
		sig = this.getSignatureContentSpan(null, null, account);
		sigStyle = sig && ac.get(ZmSetting.SIGNATURE_STYLE, null, account);
		AjxDebug.println(AjxDebug.REPLY, "Sig style: " + sigStyle);
		sigId = this._controller.getSelectedSignature();
		var signature = this.getSignatureById(sigId);
		sigFormat = signature && signature.getContentType();
	}
	if (sigStyle == ZmSetting.SIG_OUTLOOK) {
		sigPre = (sigFormat == ZmMimeTable.TEXT_PLAIN) ? sig + crlf : sig;
	}

	extraBodyText = extraBodyText || "";
	if (htmlMode) {
		extraBodyText = extraBodyText.replace(/\n/g, "<br>");
	}

	var preText;
	if (sigPre) {
		if (extraBodyText) {
			if (htmlMode && !this.isHidden) {
				var fragment = document.createElement("div");
				fragment.innerHTML = extraBodyText;
				var sigEl = Dwt.byId(sigId, fragment);
				if (sigEl) { // Signature found in extraBodyText. Replace signature content
					sigEl.innerHTML = this._replaceSignature(sigEl.innerHTML, sigPre);
					preText = fragment.innerHTML;
				} else { // Signature not found in extraBodyText. Append signature content
					preText = extraBodyText + sigPre;
				}
			} else { // Remove existing signature from text message
				extraBodyText = extraBodyText.replace(new RegExp(AjxStringUtil.regExEscape(sigPre)+"[\\s\\S]*","i"),"");
				preText = extraBodyText + sigPre;
				preText += crlf;
			}
			this.applySignature(extraBodyText, sigId, account, null, true); // Put in new signature
		} else { // There is no pre-existing text. Just append the signature
			preText = sigPre;
		}
	} else { // No signature, just take the extraBodyText
        preText = extraBodyText ? extraBodyText + crlf2 : "";
	}
	AjxDebug.println(AjxDebug.REPLY, "preText: " + AjxStringUtil.htmlEncode(preText));

	if (incOptions.headers && msg) {
		for (var i = 0; i < ZmComposeView.QUOTED_HDRS.length; i++) {
			var hdr = msg.getHeaderStr(ZmComposeView.QUOTED_HDRS[i], htmlMode);
			if (hdr) {
				headers.push(hdr);
			}
		}
	}

	this._msgAttId = null; //clear it in case of switching from "as attachment" back to "include original message" or to "don't include original"

	if (action == ZmOperation.REPLY_CANCEL) {
	  	value += crlf + sigPre;
	} else if (incOptions.what == ZmSetting.INC_NONE) {
		value = preText;
	} else if (incOptions.what == ZmSetting.INC_ATTACH && this._msg) {
		value = preText;
		this._msgAttId = this._msg.id;
	} else {
		var preface = this._preface = this._getPreface();
		var divider = (!body && !headers) ? "" : htmlMode ? preface : preface + crlf;
		AjxDebug.println(AjxDebug.REPLY, "divider: " + AjxStringUtil.htmlEncode(divider));
		var leadingSpace = preText ? "" : crlf2;
		var wrapParams = ZmHtmlEditor.getWrapParams(htmlMode, incOptions);
		wrapParams.preserveReturns = true;
		if (incOptions.what == ZmSetting.INC_BODY) {
			if (isDraft) {
				value = body;
			} else if (htmlMode) {
				var headerText = headers.length ? headers.join(crlf) + crlf2 : "";
				wrapParams.text = isDraft ? body : headerText + body;
				var bodyText = AjxStringUtil.wordWrap(wrapParams);
				AjxDebug.println(AjxDebug.REPLY, "bodyText length: " + bodyText.length);
				value = leadingSpace + preText + divider + bodyText;
				AjxDebug.println(AjxDebug.REPLY, "value length A: " + value.length);
			} else {
				var headerText = "";
				if (headers.length) {
					var text = wrapParams.text = headers.join(crlf) + (incOptions.prefix ? crlf : crlf2);
					wrapParams.len = 120; // headers tend to be longer
					headerText = incOptions.prefix ? AjxStringUtil.wordWrap(wrapParams) : text;
				}
				wrapParams.text = body;
				wrapParams.len = ZmHtmlEditor.WRAP_LENGTH;
				var bodyText = incOptions.prefix ? AjxStringUtil.wordWrap(wrapParams) : body;
				value = leadingSpace + preText + divider + headerText + bodyText;
			}
		} else if (incOptions.what == ZmSetting.INC_SMART) {
			if (htmlMode) {
				var headerText = headers.length ? headers.join(crlf) + crlf2 : "";
				wrapParams.text = isDraft ? body : headerText + body;
				var bodyText = AjxStringUtil.wordWrap(wrapParams);
				value = leadingSpace + preText + divider + bodyText;
			} else {
				var headerText = "";
				if (headers.length) {
					var text = wrapParams.text = headers.join(crlf) + (incOptions.prefix ? crlf : crlf2);
					wrapParams.len = 120; // headers tend to be longer
					headerText = incOptions.prefix ? AjxStringUtil.wordWrap(wrapParams) : text;
				}
				wrapParams.text = body;
				wrapParams.len = ZmHtmlEditor.WRAP_LENGTH;
				var bodyText = incOptions.prefix ? AjxStringUtil.wordWrap(wrapParams) : body;
				value = leadingSpace + preText + divider + headerText + bodyText;
			}
		}
	}

	if (!this.isHidden) {
		var isHtmlEditorInitd = this._htmlEditor.isHtmlModeInited();
		if (!isHtmlEditorInitd) {
			this._fixMultipartRelatedImages_onTimer(msg);
		}
	}

	var vLen = value ? value.length : 0;
	AjxDebug.println(AjxDebug.REPLY, "value length B: " + vLen);
	if (!isDraft && sigStyle == ZmSetting.SIG_INTERNET) {
		AjxDebug.println(AjxDebug.REPLY, "internet style sig, call addSignature()");
		value = this.addSignature(value);
	} else {
		value = value || (htmlMode ? "<br>" : "");
		AjxDebug.println(AjxDebug.REPLY, "value snippet: " + AjxStringUtil.htmlEncode(value.substr(0, 200)));
		if (!this.isHidden) {
			this._htmlEditor.setContent(value);
		}
	}

	if (isHtmlEditorInitd) {
		this._fixMultipartRelatedImages_onTimer(msg);
	}

	var hasInlineImages = (bodyInfo && bodyInfo.hasInlineImages) || !ac.get(ZmSetting.VIEW_AS_HTML);
	if (!this.isHidden) {
		this._showForwardField(msg || this._msg, action, incOptions, hasInlineImages, bodyInfo && bodyInfo.hasInlineAtts);
	}

	if (sigId && !isDraft) {
        this._attachSignatureVcard(sigId);
	}
	
	if (this.isHidden && this._composeMode == DwtHtmlEditor.HTML) {
		// wrap <html> and <body> tags around content, and set font style
		value = ZmAdvancedHtmlEditor._embedHtmlContent(value, true);
	}
	
	this._bodyContent = value;
};

ZmComposeView.prototype.getUnQuotedContent =
function() {
	var body = this._htmlEditor.getContent();
	var prefaceIndex = body.indexOf(this._getPreface());
	if (prefaceIndex == -1 && AjxEnv.isIE) {
		//TODO: Please make this better.  IE innerHTML value is returning <HR id=zwch> -- uppercase HR and no quotes around id
		//which causes match to fail w/out this check
		prefaceIndex = body.toUpperCase().indexOf(this._getPreface().replace(/\"/g,"").toUpperCase());
	}
	if (prefaceIndex != -1) {
		body = body.substring(0, prefaceIndex);
	}
	if (this.getComposeMode() == DwtHtmlEditor.HTML) {
		body = AjxStringUtil.htmlPlatformIndependent(body);
	}
	return body;
};

ZmComposeView.prototype._getBodyContent =
function(msg, htmlMode, incWhat) {

	var body, bodyPart, hasInlineImages, hasInlineAtts;
	var crlf = htmlMode ? "<br>" : ZmMsg.CRLF;
	var crlf2 = htmlMode ? "<br><br>" : ZmMsg.CRLF2;
	var getOrig = (incWhat == ZmSetting.INC_SMART);

	var content;
	
	// bug fix #7271 - if we have multiple body parts, append them all first
	var parts = msg.getBodyParts();
	if (msg.hasMultipleBodyParts()) {
		var bodyArr = [];
		for (var k = 0; k < parts.length; k++) {
			var part = parts[k];
			// bug: 28741
			if (ZmMimeTable.isRenderableImage(part.contentType)) {
				bodyArr.push([crlf, "[", part.contentType, ":", (part.fileName || "..."), "]", crlf].join(""));
				hasInlineImages = true;
			} else if (part.fileName && part.contentDisposition == "inline") {   //Inline attachments
				var attInfo = ZmMimeTable.getInfo(part.contentType);
				attInfo = attInfo ? attInfo.desc : part.contentType;
				bodyArr.push([crlf, "[", attInfo, ":", (part.fileName||"..."), "]", crlf].join(""));
				hasInlineAtts = true;
			} else if (part.contentType == ZmMimeTable.TEXT_PLAIN || (part.body && ZmMimeTable.isTextType(part.contentType))) {
				content = getOrig ? AjxStringUtil.getOriginalContent(part.getContent(), false) : part.getContent();
				bodyArr.push( htmlMode ? AjxStringUtil.convertToHtml(content) : content );
			} else if (part.contentType == ZmMimeTable.TEXT_HTML) {
				content = getOrig ? AjxStringUtil.getOriginalContent(part.getContent(), true) : part.getContent();
				if (htmlMode){
					bodyArr.push(content);
				} else {
					var div = document.createElement("div");
					div.innerHTML = content;
					bodyArr.push(AjxStringUtil.convertHtml2Text(div));
				}
			}
		}
		body = bodyArr.join(crlf);
	} else {
		// at this point, we should have the type of part we want if we're dealing with multipart/alternative
		if (htmlMode) {
			content = msg.getBodyContent(ZmMimeTable.TEXT_HTML);
			if (!content) {
				// just grab the first body part and convert it to HTML
				content = AjxStringUtil.convertToHtml(msg.getBodyContent());
			}
			body = getOrig ? AjxStringUtil.getOriginalContent(content, true) : content;
		} else {
			hasInlineImages = msg.hasInlineImagesInMsgBody();
			bodyPart = msg.getTextBodyPart();
			if (bodyPart) {
				// cool, got a textish body part
				content = bodyPart.getContent();
			}
			else {
				// if we can find an HTML body part, convert it to text
				var html = msg.getBodyContent(ZmMimeTable.TEXT_HTML, true);
				content = html ? this._htmlToText(html) : "";
			}
			content = content || msg.getBodyContent();	// just grab first body part
			body = getOrig ? AjxStringUtil.getOriginalContent(content, false) : content;
		}
	}

	body = body || "";
	
	if (bodyPart && AjxUtil.isObject(bodyPart) && bodyPart.isTruncated) {
		body += crlf2 + ZmMsg.messageTruncated + crlf2;
	}
	
	if (this.isHidden && this.getComposeMode() == DwtHtmlEditor.HTML) {
		// strip wrapper tags from original msg
		body = body.replace(/<\/?(html|head|body)[^>]*>/gi, '');
	}

	return {body:body, bodyPart:bodyPart, hasInlineImages:hasInlineImages, hasInlineAtts:hasInlineAtts};
};

ZmComposeView.prototype._htmlToText =
function(html) {
	
	var text = "";
	
	// create a temp iframe to create a proper DOM tree
	var params = {parent: appCtxt.getShell(), hidden: true, html: html};
	var dwtIframe = new DwtIframe(params);
	if (html && dwtIframe) {
		var self = this;
		var convertor = {"hr":
			function(el) {
				return ZmComposeView._convertHtmlPreface(self, el);
			}
		}
		text = AjxStringUtil.convertHtml2Text(dwtIframe.getDocument().body, convertor);
		var dwtEl = appCtxt.getShell().getHtmlElement().removeChild(dwtIframe.getHtmlElement());
		delete dwtEl;
		delete dwtIframe;
	}
	
	return text;
};

ZmComposeView.prototype._getPreface =
function(mode, action) {

	mode = mode || this._composeMode;
	action = action || this._action;
	var preface;
	if (mode == DwtHtmlEditor.HTML) {
		preface = '<hr id="' + AjxStringUtil.HTML_SEP_ID + '">';
	} else {
		var msgText = (action == ZmOperation.FORWARD_INLINE) ? AjxMsg.forwardedMessage : AjxMsg.origMsg;
		preface = [ZmMsg.DASHES, " ", msgText, " ", ZmMsg.DASHES].join("");
	}
	return preface;
};

// if mode isn't given, assumes mode has already been switched internally
ZmComposeView.prototype._switchPreface =
function(mode) {

	mode = mode || this._composeMode;
	var htmlMode = (mode == DwtHtmlEditor.HTML);
	var otherMode = htmlMode ? DwtHtmlEditor.TEXT : DwtHtmlEditor.HTML;
	var curPreface = this._getPreface(otherMode);
	var newPreface = this._getPreface(mode);
	if (!htmlMode) {
		newPreface = newPreface + "<br>";	// so that text preface is followed by a return after conversion
		this._switchedToText = true;
	} else if (this._switchedToText) {
		curPreface = new RegExp(curPreface + "\\s*<br>");
	}
	var content = this.getHtmlEditor().getContent();
	content = content.replace(curPreface, newPreface);
	this._htmlEditor.setContent(content);
	this._preface = newPreface;
};

// for getting text version of HTML part when sending, used by AjxStringUtil._traverse
ZmComposeView._convertHtmlPreface =
function(self, el) {
	return (el && el.id == AjxStringUtil.HTML_SEP_ID) ? self._getPreface(DwtHtmlEditor.TEXT) + ZmMsg.CRLF : null;
};

ZmComposeView.prototype._applyHtmlPrefix =
function(tagStart, tagEnd, text) {
	var incOptions = this._controller._curIncOptions;
	if (incOptions && incOptions.prefix) {
		var wrapParams = ZmHtmlEditor.getWrapParams(false, incOptions);
		wrapParams.preserveReturns = true;

		var lines = text.split("\n");
		var level = 0;
		var out = [];
		var k = 0;
		for (var i=0; i<lines.length; i++) {
			var line = lines[i];
			if (line==tagStart) {
				level++;
			} else if (line==tagEnd) {
				level--;
			} else {
				if (line=="") {
					var lastLine = lines[i-1];
					if (lastLine && (lastLine != tagStart && lastLine != tagEnd)) {
						out[k++] = line;
					}
				} else {
					wrapParams.len = ZmHtmlEditor.WRAP_LENGTH;
					for (var j=0; j<level; j++) {
						wrapParams.text = line;
						line = AjxStringUtil.wordWrap(wrapParams);
					}
					line = line.replace(/^\n|\n$/,"");
					out[k++] = line;
				}
			}
		}
		return out.join("\n");
	} else {
		return text.replace(tagStart,"").replace(tagEnd,"");
	}
}



ZmComposeView.prototype.resetBody =
function(action, msg, extraBodyText, incOptions) {
	this.cleanupAttachments(true);
	this._isDirty = this._isDirty || this.isDirty();
	this._setBody(action, msg, extraBodyText, incOptions);
	this._setFormValue();
	this._resetBodySize();
};

// Generic routine for attaching an event handler to a field. Since "this" for the handlers is
// the incoming event, we need a way to get at ZmComposeView, so it's added to the event target.
ZmComposeView.prototype._setEventHandler =
function(id, event, addrType) {
	var field = document.getElementById(id);
	field._composeView = this._internalId;
	if (addrType) {
		field._addrType = addrType;
	}
	var lcEvent = event.toLowerCase();
	field[lcEvent] = ZmComposeView["_" + event];
};

ZmComposeView.prototype._setBodyFieldCursor =
function(extraBodyText) {
	if (this._composeMode == DwtHtmlEditor.HTML) { return; }

	// this code moves the cursor to the beginning of the body
	if (AjxEnv.isIE) {
		var tr = this._bodyField.createTextRange();
		if (extraBodyText) {
			tr.move('character', extraBodyText.length + 1);
		} else {
			tr.collapse(true);
		}
		tr.select();
	} else {
		var index = extraBodyText ? (extraBodyText.length + 1) : 0;
		Dwt.setSelectionRange(this._bodyField, index, index);
	}
};

ZmComposeView.prototype.isTinyMCEEnabled =
function() {
    // Bug 54805
    // disable tinymce
    //return window.isTinyMCE;
    //return true;
    return appCtxt.isTinyMCEEnabled();
};

/**
 * This should be called only once for when compose view loads first time around
 * 
 * @private
 */
ZmComposeView.prototype._initialize =
function(composeMode) {

	this._internalId = AjxCore.assignId(this);

	// init html
	this._createHtml();

    // init drag and drop
    this._initDragAndDrop();

	// init compose view w/ based on user prefs
	var bComposeEnabled = appCtxt.get(ZmSetting.HTML_COMPOSE_ENABLED);
	var composeFormat = appCtxt.get(ZmSetting.COMPOSE_AS_FORMAT);
	var defaultCompMode = bComposeEnabled && composeFormat == ZmSetting.COMPOSE_HTML
		? DwtHtmlEditor.HTML : DwtHtmlEditor.TEXT;
	this._composeMode = composeMode || defaultCompMode;
	this._clearFormValue();

	// init html editor
	if (this.isTinyMCEEnabled()) {
		this._htmlEditor = new ZmAdvancedHtmlEditor(this, DwtControl.RELATIVE_STYLE, null, this._composeMode);
		this._bodyFieldId = this._htmlEditor.getBodyFieldId();
		this._bodyField = document.getElementById(this._bodyFieldId);
	} else {
		this._htmlEditor = new ZmHtmlEditor(this, DwtControl.RELATIVE_STYLE, null, this._composeMode, null);
		this._bodyFieldId = this._htmlEditor.getBodyFieldId();
		this._bodyField = document.getElementById(this._bodyFieldId);
	}
	this._includedPreface = "";

	// misc. inits
	this.setScrollStyle(DwtControl.SCROLL);
	this._attachCount = 0;

	// init listeners
	this.addControlListener(new AjxListener(this, this._controlListener));
};

ZmComposeView.prototype._createHtml =
function(templateId) {

	var data = {
		id:					this._htmlElId,
		headerId:			ZmId.getViewId(this._view, ZmId.CMP_HEADER),
		fromSelectId:		ZmId.getViewId(this._view, ZmId.CMP_FROM_SELECT),
		toRowId:			ZmId.getViewId(this._view, ZmId.CMP_TO_ROW),
		toPickerId:			ZmId.getViewId(this._view, ZmId.CMP_TO_PICKER),
		toInputId:			ZmId.getViewId(this._view, ZmId.CMP_TO_INPUT),
		toCellId:			ZmId.getViewId(this._view, ZmId.CMP_TO_CELL),
		ccRowId:			ZmId.getViewId(this._view, ZmId.CMP_CC_ROW),
		ccPickerId:			ZmId.getViewId(this._view, ZmId.CMP_CC_PICKER),
		ccInputId:			ZmId.getViewId(this._view, ZmId.CMP_CC_INPUT),
		ccCellId:			ZmId.getViewId(this._view, ZmId.CMP_CC_CELL),
		bccRowId:			ZmId.getViewId(this._view, ZmId.CMP_BCC_ROW),
		bccPickerId:		ZmId.getViewId(this._view, ZmId.CMP_BCC_PICKER),
		bccInputId:			ZmId.getViewId(this._view, ZmId.CMP_BCC_INPUT),
		bccToggleId:		ZmId.getViewId(this._view, ZmId.CMP_BCC_TOGGLE),
		bccCellId:			ZmId.getViewId(this._view, ZmId.CMP_BCC_CELL),
		subjectRowId:		ZmId.getViewId(this._view, ZmId.CMP_SUBJECT_ROW),
		subjectInputId:		ZmId.getViewId(this._view, ZmId.CMP_SUBJECT_INPUT),
		oboRowId:			ZmId.getViewId(this._view, ZmId.CMP_OBO_ROW),
		oboCheckboxId:		ZmId.getViewId(this._view, ZmId.CMP_OBO_CHECKBOX),
		oboLabelId:			ZmId.getViewId(this._view, ZmId.CMP_OBO_LABEL),
		identityRowId:		ZmId.getViewId(this._view, ZmId.CMP_IDENTITY_ROW),
		identitySelectId:	ZmId.getViewId(this._view, ZmId.CMP_IDENTITY_SELECT),
		priorityId:			ZmId.getViewId(this._view, ZmId.CMP_PRIORITY),
        replyAttRowId:      ZmId.getViewId(this._view, ZmId.CMP_REPLY_ATT_ROW),
		attRowId:		    ZmId.getViewId(this._view, ZmId.CMP_ATT_ROW),
		attDivId:			ZmId.getViewId(this._view, ZmId.CMP_ATT_DIV),
		attBtnId:			ZmId.getViewId(this._view, ZmId.CMP_ATT_BTN),
		zdndToolTipId:		ZmId.getViewId(this._view, ZmId.CMP_DND_TOOLTIP),
		acAddrBubbles:		this._useAcAddrBubbles
	};

	this._createHtmlFromTemplate(templateId || this.TEMPLATE, data);
};
ZmComposeView.prototype._addSendAsAndSendOboAddresses  =
function(menu){

    var optData = null;
    var displayName = appCtxt.getUsername();
    for(var i=0;i<appCtxt.sendAsEmails.length;i++){
        var email = appCtxt.sendAsEmails[i];
        optData = new DwtSelectOptionData(email,email);
        menu.addOption(optData);
    }

    for(var i=0;i<appCtxt.sendOboEmails.length;i++){
        var email = appCtxt.sendOboEmails[i];
        optData =new DwtSelectOptionData(email, displayName + " " + ZmMsg.sendOnBehalfOf + " "  + email);
        optData.obo = true;
        menu.addOption(optData);

    }

};

ZmComposeView.prototype._createHtmlFromTemplate =
function(templateId, data) {

	DwtComposite.prototype._createHtmlFromTemplate.call(this, templateId, data);

	// global identifiers
	this._identityDivId = data.identityRowId;

    this._recipients.createRecipientHtml(this, this._view, data.id, ZmMailMsg.COMPOSE_ADDRS, data.bccToggleId);
    this._acAddrSelectList = this._recipients.getACAddrSelectList();

	// save reference to DOM objects per ID's
	this._headerEl = document.getElementById(data.headerId);
	this._subjectField = document.getElementById(data.subjectInputId);
	this._oboRow = document.getElementById(data.oboRowId);
	this._oboCheckbox = document.getElementById(data.oboCheckboxId);
	this._oboLabel = document.getElementById(data.oboLabelId);
	this._attcDiv = document.getElementById(data.attDivId);
	this._attcBtn = document.getElementById(data.attBtnId);

	this._setEventHandler(data.subjectInputId, "onKeyUp");
	this._setEventHandler(data.subjectInputId, "onBlur");
	this._setEventHandler(data.subjectInputId, "onFocus");

	if (appCtxt.multiAccounts) {
		if (!this._fromSelect) {
			this._fromSelect = new DwtSelect({parent:this, id:this.getHTMLElId() + "_fromSelect", parentElement:data.fromSelectId});
			//this._addSendAsAndSendOboAddresses(this._fromSelect);
            this._fromSelect.addChangeListener(new AjxListener(this, this._handleFromListener));
            this._recipients.attachFromSelect(this._fromSelect);
		}
	} else {
		// initialize identity select
		var identityOptions = this._getIdentityOptions();
		this.identitySelect = new DwtSelect({parent:this, id:this.getHTMLElId() + "_identitySelect", options:identityOptions});
		this._addSendAsAndSendOboAddresses(this.identitySelect);
        this.identitySelect.setToolTipContent(ZmMsg.chooseIdentity, true);

		if (!this._identityChangeListenerObj) {
			this._identityChangeListenerObj = new AjxListener(this, this._identityChangeListener);
		}
		var ac = window.parentAppCtxt || window.appCtxt;
		var accounts = ac.accountList.visibleAccounts;
		for (var i = 0; i < accounts.length; i++) {
			var identityCollection = ac.getIdentityCollection(accounts[i]);
			identityCollection.addChangeListener(this._identityChangeListenerObj);
		}

		this.identitySelect.replaceElement(data.identitySelectId);
		this._setIdentityVisible();
	}

	if (appCtxt.get(ZmSetting.MAIL_PRIORITY_ENABLED)) {
		var buttonId = ZmId.getButtonId(this._view, ZmId.CMP_PRIORITY);
		this._priorityButton = new DwtButton({parent:this, id:buttonId});
		this._priorityButton.setMenu(new AjxCallback(this, this._priorityButtonMenuCallback));
		this._priorityButton.reparentHtmlElement(data.priorityId);
		this._priorityButton.setToolTipContent(ZmMsg.setPriority, true);
	}
    var attButtonId = ZmId.getButtonId(this._view, ZmId.CMP_ATT_BTN);
	this._attButton = new DwtButton({parent:this, id:attButtonId});
	this._attButton.setText(ZmMsg.attach);
    this._attButton.addSelectionListener(function(){});
    if (AjxEnv.supportsHTML5File){
        var styleStr = "";
        var node = this._attButton.getHtmlElement().getElementsByClassName("ZWidgetTitle")[0];

        var newData = {
            fileInputId : ZmId.getViewId(this._view, ZmId.CMP_ATT_INP)
        };
        node.innerHTML = AjxTemplate.expand("mail.Message#MailAttachmentAttachBtn", newData);
        var fileInputNode = node.getElementsByClassName("BrowseAttachBtn")[0];
        var attachTextWidth = node.getElementsByClassName("attach_text")[0].clientWidth;
        if (fileInputNode && attachTextWidth){
            if (AjxEnv.isFirefox){
                fileInputNode.style.right = (fileInputNode.clientWidth - attachTextWidth) + "px";
                if (fileInputNode.parentNode){
                	fileInputNode.parentNode.style.maxWidth = attachTextWidth + "px";
                }
            }
            fileInputNode.style.maxWidth = attachTextWidth;
        }
        this._attcBtnFileInpId = newData.fileInputId;
    } else {
            this._attButton.addSelectionListener(function(event){
                var curView = appCtxt.getAppViewMgr().getCurrentView();
                curView.collapseAttMenu();
                curView.showAttachmentDialog(ZmMsg.myComputer);
             });
    }
    this._attButton.setMenu(new AjxCallback(this, this._attachButtonMenuCallback));
	this._attButton.reparentHtmlElement(data.attBtnId);
	this._attButton.setToolTipContent(ZmMsg.attach, true);
};

ZmComposeView.prototype._initDragAndDrop =
function() {
    this._dnd = new ZmDragAndDrop(this);
};

ZmComposeView.prototype.collapseAttMenu =
function() {
    var menu = this._attButton && this._attButton.getMenu();
    menu.popdown();
};

ZmComposeView.prototype._handleFromListener =
function(ev) {
	var newVal = ev._args.newValue;
	var oldVal = ev._args.oldValue;
	if (oldVal == newVal) { return; }

	var ac = window.parentAppCtxt || window.appCtxt;
	var newOption = this._fromSelect.getOptionWithValue(newVal);
	var newAccount = ac.accountList.getAccount(newOption.accountId);
	var collection = ac.getIdentityCollection(newAccount);
	var identity = collection && collection.getById(newVal);

	var sigId = this._getSignatureIdForAction(identity || collection.defaultIdentity) || "";

	this._controller._accountName = newAccount.name;
	this._controller.resetSignatureToolbar(sigId, newAccount);
	this._controller.resetSignature(sigId, newAccount);
	this._controller._resetReadReceipt(newAccount);

	// reset account for autocomplete to use
	if (this._acAddrSelectList) {
		this._acAddrSelectList.setActiveAccount(newAccount);
	}

	// if this message is a saved draft, check whether it needs to be moved
	// based on newly selected value.
	if (this._msg && this._msg.isDraft) {
		var oldOption = this._fromSelect.getOptionWithValue(oldVal);
		var oldAccount = ac.accountList.getAccount(oldOption.accountId);

		// cache old info so we know what to delete after new save
		var msgId = this._origAcctMsgId = this._msg.id;

		this._msg = this._addressesMsg = null;
		var callback = new AjxCallback(this, this._handleMoveDraft, [oldAccount.name, msgId]);
		this._controller.saveDraft(this._controller._draftType, null, null, callback);
	}

	this._recipients.resetPickerButtons(newAccount);
};

ZmComposeView.prototype._handleMoveDraft =
function(accountName, msgId) {
	var jsonObj = {
		ItemActionRequest: {
			_jsns:  "urn:zimbraMail",
			action: { id:msgId, op:"delete" }
		}
	};
	var params = {
		jsonObj: jsonObj,
		asyncMode: true,
		accountName: accountName
	};
	appCtxt.getAppController().sendRequest(params);
};


ZmComposeView.prototype._createAttachMenuItem =
function(menu, text, listner) {
	var item = DwtMenuItem.create({parent:menu, text:text});
	item.value = text;
	if (listner)
	item.addSelectionListener(listner);
	return item;
};
ZmComposeView.prototype._createPriorityMenuItem =
function(menu, text, flag) {
	var item = DwtMenuItem.create({parent:menu, imageInfo:this._getPriorityImage(flag), text:text});
	item._priorityFlag = flag;
	item.addSelectionListener(this._priorityMenuListnerObj);
};

ZmComposeView.prototype._priorityButtonMenuCallback =
function() {
	var menu = new DwtMenu({parent:this._priorityButton});
	this._priorityMenuListnerObj = new AjxListener(this, this._priorityMenuListner);
	this._createPriorityMenuItem(menu, ZmMsg.high, ZmItem.FLAG_HIGH_PRIORITY);
	this._createPriorityMenuItem(menu, ZmMsg.normal, "");
	this._createPriorityMenuItem(menu, ZmMsg.low, ZmItem.FLAG_LOW_PRIORITY);
	return menu;
};

ZmComposeView.prototype._startUploadAttachment =
function() {
	this._attButton.setEnabled(false);
    this.enableToolbarButtons(this._controller, false);
	this._controller._uploadingProgress = true;
    Dwt.setVisible(ZmId.getViewId(this._view, ZmId.CMP_DND_TOOLTIP), false);

};

ZmComposeView.prototype.checkAttachments =
function(){
    if (!this._attachCount) return;
    Dwt.setVisible(ZmId.getViewId(this._view, ZmId.CMP_DND_TOOLTIP), false);
};

ZmComposeView.prototype.updateAttachFileNode =
function(files,index) {
    var curFileName = AjxStringUtil.htmlEncode(files[index].name.substr(-32));
    var prevFileName = AjxStringUtil.htmlEncode(files[index-1].name.substr(-32));

    this._loadingSpan.firstChild.innerHTML = curFileName;
    this._loadingSpan.firstChild.nextSibling.innerHTML = curFileName;

    var element = document.createElement("span");
    element.innerHTML = AjxTemplate.expand("mail.Message#MailAttachmentBubble", {fileName:prevFileName});

    if (this._loadingSpan.nextSibling)
        this._loadingSpan.parentNode.insertBefore(element.firstChild,this._loadingSpan.nextSibling);
    else
       this._loadingSpan.parentNode.appendChild(element);

};

ZmComposeView.prototype.enableToolbarButtons =
function(controller, value){ // value : true or false
    if(appCtxt.get(ZmSetting.MAIL_SEND_LATER_ENABLED))
        controller._toolbar.getButton("SEND_MENU").setEnabled(value);
    else
        controller._toolbar.getButton("SEND").setEnabled(value);
    if (controller._toolbar.getButton("SAVE_DRAFT"))
        controller._toolbar.getButton("SAVE_DRAFT").setEnabled(value);
};

ZmComposeView.prototype.enableAttachButton =
function(option){
    if (this._attButton){
       this._attButton.setEnabled(option);
        var attachElement = this._attButton.getHtmlElement();
        var node = attachElement && attachElement.getElementsByTagName("input");
        if (node && node.length){
            node[0].disabled = !(option);
        }
    }

    this._disableAttachments = !(option);

    Dwt.setVisible(ZmId.getViewId(this._view, ZmId.CMP_DND_TOOLTIP),option);
};


ZmComposeView.prototype._resetUpload =
function(err) {
	this._attButton.setEnabled(true);
    this.enableToolbarButtons(this._controller, true);
    this._setAttInline(false);
    this._controller._uploadingProgress = false;
    if (this._controller._uploadAttReq){
		this._controller._uploadAttReq = null;
	}

    if (this._msgIds){
      this._msgIds = [];
    }

	if(this.si){
		clearTimeout(this.si);
	}
    if (err === true && this._loadingSpan){
		this._loadingSpan.parentNode.removeChild(this._loadingSpan);
        this._controller.saveDraft(ZmComposeController.DRAFT_TYPE_AUTO);// Save the previous state
	}

    if(this._loadingSpan){
		this._loadingSpan = null;
	}

    if (this._uploadElementForm){
        this._uploadElementForm.reset();
        this._uploadElementForm = null;
    }
};

ZmComposeView.prototype._uploadDoneCallback =
function(resp) {
	var response = resp && resp.length && resp[2];
	this._controller.saveDraft(ZmComposeController.DRAFT_TYPE_AUTO, response, null, callback);
};


ZmComposeView.prototype._uploadFileProgress =
function(progress){
	if (!this._loadingSpan ||  (!progress.lengthComputable) ){ 
		return;
        }
	var span1 = this._loadingSpan.childNodes[0];
	var span2 = this._loadingSpan.childNodes[1];
	span1.style.width = ((span2.offsetWidth) *  (progress.loaded / progress.total)) + "px";
};

ZmComposeView.prototype._abortUploadFile =
function(){
	if (this._controller._uploadAttReq)
		this._controller._uploadAttReq.abort();
	this._resetUpload(true);
};

ZmComposeView.prototype._progress =
function() {
    var span1 = this._loadingSpan.firstChild;
    var span2 = this._loadingSpan.firstChild.nextSibling;
    span1.style.width = ((span1.offsetWidth + 1) % span2.offsetWidth) + "px";
};

ZmComposeView.prototype._initProgressSpan =
function(fileName) {
    if (fileName.length > 35)
        fileName = fileName.substr(-35);

    fileName = AjxStringUtil.htmlEncode(fileName);

    var node = this._attcDiv.getElementsByTagName("span") && this._attcDiv.getElementsByTagName("span")[0];
    if (node){
        var element = document.createElement("span");
        element.innerHTML = AjxTemplate.expand("mail.Message#MailAttachmentBubble", {fileName:fileName});
        node.parentNode.insertBefore(element.firstChild,node);
    } else {
        this._attcDiv.innerHTML = AjxTemplate.expand("mail.Message#UploadProgress",{fileName:fileName});
    }
    this._loadingSpan = this._attcDiv.getElementsByTagName("span")[0];
};


ZmComposeView.prototype._submitMyComputerAttachments =
function(files, node) {
    var size = 0;
    var name = "";
    if (!AjxEnv.supportsHTML5File){
        // IE, FF 3.5 and lower
        this.showAttachmentDialog(ZmMsg.myComputer);
        return;
    }

    if (files) {
        for (var j = 0; j < files.length; j++) {
            var file = files[j];
            size += file.size || file.fileSize; /*Safari*/;
            if ((-1 /* means unlimited */ != appCtxt.get(ZmSetting.ATTACHMENT_SIZE_LIMIT)) &&
                (size > appCtxt.get(ZmSetting.ATTACHMENT_SIZE_LIMIT))) {
                var msgDlg = appCtxt.getMsgDialog();
                var errorMsg = AjxMessageFormat.format(ZmMsg.attachmentSizeError, AjxUtil.formatSize(appCtxt.get(ZmSetting.ATTACHMENT_SIZE_LIMIT)));
                msgDlg.setMessage(errorMsg, DwtMessageDialog.WARNING_STYLE);
                msgDlg.popup();
                return false;
            }
        }
    }
    this._uploadElementForm = node.parentNode;
    this._setAttInline(node.isInline);
    this._initProgressSpan(files[0].name);

    this._controller._uploadMyComputerFile(files);

};

ZmComposeView.prototype._checkMenuItems =
function(menuItem) {
    var isHTML = (this._composeMode === DwtHtmlEditor.HTML);
    menuItem.setEnabled(isHTML);
    document.getElementById(this._attcBtnInlineFileInpId).disabled = !isHTML;
};

ZmComposeView.prototype._attachButtonMenuCallback =
function() {
	var menu = new DwtMenu({parent:this._attButton});
    var div = document.createElement("DIV");
    if (!AjxEnv.supportsHTML5File){
        mi = this._createAttachMenuItem(menu, ZmMsg.myComputer, new AjxListener(this, this.showAttachmentDialog,[ZmMsg.myComputer]) );
    } else {
        var mi = this._createAttachMenuItem(menu, ZmMsg.myComputer);
        div.innerHTML = AjxTemplate.expand("mail.Message#MailAttachmentMyComputer");
        mi.getHtmlElement().appendChild(div.firstChild);
    }


    if (AjxEnv.supportsHTML5File){
        div = document.createElement("DIV");
        var mi = this._createAttachMenuItem(menu, ZmMsg.attachInline);
        var data = {
            inlineFileInputId : ZmId.getViewId(this._view, ZmId.CMP_ATT_INLINE_INP)
        };
        this._attcBtnInlineFileInpId = data.inlineFileInputId;
        div.innerHTML = AjxTemplate.expand("mail.Message#MailAttachmentMyComputer", data);
        var fromElement = div.firstChild;
        fromElement.firstChild.style.top = "22px";
        fromElement.firstChild.isInline = true;
        mi.getHtmlElement().appendChild(div.firstChild);
        menu.addPopupListener(new AjxListener(this, this._checkMenuItems,[mi]));
    }

	if (appCtxt.multiAccounts || appCtxt.get(ZmSetting.BRIEFCASE_ENABLED)){
		mi = this._createAttachMenuItem(menu, ZmMsg.briefcase, new AjxListener(this, this.showAttachmentDialog, [ZmMsg.briefcase]) );
	}
	appCtxt.notifyZimlets("initializeAttachPopup", [menu, this], {waitUntilLoaded:true});

	return menu;
};

ZmComposeView.prototype._getPriorityImage =
function(flag) {
	if (flag == ZmItem.FLAG_HIGH_PRIORITY)	{ return "PriorityHigh_list"; }
	if (flag == ZmItem.FLAG_LOW_PRIORITY)	{ return "PriorityLow_list"; }
	return "PriorityNormal_list";
};

ZmComposeView.prototype._priorityMenuListner =
function(ev) {
	this._setPriority(ev.dwtObj._priorityFlag);
};

ZmComposeView.prototype._getPriority =
function() {
	return (this._priorityButton)
		? (this._priorityButton._priorityFlag || "") : "";
};

ZmComposeView.prototype._setPriority =
function(flag) {
	if (this._priorityButton) {
		flag = flag || "";
		this._priorityButton.setImage(this._getPriorityImage(flag));
		this._priorityButton._priorityFlag = flag;
	}
};

ZmComposeView.prototype._getIdentityOptions =
function() {
	var options = [];
	var identityCollection = appCtxt.getIdentityCollection();
	var identities = identityCollection.getIdentities(true);
	for (var i = 0, count = identities.length; i < count; i++) {
		var identity = identities[i];
		options.push(new DwtSelectOptionData(identity.id, this._getIdentityText(identity)));
	}
	return options;
};

ZmComposeView.prototype._getIdentityText =
function(identity, account) {
	var name = identity.name;
	if (identity.isDefault && name == ZmIdentity.DEFAULT_NAME) {
		name = account ? account.getDisplayName() : ZmMsg.accountDefault;
	}

	// default replacement parameters
	var defaultIdentity = appCtxt.getIdentityCollection().defaultIdentity;
	var addr = (identity.sendFromAddressType == ZmSetting.SEND_ON_BEHALF_OF) ? (appCtxt.getUsername() + " " + ZmMsg.sendOnBehalfOf + " " + identity.sendFromAddress) : identity.sendFromAddress;
	var params = [
		name,
		(identity.sendFromDisplay || ""),
		addr,
		ZmMsg.accountDefault,
		appCtxt.get(ZmSetting.DISPLAY_NAME),
		defaultIdentity.sendFromAddress
	];

	// get appropriate pattern
	var pattern;
	if (identity.isDefault) {
		pattern = ZmMsg.identityTextPrimary;
	}
	else if (identity.isFromDataSource) {
		var ds = appCtxt.getDataSourceCollection().getById(identity.id);
		if (params[1] == "") {
			params[1] = ds.userName || "";
		}
		params[2] = ds.getEmail();
		var provider = ZmDataSource.getProviderForAccount(ds);
		if (provider) {
			pattern = ZmMsg["identityText-"+provider.id];
		}
		else if (params[0] && params[1] && params[2] &&
				(params[0] != params[1] != params[2]))
		{
			pattern = ZmMsg.identityTextPersona;
		}
		else {
			pattern = ZmMsg.identityTextExternal;
		}
	}
	else {
		pattern = ZmMsg.identityTextPersona;
	}

	// format text
	return AjxMessageFormat.format(pattern, params);
};

ZmComposeView.prototype._identityChangeListener =
function(ev) {

	if (!this.identitySelect) { return; }

	var identity = ev.getDetail("item");
	if (!identity) { return; }
	if (ev.event == ZmEvent.E_CREATE) {
		// TODO: add identity in sort position
		this._setIdentityVisible();
		var text = this._getIdentityText(identity);
		var option = new DwtSelectOptionData(identity.id, text);
		this.identitySelect.addOption(option);
	} else if (ev.event == ZmEvent.E_DELETE) {
		this.identitySelect.removeOptionWithValue(identity.id);
		this._setIdentityVisible();
	} else if (ev.event == ZmEvent.E_MODIFY) {
		// TODO: see if it was actually name that changed
		// TODO: re-sort list
		var text = this._getIdentityText(identity);
		this.identitySelect.rename(identity.id, text);
	}
};

ZmComposeView.prototype._setIdentityVisible =
function() {
	var div = document.getElementById(this._identityDivId);
	if (!div) return;

	var visible = this.identitySelect.getOptionCount() > 1;
	Dwt.setVisible(div, visible);
};

ZmComposeView.prototype.getIdentity =
function() {
	var ac = window.parentAppCtxt || window.appCtxt;

	if (appCtxt.multiAccounts) {
		var newVal = this._fromSelect.getValue();
		var newOption = this._fromSelect.getOptionWithValue(newVal);
		var newAccount = ac.accountList.getAccount(newOption.accountId);
		var collection = ac.getIdentityCollection(newAccount);
		return collection && collection.getById(newVal);
	}

	if (this.identitySelect) {
		var collection = ac.getIdentityCollection();
		var val = this.identitySelect.getValue();
		var identity = collection.getById(val);
        return identity;
	}
};

ZmComposeView.prototype._showForwardField =
function(msg, action, incOptions, includeInlineImages, includeInlineAtts) {

	var html = "";
	var attIncludeOrigLinkId = null;
    this._partToAttachmentMap = [];
    var appCtxt = window.parentAppCtxt || window.appCtxt
    var messages = [];

	if (!this._originalAttachmentsInitialized ){  //only the first time we determine which attachments are original
		this._originalAttachments = []; //keep it associated by label and size (label => size => true) since that's the only way the client has to identify attachments from previous msg version.
		this._hideOriginalAttachments = msg && msg.hasAttach && (action == ZmOperation.REPLY || action == ZmOperation.REPLY_ALL);
	}
	if (!(this._msgIds && this._msgIds.length) &&
		((incOptions && incOptions.what == ZmSetting.INC_ATTACH) || action == ZmOperation.FORWARD_ATT))
	{
		html = AjxTemplate.expand("mail.Message#ForwardOneMessage", {message:msg});
		this._attachCount = 1;
	}
	else if (msg && (msg.hasAttach || includeInlineImages || includeInlineAtts || (action == ZmComposeView.ADD_ORIG_MSG_ATTS)))
	{
		var attInfo = msg.getAttachmentInfo(false, includeInlineImages, includeInlineAtts);

        if (action == ZmComposeView.ADD_ORIG_MSG_ATTS){
            if (this._replyAttachments !== this._msg.attachments) {
                attInfo = attInfo.concat(this._replyAttachInfo);
                this._msg.attachments = this._msg.attachments.concat(this._replyAttachments);
            }
                this._replyAttachInfo = this._replyAttachments = [];
                Dwt.setVisible(ZmId.getViewId(this._view, ZmId.CMP_REPLY_ATT_ROW), false);
        } else if (action == ZmOperation.REPLY || action == ZmOperation.REPLY_ALL){
            if (attInfo && attInfo.length) {
                this._replyAttachInfo = attInfo;
                this._replyAttachments = this._msg.attachments;
                this._attachCount = 0;
                Dwt.setVisible(ZmId.getViewId(this._view, ZmId.CMP_REPLY_ATT_ROW), true);
            }

            return;
        }

		if (attInfo.length > 0) {
			for (var i = 0; i < attInfo.length; i++) {
				var att = attInfo[i];
				var params = {
					att:	    att,
					id:		    [this._view, att.part, ZmMailMsgView.ATT_LINK_MAIN].join("_"),
					text:	    AjxStringUtil.clipFile(att.label, 30),
					mid:        att.mid,
					rfc822Part: att.rfc822Part
				};
				att.link = ZmMailMsgView.getMainAttachmentLinkHtml(params);
				this._partToAttachmentMap[i] = att;
				if (!this._originalAttachmentsInitialized) {
					if (!this._originalAttachments[att.label]) {
						this._originalAttachments[att.label] = [];
					}
					this._originalAttachments[att.label][att.sizeInBytes] = true;
				}
			}
			attIncludeOrigLinkId = ZmId.getViewId(this._view, ZmId.CMP_ATT_INCL_ORIG_LINK);


            if (action == ZmComposeView.ADD_ORIG_MSG_ATTS){
                action = this._action;
            }
            if (this._msgIds && this._msgIds.length) {
                for (var i = 0; i < this._msgIds.length; i++) {
                    var message = appCtxt.cacheGet(this._msgIds[i]);
                    if (!message) continue;
                    messages.push(message);
                };
            }

            var data = {
				attachments:		attInfo,
                messages:           messages,
                messagesFwdFieldName: (ZmComposeView.FORWARD_MSG_NAME + this._sessionId),
				isNew:				(action == ZmOperation.NEW_MESSAGE),
				isForward:			(action == ZmOperation.FORWARD),
				isForwardInline:	(action == ZmOperation.FORWARD_INLINE),
				isDraft: 			(action == ZmOperation.DRAFT),
				hideOriginalAttachments: this._hideOriginalAttachments,
				attIncludeOrigLinkId:	attIncludeOrigLinkId,
				originalAttachments: this._originalAttachments,
				fwdFieldName:		(ZmComposeView.FORWARD_ATT_NAME + this._sessionId)
			};
			html = AjxTemplate.expand("mail.Message#ForwardAttachments", data);
            this._attachCount = attInfo.length + messages.length;
            this.checkAttachments();
		}
	} else if (this._msgIds && this._msgIds.length) {
		// use main window's appCtxt
		for (var i = 0; i < this._msgIds.length; i++) {
			var message = appCtxt.cacheGet(this._msgIds[i]);
			if (!message) continue;
			messages.push(message);
		}
		var data = {
			messages: messages,
			fwdFieldName: (ZmComposeView.FORWARD_MSG_NAME + this._sessionId)
		};
		html = AjxTemplate.expand("mail.Message#ForwardMessages", data);
		this._attachCount = messages.length;
	}

	this._originalAttachmentsInitialized  = true; //ok, done setting it for the first time.

	this._attcDiv.innerHTML = html;
	// include original attachments
	if (attIncludeOrigLinkId){
		this._attIncludeOrigLinkEl = document.getElementById(attIncludeOrigLinkId);
		if (this._attIncludeOrigLinkEl) {
			Dwt.setHandler(this._attIncludeOrigLinkEl, DwtEvent.ONCLICK, AjxCallback.simpleClosure(this._includeOriginalAttachments, this));
		}
	}
};

ZmComposeView.prototype._includeOriginalAttachments =
function(ev, force) {
	this._hideOriginalAttachments = false;
	this._isIncludingOriginalAttachments = true;
	this._showForwardField(this._msg, this._action, null, true);
};


// Miscellaneous methods
ZmComposeView.prototype._resetBodySize =
function() {
	var size = this.getSize();
	if (size.x <= 0 || size.y <= 0) { return; }

	var height = size.y - Dwt.getSize(this._headerEl).y;
	if (height != size.y) {
		this._htmlEditor.setSize(size.x, height);
	}
};


ZmComposeView.prototype._setFromSelect =
function(msg) {
	if (!this._fromSelect) { return; }

	this._fromSelect.clearOptions();

	var ac = window.parentAppCtxt || window.appCtxt;
	var identity;
	var active = ac.getActiveAccount();
	var accounts = ac.accountList.visibleAccounts;

	for (var i = 0; i < accounts.length; i++) {
		var acct = accounts[i];
		if (appCtxt.isOffline && acct.isMain) { continue; }

		var identities = ac.getIdentityCollection(acct).getIdentities();
		if (ac.isFamilyMbox || ac.get(ZmSetting.OFFLINE_SMTP_ENABLED, null, acct)) {
			for (var j = 0; j < identities.length; j++) {
				identity = identities[j];

				var text = this._getIdentityText(identity, acct);
				var icon = appCtxt.isOffline ? acct.getIcon() : null;
				var option = new DwtSelectOption(identity.id, false, text, null, null, icon);
				option.addr = new AjxEmailAddress(identity.sendFromAddress, AjxEmailAddress.FROM, identity.sendFromDisplay);
				option.accountId = acct.id;

				this._fromSelect.addOption(option);
			}
		}
	}

	var selectedIdentity;
	if (msg) {
		var coll = ac.getIdentityCollection(msg.getAccount());
		selectedIdentity = (msg.isDraft)
			? coll.selectIdentity(msg, AjxEmailAddress.FROM)
			: coll.selectIdentity(msg);
		if (!selectedIdentity) {
			selectedIdentity = coll.defaultIdentity;
		}
	}

	if (!selectedIdentity) {
		selectedIdentity = ac.getIdentityCollection(active).defaultIdentity;
	}

    if(selectedIdentity && selectedIdentity.id) {
        this._fromSelect.setSelectedValue(selectedIdentity.id);
    }

	// for cross account searches, the active account isn't necessarily the
	// account of the selected conv/msg so reset it based on the selected option.
    // if active-account is local/main acct, reset it based on selected option.
	if ((appCtxt.getSearchController().searchAllAccounts && this._fromSelect) || active.isMain) {
		active = this.getFromAccount();
		this._controller._accountName = active.name;
	}

	if (this._acAddrSelectList) {
		this._acAddrSelectList.setActiveAccount(active);
	}

	this._recipients.resetPickerButtons(active);
};



// Returns a string representing the form content
ZmComposeView.prototype._formValue =
function(incAddrs, incSubject) {
	var vals = [];
	if (incAddrs) {
		for (var i = 0; i < ZmMailMsg.COMPOSE_ADDRS.length; i++) {
			var type = ZmMailMsg.COMPOSE_ADDRS[i];
			if (this._recipients.getUsing(type)) {
				vals.push(this._recipients.getAddrFieldValue(type));
			}
		}
	}
	if (incSubject) {
		vals.push(this._subjectField.value);
	}
	var content = this._htmlEditor.getContent();
	AjxDebug.println(AjxDebug.REPLY, "ZmComposeView::_formValue - content length: " + content.length);
	vals.push(content);
	var str = vals.join("|");
	str = str.replace(/\|+/, "|");
	return str;
};

// Listeners


ZmComposeView.prototype._controlListener =
function() {
	this._resetBodySize();
};


// Callbacks

// this callback is triggered when an event occurs inside the html editor (when in HTML mode)
// it is used to set focus to the To: field when user hits the TAB key
ZmComposeView.prototype._htmlEditorEventCallback =
function(args) {
	var rv = true;
	if (args.type == "keydown") {
		var key = DwtKeyEvent.getCharCode(args);
		if (key == DwtKeyEvent.KEY_TAB) {
			var toField = this._recipients.getField(AjxEmailAddress.TO);
			if (toField) {
				appCtxt.getKeyboardMgr().grabFocus(toField);
			}
			rv = false;
		}
	}
	return rv;
};

// needed to reset design mode when in html compose format for gecko
ZmComposeView.prototype._okCallback =
function() {
	appCtxt.getMsgDialog().popdown();
	this._controller.resetToolbarOperations();
	this.reEnableDesignMode();
};

// User has agreed to send message without a subject
ZmComposeView.prototype._noSubjectOkCallback =
function(dialog) {
	this._noSubjectOkay = true;
	this._popDownAlertAndSendMsg(dialog);
};

//this is used by several kinds of alert dialogs
ZmComposeView.prototype._popDownAlertAndSendMsg =
function(dialog) {
	// not sure why: popdown (in FF) seems to create a race condition,
	// we can't get the attachments from the document anymore.
	// W/in debugger, it looks fine, but remove the debugger and any
	// alerts, and gotAttachments will return false after the popdown call.

	if (AjxEnv.isIE) {
		dialog.popdown();
	}
	// bug fix# 3209
	// - hide the dialog instead of popdown (since window will go away anyway)
	if (AjxEnv.isNav && appCtxt.isChildWindow) {
		dialog.setVisible(false);
	}

	// dont make any calls after sendMsg if child window since window gets destroyed
	if (appCtxt.isChildWindow && !AjxEnv.isNav) {
        // bug fix #68774 Empty warning window when sending message without subject in chrome
        dialog.popdown();
		this._controller.sendMsg();
	} else {
		// bug fix #3251 - call popdown BEFORE sendMsg
		dialog.popdown();
		this._controller.sendMsg();
	}
};

// User has canceled sending message without a subject
ZmComposeView.prototype._noSubjectCancelCallback =
function(dialog) {
	this.enableInputs(true);
	dialog.popdown();
	appCtxt.getKeyboardMgr().grabFocus(this._subjectField);
	this._controller.resetToolbarOperations();
	this.reEnableDesignMode();
};

ZmComposeView.prototype._errViaZimletOkCallback =
function(params) {
	var dialog = params.errDialog; 
	var zimletName = params.zimletName;
	//add this zimlet to ignoreZimlet string
	this._ignoredZimlets = this._ignoredZimlets || {};
	this._ignoredZimlets[zimletName] = true;
	this._popDownAlertAndSendMsg(dialog);
};

ZmComposeView.prototype._errViaZimletCancelCallback =
function(params) {
	var dialog = params.errDialog; 
	var zimletName = params.zimletName;
	this.enableInputs(true);
	dialog.popdown();
	this._controller.resetToolbarOperations();
	this.reEnableDesignMode();
};

// User has agreed to send message with bad addresses
ZmComposeView.prototype._badAddrsOkCallback =
function(dialog) {
	this.enableInputs(true);
	this._badAddrsOkay = true;
	dialog.popdown();
	this._controller.sendMsg();
};

// User has declined to send message with bad addresses - set focus to bad field
ZmComposeView.prototype._badAddrsCancelCallback =
function(type, dialog) {
	this.enableInputs(true);
	this._badAddrsOkay = false;
	dialog.popdown();
	if (this._recipients.getUsing(type)) {
		appCtxt.getKeyboardMgr().grabFocus(this._recipients.getField(type));
	}
	this._controller.resetToolbarOperations();
	this.reEnableDesignMode();
};

ZmComposeView.prototype._closeAttachDialog =
function(){
    if (this._attachDialog)
        this._attachDialog.popdown();

    this._initProgressSpan(ZmMsg.uploadingAttachment);

    var progress = function (obj) {
                    var selfobject = obj;
				    obj.si = window.setInterval (function(){selfobject._progress();}, 500);
     };
    progress(this);

};

ZmComposeView.prototype._setAttachedMsgIds =
function(msgIds){
    if (!this._msgIds){
        this._msgIds = msgIds;
    } else {
         for (val in msgIds){
           if (AjxUtil.indexOf(this._msgIds, msgIds[val]) == -1) // Do not attach if the same message is forwarded
             this._msgIds.push(msgIds[val]);
         }
    }
};

// Files have been uploaded, re-initiate the send with an attachment ID.
ZmComposeView.prototype._attsDoneCallback =
function(isDraft, status, attId, docIds, msgIds) {
	DBG.println(AjxDebug.DBG1, "Attachments: isDraft = " + isDraft + ", status = " + status + ", attId = " + attId);
    this._closeAttachDialog();
	if (status == AjxPost.SC_OK) {
        if (msgIds){
          this._setAttachedMsgIds(msgIds);
        }
		var callback = this._resetUpload.bind(this);
		this._startUploadAttachment(); 
		this._controller.saveDraft(ZmComposeController.DRAFT_TYPE_AUTO, attId, docIds, callback);
	} else if (status == AjxPost.SC_UNAUTHORIZED) {
		// auth failed during att upload - let user relogin, continue with compose action
		this._resetUpload(true);
		var ex = new AjxException("401 response during attachment upload", ZmCsfeException.SVC_AUTH_EXPIRED);
		var callback = new AjxCallback(this._controller, isDraft ? this._controller.saveDraft : this._controller._send);
		this._controller._handleException(ex, {continueCallback:callback});
	} else {
		// bug fix #2131 - handle errors during attachment upload.
		this._resetUpload(true);
		var msg = AjxMessageFormat.format(ZmMsg.errorAttachment, (status || AjxPost.SC_NO_CONTENT));
		switch (status) {
			// add other error codes/message here as necessary
			case AjxPost.SC_REQUEST_ENTITY_TOO_LARGE: 	msg += " " + ZmMsg.errorAttachmentTooBig + "<br><br>"; break;
			default: 									msg += " "; break;
		}

		this._controller.popupErrorDialog(msg + ZmMsg.errorTryAgain, null, null, true);
		this._controller.resetToolbarOperations();
	}
};


//Mandatory Spellcheck Callback
ZmComposeView.prototype._spellCheckShield =
function(words){
	if (words && words.available && words.misspelled != null && words.misspelled.length != 0) {
		var msgDialog = new DwtMessageDialog({parent: appCtxt.getShell(), buttons:[DwtDialog.YES_BUTTON, DwtDialog.NO_BUTTON], id: Dwt.getNextId("SpellCheckConfirm_")});
		msgDialog.setMessage(AjxMessageFormat.format(ZmMsg.misspellingsMessage, [words.misspelled.length]), DwtMessageDialog.WARNING_STYLE);
		msgDialog.registerCallback(DwtDialog.YES_BUTTON, this._spellCheckShieldOkListener, this, [ msgDialog, words ] );
		msgDialog.registerCallback(DwtDialog.NO_BUTTON, this._spellCheckShieldCancelListener, this, msgDialog);
		msgDialog.associateEnterWithButton(DwtDialog.NO_BUTTON);
		msgDialog.getButton(DwtDialog.YES_BUTTON).setText(ZmMsg.correctSpelling);
		msgDialog.getButton(DwtDialog.NO_BUTTON).setText(ZmMsg.sendAnyway);
		var composeView = this;
		msgDialog.handleKeyAction = function(actionCode, ev) { if (actionCode && actionCode==DwtKeyMap.CANCEL) { composeView._spellCheckShieldOkListener(msgDialog, words, ev); return(true); } };
		msgDialog.popup(null, DwtDialog.NO_BUTTON);
	} else {
		this._spellCheckOkay = true;
		this._controller.sendMsg();
	}
};

ZmComposeView.prototype._spellCheckShieldOkListener =
function(msgDialog, words, ev){

	this._controller._toolbar.enableAll(true);
    this.enableInputs(true);
	this._controller.toggleSpellCheckButton(true);
	this._htmlEditor.discardMisspelledWords();

	this._spellCheckOkay = false;
	msgDialog.popdown();

	this._htmlEditor.onExitSpellChecker = new AjxCallback(this._controller, this._controller.toggleSpellCheckButton, true)
	this._htmlEditor._spellCheckCallback(words);
};

ZmComposeView.prototype._spellCheckShieldCancelListener =
function(msgDialog, ev){
	this._spellCheckOkay = true;
	msgDialog.popdown();
	this._controller.sendMsg();
};

ZmComposeView.prototype._spellCheckErrorShield =
function(ex){
	var msgDialog = appCtxt.getYesNoMsgDialog();
	msgDialog.setMessage(ZmMsg.spellCheckFailed);
	msgDialog.registerCallback(DwtDialog.YES_BUTTON, this._spellCheckErrorShieldOkListener, this, msgDialog );
	msgDialog.registerCallback(DwtDialog.NO_BUTTON, this._spellCheckErrorShieldCancelListener, this, msgDialog);
	msgDialog.associateEnterWithButton(DwtDialog.NO_BUTTON);
	msgDialog.popup(null, DwtDialog.NO_BUTTON);

	return true;
};

ZmComposeView.prototype._spellCheckErrorShieldOkListener =
function(msgDialog, ev){

	this._controller._toolbar.enableAll(true);
	this._controller.toggleSpellCheckButton(false);
	this._htmlEditor.discardMisspelledWords();
	msgDialog.popdown();

	this._spellCheckOkay = true;
	this._controller.sendMsg();
	
};

ZmComposeView.prototype._spellCheckErrorShieldCancelListener =
function(msgDialog, ev){
	this._controller._toolbar.enableAll(true);
	this._controller.toggleSpellCheckButton(false);
	this._htmlEditor.discardMisspelledWords();
	msgDialog.popdown();
};

ZmComposeView.prototype._setFormValue =
function() {
	this._origFormValue = this._formValue();
};

ZmComposeView.prototype._clearFormValue =
function() {
	this._origFormValue = "";
	this._isDirty = false;
};

ZmComposeView.prototype._focusHtmlEditor =
function() {
	this._htmlEditor.focus();
};


// Static methods

ZmComposeView._onKeyUp =
function(ev) {

	ev = DwtUiEvent.getEvent(ev);
	var element = DwtUiEvent.getTargetWithProp(ev, "id");
	if (!element) { return true; }
	var cv = AjxCore.objectWithId(element._composeView);

	return true;
};

// set focus within tab group to element so tabbing works
ZmComposeView._onFocus =
function(ev) {

	ev = DwtUiEvent.getEvent(ev);
	var element = DwtUiEvent.getTargetWithProp(ev, "id");
	if (!element) { return true; }

	var kbMgr = appCtxt.getKeyboardMgr();
	if (kbMgr.__currTabGroup) {
		kbMgr.__currTabGroup.setFocusMember(element);
	}
};

ZmComposeView._onBlur =
function(ev) {

	var element = DwtUiEvent.getTargetWithProp(ev, "id");
	if (!element) { return true; }
	var cv = AjxCore.objectWithId(element._composeView);

	cv.updateTabTitle();

	return true;
};

// for com.zimbra.dnd zimlet
ZmComposeView.prototype.uploadFiles =
function() {
	var attachDialog = appCtxt.getAttachDialog();
	var callback = new AjxCallback(this, this._attsDoneCallback, [true]);
	attachDialog.setUploadCallback(callback);
	attachDialog.upload(callback, document.getElementById("zdnd_form"));
};

ZmComposeView.prototype.deactivate =
function() {
	this._controller.inactive = true;
};

ZmComposeView.prototype._getIframeDoc =
function(){
    var editor = this._htmlEditor;
    if( editor ){
        return editor._getIframeDoc();
    }
};

/**
 * Moves the cursor to the beginning of the editor.
 * 
 * @param {boolean}		checkContent	if true, only move cursor if content is unchanged during delay
 * @param {number}		delay			timer delay in ms
 * @param {number}		offset			number of characters to skip ahead when placing cursor
 * 
 * @private
 */
ZmComposeView.prototype._moveCaretOnTimer =
function(checkContent, offset, delay) {

	delay = (delay != undefined) ? delay : 200;
	var editor = this.getHtmlEditor();
	var len = editor.getContent().length;
	AjxTimedAction.scheduleAction(new AjxTimedAction(this, function() {
		if (editor.getContent().length == len) {
			editor.moveCaretToTop(offset);
		}
	}), delay);
};


/**
 * @overview
 * This class is used to manage the creation of a composed message, without a UI. For example,
 * it an be used to reply to a msg with some canned or user-provided text.
 * 
 * @param controller
 * @param composeMode
 */
ZmHiddenComposeView = function(controller, composeMode) {
	// no need to invoke parent ctor since we don't need to create a UI
	this._controller = controller;
	this._composeMode = composeMode;
	this.isHidden = true;
	this.reset();
};

ZmHiddenComposeView.prototype = new ZmComposeView;
ZmHiddenComposeView.prototype.constructor = ZmHiddenComposeView;

ZmHiddenComposeView.prototype.isZmHiddenComposeView = true;
ZmHiddenComposeView.prototype.toString = function() { return "ZmHiddenComposeView"; };

/**
 * Sets the current view, based on the given action. The compose form is
 * created and laid out and everything is set up for interaction with the user.
 *
 * @param {Hash}		params			a hash of parameters:
 * @param {constant}	action				new message, reply, or forward
 * @param {ZmMailMsg}	msg					the original message (reply/forward), or address (new message)
 * @param {ZmIdentity}	identity			identity of sender
 * @param {String}		toOverride			To: addresses (optional)
 * @param {String}		ccOverride			Cc: addresses (optional)
 * @param {String}		subjectOverride		subject for new msg (optional)
 * @param {String}		extraBodyText		text for new msg
 * @param {String}		accountName			on-behalf-of From address
 */
ZmHiddenComposeView.prototype.set =
function(params) {

	this.reset();
	
	var action = this._action = params.action;
	var msg = this._msg = this._addressesMsg = params.msg;

	if (!ZmComposeController.IS_FORWARD[action]) {
		this._setAddresses(action, AjxEmailAddress.TO, params.toOverride);
		if (params.ccOverride) {
			this._setAddresses(action, AjxEmailAddress.CC, params.ccOverride);
		}
		if (params.bccOverride) {
			this._setAddresses(action, AjxEmailAddress.BCC, params.bccOverride);
		}
	}
	this._setSubject(action, msg, params.subjectOverride);
	this._setBody(action, msg, params.extraBodyText);
    var oboMsg = msg || (params.selectedMessages && params.selectedMessages.length && params.selectedMessages[0]);
	var obo = this._getObo(params.accountName, oboMsg);
	
	if (action != ZmOperation.FORWARD_ATT) {
		this._saveExtraMimeParts();
	}
};

ZmHiddenComposeView.prototype.setComposeMode =
function(composeMode) {
	this._composeMode = composeMode;
};

// no-op since it relies on UI components
ZmHiddenComposeView.prototype.applySignature = function() {};

/**
 * Returns a msg created from prior input.
 */
ZmHiddenComposeView.prototype.getMsg =
function() {

	var addrs = this._recipients.collectAddrs();
	var subject = this._subject;

	// Create Msg Object - use dummy if provided
	var msg = new ZmMailMsg();
	msg.setSubject(subject);

	this.sendUID = (new Date()).getTime();

	// build MIME
	var top = this._getTopPart(msg, false, this._bodyContent);

	msg.setTopPart(top);
	msg.setSubject(subject);
	
	for (var i = 0; i < ZmMailMsg.COMPOSE_ADDRS.length; i++) {
		var type = ZmMailMsg.COMPOSE_ADDRS[i];
		var a = addrs[type];
		if (a && a.length) {
			msg.setAddresses(type, AjxVector.fromArray(a));
		}
	}
	msg.identity = this.getIdentity();
	msg.sendUID = this.sendUID;

	// save a reference to the original message
	msg._origMsg = this._msg;
	if (this._msg && this._msg._instanceDate) {
		msg._instanceDate = this._msg._instanceDate;
	}

	this._setMessageFlags(msg);

	return msg;
};

ZmHiddenComposeView.prototype.reset =
function(bEnableInputs) {

	this.sendUID = null;
	this._recipients = new ZmHiddenRecipients();
	this._subject = this._bodyContent = "";
	this._controller._curIncOptions = null;
	this._msgAttId = null;
	this._addresses = {};

	// remove extra mime parts
	this._extraParts = null;
};

ZmHiddenComposeView.prototype.getIdentity =
function() {
	var ac = window.parentAppCtxt || window.appCtxt;
	var collection = ac.getIdentityCollection();
	var val = ac.getActiveAccount().id;
	return collection.getById(val);
};

ZmHiddenComposeView.prototype.__initCtrl = function() {};

/**
 * Minimal version of ZmRecipients that has no UI. Note that addresses are stored in
 * arrays rather than vectors.
 */
ZmHiddenRecipients = function() {
	this._addresses = {};
};

ZmHiddenRecipients.prototype.setAddress =
function(type, addr) {
	if (type && addr) {
		this._addresses[type] = this._addresses[type] || [];
		this._addresses[type].push(addr);
	}
};

ZmHiddenRecipients.prototype.addAddresses =
function(type, addrVec, used) {

	var addrAdded = false;
	used = used || {};
	var addrs = AjxUtil.toArray(addrVec);
	if (addrs && addrs.length) {
		if (!this._addresses[type]) {
			this._addresses[type] = [];
		}
		for (var i = 0, len = addrs.length; i < len; i++) {
			var addr = addrs[i];
			addr = addr.isAjxEmailAddress ? addr : AjxEmailAddress.parse(addr);
			if (addr) {
				var email = addr.getAddress();
				if (!email) { continue; }
				email = email.toLowerCase();
				if (!used[email]) {
					this._addresses[type].push(addr);
					used[email] = true;
					addrAdded = true;
				}
			}
		}
	}
	return addrAdded;
};

ZmHiddenRecipients.prototype.collectAddrs =
function() {
	return this._addresses;
};
