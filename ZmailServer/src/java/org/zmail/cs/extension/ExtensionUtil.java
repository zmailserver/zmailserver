/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010, 2012 VMware, Inc.
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
package org.zmail.cs.extension;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.zmail.common.localconfig.LC;
import org.zmail.common.util.ZmailLog;
import org.zmail.cs.redolog.op.RedoableOp;

public class ExtensionUtil {

    private static List<ZmailExtensionClassLoader> sClassLoaders = new ArrayList<ZmailExtensionClassLoader>();

    public static URL[] dirListToURLs(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        List<URL> urls = new ArrayList<URL>(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                URI uri = files[i].toURI();
                URL url = uri.toURL();
                urls.add(url);
                if (ZmailLog.extensions.isDebugEnabled()) {
                    ZmailLog.extensions.debug("adding url: " + url);
                }
            } catch (MalformedURLException mue) {
                ZmailLog.extensions.warn("ExtensionsUtil: exception creating url for " + files[i], mue);
            }
        }
        return urls.toArray(new URL[0]);
    }

    private static ClassLoader sExtParentClassLoader;

    static {
        File extCommonDir = new File(LC.zmail_extension_common_directory.value());
        URL[] extCommonURLs = dirListToURLs(extCommonDir);
        if (extCommonURLs == null) {
            // No ext-common libraries are present.
            sExtParentClassLoader = ExtensionUtil.class.getClassLoader();
        } else {
            sExtParentClassLoader = new URLClassLoader(extCommonURLs, ExtensionUtil.class.getClassLoader());
        }
        loadAll();
    }

    static synchronized void addClassLoader(ZmailExtensionClassLoader zcl) {
        sClassLoaders.add(zcl);
    }

    private static synchronized void loadAll() {
        if (LC.zmail_extension_directory.value() == null) {
            ZmailLog.extensions.info(LC.zmail_extension_directory.key() +
                    " is null, no extensions loaded");
            return;
        }
        File extDir = new File(LC.zmail_extension_directory.value());
        ZmailLog.extensions.info("Loading extensions from " + extDir.getPath());

        File[] extDirs = extDir.listFiles();
        if (extDirs == null) {
            return;
        }
        for (File dir : extDirs) {
            if (!dir.isDirectory()) {
                ZmailLog.extensions.warn("ignored non-directory in extensions directory: " + dir);
                continue;
            }

            ZmailExtensionClassLoader zcl = new ZmailExtensionClassLoader(
                    dirListToURLs(dir), sExtParentClassLoader);
            if (!zcl.hasExtensions()) {
                ZmailLog.extensions.warn("no " + ZmailExtensionClassLoader.ZIMBRA_EXTENSION_CLASS + " found, ignored: " + dir);
                continue;
            }

            sClassLoaders.add(zcl);
        }
    }

    private static Map<String, ZmailExtension> sInitializedExtensions = new LinkedHashMap<String, ZmailExtension>();

    public static synchronized void initAll() {
        ZmailLog.extensions.info("Initializing extensions");
        for (ZmailExtensionClassLoader zcl : sClassLoaders) {
            for (String name : zcl.getExtensionClassNames()) {
                try {
                    Class<?> clazz = zcl.loadClass(name);
                    ZmailExtension ext = (ZmailExtension) clazz.newInstance();
                    try {
                        ext.init();
                        RedoableOp.registerClassLoader(ext.getClass().getClassLoader());
                        String extName = ext.getName();
                        ZmailLog.extensions.info("Initialized extension " +
                                extName + ": " + name + "@" + zcl);
                        sInitializedExtensions.put(extName, ext);
                    } catch (ExtensionException e) {
                        ZmailLog.extensions.info(
                                "Disabled '" + ext.getName() + "' " +
                                e.getMessage());
                        ext.destroy();
                        RedoableOp.deregisterClassLoader(
                                ext.getClass().getClassLoader());
                    } catch (Exception e) {
                        ZmailLog.extensions.warn("exception in " + name + ".init()", e);
                        RedoableOp.deregisterClassLoader(
                                ext.getClass().getClassLoader());
                    }
                } catch (InstantiationException e) {
                    ZmailLog.extensions.warn("exception occurred initializing extension " + name, e);
                } catch (IllegalAccessException e) {
                    ZmailLog.extensions.warn("exception occurred initializing extension " + name, e);
                } catch (ClassNotFoundException e) {
                    ZmailLog.extensions.warn("exception occurred initializing extension " + name, e);
                }

            }
        }
    }

    public static synchronized void postInitAll() {
        ZmailLog.extensions.info("Post-Initializing extensions");

        for (Object o : sInitializedExtensions.values()) {
            if (o instanceof ZmailExtensionPostInit) {
                ((ZmailExtensionPostInit)o).postInit();
            }
        }
    }


    public static synchronized void destroyAll() {
        ZmailLog.extensions.info("Destroying extensions");
        List<String> extNames = new ArrayList<String>(sInitializedExtensions.keySet());
        for (String extName : extNames) {
            ZmailExtension ext = getExtension(extName);
            try {
                RedoableOp.deregisterClassLoader(ext.getClass().getClassLoader());
                ext.destroy();
                ZmailLog.extensions.info("Destroyed extension " + extName + ": " + ext.getClass().getName() + "@" + ext.getClass().getClassLoader());
            } catch (Exception e) {
                ZmailLog.extensions.warn("exception in " + ext.getClass().getName() + ".destroy()", e);
            }
        }
        sInitializedExtensions.clear();
    }

    public static synchronized Class<?> loadClass(String extensionName, String className) throws ClassNotFoundException {
        if (extensionName == null) {
            return Class.forName(className);
        }
        ZmailExtension ext = sInitializedExtensions.get(extensionName);
        if (ext == null) {
            throw new ClassNotFoundException("extension " + extensionName + " not found");
        }
        ClassLoader loader = ext.getClass().getClassLoader();
        return loader.loadClass(className);
    }

    /**
     * look for the specified class on our class path then across all extension class loaders and return first match.
     *
     * @param name class name to load
     * @return class
     * @throws ClassNotFoundException if class is not found
     */
    public static synchronized Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return ExtensionUtil.class.getClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            // ignore and look through extensions
        }
        for (ZmailExtensionClassLoader zcl : sClassLoaders) {
            try {
                return zcl.loadClass(name);
            } catch (ClassNotFoundException e) {
                // ignore and keep looking
            }
        }
        throw new ClassNotFoundException(name);
    }


    public static synchronized ZmailExtension getExtension(String name) {
        return (ZmailExtension) sInitializedExtensions.get(name);
    }
}
