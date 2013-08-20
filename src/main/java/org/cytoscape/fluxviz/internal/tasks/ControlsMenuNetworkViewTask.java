package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.ui.ControlsUI;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class ControlsMenuNetworkViewTask extends AbstractNetworkViewTask {

	Context appContext;
	
	public ControlsMenuNetworkViewTask(CyNetworkView networkView, Context appContext)
	{
		super(networkView);
		this.appContext = appContext;
	}
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		new ControlsUI(appContext);
	}

}
