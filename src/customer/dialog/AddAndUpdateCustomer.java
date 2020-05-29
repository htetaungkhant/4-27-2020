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
import external_classes.Fonts;
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
		setTitle("ဝယ်သူအသစ်ထည့်ခြင်း");
		setSize(360, 280);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lbCustomerName = new JLabel("ဝယ်သူအမည်"); lbCustomerName.setFont(Fonts.pyisuNormal15); add(lbCustomerName);
		JLabel lbPhone = new JLabel("ဖုန်းနံပါတ်"); lbPhone.setFont(Fonts.pyisuNormal15); add(lbPhone);
		JLabel lbAddress = new JLabel("လိပ်စာ"); lbAddress.setFont(Fonts.pyisuNormal15); add(lbAddress);

		tfCustomerName = new MyTextField(); tfCustomerName.setFont(Fonts.pyisuNormal15); add(tfCustomerName);
		tfPhone = new JNumberTextField(15); tfPhone.setFont(Fonts.pyisuNormal15); add(tfPhone);
		tfAddress = new MyTextField(); tfAddress.setFont(Fonts.pyisuNormal15); add(tfAddress);
		resetTextFields();

		btnAdd = new JButton("ထည့်မည်"); btnAdd.setFont(Fonts.pyisuNormal15); add(btnAdd);
		btnCancel = new JButton("မလုပ်ဆောင်ပါ"); btnCancel.setFont(Fonts.pyisuNormal15); add(btnCancel);

		lbCustomerName.setBounds(20, 20, 110, 40); tfCustomerName.setBounds(130, 20, 200, 40);
		lbPhone.setBounds(20, 70, 110, 40); tfPhone.setBounds(130, 70, 200, 40);
		lbAddress.setBounds(20, 120, 110, 40); tfAddress.setBounds(130, 120, 200, 40);
		btnCancel.setBounds(20, 180, 150, 50); btnAdd.setBounds(180, 180, 150, 50);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String customerName = tfCustomerName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {customerName, phone, address};
				if(CustomerTable.isCustomerExist(customerName)){
					JLabel label= new JLabel("ဝယ်သူရှိပီးသားဖြစ်ပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
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
		setTitle("ဝယ်သူအချက်အလက်ပြောင်းလဲခြင်း");

		tfCustomerName.setText((String)input[1]);
		tfPhone.setText((String)input[2]);
		tfAddress.setText((String)input[3]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("ပြောင်းလဲမည်"); btnUpdate.setFont(Fonts.pyisuNormal15); add(btnUpdate);
		btnUpdate.setBounds(180, 180, 150, 50);

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String customerName = tfCustomerName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {customerName, phone, address};

				if(!tfCustomerName.getText().equals(input[1]) && CustomerTable.isCustomerNameExist(customerName)){
					JLabel label = new JLabel("ဝယ်သူရှိပီးသားဖြစ်ပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
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
		String[] labels = {"ဝယ်သူအမည်", "ဖုန်းနံပါတ်", "လိပ်စာ"};
		for(int i = 0; i < data.length; i++){
			if(data[i].equals("")){
				JLabel label = new JLabel(labels[i]+"ထည့်ပါ");
				label.setFont(Fonts.pyisuNormal15);
				JOptionPane.showMessageDialog(null, label, "အလွတ်ဖြစ်နေမှု", JOptionPane.INFORMATION_MESSAGE);
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
