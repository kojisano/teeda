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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.FieldUtil;

/**
 * @author shot
 * @author higa
 */
public class ConstantValidatorAnnotationHandler extends
        AbstractValidatorAnnotationHandler {

    public void registerValidators(String componentName) {
        removeValidators(componentName);
        S2Container container = getContainer();
        NamingConvention namingConvention = (NamingConvention) container
                .getComponent(NamingConvention.class);
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class componentClass = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        Field[] fields = componentClass.getFields();
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            processProperty(container, componentClass, componentName,
                    namingConvention, propertyDesc, fields);
        }
    }

    protected void processProperty(S2Container container, Class componentClass,
            String componentName, NamingConvention namingConvention,
            PropertyDesc propertyDesc, Field[] fields) {
        final String propertyName = propertyDesc.getPropertyName();
        final String annotationPrefix = propertyName + "_";
        for (int i = 0; i < fields.length; ++i) {
            final Field annotation = fields[i];
            final String annotationName = annotation.getName();
            boolean isConstantAnnotation = ConstantAnnotationUtil
                    .isConstantAnnotation(annotation);
            if (!isConstantAnnotation ||
                    !annotationName.startsWith(annotationPrefix) ||
                    !annotationName.endsWith(namingConvention
                            .getValidatorSuffix())) {
                continue;
            }
            final String validatorName = annotationName
                    .substring(annotationPrefix.length());
            if (!container.hasComponentDef(validatorName)) {
                continue;
            }
            final String s = FieldUtil.getString(annotation, null);
            final Map properties = ConstantAnnotationUtil
                    .convertExpressionToMap(s);
            registerValidator(componentName, propertyName, validatorName,
                    properties);
        }
    }

}
