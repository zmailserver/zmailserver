/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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

/*!
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is BrowserPlus (tm).
 *
 * The Initial Developer of the Original Code is Yahoo!.
 * Portions created by Yahoo! are Copyright (c) 2009 Yahoo! Inc.
 * All rights reserved.
 *
 * Contributor(s):
 */

/**
 * browserplus.js
 *
 * Provides a gateway between user JavaScript and the BrowserPlus platform
 * 
 * @private
 */

// Note: Members beginning with '_' are not intended for client use.

// handle multiple inclusions of this file without resetting state.

BrowserPlus = (typeof BrowserPlus != "undefined" && BrowserPlus) ? BrowserPlus : (function() {

    // private variables
    var _pluginLoaded = false;
    var _bpPluginID = "__browserPlusPluginID";
    // a bit of state to gracefully hanlde partial initialization
    // _initState may be "uninitialized", "inprogress", or "succeeded"
    var _initState = "uninitialized";
    var _initCallbacks = [];
    var _mimeType = "application/x-yahoo-browserplus_2";
    var _browser, _version, _OS, _locale, _supportLevel;

    return {
        initWhenAvailable: function(initArgs, callback) {
          setTimeout(function() {
try { navigator.plugins.refresh(false); } catch(e) { }
BrowserPlus.init(initArgs, function(r) {
if (r.success) {
callback(r);
} else {
BrowserPlus.initWhenAvailable(initArgs, callback);
}
});
}, 1000);
        },

        clientSystemInfo: function() {
            return _detectBrowser();
        },

        listActiveServices: function (cb) {
            if (cb == null || cb.constructor != Function) {
                throw new Error("BrowserPlus.services() invoked without " +
                                " required callback parameter.");
            }
            return _corePlugin().EnumerateServices(cb);
        },

        getPlatformInfo: function() {
            if (_corePlugin() === null) {
                throw new Error("BrowserPlus.getPlatformInfo() invoked, " +
                                "but init() has not completed successfully.");
            }
            return _corePlugin().Info();
        },

        isServiceLoaded: function(name, version) {
            return ((name != undefined && BrowserPlus.hasOwnProperty(name)) &&
                    (version == undefined || BrowserPlus[name].hasOwnProperty(version)));
        },

        describeService: function(args, cb) {
            if (cb == null || cb.constructor != Function) {
                throw new Error("BrowserPlus.services() invoked without " +
                                " required callback parameter");
            }
            if (_corePlugin() === null) {
                throw new Error("BrowserPlus.describeService() invoked, " +
                                "but init() has not completed successfully.");
            }
            return _corePlugin().DescribeService(args, cb);
        },

        isServiceActivated: function(args, cb) {
            return _corePlugin().DescribeService(
                args,
                (function() {
                    var _cb = cb;
                    return function(res) {
                        _cb(res.success);
                    }
                })());
        },

        isInitialized: function() {
            return (_initState === 'succeeded');
        },

        /**
         * require attains a description of corelets from BPCore via
         * the browser plugin and adds functions appropriate to those
         * corelets to the corresponding BrowserPlus.CORELET_NAME object.
         * the return value is the full description of the coreles.
         */
        require: function(args, callback)
        {
            if (callback == null || callback.constructor != Function) {
                throw new Error("BrowserPlus.require() invoked without required " +
                                "callback parameter");
            }

            var proxyCallback = function(details) {
                // return the error code if there is one,
// otherwise dynamically build callable interfaces for the
// required services
                if (details.success) {
          var rv = [];
                    for (var k = 0; k < details.value.length; k++) {
                        // new style (post 2.0.6) require return value
                        if (details.value[k].fullDesc) {
                            _createAPI({value: details.value[k].fullDesc});
                            rv.push({service: details.value[k].service,
                                     version: details.value[k].version});
                        }
                        // obsolete (2.0.6 and before) require return value
else {
                            BrowserPlus.describeService(
                                {service: details.value[k].service,
                                 version: details.value[k].version},
                                _createAPI);
                            rv = details.value;
}
                    }
                    details.value = rv;
                }
                callback(details);
            };

            _corePlugin().RequireService(args, proxyCallback);

            return true;
        },

        /**
         * init is called by user js and is responsible for loading
         * the BrowserPlus plugin. args is an optional arguments map,
         * cb is required callback. Currently known arguments in "args"
         * are locale: <locale-string>, e.g. {locale: "en_GB"}
         */
        init: function(args, cb) {
            // refresh npapi plugins. makes sure that we work if
            // platform update has occurred since browser started
            if (_detectBrowser().browser == "Safari") {
                navigator.plugins.refresh(false);
            }

            var lArgs = null;
            var lCb = null;
            if (cb == null) {
                // only one arg in 'args', it must be callback
                lCb = args;
            } else {
                lArgs = args;
                lCb = cb;
            }
            if (lCb == null || lCb.constructor != Function) {
                throw new Error("BrowserPlus.init() invoked without " +
                                " required callback parameter");
            }

            // if caller didn't specify locale, use browser's
            if (lArgs === null) {
                lArgs = new Object;
            }
            if (typeof(lArgs.locale) == 'undefined') {
                lArgs.locale = _detectBrowser().locale;
            }

            // now check if client technology is supported to desired
            // support level
            var tryLoad = true;
            if (!lArgs.supportLevel || lArgs.supportLevel === "experimental"){
                tryLoad = (_detectBrowser().supportLevel !== "unsupported");
            } else if (lArgs.supportLevel === "supported") {
                tryLoad = (_detectBrowser().supportLevel === "supported");
            }
            if (!tryLoad) {
                lCb({success: false, error: "bp.unsupportedClient"});
                return;
            }

            if (_initState === 'succeeded') {
                lCb({success: true});
                return;
            }

            if (_initState == 'inprogress') {
                _initCallbacks.push(lCb);
                return;
            }
            _initState = 'inprogress';

            // make sure that an unload handler is installed to prevent caching
            // http://developer.mozilla.org/en/docs/Using_Firefox_1.5_caching
            if (typeof(window.onunload) == 'undefined') {
                window.onunload = function() {};
            }

            var success = false;

            if (_setupPlugin() && _corePlugin() !== null) {
                success = true;
            }

            if (!success) {
                _initState = 'uninitialized';
                lCb({ success: false, error: "bp.notInstalled" });
                return;
            } else {
                _initCallbacks.push(lCb);

                // Must let browser regain control for plugin to be
                // fully ready, then we can call Initialize().
                // Especially true on Firefox3, which will destroy and
                // reinstantiate the plugin during reflow *after*
                // we've called Initialize(). Invoking _doInit from a
                // timeout prevents this behavior.

                var _doInit = (function() {
                    var _args = lArgs;
                    return function() {
                        var proxyInitCallback = function(res) {
                            // if we recieve a bp.switchVersion error
                            // code, then there's a newer plugin available
                            // that the browser doesn't yet know about.
                            // .verboseError contains the specific version
                            // of the new platform
                            if (!res.success &&
                                res.error === 'bp.switchVersion' &&
                                _detectBrowser().browser !== "Explorer")
                            {
                                // construct a new mimetype
                                var newMT = "application/x-yahoo-browserplus_";
                                newMT += res.verboseError;
                                if (newMT !== _mimeType) {
                                    _mimeType = newMT;
                                    setTimeout(function() {
                                        try {
                                            var pi = document.getElementById( _bpPluginID );
                                            if (pi) document.documentElement.removeChild(pi.parentNode);
                                            navigator.plugins.refresh(false);
                                        } catch(ignoreErr) { }

                                        if (_setupPlugin() && _corePlugin() !== null)
                                        {
                                            setTimeout(function() { _corePlugin().Initialize(_args, proxyInitCallback); }, 10);
                                        } else {
                                            proxyInitCallback(res);
                                        }
                                    }, 10);
                                    return;
                                }
                            }

                            // finally, bp.switchVersion should never make it up to client
                            // javascript. we'll fold it into 'notInstalled'
                            if (!res.success && res.error === 'bp.switchVersion')
                            {
                                res = {
                                  success: false,
                                  error: 'bp.notInstalled',
                                  verboseError: 'BrowserPlus isn\'t installed, or couldn\'t be loaded'
                                };
                            }

                            _initState = res.success ? 'succeeded' : 'uninitialized';
                            // clear callbacks
                            var cbs = _initCallbacks;
                            _initCallbacks = [];
                            // call all callbacks
                            for (var i = 0; i < cbs.length; i++) cbs[i](res);
                        };
                        try {
                            _corePlugin().Initialize(_args, proxyInitCallback);
                        } catch(e) {
                            // we'll get here when initialize fails. this
                            // means different things on different platforms
                            var bi = _detectBrowser();
                            var eMap = {
                                success: false,
                                error: "bp.notInstalled",
                                verboseError: String(e)
                            };

                            if (bi.browser == 'Explorer') {
                                eMap.error = 'bp.unsupportedClient';
                            } else if (bi.browser == 'Firefox') {
                                // this case will occur when you uninstall on
                                // firefox and visit a page that uses browserplus
                                // without a restart (YIB-1958106). This case
                                // is a "not installed" case, notice the "true" here!!
                                try { navigator.plugins.refresh(true); } catch(e2) { }
                            }
                            proxyInitCallback(eMap);
                        }
                    };
                })();

                // give control back to browser
                setTimeout(_doInit, 0);
            }
        },

        // DEPRECATED: will be removed in a future version of the
        // platform. use BrowserPlus.clientSystemInfo()
        // instead.
        _detectBrowser: function() {
            return BrowserPlus.clientSystemInfo();
        }
    };

    // Embed an instance of the bp plugin in the current document.
    function _setupPlugin () {
        if (_isPluginActiveX()) {
            return _setupPluginActiveX();
        } else {
            return _setupPluginNPAPI();
        }
    };

    function _setupPluginNPAPI() {
        // check for presence of browserplus plugin and embed it via
        // an innerHTML property in a div. This causes the plugin to
        // load synchronously.

var mt = navigator.mimeTypes[_mimeType];
if (typeof(mt) !== "object" || typeof(mt.enabledPlugin) !== "object")
        {
return false;
}

        var div = document.createElement("div");
        div.style.visibility = "hidden";
        div.style.borderStyle = "hidden";
        div.style.width = 0;
        div.style.height = 0;
        div.style.border = 0;
        div.style.position = "absolute";
        div.style.top = 0;
        div.style.left = 0;
        div.innerHTML = '<object type="' + _mimeType + '" ' +
                        'id="' + _bpPluginID + '" ' +
                        'name="' + _bpPluginID + '">' +
                        '</object>';
        document.documentElement.appendChild(div);

        _pluginLoaded = true;
        return true;
    };

    // Returns true if plugin already exists or is created successfully.
    function _setupPluginActiveX() {
        // If plugin already embedded we're done.
        if (_corePlugin() != null) {
            return true;
        }

try {
// Create control.
var elem = document.createElement("object");
elem.id = _bpPluginID;
elem.type = _mimeType;

// If this addon disabled by the user, this statement throws.
elem.style.display = 'none';

// add the 'object' tag to the document
document.body.appendChild(elem);

// Verify that the activex control is really working:
// If the control is uncreatable this will throw.
document.getElementById( _bpPluginID ).Ping();
_pluginLoaded = true;
return true;
}
catch(e) {
// Protect the next operation - we have had issues with
// this intermittently throwing InvalidParameter.
try {
// Remove the dom element, it is just a shell not
// connected to the plugin. Keeping this element
// around has interfered with some retry scenarios.
document.body.removeChild(elem);
}
catch(e2) {
}
}

        return false;
    };

    function _corePlugin() {
        if (_pluginLoaded) {
            return document.getElementById( _bpPluginID );
        }
return null;
    };

    function _isPluginActiveX() {
        return (_detectBrowser().browser == "Explorer");
    };

    // given a service description, create a javascript API
    function _createAPI(details) {
        // now we walk the details and populate the BrowserPlus
        // object with callable functions!
        details = details.value;
        var corelet = details.name;
        var version = details.versionString;

        if (!BrowserPlus[corelet]) {
            BrowserPlus[corelet] = {};
            BrowserPlus[corelet].corelet = corelet;
            BrowserPlus[corelet].version = version;
        }
        if (!BrowserPlus[corelet][version]) {
            BrowserPlus[corelet][version] = {};
            BrowserPlus[corelet][version].corelet = corelet;
            BrowserPlus[corelet][version].version = version;
        }
        // for the core corelet we now update the version string
        if (corelet == "core") {
            BrowserPlus[corelet].version = version;
        }

        if (details.functions) {
            for (var i = 0; i < details.functions.length; i++) {
                var funcName = details.functions[i].name;
                var afunc =
                    (function() {
                        var permFuncName = funcName;
                        return function(args, cb) {
                            if (cb == null || cb.constructor != Function)
                            {
                                throw new Error("BrowserPlus." +
                                                corelet + "." + permFuncName +
                                                "() invoked without " +
                                                " required callback " +
                                                "parameter");
                            }
                            return _corePlugin().ExecuteMethod(
                                corelet, version,
                                permFuncName, args, cb);
                        };
                    })();

                BrowserPlus[corelet][version][funcName] = afunc;

                // now if this is the first version of
                // this corelet loaded, we'll plunk a
                // "symlink" in the versionless path for
                // convenience

                if (version == BrowserPlus[corelet].version) {
                    BrowserPlus[corelet][funcName] = afunc;
                }
            }
        }
    };

    // Browser Detection
    //
    // code adapted from: http://www.quirksmode.org/js/detect.html
    // thanks!
    function _detectBrowser() {
        var dataBrowser = [
            {
                string: navigator.vendor,
                subString: "Apple",
                identity: "Safari"
            },
            {
                string: navigator.vendor,
                subString: "Google",
                identity: "Chrome"
            },
            {
                prop: window.opera,
                identity: "Opera"
            },
            {
                string: navigator.userAgent,
                subString: "Firefox",
                identity: "Firefox"
            },
            {
                string: navigator.userAgent,
                subString: "MSIE",
                identity: "Explorer",
                versionSearch: "MSIE"
            }
        ];
        var dataOS = [
            {
                string: navigator.platform,
                subString: "Win",
                identity: "Windows"
            },
            {
                string: navigator.platform,
                subString: "Mac",
                identity: "Mac"
            },
            {
                string: navigator.platform,
                subString: "Linux",
                identity: "Linux"
            }
        ];

        var supportMatrix = {
            Mac: {
                Firefox: "supported",
                Safari: "supported"
            },
            Windows: {
                Explorer: {
                  "8": "supported",
                  "7": "supported",
                  "6": "experimental"
                },
                Safari: "supported",
                Firefox: "supported",
                Chrome: "supported"
            }
        };

        var versionSearchString;
        function searchString(data) {
            for (var i=0;i<data.length;i++){
                var dataString = data[i].string;
                var dataProp = data[i].prop;
                versionSearchString = data[i].versionSearch || data[i].identity;
                if (dataString) {
                    if (dataString.indexOf(data[i].subString) != -1) {
                        return data[i].identity;
                    }
                } else if (dataProp) {
                    return data[i].identity;
                }
            }
            return null;
        }

        function searchVersion(dataString) {
            var index = dataString.indexOf(versionSearchString);
            if (index == -1) {
                return null;
            } else {
                return parseFloat(dataString.substring(index+versionSearchString.length+1));
            }
        }

        function supportLevel() {
            if (supportMatrix[_OS] && supportMatrix[_OS][_browser]) {
                var e = supportMatrix[_OS][_browser];
                if (typeof e === 'string') {
                  return e;
                } else if (e[_version]) {
                  return e[_version];
                } else {
                  return "unsupported";
                }
            } else {
                return "unsupported";
            }
        }

        if (!_browser) {
            _browser = searchString(dataBrowser) || "An unknown browser";
            _version = searchVersion(navigator.userAgent) || searchVersion(navigator.appVersion) || "an unknown version";
            _OS = searchString(dataOS) || "an unknown OS";
            // catch ppc/iphone/others
            if (_OS === "Mac" && navigator.userAgent.indexOf("Intel") == -1) {
                _supportLevel = "unsupported";
            }
            // manually catch FFX 2 - not supported
            else if (navigator.userAgent.indexOf("Firefox/2.0.0") != -1)
            {
                _supportLevel = "unsupported";
            }
            else
            {
                _supportLevel = supportLevel();
            }
            _locale = navigator.languaextrage || navigator.browserLanguage ||
                      navigator.userLanguage || navigator.systemLanguage;
        }

        return {
            browser: _browser,
            version: _version,
            os: _OS,
            locale: _locale,
            supportLevel: _supportLevel
        };
    };
})();

if (typeof YAHOO == "undefined" || !YAHOO) {
    var YAHOO = {};
}

// an alias for backwards compatibility
if (typeof YAHOO["bp"] == "undefined" || !YAHOO["bp"]) {
    YAHOO.bp = BrowserPlus;
}