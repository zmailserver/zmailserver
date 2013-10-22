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
function objectCreate(iters, resultsEl) {
	var before = new Date().getTime();
	for (var i = 0; i < iters; i++) {
		var object = new Object;
	}
	var after = new Date().getTime();

	resultsEl.innerHTML = after - before;
}

function objectReadProperty(iters, resultsEl, depth) {
	var object = objectCreateProxy(depth);

	var before = new Date().getTime();
	for (var i = 0; i < iters; i++) {
		var value = object.prop;
	}
	var after = new Date().getTime();

	resultsEl.innerHTML = after - before;
}

function objectCreateProxy(depth) {
	var object = { prop: depth };
	for (var i = 0; i < depth; i++) {
		var proxyCtor = new Function;
		proxyCtor.prototype = object;
		object = new proxyCtor;
	}
	return object;
}

function objectWriteProperty(iters, resultsEl) {
	var object = new Object;
	
	var before = new Date().getTime();
	for (var i = 0; i < iters; i++) {
		object.prop = i;
	}
	var after = new Date().getTime();
	
	resultsEl.innerHTML = after - before;
}

function objectDeleteProperty(iters, resultsEl) {
	var object = new Object;
	for (var i = 0; i < iters; i++) {
		object[i] = i;
	}
	
	var before = new Date().getTime();
	for (var i = 0; i < iters; i++) {
		delete object[i];
	}
	var after = new Date().getTime();
	
	resultsEl.innerHTML = after - before;
}

function objectEnumerateProperties(iters, resultsEl) {
	var object = new Object;
	for (var i = 0; i < iters; i++) {
		object[i] = i;
	}
	
	var before = new Date().getTime();
	for (var prop in object) {
		// do nothing
	}
	var after = new Date().getTime();
	
	resultsEl.innerHTML = after - before;
}

function SomeClass() { }

SomeClass.doSomething = function(x) {
	return x * 2;
};

function objectCallStaticMethod(iters, resultsEl) {
	var before = new Date().getTime();
	for (var i = 0; i < iters; i++) {
		SomeClass.doSomething(i);
	}
	var after = new Date().getTime();
	resultsEl.innerHTML = after - before;
};

function objectCallVarMethod(iters, resultsEl) {
	var before = new Date().getTime();
	var doSomething = SomeClass.doSomething;
	for (var i = 0; i < iters; i++) {
		doSomething(i);
	}
	var after = new Date().getTime();
	resultsEl.innerHTML = after - before;
};

var OBJECT_TESTS = {
	name: "Object Tests", tests: [
		{ name: "new Object", iters: 100000, func: objectCreate },
		{ name: "write property", iters: 100000, func: objectWriteProperty },
		{ name: "read property (depth = 0)", iters: 100000, func: objectReadProperty, args: [0] },
		{ name: "read property (depth = 5)", iters: 100000, func: objectReadProperty, args: [5] },
		{ name: "read property (depth = 10)", iters: 100000, func: objectReadProperty, args: [10] },
		{ name: "enumerate properties", iters: 100000, func: objectEnumerateProperties },
		{ name: "delete property", iters: 100000, func: objectDeleteProperty },
		{ name: "call static method", iters: 100000, func: objectCallStaticMethod },
		{ name: "call var method", iters: 100000, func: objectCallVarMethod }
	]
};