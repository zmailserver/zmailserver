/*
 * ***** BEGIN LICENSE BLOCK *****
 * 
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012 VMware, Inc.
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
var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":86,"id":5450,"methods":[{"el":54,"sc":5,"sl":48},{"el":60,"sc":5,"sl":56},{"el":71,"sc":5,"sl":62},{"el":76,"sc":5,"sl":73},{"el":85,"sc":5,"sl":78}],"name":"LimitationsTest","sl":31},{"el":37,"id":5450,"methods":[{"el":36,"sc":9,"sl":34}],"name":"LimitationsTest.MyClass","sl":33},{"el":42,"id":5452,"methods":[{"el":41,"sc":9,"sl":40}],"name":"LimitationsTest.PrivateClass","sl":39},{"el":46,"id":5453,"methods":[],"name":"LimitationsTest.NativeClass","sl":44}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1037":{"methods":[{"sl":73}],"name":"privateConstructor","pass":true,"statements":[{"sl":75}]},"test_167":{"methods":[{"sl":34},{"sl":62}],"name":"mockFinalMethod","pass":true,"statements":[{"sl":35},{"sl":64},{"sl":66},{"sl":67}]},"test_205":{"methods":[{"sl":78}],"name":"mockNativeMethod","pass":true,"statements":[{"sl":80},{"sl":81},{"sl":82},{"sl":83},{"sl":84}]},"test_328":{"methods":[{"sl":73}],"name":"privateConstructor","pass":true,"statements":[{"sl":75}]},"test_452":{"methods":[{"sl":34},{"sl":62}],"name":"mockFinalMethod","pass":true,"statements":[{"sl":35},{"sl":64},{"sl":66},{"sl":67}]},"test_499":{"methods":[{"sl":56}],"name":"abstractClass","pass":true,"statements":[{"sl":58},{"sl":59}]},"test_629":{"methods":[{"sl":78}],"name":"mockNativeMethod","pass":true,"statements":[{"sl":80},{"sl":81},{"sl":82},{"sl":83},{"sl":84}]},"test_964":{"methods":[{"sl":56}],"name":"abstractClass","pass":true,"statements":[{"sl":58},{"sl":59}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [167, 452], [167, 452], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [499, 964], [], [499, 964], [499, 964], [], [], [167, 452], [], [167, 452], [], [167, 452], [167, 452], [], [], [], [], [], [328, 1037], [], [328, 1037], [], [], [629, 205], [], [629, 205], [629, 205], [629, 205], [629, 205], [629, 205], [], []]
