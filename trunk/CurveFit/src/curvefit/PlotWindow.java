/*
 * PlotWindow.java
 *
 * Created on October 28, 2009
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curvefit;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import techniques.*;

/**
 *
 * @author Sonal
 */

public class PlotWindow implements MenuListener,MouseMotionListener
{
	PlotCanvas canvas;
	JFrame frame;
	JLabel position;
	JMenuBar menuBar;
	JMenu saveMenu,errorMenu;
//	Graphics g;
	Point point;
	ArrayList<Point> actualData = new ArrayList();
	ArrayList<Point> fittedData = new ArrayList();
	ErrorReport errWindow;
	/** Creates a new instance of Main */
	public PlotWindow()
	{
		frame = new JFrame("Graph Plot");

		//creating label
		position = new JLabel();

		//creating menus
		menuBar = new JMenuBar();
		saveMenu = new JMenu("Save Plot");
		saveMenu.addMenuListener(this);
		//JMenuItem
		saveMenu.add(new JSeparator());
		errorMenu = new JMenu("Error Analysis");
		errorMenu.addMenuListener(this);
		errorMenu.add(new JSeparator());
		menuBar.add(saveMenu);
		menuBar.add(errorMenu);

////////////	/container
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		canvas = new PlotCanvas();
		canvas.addMouseMotionListener(this);
		content.add(canvas,BorderLayout.CENTER);
		content.add(position,BorderLayout.SOUTH);
//		content.add(panel);

		canvas.setVisible(true);
		frame.setJMenuBar(menuBar);
		frame.setSize(800,500);
		frame.setBounds(200,200,800,530);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		errWindow = new ErrorReport();
		
	}  

