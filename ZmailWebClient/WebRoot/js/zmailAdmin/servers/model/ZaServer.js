/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
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

/**
* @class ZaServer
* This class represents zmailServer objects. ZaServer extends ZaItem
* @author Greg Solovyev
* @contructor ZaServer
* @param app reference to the application instance
**/
ZaServer = function() {
	ZaItem.call(this, "ZaServer");
	this._init();
	//The type is required. The application tab uses it to show the right icon
	this.type = ZaItem.SERVER ; 
}
ZaServer.modelExtensions = [];
ZaServer.prototype = new ZaItem;
ZaServer.prototype.constructor = ZaServer;
ZaItem.loadMethods["ZaServer"] = new Array();
ZaItem.initMethods["ZaServer"] = new Array();
ZaItem.modifyMethods["ZaServer"] = new Array();
ZaItem.modelExtensions["ZaServer"] = new Array();
//attribute name constants, this values are taken from zmail.schema
ZaServer.A_name = "cn";
ZaServer.A_description = "description";
ZaServer.A_notes = "zmailNotes";
ZaServer.A_Service = "zmailService";
ZaServer.A_ServiceHostname = "zmailServiceHostname";
ZaServer.A_zmailMailPort = "zmailMailPort";
ZaServer.A_zmailMailSSLPort = "zmailMailSSLPort";
ZaServer.A_zmailMailMode = "zmailMailMode";
ZaServer.A_zmailMailReferMode = "zmailMailReferMode";
// services
ZaServer.A_zmailServiceInstalled = "zmailServiceInstalled";
ZaServer.A_zmailLdapServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_ldap";
ZaServer.A_zmailMailboxServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_mailbox";
ZaServer.A_zmailMtaServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_mta";
ZaServer.A_zmailSnmpServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_snmp";
ZaServer.A_zmailAntiVirusServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_antivirus";
ZaServer.A_zmailAntiSpamServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_antispam";
ZaServer.A_zmailOpenDKIMServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_opendkim";
ZaServer.A_zmailSpellServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_spell";
ZaServer.A_zmailLoggerServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_logger";
ZaServer.A_zmailMailProxyServiceInstalled = "_"+ZaServer.A_zmailServiceInstalled+"_proxy";
ZaServer.A_zmailVmwareHAServiceInstalled = "_"+ ZaServer.A_zmailServiceInstalled+"_vmwareha";
ZaServer.A_zmailPolicydServiceInstalled = "_"+ ZaServer.A_zmailServiceInstalled+"_cbpolicyd";

ZaServer.A_zmailPolicydServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_cbpolicyd";
ZaServer.A_zmailReverseProxyHttpEnabled = "zmailReverseProxyHttpEnabled";
ZaServer.A_zmailServiceEnabled = "zmailServiceEnabled";
ZaServer.A_zmailLdapServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_ldap";
ZaServer.A_zmailMailboxServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_mailbox";
ZaServer.A_zmailMtaServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_mta";
ZaServer.A_zmailSnmpServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_snmp";
ZaServer.A_zmailAntiVirusServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_antivirus";
ZaServer.A_zmailAntiSpamServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_antispam";
ZaServer.A_zmailOpenDKIMServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_opendkim";
ZaServer.A_zmailSpellServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_spell";
ZaServer.A_zmailLoggerServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_logger";
ZaServer.A_zmailMailProxyServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_proxy";
ZaServer.A_zmailVmwareHAServiceEnabled = "_"+ZaServer.A_zmailServiceEnabled+"_vmwareha";

// MTA
ZaServer.A_zmailMtaSaslAuthEnable = "zmailMtaSaslAuthEnable";
ZaServer.A_zmailMtaDnsLookupsEnabled = "zmailMtaDnsLookupsEnabled";
ZaServer.A_zmailMtaRelayHost = "zmailMtaRelayHost";
ZaServer.A_zmailMtaFallbackRelayHost = "zmailMtaFallbackRelayHost";
ZaServer.A_zmailMtaTlsAuthOnly = "zmailMtaTlsAuthOnly";
ZaServer.A_zmailMtaMyNetworks = "zmailMtaMyNetworks";
//milter server
ZaServer.A_zmailMilterBindPort = "zmailMilterBindPort";
ZaServer.A_zmailMilterBindAddress = "zmailMilterBindAddress";
ZaServer.A_zmailMilterServerEnabled = "zmailMilterServerEnabled";

//smtp
ZaServer.A_zmailSmtpHostname  = "zmailSmtpHostname";
ZaServer.A_SmtpPort = "zmailSmtpPort";
ZaServer.A_SmtpTimeout = "zmailSmtpTimeout";
//Lmtp
ZaServer.A_LmtpAdvertisedName = "zmailLmtpAdvertisedName";
ZaServer.A_LmtpBindAddress = "zmailLmtpBindAddress";
ZaServer.A_LmtpBindPort = "zmailLmtpBindPort";
//pop3
ZaServer.A_zmailPop3NumThreads = "zmailPop3NumThreads";
ZaServer.A_Pop3AdvertisedName ="zmailPop3AdvertisedName";
ZaServer.A_Pop3BindAddress = "zmailPop3BindAddress";
ZaServer.A_zmailPop3BindPort = "zmailPop3BindPort";
ZaServer.A_zmailPop3SSLBindPort = "zmailPop3SSLBindPort";
ZaServer.A_Pop3SSLServerEnabled = "zmailPop3SSLServerEnabled";
ZaServer.A_Pop3ServerEnabled = "zmailPop3ServerEnabled"
ZaServer.A_Pop3CleartextLoginEnabled = "zmailPop3CleartextLoginEnabled";
//imap
ZaServer.A_zmailImapNumThreads="zmailImapNumThreads";
ZaServer.A_zmailImapBindPort="zmailImapBindPort";
ZaServer.A_ImapServerEnabled="zmailImapServerEnabled";
ZaServer.A_ImapSSLBindPort="zmailImapSSLBindPort";
ZaServer.A_ImapSSLServerEnabled="zmailImapSSLServerEnabled";
ZaServer.A_ImapCleartextLoginEnabled="zmailImapCleartextLoginEnabled";

//proxy lookup target
ZaServer.A_zmailReverseProxyLookupTarget = "zmailReverseProxyLookupTarget";

//redo log
ZaServer.A_RedologEnabled = "zmailRedologEnabled";
ZaServer.A_RedologLogPath = "zmailRedologLogPath";
ZaServer.A_RedologArchiveDir = "zmailRedologArchiveDir";
ZaServer.A_RedologBacklogDir = "zmailRedologBacklogDir";
ZaServer.A_RedologRolloverFileSizeKB = "zmailRedologRolloverFileSizeKB";
ZaServer.A_RedologFsyncIntervalMS = "zmailRedologFsyncIntervalMS";
//master role settings
ZaServer.A_MasterRedologClientConnections = "zmailMasterRedologClientConnections";
ZaServer.A_MasterRedologClientTimeoutSec = "zmailMasterRedologClientTimeoutSec";
ZaServer.A_MasterRedologClientTcpNoDelay = "zmailMasterRedologClientTcpNoDelay";
//slave role settings
ZaServer.A_zmailUserServicesEnabled = "zmailUserServicesEnabled";

//Volume Management
ZaServer.A_RemovedVolumes = "removed_volumes";
ZaServer.A_Volumes = "volumes";
ZaServer.A_VolumeId = "id";
ZaServer.A_VolumeName = "name";
ZaServer.A_VolumeRootPath = "rootpath";
ZaServer.A_VolumeCompressBlobs = "compressBlobs";
ZaServer.A_VolumeCompressionThreshold = "compressionThreshold";
ZaServer.A_VolumeType = "type";
ZaServer.A_CurrentIndexVolumeId = "current_index_volume_id";
ZaServer.A_CurrentMsgVolumeId = "current_msg_volume_id";
ZaServer.A_isCurrent = "isCurrent";

//VAMI Appliance Update
ZaServer.A_zmailApplianceVendor = "zmailApplianceVendor";
ZaServer.A_zmailApplianceName = "zmailApplianceName";
ZaServer.A_zmailApplianceVersion = "zmailApplianceVersion";
ZaServer.A_zmailApplianceFullVersion = "zmailApplianceFullVersion";
ZaServer.A_zmailApplianceDetails = "zmailApplianceDetails";
ZaServer.A_zmailApplianceDefaultRepUrl = "zmailApplianceDefaultRepUrl";
ZaServer.A_zmailApplianceCustomRepUrl = "zmailApplianceCustomRepUrl";
ZaServer.A_zmailApplianceCustomRepUrlUser = "zmailApplianceCustomRepUrlUser";
ZaServer.A_zmailApplianceCustomRepUrlPass = "zmailApplianceCustomRepUrlPass";
ZaServer.A_zmailApplianceUpdateOption = "zmailApplianceUpdateOption";
ZaServer.A_zmailApplianceUpdateType = "zmailApplianceUpdateType";
ZaServer.A_zmailApplianceUpdatehourofrun = "zmailApplianceUpdatehourofrun";

