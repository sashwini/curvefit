package curvefit;

 import java.io.*;
 import javax.swing.event.*;
 import javax.swing.*;
 import java.net.*;
 import java.awt.event.*;
 import java.awt.*;
 
 public class HelpWindow extends JDialog implements ActionListener
 {
    private final int WIDTH = 550;
    private final int HEIGHT = 600;
    
    //frame = new JFrame("Help");
// private JEditorPane editorpane;
   //  private URL helpURL;
 //////////////////////////////////////////////////////////////////
 /**
  * HelpWindow constructor
  * @param String and URL
  */
 public HelpWindow()//String title, URL hlpURL)
 {
	setTitle("CurveFit Help");  
    try 
    {
      
         Font f1= new Font("Arial",Font.BOLD,16);
         Font f= new Font("Arial",Font.PLAIN,12);
         
                  
         JPanel jp1 = new JPanel();
         jp1.setLayout(new GridLayout(5,1));
         JPanel jp2 = new JPanel();
         jp2.setLayout(new GridLayout(6,1));
         JPanel jp3 = new JPanel();
         jp3.setLayout(new GridLayout(5,1));
         JPanel jp4 = new JPanel();
         jp4.setLayout(new GridLayout(5,1));
         JPanel jp5 = new JPanel();
         jp5.setLayout(new GridLayout(8,1));
         
         Label head1= new Label("Getting Started With Curve Fit :");
         head1.setFont(f1);

         Label para1= new Label("   The application reads data file in \".txt\" format only.");
         Label para11=new Label("   You can select new data set using New Dataset menu.\n");
         para1.setFont(f);    
         para11.setFont(f);
         
         Label head2= new Label("Plotting Curves :");
         head2.setFont(f1);

         Label para2= new Label("   1.Browse the file using Browse button in window.");
         Label para21=new Label("   2.Select one or more methods for fitting data. For multiple selections use  CTRL.");
         Label para22=new Label("   3.Select the degree of power fit for Power Fit method using Degree of Power Fit slider.");
         Label para23=new Label("   4.Select the degree Of polynomial for Polynomial Fit method using Degree Of Polynomial slider.");
         //para2.setEditable(false);
         para2.setFont(f);
         para21.setFont(f);
         para22.setFont(f);
         para23.setFont(f);
         
         
         Label head3 =new Label("Save Your Curve Fit Output :");
         head3.setFont(f1);
         
         Label para3= new Label("   1. You can save the plotted Curves using Save Plot menu.");
         Label para31=new Label("   2. The image will be stored in \".jpg\" format.");
         para3.setFont(f);
         para31.setFont(f);
         
         Label head4= new Label("Find Error Analysis :");
         head4.setFont(f1);
          
         Label para4= new Label("   1.Application generates the error report.");
         Label para42=new Label("   2.It can be viewed by selecting Error Analysis menu.");
         para4.setFont(f);
         para42.setFont(f);
         
         
          Label head5= new Label("About Us :");
          head5.setFont(f1);
         Label para5= new Label("   1. Ashwini Shahapurkar.");
         Label para51=new Label("   2. Sonal Bhosale.");
         Label para52=new Label("   3. Shital Deore.");
         Label para53=new Label("   4.Namrata Otari.\n");
         Label para54=new Label("   M.Sc.(Scientific Computing)");
         Label para55=new Label("   UOP, Ganeshkhind,");
         Label para56=new Label("   Pune. 411007");
          para5.setFont(f);
          para51.setFont(f);
          para52.setFont(f);
          para53.setFont(f);
          para54.setFont(f);
          para55.setFont(f);
          para56.setFont(f);
             
   
     GridLayout g = new GridLayout(5,1)    ;
      setLayout(g);
    //setLayout(gbag);
    jp1.add(head1);
    jp1.add(para1);
    jp1.add(para11);
    jp1.setVisible(true);
    add(jp1);
    jp2.add(head2);
    jp2.add(para2);
    jp2.add(para21);
    jp2.add(para22);
    jp2.add(para23);
    jp2.setVisible(true);
    add(jp2);
    jp3.add(head3);
    jp3.add(para3);
    jp3.add(para31);
    jp3.setVisible(true);
    add(jp3);
    jp4.add(head4);
    jp4.add(para4);
    jp4.add(para42);
    jp4.setVisible(true);
    add(jp4);
    jp5.add(head5);
    jp5.add(para5);
    jp5.add(para51);
    jp5.add(para52);
    jp5.add(para53);
    jp5.add(para54);
    jp5.add(para55);
    jp5.add(para56);
 //   jp5.add(para57);
    jp5.setVisible(true);
    add(jp5);
    
        
        System.out.println("Done.");
       
     } catch (Exception ex)
     {
         ex.printStackTrace();
     }

     //getContentPane().add(new JScrollPane(editorpane));
  //   addButtons();
     // no need for listener just dispose
     setDefaultCloseOperation(DISPOSE_ON_CLOSE);
     // dynamically set location
     calculateLocation();
     setVisible(true);
    // end constructor
 }
 /**
  * An Actionlistener so must implement this method
  *
  */
 public void actionPerformed(ActionEvent e)
 {
     String strAction = e.getActionCommand();
    try
     {      if(strAction == "Close")
           {
             // more portable if delegated
             processWindowEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
         }
     } 
     catch (Exception ex) 
       {
         ex.printStackTrace();
       }
 }
 /**
  * add buttons at the south
  */
 private void addButtons()
 {
     JButton btnclose = new JButton("Close");
     btnclose.addActionListener(this);
     //put into JPanel
     JPanel panebuttons = new JPanel();
     panebuttons.add(btnclose);
     //add panel south
     getContentPane().add(panebuttons, BorderLayout.SOUTH);
 }
 /**
  * locate on screen
 */
 private void calculateLocation() 
 {
    Dimension screendim = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(new Dimension(WIDTH, HEIGHT));
    int locationx = (screendim.width - WIDTH); // 2;
    int locationy = (screendim.height - HEIGHT)/ 2;
    setLocation(locationx, locationy);
}
}//end HelpWindow class
