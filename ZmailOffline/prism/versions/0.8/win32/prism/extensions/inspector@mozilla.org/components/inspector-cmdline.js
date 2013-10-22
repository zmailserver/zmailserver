/* ***** BEGIN LICENSE BLOCK *****
/* Zimbra Collaboration Suite Server
/* Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
/* 
/* The contents of this file are subject to the Zimbra Public License
/* Version 1.3 ("License"); you may not use this file except in
/* compliance with the License.  You may obtain a copy of the License at
/* http://www.zimbra.com/license.
/* 
/* Software distributed under the License is distributed on an "AS IS"
/* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK ***** */

// NOTE: this file implements both the seamonkey nsICmdLineHandler and
// the toolkit nsICommandLineHandler, using runtime detection.

const INSPECTOR_CMDLINE_CONTRACTID = "@mozilla.org/commandlinehandler/general-startup;1?type=inspector";
const INSPECTOR_CMDLINE_CLSID      = Components.ID('{38293526-6b13-4d4f-a075-71939435b408}');
const CATMAN_CONTRACTID            = "@mozilla.org/categorymanager;1";
const nsISupports                  = Components.interfaces.nsISupports;

const nsICategoryManager           = Components.interfaces.nsICategoryManager;
const nsICmdLineHandler            = Components.interfaces.nsICmdLineHandler;
const nsICommandLine               = Components.interfaces.nsICommandLine;
const nsICommandLineHandler        = Components.interfaces.nsICommandLineHandler;
const nsIComponentRegistrar        = Components.interfaces.nsIComponentRegistrar;
const nsISupportsString            = Components.interfaces.nsISupportsString;
const nsIWindowWatcher             = Components.interfaces.nsIWindowWatcher;

function InspectorCmdLineHandler() {}
InspectorCmdLineHandler.prototype =
{
  /* nsISupports */
  QueryInterface : function handler_QI(iid) {
    if (iid.equals(nsISupports))
      return this;

    if (nsICmdLineHandler && iid.equals(nsICmdLineHandler))
      return this;

    if (nsICommandLineHandler && iid.equals(nsICommandLineHandler))
      return this;

    throw Components.results.NS_ERROR_NO_INTERFACE;
  },

  /* nsICmdLineHandler */
  commandLineArgument : "-inspector",
  prefNameForStartup : "general.startup.inspector",
  chromeUrlForTask : "chrome://inspector/content/inspector.xul",
  helpText : "Start with the DOM Inspector.",
  handlesArgs : true,
  defaultArgs : "",
  openWindowWithArgs : true,

  /* nsICommandLineHandler */
  handle : function handler_handle(cmdLine) {
    var args = Components.classes["@mozilla.org/supports-string;1"]
                         .createInstance(nsISupportsString);
    try {
      var uristr = cmdLine.handleFlagWithParam("inspector", false);
      if (uristr == null)
        return;
      try {
        args.data = cmdLine.resolveURI(uristr).spec;
      }
      catch (e) {
        return;
      }
    }
    catch (e) {
      cmdLine.handleFlag("inspector", true);
    }

    var wwatch = Components.classes["@mozilla.org/embedcomp/window-watcher;1"]
                           .getService(nsIWindowWatcher);
    wwatch.openWindow(null, "chrome://inspector/content/", "_blank",
                      "chrome,dialog=no,all", args);
    cmdLine.preventDefault = true;
  },

  helpInfo : "  -inspector <url>     Open the DOM inspector.\n"
};


var InspectorCmdLineFactory =
{
  createInstance : function(outer, iid)
  {
    if (outer != null) {
      throw Components.results.NS_ERROR_NO_AGGREGATION;
    }

    return new InspectorCmdLineHandler().QueryInterface(iid);
  }
};


var InspectorCmdLineModule =
{
  registerSelf : function(compMgr, fileSpec, location, type)
  {
    compMgr = compMgr.QueryInterface(nsIComponentRegistrar);

    compMgr.registerFactoryLocation(INSPECTOR_CMDLINE_CLSID,
                                    "DOM Inspector CommandLine Service",
                                    INSPECTOR_CMDLINE_CONTRACTID,
                                    fileSpec,
                                    location,
                                    type);

    var catman = Components.classes[CATMAN_CONTRACTID].getService(nsICategoryManager);
    catman.addCategoryEntry("command-line-argument-handlers",
                            "inspector command line handler",
                            INSPECTOR_CMDLINE_CONTRACTID, true, true);
    catman.addCategoryEntry("command-line-handler",
                            "m-inspector",
                            INSPECTOR_CMDLINE_CONTRACTID, true, true);
  },

  unregisterSelf : function(compMgr, fileSpec, location)
  {
    compMgr = compMgr.QueryInterface(nsIComponentRegistrar);

    compMgr.unregisterFactoryLocation(INSPECTOR_CMDLINE_CLSID, fileSpec);
    catman = Components.classes[CATMAN_CONTRACTID].getService(nsICategoryManager);
    catman.deleteCategoryEntry("command-line-argument-handlers",
                               "inspector command line handler", true);
    catman.deleteCategoryEntry("command-line-handler",
                               "m-inspector", true);
  },

  getClassObject : function(compMgr, cid, iid)
  {
    if (cid.equals(INSPECTOR_CMDLINE_CLSID)) {
      return InspectorCmdLineFactory;
    }

    if (!iid.equals(Components.interfaces.nsIFactory)) {
      throw Components.results.NS_ERROR_NOT_IMPLEMENTED;
    }

    throw Components.results.NS_ERROR_NO_INTERFACE;
  },

  canUnload : function(compMgr)
  {
    return true;
  }
};


function NSGetModule(compMgr, fileSpec) {
  return InspectorCmdLineModule;
}
