/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;

import org.seasar.teeda.extension.component.UITitle;

/**
 * @author shot
 */
public class TTitleTag extends TOutputLabelTag {

    private String templateValue;

    public TTitleTag() {
    }

    public String getComponentType() {
        return UITitle.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return UITitle.DEFAULT_RENDERER_TYPE;
    }

    public void release() {
        super.release();
        templateValue = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        setComponentProperty(component, "templateValue", templateValue);
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

}
