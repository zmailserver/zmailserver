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


from logmsg import *
import commands
import config
import re
import time

class LocalConfig(config.Config):
	def load(self):
		self.loaded = True

		t1 = time.clock()
		c = commands.commands["localconfig"]
		rc = c.execute();
		if (rc != 0):
			Log.logMsg(1, "Skipping "+c.desc+" update.");
			Log.logMsg(1, str(c));
			return None
		dt = time.clock()-t1
		Log.logMsg(5,"Localconfig fetched in %.2f seconds (%d entries)" % (dt,len(c.output)))

		if (len(c.output) == 0):
			Log.logMsg(2, "Skipping " + c.desc + " No data returned.")
			c.status = 1
			raise Exception, "Skipping " + c.desc + " No data returned."

		self.config = dict([(k,v) for (k,v) in c.output])

		# Set a default for this
		if self["zmconfigd_listen_port"] is None:
			self["zmconfigd_listen_port"] = "7171"

		if self["ldap_url"] is not None:
			v = self["ldap_url"]
			v = str(v)
			self["opendkim_signingtable_uri"] = ' '.join([''.join((val,'/?DKIMSelector?sub?(DKIMIdentity=$d)')) for val in self["ldap_url"].split()])
			self["opendkim_keytable_uri"] = ' '.join([''.join((val,'/?DKIMDomain,DKIMSelector,DKIMKey,?sub?(DKIMSelector=$d)')) for val in self["ldap_url"].split()])
			
		dt = time.clock()-t1
		Log.logMsg(5,"Localconfig loaded in %.2f seconds" % dt)
