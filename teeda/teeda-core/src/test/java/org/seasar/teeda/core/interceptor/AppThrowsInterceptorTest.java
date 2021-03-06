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
package org.seasar.teeda.core.interceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.faces.application.FacesMessage;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.exception.SRuntimeException;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author higa
 */
public class AppThrowsInterceptorTest extends TeedaTestCase {

    public void testHandleThrowable() throws Throwable {
        AppThrowsInterceptor interceptor = new AppThrowsInterceptor();
        SRuntimeException ex = new SRuntimeException("ETDA0001");
        interceptor.handleThrowable(ex, new MethodInvocation() {

            public Method getMethod() {
                return null;
            }

            public Object[] getArguments() {
                return null;
            }

            public AccessibleObject getStaticPart() {
                return null;
            }

            public Object getThis() {
                return null;
            }

            public Object proceed() throws Throwable {
                return null;
            }

        });
        MockFacesContext facesContext = getFacesContext();
        Iterator messages = facesContext.getMessages(null);
        assertNotNull(messages);
        FacesMessage fm = (FacesMessage) messages.next();
        System.out.println(fm.getDetail());
        assertNotNull(fm.getDetail());
    }
}