//bind ip address
ZaServer.A_zmailMailBindAddress = "zmailMailBindAddress";
ZaServer.A_zmailMailSSLBindAddress = "zmailMailSSLBindAddress";
ZaServer.A_zmailMailSSLClientCertBindAddress = "zmailMailSSLClientCertBindAddress";
ZaServer.A_zmailAdminBindAddress = "zmailAdminBindAddress";

//spnego
ZaServer.A_zmailSpnegoAuthPrincipal = "zmailSpnegoAuthPrincipal";
ZaServer.A_zmailSpnegoAuthTargetName = "zmailSpnegoAuthTargetName";

// Auto provision
ZaServer.A_zmailAutoProvPollingInterval = "zmailAutoProvPollingInterval";
ZaServer.A_zmailAutoProvScheduledDomains = "zmailAutoProvScheduledDomains";

// web client authentication
ZaServer.A_zmailMailSSLClientCertMode = "zmailMailSSLClientCertMode";
ZaServer.A_zmailMailSSLClientCertPort = "zmailMailSSLClientCertPort";
ZaServer.A_zmailMailSSLProxyClientCertPort = "zmailMailSSLProxyClientCertPort";
ZaServer.A_zmailReverseProxyMailMode = "zmailReverseProxyMailMode";
ZaServer.A_zmailReverseProxyClientCertMode = "zmailReverseProxyClientCertMode";
ZaServer.A_zmailReverseProxyClientCertCA = "zmailReverseProxyClientCertCA";

// other
ZaServer.A_zmailScheduledTaskNumThreads = "zmailScheduledTaskNumThreads" ;
ZaServer.A_zmailMailPurgeSleepInterval = "zmailMailPurgeSleepInterval" ;
ZaServer.A_zmailIsMonitorHost = "zmailIsMonitorHost";
ZaServer.A_showVolumes = "show_volumes"; //this attribute is immutable
ZaServer.A_zmailLogHostname = "zmailLogHostname";
ZaServer.A_isCurrentVolume = "isCurrentVolume";
ZaServer.STANDALONE = "standalone";
ZaServer.MASTER = "master";
ZaServer.SLAVE = "slave";
ZaServer.A2_volume_selection_cache = "volume_selection_cache";

ZaServer.MSG = 1;
ZaServer.INDEX = 10;
ZaServer.currentkeys = {};
ZaServer.currentkeys[ZaServer.MSG] = ZaServer.A_CurrentMsgVolumeId;
ZaServer.currentkeys[ZaServer.INDEX] = ZaServer.A_CurrentIndexVolumeId;
ZaServer.volumeTypes =[ZaServer.MSG,ZaServer.INDEX];

ZaServer.updateoptions = {};
ZaServer.updateoptions[0] = ZaMsg.NONE = 111;
ZaServer.updateoptions[2] = ZaMsg.EVERYDAY = 112;
ZaServer.updateoptions[3] = ZaMsg.EVERYSUNDAY = 113;
ZaServer.updateoptions[4] = ZaMsg.EVERYMONDAY =114;
ZaServer.updateoptions[5] = ZaMsg.EVERYTUESDAY = 115;
ZaServer.updateoptions[6] = ZaMsg.EVERYWEDNESDAY = 116;
ZaServer.updateoptions[7] = ZaMsg.EVERYTHURSDAY = 117;
ZaServer.updateoptions[8] = ZaMsg.EVERYFRIDAY = 118;
ZaServer.updateoptions[9] = ZaMsg.EVERYSATURDAY = 119;

ZaServer.updatehourofrun = {};
ZaServer.updatehourofrun[0] = ZaMsg.ONEAM = 201;
ZaServer.updatehourofrun[1] = ZaMsg.TWOAM = 202;
ZaServer.updatehourofrun[2] = ZaMsg.THREEAM = 203;
ZaServer.updatehourofrun[3] = ZaMsg.FOURAM = 204;
ZaServer.updatehourofrun[4] = ZaMsg.FIVEAM = 205;
ZaServer.updatehourofrun[5] = ZaMsg.SIXAM = 206;
ZaServer.updatehourofrun[6] = ZaMsg.SEVENAM = 207;
ZaServer.updatehourofrun[7] = ZaMsg.EIGHTAM = 208;
ZaServer.updatehourofrun[8] = ZaMsg.NINEAM = 209;
ZaServer.updatehourofrun[9] = ZaMsg.TENAM = 210;
ZaServer.updatehourofrun[10] = ZaMsg.ELEVENAM = 211;
ZaServer.updatehourofrun[11] = ZaMsg.TWELVEAM = 212;
ZaServer.updatehourofrun[12] = ZaMsg.TWELVEPM = 312;
ZaServer.updatehourofrun[13] = ZaMsg.ELEVENPM = 311; 
ZaServer.updatehourofrun[14] = ZaMsg.TENPM = 310;
ZaServer.updatehourofrun[15] = ZaMsg.NINEPM = 309;
ZaServer.updatehourofrun[16] = ZaMsg.EIGHTPM = 308;
ZaServer.updatehourofrun[17] = ZaMsg.SEVENPM = 307;
ZaServer.updatehourofrun[18] = ZaMsg.SIXPM = 306;
ZaServer.updatehourofrun[19] = ZaMsg.FIVEPM = 305;
ZaServer.updatehourofrun[20] = ZaMsg.FOURPM = 304;
ZaServer.updatehourofrun[21] = ZaMsg.THREEPM = 303;
ZaServer.updatehourofrun[22] = ZaMsg.TWOPM = 302;
ZaServer.updatehourofrun[23] = ZaMsg.ONEPM =301;

ZaServer.APPLIANCEUPDATE_CD = 2;
ZaServer.APPLIANCEUPDATE_DEFAULTREP = 1;
ZaServer.APPLIANCEUPDATE_CUSTOMREP = 3;

ZaServer.DEFAULT_IMAP_PORT=143;
ZaServer.DEFAULT_IMAP_SSL_PORT=993;
ZaServer.DEFAULT_POP3_PORT=110;
ZaServer.DEFAULT_POP3_SSL_PORT=900;

ZaServer.DEFAULT_IMAP_PORT_ZCS=7143;
ZaServer.DEFAULT_IMAP_SSL_PORT_ZCS=7993;
ZaServer.DEFAULT_POP3_PORT_ZCS=7110;
ZaServer.DEFAULT_POP3_SSL_PORT_ZCS=7900;

ZaServer.ERR_NOT_CIDR = 1;
ZaServer.ERR_NOT_STARTING_ADDR = 2;

ZaServer.DOT_TO_CIDR = {};
ZaServer.DOT_TO_CIDR["0x80000000"] = ZaServer.DOT_TO_CIDR["128.0.0.0"] = 1;
ZaServer.DOT_TO_CIDR["0xc0000000"] = ZaServer.DOT_TO_CIDR["192.0.0.0"] = 2;
ZaServer.DOT_TO_CIDR["0xe0000000"] = ZaServer.DOT_TO_CIDR["224.0.0.0"] = 3;
ZaServer.DOT_TO_CIDR["0xf0000000"] = ZaServer.DOT_TO_CIDR["240.0.0.0"] = 4;
ZaServer.DOT_TO_CIDR["0xf8000000"] = ZaServer.DOT_TO_CIDR["248.0.0.0"] = 5;
ZaServer.DOT_TO_CIDR["0xfc000000"] = ZaServer.DOT_TO_CIDR["252.0.0.0"] = 6;
ZaServer.DOT_TO_CIDR["0xfe000000"] = ZaServer.DOT_TO_CIDR["254.0.0.0"] = 7;
ZaServer.DOT_TO_CIDR["0xff000000"] = ZaServer.DOT_TO_CIDR["255.0.0.0"] = 8;

ZaServer.DOT_TO_CIDR["0xff800000"] = ZaServer.DOT_TO_CIDR["255.128.0.0"] = 9;
ZaServer.DOT_TO_CIDR["0xffc00000"] = ZaServer.DOT_TO_CIDR["255.192.0.0"] = 10;
ZaServer.DOT_TO_CIDR["0xffe00000"] = ZaServer.DOT_TO_CIDR["255.224.0.0"] = 11;
ZaServer.DOT_TO_CIDR["0xfff00000"] = ZaServer.DOT_TO_CIDR["255.240.0.0"] = 12;
ZaServer.DOT_TO_CIDR["0xfff80000"] = ZaServer.DOT_TO_CIDR["255.248.0.0"] = 13;
ZaServer.DOT_TO_CIDR["0xfffc0000"] = ZaServer.DOT_TO_CIDR["255.252.0.0"] = 14;
ZaServer.DOT_TO_CIDR["0xfffe0000"] = ZaServer.DOT_TO_CIDR["255.254.0.0"] = 15;
ZaServer.DOT_TO_CIDR["0xffff0000"] = ZaServer.DOT_TO_CIDR["255.255.0.0"] = 16;

