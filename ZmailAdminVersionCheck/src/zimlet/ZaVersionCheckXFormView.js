ZaVersionCheckXFormView = function(parent, entry) {
	ZaTabView.call(this, parent, "ZaVersionCheckXFormView", undefined, "VERSION_EDIT");
	this.TAB_INDEX = 0;	
	this.criticalChoices = new XFormChoices([{value:"1",label:org_zmail_adminversioncheck.Critical},
		{value:"0",label:org_zmail_adminversioncheck.NotCritical}], XFormChoices.OBJECT_LIST, "value", "label");
	this.initForm(ZaVersionCheck.myXModel,this.getMyXForm(entry), null);
}

ZaVersionCheckXFormView.prototype = new ZaTabView();
ZaVersionCheckXFormView.prototype.constructor = ZaVersionCheckXFormView;
ZaTabView.XFormModifiers["ZaVersionCheckXFormView"] = new Array();

ZaVersionCheckXFormView.prototype.getTitle =
function () {
	return org_zmail_adminversioncheck.VersionCheck_view_title;
}

ZaVersionCheckXFormView.prototype.getTabIcon =
function () {
	return "Refresh";
}

ZaVersionCheckXFormView.prototype.getTabTitle =
function () {
	return this.getTitle();
}

ZaVersionCheckXFormView.prototype.getTabToolTip =
function () {
	return this.getTitle ();
}

ZaVersionCheckXFormView.CONFIG_TAB_ATTRS = [];
ZaVersionCheckXFormView.CONFIG_TAB_RIGHTS = [];

ZaVersionCheckXFormView.STATUS_TAB_ATTRS = [];
ZaVersionCheckXFormView.STATUS_TAB_RIGHTS = [];

ZaVersionCheckXFormView.checkLastAttemptFailed = function() {
	return (this.getInstanceValue(ZaVersionCheck.A_zmailVersionCheckLastAttempt) != this.getInstanceValue(ZaVersionCheck.A_zmailVersionCheckLastSuccess));	
}

ZaVersionCheckXFormView.prototype.setObject =
function(entry) {
    this._containedObject = new Object();
	this._containedObject.attrs = new Object();
    for (var a in entry.attrs) {
		var modelItem = this._localXForm.getModel().getItem(a) ;
        if ((modelItem != null && modelItem.type == _LIST_) || (entry.attrs[a] != null && entry.attrs[a] instanceof Array)) {  
        	//need deep clone
            this._containedObject.attrs [a] =
                    ZaItem.deepCloneListItem (entry.attrs[a]);
        } else {
            this._containedObject.attrs[a] = entry.attrs[a];
        }
	}
	this._containedObject.name = entry.name;
	this._containedObject.type = entry.type;

	if(entry.rights)
		this._containedObject.rights = entry.rights;

	if(entry.setAttrs)
		this._containedObject.setAttrs = entry.setAttrs;
	
	if(entry.getAttrs)
		this._containedObject.getAttrs = entry.getAttrs;
		
	if(entry._defaultValues)
		this._containedObject._defaultValues = entry._defaultValues;
	
	if(entry.id)
		this._containedObject.id = entry.id;
	
	if(!entry[ZaModel.currentTab])
		this._containedObject[ZaModel.currentTab] = "1";
	else
		this._containedObject[ZaModel.currentTab] = entry[ZaModel.currentTab];
			
	this._containedObject[ZaVersionCheck.A_zmailVersionCheckUpdates] = [];
	if(entry[ZaVersionCheck.A_zmailVersionCheckUpdates]) {
		this._containedObject[ZaVersionCheck.A_zmailVersionCheckUpdates] = entry[ZaVersionCheck.A_zmailVersionCheckUpdates]; 
	}
	
	this._localXForm.setInstance(this._containedObject);
	//update the tab
    if (!appNewUI)
	    this.updateTab();
}

ZaVersionCheckXFormView.getCustomHeight = function () {
    try {
        var form = this.getForm();
        var totalHeight = parseInt(form.parent.getHtmlElement().style.height);
        var warningHeight = 0;
        var warningPanel = form.getItemsById("xform_warningpanel");
        if (warningPanel && warningPanel[0]) {
            var warningPanelHtml = warningPanel[0].getElement();
            warningHeight =  warningPanelHtml.clientHeight?  warningPanelHtml.clientHeight: warningPanelHtml.offsetHeight;
        }

        var tabbarHeight = 0;
        var tabbarPanel = form.getItemsById("xform_warningpanel");
        if (tabbarPanel && tabbarPanel[0]) {
            var tabbarPanelHtml = tabbarPanel[0].getElement();
            tabbarHeight =  tabbarPanelHtml.clientHeight?  tabbarPanelHtml.clientHeight: tabbarPanelHtml.offsetHeight;
        }

        if (totalHeight > (warningHeight + tabbarHeight)) {
            return  (totalHeight - warningHeight - tabbarHeight);
        } else {
            return "100%";
        }
    } catch (ex) {

    }
    return "100%";
}

