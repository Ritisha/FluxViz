package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class StartFlowNetworkViewTask extends AbstractNetworkViewTask {

	CyNetworkView networkView;
	ViewHandler viewHandler;
	public Evaluator evaluator;
	

	//testing tunables
	@Tunable(description="Test")
	int test = 1; //default
	
	
	/**
	 * Calls the evaluate method of the Evaluator 
	 * @param networkView
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	public StartFlowNetworkViewTask(CyNetworkView networkView, ViewHandler viewHandler, Evaluator evaluator)
	{
		super(networkView);
		this.networkView = networkView;
		this.viewHandler = viewHandler;
		this.evaluator = evaluator;
		
		//check and set visual mapping
		viewHandler.createFluxVizStyle();
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {
		
		System.out.println("test is " + test);
		evaluator.startEvaluator(networkView);
	}
}
