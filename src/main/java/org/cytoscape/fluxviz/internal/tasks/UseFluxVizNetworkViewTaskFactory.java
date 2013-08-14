package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class UseFluxVizNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	Context appContext;
	
	public UseFluxVizNetworkViewTaskFactory(Context appContext)
	{
		this.appContext = appContext;
	}
	
	public boolean isReady(CyNetworkView networkView)
	{
		return (!appContext.containsNetwork(networkView.getModel()));
	}
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		return new TaskIterator(new UseFluxVizNetworkViewTask(networkView, appContext));
	}
	
	

}
