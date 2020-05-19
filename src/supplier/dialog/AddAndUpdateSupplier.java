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

public class AddAndUpdateSupplier extends JDialog{

	private static MyTextField tfSupplierName;
	private static JNumberTextField tfPhone;
	private static MyTextField tfAddress;

	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnCancel;

	public AddAndUpdateSupplier(Frame frame){
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/supplier_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Add New Supplier");
		setSize(360, 260);
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

		lbSupplierName.setBounds(20, 20, 100, 40); tfSupplierName.setBounds(130, 20, 200, 40);
		lbPhone.setBounds(20, 70, 100, 40); tfPhone.setBounds(130, 70, 200, 40);
		lbAddress.setBounds(20, 120, 100, 40); tfAddress.setBounds(130, 120, 200, 40);
		btnAdd.setBounds(20, 170, 150, 50);btnCancel.setBounds(180, 170, 150, 50);

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
					SupplierInfo.removeAlreadySupplier();
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

	public AddAndUpdateSupplier(Frame frame, Object[] input, String toFilter){
		this(frame);
		setTitle("Update Supplier Details");

		tfSupplierName.setText((String)input[1]);
		tfPhone.setText((String)input[2]);
		tfAddress.setText((String)input[3]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("Update"); add(btnUpdate);
		btnUpdate.setBounds(20, 170, 150, 50);

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String supplierName = tfSupplierName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {supplierName, phone, address};

				if(!tfSupplierName.getText().equals(input[1]) && SupplierTable.isSupplierNameExist(supplierName)){
					JOptionPane.showMessageDialog(null, "Supplier Name already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					SupplierTable.update(data, (int)input[0]);
					SupplierInfo.createSupplierTable(SupplierTable.retrieveFilterBySupplierName(toFilter));
					SupplierInfo.removeAlreadySupplier();
					SupplierInfo.setSelectedRow((int)input[0]);
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
