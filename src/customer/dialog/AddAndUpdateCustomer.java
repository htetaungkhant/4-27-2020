package customer.dialog;

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
import database.CustomerTable;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import stock.Items;
import customer.CustomerInfo;

public class AddAndUpdateCustomer extends JDialog{

	private static MyTextField tfCustomerName;
	private static JNumberTextField tfPhone;
	private static MyTextField tfAddress;

	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnCancel;

	public AddAndUpdateCustomer(Frame frame){
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/customer_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Add New Customer");
		setSize(360, 250);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lbCustomerName = new JLabel("Customer Name"); add(lbCustomerName);
		JLabel lbPhone = new JLabel("Phone Number"); add(lbPhone);
		JLabel lbAddress = new JLabel("Address"); add(lbAddress);

		tfCustomerName = new MyTextField(); add(tfCustomerName);
		tfPhone = new JNumberTextField(15); add(tfPhone);
		tfAddress = new MyTextField(); add(tfAddress);
		resetTextFields();

		btnAdd = new JButton("Add"); add(btnAdd);
		btnCancel = new JButton("Cancel"); add(btnCancel);

		lbCustomerName.setBounds(20, 20, 100, 30); tfCustomerName.setBounds(130, 20, 200, 30);
		lbPhone.setBounds(20, 60, 100, 30); tfPhone.setBounds(130, 60, 200, 30);
		lbAddress.setBounds(20, 100, 100, 30); tfAddress.setBounds(130, 100, 200, 30);
		btnAdd.setBounds(20, 150, 150, 50);btnCancel.setBounds(180, 150, 150, 50);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String customerName = tfCustomerName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {customerName, phone, address};
				if(CustomerTable.isCustomerExist(customerName)){
					JOptionPane.showMessageDialog(null, "Customer already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					CustomerTable.insert(data);
					String customer = CustomerInfo.getSelectedCustomer();
					CustomerInfo.createCustomerTable(CustomerTable.retrieveAll());
					CustomerInfo.removeDefaultCustomer();
					CustomerInfo.removeAlreadyCustomers();
					CustomerInfo.setSelectedCustomer(customer);
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

	public AddAndUpdateCustomer(Frame frame, Object[] input, String toFilter){
		this(frame);
		setTitle("Update Customer Details");

		tfCustomerName.setText((String)input[1]);
		tfPhone.setText((String)input[2]);
		tfAddress.setText((String)input[3]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("Update"); add(btnUpdate);
		btnUpdate.setBounds(20, 150, 150, 50);

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String customerName = tfCustomerName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {customerName, phone, address};

				if(!tfCustomerName.getText().equals(input[1]) && CustomerTable.isCustomerNameExist(customerName)){
					JOptionPane.showMessageDialog(null, "Customer Name already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					CustomerTable.update(data, (int)input[0]);
					CustomerInfo.createCustomerTable(CustomerTable.retrieveFilterByCustomerName(toFilter));
					CustomerInfo.removeDefaultCustomer();
					CustomerInfo.removeAlreadyCustomers();
					CustomerInfo.setSelectedRow((int)input[0]);
					setVisible(false);
					dispose();
				}
			}
		});
	}

	private boolean isEmpty(String[] data){
		String[] labels = {"Customer Name", "Phone", "Address"};
		for(int i = 0; i < data.length; i++){
			if(data[i].equals("")){
				JOptionPane.showMessageDialog(null, labels[i]+" cannot be empty.", "Empty Error", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	private static void resetTextFields(){
		tfCustomerName.setText("");
		tfPhone.setText("");
		tfAddress.setText("");
	}
}
