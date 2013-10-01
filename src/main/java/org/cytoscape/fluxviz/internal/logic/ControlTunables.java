package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.fluxviz.internal.ui.ControlsDialog;
import org.cytoscape.model.CyRow;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;
import org.cytoscape.work.util.BoundedDouble;
import org.cytoscape.work.util.BoundedInteger;
import org.cytoscape.work.util.ListSingleSelection;

public class ControlTunables implements TunableValidator {

	public BoundedDouble playbackSpeed;	
	public ListSingleSelection<String> typeOfMolecule;
	public Boolean constitutivelyActive;
	public Boolean exceedDefaultValues;
	public ListSingleSelection<String> initialActivityLevel;
	public BoundedDouble continousInitialActivityLevel;
	public Boolean binaryInitialActivityLevel;
	public BoundedInteger integerInitialActivityLevel;
	public BoundedDouble moleculeConcentration;
	public ListSingleSelection<String> typeOfEdge;
	public BoundedDouble edgeEfficiency;
	
	Context appContext;
	ControlsDialog controlsDialog;
	List<Long> currentlySelectedNodes;
	List<Long> currentlySelectedEdges;
	
	public ControlTunables(Context appContext, ControlsDialog controlsDialog)
	{
		this.appContext = appContext;
		this.controlsDialog = controlsDialog;
		currentlySelectedNodes = new ArrayList<Long>();
		currentlySelectedEdges = new ArrayList<Long>();
		
		playbackSpeed = new BoundedDouble(0.0, appContext.getSpeed(), 5.0, false, false);
		typeOfMolecule = new ListSingleSelection<String>("Kinase", "Phosphatase", "GTPase", "Transcription Factor", "Ligands", "Small Mol Activator", "Small Mol Inhibitor", "Enzyme1", "Enzyme2", "Enzyme3");
		constitutivelyActive = new Boolean(false);
		exceedDefaultValues = new Boolean(false);
		initialActivityLevel = new ListSingleSelection<String>("Continous", "Binary", "Integer");
		continousInitialActivityLevel = new BoundedDouble(0.0, 0.0, 1.0, false, false);
		binaryInitialActivityLevel = new Boolean(false);
		integerInitialActivityLevel = new BoundedInteger(0, 0, 100, false, false);
		moleculeConcentration = new BoundedDouble(0.0, 0.0, 100.0, false, false);
		typeOfEdge = new ListSingleSelection<String>("Activating", "Deactivating");
		edgeEfficiency = new BoundedDouble(0.0, 0.0, 100.0, false, false);
	}

	public List<Long> getCurrentlySelectedNodes() {
		return currentlySelectedNodes;
	}

	public void setCurrentlySelectedNodes(List<Long> currentlySelectedNodes) {
		System.out.println("Set currentlySelectedNodes");
		this.currentlySelectedNodes = currentlySelectedNodes;
		setTypeOfMolecule(null);
		this.controlsDialog.repaint();
		//setInitialActivityLevel(null);
		//setMoleculeConcentration(null);
	}

	public List<Long> getCurrentlySelectedEdges() {
		return currentlySelectedEdges;
	}

	public void setCurrentlySelectedEdges(List<Long> currentlySelectedEdges) {
		this.currentlySelectedEdges = currentlySelectedEdges;
		//setTypeOfEdge(null);
		//setEdgeEfficiency(null);
	}

	@Tunable(description = "Playback speed in [seconds/cycle]", params = "slider=true")
	public BoundedDouble getPlaybackSpeed() {
		return playbackSpeed;
	}
	public void setPlaybackSpeed(BoundedDouble playbackSpeed) {
		appContext.setSpeed(playbackSpeed.getValue());
		this.playbackSpeed = playbackSpeed;
	}
	
	//Node Tunables.
	
