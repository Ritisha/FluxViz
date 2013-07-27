package org.cytoscape.fluxviz.internal;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.awt.Color;
import java.awt.Paint;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.CyActivatorHelper;
import org.cytoscape.fluxviz.internal.logic.EdgeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.EdgeViewHandler;
import org.cytoscape.fluxviz.internal.logic.NodeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.NodeViewHandler;
import org.cytoscape.fluxviz.internal.tasks.StartFlowNetworkViewTaskFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.events.AddedEdgesListener;
import org.cytoscape.model.events.AddedNodesListener;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		VisualMappingManager visualMappingManager = getService(context, VisualMappingManager.class);
		CyNetworkManager cyNetworkManager = getService(context, CyNetworkManager.class);
		CyServiceRegistrar cyServiceRegistrar = getService(context, CyServiceRegistrar.class);
		CyNetworkViewManager cyNetworkViewManager = getService(context, CyNetworkViewManager.class);
		VisualMappingFunctionFactory continousVisualMappingFunctionFactory = getService(context, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		
		EdgeViewHandler edgeViewHandler = new EdgeViewHandler(cyNetworkViewManager, visualMappingManager);
		NodeViewHandler nodeViewHandler = new NodeViewHandler(cyNetworkViewManager, visualMappingManager);
		CyActivatorHelper helper = new CyActivatorHelper(cyServiceRegistrar, nodeViewHandler, edgeViewHandler);

	
		//add app-specific columns to default tables
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
		allNets = cyNetworkManager.getNetworkSet();
		
		for(CyNetwork currNet : allNets)
		{
			ColumnsCreator.createColumns(currNet, nodeViewHandler, edgeViewHandler);		
		}
		
		cyServiceRegistrar.registerService(new ColumnsCreator(nodeViewHandler, edgeViewHandler), NetworkAddedListener.class, new Properties() );
		
		//set defaults for attributes of newly added nodes
		cyServiceRegistrar.registerService(new NodeDefaultsSetter(nodeViewHandler), AddedNodesListener.class, new Properties());
		
		//set defaults for the attributes for newly added edges
		cyServiceRegistrar.registerService(new EdgeDefaultsSetter(edgeViewHandler), AddedEdgesListener.class, new Properties());
		
		//add fluxviz menu to node context menu
		helper.addNodeSetTypeMenus();
	  	  	
		//add fluxviz menu to edge context menu
		helper.addEdgeSetTypeMenus();
		
		//add fluxviz menu to network context menu	
		Properties startProps = new Properties();
		startProps.setProperty(PREFERRED_ACTION, "NEW");
		startProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		startProps.setProperty(MENU_GRAVITY, "10.0f");
		startProps.setProperty(IN_MENU_BAR, "false");
		startProps.setProperty(TITLE, "Start");
	  	
	  	cyServiceRegistrar.registerService(new StartFlowNetworkViewTaskFactory(nodeViewHandler, edgeViewHandler, startProps), 
	  			NetworkViewTaskFactory.class, startProps);
	  	
	  	Properties stopProps = new Properties();
	  	stopProps.setProperty(PREFERRED_ACTION, "NEW");
	  	stopProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
	  	stopProps.setProperty(MENU_GRAVITY, "10.0f");
	  	stopProps.setProperty(IN_MENU_BAR, "false");
	  	stopProps.setProperty(TITLE, "Stop");
	  	
	  	cyServiceRegistrar.registerService(new StartFlowNetworkViewTaskFactory(nodeViewHandler, edgeViewHandler, startProps), 
	  			NetworkViewTaskFactory.class, stopProps);
	  	
	  	//add the continuous visual mapping for node color mapped with currOutput
	  	Set<VisualStyle> allVisualStyles = new HashSet<VisualStyle>();
	  	allVisualStyles = visualMappingManager.getAllVisualStyles();
	  	ContinuousMapping<Double , Paint> continuousMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_FILL_COLOR);
	  	
	  	Double val1 = 2d;
	  	Double val2 = 12d;
	  	BoundaryRangeValues<Paint> brv1 = new BoundaryRangeValues<Paint>(Color.RED, Color.GREEN, Color.PINK);
	  	BoundaryRangeValues<Paint> brv2 = new BoundaryRangeValues<Paint>(Color.WHITE, Color.YELLOW, Color.BLACK);
	  	
	  	continuousMapping.addPoint(val1, brv1);
	  	continuousMapping.addPoint(val2, brv2);
	  	
	  	for(VisualStyle currVisualStyle: allVisualStyles)
	  	{
		  	currVisualStyle.addVisualMappingFunction(continuousMapping);
		  	visualMappingManager.addVisualStyle(currVisualStyle);
	  	}
	  			
	}

}
