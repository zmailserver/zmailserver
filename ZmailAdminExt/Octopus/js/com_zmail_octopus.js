ZaOctopus = function() {
	
}
ZaOctopus.APP_OCTOPUS = "octopus";
ZaOctopus.APP_BRIEFCASE = "briefcase";
ZaOctopus.deviceID = "id";
ZaOctopus.deviceName = "name";
ZaOctopus.deviceStatus = "status";
ZaOctopus.deviceCreated = "created";
ZaOctopus.deviceAccessed = "accessed";
ZaOctopus.deviceDisabled = "disabled";
ZaOctopus.A2_device_selection_cache = "device_selection_cache";
ZaOctopus.URN = "urn:zmailAdmin" ;
ZaEvent.S_AUDIT_REPORT = ZaEvent.EVENT_SOURCE_INDEX++;
ZaEvent.S_AUDIT_TEMPLATE = ZaZmailAdmin.VIEW_INDEX++;
ZaZmailAdmin._AUDIT_REPORT_LIST = ZaZmailAdmin.VIEW_INDEX++;
ZaZmailAdmin._AUDIT_TEMPLATE_LIST = ZaZmailAdmin.VIEW_INDEX++;
ZaOperation.RUN_REPORT = ++ ZA_OP_INDEX;
ZaSettings.IS_OCTOPUS = true;

ZaOctopus.getFSAppTypeMsg = function (app) {
    if (app == ZaOctopus.APP_OCTOPUS)  {
        return org_zmail_octopus.applicationOctopus;
    } else if (app == ZaOctopus.APP_BRIEFCASE) {
    	return org_zmail_octopus.applicationBriefcase;
    } else {
        return "";
    }
}

ZaOctopus.deviceObjModel = {
	items: [
		{id:ZaOctopus.deviceID, type:_STRING_},
		{id:ZaOctopus.deviceName, type:_STRING_},
		{id:ZaOctopus.deviceStatus, type:_STRING_},
		{id:ZaOctopus.deviceCreated, type:_STRING_},
		{id:ZaOctopus.deviceAccessed, type:_STRING_}
	],
	type:_OBJECT_
}

ZaOctopus.getAccessedTime =
function (serverDate) {
	if (serverDate) {
		return ZaItem.formatServerTime(new String(serverDate));
	}else{
		return org_zmail_octopus.DeviceNeverAccessed;
	}
}

ZaOctopus.fileSharingAppChoices = [
                             	    {value:ZaOctopus.APP_OCTOPUS, label:ZaOctopus.getFSAppTypeMsg (ZaOctopus.APP_OCTOPUS)},
                             	    {value:ZaOctopus.APP_BRIEFCASE, label:ZaOctopus.getFSAppTypeMsg (ZaOctopus.APP_BRIEFCASE)}
                              ];		

ZaOctopus.loadDevices = function(by, val, withCos) {
	if(this.attrs[ZaAccount.A_zmailIsExternalVirtualAccount] == "TRUE") {
		return;
	}
	var soapDoc = AjxSoapDoc.create("GetDevicesRequest", ZaOctopus.URN, null);
	var params = {
		soapDoc: soapDoc,
		asyncMode:false,
		callback:null		
	}
	var elBy = soapDoc.set("account", val);
	elBy.setAttribute("by", by);	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : org_zmail_octopus.BUSY_LOADING_DEVICES
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams);
	if(resp && resp.Body && resp.Body.GetDevicesResponse && resp.Body.GetDevicesResponse.device) {
		this.octopus = {};
		this.octopus[ZaAccount.A_octopusDevices] = resp.Body.GetDevicesResponse.device;
	}
	
	//TODO: remove when server adds the attribute
	this.setAttrs[ZaAccount.A_zmailDeviceAutoLockInteral] = true;
	this.getAttrs[ZaAccount.A_zmailDeviceAutoLockInteral] = true;
}

ZaOctopus.deviceWipeCallback = function(deviceName,resp) {
	if(resp.isException()) {
		ZaApp.getInstance().getCurrentController()._handleException(resp.getException(), "ZaOctopus.updateDeviceStatusCallback", null, false);
	} else {
		ZaApp.getInstance().getCurrentController().popupMsgDialog(AjxMessageFormat.format(org_zmail_octopus.WipeRequested,[deviceName]));
		var response = resp.getResponse();
		if(response && response.Body.UpdateDeviceStatusResponse && response.Body.UpdateDeviceStatusResponse.device) {
			this.setInstanceValue(response.Body.UpdateDeviceStatusResponse.device, ZaAccount.A_octopusDevices);
		}
	}
}

ZaOctopus.updateDeviceStatus = function(account, deviceID, status, callback) {
	ZaApp.getInstance().dialogs["confirmMessageDialog"].popdown()
	var soapDoc = AjxSoapDoc.create("UpdateDeviceStatusRequest", ZaOctopus.URN, null);
	var params = {
		soapDoc: soapDoc,
		asyncMode:true,
		callback:callback		
	}
	var elBy = soapDoc.set("account", account.id);
	elBy.setAttribute("by", "id");	
	var elDevice = soapDoc.set("device");
	elDevice.setAttribute(ZaOctopus.deviceID, deviceID);
	elDevice.setAttribute(ZaOctopus.deviceStatus, status);
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : org_zmail_octopus.BUSY_LOADING_DEVICES
	}
	ZaRequestMgr.invoke(params, reqMgrParams);
	/*if(resp && resp.Body && resp.Body.GetDevicesResponse && resp.Body.GetDevicesResponse.device) {
		this.octopus = {};
		this.octopus[ZaAccount.A_octopusDevices] = resp.Body.GetDevicesResponse.device;
	}*/
}

if(ZaAccount) {
	ZaAccount.A_octopusDevices = "octopusDevices";
	ZaAccount.A_zmailPrefFileSharingApplication = "zmailPrefFileSharingApplication";
	ZaAccount.A_zmailFileUploadMaxSizePerFile = "zmailFileUploadMaxSizePerFile";
	//sharing
	ZaAccount.A2_zmailExternalShareLimitLifetime = "zmailExternalShareLimitLifetime";
	ZaAccount.A_zmailFileExternalShareLifetime = "zmailFileExternalShareLifetime";
	ZaAccount.A2_zmailInternalShareLimitLifetime = "zmailInternalShareLimitLifetime";
	ZaAccount.A_zmailFileShareLifetime = "zmailFileShareLifetime";
	ZaAccount.A_zmailExternalSharingEnabled = "zmailExternalSharingEnabled";
	ZaAccount.A_zmailExternalShareWhitelistDomain = "zmailExternalShareWhitelistDomain";
	ZaAccount.A_zmailExternalShareDomainWhitelistEnabled = "zmailExternalShareDomainWhitelistEnabled";
	
	//retention
	ZaAccount.A_zmailFileExpirationWarningPeriod = "zmailFileExpirationWarningThreshold";
	ZaAccount.A_zmailFileLifetime = "zmailFileLifetime";
	ZaAccount.A_zmailFileExpirationWarningBody = "zmailFileExpirationWarningBody";
	ZaAccount.A_zmailFileExpirationWarningSubject = "zmailFileExpirationWarningSubject";
	ZaAccount.A_zmailFileDeletionNotificationBody = "zmailFileDeletionNotificationBody";
	ZaAccount.A_zmailFileDeletionNotificationSubject = "zmailFileDeletionNotificationSubject";
	ZaAccount.A_zmailFileVersioningEnabled = "zmailFileVersioningEnabled";
	ZaAccount.A_zmailFileVersionLifetime = "zmailFileVersionLifetime";
	ZaAccount.A_zmailDeviceAutoLockInteral = "zmailDeviceAutoLockInteral";
	//feature
    ZaAccount.A_zmailFeatureCrocodocEnabled = "zmailFeatureCrocodocEnabled";
	if(ZaItem.modelExtensions["ZaAccount"]) {
		ZaItem.modelExtensions["ZaAccount"].push("octopus");
	}	
	
	if(ZaAccount.myXModel) {
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileUploadMaxSizePerFile, type:_COS_NUMBER_, ref:"attrs/"+ZaAccount.A_zmailFileUploadMaxSizePerFile, maxInclusive:2147483648, minInclusive:0});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailPrefFileSharingApplication, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailPrefFileSharingApplication, choices:ZaOctopus.fileSharingAppChoices});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_octopusDevices, type:_LIST_, ref:"octopus/"+ZaAccount.A_octopusDevices,listItem:ZaOctopus.deviceObjModel});
		ZaAccount.myXModel.items.push({id:ZaOctopus.A2_device_selection_cache, ref:"octopus/"+ZaOctopus.A2_device_selection_cache, type:_LIST_});
		//sharing
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileExternalShareLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailFileExternalShareLifetime});
		ZaAccount.myXModel.items.push({id:ZaAccount.A2_zmailExternalShareLimitLifetime, type:_COS_ENUM_, ref:ZaAccount.A2_zmailExternalShareLimitLifetime,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailExternalSharingEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailExternalSharingEnabled,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaAccount.myXModel.items.push({id:ZaAccount.A2_zmailInternalShareLimitLifetime, type:_COS_ENUM_, ref:ZaAccount.A2_zmailInternalShareLimitLifetime,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileShareLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailFileShareLifetime});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailExternalShareDomainWhitelistEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailExternalShareDomainWhitelistEnabled,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailExternalShareWhitelistDomain, ref:"attrs/" + ZaAccount.A_zmailExternalShareWhitelistDomain, type:_COS_LIST_,  itemDelimiter:"\n", dataType:_STRING_,outputType:_STRING_});
		
		//retention
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileExpirationWarningPeriod, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailFileExpirationWarningPeriod});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailFileLifetime});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileExpirationWarningBody, type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailFileExpirationWarningBody});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileExpirationWarningSubject, type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailFileExpirationWarningSubject});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileDeletionNotificationBody, type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailFileDeletionNotificationBody});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileDeletionNotificationSubject, type:_COS_STRING_, ref:"attrs/"+ZaAccount.A_zmailFileDeletionNotificationSubject});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileVersioningEnabled, type:_COS_ENUM_, ref:"attrs/"+ZaAccount.A_zmailFileVersioningEnabled,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFileVersionLifetime, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailFileVersionLifetime});
		
		//security
		ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailDeviceAutoLockInteral, type:_COS_MLIFETIME_, ref:"attrs/"+ZaAccount.A_zmailDeviceAutoLockInteral});

        // feature
        ZaAccount.myXModel.items.push({id:ZaAccount.A_zmailFeatureCrocodocEnabled, type:_COS_ENUM_, ref:"attrs/" + ZaAccount.A_zmailFeatureCrocodocEnabled, choices:ZaModel.BOOLEAN_CHOICES});
	}
}