ZaVersionCheckXFormView.myXFormModifier = function(xFormObject, entry) {
	xFormObject.tableCssStyle = "width:100%;overflow:auto;";
	var _tab1, _tab2;
	_tab1 = ++this.TAB_INDEX;
	_tab2 = ++this.TAB_INDEX;
    var tabBarChoices = [
    	{value:_tab1, label:org_zmail_adminversioncheck.TABT_ConfigPage},
    	{value:_tab2, label:org_zmail_adminversioncheck.TABT_UpdatesPage}
    ] ;
    var case1 = {type:_ZATABCASE_, caseKey:_tab1,
		colSizes:["auto"],numCols:1,
        getCustomHeight: ZaVersionCheckXFormView.getCustomHeight,
		items:[
			{type:_ZAGROUP_,
				items:[
					{ref:ZaVersionCheck.A_zmailVersionCheckServer, type: _OSELECT1_, 
						label:org_zmail_adminversioncheck.VersionCheckServer,
						editable:false, choices: ZaApp.getInstance().getServerIdListChoices(), 
						enableDisableChecks:[],
						visibilityChecks:[],
						tableCssStyle: "height: 15px",
						required:true
				  	},
					{ type: _DWT_ALERT_,colSpan:"*",
					  containerCssStyle: "padding-bottom:0px",
					  style: DwtAlert.WARNING,
					  iconVisible: false,
					  ref:ZaVersionCheck.A_zmailVersionCheckServer,
					  getDisplayValue:function(val) {
					  	return AjxMessageFormat.format(org_zmail_adminversioncheck.Alert_Crontab, [ZaApp.getInstance().getServerMap()[val]]);
					  },
					  bmolsnr:true
					  //content: org_zmail_adminversioncheck.Alert_Crontab
					},				  	
				  	{ref:ZaVersionCheck.A_zmailVersionCheckInterval,
				  		type:_LIFETIME_, 
				  		label:org_zmail_adminversioncheck.LBL_zmailVersionCheckInterval,labelLocation:_LEFT_
				  	},
					{ref:ZaVersionCheck.A_zmailVersionCheckURL, type:_TEXTFIELD_, 
						label:org_zmail_adminversioncheck.LBL_zmailVersionCheckURL, width:250, required:true
  					},
  					{type:_CHECKBOX_, ref:ZaVersionCheck.A_zmailVersionCheckSendNotifications,
  						label:org_zmail_adminversioncheck.LBL_zmailVersionCheckSendNotifications,
						trueValue:"TRUE",falseValue:"FALSE",
						enableDisableChecks:[],visibilityChecks:[]
					},
					{ type: _DWT_ALERT_,colSpan:"*",
						  containerCssStyle: "padding-bottom:0px",
						  style: DwtAlert.WARNING,
						  iconVisible: false, content:org_zmail_adminversioncheck.NoteSendingNotificationFromAddress
					},
					{ref:ZaVersionCheck.A_zmailVersionCheckNotificationEmail, type:_TEXTFIELD_, 
						label:org_zmail_adminversioncheck.LBL_zmailVersionCheckNotificationEmail, width:250,
						visibilityChecks:[],
						enableDisableChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckSendNotifications],
						enableDisableChecks:[[XForm.checkInstanceValue,ZaVersionCheck.A_zmailVersionCheckSendNotifications,"TRUE"]]						
  					},
  					{ref:ZaVersionCheck.A_zmailVersionCheckNotificationEmailFrom, type:_TEXTFIELD_,
						visibilityChecks:[],
						enableDisableChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckSendNotifications],
						enableDisableChecks:[[XForm.checkInstanceValue,ZaVersionCheck.A_zmailVersionCheckSendNotifications,"TRUE"]],
  						label:org_zmail_adminversioncheck.LBL_zmailVersionCheckNotificationEmailFrom
  					},
					{ref:ZaVersionCheck.A_zmailVersionCheckNotificationSubject, type:_TEXTFIELD_, 
						label:org_zmail_adminversioncheck.LBL_zmailVersionCheckNotificationSubject, width:250,
						visibilityChecks:[],
						enableDisableChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckSendNotifications],
						enableDisableChecks:[[XForm.checkInstanceValue,ZaVersionCheck.A_zmailVersionCheckSendNotifications,"TRUE"]]						
  					},  					
					{type:_TEXTAREA_, ref:ZaVersionCheck.A_zmailVersionCheckNotificationBody,
						label:org_zmail_adminversioncheck.LBL_zmailVersionCheckNotificationBody,
						visibilityChecks:[],
						enableDisableChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckSendNotifications],
						enableDisableChecks:[[XForm.checkInstanceValue,ZaVersionCheck.A_zmailVersionCheckSendNotifications,"TRUE"]]
					}  					
                ]
			}
		]
	};    
    var case2 = {type:_ZATABCASE_, caseKey:_tab2,
        getCustomHeight: ZaVersionCheckXFormView.getCustomHeight,
    	colSizes:["auto"],numCols:1,
    	items:[
			{type:_ZAGROUP_,colSizes:["275","*"],items:[
	    		{ref:ZaVersionCheck.A_zmailVersionCheckLastAttempt,type:_OUTPUT_,
	    			label:org_zmail_adminversioncheck.LBL_zmailVersionCheckLastAttempt,
	    			getDisplayValue:function(val) {
	    				return ZaVersionCheck.getAttemptTime(val);
	    			}
	    		},
	    		{ref:ZaVersionCheck.A_zmailVersionCheckLastSuccess,type:_OUTPUT_,
	    			label:org_zmail_adminversioncheck.LBL_zmailVersionCheckLastSuccess,
	    			getDisplayValue:function(val) {
	    				return ZaVersionCheck.getAttemptTime(val);
	    			}	    			
	    		},
	    		{
	    			ref:ZaVersionCheck.A_zmailVersionCheckUpdates,
	    			type:_REPEAT_,
	    			colSpan:"*",
	    			bmolsnr:true,
					align:_LEFT_,
					repeatInstance:"",
					showAddButton:false,
					showRemoveButton:false,
					showAddOnNextRow:false,
					visibilityChecks:[[XForm.checkInstanceValueNotEmty,ZaVersionCheck.A_zmailVersionCheckUpdates]],
					visibilityChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckUpdates],
					items: [
						{type:_GROUP_,
							numCols:3,
							colSizes:["275","100","300"],				
							items:[
								{ 
									ref:ZaVersionCheck.A_zmailVersionCheckUpdateShortversion, 
									type: _OUTPUT_, label:null,labelLocation:_NONE_, containerCssStyle:"text-align:right"
								},
								{ 
									ref:ZaVersionCheck.A_zmailVersionCheckUpdateCritical, 
									type: _OUTPUT_, label:null,labelLocation:_NONE_,
									choices:this.criticalChoices, containerCssStyle:"text-align:center"
								},
								{ 
									ref:ZaVersionCheck.A_zmailVersionCheckUpdateUpdateURL, 
									type: _URL_, label:null,labelLocation:_NONE_, containerCssStyle:"text-align:center"
								}
							]
						}
					]
	    				
	    		}]
			}
    	]
    }
    var switchItems = [case1,case2];
    xFormObject.items = [
        { type:_GROUP_, id:"xform_warningpanel", colSpan:"*", width: "100%", cols:["auto"], items:[
            { type: _DWT_ALERT_,cssClass: "DwtTabTable",
                visibilityChecks:[ZaVersionCheckXFormView.checkLastAttemptFailed],
                visibilityChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckLastAttempt,ZaVersionCheck.A_zmailVersionCheckLastSuccess],
                containerCssStyle: "padding-bottom:0px",
                style: DwtAlert.CRITICAL,
                iconVisible: true,
                content: org_zmail_adminversioncheck.WARNING_LAST_ATTEMPT_FAILED,
                colSpan:"*"
            },
            { type: _DWT_ALERT_,cssClass: "DwtTabTable",
                visibilityChecks:[[XForm.checkInstanceValueNotEmty,ZaVersionCheck.A_zmailVersionCheckUpdates]],
                visibilityChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckUpdates],
                containerCssStyle: "padding-bottom:0px",
                style: DwtAlert.WARNING,
                iconVisible: true,
                content: org_zmail_adminversioncheck.UpdatesAreAvailable,
                colSpan:"*"
            },
            { type: _DWT_ALERT_,cssClass: "DwtTabTable",
                visibilityChecks:[[XForm.checkInstanceValueEmty,ZaVersionCheck.A_zmailVersionCheckUpdates]],
                visibilityChangeEventSources:[ZaVersionCheck.A_zmailVersionCheckUpdates],
                containerCssStyle: "padding-bottom:0px",
                style: DwtAlert.INFORMATION,
                iconVisible: true,
                content: org_zmail_adminversioncheck.ServerIsUpToDate,
                colSpan:"*"
            }
        ]},
		{type:_TAB_BAR_,  ref:ZaModel.currentTab,id:"xform_tabbar",
		 	containerCssStyle: "padding-top:0px",
			choices: tabBarChoices 
		},
		{type:_SWITCH_, items: switchItems}
	];	
}
ZaTabView.XFormModifiers["ZaVersionCheckXFormView"].push(ZaVersionCheckXFormView.myXFormModifier);
