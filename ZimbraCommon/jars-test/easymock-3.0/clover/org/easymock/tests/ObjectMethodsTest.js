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
clover.pageData = {"classes":[{"el":91,"id":5621,"methods":[{"el":41,"sc":5,"sl":38},{"el":47,"sc":5,"sl":43},{"el":54,"sc":5,"sl":49},{"el":62,"sc":5,"sl":56},{"el":67,"sc":5,"sl":64},{"el":73,"sc":5,"sl":69},{"el":89,"sc":5,"sl":83}],"name":"ObjectMethodsTest","sl":31},{"el":36,"id":5621,"methods":[],"name":"ObjectMethodsTest.EmptyInterface","sl":35},{"el":76,"id":5640,"methods":[],"name":"ObjectMethodsTest.MockedClass","sl":75},{"el":79,"id":5640,"methods":[],"name":"ObjectMethodsTest.DummyProxy","sl":78}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_176":{"methods":[{"sl":56}],"name":"testHashCode","pass":true,"statements":[{"sl":58},{"sl":59},{"sl":60},{"sl":61}]},"test_257":{"methods":[{"sl":43}],"name":"equalsBeforeActivation","pass":true,"statements":[{"sl":45},{"sl":46}]},"test_293":{"methods":[{"sl":43}],"name":"equalsBeforeActivation","pass":true,"statements":[{"sl":45},{"sl":46}]},"test_354":{"methods":[{"sl":64}],"name":"toStringBeforeActivation","pass":true,"statements":[{"sl":66}]},"test_383":{"methods":[{"sl":83}],"name":"toStringForClasses","pass":true,"statements":[{"sl":85},{"sl":86},{"sl":87}]},"test_391":{"methods":[{"sl":69}],"name":"toStringAfterActivation","pass":true,"statements":[{"sl":71},{"sl":72}]},"test_443":{"methods":[{"sl":49}],"name":"equalsAfterActivation","pass":true,"statements":[{"sl":51},{"sl":52},{"sl":53}]},"test_634":{"methods":[{"sl":49}],"name":"equalsAfterActivation","pass":true,"statements":[{"sl":51},{"sl":52},{"sl":53}]},"test_71":{"methods":[{"sl":56}],"name":"testHashCode","pass":true,"statements":[{"sl":58},{"sl":59},{"sl":60},{"sl":61}]},"test_778":{"methods":[{"sl":64}],"name":"toStringBeforeActivation","pass":true,"statements":[{"sl":66}]},"test_963":{"methods":[{"sl":83}],"name":"toStringForClasses","pass":true,"statements":[{"sl":85},{"sl":86},{"sl":87}]},"test_996":{"methods":[{"sl":69}],"name":"toStringAfterActivation","pass":true,"statements":[{"sl":71},{"sl":72}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [293, 257], [], [293, 257], [293, 257], [], [], [443, 634], [], [443, 634], [443, 634], [443, 634], [], [], [176, 71], [], [176, 71], [176, 71], [176, 71], [176, 71], [], [], [778, 354], [], [778, 354], [], [], [996, 391], [], [996, 391], [996, 391], [], [], [], [], [], [], [], [], [], [], [963, 383], [], [963, 383], [963, 383], [963, 383], [], [], [], []]
