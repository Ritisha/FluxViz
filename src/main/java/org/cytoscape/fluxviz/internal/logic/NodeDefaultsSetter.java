package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.Collection;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.AddedNodesEvent;
import org.cytoscape.model.events.AddedNodesListener;

public class NodeDefaultsSetter implements AddedNodesListener {

	@Override
	public void handleEvent(AddedNodesEvent e) {

		CyNetwork network = e.getSource();
		Collection<CyNode> nodes = new ArrayList<CyNode>();
		nodes = e.getPayloadCollection();
		addDefaults(network.getDefaultNodeTable(), network.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS), nodes);
	}
	
	public static void addDefaults(CyTable defaultNodeTable, CyTable hiddenNodeTable, Collection<CyNode> nodes)
	{
		CyRow row;
		for(CyNode currNode : nodes)
		{
			row = defaultNodeTable.getRow(currNode.getSUID());
			row.set(ColumnsCreator.NODE_TYPE, "Kinase");
			row.set(ColumnsCreator.NUM_OF_INPUTS, 1);
			row.set(ColumnsCreator.TIME_RAMP, 1.0);
			row.set(ColumnsCreator.RELATIVE_CONCENTRATION, 1.0);
			row.set(ColumnsCreator.DECAY, 0.0001);
			row.set(ColumnsCreator.INITIAL_OUTPUT_VALUE, 0.0);
			
			row = hiddenNodeTable.getRow(currNode.getSUID());
			row.set(ColumnsCreator.CURR_OUTPUT, 0.0);
			row.set(ColumnsCreator.NEXT_OUTPUT, 0.0);		
		}
	}

}
