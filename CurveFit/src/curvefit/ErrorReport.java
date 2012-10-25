/*
 * ErrorReport.java
 *
 * Created on October 29, 2009, 1:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curvefit;

import java.awt.*;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author Sonal
 */

public class ErrorReport 
{
	JDialog errFrame;
	JScrollPane scrollPane;
	String data[][];
	int row;
	/** Creates a new instance of ErrorReport */
	public ErrorReport() 
	{
		errFrame = new JDialog((Frame)null,"Error Analysis...",true);
		row = 0;
		data = new String[3][4];
	}
	
	void show()
	{
		JPanel panel = new JPanel();
		String col[] = {"Technique","Curve Equation","RMS","SD"};

		JTable table = new JTable(data,col);


		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.gray);

		panel.setLayout(new BorderLayout());
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane scrollPane = new JScrollPane(table, v, h); 

		//scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		panel.add(scrollPane,BorderLayout.CENTER);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setShowGrid(true);

		errFrame.add(panel);
		errFrame.setLocation(250,250);
		errFrame.setSize(500,95);
		errFrame.setResizable(false);
		errFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		errFrame.setVisible(true);
	}
	
	void setRow(String technique,String eq,double rms,double sd)
	{
		java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
		data[row][0] = technique;
		data[row][1] = eq;
		data[row][2] = formatter.format(rms);
		data[row][3] = formatter.format(sd);
		row++;
	}
}
