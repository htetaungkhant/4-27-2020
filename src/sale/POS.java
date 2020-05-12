package sale;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.alee.extended.date.WebDateField;

import database.StockTable;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import net.miginfocom.swing.MigLayout;

public class POS extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForItemList;
	private static JTable itemList;

	private static JNumberTextField tfTotalAmount;
	private static JNumberTextField tfNetAmount;
	private static JNumberTextField tfDiscount;

	public POS() {
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new MigLayout());
		JButton btnCustomer = new JButton("Default Customer");
		btnCustomer.setPreferredSize(new Dimension(150, 40));
		JLabel lbBarcode = new JLabel("Barcode");
		MyTextField tfBarcode = new MyTextField();
		tfBarcode.setPreferredSize(new Dimension(150, 40));
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        tfBarcode.requestFocus();
		      }
		    });
		JLabel lbPrice = new JLabel("Price");
		JNumberTextField tfPrice = new JNumberTextField(10);
		tfPrice.setEditable(false);
		tfPrice.setFocusable(false);
		tfPrice.setHorizontalAlignment(JLabel.RIGHT);
		tfPrice.setPreferredSize(new Dimension(100, 40));
		JLabel lbQty = new JLabel("Qty");
		JNumberTextField tfQty = new JNumberTextField(10);
		tfQty.setHorizontalAlignment(JLabel.RIGHT);
		tfQty.setPreferredSize(new Dimension(50, 40));
		JButton btnAddItem = new JButton("ADD");
		btnAddItem.setPreferredSize(new Dimension(60, 40));
		topLeftPanel.add(btnCustomer, "wrap");
		topLeftPanel.add(lbBarcode, "split 2");
		topLeftPanel.add(tfBarcode, "gapleft 17");
		topLeftPanel.add(lbPrice);
		topLeftPanel.add(tfPrice);
		topLeftPanel.add(lbQty);
		topLeftPanel.add(tfQty);
		topLeftPanel.add(btnAddItem);
		topPanel.add(topLeftPanel, BorderLayout.WEST);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lbDate = new JLabel("Date");
		WebDateField datePicker = new WebDateField(new Date());
		datePicker.setPreferredSize(120, 40);
		datePicker.setAllowUserInput(false);
		topRightPanel.add(lbDate);
		topRightPanel.add(datePicker);
		topPanel.add(topRightPanel, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		itemList = new JTable(modelForItemList);
		createItemListTable(null);

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new BorderLayout());

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel(new MigLayout());
		JLabel lbTotalAmount = new JLabel("Total Amount");
		tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setText("0");
		tfTotalAmount.setEditable(false);
		tfTotalAmount.setFocusable(false);
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		JLabel lbNetAmount = new JLabel("Net Amount");
		tfNetAmount = new JNumberTextField(10);
		tfNetAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfNetAmount.setPreferredSize(new Dimension(120, 40));
		JLabel lbDiscount = new JLabel("Discount");
		tfDiscount = new JNumberTextField(10);
		tfDiscount.setText("0");
		tfDiscount.setEditable(false);
		tfDiscount.setFocusable(false);
		tfDiscount.setHorizontalAlignment(JLabel.RIGHT);
		tfDiscount.setPreferredSize(new Dimension(120, 40));
		JButton btnReset = new JButton("Reset");
		btnReset.setPreferredSize(new Dimension(80, 40));
		JButton btnSave = new JButton("Save");
		btnSave.setPreferredSize(new Dimension(80, 40));
		JButton btnPrint = new JButton("Print");
		btnPrint.setPreferredSize(new Dimension(80, 40));
		bottomRightPanel.add(lbTotalAmount);
		bottomRightPanel.add(tfTotalAmount, "gapleft 20, wrap");
		bottomRightPanel.add(lbNetAmount);
		bottomRightPanel.add(tfNetAmount, "gapleft 20, wrap");
		bottomRightPanel.add(lbDiscount);
		bottomRightPanel.add(tfDiscount, "gapleft 20, wrap");
		bottomRightPanel.add(btnReset);
		bottomRightPanel.add(btnSave, "split 2");
		bottomRightPanel.add(btnPrint);
		bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		tfBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] itemDetail = StockTable.getItemDetail(tfBarcode.getText());
				if(tfBarcode.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please, fill barcode", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(itemDetail == null){
					JOptionPane.showMessageDialog(null, "Invalid barcode", "Invalid", JOptionPane.ERROR_MESSAGE);
					tfBarcode.setText("");
				}
				else if((int)itemDetail[4] == 0){
					JOptionPane.showMessageDialog(null, "This item is Out of Stock", "Can't get", JOptionPane.INFORMATION_MESSAGE);
					tfBarcode.setText("");
				}
				else{
					tfPrice.setText(Integer.toString((int) itemDetail[3]));
					tfQty.requestFocus();
				}
			}
		});

		tfBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_END){
					tfNetAmount.requestFocus();
				}
			}
		});

		tfQty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] itemDetail = StockTable.getItemDetail(tfBarcode.getText());
				if(tfBarcode.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please, fill barcode", "Error", JOptionPane.INFORMATION_MESSAGE);
					tfQty.setText("");
					tfPrice.setText("");
					tfBarcode.requestFocus();
				}
				else if(itemDetail == null){
					JOptionPane.showMessageDialog(null, "Invalid barcode", "Invalid", JOptionPane.ERROR_MESSAGE);
					tfBarcode.setText("");
				}
				else if(tfQty.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please, fill qty", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(Integer.parseInt(tfQty.getText()) == 0){
					tfQty.setText("");
					tfPrice.setText("");
					tfBarcode.setText("");
					tfBarcode.requestFocus();
				}
				else if((int)itemDetail[4] < (Integer.parseInt(tfQty.getText())+getAlreadyItemQuantity((String) itemDetail[1]))){
					JOptionPane.showMessageDialog(null, "Can't get this quantity.", "Can't get", JOptionPane.ERROR_MESSAGE);
				}
				else{
					//do something
					int qty = Integer.parseInt(tfQty.getText());
					addItem2Invoice(qty, itemDetail);
					tfQty.setText("");
					tfPrice.setText("");
					tfBarcode.setText("");
					tfBarcode.requestFocus();
				}
			}
		});

		itemList.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int row = itemList.getSelectedRow();
					if(itemList.getSelectedColumn()==itemList.getColumnCount()-1){
						int amount = Integer.parseInt(tfTotalAmount.getText()) - (int)itemList.getValueAt(row, 3);
						tfTotalAmount.setText("");
						tfTotalAmount.setText(Integer.toString(amount));
						if(!tfNetAmount.getText().equals("")){
							tfDiscount.setText("");
							tfDiscount.setText(Integer.toString(Integer.parseInt(tfTotalAmount.getText()) - Integer.parseInt(tfNetAmount.getText())));
						}
						modelForItemList.removeRow(row);
						tfBarcode.requestFocus();
					}
			}
		});

		tfNetAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tfNetAmount.getText().equals("")){
					tfDiscount.setText("");
					tfDiscount.setText(Long.toString(Long.parseLong(tfTotalAmount.getText()) - 0));
					}
				else{
					tfDiscount.setText("");
					tfDiscount.setText(Long.toString(Long.parseLong(tfTotalAmount.getText()) - Long.parseLong(tfNetAmount.getText())));
					}
			}
		});

		tfNetAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(itemList.getRowCount() == 0){
					JOptionPane.showMessageDialog(null, "Please, add some items for sale.", "Error", JOptionPane.INFORMATION_MESSAGE);
					tfBarcode.requestFocus();
				}
				else if(tfNetAmount.getText().equals("") || Integer.parseInt(tfNetAmount.getText()) == 0){
					JOptionPane.showMessageDialog(null, "Please, fill suitable net amount.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCustomer.setText("Default Customer");
				datePicker.setDate(new Date());
				tfBarcode.setText("");
				tfPrice.setText("");
				tfQty.setText("");
				modelForItemList.setRowCount(0);
				tfTotalAmount.setText("");
				tfTotalAmount.setText("0");
				tfNetAmount.setText("");
				tfDiscount.setText("");
				tfDiscount.setText("0");
				tfBarcode.requestFocus();
			}
		});
	}

	public static void addItem2Invoice(int qty, Object[] itemDetail){
		Boolean isExist = false;
		for(int row = 0; row < itemList.getRowCount(); row++){
			if(itemList.getValueAt(row, 0).equals(itemDetail[1])){
				isExist = true;
				qty = qty + (int)itemList.getValueAt(row, 1);
				int amount = qty*(int)itemDetail[3];
				int originalTotalAmount = Integer.parseInt(tfTotalAmount.getText());
				tfTotalAmount.setText("");
				tfTotalAmount.setText(Integer.toString((originalTotalAmount - (int)itemList.getValueAt(row, 3)) + amount));
				itemList.setValueAt(qty, row, 1);
				itemList.setValueAt(amount, row, 3);
				break;
			}
		}

		if(!isExist){
			int amount = qty*(int)itemDetail[3];
			int originalTotalAmount = Integer.parseInt(tfTotalAmount.getText());
			tfTotalAmount.setText("");
			tfTotalAmount.setText(Integer.toString(originalTotalAmount + amount));
			Object[] rowData = {itemDetail[0], itemDetail[1], qty, itemDetail[2], itemDetail[3], amount, new ImageIcon("picture/delete_icon.png")};
			modelForItemList.addRow(rowData);
		}
	}

	public static int getAlreadyItemQuantity(String itemName){
		int result = 0;
		for(int row = 0; row < itemList.getRowCount(); row++){
			if(itemList.getValueAt(row, 0).equals(itemName)){
				return (int)itemList.getValueAt(row, 1);
			}
		}
		return result;
	}

	public static void createItemListTable(Object[][] input){
		tableData = input;
		columnNames = new String[]{"idstock", "Item Name", "Quantity", "cost", "Unit Price", "Amount", "Del"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int column){
				return getValueAt(0,column).getClass();
			}
		};
		itemList.setModel(modelForItemList);
		itemList.setRowHeight(40);
		itemList.removeColumn(itemList.getColumnModel().getColumn(0));
		itemList.removeColumn(itemList.getColumnModel().getColumn(2));
		TableColumn column5 = itemList.getColumnModel().getColumn(4);
		column5.setMinWidth(40);
		column5.setMaxWidth(100);
		column5.setPreferredWidth(50);
	}
}
