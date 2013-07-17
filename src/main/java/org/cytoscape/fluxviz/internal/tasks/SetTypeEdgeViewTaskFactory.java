package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.model.CyEdge;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.TaskIterator;

public class SetTypeEdgeViewTaskFactory implements EdgeViewTaskFactory {

	String edgeType;
	VisualMappingManager visualMappingManager;
	
	public SetTypeEdgeViewTaskFactory(String edgeType, VisualMappingManager visualMappingManager)
	{
		super();
		this.edgeType = edgeType;
		this.visualMappingManager = visualMappingManager;
	}
	
	@Override
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeEdgeViewTask(edgeView, networkView, edgeType, visualMappingManager));
	}

	@Override
	public boolean isReady(View<CyEdge> arg0, CyNetworkView arg1) {
		return true;
	}

}