ZaServer.DOT_TO_CIDR["0xffff8000"] = ZaServer.DOT_TO_CIDR["255.255.128.0"] = 17;
ZaServer.DOT_TO_CIDR["0xffffc000"] = ZaServer.DOT_TO_CIDR["255.255.192.0"] = 16;
ZaServer.DOT_TO_CIDR["0xffffe000"] = ZaServer.DOT_TO_CIDR["255.255.224.0"] = 19;
ZaServer.DOT_TO_CIDR["0xfffff000"] = ZaServer.DOT_TO_CIDR["255.255.240.0"] = 20;
ZaServer.DOT_TO_CIDR["0xfffff800"] = ZaServer.DOT_TO_CIDR["255.255.248.0"] = 21;
ZaServer.DOT_TO_CIDR["0xfffffc00"] = ZaServer.DOT_TO_CIDR["255.255.252.0"] = 22;
ZaServer.DOT_TO_CIDR["0xfffffe00"] = ZaServer.DOT_TO_CIDR["255.255.254.0"] = 23;
ZaServer.DOT_TO_CIDR["0xffffff00"] = ZaServer.DOT_TO_CIDR["255.255.255.0"] = 24;

ZaServer.DOT_TO_CIDR["0xffffff80"] = ZaServer.DOT_TO_CIDR["255.255.255.128"] = 25;
ZaServer.DOT_TO_CIDR["0xffffffc0"] = ZaServer.DOT_TO_CIDR["255.255.255.192"] = 26;
ZaServer.DOT_TO_CIDR["0xffffffe0"] = ZaServer.DOT_TO_CIDR["255.255.255.224"] = 27;
ZaServer.DOT_TO_CIDR["0xfffffff0"] = ZaServer.DOT_TO_CIDR["255.255.255.240"] = 28;
ZaServer.DOT_TO_CIDR["0xfffffff8"] = ZaServer.DOT_TO_CIDR["255.255.255.248"] = 29;
ZaServer.DOT_TO_CIDR["0xfffffffc"] = ZaServer.DOT_TO_CIDR["255.255.255.252"] = 30;
ZaServer.DOT_TO_CIDR["0xfffffffe"] = ZaServer.DOT_TO_CIDR["255.255.255.254"] = 31;
ZaServer.DOT_TO_CIDR["0xffffffff"] = ZaServer.DOT_TO_CIDR["255.255.255.255"] = 32;

ZaServer.FLUSH_CACHE_RIGHT = "flushCache";
ZaServer.MANAGE_VOLUME_RIGHT = "manageVolume";
ZaServer.RIGHT_GET_SESSIONS="getSessions";

ZaServer.isValidPostfixSubnetString = function(mask) {
	//is this a CIDR
	var pos = mask.indexOf("/");
	var lastPos = mask.lastIndexOf("/");
	if(pos==-1 || pos!=lastPos) {
		//error! this is not a valid CIDR
		return ZaServer.ERR_NOT_CIDR;
	}
	var numNetworkBits = parseInt(mask.substr(lastPos+1,(mask.length-lastPos-1)));
	if(isNaN(numNetworkBits) || numNetworkBits=="" || numNetworkBits == null || numNetworkBits < 1) {
		return ZaServer.ERR_NOT_CIDR;
	}

	//convert the address to a number
	var addrString = mask.substr(0,lastPos);
	var addrNumber = ZaServer.octetsToLong(addrString);
	if(addrNumber < 0) {
		return ZaServer.ERR_NOT_CIDR;
	}

	//do we have a starting address?
	var maskNumber = 0;
	var lastIndex = 32 - numNetworkBits;
	for(var j=31; j>=lastIndex;j-- ) {
		maskNumber += Math.pow(2,j);
	}
	if(addrNumber != ZaServer.applyMask(addrNumber, maskNumber)) {
		return ZaServer.ERR_NOT_STARTING_ADDR;
	}
	return 0;
}

/**
 * extract number of network bits from CIDR string
 * @return integer
 */
ZaServer.iGetNumNetBits = function(mask) {
	var pos = mask.indexOf("/");
	var lastPos = mask.lastIndexOf("/");
	var numNetworkBits = parseInt(mask.substr(lastPos+1,(mask.length-lastPos-1)));
	return numNetworkBits;
}
/**
 * @member ZaServer.oGetStartingAddress
 * @argument mask - CIDR representation of a network (A.B.C.D/E)
 * @return octet string that represents the last address of the network segment
 */
ZaServer.oGetStartingAddress = function (mask) {
	return ZaServer.longToOctets(ZaServer.lGetStartingAddress(mask));
}

/**
 * @member ZaServer.lGetStartingAddress
 * @argument mask - CIDR representation of a network (A.B.C.D/E)
 * @return long number represents the first address of the network segment
 */
ZaServer.lGetStartingAddress = function (mask) {
	var numNetworkBits = ZaServer.iGetNumNetBits(mask);
	var lastPos = mask.lastIndexOf("/");
	//convert the address to a number
	var addrString = mask.substr(0,lastPos);
	var addrNumber = ZaServer.octetsToLong(addrString);
	var maskNumber = 0;
	var lastIndex = 32 - numNetworkBits;
	for(var j=31; j>=lastIndex;j-- ) {
		maskNumber += Math.pow(2,j);
	}	
	var firstAddr = ZaServer.applyMask(addrNumber, maskNumber);
	return firstAddr;
}

/**
 * @member ZaServer.lGetEndingAddress
 * @argument firstAddr - long
 * @argument numNetBits - int
 * @return long number represents the last address of the network segment
 */
ZaServer.lGetEndingAddress = function (firstAddr, numNetBits) {
	var lLastAddr = firstAddr + Math.pow(2,(32 - numNetBits))-1;
	return lLastAddr;
}

/**
 * @member ZaServer.applyMask
 * @argument addr1 - long address
 * @argument netMask - long network mask
 * @return long number that represents the starting address of the network defined by addr1 and netMask
 */

ZaServer.applyMask = function (addr1, netMask) {
	var val = (addr1 & netMask);
	if(val >= 0) {
		return val;
	} else 	{
		return (4294967296+val);
	}
}

ZaServer.octetsToLong = function (addrString) {
        var patrn=/^[A-Fa-f0-9:.]+$/;
   if(patrn.test(addrString)){
        var temp1= addrString.indexOf(".");
        var temp2= addrString.indexOf(":");
        var temp3= addrString.indexOf("::");

	   if(temp3!=-1){
        var octets = addrString.split("::");
        if(octets.length !=2) {
                return -1;
        }
         else if(temp1==-1&&temp2>-1){
        octetsub0=octets[0].split(":");
        octetsub1=octets[1].split(":");
          if (octetsub0.length+octetsub1.length>7)
           return -1;
           else{
                   for(var j=0;j<octetsub0.length;j++){
                     if(octetsub0[j].length>4)
                     return -1;
                   }
                   for(var j=0;j<octetsub1.length;j++){
                     if(octetsub1[j].length>4)
                     return -1;
                   }

           }
        }else if((temp1>-1)){
                      if(octets[0]!="")
                      return -1;
                      else{
                          octetsub=octets[1].split(".");
                          for(var j=0;j<octetsub.length;j++){
                          if(octetsub[j]>parseInt("255"))
                          return -1;
                          }
             }
            }
                   return 0;
    }

        if(temp1!=-1){
              var  octets = addrString.split(".");
              if(octets.length !=4) {
                return -1;
              } else{
                    for(var i=0;i<octets.length;i++){
                    var j=octets[i];
                    if(j>parseInt("255")){
                    return -1;
              }
}
        var addrNumber = Math.pow(256,3)*parseInt(octets[0]) + Math.pow(256,2)*parseInt(octets[1]) + Math.pow(256,1)*parseInt(octets[2]) + parseInt(octets[3]);
        return addrNumber;
             }
     }

        if(temp2!=-1){
        var octets = addrString.split(":");
        if(octets.length !=8) {
                return -1;
       } else {
        for(var i=0;i<octets.length;i++){
        if(octets[i].length>4)
        return -1;
        }
 return 0;
    }
        }
}
return -1;
}



