package sale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.date.DateListener;
import com.alee.extended.date.WebDateField;

import customer.CustomerInfo;
import database.PurchaseTable;
import database.SaleTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import net.miginfocom.swing.MigLayout;
import purchase.Purchase;
import sale.dialog.UpdateSaleRecord;
import supplier.SupplierInfo;

public class Sale extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForSaleRecordList;
	private static JTable saleRecordList;

	private static WebDateField datePicker1;
	private static WebDateField datePicker2;

	private static JButton btnChooseCustomer;
	private static JNumberTextField tfInvoiceNumber;

	private static JLabel lbTotalSaleAmount;
	private static JLabel lbTotalNetSaleAmount;

	public Sale() {
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbStartDate = new JLabel("Start Date");
		lbStartDate.setFont(Fonts.pyisuNormal15);
		datePicker1=new WebDateField(new Date());
		datePicker1.setFont(Fonts.pyisuNormal15);
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.MONTH, -1);
//		datePicker1.setDate(calendar.getTime());
		datePicker1.setAllowUserInput(false);
		datePicker1.setPreferredSize(100, 40);
		JLabel lbEndDate = new JLabel("End Date");
		lbEndDate.setFont(Fonts.pyisuNormal15);
		datePicker2=new WebDateField(new Date());
		datePicker2.setFont(Fonts.pyisuNormal15);
		datePicker2.setAllowUserInput(false);
		datePicker2.setPreferredSize(100, 40);
		btnChooseCustomer = new JButton("Choose Customer");
		btnChooseCustomer.setFont(Fonts.pyisuNormal15);
		btnChooseCustomer.setPreferredSize(new Dimension(230, 40));
		tfInvoiceNumber = new JNumberTextField("ဘောင်ချာနံပါတ်", 10);
		tfInvoiceNumber.setFont(Fonts.pyisuNormal15);
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

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel(new MigLayout());
		JLabel lbTotalSale = new JLabel("Total Sale Amount: ");
		lbTotalSale.setHorizontalAlignment(JLabel.RIGHT);
		lbTotalSale.setFont(Fonts.pyisuBold18);
		JLabel lbTotalNetSale = new JLabel("Total Net Amount: ");
		lbTotalNetSale.setHorizontalAlignment(JLabel.RIGHT);
		lbTotalNetSale.setFont(Fonts.pyisuBold18);
		lbTotalSaleAmount = new JLabel("0");
		lbTotalSaleAmount.setFont(Fonts.pyisuBold18);
		lbTotalSaleAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbTotalNetSaleAmount = new JLabel("0");
		lbTotalNetSaleAmount.setFont(Fonts.pyisuBold18);
		lbTotalNetSaleAmount.setHorizontalAlignment(JLabel.RIGHT);
		bottomRightPanel.add(lbTotalSale);
		bottomRightPanel.add(lbTotalSaleAmount, "wrap");
		bottomRightPanel.add(lbTotalNetSale);
		bottomRightPanel.add(lbTotalNetSaleAmount);
		bottomPanel.add(bottomRightPanel);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

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

		saleRecordList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					int idsale = Integer.parseInt((String) saleRecordList.getValueAt(row, 2));
					UpdateSaleRecord updateSaleRecord = new UpdateSaleRecord(Main.frame, idsale);
					updateSaleRecord.setVisible(true);
				}
			}
		});
	}

	public static void createSaleRecordTable(){
		tableData = SaleTable.retrieve(datePicker1.getDate(), datePicker2.getDate(), btnChooseCustomer.getText(), tfInvoiceNumber.getText());
		columnNames = new String[]{"ရက်စွဲ", "ဝယ်ယူသူအမည်", "ဘောင်ချာနံပါတ်", "သင့်ငွေ", "ရငွေ", "လျော့ငွေ", "မှတ်ချက်"};

		modelForSaleRecordList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		saleRecordList.setModel(modelForSaleRecordList);
		saleRecordList.getTableHeader().setPreferredSize(new Dimension(0, 50));
		saleRecordList.getTableHeader().setFont(Fonts.pyisuNormal16);
		saleRecordList.setRowHeight(40);
		saleRecordList.setFont(Fonts.pyisuNormal15);

		int totalSaleAmount = 0;
		int totalNetSaleAmount = 0;
		for(int i = 0; i < saleRecordList.getRowCount(); i++){
			totalSaleAmount += (int)saleRecordList.getValueAt(i, 3);
			totalNetSaleAmount += (int)saleRecordList.getValueAt(i, 4);
		}
		lbTotalSaleAmount.setText("");
		lbTotalSaleAmount.setText(new DecimalFormat("###,###,###.###").format(totalSaleAmount));
		lbTotalNetSaleAmount.setText("");
		lbTotalNetSaleAmount.setText(new DecimalFormat("###,###,###.###").format(totalNetSaleAmount));
	}

	public static void setSelectedRow(int idsale){
		for(int row = 0; row < saleRecordList.getRowCount(); row++){
			if(Integer.parseInt((String)saleRecordList.getValueAt(row, 2)) == idsale){
				saleRecordList.setRowSelectionInterval(row, row);
				saleRecordList.scrollRectToVisible(new Rectangle(saleRecordList.getCellRect(row, 2, true)));
				break;
			}
		}
	}
}
