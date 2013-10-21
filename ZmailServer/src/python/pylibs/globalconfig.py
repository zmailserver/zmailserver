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

		if self["zmailMtaBlockedExtensionWarnRecipient"] == "TRUE" and self["zmailAmavisQuarantineAccount"] is not None:
			self["zmailQuarantineBannedItems"] = 'TRUE'
		else:
			self["zmailQuarantineBannedItems"] = 'FALSE'

		if self["zmailSSLExcludeCipherSuites"] is not None:
			v = self["zmailSSLExcludeCipherSuites"]
			v = str(v)
			self["zmailSSLExcludeCipherSuites"] = ' '.join(sorted(v.split(), key=str.lower))
			self["zmailSSLExcludeCipherSuitesXML"] = '\n'.join([''.join(('<Item>',val,'</Item>')) for val in self["zmailSSLExcludeCipherSuites"].split()])

		if self["zmailMtaRestriction"] is not None:
			# Remove all the reject_rbl_client lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rbl_client\s+\S+\s+','',self["zmailMtaRestriction"])
			p = re.findall(r'reject_rbl_client\s+(\S+)',self["zmailMtaRestriction"])
			self["zmailMtaRestriction"] = q
			self["zmailMtaRestrictionRBLs"] = ' '.join(p)
			# Remove all the reject_rhsbl_client lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rhsbl_client\s+\S+\s+','',self["zmailMtaRestriction"])
			p = re.findall(r'reject_rhsbl_client\s+(\S+)',self["zmailMtaRestriction"])
			self["zmailMtaRestriction"] = q
			self["zmailMtaRestrictionRHSBLCs"] = ' '.join(p)
			# Remove all the reject_rhsbl_sender lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rhsbl_sender\s+\S+\s+','',self["zmailMtaRestriction"])
			p = re.findall(r'reject_rhsbl_sender\s+(\S+)',self["zmailMtaRestriction"])
			self["zmailMtaRestriction"] = q
			self["zmailMtaRestrictionRHSBLSs"] = ' '.join(p)
			# Remove all the reject_rhsbl_reverse_client lines from MTA restriction and put the values in RBLs
			q = re.sub(r'reject_rhsbl_reverse_client\s+\S+\s+','',self["zmailMtaRestriction"])
			p = re.findall(r'reject_rhsbl_reverse_client\s+(\S+)',self["zmailMtaRestriction"])
			self["zmailMtaRestriction"] = q
			self["zmailMtaRestrictionRHSBLRCs"] = ' '.join(p)

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
			if v == "ipv6":
				self["zmailLocalBindAddress"] = "::1"
				self["zmailPostconfProtocol"] = "ipv6"
				self["zmailAmavisListenSockets"] = "'[::1]:10024','[::1]:10026','[::1]:10032'"
				self["zmailInetMode"] = "inet6"
			if v == "both":
				self["zmailLocalBindAddress"] = "::1"
				self["zmailPostconfProtocol"] = "all"
				self["zmailAmavisListenSockets"] = "'10024','10026','10032','[::1]:10024','[::1]:10026','[::1]:10032'"
				self["zmailInetMode"] = "inet6"

		dt = time.clock()-t1
		Log.logMsg(5,"globalconfig loaded in %.2f seconds" % dt)
