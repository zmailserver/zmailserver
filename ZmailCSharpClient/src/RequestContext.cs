/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2006, 2007, 2009, 2010, 2012 VMware, Inc.
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
using System;
using System.Xml;

namespace Zmail.Client
{
	public class RequestContext
	{
		private String				authToken				= null;
		private String				sessionId				= null;
		private AccountFormat		targetAccountFormat		= AccountFormat.ByName;
		private String				targetAccount			= null;
		private Boolean				noNotifications			= false;
		private Boolean				noSession				= false;
		private String				targetServer			= null;
		private String				changeToken				= null;
		private RaceConditionType	raceType				= RaceConditionType.Modify;
		private String				notificationSequence	= null;

		public RequestContext()
		{}

		public String AuthToken
		{
			get{ return authToken; }
			set{ authToken = value; }
		}

		public String SessionId
		{
			get{ return sessionId; }
			set{ sessionId = value; }
		}

		public AccountFormat TargetAccountType
		{
			get{ return targetAccountFormat; }
			set{ targetAccountFormat = value; }
		}

		public String TargetAccount
		{
			get{ return targetAccount; }
			set{ targetAccount = value; }
		}

		public Boolean NoNotifications
		{
			get{ return noNotifications; }
			set{ noNotifications = value; }
		}

		public String NotificationSequence
		{
			get{ return notificationSequence;}
			set{ notificationSequence = value; }
		}

		public Boolean NoSession
		{
			get{ return noSession; }
			set{ noSession = value; }
		}

		public String TargetServer
		{
			get{ return targetServer; }
			set{ targetServer = value; }
		}

		public String ChangeToken
		{
			get{ return changeToken; }
			set{ changeToken = value; }
		}

		public RaceConditionType RaceType
		{
			get{ return raceType; }
			set{ raceType = value; }
		}

		//update this request context based on the response context
		public void Update( ResponseContext rc )
		{
			if( rc.SessionId != null )
				this.sessionId = rc.SessionId;

			if( rc.Notifications != null && rc.Notifications.SequenceToken != null )
				this.NotificationSequence = rc.Notifications.SequenceToken;
		}

		//update this request context based on the response context and auth response
		public void Update( ResponseContext rc, Zmail.Client.Account.AuthResponse ar )
		{
			this.AuthToken = ar.AuthToken;
			Update( rc );
		}

		public void Update( ResponseContext rc, Zmail.Client.Admin.AuthResponse ar )
		{
			this.AuthToken = ar.AuthToken;
			Update(rc);
		}


		public XmlDocument ToXmlDocument()
		{
			XmlDocument d = new XmlDocument();

			XmlElement contextElement = d.CreateElement( ZmailService.E_CONTEXT, ZmailService.NAMESPACE_URI );

			if( authToken != null )
			{
				XmlElement ate = d.CreateElement( ZmailService.E_AUTHTOKEN, ZmailService.NAMESPACE_URI );
				ate.InnerText = authToken;
				contextElement.AppendChild( ate );
			}

			if( sessionId != null )
			{
				XmlElement se = d.CreateElement( ZmailService.E_SESSION, ZmailService.NAMESPACE_URI );
				se.SetAttribute( ZmailService.A_ID, sessionId );
				contextElement.AppendChild( se ); 
			} else if( noSession == false)
			{
				contextElement.AppendChild( d.CreateElement( ZmailService.E_SESSION, ZmailService.NAMESPACE_URI ) );
			} 

			if( noNotifications )
			{
				contextElement.AppendChild( d.CreateElement( ZmailService.E_NONOTIFY, ZmailService.NAMESPACE_URI ) );
			}

			if( notificationSequence != null ) 
			{
				XmlElement e = d.CreateElement( ZmailService.E_NOTIFY, ZmailService.NAMESPACE_URI );
				e.SetAttribute( ZmailService.A_NOTIFY_SEQUENCE, notificationSequence );
				contextElement.AppendChild( e );
			}

			if( targetAccount != null )
			{
				XmlElement tae = d.CreateElement( ZmailService.E_ACCOUNT, ZmailService.NAMESPACE_URI );
				tae.SetAttribute( ZmailService.A_BY, targetAccountFormat.ToString() );
				tae.InnerText = targetAccount;
				contextElement.AppendChild( tae );
			}

			if( targetServer != null )
			{
				XmlElement tse = d.CreateElement( ZmailService.E_TARGET_SERVER, ZmailService.NAMESPACE_URI );
				tse.InnerText = targetServer;
				contextElement.AppendChild( tse );
			}

			if( changeToken != null )
			{
				XmlElement cte = d.CreateElement( ZmailService.E_CHANGE, ZmailService.NAMESPACE_URI );
				cte.SetAttribute( ZmailService.A_TOKEN, changeToken );
				cte.SetAttribute( ZmailService.A_TYPE, raceType.ToString() );
			}

			if( contextElement.ChildNodes.Count > 0 )
				d.AppendChild( contextElement );
			return d;
		}
	}
}