ZaServer.longToOctets = function(addrNumber) {
	var ip1 = Math.floor(addrNumber/Math.pow(256,3));
    var ip2 = Math.floor((addrNumber%Math.pow(256,3))/Math.pow(256,2));
    var ip3 = Math.floor(((addrNumber%Math.pow(256,3))%Math.pow(256,2))/Math.pow(256,1));
    var ip4 = Math.floor((((addrNumber%Math.pow(256,3))%Math.pow(256,2))%Math.pow(256,1))/Math.pow(256,0));
    return [ip1,ip2,ip3,ip4].join(".");
}

ZaServer.volumeTypeChoices = new XFormChoices({1:ZaMsg.VM_VOLUME_Msg, 10:ZaMsg.VM_VOLUME_Index}, XFormChoices.HASH);
ZaServer.volumeObjModel = {
	items: [
		{id:ZaServer.A_isCurrentVolume, type: _ENUM_, choices: [false,true]	},
		{id:ZaServer.A_VolumeId, type:_NUMBER_},
		{id:ZaServer.A_VolumeName, type:_STRING_},
		{id:ZaServer.A_VolumeType, type:_ENUM_, choices:ZaServer.volumeTypes,defaultValue:ZaServer.MSG},
		{id:ZaServer.A_VolumeRootPath, type:_STRING_},
		{id:ZaServer.A_VolumeCompressBlobs, type:_ENUM_, choices:[false,true], defaultValue:true},
		{id:ZaServer.A_VolumeCompressionThreshold, type:_NUMBER_,defaultValue:4096},
		{id:"_index", type:_NUMBER_}				
	],
	type:_OBJECT_
}
		
