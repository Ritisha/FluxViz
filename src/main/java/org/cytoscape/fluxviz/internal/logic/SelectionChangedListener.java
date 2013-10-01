package org.cytoscape.fluxviz.internal.logic;

import java.util.ArrayList;
import java.util.List;

import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;

public class SelectionChangedListener implements RowsSetListener {

	Context appContext;

	public SelectionChangedListener(Context appContext)
	{
		this.appContext = appContext;
	}
	@Override
	public void handleEvent(RowsSetEvent e) {

		System.out.println("Some row has been set..");
		if(appContext.getControlTunables() != null)
		{
			List<CyRow> allRows;

			if(e.containsColumn(CyNetwork.SELECTED))
			{
				System.out.println("Verified that selected was changed..");
				if(e.getSource().equals(ColumnsCreator.DefaultNodeTable))
				{
					System.out.println("Verified that it was nodes selection being changed..");
					//we dont want to check just the ones which have been set, we want to check all the values.
					allRows = ColumnsCreator.DefaultNodeTable.getAllRows();
					List<Long> selectedNodesSUIDs = new ArrayList<Long>();
					System.out.println("Getting selected nodes..");
					for(CyRow currRow : allRows)
					{
						if(currRow.get(CyNetwork.SELECTED, Boolean.class))
						{
							selectedNodesSUIDs.add(currRow.get(CyIdentifiable.SUID, Long.class));
						}
					}
					System.out.println("Selected nodes are " + selectedNodesSUIDs);
					System.out.println("Setting in control tunables..");
					appContext.getControlTunables().setCurrentlySelectedNodes(selectedNodesSUIDs);
				}

				if(e.getSource().equals(ColumnsCreator.DefaultEdgeTable))
				{
					allRows = ColumnsCreator.DefaultEdgeTable.getAllRows();
					List<Long> selectedEdgesSUIDs = new ArrayList<Long>();
					for(CyRow currRow : allRows)
					{
						if(currRow.get(CyNetwork.SELECTED, Boolean.class))
						{
							selectedEdgesSUIDs.add(currRow.get(CyIdentifiable.SUID, Long.class));
						}
					}
					appContext.getControlTunables().setCurrentlySelectedEdges(selectedEdgesSUIDs);
				}
			}
		}
	}
}