	public ArrayList<Point> getDataFromFile(String file)
	{
		FileInputStream reader;
		ArrayList<Point> data =new ArrayList();
		try
		{
			reader = new FileInputStream(file);
			int ch;
			double x=0.0,y=0.0;
			String s = "";
			int cnt = 0;
			while((ch=reader.read())!=-1)
			{   
				if((char)ch!='\t')
				{
					s += (char)ch;
				}
				if((char)ch=='\t')
				{
					x = Double.parseDouble(s);
					//System.out.println("this is x @ \t"+x);
					s="";                
				}
				if((char)ch=='\n')
				{
					y = Double.parseDouble(s);
					//System.out.println("this is y @ \t"+y);
					s="";
					cnt++;
					if(cnt > 100)
					{
						break;
					}
					point=new Point(x,y);

					//System.out.println("\n point"+point.getX()+" "+point.getY());
					data.add(point);     
				}       
			}
			if(cnt > 100)
			{
				JOptionPane.showMessageDialog(null,"Too large file to plot","Application Error....",JOptionPane.ERROR_MESSAGE);
			}
			reader.close();

		}
		catch(FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null,"File not found on specified location","Application Error....",JOptionPane.ERROR_MESSAGE);
			//System.out.println("file not found"+file);
			frame.dispose();
			//show error message
		}
		catch(Exception e)
		{
		}
		return data;
	}


	public void processInput(String file,Object techniques[],int degree,int degreeM)
	{
		frame.setVisible(true);
		Graphics g = canvas.getGraphics();
		System.out.print(g.toString());
		actualData=getDataFromFile(file);
		int k;
		boolean zero = false;
		//check for zero in y values
		for(k=0;k<actualData.size();k++)
		{
			Point p = actualData.get(k);
			if(p.getY()==0)
				break;
		}
		if(k<actualData.size())
		{
			zero = true;
		}
		
		canvas.setDataToPlot(actualData,0);
		//canvas.paint(g);

		for(int i=0;i<techniques.length;i++)
		{
			if(techniques[i].toString().equals("Least Square Line"))
			{
				System.out.println("\nLeast Square Line");
				LeastSquareLine lsl = new LeastSquareLine(actualData);
				lsl.findCurve();
				fittedData = lsl.getFittedData();
				canvas.setDataToPlot(fittedData,1);
				errWindow.setRow("Least Square Line",lsl.getEquation(),lsl.getRMS(),lsl.getSD());
			}
			if(techniques[i].toString().equals("Least Square Parabola"))
			{
				System.out.println("\nLeast Square Parabola");
				LeastSquareParabola lsp = new LeastSquareParabola(actualData);
				//lsp.findCurve();
				fittedData = lsp.getFittedData();
				canvas.setDataToPlot(fittedData,2);
				errWindow.setRow("Least Square Parabola",lsp.getEquation(),lsp.getRMS(),lsp.getSD());
			}
			if(techniques[i].toString().equals("Power Fit"))
			{
				System.out.println("\nPower Fit");
				PowerFit pf = new PowerFit(actualData,degreeM);
				//pf.findCurve();
				fittedData = pf.getFittedData();
				canvas.setDataToPlot(fittedData,3);
				errWindow.setRow("Power Fit",pf.getEquation(),pf.getRMS(),pf.getSD());
			}
			if(techniques[i].toString().equals("Data Linearization Method"))
			{
				try
				{
				if(zero==true)
				{
					throw new MethodNotApplicableException();
				}
				}
				catch(MethodNotApplicableException e)
				{
					frame.dispose();
				}
				System.out.println("\nData Linearization");
				DataLinearization dl = new DataLinearization(actualData);
				//pf.findCurve();
				fittedData = dl.getFittedData();
				canvas.setDataToPlot(fittedData,4);
				errWindow.setRow("Data Linearization",dl.getEquation(),dl.getRMS(),dl.getSD());
			}
			if(techniques[i].toString().equals("Polynomial Fitting"))
			{
				System.out.println("\nPolynomial Fitting");
				PolynomialFitting plf = new PolynomialFitting(actualData,degree);
				//pf.findCurve();
				fittedData = plf.getFittedData();
				canvas.setDataToPlot(fittedData,5);
				errWindow.setRow("Polynomial Fitting",plf.getEquation(),plf.getRMS(),plf.getSD());
			}
		}

		canvas.paint(g);
		//g.fillOval(100,100,5,5);
	}     

	public void mouseMoved(MouseEvent me)
	{
		int x = me.getX();
		int y = me.getY();
		position.setText(canvas.updatePosition(x,y));
		//position.setText("x="+x+",y="+y);
		//JOptionPane.showMessageDialog(null,x+" "+y,"Application info....",JOptionPane.INFORMATION_MESSAGE);
	}

	public void mouseDragged(MouseEvent me)
	{
	}

	public void menuCanceled(MenuEvent me)
	{
	}

	public void menuDeselected(MenuEvent me)
	{
	}

	public void menuSelected(MenuEvent me)
	{
		if(me.getSource().equals(saveMenu))
		{
			//code to display file dialog & save image
			FileDialog fileDialog = new FileDialog(frame,"Save Image",FileDialog.SAVE);
			fileDialog.setVisible(true);
			//fileDialog
			//fileDialog.setMode(FileDialog.SAVE);
			String dir = fileDialog.getDirectory();
			String fileName = fileDialog.getFile();
			String imgFile = dir+fileName+".png";
			if(dir.equals(null) && fileName.equals(null))
			{
				System.out.print("save menu");
			//	nameTextField.setText(null);
			}
			else
			{
				System.out.print("file:"+imgFile);
				canvas.getData(imgFile);
			}
		}
		if(me.getSource().equals(errorMenu))
		{
			//code to display error report window
			errWindow.show();
			System.out.print("action done");
		}
	}
}

class PlotCanvas extends Canvas 
{
	//for 1st quadrant origin 50,300
	Color colors[];
	double xScaleFactor;
	double yScaleFactor;
	int xTrans;
	int yTrans;
	double xmin,xmax,ymin,ymax;
	int numPts[];
	int techniques[];
	int index;
	ArrayList<Point> dataToPlot;
	Graphics2D g;
	PlotCanvas()
	{
		colors = new Color[4];
		colors[0] = Color.RED;// data point color
		colors[1] = Color.BLUE;// 1st curve color
		colors[2] = Color.DARK_GRAY;// 2nd curve color
		colors[3] = Color.magenta;// 3rd curve color
		dataToPlot = new ArrayList<Point>();
		index = 0;
		xTrans = 50;
		yTrans = 380;
		
		Color clr = new Color(255,255,255);
		setBackground(clr);
		numPts = new int[6];
		techniques = new int[6];
	}

	public Graphics getGraphics()
	{
		return(super.getGraphics());
	}

