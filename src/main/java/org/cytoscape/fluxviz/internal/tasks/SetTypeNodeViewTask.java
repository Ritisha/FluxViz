package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
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
	VisualMappingManager visualMappingManager;
	public static String KINASE = "Kinase";
	public static String MOLECULES = "Molecules";
	public static String GTPASE = "GTPase";
	public static String RECEPTOR = "Receptor";
	public static String RECEPTOR_T_KINASE = "ReceptorTKinase";
	
	public SetTypeNodeViewTask(View<CyNode> nodeView, CyNetworkView networkView, String type, VisualMappingManager visualMappingManager)
	{
		super(nodeView,networkView);	
		this.nodeView = nodeView;
		this.networkView = networkView;
		this.type = type;
		this.visualMappingManager = visualMappingManager;
	}
	@Override
	public void run(TaskMonitor tm) throws Exception {

		//update type in defaultNodeTable
		CyRow row = ColumnsCreator.DefaultNodeTable.getRow(nodeView.getSUID());
		row.set(ColumnsCreator.NODE_TYPE, type);
		
		//update the look of the node based on type
		if(type.equals(KINASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.TRIANGLE);
		}
		else if(type.equals(GTPASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.HEXAGON);
		}
		else if(type.equals(MOLECULES))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.TRIANGLE);
		}
		else if(type.equals(RECEPTOR) )
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.PARALLELOGRAM);
		}
		else if(type.equals(RECEPTOR_T_KINASE))
		{
			nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.PARALLELOGRAM);
		}
		
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
	}

}
