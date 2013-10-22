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

class GlobalConfig(config.Config):
	def load(self):
		self.loaded = True

		t1 = time.clock()
		c = commands.commands["gacf"]
		rc = c.execute();
		if (rc != 0):
			Log.logMsg(1, "Skipping "+c.desc+" update.");
			Log.logMsg(1, str(c));
			return None

		# if no output was returned we have a potential avoid stopping all services
		if (len(c.output) == 0):
			Log.logMsg(2, "Skipping " + c.desc + " No data returned.")
			c.status = 1
			return None

		self.config = dict([(e.getKey(), e.getValue()) for e in sorted(c.output, key=lambda x: x.getKey())])

		if self["zimbraMtaBlockedExtensionWarnRecipient"] == "TRUE" and self["zimbraAmavisQuarantineAccount"] is not None:
			self["zimbraQuarantineBannedItems"] = 'TRUE'
		else:
			self["zimbraQuarantineBannedItems"] = 'FALSE'

		if self["zimbraSSLExcludeCipherSuites"] is not None:
			v = self["zimbraSSLExcludeCipherSuites"]
			v = str(v)
			self["zimbraSSLExcludeCipherSuites"] = ' '.join(sorted(v.split(), key=str.lower))
			self["zimbraSSLExcludeCipherSuitesXML"] = '\n'.join([''.join(('<Item>',val,'</Item>')) for val in self["zimbraSSLExcludeCipherSuites"].split()])

		if self["zimbraMtaRestriction"] is not None:
			# Remove all the reject_rbl_client lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rbl_client\s+\S+\s+','',self["zimbraMtaRestriction"])
			p = re.findall(r'reject_rbl_client\s+(\S+)',self["zimbraMtaRestriction"])
			self["zimbraMtaRestriction"] = q
			self["zimbraMtaRestrictionRBLs"] = ' '.join(p)
			# Remove all the reject_rhsbl_client lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rhsbl_client\s+\S+\s+','',self["zimbraMtaRestriction"])
			p = re.findall(r'reject_rhsbl_client\s+(\S+)',self["zimbraMtaRestriction"])
			self["zimbraMtaRestriction"] = q
			self["zimbraMtaRestrictionRHSBLCs"] = ' '.join(p)
			# Remove all the reject_rhsbl_sender lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rhsbl_sender\s+\S+\s+','',self["zimbraMtaRestriction"])
			p = re.findall(r'reject_rhsbl_sender\s+(\S+)',self["zimbraMtaRestriction"])
			self["zimbraMtaRestriction"] = q
			self["zimbraMtaRestrictionRHSBLSs"] = ' '.join(p)
			# Remove all the reject_rhsbl_reverse_client lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rhsbl_reverse_client\s+\S+\s+','',self["zimbraMtaRestriction"])
			p = re.findall(r'reject_rhsbl_reverse_client\s+(\S+)',self["zimbraMtaRestriction"])
			self["zimbraMtaRestriction"] = q
			self["zimbraMtaRestrictionRHSBLRCs"] = ' '.join(p)

		if self["zimbraIPMode"] is not None:
			self["zimbraIPv4BindAddress"] = "127.0.0.1"
			v = self["zimbraIPMode"]
			v = str(v)
			v = v.lower()
			if v == "ipv4":
				self["zimbraLocalBindAddress"] = "127.0.0.1"
				self["zimbraPostconfProtocol"] = "ipv4"
				self["zimbraAmavisListenSockets"] = "'10024','10026','10032'"
				self["zimbraInetMode"] = "inet"
			if v == "ipv6":
				self["zimbraLocalBindAddress"] = "::1"
				self["zimbraPostconfProtocol"] = "ipv6"
				self["zimbraAmavisListenSockets"] = "'[::1]:10024','[::1]:10026','[::1]:10032'"
				self["zimbraInetMode"] = "inet6"
			if v == "both":
				self["zimbraLocalBindAddress"] = "::1"
				self["zimbraPostconfProtocol"] = "all"
				self["zimbraAmavisListenSockets"] = "'10024','10026','10032','[::1]:10024','[::1]:10026','[::1]:10032'"
				self["zimbraInetMode"] = "inet6"

		dt = time.clock()-t1
		Log.logMsg(5,"globalconfig loaded in %.2f seconds" % dt)
