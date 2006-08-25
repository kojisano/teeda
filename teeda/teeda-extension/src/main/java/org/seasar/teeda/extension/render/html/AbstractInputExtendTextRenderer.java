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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreComponent;
import javax.faces.internal.UIComponentUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.util.JavaScriptPermissionUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 */
public abstract class AbstractInputExtendTextRenderer extends
        HtmlInputTextRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        UIViewRoot root = context.getViewRoot();
        if (root instanceof ScriptEnhanceUIViewRoot
                && JavaScriptPermissionUtil.isJavaScriptPermitted(context)) {
            encodeInputExtendTextEnd(context, (HtmlInputText) component);
        } else {
            encodeHtmlInputTextEnd(context, (HtmlInputText) component);
        }
    }

    protected void encodeInputExtendTextEnd(FacesContext context,
            HtmlInputText htmlInputText) throws IOException {
        doEncodeEndStart(context, htmlInputText);
        doEncodeEndCustomize(context, htmlInputText);
        doEncodeEndEnd(context, htmlInputText);
    }

    protected void doEncodeEndStart(FacesContext context,
            HtmlInputText htmlInputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        UIViewRoot root = context.getViewRoot();
        ScriptEnhanceUIViewRoot sRoot = (ScriptEnhanceUIViewRoot) root;
        final String scriptKey = getScriptKey();
        if (!sRoot.containsScript(scriptKey)) {
            JavaScriptContext scriptContext = new JavaScriptContext();
            scriptContext.loadScript(scriptKey);
            sRoot.addScript(scriptKey, scriptContext);
            writer.write(sRoot.getAllScripts());
        }
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputText);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputText,
                getIdForRender(context, htmlInputText));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputText.getClientId(context));
        final String value = getValue(context, htmlInputText);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        if (htmlInputText.isDisabled()) {
            renderDisabledAttribute(writer);
        }
    }

    protected abstract void doEncodeEndCustomize(FacesContext context,
            HtmlInputText htmlInputText) throws IOException;

    protected void doEncodeEndEnd(FacesContext context,
            HtmlInputText htmlInputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        renderRemain(htmlInputText, writer);
        writer.endElement(JsfConstants.INPUT_ELEM);
        String scriptKey = getScriptKey();
        markJavaScriptRendererd(context, scriptKey);
    }

    protected abstract String getScriptKey();

    protected String getValue(FacesContext context, UIComponent component) {
        return ValueHolderUtil.getValueForRender(context, component);

    }

    protected void renderRemain(HtmlInputText htmlInputText,
            ResponseWriter writer) throws IOException {
        IgnoreComponent ignore = buildIgnoreComponent();
        Map map = UIComponentUtil.getAllAttributesAndProperties(htmlInputText,
                ignore);
        for (final Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }

    protected IgnoreComponent buildIgnoreComponent() {
        IgnoreComponent ignore = new IgnoreComponent();
        ignore.addIgnoreComponentName(JsfConstants.ID_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.TYPE_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.VALUE_ATTR);
        return ignore;
    };

    protected static String appendSemiColonIfNeed(String property) {
        if (property == null) {
            return "";
        }
        if (property.endsWith(";")) {
            return property;
        }
        return property + ";";
    }

    protected void markJavaScriptRendererd(FacesContext context,
            String scriptKey) {
        context.getExternalContext().getRequestMap().put(scriptKey, scriptKey);
    }

}
