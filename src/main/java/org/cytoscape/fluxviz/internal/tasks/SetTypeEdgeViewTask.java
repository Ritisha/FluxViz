package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.values.ArrowShape;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskMonitor;

public class SetTypeEdgeViewTask extends AbstractEdgeViewTask {

	View<CyEdge> edgeView;
	CyNetworkView networkView;
	String type;
	VisualMappingManager visualMappingManager;
	public static String ACTIVATING = "Activating";
	public static String DEACTIVATING = "Deactivating";
	
	public SetTypeEdgeViewTask(View<CyEdge> edgeView, CyNetworkView networkView, String type, VisualMappingManager visualMappingManager)
	{
		super(edgeView, networkView);
		this.edgeView = edgeView;
		this.networkView = networkView;
		this.type = type;
		this.visualMappingManager = visualMappingManager;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {

		//update type in default edge table
		CyRow row = ColumnsCreator.DefaultEdgeTable.getRow(edgeView.getSUID());
		row.set(ColumnsCreator.EDGE_TYPE, type);
		
		//update the look of the edge based on type
		if(type.equals(ACTIVATING))
		{
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.ARROW);
		}
		else
		{
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
		}
		
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
	}

}
