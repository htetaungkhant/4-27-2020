package supplier.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import database.StockTable;
import database.SupplierTable;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import stock.Items;
import supplier.SupplierInfo;

public class AddNewSupplier extends JDialog{

	private static MyTextField tfSupplierName;
	private static JNumberTextField tfPhone;
	private static MyTextField tfAddress;

	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnCancel;

	public AddNewSupplier(Frame frame){
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/supplier_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Add New Supplier");
		setSize(360, 250);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lbSupplierName = new JLabel("Supplier Name"); add(lbSupplierName);
		JLabel lbPhone = new JLabel("Phone Number"); add(lbPhone);
		JLabel lbAddress = new JLabel("Address"); add(lbAddress);

		tfSupplierName = new MyTextField(); add(tfSupplierName);
		tfPhone = new JNumberTextField(15); add(tfPhone);
		tfAddress = new MyTextField(); add(tfAddress);
		resetTextFields();

		btnAdd = new JButton("Add"); add(btnAdd);
		btnCancel = new JButton("Cancel"); add(btnCancel);

		lbSupplierName.setBounds(20, 20, 100, 30); tfSupplierName.setBounds(130, 20, 200, 30);
		lbPhone.setBounds(20, 60, 100, 30); tfPhone.setBounds(130, 60, 200, 30);
		lbAddress.setBounds(20, 100, 100, 30); tfAddress.setBounds(130, 100, 200, 30);
		btnAdd.setBounds(20, 150, 150, 50);btnCancel.setBounds(180, 150, 150, 50);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String supplierName = tfSupplierName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {supplierName, phone, address};
				if(SupplierTable.isSupplierExist(supplierName)){
					JOptionPane.showMessageDialog(null, "Supplier already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					SupplierTable.insert(data);
					String supplier = SupplierInfo.getSelectedSupplier();
					SupplierInfo.createSupplierTable(SupplierTable.retrieveAll());
					SupplierInfo.setSelectedSupplier(supplier);
					setVisible(false);
					dispose();
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}

	public AddNewSupplier(Frame frame, String[] input, String toFilter){
		this(frame);
		setTitle("Update Supplier Details");

		tfSupplierName.setText(input[0]);
		tfPhone.setText(input[1]);
		tfAddress.setText(input[2]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("Update"); add(btnUpdate);
		btnUpdate.setBounds(20, 150, 150, 50);

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String supplierName = tfSupplierName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {supplierName, phone, address};

				if(!tfSupplierName.getText().equals(input[0]) && SupplierTable.isSupplierNameExist(supplierName)){
					JOptionPane.showMessageDialog(null, "Supplier Name already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					SupplierTable.update(data, input[0]);
					int row = SupplierInfo.getSelectedRow();
					SupplierInfo.createSupplierTable(SupplierTable.retrieveFilterBySupplierName(toFilter));
					SupplierInfo.setSelectedRow(row);
					setVisible(false);
					dispose();
				}
			}
		});
	}

	private boolean isEmpty(String[] data){
		String[] labels = {"Supplier Name", "Phone", "Address"};
		for(int i = 0; i < data.length; i++){
			if(data[i].equals("")){
				JOptionPane.showMessageDialog(null, labels[i]+" cannot be empty.", "Empty Error", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	private static void resetTextFields(){
		tfSupplierName.setText("");
		tfPhone.setText("");
		tfAddress.setText("");
	}
}
