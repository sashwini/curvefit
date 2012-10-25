/*
 * DataLinearization.java
 *
 * Created on October 31, 2009, 10:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package techniques;

import java.io.*;
import java.util.*;
import curvefit.*;

import java.math.*;

/**
 *
 * @author Sonal
 */

public class DataLinearization
{
    double A,B,C;
    double rootMeanSquareError = 0.0,standardDeviation =0.0;
    ArrayList fittedData = new ArrayList();
    ArrayList actualData = new ArrayList();
    int sizeOfData;
    double X[];
    double Y[];

    String equation;
    Point point;
    public DataLinearization(ArrayList data)
    {
        actualData = data;
        sizeOfData = actualData.size();
        X = new double [sizeOfData];
        Y = new double [sizeOfData];
        for(int i=0;i<sizeOfData;i++)
        {
            X[i]=((Point)actualData.get(i)).getX();
            Y[i]=((Point)actualData.get(i)).getY();
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
            double y=C*(Math.exp(A*x));
            point=new Point(x,y);
            fittedData.add(point);
        }
        return fittedData;
    }

    public void findCurve()
    {
	    double sumX=0.0,sumY=0.0,sumX2=0.0,sumXY=0.0;
	    double meanX,meanY;
	    equation="";
	    for(int i=0;i<actualData.size();i++)
		{
	    	if(Y[i]==0)
	    	{
	    		sumY+=-Math.pow(10,5);
		        sumXY+=X[i]*(-Math.pow(10,5));
		   	}
	    	else
	    	{
	    		sumY+=Math.log(Y[i]);
		        sumXY+=X[i]*(Math.log(Y[i]));
	    	}
			sumX+=X[i];
			sumX2+=X[i]*X[i];
	     }
	     meanX=(double)sumX/sizeOfData;
	     meanY=(double)sumY/sizeOfData;
		 A=(double)(sumXY-meanY*sumX)/(sumX2-meanX*sumX);
		 B=(double)meanY-meanX*A;
		 C=Math.exp(B);
    }

    public String getEquation()
    {
    	java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
    	equation="Y="+formatter.format(C)+"e^"+formatter.format(A)+"X";
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
