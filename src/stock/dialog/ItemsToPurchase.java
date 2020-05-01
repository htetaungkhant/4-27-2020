package stock.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.StockTable;

public class ItemsToPurchase extends JDialog{
	public ItemsToPurchase(Frame frame) {
		super(frame, true);
		setTitle("Items to buy");
		setSize(1000, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		//creating Table Panel
		Object[][] tableData = StockTable.retrieveItems2Purchase();
		String[] columnNames = {"Item Name", "Barcode", "Purchase Price", "Sale Price", "Quantity","Quantity Limit","Remark"};
		DefaultTableModel modelForSupplierList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable itemsList2Purchase = new JTable(modelForSupplierList);
		itemsList2Purchase.setRowHeight(30);
		JScrollPane tablePanel = new JScrollPane(itemsList2Purchase);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel
	}
}
