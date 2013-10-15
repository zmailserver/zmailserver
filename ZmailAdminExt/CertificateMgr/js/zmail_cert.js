if(window.console && window.console.log) console.log("Loaded zmail_cert.js");
if(ZaSettings && ZaSettings.EnabledZimlet["org_zmail_cert_manager"]){
if (ZaOperation) ZaOperation.INSTALLCERT = ++ZA_OP_INDEX;
ZaItem.CERT = "cert" ;

if(ZaOverviewPanelController.treeModifiers)
	ZaOverviewPanelController.treeModifiers.push(ZaCert.certOvTreeModifier);

ZaZmailAdmin._CERTS = ZaZmailAdmin.VIEW_INDEX++;
ZaZmailAdmin._CERTS_SERVER_LIST_VIEW = ZaZmailAdmin.VIEW_INDEX++;

ZaApp.prototype.getCertViewController =
function(viewId) {
	if (viewId && this._controllers[viewId] != null) {
		return this._controllers[viewId];
	}else{
		var c = this._controllers[viewId] = new ZaCertViewController(this._appCtxt, this._container, this);
		return c ;
	}
}

ZaApp.prototype.getCertsServerListController =
function() {
	if (this._controllers[ZaZmailAdmin._CERTS_SERVER_LIST_VIEW] == null) {
		this._controllers[ZaZmailAdmin._CERTS_SERVER_LIST_VIEW] = new ZaCertsServerListController(this._appCtxt, this._container, this);
	}
	return this._controllers[ZaZmailAdmin._CERTS_SERVER_LIST_VIEW];
}
}