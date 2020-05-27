package tester;
import java.awt.*;  
import java.awt.event.*;  
  
import javax.swing.*;

import com.alee.laf.WebLookAndFeel;

import purchase.Purchase;
import supplier.SupplierInfo;  
  
public class CardLayoutExample extends JFrame implements ActionListener{  
CardLayout card;   
SupplierInfo supplier;
Purchase purchase;
Container c;  
    CardLayoutExample(){  
          
        c=getContentPane();  
        card=new CardLayout(40,30);   			//create CardLayout object with 40 hor space and 30 ver space  
        c.setLayout(card);  
        
        final JMenuBar menuBar = new JMenuBar ();
        JMenu menu = new JMenu("Menus");
        JMenuItem jmPurchase = new JMenuItem ( "Purchase" );
        JMenuItem jmSupplier = new JMenuItem ( "Supplier" );
        menu.add(jmPurchase);
        menu.add(jmSupplier);
        menuBar.add ( menu );
        setJMenuBar(menuBar);
        
        jmPurchase.addActionListener(this);
        jmSupplier.addActionListener(this);
        
        purchase = new Purchase(false);      
        supplier = new SupplierInfo();
        
        c.add("p",purchase);c.add("s",supplier);
                          
    }  
    public void actionPerformed(ActionEvent e) {
    	if(((JMenuItem)e.getSource()).getText().equals("Purchase")) {
    		card.show(c, "p");
    	}
    	else{
    		card.show(c, "s");
    	}
//    card.next(c);  
    }  
  
    public static void main(String[] args) {  
    	WebLookAndFeel.install();
        CardLayoutExample cl=new CardLayoutExample();  
        cl.setSize(400,400);  
        cl.setVisible(true);  
        cl.setDefaultCloseOperation(EXIT_ON_CLOSE);  
    }  
}  