package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class StartFlowNetworkViewTaskFactory extends
		AbstractNetworkViewTaskFactory {

	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	
	public StartFlowNetworkViewTaskFactory(NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler)
	{
		super();
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		return new TaskIterator(new StartFlowNetworkViewTask(networkView, nodeViewHandler, edgeViewHandler));
	}

}
