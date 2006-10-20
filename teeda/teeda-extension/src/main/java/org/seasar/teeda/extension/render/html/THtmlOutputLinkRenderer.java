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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.html.HtmlOutputLinkRenderer;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.render.AdjustableOutputDecoder;

/**
 * @author shot
 */
public class THtmlOutputLinkRenderer extends HtmlOutputLinkRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Link";

    private OutputLinkDecoder decoder = new OutputLinkDecoder();

    public void decode(FacesContext context, UIComponent component) {
        AssertionUtil.assertNotNull("context is null.", context);
        AssertionUtil.assertNotNull("component is null.", component);
        final UIForm parentForm = UIComponentUtil.findParentForm(component);
        final String formId = getIdForRender(context, parentForm);
        decoder.setParentFormId(formId);
        decoder.decode(context, component);
    }

    private static class OutputLinkDecoder extends AdjustableOutputDecoder {

        private String parentFormId;

        public void setParentFormId(String parentFormId) {
            this.parentFormId = parentFormId;
        }

        public String getParentFormId() {
            return parentFormId;
        }

        public void decode(FacesContext context, UIComponent component) {
            AssertionUtil.assertNotNull("context is null.", context);
            AssertionUtil.assertNotNull("component is null.", component);
            Map paramMap = getRequestParameterMap(context);
            if (component.getChildCount() <= 0) {
                return;
            }
            if (parentFormId == null) {
                return;
            }
            for (Iterator itr = component.getChildren().iterator(); itr
                    .hasNext();) {
                Object o = itr.next();
                if (o instanceof UIParameter) {
                    UIParameter param = (UIParameter) o;
                    String name = param.getName();
                    String maybeClientId = getMaybeClientId(name);
                    if (maybeClientId == null) {
                        maybeClientId = getClientId(param, context);
                    }
                    if (maybeClientId == null) {
                        continue;
                    }
                    for (Iterator i = paramMap.entrySet().iterator(); i
                            .hasNext();) {
                        Map.Entry entry = (Entry) i.next();
                        String key = (String) entry.getKey();
                        if (key != null && maybeClientId.equals(key)) {
                            Object value = entry.getValue();
                            param.setValue(value);
                        }
                    }
                }
            }
        }

        protected String getMaybeClientId(String name) {
            if (StringUtil.isEmpty(name)) {
                return null;
            }
            return parentFormId + ExtensionConstants.NAME_SEPARATOR + name;
        }

        public void decodeMany(FacesContext context, UIComponent component) {
            AssertionUtil.assertNotNull("context is null.", context);
            AssertionUtil.assertNotNull("component is null.", component);
            Map paramValuesMap = getRequestParameterValuesMap(context);
            if (component.getChildCount() <= 0) {
                return;
            }
            for (Iterator itr = component.getChildren().iterator(); itr
                    .hasNext();) {
                Object o = itr.next();
                if (o instanceof UIParameter) {
                    UIParameter param = (UIParameter) o;
                    String name = param.getName();
                    String maybeClientId = getMaybeClientId(name);
                    if (maybeClientId == null) {
                        maybeClientId = getClientId(param, context);
                    }
                    if (maybeClientId == null) {
                        continue;
                    }
                    for (Iterator i = paramValuesMap.entrySet().iterator(); i
                            .hasNext();) {
                        Map.Entry entry = (Entry) i.next();
                        String key = (String) entry.getKey();
                        String[] value = null;
                        if (key != null && maybeClientId.equals(key)) {
                            if (paramValuesMap.containsKey(name)) {
                                value = (String[]) paramValuesMap.get(name);
                            }
                            if (value != null) {
                                param.setValue(value);
                            } else {
                                param.setValue(new String[0]);
                            }
                        }
                    }
                }
            }
        }

    }
}