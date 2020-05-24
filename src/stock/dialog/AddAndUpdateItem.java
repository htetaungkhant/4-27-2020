package stock.dialog;

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
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import stock.Items;

public class AddAndUpdateItem extends JDialog{

	private static JLabel lbItemName;
	private static JLabel lbBarcode;
	private static JLabel lbCost;
	private static JLabel lbSalePrice;
	private static JLabel lbQuantity;
	private static JLabel lbLimitQuantity;
	private static JLabel lbRemark;

	private static MyTextField tfItemName;
	private static MyTextField tfBarcode;
	private static JNumberTextField tfCost;
	private static JNumberTextField tfSalePrice;
	private static JNumberTextField tfQuantity;
	private static JNumberTextField tfLimitQuantity ;
	private static MyTextField tfRemark;

	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnCancel;

	private static JNumberTextField tfPurchasePrice;
	public AddAndUpdateItem(Frame parent){
		super(parent, true);
		this.tfPurchasePrice = null;
		ImageIcon frameIcon = new ImageIcon("picture/items_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("ကုန်ပစ္စည်းအသစ်ထည့်သွင်းခြင်း");
		setSize(450, 500);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		lbItemName = new JLabel("ကုန်ပစ္စည်းအမည်"); lbItemName.setFont(Fonts.pyisuNormal15); add(lbItemName);
		lbBarcode = new JLabel("ဘားကုဒ်"); lbBarcode.setFont(Fonts.pyisuNormal15); add(lbBarcode);
		lbCost = new JLabel("ကုန်ကျစရိတ်"); lbCost.setFont(Fonts.pyisuNormal15); add(lbCost);
		lbSalePrice = new JLabel("ရောင်းဈေး"); lbSalePrice.setFont(Fonts.pyisuNormal15); add(lbSalePrice);
		lbQuantity = new JLabel("အရေအတွက်"); lbQuantity.setFont(Fonts.pyisuNormal15); add(lbQuantity);
		lbLimitQuantity = new JLabel("သတ်မှတ်အရေအတွက်"); lbLimitQuantity.setFont(Fonts.pyisuNormal15); add(lbLimitQuantity);
		lbRemark = new JLabel("မှတ်ချက်"); lbRemark.setFont(Fonts.pyisuNormal15); add(lbRemark);

		tfItemName = new MyTextField(); tfItemName.setFont(Fonts.pyisuNormal15); tfItemName.setHorizontalAlignment(JLabel.RIGHT); add(tfItemName);
		tfBarcode = new MyTextField(); tfBarcode.setFont(Fonts.pyisuNormal15); tfBarcode.setHorizontalAlignment(JLabel.RIGHT); add(tfBarcode);
		tfCost = new JNumberTextField(10); tfCost.setFont(Fonts.pyisuNormal15); tfCost.setHorizontalAlignment(JLabel.RIGHT); add(tfCost);
		tfSalePrice = new JNumberTextField(10); tfSalePrice.setFont(Fonts.pyisuNormal15); tfSalePrice.setHorizontalAlignment(JLabel.RIGHT); add(tfSalePrice);
		tfQuantity = new JNumberTextField(5); tfQuantity.setFont(Fonts.pyisuNormal15); tfQuantity.setHorizontalAlignment(JLabel.RIGHT); add(tfQuantity);
		tfLimitQuantity = new JNumberTextField(5); tfLimitQuantity.setFont(Fonts.pyisuNormal15); tfLimitQuantity.setHorizontalAlignment(JLabel.RIGHT); add(tfLimitQuantity);
		tfRemark = new MyTextField(); tfRemark.setFont(Fonts.pyisuNormal15); tfRemark.setHorizontalAlignment(JLabel.RIGHT); add(tfRemark);
//		resetTextFields();

		btnAdd = new JButton("ထည့်မည်");  btnAdd.setFont(Fonts.pyisuNormal15); add(btnAdd);
		btnCancel = new JButton("မလုပ်ဆောင်ပါ"); btnCancel.setFont(Fonts.pyisuNormal15); add(btnCancel);

		lbItemName.setBounds(20, 20, 100, 40); tfItemName.setBounds(170, 20, 240, 40);
		lbBarcode.setBounds(20, 70, 100, 40); tfBarcode.setBounds(170, 70, 240, 40);
		lbCost.setBounds(20, 120, 100, 40); tfCost.setBounds(170, 120, 240, 40);
		lbSalePrice.setBounds(20, 170, 100, 40); tfSalePrice.setBounds(170, 170, 240, 40);
		lbQuantity.setBounds(20, 220, 100, 40); tfQuantity.setBounds(170, 220, 240, 40);
		lbLimitQuantity.setBounds(20, 270, 140, 40); tfLimitQuantity.setBounds(170, 270, 240, 40);
		lbRemark.setBounds(20, 320, 100, 40); tfRemark.setBounds(170, 320, 240, 40);
		btnCancel.setBounds(50, 390, 150, 50); btnAdd.setBounds(230, 390, 150, 50);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String itemName = tfItemName.getText();
				String barcode = tfBarcode.getText();
				String cost = tfCost.getText();
				String salePrice = tfSalePrice.getText();
				String quantity = tfQuantity.getText();
				String limitQuantity =tfLimitQuantity.getText();
				String remark = tfRemark.getText();
				String[] data = {itemName, barcode, cost, salePrice, quantity, limitQuantity, remark};
				if(StockTable.isItemExist(itemName, barcode)){
					JLabel label = new JLabel("ကုန်ပစ္စည်းရှိပီးသားဖြစ်နေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
//					resetTextFields();
					JLabel label = new JLabel("ထည့်မှာသေချာပါပီလား။ ပြန်ဖျက်၍ မရနိုင်ပါ။");
					label.setFont(Fonts.pyisuNormal15);
					int result = JOptionPane.showConfirmDialog(null, label, "သေချာလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION){
							StockTable.insert(data);
							String selectedItem = Items.getSelectedItem();
							Items.createItemListTable(StockTable.retrieveAll());
							Items.removeAlreadyItems();
							if(selectedItem != null) Items.setSelectedItem(selectedItem); else Items.setSelectedItem(itemName);
							if(tfPurchasePrice != null) tfPurchasePrice.setText(cost);
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

	public AddAndUpdateItem(Frame parent, JNumberTextField tfPurchasePrice){
		this(parent);
		this.tfPurchasePrice = tfPurchasePrice;
		setSize(450, 450);

		lbQuantity.setVisible(false);
		tfQuantity.setText("0");
		tfQuantity.setVisible(false);
		
		lbLimitQuantity.setBounds(20, 220, 100, 40); tfLimitQuantity.setBounds(170, 220, 240, 40);
		lbRemark.setBounds(20, 270, 140, 40); tfRemark.setBounds(170, 270, 240, 40);
		btnCancel.setBounds(50, 340, 150, 50); btnAdd.setBounds(230, 340, 150, 50);
	}

	public AddAndUpdateItem(Frame parent, Object[] input, String toFilter){
		this(parent);
		setTitle("ကုန်ပစ္စည်းအချက်အလက်ပြောင်းလဲခြင်း");

		tfItemName.setText((String)input[1]);
		tfBarcode.setText((String)input[2]);
		tfCost.setText(Integer.toString((int)input[3]));tfCost.setEditable(false);
		tfSalePrice.setText(Integer.toString((int)input[4]));
		tfQuantity.setText(Integer.toString((int)input[5]));
		tfLimitQuantity.setText(Integer.toString((int)input[6]));
		tfRemark.setText((String)input[7]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("ပြောင်းလဲမည်"); btnUpdate.setFont(Fonts.pyisuNormal15); add(btnUpdate);
		btnUpdate.setBounds(230, 390, 150, 50);

		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String itemName = tfItemName.getText();
				String barcode = tfBarcode.getText();
				String cost = tfCost.getText();
				String salePrice = tfSalePrice.getText();
				String quantity = tfQuantity.getText();
				String quantityLimit = tfLimitQuantity.getText();
				String remark = tfRemark.getText();
				String[] data = {itemName, barcode, cost, salePrice, quantity, quantityLimit, remark};

				if(!tfItemName.getText().equals(input[1]) && StockTable.isItemNameExist(itemName)){
					JLabel label = new JLabel("ကုန်ပစ္စည်းအမည်ရှိပီးသားဖြစ်နေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!tfBarcode.getText().equals(input[2]) && StockTable.isBarcodeExist(barcode)){
					JLabel label = new JLabel("ဘားကုဒ်ရှိပီးသားဖြစ်နေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရှိပီးသား", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					StockTable.update(data, (int)input[0]);
					Items.createItemListTable(StockTable.retrieveFilterByItemName(toFilter));
					Items.removeAlreadyItems();
					Items.setSelectedRow((int)input[0]);
					setVisible(false);
					dispose();
				}
			}
		});
	}

	private boolean isEmpty(String[] data){
		String[] labels = {"ကုန်ပစ္စည်းအမည်", "ဘားကုဒ်", "ကုန်ကျစရိတ်", "ရောင်းဈေး", "အရေအတွက်", "သတ်မှတ်အရေအတွက်"};
		for(int i = 0; i < data.length-1; i++){
			if(data[i].equals("")){
				JLabel label = new JLabel(labels[i]+"ထည့်ပါ");
				label.setFont(Fonts.pyisuNormal15);
				JOptionPane.showMessageDialog(null, label, "အလွတ်မဖြစ်နိုင်ပါ", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

//	private static void resetTextFields(){
//		tfItemName.setText("");
//		tfBarcode.setText("");
//		tfPurchasePrice.setText("");
//		tfSalePrice.setText("");
//		tfQuantity.setText("");
//		tfLimitQuantity.setText("");
//		tfRemark.setText("");
//	}
}
