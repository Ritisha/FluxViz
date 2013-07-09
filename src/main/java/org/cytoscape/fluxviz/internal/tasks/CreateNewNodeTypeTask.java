package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.ui.CreateNewNodeTypeDialog;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;

public class CreateNewNodeTypeTask extends AbstractNodeViewTask {

	View<CyNode> nodeView;
	CyNetworkView networkView;

	public CreateNewNodeTypeTask(View<CyNode> nodeView, CyNetworkView networkView)
	{
		super(nodeView, networkView);
		this.nodeView = nodeView;
		this.networkView = networkView;
	}
	@Override
	public void run(TaskMonitor arg0) throws Exception {

		new CreateNewNodeTypeDialog();
	}

}