ZaServer.myXModel = {
	items: [
		{id:ZaItem.A_zmailId, type:_STRING_, ref:"attrs/" + ZaItem.A_zmailId},
		{id:ZaItem.A_zmailCreateTimestamp, ref:"attrs/" + ZaItem.A_zmailCreateTimestamp},
		{id:ZaServer.A_name, ref:"attrs/" + ZaServer.A_name, type:_STRING_},
//		{id:ZaServer.A_description, ref:"attrs/" +  ZaServer.A_description, type:_STRING_},
         ZaItem.descriptionModelItem,   
        {id:ZaServer.A_notes, ref:"attrs/" +  ZaServer.A_notes, type:_STRING_, maxLength:1024},
		{id:ZaServer.A_Service, ref:"attrs/" +  ZaServer.A_Service, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_ServiceHostname, ref:"attrs/" +  ZaServer.A_ServiceHostname, type:_HOSTNAME_OR_IP_, maxLength: 256 },
		// Services
		{id:ZaServer.A_zmailPolicydServiceEnabled, ref:"attrs/"+ZaServer.A_zmailPolicydServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailLdapServiceEnabled, ref:"attrs/"+ZaServer.A_zmailLdapServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailMailboxServiceEnabled, ref:"attrs/"+ZaServer.A_zmailMailboxServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailMtaServiceEnabled, ref:"attrs/"+ZaServer.A_zmailMtaServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailSnmpServiceEnabled, ref:"attrs/"+ZaServer.A_zmailSnmpServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailAntiVirusServiceEnabled, ref:"attrs/"+ZaServer.A_zmailAntiVirusServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailAntiSpamServiceEnabled, ref:"attrs/"+ZaServer.A_zmailAntiSpamServiceEnabled, type: _ENUM_, choices: [false,true] },
        {
            id: ZaServer.A_zmailOpenDKIMServiceEnabled,
            ref: "attrs/" + ZaServer.A_zmailOpenDKIMServiceEnabled,
            type: _ENUM_,
            choices: [false, true]
        },
		{id:ZaServer.A_zmailSpellServiceEnabled, ref:"attrs/"+ZaServer.A_zmailSpellServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailLoggerServiceEnabled, ref:"attrs/"+ZaServer.A_zmailLoggerServiceEnabled, type: _ENUM_, choices: [false,true] },
        {id:ZaServer.A_zmailVmwareHAServiceEnabled, ref:"attrs/"+ZaServer.A_zmailVmwareHAServiceEnabled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailMailProxyServiceEnabled, ref:"attrs/"+ZaServer.A_zmailMailProxyServiceEnabled, type: _ENUM_, choices: [false,true] },		
		{id:ZaServer.A_zmailReverseProxyLookupTarget, ref:"attrs/"+ZaServer.A_zmailReverseProxyLookupTarget, type: _COS_ENUM_, choices: ZaModel.BOOLEAN_CHOICES},
		{id:ZaServer.A_zmailLdapServiceInstalled, ref:"attrs/"+ZaServer.A_zmailLdapServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailMailboxServiceInstalled, ref:"attrs/"+ZaServer.A_zmailMailboxServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailMtaServiceInstalled, ref:"attrs/"+ZaServer.A_zmailMtaServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailSnmpServiceInstalled, ref:"attrs/"+ZaServer.A_zmailSnmpServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailAntiVirusServiceInstalled, ref:"attrs/"+ZaServer.A_zmailAntiVirusServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailAntiSpamServiceInstalled, ref:"attrs/"+ZaServer.A_zmailAntiSpamServiceInstalled, type: _ENUM_, choices: [false,true] },
        {
            id: ZaServer.A_zmailOpenDKIMServiceInstalled,
            ref: "attrs/" + ZaServer.A_zmailOpenDKIMServiceInstalled,
            type: _ENUM_,
            choices: [false, true]
        },
		{id:ZaServer.A_zmailSpellServiceInstalled, ref:"attrs/"+ZaServer.A_zmailSpellServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailLoggerServiceInstalled, ref:"attrs/"+ZaServer.A_zmailLoggerServiceInstalled, type: _ENUM_, choices: [false,true] },
		{id:ZaServer.A_zmailMailProxyServiceInstalled, ref:"attrs/"+ZaServer.A_zmailMailProxyServiceInstalled, type: _ENUM_, choices: [false,true] },
        {id:ZaServer.A_zmailVmwareHAServiceInstalled, ref:"attrs/"+ZaServer.A_zmailVmwareHAServiceInstalled, type: _ENUM_, choices: [false,true] },
        {id:ZaServer.A_zmailPolicydServiceInstalled, ref:"attrs/"+ZaServer.A_zmailPolicydServiceInstalled, type: _ENUM_, choices: [false,true] },
		// MTA
		{id:ZaServer.A_zmailMtaSaslAuthEnable, ref:"attrs/" +  ZaServer.A_zmailMtaSaslAuthEnable, type: _COS_ENUM_, choices: ["yes", "no"] },
		{id:ZaServer.A_zmailMtaTlsAuthOnly, ref:"attrs/" +  ZaServer.A_zmailMtaTlsAuthOnly, type: _COS_ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
		{id:ZaServer.A_zmailMtaRelayHost, ref:"attrs/" +  ZaServer.A_zmailMtaRelayHost,  type: _COS_HOSTNAME_OR_IP_, maxLength: 256 },
        {id:ZaServer.A_zmailMtaFallbackRelayHost, ref:"attrs/" + ZaServer.A_zmailMtaFallbackRelayHost, type: _COS_HOSTNAME_OR_IP_, maxLength: 256 },
		{id:ZaServer.A_zmailMtaMyNetworks, ref:"attrs/" +  ZaServer.A_zmailMtaMyNetworks, type:_COS_STRING_, maxLength: 10240 },
		{id:ZaServer.A_zmailMtaDnsLookupsEnabled, ref:"attrs/" +  ZaServer.A_zmailMtaDnsLookupsEnabled, type: _COS_ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
		//milter server
		{id:ZaServer.A_zmailMilterBindAddress, ref:"attrs/" +  ZaServer.A_zmailMilterBindAddress, type:_LIST_, listItem:{type:_HOSTNAME_OR_IP_, maxLength: 128} },
		{id:ZaServer.A_zmailMilterBindPort, ref:"attrs/" +  ZaServer.A_zmailMilterBindPort, type:_COS_PORT_},
		{id:ZaServer.A_zmailMilterServerEnabled, ref:"attrs/" +  ZaServer.A_zmailMilterServerEnabled, type: _COS_ENUM_, choices: ZaModel.BOOLEAN_CHOICES },
        //spnego
		{id:ZaServer.A_zmailSpnegoAuthTargetName, ref:"attrs/" +  ZaServer.A_zmailSpnegoAuthTargetName, type:_STRING_},
        {id:ZaServer.A_zmailSpnegoAuthPrincipal, ref:"attrs/" +  ZaServer.A_zmailSpnegoAuthPrincipal, type:_STRING_},
        {id:ZaServer.A_zmailMailSSLClientCertMode, ref:"attrs/" +  ZaServer.A_zmailMailSSLClientCertMode, type:_COS_STRING_, choices:["Disabled","NeedClientAuth","WantClientAuth"]},
        {id:ZaServer.A_zmailMailSSLClientCertPort, ref:"attrs/" +  ZaServer.A_zmailMailSSLClientCertPort, type:_COS_PORT_},
        {id:ZaServer.A_zmailMailSSLProxyClientCertPort, ref:"attrs/" +  ZaServer.A_zmailMailSSLProxyClientCertPort, type:_COS_PORT_},
        {id:ZaServer.A_zmailReverseProxyMailMode, ref:"attrs/" +  ZaServer.A_zmailReverseProxyMailMode, type:_COS_STRING_, choices:["http","https","both","mixed","redirect"]},
        {id:ZaServer.A_zmailReverseProxyClientCertMode, ref:"attrs/" +  ZaServer.A_zmailReverseProxyClientCertMode, type:_COS_STRING_, choices:["on","off","optional"]},
        {id:ZaServer.A_zmailReverseProxyClientCertCA, ref:"attrs/" + ZaServer.A_zmailReverseProxyClientCertCA, type:_STRING_},
		// ...other...
		{id:ZaServer.A_zmailSmtpHostname, ref:"attrs/" +  ZaServer.A_zmailSmtpHostname, type:_COS_LIST_, listItem:{type:_HOSTNAME_OR_IP_, maxLength: 256} },
		{id:ZaServer.A_SmtpPort, ref:"attrs/" +  ZaServer.A_SmtpPort, type:_COS_PORT_},
		{id:ZaServer.A_SmtpTimeout, ref:"attrs/" + ZaServer.A_SmtpTimeout, type:_COS_NUMBER_, minInclusive: 0, maxInclusive:2147483647 },
		{id:ZaServer.A_LmtpAdvertisedName, ref:"attrs/" +  ZaServer.A_LmtpAdvertisedName, type:_STRING_, maxLength: 128 },
		{id:ZaServer.A_LmtpBindAddress, ref:"attrs/" +  ZaServer.A_LmtpBindAddress, type:_HOSTNAME_OR_IP_, maxLength: 256 },
		{id:ZaServer.A_LmtpBindPort, ref:"attrs/" +  ZaServer.A_LmtpBindPort, type:_COS_PORT_},		
		{id:ZaServer.A_zmailScheduledTaskNumThreads, ref:"attrs/" +  ZaServer.A_zmailScheduledTaskNumThreads, type:_COS_INT_, minInclusive: 1, maxInclusive:2147483647 },
		{id:ZaServer.A_zmailMailPurgeSleepInterval, ref:"attrs/" +  ZaServer.A_zmailMailPurgeSleepInterval, type:_COS_MLIFETIME_, minInclusive: 0, maxInclusive:2147483647 },
		{id:ZaServer.A_zmailPop3NumThreads, ref:"attrs/" +  ZaServer.A_zmailPop3NumThreads, type:_COS_INT_, minInclusive: 0, maxInclusive:2147483647 },		
		{id:ZaServer.A_zmailImapNumThreads, ref:"attrs/" +  ZaServer.A_zmailImapNumThreads, type:_COS_INT_, minInclusive: 0, maxInclusive:2147483647 },		
		{id:ZaServer.A_Pop3AdvertisedName, ref:"attrs/" +  ZaServer.A_Pop3AdvertisedName, type:_STRING_, maxLength: 128 },
		{id:ZaServer.A_Pop3BindAddress, ref:"attrs/" +  ZaServer.A_Pop3BindAddress, type:_HOSTNAME_OR_IP_, maxLength: 128 },
		{id:ZaServer.A_Pop3AdvertisedName, ref:"attrs/" +  ZaServer.A_Pop3AdvertisedName, type:_STRING_, maxLength: 128 },
		{id:ZaServer.A_Pop3BindAddress, ref:"attrs/" +  ZaServer.A_Pop3BindAddress, type:_HOSTNAME_OR_IP_, maxLength: 128 },
		{id:ZaServer.A_zmailPop3BindPort, ref:"attrs/" +  ZaServer.A_zmailPop3BindPort, type:_COS_PORT_ },
		{id:ZaServer.A_zmailPop3SSLBindPort, ref:"attrs/" +  ZaServer.A_zmailPop3SSLBindPort, type:_COS_PORT_ },
		{id:ZaServer.A_Pop3SSLServerEnabled, ref:"attrs/" + ZaServer.A_Pop3SSLServerEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaServer.A_Pop3ServerEnabled, ref:"attrs/" + ZaServer.A_Pop3ServerEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaServer.A_Pop3CleartextLoginEnabled, ref:"attrs/" + ZaServer.A_Pop3CleartextLoginEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaServer.A_zmailImapBindPort, ref:"attrs/" + ZaServer.A_zmailImapBindPort, type:_COS_PORT_ },
		{id:ZaServer.A_ImapServerEnabled, ref:"attrs/" + ZaServer.A_ImapServerEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaServer.A_ImapSSLBindPort, ref:"attrs/" + ZaServer.A_ImapSSLBindPort, type:_COS_PORT_ },
		{id:ZaServer.A_ImapSSLServerEnabled, ref:"attrs/" + ZaServer.A_ImapSSLServerEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		
		//ip address bindings
		{id:ZaServer.A_zmailMailBindAddress, ref:"attrs/" +  ZaServer.A_zmailMailBindAddress, type:_IP_},
		{id:ZaServer.A_zmailMailSSLBindAddress, ref:"attrs/" +  ZaServer.A_zmailMailSSLBindAddress, type:_IP_ },
		{id:ZaServer.A_zmailMailSSLClientCertBindAddress, ref:"attrs/" +  ZaServer.A_zmailMailSSLClientCertBindAddress, type:_IP_ },
		{id:ZaServer.A_zmailAdminBindAddress, ref:"attrs/" +  ZaServer.A_zmailAdminBindAddress, type:_IP_ },

        // auto provision
        {id:ZaServer.A_zmailAutoProvPollingInterval, ref:"attrs/" + ZaServer.A_zmailAutoProvPollingInterval, type: _COS_MLIFETIME_, minInclusive: 0 },
        {id:ZaServer.A_zmailAutoProvScheduledDomains, ref:"attrs/" +  ZaServer.A_zmailAutoProvScheduledDomains, type:_LIST_, listItem:{type:_STRING_, maxLength: 256} },
		{id:ZaServer.A_ImapCleartextLoginEnabled, ref:"attrs/" + ZaServer.A_ImapCleartextLoginEnabled, type:_COS_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaServer.A_RedologEnabled, ref:"attrs/" + ZaServer.A_RedologEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},		
		{id:ZaServer.A_RedologLogPath, ref:"attrs/" + ZaServer.A_RedologLogPath, type:_STRING_},		
		{id:ZaServer.A_RedologArchiveDir, ref:"attrs/" + ZaServer.A_RedologArchiveDir, type:_STRING_},		
		{id:ZaServer.A_RedologBacklogDir, ref:"attrs/" + ZaServer.A_RedologBacklogDir, type:_STRING_},		
		{id:ZaServer.A_RedologRolloverFileSizeKB, ref:"attrs/" + ZaServer.A_RedologRolloverFileSizeKB, type:_NUMBER_, minInclusive: 0, maxInclusive:2147483647 },
		{id:ZaServer.A_RedologFsyncIntervalMS, ref:"attrs/" + ZaServer.A_RedologFsyncIntervalMS, type:_NUMBER_, minInclusive: 0, maxInclusive:2147483647 },
		{id:ZaServer.A_MasterRedologClientConnections, ref:"attrs/" + ZaServer.A_MasterRedologClientConnections, type:_STRING_},		
		{id:ZaServer.A_MasterRedologClientTimeoutSec, ref:"attrs/" + ZaServer.A_MasterRedologClientTimeoutSec, type:_STRING_},		
		{id:ZaServer.A_MasterRedologClientTcpNoDelay, ref:"attrs/" + ZaServer.A_MasterRedologClientTcpNoDelay, type:_STRING_},		
		{id:ZaServer.A_zmailUserServicesEnabled, ref:"attrs/" + ZaServer.A_zmailUserServicesEnabled, type:_ENUM_, choices:ZaModel.BOOLEAN_CHOICES},
		//volumes
		{id:ZaServer.A_Volumes,ref:ZaServer.A_Volumes, type:_LIST_, listItem:ZaServer.volumeObjModel},
		{id:ZaServer.A_showVolumes, ref:ZaServer.A_showVolumes, type: _ENUM_, choices: [false,true]},
		{id:ZaServer.A2_volume_selection_cache, ref:ZaServer.A2_volume_selection_cache, type:_LIST_},
		{id:ZaServer.A_CurrentIndexVolumeId, ref:ZaServer.A_CurrentIndexVolumeId, type:_NUMBER_},
		{id:ZaServer.A_CurrentMsgVolumeId, ref:ZaServer.A_CurrentMsgVolumeId, type:_NUMBER_},
		//VAMI update
		{id:ZaServer.A_zmailApplianceVendor, ref:"attrs/" +  ZaServer.A_zmailApplianceVendor, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceName, ref:"attrs/" +  ZaServer.A_zmailApplianceName, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceVersion, ref:"attrs/" +  ZaServer.A_zmailApplianceVersion, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceFullVersion, ref:"attrs/" +  ZaServer.A_zmailApplianceFullVersion, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceDetails, ref:"attrs/" +  ZaServer.A_zmailApplianceDetails, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceDefaultRepUrl, ref:"attrs/" +  ZaServer.A_zmailApplianceDefaultRepUrl, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceCustomRepUrl, ref:"attrs/" +  ZaServer.A_zmailApplianceCustomRepUrl, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceCustomRepUrlUser, ref:"attrs/" +  ZaServer.A_zmailApplianceCustomRepUrlUser, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceCustomRepUrlPass, ref:"attrs/" +  ZaServer.A_zmailApplianceCustomRepUrlPass, type:_STRING_, maxLength: 256 },
		{id:ZaServer.A_zmailApplianceUpdateOption, ref:"attrs/" +  ZaServer.A_zmailApplianceUpdateOption, type:_LIST_},
		{id:ZaServer.A_zmailApplianceUpdateType, ref:"attrs/" +  ZaServer.A_zmailApplianceUpdateType, type:_LIST_},
		{id:ZaServer.A_zmailApplianceUpdatehourofrun, ref:"attrs/" +  ZaServer.A_zmailApplianceUpdatehourofrun, type:_LIST_}
    ]
};
		
ZaServer.prototype.toString = function() {
	return this.name;
}

ZaServer.getServerByName = 
function(serverName) {
	if(!serverName)
		return null;
	var server = ZaServer.staticServerByNameCacheTable[serverName];
	if(!server) {
		domain = new ZaServer();
		try {
			server.load("name", serverName, false, true);
		} catch (ex) {
            throw (ex);
        }

		ZaServer.putServeToCache(server);
	} 
	return server;	
} 

ZaServer.getServerById = 
function (serverId) {
	if(!serverId)
		return null;
		
	var server = ZaServer.staticServerByIdCacheTable[serverId];
	if(!server) {
		server = new ZaServer();
		try {
			server.load("id", serverId, false, true);
		} catch (ex) {
			throw (ex);
		}
		ZaServer.putServeToCache(server);
	}
	return server;
}

ZaServer.getAllMBSs =
function(attrs) {
	var soapDoc = AjxSoapDoc.create("GetAllServersRequest", ZaZmailAdmin.URN, null);	
	soapDoc.getMethod().setAttribute("service", "mailbox");
	soapDoc.getMethod().setAttribute("applyConfig", "false");
	var params = new Object();
	params.soapDoc = soapDoc;
	params.asyncMode=false;
	if(attrs) {
		soapDoc.setMethodAttribute("attrs", attrs.join(","));
	}	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_ALL_SERVER
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.GetAllServersResponse;	
	var list = new ZaItemList(ZaServer);
	list.loadFromJS(resp);	
	return list;
}

ZaServer.getAll =
function(attrs) {
	var soapDoc = AjxSoapDoc.create("GetAllServersRequest", ZaZmailAdmin.URN, null);	
	soapDoc.getMethod().setAttribute("applyConfig", "false");
//	var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;
	params.asyncMode=false;
	if(attrs) {
		soapDoc.setMethodAttribute("attrs", attrs.join(","));
	}	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_ALL_SERVER
	}
	var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.GetAllServersResponse;	
	var list = new ZaItemList(ZaServer);
	list.loadFromJS(resp);	
	return list;
}

ZaServer.modifyMethod = function (tmpObj) {
	if(tmpObj.attrs == null) {
		//show error msg
		//ZaApp.getInstance().getCurrentController()._errorDialog.setMessage(ZaMsg.ERROR_UNKNOWN, null, DwtMessageDialog.CRITICAL_STYLE, null);
		//ZaApp.getInstance().getCurrentController()._errorDialog.popup();		
		return;	
	}
	
	if(ZaItem.hasWritePermission(ZaServer.A_zmailServiceEnabled,tmpObj)) {
		// update zmailServiceEnabled
		var svcInstalled = AjxUtil.isString(tmpObj.attrs[ZaServer.A_zmailServiceInstalled])
								? [ tmpObj.attrs[ZaServer.A_zmailServiceInstalled] ]
								: tmpObj.attrs[ZaServer.A_zmailServiceInstalled];
		if (svcInstalled) {
			// get list of actually enabled fields
			var enabled = [];
			for (var i = 0; i < svcInstalled.length; i++) {
				var service = svcInstalled[i];
				if (service == "vmware-ha") {
					if (tmpObj.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+"vmwareha"]) 
						enabled.push("vmware-ha");
					continue;
				}
				if (tmpObj.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+service]) {
					enabled.push(service);
				}			
			}
			
			// see if list of actually enabled fields is same as before
			
			var dirty = false; 
			
			if (this.attrs[ZaServer.A_zmailServiceEnabled]) {
				var prevEnabled = AjxUtil.isString(this.attrs[ZaServer.A_zmailServiceEnabled])
								? [ this.attrs[ZaServer.A_zmailServiceEnabled] ]
								: this.attrs[ZaServer.A_zmailServiceEnabled];
								
				dirty = (enabled.length != prevEnabled.length);		
				
				if (!dirty) {
					for (var i = 0; i < prevEnabled.length; i++) {
						var service = prevEnabled[i];
						if (service == "vmware-ha") {
							if(!tmpObj.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+"vmware"]){
								dirty = true;
								break;
							} else {
								continue;
							}

						}
						if (!tmpObj.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+service]) {
							dirty = true;
							break;
						}
					}
				}
			}
			
			// save new list of enabled fields
			if (dirty) {
				tmpObj.attrs[ZaServer.A_zmailServiceEnabled] = enabled;
			}
		}	
	}
	//modify volumes
	if(this.attrs[ZaServer.A_zmailMailboxServiceEnabled] && ZaItem.hasRight(ZaServer.MANAGE_VOLUME_RIGHT,this)) {
		//remove Volumes
		if(tmpObj[ZaServer.A_RemovedVolumes]) {
			var cnt = tmpObj[ZaServer.A_RemovedVolumes].length;
			for(var i = 0; i < cnt; i++) {
				if(tmpObj[ZaServer.A_RemovedVolumes][i][ZaServer.A_VolumeId] > 0) {
					this.deleteVolume(tmpObj[ZaServer.A_RemovedVolumes][i][ZaServer.A_VolumeId]);			
				}
			}
		}
	
		if(tmpObj[ZaServer.A_Volumes]) {			
			var tmpVolumeMap = new Array();
			var cnt = tmpObj[ZaServer.A_Volumes].length;
			for(var i = 0; i < cnt; i++) {
				tmpVolumeMap.push(tmpObj[ZaServer.A_Volumes][i]);
			}
		
			//create new Volumes
			cnt = tmpVolumeMap.length;
			for(var i = 0; i < cnt; i++) {
				//consider only new rows (no VolumeID)
				//ignore empty rows, Bug 4425
				if(!(tmpVolumeMap[i][ZaServer.A_VolumeId]>0) && tmpVolumeMap[i][ZaServer.A_VolumeName] && tmpVolumeMap[i][ZaServer.A_VolumeRootPath]) {
					var newId = this.createVolume(tmpVolumeMap[i]);	
					if(newId>0) {
						//find if we assigned this volume to current volumes
						for(var key in ZaServer.currentkeys) {
							if(tmpObj[ZaServer.currentkeys[key]] == tmpVolumeMap[i][ZaServer.A_VolumeId]) {
								tmpObj[ZaServer.currentkeys[key]] = newId;
							}
						}
					}		
				}
			}
	
			//modify existing volumes
			cnt--;	
			var cnt2 = this[ZaServer.A_Volumes].length;
			for(var i = cnt; i >= 0; i--) {
				var newVolume = tmpVolumeMap[i];
				var oldVolume;
				for (var ix =0; ix < cnt2; ix++) {
					oldVolume = this[ZaServer.A_Volumes][ix];
					if(oldVolume[ZaServer.A_VolumeId] == newVolume[ZaServer.A_VolumeId]) {
						//check attributes
						var modified = false;
						for(var attr in oldVolume) {
							if(oldVolume[attr] != newVolume[attr]) {
								modified = true;
								break;
							}
						}
						
						if(modified) {
							this.modifyVolume(tmpVolumeMap[i]);
						}
						tmpVolumeMap.splice(i,1);
					}
				}
			}
		}

		//set current volumes
		for(var key in ZaServer.currentkeys) {
			if(tmpObj[ZaServer.currentkeys[key]] && (!this[ZaServer.currentkeys[key]] || (this[ZaServer.currentkeys[key]] !=tmpObj[ZaServer.currentkeys[key]]))) {
				this.setCurrentVolume(tmpObj[ZaServer.currentkeys[key]], key);
			}
			
		}
	}	
	
	var hasSomething = false;	
	//create a ModifyServerRequest SOAP request
	var soapDoc = AjxSoapDoc.create("ModifyServerRequest", ZaZmailAdmin.URN, null);
	soapDoc.set("id", this.id);
	//get the list of changed fields
	var mods = new Object();
	for (var a in tmpObj.attrs) {
		if(a == ZaItem.A_objectClass || /^_/.test(a) || a == ZaServer.A_zmailServiceInstalled
                || a == ZaItem.A_zmailACE)
			continue;
		
		if(!ZaItem.hasWritePermission(a,this)) {
			continue;
		}
		
		hasSomething = true;
		if (this.attrs[a] != tmpObj.attrs[a] ) {
			if(tmpObj.attrs[a] instanceof Array) {
				if (!this.attrs[a]) {
					this.attrs[a] = [];
				}

				if (! this.attrs[a] instanceof Array) {
					this.attrs[a] = [this.attrs[a]];
				}

				if (tmpObj.attrs[a].join(",").valueOf() !=  this.attrs[a].join(",").valueOf()) {
					var array = tmpObj.attrs[a];
					if (array.length > 0) {
						for (var i = 0; i < array.length; i++) {
							var attr = soapDoc.set("a", array[i]);
							attr.setAttribute("n", a);
						}
					} else {
						var attr = soapDoc.set("a");
						attr.setAttribute("n", a);
					}
				}	
			} else {
				var attr = soapDoc.set("a", tmpObj.attrs[a]);
				attr.setAttribute("n", a);
			}
		}
	}
	if(hasSomething) {
		//modify the server
		var params = new Object();
		params.soapDoc = soapDoc;	
		var reqMgrParams = {
			controller : ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_MODIFY_SERVER
		}
		var resp = ZaRequestMgr.invoke(params, reqMgrParams).Body.ModifyServerResponse;		
		this.initFromJS(resp.server[0]);		
	}
}
ZaItem.modifyMethods["ZaServer"].push(ZaServer.modifyMethod);


