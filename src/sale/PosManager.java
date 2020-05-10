package sale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import external_classes.ButtonTabComponent;

public class PosManager extends JPanel{
	public PosManager() {
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(1, 3, 3, 3));

		JButton btnAddNewInvoice = new JButton("+");
		btnAddNewInvoice.setPreferredSize(new Dimension(25, 25));
		tabbedPane.add("New Invoice", null);
		tabbedPane.setEnabledAt(0, false);
		tabbedPane.setTabComponentAt(0, btnAddNewInvoice);
		tabbedPane.addTab("Regular Invoice", new POS());
		tabbedPane.setSelectedIndex(1);

		add(tabbedPane, BorderLayout.CENTER);

		btnAddNewInvoice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.addTab("New Invoice", new POS());
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
		});
	}
}
