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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alee.extended.date.DateListener;
import com.alee.extended.date.WebDateField;

import database.PurchaseTable;
import database.SupplierTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import purchase.dialog.AddNewPurchaseRecord;
import purchase.dialog.UpdatePurchaseRecord;
import stock.dialog.AddAndUpdateItem;
import supplier.SupplierInfo;

public class Purchase extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForPurchaseRecordList;
	private static JTable purchaseRecordList;

	private static WebDateField datePicker1;
	private static WebDateField datePicker2;

	private static JButton btnChooseSupplier;
	private static JNumberTextField tfInvoiceNumber;

	private static JButton btnAddNewRecord;
	
	private static Boolean forMoneyTransfer;
	
	public Purchase(Boolean forMoneyTransfer){
		this.forMoneyTransfer = forMoneyTransfer;
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbStartDate = new JLabel("စရက်");
		lbStartDate.setFont(Fonts.pyisuNormal15);
		datePicker1=new WebDateField();
		datePicker1.setFont(Fonts.pyisuNormal15);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		datePicker1.setDate(calendar.getTime());
		datePicker1.setAllowUserInput(false);
		datePicker1.setPreferredSize(100, 40);
		JLabel lbEndDate = new JLabel("ဆုံးရက်");
		lbEndDate.setFont(Fonts.pyisuNormal15);
		datePicker2=new WebDateField(new Date());
		datePicker2.setFont(Fonts.pyisuNormal15);
		datePicker2.setAllowUserInput(false);
		datePicker2.setPreferredSize(100, 40);
//		String supplierList[]=SupplierTable.retrieveSupplierNamesOnly();
//		JComboBox jcbSupplierList = new JComboBox(supplierList);
//		jcbSupplierList.insertItemAt("All Supplier", 0);
//		jcbSupplierList.setSelectedIndex(0);
//		jcbSupplierList.setPreferredSize(new Dimension(150, 40));
		btnChooseSupplier = new JButton("Choose Supplier");
		btnChooseSupplier.setFont(Fonts.pyisuNormal15);
		btnChooseSupplier.setPreferredSize(new Dimension(230, 40));
		tfInvoiceNumber = new JNumberTextField("ဘောင်ချာနံပါတ်", 10);
		tfInvoiceNumber.setFont(Fonts.pyisuNormal15);
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
		btnAddNewRecord = new JButton("အဝယ်ဘောင်ချာထည့်ရန်");
		btnAddNewRecord.setFont(Fonts.pyisuNormal15);
		btnAddNewRecord.setPreferredSize(new Dimension(160, 40));
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
				String beforeSupplier = btnChooseSupplier.getText();
				JDialog d = new JDialog(Main.frame, "ကုန်ပေးသူများ",true);
				SupplierInfo supplierList = new SupplierInfo(d, btnChooseSupplier);
				d.add(supplierList);
				ImageIcon frameIcon = new ImageIcon("picture/supplier_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(600, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
				if(!beforeSupplier.equals(btnChooseSupplier.getText())) createPurchaseRecordTable();
				else {
					int row = getSelectedRow();
					if(row != -1) setSelectedRow(row);					
				}
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
					UpdatePurchaseRecord updatePurchaseRecord = new UpdatePurchaseRecord(Main.frame, idpurchase);
					updatePurchaseRecord.setVisible(true);
					if(updatePurchaseRecord.isClickDelete()) createPurchaseRecordTable();
				}
			}
		});
	}
	
	public Purchase(JDialog d, JButton btnInput, MyTextField tfSupplier, JNumberTextField tfInvoiceNo, JNumberTextField tfRemainingAmount, JLabel selectedInvoiceNo) {
		this(true);
		btnAddNewRecord.setVisible(false);
		
		purchaseRecordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnSelect = new JButton("ရွေးမည်");
		btnSelect.setFont(Fonts.pyisuNormal15);
		btnSelect.setPreferredSize(new Dimension(100, 40));
		JButton btnCancel = new JButton("မလုပ်ဆောင်ပါ");
		btnCancel.setFont(Fonts.pyisuNormal15);
		btnCancel.setPreferredSize(new Dimension(100, 40));
		bottomPanel.add(btnCancel);
		bottomPanel.add(btnSelect);
		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnInput.setText("ငွေလွဲဘောင်ချာရွေးပါ");
				tfSupplier.setText(""); tfSupplier.setEnabled(false);
				tfInvoiceNo.setText(""); tfInvoiceNo.setEnabled(false);
				tfRemainingAmount.setText("0"); tfRemainingAmount.setEnabled(false);
				selectedInvoiceNo.setText("");
				d.setVisible(false);
				d.dispose();
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(purchaseRecordList.getSelectionModel().isSelectionEmpty()){
					JLabel label = new JLabel("ကျေးဇူးပြု၍ ဘောင်ချာရွေးပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "မှားယွင်းမှု", JOptionPane.INFORMATION_MESSAGE);
				}	
				else {
					int row = purchaseRecordList.getSelectedRow();
					btnInput.setText("ပြန်လည်ရွေးချယ်နိုင်ပါသည်");
					tfSupplier.setText(""); tfSupplier.setText((String) purchaseRecordList.getValueAt(row, 1)); tfSupplier.setEnabled(true);
					tfInvoiceNo.setText(""); tfInvoiceNo.setText((String) purchaseRecordList.getValueAt(row, 2)); tfInvoiceNo.setEnabled(true);
					tfRemainingAmount.setText(""); tfRemainingAmount.setText(Integer.toString((int) purchaseRecordList.getValueAt(row, 3) - (int) purchaseRecordList.getValueAt(row, 4))); tfRemainingAmount.setEnabled(true);
					selectedInvoiceNo.setText("");
					selectedInvoiceNo.setText(Integer.toString((int)purchaseRecordList.getModel().getValueAt(row, 0)));
					d.setVisible(false);
					d.dispose();
				}
			}
		});
	}

	public static void createPurchaseRecordTable(){
		if(forMoneyTransfer) {	
			tableData = PurchaseTable.retrieveForMoneyTransfer(datePicker1.getDate(), datePicker2.getDate(), btnChooseSupplier.getText(), tfInvoiceNumber.getText());	
		}
		else {
			tableData = PurchaseTable.retrieve(datePicker1.getDate(), datePicker2.getDate(), btnChooseSupplier.getText(), tfInvoiceNumber.getText());				
		}
		columnNames = new String[]{"ID", "ရက်စွဲ", "ကုန်ပေးသူ", "ဘောင်ချာနံပါတ်", "သင့်ငွေ", "ပေးငွေ"};

		modelForPurchaseRecordList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		purchaseRecordList.setModel(modelForPurchaseRecordList);
		purchaseRecordList.getTableHeader().setPreferredSize(new Dimension(0, 50));
		purchaseRecordList.getTableHeader().setFont(Fonts.pyisuNormal16);
		purchaseRecordList.setRowHeight(40);
		purchaseRecordList.setFont(Fonts.pyisuNormal15);
		purchaseRecordList.removeColumn(purchaseRecordList.getColumnModel().getColumn(0));
	}

	public static int getSelectedRow(){
		return purchaseRecordList.getSelectedRow();
	}

	public static void setSelectedRow(int row){
		purchaseRecordList.setRowSelectionInterval(row, row);
	}
}