if(ZaCos) {
	ZaCos.A_zmailFileUploadMaxSizePerFile = "zmailFileUploadMaxSizePerFile";
	//sharing
	ZaCos.A2_zmailExternalShareLimitLifetime = "zmailExternalShareLimitLifetime"; 
	ZaCos.A_zmailFileExternalShareLifetime = "zmailFileExternalShareLifetime";
	ZaCos.A2_zmailInternalShareLimitLifetime = "zmailInternalShareLimitLifetime";
	ZaCos.A_zmailFileShareLifetime = "zmailFileShareLifetime";
	ZaCos.A_zmailExternalSharingEnabled = "zmailExternalSharingEnabled";
	ZaCos.A_zmailExternalShareWhitelistDomain = "zmailExternalShareWhitelistDomain";
	ZaCos.A_zmailExternalShareDomainWhitelistEnabled = "zmailExternalShareDomainWhitelistEnabled";
	
	//retention
	ZaCos.A_zmailFileExpirationWarningPeriod = "zmailFileExpirationWarningThreshold";
	ZaCos.A_zmailFileLifetime = "zmailFileLifetime";
	ZaCos.A_zmailFileExpirationWarningBody = "zmailFileExpirationWarningBody";
	ZaCos.A_zmailFileExpirationWarningSubject = "zmailFileExpirationWarningSubject";
	ZaCos.A_zmailFileDeletionNotificationBody = "zmailFileDeletionNotificationBody";
	ZaCos.A_zmailFileDeletionNotificationSubject = "zmailFileDeletionNotificationSubject";
	ZaCos.A_zmailFileVersioningEnabled = "zmailFileVersioningEnabled";
	ZaCos.A_zmailFileVersionLifetime = "zmailFileVersionLifetime";
	ZaCos.A_zmailDeviceAutoLockInteral = "zmailDeviceAutoLockInteral";

    //feature
    ZaCos.A_zmailFeatureCrocodocEnabled = "zmailFeatureCrocodocEnabled";
    ZaCos.A_zmailFeatureExternalFeedbackEnabled = "zmailFeatureExternalFeedbackEnabled";
	if(ZaCos.myXModel) {
		ZaCos.myXModel.items.push({id:ZaAccount.A_zmailFileUploadMaxSizePerFile, type:_NUMBER_, ref:"attrs/"+ZaCos.A_zmailFileUploadMaxSizePerFile, maxInclusive:2147483648, minInclusive:0});
		ZaCos.myXModel.items.push({id:ZaAccount.A_zmailPrefFileSharingApplication, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailPrefFileSharingApplication, choices:ZaOctopus.fileSharingAppChoices});
        //sharing
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileExternalShareLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileExternalShareLifetime});
		ZaCos.myXModel.items.push({id:ZaCos.A2_zmailExternalShareLimitLifetime, type:_ENUM_, ref:ZaCos.A2_zmailExternalShareLimitLifetime,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailExternalSharingEnabled, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailExternalSharingEnabled,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaCos.myXModel.items.push({id:ZaCos.A2_zmailInternalShareLimitLifetime, type:_ENUM_, ref:ZaCos.A2_zmailInternalShareLimitLifetime,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileShareLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileShareLifetime});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailExternalShareWhitelistDomain, ref:"attrs/" + ZaCos.A_zmailExternalShareWhitelistDomain, type:_LIST_, itemDelimiter:"\n", dataType:_STRING_,outputType:_STRING_});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailExternalShareDomainWhitelistEnabled, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailExternalShareDomainWhitelistEnabled,  choices:ZaModel.BOOLEAN_CHOICES});
		
		//retention
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileExpirationWarningPeriod, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileExpirationWarningPeriod});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileLifetime});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileExpirationWarningBody, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailFileExpirationWarningBody});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileExpirationWarningSubject, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailFileExpirationWarningSubject});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileDeletionNotificationBody, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailFileDeletionNotificationBody});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileDeletionNotificationSubject, type:_STRING_, ref:"attrs/"+ZaCos.A_zmailFileDeletionNotificationSubject});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileVersioningEnabled, type:_ENUM_, ref:"attrs/"+ZaCos.A_zmailFileVersioningEnabled,  choices:ZaModel.BOOLEAN_CHOICES});
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailFileVersionLifetime, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailFileVersionLifetime});
		
		//security
		ZaCos.myXModel.items.push({id:ZaCos.A_zmailDeviceAutoLockInteral, type:_MLIFETIME_, ref:"attrs/"+ZaCos.A_zmailDeviceAutoLockInteral});

        // feature
        ZaCos.myXModel.items.push({id:ZaCos.A_zmailFeatureCrocodocEnabled, choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureCrocodocEnabled, type:_ENUM_});
        ZaCos.myXModel.items.push({id:ZaCos.A_zmailFeatureExternalFeedbackEnabled , choices:ZaModel.BOOLEAN_CHOICES, ref:"attrs/"+ZaCos.A_zmailFeatureExternalFeedbackEnabled, type:_ENUM_});

	}
	
}

if (ZaGlobalConfig) {
    ZaGlobalConfig.A_zmailShareNotificationMtaEnabled = "zmailShareNotificationMtaEnabled";
    ZaGlobalConfig.A_zmailShareNotificationMtaHostname = "zmailShareNotificationMtaHostname";
    ZaGlobalConfig.A_zmailShareNotificationMtaPort = "zmailShareNotificationMtaPort";
    ZaGlobalConfig.A_zmailShareNotificationMtaAuthAccount = "zmailShareNotificationMtaAuthAccount";
    ZaGlobalConfig.A_zmailShareNotificationMtaAuthPassword = "zmailShareNotificationMtaAuthPassword";
    ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType = "zmailShareNotificationMtaConnectionType";
    ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired = "zmailShareNotificationMtaAuthRequired";

    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaEnabled, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES});
    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaHostname, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaHostname, type:_STRING_, maxLength:128 });
    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaPort, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaPort, type:_PORT_});
    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaAuthAccount, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaAuthAccount, type:_STRING_, maxLength:128 });
    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaAuthPassword, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaAuthPassword, type:_STRING_, maxLength:64 });
    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES});
    ZaGlobalConfig.myXModel.items.push({ id:ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType, ref:"attrs/" + ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType, type:_STRING_});
}

if (ZaServer) {
    ZaServer.A_zmailShareNotificationMtaEnabled = "zmailShareNotificationMtaEnabled";
    ZaServer.A_zmailShareNotificationMtaHostname = "zmailShareNotificationMtaHostname";
    ZaServer.A_zmailShareNotificationMtaPort = "zmailShareNotificationMtaPort";
    ZaServer.A_zmailShareNotificationMtaAuthAccount = "zmailShareNotificationMtaAuthAccount";
    ZaServer.A_zmailShareNotificationMtaAuthPassword = "zmailShareNotificationMtaAuthPassword";
    ZaServer.A_zmailShareNotificationMtaConnectionType = "zmailShareNotificationMtaConnectionType";
    ZaServer.A_zmailShareNotificationMtaAuthRequired = "zmailShareNotificationMtaAuthRequired";

    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaEnabled, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES});
    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaHostname, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaHostname, type:_COS_STRING_, maxLength:128 });
    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaPort, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaPort, type:_COS_PORT_});
    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaAuthAccount, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaAuthAccount, type:_COS_STRING_, maxLength:128 });
    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaAuthPassword, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaAuthPassword, type:_COS_STRING_, maxLength:64 });
    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaAuthRequired, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaAuthRequired, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES});
    ZaServer.myXModel.items.push({ id:ZaServer.A_zmailShareNotificationMtaConnectionType, ref:"attrs/" + ZaServer.A_zmailShareNotificationMtaConnectionType, type:_COS_STRING_});
}

ZaOctopus.isWipeButtonEnabled = function () {
	var selArr = this.getInstanceValue(ZaOctopus.A2_device_selection_cache);
	return (!AjxUtil.isEmpty(selArr) 
			&& selArr.length==1
			&& selArr[0][ZaOctopus.deviceStatus] != ZaOctopus.deviceDisabled);
}

ZaOctopus.deviceListSelectionListener = function() {
	var arr = this.widget.getSelection();
	if(arr && arr.length) {
		this.getModel().setInstanceValue(this.getInstance(), ZaOctopus.A2_device_selection_cache, arr);
	} else {
		this.getModel().setInstanceValue(this.getInstance(), ZaOctopus.A2_device_selection_cache, null);
	}	
}

ZaOctopus.wipeButtonListener = function () {
	var instance = this.getInstance();
	
	var selArr = this.getInstanceValue(ZaOctopus.A2_device_selection_cache);
	if(AjxUtil.isEmpty(selArr)) {
		return;
	}
	var callback = new AjxCallback(this,ZaOctopus.deviceWipeCallback,[selArr[0][ZaOctopus.deviceName]]);
	ZaApp.getInstance().dialogs["confirmMessageDialog"] = new ZaMsgDialog(ZaApp.getInstance().getAppCtxt().getShell(), null, [DwtDialog.YES_BUTTON, DwtDialog.NO_BUTTON]);
	ZaApp.getInstance().dialogs["confirmMessageDialog"].setMessage(AjxMessageFormat.format(org_zmail_octopus.Q_WIPE_DEVICE,[selArr[0][ZaOctopus.deviceName]]),  DwtMessageDialog.WARNING_STYLE);
	ZaApp.getInstance().dialogs["confirmMessageDialog"].registerCallback(DwtDialog.YES_BUTTON, ZaOctopus.updateDeviceStatus, this, [this.getInstance(), selArr[0][ZaOctopus.deviceID],ZaOctopus.deviceDisabled,callback]);
	ZaApp.getInstance().dialogs["confirmMessageDialog"].popup();		

}

if(ZaTabView.ObjectModifiers["ZaAccountXFormView"]) {
    ZaOctopus.AccountObjectModifier = function () {
        var defaultShareLife;

        if (this._containedObject._defaultValues) {
            defaultShareLife = this._containedObject._defaultValues.attrs[ZaAccount.A_zmailFileExternalShareLifetime];
        }
        if (defaultShareLife && (parseInt(defaultShareLife) != 0)) {
            this._containedObject._defaultValues[ZaAccount.A2_zmailExternalShareLimitLifetime] = "TRUE";
        }  else {
            this._containedObject._defaultValues[ZaAccount.A2_zmailExternalShareLimitLifetime] = "FALSE";
        }

        var shareLife = this._containedObject.attrs[ZaAccount.A_zmailFileExternalShareLifetime];
        if (shareLife) {
            if (parseInt(shareLife) != 0) {
                this._containedObject[ZaAccount.A2_zmailExternalShareLimitLifetime] = "TRUE";
            } else {
                this._containedObject[ZaAccount.A2_zmailExternalShareLimitLifetime] = "FALSE";
            }

        }
        defaultShareLife = null;
        if (this._containedObject._defaultValues) {
            defaultShareLife = this._containedObject._defaultValues.attrs[ZaAccount.A_zmailFileShareLifetime];
        }
        if (defaultShareLife && (parseInt(defaultShareLife) != 0)) {
            this._containedObject._defaultValues[ZaAccount.A2_zmailInternalShareLimitLifetime] = "TRUE";
        }  else {
            this._containedObject._defaultValues[ZaAccount.A2_zmailInternalShareLimitLifetime] = "FALSE";
        }

        var shareLife = this._containedObject.attrs[ZaAccount.A_zmailFileShareLifetime];
        if (shareLife) {
            if (parseInt(shareLife) != 0) {
                this._containedObject[ZaAccount.A2_zmailInternalShareLimitLifetime] = "TRUE";
            } else {
                this._containedObject[ZaAccount.A2_zmailInternalShareLimitLifetime] = "FALSE";
            }

        }
    }
    ZaTabView.ObjectModifiers["ZaAccountXFormView"].push(ZaOctopus.AccountObjectModifier);
}

