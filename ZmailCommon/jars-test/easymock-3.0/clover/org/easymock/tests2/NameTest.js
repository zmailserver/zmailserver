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
clover.pageData = {"classes":[{"el":97,"id":4239,"methods":[{"el":42,"sc":5,"sl":30},{"el":56,"sc":5,"sl":44},{"el":70,"sc":5,"sl":58},{"el":85,"sc":5,"sl":72},{"el":95,"sc":5,"sl":87}],"name":"NameTest","sl":29}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_234":{"methods":[{"sl":72}],"name":"nameForMocksControl","pass":true,"statements":[{"sl":74},{"sl":75},{"sl":76},{"sl":77},{"sl":78},{"sl":79},{"sl":81},{"sl":82},{"sl":83}]},"test_32":{"methods":[{"sl":30}],"name":"nameForMock","pass":true,"statements":[{"sl":32},{"sl":33},{"sl":34},{"sl":35},{"sl":36},{"sl":38},{"sl":39},{"sl":40}]},"test_490":{"methods":[{"sl":72}],"name":"nameForMocksControl","pass":true,"statements":[{"sl":74},{"sl":75},{"sl":76},{"sl":77},{"sl":78},{"sl":79},{"sl":81},{"sl":82},{"sl":83}]},"test_526":{"methods":[{"sl":30}],"name":"nameForMock","pass":true,"statements":[{"sl":32},{"sl":33},{"sl":34},{"sl":35},{"sl":36},{"sl":38},{"sl":39},{"sl":40}]},"test_547":{"methods":[{"sl":87}],"name":"shouldThrowIllegalArgumentExceptionIfNameIsNoValidJavaIdentifier","pass":true,"statements":[{"sl":89},{"sl":90},{"sl":93}]},"test_834":{"methods":[{"sl":44}],"name":"nameForStrictMock","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":52},{"sl":53},{"sl":54}]},"test_896":{"methods":[{"sl":58}],"name":"nameForNiceMock","pass":true,"statements":[{"sl":60},{"sl":61},{"sl":62},{"sl":63},{"sl":64},{"sl":66},{"sl":67},{"sl":68}]},"test_934":{"methods":[{"sl":58}],"name":"nameForNiceMock","pass":true,"statements":[{"sl":60},{"sl":61},{"sl":62},{"sl":63},{"sl":64},{"sl":66},{"sl":67},{"sl":68}]},"test_949":{"methods":[{"sl":44}],"name":"nameForStrictMock","pass":true,"statements":[{"sl":46},{"sl":47},{"sl":48},{"sl":49},{"sl":50},{"sl":52},{"sl":53},{"sl":54}]},"test_973":{"methods":[{"sl":87}],"name":"shouldThrowIllegalArgumentExceptionIfNameIsNoValidJavaIdentifier","pass":true,"statements":[{"sl":89},{"sl":90},{"sl":93}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [32, 526], [], [32, 526], [32, 526], [32, 526], [32, 526], [32, 526], [], [32, 526], [32, 526], [32, 526], [], [], [], [834, 949], [], [834, 949], [834, 949], [834, 949], [834, 949], [834, 949], [], [834, 949], [834, 949], [834, 949], [], [], [], [934, 896], [], [934, 896], [934, 896], [934, 896], [934, 896], [934, 896], [], [934, 896], [934, 896], [934, 896], [], [], [], [234, 490], [], [234, 490], [234, 490], [234, 490], [234, 490], [234, 490], [234, 490], [], [234, 490], [234, 490], [234, 490], [], [], [], [973, 547], [], [973, 547], [973, 547], [], [], [973, 547], [], [], [], []]