	void setRange()
	{
		//find xmax and xmin
		xmin=xmax=((Point)dataToPlot.get(1)).getX();
		for(int i=0;i<dataToPlot.size();i++)
		{
			if(xmax<((Point)dataToPlot.get(i)).getX())
			{
				xmax=((Point)dataToPlot.get(i)).getX();
			}
			else
			if(xmin>((Point)dataToPlot.get(i)).getX())
			{
				xmin=((Point)dataToPlot.get(i)).getX();
			}
		}

		//find ymax and ymin
		ymin=ymax=((Point)dataToPlot.get(1)).getY();
		for(int i=0;i<dataToPlot.size();i++)
		{
			if(ymax<((Point)dataToPlot.get(i)).getY())
			{
				ymax=((Point)dataToPlot.get(i)).getY();
			}
			else
				if(ymin>((Point)dataToPlot.get(i)).getY())
				{
					ymin=((Point)dataToPlot.get(i)).getY();
				}
		}
		System.out.println("\n xmin:"+xmin+"\txmax:"+xmax);
		System.out.println("\n ymin:"+ymin+"\tymax:"+ymax);

		xScaleFactor=(xmax-xmin)/530;
		yScaleFactor=(ymax-ymin)/290;

		System.out.println("\n xscale:"+xScaleFactor+"\tyscale:"+yScaleFactor);
		System.out.println("\n xTrans:"+xTrans+"\tyTrans:"+yTrans);

	}

	void setDataToPlot(ArrayList<Point> data,int technique)
	{
		techniques[index] = technique;
		numPts[index++] = data.size();
		System.out.print("index-1:"+(index-1)+data.size());
		for(int i=0;i<data.size();i++)
		{
			dataToPlot.add(data.get(i));
		}
		//if(index==1)
		{
			setRange();
		}

	}

	//update co ordinates to be displayed
	String updatePosition(int posx,int posy)
	{
		java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.0000");
		String txt = "x=";
		txt += formatter.format(xmin+((posx-xTrans)*xScaleFactor));
		txt += ",y=";
		txt += formatter.format(ymin+((yTrans-posy)*yScaleFactor));
		if(posx >= 585 || posx <= 6 || posy <= 10 || posy >= 430)
		{
			txt = "";
		}
		return txt;
	}

	public void getData(String file)
	{
		writeToFile(file,dataToPlot,numPts);
	}