if(ZaTabView.ObjectModifiers["ZaCosXFormView"]) {
    ZaOctopus.CosObjectModifier = function () {
        var shareLifeTime;
        if (!this._containedObject.attrs[ZaCos.A_zmailFileExternalShareLifetime]) {
            shareLifeTime = "FALSE";
        }  else {
            if (parseInt(this._containedObject.attrs[ZaCos.A_zmailFileExternalShareLifetime]) == 0 ) {
                shareLifeTime = "FALSE";
            } else {
                shareLifeTime = "TRUE";
            }
        }
        this._containedObject[ZaCos.A2_zmailExternalShareLimitLifetime] =  shareLifeTime;
        
        shareLifeTime = null;
        if (!this._containedObject.attrs[ZaCos.A_zmailFileShareLifetime]) {
            shareLifeTime = "FALSE";
        }  else {
            if (parseInt(this._containedObject.attrs[ZaCos.A_zmailFileShareLifetime]) == 0 ) {
                shareLifeTime = "FALSE";
            } else {
                shareLifeTime = "TRUE";
            }
        }
        this._containedObject[ZaCos.A2_zmailInternalShareLimitLifetime] =  shareLifeTime;
    }
    ZaTabView.ObjectModifiers["ZaCosXFormView"].push(ZaOctopus.CosObjectModifier);
}

if(ZaTabView.XFormModifiers["ZaCosXFormView"]) {
	ZaOctopus.COSXFormModifier = function(xFormObject,entry) {
		var cnt = xFormObject.items.length;
		var switchObj = null;
		for(var i = 0; i <cnt; i++) {
			if(xFormObject.items[i].type=="switch") {
				switchObj = xFormObject.items[i];
				break;				
			}
		}
		cnt = switchObj.items.length;
		for(var i = 0; i <cnt; i++) {
			if(switchObj.items[i].id=="cos_form_features_tab") {
				var tmpItems = switchObj.items[i].items;
				var cnt2 = tmpItems.length;
				for(var j=0; j<cnt2; j++) {
                    if(tmpItems[j].id=="cos_form_features_general" && tmpItems[j].items) {
                        tmpItems[j].visibilityChecks[0][1].push(ZaCos.A_zmailFeatureCrocodocEnabled);
                        tmpItems[j].visibilityChecks[0][1].push(ZaCos.A_zmailFeatureExternalFeedbackEnabled);
						var genernalFeatureItems = tmpItems[j].items;
                        ZaCosXFormView.FEATURE_TAB_ATTRS.push(ZaCos.A_zmailFeatureCrocodocEnabled);
                        ZaCosXFormView.FEATURE_TAB_ATTRS.push(ZaCos.A_zmailFeatureExternalFeedbackEnabled);
						genernalFeatureItems.push({
                            ref:ZaCos.A_zmailFeatureCrocodocEnabled,
                            type:_CHECKBOX_, msgName:org_zmail_octopus.LBL_zmailFeatureCrocodocrEnabled,
                            label:org_zmail_octopus.LBL_zmailFeatureCrocodocrEnabled,
                            trueValue:"TRUE", falseValue:"FALSE"}
                        );
                        genernalFeatureItems.push({
                            ref:ZaCos.A_zmailFeatureExternalFeedbackEnabled,
                            type:_CHECKBOX_, msgName:org_zmail_octopus.LBL_zmailFeatureExternalFeedbackEnabled,
                            label:org_zmail_octopus.LBL_zmailFeatureExternalFeedbackEnabled,
                            trueValue:"TRUE", falseValue:"FALSE"}
                        );
						break;
					}
				}
			} else if (switchObj.items[i].id=="cos_form_advanced_tab") {
				var tmpItems = switchObj.items[i].items;
				var cnt2 = tmpItems.length;
				for(var j=0;j<cnt2;j++) {
					if(tmpItems[j].id == "cos_timeout_settings") {
						tmpItems[j].items.push({ref:ZaCos.A_zmailDeviceAutoLockInteral, type:_LIFETIME_MINUTES_,
							visibilityChecks:[],enableDisableChecks:[],
							msgName:org_zmail_octopus.MSG_zmailDeviceAutoLockInteral,
							label:org_zmail_octopus.LBL_zmailDeviceAutoLockInteral,labelLocation:_LEFT_});
					}
					if(tmpItems[j].id == "cos_quota_settings") {
						tmpItems[j].items.push({ref:ZaCos.A_zmailFileUploadMaxSizePerFile, type:_TEXTFIELD_,
							msgName:org_zmail_octopus.MSG_zmailFileUploadMaxSizePerFile,
							textFieldCssClass:"admin_xform_number_input",
							label:org_zmail_octopus.LBL_zmailFileUploadMaxSizePerFile,labelLocation:_LEFT_});
					}
				}
			}
		}
		
		var tabBarChoices = xFormObject.items[1].choices;
		var tabCases = xFormObject.items[2].items;

		ZaCosXFormView.EXTERNAL_SHARING_TAB_ATTRS = [ZaCos.A_zmailFileExternalShareLifetime,ZaCos.A_zmailExternalShareDomainWhitelistEnabled];
		ZaCosXFormView.EXTERNAL_SHARING_TAB_RIGHTS = [];
		var externalShareTab = 0;
		
		if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.EXTERNAL_SHARING_TAB_ATTRS, ZaCosXFormView.EXTERNAL_SHARING_TAB_RIGHTS)) {
			externalShareTab = ++this.TAB_INDEX;
	    	tabBarChoices.push({value:externalShareTab, label:org_zmail_octopus.TABT_Sharing});
	    }
	    
		if(externalShareTab) {
        	var case9 = {type:_ZATABCASE_, colSizes:["auto"],numCols:1, caseKey:externalShareTab, id:"cos_form_external_sharing_tab"};
        	var case9Items = [
        	    {type:_ZA_TOP_GROUPER_, id:"cos_external_sharing_expiration_settings", colSizes:["50px","auto"],
        	    	label:org_zmail_octopus.NAD_ExternalShareExpirationGrouper,
        	    	items:[
        	    	       {ref:ZaCos.A_zmailExternalSharingEnabled, 
        	    	    	   type:_CHECKBOX_,subLabel:null,
        	    	    	   label:org_zmail_octopus.AllowExternalSharing,
        	    	    	   labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
        	    	    	   align:_RIGHT_
        	    	       },
        	    	       {type:_GROUP_,
        	    	    	   visibilityChecks:[[XFormItem.prototype.hasReadPermission,ZaCos.A_zmailFileExternalShareLifetime]],
        	    	    	   visibilityChangeEventSources:[ZaCos.A_zmailFileExternalShareLifetime],
        	    	    	   colSpan:2,numCols:5,colSizes:["50px", "auto","5px", "auto", "auto"],
        	    	           items:[
									{ref:ZaCos.A2_zmailExternalShareLimitLifetime, 
										type:_CHECKBOX_,subLabel:null,align:_RIGHT_,
										label:org_zmail_octopus.LimitExternalShareLifetime,
										labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
									    enableDisableChangeEventSources:[ZaCos.A_zmailExternalSharingEnabled],
									    enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A_zmailExternalSharingEnabled,"TRUE"]],
                                        elementChanged: function(elementValue,instanceValue, event) {
                                            if(elementValue != "TRUE") {
                                                this.setInstanceValue("0d", ZaCos.A_zmailFileExternalShareLifetime);
                                            }
                                            this.getForm().itemChanged(this, elementValue, event);
                                        },
									    visibilityChecks:[]
									},
									{type:_CELLSPACER_, height:"100%"},
									{ref:ZaCos.A_zmailFileExternalShareLifetime, type:_LIFETIME_,
			                               label:null, labelLocation:_NONE_,  bmolsnr: true,
			                               labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
			                               enableDisableChangeEventSources:[ZaCos.A2_zmailExternalShareLimitLifetime,ZaCos.A_zmailExternalSharingEnabled],
			                               enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A2_zmailExternalShareLimitLifetime,"TRUE"],[XForm.checkInstanceValue,ZaCos.A_zmailExternalSharingEnabled,"TRUE"]]
			                        }   
        	    	           ]                                   
        	    	       }
        	    	]
        	    },
        	    {type:_ZA_TOP_GROUPER_, id:"cos_external_sharing_domain_settings", colSizes:["50px","300px"],numCols:2, width:"350px",
        	    	label:org_zmail_octopus.ExternalShareDomainPolicyGrouper,
        	    	items:[
						{ref:ZaCos.A_zmailExternalShareDomainWhitelistEnabled, 
							type:_CHECKBOX_,subLabel:null,
							label:org_zmail_octopus.LBL_ExternalShareDomainWhitelist,
							labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
							visibilityChecks:[],align:_RIGHT_,
						    enableDisableChangeEventSources:[ZaCos.A_zmailExternalSharingEnabled],
						    enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A_zmailExternalSharingEnabled,"TRUE"]]						 						   
						},
						{type:_CELLSPACER_, height:"100%"},
				      	{type:_TEXTAREA_, ref:ZaCos.A_zmailExternalShareWhitelistDomain, 
				      		label:null,
							enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A_zmailExternalShareDomainWhitelistEnabled,"TRUE"],
							                     [XForm.checkInstanceValue,ZaCos.A_zmailExternalSharingEnabled,"TRUE"]],
							enableDisableChangeEventSources:[ZaCos.A_zmailExternalSharingEnabled,
							                                 ZaCos.A_zmailExternalShareDomainWhitelistEnabled]
				      	}
        	    	]
        	    },
        	    {type:_ZA_TOP_GROUPER_, id:"cos_internal_sharing_expiration_settings", colSizes:["50px","auto"],
        	    	label:org_zmail_octopus.NAD_InternalShareExpirationGrouper,
        	    	items:[
        	    	       {type:_GROUP_,
        	    	    	   visibilityChecks:[[XFormItem.prototype.hasReadPermission,ZaCos.A_zmailFileShareLifetime]],
        	    	    	   visibilityChangeEventSources:[ZaCos.A_zmailFileShareLifetime],
        	    	    	   colSpan:2,numCols:5,colSizes:["50px", "auto","5px", "auto", "auto"],
        	    	           items:[
									{ref:ZaCos.A2_zmailInternalShareLimitLifetime, 
										type:_CHECKBOX_,subLabel:null,align:_RIGHT_,
										label:org_zmail_octopus.LimitInternalShareLifetime,
										labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
									    enableDisableChangeEventSources:[],
									    enableDisableChecks:[],
                                        elementChanged: function(elementValue,instanceValue, event) {
                                            if(elementValue != "TRUE") {
                                                this.setInstanceValue("0d", ZaCos.A_zmailFileShareLifetime);
                                            }
                                            this.getForm().itemChanged(this, elementValue, event);
                                        },
									    visibilityChecks:[]
									},
									{type:_CELLSPACER_, height:"100%"},
									{ref:ZaCos.A_zmailFileShareLifetime, type:_LIFETIME_,
			                               label:null, labelLocation:_NONE_,  bmolsnr: true,
			                               labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
			                               enableDisableChangeEventSources:[ZaCos.A2_zmailInternalShareLimitLifetime],
			                               enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A2_zmailInternalShareLimitLifetime,"TRUE"]]
			                        }   
        	    	           ]                                   
        	    	       }
        	    	]
        	    }

        	];
            case9.items = case9Items;
            tabCases.push(case9);   
        }
		
        //retention
        /*
		ZaCosXFormView.FILE_RETENTION_TAB_ATTRS = [ZaCos.A_zmailFileExpirationWarningPeriod,ZaCos.A_zmailFileExpirationWarningPeriod,
		                                           ZaCos.A_zmailFileDeletionNotificationSubject,ZaCos.A_zmailFileDeletionNotificationBody,
		                                           ZaCos.A_zmailFileExpirationWarningSubject,ZaCos.A_zmailFileExpirationWarningBody];
		ZaCosXFormView.FILE_RETENTION_TAB_RIGHTS = [];
		var fileRetentionTab = 0;
		
		if(ZaTabView.isTAB_ENABLED(entry,ZaCosXFormView.FILE_RETENTION_TAB_ATTRS, ZaCosXFormView.FILE_RETENTION_TAB_RIGHTS)) {
			fileRetentionTab = ++this.TAB_INDEX;
	    	tabBarChoices.push({value:fileRetentionTab, label:org_zmail_octopus.TABT_FileRetention});
	    }
		
		if(fileRetentionTab) {
			var case10 = {type:_ZATABCASE_, caseKey:fileRetentionTab, id:"cos_file_retention_sharing_tab",numCols:1,colSizes:["*"]};
			var case10items = [
               	{type:_ZA_TOP_GROUPER_, 
               		label:org_zmail_octopus.LBL_Thresholds,  
					items: [	
		               {ref:ZaCos.A_zmailFileLifetime, type:_LIFETIME2_, 
		            	   msgName:org_zmail_octopus.MSG_zmailFileLifetime,
		            	   label:org_zmail_octopus.LBL_zmailFileLifetime,
		            	   labelLocation:_LEFT_
		               },
		               {ref:ZaCos.A_zmailFileExpirationWarningPeriod, type:_LIFETIME2_, 
		            	   msgName:org_zmail_octopus.MSG_zmailFileExpirationWarningPeriod,
		            	   label:org_zmail_octopus.LBL_zmailFileExpirationWarningPeriod,
		            	   labelLocation:_LEFT_
		               },
		               {type:_GROUP_,
    	    	    	   colSpan:2,numCols:3,colSizes:["275px", "55px","auto"],//numCols:5,colSizes:["50px", "auto","5px", "auto", "auto"],
    	    	           items:[
    	    	                {type:_GROUP_, cssStyle:"text-align:right;float:right;padding-right:5px",items:[ 
									{ref:ZaCos.A_zmailFileVersioningEnabled, subLabel:null,
										type:_CHECKBOX_,msgName:org_zmail_octopus.MSG_zmailFileVersioningEnabled,
										label:org_zmail_octopus.LBL_zmailFileVersioningEnabled,
										labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE"
									}],useParentTable:false
     	    	                },
								{ref:ZaCos.A_zmailFileVersionLifetime, type:_LIFETIME2_,
	                               label:null, labelLocation:_NONE_,
	                               labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
	                               enableDisableChangeEventSources:[ZaCos.A_zmailFileVersioningEnabled],
	                               enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A_zmailFileVersioningEnabled,"TRUE"]]
		                        }   
    	    	           ]                                   
    	    	       }
		           ]},
	               {type:_ZA_TOP_GROUPER_, 
					label:org_zmail_octopus.LBL_Emailtemplates,  
	           		items: [
		               {ref:ZaCos.A_zmailFileDeletionNotificationSubject, type:_TEXTFIELD_, cssClass:"admin_xform_name_input",
		                   msgName:org_zmail_octopus.LBL_zmailFileDeletionNotificationSubject,
		                   label:org_zmail_octopus.LBL_zmailFileDeletionNotificationSubject,
		                   labelLocation:_LEFT_
		               },
		               {type:_TEXTAREA_, ref:ZaCos.A_zmailFileDeletionNotificationBody, 
				      		label:org_zmail_octopus.LBL_zmailFileDeletionNotificationBody,
				      		msgName:org_zmail_octopus.MSG_zmailFileDeletionNotificationBody,
				      		labelLocation:_LEFT_
				       },
				       {ref:ZaCos.A_zmailFileExpirationWarningSubject, type:_TEXTFIELD_, cssClass:"admin_xform_name_input",
		                   msgName:org_zmail_octopus.LBL_zmailFileExpirationWarningSubject,
		                   label:org_zmail_octopus.LBL_zmailFileExpirationWarningSubject,
		                   labelLocation:_LEFT_
		               },
		               {type:_TEXTAREA_, ref:ZaCos.A_zmailFileExpirationWarningBody, 
				      		label:org_zmail_octopus.LBL_zmailFileExpirationWarningBody,
				      		msgName:org_zmail_octopus.MSG_zmailFileExpirationWarningBody,
				      		labelLocation:_LEFT_
				       }
		               ]
	               }
	         ];
			case10.items=case10items;
			tabCases.push(case10);
		}*/
		
	}
	
	
	ZaTabView.XFormModifiers["ZaCosXFormView"].push(ZaOctopus.COSXFormModifier);
}

