package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;

public class SetTypeEdgeViewTask extends AbstractEdgeViewTask {

	View<CyEdge> edgeView;
	CyNetworkView networkView;
	String type;
	public static String ACTIVATING = "Activating";
	public static String DEACTIVATING = "Deactivating";
	
	public SetTypeEdgeViewTask(View<CyEdge> edgeView, CyNetworkView networkView, String type)
	{
		super(edgeView, networkView);
		this.edgeView = edgeView;
		this.networkView = networkView;
		this.type = type;
	}
	@Override
	public void run(TaskMonitor arg0) throws Exception {

		//update type in default edge table
		CyRow row = ColumnsCreator.DefaultEdgeTable.getRow(edgeView.getSUID());
		row.set(ColumnsCreator.EDGE_TYPE, type);
		
		//update the look of the edge based on type
	}

}
