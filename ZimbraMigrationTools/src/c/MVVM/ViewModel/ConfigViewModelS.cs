﻿using CssLib;
using MVVM.Model;
using Misc;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Input;
using System.Windows;
using System;

namespace MVVM.ViewModel
{
public class ConfigViewModelS: BaseViewModel
{
    internal const int PROFILE_MODE = 1;
    internal const int EXCHSVR_MODE = 2;

    public ConfigViewModelS()
    {
        this.LoadCommand = new ActionCommand(this.Load, () => true);
        this.SaveCommand = new ActionCommand(this.Save, () => true);
        this.BackCommand = new ActionCommand(this.Back, () => true);
        this.NextCommand = new ActionCommand(this.Next, () => true);
        Isprofile = true;
        IsmailServer = false;
        CSEnableNext = false;
        iMailSvrInitialized = -1;
    }
    public ICommand LoadCommand {
        get;
        private set;
    }
    public void LoadConfig(Config config)
    {
        if (config.SourceServer.UseProfile)
        {
            Isprofile = true;
            IsmailServer = false;
            OutlookProfile = config.SourceServer.Profile;
            if (ProfileList.Count > 0)
                CurrentProfileSelection = (OutlookProfile == null) ? 0 : ProfileList.IndexOf(
                    OutlookProfile);

            else
                ProfileList.Add(OutlookProfile);
        }
        else
        {
            Isprofile = false;
            IsmailServer = true;
            MailServerHostName = config.SourceServer.Hostname;
            MailServerAdminID = config.SourceServer.AdminID;
            MailServerAdminPwd = config.SourceServer.AdminPwd;
        }
        savedDomain = config.UserProvision.DestinationDomain;
    }

