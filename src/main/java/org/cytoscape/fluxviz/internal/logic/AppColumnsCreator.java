package org.cytoscape.fluxviz.internal.logic;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;

public class AppColumnsCreator implements NetworkAddedListener {

	public static CyTable DefaultNodeTable;
	public static CyTable DefaultEdgeTable;
	public static String NODE_TYPE = "Type";
	public static String OUTPUT = "Output";
	public static String ORDER = "Order";
	public static String RULE = "Rule";
	public static String EDGE_TYPE = "Type";
	
	public AppColumnsCreator()
	{
		super();
	}
	
	@Override
	public void handleEvent(NetworkAddedEvent e) {

		createColumns(e.getNetwork());
	}
	
	public static void createColumns(CyNetwork network)
	{
		DefaultNodeTable = network.getDefaultNodeTable();
		DefaultEdgeTable = network.getDefaultEdgeTable();
		
		//add columns
		
		if(DefaultNodeTable.getColumn(NODE_TYPE) == null)
			DefaultNodeTable.createColumn(NODE_TYPE, String.class, true, "Default Node");

		if(DefaultNodeTable.getColumn(OUTPUT) == null)
			DefaultNodeTable.createColumn(OUTPUT, Long.class, true, new Long((long) 0.5));

		if(DefaultNodeTable.getColumn(ORDER) == null)
			DefaultNodeTable.createColumn(ORDER, Long.class, true, new Long((long) 0.0));

		if(DefaultNodeTable.getColumn(RULE) == null)
			DefaultNodeTable.createColumn(RULE, Integer.class, true, new Integer(0));

		if(DefaultEdgeTable.getColumn(EDGE_TYPE) == null)
			DefaultEdgeTable.createColumn(EDGE_TYPE, String.class, true, "Default Edge");
	}
}
