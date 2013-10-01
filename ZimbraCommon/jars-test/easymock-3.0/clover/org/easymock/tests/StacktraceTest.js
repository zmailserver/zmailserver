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
clover.pageData = {"classes":[{"el":141,"id":6004,"methods":[{"el":41,"sc":5,"sl":38},{"el":59,"sc":5,"sl":50},{"el":70,"sc":5,"sl":61},{"el":81,"sc":5,"sl":72},{"el":94,"sc":5,"sl":83},{"el":105,"sc":5,"sl":96},{"el":114,"sc":21,"sl":111},{"el":124,"sc":5,"sl":107},{"el":131,"sc":13,"sl":129},{"el":140,"sc":5,"sl":126}],"name":"StacktraceTest","sl":34},{"el":48,"id":6006,"methods":[{"el":47,"sc":9,"sl":44}],"name":"StacktraceTest.ToStringThrowsException","sl":43}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_136":{"methods":[{"sl":96}],"name":"assertFillWhenThrowingAnswer","pass":true,"statements":[{"sl":98},{"sl":99},{"sl":100},{"sl":101},{"sl":103}]},"test_216":{"methods":[{"sl":107},{"sl":111}],"name":"assertNoFillWhenDelegatingAnswer","pass":true,"statements":[{"sl":109},{"sl":113},{"sl":116},{"sl":117},{"sl":118},{"sl":119},{"sl":121}]},"test_243":{"methods":[{"sl":44},{"sl":50}],"name":"assertRecordStateNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":52},{"sl":53},{"sl":54},{"sl":56}]},"test_286":{"methods":[{"sl":126},{"sl":129}],"name":"assertNoFillWhenIAnswerAnswer","pass":true,"statements":[{"sl":128},{"sl":130},{"sl":133},{"sl":134},{"sl":135},{"sl":136},{"sl":138}]},"test_300":{"methods":[{"sl":96}],"name":"assertFillWhenThrowingAnswer","pass":true,"statements":[{"sl":98},{"sl":99},{"sl":100},{"sl":101},{"sl":103}]},"test_378":{"methods":[{"sl":107},{"sl":111}],"name":"assertNoFillWhenDelegatingAnswer","pass":true,"statements":[{"sl":109},{"sl":113},{"sl":116},{"sl":117},{"sl":118},{"sl":119},{"sl":121}]},"test_414":{"methods":[{"sl":44},{"sl":50}],"name":"assertRecordStateNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":52},{"sl":53},{"sl":54},{"sl":56}]},"test_445":{"methods":[{"sl":44},{"sl":72}],"name":"assertReplayStateNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":74},{"sl":75},{"sl":76},{"sl":78}]},"test_54":{"methods":[{"sl":44},{"sl":61}],"name":"assertReplayNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":63},{"sl":64},{"sl":65},{"sl":67}]},"test_569":{"methods":[{"sl":44},{"sl":72}],"name":"assertReplayStateNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":74},{"sl":75},{"sl":76},{"sl":78}]},"test_621":{"methods":[{"sl":126},{"sl":129}],"name":"assertNoFillWhenIAnswerAnswer","pass":true,"statements":[{"sl":128},{"sl":130},{"sl":133},{"sl":134},{"sl":135},{"sl":136},{"sl":138}]},"test_749":{"methods":[{"sl":44},{"sl":61}],"name":"assertReplayNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":63},{"sl":64},{"sl":65},{"sl":67}]},"test_828":{"methods":[{"sl":44},{"sl":83}],"name":"assertVerifyNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":91}]},"test_877":{"methods":[{"sl":44},{"sl":83}],"name":"assertVerifyNoFillInStacktraceWhenExceptionNotFromEasyMock","pass":true,"statements":[{"sl":46},{"sl":85},{"sl":86},{"sl":87},{"sl":88},{"sl":91}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [445, 749, 243, 877, 828, 54, 414, 569], [], [445, 749, 243, 877, 828, 54, 414, 569], [], [], [], [243, 414], [], [243, 414], [243, 414], [243, 414], [], [243, 414], [], [], [], [], [749, 54], [], [749, 54], [749, 54], [749, 54], [], [749, 54], [], [], [], [], [445, 569], [], [445, 569], [445, 569], [445, 569], [], [445, 569], [], [], [], [], [877, 828], [], [877, 828], [877, 828], [877, 828], [877, 828], [], [], [877, 828], [], [], [], [], [136, 300], [], [136, 300], [136, 300], [136, 300], [136, 300], [], [136, 300], [], [], [], [216, 378], [], [216, 378], [], [216, 378], [], [216, 378], [], [], [216, 378], [216, 378], [216, 378], [216, 378], [], [216, 378], [], [], [], [], [286, 621], [], [286, 621], [286, 621], [286, 621], [], [], [286, 621], [286, 621], [286, 621], [286, 621], [], [286, 621], [], [], []]
