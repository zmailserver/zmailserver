# 
# ***** BEGIN LICENSE BLOCK *****
# Zimbra Collaboration Suite Server
# Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package Soap;

use strict;
use warnings;

use XML::Parser;

use LWP::UserAgent;
use XmlElement;
use XmlDoc;
use Soap12;
use Soap11;

#
# If you're using ActivePerl, you'll need to go and install the Crypt::SSLeay
# module for https:// to work...
#
#         ppm install http://theoryx5.uwinnipeg.ca/ppms/Crypt-SSLeay.ppd
#
#
 
BEGIN {
    use Exporter   ();
    our ($VERSION, @ISA, @EXPORT, @EXPORT_OK, %EXPORT_TAGS);

    # set the version for version checking
    $VERSION     = 1.00;
    @ISA         = qw(Exporter);
    @EXPORT      = qw();
    %EXPORT_TAGS = ( );     # eg: TAG => [ qw!name1 name2! ],

    # your exported package globals go here,
    # as well as any optionally exported functions
    @EXPORT_OK   = qw();
}

our @EXPORT_OK;

our $Soap12 = new Soap12;
our $Soap11 = new Soap11;

our $LogRequest = 0;
our $LogResponse = 0;

our $ZIMBRA_ACCT_NS = "urn:zmailAccount";
our $ZIMBRA_MAIL_NS = "urn:zmailMail";
our $ZIMBRA_ADMIN_NS = "urn:zmailAdmin";
our $ZIMBRA_IM_NS = "urn:zmailIM";

sub setLogLevel {
    shift(); 
    $LogRequest = shift();
    $LogResponse = shift();
}

#
# given a XmlElement, wrap it in a SOAP envelope and return the envelope
#

sub soapEnvelope {
    die "must override";
}

#
# Given a RemoteException, wrap it in a soap fault return the 
# soap fault document.
#
#public abstract XmlObject soapFault(java.rmi.RemoteException re);

#
# Given an XmlObject that represents a fault (i.e,. isFault returns 
# true on it), construct a SuddsFaultException from it.
#
#public abstract SuddsFaultException suddsFault(XmlObject doc);

#
# Return Content-Type header
#

sub getContentType() {
    die "must override";
}

#
# Return the namespace String
#

sub getNamespace {
    die "must override";
}

#
# Return charset encoding for converting from bytes/strings
#

sub getCharSet {
    return "UTF-8";
}

#
# Convert a SOAP message in a String to bytes
#

sub convertToBytes {
    die "not implemented yet";
}

#
# Convert a SOAP message in bytes to a String 
#

sub convertToString {
    die "not implemented yet";
}

#
# return the first child in the soap body
#

sub getElement {
    die "must override";
}

#
# Returns true if this element represents a SOAP fault
#

sub isFault {
    die "must override";
}

#
# Returns true if this soap envelope has a SOAP fault as the
# first child of its body.     
#

sub hasFault {
    my ($self, $e) = @_;
    return $self->isFault($e->child(0));
}

#
# determine if given element is Soap11 or Soap12 envelope,
# and returns the Soap11 or Soap12 instance, or undef if neither.
#

sub determineProtocol {
    my $e = shift;
    return undef unless $e->name() eq "Envelope";
    return $Soap12 if ($e->ns() eq $Soap12->getNamespace());
    return $Soap11 if ($e->ns() eq $Soap11->getNamespace());
    return undef;
}

#
# Whether or not to include a HTTP SOAPActionHeader. (Gag)
#

sub hasSOAPActionHeader {
    die "must override";
}

#
# returns the version as a string (e.g, "1.1" or "1.2")
#

sub getVersion {
    die "must override";
}

#
sub toString {
    my $self = shift;
    return "SOAP ".$self->getVersion();
}

#
# Return the URL for the standard mail service  
#    e.g.  getAdminUrl("http://localhost");
#
sub getMailUrl
{
    my ($self, $host) = @_;

    return $host."/service/soap";
}

#
# Return the URL for the admin service
#    e.g.  getAdminUrl("https://localhost:7071");
#
sub getAdminUrl
{
    my ($self, $host) = @_;

    return $host."/service/admin/soap";
}

#    
# returns a ZmailContext
#
sub stdAuthByName {
  my ($self, $url, $acctName, $passwd, $opts) = @_;
    return $self->doAuthByName($url, $ZIMBRA_ACCT_NS, $acctName, $passwd, $opts);
}
    

#    
# returns a ZmailContext
#
sub adminAuthByName {
  my ($self, $url, $acctName, $passwd, $opts) = @_;
  return $self->doAuthByName($url, $ZIMBRA_ADMIN_NS, $acctName, $passwd, $opts);
}

