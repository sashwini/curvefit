/*
 * Main.java
 *
 * Created on October 25, 2009, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curvefit;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Sonal
 */
public class Main implements MenuListener,ActionListener,ListSelectionListener,ChangeListener
{
	JPanel mainPanel,namePanel,compPanel;
	JFrame frame;
	JMenuBar menuBar;
	JMenu fileMenu,editMenu,helpMenu;
	JTextField nameTextField;
	JList techniqueList;
	JButton browseButton,plotButton;
	JLabel sliderlabel,sliderlabelM;
	JSlider slider,sliderM;

	int value=1,valueM=1;
	Object values[];
	
	public Main()
	{
		frame = new JFrame("Curve Fit");

		//creating menus
		menuBar = new JMenuBar();
		fileMenu = new JMenu("New Dataset");
		//JMenuItem
		fileMenu.addMenuListener(this);
		fileMenu.add(new JSeparator());

		editMenu = new JMenu("Edit Preferences");
		editMenu.addMenuListener(this);
		editMenu.add(new JSeparator());

		helpMenu = new JMenu("Help");
		helpMenu.addMenuListener(this);
		helpMenu.add(new JSeparator());

		menuBar.add(fileMenu);
		//menuBar.add(editMenu);
		menuBar.add(helpMenu);

		/////////////container
		Container content = frame.getContentPane();

		//mainPanel contains all panels
		mainPanel = new JPanel(new BorderLayout());

		//namePanel for titles
		namePanel = new JPanel(new FlowLayout());
		JLabel title = new JLabel("Curve Fit");
		title.setForeground(new Color(200,0,0));
		title.setFont(new Font("Comic sans",0,39));
		namePanel.add(title,BorderLayout.WEST);

		//compPanel for all components
		GridBagLayout gbag=new GridBagLayout();
		compPanel = new JPanel(gbag);

		GridBagConstraints constraint=new GridBagConstraints();
		constraint.fill = GridBagConstraints.BOTH;

		JLabel nameLabel = new JLabel("File Name: ");
		gbag.setConstraints(nameLabel,constraint);

		nameTextField = new JTextField(20);
		gbag.setConstraints(nameTextField,constraint);

		browseButton = new JButton("Browse...");
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		gbag.setConstraints(browseButton,constraint);
		browseButton.setSize(20,20);
		browseButton.addActionListener(this);

		compPanel.add(nameLabel);
		compPanel.add(nameTextField);
		compPanel.add(browseButton);

		JLabel techLabel = new JLabel("Technique: ");
		gbag.setConstraints(techLabel,constraint);

		String techniques[]={"Least Square Line","Least Square Parabola","Power Fit",
				"Data Linearization Method","Polynomial Fitting"}; 

		techniqueList = new JList(techniques);
		JScrollPane scrollPane=new JScrollPane(techniqueList);
		techniqueList.setVisibleRowCount(4);
		techniqueList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		techniqueList.addListSelectionListener(this);
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		gbag.setConstraints(scrollPane,constraint);

		compPanel.add(techLabel);
		compPanel.add(scrollPane);
		namePanel.setBounds(100,100,150,150);

		sliderlabel = new JLabel("Degree Of Polynomial :");
		slider = new JSlider(1,5);
		slider.setValue(1);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setEnabled(false);

		sliderlabelM = new JLabel("Degree of Power Fit    :");
		sliderM = new JSlider(1,5);
		sliderM.setValue(1);
		sliderM.setPaintLabels(true);
		sliderM.setMajorTickSpacing(1);
		sliderM.setEnabled(false);

		constraint.fill = GridBagConstraints.BOTH;
		compPanel.add(sliderlabel);
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		gbag.setConstraints(slider,constraint);
		compPanel.add(slider);

		constraint.fill = GridBagConstraints.BOTH;
		compPanel.add(sliderlabelM);
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		gbag.setConstraints(sliderM,constraint);
		compPanel.add(sliderM);
		
		content.add(compPanel);

		///////////for slider////////////
		slider.addChangeListener(this);
		sliderM.addChangeListener(this);
		
		JPanel panel = new JPanel(new FlowLayout());
		plotButton = new JButton("Plot");
		plotButton.addActionListener(this);
		panel.add(plotButton);

		mainPanel.add(namePanel,BorderLayout.NORTH);
		mainPanel.add(compPanel,BorderLayout.CENTER);
		content.add(mainPanel);
		content.add(panel, BorderLayout.SOUTH);

		frame.setJMenuBar(menuBar);
		frame.setSize(400,400);
		frame.setBounds(100,200,600,400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}  
     
	public void menuCanceled(MenuEvent me)
	{
	}
    
	public void menuDeselected(MenuEvent me)
	{

	}
    
	public void menuSelected(MenuEvent me)
	{
		if(me.getSource().equals(fileMenu))
		{
			//code to clear all previous selections
			nameTextField.setText(null);
			techniqueList.clearSelection();

		}
		if(me.getSource().equals(helpMenu))
		{
			//code to display help window
			try
			{
				new HelpWindow();
			}
			catch(Exception e)
			{}
		}
	}
    
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource().equals(browseButton))
		{
			//code to display filedialog box
			FileDialog fileDialog = new FileDialog(frame,"Select Data File");
			fileDialog.setVisible(true);
			fileDialog.setMode(FileDialog.LOAD);
			String dir = fileDialog.getDirectory();
			String fileName = fileDialog.getFile();
			if(dir.equals(null) && fileName.equals(null))
			{
				nameTextField.setText(null);
			}
			else
			{
				nameTextField.setText(dir+fileName);
			}
		}
		if(ae.getSource().equals(plotButton))
		{
			boolean ok = true;
			//code to display plot window
			PlotWindow plot = new PlotWindow(); 
			String fileName = nameTextField.getText();
			Object techniques[] = new String[6];
			techniques = techniqueList.getSelectedValues();   
			int degree = slider.getValue();
			int degreeM = sliderM.getValue();
			System.out.println("degree-->"+degree);
			System.out.println("M-->"+degreeM);
			System.out.println("Filename-->"+fileName);

			//System.out.print(degree);
			if(!fileName.endsWith(".txt"))
			{
				ok = false;
				JOptionPane.showMessageDialog(null,"File format not supported.....","Application Error....",JOptionPane.ERROR_MESSAGE);
			}
			if(nameTextField.getText().equals(""))
			{
				ok = false;
				System.out.print("blank file nm");
				JOptionPane.showMessageDialog(null,"File name can not be left blank.....","Application Error....",JOptionPane.ERROR_MESSAGE);
			}
			//JOptionPane.showMessageDialog(null,"length"+techniques.length,"Application Error....",JOptionPane.ERROR_MESSAGE);
			if(techniques.length > 3)
			{
				ok = false;
				//JOptionPane.showMessageDialog(null,"So many tecniques not allowed");
				JOptionPane.showMessageDialog(null,"So many techniques not allowed","Application Error....",JOptionPane.ERROR_MESSAGE);
			}
			if(techniques.length == 0)
			{
				ok = false;
				//JOptionPane.showMessageDialog(null,"So many tecniques not allowed");
				JOptionPane.showMessageDialog(null,"No technique is selected","Application Error....",JOptionPane.ERROR_MESSAGE);
			}
			if(ok==true)
			{
				plot.processInput(fileName,techniques,degree,degreeM);
			}
		}
	}
    
	public void valueChanged(ListSelectionEvent lse)
	{
		values = techniqueList.getSelectedValues();
		//JOptionPane.showMessageDialog(null,values);

		boolean poly = false;
		boolean power = false;
		
		for(int i=0;i<values.length;i++)
		{
			if("Polynomial Fitting"==(String)values[i])
			{
				poly = true;
				//slider.setEnabled(true);
			}
			if("Power Fit"==(String)values[i])
			{
				power = true;
				//sliderM.setEnabled(true);
			}
			
		}
		slider.setEnabled(poly);
		sliderM.setEnabled(power);
	}
    
	public void stateChanged(ChangeEvent ce)
	{
		JSlider slider1=(JSlider)ce.getSource();
		value=slider1.getValue();
		JSlider slider2=(JSlider)ce.getSource();
		valueM=slider2.getValue();
		//JOptionPane.showMessageDialog(null,value);
	}
    
	/**
	 * @param args the command line arguments
	 */
	
	public static void main(String[] args)
	{
		//To do code application logic here
		new Main();
	}
    
}
    