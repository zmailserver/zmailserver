/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite CSharp Client
 * Copyright (C) 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
#include "common.h"
#include <stdarg.h>
#include <time.h>
#include "Logger.h"

typedef unsigned __int64 usec_t;

static const wchar_t *USubst = L"\001\001";
const wchar_t *const Log::LevelStr[] = {
    L"none", L"err", L"warn", L"info", L"debg", L"trce"
};
const wchar_t *const Log::LevelStr2[] = {
    L"nothing", L"error", L"warning", L"information", L"debug", L"trace"
};
Log Log::glog(NULL, Log::Info);
TLSClass<Log> Log::tlog;

#define EPOCH_BIAS 116444736000000000i64

static usec_t microtime(void)
{
    union
    {
        usec_t u64;
        FILETIME ft;
    } ft;

    usec_t usec;

    GetSystemTimeAsFileTime(&ft.ft);
    usec = (ft.u64 - EPOCH_BIAS) / 10;
    return (usec / 1000000i64) * (usec_t)1000000 + (usec % 1000000i64);
}

bool Log::LogFile::close(void)
{
    if (fd == INVALID_HANDLE_VALUE)
        return false;
    CloseHandle(fd);
    fd = INVALID_HANDLE_VALUE;
    free(path);
    path = NULL;
    return true;
}

bool Log::LogFile::lock(void)
{
    OVERLAPPED ov;
    bool ret;

    memset(&ov, 0, sizeof (ov));
    ret = LockFileEx(fd, LOCKFILE_EXCLUSIVE_LOCK, 0, 1, 0, &ov) == TRUE;
    SetFilePointer((HANDLE)fd, 0, 0, FILE_END);
    return ret;
}

bool Log::LogFile::open(const wchar_t *file)
{
    wchar_t buf[1024];

    if (file && path && open() && !wcscmp(path, file))
        return true;
    close();
    if (!file)
    {
        GetTempPath(sizeof (buf) / sizeof (wchar_t), buf);
        wcscat(buf, L"default.log");
        path = wcsdup(buf);
        return true;
    }
    if (!wcscmp(file, L"cout") || !wcscmp(file, L"stdout"))
    {
        DuplicateHandle(GetCurrentProcess(), GetStdHandle(STD_OUTPUT_HANDLE),
            GetCurrentProcess(), &fd, 0L, TRUE, DUPLICATE_SAME_ACCESS);
    }
    else if (!wcscmp(file, L"cerr") || !wcscmp(file, L"stderr"))
    {
        DuplicateHandle(GetCurrentProcess(), GetStdHandle(STD_ERROR_HANDLE),
            GetCurrentProcess(), &fd, 0L, TRUE, DUPLICATE_SAME_ACCESS);
    }
    else
    {
        fd = CreateFile(file, GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE,
            NULL, OPEN_ALWAYS, FILE_ATTRIBUTE_NORMAL | FILE_FLAG_SEQUENTIAL_SCAN, 0);
        SetFilePointer(fd, 0, NULL, FILE_END);
    }
    if (fd == INVALID_HANDLE_VALUE)
    {
        wcerr << L"unable to open log " << file << endl;
        return false;
    }
    path = wcsdup(file);
    return true;
}

bool Log::LogFile::unlock(void)
{
    OVERLAPPED ov;

    memset(&ov, 0, sizeof (ov));
    return UnlockFileEx(fd, 0, 1, 0, &ov) == TRUE;
}

bool Log::LogFile::write(const wchar_t *buf, unsigned chars)
{
    DWORD out;
    char sbuf[1024];
    int sz;

    SetFilePointer(fd, 0, NULL, FILE_END);
    if ((sz = WideCharToMultiByte(CP_UTF8, 0, buf, chars, sbuf, sizeof (sbuf), NULL, NULL)) > 0)
    {
        WriteFile(fd, sbuf, sz, &out, NULL);
    }
    else
    {
        sz = WideCharToMultiByte(CP_UTF8, 0, buf, chars, NULL, 0, NULL, NULL);

        char *s = new char[sz];

        WideCharToMultiByte(CP_UTF8, 0, buf, chars, s, sz, NULL, NULL);
        WriteFile(fd, buf, sz, &out, NULL);
        delete[] buf;
    }
    return out == chars;
}

