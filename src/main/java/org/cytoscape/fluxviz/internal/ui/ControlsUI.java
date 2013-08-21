package org.cytoscape.fluxviz.internal.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cytoscape.fluxviz.internal.logic.Context;

public class ControlsUI extends JFrame implements ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Context appContext;
	private JSlider speedSlider;
	private JLabel speedSliderLabel;
	private JPanel controlsPanel;
	
	public ControlsUI(Context appContext)
	{
		super();
		this.appContext = appContext;
		setTitle("User Controls");
		setBackground(Color.GRAY);
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		
		controlsPanel = new JPanel();
		controlsPanel.setLayout(null);
		getContentPane().add(controlsPanel);
		
		speedSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 1);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(this);
		
		controlsPanel.add(speedSlider);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		appContext.setSleepTime((int)(10 / (speedSlider.getValue())));
	}

}
