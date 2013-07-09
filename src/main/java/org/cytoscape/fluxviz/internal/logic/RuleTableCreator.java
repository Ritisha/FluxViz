package org.cytoscape.fluxviz.internal.logic;

import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;

public class RuleTableCreator {
	
	CyTableFactory cyTableFactory;
	CyTableManager cyTableManager;
	public static String NAME;
	public static String NUM_OF_INPUTS;
	public static String RULE;
	public static String ID;
	private String RULE_0;
	private String RULE_1;
	private String RULE_2;
	public static Long SUID;
	
	
	public RuleTableCreator(CyTableFactory cyTableFactory, CyTableManager cyTableManager)
	{
		this.cyTableFactory = cyTableFactory;
		this.cyTableManager = cyTableManager;
		NAME = "Name";
		NUM_OF_INPUTS = "Number of inputs";
		RULE = "Rule";
		ID = "ID";
		RULE_0 = "$output = 0.5";
		RULE_1 = "if $input >= 0.5, $output = $output + 0.01, else $output = $output - 0.01";
		RULE_2 = "if $input <= 0.5, $output = $output + 0.01, else $output = $output - 0.01";
	}
	
	public void createRuleTable()
	{
		CyTable ruleTable = cyTableFactory.createTable("Rule Table", ID, Integer.class, true, true);
		cyTableManager.addTable(ruleTable);
		SUID = ruleTable.getSUID();
		
		//add columns to the table
		ruleTable.createColumn(NAME, String.class, true);
		ruleTable.createColumn(NUM_OF_INPUTS, Integer.class, true);
		ruleTable.createColumn(RULE, String.class, true);
		
		//add default rows in table
		CyRow row1 = ruleTable.getRow(new Integer(0));
		CyRow row2 = ruleTable.getRow(new Integer(1));
		CyRow row3 = ruleTable.getRow(new Integer(2));
		
		//populate the rows
		row1.set(NAME, "Default Edge");
		row1.set(NUM_OF_INPUTS, 0);
		row1.set(RULE, RULE_0);
		
		row2.set(NAME, "Activating Edge Rule");
		row2.set(NUM_OF_INPUTS, 1);
		row2.set(RULE, RULE_1);
		
		row3.set(NAME, "Deactivating Edge Rule");
		row3.set(NUM_OF_INPUTS, 1);
		row3.set(RULE, RULE_2);		
	}
}