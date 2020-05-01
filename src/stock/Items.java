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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import database.StockTable;
import external_classes.MyTextField;
import main.Main;
import stock.dialog.AddNewItem;
import stock.dialog.ItemsToPurchase;

public class Items extends JPanel{

	private static String[] columnNames = {"Item Name", "Barcode", "Purchase Price", "Sale Price", "Quantity","Quantity Limit","Remark"};
	private static Object[][] tableData;
	private static TableModel modelForItemList;
	private static JTable itemList;

	public Items(){
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnAddItem = new JButton("Add New Item");
		btnAddItem.setPreferredSize(new Dimension(150, 40));
		JButton btnItem2Purchase = new JButton("Display Items to buy");
		btnItem2Purchase.setPreferredSize(new Dimension(200, 40));
		topLeftPanel.add(btnAddItem);
		topLeftPanel.add(btnItem2Purchase);
		topPanel.add(topLeftPanel);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyTextField tfSearch = new MyTextField(30, "Search your Item");
		tfSearch.setPreferredSize(new Dimension(250,40));
//		JButton btnSearch = new JButton("Search");
//		btnSearch.setPreferredSize(new Dimension(100, 30));
		topRightPanel.add(tfSearch);
//		topRightPanel.add(btnSearch);
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

	public static void createItemListTable(Object[][] input){
		tableData = input;

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
	            return tableData[0][columnIndex].getClass();
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
}
