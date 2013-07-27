package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

public class Evaluator extends Thread {
	
	CyNetwork network;
	
	public Evaluator(CyNetwork network)
	{
		super();
		this.network = network;
	}

	public static void start(CyNetwork network)
	{
		Evaluator evaluator = new Evaluator(network);
		evaluator.start();
	}
	public void run()
	{
		List<CyNode> allNodes = new ArrayList<CyNode>();
		allNodes = network.getNodeList();
		CyTable hiddenNodeTable = network.getTable(CyNode.class, CyNetwork.HIDDEN_ATTRS);
		CyTable defaultEdgeTable = network.getDefaultEdgeTable();
		CyTable defaultNodeTable = network.getDefaultNodeTable();

		while(true)
		{
			System.out.println("now");
			for(CyNode currNode : allNodes)
			{
				Double nextOutput = evaluate(currNode, network, defaultEdgeTable, defaultNodeTable, hiddenNodeTable);
				CyRow row = hiddenNodeTable.getRow(currNode.getSUID());
				row.set(ColumnsCreator.NEXT_OUTPUT, nextOutput);
			}
			
			for(CyNode currNode : allNodes)
			{
				CyRow row = hiddenNodeTable.getRow(currNode.getSUID());
				Double currOutput = row.get(ColumnsCreator.NEXT_OUTPUT, Double.class);
				row.set(ColumnsCreator.CURR_OUTPUT, currOutput);
				System.out.println(row.get(ColumnsCreator.CURR_OUTPUT, Double.class));
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Double evaluate(CyNode node, CyNetwork network, CyTable defaultEdgeTable, CyTable defaultNodeTable, CyTable hiddenNodeTable)
	{

		Double v = new Double(0.0);
		Double newV = new Double(0.0);
		Double edgeSourceOutput = new Double(0.0);
		Double edgeEfficiency = new Double(0.0);
		Double edgeSourcePort1Efficiency = new Double(0.0);
		String edgeType;
		CyRow row;
		Double currentOutput = new Double(0.0);
		Double timeRamp = new Double(0.0);
		Double relativeConc = new Double(0.0);
		Double decay = new Double(0.0);
		
		//In the future, we can add functionality for nodeTypes and for numberOfInputs here.
		//For v1.0 each nodeType will only have one input site, so the code below will function ubiquitously for all nodeTypes in v1.0
		//In the future, we will iterate over all of the ports here: (in v1.0 we only have one port)
		List<CyEdge> incomingEdges = new ArrayList<CyEdge>();
		incomingEdges = network.getAdjacentEdgeList(node, CyEdge.Type.INCOMING);

		for(CyEdge currEdge : incomingEdges)
		{
			row = defaultEdgeTable.getRow(currEdge.getSUID());
			edgeType = row.get(ColumnsCreator.EDGE_TYPE, String.class);
			edgeEfficiency = row.get(ColumnsCreator.EDGE_EFFICIENCY, Double.class);
			
			CyNode sourceNode = currEdge.getSource();
			row = hiddenNodeTable.getRow(sourceNode.getSUID());
			edgeSourceOutput = row.get(ColumnsCreator.CURR_OUTPUT, Double.class);
			
			row = defaultNodeTable.getRow(sourceNode.getSUID());
			List<Double> edgeSourceNodePortEfficiencyList = new ArrayList<Double>();
			edgeSourceNodePortEfficiencyList = row.getList(ColumnsCreator.PORT_EFFICIENCY, Double.class);
			edgeSourcePort1Efficiency = edgeSourceNodePortEfficiencyList.get(0);
			
			if(edgeType.equals("Activating"))
			{
				v += edgeSourceOutput.doubleValue() * edgeEfficiency.doubleValue() * edgeSourcePort1Efficiency.doubleValue();
			}
			else if(edgeType.equals("Deactivating"))
			{
				v -= edgeSourceOutput.doubleValue() * edgeEfficiency.doubleValue() * edgeSourcePort1Efficiency.doubleValue();
			}
		}
		
		row = hiddenNodeTable.getRow(node.getSUID());
		currentOutput = row.get(ColumnsCreator.CURR_OUTPUT, Double.class);
		
		row = defaultNodeTable.getRow(node.getSUID());
		timeRamp = row.get(ColumnsCreator.TIME_RAMP, Double.class);
		relativeConc = row.get(ColumnsCreator.RELATIVE_CONCENTRATION, Double.class);
		decay = row.get(ColumnsCreator.DECAY, Double.class);
		
		newV = currentOutput.doubleValue() + (v.doubleValue() * (timeRamp.doubleValue() + relativeConc.doubleValue())/2) - decay.doubleValue();
		if(newV < 0)
		{
			newV = 0.0;
		}
		return newV;
	}

}
