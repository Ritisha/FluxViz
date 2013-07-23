package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskMonitor;

public class SetTypeNodeViewTask extends AbstractNodeViewTask {

	View<CyNode> nodeView;
	CyNetworkView networkView;
	String type;
	NodeViewHandler nodeViewHandler;
	public static String KINASE = "Kinase";
	public static String MOLECULES = "Molecules";
	public static String GTPASE = "GTPase";
	public static String RECEPTOR = "Receptor";
	public static String RECEPTOR_T_KINASE = "ReceptorTKinase";
	
	public SetTypeNodeViewTask(View<CyNode> nodeView, CyNetworkView networkView, String type, NodeViewHandler nodeViewHandler)
	{
		super(nodeView,networkView);	
		this.nodeView = nodeView;
		this.networkView = networkView;
		this.type = type;
		this.nodeViewHandler = nodeViewHandler;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {

		//update type in defaultNodeTable
		CyRow row = ColumnsCreator.DefaultNodeTable.getRow(nodeView.getSUID());
		row.set(ColumnsCreator.NODE_TYPE, type);
		
		//update the look of the node based on type
		nodeViewHandler.setNodeView(nodeView, networkView, type);
	}
}
