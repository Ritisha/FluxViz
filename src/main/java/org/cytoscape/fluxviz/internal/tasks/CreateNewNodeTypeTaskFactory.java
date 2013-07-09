package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class CreateNewNodeTypeTaskFactory extends AbstractNodeViewTaskFactory {

	public CreateNewNodeTypeTaskFactory()
	{
		super();
	}
	
	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		return new TaskIterator(new CreateNewNodeTypeTask(nodeView, networkView));
	}

}
