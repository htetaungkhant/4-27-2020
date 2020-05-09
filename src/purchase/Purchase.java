package purchase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
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

import com.alee.extended.date.DateListener;
import com.alee.extended.date.WebDateField;

import database.PurchaseTable;
import database.SupplierTable;
import external_classes.MyTextField;
import main.Main;
import purchase.dialog.AddNewPurchaseRecord;
import purchase.dialog.UpdatePurchaseRecord;
import stock.dialog.AddNewItem;
import supplier.SupplierInfo;

public class Purchase extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForPurchaseRecordList;
	private static JTable purchaseRecordList;

	private static WebDateField datePicker1;
	private static WebDateField datePicker2;

	private static JButton btnChooseSupplier;
	private static MyTextField tfInvoiceNumber;

	public Purchase(){
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbStartDate = new JLabel("Start Date");
		datePicker1=new WebDateField();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		datePicker1.setDate(calendar.getTime());
		datePicker1.setAllowUserInput(false);
		datePicker1.setPreferredSize(100, 40);
		JLabel lbEndDate = new JLabel("End Date");
		datePicker2=new WebDateField(new Date());
		datePicker2.setAllowUserInput(false);
		datePicker2.setPreferredSize(100, 40);
//		String supplierList[]=SupplierTable.retrieveSupplierNamesOnly();
//		JComboBox jcbSupplierList = new JComboBox(supplierList);
//		jcbSupplierList.insertItemAt("All Supplier", 0);
//		jcbSupplierList.setSelectedIndex(0);
//		jcbSupplierList.setPreferredSize(new Dimension(150, 40));
		btnChooseSupplier = new JButton("Choose Supplier");
		btnChooseSupplier.setPreferredSize(new Dimension(150, 40));
		tfInvoiceNumber = new MyTextField(30, "Invoice Number");
		tfInvoiceNumber.setPreferredSize(new Dimension(110,40));
		tfInvoiceNumber.setHorizontalAlignment(JLabel.CENTER);
		topLeftPanel.add(lbStartDate);
		topLeftPanel.add(datePicker1);
		topLeftPanel.add(lbEndDate);
		topLeftPanel.add(datePicker2);
		topLeftPanel.add(btnChooseSupplier);
		topLeftPanel.add(tfInvoiceNumber);
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
		createPurchaseRecordTable();

		JScrollPane tablePanel = new JScrollPane(purchaseRecordList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		datePicker1.addDateListener(new DateListener() {
			@Override
			public void dateChanged(Date arg0) {
				createPurchaseRecordTable();
			}
		});

		datePicker2.addDateListener(new DateListener() {
			@Override
			public void dateChanged(Date arg0) {
				createPurchaseRecordTable();
			}
		});

		btnChooseSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame, "Choose Supplier",true);
				SupplierInfo supplierList = new SupplierInfo(d, btnChooseSupplier);
				d.add(supplierList);
				d.setSize(600, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});

		tfInvoiceNumber.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				createPurchaseRecordTable();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				createPurchaseRecordTable();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				createPurchaseRecordTable();
			}
		});

		btnAddNewRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewPurchaseRecord addNewPurchaseRecord = new AddNewPurchaseRecord(Main.frame);
				addNewPurchaseRecord.setVisible(true);
			}
		});

		purchaseRecordList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					int idpurchase = (int) purchaseRecordList.getModel().getValueAt(row, 0);
					UpdatePurchaseRecord updateRecord = new UpdatePurchaseRecord(Main.frame, idpurchase);
					updateRecord.setVisible(true);
				}
			}
		});
	}

	public static void createPurchaseRecordTable(){
		tableData = PurchaseTable.retrieve(datePicker1.getDate(), datePicker2.getDate(), btnChooseSupplier.getText(), tfInvoiceNumber.getText());
		columnNames = new String[]{"ID", "Date", "Supplier", "Invoice Number", "Amount", "Paid Amount"};

		modelForPurchaseRecordList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		purchaseRecordList.setModel(modelForPurchaseRecordList);
		purchaseRecordList.setRowHeight(30);
		purchaseRecordList.removeColumn(purchaseRecordList.getColumnModel().getColumn(0));
	}

	public static int getSelectedRow(){
		return purchaseRecordList.getSelectedRow();
	}

	public static void setSelectedRow(int row){
		purchaseRecordList.setRowSelectionInterval(row, row);
	}
}
