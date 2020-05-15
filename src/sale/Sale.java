package sale;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.date.DateListener;
import com.alee.extended.date.WebDateField;

import customer.CustomerInfo;
import database.PurchaseTable;
import database.SaleTable;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import purchase.Purchase;
import supplier.SupplierInfo;

public class Sale extends JPanel{

	private String[] columnNames;
	private Object[][] tableData;
	private DefaultTableModel modelForSaleRecordList;
	private JTable saleRecordList;

	private WebDateField datePicker1;
	private WebDateField datePicker2;

	private JButton btnChooseCustomer;
	private JNumberTextField tfInvoiceNumber;

	public Sale() {
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
		btnChooseCustomer = new JButton("Choose Customer");
		btnChooseCustomer.setPreferredSize(new Dimension(150, 40));
		tfInvoiceNumber = new JNumberTextField("Invoice Number", 10);
		tfInvoiceNumber.setPreferredSize(new Dimension(110,40));
		tfInvoiceNumber.setHorizontalAlignment(JLabel.CENTER);
		topPanel.add(lbStartDate);
		topPanel.add(datePicker1);
		topPanel.add(lbEndDate);
		topPanel.add(datePicker2);
		topPanel.add(btnChooseCustomer);
		topPanel.add(tfInvoiceNumber);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		saleRecordList = new JTable(modelForSaleRecordList);
		createSaleRecordTable();

		JScrollPane tablePanel = new JScrollPane(saleRecordList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		datePicker1.addDateListener(new DateListener() {
			@Override
			public void dateChanged(Date arg0) {
				createSaleRecordTable();
			}
		});

		datePicker2.addDateListener(new DateListener() {
			@Override
			public void dateChanged(Date arg0) {
				createSaleRecordTable();
			}
		});

		btnChooseCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String beforeCustomer = btnChooseCustomer.getText();
				JDialog d = new JDialog(Main.frame, "Choose Supplier",true);
				CustomerInfo customerList = new CustomerInfo(d, btnChooseCustomer, new ArrayList<String>(Arrays.asList(btnChooseCustomer.getText())));
				d.add(customerList);
				ImageIcon frameIcon = new ImageIcon("picture/customer_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(600, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
				if(!beforeCustomer.equals(btnChooseCustomer.getText())) createSaleRecordTable();
			}
		});

		tfInvoiceNumber.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				createSaleRecordTable();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				createSaleRecordTable();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				createSaleRecordTable();
			}
		});
	}

	public void createSaleRecordTable(){
		tableData = SaleTable.retrieve(datePicker1.getDate(), datePicker2.getDate(), btnChooseCustomer.getText(), tfInvoiceNumber.getText());
		columnNames = new String[]{"Date", "Customer", "Invoice Number", "Amount", "Net Amount", "Discount", "Remark"};

		modelForSaleRecordList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		saleRecordList.setModel(modelForSaleRecordList);
		saleRecordList.setRowHeight(40);
	}
}