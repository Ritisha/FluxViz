package org.cytoscape.fluxviz.internal.ui;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cytoscape.fluxviz.internal.logic.Context;

public class ControlsUI extends JFrame implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Context appContext;
	
	public ControlsUI(Context appContext)
	{
		this.appContext = appContext;
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {

	}

}
