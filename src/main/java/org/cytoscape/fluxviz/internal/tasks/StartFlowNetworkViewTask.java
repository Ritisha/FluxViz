package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StartFlowNetworkViewTask extends AbstractNetworkViewTask {

	CyNetworkView networkView;
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	public Evaluator evaluator;
	
	/**
	 * Calls the evaluate method of the Evaluator 
	 * @param networkView
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public StartFlowNetworkViewTask(CyNetworkView networkView, NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler)
	{
		super(networkView);
		this.networkView = networkView;
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {
		
		evaluator = new Evaluator(networkView.getModel());
		evaluator.start();
	}
}
