package tester;

import javax.swing.*;

import com.alee.laf.WebLookAndFeel;

import external_classes.Fonts;

import java.awt.*;
public class Test2 {
   public static void main(String args[]) {
//	   WebLookAndFeel.install();
      JFrame frame = new JFrame("Technologies");
      JTabbedPane tabbedPane = new JTabbedPane();
      JPanel panel1, panel2, panel3, panel4, panel5;
      panel1 = new JPanel();
      panel2 = new JPanel();
      panel3 = new JPanel();
      panel4 = new JPanel();
      panel5 = new JPanel();
      tabbedPane.addTab("Blockchain", panel1);
      tabbedPane.addTab("Salesforce", panel2);
      tabbedPane.addTab("SAS", panel3);
      tabbedPane.addTab("Matlab ", panel4);
      tabbedPane.addTab("Servlet", panel5);
      tabbedPane.setFont(Fonts.pyisuNormal16);
      frame.add(tabbedPane);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(660,350);
      frame.setVisible(true);
   }
}
