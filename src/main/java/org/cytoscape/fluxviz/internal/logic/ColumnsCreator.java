package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class ColumnsCreator implements NetworkAddedListener {

	public static CyTable DefaultNodeTable;
	public static CyTable DefaultEdgeTable;
	public static CyTable HiddenNodeTable;
	public static String NODE_TYPE = "Node Type";
	public static String NUM_OF_INPUTS = "Number of Inputs";
	public static String TIME_RAMP = "Time Ramp";
	public static String RELATIVE_CONCENTRATION = "Relative Concentration";
	public static String DECAY = "Decay";
	public static String INITIAL_OUTPUT_VALUE = "Initial value of Output";
	public static String CURR_OUTPUT = "Current Output";
	public static String NEXT_OUTPUT = "Next Output";
	public static String EDGE_TYPE = "Edge Type";
	public static String TARGET_INPUT = "Target Input";

	public ColumnsCreator()
	{
		super();
	}
	
	@Override
	public void handleEvent(NetworkAddedEvent e) {

		createColumns(e.getNetwork());
	}
	
	public static void createColumns(CyNetwork network)
	{
		//get tables
		DefaultNodeTable = network.getDefaultNodeTable();
		DefaultEdgeTable = network.getDefaultEdgeTable();
		HiddenNodeTable = network.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		
		//add columns
		if(DefaultNodeTable.getColumn(NODE_TYPE) == null)
			DefaultNodeTable.createColumn(NODE_TYPE, String.class, true, "Kinase");

		if(DefaultNodeTable.getColumn(NUM_OF_INPUTS) == null)
			DefaultNodeTable.createColumn(NUM_OF_INPUTS, Integer.class, true, 1);

		if(DefaultNodeTable.getColumn(TIME_RAMP) == null)
			DefaultNodeTable.createColumn(TIME_RAMP, Double.class, true, 1.0);

		if(DefaultNodeTable.getColumn(RELATIVE_CONCENTRATION) == null)
			DefaultNodeTable.createColumn(RELATIVE_CONCENTRATION, Double.class, true, 1.0);

		if(DefaultNodeTable.getColumn(DECAY) == null)
			DefaultNodeTable.createColumn(DECAY, Double.class, true, 0.0001);
		
		if(DefaultNodeTable.getColumn(INITIAL_OUTPUT_VALUE) == null)
			DefaultNodeTable.createColumn(INITIAL_OUTPUT_VALUE, Double.class, true, 0.0);
		
		if(HiddenNodeTable.getColumn(CURR_OUTPUT) == null)
			HiddenNodeTable.createColumn(CURR_OUTPUT, Double.class, true, 0.0);
		
		if(HiddenNodeTable.getColumn(NEXT_OUTPUT) == null)
			HiddenNodeTable.createColumn(NEXT_OUTPUT, Double.class, true, 0.0);
		
		if(DefaultEdgeTable.getColumn(EDGE_TYPE) == null)
			DefaultEdgeTable.createColumn(EDGE_TYPE, String.class, true, "Activating");
		
		if(DefaultEdgeTable.getColumn(TARGET_INPUT) == null)
			DefaultEdgeTable.createColumn(TARGET_INPUT, Integer.class, true, 1);	
		
		//set defaults
		List<CyNode> allNodes = new ArrayList<CyNode>();
		List<CyEdge> allEdges = new ArrayList<CyEdge>();
		allNodes = network.getNodeList();
		allEdges = network.getEdgeList();
		EdgeDefaultsSetter.addDefaults(DefaultEdgeTable, allEdges);
		NodeDefaultsSetter.addDefaults(DefaultNodeTable, HiddenNodeTable, allNodes);
	}
}
