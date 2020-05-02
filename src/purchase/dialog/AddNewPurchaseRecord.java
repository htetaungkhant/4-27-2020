package purchase.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Main;
import supplier.SupplierInfo;

public class AddNewPurchaseRecord extends JDialog{
	public AddNewPurchaseRecord(Frame parent){
		super(parent, true);
		setTitle("Add New Item");
		setSize(1200, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel();
		JButton test = new JButton("Test");
		topPanel.add(test);
		add(topPanel, BorderLayout.NORTH);

		test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame);
				SupplierInfo test = new SupplierInfo();
				System.out.println(test.addBottomPanel(test, d));
				d.add(test);
				d.setModal(true);
				d.setSize(600, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});
	}
}
