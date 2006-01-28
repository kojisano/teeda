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

import javax.servlet.http.HttpSession;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;


public class TestHttpSessionMap extends S2TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestHttpSessionMap.class);
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
     * Constructor for TestHttpSessionMap.
     * @param arg0
     */
    public TestHttpSessionMap(String arg0) {
        super(arg0);
    }

    public void testHttpSessionMap(){
        MockHttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("aaa", "bbb");
        HttpSessionMap map = new HttpSessionMap(request);
        assertEquals("bbb", map.getAttribute("aaa"));
        assertEquals("aaa", map.getAttributeNames().nextElement());
        
        map.removeAttribute("aaa");
        assertNull(map.getAttribute("aaa"));
        
        map.setAttribute("bbb","ccc");
        assertEquals("ccc", map.getAttribute("bbb"));
        
        session.setAttribute("zzz", "ZZZ");
        assertEquals("ZZZ", map.getAttribute("zzz"));
        
        MockHttpServletRequest request2 = 
            new MockHttpServletRequestImpl(getServletContext(), "/");
        HttpSessionMap map2 = new HttpSessionMap(request2);
        assertNull(map2.getAttribute("aaa"));
        map2.setAttribute("aaa", "AAA");
        assertEquals("AAA", map2.getAttribute("aaa"));
    }
}
