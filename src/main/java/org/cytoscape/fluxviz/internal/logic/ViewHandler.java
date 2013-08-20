package org.cytoscape.fluxviz.internal.logic;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;

public class ViewHandler {
	
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	VisualMappingFunctionFactory continousVisualMappingFunctionFactory;
	VisualMappingFunctionFactory discreteVisualMappingFunctionFactory;
	VisualMappingManager visualMappingManager;
	VisualStyleFactory visualStyleFactory;
	ContinuousMapping<Double, Paint> currOutputNodeFillColorMapping;
	ContinuousMapping<Double, Paint> currOutputNodeSelectedColorMapping;
	ContinuousMapping<Double, Paint> activatingEdgeMapping;
	ContinuousMapping<Double, Paint> deactivatingEdgeMapping;
	DiscreteMapping<Boolean, Paint> selectedNodeBorderColorMapping;
	
	public ViewHandler(CyNetworkViewManager cyNetworkViewManager, VisualMappingManager visualMappingManager, VisualStyleFactory visualStyleFactory, VisualMappingFunctionFactory continousVisualMappingFunctionFactory, VisualMappingFunctionFactory discreteVisualMappingFunctionFactory)
	{
		nodeViewHandler = new NodeViewHandler(cyNetworkViewManager, visualMappingManager);
		edgeViewHandler = new EdgeViewHandler(cyNetworkViewManager, visualMappingManager);
		this.continousVisualMappingFunctionFactory = continousVisualMappingFunctionFactory;
		this.discreteVisualMappingFunctionFactory = discreteVisualMappingFunctionFactory;
		this.visualMappingManager = visualMappingManager;
		this.visualStyleFactory = visualStyleFactory;
	}

	public void createVisualMappings()
	{
		Double val1 = 0d;
		Double val2 = 1d;
		Double val3 = 5d;
		
		BoundaryRangeValues<Paint> brv1 = new BoundaryRangeValues<Paint>(Color.GRAY, Color.GRAY, Color.GRAY);
		BoundaryRangeValues<Paint> brv2 = new BoundaryRangeValues<Paint>(Color.GREEN, Color.GREEN, Color.GREEN);
		BoundaryRangeValues<Paint> brv3 = new BoundaryRangeValues<Paint>(Color.RED, Color.RED, Color.RED);
		BoundaryRangeValues<Paint> brv4 = new BoundaryRangeValues<Paint>(Color.BLUE, Color.BLUE, Color.BLUE);
		BoundaryRangeValues<Paint> brv5 = new BoundaryRangeValues<Paint>(Color.RED, Color.RED, Color.RED);
		BoundaryRangeValues<Paint> brv6 = new BoundaryRangeValues<Paint>(Color.BLACK, Color.BLACK, Color.BLACK);

		currOutputNodeFillColorMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_FILL_COLOR);
		currOutputNodeFillColorMapping.addPoint(val1, brv1);
		currOutputNodeFillColorMapping.addPoint(val2, brv2);
		currOutputNodeFillColorMapping.addPoint(val3, brv3);
		
		currOutputNodeSelectedColorMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_SELECTED_PAINT);
		currOutputNodeSelectedColorMapping.addPoint(val1, brv1);
		currOutputNodeSelectedColorMapping.addPoint(val2, brv2);
		currOutputNodeSelectedColorMapping.addPoint(val3, brv3);
		
		activatingEdgeMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT, Double.class, BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT);
		activatingEdgeMapping.addPoint(val1, brv1);
		activatingEdgeMapping.addPoint(val2, brv2);
		activatingEdgeMapping.addPoint(val3, brv4);
		
		deactivatingEdgeMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.EDGE_SOURCE_NODE_OUTPUT, Double.class, BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT);
		deactivatingEdgeMapping.addPoint(val1, brv5);
		deactivatingEdgeMapping.addPoint(val2, brv1);
		deactivatingEdgeMapping.addPoint(val3, brv6);
		
		selectedNodeBorderColorMapping = (DiscreteMapping<Boolean, Paint>) discreteVisualMappingFunctionFactory.createVisualMappingFunction(CyNetwork.SELECTED, Boolean.class, BasicVisualLexicon.NODE_BORDER_PAINT);
		selectedNodeBorderColorMapping.putMapValue(true, Color.YELLOW);
	}
	
	public void createFluxVizStyle()
	{
		VisualStyle currVisualStyle = visualMappingManager.getCurrentVisualStyle();
		String oldName = currVisualStyle.getTitle();
		if(oldName.startsWith("FluxViz_"))
			return;
		String newName = "FluxViz_" + oldName;
		VisualStyle newVisualStyle = visualStyleFactory.createVisualStyle(currVisualStyle);
		newVisualStyle.setTitle(newName);
		newVisualStyle.addVisualMappingFunction(currOutputNodeFillColorMapping);
		newVisualStyle.addVisualMappingFunction(currOutputNodeSelectedColorMapping);
		newVisualStyle.addVisualMappingFunction(selectedNodeBorderColorMapping);
		visualMappingManager.addVisualStyle(newVisualStyle);
		visualMappingManager.setCurrentVisualStyle(newVisualStyle);
	}
	
	public void refresh(CyNetworkView networkView)
	{
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
		
		List<CyEdge> allEdges = new ArrayList<CyEdge>();
		allEdges = networkView.getModel().getEdgeList();
		for(CyEdge currEdge : allEdges)
		{
			CyRow row = ColumnsCreator.DefaultEdgeTable.getRow(currEdge.getSUID());
			String currEdgeType = row.get(ColumnsCreator.EDGE_TYPE, String.class);
			if(currEdgeType.equals(SetTypeEdgeViewTask.ACTIVATING))
			{
				activatingEdgeMapping.apply(row, networkView.getEdgeView(currEdge));
				System.out.println("activatingEdgeMapping");
			}
			else if(currEdgeType.equals(SetTypeEdgeViewTask.DEACTIVATING))
			{
				deactivatingEdgeMapping.apply(row, networkView.getEdgeView(currEdge));
				System.out.println("deactivatingEdgeMapping");
			}
		}
	}
	
	public ContinuousMapping<Double, Paint> getContinuousCurrOutputNodeFillColorMapping() {
		return currOutputNodeFillColorMapping;
	}

	public void setContinuousCurrOutputNodeFillColorMapping(
			ContinuousMapping<Double, Paint> continuousCurrOutputNodeFillColorMapping) {
		this.currOutputNodeFillColorMapping = continuousCurrOutputNodeFillColorMapping;
	}

	public NodeViewHandler getNodeViewHandler() {
		return nodeViewHandler;
	}

	public void setNodeViewHandler(NodeViewHandler nodeViewHandler) {
		this.nodeViewHandler = nodeViewHandler;
	}

	public EdgeViewHandler getEdgeViewHandler() {
		return edgeViewHandler;
	}

	public void setEdgeViewHandler(EdgeViewHandler edgeViewHandler) {
		this.edgeViewHandler = edgeViewHandler;
	}

}
