package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.Collection;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
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
		CyTable defaultEdgeTable = network.getDefaultEdgeTable();

		for(CyEdge currEdge : edges)
		{
			row = defaultEdgeTable.getRow(currEdge.getSUID());
			row.set(ColumnsCreator.EDGE_TYPE, "Activating");
			row.set(ColumnsCreator.TARGET_INPUT, 1);
			row.set(ColumnsCreator.EDGE_EFFICIENCY, 1.0);
			edgeViewHandler.setDefaultEdgeView(currEdge, network);
		}	
	}
}