/**
* Returns HTML for a tool tip for this domain.
*/
ZaServer.prototype.getToolTip =
function() {
	// update/null if modified
	if (!this._toolTip) {
		var html = new Array(20);
		var idx = 0;
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0'>";
		html[idx++] = "<tr valign='center'><td colspan='2' align='left'>";
		html[idx++] = "<div style='border-bottom: 1px solid black; white-space:nowrap; overflow:hidden;width:350'>";
		html[idx++] = "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;'>";
		html[idx++] = "<tr valign='center'>";
		html[idx++] = "<td><b>" + AjxStringUtil.htmlEncode(this.name) + "</b></td>";
		html[idx++] = "<td align='right'>";
		html[idx++] = AjxImg.getImageHtml("Server");		
		html[idx++] = "</td>";
		html[idx++] = "</table></div></td></tr>";
		html[idx++] = "<tr></tr>";
		idx = this._addAttrRow(ZaItem.A_description, html, idx);	
		idx = this._addAttrRow(ZaItem.A_zmailId, html, idx);
		html[idx++] = "</table>";
		this._toolTip = html.join("");
	}
	return this._toolTip;
}

ZaServer.loadMethod = 
function(by, val) {
	var _by = by ? by : "id";
	var _val = val ? val : this.id
	var soapDoc = AjxSoapDoc.create("GetServerRequest", ZaZmailAdmin.URN, null);
	var elBy = soapDoc.set("server", _val);
	elBy.setAttribute("by", _by);
	soapDoc.setMethodAttribute("applyConfig", "false");
	if(!this.getAttrs.all && !AjxUtil.isEmpty(this.attrsToGet)) {
		soapDoc.setMethodAttribute("attrs", this.attrsToGet.join(","));
	}	
	
	//var command = new ZmCsfeCommand();
	var params = new Object();
	params.soapDoc = soapDoc;	
	params.asyncMode = false;
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_GET_SERVER
	}
	resp = ZaRequestMgr.invoke(params, reqMgrParams);		
	this.initFromJS(resp.Body.GetServerResponse.server[0]);
	
	//this._defaultValues = ZaApp.getInstance().getGlobalConfig();
	soapDoc = AjxSoapDoc.create("BatchRequest", "urn:zmail");
	soapDoc.setMethodAttribute("onerror", "continue");	
	
	if(this.attrs[ZaServer.A_zmailMailboxServiceEnabled] && ZaItem.hasRight(ZaServer.MANAGE_VOLUME_RIGHT,this)) {
		var getAllVols = soapDoc.set("GetAllVolumesRequest", null, null, ZaZmailAdmin.URN);
		var getCurrentVols = soapDoc.set("GetCurrentVolumesRequest", null, null, ZaZmailAdmin.URN);
	}				
	var getAllVols = soapDoc.set("GetServerNIfsRequest", null, null, ZaZmailAdmin.URN);
	var server = soapDoc.set("server", _val, getAllVols);
	server.setAttribute("by", _by);
	try {
		params = new Object();
		params.soapDoc = soapDoc;	
		params.asyncMode = false;
		if(this.attrs && this.attrs[ZaServer.A_zmailMailboxServiceInstalled] && this.attrs[ZaServer.A_zmailMailboxServiceEnabled]) {
			params.targetServer = this.id;
		}
		var reqMgrParams = {
			controller : ZaApp.getInstance().getCurrentController(),
			busyMsg : ZaMsg.BUSY_GET_SERVER
		}
		
		var respObj = ZaRequestMgr.invoke(params, reqMgrParams);
		this[ZaServer.A_Volumes] = new Array();
		
		if(respObj.isException && respObj.isException()) {
			ZaApp.getInstance().getCurrentController()._handleException(respObj.getException(), "ZaServer.loadMethod", null, false);
		} 
		if (respObj.Body.BatchResponse) {
			if(respObj.Body.BatchResponse.Fault) {
				var fault = respObj.Body.BatchResponse.Fault;
				if(fault instanceof Array)
					fault = fault[0];
			
				if (fault) {
					// JS response with fault
					var ex = ZmCsfeCommand.faultToEx(fault);
					ZaApp.getInstance().getCurrentController()._handleException(ex,"ZaServer.loadMethod", null, false);
				}
			} 
		
			var batchResp = respObj.Body.BatchResponse;
			if(batchResp.GetAllVolumesResponse) {
				resp = batchResp.GetAllVolumesResponse[0];
				this.parseMyVolumes(resp);
			}
				
			if(batchResp.GetCurrentVolumesResponse) {
				resp = batchResp.GetCurrentVolumesResponse[0];
				this.parseCurrentVolumesResponse(resp);
			}
				
			if(batchResp.GetServerNIfsResponse) {
				resp = batchResp.GetServerNIfsResponse[0];
				this.parseNIFsResponse(resp);
			}
		}
	} catch (ex) {
		//show the error and go on
		ZaApp.getInstance().getCurrentController()._handleException(ex, "ZaServer.loadMethod", null, false);
	}		
}

