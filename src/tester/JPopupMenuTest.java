package tester;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import com.alee.laf.WebLookAndFeel;
public class JPopupMenuTest extends JFrame {
   private JPopupMenu popup;
   public JPopupMenuTest() {
      setTitle("JPopupMenu Test");
      Container contentPane = getContentPane() ;
      popup = new JPopupMenu();
      // add menu items to popup
      popup.add(new JMenuItem("Cut"));
      popup.add(new JMenuItem("Copy"));
      popup.add(new JMenuItem("Paste"));
      JMenuItem delete = new JMenuItem("Delete");
      popup.add(delete);
      popup.addSeparator();
      popup.add(new JMenuItem("SelectAll"));
      contentPane.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent me) {
            showPopup(me); // showPopup() is our own user-defined method
         }
      }) ;

      delete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) { System.out.println("gg"); }
	});

      setSize(375, 250);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
   }
   void showPopup(MouseEvent me) {
      if(me.isPopupTrigger())
         popup.show(me.getComponent(), me.getX(), me.getY());
   }
   public static void main(String args[]) {
		WebLookAndFeel.install();
     	new JPopupMenuTest();
   }
}