package org.cytoscape.fluxviz.internal.tasks;

import org.cytoscape.fluxviz.internal.logic.ColumnsCreator;
import org.cytoscape.fluxviz.internal.logic.Context;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.ProvidesTitle;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.BoundedDouble;
import org.cytoscape.work.util.ListSingleSelection;

public class ControlsMenuNetworkViewTask extends AbstractNetworkViewTask {

	CyNetworkView networkView;
	Context appContext;
	CyNetwork network;
	
	@Tunable(description = "Playback Speed", params = "slider=true")
	public BoundedDouble playbackSpeed;
	
	@Tunable(description = "Type of Molecule")
	public ListSingleSelection<String> typeOfMolecule;
	
	public ControlsMenuNetworkViewTask(CyNetworkView networkView, Context appContext)
	{
		super(networkView);
		this.appContext = appContext;
		this.networkView = networkView;
		this.network = networkView.getModel();
		
		playbackSpeed = new BoundedDouble(0.0, appContext.getSpeed(), 5.0, false, false);
		typeOfMolecule = new ListSingleSelection<String>("Kinase", "GTPAse", "ATPAse");
	}
	
	@Override
	public void run(TaskMonitor arg0) throws Exception {
		//put values picked up from tunables at the right places.
		appContext.setSpeed(playbackSpeed.getValue());
		System.out.println(typeOfMolecule.getSelectedValue());
	}
	
	@ProvidesTitle
	public String getTitle()
	{
		String networkName = ColumnsCreator.DefaultNetworkTable.getRow(network.getSUID()).get(CyNetwork.NAME, String.class);
		return networkName + " FluxViz Controls";
	}

}
