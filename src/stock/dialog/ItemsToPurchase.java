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
import external_classes.Fonts;

public class ItemsToPurchase extends JDialog{
	public ItemsToPurchase(Frame frame) {
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/items_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("ဝယ်စာရင်း");
		setSize(1200, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		//creating Table Panel
		Object[][] tableData = StockTable.retrieveItems2Purchase();
		String[] columnNames = {"ကုန်ပစ္စည်းအမည်", "ဘားကုဒ်", "ကုန်ကျစရိတ်", "ရောင်းဈေး", "အရေအတွက်", "သတ်မှတ်အရေအတွက်","မှတ်ချက်"};
		DefaultTableModel modelForSupplierList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		JTable itemsList2Purchase = new JTable(modelForSupplierList);
		itemsList2Purchase.getTableHeader().setPreferredSize(new Dimension(0, 50));
		itemsList2Purchase.getTableHeader().setFont(Fonts.pyisuNormal16);
		itemsList2Purchase.setRowHeight(40);
		itemsList2Purchase.setFont(Fonts.pyisuNormal15);
		JScrollPane tablePanel = new JScrollPane(itemsList2Purchase);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel
	}
}
