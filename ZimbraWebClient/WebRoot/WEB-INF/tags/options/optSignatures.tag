<%--
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
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
--%>
<%@ tag body-content="empty" %>
<%@ attribute name="mailbox" rtexprvalue="true" required="true" type="com.zimbra.cs.taglib.bean.ZMailboxBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="com.zimbra.i18n" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="app" uri="com.zimbra.htmlclient" %>
<fmt:setBundle basename='/messages/AjxMsg' var='AjxMsg' scope='request' />

<script type="text/javascript" src="../js/ajax/3rdparty/tinymce/tiny_mce.js"></script>
<body class="yui-skin-sam">
<table width="100%">
<tr>
<td>
<table class="ZOptionsSectionTable" width="100%">
<tr class="ZOptionsHeaderRow">
    <td class="ImgPrefsHeader_L">
        &nbsp;
    </td>
    <td class='ZOptionsHeader ImgPrefsHeader' >
        <fmt:message key="optionsSignatures"/>
    </td>
    <td class="ImgPrefsHeader_R">
        &nbsp;
    </td>
</tr>
</table>
<table width="100%"  class="ZOptionsSectionMain" cellspacing="6">
    <c:set var="numSigs" value="${0}"/>
    <zm:forEachSignature var="signature">
        <c:if test="${numSigs gt 0}">
            <tr>
                <td colspan="4">
                    <hr>
                </td>
            </tr>
        </c:if>
        <c:set var="isHtml" value="${fn:escapeXml(signature.type) eq 'text/html' ? true : false }"/>
        <tr>
            <td class='ZOptionsTableLabel'>
                <fmt:message key="optionsSignatureName"/>
                :
            </td>
            <td>
                <input type="hidden" name="signatureId${numSigs}" value="${fn:escapeXml(signature.id)}"/>
                <input type="hidden" name="origSignatureName${numSigs}" value="${fn:escapeXml(signature.name)}"/>
                <input id="signatureName${numSigs}" size="40" type="text" name='signatureName${numSigs}'
                       value="${fn:escapeXml(signature.name)}">
            </td>
            <td align=right>
                <input class='tbButton' type="submit" name="actionDeleteSig:${fn:escapeXml(signature.id)}"
                       value="<fmt:message key="delete"/>">
            </td>
            <td width="20%">&nbsp;</td>
        </tr>
        <tr>
            <td class='ZOptionsTableLabel' style='vertical-align:top;' valign='top'>
                <fmt:message key="optionsSignature"/>
                :
            </td>
            <td colspan=2 <c:if test="${isHtml}">style="background-color:white;"</c:if>>
                <input type="hidden" id="signatureType${numSigs}" name="signatureType${numSigs}" value="${fn:escapeXml(signature.type)}"/>
                <input type="hidden" name="origSignatureValue${numSigs}" value="${fn:escapeXml(signature.value)}"/>
                <textarea style='width:100%' id="signatureValue${numSigs}" name='signatureValue${numSigs}' cols='80' rows='5' style='<c:if test="${isHtml}">visibility:hidden;</c:if>width:100%'>${fn:escapeXml(signature.value)}</textarea>
            </td>
            <td width="20%">&nbsp;</td>
        </tr>
        <c:set var="numSigs" value="${numSigs+1}"/>
    </zm:forEachSignature>

    <tr>
        <td colspan="4">
            <input type="hidden" name="numSignatures" value="${numSigs}"/>
            <hr>
        </td>
    </tr>
    <c:set var="maxSigs" value="${mailbox.accountInfo.attrs.zimbraSignatureMaxNumEntries[0]}"/>    
    <c:choose>
        <c:when test="${not empty param.actionNewSig or requestScope.newSignatureWarning}">
            <tr>
                <td colspan="4" class='ZHeader'>
                    <fmt:message key="optionsNewSignature"/>
                </td>
            </tr>
            <tr>
                <td class='ZOptionsTableLabel'>
                    <label><fmt:message key="optionsSignatureName"/>:</label>
                </td>
                <td>
                    <input type="hidden" name="newSignature" value="TRUE"/>
                    <input id="newSignatureName" size="40" type="text" name='newSignatureName'
                           value="${fn:escapeXml(param.newSignatureName)}">
                </td>
                <td align=right>
                    <input class='tbButton' type="submit" name="actionCancelNewSig"
                           value="<fmt:message key="cancel"/>">
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class='ZOptionsTableLabel' style='vertical-align:top;' valign='top'>
                    <fmt:message key="optionsSignature"/>:
                </td>
                <td colspan=2 style="background-color: #FFFFFF;">
                    <input type="hidden" id="newSignatureType" name="newSignatureType" value="text/html"/>
                    <textarea style='width:100%' id="newSignatureValue" name='newSignatureValue' cols='80'rows='5' 
						style='visibility:hidden;width:100%'>${fn:escapeXml(param.newSignatureValue)}</textarea>
                </td>
                <td width="20%">&nbsp;</td>
            </tr>
        </c:when>
        <c:otherwise>
            <tr>
                <td>
                    &nbsp;
                </td>
                <td colspan=2>
                    <c:if test="${numSigs lt maxSigs}">
                    <input id="OPNEW" class='tbButton' type="submit" name="actionNewSig"
                           value="<fmt:message key="optionsAddSignature"/>">
                    </c:if>
                </td>
                <td width="20%">&nbsp;</td>
            </tr>
        </c:otherwise>
    </c:choose>
    <tr>
        <td class='ZOptionsTableLabel'>&nbsp;</td>
        <td colspan="3"><fmt:message key="optionsSignatureMaxNumber"><fmt:param value="${maxSigs}"></fmt:param></fmt:message></td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
