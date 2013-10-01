﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Microsoft.Win32;

namespace CssLib
{
    public class CompatibilityChk
    {


        public enum MachineType : ushort
        {
            IMAGE_FILE_MACHINE_UNKNOWN = 0x0,
            IMAGE_FILE_MACHINE_AM33 = 0x1d3,
            IMAGE_FILE_MACHINE_AMD64 = 0x8664,
            IMAGE_FILE_MACHINE_ARM = 0x1c0,
            IMAGE_FILE_MACHINE_EBC = 0xebc,
            IMAGE_FILE_MACHINE_I386 = 0x14c,
            IMAGE_FILE_MACHINE_IA64 = 0x200,
            IMAGE_FILE_MACHINE_M32R = 0x9041,
            IMAGE_FILE_MACHINE_MIPS16 = 0x266,
            IMAGE_FILE_MACHINE_MIPSFPU = 0x366,
            IMAGE_FILE_MACHINE_MIPSFPU16 = 0x466,
            IMAGE_FILE_MACHINE_POWERPC = 0x1f0,
            IMAGE_FILE_MACHINE_POWERPCFP = 0x1f1,
            IMAGE_FILE_MACHINE_R4000 = 0x166,
            IMAGE_FILE_MACHINE_SH3 = 0x1a2,
            IMAGE_FILE_MACHINE_SH3DSP = 0x1a3,
            IMAGE_FILE_MACHINE_SH4 = 0x1a6,
            IMAGE_FILE_MACHINE_SH5 = 0x1a8,
            IMAGE_FILE_MACHINE_THUMB = 0x1c2,
            IMAGE_FILE_MACHINE_WCEMIPSV2 = 0x169,
        }
        public static MachineType GetDllMachineType(string dllPath)
        {
            // http://www.microsoft.com/whdc/system/platform/firmware/PECOFF.mspx 

            FileStream fs = new FileStream(dllPath, FileMode.Open, FileAccess.Read);
            BinaryReader br = new BinaryReader(fs);
            fs.Seek(0x3c, SeekOrigin.Begin);
            Int32 peOffset = br.ReadInt32();
            fs.Seek(peOffset, SeekOrigin.Begin);
            UInt32 peHead = br.ReadUInt32();
            if (peHead != 0x00004550) // "PE\0\0", little-endian 
                throw new Exception("Can't find PE header");
            MachineType machineType = (MachineType)br.ReadUInt16();
            br.Close();
            fs.Close();
            return machineType;
        }

        // returns true if the dll is 64-bit, false if 32-bit, and null if unknown 
        public static bool? UnmanagedDllIs64Bit(string dllPath)
        {
            switch (GetDllMachineType(dllPath))
            {
                case MachineType.IMAGE_FILE_MACHINE_AMD64:
                case MachineType.IMAGE_FILE_MACHINE_IA64:
                    return true;
                case MachineType.IMAGE_FILE_MACHINE_I386:
                    return false;
                default:
                    return null;
            }
        }


        public static string CheckCompat(string path)
        {
            string status = "";
            string absolutepath = Path.GetFullPath("Exchange.dll");

            bool retval = UnmanagedDllIs64Bit(absolutepath).Value;


            string Bitness = (string)Microsoft.Win32.Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Office\14.0\Outlook", "Bitness", null);
            if (Bitness != null)
            {
                if ((Bitness == "x64") && (!retval))
                {

                    status = "Outlook is 64 bit and migration is 32 bit";

                }

            }
            return status;

        }


    }

