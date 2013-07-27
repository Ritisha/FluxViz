package org.cytoscape.fluxviz.internal.logic;

import java.util.Collection;
import java.util.HashSet;

import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

public class NodeViewHandler {
	
	CyNetworkViewManager cyNetworkViewManager;
	VisualMappingManager visualMappingManager;
	
	public NodeViewHandler(CyNetworkViewManager cyNetworkViewManager, VisualMappingManager visualMappingManager)
	{
		this.cyNetworkViewManager = cyNetworkViewManager;
		this.visualMappingManager = visualMappingManager;
	}
	
	public void setDefaultNodeView(CyNode node, CyNetwork network)
	{
		Collection<CyNetworkView> networkViewCollection = new HashSet<CyNetworkView>();
		networkViewCollection = cyNetworkViewManager.getNetworkViews(network);
		CyNetworkView networkView = networkViewCollection.iterator().next();
		networkView.getNodeView(node).setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
		refresh(networkView);
	}
	
	public void setNodeView(View<CyNode> nodeView, CyNetworkView networkView, String type)
	{
		if(type.equals(SetTypeNodeViewTask.KINASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
		}
		else if(type.equals(SetTypeNodeViewTask.GTPASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.HEXAGON);
		}
		else if(type.equals(SetTypeNodeViewTask.MOLECULES))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.TRIANGLE);
		}
		else if(type.equals(SetTypeNodeViewTask.RECEPTOR) )
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.PARALLELOGRAM);
		}
		else if(type.equals(SetTypeNodeViewTask.RECEPTOR_T_KINASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.PARALLELOGRAM);
		}
		else if(type.equals(SetTypeNodeViewTask.PHOSPHATASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.OCTAGON);
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