</table>
<br/>
<table class="ZOptionsSectionTable" width="100%">
    <tr class="ZOptionsHeaderRow">
        <td class="ImgPrefsHeader_L">
            &nbsp;
        </td>
        <td class='ZOptionsHeader ImgPrefsHeader' >
            <fmt:message key="optionsUsingSignatures"/>
        </td>
        <td class="ImgPrefsHeader_R">
            &nbsp;
        </td>
    </tr>
</table>
<table width="100%" class="ZOptionsSectionMain" cellspacing="6">
    <tr>
        <td class='ZOptionsTableLabel'>
            <label><fmt:message key="optionsSignaturePlacement"/>:</label>
        </td>
        <td>
            <label><fmt:message key="optionsSignaturePlaceTheSignature"/>:</label>
        </td>
    </tr>
    <tr>
        <td class='ZOptionsTableLabel'>&nbsp;</td>
        <td>
            <table>
                <tr>
                    <td>
                        <input id="placeAbove" type="radio" name="zimbraPrefMailSignatureStyle" value="outlook"
							<c:if test="${mailbox.prefs.signatureStyleTop}">checked</c:if> />
                    </td>
                    <td>
                        <label for="placeAbove">
                            <fmt:message key="aboveQuotedText"/>
                        </label>
                    </td>
                    <td>
                        <input id="placeBelow" type="radio" name="zimbraPrefMailSignatureStyle" value="internet"
							<c:if test="${mailbox.prefs.signatureStyleBottom}">checked</c:if> />
                    </td>
                    <td>
                        <label for="placeBelow">
                            <fmt:message key="atBottomOfMessage"/>
                        </label>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
     <tr>
        <td colspan="4">
            <hr>
        </td>
    </tr>
    <tr>
		<td colspan='4' class='ZOptionsTableField' style='text-align:center;font-weight:bold;width:auto;'>
            <fmt:message key="optionsManageAccounts">
                <fmt:param><fmt:message key="optionsSigManageAccountsPre"/></fmt:param>
                <fmt:param><a href="options?selected=accounts"><fmt:message key="optionsManageAccountsLink"/></a></fmt:param>
                <fmt:param><fmt:message key="optionsManageAccountsPost"/></fmt:param>
            </fmt:message>
        </td>
    </tr>
    <tr>
        <td colspan="4">&nbsp;</td>
    </tr>
</table>
</td>
</tr>
</table>

