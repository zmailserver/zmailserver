/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2008, 2009, 2010, 2012 VMware, Inc.
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
/*
Copyright (c) 2008, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 2.5.1
*/
(function(){var G=YAHOO.util.Dom,L=YAHOO.util.Event,I=YAHOO.lang,B=YAHOO.widget.Overlay,J=YAHOO.widget.Menu,D={},K=null,E=null,C=null;function F(N,M,Q,O){var R,P;if(I.isString(N)&&I.isString(M)){if(YAHOO.env.ua.ie){P="<input type=\""+N+"\" name=\""+M+"\"";if(O){P+=" checked";}P+=">";R=document.createElement(P);}else{R=document.createElement("input");R.name=M;R.type=N;if(O){R.checked=true;}}R.value=Q;return R;}}function H(N,T){var M=N.nodeName.toUpperCase(),R=this,S,O,P;function U(V){if(!(V in T)){S=N.getAttributeNode(V);if(S&&("value" in S)){T[V]=S.value;}}}function Q(){U("type");if(T.type=="button"){T.type="push";}if(!("disabled" in T)){T.disabled=N.disabled;}U("name");U("value");U("title");}switch(M){case"A":T.type="link";U("href");U("target");break;case"INPUT":Q();if(!("checked" in T)){T.checked=N.checked;}break;case"BUTTON":Q();O=N.parentNode.parentNode;if(G.hasClass(O,this.CSS_CLASS_NAME+"-checked")){T.checked=true;}if(G.hasClass(O,this.CSS_CLASS_NAME+"-disabled")){T.disabled=true;}N.removeAttribute("value");N.setAttribute("type","button");break;}N.removeAttribute("id");N.removeAttribute("name");if(!("tabindex" in T)){T.tabindex=N.tabIndex;}if(!("label" in T)){P=M=="INPUT"?N.value:N.innerHTML;if(P&&P.length>0){T.label=P;}}}function A(O){var N=O.attributes,M=N.srcelement,Q=M.nodeName.toUpperCase(),P=this;if(Q==this.NODE_NAME){O.element=M;O.id=M.id;G.getElementsBy(function(R){switch(R.nodeName.toUpperCase()){case"BUTTON":case"A":case"INPUT":H.call(P,R,N);break;}},"*",M);}else{switch(Q){case"BUTTON":case"A":case"INPUT":H.call(this,M,N);break;}}}YAHOO.widget.Button=function(Q,N){if(!B&&YAHOO.widget.Overlay){B=YAHOO.widget.Overlay;}if(!J&&YAHOO.widget.Menu){J=YAHOO.widget.Menu;}var P=YAHOO.widget.Button.superclass.constructor,O,M;if(arguments.length==1&&!I.isString(Q)&&!Q.nodeName){if(!Q.id){Q.id=G.generateId();}P.call(this,(this.createButtonElement(Q.type)),Q);}else{O={element:null,attributes:(N||{})};if(I.isString(Q)){M=G.get(Q);if(M){if(!O.attributes.id){O.attributes.id=Q;}O.attributes.srcelement=M;A.call(this,O);if(!O.element){O.element=this.createButtonElement(O.attributes.type);}P.call(this,O.element,O.attributes);}}else{if(Q.nodeName){if(!O.attributes.id){if(Q.id){O.attributes.id=Q.id;}else{O.attributes.id=G.generateId();}}O.attributes.srcelement=Q;A.call(this,O);if(!O.element){O.element=this.createButtonElement(O.attributes.type);}P.call(this,O.element,O.attributes);}}}};YAHOO.extend(YAHOO.widget.Button,YAHOO.util.Element,{_button:null,_menu:null,_hiddenFields:null,_onclickAttributeValue:null,_activationKeyPressed:false,_activationButtonPressed:false,_hasKeyEventHandlers:false,_hasMouseEventHandlers:false,NODE_NAME:"SPAN",CHECK_ACTIVATION_KEYS:[32],ACTIVATION_KEYS:[13,32],OPTION_AREA_WIDTH:20,CSS_CLASS_NAME:"yui-button",RADIO_DEFAULT_TITLE:"Unchecked.  Click to check.",RADIO_CHECKED_TITLE:"Checked.  Click another button to uncheck",CHECKBOX_DEFAULT_TITLE:"Unchecked.  Click to check.",CHECKBOX_CHECKED_TITLE:"Checked.  Click to uncheck.",MENUBUTTON_DEFAULT_TITLE:"Menu collapsed.  Click to expand.",MENUBUTTON_MENU_VISIBLE_TITLE:"Menu expanded.  Click or press Esc to collapse.",SPLITBUTTON_DEFAULT_TITLE:("Menu collapsed.  Click inside option region or press Ctrl + Shift + M to show the menu."),SPLITBUTTON_OPTION_VISIBLE_TITLE:"Menu expanded.  Press Esc or Ctrl + Shift + M to hide the menu.",SUBMIT_TITLE:"Click to submit form.",_setType:function(M){if(M=="split"){this.on("option",this._onOption);}},_setLabel:function(M){this._button.innerHTML=M;var O,N;if(YAHOO.env.ua.gecko&&G.inDocument(this.get("element"))){N=this;O=this.CSS_CLASS_NAME;this.removeClass(O);window.setTimeout(function(){N.addClass(O);},0);}},_setTabIndex:function(M){this._button.tabIndex=M;},_setTitle:function(N){var M=N;if(this.get("type")!="link"){if(!M){switch(this.get("type")){case"radio":M=this.RADIO_DEFAULT_TITLE;break;case"checkbox":M=this.CHECKBOX_DEFAULT_TITLE;break;case"menu":M=this.MENUBUTTON_DEFAULT_TITLE;break;case"split":M=this.SPLITBUTTON_DEFAULT_TITLE;break;case"submit":M=this.SUBMIT_TITLE;break;}}this._button.title=M;}},_setDisabled:function(M){if(this.get("type")!="link"){if(M){if(this._menu){this._menu.hide();}if(this.hasFocus()){this.blur();}this._button.setAttribute("disabled","disabled");this.addStateCSSClasses("disabled");this.removeStateCSSClasses("hover");this.removeStateCSSClasses("active");this.removeStateCSSClasses("focus");}else{this._button.removeAttribute("disabled");this.removeStateCSSClasses("disabled");}}},_setHref:function(M){if(this.get("type")=="link"){this._button.href=M;}},_setTarget:function(M){if(this.get("type")=="link"){this._button.setAttribute("target",M);}},_setChecked:function(N){var O=this.get("type"),M;if(O=="checkbox"||O=="radio"){if(N){this.addStateCSSClasses("checked");M=(O=="radio")?this.RADIO_CHECKED_TITLE:this.CHECKBOX_CHECKED_TITLE;}else{this.removeStateCSSClasses("checked");M=(O=="radio")?this.RADIO_DEFAULT_TITLE:this.CHECKBOX_DEFAULT_TITLE;}this.set("title",M);}},_setMenu:function(W){var Q=this.get("lazyloadmenu"),T=this.get("element"),M,Y=false,Z,P,S,O,N,V,R;if(!B){return false;}if(J){M=J.prototype.CSS_CLASS_NAME;}function X(){Z.render(T.parentNode);this.removeListener("appendTo",X);}function U(){if(Z){G.addClass(Z.element,this.get("menuclassname"));G.addClass(Z.element,"yui-"+this.get("type")+"-button-menu");Z.showEvent.subscribe(this._onMenuShow,null,this);Z.hideEvent.subscribe(this._onMenuHide,null,this);Z.renderEvent.subscribe(this._onMenuRender,null,this);if(J&&Z instanceof J){Z.keyDownEvent.subscribe(this._onMenuKeyDown,this,true);Z.subscribe("click",this._onMenuClick,this,true);Z.itemAddedEvent.subscribe(this._onMenuItemAdded,this,true);S=Z.srcElement;if(S&&S.nodeName.toUpperCase()=="SELECT"){S.style.display="none";S.parentNode.removeChild(S);}}else{if(B&&Z instanceof B){if(!K){K=new YAHOO.widget.OverlayManager();}K.register(Z);}}this._menu=Z;if(!Y){if(Q&&J&&!(Z instanceof J)){Z.beforeShowEvent.subscribe(this._onOverlayBeforeShow,null,this);}else{if(!Q){if(G.inDocument(T)){Z.render(T.parentNode);
}else{this.on("appendTo",X);}}}}}}if(W&&J&&(W instanceof J)){Z=W;O=Z.getItems();N=O.length;Y=true;if(N>0){R=N-1;do{V=O[R];if(V){V.cfg.subscribeToConfigEvent("selected",this._onMenuItemSelected,V,this);}}while(R--);}U.call(this);}else{if(B&&W&&(W instanceof B)){Z=W;Y=true;Z.cfg.setProperty("visible",false);Z.cfg.setProperty("context",[T,"tl","bl"]);U.call(this);}else{if(J&&I.isArray(W)){this.on("appendTo",function(){Z=new J(G.generateId(),{lazyload:Q,itemdata:W});U.call(this);});}else{if(I.isString(W)){P=G.get(W);if(P){if(J&&G.hasClass(P,M)||P.nodeName.toUpperCase()=="SELECT"){Z=new J(W,{lazyload:Q});U.call(this);}else{if(B){Z=new B(W,{visible:false,context:[T,"tl","bl"]});U.call(this);}}}}else{if(W&&W.nodeName){if(J&&G.hasClass(W,M)||W.nodeName.toUpperCase()=="SELECT"){Z=new J(W,{lazyload:Q});U.call(this);}else{if(B){if(!W.id){G.generateId(W);}Z=new B(W,{visible:false,context:[T,"tl","bl"]});U.call(this);}}}}}}}},_setOnClick:function(M){if(this._onclickAttributeValue&&(this._onclickAttributeValue!=M)){this.removeListener("click",this._onclickAttributeValue.fn);this._onclickAttributeValue=null;}if(!this._onclickAttributeValue&&I.isObject(M)&&I.isFunction(M.fn)){this.on("click",M.fn,M.obj,M.scope);this._onclickAttributeValue=M;}},_setSelectedMenuItem:function(N){var M=this._menu,O;if(J&&M&&M instanceof J){O=M.getItem(N);if(O&&!O.cfg.getProperty("selected")){O.cfg.setProperty("selected",true);}}},_isActivationKey:function(M){var Q=this.get("type"),N=(Q=="checkbox"||Q=="radio")?this.CHECK_ACTIVATION_KEYS:this.ACTIVATION_KEYS,P=N.length,O;if(P>0){O=P-1;do{if(M==N[O]){return true;}}while(O--);}},_isSplitButtonOptionKey:function(M){return(M.ctrlKey&&M.shiftKey&&L.getCharCode(M)==77);},_addListenersToForm:function(){var S=this.getForm(),R=YAHOO.widget.Button.onFormKeyPress,Q,M,P,O,N;if(S){L.on(S,"reset",this._onFormReset,null,this);L.on(S,"submit",this.createHiddenFields,null,this);M=this.get("srcelement");if(this.get("type")=="submit"||(M&&M.type=="submit")){P=L.getListeners(S,"keypress");Q=false;if(P){O=P.length;if(O>0){N=O-1;do{if(P[N].fn==R){Q=true;break;}}while(N--);}}if(!Q){L.on(S,"keypress",R);}}}},_showMenu:function(R){if(YAHOO.widget.MenuManager){YAHOO.widget.MenuManager.hideVisible();}if(K){K.hideAll();}var P=B.VIEWPORT_OFFSET,Y=this._menu,W=this,Z=W.get("element"),T=false,V=G.getY(Z),U=G.getDocumentScrollTop(),M,Q,b;if(U){V=V-U;}var O=V,N=(G.getViewportHeight()-(V+Z.offsetHeight));function S(){if(T){return(O-P);}else{return(N-P);}}function a(){var c=S();if(Q>c){M=Y.cfg.getProperty("minscrollheight");if(c>M){Y.cfg.setProperty("maxheight",c);if(T){Y.align("bl","tl");}}if(c<M){if(T){Y.cfg.setProperty("context",[Z,"tl","bl"],true);Y.align("tl","bl");}else{Y.cfg.setProperty("context",[Z,"bl","tl"],true);Y.align("bl","tl");T=true;return a();}}}}if(J&&Y&&(Y instanceof J)){Y.cfg.applyConfig({context:[Z,"tl","bl"],clicktohide:false,visible:true});Y.cfg.fireQueue();Y.cfg.setProperty("maxheight",0);Y.align("tl","bl");if(R.type=="mousedown"){L.stopPropagation(R);}Q=Y.element.offsetHeight;b=Y.element.lastChild;a();if(this.get("focusmenu")){this._menu.focus();}}else{if(B&&Y&&(Y instanceof B)){Y.show();Y.align("tl","bl");var X=S();Q=Y.element.offsetHeight;if(X<Q){Y.align("bl","tl");T=true;X=S();if(X<Q){Y.align("tl","bl");}}}}},_hideMenu:function(){var M=this._menu;if(M){M.hide();}},_onMouseOver:function(M){if(!this._hasMouseEventHandlers){this.on("mouseout",this._onMouseOut);this.on("mousedown",this._onMouseDown);this.on("mouseup",this._onMouseUp);this._hasMouseEventHandlers=true;}this.addStateCSSClasses("hover");if(this._activationButtonPressed){this.addStateCSSClasses("active");}if(this._bOptionPressed){this.addStateCSSClasses("activeoption");}if(this._activationButtonPressed||this._bOptionPressed){L.removeListener(document,"mouseup",this._onDocumentMouseUp);}},_onMouseOut:function(M){this.removeStateCSSClasses("hover");if(this.get("type")!="menu"){this.removeStateCSSClasses("active");}if(this._activationButtonPressed||this._bOptionPressed){L.on(document,"mouseup",this._onDocumentMouseUp,null,this);}},_onDocumentMouseUp:function(O){this._activationButtonPressed=false;this._bOptionPressed=false;var P=this.get("type"),M,N;if(P=="menu"||P=="split"){M=L.getTarget(O);N=this._menu.element;if(M!=N&&!G.isAncestor(N,M)){this.removeStateCSSClasses((P=="menu"?"active":"activeoption"));this._hideMenu();}}L.removeListener(document,"mouseup",this._onDocumentMouseUp);},_onMouseDown:function(P){var R,N,Q,O;function M(){this._hideMenu();this.removeListener("mouseup",M);}if((P.which||P.button)==1){if(!this.hasFocus()){this.focus();}R=this.get("type");if(R=="split"){N=this.get("element");Q=L.getPageX(P)-G.getX(N);if((N.offsetWidth-this.OPTION_AREA_WIDTH)<Q){this.fireEvent("option",P);}else{this.addStateCSSClasses("active");this._activationButtonPressed=true;}}else{if(R=="menu"){if(this.isActive()){this._hideMenu();this._activationButtonPressed=false;}else{this._showMenu(P);this._activationButtonPressed=true;}}else{this.addStateCSSClasses("active");this._activationButtonPressed=true;}}if(R=="split"||R=="menu"){O=this;this._hideMenuTimerId=window.setTimeout(function(){O.on("mouseup",M);},250);}}},_onMouseUp:function(M){var N=this.get("type");if(this._hideMenuTimerId){window.clearTimeout(this._hideMenuTimerId);}if(N=="checkbox"||N=="radio"){this.set("checked",!(this.get("checked")));}this._activationButtonPressed=false;if(this.get("type")!="menu"){this.removeStateCSSClasses("active");}},_onFocus:function(N){var M;this.addStateCSSClasses("focus");if(this._activationKeyPressed){this.addStateCSSClasses("active");}C=this;if(!this._hasKeyEventHandlers){M=this._button;L.on(M,"blur",this._onBlur,null,this);L.on(M,"keydown",this._onKeyDown,null,this);L.on(M,"keyup",this._onKeyUp,null,this);this._hasKeyEventHandlers=true;}this.fireEvent("focus",N);},_onBlur:function(M){this.removeStateCSSClasses("focus");if(this.get("type")!="menu"){this.removeStateCSSClasses("active");}if(this._activationKeyPressed){L.on(document,"keyup",this._onDocumentKeyUp,null,this);
}C=null;this.fireEvent("blur",M);},_onDocumentKeyUp:function(M){if(this._isActivationKey(L.getCharCode(M))){this._activationKeyPressed=false;L.removeListener(document,"keyup",this._onDocumentKeyUp);}},_onKeyDown:function(N){var M=this._menu;if(this.get("type")=="split"&&this._isSplitButtonOptionKey(N)){this.fireEvent("option",N);}else{if(this._isActivationKey(L.getCharCode(N))){if(this.get("type")=="menu"){this._showMenu(N);}else{this._activationKeyPressed=true;this.addStateCSSClasses("active");}}}if(M&&M.cfg.getProperty("visible")&&L.getCharCode(N)==27){M.hide();this.focus();}},_onKeyUp:function(M){var N;if(this._isActivationKey(L.getCharCode(M))){N=this.get("type");if(N=="checkbox"||N=="radio"){this.set("checked",!(this.get("checked")));}this._activationKeyPressed=false;if(this.get("type")!="menu"){this.removeStateCSSClasses("active");}}},_onClick:function(P){var S=this.get("type"),M,Q,N,O,R;switch(S){case"radio":case"checkbox":if(this.get("checked")){M=(S=="radio")?this.RADIO_CHECKED_TITLE:this.CHECKBOX_CHECKED_TITLE;}else{M=(S=="radio")?this.RADIO_DEFAULT_TITLE:this.CHECKBOX_DEFAULT_TITLE;}this.set("title",M);break;case"submit":this.submitForm();break;case"reset":Q=this.getForm();if(Q){Q.reset();}break;case"menu":M=this._menu.cfg.getProperty("visible")?this.MENUBUTTON_MENU_VISIBLE_TITLE:this.MENUBUTTON_DEFAULT_TITLE;this.set("title",M);break;case"split":O=this.get("element");R=L.getPageX(P)-G.getX(O);if((O.offsetWidth-this.OPTION_AREA_WIDTH)<R){return false;}else{this._hideMenu();N=this.get("srcelement");if(N&&N.type=="submit"){this.submitForm();}}M=this._menu.cfg.getProperty("visible")?this.SPLITBUTTON_OPTION_VISIBLE_TITLE:this.SPLITBUTTON_DEFAULT_TITLE;this.set("title",M);break;}},_onAppendTo:function(N){var M=this;window.setTimeout(function(){M._addListenersToForm();},0);},_onFormReset:function(N){var O=this.get("type"),M=this._menu;if(O=="checkbox"||O=="radio"){this.resetValue("checked");}if(J&&M&&(M instanceof J)){this.resetValue("selectedMenuItem");}},_onDocumentMouseDown:function(P){var M=L.getTarget(P),O=this.get("element"),N=this._menu.element;if(M!=O&&!G.isAncestor(O,M)&&M!=N&&!G.isAncestor(N,M)){this._hideMenu();L.removeListener(document,"mousedown",this._onDocumentMouseDown);}},_onOption:function(M){if(this.hasClass("yui-split-button-activeoption")){this._hideMenu();this._bOptionPressed=false;}else{this._showMenu(M);this._bOptionPressed=true;}},_onOverlayBeforeShow:function(N){var M=this._menu;M.render(this.get("element").parentNode);M.beforeShowEvent.unsubscribe(this._onOverlayBeforeShow);},_onMenuShow:function(N){L.on(document,"mousedown",this._onDocumentMouseDown,null,this);var M,O;if(this.get("type")=="split"){M=this.SPLITBUTTON_OPTION_VISIBLE_TITLE;O="activeoption";}else{M=this.MENUBUTTON_MENU_VISIBLE_TITLE;O="active";}this.addStateCSSClasses(O);this.set("title",M);},_onMenuHide:function(O){var N=this._menu,M,P;if(this.get("type")=="split"){M=this.SPLITBUTTON_DEFAULT_TITLE;P="activeoption";}else{M=this.MENUBUTTON_DEFAULT_TITLE;P="active";}this.removeStateCSSClasses(P);this.set("title",M);if(this.get("type")=="split"){this._bOptionPressed=false;}},_onMenuKeyDown:function(O,N){var M=N[0];if(L.getCharCode(M)==27){this.focus();if(this.get("type")=="split"){this._bOptionPressed=false;}}},_onMenuRender:function(N){var P=this.get("element"),M=P.parentNode,O=this._menu.element;if(M!=O.parentNode){M.appendChild(O);}this.set("selectedMenuItem",this.get("selectedMenuItem"));},_onMenuItemSelected:function(O,N,M){var P=N[0];if(P){this.set("selectedMenuItem",M);}},_onMenuItemAdded:function(O,N,M){var P=N[0];P.cfg.subscribeToConfigEvent("selected",this._onMenuItemSelected,P,this);},_onMenuClick:function(N,M){var P=M[1],O;if(P){O=this.get("srcelement");if(O&&O.type=="submit"){this.submitForm();}this._hideMenu();}},createButtonElement:function(M){var O=this.NODE_NAME,N=document.createElement(O);N.innerHTML="<"+O+" class=\"first-child\">"+(M=="link"?"<a></a>":"<button type=\"button\"></button>")+"</"+O+">";return N;},addStateCSSClasses:function(M){var N=this.get("type");if(I.isString(M)){if(M!="activeoption"){this.addClass(this.CSS_CLASS_NAME+("-"+M));}this.addClass("yui-"+N+("-button-"+M));}},removeStateCSSClasses:function(M){var N=this.get("type");if(I.isString(M)){this.removeClass(this.CSS_CLASS_NAME+("-"+M));this.removeClass("yui-"+N+("-button-"+M));}},createHiddenFields:function(){this.removeHiddenFields();var R=this.getForm(),U,N,P,S,T,O,Q,M;if(R&&!this.get("disabled")){N=this.get("type");P=(N=="checkbox"||N=="radio");if(P||(E==this)){U=F((P?N:"hidden"),this.get("name"),this.get("value"),this.get("checked"));if(U){if(P){U.style.display="none";}R.appendChild(U);}}S=this._menu;if(J&&S&&(S instanceof J)){M=S.srcElement;T=this.get("selectedMenuItem");if(T){if(M&&M.nodeName.toUpperCase()=="SELECT"){R.appendChild(M);M.selectedIndex=T.index;}else{Q=(T.value===null||T.value==="")?T.cfg.getProperty("text"):T.value;O=this.get("name");if(Q&&O){M=F("hidden",(O+"_options"),Q);R.appendChild(M);}}}}if(U&&M){this._hiddenFields=[U,M];}else{if(!U&&M){this._hiddenFields=M;}else{if(U&&!M){this._hiddenFields=U;}}}return this._hiddenFields;}},removeHiddenFields:function(){var P=this._hiddenFields,N,O;function M(Q){if(G.inDocument(Q)){Q.parentNode.removeChild(Q);}}if(P){if(I.isArray(P)){N=P.length;if(N>0){O=N-1;do{M(P[O]);}while(O--);}}else{M(P);}this._hiddenFields=null;}},submitForm:function(){var P=this.getForm(),O=this.get("srcelement"),N=false,M;if(P){if(this.get("type")=="submit"||(O&&O.type=="submit")){E=this;}if(YAHOO.env.ua.ie){N=P.fireEvent("onsubmit");}else{M=document.createEvent("HTMLEvents");M.initEvent("submit",true,true);N=P.dispatchEvent(M);}if((YAHOO.env.ua.ie||YAHOO.env.ua.webkit)&&N){P.submit();}}return N;},init:function(M,T){var O=T.type=="link"?"a":"button",Q=T.srcelement,S=M.getElementsByTagName(O)[0],R;if(!S){R=M.getElementsByTagName("input")[0];if(R){S=document.createElement("button");S.setAttribute("type","button");R.parentNode.replaceChild(S,R);}}this._button=S;YAHOO.widget.Button.superclass.init.call(this,M,T);
D[this.get("id")]=this;this.addClass(this.CSS_CLASS_NAME);this.addClass("yui-"+this.get("type")+"-button");L.on(this._button,"focus",this._onFocus,null,this);this.on("mouseover",this._onMouseOver);this.on("click",this._onClick);this.on("appendTo",this._onAppendTo);var V=this.get("container"),N=this.get("element"),U=G.inDocument(N),P;if(V){if(Q&&Q!=N){P=Q.parentNode;if(P){P.removeChild(Q);}}if(I.isString(V)){L.onContentReady(V,function(){this.appendTo(V);},null,this);}else{this.appendTo(V);}}else{if(!U&&Q&&Q!=N){P=Q.parentNode;if(P){this.fireEvent("beforeAppendTo",{type:"beforeAppendTo",target:P});P.replaceChild(N,Q);this.fireEvent("appendTo",{type:"appendTo",target:P});}}else{if(this.get("type")!="link"&&U&&Q&&Q==N){this._addListenersToForm();}}}},initAttributes:function(N){var M=N||{};YAHOO.widget.Button.superclass.initAttributes.call(this,M);this.setAttributeConfig("type",{value:(M.type||"push"),validator:I.isString,writeOnce:true,method:this._setType});this.setAttributeConfig("label",{value:M.label,validator:I.isString,method:this._setLabel});this.setAttributeConfig("value",{value:M.value});this.setAttributeConfig("name",{value:M.name,validator:I.isString});this.setAttributeConfig("tabindex",{value:M.tabindex,validator:I.isNumber,method:this._setTabIndex});this.configureAttribute("title",{value:M.title,validator:I.isString,method:this._setTitle});this.setAttributeConfig("disabled",{value:(M.disabled||false),validator:I.isBoolean,method:this._setDisabled});this.setAttributeConfig("href",{value:M.href,validator:I.isString,method:this._setHref});this.setAttributeConfig("target",{value:M.target,validator:I.isString,method:this._setTarget});this.setAttributeConfig("checked",{value:(M.checked||false),validator:I.isBoolean,method:this._setChecked});this.setAttributeConfig("container",{value:M.container,writeOnce:true});this.setAttributeConfig("srcelement",{value:M.srcelement,writeOnce:true});this.setAttributeConfig("menu",{value:null,method:this._setMenu,writeOnce:true});this.setAttributeConfig("lazyloadmenu",{value:(M.lazyloadmenu===false?false:true),validator:I.isBoolean,writeOnce:true});this.setAttributeConfig("menuclassname",{value:(M.menuclassname||"yui-button-menu"),validator:I.isString,method:this._setMenuClassName,writeOnce:true});this.setAttributeConfig("selectedMenuItem",{value:null,method:this._setSelectedMenuItem});this.setAttributeConfig("onclick",{value:M.onclick,method:this._setOnClick});this.setAttributeConfig("focusmenu",{value:(M.focusmenu===false?false:true),validator:I.isBoolean});},focus:function(){if(!this.get("disabled")){this._button.focus();}},blur:function(){if(!this.get("disabled")){this._button.blur();}},hasFocus:function(){return(C==this);},isActive:function(){return this.hasClass(this.CSS_CLASS_NAME+"-active");},getMenu:function(){return this._menu;},getForm:function(){return this._button.form;},getHiddenFields:function(){return this._hiddenFields;},destroy:function(){var O=this.get("element"),N=O.parentNode,M=this._menu,Q;if(M){if(K&&K.find(M)){K.remove(M);}M.destroy();}L.purgeElement(O);L.purgeElement(this._button);L.removeListener(document,"mouseup",this._onDocumentMouseUp);L.removeListener(document,"keyup",this._onDocumentKeyUp);L.removeListener(document,"mousedown",this._onDocumentMouseDown);var P=this.getForm();if(P){L.removeListener(P,"reset",this._onFormReset);L.removeListener(P,"submit",this.createHiddenFields);}this.unsubscribeAll();if(N){N.removeChild(O);}delete D[this.get("id")];Q=G.getElementsByClassName(this.CSS_CLASS_NAME,this.NODE_NAME,P);if(I.isArray(Q)&&Q.length===0){L.removeListener(P,"keypress",YAHOO.widget.Button.onFormKeyPress);}},fireEvent:function(N,M){var O=arguments[0];if(this.DOM_EVENTS[O]&&this.get("disabled")){return ;}return YAHOO.widget.Button.superclass.fireEvent.apply(this,arguments);},toString:function(){return("Button "+this.get("id"));}});YAHOO.widget.Button.onFormKeyPress=function(Q){var O=L.getTarget(Q),R=L.getCharCode(Q),P=O.nodeName&&O.nodeName.toUpperCase(),M=O.type,S=false,U,V,N,W;function T(Z){var Y,X;switch(Z.nodeName.toUpperCase()){case"INPUT":case"BUTTON":if(Z.type=="submit"&&!Z.disabled){if(!S&&!N){N=Z;}if(V&&!W){W=Z;}}break;default:Y=Z.id;if(Y){U=D[Y];if(U){S=true;if(!U.get("disabled")){X=U.get("srcelement");if(!V&&(U.get("type")=="submit"||(X&&X.type=="submit"))){V=U;}}}}break;}}if(R==13&&((P=="INPUT"&&(M=="text"||M=="password"||M=="checkbox"||M=="radio"||M=="file"))||P=="SELECT")){G.getElementsBy(T,"*",this);if(N){N.focus();}else{if(!N&&V){if(W){L.preventDefault(Q);}V.submitForm();}}}};YAHOO.widget.Button.addHiddenFieldsToForm=function(M){var R=G.getElementsByClassName(YAHOO.widget.Button.prototype.CSS_CLASS_NAME,"*",M),P=R.length,Q,N,O;if(P>0){for(O=0;O<P;O++){N=R[O].id;if(N){Q=D[N];if(Q){Q.createHiddenFields();}}}}};YAHOO.widget.Button.getButton=function(M){var N=D[M];if(N){return N;}};})();(function(){var C=YAHOO.util.Dom,B=YAHOO.util.Event,D=YAHOO.lang,A=YAHOO.widget.Button,E={};YAHOO.widget.ButtonGroup=function(J,H){var I=YAHOO.widget.ButtonGroup.superclass.constructor,K,G,F;if(arguments.length==1&&!D.isString(J)&&!J.nodeName){if(!J.id){F=C.generateId();J.id=F;}I.call(this,(this._createGroupElement()),J);}else{if(D.isString(J)){G=C.get(J);if(G){if(G.nodeName.toUpperCase()==this.NODE_NAME){I.call(this,G,H);}}}else{K=J.nodeName.toUpperCase();if(K&&K==this.NODE_NAME){if(!J.id){J.id=C.generateId();}I.call(this,J,H);}}}};YAHOO.extend(YAHOO.widget.ButtonGroup,YAHOO.util.Element,{_buttons:null,NODE_NAME:"DIV",CSS_CLASS_NAME:"yui-buttongroup",_createGroupElement:function(){var F=document.createElement(this.NODE_NAME);return F;},_setDisabled:function(G){var H=this.getCount(),F;if(H>0){F=H-1;do{this._buttons[F].set("disabled",G);}while(F--);}},_onKeyDown:function(K){var G=B.getTarget(K),I=B.getCharCode(K),H=G.parentNode.parentNode.id,J=E[H],F=-1;if(I==37||I==38){F=(J.index===0)?(this._buttons.length-1):(J.index-1);}else{if(I==39||I==40){F=(J.index===(this._buttons.length-1))?0:(J.index+1);}}if(F>-1){this.check(F);this.getButton(F).focus();
}},_onAppendTo:function(H){var I=this._buttons,G=I.length,F;for(F=0;F<G;F++){I[F].appendTo(this.get("element"));}},_onButtonCheckedChange:function(G,F){var I=G.newValue,H=this.get("checkedButton");if(I&&H!=F){if(H){H.set("checked",false,true);}this.set("checkedButton",F);this.set("value",F.get("value"));}else{if(H&&!H.set("checked")){H.set("checked",true,true);}}},init:function(I,H){this._buttons=[];YAHOO.widget.ButtonGroup.superclass.init.call(this,I,H);this.addClass(this.CSS_CLASS_NAME);var J=this.getElementsByClassName("yui-radio-button");if(J.length>0){this.addButtons(J);}function F(K){return(K.type=="radio");}J=C.getElementsBy(F,"input",this.get("element"));if(J.length>0){this.addButtons(J);}this.on("keydown",this._onKeyDown);this.on("appendTo",this._onAppendTo);var G=this.get("container");if(G){if(D.isString(G)){B.onContentReady(G,function(){this.appendTo(G);},null,this);}else{this.appendTo(G);}}},initAttributes:function(G){var F=G||{};YAHOO.widget.ButtonGroup.superclass.initAttributes.call(this,F);this.setAttributeConfig("name",{value:F.name,validator:D.isString});this.setAttributeConfig("disabled",{value:(F.disabled||false),validator:D.isBoolean,method:this._setDisabled});this.setAttributeConfig("value",{value:F.value});this.setAttributeConfig("container",{value:F.container,writeOnce:true});this.setAttributeConfig("checkedButton",{value:null});},addButton:function(J){var L,K,G,F,H,I;if(J instanceof A&&J.get("type")=="radio"){L=J;}else{if(!D.isString(J)&&!J.nodeName){J.type="radio";L=new A(J);}else{L=new A(J,{type:"radio"});}}if(L){F=this._buttons.length;H=L.get("name");I=this.get("name");L.index=F;this._buttons[F]=L;E[L.get("id")]=L;if(H!=I){L.set("name",I);}if(this.get("disabled")){L.set("disabled",true);}if(L.get("checked")){this.set("checkedButton",L);}K=L.get("element");G=this.get("element");if(K.parentNode!=G){G.appendChild(K);}L.on("checkedChange",this._onButtonCheckedChange,L,this);return L;}},addButtons:function(G){var H,I,J,F;if(D.isArray(G)){H=G.length;J=[];if(H>0){for(F=0;F<H;F++){I=this.addButton(G[F]);if(I){J[J.length]=I;}}if(J.length>0){return J;}}}},removeButton:function(H){var I=this.getButton(H),G,F;if(I){this._buttons.splice(H,1);delete E[I.get("id")];I.removeListener("checkedChange",this._onButtonCheckedChange);I.destroy();G=this._buttons.length;if(G>0){F=this._buttons.length-1;do{this._buttons[F].index=F;}while(F--);}}},getButton:function(F){if(D.isNumber(F)){return this._buttons[F];}},getButtons:function(){return this._buttons;},getCount:function(){return this._buttons.length;},focus:function(H){var I,G,F;if(D.isNumber(H)){I=this._buttons[H];if(I){I.focus();}}else{G=this.getCount();for(F=0;F<G;F++){I=this._buttons[F];if(!I.get("disabled")){I.focus();break;}}}},check:function(F){var G=this.getButton(F);if(G){G.set("checked",true);}},destroy:function(){var I=this._buttons.length,H=this.get("element"),F=H.parentNode,G;if(I>0){G=this._buttons.length-1;do{this._buttons[G].destroy();}while(G--);}B.purgeElement(H);F.removeChild(H);},toString:function(){return("ButtonGroup "+this.get("id"));}});})();YAHOO.register("button",YAHOO.widget.Button,{version:"2.5.1",build:"984"});