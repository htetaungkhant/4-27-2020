package stock.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.StockTable;

public class ItemsToPurchase extends JDialog{
	public ItemsToPurchase(Frame frame) {
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/items_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Items to buy");
		setSize(1000, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		//creating Table Panel
		Object[][] tableData = StockTable.retrieveItems2Purchase();
		String[] columnNames = {"Item Name", "Barcode", "Cost", "Sale Price", "Quantity","Quantity Limit","Remark"};
		DefaultTableModel modelForSupplierList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		JTable itemsList2Purchase = new JTable(modelForSupplierList);
		itemsList2Purchase.getTableHeader().setPreferredSize(new Dimension(0, 40));
		itemsList2Purchase.setRowHeight(40);
		JScrollPane tablePanel = new JScrollPane(itemsList2Purchase);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel
	}
}
