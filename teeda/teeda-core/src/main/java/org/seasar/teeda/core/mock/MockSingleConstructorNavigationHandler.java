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
package org.seasar.teeda.core.mock;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public class MockSingleConstructorNavigationHandler extends NavigationHandler {

    private NavigationHandler originalHandler_;

    public MockSingleConstructorNavigationHandler(NavigationHandler handler) {
        originalHandler_ = handler;
    }

    public void handleNavigation(FacesContext context, String fromAction,
            String outcome) {
        originalHandler_.handleNavigation(context, fromAction, outcome);
    }

    public NavigationHandler getOriginal() {
        return originalHandler_;
    }
}
