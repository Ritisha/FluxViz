package org.cytoscape.fluxviz.internal;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.EdgeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.Evaluator;
import org.cytoscape.fluxviz.internal.logic.NodeDefaultsSetter;
import org.cytoscape.fluxviz.internal.logic.ViewHandler;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.FluxVizNetworkViewTaskFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.events.AddedEdgesListener;
import org.cytoscape.model.events.AddedNodesListener;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
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
	public void start(BundleContext context) throws Exception {

		VisualMappingManager visualMappingManager = getService(context, VisualMappingManager.class);
		CyNetworkManager cyNetworkManager = getService(context, CyNetworkManager.class);
		CyServiceRegistrar cyServiceRegistrar = getService(context, CyServiceRegistrar.class);
		CyNetworkViewManager cyNetworkViewManager = getService(context, CyNetworkViewManager.class);
		VisualMappingFunctionFactory continousVisualMappingFunctionFactory = getService(context, VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualStyleFactory visualStyleFactory = getService(context, VisualStyleFactory.class);

		ViewHandler viewHandler = new ViewHandler(cyNetworkViewManager, visualMappingManager, visualStyleFactory, continousVisualMappingFunctionFactory);


		//add app-specific columns to default tables
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
		allNets = cyNetworkManager.getNetworkSet();

		for(CyNetwork currNet : allNets)
		{
			ColumnsCreator.createColumns(currNet, viewHandler);		
		}

		cyServiceRegistrar.registerService(new ColumnsCreator(viewHandler), NetworkAddedListener.class, new Properties() );

		//set defaults for attributes of newly added nodes
		cyServiceRegistrar.registerService(new NodeDefaultsSetter(viewHandler.getNodeViewHandler()), AddedNodesListener.class, new Properties());

		//set defaults for the attributes for newly added edges
		cyServiceRegistrar.registerService(new EdgeDefaultsSetter(viewHandler.getEdgeViewHandler()), AddedEdgesListener.class, new Properties());

		//add fluxviz menu to node context menu
		Properties kinaseProps = new Properties();
		kinaseProps.setProperty(PREFERRED_ACTION, "NEW");
		kinaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		kinaseProps.setProperty(MENU_GRAVITY, "6.0f");
		kinaseProps.setProperty(IN_MENU_BAR, "false");
		kinaseProps.setProperty(TITLE, SetTypeNodeViewTask.KINASE);

		cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.KINASE, viewHandler), 
				NodeViewTaskFactory.class, kinaseProps);

		Properties moleculesProps = new Properties();
		moleculesProps.setProperty(PREFERRED_ACTION, "NEW");
		moleculesProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		moleculesProps.setProperty(MENU_GRAVITY, "7.0f");
		moleculesProps.setProperty(IN_MENU_BAR, "false");
		moleculesProps.setProperty(TITLE, SetTypeNodeViewTask.MOLECULES);

		cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.MOLECULES, viewHandler), 
				NodeViewTaskFactory.class, moleculesProps);

		Properties gtpaseProps = new Properties();
		gtpaseProps.setProperty(PREFERRED_ACTION, "NEW");
		gtpaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		gtpaseProps.setProperty(MENU_GRAVITY, "8.0f");
		gtpaseProps.setProperty(IN_MENU_BAR, "false");
		gtpaseProps.setProperty(TITLE, SetTypeNodeViewTask.GTPASE);

		cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.GTPASE, viewHandler), 
				NodeViewTaskFactory.class, gtpaseProps);

		Properties receptorProps = new Properties();
		receptorProps.setProperty(PREFERRED_ACTION, "NEW");
		receptorProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		receptorProps.setProperty(MENU_GRAVITY, "9.0f");
		receptorProps.setProperty(IN_MENU_BAR, "false");
		receptorProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR);

		cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR, viewHandler), 
				NodeViewTaskFactory.class, receptorProps);

		Properties receptorTKinaseProps = new Properties();
		receptorTKinaseProps.setProperty(PREFERRED_ACTION, "NEW");
		receptorTKinaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		receptorTKinaseProps.setProperty(MENU_GRAVITY, "10.0f");
		receptorTKinaseProps.setProperty(IN_MENU_BAR, "false");
		receptorTKinaseProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR_T_KINASE);

		cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR_T_KINASE, viewHandler), 
				NodeViewTaskFactory.class, receptorTKinaseProps);

		Properties phosphataseProps = new Properties();
		phosphataseProps.setProperty(PREFERRED_ACTION, "NEW");
		phosphataseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		phosphataseProps.setProperty(MENU_GRAVITY, "10.0f");
		phosphataseProps.setProperty(IN_MENU_BAR, "false");
		phosphataseProps.setProperty(TITLE, SetTypeNodeViewTask.PHOSPHATASE);

		cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.PHOSPHATASE, viewHandler), 
				NodeViewTaskFactory.class, phosphataseProps);	

		//add fluxviz menu to edge context menu
		Properties activatingProps = new Properties();
		activatingProps.setProperty(PREFERRED_ACTION, "NEW");
		activatingProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		activatingProps.setProperty(MENU_GRAVITY, "10.0f");
		activatingProps.setProperty(IN_MENU_BAR, "false");
		activatingProps.setProperty(TITLE, SetTypeEdgeViewTask.ACTIVATING);

		cyServiceRegistrar.registerService(new SetTypeEdgeViewTaskFactory(SetTypeEdgeViewTask.ACTIVATING, viewHandler.getEdgeViewHandler()), 
				EdgeViewTaskFactory.class, activatingProps);

		Properties deactivatingProps = new Properties();
		deactivatingProps.setProperty(PREFERRED_ACTION, "NEW");
		deactivatingProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		deactivatingProps.setProperty(MENU_GRAVITY, "10.0f");
		deactivatingProps.setProperty(IN_MENU_BAR, "false");
		deactivatingProps.setProperty(TITLE, SetTypeEdgeViewTask.DEACTIVATING);

		cyServiceRegistrar.registerService(new SetTypeEdgeViewTaskFactory(SetTypeEdgeViewTask.DEACTIVATING, viewHandler.getEdgeViewHandler()), 
				EdgeViewTaskFactory.class, deactivatingProps);	

		//add fluxviz menu to network context menu
		Properties startProps = new Properties();
		startProps.setProperty(PREFERRED_ACTION, "NEW");
		startProps.setProperty(PREFERRED_MENU, "Apps.FluxViz");
		startProps.setProperty(MENU_GRAVITY, "10.0f");
		startProps.setProperty(IN_MENU_BAR, "false");
		startProps.setProperty(TITLE, "Start1");

		cyServiceRegistrar.registerService(new FluxVizNetworkViewTaskFactory(viewHandler, startProps), NetworkViewTaskFactory.class, startProps);

		//add the continuous visual mapping for node color mapped with currOutput
		viewHandler.createVisualMapping();
		viewHandler.createFluxVizStyle();
	}
}
