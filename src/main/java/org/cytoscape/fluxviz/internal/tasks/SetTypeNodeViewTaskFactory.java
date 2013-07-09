package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetTypeNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	String nodeType;
	
	public SetTypeNodeViewTaskFactory(String type)
	{
		super();
		nodeType = type;
	}
	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeNodeViewTask(nodeView, networkView, nodeType));
	}
}
