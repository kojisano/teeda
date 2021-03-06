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
package org.seasar.teeda.core.util;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author shot
 */
public class PropertyDescUtil {

    public static Class getProperty(Class clazz, String propertyName) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        PropertyDesc propDesc = beanDesc.getPropertyDesc(propertyName);
        return propDesc.getPropertyType();
    }

    public static void setValue(Object target, String propertyName, String value) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(target.getClass());
        PropertyDesc propertyDesc = beanDesc.getPropertyDesc(propertyName);
        if (propertyDesc.isWritable()) {
            propertyDesc.setValue(target, value);
        }
    }
}