#    
# You probably want stdAuthByName() or adminAuthByName()
# 
sub doAuthByName {
  my ($self, $url, $namespace, $acctName, $passwd, $opts) = @_;

  my $sessionId;
  if (defined($opts) && defined($opts->{SESSIONID})) {
    $sessionId = $opts->{SESSIONID};
  }

  my $ctxt = new XmlElement("context", "urn:zmail");
  if (!defined($opts) || !defined($opts->{NOTIFY})) {
    $ctxt->add_child(new XmlElement("nosession"));
  } else {
    $ctxt->add_child(new XmlElement("session"));
  }

  my $d = new XmlDoc;
  
  $d->start('AuthRequest', $namespace);
  {
    $d->add('account', undef, { by => "name"}, $acctName);
#    $d->add('name', undef, undef, $acctName);
    $d->add('password', undef, undef, $passwd);
    
  } $d->end();

  my $authResponse = $self->invoke($url, $d->root(), $ctxt);

  # get auth token
  my $elt = $authResponse->find_child('authToken');
  if (!defined($elt)) { 
    print "AuthRequest: ".$d->to_string("pretty")."\n";
    print "AuthResponse: ".$authResponse->to_string("pretty")."\n";
    die "Could not find AuthToken in AuthResponse";
  }
  my $authToken = $elt->content;

  if (!defined($sessionId)) {
    # 6.x servers
    $elt = $authResponse->find_child('session');
    if (!defined($elt)) {
      # 5.x servers
      $elt = $authResponse->find_child('sessionId');
#      if (!defined($elt)) {
#        print "Unable to locate session ID\n";
        #      print "AuthRequest: ".$d->to_string("pretty")."\n";
        #      print "AuthResponse: ".$authResponse->to_string("pretty")."\n";
        #    die "Could not find sessionId in AuthToken";
#      }
    }
    if (defined($elt)) {
      $sessionId = $elt->content;
    }
  }
  
  my $wantcontext;
  if (defined($opts) && defined($opts->{NOTIFY})) {
    $wantcontext = 1;
  }
  
  return $self->zmailContext($authToken, $sessionId, $wantcontext, $opts);
}

sub zmailContext {
	my ($self, $authtoken, $sessionId, $wantcontext, $opts) = @_;

#    if (defined($sessionId)) {
#      print "SESSIONID is $sessionId\n";
#    } else {
#      print "No session ID\n";
#    }

    my $notSeq = '0';
    if (defined($opts) && defined($opts->{NOTSEQ})) {
      $notSeq = $opts->{NOTSEQ};
    }
    
	my $context = new XmlElement("context", "urn:zmail");
	my $auth = new XmlElement("authToken");
	$auth->content($authtoken);
	$context->add_child($auth);
    if (defined($sessionId) && $sessionId ne "") {
      # SESSION elt (6.x servers)
      my $sessionElt = new XmlElement("session");
      if (defined ($wantcontext) && $wantcontext) {
        $sessionElt->attrs({'notify' => '1' });
      }
      $sessionElt->attrs({'id' => $sessionId});
      $context->add_child($sessionElt);

      # SESSIONID elt (5.x servers)
      my $sessionIdElt = new XmlElement("sessionId");
      if (defined ($wantcontext) && $wantcontext) {
        $sessionIdElt->attrs({'notify' => '1' });
      }
      $sessionIdElt->attrs({'id' => $sessionId});
      $context->add_child($sessionIdElt);
      
      
    }
	if (! defined ($wantcontext) ) {
		my $want = new XmlElement("nosession");
		$want->content("");
		$context->add_child($want);
	} else {
#      print "****************notSeq = $notSeq***************************\n\n";
#      my $session = new XmlElement("session");
#      $context->add_child($session);
      my $want = new XmlElement("notify");
      $want->attrs({ 'seq' => $notSeq });
      $context->add_child($want);
    }
	return $context;		
}

sub invoke {
    my ($self, $uri, $doc, $context, $options) = @_;

    my ($toRet, $err, $req, $res);

    my $reqOK = 0;

    my $env = $self->soapEnvelope($doc, $context);
    my $soap = $env->to_string();
    my $ua;
    if (defined($options->{TIMEOUT})) {
      $ua = new LWP::UserAgent(timeout => $options->{TIMEOUT});
    } else {
      $ua = new LWP::UserAgent();
    }
    $req = new HTTP::Request(POST=> $uri);
    $req->content_type($self->getContentType());
    $req->content_length(length($soap));
    if ($self->hasSOAPActionHeader()) {
        $req->header("SOAPAction" => $uri);
    }
    $req->add_content($soap);

    if ($LogRequest > 0) {
      print "\nREQUEST: \n\t".$req->content."\n";
    }
    
    eval {
        $res = $ua->request($req);
        
        $reqOK = 1;

        my $xml = XmlElement::parse($res->content);

        $reqOK = 2;
        
        my $rsoap = Soap::determineProtocol($xml);
        
        die "unable to determine soap protocol" unless defined $rsoap;
        die "unexpected soap version in response" unless $rsoap == $self;

        if (defined($options->{FULLRESPONSE})) {
          $toRet = $xml;
        } else {
          $toRet = $self->getElement($xml);
        }
    };

    $err = $@;

    if ( ($err && ($reqOK == 0))  &&  (!($LogRequest > 0))) {
      print "\nREQUEST: \n\t".$req->content."\n";
    }
    
    if ( ($err && ($reqOK == 2))  || ($LogResponse  > 0)) {
        print "\nRESPONSE: \n\t".$res->content."\n";
    }

    if ($err) {
      print "\nERROR RESPONSE: \n\t".$res->content."\n";
      if ($reqOK == 0) {
          $err = "\nError Handling Soap Request".$err;
        } else {
          $err = "\nError Handling Soap Response".$err;
        }
        die $err;
    }
    return $toRet;
}

1;
