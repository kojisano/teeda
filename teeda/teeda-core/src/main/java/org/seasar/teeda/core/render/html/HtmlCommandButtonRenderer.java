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
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlCommandButtonRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Command";

    public static final String RENDERER_TYPE = "javax.faces.Button";

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlCommandButtonEnd(context, (HtmlCommandButton) component);
    }

    protected void encodeHtmlCommandButtonEnd(FacesContext context,
            HtmlCommandButton htmlCommandButton) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlCommandButton);
        final String image = htmlCommandButton.getImage();
        final boolean isImageType = StringUtil.isNotBlank(image);
        String type;
        if (isImageType) {
            type = JsfConstants.IMAGE_VALUE;
        } else {
            type = htmlCommandButton.getType();
        }
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR, type);

        RendererUtil.renderIdAttributeIfNecessary(writer, htmlCommandButton,
                getIdForRender(context, htmlCommandButton));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlCommandButton.getClientId(context));

        if (isImageType) {
            RendererUtil.renderAttribute(writer, JsfConstants.SRC_ATTR, image,
                    JsfConstants.IMAGE_ATTR);
        } else {
            RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR,
                    htmlCommandButton.getValue());
        }
        renderAttributes(htmlCommandButton, writer);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeHtmlCommandButton(context, (HtmlCommandButton) component);
    }

    protected void decodeHtmlCommandButton(FacesContext context,
            HtmlCommandButton htmlCommandButton) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String clientId = htmlCommandButton.getClientId(context);
        if (paramMap.containsKey(clientId)
                || paramMap.containsKey(clientId + ".x")
                || paramMap.containsKey(clientId + ".y")) {
            if (JsfConstants.RESET_VALUE.equalsIgnoreCase(htmlCommandButton
                    .getType())) {
            } else {
                htmlCommandButton
                        .queueEvent(new ActionEvent(htmlCommandButton));
            }
        }
    }
}
