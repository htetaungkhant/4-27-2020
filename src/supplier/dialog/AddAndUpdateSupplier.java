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
import external_classes.Fonts;
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
		setTitle("ကုန်ပေးသူအသစ်သွင်းခြင်း");
		setSize(360, 300);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lbSupplierName = new JLabel("ဆိုင်နာမည်"); lbSupplierName.setFont(Fonts.pyisuNormal15); add(lbSupplierName);
		JLabel lbPhone = new JLabel("ဖုန်းနံပါတ်"); lbPhone.setFont(Fonts.pyisuNormal15); add(lbPhone);
		JLabel lbAddress = new JLabel("လိပ်စာ"); lbAddress.setFont(Fonts.pyisuNormal15); add(lbAddress);

		tfSupplierName = new MyTextField(); tfSupplierName.setFont(Fonts.pyisuNormal15); add(tfSupplierName);
		tfPhone = new JNumberTextField(15); tfPhone.setFont(Fonts.pyisuNormal15); add(tfPhone);
		tfAddress = new MyTextField(); tfAddress.setFont(Fonts.pyisuNormal15); add(tfAddress);
		resetTextFields();

		btnAdd = new JButton("ထည့်မည်"); btnAdd.setFont(Fonts.pyisuNormal15); add(btnAdd);
		btnCancel = new JButton("မလုပ်ဆောင်ပါ"); btnCancel.setFont(Fonts.pyisuNormal15); add(btnCancel);

		lbSupplierName.setBounds(20, 20, 100, 40); tfSupplierName.setBounds(130, 20, 200, 40);
		lbPhone.setBounds(20, 70, 100, 40); tfPhone.setBounds(130, 70, 200, 40);
		lbAddress.setBounds(20, 120, 100, 40); tfAddress.setBounds(130, 120, 200, 40);
		btnCancel.setBounds(20, 190, 150, 50); btnAdd.setBounds(180, 190, 150, 50);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String supplierName = tfSupplierName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {supplierName, phone, address};
				if(SupplierTable.isSupplierExist(supplierName)){
					JLabel label = new JLabel("ကုန်ပေးသူရှိပီးသားဖြစ်နေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					JLabel label = new JLabel("ထည့်မှာသေချာပါပီလား။ ပြန်ဖျက်၍ မရနိုင်ပါ။");
					label.setFont(Fonts.pyisuNormal15);
					int result = JOptionPane.showConfirmDialog(null, label, "သေချာလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION){
							SupplierTable.insert(data);
							String supplier = SupplierInfo.getSelectedSupplier();
							SupplierInfo.createSupplierTable(SupplierTable.retrieveAll());
							SupplierInfo.removeAlreadySupplier();
							SupplierInfo.setSelectedSupplier(supplier);
							setVisible(false);
							dispose();
						}
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
		setTitle("ကုန်ပေးသူအချက်အလက်ပြောင်းလဲခြင်း");

		tfSupplierName.setText((String)input[1]);
		tfPhone.setText((String)input[2]);
		tfAddress.setText((String)input[3]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("ပြောင်းလဲမည်"); btnUpdate.setFont(Fonts.pyisuNormal15); add(btnUpdate);
		btnUpdate.setBounds(180, 190, 150, 50);

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String supplierName = tfSupplierName.getText();
				String phone = tfPhone.getText();
				String address = tfAddress.getText();
				String[] data = {supplierName, phone, address};

				if(supplierName.equals(input[1]) && phone.equals(input[2]) && address.equals(input[3])) {
					setVisible(false);
					dispose();					
				}
				else if(!supplierName.equals(input[1]) && SupplierTable.isSupplierNameExist(supplierName)){
					JLabel label = new JLabel("ကုန်ပေးသူရှိပီးသားဖြစ်နေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					JLabel label = new JLabel("ပြောင်းလဲမှာသေချာပီလား");
					label.setFont(Fonts.pyisuNormal15);
					int result = JOptionPane.showConfirmDialog(null, label, "သေချာလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION){
							SupplierTable.update(data, (int)input[0]);
							SupplierInfo.createSupplierTable(SupplierTable.retrieveFilterBySupplierName(toFilter));
							SupplierInfo.removeAlreadySupplier();
							SupplierInfo.setSelectedRow((int)input[0]);
							setVisible(false);
							dispose();
					}
				}
			}
		});
	}

	private boolean isEmpty(String[] data){
		String[] labels = {"ကုန်ပေးသူအမည်", "ဖုန်းနံပါတ်", "လိပ်စာ"};
		for(int i = 0; i < data.length; i++){
			if(data[i].equals("")){
				JLabel label = new JLabel(labels[i]+"ထည့်ပါ");
				label.setFont(Fonts.pyisuNormal15);
				JOptionPane.showMessageDialog(null, label, "အလွတ်မဖြစ်နိုင်ပါ", JOptionPane.INFORMATION_MESSAGE);
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
