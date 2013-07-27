package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StopFlowNetworkViewTask extends AbstractNetworkViewTask {

	StartFlowNetworkViewTask lastTask;
	
	public StopFlowNetworkViewTask(CyNetworkView networkView, StartFlowNetworkViewTask lastTask)
	{
		super(networkView);
		this.lastTask = lastTask;
	}
	@Override
	public void run(TaskMonitor arg0) throws Exception {

		lastTask.evaluator.interrupt();
	}

}
