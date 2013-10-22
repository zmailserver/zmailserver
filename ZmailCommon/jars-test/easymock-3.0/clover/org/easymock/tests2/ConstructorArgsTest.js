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
clover.pageData = {"classes":[{"el":92,"id":3302,"methods":[{"el":43,"sc":5,"sl":39},{"el":51,"sc":5,"sl":45},{"el":56,"sc":5,"sl":53},{"el":61,"sc":5,"sl":58},{"el":66,"sc":5,"sl":63},{"el":71,"sc":5,"sl":68},{"el":76,"sc":5,"sl":73},{"el":81,"sc":5,"sl":78},{"el":86,"sc":5,"sl":83},{"el":91,"sc":5,"sl":88}],"name":"ConstructorArgsTest","sl":27},{"el":37,"id":3302,"methods":[{"el":36,"sc":9,"sl":35}],"name":"ConstructorArgsTest.A","sl":31}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_162":{"methods":[{"sl":35},{"sl":83}],"name":"testConstructorArgs_TypeExistsButPrivate","pass":true,"statements":[{"sl":85}]},"test_190":{"methods":[{"sl":39},{"sl":45}],"name":"testConstructorArgs","pass":true,"statements":[{"sl":41},{"sl":42},{"sl":46},{"sl":47},{"sl":48},{"sl":50}]},"test_255":{"methods":[{"sl":88}],"name":"testConstructorArgs_TypeExistsButNotStatic","pass":true,"statements":[{"sl":90}]},"test_27":{"methods":[{"sl":78}],"name":"testConstructorArgs_WrongNumberOfArgs","pass":true,"statements":[{"sl":80}]},"test_337":{"methods":[{"sl":39},{"sl":45}],"name":"testConstructorArgs","pass":true,"statements":[{"sl":41},{"sl":42},{"sl":46},{"sl":47},{"sl":48},{"sl":50}]},"test_34":{"methods":[{"sl":88}],"name":"testConstructorArgs_TypeExistsButNotStatic","pass":true,"statements":[{"sl":90}]},"test_340":{"methods":[{"sl":68}],"name":"testConstructorArgs_NullObject","pass":true,"statements":[{"sl":70}]},"test_342":{"methods":[{"sl":73}],"name":"testConstructorArgs_WrongPrimitive","pass":true,"statements":[{"sl":75}]},"test_399":{"methods":[{"sl":53}],"name":"testConstructorArgs_WrongArgument","pass":true,"statements":[{"sl":55}]},"test_41":{"methods":[{"sl":73}],"name":"testConstructorArgs_WrongPrimitive","pass":true,"statements":[{"sl":75}]},"test_411":{"methods":[{"sl":63}],"name":"testConstructorArgs_PrimitiveForObject","pass":true,"statements":[{"sl":65}]},"test_447":{"methods":[{"sl":53}],"name":"testConstructorArgs_WrongArgument","pass":true,"statements":[{"sl":55}]},"test_686":{"methods":[{"sl":58}],"name":"testConstructorArgs_NullPrimitive","pass":true,"statements":[{"sl":60}]},"test_748":{"methods":[{"sl":35},{"sl":83}],"name":"testConstructorArgs_TypeExistsButPrivate","pass":true,"statements":[{"sl":85}]},"test_846":{"methods":[{"sl":78}],"name":"testConstructorArgs_WrongNumberOfArgs","pass":true,"statements":[{"sl":80}]},"test_859":{"methods":[{"sl":58}],"name":"testConstructorArgs_NullPrimitive","pass":true,"statements":[{"sl":60}]},"test_89":{"methods":[{"sl":68}],"name":"testConstructorArgs_NullObject","pass":true,"statements":[{"sl":70}]},"test_939":{"methods":[{"sl":63}],"name":"testConstructorArgs_PrimitiveForObject","pass":true,"statements":[{"sl":65}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [748, 162], [], [], [], [337, 190], [], [337, 190], [337, 190], [], [], [337, 190], [337, 190], [337, 190], [337, 190], [], [337, 190], [], [], [447, 399], [], [447, 399], [], [], [859, 686], [], [859, 686], [], [], [411, 939], [], [411, 939], [], [], [89, 340], [], [89, 340], [], [], [342, 41], [], [342, 41], [], [], [27, 846], [], [27, 846], [], [], [748, 162], [], [748, 162], [], [], [255, 34], [], [255, 34], [], []]
