package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetTypeNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	String nodeType;
	ViewHandler viewHandler;
	
	/**
	 * Calls the SetTypeNodeViewTask
	 * @param type
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public SetTypeNodeViewTaskFactory(String type, ViewHandler viewHandler)
	{
		super();
		nodeType = type;
		this.viewHandler = viewHandler;
	}
	@Override
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
		return new TaskIterator(new SetTypeNodeViewTask(nodeView, networkView, nodeType, viewHandler));
	}
}
