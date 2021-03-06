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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.LoopIterator;
import org.seasar.teeda.core.util.RendererUtil;

/**
 * @author manhole
 */
public class HtmlPanelGridRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Panel";

    public static final String RENDERER_TYPE = "javax.faces.Grid";

    private final IgnoreAttribute ignoreComponent = new IgnoreAttribute();
    {
        ignoreComponent.addAttributeName(JsfConstants.ID_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.HEADER_CLASS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.FOOTER_CLASS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.COLUMNS_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.COLUMN_CLASSES_ATTR);
        ignoreComponent.addAttributeName(JsfConstants.ROW_CLASSES_ATTR);
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlPanelGridBegin(context, (HtmlPanelGrid) component);
    }

    protected void encodeHtmlPanelGridBegin(FacesContext context,
            HtmlPanelGrid htmlPanelGrid) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.TABLE_ELEM, htmlPanelGrid);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlPanelGrid,
                getIdForRender(context, htmlPanelGrid));
        renderRemainAttributes(htmlPanelGrid, writer, ignoreComponent);

        // thead
        {
            UIComponent tableHeader = toNullIfNotRendered(htmlPanelGrid
                    .getFacet("header"));
            if (tableHeader != null) {
                writer.startElement(JsfConstants.THEAD_ELEM, tableHeader);

                writer.startElement(JsfConstants.TR_ELEM, tableHeader);
                writer.startElement(JsfConstants.TH_ELEM, tableHeader);
                RendererUtil.renderAttribute(writer, JsfConstants.COLSPAN_ATTR,
                        new Integer(getColumns(htmlPanelGrid)));
                writer.writeAttribute(JsfConstants.SCOPE_ATTR,
                        JsfConstants.COLGROUP_VALUE, null);
                RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                        htmlPanelGrid.getHeaderClass(),
                        JsfConstants.HEADER_CLASS_ATTR);
                encodeComponent(context, tableHeader);
                writer.endElement(JsfConstants.TH_ELEM);
                closeTr(writer);

                writer.endElement(JsfConstants.THEAD_ELEM);
            }
        }
        // tfoot
        {
            UIComponent tableFooter = toNullIfNotRendered(htmlPanelGrid
                    .getFacet("footer"));
            if (tableFooter != null) {
                writer.startElement(JsfConstants.TFOOT_ELEM, tableFooter);

                writer.startElement(JsfConstants.TR_ELEM, tableFooter);
                writer.startElement(JsfConstants.TD_ELEM, tableFooter);
                RendererUtil.renderAttribute(writer, JsfConstants.COLSPAN_ATTR,
                        new Integer(getColumns(htmlPanelGrid)));
                RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                        htmlPanelGrid.getFooterClass(),
                        JsfConstants.FOOTER_CLASS_ATTR);
                encodeComponent(context, tableFooter);
                writer.endElement(JsfConstants.TD_ELEM);
                closeTr(writer);

                writer.endElement(JsfConstants.TFOOT_ELEM);
            }
        }
    }

    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlPanelGridChildren(context, (HtmlPanelGrid) component);
    }

    protected void encodeHtmlPanelGridChildren(FacesContext context,
            HtmlPanelGrid htmlPanelGrid) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.TBODY_ELEM, htmlPanelGrid);

        LoopIterator rowClasses = toStyleLoopIterator(htmlPanelGrid
                .getRowClasses());
        LoopIterator columnClasses = toStyleLoopIterator(htmlPanelGrid
                .getColumnClasses());

        final int maxColumns = getColumns(htmlPanelGrid);
        final int initialPosition = 0;
        int position = initialPosition;
        boolean trTagOpening = false;
        for (Iterator itChild = getRenderedChildrenIterator(htmlPanelGrid); itChild
                .hasNext();) {
            UIComponent child = (UIComponent) itChild.next();
            if (position == initialPosition) {
                openTr(writer, htmlPanelGrid, rowClasses, columnClasses);
                trTagOpening = true;
            }
            encodeBodyRowColumn(context, child, writer, columnClasses);
            position++;
            if (maxColumns <= position) {
                position = initialPosition;
                closeTr(writer);
                trTagOpening = false;
            }
        }
        if (trTagOpening) {
            closeTr(writer);
            trTagOpening = false;
        }

        writer.endElement(JsfConstants.TBODY_ELEM);
    }

    private void openTr(ResponseWriter writer, HtmlPanelGrid htmlPanelGrid,
            LoopIterator rowClasses, LoopIterator columnClasses)
            throws IOException {
        writer.startElement(JsfConstants.TR_ELEM, htmlPanelGrid);
        if (rowClasses.hasNext()) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    rowClasses.next(), JsfConstants.ROW_CLASSES_ATTR);
        }
        columnClasses.reset();
    }

    private void closeTr(ResponseWriter writer) throws IOException {
        writer.endElement(JsfConstants.TR_ELEM);
    }

    private void encodeBodyRowColumn(FacesContext context,
            UIComponent component, ResponseWriter writer, Iterator columnClasses)
            throws IOException {
        writer.startElement(JsfConstants.TD_ELEM, component);
        if (columnClasses.hasNext()) {
            RendererUtil.renderAttribute(writer, JsfConstants.CLASS_ATTR,
                    columnClasses.next(), JsfConstants.COLUMN_CLASSES_ATTR);
        }
        encodeComponent(context, component);
        writer.endElement(JsfConstants.TD_ELEM);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlPanelGridEnd(context, (HtmlPanelGrid) component);
    }

    protected void encodeHtmlPanelGridEnd(FacesContext context,
            HtmlPanelGrid htmlPanelGrid) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.TABLE_ELEM);
    }

    public boolean getRendersChildren() {
        return true;
    }

    private int getColumns(HtmlPanelGrid htmlPanelGrid) {
        int columns = htmlPanelGrid.getColumns();
        if (columns <= 0) {
            columns = 1;
        }
        return columns;
    }

}
