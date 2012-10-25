/*
 * PreferencesWindow.java
 *
 * Created on October 25, 2009, 12:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curvefit;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Sonal
 */
public class PreferencesWindow implements ActionListener
{
    JDialog editFrame;
    JCheckBox cbBars,cbQuadrant,cbMultipleTech;
    JButton okButton,cancelButton;
    /** Creates a new instance of PreferencesWindow */
    public PreferencesWindow()
    {
        	editFrame = new JDialog((Frame)null,"Edit Preferences...",true);
   
                JLabel barsLabel = new JLabel("Show Error Bars");
                cbBars = new JCheckBox();
                
                JLabel quadrantLabel = new JLabel("Multiple Quardrants");
                cbQuadrant = new JCheckBox();
                
                JLabel multipleTechLabel = new JLabel("Multiple Techniques");
                cbMultipleTech = new JCheckBox();
                
		Container content = editFrame.getContentPane();
                GridBagLayout gbag=new GridBagLayout();
                
                JPanel panel = new JPanel(gbag);
                
                GridBagConstraints constraint=new GridBagConstraints();
                constraint.fill = GridBagConstraints.BOTH; 
                
                gbag.setConstraints(cbBars,constraint);  
                constraint.gridwidth = GridBagConstraints.REMAINDER;
                gbag.setConstraints(barsLabel,constraint);
                constraint.gridwidth = GridBagConstraints.RELATIVE;  
                
                gbag.setConstraints(cbMultipleTech,constraint);
                constraint.gridwidth = GridBagConstraints.REMAINDER;
                gbag.setConstraints(multipleTechLabel,constraint);
                constraint.gridwidth = GridBagConstraints.RELATIVE;  
                
                gbag.setConstraints(cbQuadrant,constraint);
                constraint.gridwidth = GridBagConstraints.REMAINDER;
                gbag.setConstraints(quadrantLabel,constraint);
       
                okButton = new JButton("OK");
                cancelButton = new JButton("Cancel");
                okButton.addActionListener(this);
                cancelButton.addActionListener(this);
                
                panel.add(cbBars);
                panel.add(barsLabel);
                panel.add(cbMultipleTech);
                panel.add(multipleTechLabel);
                panel.add(cbQuadrant);
                panel.add(quadrantLabel);
                panel.add(okButton);
                panel.add(cancelButton);
                
		editFrame.add(panel);

                editFrame.setLocation(250,250);
		editFrame.setSize(350,150);
		editFrame.setResizable(false);
		editFrame.setVisible(true);
     
		editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource().equals(okButton))
        {
            //write the changes to config file
        }
        
        if(ae.getSource().equals(cancelButton))
        {
            //System.out.print("cancel edit");
            //editFrame.setVisible(false);
            editFrame.dispose();
        }
    }
}
