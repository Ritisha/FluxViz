package org.cytoscape.fluxviz.internal.logic;

import java.util.Collection;
import java.util.HashSet;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

public class EdgeViewHandler {
	
	CyNetworkViewManager cyNetworkViewManager;
	VisualMappingManager visualMappingManager;
	
	public EdgeViewHandler(CyNetworkViewManager cyNetworkViewManager, VisualMappingManager visualMappingManager)
	{
		this.cyNetworkViewManager = cyNetworkViewManager;
		this.visualMappingManager = visualMappingManager;
	}
	
	public void setDefaultEdgeView(CyEdge edge, CyNetwork network)
	{
		Collection<CyNetworkView> networkViewCollection = new HashSet<CyNetworkView>();
		networkViewCollection = cyNetworkViewManager.getNetworkViews(network);
		CyNetworkView networkView = networkViewCollection.iterator().next();
		System.out.println(networkView.getSUID());
		networkView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.ARROW);
		refresh(networkView);
	}
	
	public void setEdgeView(CyEdge edge, CyNetwork network, String type)
	{
		Collection<CyNetworkView> networkViewCollection = new HashSet<CyNetworkView>();
		networkViewCollection = cyNetworkViewManager.getNetworkViews(network);
		CyNetworkView networkView = networkViewCollection.iterator().next();
		if(type.equals(SetTypeEdgeViewTask.ACTIVATING))
		{
			networkView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.ARROW);
		}
		else if(type.equals(SetTypeEdgeViewTask.DEACTIVATING))
		{
			networkView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
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
