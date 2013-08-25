package org.cytoscape.fluxviz.internal.ui;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.cytoscape.fluxviz.internal.logic.Context;

public class ControlsUI extends JDialog implements ChangeListener {

	private static final long serialVersionUID = 1L;
	Context appContext;
	private JSlider speedSlider;
	private JLabel speedSliderLabel;
	
	public ControlsUI(Context appContext)
	{
		super();
		this.appContext = appContext;
		setTitle("User Controls");
		setBackground(Color.GRAY);
		
		speedSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 10, appContext.getSleepTime()*10);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setMinorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(this);
				
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(speedSlider)
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(speedSlider)
        );
 
        setTitle("User Controls");
        pack();
		setAlwaysOnTop(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);	
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		appContext.setSleepTime((int)(10 / (speedSlider.getValue())));
	}
}
