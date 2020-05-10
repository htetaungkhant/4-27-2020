package sale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import external_classes.ButtonTabComponent;

public class POS extends JPanel{
	public POS() {
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel();
		topPanel.add(new JButton("GGG"));

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Center Panel
		//End of Center Panel

		//creating Bottom Panel
		//End of Bottom Panel
	}
}
