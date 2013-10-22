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
clover.pageData = {"classes":[{"el":100,"id":4296,"methods":[{"el":55,"sc":5,"sl":50},{"el":67,"sc":5,"sl":57},{"el":79,"sc":5,"sl":69},{"el":86,"sc":5,"sl":81},{"el":99,"sc":5,"sl":88}],"name":"PartialMockingTest","sl":31},{"el":48,"id":4296,"methods":[{"el":41,"sc":9,"sl":39},{"el":45,"sc":9,"sl":43}],"name":"PartialMockingTest.A","sl":33}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1000":{"methods":[{"sl":50}],"name":"testPartialMock_PublicConstructor","pass":true,"statements":[{"sl":53},{"sl":54}]},"test_115":{"methods":[{"sl":69}],"name":"testPartialMock_ConstructorNotFound","pass":true,"statements":[{"sl":71},{"sl":72}]},"test_186":{"methods":[{"sl":81}],"name":"testPartialMock_InvalidParams","pass":true,"statements":[{"sl":83},{"sl":84}]},"test_317":{"methods":[{"sl":50}],"name":"testPartialMock_PublicConstructor","pass":true,"statements":[{"sl":53},{"sl":54}]},"test_461":{"methods":[{"sl":39},{"sl":57}],"name":"testPartialMock_ProtectedConstructor","pass":true,"statements":[{"sl":40},{"sl":59},{"sl":60},{"sl":63},{"sl":64},{"sl":65},{"sl":66}]},"test_474":{"methods":[{"sl":69}],"name":"testPartialMock_ConstructorNotFound","pass":true,"statements":[{"sl":71},{"sl":72}]},"test_539":{"methods":[{"sl":88}],"name":"testPartialMock_ExceptionInConstructor","pass":true,"statements":[{"sl":90},{"sl":91},{"sl":92},{"sl":93},{"sl":95},{"sl":97}]},"test_840":{"methods":[{"sl":88}],"name":"testPartialMock_ExceptionInConstructor","pass":true,"statements":[{"sl":90},{"sl":91},{"sl":92},{"sl":93},{"sl":95},{"sl":97}]},"test_863":{"methods":[{"sl":81}],"name":"testPartialMock_InvalidParams","pass":true,"statements":[{"sl":83},{"sl":84}]},"test_993":{"methods":[{"sl":39},{"sl":57}],"name":"testPartialMock_ProtectedConstructor","pass":true,"statements":[{"sl":40},{"sl":59},{"sl":60},{"sl":63},{"sl":64},{"sl":65},{"sl":66}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [993, 461], [993, 461], [], [], [], [], [], [], [], [], [], [1000, 317], [], [], [1000, 317], [1000, 317], [], [], [993, 461], [], [993, 461], [993, 461], [], [], [993, 461], [993, 461], [993, 461], [993, 461], [], [], [474, 115], [], [474, 115], [474, 115], [], [], [], [], [], [], [], [], [863, 186], [], [863, 186], [863, 186], [], [], [], [840, 539], [], [840, 539], [840, 539], [840, 539], [840, 539], [], [840, 539], [], [840, 539], [], [], []]
