/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import sun.misc.BASE64Encoder;

public class MyExportPrivKey {
        private File keystoreFile;
        private String keyStoreType;
        private char[] password;
        private String alias;
        private File exportedFile;

        public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
                try {
                        Key key=keystore.getKey(alias,password);
                        if(key instanceof PrivateKey) {
                                Certificate cert=keystore.getCertificate(alias);
                                PublicKey publicKey=cert.getPublicKey();
                                return new KeyPair(publicKey,(PrivateKey)key);
                        }
                } catch (UnrecoverableKeyException e) { return null;
        } catch (NoSuchAlgorithmException e) { return null;
        } catch (KeyStoreException e) { return null;
        }
        return null;
        }

        public void export() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
            KeyStore keystore=KeyStore.getInstance(keyStoreType);
            BASE64Encoder encoder=new BASE64Encoder();
            FileInputStream input = new FileInputStream(keystoreFile);
            keystore.load(input,password);
            KeyPair keyPair=getPrivateKey(keystore,alias,password);
            PrivateKey privateKey=keyPair.getPrivate();
            String encoded=encoder.encode(privateKey.getEncoded());
            FileWriter fw=new FileWriter(exportedFile);
            fw.write("-----BEGIN PRIVATE KEY-----\n");
            fw.write(encoded);
            fw.write("\n");
            fw.write("-----END PRIVATE KEY-----");
            fw.close();
            input.close();
        }

        /**
         * See http://www.anandsekar.com/2006/01/19/exporting-the-private-key-from-a-jks-keystore/
         * java -jar ExportPrivateKey.zip {keystore_path} JKS {keystore_password} {alias} {target_file}
         * This would export the key to PKCS #8 PEM format.
         * Then you need to run "openssl pkcs8 -inform PEM -nocrypt -in exported-pkcs8.key -out exported.key" afterwards
         * to convert it to the format of zimbra server.key (it is also apache modssl expects)
         * @param args
         * @throws Exception
         * 
         */
        public static void main(String args[]) throws Exception{
               MyExportPrivKey export=new MyExportPrivKey();
               export.keystoreFile=new File(args[0]);
               export.keyStoreType=args[1];
               export.password=args[2].toCharArray();
               export.alias=args[3];
               export.exportedFile=new File(args[4]);
               export.export();
        }
}
