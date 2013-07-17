package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class StartFlowNetworkViewTaskFactory extends
		AbstractNetworkViewTaskFactory {

	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		return new TaskIterator(new StartFlowNetworkViewTask(networkView));
	}

}
