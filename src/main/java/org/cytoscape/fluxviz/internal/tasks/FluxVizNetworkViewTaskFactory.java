package org.cytoscape.fluxviz.internal.tasks;

import java.util.Properties;

import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import static org.cytoscape.work.ServiceProperties.TITLE;


public class FluxVizNetworkViewTaskFactory extends AbstractNetworkViewTaskFactory {

	ViewHandler viewHandler;
	Evaluator evaluator;
	Properties properties;
	String mode = "Start";
	
	/**
	 * Calls the StartFlowNetworkViewTask and the StopFlowNetworkViewTask. 
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	//TODO Needs cleanup
	public FluxVizNetworkViewTaskFactory(ViewHandler viewHandler, Properties properties)
	{
		super();
		this.viewHandler = viewHandler;
		this.properties = properties;
	}
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		
		TaskIterator taskIterator = null;
		if(properties.get(TITLE).equals("Start1"))
		{
			evaluator = new Evaluator(viewHandler);
			properties.setProperty(TITLE, "Stop");
			viewHandler.refresh(networkView);
			taskIterator = new TaskIterator(new StartFlowNetworkViewTask(networkView, viewHandler, evaluator));
		}
		else if(properties.get(TITLE).equals("Stop"))
		{
			properties.setProperty(TITLE, "Start1");
			viewHandler.refresh(networkView);
			taskIterator = new TaskIterator(new StopFlowNetworkViewTask(networkView, evaluator));
		}
		return taskIterator;
	}
}
