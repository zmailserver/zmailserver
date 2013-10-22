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
clover.pageData = {"classes":[{"el":139,"id":3327,"methods":[{"el":41,"sc":13,"sl":39},{"el":55,"sc":5,"sl":33},{"el":65,"sc":13,"sl":63},{"el":79,"sc":5,"sl":57},{"el":87,"sc":13,"sl":85},{"el":101,"sc":5,"sl":81},{"el":116,"sc":5,"sl":103},{"el":127,"sc":5,"sl":118},{"el":138,"sc":5,"sl":129}],"name":"DelegateToTest","sl":27},{"el":31,"id":3327,"methods":[],"name":"DelegateToTest.IMyInterface","sl":29}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1022":{"methods":[{"sl":81},{"sl":85}],"name":"testReturnException","pass":true,"statements":[{"sl":83},{"sl":84},{"sl":86},{"sl":89},{"sl":91},{"sl":93},{"sl":94},{"sl":97},{"sl":100}]},"test_13":{"methods":[{"sl":103}],"name":"testWrongClass","pass":true,"statements":[{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112}]},"test_244":{"methods":[{"sl":57},{"sl":63}],"name":"testStubDelegate","pass":true,"statements":[{"sl":59},{"sl":60},{"sl":64},{"sl":67},{"sl":68},{"sl":70},{"sl":72},{"sl":73},{"sl":74},{"sl":75},{"sl":76},{"sl":78}]},"test_346":{"methods":[{"sl":118}],"name":"nullDelegationNotAllowed","pass":true,"statements":[{"sl":120},{"sl":121},{"sl":122},{"sl":125}]},"test_407":{"methods":[{"sl":118}],"name":"nullDelegationNotAllowed","pass":true,"statements":[{"sl":120},{"sl":121},{"sl":122},{"sl":125}]},"test_412":{"methods":[{"sl":129}],"name":"nullStubDelegationNotAllowed","pass":true,"statements":[{"sl":131},{"sl":132},{"sl":133},{"sl":136}]},"test_5":{"methods":[{"sl":129}],"name":"nullStubDelegationNotAllowed","pass":true,"statements":[{"sl":131},{"sl":132},{"sl":133},{"sl":136}]},"test_537":{"methods":[{"sl":103}],"name":"testWrongClass","pass":true,"statements":[{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112}]},"test_646":{"methods":[{"sl":33},{"sl":39}],"name":"testDelegate","pass":true,"statements":[{"sl":35},{"sl":36},{"sl":40},{"sl":44},{"sl":45},{"sl":47},{"sl":49},{"sl":50},{"sl":51},{"sl":52},{"sl":54}]},"test_812":{"methods":[{"sl":81},{"sl":85}],"name":"testReturnException","pass":true,"statements":[{"sl":83},{"sl":84},{"sl":86},{"sl":89},{"sl":91},{"sl":93},{"sl":94},{"sl":97},{"sl":100}]},"test_892":{"methods":[{"sl":33},{"sl":39}],"name":"testDelegate","pass":true,"statements":[{"sl":35},{"sl":36},{"sl":40},{"sl":44},{"sl":45},{"sl":47},{"sl":49},{"sl":50},{"sl":51},{"sl":52},{"sl":54}]},"test_911":{"methods":[{"sl":57},{"sl":63}],"name":"testStubDelegate","pass":true,"statements":[{"sl":59},{"sl":60},{"sl":64},{"sl":67},{"sl":68},{"sl":70},{"sl":72},{"sl":73},{"sl":74},{"sl":75},{"sl":76},{"sl":78}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [892, 646], [], [892, 646], [892, 646], [], [], [892, 646], [892, 646], [], [], [], [892, 646], [892, 646], [], [892, 646], [], [892, 646], [892, 646], [892, 646], [892, 646], [], [892, 646], [], [], [911, 244], [], [911, 244], [911, 244], [], [], [911, 244], [911, 244], [], [], [911, 244], [911, 244], [], [911, 244], [], [911, 244], [911, 244], [911, 244], [911, 244], [911, 244], [], [911, 244], [], [], [812, 1022], [], [812, 1022], [812, 1022], [812, 1022], [812, 1022], [], [], [812, 1022], [], [812, 1022], [], [812, 1022], [812, 1022], [], [], [812, 1022], [], [], [812, 1022], [], [], [537, 13], [], [537, 13], [537, 13], [537, 13], [537, 13], [537, 13], [], [], [537, 13], [], [], [], [], [], [407, 346], [], [407, 346], [407, 346], [407, 346], [], [], [407, 346], [], [], [], [412, 5], [], [412, 5], [412, 5], [412, 5], [], [], [412, 5], [], [], []]
