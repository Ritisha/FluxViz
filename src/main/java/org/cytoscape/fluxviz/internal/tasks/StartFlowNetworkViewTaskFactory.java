package org.cytoscape.fluxviz.internal.tasks;


import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;


public class StartFlowNetworkViewTaskFactory extends
		AbstractNetworkViewTaskFactory {

	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	StartFlowNetworkViewTask lastTask;
	public static String mode = "Start";
	
	/**
	 * Calls the StartFlowNetworkViewTask and the StopFlowNetworkViewTask. 
	 * @param nodeViewHandler
	 * @param edgeViewHandler
	 */
	//TODO Needs cleanup
	public StartFlowNetworkViewTaskFactory(NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler)
	{
		super();
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
	}
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		
		TaskIterator taskIterator = null;
		if(mode.equals("Start"))
		{
			mode = "Stop";
			lastTask = new StartFlowNetworkViewTask(networkView, nodeViewHandler, edgeViewHandler);
			taskIterator = new TaskIterator(lastTask);
		}
		else if(mode.equals("Stop"))
		{
			mode = "Start";
			taskIterator = new TaskIterator(new StopFlowNetworkViewTask(networkView, lastTask));
		}
		return taskIterator;
	}

}
