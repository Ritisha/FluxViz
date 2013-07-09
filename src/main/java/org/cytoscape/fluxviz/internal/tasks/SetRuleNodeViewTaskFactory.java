package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class SetRuleNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

	String rule;
	int id;
	
	public SetRuleNodeViewTaskFactory(String rule, int id)
	{
		super();
		this.rule = rule;
		this.id = id;
	}
	@Override
	public TaskIterator createTaskIterator(View<CyNode> arg0, CyNetworkView arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