if(ZaXDialog.XFormModifiers["ZaNewCosXWizard"]) {
	ZaOctopus.NewCOSXWizardModifier = function(xFormObject,entry) {
		var cnt = xFormObject.items.length;
		var switchObj = null;
		for(var i = 0; i <cnt; i++) {
			if(xFormObject.items[i].type=="switch") {
				switchObj = xFormObject.items[i];
				break;
			}
		}
		cnt = switchObj.items.length;
		for(var i = 0; i <cnt; i++) {
			if(switchObj.items[i].id=="cos_form_features_tab") {
				var tmpItems = switchObj.items[i].items;
				var cnt2 = tmpItems.length;
				for(var j=0; j<cnt2; j++) {
                    if(tmpItems[j].id=="cos_form_features_general" && tmpItems[j].items) {
                        tmpItems[j].visibilityChecks[0][1].push(ZaCos.A_zmailFeatureCrocodocEnabled);
                        tmpItems[j].visibilityChecks[0][1].push(ZaCos.A_zmailFeatureExternalFeedbackEnabled);
						var genernalFeatureItems = tmpItems[j].items;
                        ZaNewCosXWizard.FEATURE_TAB_ATTRS.push(ZaCos.A_zmailFeatureCrocodocEnabled);
                        ZaNewCosXWizard.FEATURE_TAB_ATTRS.push(ZaCos.A_zmailFeatureExternalFeedbackEnabled);
						genernalFeatureItems.push({
                            ref:ZaCos.A_zmailFeatureCrocodocEnabled,
                            type:_WIZ_CHECKBOX_, msgName:org_zmail_octopus.LBL_zmailFeatureCrocodocrEnabled,
                            label:org_zmail_octopus.LBL_zmailFeatureCrocodocrEnabled,
                            trueValue:"TRUE", falseValue:"FALSE"}
                        );
                        genernalFeatureItems.push({
                            ref:ZaCos.A_zmailFeatureExternalFeedbackEnabled,
                            type:_WIZ_CHECKBOX_, msgName:org_zmail_octopus.LBL_zmailFeatureExternalFeedbackEnabled,
                            label:org_zmail_octopus.LBL_zmailFeatureExternalFeedbackEnabled,
                            trueValue:"TRUE", falseValue:"FALSE"}
                        );
						break;
					}
				}
			}
		}
	}
	ZaXDialog.XFormModifiers["ZaNewCosXWizard"].push(ZaOctopus.NewCOSXWizardModifier);
}

