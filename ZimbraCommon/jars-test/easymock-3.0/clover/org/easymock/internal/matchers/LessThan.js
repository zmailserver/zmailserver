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
clover.pageData = {"classes":[{"el":42,"id":2767,"methods":[{"el":31,"sc":5,"sl":29},{"el":36,"sc":5,"sl":33},{"el":41,"sc":5,"sl":38}],"name":"LessThan","sl":25}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1060":{"methods":[{"sl":29},{"sl":38}],"name":"lessThan","pass":true,"statements":[{"sl":30},{"sl":40}]},"test_159":{"methods":[{"sl":29},{"sl":38}],"name":"lessThanOverloaded","pass":true,"statements":[{"sl":30},{"sl":40}]},"test_264":{"methods":[{"sl":29},{"sl":38}],"name":"lessThanOverloaded","pass":true,"statements":[{"sl":30},{"sl":40}]},"test_467":{"methods":[{"sl":29},{"sl":38}],"name":"greaterOrEqual","pass":true,"statements":[{"sl":30},{"sl":40}]},"test_56":{"methods":[{"sl":29}],"name":"additionalMatchersFailAtReplay","pass":true,"statements":[{"sl":30}]},"test_674":{"methods":[{"sl":29},{"sl":38}],"name":"lessThan","pass":true,"statements":[{"sl":30},{"sl":40}]},"test_694":{"methods":[{"sl":29},{"sl":38}],"name":"greaterOrEqual","pass":true,"statements":[{"sl":30},{"sl":40}]},"test_835":{"methods":[{"sl":29},{"sl":33},{"sl":38}],"name":"testLessThan","pass":true,"statements":[{"sl":30},{"sl":35},{"sl":40}]},"test_958":{"methods":[{"sl":29}],"name":"additionalMatchersFailAtReplay","pass":true,"statements":[{"sl":30}]},"test_99":{"methods":[{"sl":29},{"sl":33},{"sl":38}],"name":"testLessThan","pass":true,"statements":[{"sl":30},{"sl":35},{"sl":40}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [674, 56, 467, 835, 1060, 99, 264, 694, 159, 958], [674, 56, 467, 835, 1060, 99, 264, 694, 159, 958], [], [], [835, 99], [], [835, 99], [], [], [674, 467, 835, 1060, 99, 264, 694, 159], [], [674, 467, 835, 1060, 99, 264, 694, 159], [], []]
