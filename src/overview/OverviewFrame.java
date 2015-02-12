package overview;

import java.awt.*;
import javax.swing.*;
import connector.*;
import line.ConnectLine;
import javax.swing.border.EtchedBorder;


public class OverviewFrame {
	protected JTabbedPane tabbedPane;

	public OverviewFrame(JTabbedPane tp) {
		this.tabbedPane = tp;
		init();
	}

	protected void init() {
		initConnectors();
	}

	protected ConnectorContainer initConnectors() {
		JConnector[] connectors = new JConnector[2];
		String[][] table1cols = {{"ID", "ID"},
                                        {"Name", "Varchar"},
                                        {"Gruppe", "Gruppe"}};
		String[][] table2cols = {{"ID", "ID"},
                                        {"Bezeichnung", "Varchar"}};
		
		
		DBTablePanel b1 = new DBTablePanel("User", table1cols);
                

		DBTablePanel b2 = new DBTablePanel("Gruppe", table2cols);

		
		
		
		connectors[0] = new JConnector(b2, 25, b1, 62,
				ConnectLine.LINE_ARROW_SOURCE,
				JConnector.CONNECT_LINE_TYPE_RECTANGULAR, Color.red);

		ConnectorContainer cc = new ConnectorContainer(connectors);
		tabbedPane.addTab("Übersicht", null, cc, null);

		cc.add(b1);
		cc.add(b2);

		cc.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		return cc;

	}
}
