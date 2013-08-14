package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;

public class Context {
	
	public List<CyNetwork> activeNetworks;
	
	public Context()
	{
		activeNetworks = new ArrayList<CyNetwork>();
	}

	public boolean containsNetwork(CyNetwork network)
	{
		return activeNetworks.contains(network);
	}
	
	public void addNetwork(CyNetwork network)
	{
		activeNetworks.add(network);
	}
}
