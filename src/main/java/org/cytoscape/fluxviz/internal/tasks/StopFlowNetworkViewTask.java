package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StopFlowNetworkViewTask extends AbstractNetworkViewTask {

	Evaluator evaluator;
	
	/**
	 * Interrupts the thread start by StartFlowNetworkViewTask
	 * @param networkView
	 * @param lastTask
	 */
	public StopFlowNetworkViewTask(CyNetworkView networkView, Evaluator evaluator)
	{
		super(networkView);
		this.evaluator = evaluator;
	}
	@Override
	public void run(TaskMonitor arg0) throws Exception {

		evaluator.stopEvaluator();
	}

}
