package org.cytoscape.fluxviz.internal.logic;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.util.Properties;

import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeEdgeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTaskFactory;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;

public class CyActivatorHelper {
	
	CyServiceRegistrar cyServiceRegistrar;
	NodeViewHandler nodeViewHandler;
	EdgeViewHandler edgeViewHandler;
	
	public CyActivatorHelper(CyServiceRegistrar cyServiceRegistrar, NodeViewHandler nodeViewHandler, EdgeViewHandler edgeViewHandler)
	{
		this.cyServiceRegistrar = cyServiceRegistrar;
		this.nodeViewHandler = nodeViewHandler;
		this.edgeViewHandler = edgeViewHandler;
	}
	
	public void addNodeSetTypeMenus()
	{ 	
		Properties kinaseProps = new Properties();
	  	kinaseProps.setProperty(PREFERRED_ACTION, "NEW");
	  	kinaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
	  	kinaseProps.setProperty(MENU_GRAVITY, "6.0f");
	  	kinaseProps.setProperty(IN_MENU_BAR, "false");
	  	kinaseProps.setProperty(TITLE, SetTypeNodeViewTask.KINASE);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.KINASE, nodeViewHandler), 
	  			NodeViewTaskFactory.class, kinaseProps);
	  	
	  	System.out.println("registering node menus");
	  	
	  	Properties moleculesProps = new Properties();
	  	moleculesProps.setProperty(PREFERRED_ACTION, "NEW");
	  	moleculesProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
	  	moleculesProps.setProperty(MENU_GRAVITY, "7.0f");
	  	moleculesProps.setProperty(IN_MENU_BAR, "false");
	  	moleculesProps.setProperty(TITLE, SetTypeNodeViewTask.MOLECULES);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.MOLECULES, nodeViewHandler), 
	  			NodeViewTaskFactory.class, moleculesProps);
	  	
	  	Properties gtpaseProps = new Properties();
	  	gtpaseProps.setProperty(PREFERRED_ACTION, "NEW");
	  	gtpaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
	  	gtpaseProps.setProperty(MENU_GRAVITY, "8.0f");
	  	gtpaseProps.setProperty(IN_MENU_BAR, "false");
	  	gtpaseProps.setProperty(TITLE, SetTypeNodeViewTask.GTPASE);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.GTPASE, nodeViewHandler), 
	  			NodeViewTaskFactory.class, gtpaseProps);
	  	
	  	Properties receptorProps = new Properties();
	  	receptorProps.setProperty(PREFERRED_ACTION, "NEW");
	  	receptorProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
	  	receptorProps.setProperty(MENU_GRAVITY, "9.0f");
	  	receptorProps.setProperty(IN_MENU_BAR, "false");
	  	receptorProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR, nodeViewHandler), 
	  			NodeViewTaskFactory.class, receptorProps);
	  	
	  	Properties receptorTKinaseProps = new Properties();
	  	receptorTKinaseProps.setProperty(PREFERRED_ACTION, "NEW");
	  	receptorTKinaseProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
	  	receptorTKinaseProps.setProperty(MENU_GRAVITY, "10.0f");
	  	receptorTKinaseProps.setProperty(IN_MENU_BAR, "false");
	  	receptorTKinaseProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR_T_KINASE);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR_T_KINASE, nodeViewHandler), 
	  			NodeViewTaskFactory.class, receptorTKinaseProps);
	}
	
	public void addEdgeSetTypeMenus()
	{
		Properties activatingProps = new Properties();
		activatingProps.setProperty(PREFERRED_ACTION, "NEW");
		activatingProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		activatingProps.setProperty(MENU_GRAVITY, "10.0f");
		activatingProps.setProperty(IN_MENU_BAR, "false");
		activatingProps.setProperty(TITLE, SetTypeEdgeViewTask.ACTIVATING);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeEdgeViewTaskFactory(SetTypeEdgeViewTask.ACTIVATING, edgeViewHandler), 
	  			EdgeViewTaskFactory.class, activatingProps);
	  	
	  	Properties deactivatingProps = new Properties();
		deactivatingProps.setProperty(PREFERRED_ACTION, "NEW");
		deactivatingProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
		deactivatingProps.setProperty(MENU_GRAVITY, "10.0f");
		deactivatingProps.setProperty(IN_MENU_BAR, "false");
		deactivatingProps.setProperty(TITLE, SetTypeEdgeViewTask.DEACTIVATING);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeEdgeViewTaskFactory(SetTypeEdgeViewTask.DEACTIVATING, edgeViewHandler), 
	  			EdgeViewTaskFactory.class, deactivatingProps);
	}
}