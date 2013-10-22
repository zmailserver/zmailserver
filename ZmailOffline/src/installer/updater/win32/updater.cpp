/* ***** BEGIN LICENSE BLOCK *****
/* Zimbra Collaboration Suite Server
/* Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
/* 
/* The contents of this file are subject to the Zimbra Public License
/* Version 1.3 ("License"); you may not use this file except in
/* compliance with the License.  You may obtain a copy of the License at
/* http://www.zimbra.com/license.
/* 
/* Software distributed under the License is distributed on an "AS IS"
/* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK ***** */

#if defined(XP_WIN)
# include <windows.h>
# include <direct.h>
# include <io.h>
# define F_OK 00
# define W_OK 02
# define R_OK 04
# define access _access
# define putenv _putenv
# define snprintf _snprintf
# define fchmod(a,b)
# define mkdir(path, perms) _mkdir(path)

# define NS_T(str) L ## str
# define NS_tfprintf fwprintf
# define NS_tsnprintf _snwprintf
# define NS_tstrrchr wcsrchr
# define NS_tchdir _wchdir
# define NS_tremove _wremove
# define NS_trename _wrename
# define NS_taccess _waccess
# define NS_topen _wopen
# define NS_tfopen _wfopen
# define NS_tatoi _wtoi64
# define NS_main wmain
typedef WCHAR NS_tchar;
#else
# include <sys/wait.h>
# include <unistd.h>

# define NS_T(str) str
# define NS_tfprintf fprintf
# define NS_tsnprintf snprintf
# define NS_tstrrchr strrchr
# define NS_tchdir chdir
# define NS_tremove remove
# define NS_trename rename
# define NS_taccess access
# define NS_topen open
# define NS_tfopen fopen
# define NS_tatoi atoi
# define NS_main main
typedef char NS_tchar;
#endif

// We use the NSPR types, but we don't link with NSPR
#include "prtypes.h"

#include "errors.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdarg.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <limits.h>
#include <errno.h>

#if defined(XP_MACOSX)
// This function is defined in launchchild_osx.mm
void LaunchChild(int argc, char **argv);
#endif

#ifndef _O_BINARY
# define _O_BINARY 0
#endif

#ifndef NULL
# define NULL (0)
#endif

#ifndef SSIZE_MAX
# define SSIZE_MAX LONG_MAX
#endif

#ifndef MAXPATHLEN
# ifdef MAX_PATH
#  define MAXPATHLEN MAX_PATH
# elif defined(_MAX_PATH)
#  define MAXPATHLEN _MAX_PATH
# elif defined(CCHMAXPATH)
#  define MAXPATHLEN CCHMAXPATH
# else
#  define MAXPATHLEN 1024
# endif
#endif

// We want to use execv to invoke the callback executable on platforms where
// we were launched using execv.  See nsUpdateDriver.cpp.
#if defined(XP_UNIX) && !defined(XP_MACOSX)
#define USE_EXECV
#endif

//-----------------------------------------------------------------------------
// LOGGING

static FILE *gLogFP = NULL;

static void LogInit(NS_tchar *path)
{
  if (gLogFP)
    return;

  NS_tchar logFile[MAXPATHLEN];
  NS_tsnprintf(logFile, MAXPATHLEN, NS_T("%s/update.log"), path);

  gLogFP = NS_tfopen(logFile, NS_T("w"));
}

static void LogFinish()
{
  if (!gLogFP)
    return;

  fclose(gLogFP);
  gLogFP = NULL;
}

static void LogPrintf(const char *fmt, ... )
{
  if (!gLogFP)
    return;

  va_list ap;
  va_start(ap, fmt);
  vfprintf(gLogFP, fmt, ap);
  va_end(ap);
}

#define LOG(args) LogPrintf args

//-----------------------------------------------------------------------------

#ifdef XP_WIN
#include "nsWindowsRestart.cpp"
#endif

static void
LaunchApp(const NS_tchar *workingDir, int argc, NS_tchar **argv)
{
  putenv("NO_EM_RESTART=");
  putenv("MOZ_LAUNCHED_CHILD=1");

  // Run from the specified working directory (see bug 312360).
  NS_tchdir(workingDir);

#if defined(USE_EXECV)
  execv(argv[0], argv);
#elif defined(XP_MACOSX)
  LaunchChild(argc, argv);
#elif defined(XP_WIN)
  WinLaunchChild(argv[0], argc, argv);
#else
# warning "Need implementaton of LaunchCallbackApp"
#endif
}

static void
WriteStatusFile(NS_tchar* path, int status)
{
  // This is how we communicate our completion status to the main application.

  NS_tchar filename[MAXPATHLEN];
  NS_tsnprintf(filename, MAXPATHLEN, NS_T("%s/update.status"), path);

  int fd = NS_topen(filename, O_WRONLY | O_TRUNC | O_CREAT | _O_BINARY, 0644);
  if (fd < 0)
    return;

  const char *text;

  char buf[32];
  if (status == OK) {
    text = "succeeded\n";
  } else {
    snprintf(buf, sizeof(buf), "failed: %d\n", status);
    text = buf;
  }
  write(fd, text, strlen(text));
  close(fd);
}

