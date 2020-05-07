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
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import stock.Items;

public class AddNewItem extends JDialog{
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

	public AddNewItem(Frame parent, String[] itemNameList){
		super(parent, true);
		ImageIcon frameIcon = new ImageIcon("picture/items_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Add New Item");
		setSize(360, 400);
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lbItemName = new JLabel("Item Name"); add(lbItemName);
		JLabel lbBarcode = new JLabel("Barcode"); add(lbBarcode);
		JLabel lbCost = new JLabel("Cost"); add(lbCost);
		JLabel lbSalePrice = new JLabel("Sale Price"); add(lbSalePrice);
		JLabel lbQuantity = new JLabel("Quantity"); add(lbQuantity);
		JLabel lbLimitQuantity = new JLabel("Quantity Limit"); add(lbLimitQuantity);
		JLabel lbRemark = new JLabel("Remark"); add(lbRemark);

		tfItemName = new MyTextField(); add(tfItemName);
		tfBarcode = new MyTextField(); add(tfBarcode);
		tfCost = new JNumberTextField(10); add(tfCost);
		tfSalePrice = new JNumberTextField(10); add(tfSalePrice);
		tfQuantity = new JNumberTextField(5); add(tfQuantity);
		tfLimitQuantity = new JNumberTextField(5); add(tfLimitQuantity);
		tfRemark = new MyTextField(); add(tfRemark);
//		resetTextFields();

		btnAdd = new JButton("Add"); add(btnAdd);
		btnCancel = new JButton("Cancel"); add(btnCancel);

		lbItemName.setBounds(20, 20, 100, 30); tfItemName.setBounds(130, 20, 200, 30);
		lbBarcode.setBounds(20, 60, 100, 30); tfBarcode.setBounds(130, 60, 200, 30);
		lbCost.setBounds(20, 100, 100, 30); tfCost.setBounds(130, 100, 200, 30);
		lbSalePrice.setBounds(20, 140, 100, 30); tfSalePrice.setBounds(130, 140, 200, 30);
		lbQuantity.setBounds(20, 180, 100, 30); tfQuantity.setBounds(130, 180, 200, 30);
		lbLimitQuantity.setBounds(20, 220, 100, 30); tfLimitQuantity.setBounds(130, 220, 200, 30);
		lbRemark.setBounds(20, 260, 100, 30); tfRemark.setBounds(130, 260, 200, 30);
		btnAdd.setBounds(20, 310, 150, 50); btnCancel.setBounds(180, 310, 150, 50);

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
					JOptionPane.showMessageDialog(null, "Item already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					StockTable.insert(data);
//					resetTextFields();
					String selectedItem = Items.getSelectedItem();
					Items.createItemListTable(StockTable.retrieveAll());
					Items.removeAlreadyItems(itemNameList);
					Items.setSelectedItem(selectedItem);
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

	public AddNewItem(Frame parent, String[] input, String toFilter, String[] itemNameList){
		this(parent, itemNameList);
		setTitle("Update Item Details");

		tfItemName.setText(input[0]);
		tfBarcode.setText(input[1]);
		tfCost.setText(input[2]);tfCost.setEditable(false);
		tfSalePrice.setText(input[3]);
		tfQuantity.setText(input[4]);
		tfLimitQuantity.setText(input[5]);
		tfRemark.setText(input[6]);

		btnAdd.setVisible(false);
		btnUpdate = new JButton("Update"); add(btnUpdate);
		btnUpdate.setBounds(20, 310, 150, 50);

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

				if(!tfItemName.getText().equals(input[0]) && StockTable.isItemNameExist(itemName)){
					JOptionPane.showMessageDialog(null, "ItemName already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!tfBarcode.getText().equals(input[1]) && StockTable.isBarcodeExist(barcode)){
					JOptionPane.showMessageDialog(null, "Barcode already Exists.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!isEmpty(data)){
					StockTable.update(data, input[1]);
					int row = Items.getSelectedRow();
					Items.createItemListTable(StockTable.retrieveFilterByItemName(toFilter));
					Items.removeAlreadyItems(itemNameList);
					Items.setSelectedRow(row);
					setVisible(false);
					dispose();
				}
			}
		});
	}

	private boolean isEmpty(String[] data){
		String[] labels = {"Item Name", "Barcode", "Purchase Price", "Sale Price", "Quantity", "Quantity Limit"};
		for(int i = 0; i < data.length-1; i++){
			if(data[i].equals("")){
				JOptionPane.showMessageDialog(null, labels[i]+" cannot be empty.", "Empty Error", JOptionPane.INFORMATION_MESSAGE);
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
