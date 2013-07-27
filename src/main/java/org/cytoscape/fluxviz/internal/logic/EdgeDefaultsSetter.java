package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.Collection;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.events.AddedEdgesEvent;
import org.cytoscape.model.events.AddedEdgesListener;

public class EdgeDefaultsSetter implements AddedEdgesListener {

	EdgeViewHandler edgeViewHandler;
	
	public EdgeDefaultsSetter(EdgeViewHandler edgeViewHandler)
	{
		super();
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public void handleEvent(AddedEdgesEvent e) {

		CyNetwork network = e.getSource();
		Collection<CyEdge> edges = new ArrayList<CyEdge>();
		edges = e.getPayloadCollection();
		addDefaults(network, edges, edgeViewHandler);
	}
	
	public static void addDefaults(CyNetwork network, Collection<CyEdge> edges, EdgeViewHandler edgeViewHandler)
	{
		CyRow row;
		CyNode sourceNode;
		String sourceNodeType;

		for(CyEdge currEdge : edges)
		{
			sourceNode = currEdge.getSource();
			row = ColumnsCreator.DefaultNodeTable.getRow(sourceNode.getSUID());
			sourceNodeType = row.get(ColumnsCreator.NODE_TYPE, String.class);
			
			SetTypeNodeViewTask.setDefaultsForEdgeBasedOnNodeType(currEdge, network, sourceNodeType, edgeViewHandler);
		}	
	}
}
