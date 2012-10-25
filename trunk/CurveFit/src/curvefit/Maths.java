/*
 * Maths.java
 *
 * Created on November 10, 2009, 8:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curvefit;

import java.util.*;

/**
 *
 * @author Sonal
 */
public class Maths 
{
    double lead,temp;
    double system[][];
    double solution[];   
    ArrayList answer = new ArrayList();
    /** Creates a new instance of Maths */
    public Maths() 
    {
    	
    }
    public Maths(int n) 
    {
    	system = new double [n][n+1];
    	solution = new double[n];
    }
    public ArrayList gauss(double system[][],int n)
    {
     /*	Gauss Elimination Method	*/
    for(int i=0;i<n;i++)
	{
    	lead = system[i][i];
    	if(lead!=0)	//Check if leading element is not zero
        {
      		//Divide by whole row by zero
			for(int j=0;j<(n+1);j++)
			{
				system[i][j]=system[i][j]/lead;
			}
	            /*	Reducing the elements below leading 1 to 0		*/

			for(int k=i+1;k<n;k++)
			{
				temp=system[k][i];
				for(int j=0;j<(n+1);j++)
				{
					system[k][j]=system[k][j]-system[i][j]*temp;
	            }	
			}
	    }
        else //The leading element is 0.. So swap the row with next
        {
	        for(int j=0;j<(n+1);j++)
	        { 
		        temp=system[i][j];
		        system[i][j]=system[i+1][j];
		        system[i+1][j]=temp;
			}		
			i--;
        }	
	}
    
	/*	Solving the triangular system	*/
    	solution[n-1]=system[n-1][n];
    	for(int i=n-2;i>=0;i--)
    	{
    		double sum=0.0;
    		for(int j=n-1;j>i;j--)
    		{
    			sum+=solution[j]*system[i][j];
    		}
    		solution[i] = system[i][n]-sum;
    	}
   	for(int i=0;i<n;i++)
		answer.add(solution[i]);
    return answer;
    }
}
