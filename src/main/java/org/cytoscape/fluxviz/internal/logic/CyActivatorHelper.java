package org.cytoscape.fluxviz.internal.logic;

import static org.cytoscape.work.ServiceProperties.IN_MENU_BAR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.cytoscape.fluxviz.internal.tasks.CreateNewNodeTypeTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.CreateNewRuleNodeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetRuleNodeViewTaskFactory;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTask;
import org.cytoscape.fluxviz.internal.tasks.SetTypeNodeViewTaskFactory;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.NodeViewTaskFactory;
import org.osgi.framework.BundleContext;

public class CyActivatorHelper {
	
	BundleContext context;
	CyServiceRegistrar cyServiceRegistrar;
	
	public CyActivatorHelper(BundleContext context, CyServiceRegistrar cyServiceRegistrar)
	{
		this.context = context;
		this.cyServiceRegistrar = cyServiceRegistrar;
	}
	
	public void addNodeSetTypeMenus()
	{ 	
		Properties kinaseProps = new Properties();
	  	kinaseProps.setProperty(PREFERRED_ACTION, "NEW");
	  	kinaseProps.setProperty(PREFERRED_MENU, "Set Type[100]");
	  	kinaseProps.setProperty(MENU_GRAVITY, "6.0f");
	  	kinaseProps.setProperty(IN_MENU_BAR, "false");
	  	kinaseProps.setProperty(TITLE, SetTypeNodeViewTask.KINASE);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.KINASE), 
	  			NodeViewTaskFactory.class, kinaseProps);
	  	
	  	Properties moleculesProps = new Properties();
	  	moleculesProps.setProperty(PREFERRED_ACTION, "NEW");
	  	moleculesProps.setProperty(PREFERRED_MENU, "Set Type[100]");
	  	moleculesProps.setProperty(MENU_GRAVITY, "7.0f");
	  	moleculesProps.setProperty(IN_MENU_BAR, "false");
	  	moleculesProps.setProperty(TITLE, SetTypeNodeViewTask.MOLECULES);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.MOLECULES), 
	  			NodeViewTaskFactory.class, moleculesProps);
	  	
	  	Properties gtpaseProps = new Properties();
	  	gtpaseProps.setProperty(PREFERRED_ACTION, "NEW");
	  	gtpaseProps.setProperty(PREFERRED_MENU, "Set Type[100]");
	  	gtpaseProps.setProperty(MENU_GRAVITY, "8.0f");
	  	gtpaseProps.setProperty(IN_MENU_BAR, "false");
	  	gtpaseProps.setProperty(TITLE, SetTypeNodeViewTask.GTPASE);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.GTPASE), 
	  			NodeViewTaskFactory.class, gtpaseProps);
	  	
	  	Properties receptorProps = new Properties();
	  	receptorProps.setProperty(PREFERRED_ACTION, "NEW");
	  	receptorProps.setProperty(PREFERRED_MENU, "Set Type[100]");
	  	receptorProps.setProperty(MENU_GRAVITY, "9.0f");
	  	receptorProps.setProperty(IN_MENU_BAR, "false");
	  	receptorProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR), 
	  			NodeViewTaskFactory.class, receptorProps);
	  	
	  	Properties receptorTKinaseProps = new Properties();
	  	receptorTKinaseProps.setProperty(PREFERRED_ACTION, "NEW");
	  	receptorTKinaseProps.setProperty(PREFERRED_MENU, "Set Type[100]");
	  	receptorTKinaseProps.setProperty(MENU_GRAVITY, "10.0f");
	  	receptorTKinaseProps.setProperty(IN_MENU_BAR, "false");
	  	receptorTKinaseProps.setProperty(TITLE, SetTypeNodeViewTask.RECEPTOR_T_KINASE);
	  	
	  	cyServiceRegistrar.registerService(new SetTypeNodeViewTaskFactory(SetTypeNodeViewTask.RECEPTOR_T_KINASE), 
	  			NodeViewTaskFactory.class, receptorTKinaseProps);
	  	
	  	Properties createNewNodeTypeProps = new Properties();
	  	createNewNodeTypeProps.setProperty(PREFERRED_ACTION, "NEW");
	  	createNewNodeTypeProps.setProperty(PREFERRED_MENU, "Apps.FluxViz.Set Type");
	  	createNewNodeTypeProps.setProperty(MENU_GRAVITY, "11.0f");
	  	createNewNodeTypeProps.setProperty(IN_MENU_BAR, "false");
	  	createNewNodeTypeProps.setProperty(TITLE, "Create New");
	  	
	  	cyServiceRegistrar.registerService(new CreateNewNodeTypeTaskFactory(), 
	  			NodeViewTaskFactory.class, createNewNodeTypeProps);
	}
	
	public void addNodeSetRuleMenu()
	{
		CyTableManager cyTableManager = cyServiceRegistrar.getService(CyTableManager.class);
		CyTable ruleTable = cyTableManager.getTable(RuleTableCreator.SUID);
		List<CyRow> allRows = new ArrayList<CyRow>();
		allRows = ruleTable.getAllRows();
		int numRules = allRows.size();
		Properties[] ruleNamesProps = new Properties[numRules];
		int i = 0;
		
		for(CyRow currRow : allRows)
		{
			String rule = currRow.get(RuleTableCreator.NAME, String.class);
			int id = currRow.get(RuleTableCreator.ID, Integer.class);
			ruleNamesProps[i] = new Properties();
			ruleNamesProps[i].setProperty(PREFERRED_ACTION, "NEW");
			ruleNamesProps[i].setProperty(PREFERRED_MENU, "Set Rule[100]");
			ruleNamesProps[i].setProperty(MENU_GRAVITY, "6.0f");
			ruleNamesProps[i].setProperty(IN_MENU_BAR, "false");
			ruleNamesProps[i].setProperty(TITLE, rule);
			
			cyServiceRegistrar.registerService(new SetRuleNodeViewTaskFactory(rule, id), 
		  			NodeViewTaskFactory.class, ruleNamesProps[i]);
			i++;
		}
		
		Properties createNewRuleProps = new Properties();
		createNewRuleProps.setProperty(PREFERRED_ACTION, "NEW");
		createNewRuleProps.setProperty(PREFERRED_MENU, "Set Type[100]");
		createNewRuleProps.setProperty(MENU_GRAVITY, "11.0f");
		createNewRuleProps.setProperty(IN_MENU_BAR, "false");
		createNewRuleProps.setProperty(TITLE, "Create New");
	  	
	  	cyServiceRegistrar.registerService(new CreateNewRuleNodeViewTaskFactory(), 
	  			NodeViewTaskFactory.class, createNewRuleProps);
	}
}