if(ZaTabView.XFormModifiers["ZaAccountXFormView"]) {
	
	ZaOctopus.AccountXFormModifier = function(xFormObject,entry) {
		if(entry.attrs[ZaAccount.A_zmailIsExternalVirtualAccount] == "TRUE") {
			return;
		}
		var cnt = xFormObject.items.length;
		var switchObj = null;
		for(var i = 0; i <cnt; i++) {
			if(xFormObject.items[i].type=="switch")  {
				switchObj = xFormObject.items[i]; 
				break;
			}
		}
		cnt = switchObj.items.length;
		for(var i = 0; i <cnt; i++) {
			if(switchObj.items[i].id=="account_form_features_tab") {
				var tmpItems = switchObj.items[i].items;
				var cnt2 = tmpItems.length;
				for(var k=0; k<cnt2; k++) {
                    /*
					if(tmpItems[k].id=="account_form_features_major" && tmpItems[k].items) {
						var majorFeatureItems = tmpItems[k].items;
						for(var l=0;l<majorFeatureItems.length;l++) {
							if(majorFeatureItems[l].ref == ZaAccount.A_zmailFeatureBriefcasesEnabled) {
								//add filesharing dropdown 
								majorFeatureItems.splice(l+1,0,
										{ref:ZaAccount.A_zmailPrefFileSharingApplication,choices:ZaOctopus.fileSharingAppChoices,
											type:_SUPER_SELECT1_,resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
											label:org_zmail_octopus.LBL_zmailPrefFileSharingApplication,labelLocation:_LEFT_,
											enableDisableChecks:[[ZaItem.hasWritePermission,ZaAccount.A_zmailPrefFileSharingApplication]],
											enableDisableChangeEventSources:[ZaAccount.A_zmailFeatureBriefcasesEnabled]
										}		
								);
								break;
							}
						}
						break;
					} */
                    if(tmpItems[k].id=="account_form_features_general" && tmpItems[k].items) {
                        ZaAccountXFormView.FEATURE_TAB_ATTRS.push(ZaAccount.A_zmailFeatureCrocodocEnabled);
                        tmpItems[k].visibilityChecks[0][1].push(ZaCos.A_zmailFeatureCrocodocEnabled);
                        tmpItems[k].items.push(
                            {ref:ZaAccount.A_zmailFeatureCrocodocEnabled,
                                type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
                                msgName:org_zmail_octopus.LBL_zmailFeatureCrocodocrEnabled,checkBoxLabel:org_zmail_octopus.LBL_zmailFeatureCrocodocrEnabled,
                                trueValue:"TRUE", falseValue:"FALSE"
                            }
                        );
                    }
				}
			} else if (switchObj.items[i].id=="account_form_advanced_tab") {
				var tmpItems = switchObj.items[i].items;
				var cnt2 = tmpItems.length;
				for(var j=0;j<cnt2;j++) {
					if(tmpItems[j].id == "timeout_settings") {
						tmpItems[j].items.push({ref:ZaAccount.A_zmailDeviceAutoLockInteral, type:_SUPER_LIFETIME_MINUTES_,
							resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
							visibilityChecks:[],enableDisableChecks:[],
							msgName:org_zmail_octopus.MSG_zmailDeviceAutoLockInteral,
							colSpan:1, colSizes:["275px", "65px'", "210px", "*"],
							txtBoxLabel:org_zmail_octopus.LBL_zmailDeviceAutoLockInteral,labelLocation:_LEFT_});
					}
					if(tmpItems[j].id == "account_quota_settings") {
						tmpItems[j].items.splice(0,0,
								{ref:ZaAccount.A_zmailFileUploadMaxSizePerFile, type:_SUPER_TEXTFIELD_,
									resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
									msgName:org_zmail_octopus.MSG_zmailFileUploadMaxSizePerFile,
									textFieldCssClass:"admin_xform_number_input",
									colSpan:1,
									txtBoxLabel:org_zmail_octopus.LBL_zmailFileUploadMaxSizePerFile,
									labelLocation:_LEFT_}
						);
					}
				}
			}
		}
		var tabCases = xFormObject.items[2].items;
		var tabBarChoices = xFormObject.items[1].choices;
		
		var deviceHeaderList = new Array();
		deviceHeaderList[0] = new ZaListHeaderItem(ZaOctopus.deviceName, org_zmail_octopus.deviceName, null, "200px", null, null, false, true);
		deviceHeaderList[1] = new ZaListHeaderItem(ZaOctopus.deviceStatus, org_zmail_octopus.deviceStatus, null, "200px", null, null, false, true);
		deviceHeaderList[2] = new ZaListHeaderItem(ZaOctopus.deviceCreated, org_zmail_octopus.deviceCreated, null, "200px", null, null, false, true);
		deviceHeaderList[3] = new ZaListHeaderItem(ZaOctopus.deviceAccessed, org_zmail_octopus.deviceAccessed, null, "auto", null, null, false, true);

		var devicesTab = ++this.TAB_INDEX;
		var case1 = {type:_ZATABCASE_, caseKey:devicesTab, colSizes:["auto"],numCols:1, 
				items:[
					{ref:ZaAccount.A_octopusDevices, type:_DWT_LIST_, height:"150px", width:"90%",
					 	preserveSelection:false, multiselect:false,cssClass: "DLSource",
					 	headerList:deviceHeaderList, widgetClass:OctopusDeviceListView,
					 	visibilityChecks:[],enableDisableChecks:[],
					 	onSelection:ZaOctopus.deviceListSelectionListener,bmolsnr:true
					},
					{type:_GROUP_, numCols:2, colSizes:["100px","auto"/*,"180px","auto","180px"*/], width:"550px",
						cssStyle:"margin-bottom:10px;padding-bottom:0px;margin-top:10px;pxmargin-left:10px;margin-right:10px;",
						items: [
							{type:_DWT_BUTTON_, label:org_zmail_octopus.WipeBtn,width:"80px",
								onActivate:"ZaOctopus.wipeButtonListener.call(this);",
								enableDisableChecks:[ZaOctopus.isWipeButtonEnabled],
	                            enableDisableChangeEventSources:[ZaOctopus.A2_device_selection_cache],
	                            visibilityChecks:[]
							},
							{type:_CELLSPACER_}
						]
					}					
        ]};
		
		tabCases.push(case1);
		
		tabBarChoices.push({value:devicesTab,label:org_zmail_octopus.Devices});
		
		ZaAccountXFormView.EXTERNAL_SHARING_TAB_ATTRS = [ZaAccount.A_zmailFileExternalShareLifetime,ZaAccount.A_zmailExternalShareDomainWhitelistEnabled];
		ZaAccountXFormView.EXTERNAL_SHARING_TAB_RIGHTS = [];
		var externalShareTab = 0;
		
		if(ZaTabView.isTAB_ENABLED(entry,ZaAccountXFormView.EXTERNAL_SHARING_TAB_ATTRS, ZaAccountXFormView.EXTERNAL_SHARING_TAB_RIGHTS)) {
			externalShareTab = ++this.TAB_INDEX;
	    	tabBarChoices.push({value:externalShareTab, label:org_zmail_octopus.TABT_Sharing});
	    }
	    
		if(externalShareTab) {
        	var case2 = {type:_ZATABCASE_, colSizes:["auto"],numCols:1,caseKey:externalShareTab, id:"account_form_external_sharing_tab"};
        	var case2Items = [
        	    {type:_ZA_TOP_GROUPER_, id:"account_external_sharing_expiration_settings", colSizes:["auto"],numCols:1,
        	    	label:org_zmail_octopus.NAD_ExternalShareExpirationGrouper,
        	    	items:[
        	    	       {ref:ZaAccount.A_zmailExternalSharingEnabled, 
        	    	    	   type:_SUPER_CHECKBOX_,checkboxSubLabel:null,
        	    	    	   colSizes:["275px","245px", "*"], colSpan:"*",
        	    	    	   checkBoxLabel:org_zmail_octopus.AllowExternalSharing,
        	    	    	   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
        	    	    	   labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
        	    	    	   align:_RIGHT_
        	    	       },
        	    	       {type:_GROUP_,
        	    	    	   colSpan:"*",numCols:5,colSizes:["30px", "245px", "245px", "auto", "auto"],
        	    	           items:[
									{ref:ZaAccount.A2_zmailExternalShareLimitLifetime, 
										type:_CHECKBOX_,subLabel:null,
										bmolsnr:true,
										id:"account_limit_external_share_lifetime",
										labelCssClass: "gridGroupBodyLabel",
										label:org_zmail_octopus.LimitExternalShareLifetime,
										labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
									    enableDisableChangeEventSources:[ZaAccount.A_zmailExternalSharingEnabled],
									    enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailExternalSharingEnabled,"TRUE"]],
									    updateElement:function(value) {
											Super_XFormItem.updateCss.call(this,5);
											Checkbox_XFormItem.prototype.updateElement.call(this, value);
										},
                                        elementChanged: function(elementValue,instanceValue, event) {
                                            if(elementValue != "TRUE") {
                                                this.setInstanceValue("0d", ZaAccount.A_zmailFileExternalShareLifetime);
                                            }
                                            this.getForm().itemChanged(this, elementValue, event);
                                        },
										visibilityChecks:[]
									},
									{ref:ZaAccount.A_zmailFileExternalShareLifetime, type:_LIFETIME_,
									   id:"account_external_share_lifetime",
		                               label:null, labelLocation:_NONE_,
		                               bmolsnr:true,
		                               labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
		                               enableDisableChangeEventSources:[ZaAccount.A2_zmailExternalShareLimitLifetime,ZaAccount.A_zmailExternalSharingEnabled],
		                               enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A2_zmailExternalShareLimitLifetime,"TRUE"],[XForm.checkInstanceValue,ZaAccount.A_zmailExternalSharingEnabled,"TRUE"]],
		                               updateElement:function(value) {
											Super_XFormItem.updateCss.call(this,5);
											Lifetime_XFormItem.prototype.updateElement.call(this, value);
		                       		   }
			                        },
			                        {	
										type:_DWT_BUTTON_,
										ref:".", label:ZaMsg.NAD_ResetToCOS,
										visibilityChecks:[
										    [XFormItem.prototype.hasReadPermission,ZaAccount.A_zmailFileExternalShareLifetime],
										        function(){
										       		var rad1 = (this.getForm().getItemsById("account_external_share_lifetime")[0].getModelItem().getLocalValue(this.getInstance()) != null);
										       		var rad2 = (this.getForm().getItemsById("account_limit_external_share_lifetime")[0].getModelItem().getLocalValue(this.getInstance()) != null);	
										       		return (rad1 || rad2);										                	  
										         }
										],
										visibilityChangeEventSources:[ZaAccount.A_zmailFileExternalShareLifetime,ZaAccount.A2_zmailExternalShareLimitLifetime],
										onActivate:function(ev) {
											this.setInstanceValue(null, ZaAccount.A_zmailFileExternalShareLifetime);
											this.setInstanceValue(null,ZaAccount.A2_zmailExternalShareLimitLifetime);
                                            this.getForm().parent.setDirty(true);
			                            },
										onChange:ZaTabView.onFormFieldChanged
								   }  
        	    	           ]                                   
        	    	       }
                           
        	    	]
        	    },
        	    {type:_ZA_TOP_GROUPER_, id:"account_external_sharing_domain_settings",colSizes:["auto"],numCols:1,
        	    	label:org_zmail_octopus.ExternalShareDomainPolicyGrouper,
        	    	items:[
						{ref:ZaAccount.A_zmailExternalShareDomainWhitelistEnabled, 
							type:_SUPER_CHECKBOX_,checkboxSubLabel:null,
							colSizes:["275px","275px", "*"],
							checkBoxLabel:org_zmail_octopus.LBL_ExternalShareDomainWhitelistCheckbox,
							trueValue:"TRUE", falseValue:"FALSE",
							visibilityChecks:[],
							resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
						    enableDisableChangeEventSources:[ZaAccount.A_zmailExternalSharingEnabled],
						    enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailExternalSharingEnabled,"TRUE"]]						 						   
						},
						{type:_CELLSPACER_, height:"100%"},
				      	{type:_SUPER_TEXTAREA_, ref:ZaAccount.A_zmailExternalShareWhitelistDomain, 
				      		txtBoxLabel:org_zmail_octopus.LBL_ExternalShareDomainWhitelist,
				      		txtBoxLabelCssClass:"gridGroupBodyLabel",
				      		resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
				      		colSizes:["275px","275px", "*"],
							enableDisableChecks:[[XForm.checkInstanceValue,ZaAccount.A_zmailExternalShareDomainWhitelistEnabled,"TRUE"],
							                     [XForm.checkInstanceValue,ZaAccount.A_zmailExternalSharingEnabled,"TRUE"]],
							enableDisableChangeEventSources:[ZaAccount.A_zmailExternalSharingEnabled,
							                                 ZaAccount.A_zmailExternalShareDomainWhitelistEnabled]
				      	}
        	    	]
        	    },
        	    {type:_ZA_TOP_GROUPER_, id:"account_internal_sharing_expiration_settings", colSizes:["auto"],numCols:1,
        	    	label:org_zmail_octopus.NAD_InternalShareExpirationGrouper,
        	    	items:[
        	    	       {type:_GROUP_,
        	    	    	   colSpan:"*",numCols:5,colSizes:["30px", "245px", "245px", "auto", "auto"],
        	    	           items:[
									{ref:ZaAccount.A2_zmailInternalShareLimitLifetime, 
										type:_CHECKBOX_,subLabel:null,
										bmolsnr:true,
										id:"account_limit_internal_share_lifetime",
										labelCssClass: "gridGroupBodyLabel",
										label:org_zmail_octopus.LimitInternalShareLifetime,
										labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
									    enableDisableChangeEventSources:[],
									    enableDisableChecks:[],
									    updateElement:function(value) {
											Super_XFormItem.updateCss.call(this,5);
											Checkbox_XFormItem.prototype.updateElement.call(this, value);
										},
                                        elementChanged: function(elementValue,instanceValue, event) {
                                            if(elementValue != "TRUE") {
                                                this.setInstanceValue("0d", ZaAccount.A_zmailFileShareLifetime);
                                            }
                                            this.getForm().itemChanged(this, elementValue, event);
                                        },
										visibilityChecks:[]
									},
									{ref:ZaAccount.A_zmailFileShareLifetime, type:_LIFETIME_,
									   id:"account_internal_share_lifetime",
		                               label:null, labelLocation:_NONE_,
		                               bmolsnr:true,
		                               labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
		                               enableDisableChangeEventSources:[ZaAccount.A2_zmailInternalShareLimitLifetime],
		                               enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A2_zmailInternalShareLimitLifetime,"TRUE"]],
		                               updateElement:function(value) {
											Super_XFormItem.updateCss.call(this,5);
											Lifetime_XFormItem.prototype.updateElement.call(this, value);
		                       		   }
			                        },
			                        {	
										type:_DWT_BUTTON_,
										ref:".", label:ZaMsg.NAD_ResetToCOS,
										visibilityChecks:[
										    [XFormItem.prototype.hasReadPermission,ZaAccount.A_zmailFileShareLifetime],
										        function(){
										       		var rad1 = (this.getForm().getItemsById("account_internal_share_lifetime")[0].getModelItem().getLocalValue(this.getInstance()) != null);
										       		var rad2 = (this.getForm().getItemsById("account_limit_internal_share_lifetime")[0].getModelItem().getLocalValue(this.getInstance()) != null);	
										       		return (rad1 || rad2);										                	  
										         }
										],
										visibilityChangeEventSources:[ZaAccount.A_zmailFileShareLifetime,ZaAccount.A2_zmailInternalShareLimitLifetime],
										onActivate:function(ev) {
											this.setInstanceValue(null, ZaAccount.A_zmailFileShareLifetime);
											this.setInstanceValue(null,ZaAccount.A2_zmailInternalShareLimitLifetime);
                                            this.getForm().parent.setDirty(true);
			                            },
										onChange:ZaTabView.onFormFieldChanged
								   }  
        	    	           ]                                   
        	    	       }
                           
        	    	]
        	    }
        	]
        	case2.items = case2Items;
            tabCases.push(case2);  
		}
		
        //retention
        /*
		ZaAccountXFormView.FILE_RETENTION_TAB_ATTRS = [ZaAccount.A_zmailFileExpirationWarningPeriod,ZaAccount.A_zmailFileExpirationWarningPeriod,
		                                               ZaAccount.A_zmailFileDeletionNotificationSubject,ZaAccount.A_zmailFileDeletionNotificationBody,
		                                               ZaAccount.A_zmailFileExpirationWarningSubject,ZaAccount.A_zmailFileExpirationWarningBody];
		ZaAccountXFormView.FILE_RETENTION_TAB_RIGHTS = [];
		var fileRetentionTab = 0;
		
		if(true) {
			fileRetentionTab = ++this.TAB_INDEX;
	    	tabBarChoices.push({value:fileRetentionTab, label:org_zmail_octopus.TABT_FileRetention});
	    }
		
		if(fileRetentionTab) {
			var case10 = {type:_ZATABCASE_, caseKey:fileRetentionTab, id:"account_file_retention_sharing_tab",numCols:1,colSizes:["*"]};
			var case10items = [
				{type:_ZA_TOP_GROUPER_, 
					label:org_zmail_octopus.LBL_Thresholds,  
					items: [	
		               {ref:ZaAccount.A_zmailFileLifetime, type:_SUPER_LIFETIME2_, 
		            	   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
		            	   msgName:org_zmail_octopus.MSG_zmailFileLifetime,
		            	   txtBoxLabel:org_zmail_octopus.LBL_zmailFileLifetime,
		            	   labelLocation:_LEFT_
		               },
		               {ref:ZaAccount.A_zmailFileExpirationWarningPeriod, type:_SUPER_LIFETIME2_,
		            	   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
		            	   msgName:org_zmail_octopus.MSG_zmailFileExpirationWarningPeriod,
		            	   txtBoxLabel:org_zmail_octopus.LBL_zmailFileExpirationWarningPeriod,
		            	   labelLocation:_LEFT_
		               },
		               {type:_GROUP_,
    	    	    	   colSpan:2,numCols:6,colSizes:["50px", "auto","5px", "auto", "auto", "auto"],
    	    	           items:[
								{ref:ZaAccount.A_zmailFileVersioningEnabled, 
									type:_CHECKBOX_,subLabel:null,
									bmolsnr:true,
									id:"account_limit_file_versions",
									label:org_zmail_octopus.LBL_zmailFileVersioningEnabled,
									labelLocation:_RIGHT_, trueValue:"TRUE", falseValue:"FALSE",
								    updateElement:function(value) {
										Super_XFormItem.updateCss.call(this,5);
										Checkbox_XFormItem.prototype.updateElement.call(this, value);
									}
								},
								{type:_CELLSPACER_, height:"100%"},
								{ref:ZaAccount.A_zmailFileVersionLifetime, type:_LIFETIME2_,
								   id:"account_file_version_age",
	                               label:null, labelLocation:_NONE_,
	                               bmolsnr:true,
	                               labelCssStyle:"white-space:normal;",nowrap:false,labelWrap:true,
	                               enableDisableChangeEventSources:[ZaAccount.A_zmailFileVersioningEnabled],
	                               enableDisableChecks:[[XForm.checkInstanceValue,ZaCos.A_zmailFileVersioningEnabled,"TRUE"]],
	                               updateElement:function(value) {
										Super_XFormItem.updateCss.call(this,5);
										Lifetime_XFormItem.prototype.updateElement.call(this, value);
	                       		   }
		                        },
		                        {	
									type:_DWT_BUTTON_,
									ref:".", label:ZaMsg.NAD_ResetToCOS,
									visibilityChecks:[
									    [XFormItem.prototype.hasReadPermission,ZaAccount.A_zmailFileVersionLifetime],
									    [XFormItem.prototype.hasReadPermission,ZaAccount.A_zmailFileVersionLifetime],
									        function(){
									       		var rad1 = (this.getForm().getItemsById("account_file_version_age")[0].getModelItem().getLocalValue(this.getInstance()) != null);
									       		var rad2 = (this.getForm().getItemsById("account_limit_file_versions")[0].getModelItem().getLocalValue(this.getInstance()) != null);	
									       		return (rad1 || rad2);										                	  
									         }
									],
									visibilityChangeEventSources:[ZaAccount.A_zmailFileVersionLifetime,ZaAccount.A_zmailFileVersionLifetime],
									onActivate:function(ev) {
										this.setInstanceValue(null, ZaAccount.A_zmailFileVersionLifetime);
										this.setInstanceValue(null,ZaAccount.A_zmailFileVersionLifetime);
		                            },
									onChange:ZaTabView.onFormFieldChanged
							   }  
    	    	           ]                                   
    	    	       }		               
               ]},
               {type:_ZA_TOP_GROUPER_, 
					label:org_zmail_octopus.LBL_Emailtemplates,  
           			items: [	               
		               {ref:ZaAccount.A_zmailFileDeletionNotificationSubject, type:_SUPER_TEXTFIELD_, 
		            	   colSizes:["275px", "275px", "*"],
		            	   textFieldWidth: "250px",
		            	   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
		                   msgName:org_zmail_octopus.LBL_zmailFileDeletionNotificationSubject,
		                   txtBoxLabel:org_zmail_octopus.LBL_zmailFileDeletionNotificationSubject,
		                   labelLocation:_LEFT_
		               },
		               {ref:ZaAccount.A_zmailFileDeletionNotificationBody,type:_SUPER_TEXTAREA_, 
		            	   colSizes:["275px", "275px", "*"],
		            	   txtBoxLabel:org_zmail_octopus.LBL_zmailFileDeletionNotificationBody,
		            	   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
		            	   msgName:org_zmail_octopus.MSG_zmailFileDeletionNotificationBody,
		            	   labelLocation:_LEFT_
				       },
				       {ref:ZaAccount.A_zmailFileExpirationWarningSubject, type:_SUPER_TEXTFIELD_, 
		            	   colSizes:["275px", "275px", "*"],
		            	   textFieldWidth: "250px",				    	   
		                   msgName:org_zmail_octopus.LBL_zmailFileExpirationWarningSubject,
		                   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
		                   txtBoxLabel:org_zmail_octopus.LBL_zmailFileExpirationWarningSubject,
		                   labelLocation:_LEFT_
		               },
		               {ref:ZaAccount.A_zmailFileExpirationWarningBody,type:_SUPER_TEXTAREA_, 
		            	   colSizes:["275px", "275px", "*"],
		            	   resetToSuperLabel:ZaMsg.NAD_ResetToCOS,
		            	   txtBoxLabel:org_zmail_octopus.LBL_zmailFileExpirationWarningBody,
		            	   msgName:org_zmail_octopus.MSG_zmailFileExpirationWarningBody,
		            	   labelLocation:_LEFT_
				       }
		            ]
               }
            ];
			case10.items=case10items;
			tabCases.push(case10);
				
		} */
	}
	ZaTabView.XFormModifiers["ZaAccountXFormView"].push(ZaOctopus.AccountXFormModifier);
}

