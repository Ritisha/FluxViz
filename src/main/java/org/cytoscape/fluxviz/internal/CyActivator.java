package org.cytoscape.fluxviz.internal;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.util.Properties;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.fluxviz.internal.logic.EdgeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.NodeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.fluxviz.internal.tasks.ControlsMenuNetworkViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.EditNodeAttributesTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.StartFlowNetworkViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.StopFlowNetworkViewTaskFactory;
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
		//CyNetworkManager cyNetworkManager = getService(bundleContext, CyNetworkManager.class);
		//CyServiceRegistrar cyServiceRegistrar = getService(bundleContext, CyServiceRegistrar.class);
		CyNetworkViewManager cyNetworkViewManager = getService(bundleContext, CyNetworkViewManager.class);
		VisualMappingFunctionFactory continousVisualMappingFunctionFactory = getService(bundleContext, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory discreteVisualMappingFunctionFactory = getService(bundleContext, VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualStyleFactory visualStyleFactory = getService(bundleContext, VisualStyleFactory.class);
		//CyApplicationManager cyApplicationManager = getService(bundleContext, CyApplicationManager.class);

		Context appContext = new Context();
		ViewHandler viewHandler = new ViewHandler(cyNetworkViewManager, visualMappingManager, visualStyleFactory, continousVisualMappingFunctionFactory, discreteVisualMappingFunctionFactory);

		//set defaults for attributes of newly added nodes
		registerService(bundleContext, new NodeDefaultsSetter(viewHandler.getNodeViewHandler(), appContext), AddedNodesListener.class, new Properties());

		//set defaults for the attributes for newly added edges
		registerService(bundleContext, new EdgeDefaultsSetter(viewHandler.getEdgeViewHandler(), appContext), AddedEdgesListener.class, new Properties());

		//add Use FluxViz in network context menu
		Properties switchProps = new Properties();
		switchProps.setProperty(PREFERRED_ACTION, "NEW");
		switchProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		switchProps.setProperty(MENU_GRAVITY, "6.0f");
		switchProps.setProperty(IN_MENU_BAR, "false");
		switchProps.setProperty(TITLE, "Use FluxViz");
		registerService(bundleContext, new UseFluxVizNetworkViewTaskFactory(viewHandler, appContext), 
				NetworkViewTaskFactory.class, switchProps);
		
		//add Set Type menu to node context menu
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

		//add Edit FluxViz Attributes to node context menu
		Properties editNodeAttrProps = new Properties();
		editNodeAttrProps.setProperty(PREFERRED_ACTION, "NEW");
		editNodeAttrProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		editNodeAttrProps.setProperty(MENU_GRAVITY, "10.0f");
		editNodeAttrProps.setProperty(IN_MENU_BAR, "false");
		editNodeAttrProps.setProperty(TITLE, "Edit FluxViz Node Attributes");
		registerService(bundleContext, new EditNodeAttributesTaskFactory(appContext), 
				NodeViewTaskFactory.class, editNodeAttrProps);
		
		//add Set Type menu to edge context menu
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

		//add Start menu to network context menu
		Properties startProps = new Properties();
		startProps.setProperty(PREFERRED_ACTION, "NEW");
		startProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		startProps.setProperty(MENU_GRAVITY, "10.0f");
		startProps.setProperty(IN_MENU_BAR, "false");
		startProps.setProperty(TITLE, "Start");
		registerService(bundleContext, new StartFlowNetworkViewTaskFactory(viewHandler, appContext, false), NetworkViewTaskFactory.class, startProps);

		//add Stop menu to network context menu
		Properties stopProps = new Properties();
		stopProps.setProperty(PREFERRED_ACTION, "NEW");
		stopProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		stopProps.setProperty(MENU_GRAVITY, "10.0f");
		stopProps.setProperty(IN_MENU_BAR, "false");
		stopProps.setProperty(TITLE, "Stop");
		registerService(bundleContext, new StopFlowNetworkViewTaskFactory(appContext), NetworkViewTaskFactory.class, stopProps);

		//add Restart menu to network context menu
		Properties restartProps = new Properties();
		restartProps.setProperty(PREFERRED_ACTION, "NEW");
		restartProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		restartProps.setProperty(MENU_GRAVITY, "10.0f");
		restartProps.setProperty(IN_MENU_BAR, "false");
		restartProps.setProperty(TITLE, "Restart");
		registerService(bundleContext, new StartFlowNetworkViewTaskFactory(viewHandler, appContext, true), NetworkViewTaskFactory.class, restartProps);
		
		//add Control menu to network context menu
		Properties controlProps = new Properties();
		controlProps.setProperty(PREFERRED_ACTION, "NEW");
		controlProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		controlProps.setProperty(MENU_GRAVITY, "10.0f");
		controlProps.setProperty(IN_MENU_BAR, "false");
		controlProps.setProperty(TITLE, "Controls");
		registerService(bundleContext, new ControlsMenuNetworkViewTaskFactory(appContext), NetworkViewTaskFactory.class, controlProps);
		
		viewHandler.createVisualMappings();
	}
}
