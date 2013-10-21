#
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2010, 2011, 2012, 2013 VMware, Inc.
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


from logmsg import *
import commands
import config
import re
import time

class ServerConfig(config.Config):
	def getServices(self, key=None):
		if key is not None:
			if key == "mailboxd":
				key = "mailbox"
			Log.logMsg(5, "Checking service %s in services %s (%s)" % (key, self.serviceconfig, key in self.serviceconfig.keys()))
			return key in self.serviceconfig
		return self.serviceconfig.iterkeys()
                
	def load(self, hostname):
		if (hostname is None):
			raise Exception, "Hostname required"
		self.loaded = True

		t1 = time.clock()
		c = commands.commands["gs"]
		rc = c.execute((hostname,));
		if (rc != 0):
			Log.logMsg(1, "Skipping %s update." % c.desc);
			Log.logMsg(1, str(c));
			return None

		# if no output was returned we have a potential avoid stopping all services
		if (len(c.output) == 0):
			Log.logMsg(2, "Skipping %s - No data returned." % c.desc)
			c.status = 1
			return None

		self.config = dict([(e.getKey(), e.getValue()) for e in sorted(c.output, key=lambda x: x.getKey())])

		if self["zmailSSLExcludeCipherSuites"] is not None:
			v = self["zmailSSLExcludeCipherSuites"]
			v = str(v)
			self["zmailSSLExcludeCipherSuites"] = ' '.join(sorted(v.split(), key=str.lower))
			self["zmailSSLExcludeCipherSuitesXML"] = '\n'.join([''.join(('<Item>',val,'</Item>')) for val in self["zmailSSLExcludeCipherSuites"].split()])

		if self["zmailMtaMyNetworks"] is not None:
			self["zmailMtaMyNetworksPerLine"] = '\n'.join([''.join((val,'')) for val in self["zmailMtaMyNetworks"].split()])

		if self["zmailServiceEnabled"] is not None:
			for v in self["zmailServiceEnabled"].split():
				self.serviceconfig[v] = "zmailServiceEnabled"
				if (v == "mailbox"):
					self.serviceconfig["mailboxd"] = "zmailServiceEnabled"
				elif (v == "mta"):
					self.serviceconfig["sasl"] = "zmailServiceEnabled"

		if self["zmailIPMode"] is not None:
			self["zmailIPv4BindAddress"] = "127.0.0.1"
			v = self["zmailIPMode"]
			v = str(v)
			v = v.lower()
			if v == "ipv4":
				self["zmailLocalBindAddress"] = "127.0.0.1"
				self["zmailPostconfProtocol"] = "ipv4"
				self["zmailAmavisListenSockets"] = "'10024','10026','10032'"
				self["zmailInetMode"] = "inet"
				if self["zmailMilterBindAddress"] is None:
					self["zmailMilterBindAddress"] = "127.0.0.1"
			if v == "ipv6":
				self["zmailLocalBindAddress"] = "::1"
				self["zmailPostconfProtocol"] = "ipv6"
				self["zmailAmavisListenSockets"] = "'[::1]:10024','[::1]:10026','[::1]:10032'"
				self["zmailInetMode"] = "inet6"
				if self["zmailMilterBindAddress"] is None:
					self["zmailMilterBindAddress"] = "[::1]"
			if v == "both":
				self["zmailLocalBindAddress"] = "::1"
				self["zmailPostconfProtocol"] = "all"
				self["zmailAmavisListenSockets"] = "'10024','10026','10032','[::1]:10024','[::1]:10026','[::1]:10032'"
				self["zmailInetMode"] = "inet6"
				if self["zmailMilterBindAddress"] is None:
					self["zmailMilterBindAddress"] = "[::1]"

		milter = None
		if (self["zmailMilterServerEnabled"] == "TRUE"):
			milter = "inet:%s:%s" % (self["zmailMilterBindAddress"],self["zmailMilterBindPort"])

		if self["zmailMtaSmtpdMilters"] is not None and milter is not None:
			self["zmailMtaSmtpdMilters"] = "%s, %s" % (self["zmailMtaSmtpdMilters"], milter)
		elif self["zmailMtaSmtpdMilters"] is None and milter is not None:
			self["zmailMtaSmtpdMilters"] = milter

		dt = time.clock()-t1
		Log.logMsg(5,"Serverconfig loaded in %.2f seconds" % dt)
