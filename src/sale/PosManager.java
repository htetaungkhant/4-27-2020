package sale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import external_classes.ButtonTabComponent;
import external_classes.Fonts;

public class PosManager extends JPanel{
	public PosManager() {
		setLayout(new BorderLayout());
		setBackground(Color.LIGHT_GRAY);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(1, 3, 3, 3));

		JButton btnAddNewInvoice = new JButton("+");
		btnAddNewInvoice.setPreferredSize(new Dimension(25, 25));
		tabbedPane.addTab("+", null);
		tabbedPane.setEnabledAt(0, false);
		tabbedPane.setTabComponentAt(0, btnAddNewInvoice);
		tabbedPane.addTab("Regular Invoice", new POS());
		tabbedPane.setSelectedIndex(1);

		add(tabbedPane, BorderLayout.CENTER);

		POS.removeAllExistingCustomers();
//		tabbedPane.addChangeListener(new ChangeListener() {
//			@Override
//			public void stateChanged(ChangeEvent e) {
//		        Component comp = tabbedPane.getSelectedComponent();
//				comp.requestFocusInWindow();
//			}
//		});

		btnAddNewInvoice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				POS newInvoicePanel = new POS();
				tabbedPane.addTab("New Invoice", newInvoicePanel);
				tabbedPane.setTabComponentAt(tabbedPane.getTabCount()-1, new ButtonTabComponent(tabbedPane, newInvoicePanel));
				tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
			}
		});
	}
}
