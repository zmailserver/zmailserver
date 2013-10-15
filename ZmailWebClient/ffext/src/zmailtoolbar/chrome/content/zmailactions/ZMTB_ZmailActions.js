var ZMTB_ZmailActions = function(zmtb)
{
	this.super(zmtb)
	var This = this;
	document.getElementById("ZimTB-OpenAdvanced").addEventListener("click",function(event){
		This._rqManager.goToPath("");
	},false);
	document.getElementById("ZimTB-OpenStandard").addEventListener("click",function(event){
		This._rqManager.goToPath("zmail/h/");
	},false);
	document.getElementById("ZimTB-OpenPreferences").addEventListener("click",function(event){
		This.openPrefsCommand();
	},false);
	document.getElementById("ZimTB-Refresh-Button").addEventListener("command", function(){This._zmtb.update()}, false);
}

ZMTB_ZmailActions.prototype = new ZMTB_Actions();
ZMTB_ZmailActions.prototype.constructor = ZMTB_ZmailActions;
ZMTB_ZmailActions.prototype.super = ZMTB_Actions;

ZMTB_ZmailActions.prototype.enable = function()
{
	document.getElementById("ZimTB-OpenAdvanced").disabled = false;
	document.getElementById("ZimTB-OpenStandard").disabled = false;
}

ZMTB_ZmailActions.prototype.disable = function()
{
	document.getElementById("ZimTB-OpenAdvanced").disabled = true;
	document.getElementById("ZimTB-OpenStandard").disabled = true;
}

ZMTB_ZmailActions.prototype.openPrefsCommand = function(name, parentId, query)
{
	var wm = Components.classes["@mozilla.org/appshell/window-mediator;1"].getService(Components.interfaces.nsIWindowMediator);
	var enumerator = wm.getEnumerator("");
	while(enumerator.hasMoreElements())
	{
		var win = enumerator.getNext();
		if(win.location == "chrome://zmailtb/content/preferences/preferences.xul")
		{
			win.focus();
			return;
		}
	}
	window.openDialog("chrome://zmailtb/content/preferences/preferences.xul", "preferences", "centerscreen, chrome, titlebar");
}