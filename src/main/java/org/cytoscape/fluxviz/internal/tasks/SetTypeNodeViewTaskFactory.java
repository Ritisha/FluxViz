package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetTypeNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	String nodeType;
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	
	/**
	 * Calls the SetTypeNodeViewTask
	 * @param type
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public SetTypeNodeViewTaskFactory(String type, NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler)
	{
		super();
		nodeType = type;
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeNodeViewTask(nodeView, networkView, nodeType, nodeViewHandler, edgeViewHandler));
	}
}
