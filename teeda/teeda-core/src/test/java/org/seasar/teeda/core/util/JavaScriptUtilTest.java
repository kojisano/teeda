/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.util;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class JavaScriptUtilTest extends TestCase {

    public void testGetClearHiddenCommandFormParamsFunctionName1()
            throws Exception {
        assertEquals("clear__5Fid0", JavaScriptUtil
                .getClearHiddenCommandFormParamsFunctionName("_id0"));
    }

    public void testGetClearHiddenCommandFormParamsFunctionName2()
            throws Exception {
        assertEquals("clear_form", JavaScriptUtil
                .getClearHiddenCommandFormParamsFunctionName("form"));
    }

}