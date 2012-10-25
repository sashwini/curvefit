package techniques;

import java.io.*;
import java.util.*;
import curvefit.*;

import java.math.*;

public class LeastSquareLine
{
    double A,B;
    double rootMeanSquareError = 0.0,standardDeviation =0.0;
    ArrayList fittedData = new ArrayList();
    ArrayList actualData = new ArrayList();
    int sizeOfData;
    double X[];
    double Y[];

    String equation;
    Point point;
    public LeastSquareLine(ArrayList data)
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
            double y=A*x+B;
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
		sumX+=X[i];
		sumX2+=X[i]*X[i];
                sumY+=Y[i];
                sumXY+=X[i]*Y[i];
        }
        meanX=(double)sumX/sizeOfData;
        meanY=(double)sumY/sizeOfData;
        A=(double)(sumXY-meanY*sumX)/(sumX2-meanX*sumX);
        B=(double)meanY-meanX*A;
    }

    public String getEquation()
    {
    	java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
        if(B>=0.0)
        	equation="Y="+formatter.format(A)+"X+"+formatter.format(B);
        else
        	equation="Y="+formatter.format(A)+"X"+formatter.format(B);
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
