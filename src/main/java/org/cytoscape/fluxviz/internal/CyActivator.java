package org.cytoscape.fluxviz.internal;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.CyActivatorHelper;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		CyTableFactory cyTableFactory = getService(context, CyTableFactory.class);
		CyTableManager cyTableManager = getService(context, CyTableManager.class);
		CyNetworkManager cyNetworkManager = getService(context, CyNetworkManager.class);
		CyServiceRegistrar cyServiceRegistrar = getService(context, CyServiceRegistrar.class);
		CyActivatorHelper helper = new CyActivatorHelper(context, cyServiceRegistrar);
	
		//add app-specific columns to default tables
		Set<CyNetwork> allNets = new HashSet<CyNetwork>();
		allNets = cyNetworkManager.getNetworkSet();
		
		for(CyNetwork currNet : allNets)
		{
			ColumnsCreator.createColumns(currNet);
		}
		
		cyServiceRegistrar.registerService(new ColumnsCreator(), NetworkAddedListener.class, new Properties() );

		//add fluxviz menu to node context menu
		helper.addNodeSetTypeMenus();		
	  	  	
		//add fluxviz menu to edge context menu
		
		//add fluxviz menu to network context menu	
	}

}