ZaItem.loadMethods["ZaServer"].push(ZaServer.loadMethod);

ZaServer.prototype.parseNIFsResponse = 
function(resp) {
	if(resp && resp.ni) {
		var NIs = resp.ni;
		var cnt = NIs.length;
		this.nifs = [];
		for(var i=0;i<cnt;i++) {
			var ni = {};
			ZaItem.prototype.initFromJS.call(ni, NIs[i]);
			this.nifs.push(ni);
		}
	}
}
//ZaItem.loadMethods["ZaServer"].push(ZaServer.loadNIFS);

ZaServer.prototype.initFromJS = function(server) {
	ZaItem.prototype.initFromJS.call(this, server);
	// convert installed/enabled services to hidden fields for xform binding
	var installed = this.attrs[ZaServer.A_zmailServiceInstalled];
	if (installed) {
		if (AjxUtil.isString(installed)) {
			installed = [ installed ];
		}
		for (var i = 0; i < installed.length; i++) {
			var service = installed[i];
			if(service == "vmware-ha"){
				this.attrs["_"+ZaServer.A_zmailServiceInstalled+"_"+"vmwareha"] = true;
                        	this.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+"vmwareha"] = false;
				continue;
			}
			this.attrs["_"+ZaServer.A_zmailServiceInstalled+"_"+service] = true;
			this.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+service] = false;
		}
	}
	
	var enabled = this.attrs[ZaServer.A_zmailServiceEnabled];
	if (enabled) {
		if (AjxUtil.isString(enabled)) {
			enabled = [ enabled ];
		}
		for (var i = 0; i < enabled.length; i++) {
			var service = enabled[i];
			if (service == "vmware-ha") {
				this.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+"vmwareha"] = true;
				continue;
			}

			this.attrs["_"+ZaServer.A_zmailServiceEnabled+"_"+service] = true;
		}
	}
	this[ZaServer.A_ServiceHostname] = this.attrs[ZaServer.A_ServiceHostname]; // a hack for New Account Wizard	
	this[ZaServer.A_showVolumes] = this.attrs[ZaServer.A_zmailMailboxServiceEnabled];
	if(this.attrs[ZaServer.A_zmailSmtpHostname] && !(this.attrs[ZaServer.A_zmailSmtpHostname] instanceof Array)) {
		this.attrs[ZaServer.A_zmailSmtpHostname] = [this.attrs[ZaServer.A_zmailSmtpHostname]];
	}
	
	if(this._defaultValues && this._defaultValues.attrs[ZaServer.A_zmailSmtpHostname] && !(this._defaultValues.attrs[ZaServer.A_zmailSmtpHostname]  instanceof Array)) {
		this._defaultValues.attrs[ZaServer.A_zmailSmtpHostname]  = [this._defaultValues.attrs[ZaServer.A_zmailSmtpHostname]];
	}
	
	if(this.attrs[ZaServer.A_zmailMilterBindAddress] && !(this.attrs[ZaServer.A_zmailMilterBindAddress] instanceof Array)) {
                this.attrs[ZaServer.A_zmailMilterBindAddress] = [this.attrs[ZaServer.A_zmailMilterBindAddress]];
        }

        if(this._defaultValues && this._defaultValues.attrs[ZaServer.A_zmailMilterBindAddress] && !(this._defaultValues.attrs[ZaServer.A_zmailMilterBindAddress]  instanceof Array)) {
                this._defaultValues.attrs[ZaServer.A_zmailMilterBindAddress]  = [this._defaultValues.attrs[ZaServer.A_zmailMilterBindAddress]];
        }

	if(this.attrs[ZaServer.A_zmailAutoProvScheduledDomains] && !(this.attrs[ZaServer.A_zmailAutoProvScheduledDomains] instanceof Array)) {
        this.attrs[ZaServer.A_zmailAutoProvScheduledDomains] = [this.attrs[ZaServer.A_zmailAutoProvScheduledDomains]];
    }

}

