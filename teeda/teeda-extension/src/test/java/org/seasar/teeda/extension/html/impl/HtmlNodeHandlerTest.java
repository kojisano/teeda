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
package org.seasar.teeda.extension.html.impl;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.html.DocumentNode;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.HtmlParser;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 * @author shot
 */
public class HtmlNodeHandlerTest extends TeedaExtensionTestCase {

    public void testParse() throws Exception {
        String path = convertPath("HtmlNodeHandler.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals("1", "<html><body>Hello</body></html>", root.toString());
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        assertEquals("2", 1, n.getChildSize());
        ElementNode body = (ElementNode) n.getChild(0);
        assertEquals("3", "<body>Hello</body>", body.toString());
    }

    public void testParseUsingId() throws Exception {
        String path = convertPath("HtmlNodeHandler2.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals("1",
                "<html><body>Hello<span id=\"aaa\" />World</body></html>", root
                        .toString());
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        ElementNode body = (ElementNode) n.getChild(0);
        assertEquals("2", 3, body.getChildSize());
        TextNodeImpl t = (TextNodeImpl) body.getChild(0);
        assertEquals("3", "Hello", t.getValue());
        ElementNodeImpl n2 = (ElementNodeImpl) body.getChild(1);
        assertEquals("4", "<span id=\"aaa\" />", n2.toString());
        TextNodeImpl t2 = (TextNodeImpl) body.getChild(2);
        assertEquals("5", "World", t2.getValue());
    }

    public void testParseUsingIdNest() throws Exception {
        String path = convertPath("HtmlNodeHandler3.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals(
                "1",
                "<html><div id=\"aaa\">Hello<span id=\"bbb\" />World</div></html>",
                root.toString());
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        assertEquals("2", 1, n.getChildSize());
        ElementNodeImpl n2 = (ElementNodeImpl) n.getChild(0);
        assertEquals("3", 3, n2.getChildSize());
        TextNodeImpl t = (TextNodeImpl) n2.getChild(0);
        assertEquals("4", "Hello", t.getValue());
        ElementNodeImpl n3 = (ElementNodeImpl) n2.getChild(1);
        assertEquals("5", "<span id=\"bbb\" />", n3.toString());
        TextNodeImpl t2 = (TextNodeImpl) n2.getChild(2);
        assertEquals("6", "World", t2.getValue());
    }

    public void testParseIfDtdIsXhtmlStrict() throws Exception {
        String path = convertPath("HtmlNodeHandler4.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals(
                "1",
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\"><body><div><span value=\"Hello\"></span></div></body></html>",
                root.toString());
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        assertEquals("2", 1, n.getChildSize());
        ElementNode body = (ElementNode) n.getChild(0);
        TextNodeImpl t = (TextNodeImpl) body.getChild(0);
        assertEquals("3", "<div><span value=\"Hello\"></span></div>", t
                .getValue());
    }

    public void testParseSelect() throws Exception {
        String path = convertPath("select.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        assertEquals(
                "1",
                root.toString(),
                "<html><body><select id=\"ccc\"><option value=\"0\">aaa</option></select></body></html>");
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        assertTrue(n.getChildSize() == 1);
        ElementNode body = (ElementNode) n.getChild(0);
        assertTrue(body.getChildSize() == 1);
    }

    public void testParseInputRadio() throws Exception {
        String path = convertPath("inputRadio.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        System.out.println(root.toString());
        assertEquals("1", root.toString(),
                "<html><body><input type=\"radio\" name=\"aaa\" /></body></html>");
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        assertTrue(n.getChildSize() == 1);
        ElementNodeImpl body = (ElementNodeImpl) n.getChild(0);
        ElementNode child = (ElementNode) body.getChild(0);
        assertEquals("input", child.getTagName());
        assertEquals("radio", child.getProperty("type"));
    }

    public void testParseInputCheckbox() throws Exception {
        String path = convertPath("inputCheckbox.xhtml");
        HtmlParser parser = getHtmlParser();
        HtmlNode root = parser.parse(ResourceUtil.getResourceAsStream(path));
        System.out.println(root.toString());
        assertEquals("1", root.toString(),
                "<html><body><input type=\"checkbox\" name=\"aaa\" /></body></html>");
        DocumentNode docRoot = (DocumentNode) root;
        ElementNodeImpl n = (ElementNodeImpl) docRoot.getChild(0);
        assertTrue(n.getChildSize() == 1);
        ElementNode child = (ElementNode) n.getChild(0);
        assertEquals("body", child.getTagName());
        ElementNode gChild = (ElementNode) child.getChild(0);
        assertEquals("checkbox", gChild.getProperty("type"));
    }

}