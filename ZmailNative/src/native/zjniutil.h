/* -*- Mode: c; c-basic-offset: 4 -*- */
/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2007, 2009, 2010, 2012 VMware, Inc.
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

#ifndef _ZIMBRA_NATIVE_UTIL_H
#define _ZIMBRA_NATIVE_UTIL_H

#ifdef __cplusplus
extern "C" {
#endif

void
ZmailThrowNPE(JNIEnv *env, const char *msg);

void
ZmailThrowIAE(JNIEnv *env, const char *msg);

void
ZmailThrowIOE(JNIEnv *env, const char *msg);

void
ZmailThrowFNFE(JNIEnv *env, const char *msg);

void
ZmailThrowOFE(JNIEnv *env, const char *msg);

#ifdef __cplusplus
}
#endif
#endif
