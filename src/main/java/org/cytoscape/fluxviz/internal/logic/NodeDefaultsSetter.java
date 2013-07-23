package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.AddedNodesEvent;
import org.cytoscape.model.events.AddedNodesListener;

public class NodeDefaultsSetter implements AddedNodesListener {

	NodeViewHandler nodeViewHandler;
	
	public NodeDefaultsSetter(NodeViewHandler nodeViewHandler)
	{
		super();
		this.nodeViewHandler = nodeViewHandler;
	}
	@Override
	public void handleEvent(AddedNodesEvent e) {

		CyNetwork network = e.getSource();
		Collection<CyNode> nodes = new ArrayList<CyNode>();
		nodes = e.getPayloadCollection();
		addDefaults(network, nodes, nodeViewHandler);
	}
	
	public static void addDefaults(CyNetwork network, Collection<CyNode> nodes, NodeViewHandler nodeViewHandler)
	{
		CyRow row;
		CyTable defaultNodeTable = network.getDefaultNodeTable();
		CyTable hiddenNodeTable = network.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		
		for(CyNode currNode : nodes)
		{
			System.out.println("in addDefaults of nodes");
			row = defaultNodeTable.getRow(currNode.getSUID());
			List<Double> portEfficiency = new ArrayList<Double>();
			portEfficiency.add(1.0);
			
			row.set(ColumnsCreator.NODE_TYPE, "Kinase");
			row.set(ColumnsCreator.NUM_OF_INPUTS, 1);
			row.set(ColumnsCreator.TIME_RAMP, 1.0);
			row.set(ColumnsCreator.RELATIVE_CONCENTRATION, 1.0);
			row.set(ColumnsCreator.DECAY, 0.0001);
			row.set(ColumnsCreator.INITIAL_OUTPUT_VALUE, 0.0);
			row.set(ColumnsCreator.PORT_EFFICIENCY, portEfficiency);
			row.set(ColumnsCreator.INTEGER_OUTPUT_NODE_THRESH, 0.5);
			row.set(ColumnsCreator.UPPER_BOUND, 1.0);
			
			row = hiddenNodeTable.getRow(currNode.getSUID());
			row.set(ColumnsCreator.CURR_OUTPUT, 0.0);
			row.set(ColumnsCreator.NEXT_OUTPUT, 0.0);	
			nodeViewHandler.setDefaultNodeView(currNode, network);
		}
	}

}