    private void Load()
    {
        System.Xml.Serialization.XmlSerializer reader =
            new System.Xml.Serialization.XmlSerializer(typeof (Config));

        Microsoft.Win32.OpenFileDialog fDialog = new Microsoft.Win32.OpenFileDialog();
        fDialog.Filter = "Config Files|*.xml";
        fDialog.CheckFileExists = true;
        fDialog.Multiselect = false;
        if (fDialog.ShowDialog() == true)
        {
            if (File.Exists(fDialog.FileName))
            {
                System.IO.StreamReader fileRead = new System.IO.StreamReader(fDialog.FileName);

                Config config = new Config();

                try
                {
                    config = (Config)reader.Deserialize(fileRead);
                }
                catch (Exception e)
                {
                    string temp = string.Format("Incorrect configuration file format.\n{0}", e.Message);
                    MessageBox.Show(temp, "Zimbra Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                    fileRead.Close();
                    return;
                }

                fileRead.Close();

                try
                {
                    LoadConfig(config);
                    ((ConfigViewModelSDest)ViewModelPtrs[(int)ViewType.SVRDEST]).LoadConfig(config);
                    ((OptionsViewModel)ViewModelPtrs[(int)ViewType.OPTIONS]).LoadConfig(config);
                    ((UsersViewModel)ViewModelPtrs[(int)ViewType.USERS]).LoadDomain(config);
                    ((ScheduleViewModel)ViewModelPtrs[(int)ViewType.SCHED]).SetConfigFile(fDialog.FileName);
                    if ((IsProfile) && (CurrentProfileSelection == -1))
                    {
                        MessageBox.Show("The profile listed in the file does not exist on this system.  Please select a valid profile",
                                        "Zimbra Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                        return;
                    }
                }
                catch (Exception e)
                {
                    DisplayLoadError(e);
                    return;
                }
            }
        }
    }
    public ICommand SaveCommand {
        get;
        private set;
    }

    private void Save()
    {
        Microsoft.Win32.SaveFileDialog fDialog = new Microsoft.Win32.SaveFileDialog();
        fDialog.Filter = "Config Files|*.xml";
        if (fDialog.ShowDialog() == true)
        {
            System.Xml.Serialization.XmlSerializer writer =
                new System.Xml.Serialization.XmlSerializer(typeof(Config));

            System.IO.StreamWriter file = new System.IO.StreamWriter(fDialog.FileName);
            PopulateConfig(isServer);
            writer.Serialize(file, m_config);
            file.Close();

            ((ScheduleViewModel)ViewModelPtrs[(int)ViewType.SCHED]).SetConfigFile(
                fDialog.FileName);
        }
    }
    public ICommand BackCommand {
        get;
        private set;
    }
    private void Back()
    {
        lb.SelectedIndex = 0;
    }
    public ICommand NextCommand {
        get;
        private set;
    }
    private void Next()
    {
        string ret = "";
        CSMigrationWrapper mw = ((IntroViewModel)ViewModelPtrs[(int)ViewType.INTRO]).mw;

        if (IsProfile)
        {
            if (CurrentProfileSelection == -1)
            {
                MessageBox.Show("Please select a valid profile", "Zimbra Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            if (iMailSvrInitialized == EXCHSVR_MODE)
            {
                MessageBox.Show("You are already logged in via Exchange Server credentials",
                    "Zimbra Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            if (iMailSvrInitialized == -1)
                ret = mw.GlobalInit(ProfileList[CurrentProfileSelection], "", "");
        }
        else
        {
            if (iMailSvrInitialized == PROFILE_MODE)
            {
                MessageBox.Show("You are already logged in via an Outlook Profile",
                    "Zimbra Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            if (iMailSvrInitialized == -1)
            {
                if ((MailServerHostName.Length == 0) || (MailServerAdminID.Length == 0) ||
                    (MailServerAdminPwd.Length == 0))
                {
                    MessageBox.Show("Please enter all source mail server credentials",
                        "Zimbra Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                    return;
                }
                ret = mw.GlobalInit(MailServerHostName, MailServerAdminID, MailServerAdminPwd);
            }
        }
        if (ret.Length > 0)
        {
            MessageBox.Show(ret, "Zimbra Migration", MessageBoxButton.OK,
                MessageBoxImage.Error);
            ret = mw.GlobalUninit();
            return;
        }
        iMailSvrInitialized = (IsProfile) ? PROFILE_MODE : EXCHSVR_MODE;

        lb.SelectedIndex = 2;
    }

    private int iMailSvrInitialized;
    private bool IsProfile;
    private bool IsMailServer;
    public bool IsMailServerInitialized {
        get { return iMailSvrInitialized != -1; }
    }
    public bool IsmailServer {
        get { return IsMailServer; }
        set
        {
            IsMailServer = value;
            CSEnableNext = (IsMailServer) ? true : ProfileList.Count > 0;
            OnPropertyChanged(new PropertyChangedEventArgs("IsmailServer"));
        }
    }
    public bool Isprofile {
        get { return IsProfile; }
        set
        {
            IsProfile = value;
            CSEnableNext = (IsProfile) ? ProfileList.Count > 0 : true;
            OnPropertyChanged(new PropertyChangedEventArgs("Isprofile"));
        }
    }
    public string OutlookProfile {
        get { return m_config.SourceServer.Profile; }
        set
        {
            if (value == m_config.SourceServer.Profile)
                return;
            m_config.SourceServer.Profile = value;
            OnPropertyChanged(new PropertyChangedEventArgs("OutlookProfile"));
        }
    }
    private ObservableCollection<string> profilelist = new ObservableCollection<string>();
    public ObservableCollection<string> ProfileList {
        get { return profilelist; }
        set
        {
            profilelist = value;
        }
    }
    public int CurrentProfileSelection {
        get { return profileselection; }
        set
        {
            profileselection = value;
            CSEnableNext = (value != -1);

            OnPropertyChanged(new PropertyChangedEventArgs("CurrentProfileSelection"));
        }
    }
    private int profileselection;
    public string MailServerHostName {
        get { return m_config.SourceServer.Hostname; }
        set
        {
            if (value == m_config.SourceServer.Hostname)
                return;
            m_config.SourceServer.Hostname = value;

            OnPropertyChanged(new PropertyChangedEventArgs("MailServerHostName"));
        }
    }
    public string MailServerAdminID {
        get { return m_config.SourceServer.AdminID; }
        set
        {
            if (value == m_config.SourceServer.AdminID)
                return;
            m_config.SourceServer.AdminID = value;

            OnPropertyChanged(new PropertyChangedEventArgs("MailServerAdminID"));
        }
    }
    public string MailServerAdminPwd {
        get { return m_config.SourceServer.AdminPwd; }
        set
        {
            if (value == m_config.SourceServer.AdminPwd)
                return;
            m_config.SourceServer.AdminPwd = value;

            OnPropertyChanged(new PropertyChangedEventArgs("MailServerAdminPwd"));
        }
    }
    private bool csenableNext;
    public bool CSEnableNext {
        get { return csenableNext; }
        set
        {
            csenableNext = value;
            OnPropertyChanged(new PropertyChangedEventArgs("CSEnableNext"));
        }
    }
}
}
