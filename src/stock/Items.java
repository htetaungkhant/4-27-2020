package stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.sun.javafx.font.Disposer;

import database.StockTable;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import purchase.dialog.AddNewPurchaseRecord;
import stock.dialog.AddNewItem;
import stock.dialog.ItemsToPurchase;

public class Items extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForItemList;
	private static JTable itemList;

	private static JButton btnItem2Purchase;

	public Items(){
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnAddItem = new JButton("Add New Item");
		btnAddItem.setPreferredSize(new Dimension(150, 40));
		btnItem2Purchase = new JButton("Display Items to buy");
		btnItem2Purchase.setPreferredSize(new Dimension(200, 40));
		topLeftPanel.add(btnAddItem);
		topLeftPanel.add(btnItem2Purchase);
		topPanel.add(topLeftPanel);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyTextField tfSearch = new MyTextField(30, "Search your Item");
		tfSearch.setPreferredSize(new Dimension(250,40));
		topRightPanel.add(tfSearch);
		topPanel.add(topRightPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		itemList = new JTable(modelForItemList);
		createItemListTable(StockTable.retrieveAll());

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		btnItem2Purchase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemsToPurchase items2Purchase = new ItemsToPurchase(Main.frame);
				items2Purchase.setVisible(true);
			}
		});

		btnAddItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewItem addNewItem = new AddNewItem(Main.frame);
				addNewItem.setVisible(true);
			}
		});

		tfSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddItem.setEnabled(true);
					createItemListTable(StockTable.retrieveAll());
				}
				else{
					btnAddItem.setEnabled(false);
					createItemListTable(StockTable.retrieveFilterByItemName(tfSearch.getText()));
				}
			}		@Override
			public void keyReleased(KeyEvent e) {

				if(tfSearch.getText().equals("")){
					btnAddItem.setEnabled(true);
					createItemListTable(StockTable.retrieveAll());
				}
				else{
					btnAddItem.setEnabled(false);
					createItemListTable(StockTable.retrieveFilterByItemName(tfSearch.getText()));
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddItem.setEnabled(true);
					createItemListTable(StockTable.retrieveAll());
				}
				else{
					btnAddItem.setEnabled(false);
					createItemListTable(StockTable.retrieveFilterByItemName(tfSearch.getText()));
				}
			}
		});

		itemList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					String[] data = {
							(String) itemList.getValueAt(row, 0),
							(String) itemList.getValueAt(row, 1),
							Integer.toString((int)itemList.getValueAt(row, 2)),
							Integer.toString((int)itemList.getValueAt(row, 3)),
							Integer.toString((int)itemList.getValueAt(row, 4)),
							Integer.toString((int)itemList.getValueAt(row, 5)),
							(String) itemList.getValueAt(row, 6),
					};
					AddNewItem addNewItem = new AddNewItem(Main.frame, data, tfSearch.getText());
					addNewItem.setVisible(true);
				}
			}
		});
	}

	public Items(JDialog d){
		this();
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		btnItem2Purchase.setVisible(false);

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel();
		JLabel lbQuantity = new JLabel("Enter Quantity");
		lbQuantity.setHorizontalAlignment(JLabel.RIGHT);
		lbQuantity.setPreferredSize(new Dimension(100, 40));
		JNumberTextField tfQuantity = new JNumberTextField(10);
		tfQuantity.setHorizontalAlignment(JLabel.RIGHT);
		tfQuantity.setPreferredSize(new Dimension(100, 40));
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(100, 40));
		JButton btnAdd = new JButton("Add");
		btnAdd.setPreferredSize(new Dimension(100, 40));

		GroupLayout groupLayout = new GroupLayout(bottomRightPanel);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		bottomRightPanel.setLayout(groupLayout);
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(lbQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(tfQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lbQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(tfQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

		bottomPanel.add(bottomRightPanel);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				d.dispose();
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(itemList.getSelectionModel().isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Please, select item", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(tfQuantity.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please, enter quantity", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					d.setVisible(false);
					d.dispose();
					int row = itemList.getSelectedRow();
					String itemName = (String)itemList.getValueAt(row, 0);
					int unitPrice = (int)itemList.getValueAt(row, 2);
					int quantity = Integer.parseInt(tfQuantity.getText());
					int amount = unitPrice * quantity;
					Object[] data = {itemName, unitPrice, quantity, amount, new ImageIcon("picture/delete_icon.png")};
//					AddNewPurchaseRecord.setTotalAmount(amount);
					AddNewPurchaseRecord.addItem(data);
				}
			}
		});
	}

	public static void createItemListTable(Object[][] input){
		tableData = input;
		columnNames = new String[]{"Item Name", "Barcode", "Purchase Price", "Sale Price", "Quantity","Quantity Limit","Remark"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
//	            return tableData[0][columnIndex].getClass();
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		itemList.setModel(modelForItemList);
		itemList.setRowHeight(30);
//		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
//		DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
//		centerRender.setHorizontalAlignment(JLabel.CENTER);
//		TableColumn column0 = itemList.getColumnModel().getColumn(0);
//		column0.setCellRenderer(centerRender);
//		TableColumn column1 = itemList.getColumnModel().getColumn(1);
//		column1.setCellRenderer(centerRender);
//		TableColumn column2 = itemList.getColumnModel().getColumn(2);
//		column2.setCellRenderer(rightRenderer);
//		TableColumn column3 = itemList.getColumnModel().getColumn(3);
//		column3.setCellRenderer(rightRenderer);
//		TableColumn column4 = itemList.getColumnModel().getColumn(4);
//		column4.setCellRenderer(rightRenderer);
//		TableColumn column5 = itemList.getColumnModel().getColumn(5);
//		column5.setCellRenderer(rightRenderer);
//		TableColumn column6 = itemList.getColumnModel().getColumn(6);
//		column6.setCellRenderer(rightRenderer);
//		TableColumn column7 = itemList.getColumnModel().getColumn(7);
//		column7.setMinWidth(40);
//		column7.setMaxWidth(100);
//		column7.setPreferredWidth(50);
	}

	public static int getTableRow(){
		return itemList.getSelectedRow();
	}

	public static void setSelectedRow(int row){
		itemList.setRowSelectionInterval(row, row);
	}
}
