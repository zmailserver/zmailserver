﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text;

namespace CssLib
{
public class Log {
    public enum Level { None, Err, Warn, Info, Debug, Trace };

    public static void init(string file, Level level)
    {
        log_init(file, level);
    }

    public static void log(Level level, object obj)
    {
        log_print(level, obj.ToString());
    }

    public static void log(Level level, params object[] objs)
    {
        StringBuilder s = new StringBuilder();
        String last = null;

        foreach (object obj in objs) {
            if (s.Length > 0 && !last.EndsWith("="))
                s.Append(' ');
            last = obj.ToString();
            s.Append(last);
        }
        log_print(level, s.ToString());
    }

    public static void trace(string str) { log_print(Level.Trace, str); }
    public static void trace(params object[] objs) { log(Level.Trace, objs); }
    public static void debug(string str) { log_print(Level.Debug, str); }
    public static void debug(params object[] objs) { log(Level.Debug, objs); }
    public static void err(string str) { log_print(Level.Err, str); }
    public static void err(params object[] objs) { log(Level.Err, objs); }
    public static void info(string str) { log_print(Level.Info, str); }
    public static void info(params object[] objs) { log(Level.Info, objs); }
    public static void warn(string str) { log_print(Level.Warn, str); }
    public static void warn(params object[] objs) { log(Level.Warn, objs); }

    public static void dump(string str, string data)
    {
        log_print(Level.Trace, (str + "\r\n" + data).ToString());
    }
    public static string file() { return log_file(); }
    public static void open(string file) { log_open(file); }
    public static void prefix(string prefix) { log_prefix(prefix); }

    #region PInvokes