static int
WCharReplace(NS_tchar *wstr, NS_tchar oc, NS_tchar nc)
{
  int len = wcslen(wstr);
  int n = 0;
  for (int i = 0; i < len; i++) {
    if (wstr[i] == oc) {
      wstr[i] = nc;
      n++;	
    }
  }
  return n;
}

static void
DoUpdate(NS_tchar *path)
{
  NS_tchar spath[MAXPATHLEN];
  NS_tchar dpath[MAXPATHLEN];
  NS_tchar upstatus[MAXPATHLEN];
  NS_tchar upparams[MAXPATHLEN];
  NS_tchar sysdir[MAXPATHLEN];
  NS_tchar msiexec[MAXPATHLEN];
  NS_tsnprintf(spath, MAXPATHLEN, NS_T("%s/update.mar"), path);
  NS_tsnprintf(dpath, MAXPATHLEN, NS_T("%s/update.msi"), path);
  NS_tsnprintf(upstatus, MAXPATHLEN, NS_T("%s/update.status"), path);
  NS_tsnprintf(upparams, MAXPATHLEN, NS_T("%s/../../update.params"), path);
  GetSystemDirectory(sysdir, MAXPATHLEN);
  NS_tsnprintf(msiexec, MAXPATHLEN, NS_T("%s\\msiexec.exe"), sysdir);

  int rv = NS_taccess(spath, F_OK | R_OK | W_OK);
  if (rv != OK) {
  	LOG(("failed: can't access update.mar (rv=%d)\n", rv));
	NS_tremove(spath);
	NS_tremove(upstatus);
	return;
  }

  NS_tremove(dpath);
  rv = NS_trename(spath, dpath);
  if (rv != OK) {
  	LOG(("failed: can't rename update.mar (rv=%d)\n", rv));
	NS_tremove(spath);
	NS_tremove(upstatus);
	return;
  }
 
  WCharReplace(dpath, L'/', L'\\');
  int largc = 3;
  NS_tchar* largv[16]; //16 being max
  largv[0] = msiexec; //first arg is the path to msiexec.exe
  largv[1] = NS_T("/i"); //second arg is the install-flag
  largv[2] = dpath; //third arg is the path to update.msi
  NS_tchar argbuf[MAXPATHLEN];
  int start = 0;
  int end = 0;

  rv = NS_taccess(upparams, F_OK | R_OK);
  char buf[MAXPATHLEN];
  if (rv == OK) {
      int fd = NS_topen(upparams, O_RDONLY | O_BINARY);
      if (fd >= 0) {
          int num = read(fd, buf, MAXPATHLEN - 1);
	  if (num > 0) {
	      buf[num] = '\0';
	      for (int i = 0; i <= num; ++i) {
                  if (buf[i] != ' ' && buf[i] != '\n' && buf[i] != '\r' && buf[i] != '\0')
                      argbuf[end++] = buf[i];
	          else if (end > start) { //we had an arg
		      largv[largc++] = argbuf + start;
		      argbuf[end++] = '\0';
		      start = end;
	          }
	      }	
	  } else
              LOG(("warn: can't read update.params\n"));
      } else
  	  LOG(("warn: can't open update.params\n"));
  } else
      LOG(("warn: can't access update.params (rv=%d)\n", rv));

  //for (int i = 0; i < largc; ++i)
  //    NS_tfprintf(stderr, NS_T("arg[%d]=%s\n"), i, largv[i]);

  LOG(("info: running %ls %ls %ls\n", largv[0], largv[1], largv[2]));
  LaunchApp(path, largc, largv);

  LOG(("succeeded\n"));
  //WriteStatusFile(path, rv);
  NS_tremove(upstatus);
}

int NS_main(int argc, NS_tchar **argv)
{
  // The updater command line consists of the directory path containing the
  // updater.mar file to process followed by the PID of the calling process.
  // The updater will wait on the parent process to exit if the PID is non-
  // zero.  This is leveraged on platforms such as Windows where it is
  // necessary for the parent process to exit before its executable image may
  // be altered.

  //for (int i = 0; i < argc; ++i) {
  //	NS_tfprintf(stderr, NS_T("arg[%d]=%s\n"), i, argv[i]);
  //}

  if (argc < 3) {
    fprintf(stderr, "Usage: updater <dir-path> <parent-pid> [working-dir callback args...]\n");
    return 1;
  }


  int pid = NS_tatoi(argv[2]);
  if (pid) {
#ifdef XP_WIN
    HANDLE parent = OpenProcess(SYNCHRONIZE, FALSE, (DWORD) pid);
    // May return NULL if the parent process has already gone away.
    // Otherwise, wait for the parent process to exit before starting the
    // update.
    if (parent) {
      DWORD result = WaitForSingleObject(parent, 5000);
      CloseHandle(parent);
      if (result != WAIT_OBJECT_0)
        return 1;
      // The process may be signaled before it releases the executable image.
      // This is a terrible hack, but it'll have to do for now :-(
      Sleep(50);
    }
#else
    int status;
    waitpid(pid, &status, 0);
#endif
  }

  LogInit(argv[1]);

  DoUpdate(argv[1]);

  LogFinish();

  return 0;
}