ZaServer.prototype.parseCurrentVolumesResponse =
function (resp) {
	var volumes = resp.volume;
	if(volumes) {
		var cnt = volumes.length;
		for (var i=0; i< cnt;  i++) {
			var volume = volumes[i];
			for(var key in ZaServer.currentkeys) {
				if(volume[ZaServer.A_VolumeType]==key) {
					this[ZaServer.currentkeys[key]] = volume[ZaServer.A_VolumeId];
				}
			}			
		}
	}
}

ZaServer.prototype.parseMyVolumes = 
function(resp) {
	var volumes = resp.volume;
	if(volumes) {
		var cnt = volumes.length;
		for (var i=0; i< cnt;  i++) {
			this[ZaServer.A_Volumes].push(volumes[i]);	
		}
	}
}

ZaServer.compareVolumesByName = function (a,b) {
	
	if(a[ZaServer.A_VolumeName]>b[ZaServer.A_VolumeName])
		return 1;
	if(a[ZaServer.A_VolumeName]<b[ZaServer.A_VolumeName])
		return -1;
	return 0;
	
}

ZaServer.prototype.loadVolumes = function (callback) {
	var soapDoc = AjxSoapDoc.create("GetAllVolumesRequest", ZaZmailAdmin.URN, null);
	var params = {
		soapDoc: soapDoc,
		targetServer: this.id,
		asyncMode: callback ? true : false,
		callback: callback ? callback : null		
	}
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_LOADING_VOL
	}
	ZaRequestMgr.invoke(params, reqMgrParams) ;
}

ZaServer.prototype.deleteVolume =
function (id, callback) {
	if(!id)
		return false;
		
	var soapDoc = AjxSoapDoc.create("DeleteVolumeRequest", ZaZmailAdmin.URN, null);		
	soapDoc.getMethod().setAttribute(ZaServer.A_VolumeId, id);	
	var params = {
		soapDoc: soapDoc,
		targetServer: this.id,
		asyncMode: callback ? true : false,
		callback: callback ? callback : null		
	}
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_DELETE_VOL
	}
	ZaRequestMgr.invoke(params, reqMgrParams) ;
}

ZaServer.prototype.createVolume =
function (volume) {
	if(!volume)
		return false;
	var soapDoc = AjxSoapDoc.create("CreateVolumeRequest", ZaZmailAdmin.URN, null);		
	var elVolume = soapDoc.set("volume", null);
	elVolume.setAttribute("type", volume[ZaServer.A_VolumeType]);
	elVolume.setAttribute("name", volume[ZaServer.A_VolumeName]);	
	elVolume.setAttribute("rootpath", volume[ZaServer.A_VolumeRootPath]);		
	elVolume.setAttribute("compressBlobs", volume[ZaServer.A_VolumeCompressBlobs]);		
	elVolume.setAttribute("compressionThreshold", volume[ZaServer.A_VolumeCompressionThreshold]);			
	var params = {
		soapDoc: soapDoc,
		targetServer: this.id,
		asyncMode: false
	}
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_CREATE_VOL
	}
	var response = ZaRequestMgr.invoke(params, reqMgrParams) ;
	if(response.Body && response.Body.CreateVolumeResponse && response.Body.CreateVolumeResponse.volume) {
		return response.Body.CreateVolumeResponse.volume[0][ZaServer.A_VolumeId];
	}
	
}

ZaServer.prototype.modifyVolume =
function (volume) {
	if(!volume)
		return false;
	var soapDoc = AjxSoapDoc.create("ModifyVolumeRequest", ZaZmailAdmin.URN, null);		
	soapDoc.getMethod().setAttribute(ZaServer.A_VolumeId, volume[ZaServer.A_VolumeId]);	
	var elVolume = soapDoc.set("volume", null);
	elVolume.setAttribute("type", volume[ZaServer.A_VolumeType]);
	elVolume.setAttribute("name", volume[ZaServer.A_VolumeName]);	
	elVolume.setAttribute("rootpath", volume[ZaServer.A_VolumeRootPath]);		
	elVolume.setAttribute("compressBlobs", volume[ZaServer.A_VolumeCompressBlobs]);		
	elVolume.setAttribute("compressionThreshold", volume[ZaServer.A_VolumeCompressionThreshold]);			
	var params = {
		soapDoc: soapDoc,
		targetServer: this.id,
		asyncMode: false
	}
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_MODIFY_VOL
	}
	ZaRequestMgr.invoke(params, reqMgrParams) ;
}

ZaServer.prototype.setCurrentVolume = function (id, type) {
	if(!id || !type)
		return false;	
	var soapDoc = AjxSoapDoc.create("SetCurrentVolumeRequest", ZaZmailAdmin.URN, null);		
	soapDoc.getMethod().setAttribute(ZaServer.A_VolumeType, type);		
	soapDoc.getMethod().setAttribute(ZaServer.A_VolumeId, id);	
	var params = {
		soapDoc: soapDoc,
		targetServer: this.id,
		asyncMode: false
	}
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : ZaMsg.BUSY_SET_VOL
	}
	ZaRequestMgr.invoke(params, reqMgrParams) ;
}

ZaServer.initMethod = function () {
	this.attrs = new Object();
	this.id = "";
	this.name="";
}
ZaItem.initMethods["ZaServer"].push(ZaServer.initMethod);

ZaServer.flushCache = function (params) {
	var soapDoc = AjxSoapDoc.create("FlushCacheRequest", ZaZmailAdmin.URN, null);
	var elCache = soapDoc.set("cache", null);
	
	var type = [];
	if(params.flushSkin)
		type.push("skin")
	if(params.flushLocale)	
		type.push("locale");
	if(params.flushZimlet)	
		type.push("zimlet");
		
	elCache.setAttribute("type", type.join(","));		
	
	var reqMgrParams = {
		controller : ZaApp.getInstance().getCurrentController(),
		busyMsg : params.busyMsg ? params.busyMsg : ZaMsg.BUSY_FLUSH_CACHE,
		busyId:params.busyId
	}
	
	var reqParams = {
		soapDoc: soapDoc,
		targetServer: params.serverId ? params.serverId : params.serverList[params.ix].attrs[ZaItem.A_zmailId],
		asyncMode: params.callback ? true : false,
		callback: params.callback ? params.callback : null
	}
	ZaRequestMgr.invoke(reqParams, reqMgrParams) ;
}
