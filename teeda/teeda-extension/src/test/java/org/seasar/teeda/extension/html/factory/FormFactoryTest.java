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
package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.FormTag;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.PageDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;

/**
 * @author higa
 */
public class FormFactoryTest extends TeedaTestCase {

    public void testIsMatch() throws Exception {
        FormFactory factory = new FormFactory();
        Map properties = new HashMap();
        properties.put("id", "hogeForm");
        ElementNode elementNode = new ElementNodeImpl("form", properties);
        assertTrue(factory.isMatch(elementNode, null, null));
        ElementNode elementNode2 = new ElementNodeImpl("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
        ElementNode elementNode3 = new ElementNodeImpl("form", new HashMap());
        assertFalse(factory.isMatch(elementNode3, null, null));
    }
    
    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("form");
        tagElement.setTagClass(FormTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);
        FormFactory factory = new FormFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "fooForm");
        ElementNode elementNode = new ElementNodeImpl("form", properties);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        
        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode, pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", FormTag.class, processor.getTagClass());
    }
}
