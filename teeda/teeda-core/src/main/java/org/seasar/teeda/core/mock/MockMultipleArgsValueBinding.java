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
package org.seasar.teeda.core.mock;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.ELParser;

public class MockMultipleArgsValueBinding extends ValueBinding{

    private Application app_;
    private String expression_;
    private ELParser parser_;
    public MockMultipleArgsValueBinding(Application app, String expression, ELParser parser){
        app_ = app;
        expression_ = expression;
        parser_ = parser;
    }
    
    public Object getValue(FacesContext context) 
        throws EvaluationException, PropertyNotFoundException {
        return null;
    }

    public void setValue(FacesContext context, Object obj)
        throws EvaluationException, PropertyNotFoundException {
    }

    public boolean isReadOnly(FacesContext context) 
        throws EvaluationException, PropertyNotFoundException {
        return false;
    }

    public Class getType(FacesContext context) 
        throws EvaluationException, PropertyNotFoundException {
        return null;
    }
    
}
