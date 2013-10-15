function Com_Zmail_Preview () {

}

Com_Zmail_Preview.prototype = new ZmZimletBase();
Com_Zmail_Preview.prototype.constructor = Com_Zmail_Preview;

Com_Zmail_Preview.prototype.init = function () {
    appCtxt.set(ZmSetting.PREVIEW_ENABLED, true);
};