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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;

/**
 * @author manhole
 */
public class HtmlSelectBooleanCheckboxRenderer extends AbstractHtmlRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlSelectBooleanCheckboxEnd(context,
                (HtmlSelectBooleanCheckbox) component);
    }

    protected void encodeHtmlSelectBooleanCheckboxEnd(FacesContext context,
            HtmlSelectBooleanCheckbox htmlSelectBooleanCheckbox)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlSelectBooleanCheckbox);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.CHECKBOX_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer,
                htmlSelectBooleanCheckbox, getIdForRender(context,
                        htmlSelectBooleanCheckbox));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlSelectBooleanCheckbox.getClientId(context));

        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                Boolean.TRUE);
        String value = ValueHolderUtil.getValueForRender(context,
                htmlSelectBooleanCheckbox);
        if (isChecked(value)) {
            RendererUtil.renderCheckedAttribute(writer);
        }
        if (htmlSelectBooleanCheckbox.isDisabled()) {
            RendererUtil.renderDisabledAttribute(writer);
        }
        RendererUtil.renderAttributes(writer, htmlSelectBooleanCheckbox,
                JsfConstants.INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    private boolean isChecked(String value) {
        return "true".equalsIgnoreCase(value);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlSelectBooleanCheckbox(context,
                (HtmlSelectBooleanCheckbox) component);
    }

    protected void decodeHtmlSelectBooleanCheckbox(FacesContext context,
            HtmlSelectBooleanCheckbox htmlSelectBooleanCheckbox) {
        if (isChecked(context, htmlSelectBooleanCheckbox)) {
            htmlSelectBooleanCheckbox
                    .setSubmittedValue(Boolean.TRUE.toString());
        } else {
            htmlSelectBooleanCheckbox.setSubmittedValue(Boolean.FALSE
                    .toString());
        }
    }

    private boolean isChecked(FacesContext context, UIComponent component) {
        Map reqParam = context.getExternalContext().getRequestParameterMap();
        String clientId = component.getClientId(context);
        if (reqParam.containsKey(clientId)) {
            String value = (String) reqParam.get(clientId);
            return "on".equalsIgnoreCase(value)
                    || "yes".equalsIgnoreCase(value) || isChecked(value);
        }
        return false;
    }

}