    [DllImport("CppLib.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Unicode)]
    static private extern string log_file();

    [DllImport("CppLib.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Unicode)]
    static private extern void log_init(string file, Level level);

    [DllImport("CppLib.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Unicode)]
    static private extern void log_open(string file);

    [DllImport("CppLib.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Unicode)]
    static private extern void log_prefix(string prefix);

    [DllImport("CppLib.dll", CallingConvention = CallingConvention.Cdecl, CharSet = CharSet.Unicode)]
    static private extern void log_print(Level level, string str);

    #endregion PInvokes
}

[Flags] public enum ItemsAndFoldersOptions
{
    OOO = 0x100, Junk = 0x0080, DeletedItems = 0x0040, Sent = 0x0020, Rules = 0x0010,
    Tasks = 0x0008, Calendar = 0x0004, Contacts = 0x0002, Mail = 0x0001, None = 0x0000
}

public enum ZimbraFolders
{
    Min = 0, UserRoot = 1, Inbox = 2, Trash = 3, Junk = 4, Sent = 5, Drafts = 6,
    Contacts = 7, Tags = 8, Conversations = 9, Calendar = 10, MailboxRoot = 11, Wiki = 12,
    EmailedContacts = 13, Chats = 14, Tasks = 15, Max = 16
}

public enum LogLevel
{
    Info = 3,
    Debug = 4,
    Trace = 5,
}

public class MigrationOptions
{
    public ItemsAndFoldersOptions ItemsAndFolders;

    public string DateFilter;
    public string MessageSizeFilter;
    public string SkipFolders;
    public LogLevel VerboseOn;
    public bool SkipPrevMigrated;
    public Int32 MaxErrorCnt;
    public string SpecialCharRep;
    public bool IsMaintainenceMode;
    public long LangID;
    public Int32 MaxRetries;
    public string DateFilterItem;
    
}

public class CSMigrationWrapper
{
    enum foldertype
    {
        Mail = 1, Contacts = 2, Calendar = 3, Task = 4, MeetingReq = 5
    };

    string m_MailClient;
    public string MailClient {
        get { return m_MailClient; }
        set { m_MailClient = value; }
    }

    dynamic MailWrapper;

    dynamic m_umUser = null; // used to store user object so Exit can do a user.Uninit
    public dynamic UmUser {
        get { return m_umUser; }
        set { m_umUser = value; }
    }

    public CSMigrationWrapper(string mailClient, LogLevel opts=LogLevel.Info)
    {
        // try
        {
            Log.Level level = (Log.Level)opts;
            InitLogFile("migration",level);
            Log.info("Initializing migration");

            MailClient = mailClient;
            if (MailClient == "MAPI")
            {
                MailWrapper = new MapiMigration();
            }
        }
        /* catch (Exception e)
        {
            Log.err("Exception in CSMigrationWrapper construcor", e.Message);
        }*/
      
    }

    public string GlobalInit(string Target, string AdminUser, string AdminPassword)
    {
        string s = "";

        try
        {
            s = MailWrapper.GlobalInit(Target, AdminUser, AdminPassword);
        }
        catch (Exception e)
        {
            s = string.Format("Initialization Exception. Make sure to enter the proper credentials: {0}", e.Message);
        }
        return s;
    }

    public string GlobalUninit()
    {
        try
        {
            return MailWrapper.GlobalUninit();
        }
        catch (Exception e)
        {
            string msg = string.Format("GetListofMapiProfiles Exception: {0}", e.Message);
            return msg;
        }
    }

    public bool AvoidInternalErrors(string strErr)
    {
	return MailWrapper.AvoidInternalErrors(strErr);
    }

    public string[] GetListofMapiProfiles()
    {
        object var = new object();
        string msg = "";
        string[] s = { "" };

        try
        {
            msg = MailWrapper.GetProfilelist(out var);
            s = (string[])var;
        }
        catch (Exception e)
        {
            // FBS bug 73020 -- 4/18/12
            string tmp = msg;

            msg = string.Format("GetListofMapiProfiles Exception: {0}", e.Message);
            if (tmp.Length > 0)
            {
                if (tmp.Substring(0, 11) == "No profiles")
                {
                    msg = "No profiles";
                }
            }
            s[0]= msg;
        }
        return s;
    }

    public string[] GetListFromObjectPicker()
    {
        // Change this to above signature when I start getting the real ObjectPicker object back
        string[] s = { "" };
        string status = "";
        object var = new object();

        try
        {
            status = MailWrapper.SelectExchangeUsers(out var);
            s = (string[])var;
        }
        catch (Exception e)
        {
            status = string.Format("GetListFromObjectPicker Exception: {0}", e.Message);
            s[0]= status;
        }
        return s;
    }

    private void InitLogFile(string prefix, Log.Level level)
    {
        string bakfile = Path.GetTempPath() + prefix + ".bak";
        string logfile = Path.GetTempPath() + prefix + ".log";

        try
        {
            if (File.Exists(bakfile))
            {
                File.Delete(bakfile);
            }
            if (File.Exists(logfile))
            {
                File.Move(logfile, bakfile);
            }
            Log.init(logfile, level);
        }
        catch (Exception e)
        {
            // need to do better than Console.WriteLine -- we'll only see this during debugging
            // but at least we won't crash
            string temp = string.Format("Initialization error on {0}: {1}", logfile, e.Message);
            Console.WriteLine(temp);
            return;
        }
    }

    private bool SkipFolder(MigrationOptions options, List<string> skipList, dynamic folder) {
        // Note that Rules and OOO do not apply here
        if ((folder.Id == (int)ZimbraFolders.Calendar &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Calendar)) ||
            (folder.Id == (int)ZimbraFolders.Contacts &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Contacts)) ||
            (folder.Id == (int)ZimbraFolders.Junk &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Junk)) ||
            (folder.Id == (int)ZimbraFolders.Sent &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Sent)) ||
            (folder.Id == (int)ZimbraFolders.Tasks &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Tasks)) ||
            (folder.Id == (int)ZimbraFolders.Trash &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.DeletedItems)) ||
            // FBS NOTE THAT THESE ARE EXCHANGE SPECIFIC and need to be removed
            (folder.ContainerClass == "IPF.Contact" &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Contacts)) ||
            (folder.ContainerClass == "IPF.Appointment" &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Calendar)) ||
            (folder.ContainerClass == "IPF.Task" &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Tasks)) ||
            (folder.ContainerClass == "IPF.Note" &&
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Mail)) ||
            (folder.ContainerClass == "" &&     // if no container class, assume IPF.Note
            !options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Mail)))
        {
            return true;
        }
        for (int i = 0; i < skipList.Count; i++)
        {
            if (string.Compare(folder.Name, skipList[i], StringComparison.OrdinalIgnoreCase) == 0)
            {
                return true;
            }
        }
        return false;
    }

    // TODO - this is Exchange specific - should use folder ids
    private string GetFolderViewType(string containerClass)
    {
        string retval = "";                     // if it's a "message", blanks are cool

        if (containerClass == "IPF.Contact")
            retval = "contact";
        else if (containerClass == "IPF.Appointment")
            retval = "appointment";
        else if (containerClass == "IPF.Task")
            retval = "task";
        return retval;
    }

    // if the tag has already been created, just return it; if not, do the req and create it
    private string DealWithTags(string theTags, MigrationAccount acct, ZimbraAPI api)
    {
        string retval = "";
        string[] tokens = theTags.Split(',');
        for (int i = 0; i < tokens.Length; i ++)
        {
            if (i > 0)
            {
                retval += ",";
            }
            string token = tokens.GetValue(i).ToString();
            if (acct.tagDict.ContainsKey(token))
            {
                retval += acct.tagDict[token];
            }
            else
            {
                string tagID = "";  // output from CreateTag
                string color = (acct.tagDict.Count).ToString(); // color starts at 0, will keep bumping up by 1
                int stat = api.CreateTag(token, color, out tagID);  // do request and get the numstr back from response
                acct.tagDict.Add(token, tagID);   // add to existing dict; token is Key, tokenNumstr is Val
                retval += tagID;
            }
        }
        return retval;
    }

    private bool ProcessIt(MigrationOptions options, foldertype type)
    {
        bool retval = false;

        switch (type)
        {
        case foldertype.Mail:
        case foldertype.MeetingReq:
            retval = options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Mail);
            break;
        case foldertype.Calendar:
            retval = options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Calendar);
            break;
        case foldertype.Contacts:
            retval = options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Contacts);
            break;
        case foldertype.Task:
            retval = options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Tasks);
            break;
        default:
            break;
        }
        return retval;
    }

    private void ProcessItems(MigrationAccount Acct, bool isServer, dynamic user, dynamic folder,
        ZimbraAPI api, string path, MigrationOptions options)
    {
        int trial = 0;
      do
      {
          Acct.IsCompletedMigration = true;
        DateTime dt = DateTime.UtcNow;
        dynamic[] itemobjectarray = null ;
        trial++;
        
        try
        {
            itemobjectarray = user.GetItemsForFolder(folder, dt.ToOADate());
        }
        catch (Exception e)
        {
            Log.err("exception in ProcessItems->user.GetItemsFolder", e.Message);
        }
        if (itemobjectarray != null)
        {
            int iProcessedItems = 0;
            string historyfile = Path.GetTempPath() + Acct.AccountName.Substring(0, Acct.AccountName.IndexOf('@')) + "history.log";
            string historyid = "";

            if (itemobjectarray.GetLength(0) > 0)
            {
                if (Acct.migrationFolder.TotalCountOfItems == itemobjectarray.Count())
                {
                    
                    while (iProcessedItems < Acct.migrationFolder.TotalCountOfItems)
                    {
                        Log.debug("Processing folder", folder.Name, "-- Total items:", folder.ItemCount);
                        Log.debug("Processing folder", folder.Name, "-- Total items returned from itemobjectarray:", itemobjectarray.Count());
                        foreach (dynamic itemobject in itemobjectarray)
                        {
                            if (options.MaxErrorCnt > 0)
                            {
                                if (Acct.TotalErrors > options.MaxErrorCnt)
                                {
                                    Log.err("Cancelling migration -- error threshold reached");
                                    return;
                                }
                            }

                            Log.trace("CSmigration processitems foldertype from itemobject");
                            foldertype type = (foldertype)itemobject.Type;
                            Log.trace("CSmigration processitems ProcessIt");
                            bool bError = false;
                            if (ProcessIt(options, type))
                            {
                                 bError = false;

                                bool bSkipMessage = false;
                                Dictionary<string, string> dict = new Dictionary<string, string>();
                                string[,] data = null;
                                string itemtype = type.ToString();
                                if (options.IsMaintainenceMode)
                                {
                                    Log.err("Cancelling migration -- Mailbox is in maintainence  mode.Try back later");

                                    return;
                                }

                                try
                                {

                                    string hex = BitConverter.ToString(itemobject.ItemID);
                                    hex = hex.Replace("-", "");
                                    historyid = itemtype + hex;

                                }
                                catch (Exception e)
                                {
                                    Log.err("exception in Bitconverter cconverting itemid to a hexstring", e.Message);


                                }
                                if (options.SkipPrevMigrated)
                                {

                                    Log.trace("CSmigration processitems SkipPrevMigrated is true");
                                    if (historyid != "")
                                    {
                                        Log.trace("CSmigration processitems CheckifAlreadyMigrated");
                                        if (CheckifAlreadyMigrated(historyfile, historyid))
                                        {
                                            bSkipMessage = true;
                                            iProcessedItems++;
                                            continue;
                                        }
                                    }
                                    //uncomment after more testing
                                }
                                try
                                {
                                    Log.trace("CSmigration processitems GetDataForItemID");
                                    data = itemobject.GetDataForItemID(user.GetInternalUser(),
                                                    itemobject.ItemID, itemobject.Type);
                                }
                                catch (Exception e)
                                {
                                    Log.err("exception in ProcessItems->itemobject.GetDataForItemID", e.Message);
                                    iProcessedItems++;
                                    continue;
                                }
                                //check if data is valid
                                if (data == null)
                                {
                                    iProcessedItems++;
                                    continue;
                                }
                                int bound0 = data.GetUpperBound(0);
                                if (bound0 > 0)
                                {
                                    for (int i = 0; i <= bound0; i++)
                                    {
                                        string Key = data[0, i];
                                        string Value = data[1, i];

                                        try
                                        {
                                            dict.Add(Key, Value);
                                            // Console.WriteLine("{0}, {1}", so1, so2);
                                        }
                                        catch (Exception e)
                                        {
                                            string s = string.Format("Exception adding {0}/{1}: {2}", Key, Value, e.Message);
                                            Log.warn(s);
                                            // Console.WriteLine("{0}, {1}", so1, so2);
                                        }
                                    }
                                }

                                api.AccountID = Acct.AccountID;
                                api.AccountName = Acct.AccountName;
                                if (dict.Count > 0)
                                {
                                    int stat = 0;

                                    if ((type == foldertype.Mail) || (type == foldertype.MeetingReq))
                                    {
                                        //Log.debug("Msg Subject: ", dict["Subject"]);
                                        int msf = 0;
                                        if (options.MessageSizeFilter != null)
                                        {
                                            if (options.MessageSizeFilter.Length > 0)
                                            {
                                                msf = Int32.Parse(options.MessageSizeFilter);
                                                msf *= 1000000;
                                                if (dict["wstrmimeBuffer"].Length > msf)    // FBS bug 74000 -- 5/14/12 
                                                {
                                                    bSkipMessage = true;
                                                    Log.debug("Skipping", dict["Subject"], "-- message size exceeds size filter value");
                                                }
                                            }
                                        }
                                        if ((options.DateFilter != null))
                                        //if ((options.DateFilter != null) && (options.DateFilterItem.Contains(itemtype)))
                                        {
                                            try
                                            {
                                                DateTime dtm = DateTime.Parse(dict["Date"]);
                                                DateTime filterDtm = Convert.ToDateTime(options.DateFilter);
                                                if (options.DateFilterItem != null)
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) && (options.DateFilterItem.Contains(itemtype)))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["Subject"], "-- message older than date filter value");
                                                    }
                                                }
                                                else
                                                {

                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) )
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["Subject"], "-- message older than date filter value");
                                                    }
                                                }
                                            }
                                            catch (Exception)
                                            {
                                                Log.info(dict["Subject"], ": unable to parse date");
                                                bError = true;
                                            }
                                        }
                                        if (!bSkipMessage)
                                        {
                                            if (dict["tags"].Length > 0)
                                            {
                                                // change the tag names into tag numbers for AddMessage
                                                string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                                bool bRet = dict.Remove("tags");
                                                dict.Add("tags", tagsNumstrs);
                                            }
                                            dict.Add("folderId", folder.FolderPath);
                                            try
                                            {
                                                stat = api.AddMessage(dict);
                                                if (stat != 0)
                                                {
                                                    string errMsg = (api.LastError.IndexOf("upload ID: null") != -1)    // FBS bug 75159 -- 6/7/12
                                                                    ? "Unable to upload file. Please check server message size limits."
                                                                    : api.LastError;
                                                    if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                    {
                                                        errMsg = errMsg + " Try Back later";
                                                        options.IsMaintainenceMode = true;
                                                    }

                                                    Acct.LastProblemInfo = new ProblemInfo(dict["Subject"], errMsg, ProblemInfo.TYPE_ERR);
                                                    Acct.TotalErrors++;
                                                    bError = true;
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Acct.TotalErrors++;
                                                Log.err("Exception caught in ProcessItems->api.AddMessage", e.Message);
                                                bError = true;
                                            }
                                        }
                                    }
                                    else if (type == foldertype.Contacts)
                                    {
                                        //Log.debug("Contact Firstname: ", dict["firstName"]);
                                        if (dict["tags"].Length > 0)
                                        {
                                            // change the tag names into tag numbers for CreateContact
                                            string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                            bool bRet = dict.Remove("tags");
                                            dict.Add("tags", tagsNumstrs);
                                        }
                                        try
                                        {
                                            stat = api.CreateContact(dict, path);

                                            if (stat != 0)
                                            {
                                                string errMsg = api.LastError;
                                                if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                {
                                                    errMsg = errMsg + " Try Back later";

                                                    options.IsMaintainenceMode = true;
                                                }
                                                Log.err("Error in CreateContact ", errMsg);

                                                
                                                Acct.TotalErrors++;
                                                bError = true;
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            Acct.TotalErrors++;
                                            Log.err("Exception caught in ProcessItems->api.CreateContact", e.Message);
                                            bError = true;

                                        }
                                    }
                                    else if (type == foldertype.Calendar)
                                    {
                                        //Log.debug("Cal Subject: ", dict["su"]);
                                        if ((options.DateFilter != null))
                                       // if (options.DateFilter != null)
                                        {
                                            try
                                            {
                                                DateTime dtm = DateTime.Parse(dict["sFilterDate"]);
                                                DateTime filterDtm = Convert.ToDateTime(options.DateFilter);
                                                if (options.DateFilterItem != null)
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) && (options.DateFilterItem.Contains(itemtype)))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- appointment older than date filter value");
                                                    }
                                                }
                                                else
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- appointment older than date filter value");
                                                    }
                                                }
                                            }
                                            catch (Exception)
                                            {
                                                Log.info(dict["su"], ": unable to parse date");
                                                bError = true;
                                            }
                                        }
                                        if (!bSkipMessage)
                                        {
                                            try
                                            {
                                                if (dict["tags"].Length > 0)
                                                {
                                                    // change the tag names into tag numbers for AddAppointment
                                                    string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                                    bool bRet = dict.Remove("tags");
                                                    dict.Add("tags", tagsNumstrs);
                                                }
                                                dict.Add("accountNum", Acct.AccountNum.ToString());
                                                stat = api.AddAppointment(dict, path);
                                                if (stat != 0)
                                                {
                                                    string errMsg = api.LastError;
                                                    if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                    {
                                                        errMsg = errMsg + " Try Back later";

                                                        options.IsMaintainenceMode = true;
                                                    }
                                                    Acct.LastProblemInfo = new ProblemInfo(dict["su"], errMsg,
                                                                                           ProblemInfo.TYPE_ERR);
                                                    Acct.TotalErrors++;
                                                    bError = true;


                                                }

                                            }
                                            catch (Exception e)
                                            {
                                                Acct.LastProblemInfo = new ProblemInfo(dict["su"], e.Message,
                                                                                       ProblemInfo.TYPE_ERR);
                                                Acct.TotalErrors++;
                                                Log.err(dict["su"], "exception caught in ProcessItems->api.AddAppointment", e.Message);
                                                bError = true;
                                            }
                                        }
                                    }
                                    else if (type == foldertype.Task)
                                    {
                                        //Log.debug("Task Subject: ", dict["su"]);
                                        if ((options.DateFilter != null))
                                        //if (options.DateFilter != null)
                                        {
                                            try
                                            {
                                                DateTime dtm = DateTime.Parse(dict["sFilterDate"]);
                                                DateTime filterDtm = Convert.ToDateTime(options.DateFilter);
                                                if ((options.DateFilterItem != null))
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) && (options.DateFilterItem.Contains(itemtype)))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- task older than date filter value");
                                                    }
                                                }
                                                else
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- task older than date filter value");
                                                    }
                                                }
                                            }
                                            catch (Exception)
                                            {

                                                Log.info(dict["su"], ": unable to parse date");
                                                bError = true;
                                            }
                                        }
                                        if (!bSkipMessage)
                                        {
                                            if (dict["tags"].Length > 0)
                                            {
                                                // change the tag names into tag numbers for AddTask
                                                string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                                bool bRet = dict.Remove("tags");
                                                dict.Add("tags", tagsNumstrs);
                                            }
                                            try
                                            {
                                                stat = api.AddTask(dict, path);

                                                if (stat != 0)
                                                {
                                                    string errMsg = api.LastError;
                                                    if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                    {
                                                        errMsg = errMsg + " Try Back later";

                                                        options.IsMaintainenceMode = true;
                                                    }

                                                    Log.err("error in AddTask ", errMsg);
                                                    
                                                    Acct.TotalErrors++;
                                                    bError = true;


                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Acct.TotalErrors++;
                                                Log.err("exception caught in ProcessItems->api.AddTask", e.Message);
                                                bError = true;
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    Acct.LastProblemInfo = new ProblemInfo(Acct.AccountName, "Error on message",
                                                                           ProblemInfo.TYPE_ERR);
                                    Acct.TotalErrors++;
                                    bError = true;
                                }

                                if (!bError)
                                {
                                    // Note the : statement.  It seems weird to set Acct.migrationFolder.CurrentCountOFItems
                                    // to itself, but this is done so the method will be called to increment the progress bar
                                    Acct.migrationFolder.CurrentCountOfItems = bSkipMessage
                                        ? Acct.migrationFolder.CurrentCountOfItems
                                        : Acct.migrationFolder.CurrentCountOfItems + 1;
                                }
                            }
                            if ((historyid != "") &&(!bError))
                            {
                                try
                                {

                                    File.AppendAllText(historyfile, historyid); //uncomment after more testing
                                    File.AppendAllText(historyfile, "\n");
                                   
                                }
                                catch (Exception e)
                                {
                                    Acct.TotalErrors++;
                                    Log.err("CSmigrationwrapper  Exception caught in ProcessItems writing history to the history file", e.Message);
                                }
                            }
                            iProcessedItems++;
                        }
                    }
                }
                else
                {
                    Log.trace(" Itemobjectarray.count is not equal to MigrationFolder.totalcountofitems");
                    string errMesg = "MAPI Could not be returning all the items for the folder - Migration is not complete.Please re-run migration for the user";
                    Acct.TotalErrors++;
                    Log.err("CSmigrationwrapper MAPI edgecase",errMesg);
                    Acct.LastProblemInfo = new ProblemInfo(Acct.AccountName, errMesg, ProblemInfo.TYPE_ERR);
                    Acct.IsCompletedMigration = false;
                    options.SkipPrevMigrated = true;
                    while (iProcessedItems < itemobjectarray.Count())
                    {
                        Log.debug("Processing folder", folder.Name, "-- Total items:", folder.ItemCount);
                        Log.debug("Processing folder", folder.Name, "-- Total items returned from itemobjectarray:", itemobjectarray.Count());
                        foreach (dynamic itemobject in itemobjectarray)
                        {
                            if (options.MaxErrorCnt > 0)
                            {
                                if (Acct.TotalErrors > options.MaxErrorCnt)
                                {
                                    Log.err("Cancelling migration -- error threshold reached");
                                    return;
                                }
                            }
                            foldertype type = (foldertype)itemobject.Type;
                            if (ProcessIt(options, type))
                            {
                                bool bError = false;

                                bool bSkipMessage = false;
                                Dictionary<string, string> dict = new Dictionary<string, string>();
                                string[,] data = null;
                                string itemtype = type.ToString();
                                if (options.IsMaintainenceMode)
                                {
                                    Log.err("Cancelling migration -- Mailbox is in maintainence  mode.Try back later");

                                    return;
                                }

                                try
                                {

                                    string hex = BitConverter.ToString(itemobject.ItemID);
                                    hex = hex.Replace("-", "");
                                    historyid = itemtype + hex;

                                }
                                catch (Exception e)
                                {
                                    Log.err("exception in Bitconverter cconverting itemid to a hexstring", e.Message);


                                }
                                if (options.SkipPrevMigrated)
                                {
                                    if (historyid != "")
                                    {
                                        if (CheckifAlreadyMigrated(historyfile, historyid))
                                        {
                                            bSkipMessage = true;
                                            iProcessedItems++;
                                            continue;
                                        }
                                    }
                                    //uncomment after more testing
                                }
                                try
                                {
                                    data = itemobject.GetDataForItemID(user.GetInternalUser(),
                                                    itemobject.ItemID, itemobject.Type);
                                }
                                catch (Exception e)
                                {
                                    Log.err("exception in ProcessItems->itemobject.GetDataForItemID", e.Message);
                                    iProcessedItems++;
                                    continue;
                                }
                                //check if data is valid
                                if (data == null)
                                {
                                    iProcessedItems++;
                                    continue;
                                }
                                int bound0 = data.GetUpperBound(0);
                                if (bound0 > 0)
                                {
                                    for (int i = 0; i <= bound0; i++)
                                    {
                                        string Key = data[0, i];
                                        string Value = data[1, i];

                                        try
                                        {
                                            dict.Add(Key, Value);
                                            // Console.WriteLine("{0}, {1}", so1, so2);
                                        }
                                        catch (Exception e)
                                        {
                                            string s = string.Format("Exception adding {0}/{1}: {2}", Key, Value, e.Message);
                                            Log.warn(s);
                                            // Console.WriteLine("{0}, {1}", so1, so2);
                                        }
                                    }
                                }

                                api.AccountID = Acct.AccountID;
                                api.AccountName = Acct.AccountName;
                                if (dict.Count > 0)
                                {
                                    int stat = 0;

                                    if ((type == foldertype.Mail) || (type == foldertype.MeetingReq))
                                    {
                                        //Log.debug("Msg Subject: ", dict["Subject"]);
                                        int msf = 0;
                                        if (options.MessageSizeFilter != null)
                                        {
                                            if (options.MessageSizeFilter.Length > 0)
                                            {
                                                msf = Int32.Parse(options.MessageSizeFilter);
                                                msf *= 1000000;
                                                if (dict["wstrmimeBuffer"].Length > msf)    // FBS bug 74000 -- 5/14/12 
                                                {
                                                    bSkipMessage = true;
                                                    Log.debug("Skipping", dict["Subject"], "-- message size exceeds size filter value");
                                                }
                                            }
                                        }
                                        if ((options.DateFilter != null))
                                        //if (options.DateFilter != null)
                                        {
                                            try
                                            {
                                                DateTime dtm = DateTime.Parse(dict["Date"]);
                                                DateTime filterDtm = Convert.ToDateTime(options.DateFilter);
                                                if (options.DateFilterItem != null)
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) && (options.DateFilterItem.Contains(itemtype)))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["Subject"], "-- message older than date filter value");
                                                    }
                                                }
                                                else
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) )
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["Subject"], "-- message older than date filter value");
                                                    }
                                                }
                                            }
                                            catch (Exception)
                                            {
                                                Log.info(dict["Subject"], ": unable to parse date");
                                            }
                                        }
                                        if (!bSkipMessage)
                                        {
                                            if (dict["tags"].Length > 0)
                                            {
                                                // change the tag names into tag numbers for AddMessage
                                                string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                                bool bRet = dict.Remove("tags");
                                                dict.Add("tags", tagsNumstrs);
                                            }
                                            dict.Add("folderId", folder.FolderPath);
                                            try
                                            {
                                                stat = api.AddMessage(dict);
                                                if (stat != 0)
                                                {
                                                    string errMsg = (api.LastError.IndexOf("upload ID: null") != -1)    // FBS bug 75159 -- 6/7/12
                                                                    ? "Unable to upload file. Please check server message size limits."
                                                                    : api.LastError;
                                                    if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                    {
                                                        errMsg = errMsg + " Try Back later";
                                                        options.IsMaintainenceMode = true;
                                                    }

                                                    Acct.LastProblemInfo = new ProblemInfo(dict["Subject"], errMsg, ProblemInfo.TYPE_ERR);
                                                    Acct.TotalErrors++;
                                                    bError = true;
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Acct.TotalErrors++;
                                                Log.err("Exception caught in ProcessItems->api.AddMessage", e.Message);

                                            }
                                        }
                                    }
                                    else if (type == foldertype.Contacts)
                                    {
                                        //Log.debug("Contact Firstname: ", dict["firstName"]);
                                        if (dict["tags"].Length > 0)
                                        {
                                            // change the tag names into tag numbers for CreateContact
                                            string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                            bool bRet = dict.Remove("tags");
                                            dict.Add("tags", tagsNumstrs);
                                        }
                                        try
                                        {
                                            stat = api.CreateContact(dict, path);

                                            if (stat != 0)
                                            {
                                                string errMsg = api.LastError;
                                                if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                {
                                                    errMsg = errMsg + " Try Back later";

                                                    options.IsMaintainenceMode = true;
                                                }
                                                Log.err("Error in CreateContact ", errMsg);
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            Acct.TotalErrors++;
                                            Log.err("Exception caught in ProcessItems->api.CreateContact", e.Message);


                                        }
                                    }
                                    else if (type == foldertype.Calendar)
                                    {
                                        //Log.debug("Cal Subject: ", dict["su"]);
                                        if ((options.DateFilter != null))
                                        //if (options.DateFilter != null)
                                        {
                                            try
                                            {
                                                DateTime dtm = DateTime.Parse(dict["sFilterDate"]);
                                                DateTime filterDtm = Convert.ToDateTime(options.DateFilter);
                                                if ((options.DateFilterItem != null))
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) && (options.DateFilterItem.Contains(itemtype)))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- appointment older than date filter value");
                                                    }
                                                }
                                                else
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) )
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- appointment older than date filter value");
                                                    }
                                                }
                                            }
                                            catch (Exception)
                                            {
                                                Log.info(dict["su"], ": unable to parse date");
                                            }
                                        }
                                        if (!bSkipMessage)
                                        {
                                            try
                                            {
                                                if (dict["tags"].Length > 0)
                                                {
                                                    // change the tag names into tag numbers for AddAppointment
                                                    string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                                    bool bRet = dict.Remove("tags");
                                                    dict.Add("tags", tagsNumstrs);
                                                }
                                                dict.Add("accountNum", Acct.AccountNum.ToString());
                                                stat = api.AddAppointment(dict, path);
                                                if (stat != 0)
                                                {
                                                    string errMsg = api.LastError;
                                                    if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                    {
                                                        errMsg = errMsg + " Try Back later";

                                                        options.IsMaintainenceMode = true;
                                                    }
                                                    Acct.LastProblemInfo = new ProblemInfo(dict["su"], errMsg,
                                                                                           ProblemInfo.TYPE_ERR);
                                                    Acct.TotalErrors++;
                                                    bError = true;


                                                }

                                            }
                                            catch (Exception e)
                                            {
                                                Acct.LastProblemInfo = new ProblemInfo(dict["su"], e.Message,
                                                                                       ProblemInfo.TYPE_ERR);
                                                Acct.TotalErrors++;
                                                Log.err(dict["su"], "exception caught in ProcessItems->api.AddAppointment", e.Message);

                                            }
                                        }
                                    }
                                    else if (type == foldertype.Task)
                                    {
                                        //Log.debug("Task Subject: ", dict["su"]);
                                        if ((options.DateFilter != null))
                                        //if (options.DateFilter != null)
                                        {
                                            try
                                            {
                                                DateTime dtm = DateTime.Parse(dict["sFilterDate"]);
                                                DateTime filterDtm = Convert.ToDateTime(options.DateFilter);
                                                if ((options.DateFilterItem != null))
                                                {

                                                    if ((DateTime.Compare(dtm, filterDtm) < 0) && (options.DateFilterItem.Contains(itemtype)))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- task older than date filter value");
                                                    }
                                                }
                                                else
                                                {
                                                    if ((DateTime.Compare(dtm, filterDtm) < 0))
                                                    {
                                                        bSkipMessage = true;
                                                        Log.debug("Skipping", dict["su"], "-- task older than date filter value");
                                                    }

                                                }
                                            }
                                            catch (Exception)
                                            {

                                                Log.info(dict["su"], ": unable to parse date");
                                            }
                                        }
                                        if (!bSkipMessage)
                                        {
                                            if (dict["tags"].Length > 0)
                                            {
                                                // change the tag names into tag numbers for AddTask
                                                string tagsNumstrs = DealWithTags(dict["tags"], Acct, api);
                                                bool bRet = dict.Remove("tags");
                                                dict.Add("tags", tagsNumstrs);
                                            }
                                            try
                                            {
                                                stat = api.AddTask(dict, path);

                                                if (stat != 0)
                                                {
                                                    string errMsg = api.LastError;
                                                    if (errMsg.Contains("maintenance") || errMsg.Contains("not active"))
                                                    {
                                                        errMsg = errMsg + " Try Back later";

                                                        options.IsMaintainenceMode = true;
                                                    }

                                                    Log.err("error in AddTask ", errMsg);


                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                Acct.TotalErrors++;
                                                Log.err("exception caught in ProcessItems->api.AddTask", e.Message);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    Acct.LastProblemInfo = new ProblemInfo(Acct.AccountName, "Error on message",
                                                                           ProblemInfo.TYPE_ERR);
                                    Acct.TotalErrors++;
                                    bError = true;
                                }

                                if (!bError)
                                {
                                    // Note the : statement.  It seems weird to set Acct.migrationFolder.CurrentCountOFItems
                                    // to itself, but this is done so the method will be called to increment the progress bar
                                    Acct.migrationFolder.CurrentCountOfItems = bSkipMessage
                                        ? Acct.migrationFolder.CurrentCountOfItems
                                        : Acct.migrationFolder.CurrentCountOfItems + 1;
                                }
                            }
                            if (historyid != "")
                            {
                                try
                                {

                                    File.AppendAllText(historyfile, historyid); //uncomment after more testing
                                    File.AppendAllText(historyfile, "\n");

                                }
                                catch (Exception e)
                                {
                                    Acct.TotalErrors++;
                                    Log.err("CSmigrationwrapper  Exception caught in ProcessItems writing history to the history file", e.Message);
                                }
                                
                            }
                            iProcessedItems++;
                        }
                    }
                    /*if (Acct.IsCompletedMigration == false)
                    {
                        ProcessItems(Acct, isServer, user, folder, api, path, options);
                    }*/
                }
            }
        }
        else
        {
            Log.err("CSmigrationwrapper --- GetItemsForFolder returned null for itemfolderlist");
            return;
        }
        Log.info("CSmigrationwrapper -- processItems trial # ", trial);
    } while((!(Acct.IsCompletedMigration)) &&(trial < options.MaxRetries));

}

    public void StartMigration(MigrationAccount Acct, MigrationOptions options, bool isServer = true,
        LogLevel logLevel = LogLevel.Info, bool isPreview = false, bool doRulesAndOOO = true)      
    {
        string accountName = "";
        dynamic[] folders = null;
        int idx = Acct.AccountName.IndexOf("@");
        Log.Level level = (Log.Level)logLevel;
        dynamic user = null;
        string value = "";

        options.IsMaintainenceMode = false;

        if (MailClient == "MAPI")
        {
            user = new MapiUser();
        }
        if (!isServer)
        {
            m_umUser = user;
        }
        if (idx == -1)
        {
            Acct.LastProblemInfo = new ProblemInfo(Acct.AccountName, "Illegal account name",
                ProblemInfo.TYPE_ERR);
            Acct.TotalErrors++;
            return;
        }
        else
        {
            accountName = Acct.AccountName.Substring(0, idx);
        }

        Log.init(Path.GetTempPath() + "migration.log", level);  // might have gotten a new level from options
        InitLogFile(accountName, level);
        try
        {
            value = user.Init(isServer ? "host" : "", Acct.AccountID, accountName);
        }
        catch (Exception e)
        {
            string s = string.Format("Initialization Exception. {0}", e.Message);

            Acct.LastProblemInfo = new ProblemInfo(accountName, s, ProblemInfo.TYPE_ERR);
            Acct.TotalErrors++;
            return;
        }
        Log.info("Account name", accountName);
        Log.info("Account Id", Acct.AccountID);
        Log.info("Account Num", Acct.AccountNum.ToString());

        if (value.Length > 0)
        {
            Acct.IsValid = false;
            Log.err("Unable to initialize", accountName, value +"or verify if source mailbox exists.");
            Acct.LastProblemInfo = new ProblemInfo(accountName, value + " Or Verify if source mailbox exists.", ProblemInfo.TYPE_ERR);
            Acct.TotalErrors++;
            //Acct.TotalErrors = Acct.MaxErrorCount;
            return;
        }
        else
        {
            Acct.IsValid = true;
            Acct.IsCompletedMigration = true;
            Log.info(accountName, "initialized");
        }

        // set up check for skipping folders
        List<string> skipList = new List<string>();

        if (options.SkipFolders != null && options.SkipFolders.Length > 0)
        {
            string[] tokens = options.SkipFolders.Split(',');

            for (int i = 0; i < tokens.Length; i++)
            {
                string token = tokens.GetValue(i).ToString();

                skipList.Add(token.Trim());
            }
        }

        Log.debug("Retrieving folders");
        try
        {

            folders = user.GetFolders();
        }
        catch (Exception e)
        {
            Log.err("exception in startmigration->user.GetFolders", e.Message);
            
        }
        if (folders != null)
        {
            Log.info("CSmigrationwrapper get folders returned folders");
            if (folders.Count() == 0)
            {

                Log.info("No folders for user to migrate");
                return;
            }
        }
        else
        {
            Log.err("CSmigrationwrapper get folders returned null for folders");
            return;
        }
        Acct.migrationFolder.CurrentCountOfItems = folders.Count();
        Log.debug("Retrieved folders.  Count:", Acct.migrationFolder.CurrentCountOfItems.ToString());

        foreach (dynamic folder in folders) {
            if (!SkipFolder(options, skipList, folder))
                Acct.TotalItems += folder.ItemCount;
        }
        Log.info("Acct.TotalItems=", Acct.TotalItems.ToString());
        ZimbraAPI api;
        if (options.LangID != 0)
            api = new ZimbraAPI(isServer, logLevel, options.SpecialCharRep, options.LangID);
        else
            api = new ZimbraAPI(isServer, logLevel, options.SpecialCharRep);

        api.AccountID = Acct.AccountID;
        api.AccountName = Acct.AccountName;

        api.GetTags();
        foreach (TagInfo taginfo in ZimbraValues.GetZimbraValues().Tags)
        {
            try
            {

                Acct.tagDict.Add(taginfo.TagName, taginfo.TagID);
            }
            catch (Exception e)
            {
                Log.err("Exception in Add tags :",e.Message);
            }
        }
        
        foreach (dynamic folder in folders)
        {
            string path = "";

            if (options.IsMaintainenceMode)
            {
                Log.err("Cancelling migration -- Mailbox is in maintainence  mode.try back later");
                return;
            }


            if (options.MaxErrorCnt > 0)
            {
                if (Acct.TotalErrors > options.MaxErrorCnt)
                {
                    Log.err("Cancelling migration -- error threshold reached");
                    return;
                }
            }

            if (SkipFolder(options, skipList, folder))
            {
                Log.info("Skipping folder", folder.Name);
                continue;
            }
            Log.info("Processing folder", folder.Name);
            if (folder.Id == 0)
            {
                string ViewType = GetFolderViewType(folder.ContainerClass);
                try
                {

                    int stat = api.CreateFolder(folder.FolderPath, ViewType);
                }
                catch (Exception e)
                {
                    Log.err("Exception in api.CreateFolder in Startmigration ", e.Message);
                }

                path = folder.FolderPath;
            }
            if (folder.ItemCount == 0)
            {
                Log.info("Skipping empty folder", folder.Name);
                continue;
            }
            // Set FolderName at the end, since we trigger results on that, so we need all the values set
            Acct.migrationFolder.TotalCountOfItems = folder.ItemCount;
            Acct.migrationFolder.CurrentCountOfItems = 0;
            Acct.migrationFolder.FolderView = folder.ContainerClass;
            Acct.migrationFolder.FolderName = folder.Name;
            if (folder.Id == (int)ZimbraFolders.Trash)
            {
                path = "/MAPIRoot/Deleted Items";   // FBS EXCHANGE SPECIFIC HACK !!!
            }
            if (!isPreview)
            {
                ProcessItems(Acct, isServer, user, folder, api, path, options);

                
            }
        }

        // now do Rules
        if ((options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.Rules)) && (doRulesAndOOO))
        {
            string[,] data  = null;
            try
            {
                data = user.GetRules();
            }
            catch (Exception e)
            {
                Log.err("Exception in StartMigration->user.Getrules", e.Message);
            }
            if (data != null)
            {
                Acct.TotalItems++;
                Acct.migrationFolder.TotalCountOfItems = 1;
                Acct.migrationFolder.CurrentCountOfItems = 0;
                Acct.migrationFolder.FolderView = "All Rules";
                Acct.migrationFolder.FolderName = "Rules Table";
                api.AccountID = Acct.AccountID;
                api.AccountName = Acct.AccountName;
                if (!isPreview)
                {
                    Dictionary<string, string> dict = new Dictionary<string, string>();
                    int bound0 = data.GetUpperBound(0);
                    if (bound0 > 0)
                    {
                        for (int i = 0; i <= bound0; i++)
                        {
                            string Key = data[0, i];
                            string Value = data[1, i];
                            try
                            {

                                dict.Add(Key, Value);
                            }
                            catch (Exception e)
                            {
                                string s = string.Format("Exception adding {0}/{1}: {2}", Key, Value, e.Message);
                                Log.warn(s);
                                // Console.WriteLine("{0}, {1}", so1, so2);
                            }
                        }
                    }
                        api.AccountID = Acct.AccountID;
                        api.AccountName = Acct.AccountName;
                        try
                        {
                            Log.info("Migrating Rules");
                            int stat = api.AddRules(dict);
                        }
                        catch (Exception e)
                        {
                            Acct.TotalErrors++;
                            Log.err("CSmigrationWrapper: Exception in AddRules ", e.Message);

                        }
                        Acct.migrationFolder.CurrentCountOfItems = 1;
                    }
                
            }
            else
            {
                Log.info("There are no rules to migrate");
            }
        }

        // now do OOO
        if ((options.ItemsAndFolders.HasFlag(ItemsAndFoldersOptions.OOO)) && (doRulesAndOOO))
        {
            bool isOOO = false;
            string ooo ="";
            try
            {
                ooo = user.GetOOO();
            }
            catch (Exception e)
            {
                Log.err("Exception in StartMigration->user.GetOOO", e.Message);
            }
            if (ooo.Length > 0)
            {
                isOOO = (ooo != "0:");
            }
            if (isOOO)
            {
                Acct.TotalItems++;
                Acct.migrationFolder.TotalCountOfItems = 1;
                Acct.migrationFolder.CurrentCountOfItems = 0;
                Acct.migrationFolder.FolderView = "OOO";
                Acct.migrationFolder.FolderName = "Out of Office";
                api.AccountID = Acct.AccountID;
                api.AccountName = Acct.AccountName;
                if (!isPreview)
                {
                    Log.info("Migrating Out of Office");
                    try
                    {

                        api.AddOOO(ooo, isServer);
                    }
                    catch (Exception e)
                    {
                        Acct.TotalErrors++;
                        Log.err("CSmigrationWrapper: Exception in AddOOO ", e.Message);
                    }
                }
            }
            else
            {
                Log.info("Out of Office state is off, and there is no message");
            }
        }
        try
        {
            user.Uninit();
        }
        catch (Exception e)
        {
            Log.err("Exception in user.Uninit ", e.Message);

        }
        if (!isServer)
        {
            m_umUser = null;
        }
    }

    private bool CheckifAlreadyMigrated(string filename, string itemid)
    {

        List<string> parsedData = new List<string>();

        try
        {

            if (File.Exists(filename))
            {
                using (StreamReader readFile = new StreamReader(filename))
                {
                    string line;

                    string row;
                    while ((line = readFile.ReadLine()) != null)
                    {
                        row = line;
                        if (row.CompareTo(itemid) == 0)
                        {
                            return true;
                        }
                    }
                    readFile.Close();
                    return false;
                }

            }
        }
        catch (Exception e)
        {
            Log.err("CSmigrationwrapper  CheckifAlreadyMigrated method errored out", e.Message);

        }

        return false;
    }
} 


}
