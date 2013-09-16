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
clover.pageData = {"classes":[{"el":219,"id":1524,"methods":[{"el":51,"sc":5,"sl":49},{"el":65,"sc":5,"sl":62},{"el":70,"sc":5,"sl":67},{"el":79,"sc":5,"sl":72},{"el":88,"sc":5,"sl":81},{"el":95,"sc":5,"sl":90},{"el":102,"sc":5,"sl":97},{"el":109,"sc":5,"sl":104},{"el":117,"sc":5,"sl":111},{"el":128,"sc":5,"sl":119},{"el":139,"sc":5,"sl":130},{"el":150,"sc":5,"sl":141},{"el":163,"sc":5,"sl":152},{"el":167,"sc":5,"sl":165},{"el":171,"sc":5,"sl":169},{"el":175,"sc":5,"sl":173},{"el":179,"sc":5,"sl":177},{"el":195,"sc":5,"sl":181},{"el":200,"sc":5,"sl":197},{"el":206,"sc":5,"sl":202},{"el":212,"sc":5,"sl":208},{"el":218,"sc":5,"sl":214}],"name":"MockBuilder","sl":37}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1000":{"methods":[{"sl":49},{"sl":62},{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testPartialMock_PublicConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_1002":{"methods":[{"sl":165},{"sl":181}],"name":"testCreateMockIMocksControl","pass":true,"statements":[{"sl":166},{"sl":184},{"sl":185}]},"test_1012":{"methods":[{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructorWithArgs","pass":true,"statements":[{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_1013":{"methods":[{"sl":72}],"name":"testAddMethod_NotExisting","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":75}]},"test_1062":{"methods":[{"sl":49},{"sl":62},{"sl":81},{"sl":177},{"sl":181},{"sl":208}],"name":"testStrictMock_Partial","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":82},{"sl":83},{"sl":86},{"sl":87},{"sl":178},{"sl":184},{"sl":185},{"sl":209},{"sl":211}]},"test_1065":{"methods":[{"sl":72},{"sl":181},{"sl":208}],"name":"testCreateStrictMockString","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185},{"sl":209},{"sl":211}]},"test_1087":{"methods":[{"sl":49},{"sl":62},{"sl":72},{"sl":119},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testClass","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":120},{"sl":121},{"sl":122},{"sl":126},{"sl":127},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_109":{"methods":[{"sl":72},{"sl":173},{"sl":181},{"sl":202}],"name":"testCreateNiceMock","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":174},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_137":{"methods":[{"sl":72},{"sl":169},{"sl":181},{"sl":197}],"name":"testCreateMock","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_142":{"methods":[{"sl":72}],"name":"testAddMethod_NotExisting","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":75}]},"test_143":{"methods":[{"sl":141},{"sl":152},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructorParams","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":153},{"sl":157},{"sl":161},{"sl":162},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_16":{"methods":[{"sl":72},{"sl":181},{"sl":208}],"name":"testCreateStrictMockString","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185},{"sl":209},{"sl":211}]},"test_171":{"methods":[{"sl":111},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructorConstructorArgs","pass":true,"statements":[{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_178":{"methods":[{"sl":141},{"sl":214}],"name":"testWithConstructor_WrongClass","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":147},{"sl":215}]},"test_199":{"methods":[{"sl":141},{"sl":152},{"sl":214}],"name":"testWithArgsTwice","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":153},{"sl":157},{"sl":158},{"sl":161},{"sl":162},{"sl":215}]},"test_203":{"methods":[{"sl":49},{"sl":62},{"sl":141},{"sl":152},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testCreateMockBuilder","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":153},{"sl":157},{"sl":161},{"sl":162},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_212":{"methods":[{"sl":104},{"sl":152},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructor","pass":true,"statements":[{"sl":106},{"sl":107},{"sl":108},{"sl":153},{"sl":157},{"sl":161},{"sl":162},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_213":{"methods":[{"sl":72},{"sl":181},{"sl":202}],"name":"testCreateNiceMockString","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_235":{"methods":[{"sl":141},{"sl":152},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructorParams","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":153},{"sl":157},{"sl":161},{"sl":162},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_253":{"methods":[{"sl":49},{"sl":62},{"sl":81},{"sl":169},{"sl":181},{"sl":197}],"name":"testMock_Partial","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":82},{"sl":83},{"sl":86},{"sl":87},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_279":{"methods":[{"sl":49},{"sl":62},{"sl":72},{"sl":119},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testClass","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":120},{"sl":121},{"sl":122},{"sl":126},{"sl":127},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_28":{"methods":[{"sl":141},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testCreateMock_ConstructorWithoutArgs","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":170},{"sl":184},{"sl":189},{"sl":190},{"sl":198},{"sl":199},{"sl":215}]},"test_290":{"methods":[{"sl":141},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testCreateMock_ConstructorWithoutArgs","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":170},{"sl":184},{"sl":189},{"sl":190},{"sl":198},{"sl":199},{"sl":215}]},"test_317":{"methods":[{"sl":49},{"sl":62},{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testPartialMock_PublicConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_347":{"methods":[{"sl":62},{"sl":67},{"sl":169},{"sl":173},{"sl":177},{"sl":181},{"sl":197},{"sl":202},{"sl":208}],"name":"testAllMockBuilderFlavors","pass":true,"statements":[{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":170},{"sl":174},{"sl":178},{"sl":184},{"sl":185},{"sl":198},{"sl":199},{"sl":203},{"sl":205},{"sl":209},{"sl":211}]},"test_367":{"methods":[{"sl":62},{"sl":67},{"sl":169},{"sl":173},{"sl":177},{"sl":181},{"sl":197},{"sl":202},{"sl":208}],"name":"testAllMockBuilderFlavors","pass":true,"statements":[{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":170},{"sl":174},{"sl":178},{"sl":184},{"sl":185},{"sl":198},{"sl":199},{"sl":203},{"sl":205},{"sl":209},{"sl":211}]},"test_4":{"methods":[{"sl":72},{"sl":181}],"name":"testCreateMockStringIMocksControl","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185}]},"test_400":{"methods":[{"sl":104},{"sl":152},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructor","pass":true,"statements":[{"sl":106},{"sl":107},{"sl":108},{"sl":153},{"sl":157},{"sl":161},{"sl":162},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_436":{"methods":[{"sl":67},{"sl":72},{"sl":81},{"sl":90},{"sl":97},{"sl":169},{"sl":181},{"sl":197}],"name":"testAddMockedMethod","pass":true,"statements":[{"sl":68},{"sl":69},{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":82},{"sl":83},{"sl":86},{"sl":87},{"sl":91},{"sl":92},{"sl":94},{"sl":98},{"sl":99},{"sl":101},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_461":{"methods":[{"sl":49},{"sl":62},{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testPartialMock_ProtectedConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_483":{"methods":[{"sl":49},{"sl":62},{"sl":81},{"sl":169},{"sl":181},{"sl":197}],"name":"testMock_Partial","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":82},{"sl":83},{"sl":86},{"sl":87},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_491":{"methods":[{"sl":141},{"sl":214}],"name":"testWithConstructor_Twice","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":215},{"sl":216}]},"test_495":{"methods":[{"sl":152}],"name":"testWithArgs_WithoutConstructor","pass":true,"statements":[{"sl":153},{"sl":154}]},"test_512":{"methods":[{"sl":130},{"sl":214}],"name":"testWithConstructorWithArgs_NotExisting","pass":true,"statements":[{"sl":131},{"sl":132},{"sl":133},{"sl":135},{"sl":215}]},"test_517":{"methods":[{"sl":152}],"name":"testWithArgs_WithoutConstructor","pass":true,"statements":[{"sl":153},{"sl":154}]},"test_529":{"methods":[{"sl":49},{"sl":62},{"sl":72},{"sl":173},{"sl":181},{"sl":202}],"name":"testNiceMock_Partial","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":174},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_539":{"methods":[{"sl":49},{"sl":62},{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testPartialMock_ExceptionInConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_578":{"methods":[{"sl":72},{"sl":181}],"name":"testCreateMockStringIMocksControl","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185}]},"test_581":{"methods":[{"sl":72},{"sl":181},{"sl":202}],"name":"testCreateNiceMockString","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_59":{"methods":[{"sl":62},{"sl":67},{"sl":169},{"sl":181},{"sl":197}],"name":"testCreateMockBuilder","pass":true,"statements":[{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_627":{"methods":[{"sl":141},{"sl":214}],"name":"testWithConstructor_WrongClass","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":147},{"sl":215}]},"test_631":{"methods":[{"sl":49},{"sl":62},{"sl":119},{"sl":214}],"name":"testWithEmptyConstructor_NoEmptyConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":120},{"sl":121},{"sl":122},{"sl":124},{"sl":215}]},"test_642":{"methods":[{"sl":72},{"sl":173},{"sl":181},{"sl":202}],"name":"testCreateNiceMock","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":174},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_660":{"methods":[{"sl":72},{"sl":177},{"sl":181},{"sl":208}],"name":"testCreateStrictMock","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":178},{"sl":184},{"sl":185},{"sl":209},{"sl":211}]},"test_661":{"methods":[{"sl":49},{"sl":62},{"sl":119},{"sl":214}],"name":"testWithEmptyConstructor_NoEmptyConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":120},{"sl":121},{"sl":122},{"sl":124},{"sl":215}]},"test_665":{"methods":[{"sl":81}],"name":"testAddMethodWithParams_NotExisting","pass":true,"statements":[{"sl":82},{"sl":83},{"sl":84}]},"test_693":{"methods":[{"sl":141},{"sl":152},{"sl":214}],"name":"testWithArgsTwice","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":153},{"sl":157},{"sl":158},{"sl":161},{"sl":162},{"sl":215}]},"test_7":{"methods":[{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructorWithArgs","pass":true,"statements":[{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_755":{"methods":[{"sl":49},{"sl":62},{"sl":173},{"sl":181},{"sl":202}],"name":"testNotMockedFillInStackTrace","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":174},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_759":{"methods":[{"sl":111},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithConstructorConstructorArgs","pass":true,"statements":[{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_761":{"methods":[{"sl":62},{"sl":67},{"sl":165},{"sl":181}],"name":"testCreateMockBuilder_existingControl","pass":true,"statements":[{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":166},{"sl":184},{"sl":185}]},"test_764":{"methods":[{"sl":72},{"sl":181},{"sl":197}],"name":"testCreateMockString","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_772":{"methods":[{"sl":49},{"sl":62},{"sl":67},{"sl":181},{"sl":197},{"sl":202},{"sl":208}],"name":"testNamedMock","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":184},{"sl":185},{"sl":198},{"sl":199},{"sl":203},{"sl":205},{"sl":209},{"sl":211}]},"test_773":{"methods":[{"sl":49},{"sl":62},{"sl":141},{"sl":152},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testCreateMockBuilder","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":153},{"sl":157},{"sl":161},{"sl":162},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_80":{"methods":[{"sl":72},{"sl":177},{"sl":181},{"sl":208}],"name":"testCreateStrictMock","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":178},{"sl":184},{"sl":185},{"sl":209},{"sl":211}]},"test_809":{"methods":[{"sl":119},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithEmptyConstructor","pass":true,"statements":[{"sl":120},{"sl":121},{"sl":122},{"sl":126},{"sl":127},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_819":{"methods":[{"sl":72},{"sl":181},{"sl":197}],"name":"testCreateMockString","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_829":{"methods":[{"sl":62},{"sl":67},{"sl":169},{"sl":181},{"sl":197}],"name":"testCreateMockBuilder","pass":true,"statements":[{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_840":{"methods":[{"sl":49},{"sl":62},{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testPartialMock_ExceptionInConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_855":{"methods":[{"sl":67},{"sl":72},{"sl":81},{"sl":90},{"sl":97},{"sl":169},{"sl":181},{"sl":197}],"name":"testAddMockedMethod","pass":true,"statements":[{"sl":68},{"sl":69},{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":82},{"sl":83},{"sl":86},{"sl":87},{"sl":91},{"sl":92},{"sl":94},{"sl":98},{"sl":99},{"sl":101},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_861":{"methods":[{"sl":49},{"sl":62},{"sl":173},{"sl":181},{"sl":202}],"name":"testNotMockedFillInStackTrace","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":174},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_899":{"methods":[{"sl":130},{"sl":214}],"name":"testWithConstructorWithArgs_NotExisting","pass":true,"statements":[{"sl":131},{"sl":132},{"sl":133},{"sl":135},{"sl":215}]},"test_916":{"methods":[{"sl":49},{"sl":62},{"sl":81},{"sl":177},{"sl":181},{"sl":208}],"name":"testStrictMock_Partial","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":82},{"sl":83},{"sl":86},{"sl":87},{"sl":178},{"sl":184},{"sl":185},{"sl":209},{"sl":211}]},"test_942":{"methods":[{"sl":119},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testWithEmptyConstructor","pass":true,"statements":[{"sl":120},{"sl":121},{"sl":122},{"sl":126},{"sl":127},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_95":{"methods":[{"sl":81}],"name":"testAddMethodWithParams_NotExisting","pass":true,"statements":[{"sl":82},{"sl":83},{"sl":84}]},"test_954":{"methods":[{"sl":72},{"sl":169},{"sl":181},{"sl":197}],"name":"testCreateMock","pass":true,"statements":[{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":170},{"sl":184},{"sl":185},{"sl":198},{"sl":199}]},"test_957":{"methods":[{"sl":49},{"sl":62},{"sl":72},{"sl":173},{"sl":181},{"sl":202}],"name":"testNiceMock_Partial","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":73},{"sl":74},{"sl":77},{"sl":78},{"sl":174},{"sl":184},{"sl":185},{"sl":203},{"sl":205}]},"test_979":{"methods":[{"sl":141},{"sl":214}],"name":"testWithConstructor_Twice","pass":true,"statements":[{"sl":142},{"sl":144},{"sl":145},{"sl":149},{"sl":215},{"sl":216}]},"test_989":{"methods":[{"sl":49},{"sl":62},{"sl":67},{"sl":181},{"sl":197},{"sl":202},{"sl":208}],"name":"testNamedMock","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":184},{"sl":185},{"sl":198},{"sl":199},{"sl":203},{"sl":205},{"sl":209},{"sl":211}]},"test_993":{"methods":[{"sl":49},{"sl":62},{"sl":130},{"sl":169},{"sl":181},{"sl":197},{"sl":214}],"name":"testPartialMock_ProtectedConstructor","pass":true,"statements":[{"sl":50},{"sl":63},{"sl":64},{"sl":131},{"sl":132},{"sl":133},{"sl":137},{"sl":138},{"sl":170},{"sl":184},{"sl":189},{"sl":193},{"sl":198},{"sl":199},{"sl":215}]},"test_995":{"methods":[{"sl":62},{"sl":67},{"sl":165},{"sl":181}],"name":"testCreateMockBuilder_existingControl","pass":true,"statements":[{"sl":63},{"sl":64},{"sl":68},{"sl":69},{"sl":166},{"sl":184},{"sl":185}]},"test_999":{"methods":[{"sl":165},{"sl":181}],"name":"testCreateMockIMocksControl","pass":true,"statements":[{"sl":166},{"sl":184},{"sl":185}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [279, 772, 957, 539, 203, 253, 916, 993, 1087, 461, 755, 661, 631, 483, 773, 840, 1000, 989, 317, 529, 861, 1062], [279, 772, 957, 539, 203, 253, 916, 993, 1087, 461, 755, 661, 631, 483, 773, 840, 1000, 989, 317, 529, 861, 1062], [], [], [], [], [], [], [], [], [], [], [], [279, 772, 957, 539, 203, 253, 367, 916, 995, 993, 1087, 461, 755, 661, 631, 829, 483, 773, 840, 59, 347, 1000, 989, 317, 529, 761, 861, 1062], [279, 772, 957, 539, 203, 253, 367, 916, 995, 993, 1087, 461, 755, 661, 631, 829, 483, 773, 840, 59, 347, 1000, 989, 317, 529, 761, 861, 1062], [279, 772, 957, 539, 203, 253, 367, 916, 995, 993, 1087, 461, 755, 661, 631, 829, 483, 773, 840, 59, 347, 1000, 989, 317, 529, 761, 861, 1062], [], [], [772, 367, 995, 436, 829, 59, 347, 989, 761, 855], [772, 367, 995, 436, 829, 59, 347, 989, 761, 855], [772, 367, 995, 436, 829, 59, 347, 989, 761, 855], [], [], [279, 109, 957, 764, 642, 1087, 142, 1065, 819, 581, 4, 436, 16, 137, 954, 578, 529, 660, 855, 1013, 80, 213], [279, 109, 957, 764, 642, 1087, 142, 1065, 819, 581, 4, 436, 16, 137, 954, 578, 529, 660, 855, 1013, 80, 213], [279, 109, 957, 764, 642, 1087, 142, 1065, 819, 581, 4, 436, 16, 137, 954, 578, 529, 660, 855, 1013, 80, 213], [142, 1013], [], [279, 109, 957, 764, 642, 1087, 1065, 819, 581, 4, 436, 16, 137, 954, 578, 529, 660, 855, 80, 213], [279, 109, 957, 764, 642, 1087, 1065, 819, 581, 4, 436, 16, 137, 954, 578, 529, 660, 855, 80, 213], [], [], [253, 916, 436, 665, 483, 95, 855, 1062], [253, 916, 436, 665, 483, 95, 855, 1062], [253, 916, 436, 665, 483, 95, 855, 1062], [665, 95], [], [253, 916, 436, 483, 855, 1062], [253, 916, 436, 483, 855, 1062], [], [], [436, 855], [436, 855], [436, 855], [], [436, 855], [], [], [436, 855], [436, 855], [436, 855], [], [436, 855], [], [], [212, 400], [], [212, 400], [212, 400], [212, 400], [], [], [759, 171], [], [759, 171], [759, 171], [759, 171], [759, 171], [], [], [279, 1087, 809, 661, 631, 942], [279, 1087, 809, 661, 631, 942], [279, 1087, 809, 661, 631, 942], [279, 1087, 809, 661, 631, 942], [], [661, 631], [], [279, 1087, 809, 942], [279, 1087, 809, 942], [], [], [539, 899, 993, 461, 1012, 7, 840, 1000, 317, 512], [539, 899, 993, 461, 1012, 7, 840, 1000, 317, 512], [539, 899, 993, 461, 1012, 7, 840, 1000, 317, 512], [539, 899, 993, 461, 1012, 7, 840, 1000, 317, 512], [], [899, 512], [], [539, 993, 461, 1012, 7, 840, 1000, 317], [539, 993, 461, 1012, 7, 840, 1000, 317], [], [], [143, 203, 178, 693, 627, 235, 773, 491, 28, 199, 290, 979], [143, 203, 178, 693, 627, 235, 773, 491, 28, 199, 290, 979], [], [143, 203, 178, 693, 627, 235, 773, 491, 28, 199, 290, 979], [143, 203, 178, 693, 627, 235, 773, 491, 28, 199, 290, 979], [], [178, 627], [], [143, 203, 693, 235, 773, 491, 28, 199, 290, 979], [], [], [143, 203, 517, 693, 235, 495, 773, 212, 199, 400], [143, 203, 517, 693, 235, 495, 773, 212, 199, 400], [517, 495], [], [], [143, 203, 693, 235, 773, 212, 199, 400], [693, 199], [], [], [143, 203, 693, 235, 773, 212, 199, 400], [143, 203, 693, 235, 773, 212, 199, 400], [], [], [999, 995, 1002, 761], [999, 995, 1002, 761], [], [], [279, 143, 539, 203, 253, 367, 993, 1087, 809, 461, 235, 436, 1012, 7, 759, 137, 954, 829, 483, 773, 840, 171, 59, 347, 1000, 28, 317, 212, 855, 400, 290, 942], [279, 143, 539, 203, 253, 367, 993, 1087, 809, 461, 235, 436, 1012, 7, 759, 137, 954, 829, 483, 773, 840, 171, 59, 347, 1000, 28, 317, 212, 855, 400, 290, 942], [], [], [109, 957, 367, 642, 755, 347, 529, 861], [109, 957, 367, 642, 755, 347, 529, 861], [], [], [367, 916, 347, 660, 1062, 80], [367, 916, 347, 660, 1062, 80], [], [], [279, 109, 772, 143, 957, 539, 999, 203, 764, 253, 367, 916, 995, 642, 993, 1087, 1065, 809, 819, 461, 581, 4, 755, 235, 436, 1012, 7, 16, 759, 137, 954, 829, 483, 773, 840, 1002, 171, 59, 347, 1000, 989, 28, 578, 317, 529, 212, 761, 660, 855, 861, 1062, 400, 290, 80, 942, 213], [], [], [279, 109, 772, 143, 957, 539, 999, 203, 764, 253, 367, 916, 995, 642, 993, 1087, 1065, 809, 819, 461, 581, 4, 755, 235, 436, 1012, 7, 16, 759, 137, 954, 829, 483, 773, 840, 1002, 171, 59, 347, 1000, 989, 28, 578, 317, 529, 212, 761, 660, 855, 861, 1062, 400, 290, 80, 942, 213], [109, 772, 957, 999, 764, 253, 367, 916, 995, 642, 1065, 819, 581, 4, 755, 436, 16, 137, 954, 829, 483, 1002, 59, 347, 989, 578, 529, 761, 660, 855, 861, 1062, 80, 213], [], [], [], [279, 143, 539, 203, 993, 1087, 809, 461, 235, 1012, 7, 759, 773, 840, 171, 1000, 28, 317, 212, 400, 290, 942], [28, 290], [], [], [279, 143, 539, 203, 993, 1087, 809, 461, 235, 1012, 7, 759, 773, 840, 171, 1000, 317, 212, 400, 942], [], [], [], [279, 772, 143, 539, 203, 764, 253, 367, 993, 1087, 809, 819, 461, 235, 436, 1012, 7, 759, 137, 954, 829, 483, 773, 840, 171, 59, 347, 1000, 989, 28, 317, 212, 855, 400, 290, 942], [279, 772, 143, 539, 203, 764, 253, 367, 993, 1087, 809, 819, 461, 235, 436, 1012, 7, 759, 137, 954, 829, 483, 773, 840, 171, 59, 347, 1000, 989, 28, 317, 212, 855, 400, 290, 942], [279, 772, 143, 539, 203, 764, 253, 367, 993, 1087, 809, 819, 461, 235, 436, 1012, 7, 759, 137, 954, 829, 483, 773, 840, 171, 59, 347, 1000, 989, 28, 317, 212, 855, 400, 290, 942], [], [], [109, 772, 957, 367, 642, 581, 755, 347, 989, 529, 861, 213], [109, 772, 957, 367, 642, 581, 755, 347, 989, 529, 861, 213], [], [109, 772, 957, 367, 642, 581, 755, 347, 989, 529, 861, 213], [], [], [772, 367, 916, 1065, 16, 347, 989, 660, 1062, 80], [772, 367, 916, 1065, 16, 347, 989, 660, 1062, 80], [], [772, 367, 916, 1065, 16, 347, 989, 660, 1062, 80], [], [], [279, 143, 539, 203, 899, 178, 693, 993, 1087, 809, 461, 627, 235, 661, 631, 1012, 7, 759, 773, 840, 171, 491, 1000, 28, 317, 212, 199, 400, 290, 979, 512, 942], [279, 143, 539, 203, 899, 178, 693, 993, 1087, 809, 461, 627, 235, 661, 631, 1012, 7, 759, 773, 840, 171, 491, 1000, 28, 317, 212, 199, 400, 290, 979, 512, 942], [491, 979], [], [], []]
