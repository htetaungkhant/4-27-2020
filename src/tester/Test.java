package tester;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.alee.laf.WebLookAndFeel;

public class Test {
	public static void main (String[] args) {
		WebLookAndFeel.install();
	    JFrame parent = new JFrame ();
	    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    final JTabbedPane pane = new JTabbedPane ();
	    pane.addTab ("test", null);
	    FlowLayout f = new FlowLayout (FlowLayout.CENTER, 5, 0);

	    // Make a small JPanel with the layout and make it non-opaque
	    JPanel pnlTab = new JPanel (f);
	    pnlTab.setOpaque (false);
	    // Create a JButton for adding the tabs
	    JButton addTab = new JButton ("+");
	    addTab.setOpaque (false); //
	    addTab.setBorder (null);
	    addTab.setContentAreaFilled (false);
	    addTab.setFocusPainted (false);
	    addTab.setPreferredSize(new Dimension(30, 30));

	    addTab.setFocusable (false);

	    pnlTab.add (addTab);

	    pane.setTabComponentAt (pane.getTabCount () - 1, pnlTab);

	    ActionListener listener = new ActionListener () {
	        @Override
	        public void actionPerformed (ActionEvent e) {
	            String title = "Tab " + String.valueOf (pane.getTabCount () - 1);
	            pane.addTab (title, new JLabel (title));
	        }
	    };
	    addTab.setFocusable (false);
	    addTab.addActionListener (listener);
	    pane.setVisible (true);

	    parent.add (pane);
	    parent.setSize (new Dimension (400, 200));
	    parent.setVisible (true);
	}
}
