/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * 
 * ***** END LICENSE BLOCK *****
 */
/**
 * editor_plugin_src.js
 *
 * Copyright 2009, Moxiecode Systems AB
 * Released under LGPL License.
 *
 * License: http://tinymce.moxiecode.com/license
 * Contributing: http://tinymce.moxiecode.com/contributing
 */

(function() {
	tinymce.create('tinymce.plugins.PageBreakPlugin', {
		init : function(ed, url) {
			var pb = '<img src="' + ed.theme.url + '/img/trans.gif" class="mcePageBreak mceItemNoResize" />', cls = 'mcePageBreak', sep = ed.getParam('pagebreak_separator', '<!-- pagebreak -->'), pbRE;

			pbRE = new RegExp(sep.replace(/[\?\.\*\[\]\(\)\{\}\+\^\$\:]/g, function(a) {return '\\' + a;}), 'g');

			// Register commands
			ed.addCommand('mcePageBreak', function() {
				ed.execCommand('mceInsertContent', 0, pb);
			});

			// Register buttons
			ed.addButton('pagebreak', {title : 'pagebreak.desc', cmd : cls});

			ed.onInit.add(function() {
				if (ed.theme.onResolveName) {
					ed.theme.onResolveName.add(function(th, o) {
						if (o.node.nodeName == 'IMG' && ed.dom.hasClass(o.node, cls))
							o.name = 'pagebreak';
					});
				}
			});

			ed.onClick.add(function(ed, e) {
				e = e.target;

				if (e.nodeName === 'IMG' && ed.dom.hasClass(e, cls))
					ed.selection.select(e);
			});

			ed.onNodeChange.add(function(ed, cm, n) {
				cm.setActive('pagebreak', n.nodeName === 'IMG' && ed.dom.hasClass(n, cls));
			});

			ed.onBeforeSetContent.add(function(ed, o) {
				o.content = o.content.replace(pbRE, pb);
			});

			ed.onPostProcess.add(function(ed, o) {
				if (o.get)
					o.content = o.content.replace(/<img[^>]+>/g, function(im) {
						if (im.indexOf('class="mcePageBreak') !== -1)
							im = sep;

						return im;
					});
			});
		},

		getInfo : function() {
			return {
				longname : 'PageBreak',
				author : 'Moxiecode Systems AB',
				authorurl : 'http://tinymce.moxiecode.com',
				infourl : 'http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/pagebreak',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		}
	});

	// Register plugin
	tinymce.PluginManager.add('pagebreak', tinymce.plugins.PageBreakPlugin);
})();