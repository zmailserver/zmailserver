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
public class ConfigViewModelUDest: BaseViewModel
{
    public ConfigViewModelUDest()
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
        ZmailUser = config.ZmailServer.UserAccount;
        ZmailUserPasswd = config.ZmailServer.UserPassword;
        ZmailSSL = config.ZmailServer.UseSSL;
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
                    ((ConfigViewModelU)ViewModelPtrs[(int)ViewType.USRSRC]).LoadConfig(config);
                    ((OptionsViewModel)ViewModelPtrs[(int)ViewType.OPTIONS]).LoadConfig(config);
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
        try
        {
            System.Net.IPAddress address = System.Net.IPAddress.Parse(ZmailServerHostName);
            MessageBox.Show("Please enter a valid host name rather than an IP address",
                "Zmail Migration", MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }
        catch (Exception)
        {}
        ZmailAPI zmailAPI = new ZmailAPI(false);

        int stat = -1;
        try
        {
            stat = zmailAPI.Logon(this.ZmailServerHostName, this.ZmailPort, this.ZmailUser,
                this.ZmailUserPasswd, this.ZmailSSL, false);
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
                zmailAPI.GetInfo();
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
    public string ZmailUser {
        get { return m_config.ZmailServer.UserAccount; }
        set
        {
            if (value == m_config.ZmailServer.UserAccount)
                return;
            m_config.ZmailServer.UserAccount = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailUser"));
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
    public string ZmailUserPasswd {
        get { return m_config.ZmailServer.UserPassword; }
        set
        {
            if (value == m_config.ZmailServer.UserPassword)
                return;
            m_config.ZmailServer.UserPassword = value;

            OnPropertyChanged(new PropertyChangedEventArgs("ZmailUserPasswd"));
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
