/*
 * LeastSquareParabola.java
 *
 * Created on October 31, 2009, 10:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package techniques;

import java.util.*;
import curvefit.*;

/**
 *
 * @author Sonal
 */

public class LeastSquareParabola 
{
    double A,B,C;
    double rootMeanSquareError = 0.0,standardDeviation =0.0;
    ArrayList<Point> fittedData = new ArrayList();
    ArrayList<Point> actualData = new ArrayList();
    int sizeOfData;
    double X[];
    double Y[];    
    double system[][] = new double[3][4];
    ArrayList solution = new ArrayList();    
    
    String equation;
    Point point;
    Maths maths=new Maths(3);
    /** Creates a new instance of LeastSquareParabola */
    public LeastSquareParabola(ArrayList data) 
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
            double y=A*x*x+B*x+C;
            point=new Point(x,y);
            fittedData.add(point);
        }
        return fittedData;
    }
  
    public void findCurve() 
    {
        double sumX=0.0,sumY=0.0,sumX2=0.0,sumX3=0.0,sumX4=0.0,sumXY=0.0,sumX2Y=0.0;
        equation="";      
       	for(int i=0;i<actualData.size();i++)
       	{
       			sumX+=X[i];
      			sumX2+=X[i]*X[i];
                sumX3+=X[i]*X[i]*X[i];
                sumX4+=X[i]*X[i]*X[i]*X[i];
                sumY+=Y[i];
                sumXY+=X[i]*Y[i];
                sumX2Y+=X[i]*X[i]*Y[i];
       	}
       	
       	system[0][0]=sumX4;     system[0][1]=sumX3;     system[0][2]=sumX2;     system[0][3]=sumX2Y;
        system[1][0]=sumX3;     system[1][1]=sumX2;     system[1][2]=sumX;     system[1][3]=sumXY;
        system[2][0]=sumX2;     system[2][1]=sumX;      system[2][2]=sizeOfData;     system[2][3]=sumY;
       
        solution = maths.gauss(system,3);
 
        for(int i=0;i<3;i++)
        A=((Double)solution.get(0)).doubleValue();   
        B=((Double)solution.get(1)).doubleValue();      
        C=((Double)solution.get(2)).doubleValue();
     }
    
    public String getEquation()
    {
    	java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
    	if(B>=0.0 && C>=0.0)
            equation="Y="+formatter.format(A)+"X^2+"+formatter.format(B)+"X+"+formatter.format(C);
        else if(B>=0.0 && C<0.0)
                equation="Y="+formatter.format(A)+"X^2+"+formatter.format(B)+"X"+formatter.format(C);
        else if(B<0.0 && C>=0.0)
            equation="Y="+formatter.format(A)+"X^2"+formatter.format(B)+"X+"+formatter.format(C);
        else if(B<0.0 && C<0.0)
            equation="Y="+formatter.format(A)+"X^2"+formatter.format(B)+"X"+formatter.format(C);
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