	@Tunable(description = "Type of Molecule", groups = "MyNode Attributes")
	public ListSingleSelection<String> getTypeOfMolecule() {
		return typeOfMolecule;
	}
	public void setTypeOfMolecule(ListSingleSelection<String> typeOfMolecule) {
		
		CyRow row;
		System.out.println("In setTypeOfMolecule..");
		if(typeOfMolecule == null) 
		{
			System.out.println("its null");
			//User selected. 
			if(this.currentlySelectedNodes.size() != 1)
			{
				//no nodes or more than 1 node are selected, display nothing
				this.typeOfMolecule.setSelectedValue(null);
			}
			else
			{
				System.out.println("Its 1!");
				//one node selected, display it's value
				row = ColumnsCreator.DefaultNodeTable.getRow(currentlySelectedNodes.get(0));
				this.typeOfMolecule.setSelectedValue(row.get(ColumnsCreator.NODE_TYPE, String.class));
				System.out.println(this.typeOfMolecule.getSelectedValue());
			}
		}
		else
		{
			System.out.println("Not null..");
			String newType = typeOfMolecule.getSelectedValue();
			System.out.println("newType is " + newType);
			for(Long currNode : currentlySelectedNodes)
			{
				System.out.println("For node " + currNode);
				row = ColumnsCreator.DefaultNodeTable.getRow(currNode);
				row.set(ColumnsCreator.NODE_TYPE, newType);
			}
			this.typeOfMolecule = typeOfMolecule;
		}				
	}
	
	@Tunable(description = "Constitutively active", groups = "MyNode Attributes")
	public Boolean getConstitutivelyActive() {
		return constitutivelyActive;
	}
	public void setConstitutivelyActive(Boolean constitutivelyActive) {
		this.constitutivelyActive = constitutivelyActive;
	}
	
	@Tunable(description = "Exceed default values", groups = "MyNode Attributes")
	public Boolean getExceedDefaultValues() {
		return exceedDefaultValues;
	}
	public void setExceedDefaultValues(Boolean exceedDefaultValues) {
		this.exceedDefaultValues = exceedDefaultValues;
	}
	
	@Tunable(description = "Initial activity level", groups = "MyNode Attributes")
	public ListSingleSelection<String> getInitialActivityLevel() {
		return initialActivityLevel;
	}
	public void setInitialActivityLevel(
			ListSingleSelection<String> initialActivityLevel) {
		this.initialActivityLevel = initialActivityLevel;
	}
	
	@Tunable(description = "Continous Initial Activity Level", params = "slider=true", dependsOn = "initialActivityLevel=Continous", groups = "MyNode Attributes")
	public BoundedDouble getContinousInitialActivityLevel() {
		return continousInitialActivityLevel;
	}
	public void setContinousInitialActivityLevel(
			BoundedDouble continousInitialActivityLevel) {
		this.continousInitialActivityLevel = continousInitialActivityLevel;
	}
	
	@Tunable(description = "Binary Initial Activity Level", dependsOn = "initialActivityLevel=Binary", groups = "MyNode Attributes")
	public Boolean getBinaryInitialActivityLevel() {
		return binaryInitialActivityLevel;
	}
	public void setBinaryInitialActivityLevel(Boolean binaryInitialActivityLevel) {
		this.binaryInitialActivityLevel = binaryInitialActivityLevel;
	}
	
	@Tunable(description = "Integer Initial Activity Level", params = "slider=true", dependsOn = "initialActivityLevel=Integer", groups = "MyNode Attributes")
	public BoundedInteger getIntegerInitialActivityLevel() {
		return integerInitialActivityLevel;
	}
	public void setIntegerInitialActivityLevel(
			BoundedInteger integerInitialActivityLevel) {
		this.integerInitialActivityLevel = integerInitialActivityLevel;
	}
	
	@Tunable(description = "Molecule concentration as % of normal", params = "slider=true", groups = "MyNode Attributes")
	public BoundedDouble getMoleculeConcentration() {
		return moleculeConcentration;
	}
	public void setMoleculeConcentration(BoundedDouble moleculeConcentration) {
		this.moleculeConcentration = moleculeConcentration;
	}
	
	//Edge tunables
	
	@Tunable(description = "Type of edge", groups = "MyEdge Attributes")
	public ListSingleSelection<String> getTypeOfEdge() {
		return typeOfEdge;
	}
	public void setTypeOfEdge(ListSingleSelection<String> typeOfEdge) {
		this.typeOfEdge = typeOfEdge;
	}
	
	@Tunable(description = "Edge efficiency as % of normal", params = "slider=true", groups = "MyEdge Attributes")
	public BoundedDouble getEdgeEfficiency() {
		return edgeEfficiency;
	}
	public void setEdgeEfficiency(BoundedDouble edgeEfficiency) {
		this.edgeEfficiency = edgeEfficiency;
	}
	
	@Override
	public ValidationState getValidationState(Appendable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
