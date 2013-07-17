package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StartFlowNetworkViewTask extends AbstractNetworkViewTask {

	CyNetworkView networkView;
	
	public StartFlowNetworkViewTask(CyNetworkView networkView)
	{
		super(networkView);
		this.networkView = networkView;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {
		while(true)
			{
			System.out.println("now");
			Thread.sleep(5000);
			}

	}

}
