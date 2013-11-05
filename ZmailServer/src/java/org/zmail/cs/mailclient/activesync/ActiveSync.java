package org.zmail.cs.mailclient.activesync;

import org.zmail.cs.mailclient.activesync.ASTypes;
import org.zmail.cs.mailclient.activesync.ASFunctions;
import org.zmail.cs.mailclient.activesync.ASTypes.*;

import java.io.*;
import java.nio.file.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.security.SecureRandom;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.EncoderException;
import org.apache.log4j.Logger;

public class ActiveSync {

    Logger logger = Logger.getLogger(ActiveSync.class);

    private ASFunctions mAsf = null;
    private ASTypes mAst = null;

    private TreeMap<String, String> mFoldermap = new TreeMap<String, String>();
    private TreeMap<String, Integer> mStatuses = new TreeMap<String,Integer>();
    private TreeMap<String, String> mSynckeys = new TreeMap<String, String>();

    public ActiveSync(String serverName, String domain, String username, String password, boolean useSSL,
                      boolean acceptAllCerts, String policyKey, String activeSyncVersion, String deviceType, String deviceId) {
        mAsf = new ASFunctions(serverName, domain, username, password, useSSL,
                               acceptAllCerts, policyKey, activeSyncVersion, deviceType, deviceId);
        mAst = mAsf.getAst();
    }

    public String getServerId(String displayname) {
        String serverid = mFoldermap.get(displayname);
        return (serverid == null) ? "0" : serverid;
    }

    public void setServerId(String displayname, String serverid) {
        mFoldermap.put(displayname, serverid);
    }

    private String getSyncKey(String keyname) {
        String synckey = mSynckeys.get(keyname);
        return (synckey == null) ? "0" : synckey;
    }

    public void setSyncKey(String keyname, String synckey) {
        mSynckeys.put(keyname, synckey);
    }

    @SuppressWarnings("unused")
    private Integer getStatus(String keyname) {
        Integer status = mStatuses.get(keyname);
        return status;
    }

    public void setStatus(String keyname, Integer status) {
        mStatuses.put(keyname, status);
    }

    // This might be a bit heavy here, we're only generating Mail IDs, not secure keys.
    private final SecureRandom random = new SecureRandom();

