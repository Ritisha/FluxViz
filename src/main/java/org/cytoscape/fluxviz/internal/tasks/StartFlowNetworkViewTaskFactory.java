package org.cytoscape.fluxviz.internal.tasks;

import java.util.Properties;

import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import static org.cytoscape.work.ServiceProperties.TITLE;


public class StartFlowNetworkViewTaskFactory extends
		AbstractNetworkViewTaskFactory {

	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	Properties startProps;
	StartFlowNetworkViewTask lastTask;
	
	public StartFlowNetworkViewTaskFactory(NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler, Properties startProps)
	{
		super();
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
		this.startProps = startProps;
	}
	@Override
	public TaskIterator createTaskIterator(CyNetworkView networkView) {
		
		TaskIterator taskIterator = null;
		if(startProps.getProperty(TITLE).equals("Start"))
		{
			startProps.setProperty(TITLE, "Stop");
			lastTask = new StartFlowNetworkViewTask(networkView, nodeViewHandler, edgeViewHandler);
			taskIterator = new TaskIterator(lastTask);
		}
		else if(startProps.getProperty(TITLE).equals("Stop"))
		{
			startProps.setProperty(TITLE, "Start");
			taskIterator = new TaskIterator(new StopFlowNetworkViewTask(networkView, lastTask));
		}
		return taskIterator;
	}

}
