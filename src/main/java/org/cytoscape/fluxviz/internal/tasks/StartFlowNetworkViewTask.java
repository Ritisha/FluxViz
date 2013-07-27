package org.cytoscape.fluxviz.internal.tasks;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;

public class StartFlowNetworkViewTask extends AbstractNetworkViewTask {

	CyNetworkView networkView;
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	public Evaluator evaluator;
	
	public StartFlowNetworkViewTask(CyNetworkView networkView, NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler)
	{
		super(networkView);
		this.networkView = networkView;
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {
		
		evaluator = new Evaluator(networkView.getModel());
		evaluator.start();
	}
}