<script type="text/javascript">

    (function(){
        <%-- Get font definitions from AjxMsg --%>
        var fonts = [];
        <c:forEach var="KEY" items="fontFamilyIntl,fontFamilyBase">
            <c:forEach var="i" begin="1" end="30">
                <fmt:message var="style" bundle='${AjxMsg}' key="${KEY}${i}.css"/>
                <c:choose>
                    <c:when test="${fn:startsWith(style, '#') or fn:startsWith(style, '?')}">
                    <%-- Do nothing --%>
                    </c:when>
                    <c:otherwise>
                        <c:set var="style" value="${fn:replace(style,', ',',')}"/>
                            <fmt:message var="name" bundle='${AjxMsg}' key="${KEY}${i}.display"/>
                        <c:set var="selected" value="${fn:replace(mailbox.prefs.htmlEditorDefaultFontFamily,', ',',') eq style}"/>
                            fonts.push("${name}=${style}");
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:forEach>

        var onTinyMCEEditorInit = function(ed){
            ed.dom.setStyles( ed.getBody(), {
                "font-family" : "${mailbox.prefs.htmlEditorDefaultFontFamily}",
                "font-size"   : "${mailbox.prefs.htmlEditorDefaultFontSize}",
                "color"       : "${mailbox.prefs.htmlEditorDefaultFontColor}"
            });
            if( ed.id === "newSignatureValue"){
                myEditor = ed;
                ed.focus();
            }
            else{
                myEdit.push(ed);
            }
        };

        var handleContentLoad = function(ed){
            var imageArray = ed.dom.select("img[dfsrc^='doc:']"),
                    path = ["/home/", "${mailbox.accountInfo.name}", "/"].join(""),
                    image;

            while( image = imageArray.shift() ){
                image.src = [path, image.getAttribute("dfsrc").substring(4)].join('');
            }
        };

        //Refer http://www.tinymce.com/i18n/index.php?ctrl=lang&act=download&pr_id=1
        var tinyMCELocaleArray = ['sq', 'ar', 'hy', 'az', 'eu', 'be', 'bn', 'nb', 'bs', 'br', 'bg', 'my', 'ca', 'km', 'ch', 'zh', 'hr', 'cs', 'da', 'dv', 'nl', 'en', 'eo', 'et', 'fi', 'fr', 'gl', 'ka', 'de', 'el', 'gu', 'he', 'hi', 'hu', 'is', 'id', 'ia', 'it', 'ja', 'kl', 'ko', 'lv', 'lt', 'lb', 'mk', 'ms', 'ml', 'mn', 'se', 'no', 'nn', 'fa', 'pl', 'pt', 'ps', 'ro', 'ru', 'sc', 'sr', 'si', 'sk', 'sl', 'es', 'sv', 'ta', 'tt', 'te', 'th', 'tn', 'tr', 'tw', 'uk', 'ur', 'vi', 'cy', 'zu', 'zh-tw', 'cn', 'zh-cn'],
                locale = "${mailbox.prefs.locale}" || "en";

        locale = locale.toLowerCase().replace("_", "-");
        if (tinymce.inArray(tinyMCELocaleArray, locale) === -1) {
            locale = locale.substr(0, 2);
            if (tinymce.inArray(tinyMCELocaleArray, locale) === -1) {
                locale = "en";
            }
        }

        var tinyMCEInitObj = {
            mode : "none",
            height : "200px",
            width : "100%",
            plugins : "advlist,inlinepopups,table,paste,directionality,emotions" + (tinymce.isIE ? "" : ",autolink"),
            theme : "advanced",
            theme_advanced_buttons1 : "fontselect,fontsizeselect,forecolor,backcolor,|,bold,italic,underline,strikethrough,|,bullist,numlist,|,outdent,indent,|,justifyleft,justifycenter,justifyright,|,image,link,unlink,emotions",
            theme_advanced_buttons2 : "formatselect,undo,redo,|,removeformat,|,pastetext,|,tablecontrols,|,blockquote,hr,charmap",
            theme_advanced_buttons3 : "",
            theme_advanced_buttons4 : "",
            theme_advanced_toolbar_location : "top",
            theme_advanced_toolbar_align : "left",
            theme_advanced_resizing : true,
            theme_advanced_fonts : fonts.join(";"),
            theme_advanced_statusbar_location : "none",
            convert_urls : false,
            verify_html : false,
            gecko_spellcheck : true,
            dialog_type : "modal",
            forced_root_block : "div",
            table_default_cellpadding : 3,
            table_default_border: 1,
            content_css : false,
            language : locale,
            theme_advanced_show_current_color : true,
            paste_retain_style_properties : "all",
            paste_remove_styles_if_webkit : false,
            setup : function(ed) {
                ed.onInit.add(onTinyMCEEditorInit);
                ed.onLoadContent.add(handleContentLoad);
                ed.onBeforeRenderUI.add(function() {
                    tinymce.ScriptLoader.loadScripts(['../js/ajax/3rdparty/tinymce/themes/advanced/Zmeditor_template.js']);
                });
                ed.onBeforeSetContent.add(function(ed, o) {
                    // Replaces all double br elements for avoiding enter issue
                    o.content = o.content.replace(/<br><br>/ig, '<br><div><br></div>');
                });
            }
        };
        window.tinyMCE && window.tinyMCE.init(tinyMCEInitObj);

        window.sigcount = ${numSigs};
        window.myEdit = [];

        for(var i = 0 ;i < sigcount ; i++) {
            var sigType = document.getElementById("signatureType"+i).value;
            if(sigType == 'text/html') {
                window.tinyMCE && window.tinyMCE.execCommand('mceAddControl', false, "signatureValue"+i);
            }
            else if(sigType == 'text/plain') {
                myEdit[i] == null;
            }
        }
        window.tinyMCE && window.tinyMCE.execCommand('mceAddControl', false, "newSignatureValue");
    }());

    /* List of elements that has to be handled for send */
    var sendElemts = new Array("SOPSEND","IOPSEND");
    var y;
    for (y in sendElemts){
        var _elemA = document.getElementById(sendElemts[y]);
        _elemA.onclick = function () {
            return prepToSend();
        }
    }

    function prepToSend (){
       for(var j = 0 ;j < sigcount ; j++) {
          if(myEdit[j] != null) {
            myEdit[j].saveHTML();
          }
       }
       myEditor.saveHTML();
       return true;
    }

</script>
</body>


