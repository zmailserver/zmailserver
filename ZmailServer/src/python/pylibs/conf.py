#
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2010, 2011, 2012 VMware, Inc.
# 
# The contents of this file are subject to the Zimbra Public License
# Version 1.3 ("License"); you may not use this file except in
# compliance with the License.  You may obtain a copy of the License at
# http://www.zimbra.com/license.
# 
# Software distributed under the License is distributed on an "AS IS"
# basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
# ***** END LICENSE BLOCK *****
#


import os

class Config:
	def __init__(self):
		self.config = {}
		self.progname 	= "zmconfigd"
		if (os.getenv("zimbra_server_hostname") is not None):
			self.hostname 	= os.getenv("zimbra_server_hostname")
		else:
			self.hostname 	= os.popen("/opt/zimbra/bin/zmhostname").readline().strip()
		if (self.hostname is None or self.hostname == ""):
			os._exit(1)
		self.wd_all 	= False
		self.debug 		= False
		self.baseDir	= "/opt/zimbra"
		self.logStatus 	= { 
						4 : "Debug", 
						3 : "Info", 
						2 : "Warning", 
						1 : "Error", 
						0 : "Fatal"
						}
		self.configFile = self.baseDir+"/conf/zmconfigd.cf";
		self.logFile    = self.baseDir+"/log/"+self.progname+".log";
		self.pidFile    = self.baseDir+"/log/"+self.progname+".pid";
		self.interval 	= 60
		if self.debug:
			self.interval  = 10
		self.restartconfig = False
		self.watchdog 	= True
		self.wd_list	= [ "antivirus" ]
		self.loglevel 	= 3

	def __setitem__(self, key, val):
		self.config[key] = val

	def __getitem__(self, key):
		try:
			return self.config[key]
		except Exception, e:
			return None

	def setVals(self, state):
		self.ldap_is_master = state.localconfig["ldap_is_master"]
		self.ldap_root_password = state.localconfig["ldap_root_password"]
		self.ldap_master_url = state.localconfig["ldap_master_url"]
		self.loglevel 	= 3
		if state.localconfig["ldap_starttls_required"] is not None:
			self.ldap_starttls_required = (state.localconfig["ldap_starttls_required"].upper() != "FALSE")
		if state.localconfig["zmconfigd_log_level"] is not None:
			self.loglevel 	= int(state.localconfig["zmconfigd_log_level"])
		self.interval 	= 60
		if state.localconfig["zmconfigd_interval"] is not None and state.localconfig["zmconfigd_interval"] != "":
			self.interval 	= int(state.localconfig["zmconfigd_interval"])
		self.debug 		= False
		if state.localconfig["zmconfigd_debug"] is not None:
			self.debug 		= state.localconfig["zmconfigd_debug"]
		if state.localconfig["zmconfigd_watchdog"] is not None:
			self.watchdog	= (state.localconfig["zmconfigd_watchdog"].upper() != "FALSE")
		if state.localconfig["zmconfigd_enable_config_restarts"] is not None:
			self.restartconfig = (state.localconfig["zmconfigd_enable_config_restarts"].upper() != "FALSE")
		if state.localconfig["zmconfigd_watchdog_services"] is not None:
			self.wd_list = state.localconfig["zmconfigd_watchdog_services"].split()
