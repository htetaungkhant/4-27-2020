package purchase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alee.extended.date.WebDateField;

import database.SupplierTable;
import external_classes.MyTextField;
import main.Main;
import purchase.dialog.AddNewPurchaseRecord;
import supplier.SupplierInfo;

public class Purchase extends JPanel{

	private static String[] columnNames = {"Date", "Supplier", "Invoice Number", "Amount", "Paid Amount","Remark"};
	private static Object[][] tableData;
	private static TableModel modelForPurchaseRecordList;
	private static JTable purchaseRecordList;

	public Purchase(){
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbStartDate = new JLabel("Start Date");
		WebDateField datePicker1=new WebDateField(new Date());
		datePicker1.setPreferredSize(100, 40);
		JLabel lbEndDate = new JLabel("End Date");
		WebDateField datePicker2=new WebDateField(new Date());
		datePicker2.setPreferredSize(100, 40);
		String supplierList[]=SupplierTable.retrieveSupplierNamesOnly();
		JComboBox jcbSupplierList = new JComboBox(supplierList);
		jcbSupplierList.insertItemAt("All Supplier", 0);
		jcbSupplierList.setSelectedIndex(0);
		jcbSupplierList.setPreferredSize(new Dimension(150, 40));
		MyTextField tfInvoiceNumber = new MyTextField(30, "Invoice Number");
		tfInvoiceNumber.setPreferredSize(new Dimension(110,40));
		tfInvoiceNumber.setHorizontalAlignment(JLabel.RIGHT);
		JButton btnSearch = new JButton("Search");
		btnSearch.setPreferredSize(new Dimension(100, 40));
		topLeftPanel.add(lbStartDate);
		topLeftPanel.add(datePicker1);
		topLeftPanel.add(lbEndDate);
		topLeftPanel.add(datePicker2);
		topLeftPanel.add(jcbSupplierList);
		topLeftPanel.add(tfInvoiceNumber);
		topLeftPanel.add(btnSearch);
		topPanel.add(topLeftPanel, BorderLayout.WEST);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnAddNewRecord = new JButton("Add New Record");
		btnAddNewRecord.setPreferredSize(new Dimension(150, 40));
		topRightPanel.add(btnAddNewRecord);
		topPanel.add(topRightPanel, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		purchaseRecordList = new JTable(modelForPurchaseRecordList);
		createPurchaseRecordTable(null);

		JScrollPane tablePanel = new JScrollPane(purchaseRecordList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		btnAddNewRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewPurchaseRecord addNewPurchaseRecord = new AddNewPurchaseRecord(Main.frame);
				addNewPurchaseRecord.setVisible(true);
			}
		});
	}

	public static void createPurchaseRecordTable(Object[][] input){
		tableData = input;

		modelForPurchaseRecordList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
	            return tableData[0][columnIndex].getClass();
	        }
		};
		purchaseRecordList.setModel(modelForPurchaseRecordList);
		purchaseRecordList.setRowHeight(30);
	}
}
