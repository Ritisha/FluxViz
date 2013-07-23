package org.cytoscape.fluxviz.internal.logic;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

public class EdgeViewHandler {
	
	CyNetworkViewFactory cyNetworkViewFactory;
	VisualMappingManager visualMappingManager;
	
	public EdgeViewHandler(CyNetworkViewFactory cyNetworkViewFactory, VisualMappingManager visualMappingManager)
	{
		this.cyNetworkViewFactory = cyNetworkViewFactory;
		this.visualMappingManager = visualMappingManager;
	}
	
	public void setDefaultEdgeView(CyEdge edge, CyNetwork network)
	{
		CyNetworkView networkView = cyNetworkViewFactory.createNetworkView(network);
		networkView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.ARROW);
		refresh(networkView);
	}
	
	public void setEdgeView(View<CyEdge> edgeView, CyNetworkView networkView, String type)
	{
		if(type.equals(SetTypeEdgeViewTask.ACTIVATING))
		{
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.ARROW);
		}
		else if(type.equals(SetTypeEdgeViewTask.DEACTIVATING))
		{
			edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
		}
		
		refresh(networkView);
	}
	
	public void refresh(CyNetworkView networkView)
	{
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
	}
}