if(ZaTabView.XFormModifiers["GlobalConfigXFormView"]) {

	ZaOctopus.GlobalXFormModifier = function(xFormObject,entry) {
		var cnt = xFormObject.items.length;
		var switchObj = null;
		for(var i = 0; i <cnt; i++) {
			if(xFormObject.items[i].type=="switch")  {
				switchObj = xFormObject.items[i];
				break;
			}
		}
		cnt = switchObj.items.length;

		var tabCases = xFormObject.items[2].items;
		var tabBarChoices = xFormObject.items[1].choices;

		GlobalConfigXFormView.SMTP_TAB_ATTRS = [ZaGlobalConfig.A_zmailShareNotificationMtaEnabled,
                                                ZaGlobalConfig.A_zmailShareNotificationMtaHostname,
                                                ZaGlobalConfig.A_zmailShareNotificationMtaPort,
                                                ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType,
                                                ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired,
                                                ZaGlobalConfig.A_zmailShareNotificationMtaAuthAccount,
                                                ZaGlobalConfig.A_zmailShareNotificationMtaAuthPassword
                                                ];
		GlobalConfigXFormView.SMTP_TAB_RIGHTS = [];
		var smtpTab = 0;

		if(ZaTabView.isTAB_ENABLED(entry,GlobalConfigXFormView.SMTP_TAB_ATTRS , GlobalConfigXFormView.SMTP_TAB_RIGHTS)) {
			smtpTab = ++this.TAB_INDEX;
	    	tabBarChoices.push({value:smtpTab, label:org_zmail_octopus.TABT_SMTP_SETTING});
	    }

		if(smtpTab) {
        	var smtpCase = {type:_ZATABCASE_, colSizes:["auto"],numCols:1,caseKey:smtpTab, id:"global_smtp_tab"};
        	var smtpCaseItems = [
        	    {type:_ZA_TOP_GROUPER_, id:"global_smtp_settings", numCols:2,colSizes: ["275px","auto"],
        	    	label:org_zmail_octopus.NAD_SmtpSettingGrouper,
        	    	items:[
                        {ref:ZaGlobalConfig.A_zmailShareNotificationMtaEnabled, type: _CHECKBOX_,
                            label: org_zmail_octopus.LBL_SmtpMtaEnabled,
                            trueValue: "TRUE", falseValue: "FALSE"
                        },
                        { ref: ZaGlobalConfig.A_zmailShareNotificationMtaHostname,
                            type: _TEXTFIELD_,width: "60%",
                            label: org_zmail_octopus.LBL_SmtpMtaHostName
                        },
                        { ref: ZaGlobalConfig.A_zmailShareNotificationMtaPort,
                            type: _TEXTFIELD_, width: "60%",
                            label: org_zmail_octopus.LBL_SmtpMtaPort
                        },
                        {  type:_GROUP_,  label:org_zmail_octopus.LBL_SmtpEncryp, colSize:["250px", "*"],
                            visibilityChecks:[[XFormItem.prototype.hasReadPermission,ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType]],
                            items:[
                            {ref:ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType, type:_RADIO_, groupname:"global_connection_type_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionNoEncrypResult,
                                bmolsnr: true,
                                updateElement:function () {
                                    this.getElement().checked = (this.getInstanceValue() == "CLEARTEXT");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("CLEARTEXT");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),"CLEARTEXT", event);
                                    }

                                }
							},
                            {ref:ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType, type:_RADIO_, groupname:"global_connection_type_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionSSL,
                                bmolsnr: true,
                                updateElement:function () {
                                    this.getElement().checked = (this.getInstanceValue() == "SSL");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("SSL");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),"SSL", event);
                                    }

                                }
							},
                            {ref:ZaGlobalConfig.A_zmailShareNotificationMtaConnectionType, type:_RADIO_, groupname:"global_connection_type_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionStartTLS,
                                bmolsnr: true,
                                updateElement:function () {
                                    this.getElement().checked = (this.getInstanceValue() == "STARTTLS");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("STARTTLS");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),"STARTTLS", event);
                                    }

                                }
							}

                        ]

                        },
                        {  type:_GROUP_,  label:org_zmail_octopus.LBL_SmtpAuth, colSize:["250px", "*"],
                            visibilityChecks:[[XFormItem.prototype.hasReadPermission,ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired]],
                            items:[
                            {ref:ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired, type:_RADIO_, groupname:"global_auth_required_settings",
                                label:org_zmail_octopus.LBL_SmtpNoAuthRequired,
                                bmolsnr: true,
                                updateElement:function () {
                                    this.getElement().checked = (this.getInstanceValue() == "FALSE");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("FALSE");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),instanceValue, event);
                                    }

                                }
							},
                            {ref:ZaGlobalConfig.A_zmailShareNotificationMtaAuthRequired, type:_RADIO_, groupname:"global_auth_required_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionStartTLS,
                                bmolsnr: true,
                                updateElement:function () {
                                    this.getElement().checked = (this.getInstanceValue() == "TRUE");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("TRUE");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),instanceValue, event);
                                    }

                                }
							},
                            { type: _DWT_ALERT_,
                              colSpan: "*",
                              containerCssStyle: "padding-bottom:0px",
                              style: DwtAlert.WARNING,
                              iconVisible: false,
                              content: org_zmail_octopus.MSG_AuthAlert
                            }
                        ]

                        },
                        { ref: ZaGlobalConfig.A_zmailShareNotificationMtaAuthAccount,
                            type: _TEXTFIELD_,width: "60%",
                            label: org_zmail_octopus.LBL_SmtpMtaAuthAccount
                        },
                        { ref: ZaGlobalConfig.A_zmailShareNotificationMtaAuthPassword,
                            type: _TEXTFIELD_, width: "60%",
                            label: org_zmail_octopus.LBL_SmtpMtaAuthPassword
                        }

        	    	]
        	    }
        	]
        	smtpCase.items = smtpCaseItems;
            tabCases.push(smtpCase);
		}
	}
	ZaTabView.XFormModifiers["GlobalConfigXFormView"].push(ZaOctopus.GlobalXFormModifier);
}

