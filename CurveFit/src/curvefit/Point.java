/*
 * Point.java
 *
 * Created on October 31, 2009, 5:52 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curvefit;

/**
 *
 * @author hp
 */
public class Point 
{
    double x;
    double y;
    /** Creates a new instance of Point */
    public Point() 
    {
    }
    
    public Point(double x_,double y_)
    {
        x = x_;
        y = y_;
    }
    
    public Point(Point pt)
    {
        
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
}
