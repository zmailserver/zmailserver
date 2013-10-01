
' * ***** BEGIN LICENSE BLOCK *****
' * Zimbra Collaboration Suite Server
' * Copyright (C) 2009, 2010, 2011, 2012 VMware, Inc.
' * 
' * The contents of this file are subject to the Zimbra Public License
' * Version 1.3 ("License"); you may not use this file except in
' * compliance with the License.  You may obtain a copy of the License at
' * http://www.zimbra.com/license.
' * 
' * Software distributed under the License is distributed on an "AS IS"
' * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
' * ***** END LICENSE BLOCK *****
' */
'
' ZD runner
'

Dim oFso, oReg, oShellApp, oShell, oWMI, sScriptPath, sScriptDir, oTokens, sAppRoot, sDataRoot, sOverridePath
Dim sLocalAppDir, bIsUpgrade, sTmpDir, sRestoreDir, aUserDirs, aUserFiles, sVersion, sVerFile

const HKEY_CURRENT_USER = &H80000001

Sub LogMsg(sMsg, iLevel)
    If (InStr(Wscript.FullName,"cscript") > 0) Then
        WScript.StdOut.WriteLine(sMsg)
    End If
    If iLevel <= iLogLevel Then
        oShell.LogEvent iLevel, "Zimbra Desktop: " & sMsg
    End If
End Sub


Sub FindAndReplace(sFile, oTokens)
    Dim oFso, oInFile, oOutFile, sTmpFile
    
    Set oFso = CreateObject("Scripting.FileSystemObject")
    sTmpFile = sFile & ".tmp"
    
    On Error Resume Next
    Set oInFile = oFso.OpenTextFile(sFile, 1, false)
    If Err.number <> 0 Then
        LogMsg "failed to open file: " & sFile, 1
        Exit Sub
    End If
    Set oOutFile = oFso.OpenTextFile(sTmpFile, 2, true)
    If Err.number <> 0 Then
        LogMsg "failed to open file: " & sTmpFile, 1
        Exit Sub   
    End If
    
    Do Until oInFile.AtEndOfStream
        Dim sLine, sKey
        sLine = oInFile.ReadLine
        For Each sKey In oTokens.Keys
            sLine = Replace(sLine, sKey, oTokens.Item(sKey))
        Next
        oOutFile.WriteLine(sLine)       
    Loop
   
    oInFile.Close
    oOutFile.Close
    oFso.DeleteFile sFile, true
    oFso.MoveFile sTmpFile, sFile
End Sub

Function GetRandomId
    Set oTypeLib = CreateObject("Scriptlet.TypeLib")
    GetRandomId = LCase(Mid(oTypeLib.GUID, 2, 36))
End Function

Sub CopyIfExists(sSrc, sDest, bOW)
    If oFso.FileExists(sSrc) Then
        oFso.CopyFile sSrc, sDest, bOW
    End If
End Sub

Sub LaunchPrism()
    Dim sCmd, iRet 

    iRet = oReg.CreateKey(HKEY_CURRENT_USER, "Software\Zimbra\Zimbra Desktop\Prism")
    oReg.SetStringValue HKEY_CURRENT_USER, "Software\Zimbra\Zimbra Desktop\Prism", "OverridePath", sOverridePath

    sCmd = Chr(34) & sAppRoot & "\win32\prism\zdclient.exe" & Chr(34)
    oShell.Run sCmd, 1, false 
    WScript.Quit
End Sub

Sub StopProcesses()
    Dim sCmd, sCScript, sZdCtl

    sCScript = Chr(34) & oFso.GetSpecialFolder(1).Path & "\cscript.exe" & Chr(34)
    sZdCtl = Chr(34) & sDataRoot & "\bin\zdctl.vbs" & Chr(34)

    'Stop backend service and prism
    oShell.Run sCScript & " " & sZdCtl & " shutdown", 0, true
End Sub

Sub BackupFailed(sMsg)
    If Not IsNull(sMsg) Then
        oShell.Popup sMsg, 0, "Zimbra Desktop", 48
    End If
    oFso.MoveFolder sTmpDir, sRestoreDir
    WScript.Quit
End Sub

