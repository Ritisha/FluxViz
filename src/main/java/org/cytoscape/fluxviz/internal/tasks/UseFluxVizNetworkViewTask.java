package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class UseFluxVizNetworkViewTask extends AbstractNetworkViewTask {

	Context appContext;
	CyNetworkView networkView;
	
	public UseFluxVizNetworkViewTask(CyNetworkView networkView, Context appContext)
	{
		super(networkView);
		this.appContext = appContext;
		this.networkView = networkView;
	}
	
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		appContext.addNetwork(networkView.getModel());
	}

}
