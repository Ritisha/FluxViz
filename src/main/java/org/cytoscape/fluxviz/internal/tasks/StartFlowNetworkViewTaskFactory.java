package org.cytoscape.fluxviz.internal.tasks;

import java.util.Properties;

import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import static org.cytoscape.work.ServiceProperties.TITLE;


public class StartFlowNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	ViewHandler viewHandler;
	Context appContext;
	boolean doRestart = false;
	
	/**
	 * Calls the StartFlowNetworkViewTask and the StopFlowNetworkViewTask. 
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	//TODO Needs cleanup
	public StartFlowNetworkViewTaskFactory(ViewHandler viewHandler, Context appContext, boolean doRestart)
	{
		super();
		this.viewHandler = viewHandler;
		this.appContext = appContext;
	}
	
	public boolean isReady(CyNetworkView networkView)
	{
		return (appContext.containsNetwork(networkView.getModel()) && (appContext.getEvaluator() == null));
	}
	
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		
		//TaskIterator taskIterator = null;
		//if(properties.get(TITLE).equals("Start"))
		//{
			appContext.setEvaluator(new Evaluator(viewHandler));
			//properties.setProperty(TITLE, "Stop");
			viewHandler.refresh(networkView);
			return new TaskIterator(new StartFlowNetworkViewTask(networkView, viewHandler, appContext, doRestart));
		//}
//		else if(properties.get(TITLE).equals("Stop"))
//		{
//			properties.setProperty(TITLE, "Start");
//			viewHandler.refresh(networkView);
//			taskIterator = new TaskIterator(new StopFlowNetworkViewTask(networkView, evaluator));
//		}
//		return taskIterator;
	}
}
