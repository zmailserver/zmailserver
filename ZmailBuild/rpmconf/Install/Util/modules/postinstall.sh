#!/bin/bash
# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2005, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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

postInstallConfig() {
	echo ""
	echo "Post installation configuration"
	echo ""

	chmod 755 /opt/zmail

	if [ $UPGRADE = "yes" ]; then
		#restore old config, then overwrite...
		restoreExistingConfig
	fi

	if [ $UPGRADE = "no" -a $STORE_HERE = "yes" ]; then
		echo -n "Creating db..."
		runAsZmail "/opt/zmail/libexec/zmmyinit"
		echo "done"
	fi

	if [ $LOGGER_HERE = "yes" ]; then
		if [ ! -d "/opt/zmail/logger/db/data" ]; then
			echo -n "Creating logger db..."
			runAsZmail "/opt/zmail/libexec/zmloggerinit"
			echo "done"
		fi
	fi

	echo -n "Setting the hostname to $HOSTNAME..."
	runAsZmail "zmlocalconfig -e zmail_server_hostname=${HOSTNAME}"
	echo "done"

	echo -n "Setting the LDAP host to $LDAPHOST..."
	runAsZmail "zmlocalconfig -e ldap_host=$LDAPHOST"
	runAsZmail "zmlocalconfig -e ldap_port=$LDAPPORT"
	echo "done"

	SERVERCREATED="no"
	if [ $UPGRADE = "no" ]; then
		if [ $LDAP_HERE = "yes" ]; then
			echo -n "Initializing ldap..."
			runAsZmail "/opt/zmail/libexec/zmldapinit $LDAPROOTPW $LDAPZIMBRAPW"
			echo "done"
		else
			# set the ldap password in localconfig only
			echo -n "Setting the ldap passwords..."
			runAsZmail "zmlocalconfig -f -e ldap_root_password=$LDAPROOTPW"
			runAsZmail "zmlocalconfig -f -e zmail_ldap_password=$LDAPZIMBRAPW"
			runAsZmail "zmlocalconfig -f -e ldap_postfix_password=$LDAPPOSTPW"
			runAsZmail "zmlocalconfig -f -e ldap_replication_password=$LDAPREPPW"
			runAsZmail "zmlocalconfig -f -e ldap_amavis_password=$LDAPAMAVISPW"
			runAsZmail "zmlocalconfig -f -e ldap_nginx_password=$LDAPNGINXPW"
			echo "done"
		fi

		echo -n "Creating server $HOSTNAME..."
		runAsZmail "zmprov cs $HOSTNAME"
		if [ $? = 0 ]; then
			SERVERCREATED="yes"
		fi
		echo "done"

		if [ x$CREATEDOMAIN != "x" ]; then
			echo -n "Creating domain $CREATEDOMAIN..."
			runAsZmail "zmprov cd $CREATEDOMAIN"
			runAsZmail "zmprov mcf zmailDefaultDomainName $CREATEDOMAIN"
			echo "done"
			if [ x$CREATEADMIN != "x" ]; then
				echo -n "Creating admin account $CREATEADMIN..."
				runAsZmail "zmprov ca $CREATEADMIN $CREATEADMINPASS zmailIsAdminAccount TRUE"
				LOCALHOSTNAME=`hostname --fqdn`
				if [ $LOCALHOSTNAME = $CREATEDOMAIN ]; then
					runAsZmail "zmprov aaa $CREATEADMIN postmaster@$HOSTNAME"
				fi
				echo "done"
			fi
		fi
	else
		if [ $LDAP_HERE = "yes" ]; then
			echo -n "Starting ldap..."
			runAsZmail "ldap start"
			runAsZmail "zmldapapplyldif"
			echo "done"
		fi
	fi

	if [ $LDAP_HERE = "yes" ]; then
		SERVICES="zmailServiceInstalled ldap"
	fi

	if [ $LOGGER_HERE = "yes" ]; then
		SERVICES="$SERVICES zmailServiceInstalled logger"
		runAsZmail "zmprov mcf zmailLogHostname $HOSTNAME"
	fi

	if [ $STORE_HERE = "yes" ]; then
		if [ $SERVERCREATED = "yes" ]; then
			echo -n "Setting smtp host to $SMTPHOST..."
			runAsZmail "zmprov ms $HOSTNAME zmailSmtpHostname $SMTPHOST"
			echo "done"
		fi

		echo -n "Adding $HOSTNAME to zmailMailHostPool in default COS..."
		runAsZmail "id=\`zmprov gs $HOSTNAME | grep zmailId | awk '{print \$2}'\`; for i in \`zmprov gc default | grep zmailMailHostPool | sed 's/zmailMailHostPool: //'\`; do host=\"\$host zmailMailHostPool \$i\"; done; zmprov mc default \$host zmailMailHostPool \$id"
		echo "done"

		SERVICES="$SERVICES zmailServiceInstalled mailbox"
	fi

	if [ $POSTFIX_HERE = "yes" ]; then
		echo -n "Initializing mta config..."
		runAsZmail "/opt/zmail/libexec/zmmtainit $LDAPHOST"
		echo "done"

		# zmprov isn't very friendly

		SERVICES="$SERVICES zmailServiceInstalled mta"

		if [ $RUNAV = "yes" ]; then
			SERVICES="$SERVICES zmailServiceInstalled antivirus"
			runAsZmail "zmlocalconfig -e av_notify_user=$AVUSER"
			runAsZmail "zmlocalconfig -e av_notify_domain=$AVDOMAIN"
		fi
		if [ $RUNSA = "yes" ]; then
			SERVICES="$SERVICES zmailServiceInstalled antispam"
		fi
	fi

	if [ $SNMP_HERE = "yes" ]; then
		echo -n "Configuring SNMP..."
		runAsZmail "zmlocalconfig -e snmp_notify=$SNMPNOTIFY"
		runAsZmail "zmlocalconfig -e smtp_notify=$SMTPNOTIFY"
		runAsZmail \
			"zmlocalconfig -e snmp_trap_host=$SNMPTRAPHOST"
		runAsZmail "zmlocalconfig -e smtp_source=$SMTPSOURCE"
		runAsZmail \
			"zmlocalconfig -e smtp_destination=$SMTPDEST"
		runAsZmail "zmsnmpinit"
		echo "done"
		SERVICES="$SERVICES zmailServiceInstalled snmp"
	fi

	echo -n "Setting services on $HOSTNAME..."
	runAsZmail "zmprov -r ms $HOSTNAME $SERVICES"

	ENABLEDSERVICES=`echo $SERVICES | sed -e 's/zmailServiceInstalled/zmailServiceEnabled/g'`
	runAsZmail "zmprov -r ms $HOSTNAME $ENABLEDSERVICES"

	LOCALSERVICES=`echo $SERVICES | sed -e 's/zmailServiceInstalled //g'`
	runAsZmail "zmlocalconfig -e zmail_services=\"$LOCALSERVICES\""
	echo "done"

	if [ $STORE_HERE = "yes" -o $POSTFIX_HERE = "yes" ]; then
		echo -n "Setting up SSL..."
		runAsZmail "zmcreatecert"
		if [ $STORE_HERE = "yes" ]; then
			runAsZmail "zmcertinstall mailbox"
			runAsZmail "zmtlsctl $MODE"
		fi
		if [ $POSTFIX_HERE = "yes" ]; then
			runAsZmail "zmcertinstall mta /opt/zmail/ssl/ssl/server/smtpd.crt /opt/zmail/ssl/ssl/ca/ca.key"
		fi

		runAsZmail "zmlocalconfig -e ssl_allow_untrusted_certs=$ALLOWSELFSIGNED"
		echo "done"
		if [ $UPGRADE = "yes" ]; then
			restoreCerts
		fi
	fi

	setupCrontab
}
