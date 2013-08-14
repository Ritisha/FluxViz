package org.cytoscape.fluxviz.internal;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.EdgeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.NodeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.FluxVizNetworkViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.UseFluxVizNetworkViewTaskFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.events.AddedEdgesListener;
import org.cytoscape.model.events.AddedNodesListener;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.work.Tunable;
import org.osgi.framework.BundleContext;

/**
 * 
 * @author laungani
 *
 */
public class CyActivator extends AbstractCyActivator {

	/**
	 * The entry point for the app. Registers all menus and creates appropriate columns for the app
	 * 
	 * @param context the BundleContext which helps getting managers and factories and in registering the listeners
	 * @return void
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {

		VisualMappingManager visualMappingManager = getService(bundleContext, VisualMappingManager.class);
		CyNetworkManager cyNetworkManager = getService(bundleContext, CyNetworkManager.class);
		//CyServiceRegistrar cyServiceRegistrar = getService(bundleContext, CyServiceRegistrar.class);
		CyNetworkViewManager cyNetworkViewManager = getService(bundleContext, CyNetworkViewManager.class);
		VisualMappingFunctionFactory continousVisualMappingFunctionFactory = getService(bundleContext, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualStyleFactory visualStyleFactory = getService(bundleContext, VisualStyleFactory.class);
		//CyApplicationManager cyApplicationManager = getService(bundleContext, CyApplicationManager.class);

		Context appContext = new Context();
		ViewHandler viewHandler = new ViewHandler(cyNetworkViewManager, visualMappingManager, visualStyleFactory, continousVisualMappingFunctionFactory);

		//add app-specific columns to default tables
//		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
//		allNets = cyNetworkManager.getNetworkSet();
//
//		for(CyNetwork currNet : allNets)
//		{
//			ColumnsCreator.createColumns(currNet, viewHandler);		
//		}
//
//		registerService(bundleContext, new ColumnsCreator(viewHandler), NetworkAddedListener.class, new Properties());

//		//set defaults for attributes of newly added nodes
//		registerService(bundleContext, new NodeDefaultsSetter(viewHandler.getNodeViewHandler()), AddedNodesListener.class, new Properties());
//
//		//set defaults for the attributes for newly added edges
//		registerService(bundleContext, new EdgeDefaultsSetter(viewHandler.getEdgeViewHandler()), AddedEdgesListener.class, new Properties());

		//add Use FluxViz in network context menu
		Properties switchProps = new Properties();
		switchProps.setProperty(PREFERRED_ACTION, "NEW");
		switchProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		switchProps.setProperty(MENU_GRAVITY, "6.0f");
		switchProps.setProperty(IN_MENU_BAR, "false");
		switchProps.setProperty(TITLE, "Use FluxViz");

		registerService(bundleContext, new UseFluxVizNetworkViewTaskFactory(appContext), 
				NetworkViewTaskFactory.class, switchProps);
		
		//add fluxviz menu to node context menu
		Properties kinaseProps = new Properties();
		kinaseProps.setProperty(PREFERRED_ACTION, "NEW");
		kinaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		kinaseProps.setProperty(MENU_GRAVITY, "6.0f");
		kinaseProps.setProperty(IN_MENU_BAR, "false");
		kinaseProps.setProperty(TITLE, SetTypeNodeViewTask.KINASE);

		registerService(bundleContext, new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.KINASE, viewHandler, appContext), 
				NodeViewTaskFactory.class, kinaseProps);

		Properties moleculesProps = new Properties();
		moleculesProps.setProperty(PREFERRED_ACTION, "NEW");
		moleculesProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		moleculesProps.setProperty(MENU_GRAVITY, "7.0f");
		moleculesProps.setProperty(IN_MENU_BAR, "false");
		moleculesProps.setProperty(TITLE, SetTypeNodeViewTask.MOLECULES);

		registerService(bundleContext, new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.MOLECULES, viewHandler, appContext), 
				NodeViewTaskFactory.class, moleculesProps);

		Properties gtpaseProps = new Properties();
		gtpaseProps.setProperty(PREFERRED_ACTION, "NEW");
		gtpaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		gtpaseProps.setProperty(MENU_GRAVITY, "8.0f");
		gtpaseProps.setProperty(IN_MENU_BAR, "false");
		gtpaseProps.setProperty(TITLE, SetTypeNodeViewTask.GTPASE);

		registerService(bundleContext, new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.GTPASE, viewHandler, appContext), 
				NodeViewTaskFactory.class, gtpaseProps);

		Properties receptorProps = new Properties();
		receptorProps.setProperty(PREFERRED_ACTION, "NEW");
		receptorProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		receptorProps.setProperty(MENU_GRAVITY, "9.0f");
		receptorProps.setProperty(IN_MENU_BAR, "false");
		receptorProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR);

		registerService(bundleContext, new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR, viewHandler, appContext), 
				NodeViewTaskFactory.class, receptorProps);

		Properties receptorTKinaseProps = new Properties();
		receptorTKinaseProps.setProperty(PREFERRED_ACTION, "NEW");
		receptorTKinaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		receptorTKinaseProps.setProperty(MENU_GRAVITY, "10.0f");
		receptorTKinaseProps.setProperty(IN_MENU_BAR, "false");
		receptorTKinaseProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR_T_KINASE);

		registerService(bundleContext, new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR_T_KINASE, viewHandler, appContext), 
				NodeViewTaskFactory.class, receptorTKinaseProps);

		Properties phosphataseProps = new Properties();
		phosphataseProps.setProperty(PREFERRED_ACTION, "NEW");
		phosphataseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		phosphataseProps.setProperty(MENU_GRAVITY, "10.0f");
		phosphataseProps.setProperty(IN_MENU_BAR, "false");
		phosphataseProps.setProperty(TITLE, SetTypeNodeViewTask.PHOSPHATASE);

		registerService(bundleContext, new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.PHOSPHATASE, viewHandler, appContext), 
				NodeViewTaskFactory.class, phosphataseProps);	

		//add fluxviz menu to edge context menu
		Properties activatingProps = new Properties();
		activatingProps.setProperty(PREFERRED_ACTION, "NEW");
		activatingProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		activatingProps.setProperty(MENU_GRAVITY, "10.0f");
		activatingProps.setProperty(IN_MENU_BAR, "false");
		activatingProps.setProperty(TITLE, SetTypeEdgeViewTask.ACTIVATING);

		registerService(bundleContext, new SetTypeEdgeViewTaskFactory(SetTypeEdgeViewTask.ACTIVATING, viewHandler.getEdgeViewHandler(), appContext), 
				EdgeViewTaskFactory.class, activatingProps);

		Properties deactivatingProps = new Properties();
		deactivatingProps.setProperty(PREFERRED_ACTION, "NEW");
		deactivatingProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		deactivatingProps.setProperty(MENU_GRAVITY, "10.0f");
		deactivatingProps.setProperty(IN_MENU_BAR, "false");
		deactivatingProps.setProperty(TITLE, SetTypeEdgeViewTask.DEACTIVATING);

		registerService(bundleContext, new SetTypeEdgeViewTaskFactory(SetTypeEdgeViewTask.DEACTIVATING, viewHandler.getEdgeViewHandler(), appContext), 
				EdgeViewTaskFactory.class, deactivatingProps);	

		//add fluxviz menu to network context menu
		Properties startProps = new Properties();
		startProps.setProperty(PREFERRED_ACTION, "NEW");
		startProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		startProps.setProperty(MENU_GRAVITY, "10.0f");
		startProps.setProperty(IN_MENU_BAR, "false");
		startProps.setProperty(TITLE, "Start4");

		registerService(bundleContext, new FluxVizNetworkViewTaskFactory(viewHandler, startProps, appContext), NetworkViewTaskFactory.class, startProps);

		//add the continuous visual mapping for node color mapped with currOutput
		//ContinuousMapping<Double, Paint> tempMapping = (ContinuousMapping<Double, Paint>) continousVisualMappingFunctionFactory.createVisualMappingFunction(ColumnsCreator.CURR_OUTPUT, Double.class, BasicVisualLexicon.NODE_FILL_COLOR);

//		Double val1 = 0d;
//		Double val2 = 1d;
//		Double val3 = 5d;
//		BoundaryRangeValues<Paint> brv1 = new BoundaryRangeValues<Paint>(Color.GRAY, Color.GRAY, Color.DARK_GRAY);
//		BoundaryRangeValues<Paint> brv2 = new BoundaryRangeValues<Paint>(Color.GREEN, Color.GREEN, Color.GREEN);
//		BoundaryRangeValues<Paint> brv3 = new BoundaryRangeValues<Paint>(Color.RED, Color.RED, Color.RED);
//
//		tempMapping.addPoint(val1, brv1);
//		tempMapping.addPoint(val2, brv2);
//		tempMapping.addPoint(val3, brv3);
//		
//		CyNetwork tempNet = cyApplicationManager.getCurrentNetwork();
//		List<CyNode> allNodes = new ArrayList<CyNode>();
//		allNodes = tempNet.getNodeList();
//		boolean temp = true;
//		for(CyNode currNode : allNodes)
//		{
//			temp = !temp;
//			if(temp)
//			{
//				tempMapping.apply(ColumnsCreator.DefaultNodeTable.getRow(currNode.getSUID()), cyApplicationManager.getCurrentNetworkView().getNodeView(currNode));
//			}
//		}
		viewHandler.createVisualMappings();
		viewHandler.createFluxVizStyle();		
	}
}
