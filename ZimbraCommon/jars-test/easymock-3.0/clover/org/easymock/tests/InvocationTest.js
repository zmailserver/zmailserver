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
clover.pageData = {"classes":[{"el":97,"id":5420,"methods":[{"el":48,"sc":5,"sl":38},{"el":56,"sc":5,"sl":50},{"el":66,"sc":5,"sl":58},{"el":96,"sc":5,"sl":68}],"name":"InvocationTest","sl":30},{"el":85,"id":5440,"methods":[{"el":76,"sc":13,"sl":74},{"el":81,"sc":13,"sl":78},{"el":84,"sc":13,"sl":83}],"name":"InvocationTest.ToString","sl":71}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1032":{"methods":[{"sl":50}],"name":"testEquals","pass":true,"statements":[{"sl":52},{"sl":53},{"sl":54},{"sl":55}]},"test_277":{"methods":[{"sl":58}],"name":"testHashCode","pass":true,"statements":[{"sl":60},{"sl":61},{"sl":64}]},"test_480":{"methods":[{"sl":68},{"sl":74},{"sl":78}],"name":"testShouldDisplayMocksToStringIfValidJavaIdentifier","pass":true,"statements":[{"sl":75},{"sl":80},{"sl":87},{"sl":88},{"sl":90},{"sl":92},{"sl":94}]},"test_500":{"methods":[{"sl":58}],"name":"testHashCode","pass":true,"statements":[{"sl":60},{"sl":61},{"sl":64}]},"test_522":{"methods":[{"sl":68},{"sl":74},{"sl":78}],"name":"testShouldDisplayMocksToStringIfValidJavaIdentifier","pass":true,"statements":[{"sl":75},{"sl":80},{"sl":87},{"sl":88},{"sl":90},{"sl":92},{"sl":94}]},"test_566":{"methods":[{"sl":50}],"name":"testEquals","pass":true,"statements":[{"sl":52},{"sl":53},{"sl":54},{"sl":55}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [566, 1032], [], [566, 1032], [566, 1032], [566, 1032], [566, 1032], [], [], [500, 277], [], [500, 277], [500, 277], [], [], [500, 277], [], [], [], [480, 522], [], [], [], [], [], [480, 522], [480, 522], [], [], [480, 522], [], [480, 522], [], [], [], [], [], [], [480, 522], [480, 522], [], [480, 522], [], [480, 522], [], [480, 522], [], [], []]
