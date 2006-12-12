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
package org.seasar.teeda.extension.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.ComponentUtil_;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.internal.ComponentStates;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.event.ToggleEvent;
import org.seasar.teeda.extension.event.TreeEventWrapper;

/**
 * @author shot
 */
public class UITreeData extends UIInput implements NamingContainer {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Tree";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Tree";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Tree";

    private TreeModel model;

    private String nodeId;

    private TreeNode node;

    private Object value;

    private String var;

    private ComponentStates states = new ComponentStates();

    public UITreeData() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = var;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        var = (String) values[1];
    }

    public void encodeBegin(FacesContext context) throws IOException {
        states.clear();
        model = null;
        super.encodeBegin(context);
    }

    public void encodeEnd(FacesContext context) throws IOException {
        super.encodeEnd(context);
        TreeModel model = getDataModel();
        if (model == null) {
            throw new IllegalStateException();
        }
    }

    public void queueEvent(FacesEvent event) {
        super.queueEvent(new TreeEventWrapper(event, getNodeId(), this));
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (event instanceof TreeEventWrapper) {
            TreeEventWrapper childEvent = (TreeEventWrapper) event;
            String currNodeId = getNodeId();
            setNodeId(childEvent.getOriginalNodeId());
            FacesEvent originalEvent = childEvent.getOriginalEvent();
            originalEvent.getComponent().broadcast(originalEvent);
            setNodeId(currNodeId);
        } else if (event instanceof ToggleEvent) {
            ToggleEvent toggleEvent = (ToggleEvent) event;
            String currentNodeId = getNodeId();
            setNodeId(toggleEvent.getNodeId());
            toggleExpanded();
            setNodeId(currentNodeId);
        } else {
            super.broadcast(event);
        }
    }

    public void processDecodes(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        model = null;
        states.clear();
        setNodeId(null);
        decode(context);
        processNodes(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        processNodes(context, PhaseId.PROCESS_VALIDATIONS);
        setNodeId(null);
    }

    public void processUpdates(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        //processNodes(context, PhaseId.UPDATE_MODEL_VALUES);
        setNodeId(null);
    }

    public void updateModel(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isValid() || !isLocalValueSet()) {
            return;
        }
        final ValueBinding valueBinding = getValueBinding("value");
        if (valueBinding == null) {
            return;
        }
        try {
            valueBinding.setValue(context, getValue());
        } catch (RuntimeException e) {
            Object[] args = { getId() };
            context.getExternalContext().log(e.getMessage(), e);
            FacesMessageUtil.addErrorMessage(context, this,
                    CONVERSION_MESSAGE_ID, args);
            setValid(false);
        }
    }

    public String getClientId(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        final String ownClientId = getOwnClientId(context);
        if (nodeId != null) {
            return ownClientId + NamingContainer.SEPARATOR_CHAR + nodeId;
        } else {
            return ownClientId;
        }
    }

    public String getOwnClientId(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        return super.getClientId(context);
    }
    
    public void setValueBinding(String name, ValueBinding binding) {
        if ("value".equals(name)) {
            model = null;
        } else if ("nodeVar".equals(name) || "nodeId".equals(name)
                || "treeVar".equals(name)) {
            throw new IllegalArgumentException("name " + name);
        }
        super.setValueBinding(name, binding);
    }

    public void setValue(Object value) {
        model = null;
        this.value = value;
        setLocalValueSet(true);
    }

    public Object getValue() {
        if (value != null) {
            return value;
        }
        ValueBinding vb = getValueBinding("value");
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public TreeNode getNode() {
        return node;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        FacesContext context = getFacesContext();
        saveDescendantState(context);
        this.nodeId = nodeId;
        TreeModel model = getDataModel();
        if (model == null) {
            return;
        }
        try {
            node = model.getNodeById(nodeId);
        } catch (IndexOutOfBoundsException aob) {
            //TODO fix id
            FacesMessage message = FacesMessageUtil.getMessage(context,
                    "missing_node_message_id_here", new String[] { nodeId });
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            context.addMessage(getId(), message);
        }
        restoreDescendantState(context);
        if (var != null) {
            Map requestMap = context.getExternalContext().getRequestMap();
            if (nodeId == null) {
                requestMap.remove(var);
            } else {
                requestMap.put(var, getNode());
            }
        }
    }

    public String[] getPathInformation(String nodeId) {
        return getDataModel().getPathInformation(nodeId);
    }

    public boolean isLastChild(String nodeId) {
        return getDataModel().isLastChild(nodeId);
    }

    public TreeModel getDataModel() {
        if (model != null) {
            return model;
        }
        Object value = getValue();
        if (value != null) {
            if (value instanceof TreeModel) {
                model = (TreeModel) value;
            } else if (value instanceof TreeNode) {
                model = wrapTreeNode((TreeNode) value);
            } else {
                throw new IllegalStateException(
                        "Value must be a TreeModel or TreeNode");
            }
        }
        return model;
    }

    protected TreeModel wrapTreeNode(TreeNode node) {
        return new TreeModelImpl(node);
    }

    public void expandAll() {
        toggleAll(true);
    }

    public void collapseAll() {
        toggleAll(false);
    }

    private void toggleAll(boolean expanded) {
        TreeWalker walker = getDataModel().getTreeWalker();
        walker.walkBegin(this);
        final TreeModel model = getDataModel();
        while (walker.next()) {
            String id = getNodeId();
            if ((expanded && !model.isNodeExpanded(id))
                    || (!expanded && model.isNodeExpanded(id))) {
                model.toggleExpanded(id);
            }
        }
    }

    protected void processNodes(FacesContext context, PhaseId phaseId) {
        UIComponent facet = null;
        TreeModel dataModel = getDataModel();
        if (dataModel == null) {
            return;
        }
        TreeWalker walker = dataModel.getTreeWalker();
        walker.walkBegin(this);
        while (walker.next()) {
            TreeNode node = getNode();
            String type = node.getType();
            facet = getFacet(type);
            if (facet == null) {
                continue;
            }
            ComponentUtil_.processAppropriatePhaseAction(context, facet,
                    phaseId);
        }
    }

    public void restoreDescendantState(final FacesContext context) {
        states.restoreDescendantState(context, this);
    }

    public void saveDescendantState(final FacesContext context) {
        states.saveDescendantComponentStates(context, this);
    }

    public void toggleExpanded() {
        getDataModel().toggleExpanded(getNodeId());
    }

    public boolean isNodeExpanded() {
        return getDataModel().isNodeExpanded(getNodeId());
    }

}
