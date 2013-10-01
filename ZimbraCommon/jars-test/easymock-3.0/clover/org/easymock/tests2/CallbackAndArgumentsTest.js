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
clover.pageData = {"classes":[{"el":101,"id":2904,"methods":[{"el":37,"sc":5,"sl":34},{"el":49,"sc":13,"sl":46},{"el":60,"sc":5,"sl":39},{"el":65,"sc":5,"sl":62},{"el":78,"sc":13,"sl":75},{"el":87,"sc":13,"sl":83},{"el":100,"sc":5,"sl":67}],"name":"CallbackAndArgumentsTest","sl":30}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_275":{"methods":[{"sl":62}],"name":"currentArgumentsFailsOutsideCallbacks","pass":true,"statements":[{"sl":64}]},"test_283":{"methods":[{"sl":67},{"sl":75},{"sl":83}],"name":"callbackGetsArgumentsEvenIfAMockCallsAnother","pass":true,"statements":[{"sl":70},{"sl":72},{"sl":73},{"sl":74},{"sl":77},{"sl":81},{"sl":82},{"sl":84},{"sl":85},{"sl":86},{"sl":90},{"sl":91},{"sl":93},{"sl":94},{"sl":96},{"sl":97},{"sl":99}]},"test_309":{"methods":[{"sl":67},{"sl":75},{"sl":83}],"name":"callbackGetsArgumentsEvenIfAMockCallsAnother","pass":true,"statements":[{"sl":70},{"sl":72},{"sl":73},{"sl":74},{"sl":77},{"sl":81},{"sl":82},{"sl":84},{"sl":85},{"sl":86},{"sl":90},{"sl":91},{"sl":93},{"sl":94},{"sl":96},{"sl":97},{"sl":99}]},"test_379":{"methods":[{"sl":39},{"sl":46}],"name":"callbackGetsArguments","pass":true,"statements":[{"sl":42},{"sl":44},{"sl":45},{"sl":47},{"sl":48},{"sl":52},{"sl":54},{"sl":55},{"sl":57},{"sl":59}]},"test_716":{"methods":[{"sl":39},{"sl":46}],"name":"callbackGetsArguments","pass":true,"statements":[{"sl":42},{"sl":44},{"sl":45},{"sl":47},{"sl":48},{"sl":52},{"sl":54},{"sl":55},{"sl":57},{"sl":59}]},"test_909":{"methods":[{"sl":62}],"name":"currentArgumentsFailsOutsideCallbacks","pass":true,"statements":[{"sl":64}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [716, 379], [], [], [716, 379], [], [716, 379], [716, 379], [716, 379], [716, 379], [716, 379], [], [], [], [716, 379], [], [716, 379], [716, 379], [], [716, 379], [], [716, 379], [], [], [275, 909], [], [275, 909], [], [], [283, 309], [], [], [283, 309], [], [283, 309], [283, 309], [283, 309], [283, 309], [], [283, 309], [], [], [], [283, 309], [283, 309], [283, 309], [283, 309], [283, 309], [283, 309], [], [], [], [283, 309], [283, 309], [], [283, 309], [283, 309], [], [283, 309], [283, 309], [], [283, 309], [], []]
