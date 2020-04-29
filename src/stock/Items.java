package stock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.mysql.cj.xdevapi.Client;

import database.StockTable;
import external_classes.MyTextField;
import main.Main;
import stock.dialog.AddNewItem;

public class Items extends JPanel{
	public Items(){
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		MyTextField tfSearch = new MyTextField(30, "Search by Item Name or Barcode");
		tfSearch.setPreferredSize(new Dimension(250,30));
		JButton btnSearch = new JButton("Search");
		btnSearch.setPreferredSize(new Dimension(100, 30));
		topLeftPanel.add(tfSearch);
		topLeftPanel.add(btnSearch);
		topPanel.add(topLeftPanel);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnAddItem = new JButton("Add New Item");
		btnAddItem.setPreferredSize(new Dimension(150, 30));
		topRightPanel.add(btnAddItem);
		topPanel.add(topRightPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		String[] columnNames = {"Item Name", "Barcode", "Purchase Price", "Sale Price", "Quantity","Quantity Limit","Remark","Edit"};
		Object[][] data = StockTable.retrieveAll();

		TableModel modelForItemList = new DefaultTableModel(data, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable itemList = new JTable(modelForItemList);
		itemList.setRowHeight(30);
		TableColumn column7 = itemList.getColumnModel().getColumn(7);
		column7.setMinWidth(40);
		column7.setMaxWidth(100);
		column7.setPreferredWidth(50);

		add(new JScrollPane(itemList), BorderLayout.CENTER);
		//End of Table Panel

		btnAddItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewItem addNewItem = new AddNewItem(Main.frame);
				addNewItem.setVisible(true);
			}
		});
	}
}
