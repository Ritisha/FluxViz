package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyNetwork;

public class Context {
	
	private List<CyNetwork> activeNetworks;
	private Evaluator evaluator = null;
	double speed;
	

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public Context()
	{
		activeNetworks = new ArrayList<CyNetwork>();
		speed = 2.0;
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
