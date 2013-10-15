using CssLib;
using MVVM.Model;
using Misc;
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
public class ConfigViewModelSDest: BaseViewModel
{
    public ConfigViewModelSDest()
    {
        this.LoadCommand = new ActionCommand(this.Load, () => true);
        this.SaveCommand = new ActionCommand(this.Save, () => true);
        this.BackCommand = new ActionCommand(this.Back, () => true);
        this.NextCommand = new ActionCommand(this.Next, () => true);
    }
    public ICommand LoadCommand {
        get;
        private set;
    }
    public void LoadConfig(Config config)
    {
        ZmailServerHostName = config.ZmailServer.Hostname;
        ZmailPort = config.ZmailServer.Port;
        ZmailAdmin = config.ZmailServer.AdminID;
        ZmailAdminPasswd = config.ZmailServer.AdminPwd;
        ZmailSSL = config.ZmailServer.UseSSL;
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
                    MessageBox.Show(temp, "Zmail Migration", MessageBoxButton.OK, MessageBoxImage.Error);
                    fileRead.Close();
                    return;
                }
                
                fileRead.Close();

                try
                {
                    LoadConfig(config);
                    ((ConfigViewModelS)ViewModelPtrs[(int)ViewType.SVRSRC]).LoadConfig(config);
                    ((OptionsViewModel)ViewModelPtrs[(int)ViewType.OPTIONS]).LoadConfig(config);
                    ((UsersViewModel)ViewModelPtrs[(int)ViewType.USERS]).LoadDomain(config);
                    ((ScheduleViewModel)ViewModelPtrs[(int)ViewType.SCHED]).SetConfigFile(fDialog.FileName);
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
        lb.SelectedIndex = 1;
    }
    public ICommand NextCommand {
        get;
        private set;
    }
    private void Next()
    {
        if ((this.ZmailServerHostName.Length == 0) || (this.ZmailPort.Length == 0))
        {
            MessageBox.Show("Please fill in the host name and port", "Zmail Migration",
                MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }

        ZmailAPI zmailAPI = new ZmailAPI(true);
        int stat = -1;
        try
        {
            stat = zmailAPI.Logon(this.ZmailServerHostName, this.ZmailPort, this.ZmailAdmin,
                this.ZmailAdminPasswd, this.ZmailSSL, true);
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message, "Logon", MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }

        if (stat == 0)
        {
            string authToken = ZmailValues.GetZmailValues().AuthToken;

            if (authToken.Length > 0)
            {
                UsersViewModel usersViewModel =
                    ((UsersViewModel)ViewModelPtrs[(int)ViewType.USERS]);
                ScheduleViewModel scheduleViewModel =
                    ((ScheduleViewModel)ViewModelPtrs[(int)ViewType.SCHED]);
                string currentDomain = (usersViewModel.DomainList.Count > 0) ?
                    usersViewModel.DomainList[usersViewModel.CurrentDomainSelection] : "";

                usersViewModel.DomainList.Clear();
                scheduleViewModel.CosList.Clear();
                zmailAPI.GetAllDomains();
                for (int i = 0; i < ZmailValues.GetZmailValues().Domains.Count; i++)
                {
                    string s = ZmailValues.GetZmailValues().Domains[i];

                    usersViewModel.DomainList.Add(s);
                    // if we've loaded a config file where the domain was specified, then set it as selected
                    if (currentDomain != null)
                    {
                        if (currentDomain.Length > 0)
                        {
                            if (s == currentDomain)
                                usersViewModel.CurrentDomainSelection = i;
                            usersViewModel.DomainsFilledIn = true;
                        }
                    }
                }
                zmailAPI.GetAllCos();
                foreach (CosInfo cosinfo in ZmailValues.GetZmailValues().COSes)
                {
                    scheduleViewModel.CosList.Add(new CosInfo(cosinfo.CosName, cosinfo.CosID));
                }
                lb.SelectedIndex = 3;
            }
        }
        else
        {
            MessageBox.Show(string.Format("Logon Unsuccessful: {0}", zmailAPI.LastError),
                "Zmail Migration", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
    public string ZmailPort {
        get { return m_config.ZmailServer.Port; }
        set
        {
            if (value == m_config.ZmailServer.Port)
                return;
            m_config.ZmailServer.Port = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailPort"));
        }
    }
    public string ZmailServerHostName {
        get { return m_config.ZmailServer.Hostname; }
        set
        {
            if (value == m_config.ZmailServer.Hostname)
                return;
            m_config.ZmailServer.Hostname = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailServerHostName"));
        }
    }
    public string ZmailAdmin {
        get { return m_config.ZmailServer.AdminID; }
        set
        {
            if (value == m_config.ZmailServer.AdminID)
                return;
            m_config.ZmailServer.AdminID = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailAdmin"));
        }
    }
    public string ZmailAdminPasswd {
        get { return m_config.ZmailServer.AdminPwd; }
        set
        {
            if (value == m_config.ZmailServer.AdminPwd)
                return;
            m_config.ZmailServer.AdminPwd = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailAdminPasswd"));
        }
    }
    public bool ZmailSSL {
        get { return m_config.ZmailServer.UseSSL; }
        set
        {
            if (value == m_config.ZmailServer.UseSSL)
                return;
            m_config.ZmailServer.UseSSL = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailSSL"));
        }
    }
}
}
