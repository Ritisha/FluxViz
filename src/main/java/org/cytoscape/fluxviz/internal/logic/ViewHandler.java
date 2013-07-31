package org.cytoscape.fluxviz.internal.logic;

import java.awt.Color;
import java.awt.Paint;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

public class ViewHandler {
	
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	VisualMappingFunctionFactory continousVisualMappingFunctionFactory;
	VisualMappingManager visualMappingManager;
	VisualStyleFactory visualStyleFactory;
	ContinuousMapping<Double, Paint> continuousCurrOutputNodeFillColorMapping;
	
	public ViewHandler(CyNetworkViewManager cyNetworkViewManager, VisualMappingManager visualMappingManager, VisualStyleFactory visualStyleFactory, VisualMappingFunctionFactory continousVisualMappingFunctionFactory)
	{
		nodeViewHandler = new NodeViewHandler(cyNetworkViewManager, visualMappingManager);
		edgeViewHandler = new EdgeViewHandler(cyNetworkViewManager, visualMappingManager);
		this.continousVisualMappingFunctionFactory = continousVisualMappingFunctionFactory;
		this.visualMappingManager = visualMappingManager;
		this.visualStyleFactory = visualStyleFactory;
	}

	public void createVisualMapping()
	{
		continuousCurrOutputNodeFillColorMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_FILL_COLOR);

		Double val1 = 0d;
		Double val2 = 1d;
		Double val3 = 5d;
		BoundaryRangeValues<Paint> brv1 = new BoundaryRangeValues<Paint>(Color.GRAY, Color.GRAY, Color.DARK_GRAY);
		BoundaryRangeValues<Paint> brv2 = new BoundaryRangeValues<Paint>(Color.GREEN, Color.GREEN, Color.GREEN);
		BoundaryRangeValues<Paint> brv3 = new BoundaryRangeValues<Paint>(Color.RED, Color.RED, Color.RED);

		continuousCurrOutputNodeFillColorMapping.addPoint(val1, brv1);
		continuousCurrOutputNodeFillColorMapping.addPoint(val2, brv2);
		continuousCurrOutputNodeFillColorMapping.addPoint(val3, brv3);
	}
	
	public void createFluxVizStyle()
	{
		VisualStyle currVisualStyle = visualMappingManager.getCurrentVisualStyle();
		String oldName = currVisualStyle.getTitle();
		if(oldName.startsWith("FlixViz_"))
			return;
		String newName = "FluxViz_" + oldName;
		VisualStyle newVisualStyle = visualStyleFactory.createVisualStyle(currVisualStyle);
		newVisualStyle.setTitle(newName);
		newVisualStyle.addVisualMappingFunction(continuousCurrOutputNodeFillColorMapping);
		visualMappingManager.addVisualStyle(newVisualStyle);
		visualMappingManager.setCurrentVisualStyle(newVisualStyle);
	}
	
	public void refresh(CyNetworkView networkView)
	{
		System.out.println("refreshing!!**");
		VisualStyle style = visualMappingManager.getCurrentVisualStyle();
		style.apply(networkView);
		networkView.updateView();
	}
	
	public ContinuousMapping<Double, Paint> getContinuousCurrOutputNodeFillColorMapping() {
		return continuousCurrOutputNodeFillColorMapping;
	}

	public void setContinuousCurrOutputNodeFillColorMapping(
			ContinuousMapping<Double, Paint> continuousCurrOutputNodeFillColorMapping) {
		this.continuousCurrOutputNodeFillColorMapping = continuousCurrOutputNodeFillColorMapping;
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
