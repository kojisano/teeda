/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.internal.scope.PageScope;
import javax.faces.internal.scope.RedirectScope;
import javax.faces.internal.scope.SubApplicationScope;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author shot
 */
public class ScopeValueHelperTest extends TeedaTestCase {

    public void testGetSubapplicationScopeValues() throws Exception {
        final Map map = ScopeValueHelper
                .getSubApplicationScopeValues(getFacesContext());
        assertTrue(map == null);
        Map pageScope = SubApplicationScope
                .getOrCreateContext(getFacesContext());
        Map m = new HashMap();
        m.put("aaa", "AAA");
        pageScope.put(PagePersistence.SUBAPPLICATION_SCOPE_KEY, m);

        final Map map2 = ScopeValueHelper
                .getSubApplicationScopeValues(getFacesContext());
        assertEquals("AAA", map2.get("aaa"));
    }

    public void testGetOrCreateSubApplicationScopeValues() throws Exception {
        final Map map = ScopeValueHelper
                .getOrCreateSubApplicationScopeValues(getFacesContext());
        assertTrue(map.isEmpty());
        map.put("aaa", "AAA");
        final Map map2 = ScopeValueHelper
                .getOrCreateSubApplicationScopeValues(getFacesContext());
        assertEquals("AAA", map2.get("aaa"));
    }

    public void testGetRedirectScopeValues() throws Exception {
        final Map map = ScopeValueHelper
                .getRedirectScopeValues(getFacesContext());
        assertTrue(map == null);
        Map pageScope = RedirectScope.getOrCreateContext(getFacesContext());
        Map m = new HashMap();
        m.put("aaa", "AAA");
        pageScope.put(PagePersistence.REDIRECT_SCOPE_KEY, m);

        final Map map2 = ScopeValueHelper
                .getRedirectScopeValues(getFacesContext());
        assertEquals("AAA", map2.get("aaa"));
    }

    public void testGetOrCreateRedirectScopeValues() throws Exception {
        final Map map = ScopeValueHelper
                .getOrCreateRedirectScopeValues(getFacesContext());
        assertTrue(map.isEmpty());
        map.put("aaa", "AAA");
        final Map map2 = ScopeValueHelper
                .getOrCreateRedirectScopeValues(getFacesContext());
        assertEquals("AAA", map2.get("aaa"));
    }

    public void testGetPageScopeValues() throws Exception {
        final Map map = ScopeValueHelper.getPageScopeValues(getFacesContext());
        assertTrue(map == null);
        Map pageScope = PageScope.getOrCreateContext(getFacesContext());
        Map m = new HashMap();
        m.put("aaa", "AAA");
        pageScope.put(PagePersistence.PAGE_SCOPE_KEY, m);

        final Map map2 = ScopeValueHelper.getPageScopeValues(getFacesContext());
        assertEquals("AAA", map2.get("aaa"));
    }

    public void testGetOrCreatePageScopeValues() throws Exception {
        final Map map = ScopeValueHelper
                .getOrCreatePageScopeValues(getFacesContext());
        assertTrue(map.isEmpty());
        map.put("aaa", "AAA");
        final Map map2 = ScopeValueHelper
                .getOrCreatePageScopeValues(getFacesContext());
        assertEquals("AAA", map2.get("aaa"));
    }

}