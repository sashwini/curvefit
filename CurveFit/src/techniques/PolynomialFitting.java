/*
 * LeastSquareParabola.java
 *
 * Created on October 31, 2009, 10:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package techniques;

import java.io.*;
import java.util.*;
import java.math.*;

import curvefit.*;
/**
 *
 * @author Sonal
 */
public class PolynomialFitting
{
    double rootMeanSquareError = 0.0,standardDeviation =0.0;
    int sizeOfData,degreeOfPolynomial;
    double X[];
    double Y[];
    double B[];
    double C[];//array for coefficients
    double P[];//array for powers of x
    double system[][];
    ArrayList solution = new ArrayList();
    ArrayList fittedData = new ArrayList();
    ArrayList actualData = new ArrayList();
    
    String equation;
    Point point;
    Maths maths;
    /** Creates a new instance of LeastSquareParabola */
    public PolynomialFitting(ArrayList data,int degree)
    {
        actualData = data;
        sizeOfData = actualData.size();
        degreeOfPolynomial=degree;
        X = new double [sizeOfData];
        Y = new double [sizeOfData];
        B = new double [degreeOfPolynomial+1];
        C = new double [degreeOfPolynomial+1];
        P = new double [(degreeOfPolynomial*2)+1];
        system = new double [degreeOfPolynomial+1][degreeOfPolynomial+2];
        maths=new Maths(degreeOfPolynomial+1);
        for(int i=0;i<sizeOfData;i++)
        {
            X[i]=((Point)actualData.get(i)).getX();
            Y[i]=((Point)actualData.get(i)).getY();
        }
		//initializing B vector to 0.0
		for(int i=0;i<(degreeOfPolynomial+1);i++)
		{
			B[i]=0.0;
		}
		//initializing P vector to 0.0
		for(int i=0;i<=(degreeOfPolynomial*2);i++)
		{
			P[i]=0.0;
		}
    }
    public ArrayList getFittedData()
    {
        findCurve();
        double xmin=X[0];
        double xmax=X[0];
        for(int i=0;i<X.length;i++)
        {
                if(xmax<(X[i]))
                {
                    xmax=X[i];
                }
                else
                if(xmin>(X[i]))
                {
                    xmin=X[i];
                }
        }

        double incr=(xmax-xmin)/sizeOfData;
        for(double x=xmin;x<=xmax;x+=incr)
        {
            double y=0.0;
            double xpower=1;	
            for(int i=0;i<solution.size();i++)
            {
                y+=C[i]*xpower;
            	xpower*=x;
            }	
            point=new Point(x,y);
            fittedData.add(point);
        }
        return fittedData;
    }

    public void findCurve()
    {
    	double power,x,y;
        int r,c;
    	for(int k=0;k<sizeOfData;k++)
    	{
    		y=Y[k];  
    		x=X[k]; 
    		power=1;
    		for(r=0;r<(degreeOfPolynomial+1);r++)
    		{
    			B[r]=B[r]+(y*power);
    			power=power*x;
    		}
    	}
        //P :powers array
    	P[0]=sizeOfData;
    	for(int k=0;k<sizeOfData;k++)
    	{
    		x=X[k];
    		power=X[k];
    		for(int j=1;j<=(degreeOfPolynomial*2);j++)
    		{
    			P[j]=P[j]+power;
    			power=power*x;
    		}
    	}
        for(r=0;r<(degreeOfPolynomial+1);r++)
        {
    		for(c=0;c<(degreeOfPolynomial+1);c++)
    		{
    			system[r][c]=P[r+c];
    		}
        system[r][c]=B[r];
    	}
 
    solution = maths.gauss(system,degreeOfPolynomial+1);
    for(int i=0;i<solution.size();i++)
    {
    	C[i]=((Double)solution.get(i)).doubleValue();
    }
    }

    public String getEquation()
    {
    	java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
        if(degreeOfPolynomial==1)
        {	
        	if(C[1]>=0)
        		equation="Y="+formatter.format(C[0])+"+"+formatter.format(C[1])+"X";
        	if(C[1]<0)
            	equation="Y="+formatter.format(C[0])+formatter.format(C[1])+"X";
        }
        if(degreeOfPolynomial>=2)
        {
        	if(C[1]>=0)
        		equation="Y="+formatter.format(C[0])+"+"+formatter.format(C[1])+"X";
        	if(C[1]<0)
            	equation="Y="+formatter.format(C[0])+formatter.format(C[1])+"X";
        	for(int i=2;i<solution.size();i++)
        	{
        		if(C[i]>=0)
        			equation+="+"+formatter.format(C[i])+"X^"+i;
        		if(C[i]<0)
        			equation+=formatter.format(C[i])+"X^"+i;
        	}
    	}    
        return equation;
    }

    public double getRMS()
    {
      	double err=0.0;
        for(int i=0;i<sizeOfData;i++)
        {
        	err=err+Math.pow(((Point)fittedData.get(i)).getY()-((Point)actualData.get(i)).getY(),2);
        }
        rootMeanSquareError=(double)Math.sqrt(err/sizeOfData);
        return rootMeanSquareError;
    }

    public double getSD()
    {
    	double sum=0.0,sumY=0.0, meanY;
        for(int i=0;i<fittedData.size();i++)
        {
        	sumY+=((Point)fittedData.get(i)).getY();
        }
        meanY=(double)sumY/sizeOfData;
        for(int i=0;i<fittedData.size();i++)
        {
        	sum+=Math.pow((((Point)fittedData.get(i)).getY()-meanY),2);
        }
        standardDeviation=Math.sqrt(sum/fittedData.size());
        return standardDeviation;
    }
}
