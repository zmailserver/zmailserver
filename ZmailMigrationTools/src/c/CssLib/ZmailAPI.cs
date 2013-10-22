﻿using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Xml.Linq;
using System.Xml;
using System;

namespace CssLib
{
public class ZimbraAPI
{
    // Errors
    internal const int OOO_NO_TEXT = 92;
    internal const int TASK_CREATE_FAILED_FLDR = 93;
    internal const int APPT_CREATE_FAILED_FLDR = 94;
    internal const int CONTACT_CREATE_FAILED_FLDR = 95;
    internal const int FOLDER_CREATE_FAILED_SYN = 96;
    internal const int FOLDER_CREATE_FAILED_SEM = 97;
    internal const int ACCOUNT_NO_NAME = 98;
    internal const int ACCOUNT_CREATE_FAILED = 99;
    //

    // Upload modes
    public const int STRING_MODE = 1;           // for messages -- request is all string data
    public const int CONTACT_MODE = 2;          // for contacts -- with pictures
    public const int APPT_VALUE_MODE = 3;       // for appts -- with binary
    public const int APPT_EMB_MODE = 4;         // for appts -- embedded messages
         
    //

    // Values
    internal const int INLINE_LIMIT = 4000;     // smaller than this limit, we'll inline; larger, we'll upload

