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
package javax.faces.webapp;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.PageContext;

class PageContextOutWriter_ extends Writer {

    private PageContext pageContext_;

    public PageContextOutWriter_(PageContext pageContext) {
        pageContext_ = pageContext;
    }

    public void close() throws IOException {
        pageContext_.getOut().close();
    }

    public void flush() throws IOException {
        pageContext_.getOut().flush();
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        pageContext_.getOut().write(cbuf, off, len);
    }

    public void write(int c) throws IOException {
        pageContext_.getOut().write(c);
    }

    public void write(char cbuf[]) throws IOException {
        pageContext_.getOut().write(cbuf);
    }

    public void write(String str) throws IOException {
        pageContext_.getOut().write(str);
    }

    public void write(String str, int off, int len) throws IOException {
        pageContext_.getOut().write(str, off, len);
    }
}
