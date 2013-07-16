package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.model.CyEdge;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetTypeEdgeViewTaskFactory implements EdgeViewTaskFactory {

	String edgeType;
	
	public SetTypeEdgeViewTaskFactory(String edgeType)
	{
		super();
		this.edgeType = edgeType;
	}
	
	@Override
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeEdgeViewTask(edgeView, networkView, edgeType));
	}

	@Override
	public boolean isReady(View<CyEdge> arg0, CyNetworkView arg1) {
		return true;
	}

}
