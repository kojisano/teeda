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
package org.seasar.teeda.core.context.servlet;

import java.util.Enumeration;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;


public class TestServletRequestParameterMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestServletRequestParameterMap.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for TestServletRequestParameterMap.
     * @param arg0
     */
    public TestServletRequestParameterMap(String arg0) {
        super(arg0);
    }

    public void testServletRequestHeaderMap(){
        MockHttpServletRequest request = getRequest();
        request.setParameter("a", "A");
        ServletRequestParameterMap map = new ServletRequestParameterMap(request);
        assertEquals("A", map.getAttribute("a"));
        Enumeration e = map.getAttributeNames();
        assertNotNull(e);
        assertEquals("a", e.nextElement());
    }
}