    private String randomString(String alphabet, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) 
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return sb.toString();
    }

    public String makeClientId() { return randomString("0123456789",20); }

    /**
     * Perform foldersync from the stored "foldersync" synckey value.
     * Update the status and synckey values on exit.
     * Also updates the stored folder serverid map.
     * Should be called on a regular basis to keep folders up to date.
     */
    public AS_FolderSyncB foldersync() throws Exception {
        if (mAsf == null || mAst == null) throw new Exception("ActiveSync not initialised");
        AS_FolderSyncF fs = mAst.FolderSyncF_default();
        fs.set_SyncKey(getSyncKey("foldersync"));
        AS_FolderSyncB foldersync = mAsf.foldersync(null, fs);
        if (foldersync != null) {
            mStatuses.put("foldersync",foldersync.Status);
            mSynckeys.put("foldersync",foldersync.SyncKey);
            if (foldersync.Changes != null) {
                for (AS_FolderSyncChange fsc : foldersync.Changes) {
                    if (fsc.Add != null) mFoldermap.put(fsc.Add.DisplayName, fsc.Add.ServerId);
                    if (fsc.Update != null) mFoldermap.put(fsc.Update.DisplayName, fsc.Update.ServerId);
                    if (fsc.Delete != null) mFoldermap.remove(fsc.Delete.ServerId);
                }
            }
        }
        return foldersync;
    }

    /**
     * Debug code, print out result from foldersync call.
     */
    public void print_foldersync(AS_FolderSyncB foldersync) {
        if (foldersync.Status != null) System.out.println("foldersync.Status = "+foldersync.Status);
        if (foldersync.SyncKey != null) System.out.println("foldersync.SyncKey = "+foldersync.SyncKey);
        if (foldersync.Changes != null) {
            for (AS_FolderSyncChange fsc : foldersync.Changes) {
                if (fsc.Count != null) System.out.println("foldersync.Changes.Count = "+fsc.Count);
                if (fsc.Add != null) {
                    System.out.println("foldersync.Changes.Add.ServerId = "+fsc.Add.ServerId);
                    System.out.println("foldersync.Changes.Add.ParentId = "+fsc.Add.ParentId);
                    System.out.println("foldersync.Changes.Add.DisplayName = "+fsc.Add.DisplayName);
                    System.out.println("foldersync.Changes.Add.Type = "+fsc.Add.Type);
                }
                if (fsc.Update != null) {
                    System.out.println("foldersync.Changes.Update.ServerId = "+fsc.Update.ServerId);
                    System.out.println("foldersync.Changes.Update.ParentId = "+fsc.Update.ParentId);
                    System.out.println("foldersync.Changes.Update.DisplayName = "+fsc.Update.DisplayName);
                    System.out.println("foldersync.Changes.Update.Type = "+fsc.Update.Type);
                }
                if (fsc.Delete != null) {
                    System.out.println("foldersync.Changes.Delete.ServerId = "+fsc.Delete.ServerId);
                }
            }
        }
    }

    private AS_Sync sync(AS_Sync syncf) throws Exception {
        if (mAsf == null || mAst == null) throw new Exception("ActiveSync not initialised");
        return mAsf.sync(null, syncf);
    }

    private void sync_status(AS_Sync syncb, String collectionid) {
        if (syncb != null) {
            setStatus(collectionid, syncb.Collections.Collection.Status);
            setSyncKey(collectionid, syncb.Collections.Collection.SyncKey);
        }
    }

    /**
     * Simple Sync command.
     * Gets the SyncKey from the internal map for the given CollectionId value.
     * Mostly used for getting the initial SyncKey value.
     */
    public AS_Sync sync_simple(String collectionid) throws Exception {
        AS_Sync syncf = mAst.Sync_default();
        syncf.Collections.Collection.set_SyncKey(getSyncKey(collectionid));
        syncf.Collections.Collection.set_CollectionId(collectionid);
        AS_Sync syncb = sync(syncf);
        sync_status(syncb, collectionid);
        return syncb;
    }

    /**
     * Sync command with GetChanges set.
     * Currently only works with Email class.
     */
    public AS_Sync sync_getchanges(String collectionid,
                                   AS_MIMESupport mimesupport, AS_MIMETruncation mimetruncation, AS_Class cls) throws Exception {
        AS_BodyPreference bodypreference = mAst.new AS_BodyPreference(AS_Type.text,1024,true,null);
        AS_Options options = mAst.new AS_Options(null,mimesupport,mimetruncation,null,cls,bodypreference,null);
        AS_Collection collectionout = mAst.Collection_default();
        collectionout.set_SyncKey(getSyncKey(collectionid));
        collectionout.set_CollectionId(collectionid);
        collectionout.set_GetChanges(true);
        collectionout.set_Options(options);
        AS_Sync syncf = mAst.Sync_default();
        syncf.set_Collections(mAst.new AS_Collections(collectionout));
        AS_Sync syncb = sync(syncf);
        sync_status(syncb, collectionid);
        return syncb;
    }

    /**
     * Retrieve an attachment from the ActiveSync server.
     * Uses the FileReference value in the attachment parameter.
     * 
     * @param attachment
     * @return Returns an AS_AttachFetchResponse value.
     * @throws Exception
     */
    public AS_AttachFetchResponse fetch_attachment(AS_Attachment attachment) throws Exception {
        if (mAsf == null || mAst == null) throw new Exception("ActiveSync not initialised");
        AS_Fetch fetch = mAst.new AS_Fetch();
        List<AS_ItemOperationsFetch> iofs = new ArrayList<AS_ItemOperationsFetch>(1);
        AS_ItemOperationsFetch iof = mAst.new AS_ItemOperationsFetch();
        iof.set_Store(AS_Store.mailbox); 
        iof.set_FileReference(attachment.FileReference);
        iofs.add(iof);
        fetch.set_Fetch(iofs);
        AS_AttachFetchResponse fetchresponse = mAsf.itemoperationsfetch(null,fetch);
        if (!fetchresponse.FileReference.equalsIgnoreCase(attachment.FileReference)) {
            throw new Exception("mismatching FileReferences, expected "+attachment.FileReference+
                                " got "+fetchresponse.FileReference);
        }
        return fetchresponse;
    }

    /**
     * Return a list of attachments from an ActiveSync email data value.
     * @param data  AS_EmailApplicationData value returned by a Fetch or Sync command.
     * @return List of AS_Attachment values.
     */
    public List<AS_Attachment> get_attachments(AS_EmailApplicationData data) {
        List<AS_Attachment> attachments = new ArrayList<AS_Attachment>(data.Attachments.size());
        for (AS_Attachments attachment : data.Attachments) {
            attachments.add(attachment.Attachment);
        }
        return attachments;
    }

    /**
     * Return the content type for the body of an email.
     * No body will return "unknown/unknown", as will the "mime" body type.
     * @param data  AS_EmailApplicationData value returned by a Fetch or Sync command.
     * @return String containing content type.
     */
    public String emailBodyType(AS_EmailApplicationData data) {
        String bodyType = "unknown/unknown";
        if (data.Body != null) {
            switch (data.Body.Type) {
            case text: bodyType = "text/plain"; break;
            case html: bodyType = "text/html"; break;
            case rtf: bodyType = "text/enriched"; break;
            case mime: bodyType = "unknown/unknown"; break;
            }
        }
        return bodyType;
    }

    /**
     * Convert an ActiveSync email into a MimeMessage object.
     * 
     * @param ServerId The ActiveSync ServerId value for this email.
     * @param data AS_EmailApplicationData value returned by a Fetch or Sync command.
     * @return A MimeMessage value constructed from the ActiveSync data.
     * @throws Exception
     */
    public Message emailToMimeMessage(String ServerId, AS_EmailApplicationData data) throws Exception {
        Message message = new MimeMessage(Session.getDefaultInstance(System.getProperties()));
        if (data.From != null)
            message.setFrom(new InternetAddress(data.From));
        if (data.To != null)
            for (String to : data.To.split("\\s*,\\s*")) message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        if (data.Cc != null)
            for (String cc : data.Cc.split("\\s*,\\s*")) message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
        if (data.DateReceived != null) {
            Date dateReceived = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(data.DateReceived);
            message.setSentDate(dateReceived);
        } else {
            message.setSentDate(new Date());
        }
        if (data.Read != null)
            message.setFlag(Flags.Flag.SEEN, data.Read != 0);
        if (data.Flag != null && data.Flag.size() > 0) {
            for (AS_Flag flag : data.Flag) {
                switch (flag.which) {
                case Status: if (flag.Status != null) message.setFlag(Flags.Flag.FLAGGED, flag.Status != AS_flag_status.cleared);
                default: message.setFlag(Flags.Flag.FLAGGED, false); break;
                }
            }
        }
        message.setHeader("ZM-ActiveSync-ServerId",ServerId);
        if (data.Body == null) return message; // actually, no body is illegal, throw an exception???
        String contentType;
        BodyPart part;
        contentType = emailBodyType(data);
        if (data.Attachments.size() == 0) {
            if (data.Body.Data != null) {
                message.setContent(new ByteArrayDataSource(data.Body.Data.getBytes(), contentType), contentType);
            }
        } else {
            Multipart multi = new MimeMultipart("mixed");
            part = new MimeBodyPart();
            if (data.Body.Data != null) {
                part.setContent(new ByteArrayDataSource(data.Body.Data.getBytes(), contentType), contentType);
            }
            multi.addBodyPart(part);
            for (AS_Attachments attachment : data.Attachments) {
                AS_AttachFetchResponse downloadedAttachment;
                if ((downloadedAttachment = fetch_attachment(attachment.Attachment)) != null) {
                    part = new MimeBodyPart();
                    contentType = downloadedAttachment.Properties.ContentType;
                    part.setDisposition("attachment; filename=\""+attachment.Attachment.DisplayName+"\"");
                    part.setContent(new ByteArrayDataSource(downloadedAttachment.Properties.Data, contentType), contentType);
                }
            }
            message.setContent(multi,"mime/multi");
        }
        return message;
    }

    public interface Callable2<I1, I2> {
        public void call(I1 input1, I2 input2);
    }

    /**
     * Scan an ActiveSync result and pass the actions to the given function.
     * @param syncb The ActiveSync AS_Sync result.
     * @param fn A Callable function which is passed the actions ("add", "change" or "delete") plus the email.
     * @throws Exception
     */
    public void convertToMimeMessages(AS_Sync syncb, Callable2<String,Message> fn) throws Exception {
        if (syncb.Collections.Collection.Commands != null) {
            for (AS_SyncCommandsAlt cmd : syncb.Collections.Collection.Commands) {
                switch (cmd.which) {
                case Add: fn.call("add",emailToMimeMessage(cmd.Add.ServerId, cmd.Add.ApplicationData)); break;
                case Change: fn.call("change",emailToMimeMessage(cmd.Change.ServerId, cmd.Change.ApplicationData)); break;
                case Delete:
                    Message message = new MimeMessage(Session.getDefaultInstance(System.getProperties()));
                    message.setHeader("ZM-ActiveSync-ServerId",cmd.Delete.ServerId);
                    fn.call("delete",message);
                    break;
                default: break;
                }
            }
        }
    }

    /**
     * Debug code, print out result from sync call.
     */
    public void print_sync(AS_Sync syncb) throws Exception {
        if (syncb.Collections.Collection.Status != null)
            System.out.println("sync.Collections.Status = "+syncb.Collections.Collection.Status);
        if (syncb.Collections.Collection.SyncKey != null)
            System.out.println("sync.Collections.SyncKey = "+syncb.Collections.Collection.SyncKey);
        if (syncb.Collections.Collection.CollectionId != null)
            System.out.println("sync.Collections.CollectionId = "+syncb.Collections.Collection.CollectionId);
        if (syncb.Collections.Collection.Commands != null) {
            for (AS_SyncCommandsAlt cmd : syncb.Collections.Collection.Commands) {
                switch (cmd.which) {
                case Add:
                    if (cmd.Add.ApplicationData.From != null)
                        System.out.println("cmd.Add.ApplicationData.From = "+cmd.Add.ApplicationData.From);
                    if (cmd.Add.ApplicationData.To != null)
                        System.out.println("cmd.Add.ApplicationData.To = "+cmd.Add.ApplicationData.To);
                    if (cmd.Add.ApplicationData.Cc != null)
                        System.out.println("cmd.Add.ApplicationData.Cc = "+cmd.Add.ApplicationData.Cc);
                    if (cmd.Add.ApplicationData.Subject != null)
                        System.out.println("cmd.Add.ApplicationData.Subject = "+cmd.Add.ApplicationData.Subject);
                    if (cmd.Add.ApplicationData.DateReceived != null)
                        System.out.println("cmd.Add.ApplicationData.DateReceived = "+cmd.Add.ApplicationData.DateReceived);
                    if (cmd.Add.ApplicationData.DisplayTo != null)
                        System.out.println("cmd.Add.ApplicationData.DisplayTo = "+cmd.Add.ApplicationData.DisplayTo);
                    if (cmd.Add.ApplicationData.ThreadTopic != null)
                        System.out.println("cmd.Add.ApplicationData.ThreadTopic = "+cmd.Add.ApplicationData.ThreadTopic);
                    if (cmd.Add.ApplicationData.Importance != null)
                        System.out.println("cmd.Add.ApplicationData.Importance = "+cmd.Add.ApplicationData.Importance);
                    if (cmd.Add.ApplicationData.Read != null)
                        System.out.println("cmd.Add.ApplicationData.Read = "+cmd.Add.ApplicationData.Read);
                    if (cmd.Add.ApplicationData.Attachments.size() > 0) {
                        System.out.println("cmd.Add.ApplicationData.Attachments:");
                        for (AS_Attachments attachment : cmd.Add.ApplicationData.Attachments) {
                            System.out.println("    attachment.DisplayName = "+attachment.Attachment.DisplayName);
                            System.out.println("    attachment.FileReference = "+attachment.Attachment.FileReference);
                            if (attachment.Attachment.Method != null) 
                                System.out.println("    attachment.Method = "+attachment.Attachment.Method);
                            if (attachment.Attachment.EstimatedDataSize != null) 
                                System.out.println("    attachment.EstimatedDataSize = "+attachment.Attachment.EstimatedDataSize);
                            if (attachment.Attachment.ContentId != null) 
                                System.out.println("    attachment.ContentId = "+attachment.Attachment.ContentId);
                            if (attachment.Attachment.ContentLocation != null) 
                                System.out.println("    attachment.ContentLocation = "+attachment.Attachment.ContentLocation);
                            if (attachment.Attachment.IsInline != null) 
                                System.out.println("    attachment.IsInline = "+attachment.Attachment.IsInline);
                        }
                    }
                    if (cmd.Add.ApplicationData.Body != null) {
                        System.out.println("cmd.Add.ApplicationData.Body:");
                        if (cmd.Add.ApplicationData.Body.Type != null)
                            System.out.println("    cmd.Add.ApplicationData.Body.Type = "+cmd.Add.ApplicationData.Body.Type);
                        if (cmd.Add.ApplicationData.Body.EstimatedDataSize != null)
                            System.out.println("    cmd.Add.ApplicationData.Body.EstimatedDataSize = "+cmd.Add.ApplicationData.Body.EstimatedDataSize);
                        if (cmd.Add.ApplicationData.Body.Truncated != null)
                            System.out.println("    cmd.Add.ApplicationData.Body.Truncated = "+cmd.Add.ApplicationData.Body.Truncated);
                        if (cmd.Add.ApplicationData.Body.Data != null)
                            System.out.println("    cmd.Add.ApplicationData.Body.Data = "+cmd.Add.ApplicationData.Body.Data);
                        if (cmd.Add.ApplicationData.Body.Part != null)
                            System.out.println("    cmd.Add.ApplicationData.Body.Part = "+cmd.Add.ApplicationData.Body.Part);
                        if (cmd.Add.ApplicationData.Body.Preview != null)
                            System.out.println("    cmd.Add.ApplicationData.Body.Preview = "+cmd.Add.ApplicationData.Body.Preview);
                    }
                    if (cmd.Add.ApplicationData.MessageClass != null)
                        System.out.println("cmd.Add.ApplicationData.MessageClass = "+cmd.Add.ApplicationData.MessageClass);
                    if (cmd.Add.ApplicationData.InternetCPID != null)
                        System.out.println("cmd.Add.ApplicationData.InternetCPID = "+cmd.Add.ApplicationData.InternetCPID);
                    if (cmd.Add.ApplicationData.Flag.size() > 0) {
                        System.out.println("cmd.Add.ApplicationData.Flags:");
                        for (AS_Flag flag : cmd.Add.ApplicationData.Flag) {
                            switch (flag.which) {
                            case Status: System.out.println("    flag.Status = "+flag.Status); break;
                            default: break;
                            }
                        }
                    }
                    if (cmd.Add.ApplicationData.ContentClass != null)
                        System.out.println("cmd.Add.ApplicationData.ContentClass = "+cmd.Add.ApplicationData.ContentClass);
                    if (cmd.Add.ApplicationData.NativeBodyType != null)
                        System.out.println("cmd.Add.ApplicationData.NativeBodyType = "+cmd.Add.ApplicationData.NativeBodyType);
                    if (cmd.Add.ApplicationData.ConversationId != null)
                        System.out.println("cmd.Add.ApplicationData.ConversationId = "+cmd.Add.ApplicationData.ConversationId);
                    if (cmd.Add.ApplicationData.ConversationIndex != null)
                        System.out.println("cmd.Add.ApplicationData.ConversationIndex = "+cmd.Add.ApplicationData.ConversationIndex);
                    if (cmd.Add.ApplicationData.EmailCategories != null) {
                        System.out.println("cmd.Add.ApplicationData.EmailCategories.Category:");
                        for (String category : cmd.Add.ApplicationData.EmailCategories.Category) {
                            System.out.println("    category= "+category);
                        }
                    }
                    if (cmd.Add.ApplicationData.LastVerbExecuted != null)
                        System.out.println("cmd.Add.ApplicationData.LastVerbExecuted = "+cmd.Add.ApplicationData.LastVerbExecuted);
                    if (cmd.Add.ApplicationData.LastVerbExecutionTime != null)
                        System.out.println("cmd.Add.ApplicationData.LastVerbExecutionTime = "+cmd.Add.ApplicationData.LastVerbExecutionTime);
                    break;
                default:
                    break;
                }
            }
        }
        return;
    }

    private String list_of_emails(List<String> emails) {
        if (emails == null) return "";
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int len = emails.size()-1;
        for (String email : emails) {
            sb.append(email);
            if (i < len) sb.append(", ");
            i++;
        }
        return sb.toString();
    }

    /**
     * Helper type, used to build an email for conversion to mime data.
     * This represents an email body, either text or html.
     */
    public class AS_MailBody {
        public String text;
        public String html;
        public AS_MailBody(String text, String html) {
            this.text = text; this.html = html;
        };
    }

    /**
     * Helper type, used to build an email for conversion to mime data.
     * This represents an email attachment.
     */
    public class AS_MailAttachment {
        String mimetype;
        String filename;
        String encoding;
        byte[] data;
        public AS_MailAttachment(String mimetype, String filename, byte[] data, String encoding) {
            this.mimetype = mimetype; this.filename = filename; this.data = data; this.encoding = encoding;
        }
    }

    /**
     * Helper type, passed to mime_from_message.
     */
    public class AS_Mail {
        public String id;
        public String from;
        public List<String> to;
        public List<String> cc;
        public String subject;
        public Date creation_date;
        public AS_MailBody body;
        public List<AS_MailAttachment> attachments;
        public AS_Mail() { }
        public void set_id(String id) { this.id = id; }
        public void set_from(String from) { this.from = from; }
        public void set_to(List<String> to) { this.to = to; }
        public void set_cc(List<String> cc) { this.cc = cc; }
        public void set_subject(String subject) { this.subject = subject; }
        public void set_creation_date(Date creation_date) { this.creation_date = creation_date; }
        public void set_body(AS_MailBody body) { this.body = body; }
        public void set_attachments(List<AS_MailAttachment> attachments) { this.attachments = attachments; }
    }

    /**
     * Simple email builder, From, To, Subject and Text.
     */
    public AS_Mail simple_email_text(String from, String sto, String subject, String text) {
        AS_Mail mail = new AS_Mail();
        mail.set_id(UUID.randomUUID().toString());
        mail.set_subject(subject);
        mail.set_from(from);
        List<String> to = new ArrayList<String>();
        to.add(sto);
        mail.set_to(to);
        mail.set_creation_date(new Date());
        mail.set_body(new AS_MailBody(text,""));
        return mail;
    }

    private byte[] readFile(String path) throws IOException {
        File file = new File(path);
        byte[] bytes = new byte[(int)file.length()];
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
        dataInputStream.readFully(bytes);
        dataInputStream.close();
        return bytes;
    }

    /**
     * Add an attachement to an AS_Mail object.
     */
    public void add_attachment(AS_Mail mail, String pathname, boolean base64) throws IOException {
        File file = new File(pathname);
        byte[] data = readFile(pathname);
        Path path = Paths.get(pathname);
        String mimetype = Files.probeContentType(path);
        AS_MailAttachment attachment = new AS_MailAttachment(mimetype, file.getName(), data, (base64)?"base64":"");
        if (mail.attachments == null) {
            List<AS_MailAttachment> attachments = new ArrayList<AS_MailAttachment>();
            attachments.add(attachment);
            mail.set_attachments(attachments);
        } else {
            mail.attachments.add(attachment);
        }
    }

    /**
     * Convert an AS_Mail type into a mime string.
     * TODO: should really return byte[] data.
     */
    public String mime_from_message(AS_Mail mail) throws EncoderException {
        String id = (mail.id != null) ? "Message-ID: "+mail.id+"\r\n" : "";
        String from = (mail.from != null) ? "From: "+mail.from+"\r\n" : "";
        String to = (mail.to != null) ? "To: "+list_of_emails(mail.to)+"\r\n" : "";
        String cc = (mail.cc != null) ? "Cc: "+list_of_emails(mail.cc)+"\r\n" : "";
        //bcc will have to be handled by the caller.
        String subject = (mail.subject != null) ? "Subject: "+mail.subject+"\r\n" : "";
        String date =
            (mail.creation_date != null)
            ? "Date: "+DatatypeConverter.printDateTime(mAst.DateToCalendar(mail.creation_date))+"\r\n"
            : "";
        String xm = "X-Mailer: MLstate Exchange Client\r\n";
        String cte = "Content-Transfer-Encoding: 7bit\r\n";
        String mv = "Mime-Version: 1.0\r\n";
        String ct;
        String content;
        if (mail.attachments == null || mail.attachments.size() == 0) {
            if (mail.body.html.equalsIgnoreCase("")) {
                ct = "Content-Type: text/plain\r\n"; content = mail.body.text;
            } else {
                if (mail.body.text.equalsIgnoreCase("")) {
                    ct = "Content-Type: text/html\r\n"; content = mail.body.html;
                } else {
                    String boundary = randomString("0123456789abcdefghijklmnopqrstuvwxyz",20);
                    ct = "Content-Type: multipart/alternative; boundary="+boundary+"\r\n";
                    content = "--"+boundary+"\r\nContent-Type: text/plain\r\n\r\n"+mail.body.text+"\r\n--"+
                                   boundary+"\r\nContent-Type: text/html\r\n\r\n"+mail.body.html+"\r\n--"+boundary+"--";
                }
            }
        } else {
            StringBuffer cntnt = new StringBuffer();
            String boundary = randomString("0123456789abcdefghijklmnopqrstuvwxyz",20);
            ct = "Content-Type: multipart/mixed; boundary="+boundary+"\r\n";
            if (!mail.body.text.equalsIgnoreCase("")) {
                cntnt.append("--"); cntnt.append(boundary);
                cntnt.append("\r\nContent-Type: text/plain\r\n\r\n"); cntnt.append(mail.body.text);
            }
            if (!mail.body.html.equalsIgnoreCase("")) {
                cntnt.append("--"); cntnt.append(boundary);
                cntnt.append("\r\nContent-Type: text/html\r\n\r\n"); cntnt.append(mail.body.html);
            }
            for (AS_MailAttachment att : mail.attachments) {
                String acte;
                String data;
                switch (att.encoding) {
                case "base64":
                    acte = "Content-Transfer-Encoding: base64\r\n";
                    data = DatatypeConverter.printBase64Binary(att.data);
                    int dlen = data.length();
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < dlen;) {
                        int len = (dlen-i>72)?72:dlen-i;
                        String frag = data.substring(i, i+len);
                        sb.append(frag); sb.append("\r\n");
                        i += len;
                    }
                    data = sb.toString();
                    break;
                default: acte = ""; data = new String(att.data); break;
                }
                cntnt.append("--"); cntnt.append(boundary);
                cntnt.append("\r\nContent-Type: "); cntnt.append(att.mimetype);
                cntnt.append("\r\nContent-Disposition: attachment; filename=\"");
                cntnt.append(att.filename); cntnt.append("\"\r\n");
                cntnt.append(acte); cntnt.append("\r\n");
                cntnt.append(data); cntnt.append("\r\n");
            }
            cntnt.append("--"); cntnt.append(boundary); cntnt.append("--"); 
            content = cntnt.toString();
        }
        return id+from+to+cc+subject+date+xm+cte+mv+ct+"\r\n"+content+"\r\n\r\n";
  }

    /**
     * Send an email.
     * Required clientid but accountid is optional.
     * Use mime_from_message to generate suitable mime data.
     */
    public AS_Status sendmail(String clientid, String accountid, Boolean saveinsentitems, byte[] mime) throws Exception {
        if (mAsf == null || mAst == null) throw new Exception("ActiveSync not initialised");
        AS_SendMail sm = mAst.SendMail_default();
        sm.set_ClientId(clientid);
        sm.set_AccountId(accountid);
        sm.set_SaveInSentItems(saveinsentitems);
        sm.set_Mime(mime);
        AS_Status status = mAsf.sendmail(null, sm);
        if (status != null)
            mStatuses.put("sendmail",status.Status);
        else
            mStatuses.put("sendmail",null);
        return status;
    }

    /**
     * Print the result of a sendmail return code.
     * Note that a successful send returns an empty reply and therefore no status code.
     */
    public void print_status(AS_Status status) {
        if (status.Status != null)
            System.out.println("status.Status = "+status.Status);
    }

}