Sub BackupData()
    If oFso.FolderExists(sTmpDir) Then
        ' Save leftover temp dir, in case it's needed in manual recovery 
        Dim iEpoch
        iEpoch = DateDiff("s", "01/01/1970 00:00:00", Now())
        oFso.MoveFolder sTmpDir, sTmpDir & "." & iEpoch 
    End If
    oFso.CreateFolder sTmpDir
    
    On Error Resume Next

    Dim sDir
    For Each sDir In aUserDirs
        If oFso.FolderExists(sDataRoot & "\" & sDir) Then
            oFso.MoveFolder sDataRoot & "\" & sDir, sTmpDir & "\" & sDir
            If Err.number <> 0 Then
                BackupFailed "File operation failed. Please close any open files under " & _
                    sDataRoot & "\" & sDir 
            End If
        End If
    Next
    
    oFso.CreateFolder sTmpDir & "\profile"
    oFso.CreateFolder sTmpDir & "\conf"
    Dim sFile
    For Each sFile In aUserFiles
        CopyIfExists sDataRoot & "\" & sFile, sTmpDir & "\" & sFile, true 
    Next

    Dim iButton, sMsg
    Do  
        oFso.DeleteFolder sDataRoot, true
        If Err.number = 0 Then
            Exit Sub
        Else
            sMsg = "Unable to delete folder: " & sDataRoot & ". " & _
                "Please close any open files in this folder and its sub-folders."
            iButton = oShell.Popup(sMsg, 0, "Zimbra Desktop", 5 + 48)
        End If
        Err.Clear
    Loop While iButton = 4 ' Retry

    ' Cancled
    BackupFailed Null
End Sub

Sub RestoreData(sSrcRoot)
    Dim sDir
    For Each sDir In aUserDirs
        If oFso.FolderExists(sSrcRoot & "\" & sDir) Then
            If oFso.FolderExists(sDataRoot & "\" & sDir) Then 
                oFso.DeleteFolder sDataRoot & "\" & sDir, true
            End If
            oFso.MoveFolder sSrcRoot & "\" & sDir, sDataRoot & "\" & sDir
        End If
    Next
    
    Dim sFile
    For Each sFile In aUserFiles
        CopyIfExists sSrcRoot & "\" & sFile, sDataRoot & "\" & sFile, true
    Next
    
    oFso.DeleteFolder sSrcRoot, true
End Sub

Sub WriteVersion()
    Dim oFout

    On Error Resume Next
    Set oFout = oFso.OpenTextFile(sVerFile, 2, true)
    If Err.number = 0 Then
        oFout.WriteLine(sVersion)
    End If
    oFout.Close
End Sub

Function ReadVersion()
    Dim oFin

    ReadVersion = ""
    On Error Resume Next
    Set oFin = oFso.OpenTextFile(sVerFile, 1, false)
    If Err.number = 0 Then
        ReadVersion= oFin.ReadLine()
    End If
    oFin.Close
End Function

Sub EnsureSingleInstance()
    Dim oProcs, oProc, bFound
    Set oProcs = oWMI.ExecQuery("Select * from Win32_Process " & _
        "where Name='cscript.exe'",, 48) ' 48: forward-only enumerator + return-immediately

    bFound = false      
    For Each oProc in oProcs
        If Instr(1, oProc.CommandLine, WScript.ScriptName, 1) > 0 Then
            If bFound Then
                WScript.Quit
            End If
            bFound = true
        End If
    Next
End Sub

Sub BuildPath(ByVal Path)
    If Not oFso.FolderExists(Path) Then
        BuildPath oFso.GetParentFolderName(Path)
        oFso.CreateFolder Path
    End If
End Sub

Function GetDataRoot()
    oReg.GetStringValue HKEY_CURRENT_USER, "Software\Zimbra\Zimbra Desktop", "DataRoot", GetDataRoot
    If IsNull(GetDataRoot) Then
        GetDataRoot = sLocalAppDir & "\Zimbra\Zimbra Desktop"
    Else
        If Not oFso.FolderExists(GetDataRoot) Then
            BuildPath(GetDataRoot)
        End If
        GetDataRoot = oFso.getFolder(GetDataRoot).ShortPath
    End If
End Function

Function IsNonEnUsXp()
    Dim nLang, sVer
    Set colOSes = oWMI.ExecQuery("Select * from Win32_OperatingSystem")
    For Each oOS in colOSes
        nLang = oOS.OSLanguage
        sVer = oOS.Version
        If nLang <> 1033 AND Instr(sVer, "5.") = 1 Then
            IsNonEnUsXp = true
        Else
            IsNonEnUsXp = false
        End If
        Exit For
    Next
End Function

'------------------------------- main ---------------------------------

Set oFso = CreateObject("Scripting.FileSystemObject")
Set oShellApp = CreateObject("Shell.Application")
Set oShell = CreateObject("WScript.Shell")
Set oReg=GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\default:StdRegProv")
Set oWMI = GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")

EnsureSingleInstance

sVersion="@version@"
aUserDirs = Array("index", "store", "sqlite", "log", "zimlets-properties", "zimlets-deployed")
aUserFiles = Array("conf\keystore", "profile\prefs.js", "profile\persdict.dat", "profile\localstore.json")
sScriptPath = WScript.ScriptFullName
sScriptDir = Left(sScriptPath, InStrRev(sScriptPath, WScript.ScriptName) - 2)
sAppRoot = oFso.GetParentFolderName(sScriptDir)
sLocalAppDir = oFso.getFolder(oShellApp.Namespace(&H1c&).Self.Path).ShortPath

sDataRoot = GetDataRoot()
xmlDataRoot = Replace(sDataRoot,"&","&amp;")
sVerFile = sDataRoot & "\conf\version"
sTmpDir = sDataRoot & ".tmp"
sRestoreDir = sDataRoot & ".rst"
sOverridePath = sDataRoot & "\zdesktop.webapp\override.ini"
bIsUpgrade = false

If oFso.FolderExists(sDataRoot) Then
    If oFso.FolderExists(sRestoreDir) Then
        RestoreData sRestoreDir
    End If 

    Dim sCurVer
    sCurVer = ReadVersion
    If StrComp(sCurVer, sVersion) = 0 Then
        LaunchPrism
    Else
        bIsUpgrade = true
    End If
End If

Dim sMsg
sMsg = "Initializing, please wait..."
If (InStr(Wscript.FullName,"cscript") > 0) Then
    WScript.Echo sMsg
End If
oShell.Popup sMsg, 5, "Zimbra Desktop", 64

StopProcesses

If bIsUpgrade Then
    BackupData
End If

' copy data files
If Not oFso.FolderExists(sLocalAppDir & "\Zimbra") Then
    oFso.CreateFolder sLocalAppDir & "\Zimbra"
End If
If Not oFso.FolderExists(sLocalAppDir & "\Zimbra\Zimbra Desktop") Then
    oFso.CreateFolder sLocalAppDir & "\Zimbra\Zimbra Desktop"
End If
oFso.CopyFolder sAppRoot & "\data\*", sDataRoot & "\", true
WriteVersion

Set physMem = GetObject("winmgmts:").InstancesOf("Win32_PhysicalMemory")
For Each mem In physMem
memTmp = mem.capacity / 1024 / 1024
TotalRam = TotalRam + memTmp
Next

If TotalRam > 1000 Then
  javaXms = "-Xms128m"
  javaXmx = "-Xmx512m"
Else
  javaXms = "-Xms32m"
  javaXmx = "-Xmx150m"
End If

' fix data files
Set oTokens = CreateObject("Scripting.Dictionary")
oTokens.Add "@install.app.root@", sAppRoot
oTokens.Add "@install.data.root@", sDataRoot
oTokens.Add "@install.key@", GetRandomId()
oTokens.Add "@install.mutex.name@", GetRandomId()
oTokens.Add "@install.locale@", "en-US"
oTokens.Add "@java.xms@", javaXms
oTokens.Add "@java.xmx@", javaXmx

FindAndReplace sDataRoot & "\bin\zdctl.vbs", oTokens
FindAndReplace sDataRoot & "\conf\zdesktop.conf", oTokens
FindAndReplace sDataRoot & "\zdesktop.webapp\webapp.ini", oTokens
FindAndReplace sDataRoot & "\profile\user.js", oTokens
FindAndReplace sOverridePath, oTokens

oTokens.Remove "@install.data.root@"
oTokens.Add "@install.data.root@", xmlDataRoot
FindAndReplace sDataRoot & "\conf\localconfig.xml", oTokens
FindAndReplace sDataRoot & "\jetty\etc\jetty.xml", oTokens

If bIsUpgrade Then
    RestoreData sTmpDir
End If

oReg.CreateKey HKEY_CURRENT_USER, "Software\Zimbra\Zimbra Desktop"
oReg.SetStringValue HKEY_CURRENT_USER, "Software\Zimbra\Zimbra Desktop", "DataRoot", sDataRoot

LaunchPrism