Log::Log(const wchar_t *file, Level level): ffd(file), fmt(NULL), last_fmt(NULL),
    last_sec(0), lvl(level), upos(0)
{
    format(L"[%Y-%m-%d %H:%M:%S.%#]");
}

void Log::endlog(Tlsdata &tlsd, Level clvl)
{
    wchar_t buf[128];
    time_t now_sec;
    usec_t now_usec;
    wstring &strbuf(tlsd.strbuf);

    lck.Enter();
    if (!ffd.open())
        ffd.open(ffd.file());
    ffd.lock();
    now_usec = microtime();
    now_sec = (unsigned)(now_usec / 1000000);
    if (now_sec != last_sec)
    {
        wchar_t *p;
        struct tm *tm;

        tm = localtime(&now_sec);
        wcsftime(buf, sizeof (buf) / sizeof (wchar_t), fmt, tm);
        if (last_fmt != NULL)
            free(last_fmt);
        last_fmt = wcsdup(buf);
        last_sec = now_sec;
        p = wcsstr(last_fmt, USubst);
        upos = p ? (int)(p - last_fmt) : -1;
    }
    strbuf = last_fmt;
    if (upos != -1)
    {
        wsprintf(buf, L"%06u", (unsigned)(now_usec % 1000000));
        strbuf.replace(upos, 2, buf);
    }
    if (!strbuf.empty())
        strbuf += ' ';
    strbuf += LevelStr[clvl];
    if (clvl == Err)
        strbuf += ' ';
    strbuf += ' ';
    if (!tlsd.prefix.empty())
    {
        strbuf += tlsd.prefix;
        strbuf += ' ';
    }
    strbuf.append(tlsd.strm.str(), (unsigned)tlsd.strm.size());
    if (strbuf.at(strbuf.length() - 1) != '\n') {
        strbuf += '\r';
        strbuf += '\n';
    }
    ffd.write(strbuf.c_str(), (unsigned)strbuf.size());
    ffd.unlock();
    lck.Leave();
    tlsd.clvl = None;
    tlsd.space = false;
    tlsd.strm.reset();
}

void Log::format(const wchar_t *f)
{
    wstring s(f);
    wstring::size_type pos;

    last_sec = 0;
    if ((pos = s.find(L"%#")) != s.npos)
        s.replace(pos, 2, USubst);
    if (fmt != NULL)
        free(fmt);
    fmt = wcsdup(s.c_str());
}

void Log::logv(Level l, ...)
{
    if (l > lvl)
        return;

    bool first = true;
    const wchar_t *p;
    Tlsdata &tlsd(*tls);
    va_list vl;

    va_start(vl, l);
    while ((p = va_arg(vl, const wchar_t *)) != NULL)
    {
        if (first)
            first = false;
        else
            tlsd.strm << ' ';
        tlsd.strm << p;
    }
    va_end(vl);
    endlog(tlsd, l);
}

Log::Level Log::str2enum(const wchar_t *l)
{
    for (int i = 0; i < (int)(sizeof (LevelStr) / sizeof (const wchar_t *)); i++)
    {
        if (!wcsicmp(l, LevelStr[i]) || !wcsicmp(l, LevelStr2[i]))
            return (Level)i;
    }
    return None;
}

extern "C" {
CPPLIB_DLLAPI const wchar_t *log_file(void)
{
    return dlog.file();
}

CPPLIB_DLLAPI void log_init(const wchar_t *file, Log::Level level)
{
    Log::init(file, level);
}

CPPLIB_DLLAPI int log_level(void)
{
    return dlog.level();
}

CPPLIB_DLLAPI void log_open(const wchar_t *file)
{
    if (!file || wcscmp(dlog.file(), file))
        Log::open(file);
}

CPPLIB_DLLAPI void log_prefix(const wchar_t *prefix)
{
    dlog.prefix(prefix);
}

CPPLIB_DLLAPI void log_print(Log::Level level, const wchar_t *str)
{
    dlog.log(level, str);
}

}