if(ZaTabView.XFormModifiers["ZaServerXFormView"]) {

	ZaOctopus.ServerXFormModifier = function(xFormObject,entry) {
		var cnt = xFormObject.items.length;
		var switchObj = null;
		for(var i = 0; i <cnt; i++) {
			if(xFormObject.items[i].type=="switch")  {
				switchObj = xFormObject.items[i];
				break;
			}
		}
		cnt = switchObj.items.length;

		var tabCases = xFormObject.items[2].items;
		var tabBarChoices = xFormObject.items[1].choices;

		ZaServerXFormView.SMTP_TAB_ATTRS = [ZaServer.A_zmailShareNotificationMtaEnabled,
                                            ZaServer.A_zmailShareNotificationMtaHostname,
                                            ZaServer.A_zmailShareNotificationMtaPort,
                                            ZaServer.A_zmailShareNotificationMtaConnectionType,
                                            ZaServer.A_zmailShareNotificationMtaAuthRequired,
                                            ZaServer.A_zmailShareNotificationMtaAuthAccount,
                                            ZaServer.A_zmailShareNotificationMtaAuthPassword
                                            ];
		ZaServerXFormView.SMTP_TAB_RIGHTS = [];
        ZaServerXFormView.checkIfConnectionTypeOverwritten = function () {
	        return this.getForm().getItemsById("ConectionType")[0].getModelItem().getLocalValue(this.getInstance()) != null;
        }
        ZaServerXFormView.resetConnectionTypeToGlobal = function () {
            this.setInstanceValue(null, ZaServer.A_zmailShareNotificationMtaConnectionType);
        }

        ZaServerXFormView.checkIfAuthRequiredOverwritten = function () {
	        return this.getForm().getItemsById("AuthReuqired")[0].getModelItem().getLocalValue(this.getInstance()) != null;
        }
        ZaServerXFormView.resetAuthRequiredToGlobal = function () {
            this.setInstanceValue(null, ZaServer.A_zmailShareNotificationMtaAuthRequired);
        }
		var smtpTab = 0;

		if(ZaTabView.isTAB_ENABLED(entry,ZaServerXFormView.SMTP_TAB_ATTRS , ZaServerXFormView.SMTP_TAB_RIGHTS)) {
			smtpTab = ++this.TAB_INDEX;
	    	tabBarChoices.push({value:smtpTab, label:org_zmail_octopus.TABT_SMTP_SETTING});
	    }

		if(smtpTab) {
        	var smtpCase = {type:_ZATABCASE_, colSizes:["auto"],numCols:1,caseKey:smtpTab, id:"global_smtp_tab"};
        	var smtpCaseItems = [
        	    {type:_ZA_TOP_GROUPER_, id:"global_smtp_settings", numCols:2,colSizes: ["275px","auto"],
                    width: "100%",
        	    	label:org_zmail_octopus.NAD_SmtpSettingGrouper,
        	    	items:[
                        {ref:ZaServer.A_zmailShareNotificationMtaEnabled,
                            type:_SUPER_CHECKBOX_, resetToSuperLabel:ZaMsg.NAD_ResetToGlobal,
                            colSpan:2, width: "100%", numCols:3, colSizes:["275px", "*", "150px"],
                            checkBoxLabel:org_zmail_octopus.LBL_SmtpMtaEnabled,
                            trueValue:"TRUE", falseValue:"FALSE",
                            onChange:ZaServerXFormView.onFormFieldChanged
                        },
                        { ref: ZaServer.A_zmailShareNotificationMtaHostname,
                            colSpan:2, width: "100%", numCols:3, colSizes:["275px", "*", "150px"],
                            type: _SUPER_TEXTFIELD_,textFieldWidth: "60%",
                            txtBoxLabel: org_zmail_octopus.LBL_SmtpMtaHostName,
                            onChange: ZaServerXFormView.onFormFieldChanged,
                            resetToSuperLabel:ZaMsg.NAD_ResetToGlobal
                        },
                        { ref: ZaServer.A_zmailShareNotificationMtaPort,
                            colSpan:2, width: "100%", numCols:3, colSizes:["275px", "*", "150px"],
                            type: _SUPER_TEXTFIELD_, textFieldWidth: "60%",
                            txtBoxLabel: org_zmail_octopus.LBL_SmtpMtaPort,
                            onChange: ZaServerXFormView.onFormFieldChanged,
                            resetToSuperLabel:ZaMsg.NAD_ResetToGlobal
                        },
                        {  type:_GROUP_,  label:org_zmail_octopus.LBL_SmtpEncryp,
                            colSizes:["30px", "*", "150px"], numCols:3, width: "100%",
                            visibilityChecks:[[XFormItem.prototype.hasReadPermission,ZaServer.A_zmailShareNotificationMtaConnectionType]],
                            items:[
                            {ref:ZaServer.A_zmailShareNotificationMtaConnectionType, type:_RADIO_, groupname:"global_connection_type_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionNoEncrypResult,
                                bmolsnr: true,
                                updateElement:function () {
                                    Super_XFormItem.updateCss.call(this,1);
                                    this.getElement().checked = (this.getInstanceValue() == "CLEARTEXT");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("CLEARTEXT");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),"CLEARTEXT", event);
                                    }

                                }
							},
                            {type:_DWT_BUTTON_, label:ZaMsg.NAD_ResetToGlobal,
                                visibilityChecks:[ZaServerXFormView.checkIfConnectionTypeOverwritten],
                                visibilityChangeEventSources:[ZaServer.A_zmailShareNotificationMtaConnectionType],
                                onActivate:ZaServerXFormView.resetConnectionTypeToGlobal,
                                rowSpan:3, autoPadding: false
                            },
                            {ref:ZaServer.A_zmailShareNotificationMtaConnectionType, type:_RADIO_, groupname:"global_connection_type_settings",
                                id: "ConectionType",
                                label:org_zmail_octopus.LBL_MtaConnectionSSL,
                                bmolsnr: true,
                                updateElement:function () {
                                    Super_XFormItem.updateCss.call(this,1);
                                    this.getElement().checked = (this.getInstanceValue() == "SSL");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("SSL");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),"SSL", event);
                                    }

                                }
							},
                            {ref:ZaServer.A_zmailShareNotificationMtaConnectionType, type:_RADIO_, groupname:"global_connection_type_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionStartTLS,
                                bmolsnr: true,
                                updateElement:function () {
                                    Super_XFormItem.updateCss.call(this,1);
                                    this.getElement().checked = (this.getInstanceValue() == "STARTTLS");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("STARTTLS");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),"STARTTLS", event);
                                    }

                                }
							}

                        ]

                        },
                        {  type:_GROUP_,  label:org_zmail_octopus.LBL_SmtpAuth,
                            colSizes:["30px", "*", "150px"], numCols:3, width: "100%",
                            visibilityChecks:[[XFormItem.prototype.hasReadPermission,ZaServer.A_zmailShareNotificationMtaAuthRequired]],
                            items:[
                            {ref:ZaServer.A_zmailShareNotificationMtaAuthRequired, type:_RADIO_, groupname:"global_auth_required_settings",
                                label:org_zmail_octopus.LBL_SmtpNoAuthRequired,
                                bmolsnr: true,
                                id: "AuthReuqired",
                                updateElement:function () {
                                    Super_XFormItem.updateCss.call(this,1);
                                    this.getElement().checked = (this.getInstanceValue() == "FALSE");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("FALSE");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),instanceValue, event);
                                    }

                                }
							},
                            {type:_DWT_BUTTON_, label:ZaMsg.NAD_ResetToGlobal,
                                visibilityChecks:[ZaServerXFormView.checkIfAuthRequiredOverwritten],
                                visibilityChangeEventSources:[ZaServer.A_zmailShareNotificationMtaAuthRequired],
                                onActivate:ZaServerXFormView.resetAuthRequiredToGlobal,
                                rowSpan:2, autoPadding: false
                            },
                            {ref:ZaServer.A_zmailShareNotificationMtaAuthRequired, type:_RADIO_, groupname:"global_auth_required_settings",
                                label:org_zmail_octopus.LBL_MtaConnectionStartTLS,
                                bmolsnr: true,
                                updateElement:function () {
                                    Super_XFormItem.updateCss.call(this,1);
                                    this.getElement().checked = (this.getInstanceValue() == "TRUE");
                                },
                                elementChanged: function(elementValue,instanceValue, event) {
                                    if (elementValue == true) {
                                        this.setInstanceValue ("TRUE");
                                        this.getForm().itemChanged(this.getParentItem().getParentItem().getParentItem(),instanceValue, event);
                                    }

                                }
							},
                            { type: _DWT_ALERT_,
                              colSpan: "*",
                              containerCssStyle: "padding-bottom:0px",
                              style: DwtAlert.WARNING,
                              iconVisible: false,
                              content: org_zmail_octopus.MSG_AuthAlert
                            }

                        ]

                        },
                        { ref: ZaServer.A_zmailShareNotificationMtaAuthAccount,
                            type: _SUPER_TEXTFIELD_,textFieldWidth: "60%",
                            colSpan:2, width: "100%", numCols:3, colSizes:["275px", "*", "150px"],
                            txtBoxLabel: org_zmail_octopus.LBL_SmtpMtaAuthAccount,
                            onChange: ZaServerXFormView.onFormFieldChanged,
                            resetToSuperLabel:ZaMsg.NAD_ResetToGlobal
                        },
                        { ref: ZaServer.A_zmailShareNotificationMtaAuthPassword,
                            type: _SUPER_TEXTFIELD_, textFieldWidth: "60%",
                            colSpan:2, width: "100%", numCols:3, colSizes:["275px", "*", "150px"],
                            txtBoxLabel: org_zmail_octopus.LBL_SmtpMtaAuthPassword,
                            onChange: ZaServerXFormView.onFormFieldChanged,
                            resetToSuperLabel:ZaMsg.NAD_ResetToGlobal
                        }

        	    	]
        	    }
        	]
        	smtpCase.items = smtpCaseItems;
            tabCases.push(smtpCase);
		}
	}
	ZaTabView.XFormModifiers["ZaServerXFormView"].push(ZaOctopus.ServerXFormModifier);
}
if(ZaTabView.XFormModifiers["ZaHelpView"]) {
	ZaOctopus.HelpViewXFormModifier = function(xFormObject) {
            if (!ZaSettings.isNetworkVersion ()) {
                 return ;
            }
			var octopusNetworkHelpItems = [
				{type:_GROUP_,numCols:2, items: [
						{type:_OUTPUT_, value:AjxImg.getImageHtml("PDFDoc")},
						{type:_ANCHOR_, cssStyle:"font-size:12px;", showInNewWindow:true, labelLocation:_NONE_, label:org_zmail_octopus.HELP_GUIDES_MAC_INSTALL,
							href:(location.pathname + "help/admin/pdf/o_macclient_install.pdf?locid=" + AjxEnv.DEFAULT_LOCALE)},
						{type:_OUTPUT_, value:AjxImg.getImageHtml("PDFDoc")},
						{type:_ANCHOR_, cssStyle:"font-size:12px;", showInNewWindow:true, labelLocation:_NONE_, label:org_zmail_octopus.HELP_GUIDES_WIN_INSTALL,
							href:(location.pathname + "help/admin/pdf/o_windowsclient_install.pdf?locid=" + AjxEnv.DEFAULT_LOCALE)},
                        {type:_OUTPUT_, value:AjxImg.getImageHtml("PDFDoc")},
						{type:_ANCHOR_, cssStyle:"font-size:12px;", showInNewWindow:true, labelLocation:_NONE_, label:org_zmail_octopus.HELP_GUIDES_ANDROID_INSTALL,
							href:(location.pathname + "help/admin/pdf/o_androidclient_install.pdf?locid=" + AjxEnv.DEFAULT_LOCALE)},
                    	{type:_OUTPUT_, value:AjxImg.getImageHtml("PDFDoc")},
						{type:_ANCHOR_, cssStyle:"font-size:12px;", showInNewWindow:true, labelLocation:_NONE_, label:org_zmail_octopus.HELP_GUIDES_IOS_INSTALL,
							href:(location.pathname + "help/admin/pdf/o_iosclient_install.pdf?locid=" + AjxEnv.DEFAULT_LOCALE)}
				 	]
				},
                {type:_CELL_SPACER_},
                {type:_SPACER_, colSpan:"*"},
				{type:_OUTPUT_, cssStyle:"font-size:12px;", label:null, value:ZabMsg.HELP_OTHER_GUIDES_CONNNECTOR_INFO,
				 	cssStyle:"padding-right:10px;padding-left:10px;"},
			    {type:_CELL_SPACER_},
                {type:_SEPARATOR_, colSpan:1, cssClass:"helpSeparator"}  ,
                {type:_CELL_SPACER_}
			];
            var helpItems = xFormObject.items[0].items[0].items ;
			for (var i=0; i< helpItems.length; i++) {
                //insert teh networkHelpItems before the About button
                if (helpItems[i].id == "ZmailHelpPageDownloadItems") {
					helpItems [i].items = helpItems[i].items.concat(octopusNetworkHelpItems) ;
                    break ;
                }
			}
		//}
	}
	ZaTabView.XFormModifiers["ZaHelpView"].push(ZaOctopus.HelpViewXFormModifier);
}