    //string[] specialFolders = new string[20];//{};
    string[] specialFolders = {
        "", "/MAPIRoot", "/MAPIRoot/Inbox",
        "/MAPIRoot/Deleted Items",
        "/MAPIRoot/Junk E-Mail", "/MAPIRoot/Sent Items",
        "/MAPIRoot/Drafts", "/MAPIRoot/Contacts",
        "/MAPIRoot/Tags", "/MAPIRoot/Conversations",
        "/MAPIRoot/Calendar", "", "/MAPIRoot/Wiki",
        "/MAPIRoot/Emailed Contacts", "/MAPIRoot/Chats",
        "/MAPIRoot/Tasks"
    };
    Dictionary<string ,string > specialFoldersMap = new Dictionary<string,string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/Posteingang","2"},
        {"/MAPIRoot/Papierkorb","3"},
        {"/MAPIRoot/Spam","4"}, {"/MAPIRoot/Gesendet","5"},
        {"/MAPIRoot/Entw\u00fcrfe","6"}, {"/MAPIRoot/Kontakte","7"},
        {"/MAPIRoot/Unterhaltungen","9"},
        {"/MAPIRoot/Kalender","10"}, 
        {"/MAPIRoot/Mailempf\u00e4nger","13"},{"/MAPIRoot/Aufgaben","15"},
       
};
    Dictionary<string, string> specialFoldersMap_es = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},{"/MAPIRoot/Bandeja de entrada","2"},
        {"/MAPIRoot//Papelera","4"},
        {"/MAPIRoot/Enviados","5"},
{"/MAPIRoot/Borradores","6"},
{"/MAPIRoot/Contactos","7"},
 {"/MAPIRoot/Etiquetas","8"},
 {"/MAPIRoot/Agenda","10"},
{"/MAPIRoot/Tareas","15"},
{"/MAPIRoot/Conversaciones","9"},
 {"/MAPIRoot/Contactos respondidos","13"},
{"/MAPIRoot/Chat","14"}};

    Dictionary<string, string> specialFoldersMap_da = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/Indbakke","2"},{"/MAPIRoot/Papirkurv","3"},
        {"/MAPIRoot/U\u00f8nsket","4"}, {"/MAPIRoot/Sendt","5"},
        {"/MAPIRoot/Kladder","6"}, {"/MAPIRoot/Kontakter","7"},
        {"/MAPIRoot/M\u00e6rkater","8"}, {"/MAPIRoot/Samtaler","9"},
        {"/MAPIRoot/Kalender","10"},
        {"/MAPIRoot/Kontakter, der er sendt mail til","13"},
        {"/MAPIRoot/Opgaver","15"}
    };


    Dictionary<string, string> specialFoldersMap_fr = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Bo\u00eete de R\u00e9ception","2"},{"/MAPIRoot/Corbeille","3"},
        {"/MAPIRoot/Spam","4"}, {"/MAPIRoot/Envoy\u00e9","5"},
        {"/MAPIRoot/Brouillons","6"},
        {"/MAPIRoot/libell\u00e9s","8"},
        {"/MAPIRoot/Calendrier","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Personnes contact\u00e9es par mail","13"}, {"/MAPIRoot/Discussions","14"},
        {"/MAPIRoot/T\u00e2ches","15"},
    
        {"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, 
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
    
     };

    Dictionary<string, string> specialFoldersMap_ITA = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/In arrivo","2"},{"/MAPIRoot/Cestino","3"},
        {"/MAPIRoot/Posta indesiderata","4"}, {"/MAPIRoot/Inviati","5"},
        {"/MAPIRoot/Bozze","6"}, {"/MAPIRoot/Contatti","7"},
        {"/MAPIRoot/Tag","8"}, {"/MAPIRoot/Conversazioni","9"},
        {"/MAPIRoot/Agenda","10"}, 
        {"/MAPIRoot/Contatti usati per email","13"}, {"/MAPIRoot/Chat","14"},
        {"/MAPIRoot/Impegni","15"}};

   
    Dictionary<string, string> specialFoldersMap_Ma = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/Peti Masuk","2"},{"/MAPIRoot/Sampah","3"},
        {"/MAPIRoot/Sarap","4"}, {"/MAPIRoot/Dihantar","5"},
        {"/MAPIRoot/Draf","6"}, {"/MAPIRoot/Kenalan","7"},
        {"/MAPIRoot/Tag","8"}, {"/MAPIRoot/Perbualan","9"},
        {"/MAPIRoot/Kalendar","10"}, 
        {"/MAPIRoot/Kenalan Dihantar E-mel","13"}, {"/MAPIRoot/Sembang","14"},
        {"/MAPIRoot/Tugas","15"}};


    Dictionary<string, string> specialFoldersMap_nl = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/Postvak IN","2"},{"/MAPIRoot/Prullenbak","3"},
        {"/MAPIRoot/Junk","4"}, {"/MAPIRoot/Verzonden","5"},
        {"/MAPIRoot/Concepten","6"}, {"/MAPIRoot/Contacten","7"},
        {"/MAPIRoot/Labels","8"}, {"/MAPIRoot/Conversaties","9"},
        {"/MAPIRoot/Agenda","10"}, 
        {"/MAPIRoot/Gemailde contacten","13"},
        {"/MAPIRoot/Taken","15"}

    };
   Dictionary<string, string> specialFoldersMap_pl = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Skrzynka odbiorcza","2"},{"/MAPIRoot/Kosz","3"},
        {"/MAPIRoot/Spam","4"}, {"/MAPIRoot/Wys\u0142ano","5"},
        {"/MAPIRoot/Kopie robocze","6"}, {"/MAPIRoot/Kontakty","7"},
        {"/MAPIRoot/Znaczniki","8"}, {"/MAPIRoot/Konwersacje","9"},
        {"/MAPIRoot/Kalendarz","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Czaty","14"},
        {"/MAPIRoot/Zadania","15"},
        {"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, 
        {"/MAPIRoot/Kontakty, do kt\u00f3rych wys\u0142ano wiadomo\u015b\u0107 e-mail","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"}};

     Dictionary<string, string> specialFoldersMap_pt = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/Entrada","2"},{"/MAPIRoot/Lixeira","3"},
        {"/MAPIRoot/Spam","4"}, {"/MAPIRoot/Enviadas","5"},
        {"/MAPIRoot/Rascunhos","6"}, {"/MAPIRoot/Contatos","7"},
        {"/MAPIRoot/Marcadores","8"}, {"/MAPIRoot/T\u00f3picos de conversa\u00e7\u00e3o","9"},
        {"/MAPIRoot/Agenda","10"}, 
        {"/MAPIRoot/Contatos que receberam e-mail","13"}, {"/MAPIRoot/Bate-papos","14"},
        {"/MAPIRoot/Tarefas","15"}};

   
     Dictionary<string, string> specialFoldersMap_ro = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},{"/MAPIRoot/Co\u015f de gunoi","3"},
        {"/MAPIRoot/Mesaje spam","4"}, {"/MAPIRoot/Trimise","5"},
        {"/MAPIRoot/Ciorne","6"}, {"/MAPIRoot/Contacte","7"},
        {"/MAPIRoot/Etichete","8"}, {"/MAPIRoot/Conversa\u0163ii","9"},
        
        {"/MAPIRoot/Contacte destinatare","13"}, {"/MAPIRoot/Discu\u0163ii","14"},
        {"/MAPIRoot/ Activit\u0103\u0163i","15"}};
   


    Dictionary<string, string> specialFoldersMap_sv = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"},
        {"/MAPIRoot/Inkorgen","2"},{"/MAPIRoot/Papperskorg","3"},
        {"/MAPIRoot/Skr\u00e4p","4"}, {"/MAPIRoot/Skickat","5"},
        {"/MAPIRoot/Utkast","6"}, {"/MAPIRoot/Kontakter","7"},
        {"/MAPIRoot/Etiketter","8"}, {"/MAPIRoot/Konversationer","9"},
        {"/MAPIRoot/Kalender","10"}, 
        {"/MAPIRoot/E-postkontakter","13"}, {"/MAPIRoot/Chattar","14"},
        {"/MAPIRoot/Uppgifter","15"}};


   /* Dictionary<string, string> specialFoldersMap_tr = new Dictionary<string, string>()
    {{"","0"},{"/MAPIRoot","1"},{"/MAPIRoot/Gelen Kutusu","2"},{"/MAPIRoot/\u00c7\u00f6p Kutusu","3"},
        {"/MAPIRoot/\u0130stenmeyen Posta","4"}, {"/MAPIRoot/G\u00f6nderilenler","5"},
        {"/MAPIRoot/Taslaklar","6"}, {"/MAPIRoot/Ki\u015filer","7"},
        {"/MAPIRoot/Etiketler","8"}, {"/MAPIRoot/Sohbetler","9"},
        {"/MAPIRoot/Takvim","10"}, {"/MAPIRoot/Wiki","12"},
        {"/MAPIRoot/E-posta G\u00f6nderilmi\u015f Ki\u015filer","13"}, {"/MAPIRoot/Sohbetler","14"},
        {"/MAPIRoot/G\u00f6revler","15"}};
    
        {"/MAPIRoot/Inbox","2"},{"/MAPIRoot/Deleted Items","3"},
        {"/MAPIRoot/Junk E-Mail","4"}, {"/MAPIRoot/Sent Items","5"},
        {"/MAPIRoot/Drafts","6"}, {"/MAPIRoot/Contacts","7"},
        {"/MAPIRoot/Tags","8"}, {"/MAPIRoot/Conversations","9"},
        {"/MAPIRoot/Calendar","10"}, 
        {"/MAPIRoot/Emailed Contacts","13"}, {"/MAPIRoot/Chats","14"},
        {"/MAPIRoot/Tasks","15"}};*/

    

    
    /*string[] DespecialFolders = {
        "", "/MAPIRoot", "/MAPIRoot/Posteingang",
        "/MAPIRoot/Papierkorb",
        "/MAPIRoot/Spam", "/MAPIRoot/Gesendet",
        "/MAPIRoot/Entw\u00fcrfe", "/MAPIRoot/Kontakte",
        "/MAPIRoot/Tags", "/MAPIRoot/Unterhaltungen",
        "/MAPIRoot/Kalender", "", "/MAPIRoot/Wiki",
        "/MAPIRoot/Mailempf\u00e4nger", "/MAPIRoot/Chats",
        "/MAPIRoot/Aufgaben"
    };*/
    char[] specialCharacters = { ':','/','"'};


    private string lastError;
    public string LastError {
        get { return lastError; }
        set
        {
            lastError = value;
        }
    }
    private string sAccountName;
    public string AccountName {
        get { return sAccountName; }
        set
        {
            sAccountName = value;
        }
    }
    private string sAccountID;
    public string AccountID
    {
        get { return sAccountID; }
        set
        {
            sAccountID = value;
        }
    }
    private bool bIsAdminAccount;
    public bool IsAdminAccount {
        get { return bIsAdminAccount; }
        set
        {
            bIsAdminAccount = value;
        }
    }
    private bool bIsDomainAdminAccount;
    public bool IsDomainAdminAccount {
        get { return bIsDomainAdminAccount; }
        set
        {
            bIsDomainAdminAccount = value;
        }
    }
    private bool bIsServerMigration;
    public bool IsServerMigration
    {
        get { return bIsServerMigration; }
        set
        {
            bIsServerMigration = value;
        }
    }

    private long LangID;

    public long Langid
    {
        get { return LangID; }
        set { LangID = value; }
    }

    private Dictionary<string, string> dFolderMap;
    

    private LogLevel loglevel;

    private string ReplaceSlash;

    public ZimbraAPI(bool isServer, LogLevel level = LogLevel.Info, string replaceslash = "_", long lang = 1033)
    {
        bIsServerMigration = isServer;
        loglevel = level;
        dFolderMap = new Dictionary<string, string>();
        ReplaceSlash = replaceslash;

        Langid = lang;
       /* switch (lang)
        {
            case 1033:
                {
                    Array.Copy(EnspecialFolders, specialFolders, EnspecialFolders.Length);

                } break;

            case 1031:
                {
                    Array.Copy(DespecialFolders, specialFolders, EnspecialFolders.Length);

                } break;

            default:
                {

                }
                break;

        }*/
        
        
    }

    private string GetSpecialFolderNum(string folderPath)
    {
        string sFolderPath = folderPath.ToUpper();
        //for pst migration
        if (sFolderPath == "/MAPIROOT/TRASH")
        {
            return "3"; //same as MAPIRoot/Deleted Items
        }
        if (sFolderPath == "/MAPIROOT/SENT")
        {
            return "5";//same as MAPIRoot/Sent Items
        }
      /*  for (int i = 0; i < specialFolders.Length; i++)
        {
            string sSpecialFolder = specialFolders[i].ToUpper();
            if (sFolderPath == sSpecialFolder)
                return i.ToString();
        }*/
        switch(Langid)
        {
            case 1033:
                {
                    for (int i = 0; i < specialFolders.Length; i++)
                    {
                        string sSpecialFolder = specialFolders[i].ToUpper();
                        if (sFolderPath == sSpecialFolder)
                            return i.ToString();
                    }

                    break;
                }

            case 1031:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap.ContainsKey(tempstr))
                            return specialFoldersMap[tempstr];
                        else
                            return "";
                    }

                   
                }
            case 3082:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_es.ContainsKey(tempstr))
                            return specialFoldersMap_es[tempstr];
                        else
                            return "";
                    }


                }

            case 1030:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_da.ContainsKey(tempstr))
                            return specialFoldersMap_da[tempstr];
                        else
                            return "";
                    }


                }

            case 1036:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_fr.ContainsKey(tempstr))
                            return specialFoldersMap_fr[tempstr];
                        else
                            return "";
                    }


                }
            case 1040:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_ITA.ContainsKey(tempstr))
                            return specialFoldersMap_ITA[tempstr];
                        else
                            return "";
                    }


                }

            case 1086:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_Ma.ContainsKey(tempstr))
                            return specialFoldersMap_Ma[tempstr];
                        else
                            return "";
                    }


                }

            case 1043:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_nl.ContainsKey(tempstr))
                            return specialFoldersMap_nl[tempstr];
                        else
                            return "";
                    }


                }

            case 1045:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_pl.ContainsKey(tempstr))
                            return specialFoldersMap_pl[tempstr];
                        else
                            return "";
                    }


                }

            case 1046:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_pt.ContainsKey(tempstr))
                            return specialFoldersMap_pt[tempstr];
                        else
                            return "";
                    }


                }

            case 1048:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_ro.ContainsKey(tempstr))
                            return specialFoldersMap_ro[tempstr];
                        else
                            return "";
                    }


                }

            case 1053:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_sv.ContainsKey(tempstr))
                            return specialFoldersMap_sv[tempstr];
                        else
                            return "";
                    }


                }

           /* case 1055:
                {
                    {
                        string tempstr = folderPath;
                        if (specialFoldersMap_tr.ContainsKey(tempstr))
                            return specialFoldersMap_tr[tempstr];
                        else
                            return "";
                    }


                }*/


            default:
                {
                    
                        for (int i = 0; i < specialFolders.Length; i++)
                        {
                            string sSpecialFolder = specialFolders[i].ToUpper();
                            if (sFolderPath == sSpecialFolder)
                                return i.ToString();
                        }

                        break;
                    
                }
               

        }

        return "";
        
       /* string tempstr = folderPath;
        if (specialFoldersMap.ContainsKey(tempstr))
            return specialFoldersMap[tempstr];
        else
            return "";*/
    }

    // Parse Methods //////////////////
    // [note that we don't have Parse methods for CreateContact, CreateFolder, etc.]
    private string ParseSoapFault(string rsperr)
    {
        if (rsperr.Length == 0)
            return "";
        if (rsperr.IndexOf("<soap:Fault>") == -1)
            return "";

        string soapReason = "";
        XDocument xmlDoc = XDocument.Parse(rsperr);
        XNamespace ns = "http://www.w3.org/2003/05/soap-envelope";

        IEnumerable<XElement> de = from el in xmlDoc.Descendants() select el;

        foreach (XElement el in de)
            if (el.Name == ns + "Reason")
            {
                soapReason = el.Value;
                break;
            }
        return soapReason;
    }

    private void ParseLogon(string rsp, bool isAdmin)
    {
        string authToken = "";
        string isDomainAdmin = "false";

        if (rsp != null)
        {
            int startIdx = rsp.IndexOf("<authToken>");

            if (startIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = (isAdmin) ? "urn:zimbraAdmin" : "urn:zimbraAccount";

                // we'll have to deal with this -- need to figure this out later -- with GetInfo
                // for now, just faking -- always setting admin stuff to false if not admin -- not right
                foreach (var objIns in xmlDoc.Descendants(ns + "AuthResponse"))
                {
                    authToken += objIns.Element(ns + "authToken").Value;
                    isDomainAdmin = "false";
                    if (isAdmin)
                    {
                        var x = from a in objIns.Elements(ns + "a") where a.Attribute(
                            "n").Value == "zimbraIsDomainAdminAccount" select a.Value;

                        if (x.Any())    // FBS bug 72777
                        {
                            isDomainAdmin = x.ElementAt(0);
                        }
                    }
                }
            }
        }
        ZimbraValues.GetZimbraValues().AuthToken = authToken;
    }

    private void ParseGetInfo(string rsp)
    {
        string accountName = "";
        string serverVersion = "";

        if (rsp != null)
        {
            int startNameIdx = rsp.IndexOf("<name>");
            int startVersionIdx = rsp.IndexOf("<version>");

            if ((startNameIdx != -1) && (startVersionIdx != -1))
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraAccount";

                foreach (var objIns in xmlDoc.Descendants(ns + "GetInfoResponse"))
                {
                    accountName += objIns.Element(ns + "name").Value;
                    serverVersion += objIns.Element(ns + "version").Value;
                }
            }
        }
        ZimbraValues.GetZimbraValues().ServerVersion = serverVersion;
        ZimbraValues.GetZimbraValues().AccountName = accountName;
    }

    private int ParseGetAccount(string rsp)
    {
        int retval = 0;

        if (rsp != null)
        {
            int dIdx = rsp.IndexOf("account id=");

            if (dIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraAdmin";

                foreach (var objIns in xmlDoc.Descendants(ns + "GetAccountResponse"))
                {
                    foreach (XElement accountIns in objIns.Elements())
                    {
                        foreach (XAttribute accountAttr in accountIns.Attributes())
                        {
                            if (accountAttr.Name == "name")
                            {
                                retval = (accountAttr.Value).Length;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return retval;
    }

    private int ParseCreateAccount(string rsp)
    {
        int retval = 0;

        if (rsp != null)
        {
            int dIdx = rsp.IndexOf("account id=");

            if (dIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraAdmin";

                foreach (var objIns in xmlDoc.Descendants(ns + "CreateAccountResponse"))
                {
                    foreach (XElement accountIns in objIns.Elements())
                    {
                        foreach (XAttribute accountAttr in accountIns.Attributes())
                        {
                            if (accountAttr.Name == "name")
                            {
                                retval = (accountAttr.Value).Length;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return retval;
    }

    private void ParseGetAllDomain(string rsp)
    {
        if (rsp != null)
        {
            int dIdx = rsp.IndexOf("domain");

            if (dIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraAdmin";

                foreach (var objIns in xmlDoc.Descendants(ns + "GetAllDomainsResponse"))
                {
                    foreach (XElement domainIns in objIns.Elements())
                    {
                        foreach (XAttribute domainAttr in domainIns.Attributes())
                        {
                            if (domainAttr.Name == "name")
                                ZimbraValues.GetZimbraValues().Domains.Add(domainAttr.Value);
                        }
                    }
                }
            }
        }
    }

    private void ParseGetAllCos(string rsp)
    {
        if (rsp != null)
        {
            int dIdx = rsp.IndexOf("cos");

            if (dIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraAdmin";

                foreach (var objIns in xmlDoc.Descendants(ns + "GetAllCosResponse"))
                {
                    foreach (XElement cosIns in objIns.Elements())
                    {
                        string name = "";
                        string id = "";

                        foreach (XAttribute cosAttr in cosIns.Attributes())
                        {
                            if (cosAttr.Name == "name")
                                name = cosAttr.Value;
                            if (cosAttr.Name == "id")
                                id = cosAttr.Value;
                        }
                        if ((name.Length > 0) || (id.Length > 0))
                            ZimbraValues.GetZimbraValues().COSes.Add(new CosInfo(name, id));
                    }
                }
            }
        }
    }

    private void ParseGetTag(string rsp)
    {
        if (rsp != null)
        {
            int dIdx = rsp.IndexOf("tag");

            if (dIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraMail";

                foreach (var objIns in xmlDoc.Descendants(ns + "GetTagResponse"))
                {
                    foreach (XElement tagIns in objIns.Elements())
                    {
                        string name = "";
                        string id = "";

                        foreach (XAttribute tagAttr in tagIns.Attributes())
                        {
                            if (tagAttr.Name == "name")
                                name = tagAttr.Value;
                            if (tagAttr.Name == "id")
                                id = tagAttr.Value;
                        }
                        if ((name.Length > 0) || (id.Length > 0))
                            ZimbraValues.GetZimbraValues().Tags.Add(new TagInfo(name, id));
                    }
                }
            }
        }
    }

    // may not need this -- it's here anyway for now
    private void ParseAddMsg(string rsp, out string mID)
    {
        mID = "";
        if (rsp != null)
        {
            int midIdx = rsp.IndexOf("m id");

            if (midIdx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraMail";

                foreach (var objIns in xmlDoc.Descendants(ns + "AddMsgResponse"))
                {
                    foreach (XElement mIns in objIns.Elements())
                    {
                        foreach (XAttribute mAttr in mIns.Attributes())
                        {
                            if (mAttr.Name == "id")
                                mID = mAttr.Value;
                        }
                    }
                }
            }
        }
    }

    private void ParseCreateFolder(string rsp, out string folderID)
    {
        folderID = "";
        if (rsp != null)
        {
            int idx = rsp.IndexOf("id=");

            if (idx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraMail";

                foreach (var objIns in xmlDoc.Descendants(ns + "CreateFolderResponse"))
                {
                    foreach (XElement folderIns in objIns.Elements())
                    {
                        foreach (XAttribute mAttr in folderIns.Attributes())
                        {
                            if (mAttr.Name == "id")
                                folderID = mAttr.Value;
                        }
                    }
                }
            }
        }
    }

    private void ParseCreateTag(string rsp, out string tagID)
    {
        tagID = "";
        if (rsp != null)
        {
            int idx = rsp.IndexOf("tag id=");

            if (idx != -1)
            {
                XDocument xmlDoc = XDocument.Parse(rsp);
                XNamespace ns = "urn:zimbraMail";

                foreach (var objIns in xmlDoc.Descendants(ns + "CreateTagResponse"))
                {
                    foreach (XElement tagIns in objIns.Elements())
                    {
                        foreach (XAttribute mAttr in tagIns.Attributes())
                        {
                            if (mAttr.Name == "id")
                                tagID = mAttr.Value;
                        }
                    }
                }
            }
        }
    }

    // ////////

    // private UploadFile method
    private int UploadFile(string filepath, string mimebuffer, string contentdisposition, string contenttype, int mode, out string uploadToken)
    {
        bool isSecure = (ZimbraValues.GetZimbraValues().Url).Substring(0, 5) == "https";
        WebServiceClient client = (isSecure) ? new WebServiceClient {
            Url = "https://" + ZimbraValues.GetZimbraValues().HostName + ":" +
                ZimbraValues.GetZimbraValues().Port + "/service/upload?fmt=raw",
            WSServiceType = WebServiceClient.ServiceType.Traditional
        } : new WebServiceClient {
            Url = "http://" + ZimbraValues.GetZimbraValues().HostName + ":" +
                ZimbraValues.GetZimbraValues().Port + "/service/upload?fmt=raw",
            WSServiceType = WebServiceClient.ServiceType.Traditional
        };
        int retval = 0;
        string rsp = "";

        uploadToken = "";

        client.InvokeUploadService(ZimbraValues.GetZimbraValues().AuthToken, IsServerMigration, filepath, mimebuffer,
            contentdisposition, contenttype, mode, out rsp);
        retval = client.status;
        if (retval == 0)
        {
            int li = rsp.LastIndexOf(",");

            if (li != -1)
            {
                // get the string with the upload token, which will have a leading ' and a trailing '\r\n -- so strip that stuff off
                int uti = li + 1;               // upload token index
                string tmp = rsp.Substring(uti, (rsp.Length - uti));
                int lastsinglequoteidx = tmp.LastIndexOf("'");

                uploadToken = tmp.Substring(1, (lastsinglequoteidx - 1));
            }
        }
        return retval;
    }

    //

    // private API helper methods

    // example: <name>foo</name>
    private void WriteNVPair(XmlWriter writer, string name, string value)
    {
        writer.WriteStartElement(name);
        writer.WriteValue(value);
        writer.WriteEndElement();
    }

    // example: <a n="displayName">bar</a>
    private void WriteAttrNVPair(XmlWriter writer, string fieldType, string fieldName, string
        attrName, string attrValue)
    {
        writer.WriteStartElement(fieldType);
        writer.WriteStartAttribute(fieldName);
        writer.WriteString(attrName);
        writer.WriteEndAttribute();
        writer.WriteValue(attrValue);
        writer.WriteEndElement();
    }

    // example: <account by="name">foo@bar.com</account>
    private void WriteAccountBy(XmlWriter writer, string val)
    {
        WriteAttrNVPair(writer, "account", "by", "name", val);
    }

    private void WriteHeader(XmlWriter writer, bool bWriteSessionId, bool bWriteAuthtoken, bool
        bWriteAccountBy)
    {
        writer.WriteStartElement("Header", "http://www.w3.org/2003/05/soap-envelope");
        writer.WriteStartElement("context", "urn:zimbra");
        writer.WriteStartElement("nonotify");
        writer.WriteEndElement();               // nonotify
        writer.WriteStartElement("noqualify");
        writer.WriteEndElement();               // noqualify
        writer.WriteStartElement("nosession");
        writer.WriteEndElement();               // nosession
        if (bWriteSessionId)
        {
            writer.WriteStartElement("sessionId");
            writer.WriteEndElement();           // sessionId
        }
        if (bWriteAuthtoken)
            WriteNVPair(writer, "authToken", ZimbraValues.zimbraValues.AuthToken);
        if (bWriteAccountBy)                    // would only happen after a logon
            WriteAccountBy(writer, AccountName);
        writer.WriteEndElement();               // context
        writer.WriteEndElement();               // header
    }

    //

    // API methods /////////
    public int Logon(string hostname, string port, string username, string password, bool
        isSecure, bool isAdmin)
    {
        Log.trace("ZimbraAPI - Logon");
        if (ZimbraValues.GetZimbraValues().AuthToken.Length > 0)
            return 0;                           // already logged on
        lastError = "";

        // FBS Bug 73394 -- 4/26/12 -- rewrite this section
        string mode = isSecure ? "https://"            : "http://";
        string svc  = isAdmin  ? "/service/admin/soap" : "/service/soap";
        string urn  = isAdmin  ? "urn:zimbraAdmin"     : "urn:zimbraAccount";
        ZimbraValues.GetZimbraValues().Url = mode + hostname + ":" + port + svc;
        // end Bug 73394

        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, false, false, false);

            // body
            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("AuthRequest", urn);
            if (isAdmin)
                WriteNVPair(writer, "name", username);
            else
                WriteAccountBy(writer, username);
            WriteNVPair(writer, "password", password);

            writer.WriteEndElement();           // AuthRequest
            writer.WriteEndElement();           // soap body
            // end body

            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";

        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        WriteSoapLog(rsp.ToString(),false);
        
        if (client.status == 0)
        {
            ParseLogon(rsp, isAdmin);
            ZimbraValues.GetZimbraValues().HostName = hostname;
            ZimbraValues.GetZimbraValues().Port = port;
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return client.status;
    }

    public int GetInfo()
    {
        Log.trace("ZimbraAPI - GetInfo");
        lastError = "";
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, false);

            // body
            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("GetInfoRequest", "urn:zimbraAccount");
            writer.WriteAttributeString("sections", "mbox");
            writer.WriteEndElement();           // GetInfoRequest
            writer.WriteEndElement();           // soap body
            // end body

            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        //Log.dump("Soap Request for GetInfo", sb.ToString());
        client.InvokeService(sb.ToString(), out rsp);
        //Log.dump("Soap Response for GetInfo", rsp.ToString());
        WriteSoapLog(rsp.ToString(),false);
        if (client.status == 0)
        {
            ParseGetInfo(rsp);
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return client.status;
    }

    public int GetAllDomains()
    {
        Log.trace("ZimbraAPI - GetAllDomains");
        if (ZimbraValues.zimbraValues.Domains.Count > 0)        // already got 'em
            return 0;
        lastError = "";
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, false);

            // body
            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("GetAllDomainsRequest", "urn:zimbraAdmin");
            writer.WriteEndElement();           // GetAllDomainsRequest
            writer.WriteEndElement();           // soap body
            // end body

            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        if (client.status == 0)
        {
            ParseGetAllDomain(rsp);
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return client.status;
    }

    public int GetAllCos()
    {
        Log.trace("ZimbraAPI - GetAllCos");
        if (ZimbraValues.zimbraValues.COSes.Count > 0)  // already got 'em
            return 0;
        lastError = "";
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, false);

            // body
            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("GetAllCosRequest", "urn:zimbraAdmin");
            writer.WriteEndElement();           // GetAllCosRequest
            writer.WriteEndElement();           // soap body
            // end body

            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        if (client.status == 0)
        {
            ParseGetAllCos(rsp);
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return client.status;
    }

    public int GetAccount(string accountname)
    {
        Log.trace("ZimbraAPI - GetAccount");
        int retval = 0;

        lastError = "";
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, false);

            // body
            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("GetAccountRequest", "urn:zimbraAdmin");

            WriteAccountBy(writer, accountname);

            writer.WriteEndElement();           // GetAccountRequest
            writer.WriteEndElement();           // soap body
            // end body

            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        if (client.status == 0)
        {
            if (ParseGetAccount(rsp) == 0)      // length of name is 0 -- this is bad
                retval = ACCOUNT_NO_NAME;
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return retval;
    }

    public int CreateAccount(string accountname, string displayname, string givenname, string sn, string zfp, string defaultpw, bool mustChangePW, string cosid)
    {
        Log.trace("ZimbraAPI CreateAccount");
        int retval = 0;

        lastError = "";
        try
        {

            if (displayname.Length == 0)
            {
                displayname = accountname.Substring(0, accountname.IndexOf("@"));
            }
            string zimbraForeignPrincipal = (zfp.Length > 0) ? zfp : "ad:" + displayname;
            WebServiceClient client = new WebServiceClient
            {
                Url = ZimbraValues.GetZimbraValues().Url,
                WSServiceType =
                    WebServiceClient.ServiceType.Traditional
            };
            StringBuilder sb = new StringBuilder();
            XmlWriterSettings settings = new XmlWriterSettings();

            settings.OmitXmlDeclaration = true;
            using (XmlWriter writer = XmlWriter.Create(sb, settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("soap", "Envelope",
                    "http://www.w3.org/2003/05/soap-envelope");

                WriteHeader(writer, true, true, false);

                // body
                writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
                writer.WriteStartElement("CreateAccountRequest", "urn:zimbraAdmin");

                WriteNVPair(writer, "name", accountname);
                WriteNVPair(writer, "password", defaultpw);

                WriteAttrNVPair(writer, "a", "n", "displayName", displayname);
                if (givenname.Length > 0)
                {
                    WriteAttrNVPair(writer, "a", "n", "givenName", givenname);
                }
                if (sn.Length > 0)
                {
                    WriteAttrNVPair(writer, "a", "n", "sn", sn);
                }
                WriteAttrNVPair(writer, "a", "n", "zimbraForeignPrincipal", zimbraForeignPrincipal);
                WriteAttrNVPair(writer, "a", "n", "zimbraCOSId", cosid);
                if (mustChangePW)
                {
                    WriteAttrNVPair(writer, "a", "n", "zimbraPasswordMustChange", "TRUE");
                }

                writer.WriteEndElement();           // CreateAccountRequest
                writer.WriteEndElement();           // soap body
                // end body

                writer.WriteEndElement();           // soap envelope
                writer.WriteEndDocument();
            }

            string rsp = "";
            WriteSoapLog(sb.ToString(), true);

            client.InvokeService(sb.ToString(), out rsp);

            WriteSoapLog(rsp.ToString(), false);
            retval = client.status;
            if (client.status == 0)
            {
                if (ParseCreateAccount(rsp) == 0)   // length of name is 0 -- this is bad
                    retval = ACCOUNT_CREATE_FAILED;
            }
            else
            {
                string soapReason = ParseSoapFault(client.errResponseMessage);

                if (soapReason.Length > 0)
                    lastError = soapReason;
                else
                    lastError = client.exceptionMessage;
            }
        }
        catch (Exception e)
        {
            Log.err("ZimbraAPI: Exception in CreateAccount", e.Message);
        }
        return retval;

    }

    public void CreateContactRequest(XmlWriter writer, Dictionary<string, string> contact,
        string folderId, int requestId)
    {
        Log.trace("ZimbraAPI CreateContactRequest");
        bool IsGroup = false;
        writer.WriteStartElement("CreateContactRequest", "urn:zimbraMail");
        if (requestId != -1)
            writer.WriteAttributeString("requestId", requestId.ToString());
        writer.WriteStartElement("cn");
        writer.WriteAttributeString("l", folderId);
        if (contact["tags"].Length > 0)
        {
            writer.WriteAttributeString("t", contact["tags"]);
        }

        

        foreach (KeyValuePair<string, string> pair in contact)
        {
            string nam = pair.Key;
            string val = pair.Value;

            

            if (nam == "image")
            {
                if (val.Length > 0)
                {
                    string uploadToken = "";
                    string tmp = "";
                    //if (UploadFile(val, tmp, "", "", CONTACT_MODE, out uploadToken) == 0)

                    if (UploadFile(val, tmp, contact["imageContentDisp"], contact["imageContentType"], CONTACT_MODE, out uploadToken) == 0)
                    {
                        writer.WriteStartElement("a");
                        writer.WriteAttributeString("n", nam);
                        writer.WriteAttributeString("aid", uploadToken);
                        writer.WriteEndElement();
                    }
                    File.Delete(val);
                }
            }
            else
            {
                if ((nam.CompareTo("imageContentDisp")== 0) || (nam.CompareTo("imageContentType")==0))
                { }
                else
                    if (nam.CompareTo("dlist") == 0)
                    {
                        IsGroup = true;
                        //string[] tokens = contact["dlist"].Split(',');
                        string[] tokens = contact["dlist"].Split(';');

                        if (tokens.Length > 0)
                        {

                            for (int i = 0; i < tokens.Length; i++)
                            {
                                writer.WriteStartElement("m");
                                writer.WriteAttributeString("type", "I");
                                writer.WriteAttributeString("value", tokens.GetValue(i).ToString());

                                writer.WriteEndElement();
                            }
                        }
                        else
                        {
                            writer.WriteStartElement("m");
                            writer.WriteAttributeString("type", "I");
                            writer.WriteAttributeString("value",val);

                            writer.WriteEndElement();
                        }

                    }
                    else
                        WriteAttrNVPair(writer, "a", "n", nam, val);

            }
        }
        
        if(IsGroup)
        {

            string tempname = contact["fileAs"];
            int index = tempname.IndexOf(":");
            if (index > 0)
            {
                string nickname = tempname.Substring((index + 1));


                if (nickname.Length > 0)
                {
                   
                    WriteAttrNVPair(writer, "a", "n", "nickname", nickname);


                }
            }

        }
        writer.WriteEndElement();               // cn
        writer.WriteEndElement();               // CreateContactRequest
    }

    public int CreateContact(Dictionary<string, string> contact, string folderPath = "")
    {
        Log.trace("ZimbraAPI CreateContact");
        lastError = "";

        // Create in Contacts unless another folder was desired
        string folderId = "7";

        if (folderPath.Length > 0)
        {
            folderId = FindFolder(folderPath);
            if (folderId.Length == 0)
                return CONTACT_CREATE_FAILED_FLDR;
        }

        // //////
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        int retval = 0;
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");

            CreateContactRequest(writer, contact, folderId, -1);

            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        return retval;
    }

    public int CreateContacts(List<Dictionary<string, string> > lContacts, string folderPath =
        "")
    {
        Log.trace("ZimbraAPI CreateContacts");
        lastError = "";

        // Create in Contacts unless another folder was desired
        string folderId = "7";

        if (folderPath.Length > 0)
        {
            folderId = FindFolder(folderPath);
            if (folderId.Length == 0)
                return CONTACT_CREATE_FAILED_FLDR;
        }

        // //////
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        int retval = 0;
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("BatchRequest", "urn:zimbra");
            for (int i = 0; i < lContacts.Count; i++)
            {
                Dictionary<string, string> contact = lContacts[i];
                CreateContactRequest(writer, contact, folderId, i);
            }
            writer.WriteEndElement();           // BatchRequest
            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";

        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        return retval;
    }

    public void AddMsgRequest(XmlWriter writer, string uploadInfo, ZimbraMessage message, bool
        isInline, int requestId)
    {
        Log.trace("ZimbraAPI AddMsgRequest");
        // if isLine, uploadInfo will be a file path; if not, uploadInfo will be the upload token
        writer.WriteStartElement("AddMsgRequest", "urn:zimbraMail");
        if (requestId != -1)
            writer.WriteAttributeString("requestId", requestId.ToString());
        writer.WriteStartElement("m");
        writer.WriteAttributeString("l", message.folderId);
        writer.WriteAttributeString("d", message.rcvdDate);
        writer.WriteAttributeString("f", message.flags);
        if (message.tags.Length > 0)
        {
            writer.WriteAttributeString("t", message.tags);
        }
        if (isInline)
        {
            WriteNVPair(writer, "content", System.Text.Encoding.Default.GetString(
                File.ReadAllBytes(uploadInfo)));
        }
        else
        {
            writer.WriteAttributeString("aid", uploadInfo);
        }
        writer.WriteEndElement();               // m
        writer.WriteEndElement();               // AddMsgRequest
    }

    public int AddMessage(Dictionary<string, string> message)
    {
        Log.trace("ZimbraAPI AddMessage");
        lastError = "";

        string uploadInfo = "";
        int retval = 0;
        ZimbraMessage zm = new ZimbraMessage("", "", "", "", "");

        System.Type type = typeof (ZimbraMessage);
        FieldInfo[] myFields = type.GetFields(BindingFlags.Public | BindingFlags.Instance);
        for (int i = 0; i < myFields.Length; i++)       // use reflection to set ZimbraMessage object values
        {
            string nam = (string)myFields[i].Name;

            if (nam == "folderId")
                myFields[i].SetValue(zm, FindFolder(message[nam]));
            else
                myFields[i].SetValue(zm, message[nam]);
        }

        bool isInline = false;
        if (message["wstrmimeBuffer"].Length > 0)
        {
            //
        }
        else
        {
            FileInfo f = new FileInfo(zm.filePath);// use a try/catch?
            isInline = (f.Length < INLINE_LIMIT);
        }

        if (isInline)
            uploadInfo = zm.filePath;
        else
        {
            //Log.debug("Begin UploadFile");
            string tmp = message["wstrmimeBuffer"];
            retval = UploadFile(zm.filePath, tmp, "", "", STRING_MODE, out uploadInfo);
            //Log.debug("End UploadFile");
        }
        if (retval == 0)
        {
            WebServiceClient client = new WebServiceClient {
                Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                    WebServiceClient.ServiceType.Traditional
            };
            StringBuilder sb = new StringBuilder();
            XmlWriterSettings settings = new XmlWriterSettings();

            settings.OmitXmlDeclaration = true;
            using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
                writer.WriteStartDocument();
                writer.WriteStartElement("soap", "Envelope",
                    "http://www.w3.org/2003/05/soap-envelope");

                WriteHeader(writer, true, true, true);

                writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");

                AddMsgRequest(writer, uploadInfo, zm, isInline, -1);

                writer.WriteEndElement();       // soap body
                writer.WriteEndElement();       // soap envelope
                writer.WriteEndDocument();
            }

            string rsp = "";
            WriteSoapLog(sb.ToString(),true);
            
            client.InvokeService(sb.ToString(), out rsp);
            
            WriteSoapLog(rsp.ToString(),false);
            retval = client.status;
            if (client.status == 0)
            {
                string mID = "";

                ParseAddMsg(rsp, out mID);      // get the id
            }
            else
            {
                string soapReason = ParseSoapFault(client.errResponseMessage);
                string errMsg = (soapReason.IndexOf("upload ID: null") != -1)    // FBS bug 75159 -- 6/7/12
                                ? "Unable to upload file. Please check server message size limits (Global Settings General Information and MTA)."
                                : soapReason; 
                if (soapReason.Length > 0)
                {
                    lastError = soapReason;
                    Log.err("Error on message", message["Subject"], "--", errMsg);
                }
                else
                {
                    lastError = client.exceptionMessage;
                }
            }
        }
        //File.Delete(zm.filePath);
        return retval;
    }

    public int AddMessages(List<Dictionary<string, string> > lMessages)
    {
        Log.trace("ZimbraAPI AddMessages");
        int retval = 0;

        lastError = "";

        string uploadInfo = "";

        System.Type type = typeof (ZimbraMessage);
        FieldInfo[] myFields = type.GetFields(BindingFlags.Public | BindingFlags.Instance);
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            writer.WriteStartElement("BatchRequest", "urn:zimbra");
            for (int i = 0; i < lMessages.Count; i++)
            {
                Dictionary<string, string> message = lMessages[i];

                ZimbraMessage zm = new ZimbraMessage("", "", "", "", "");

                for (int j = 0; j < myFields.Length; j++)       // use reflection to set ZimbraMessage object values
                {
                    string nam = (string)myFields[j].Name;

                    if (nam == "folderId")
                        myFields[j].SetValue(zm, FindFolder(message[nam]));
                    else
                        myFields[j].SetValue(zm, message[nam]);
                }

                bool isInline = false;
                if (message["wstrmimeBuffer"].Length > 0)
                {
                    //
                }
                else
                {
                    FileInfo f = new FileInfo(zm.filePath);
                    isInline = (f.Length < INLINE_LIMIT);
                }

                if (isInline)
                    uploadInfo = zm.filePath;
                else
                {
                    Log.debug("Begin UploadFile");
                    string tmp = message["wstrmimeBuffer"];
                    retval = UploadFile(zm.filePath, tmp, "", "", STRING_MODE, out uploadInfo);
                    Log.debug("End UploadFile");
                }
                if (retval == 0)
                    AddMsgRequest(writer, uploadInfo, zm, isInline, -1);
                File.Delete(zm.filePath);
            }
            writer.WriteEndElement();           // BatchRequest
            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;

        return retval;
    }

    public void SetAppointmentRequest(XmlWriter writer, Dictionary<string, string> appt,
        string folderId)
    {
        Log.trace("ZimbraAPI SetAppointmentRequest");
        bool isRecurring = appt.ContainsKey("freq");
        int numExceptions = (appt.ContainsKey("numExceptions")) ? Int32.Parse(appt["numExceptions"]) : 0;
        writer.WriteStartElement("SetAppointmentRequest", "urn:zimbraMail");
        writer.WriteAttributeString("l", folderId);
        if (appt["tags"].Length > 0)
        {
            writer.WriteAttributeString("t", appt["tags"]);
        }
        writer.WriteStartElement("default");
        writer.WriteAttributeString("ptst", appt["ptst"]);
        writer.WriteStartElement("m");

        if (isRecurring)    // Timezone nodes if recurring appt
        {
            WriteTimezone(writer, appt);
        }

        writer.WriteStartElement("inv");
        writer.WriteAttributeString("method", "REQUEST");
        
        if (appt["fb"].Length > 0)
        {
            writer.WriteAttributeString("fb", appt["fb"]);
        }
        writer.WriteAttributeString("transp", appt["transp"]);

        if (appt["allDay"].Length > 0)
            writer.WriteAttributeString("allDay", appt["allDay"]);
        else
            writer.WriteAttributeString("allDay", "0");
        
        writer.WriteAttributeString("name", appt["name"]);
        writer.WriteAttributeString("loc", appt["loc"]);
        if (appt["class"].Length > 0)
        {
            if (appt["class"] == "1")
            {
                writer.WriteAttributeString("class", "PRI");
            }
        }
        if (appt["uid"].Length > 0)
        {
            writer.WriteAttributeString("uid", appt["uid"]);
        }

        writer.WriteStartElement("s");
        writer.WriteAttributeString("d", appt["s"]);
        if (isRecurring)
        {
            writer.WriteAttributeString("tz", appt["tid"]);
        }
        writer.WriteEndElement();

        writer.WriteStartElement("e");
        writer.WriteAttributeString("d", appt["e"]);
        if (isRecurring)
        {
            writer.WriteAttributeString("tz", appt["tid"]);
        }
        writer.WriteEndElement();

        writer.WriteStartElement("or");
        writer.WriteAttributeString("d", appt["orName"]);

       

        // always convert -- not like old tool that gives you a choice
        //string theOrganizer = AccountName;
        string theOrganizer = "";
        if (appt["orAddr"].Length > 0)
        {
            theOrganizer = AccountName;
            if (!IAmTheOrganizer(appt["orAddr"]))
            {
                theOrganizer = appt["orAddr"];
            }
        }
        else
        {
            if (appt["orName"].Length > 0)
            {
                theOrganizer = appt["orName"];

                /*int idxOrg = theOrganizer.IndexOf("@");
                if (idxOrg == -1)  // can happen if no recip table
                {
                    int idxAcct = AccountName.IndexOf("@");
                    string Name = AccountName.Substring(0, idxAcct);
                    if (Name == appt["orName"])
                    {
                        theOrganizer = AccountName;
                    }
                }*/
            }
            else
            {
                theOrganizer = AccountName;
                if (!IAmTheOrganizer(AccountName))
                {
                    theOrganizer = AccountName;
                }


            }
        }
        writer.WriteAttributeString("a", theOrganizer);
        writer.WriteEndElement();
        //

        if (appt.ContainsKey("attendees"))
        {
            string[] tokens = appt["attendees"].Split('~');
            for (int i = 0; i < tokens.Length; i += 4)
            {
                writer.WriteStartElement("at");
                writer.WriteAttributeString("d",    tokens.GetValue(i).ToString());
                writer.WriteAttributeString("a",    tokens.GetValue(i + 1).ToString());
                writer.WriteAttributeString("role", tokens.GetValue(i + 2).ToString());
                writer.WriteAttributeString("rsvp", appt["rsvp"]);
                if(appt["currst"] == "OR")
                {
                    if (tokens.GetValue(i + 3).ToString().Length > 0)   // FBS bug 75686 -- 6/27/12
                    {
                        writer.WriteAttributeString("ptst", tokens.GetValue(i + 3).ToString());
                    }
                    else
                    {
                        writer.WriteAttributeString("ptst", "NE");
                    }
                }
                else
                {
                    if (appt["orAddr"] != tokens.GetValue(i + 1).ToString())
                    {
                        if(AccountID == tokens.GetValue(i).ToString())
                        writer.WriteAttributeString("ptst", appt["currst"]);
                        else
                            writer.WriteAttributeString("ptst", "NE");
                    }
                }

                writer.WriteEndElement();
            }
        }

        if (isRecurring)
        {
            writer.WriteStartElement("recur");
            writer.WriteStartElement("add");
            writer.WriteStartElement("rule");
            writer.WriteAttributeString("freq", appt["freq"]);
            writer.WriteStartElement("interval");
            writer.WriteAttributeString("ival", appt["ival"]);
            writer.WriteEndElement();   // interval
            if (appt.ContainsKey("wkday"))
            {
                writer.WriteStartElement("byday");
                string wkday = appt["wkday"];
                int len = wkday.Length;
                for (int i = 0; i < len; i += 2)
                {
                    writer.WriteStartElement("wkday");
                    writer.WriteAttributeString("day", wkday.Substring(i, 2));
                    writer.WriteEndElement();   //wkday
                }
                writer.WriteEndElement();   // byday
            }
            if (appt.ContainsKey("modaylist"))
            {
                writer.WriteStartElement("bymonthday");
                writer.WriteAttributeString("modaylist", appt["modaylist"]);
                writer.WriteEndElement();   // bymonthday
            }
            if (appt.ContainsKey("molist"))
            {
                writer.WriteStartElement("bymonth");
                writer.WriteAttributeString("molist", appt["molist"]);
                writer.WriteEndElement();   // bymonthday
            }
            if (appt.ContainsKey("poslist"))
            {
                writer.WriteStartElement("bysetpos");
                writer.WriteAttributeString("poslist", appt["poslist"]);
                writer.WriteEndElement();   // bymonthday
            }
            if (appt["count"].Length > 0)
            {
                writer.WriteStartElement("count");
                writer.WriteAttributeString("num", appt["count"]);
                writer.WriteEndElement();   // count
            }
            if (appt.ContainsKey("until"))
            {
                writer.WriteStartElement("until");
                writer.WriteAttributeString("d", appt["until"]);
                writer.WriteEndElement();   // until
            }
            writer.WriteEndElement();   // rule
            writer.WriteEndElement();   // add
            writer.WriteEndElement();   // recur
        }

        if (appt["m"].Length > 0)   // FBS bug 73665 -- 6/4/12
        {
            writer.WriteStartElement("alarm");
            writer.WriteAttributeString("action", "DISPLAY");
            writer.WriteStartElement("trigger");
            writer.WriteStartElement("rel");
            writer.WriteAttributeString("related", "START");
            writer.WriteAttributeString("neg", "1");
            writer.WriteAttributeString("m", appt["m"]);
            writer.WriteEndElement();   // rel
            writer.WriteEndElement();   // trigger
            writer.WriteEndElement();   // alarm
        }

        writer.WriteEndElement();   // inv

        WriteNVPair(writer, "su", appt["su"]);

        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", "multipart/alternative");
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", appt["contentType0"]);
        if (appt["content0"].Length > 0)
        {
            WriteNVPair(writer, "content", System.Text.Encoding.Unicode.GetString(File.ReadAllBytes(appt["content0"])));           
            //WriteNVPair(writer, "content", System.Text.Encoding.Default.GetString(File.ReadAllBytes(appt["content0"])));
        }
        writer.WriteEndElement();   // mp
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", appt["contentType1"]);
        if (appt["content1"].Length > 0)
        {
	    WriteNVPair(writer, "content", System.Text.Encoding.Unicode.GetString(File.ReadAllBytes(appt["content1"])));
        }
    
        writer.WriteEndElement();   // mp
        writer.WriteEndElement();   // mp

        int numAttachments = (appt.ContainsKey("numAttachments")) ? Int32.Parse(appt["numAttachments"]) : 0;
        if (numAttachments > 0)
        {
            string aids = "";
            for (int i = 0; i < numAttachments; i++)
            {
                string ContentTypeAttr = "attContentType" + "_" + i.ToString();
                string ContentType = appt[ContentTypeAttr];
                string TempFileAttr = "attTempFile" + "_" + i.ToString();
                string TempFile = appt[TempFileAttr];
                string RealNameAttr = "attRealName" + "_" + i.ToString();
                string RealName = appt[RealNameAttr];
                string ContentDispositionAttr = "attContentDisposition" + "_" + i.ToString();
                string ContentDisposition = appt[ContentDispositionAttr];

                /*
                if contentType is message/rfc822, we'll rename the temp file to email_n and massage the content disposition
                if not, we'll just rename the temp file to the real name
                */
                string newfile = "";
                string path = "";
                string name = "";
                int mode;
                GetParentAndChild("\\", TempFile, out path, out name);    // don't need name
                if (ContentType == "message/rfc822")    // rename file to email_x_y and massage content disposition
                {
                    string newname = "email_" + appt["accountNum"] + "_" + i.ToString();
                    newfile = path + "\\" + newname;  // accountNum for threading
                    string oldValue = "\"" + RealName + "\"";
                    string newValue = "\"" + newname + "\"";
                    mode = APPT_EMB_MODE;
                    ContentDisposition = ContentDisposition.Replace(oldValue, newValue);
                }
                else
                {
                    newfile = path + "\\" + RealName;
                    mode = APPT_VALUE_MODE;
                }
                File.Move(TempFile, newfile);

                string uploadToken = "";
                string tmp = "";
                if (UploadFile(newfile, tmp, ContentDisposition, ContentType, mode, out uploadToken) == 0)
                {
                    aids += uploadToken;
                    if (i < (numAttachments - 1))
                    {
                        aids += ",";
                    }
                }
                File.Delete(newfile);
            }

            writer.WriteStartElement("attach");
            writer.WriteAttributeString("aid", aids);
            writer.WriteEndElement();
        }

        writer.WriteEndElement();   // m
        writer.WriteEndElement();   // default
        for (int i = 0; i < numExceptions; i++)
        {
            AddExceptionToRequest(writer, appt, i);
        }
        writer.WriteEndElement();   // SetAppointmentRequest

        DeleteApptTempFiles(appt, numExceptions);
    }

    private void AddExceptionToRequest(XmlWriter writer, Dictionary<string, string> appt, int num)
    {
        Log.trace("ZimbraAPI -AddExceptionToRequest");
        string attr = "exceptionType" + "_" + num.ToString();
        bool isCancel = appt[attr] == "cancel";
        if (isCancel)
        {
            writer.WriteStartElement("cancel");
        }
        else
        {
            writer.WriteStartElement("except");
        }
        attr = "ptst" + "_" + num.ToString();
        writer.WriteAttributeString("ptst", appt[attr]);
        writer.WriteStartElement("m");

        WriteTimezone(writer, appt); // timezone stuff since it is a recurrence

        writer.WriteStartElement("inv");
        if (!isCancel)
        {
            writer.WriteAttributeString("method", "REQUEST");
        }
        attr = "fb" + "_" + num.ToString();
        writer.WriteAttributeString("fb", appt[attr]);
        writer.WriteAttributeString("transp", "O");
        attr = "allDay" + "_" + num.ToString();
        writer.WriteAttributeString("allDay", appt[attr]);
        attr = "name" + "_" + num.ToString();
        writer.WriteAttributeString("name", appt[attr]);
        attr = "loc" + "_" + num.ToString();
        writer.WriteAttributeString("loc", appt[attr]);
        if (appt["uid"].Length > 0)
        {
            writer.WriteAttributeString("uid", appt["uid"]);
        }
        writer.WriteStartElement("s");
        attr = "s" + "_" + num.ToString();
        writer.WriteAttributeString("d", appt[attr]);
        writer.WriteAttributeString("tz", appt["tid"]);
        writer.WriteEndElement();
        if (!isCancel)
        {
            writer.WriteStartElement("e");
            attr = "e" + "_" + num.ToString();
            writer.WriteAttributeString("d", appt[attr]);
            writer.WriteAttributeString("tz", appt["tid"]);
            writer.WriteEndElement();
        }

        // FBS bug 71050 -- used to compute recurrence id
        attr = (isCancel) ? "s" + "_" + num.ToString() : "rid" + "_" + num.ToString();
        //

        if (appt[attr].Length > 0)
        {
            writer.WriteStartElement("exceptId");
            string exceptId = ComputeExceptId(appt[attr], appt["s"]);
            writer.WriteAttributeString("d", exceptId);
            writer.WriteAttributeString("tz", appt["tid"]);
            writer.WriteEndElement();   // exceptId
        }
        writer.WriteStartElement("or");
        attr = "orName" + "_" + num.ToString();
        writer.WriteAttributeString("d", appt[attr]);
        attr = "orAddr" + "_" + num.ToString();
        /*
        string theOrganizer = AccountName;
        if (appt[attr].Length > 0)
        {
            if (!IAmTheOrganizer(appt[attr]))
            {
                theOrganizer = appt[attr];
            }
        }*/
        string theOrganizer = "";
        if (appt["orAddr"].Length > 0)
        {
            theOrganizer = AccountName;
            if (!IAmTheOrganizer(appt["orAddr"]))
            {
                theOrganizer = appt["orAddr"];
            }
        }
        else
        {
            
            if (appt["orName"].Length > 0)
            {
                theOrganizer = appt["orName"];
            }
            else
            {
                theOrganizer = AccountName;
                if (!IAmTheOrganizer(AccountName))
                {
                    theOrganizer = AccountName;
                }


            }
        }
        writer.WriteAttributeString("a", theOrganizer);
        writer.WriteEndElement();

        // FBS Bug 71054 -- 4/11/12
        attr = "attendees" + "_" + num.ToString();
        if (appt[attr].Length > 0)
        {
            string[] tokens = appt[attr].Split('~');
            for (int i = 0; i < tokens.Length; i += 4)
            {
                writer.WriteStartElement("at");
                writer.WriteAttributeString("d", tokens.GetValue(i).ToString());
                writer.WriteAttributeString("a", tokens.GetValue(i + 1).ToString());
                writer.WriteAttributeString("role", tokens.GetValue(i + 2).ToString());
                if (appt["currst"] == "OR")
                {
                    if (tokens.GetValue(i + 3).ToString().Length > 0)   // FBS bug 75686 -- 6/27/12
                    {
                        writer.WriteAttributeString("ptst", tokens.GetValue(i + 3).ToString());
                    }
                    else
                    {
                        writer.WriteAttributeString("ptst", "NE");
                    }
                }
                else
                {
                    if (appt["orAddr"] != tokens.GetValue(i + 1).ToString())
                        writer.WriteAttributeString("ptst", appt["currst"]);
                }
                writer.WriteEndElement();
            }
        }
        //

        if (!isCancel)
        {
            attr = "m" + "_" + num.ToString();
            if (appt[attr].Length > 0)   // FBS bug 73665 -- 6/4/12
            {
                writer.WriteStartElement("alarm");
                writer.WriteAttributeString("action", "DISPLAY");
                writer.WriteStartElement("trigger");
                writer.WriteStartElement("rel");
                writer.WriteAttributeString("related", "START");
                writer.WriteAttributeString("neg", "1");
                writer.WriteAttributeString("m", appt[attr]);
                writer.WriteEndElement();   // rel
                writer.WriteEndElement();   // trigger
                writer.WriteEndElement();   // alarm
            }
        }
        writer.WriteEndElement();   // inv
        attr = "su" + "_" + num.ToString();
        WriteNVPair(writer, "su", appt[attr]);

        attr = "contentType0" + "_" + num.ToString();
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", "multipart/alternative");
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", appt[attr]);
        attr = "content0" + "_" + num.ToString();
        if (appt[attr].Length > 0)
        {
            WriteNVPair(writer, "content", System.Text.Encoding.Unicode.GetString(File.ReadAllBytes(appt[attr])));
            //WriteNVPair(writer, "content", System.Text.Encoding.Default.GetString(File.ReadAllBytes(appt[attr])));
        }

        attr = "contentType1" + "_" + num.ToString();
        writer.WriteEndElement();   // mp
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", appt[attr]);
        attr = "content1" + "_" + num.ToString();
        if (appt[attr].Length > 0)
        {
            WriteNVPair(writer, "content", System.Text.Encoding.Unicode.GetString(File.ReadAllBytes(appt[attr])));
        }        
        writer.WriteEndElement();   // mp
        writer.WriteEndElement();   // mp

        writer.WriteEndElement();   // m
        writer.WriteEndElement();   // except or cancel
    }

    public int AddAppointment(Dictionary<string, string> appt, string folderPath = "")
    {
        Log.trace("ZimbraAPI -AddAppointment");
        lastError = "";

        // Create in Calendar unless another folder was desired
        string folderId = "10";

        if (folderPath.Length > 0)
        {
            folderId = FindFolder(folderPath);
            if (folderId.Length == 0)
                return APPT_CREATE_FAILED_FLDR;
        }

        // //////
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        int retval = 0;
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings))
        {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            SetAppointmentRequest(writer, appt, folderId);

            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }
        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        if (client.status != 0)
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);
            if (soapReason.Length > 0)
            {
                lastError = soapReason;
                Log.err("Error on appointment", appt["su"], "--", soapReason);
            }
        }
        return retval;
    }

    private void WriteTimezone(XmlWriter writer, Dictionary<string, string> appt)
    {
        writer.WriteStartElement("tz");
        writer.WriteAttributeString("id", appt["tid"]);
        writer.WriteAttributeString("stdoff", appt["stdoff"]);

        // FBS bug 73047 -- 4/24/12 -- don't write standard/daylight nodes if no DST
        if ((appt["sweek"] != "0") && (appt["smon"] != "0"))
        {
            writer.WriteAttributeString("dayoff", appt["dayoff"]);
            writer.WriteStartElement("standard");
            writer.WriteAttributeString("week", appt["sweek"]);
            writer.WriteAttributeString("wkday", appt["swkday"]);
            writer.WriteAttributeString("mon", appt["smon"]);
            writer.WriteAttributeString("hour", appt["shour"]);
            writer.WriteAttributeString("min", appt["smin"]);
            writer.WriteAttributeString("sec", appt["ssec"]);
            writer.WriteEndElement();   // standard
        }
        if ((appt["dweek"] != "0") && (appt["dmon"] != "0"))
        {
            writer.WriteStartElement("daylight");
            writer.WriteAttributeString("week", appt["dweek"]);
            writer.WriteAttributeString("wkday", appt["dwkday"]);
            writer.WriteAttributeString("mon", appt["dmon"]);
            writer.WriteAttributeString("hour", appt["dhour"]);
            writer.WriteAttributeString("min", appt["dmin"]);
            writer.WriteAttributeString("sec", appt["dsec"]);
            writer.WriteEndElement();   // daylight
        }
        writer.WriteEndElement();   // tz
    }

    private string ComputeExceptId(string exceptDate, string originalDate)
    {
        if (exceptDate.Length == 8) // already done -- must be allday
        {
            return exceptDate;
        }
        string retval = exceptDate.Substring(0, 9);
        retval += originalDate.Substring(9, 6);
        return retval;
    }

    private void SetTaskRequest(XmlWriter writer, Dictionary<string, string> task,
        string folderId)
    {
        bool isRecurring = task.ContainsKey("freq");
        writer.WriteStartElement("SetTaskRequest", "urn:zimbraMail");
        writer.WriteAttributeString("l", folderId);
        if (task["tags"].Length > 0)
        {
            writer.WriteAttributeString("t", task["tags"]);
        }
        writer.WriteStartElement("default");
        writer.WriteAttributeString("ptst", "NE");  // we don't support Task Requests
        writer.WriteStartElement("m");

        /*
        // Timezone nodes if recurring appt
        if (isRecurring)
        {
            writer.WriteStartElement("tz");
            writer.WriteAttributeString("id", appt["tid"]);
            writer.WriteAttributeString("stdoff", appt["stdoff"]);
            writer.WriteAttributeString("dayoff", appt["dayoff"]);
            writer.WriteStartElement("standard");
            writer.WriteAttributeString("week", appt["sweek"]);
            writer.WriteAttributeString("wkday", appt["swkday"]);
            writer.WriteAttributeString("mon", appt["smon"]);
            writer.WriteAttributeString("hour", appt["shour"]);
            writer.WriteAttributeString("min", appt["smin"]);
            writer.WriteAttributeString("sec", appt["ssec"]);
            writer.WriteEndElement();   // standard
            writer.WriteStartElement("daylight");
            writer.WriteAttributeString("week", appt["dweek"]);
            writer.WriteAttributeString("wkday", appt["dwkday"]);
            writer.WriteAttributeString("mon", appt["dmon"]);
            writer.WriteAttributeString("hour", appt["dhour"]);
            writer.WriteAttributeString("min", appt["dmin"]);
            writer.WriteAttributeString("sec", appt["dsec"]);
            writer.WriteEndElement();   // daylight
            writer.WriteEndElement();   // tz
        }
        */

        writer.WriteStartElement("inv");
        writer.WriteAttributeString("status", task["status"]);
        writer.WriteAttributeString("method", "REQUEST");
        writer.WriteAttributeString("priority", task["priority"]);
        writer.WriteAttributeString("percentComplete", task["percentComplete"]);
        writer.WriteAttributeString("name", task["name"]);

        // hard code these -- probably fine
        writer.WriteAttributeString("allDay", "1");
        writer.WriteAttributeString("transp", "O");
        writer.WriteAttributeString("fb", "B");
        //

        // private, if applicable
        if (task["class"].Length > 0)
        {
            if (task["class"] == "1")
            {
                writer.WriteAttributeString("class", "PRI");
            }
        }

        if (task.ContainsKey("uid"))     // for now
        {
            writer.WriteAttributeString("uid", task["uid"]);
        }

        if (task["s"].Length > 0)   // FBS bug 71748 -- 3/19/12
        {
            writer.WriteStartElement("s");
            writer.WriteAttributeString("d", task["s"]);
            //if (isRecurring)
            //{
            //    writer.WriteAttributeString("tz", task["tid"]);
            //}
            writer.WriteEndElement();
        }

        if (task["e"].Length > 0)   // FBS bug 71748 -- 3/19/12
        {
            writer.WriteStartElement("e");
            writer.WriteAttributeString("d", task["e"]);
            //if (isRecurring)
            //{
            //    writer.WriteAttributeString("tz", task["tid"]);
            //}
            writer.WriteEndElement();
        }

        // hard code the organizer -- we don't support task requests
        writer.WriteStartElement("or");
        writer.WriteAttributeString("a", AccountName);
        writer.WriteEndElement();
        //

        // task reminder if applicable
        if (task.ContainsKey("taskflagdueby"))
        {
            if (task["taskflagdueby"].Length > 0)   // FBS bug 73665 -- 6/4/12
            {
                writer.WriteStartElement("alarm");
                writer.WriteAttributeString("action", "DISPLAY");
                writer.WriteStartElement("trigger");
                writer.WriteStartElement("abs");
                writer.WriteAttributeString("d", task["taskflagdueby"]);
                writer.WriteEndElement();   // abs
                writer.WriteEndElement();   // trigger
                writer.WriteEndElement();   // alarm
            }
        }

        if (isRecurring)
        {
            writer.WriteStartElement("recur");
            writer.WriteStartElement("add");
            writer.WriteStartElement("rule");
            writer.WriteAttributeString("freq", task["freq"]);
            writer.WriteStartElement("interval");
            writer.WriteAttributeString("ival", task["ival"]);
            writer.WriteEndElement();   // interval
            if (task.ContainsKey("wkday"))
            {
                writer.WriteStartElement("byday");
                string wkday = task["wkday"];
                int len = wkday.Length;
                for (int i = 0; i < len; i += 2)
                {
                    writer.WriteStartElement("wkday");
                    writer.WriteAttributeString("day", wkday.Substring(i, 2));
                    writer.WriteEndElement();   //wkday
                }
                writer.WriteEndElement();   // byday
            }
            if (task.ContainsKey("modaylist"))
            {
                writer.WriteStartElement("bymonthday");
                writer.WriteAttributeString("modaylist", task["modaylist"]);
                writer.WriteEndElement();   // bymonthday
            }
            if (task.ContainsKey("molist"))
            {
                writer.WriteStartElement("bymonth");
                writer.WriteAttributeString("molist", task["molist"]);
                writer.WriteEndElement();   // bymonthday
            }
            if (task.ContainsKey("poslist"))
            {
                writer.WriteStartElement("bysetpos");
                writer.WriteAttributeString("poslist", task["poslist"]);
                writer.WriteEndElement();   // bymonthday
            }
            if (task["count"].Length > 0)
            {
                writer.WriteStartElement("count");
                writer.WriteAttributeString("num", task["count"]);
                writer.WriteEndElement();   // count
            }
            if (task.ContainsKey("until"))
            {
                writer.WriteStartElement("until");
                writer.WriteAttributeString("d", task["until"]);
                writer.WriteEndElement();   // until
            }
            writer.WriteEndElement();   // rule
            writer.WriteEndElement();   // add
            writer.WriteEndElement();   // recur
        }       

        if (task["xp-TOTAL_WORK"].Length > 0)
        {
            writer.WriteStartElement("xprop");
            writer.WriteAttributeString("name", "X-ZIMBRA-TASK-TOTAL-WORK");
            writer.WriteAttributeString("value", task["xp-TOTAL_WORK"]);
            writer.WriteEndElement();   // xprop
        }
        if (task["xp-ACTUAL_WORK"].Length > 0)
        {
            writer.WriteStartElement("xprop");
            writer.WriteAttributeString("name", "X-ZIMBRA-TASK-ACTUAL-WORK");
            writer.WriteAttributeString("value", task["xp-ACTUAL_WORK"]);
            writer.WriteEndElement();   // xprop
        }
        if (task["xp-COMPANIES"].Length > 0)
        {
            writer.WriteStartElement("xprop");
            writer.WriteAttributeString("name", "X-ZIMBRA-TASK-COMPANIES");
            writer.WriteAttributeString("value", task["xp-COMPANIES"]);
            writer.WriteEndElement();   // xprop
        }
        if (task["xp-MILEAGE"].Length > 0)
        {
            writer.WriteStartElement("xprop");
            writer.WriteAttributeString("name", "X-ZIMBRA-TASK-MILEAGE");
            writer.WriteAttributeString("value", task["xp-MILEAGE"]);
            writer.WriteEndElement();   // xprop
        }
        if (task["xp-BILLING"].Length > 0)
        {
            writer.WriteStartElement("xprop");
            writer.WriteAttributeString("name", "X-ZIMBRA-TASK-BILLING");
            writer.WriteAttributeString("value", task["xp-BILLING"]);
            writer.WriteEndElement();   // xprop
        }

        writer.WriteEndElement();   // inv

        WriteNVPair(writer, "su", task["su"]);

        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", "multipart/alternative");
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", task["contentType0"]);
        if (task["content0"].Length > 0)
        {
            WriteNVPair(writer, "content", System.Text.Encoding.Unicode.GetString(File.ReadAllBytes(task["content0"])));
            //WriteNVPair(writer, "content", System.Text.Encoding.Default.GetString(File.ReadAllBytes(task["content0"])));
            File.Delete(task["content0"]);
        }
        writer.WriteEndElement();   // mp
        writer.WriteStartElement("mp");
        writer.WriteAttributeString("ct", task["contentType1"]);
        if (task["content1"].Length > 0)
        {
            WriteNVPair(writer, "content", System.Text.Encoding.Unicode.GetString(File.ReadAllBytes(task["content1"])));
            File.Delete(task["content1"]);
        }

        writer.WriteEndElement();   // mp
        writer.WriteEndElement();   // mp

        int numAttachments = (task.ContainsKey("numAttachments")) ? Int32.Parse(task["numAttachments"]) : 0;
        if (numAttachments > 0)
        {
            string aids = "";
            for (int i = 0; i < numAttachments; i++)
            {
                string ContentTypeAttr = "attContentType" + "_" + i.ToString();
                string ContentType = task[ContentTypeAttr];
                string TempFileAttr = "attTempFile" + "_" + i.ToString();
                string TempFile = task[TempFileAttr];
                string RealNameAttr = "attRealName" + "_" + i.ToString();
                string RealName = task[RealNameAttr];
                string ContentDispositionAttr = "attContentDisposition" + "_" + i.ToString();
                string ContentDisposition = task[ContentDispositionAttr];

                /*
                if contentType is message/rfc822, we'll rename the temp file to email_n and massage the content disposition
                if not, we'll just rename the temp file to the real name
                */
                string newfile = "";
                string path = "";
                string name = "";
                int mode;
                GetParentAndChild("\\", TempFile, out path, out name);    // don't need name
                if (ContentType == "message/rfc822")    // rename file to email_x_y and massage content disposition
                {
                    string newname = "email_" + task["accountNum"] + "_" + i.ToString();
                    newfile = path + "\\" + newname;  // accountNum for threading
                    string oldValue = "\"" + RealName + "\"";
                    string newValue = "\"" + newname + "\"";
                    mode = APPT_EMB_MODE;
                    ContentDisposition = ContentDisposition.Replace(oldValue, newValue);
                }
                else
                {
                    newfile = path + "\\" + RealName;
                    mode = APPT_VALUE_MODE;
                }
                File.Move(TempFile, newfile);

                string uploadToken = "";
                string tmp = "";
                if (UploadFile(newfile, tmp, ContentDisposition, ContentType, mode, out uploadToken) == 0)
                {
                    aids += uploadToken;
                    if (i < (numAttachments - 1))
                    {
                        aids += ",";
                    }
                }
                File.Delete(newfile);
            }

            writer.WriteStartElement("attach");
            writer.WriteAttributeString("aid", aids);
            writer.WriteEndElement();
        }

        writer.WriteEndElement();   // m
        writer.WriteEndElement();   // default
        writer.WriteEndElement();   // SetTaskRequest
    }

    public int AddTask(Dictionary<string, string> appt, string folderPath = "")
    {
        Log.trace("ZimbraAPI AddTask");
        lastError = "";

        // Create in Tasks unless another folder was desired
        string folderId = "15";

        if (folderPath.Length > 0)
        {
            folderId = FindFolder(folderPath);
            if (folderId.Length == 0)
                return TASK_CREATE_FAILED_FLDR;
        }

        // //////
        WebServiceClient client = new WebServiceClient
        {
            Url = ZimbraValues.GetZimbraValues().Url,
            WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        int retval = 0;
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings))
        {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            SetTaskRequest(writer, appt, folderId);

            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }
        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        return retval;
    }

    private void SetModifyPrefsRequest(XmlWriter writer, bool isOOOEnabled, string OOOInfo)
    { 
        string state = (isOOOEnabled) ? "TRUE" : "FALSE";
        string OOOmsg = OOOInfo.Substring(2);   // provide for the :

        writer.WriteStartElement("ModifyPrefsRequest", "urn:zimbraAccount");
        WriteAttrNVPair(writer, "pref", "name", "zimbraPrefOutOfOfficeReplyEnabled", state);
        WriteAttrNVPair(writer, "pref", "name", "zimbraPrefOutOfOfficeReply", OOOmsg);
        WriteAttrNVPair(writer, "pref", "name", "zimbraPrefOutOfOfficeFromDate", "");
        WriteAttrNVPair(writer, "pref", "name", "zimbraPrefOutOfOfficeUntilDate", "");
        writer.WriteEndElement();   // ModifyPrefsRequest
    }

    public int AddOOO(string OOOInfo, bool isServer)
    {
        Log.trace("ZimbraAPI AddOOO");
        lastError = "";

        if (OOOInfo.Length == 0)
        {
            return OOO_NO_TEXT;
        }

        bool isOOOEnabled = OOOInfo.Substring(0, 1) == "1";
        if ((!isOOOEnabled) && (OOOInfo.Length == 2))   // 0:
        {
            return 0;   // it's ok -- just no need to do anything
        }

        WebServiceClient client = new WebServiceClient
        {
            Url = ZimbraValues.GetZimbraValues().Url,
            WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        int retval = 0;
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings))
        {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, isServer);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
            SetModifyPrefsRequest(writer, isOOOEnabled, OOOInfo);

            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }
        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        return retval;
    }

    private void AddFilterRuleToRequest(XmlWriter writer, Dictionary<string, string> rules, int idx)
    {
        // ^^^ is the delimiter for multiple filterTests, and filterActions
        // `~  is the token delimiter for individual filterTests, and filterActions
        // see CRuleMap::WriteFilterRule, CRuleMap::WriteFilterTests,CRuleMap::WriteFilterActions

        int i, j;

        // in the map, we'll have nfilterRule, nFilterTests, nFilterActions where n is idx
        string filterRuleDictName = idx.ToString() + "filterRule";
        string filterTestsDictName = idx.ToString() + "filterTests";
        string filterActionsDictName = idx.ToString() + "filterActions";

        writer.WriteStartElement("filterRule");
       // string[] tokens = rules[filterRuleDictName].Split(',');
        string[] tokens = rules[filterRuleDictName].Split(';');
        writer.WriteAttributeString(tokens.GetValue(0).ToString(), tokens.GetValue(1).ToString());
        writer.WriteAttributeString(tokens.GetValue(2).ToString(), tokens.GetValue(3).ToString());

        writer.WriteStartElement("filterTests");

        // first get anyof or allof.  String starts with "anyof:" or "allof:"
        writer.WriteAttributeString("condition", rules[filterTestsDictName].Substring(0, 5));

        

        // now process the actual tests
        string[] allTests = rules[filterTestsDictName].Substring(6).Split(new string[] { "^^^" }, StringSplitOptions.None);
        for (i = 0; i < allTests.Length; i++)
        {
            string eachTest = allTests.GetValue(i).ToString();
            string[] testTokens = eachTest.Split(new string[] { "`~" }, StringSplitOptions.None);
            string theTest = testTokens.GetValue(0).ToString();
            writer.WriteStartElement(theTest);
            if (theTest == "inviteTest")    // special case since we have to create a node
            {
                writer.WriteAttributeString(testTokens.GetValue(1).ToString(), testTokens.GetValue(2).ToString());  // index
                WriteNVPair(writer, testTokens.GetValue(3).ToString(), testTokens.GetValue(4).ToString());
            }
            else
            {
                for (j = 1; j < testTokens.Length; j++)
                {
                    writer.WriteAttributeString(testTokens.GetValue(j++).ToString(), testTokens.GetValue(j).ToString());
                }
            }
            writer.WriteEndElement();   // theTest
        }
        writer.WriteEndElement();   // filterTests

        writer.WriteStartElement("filterActions");
        string[] allActions = rules[filterActionsDictName].Split(new string[] { "^^^" }, StringSplitOptions.None);
        for (i = 0; i < allActions.Length; i++)
        {
            string eachAction = allActions.GetValue(i).ToString();
            if (eachAction.Length > 0)    // FBS bug 71271 -- 3/9/12
            {
                string[] actionTokens = eachAction.Split(new string[] { "`~" }, StringSplitOptions.None); ;
                writer.WriteStartElement(actionTokens.GetValue(0).ToString());
                if ((actionTokens.GetValue(0).ToString() != "actionStop") &&
                    (actionTokens.GetValue(0).ToString() != "actionDiscard"))
                {
                    for (j = 1; j < actionTokens.Length; j++)
                    {
                        writer.WriteAttributeString(actionTokens.GetValue(j++).ToString(), actionTokens.GetValue(j).ToString());
                    }
                }
                writer.WriteEndElement();   // actionTokens.GetValue(i)
            }
        }
        writer.WriteEndElement();   // filterActions

        writer.WriteEndElement();   // filterRule

        
    }

    private void SetModifyFilterRulesRequest(XmlWriter writer, Dictionary<string, string> rules)
    {       
        writer.WriteStartElement("ModifyFilterRulesRequest", "urn:zimbraMail");
        writer.WriteStartElement("filterRules");
        int numRules = Int32.Parse(rules["numRules"]);
        for (int i = 0; i < numRules; i++)
        {
            AddFilterRuleToRequest(writer, rules, i);
        }
        writer.WriteEndElement();   // filterRules
        writer.WriteEndElement();   // ModifyFilterRulesRequest
    }

    public int AddRules(Dictionary<string, string> rules)
    {
        Log.trace("ZimbraAPI AddRules");
            lastError = "";

            WebServiceClient client = new WebServiceClient
            {
                Url = ZimbraValues.GetZimbraValues().Url,
                WSServiceType =
                    WebServiceClient.ServiceType.Traditional
            };
            int retval = 0;
            StringBuilder sb = new StringBuilder();
            XmlWriterSettings settings = new XmlWriterSettings();

            settings.OmitXmlDeclaration = true;
            using (XmlWriter writer = XmlWriter.Create(sb, settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("soap", "Envelope",
                    "http://www.w3.org/2003/05/soap-envelope");

                WriteHeader(writer, true, true, true);

                writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");
                SetModifyFilterRulesRequest(writer, rules);

                writer.WriteEndElement();           // soap body
                writer.WriteEndElement();           // soap envelope
                writer.WriteEndDocument();
            }
            string rsp = "";

            WriteSoapLog(sb.ToString(), true);
            client.InvokeService(sb.ToString(), out rsp);

            WriteSoapLog(rsp.ToString(), false);
            retval = client.status;
            return retval;
       
    }

    private void CreateFolderRequest(XmlWriter writer, ZimbraFolder folder, int requestId)
    {
        writer.WriteStartElement("CreateFolderRequest", "urn:zimbraMail");
        if (requestId != -1)
            writer.WriteAttributeString("requestId", requestId.ToString());
        writer.WriteStartElement("folder");

       // specialCharacters.Any(s => s.Equals(folder.name));

        int indSpecialC = folder.name.IndexOfAny(specialCharacters);
        

        if ( indSpecialC != -1)
        {
            
            StringBuilder sb = new StringBuilder(folder.name);
            if (ReplaceSlash == null)
            {
                ReplaceSlash = "_";
            }
            sb[indSpecialC] = ReplaceSlash.ToCharArray().ElementAt(0)/*'_'*/;
            string newS = sb.ToString();
            writer.WriteAttributeString("name", newS);
        }
        else
            writer.WriteAttributeString("name", folder.name);

        writer.WriteAttributeString("l", folder.parent);
        writer.WriteAttributeString("fie", "1");        // return the existing ID instead of an error
        if (folder.view.Length > 0)
            writer.WriteAttributeString("view", folder.view);
        if (folder.color.Length > 0)
            writer.WriteAttributeString("color", folder.color);
        if (folder.flags.Length > 0)
            writer.WriteAttributeString("f", folder.flags);
        writer.WriteEndElement();               // folder
        writer.WriteEndElement();               // CreateFolderRequest
    }

    private int DoCreateFolder(ZimbraFolder folder, out string folderID)
    {
        folderID = "";
        lastError = "";

        int retval = 0;
        WebServiceClient client = new WebServiceClient {
            Url = ZimbraValues.GetZimbraValues().Url, WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings)) {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");

            CreateFolderRequest(writer, folder, -1);

            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        if (client.status == 0)
        {
            ParseCreateFolder(rsp, out folderID);       // get the id
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return retval;
    }

    private bool GetParentAndChild(string slash, string fullPath, out string parent, out string child)
    {
        parent = "";
        child = "";

        // break up the folder name and parent from the path
        int lastSlash = fullPath.LastIndexOf(slash);

        if (lastSlash == -1)
            return false;

        int folderNameStart = lastSlash + 1;
        int len = fullPath.Length;

        parent = fullPath.Substring(0, lastSlash);
        child = fullPath.Substring(folderNameStart, (len - folderNameStart));

        return true;
    }

    private string FindFolder(string folderPath)
    {
        // first look if the folder is in the map.  If it is, return the id
        if (dFolderMap.ContainsKey(folderPath))
            return dFolderMap[folderPath];
        // wasn't in the map. See if it's a special folder
        return GetSpecialFolderNum(folderPath);
    }

    public int CreateFolder(string FolderPath, string View = "", string Color = "", string
        Flags = "")
    {
        Log.trace("ZimbraAPI : CreateFolder");
        string parentPath = "";
        string folderName = "";

        if (!GetParentAndChild("/", FolderPath, out parentPath, out folderName))
            return FOLDER_CREATE_FAILED_SYN;

        // first look in the special folders array
        // if it's not there, look in the map
        string strParentNum = GetSpecialFolderNum(parentPath);

        

        if (strParentNum.Length == 0)
        {
            if (dFolderMap.ContainsKey(parentPath))
                strParentNum = dFolderMap[parentPath];
            else
            {

                if (strParentNum == "")
                {
                    string[] words = FolderPath.Split('/');

                    int ind = 0;
                    int  mnIndex =0;
                    if ((words[2]==("Inbox")) || (words[2]==("Calendar")) || (words[2]==("Contacts")) || (words[2]==("Tasks")) ||(words[2]==("Junk")) || (words[2]==("Drafts")))
                    
                    //if ((FolderPath.Contains("Inbox")) || (FolderPath.Contains("Calendar")) || ((FolderPath.Contains("Contacts"))) || ((FolderPath.Contains("Tasks"))) ||(FolderPath.Contains("Junk")) || (FolderPath.Contains("Drafts")))
                    { mnIndex = 3; }
                    else
                        mnIndex = 2;
                    int len = 0;
                    if (words[mnIndex] != "")
                        ind = FolderPath.IndexOf(words[mnIndex]);
                    else
                    {
                        ind = FolderPath.IndexOf(words[++mnIndex]);
                        ind = ind - 1;
                    }

                   
                    len = FolderPath.Length;

                    string newpath = FolderPath.Substring(ind, (len - ind));
                    validatefoldernames(FolderPath, newpath, out parentPath);
                    if (ReplaceSlash == null)
                    {
                        ReplaceSlash = "_";
                    }
                 
                    if (parentPath != "")
                    {
                        int newlen = parentPath.Length;
                        folderName = FolderPath.Substring(newlen+1);

                        folderName = folderName.Replace("/", ReplaceSlash);
                        if (dFolderMap.ContainsKey(parentPath))
                            strParentNum = dFolderMap[parentPath];
                        else
                            return FOLDER_CREATE_FAILED_SEM;
                    }
                    else
                    {
                        folderName = newpath.Replace("/", ReplaceSlash);
                        string NewParentPath = FolderPath.Substring(0, (ind - 1));
                        strParentNum = GetSpecialFolderNum(NewParentPath);
                        if (strParentNum.Length == 0)
                        {
                            if (dFolderMap.ContainsKey(NewParentPath))
                                strParentNum = dFolderMap[NewParentPath];
                            else
                                return FOLDER_CREATE_FAILED_SEM;
                        }
                    }
                }
               // return FOLDER_CREATE_FAILED_SEM;
            }
        }

        string folderID = "";
        int dcfReturnVal = DoCreateFolder(new ZimbraFolder(folderName, strParentNum, View,
            Color, Flags), out folderID);

        if (dcfReturnVal == 0)
        { dFolderMap.Add(FolderPath, folderID);}
        return dcfReturnVal;
    }

    private void validatefoldernames(string folderpath,string newfolderpath,out string currentpath)
    {
        //int inds = 0;
         int itercnt = 0;
         int len = folderpath.Length;
         string[] nwords = newfolderpath.Split('/');
         string currentfoldername = "";
         currentpath = "" ;
         string np ="";

        while (itercnt <= (nwords.Length - 1))
        {

            string temp = nwords[itercnt];
            if (temp != "")
            {

                int i = folderpath.LastIndexOf(temp);
                np = folderpath.Substring(0, (i - 1));
                currentfoldername = np + '/' + nwords[itercnt];
            }
            else
                currentfoldername = currentfoldername + '/' ;
          
             if (dFolderMap.ContainsKey(currentfoldername))
             {
                // foldername = nwords[itercnt];
                 currentpath = currentfoldername;
             }
             else
             {
             }
             itercnt = itercnt + 1;

        }
        
        

    }

    private void CreateTagRequest(XmlWriter writer, string tag, string color, int requestId)
    {
        writer.WriteStartElement("CreateTagRequest", "urn:zimbraMail");
        if (requestId != -1)
            writer.WriteAttributeString("requestId", requestId.ToString());
        writer.WriteStartElement("tag");
        writer.WriteAttributeString("name", tag);
        writer.WriteAttributeString("color", color);
        writer.WriteEndElement();               // tag
        writer.WriteEndElement();               // CreateTagRequest
    }

    public int CreateTag(string tag, string color, out string tagID)
    {
        tagID = "";
        lastError = "";
        Log.trace("ZimbraAPI : CreateTag");
        int retval = 0;
        try
        {

            WebServiceClient client = new WebServiceClient
            {
                Url = ZimbraValues.GetZimbraValues().Url,
                WSServiceType =
                    WebServiceClient.ServiceType.Traditional
            };
            StringBuilder sb = new StringBuilder();
            XmlWriterSettings settings = new XmlWriterSettings();

            settings.OmitXmlDeclaration = true;
            using (XmlWriter writer = XmlWriter.Create(sb, settings))
            {
                writer.WriteStartDocument();
                writer.WriteStartElement("soap", "Envelope",
                    "http://www.w3.org/2003/05/soap-envelope");

                WriteHeader(writer, true, true, true);

                writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");

                CreateTagRequest(writer, tag, color, -1);

                writer.WriteEndElement();           // soap body
                writer.WriteEndElement();           // soap envelope
                writer.WriteEndDocument();
            }

            string rsp = "";
            WriteSoapLog(sb.ToString(), true);

            client.InvokeService(sb.ToString(), out rsp);

            WriteSoapLog(rsp.ToString(), false);
            retval = client.status;
            if (client.status == 0)
            {
                ParseCreateTag(rsp, out tagID);       // get the id
            }
            else
            {
                string soapReason = ParseSoapFault(client.errResponseMessage);

                if (soapReason.Length > 0)
                    lastError = soapReason;
                else
                    lastError = client.exceptionMessage;
            }
        }
        catch (Exception e)
        {
            Log.err("ZimbraApi:Exception in CreateTag {0}: {1}", tag, e.Message);
        }
        return retval;
    }

    private void GetTagRequest(XmlWriter writer, int requestId)
    {
        writer.WriteStartElement("GetTagRequest", "urn:zimbraMail");
        if (requestId != -1)
            writer.WriteAttributeString("requestId", requestId.ToString());
        writer.WriteEndElement();               // CreateTagRequest
    }

    public int GetTags()
    {
        Log.trace("ZimbraAPI : GetTags");
        lastError = "";

        int retval = 0;
        WebServiceClient client = new WebServiceClient
        {
            Url = ZimbraValues.GetZimbraValues().Url,
            WSServiceType =
                WebServiceClient.ServiceType.Traditional
        };
        StringBuilder sb = new StringBuilder();
        XmlWriterSettings settings = new XmlWriterSettings();

        settings.OmitXmlDeclaration = true;
        using (XmlWriter writer = XmlWriter.Create(sb, settings))
        {
            writer.WriteStartDocument();
            writer.WriteStartElement("soap", "Envelope",
                "http://www.w3.org/2003/05/soap-envelope");

            WriteHeader(writer, true, true, true);

            writer.WriteStartElement("Body", "http://www.w3.org/2003/05/soap-envelope");

            GetTagRequest(writer, -1);

            writer.WriteEndElement();           // soap body
            writer.WriteEndElement();           // soap envelope
            writer.WriteEndDocument();
        }

        string rsp = "";
        WriteSoapLog(sb.ToString(),true);
        
        client.InvokeService(sb.ToString(), out rsp);
        
        WriteSoapLog(rsp.ToString(),false);
        retval = client.status;
        if (client.status == 0)
        {
            ParseGetTag(rsp);   // will store in ZimbraValues
        }
        else
        {
            string soapReason = ParseSoapFault(client.errResponseMessage);

            if (soapReason.Length > 0)
                lastError = soapReason;
            else
                lastError = client.exceptionMessage;
        }
        return retval;
    }

    private bool IAmTheOrganizer(string theOrganizer)
    {
        int idxAcc = AccountName.IndexOf("@");
        int idxOrg = theOrganizer.IndexOf("@");
        if (idxOrg == -1)
        {
            int idxCN = theOrganizer.LastIndexOf("CN=");
            string Name = theOrganizer.Substring((idxCN +3));
            string nameAcc1 = AccountName.Substring(0, idxAcc);
            return ((nameAcc1.ToUpper() == Name) || (AccountID.ToUpper() == Name));
        }
        if ((idxAcc == -1) || (idxOrg == -1))   // can happen if no recip table
        {
            return false;
        }
        string nameAcc = AccountName.Substring(0, idxAcc);
        string nameOrg = theOrganizer.Substring(0, idxOrg);
        return ((nameAcc == nameOrg) || (AccountID == nameOrg));
    }

    private void DeleteApptTempFiles(Dictionary<string, string> appt, int numExceptions)
    {
        string attr = "";
        if (appt["content0"].Length > 0)
        {
            File.Delete(appt["content0"]);
        }
        if (appt["content1"].Length > 0)
        {
            File.Delete(appt["content1"]);
        }
        for (int i = 0; i < numExceptions; i++)
        {
            attr = "content0" + "_" + i.ToString();
            if (appt[attr].Length > 0)
            {
                if (File.Exists(appt[attr]))
                {
                    File.Delete(appt[attr]);
                }
            }
            attr = "content1" + "_" + i.ToString();
            if (appt[attr].Length > 0)
            {
                if (File.Exists(appt[attr]))
                {
                    File.Delete(appt[attr]);
                }
            }
        }
    }

    // ///////////////////////

   private bool WriteSoapLog(string  message,bool request)
{
    bool bReturn        = false;
    if (loglevel == LogLevel.Trace)
    {
        try
        {
            
            string path = Path.GetTempPath() + "\\SoapXml.log";
            FileStream fs = new FileStream(path, FileMode.Append,
                    FileAccess.Write);
            StreamWriter w = new StreamWriter(fs);
            if( request)
            w.WriteLine(DateTime.Now + "" +"Soap Request -------------------");
            else
                w.WriteLine(DateTime.Now+ ""+ "Soap Response -------------------");
            w.WriteLine(message);
            w.WriteLine("\n");
            w.Close();
            bReturn = true;
        }
        catch (Exception e)
        {
            bReturn = false;
            Log.err(" ZimbraAPI::Exception is WriteSoapLog ", e.Message);
        }
    }
    return bReturn;
}

}
}