    public class MapiMigration : MailMigration
    {
       // public dynamic MapiWrapper;
        public Exchange.MapiWrapper MapiWrapper;
        static public string  checkPrereqs()
        {
            string str = "";
            string path  = Directory.GetDirectoryRoot(Directory.GetCurrentDirectory());
            string absolutepath = Path.GetFullPath("Exchange.dll");

            bool bitness = CompatibilityChk.UnmanagedDllIs64Bit(absolutepath).Value;

           // string InstallPath = (string)Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Office\14\Outlook\", "Bitness", null);
          /* RegistryKey mykey =  Registry.LocalMachine.OpenSubKey(@"SOFTWARE\Microsoft\Office\14.0\Outlook");
            string InstallPath= null;
            //if( mykey != null)
            {
           InstallPath = (string)mykey.GetValue("test");
            }*/

            string InstallPath = (string)Registry.GetValue(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Office\14.0\Outlook", "Bitness", null);
            if (InstallPath != null)
            {
                //its 64 bit outlook and 64 bit migration

                if (bitness == true)
                {
                    if (InstallPath == "x64")
                    {
                        //64 bit mapi and 64 bit migration perfect
                    }
                    else
                    {
                        if (InstallPath == "x86")
                        {
                            //32 bit mapi and 64 bit migration cannot continue
                            str = "The 64 bit Migration wizard is not compatible with MAPI 32 bit libraries.  Please run the 32 bit Migration wizard.";
                            return str;
                        }
                        
                    }

                }
               else
                {
                    
                    if (InstallPath == "x64")
                    {
                        //64 bit mapi and 32 bit bit migration cannot continue
                        str = "The 32 bit Migration wizard is not compatible with MAPI 64 bit libraries.  Please run the 64 bit Migration wizard.";
                        return str;

                    }
                    else
                    {
                        if (InstallPath == "x86")
                        {
                            //32 bit mapi and 32 bit migration perfect
                            
                        }

                    }

                   
                }
            }
            else
            {
                if (bitness == true)
                {
                    //32 bit mapi and 64 bitmigration
                    str = "Older versions of Outlook are not compatible with the 64 bit Migration wizard.  Please run the 32 bit Migration wizard.";
                    return str;
                }
                else
                {

                    //32 bit mapi and 32 bit migration.
                }


            }

          
            return str;

        }
        public  MapiMigration()
        {
            string message = MapiMigration.checkPrereqs();
            if (message == "")
           // try
            {

                MapiWrapper = new Exchange.MapiWrapper();
            }
           else
           // catch(Exception )
            {
                //Log.err("Exception in CSMigrationWrapper construcor", message);
               // throw new Exception(message);
                
                throw new Exception(message);
            }
            

             
        }

        public override string GlobalInit(string Target, string AdminUser, string AdminPassword)
        {
        
        string s = "";

        try
        {
            s = MapiWrapper.GlobalInit(Target, AdminUser, AdminPassword);
        }
        catch (Exception e)
        {
            s = string.Format("Initialization Exception. Make sure to enter the proper credentials: {0}", e.Message);
        }
        return s;

        }


        public override string GlobalUninit()
        {
            try
            {
                return MapiWrapper.GlobalUninit();
            }
            catch (Exception e)
            {
                string msg = string.Format("GlobalUninit Exception: {0}", e.Message);
                return msg;
            }


        }

	public override bool AvoidInternalErrors(string strErr)
	{
	    return (MapiWrapper.AvoidInternalErrors(strErr)!=0);
	}

        public override string GetProfilelist(out object  var)
        {
            
            return MapiWrapper.GetProfilelist(out var);

        }
        public override string SelectExchangeUsers(out object var)
        {
            return MapiWrapper.SelectExchangeUsers(out var);

        }
    }

   public class MapiUser : MigrationUser
    {
       public Exchange.UserObject UserObj;
       public Exchange.UserObject GetInternalUser()
       {

           return UserObj;
       }
        public MapiUser()
        {
            UserObj = new Exchange.UserObject();
        }
        public override string Init(string host, string AccountID, string accountName)
        {

            string value ="";
            try
            {
                value = UserObj.Init(host, AccountID, accountName);
            }
            catch (Exception e)
            {
                Log.err(" MAPIMigration: Exception in UserObj.init", e.Message);
            }
            return value;
        }

        public override dynamic[] GetFolders()
        {
            dynamic[] folders = null;
            try
            {
                folders = UserObj.GetFolders();
                
            }
            catch (Exception e)
            {
                Log.err(" MApiMigration::Getfolders exception", e.Message);
            }

            return folders;
        }

        public override dynamic[] GetItemsForFolder(dynamic folderobject, double Date)
        {
            dynamic[] Itemsfolder = null;
            try
            {
                Itemsfolder = UserObj.GetItemsForFolder(folderobject, Date);
            }
            catch (Exception e)
            {
                Log.err(" MApiMigration::Getfolders exception", e.Message);
            }
            return Itemsfolder;

        }

        public override string[,] GetRules()
        {

            try
            {

                return UserObj.GetRules();
            }

            catch (Exception e)
            {

                Log.err("MAPIMigration: Exception caught in GetRules", e.Message);
                return null;
            }
        }
        public override string GetOOO()
        {
            return UserObj.GetOOO();
        }

        public override void Uninit()
        {
            try
            {


                UserObj.Uninit();
            }
             
            catch(Exception e)
             {
                 Log.err(" MAPIMigration: Exception caught in Uninit", e.Message);
            }
        }



    }

    /*class MapiItem : MigrationItem
    {
        public dynamic ItemObj;
        public dynamic ItemID()
        {
            return ItemObj.ItemID;
        }
        public dynamic Type()
        {
            return ItemObj.Type;
        }
        public MapiItem()
        {
             ItemObj= new Exchange.ItemObject();

        }
        public override string[,]  GetDataForItemID(dynamic userobj, dynamic ItemId, dynamic type)
        {
            return ItemObj.GetDataForItemID( userobj,  ItemId,  type);

        }

    }*/
}