/*
ZaOctopus.addReportsTreeItem = function (tree) {
	//find out if we have any xmbx rights on any servers

	if(!this._monitoringTi) {
		this._monitoringTi = new DwtTreeItem({parent:tree,className:"overviewHeader",id:ZaId.getTreeItemId(ZaId.PANEL_APP,ZaId.PANEL_MONITORING, true)});
		this._monitoringTi.enableSelection(false);	
		this._monitoringTi.setText(ZaMsg.OVP_monitoring);
		this._monitoringTi.setData(ZaOverviewPanelController._TID, ZaZmailAdmin._MONITORING);
	}
	
	this._auditReportTi = new DwtTreeItem({parent:this._monitoringTi,className:"AdminTreeItem"});
	this._auditReportTi.setText(org_zmail_octopus.OVP_AuditReports);
	this._auditReportTi.setImage("CrossMailboxSearch");
	this._auditReportTi.setData(ZaOverviewPanelController._TID, ZaZmailAdmin._AUDIT_REPORT_LIST);	
	
	if(ZaOverviewPanelController.overviewTreeListeners) {
		ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._AUDIT_REPORT_LIST] = ZaOctopus.reportsTreeItemListener;
	}
	
	this._auditReportTemplateTi = new DwtTreeItem({parent:this._monitoringTi,className:"AdminTreeItem"});
	this._auditReportTemplateTi.setText(org_zmail_octopus.OVP_AuditReportTemplates);
	this._auditReportTemplateTi.setImage("CrossMailboxSearch");
	this._auditReportTemplateTi.setData(ZaOverviewPanelController._TID, ZaZmailAdmin._AUDIT_TEMPLATE_LIST);	
	
	if(ZaOverviewPanelController.overviewTreeListeners) {
		ZaOverviewPanelController.overviewTreeListeners[ZaZmailAdmin._AUDIT_TEMPLATE_LIST] = ZaOctopus.templatesTreeItemListener;
	}

	
}

if(ZaOverviewPanelController.treeModifiers)
	ZaOverviewPanelController.treeModifiers.push(ZaOctopus.addReportsTreeItem);

ZaOctopus.reportsTreeItemListener = function (ev) {
	if(ZaApp.getInstance().getCurrentController()) {
		ZaApp.getInstance().getCurrentController().switchToNextView(ZaApp.getInstance().getAuditReportsController(),AuditReportsController.prototype.show, [AuditReport.getAuditReports()]);
	} else {					
		ZaApp.getInstance().getAuditReportsController().show(AuditReport.getAuditReports());
	}
}

ZaOctopus.templatesTreeItemListener = function (ev) {
	if(ZaApp.getInstance().getCurrentController()) {
		ZaApp.getInstance().getCurrentController().switchToNextView(ZaApp.getInstance().getAuditTemplatesListController(),AuditTemplatesListController.prototype.show,[AuditReport.getReportTemplates()]);
	} else {					
		ZaApp.getInstance().getAuditTemplatesListController().show(AuditReport.getReportTemplates());
	}
}

ZaApp.prototype.getAuditReportsController =
function(viewId) {
	if (viewId && this._controllers[viewId] != null) {
		return this._controllers[viewId];
	}else{
		return new AuditReportsController(this._appCtxt, this._container);
	}
}

ZaApp.prototype.getAuditTemplateController =
function(viewId) {
	if (viewId && this._controllers[viewId] != null) {
		return this._controllers[viewId];
	}else{
		return new AuditTemplateController(this._appCtxt, this._container);
	}
}

ZaApp.prototype.getAuditTemplatesListController =
function(viewId) {
	if (viewId && this._controllers[viewId] != null) {
		return this._controllers[viewId];
	}else{
		return new AuditTemplatesListController(this._appCtxt, this._container);
	}
}*/

ZaOctopus.cosInitMethod = function() {
	this[ZaCos.A2_zmailExternalShareLimitLifetime] = this.attrs[ZaCos.A_zmailFileExternalShareLifetime] ? "TRUE" : "FALSE";
	this[ZaCos.A2_zmailInternalShareLimitLifetime] = this.attrs[ZaCos.A_zmailFileShareLifetime] ? "TRUE" : "FALSE";
}

/*
 * Update Help link start
 *
 */

ZaUtil.HELP_URL = "help/admin/html/";
ZaHelpView.mainHelpPage = "admin_home_page.htm";
ZaHelpView.RELEASE_NOTE_LINK = "help/admin/pdf/o_generic_release_notes.pdf";
ZaHelpView.HELP_FORUM_LINK = "https://vmwareoctopus.socialcast.com";

ZaOctopus.octopusInit = function() {
	for(var a in org_zmail_octopus) {
		ZaMsg[a] = org_zmail_octopus[a];
	}
	//TODO: manage available views based on license
	ZaSettings.ALL_UI_COMPONENTS = [];
	//List views
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.ACCOUNT_LIST_VIEW, label: ZaMsg.UI_Comp_AccountListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.DL_LIST_VIEW, label: ZaMsg.UI_Comp_DlListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.COS_LIST_VIEW, label: ZaMsg.UI_Comp_COSListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.DOMAIN_LIST_VIEW, label: ZaMsg.UI_Comp_DomainListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.SERVER_LIST_VIEW, label: ZaMsg.UI_Comp_ServerListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.ZIMLET_LIST_VIEW, label: ZaMsg.UI_Comp_ZimletListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.ADMIN_ZIMLET_LIST_VIEW, label: ZaMsg.UI_Comp_AdminZimletListView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.GLOBAL_CONFIG_VIEW, label: ZaMsg.UI_Comp_globalConfigView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.GLOBAL_STATUS_VIEW, label: ZaMsg.UI_Comp_GlobalStatusView });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.SAVE_SEARCH, label: ZaMsg.UI_Comp_SaveSearch });
	ZaSettings.ALL_UI_COMPONENTS.push({ value: ZaSettings.SERVER_STATS_VIEW, label: ZaMsg.UI_Comp_ServerStatsView });
	if(ZaItem.loadMethods["ZaAccount"]) {
		ZaItem.loadMethods["ZaAccount"].push(ZaOctopus.loadDevices);
	}
	if(ZaItem.initMethods["ZaCos"]) {
		ZaItem.initMethods["ZaCos"].push(ZaOctopus.cosInitMethod);
	}
}
ZaSettings.initMethods.push(ZaOctopus.octopusInit);

ZaSearch.getPredefinedSavedSearches =  function () {
    return [
        {name: ZaMsg.ss_locked_out_accounts, query: "(zmailAccountStatus=*lockout*)"},
        {name: ZaMsg.ss_closed_accounts, query: "(zmailAccountStatus=*closed*)"},
        {name: ZaMsg.ss_maintenance_accounts, query: "(zmailAccountStatus=*maintenance*)"},
        {name: ZaMsg.ss_non_active_accounts, query: "(!(zmailAccountStatus=*active*))" },
        {name: ZaMsg.ss_inactive_accounts_30, query: "(zmailLastLogonTimestamp<=###JSON:{func: ZaSearch.getTimestampByDays, args:[-30]}###)"},
        {name: ZaMsg.ss_inactive_accounts_90, query: "(zmailLastLogonTimestamp<=###JSON:{func: ZaSearch.getTimestampByDays, args:[-90]}###)"},
        {name: org_zmail_octopus.ss_external_virtual_accounts, query: "(zmailIsExternalVirtualAccount=TRUE)" }
    ];
}