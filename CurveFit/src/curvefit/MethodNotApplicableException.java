package curvefit;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MethodNotApplicableException extends Exception 
{
	MethodNotApplicableException()
	{
		JOptionPane.showMessageDialog(null,"The method is not applicable for given data.","Application Error....",JOptionPane.ERROR_MESSAGE);
		
	}
	
}
