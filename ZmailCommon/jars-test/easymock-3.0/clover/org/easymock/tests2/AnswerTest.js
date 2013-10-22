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
clover.pageData = {"classes":[{"el":173,"id":2831,"methods":[{"el":37,"sc":5,"sl":34},{"el":45,"sc":13,"sl":42},{"el":52,"sc":13,"sl":49},{"el":71,"sc":5,"sl":39},{"el":79,"sc":13,"sl":76},{"el":86,"sc":13,"sl":83},{"el":101,"sc":5,"sl":73},{"el":111,"sc":5,"sl":103},{"el":121,"sc":5,"sl":113},{"el":143,"sc":13,"sl":141},{"el":154,"sc":5,"sl":133},{"el":165,"sc":13,"sl":161},{"el":172,"sc":5,"sl":156}],"name":"AnswerTest","sl":30},{"el":124,"id":2880,"methods":[],"name":"AnswerTest.A","sl":123},{"el":127,"id":2880,"methods":[],"name":"AnswerTest.B","sl":126},{"el":131,"id":2880,"methods":[],"name":"AnswerTest.C","sl":129}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_110":{"methods":[{"sl":39},{"sl":42},{"sl":49}],"name":"answer","pass":true,"statements":[{"sl":41},{"sl":43},{"sl":44},{"sl":48},{"sl":50},{"sl":51},{"sl":55},{"sl":58},{"sl":60},{"sl":61},{"sl":62},{"sl":63},{"sl":66},{"sl":68},{"sl":70}]},"test_180":{"methods":[{"sl":103}],"name":"nullAnswerNotAllowed","pass":true,"statements":[{"sl":105},{"sl":106},{"sl":109}]},"test_552":{"methods":[{"sl":113}],"name":"nullStubAnswerNotAllowed","pass":true,"statements":[{"sl":115},{"sl":116},{"sl":119}]},"test_570":{"methods":[{"sl":103}],"name":"nullAnswerNotAllowed","pass":true,"statements":[{"sl":105},{"sl":106},{"sl":109}]},"test_620":{"methods":[{"sl":113}],"name":"nullStubAnswerNotAllowed","pass":true,"statements":[{"sl":115},{"sl":116},{"sl":119}]},"test_685":{"methods":[{"sl":39},{"sl":42},{"sl":49}],"name":"answer","pass":true,"statements":[{"sl":41},{"sl":43},{"sl":44},{"sl":48},{"sl":50},{"sl":51},{"sl":55},{"sl":58},{"sl":60},{"sl":61},{"sl":62},{"sl":63},{"sl":66},{"sl":68},{"sl":70}]},"test_762":{"methods":[{"sl":73},{"sl":76},{"sl":83}],"name":"stubAnswer","pass":true,"statements":[{"sl":75},{"sl":77},{"sl":78},{"sl":82},{"sl":84},{"sl":85},{"sl":89},{"sl":90},{"sl":92},{"sl":94},{"sl":95},{"sl":96},{"sl":97},{"sl":98},{"sl":100}]},"test_790":{"methods":[{"sl":133},{"sl":141}],"name":"testGenericityFlexibility","pass":true,"statements":[{"sl":136},{"sl":137},{"sl":139},{"sl":142},{"sl":147},{"sl":148},{"sl":150},{"sl":151},{"sl":152},{"sl":153}]},"test_827":{"methods":[{"sl":156},{"sl":161}],"name":"answerOnVoidMethod","pass":true,"statements":[{"sl":158},{"sl":159},{"sl":160},{"sl":162},{"sl":163},{"sl":164},{"sl":167},{"sl":168},{"sl":169},{"sl":171}]},"test_860":{"methods":[{"sl":73},{"sl":76},{"sl":83}],"name":"stubAnswer","pass":true,"statements":[{"sl":75},{"sl":77},{"sl":78},{"sl":82},{"sl":84},{"sl":85},{"sl":89},{"sl":90},{"sl":92},{"sl":94},{"sl":95},{"sl":96},{"sl":97},{"sl":98},{"sl":100}]},"test_878":{"methods":[{"sl":133},{"sl":141}],"name":"testGenericityFlexibility","pass":true,"statements":[{"sl":136},{"sl":137},{"sl":139},{"sl":142},{"sl":147},{"sl":148},{"sl":150},{"sl":151},{"sl":152},{"sl":153}]},"test_880":{"methods":[{"sl":156},{"sl":161}],"name":"answerOnVoidMethod","pass":true,"statements":[{"sl":158},{"sl":159},{"sl":160},{"sl":162},{"sl":163},{"sl":164},{"sl":167},{"sl":168},{"sl":169},{"sl":171}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [685, 110], [], [685, 110], [685, 110], [685, 110], [685, 110], [], [], [], [685, 110], [685, 110], [685, 110], [685, 110], [], [], [], [685, 110], [], [], [685, 110], [], [685, 110], [685, 110], [685, 110], [685, 110], [], [], [685, 110], [], [685, 110], [], [685, 110], [], [], [860, 762], [], [860, 762], [860, 762], [860, 762], [860, 762], [], [], [], [860, 762], [860, 762], [860, 762], [860, 762], [], [], [], [860, 762], [860, 762], [], [860, 762], [], [860, 762], [860, 762], [860, 762], [860, 762], [860, 762], [], [860, 762], [], [], [570, 180], [], [570, 180], [570, 180], [], [], [570, 180], [], [], [], [552, 620], [], [552, 620], [552, 620], [], [], [552, 620], [], [], [], [], [], [], [], [], [], [], [], [], [], [790, 878], [], [], [790, 878], [790, 878], [], [790, 878], [], [790, 878], [790, 878], [], [], [], [], [790, 878], [790, 878], [], [790, 878], [790, 878], [790, 878], [790, 878], [], [], [880, 827], [], [880, 827], [880, 827], [880, 827], [880, 827], [880, 827], [880, 827], [880, 827], [], [], [880, 827], [880, 827], [880, 827], [], [880, 827], [], []]