	public void writeToFile(String file,ArrayList<Point> data,int[] numPts)
	{
		BufferedImage bi = new BufferedImage(600,450,BufferedImage.TYPE_INT_RGB);
		Graphics big = bi.getGraphics();
		big.setColor(Color.WHITE);
		big.fillRect(0,0,600,450);
		big.setColor(Color.BLACK);
		big.drawLine(50,30,50,410);//y-axis
		big.drawLine(20,380,580,380);//x-axis
		//draw ticks and values on x-axis
		int x = 50;
		for(int i=0;i<=5;i++)
		{
			double xval = xmin +(i*(xmax-xmin)/5);
			java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.00");
			String txt ="";
			txt+= formatter.format(xval);
			big.drawString(txt,x-5,395);
			big.drawLine(x,375,x,385);
			x=x+106;
		}
		//draw ticks and values on y-axis
		int y = 380;
		for(int i=0;i<=6;i++)
		{
			double yval = ymin +(i*(ymax-ymin)/5);
			java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.00");
			//System.out.println("yval:"+(ymax-ymin));
			String txt ="";
			txt+= formatter.format(yval);
			big.drawLine(45,y,55,y);
			big.drawString(txt,15,y+5);
			y=y-58;
		}
		
		int k=0;
		for(int j=0;j<index;j++)
		{
			
			big.setColor(colors[j]);
			
			int pts = 0;
			
			while(pts<(numPts[j]))
			{
				
				if(k==(dataToPlot.size()-1))
					break;
				if(big.getColor().equals(colors[0]))//these are data points
				{
					Point p1 = (Point)dataToPlot.get(k);
					double x1 = p1.getX();
					double y1 = p1.getY();
					//System.out.println("actual data..."+pts+"k..."+k);
					big.fillOval((int)((x1-xmin)/xScaleFactor)+xTrans,yTrans-(int)((y1-ymin)/yScaleFactor),5,5);
				}
				else
				{
					if(pts==(numPts[j]-1))
					{
						k++;
						break;
					}
					Point p1 = (Point)dataToPlot.get(k);
					Point p2 = (Point)dataToPlot.get(k+1);
					//System.out.println("pts1"+p1.getX()+"..."+p1.getY());
					double x1 = p1.getX();
					double y1 = p1.getY();
					//System.out.println("pts2"+p2.getX()+"..."+p2.getY());
					double x2 = p2.getX();
					double y2 = p2.getY();
					//System.out.println("data..."+pts+"k..."+k);
					//g.fillOval((int)(x1/xScaleFactor)+xTrans,yTrans-(int)(y1/yScaleFactor),1,1);
					big.drawLine((int)((x1-xmin)/xScaleFactor)+xTrans,yTrans-(int)((y1-ymin)/yScaleFactor),(int)((x2-xmin)/xScaleFactor)+xTrans,yTrans-(int)((y2-ymin)/yScaleFactor));
				}
				k++;pts++;
			}
		}
		
		big.drawImage(bi,0,0,null);
		try
		{
			File outfile = new File(file);
			ImageIO.write(bi,"jpg",outfile);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Image can not be saved.....","Application Error....",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void paint(Graphics gr)
	{
		g = (Graphics2D)gr;

		//rectangular plot area
		//old : 6,6,580,330
		g.drawRect(6,10,580,420);
		//rectangular legend area
		g.drawRect(600, 10, 180, 420);
		g.drawString("Legends",610,30);

		//origin at 50,380
		//draw axes here
		g.drawLine(50,30,50,410);//y-axis
		g.drawLine(20,380,580,380);//x-axis
		
		//draw ticks and values on x-axis
		int x = 50;
		for(int i=0;i<=5;i++)
		{
			double xval = xmin +(i*(xmax-xmin)/5);
			java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.00");
			String txt ="";
			txt+= formatter.format(xval);
			g.drawString(txt,x-5,395);
			g.drawLine(x,375,x,385);
			x=x+106;
		}
		//draw ticks and values on y-axis
		int y = 380;
		for(int i=0;i<=6;i++)
		{
			double yval = ymin +(i*(ymax-ymin)/5);
			java.text.NumberFormat formatter = new java.text.DecimalFormat("#0.00");
			//System.out.println("yval:"+(ymax-ymin));
			String txt ="";
			txt+= formatter.format(yval);
			g.drawLine(45,y,55,y);
			g.drawString(txt,15,y+5);
			y=y-58;
		}


		//plot the data 
		int k=0;
		for(int j=0;j<index;j++)
		{
			
			g.setColor(colors[j]);
			g.fillRect(610,40*(j+1),10,10);
			switch(techniques[j])
			{
			case 0:g.drawString("Data Points",630,50*(j+1));
				break;
			case 1:g.drawString("Least Square Line",630,(40+((j+1)))*(j+1));
				break;
			case 2:g.drawString("Least Square Parabola",630,(40+((j+1)))*(j+1));
				break;
			case 3:g.drawString("Power Fit",630,(40+((j+1)))*(j+1));
				break;
			case 4:g.drawString("Data Linearization",630,(40+((j+1)))*(j+1));
				break;
			case 5:g.drawString("Polynomial Fit",630,(40+((j+1)))*(j+1));
				break;
			default:break;
			}
			
			int pts = 0;
			
			while(pts<(numPts[j]))
			{
				
				if(k==(dataToPlot.size()-1))
					break;
				

				if(g.getColor().equals(colors[0]))//these are data points
				{
					Point p1 = (Point)dataToPlot.get(k);
					double x1 = p1.getX();
					double y1 = p1.getY();
					//System.out.println("actual data..."+pts+"k..."+k);
					g.fillOval((int)((x1-xmin)/xScaleFactor)+xTrans,yTrans-(int)((y1-ymin)/yScaleFactor),5,5);
				}
				else
				{
					if(pts==(numPts[j]-1))
					{
						k++;
						break;
					}
					Point p1 = (Point)dataToPlot.get(k);
					Point p2 = (Point)dataToPlot.get(k+1);
					//System.out.println("pts1"+p1.getX()+"..."+p1.getY());
					double x1 = p1.getX();
					double y1 = p1.getY();
					//System.out.println("pts2"+p2.getX()+"..."+p2.getY());
					double x2 = p2.getX();
					double y2 = p2.getY();
					//System.out.println("data..."+pts+"k..."+k);
					//g.fillOval((int)(x1/xScaleFactor)+xTrans,yTrans-(int)(y1/yScaleFactor),1,1);
					//if((((x1-xmin)/xScaleFactor)+xTrans) < 580 && (yTrans-(int)((y1-ymin)/yScaleFactor) < 420) && ((((x2-xmin)/xScaleFactor)+xTrans) < 580) && ((yTrans-(int)((y2-ymin)/yScaleFactor) < 420)))
					g.drawLine((int)((x1-xmin)/xScaleFactor)+xTrans,yTrans-(int)((y1-ymin)/yScaleFactor),(int)((x2-xmin)/xScaleFactor)+xTrans,yTrans-(int)((y2-ymin)/yScaleFactor));
				}
				k++;pts++;
			}
			

		}
		//g.drawImage(bi,null,6,10);
		
		
	}
}