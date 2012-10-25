/*
 * PowerFit.java
 *
 * Created on December 10, 2009, 7:19 AM
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package techniques;
import java.io.*;
import java.util.*;
import java.math.*;
import curvefit.*;


/**
 @author shital
 */
public class PowerFit 
{
    
    double rootMeanSquareError = 0.0,standardDeviation =0.0;
    ArrayList fittedData = new ArrayList();
    ArrayList actualData = new ArrayList();
    int sizeOfData,M;
    double X[];
    double Y[];
    String equation;
    Point point;

    private double A;
    Maths maths=new Maths();
    /** Creates a new instance of PowerFit */
    public PowerFit(ArrayList data,int m) 
    {
        actualData = data;
        sizeOfData = actualData.size();
        M=m;
        X = new double [sizeOfData];
        Y = new double [sizeOfData];
	    for(int i=0;i<sizeOfData;i++)
        {
            X[i]=((Point)actualData.get(i)).getX();
            Y[i]=((Point)actualData.get(i)).getY();
        }
    }
    
    public void findCurve()
    {
        double sumX=0.0,sumY=0.0,sumX2M=0.0,sumXMY=0.0;
        equation="";
       	for(int i=0;i<actualData.size();i++)
       	{
       		sumX2M+=Math.pow(X[i],2*M);
       		sumXMY+=(Math.pow(X[i],M))*Y[i];
        }
       	A=(double)(sumXMY)/(sumX2M);
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
            double y=A*Math.pow(x,M);
            point=new Point(x,y);
            System.out.println("\nPower fit x:"+x+"\ty: "+y);
            fittedData.add(point);
        }
        return fittedData;
    }

    public String getEquation()
    {
    	java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
    	equation="Y="+formatter.format(A)+"X^"+formatter.format(M);
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
