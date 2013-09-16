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
clover.pageData = {"classes":[{"el":226,"id":3581,"methods":[{"el":62,"sc":5,"sl":35},{"el":74,"sc":5,"sl":64},{"el":86,"sc":5,"sl":76},{"el":93,"sc":5,"sl":88},{"el":116,"sc":5,"sl":95},{"el":133,"sc":25,"sl":130},{"el":138,"sc":25,"sl":135},{"el":143,"sc":25,"sl":140},{"el":148,"sc":25,"sl":145},{"el":152,"sc":13,"sl":126},{"el":176,"sc":5,"sl":118},{"el":189,"sc":13,"sl":183},{"el":214,"sc":5,"sl":178},{"el":221,"sc":5,"sl":216},{"el":225,"sc":5,"sl":223}],"name":"EasyMockPropertiesTest","sl":33}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_106":{"methods":[{"sl":118},{"sl":126},{"sl":130},{"sl":135},{"sl":216}],"name":"testBadPropertiesFile","pass":true,"statements":[{"sl":121},{"sl":124},{"sl":128},{"sl":129},{"sl":132},{"sl":137},{"sl":155},{"sl":156},{"sl":157},{"sl":160},{"sl":162},{"sl":163},{"sl":166},{"sl":168},{"sl":174},{"sl":218},{"sl":219},{"sl":220}]},"test_1061":{"methods":[{"sl":178},{"sl":183},{"sl":216},{"sl":223}],"name":"testNoEasymockPropertiesFile","pass":true,"statements":[{"sl":181},{"sl":185},{"sl":186},{"sl":192},{"sl":193},{"sl":194},{"sl":197},{"sl":202},{"sl":205},{"sl":207},{"sl":212},{"sl":218},{"sl":219},{"sl":220},{"sl":224}]},"test_1066":{"methods":[{"sl":88}],"name":"testSetProperty","pass":true,"statements":[{"sl":90},{"sl":92}]},"test_132":{"methods":[{"sl":95},{"sl":216},{"sl":223}],"name":"testNoThreadContextClassLoader","pass":true,"statements":[{"sl":97},{"sl":98},{"sl":99},{"sl":102},{"sl":106},{"sl":109},{"sl":114},{"sl":218},{"sl":219},{"sl":220},{"sl":224}]},"test_184":{"methods":[{"sl":76}],"name":"testGetProperty","pass":true,"statements":[{"sl":78},{"sl":81},{"sl":83},{"sl":85}]},"test_222":{"methods":[{"sl":76}],"name":"testGetProperty","pass":true,"statements":[{"sl":78},{"sl":81},{"sl":83},{"sl":85}]},"test_504":{"methods":[{"sl":64},{"sl":223}],"name":"testGetInstance","pass":true,"statements":[{"sl":66},{"sl":67},{"sl":68},{"sl":69},{"sl":70},{"sl":71},{"sl":72},{"sl":73},{"sl":224}]},"test_565":{"methods":[{"sl":118},{"sl":126},{"sl":130},{"sl":135},{"sl":216}],"name":"testBadPropertiesFile","pass":true,"statements":[{"sl":121},{"sl":124},{"sl":128},{"sl":129},{"sl":132},{"sl":137},{"sl":155},{"sl":156},{"sl":157},{"sl":160},{"sl":162},{"sl":163},{"sl":166},{"sl":168},{"sl":174},{"sl":218},{"sl":219},{"sl":220}]},"test_596":{"methods":[{"sl":178},{"sl":183},{"sl":216},{"sl":223}],"name":"testNoEasymockPropertiesFile","pass":true,"statements":[{"sl":181},{"sl":185},{"sl":186},{"sl":192},{"sl":193},{"sl":194},{"sl":197},{"sl":202},{"sl":205},{"sl":207},{"sl":212},{"sl":218},{"sl":219},{"sl":220},{"sl":224}]},"test_599":{"methods":[{"sl":95},{"sl":216},{"sl":223}],"name":"testNoThreadContextClassLoader","pass":true,"statements":[{"sl":97},{"sl":98},{"sl":99},{"sl":102},{"sl":106},{"sl":109},{"sl":114},{"sl":218},{"sl":219},{"sl":220},{"sl":224}]},"test_639":{"methods":[{"sl":64},{"sl":223}],"name":"testGetInstance","pass":true,"statements":[{"sl":66},{"sl":67},{"sl":68},{"sl":69},{"sl":70},{"sl":71},{"sl":72},{"sl":73},{"sl":224}]},"test_66":{"methods":[{"sl":88}],"name":"testSetProperty","pass":true,"statements":[{"sl":90},{"sl":92}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [639, 504], [], [639, 504], [639, 504], [639, 504], [639, 504], [639, 504], [639, 504], [639, 504], [639, 504], [], [], [222, 184], [], [222, 184], [], [], [222, 184], [], [222, 184], [], [222, 184], [], [], [66, 1066], [], [66, 1066], [], [66, 1066], [], [], [132, 599], [], [132, 599], [132, 599], [132, 599], [], [], [132, 599], [], [], [], [132, 599], [], [], [132, 599], [], [], [], [], [132, 599], [], [], [], [565, 106], [], [], [565, 106], [], [], [565, 106], [], [565, 106], [], [565, 106], [565, 106], [565, 106], [], [565, 106], [], [], [565, 106], [], [565, 106], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [565, 106], [565, 106], [565, 106], [], [], [565, 106], [], [565, 106], [565, 106], [], [], [565, 106], [], [565, 106], [], [], [], [], [], [565, 106], [], [], [], [596, 1061], [], [], [596, 1061], [], [596, 1061], [], [596, 1061], [596, 1061], [], [], [], [], [], [596, 1061], [596, 1061], [596, 1061], [], [], [596, 1061], [], [], [], [], [596, 1061], [], [], [596, 1061], [], [596, 1061], [], [], [], [], [596, 1061], [], [], [], [565, 596, 106, 1061, 132, 599], [], [565, 596, 106, 1061, 132, 599], [565, 596, 106, 1061, 132, 599], [565, 596, 106, 1061, 132, 599], [], [], [596, 639, 504, 1061, 132, 599], [596, 639, 504, 1061, 132, 599], [], []]
