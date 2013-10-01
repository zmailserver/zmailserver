﻿using System.Collections.Generic;
using System.Linq;
using System.Text;
using System;

namespace MVVM.Model
{
public class ImportOptions
{
    private bool m_Mail;
    public bool Mail {
        get { return m_Mail; }
        set { m_Mail = value; }
    }
    private bool m_Contacts;
    public bool Contacts {
        get { return m_Contacts; }
        set { m_Contacts = value; }
    }
    private bool m_Calendar;
    public bool Calendar {
        get { return m_Calendar; }
        set { m_Calendar = value; }
    }
    private bool m_Tasks;
    public bool Tasks {
        get { return m_Tasks; }
        set { m_Tasks = value; }
    }
    private bool m_DeletedItems;
    public bool DeletedItems {
        get { return m_DeletedItems; }
        set { m_DeletedItems = value; }
    }
    private bool m_Junk;
    public bool Junk {
        get { return m_Junk; }
        set { m_Junk = value; }
    }
    private bool m_Sent;
    public bool Sent {
        get { return m_Sent; }
        set { m_Sent = value; }
    }
    private bool m_Rules;
    public bool Rules {
        get { return m_Rules; }
        set { m_Rules = value; }
    }
    private bool m_OOO;
    public bool OOO
    {
        get { return m_OOO; }
        set { m_OOO = value; }
    }
}

public class AdvancedImportOptions
{
    private DateTime m_MigrateONRAfter;
    public DateTime MigrateOnOrAfter {
        get { return m_MigrateONRAfter; }
        set { m_MigrateONRAfter = value; }
    }
    private bool m_IsOnOrAfter;
    public bool IsOnOrAfter {
        get { return m_IsOnOrAfter; }
        set { m_IsOnOrAfter = value; }
    }
    private string m_MaxMessageSize;
    public string MaxMessageSize {
        get { return m_MaxMessageSize; }
        set { m_MaxMessageSize = value; }
    }
    private string m_DateFilterItem;
    public string DateFilterItem
    {
        get { return m_DateFilterItem; }
        set { m_DateFilterItem = value; }
    }
    private bool m_IsMaxMessageSize;
    public bool IsMaxMessageSize
    {
        get { return m_IsMaxMessageSize; }
        set { m_IsMaxMessageSize = value; }
    }
    public Folder[] FoldersToSkip;
    private bool m_IsSkipFolders;
    public bool IsSkipFolders
    {
        get { return m_IsSkipFolders; }
        set { m_IsSkipFolders = value; }
    }
    private bool m_IsSkipPrevMigratedItems;
    public bool IsSkipPrevMigratedItems
    {
        get { return m_IsSkipPrevMigratedItems; }
        set { m_IsSkipPrevMigratedItems = value; }
    }
    private string m_SpecialCharReplace;

    public string SpecialCharReplace
    {
        get { return m_SpecialCharReplace; }
        set { m_SpecialCharReplace = value; }
    }

    private string m_CSVDelimiter;

    public string CSVDelimiter
    {
        get { return m_CSVDelimiter; }
        set { m_CSVDelimiter = value; }
    }

    private long m_LangID;
    public long LangID
    {
        get { return m_LangID; }
        set { m_LangID = value; }
    }

    private Int32 m_MaxRetries;
    public Int32 MaxRetries
    {
        get { return m_MaxRetries; }
        set { m_MaxRetries = value; }
    }
}

public class Folder
{
    private string m_FolderName;
    public string FolderName {
        get { return m_FolderName; }
        set { m_FolderName = value; }
    }
    /*  public List<string> FoldersToSkip
     * {
     *    get { return m_FoldersToSkip; }
     *    set { m_FoldersToSkip = value; }
     * }
     *
     */
}
}
