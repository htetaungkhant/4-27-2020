package sale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.alee.extended.date.WebDateField;

import customer.CustomerInfo;
import database.CustomerTable;
import database.DBConnection;
import database.PurchaseTable;
import database.SaleDetailTable;
import database.SaleTable;
import database.StockTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class POS extends JPanel{

	private static ArrayList<String> existedCustomers = new ArrayList<String>();

	private JButton btnCustomer;
	private MyTextField tfBarcode;
	private JNumberTextField tfPrice;
	private JNumberTextField tfQty;
	private WebDateField datePicker;
	private String[] columnNames;
	private Object[][] tableData;
	private DefaultTableModel modelForItemList;
	private JTable itemList;
	private JTextArea taRemark;
	private JNumberTextField tfTotalAmount;
	private JNumberTextField tfNetAmount;
	private JNumberTextField tfDiscount;

	public POS() {
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new MigLayout());
		btnCustomer = new JButton("ဝယ်သူအမည်");
		btnCustomer.setFont(Fonts.pyisuNormal15);
		btnCustomer.setPreferredSize(new Dimension(150, 40));
		JLabel lbBarcode = new JLabel("ဘားကုဒ်");
		lbBarcode.setFont(Fonts.pyisuNormal15);
		tfBarcode = new MyTextField();
		tfBarcode.setFont(Fonts.pyisuNormal15);
		tfBarcode.setPreferredSize(new Dimension(150, 40));
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        tfBarcode.requestFocusInWindow();
		      }
		    });
		JLabel lbPrice = new JLabel("ဈေးနှုန်း");
		lbPrice.setFont(Fonts.pyisuNormal15);
		tfPrice = new JNumberTextField(10);
		tfPrice.setFont(Fonts.pyisuNormal15);
		tfPrice.setEditable(false);
		tfPrice.setFocusable(false);
		tfPrice.setHorizontalAlignment(JLabel.RIGHT);
		tfPrice.setPreferredSize(new Dimension(100, 40));
		JLabel lbQty = new JLabel("အရေအတွက်");
		lbQty.setFont(Fonts.pyisuNormal15);
		tfQty = new JNumberTextField(10);
		tfQty.setFont(Fonts.pyisuNormal15);
		tfQty.setHorizontalAlignment(JLabel.RIGHT);
		tfQty.setPreferredSize(new Dimension(50, 40));
		JButton btnAddItem = new JButton("ထည့်မည်");
		btnAddItem.setFont(Fonts.pyisuNormal15);
		btnAddItem.setPreferredSize(new Dimension(60, 40));
		topLeftPanel.add(btnCustomer, "wrap");
		topLeftPanel.add(lbBarcode, "split 2");
		topLeftPanel.add(tfBarcode, "gapleft 17");
		topLeftPanel.add(lbPrice);
		topLeftPanel.add(tfPrice);
		topLeftPanel.add(lbQty);
		topLeftPanel.add(tfQty);
		topLeftPanel.add(btnAddItem);
		topPanel.add(topLeftPanel, BorderLayout.WEST);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lbDate = new JLabel("ရက်စွဲ");
		lbDate.setFont(Fonts.pyisuNormal15);
		datePicker = new WebDateField(new Date());
		datePicker.setFont(Fonts.pyisuNormal15);
		datePicker.setPreferredSize(120, 40);
		datePicker.setAllowUserInput(false);
		topRightPanel.add(lbDate);
		topRightPanel.add(datePicker);
		topPanel.add(topRightPanel, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		tableData = null;
		columnNames = new String[]{"idstock", "ကုန်ပစ္စည်းအမည်", "အရေအတွက်", "cost", "ဈေးနှုန်း", "သင့်ငွေ", "ဖျက်ရန်"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int column){
				return getValueAt(0,column).getClass();
			}
		};
		itemList = new JTable(modelForItemList);
		itemList.getTableHeader().setPreferredSize(new Dimension(0, 50));
		itemList.getTableHeader().setFont(Fonts.pyisuNormal16);
		itemList.setRowHeight(40);
		itemList.setFont(Fonts.pyisuNormal15);
		itemList.removeColumn(itemList.getColumnModel().getColumn(0));
		itemList.removeColumn(itemList.getColumnModel().getColumn(2));
		TableColumn column5 = itemList.getColumnModel().getColumn(4);
		column5.setMinWidth(40);
		column5.setMaxWidth(100);
		column5.setPreferredWidth(60);

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel
		
		//creating Invoice Panel
		JPanel invoicePanel = new JPanel(new GridLayout(1, 1));
		Connection connection = DBConnection.createConnection();
		JasperPrint printReport;
		JRViewer gg = null;
		try {
			printReport = JasperFillManager.fillReport(new File("").getAbsolutePath()+"/report/GPReportBySale.jasper", null, connection);
			connection.close();
			gg = new JRViewer(printReport);
		} catch (JRException | SQLException e1) {
			e1.printStackTrace();
		}
		invoicePanel.add(gg);
		add(invoicePanel, BorderLayout.EAST);
		//End of Invoice Panel

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new BorderLayout());

		//creating Bottom Left Panel
		JPanel bottomLeftPanel = new JPanel(new MigLayout());
		JLabel lbRemark = new JLabel("မှတ်ချက်");
		lbRemark.setFont(Fonts.pyisuNormal15);
//		lbRemark.setVerticalAlignment(JLabel.TOP);
		taRemark = new JTextArea(5, 30);
		taRemark.setFont(Fonts.pyisuNormal15);
		bottomLeftPanel.add(lbRemark);
		bottomLeftPanel.add(new JScrollPane(taRemark));
		bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel(new MigLayout());
		JLabel lbTotalAmount = new JLabel("စုစုပေါင်းကျသင့်ငွေ");
		lbTotalAmount.setFont(Fonts.pyisuNormal15);
		tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setFont(Fonts.pyisuNormal15);
		tfTotalAmount.setText("0");
		tfTotalAmount.setEditable(false);
		tfTotalAmount.setFocusable(false);
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		JLabel lbNetAmount = new JLabel("ရငွေ");
		lbNetAmount.setFont(Fonts.pyisuNormal15);
		tfNetAmount = new JNumberTextField(10);
		tfNetAmount.setFont(Fonts.pyisuNormal15);
		tfNetAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfNetAmount.setPreferredSize(new Dimension(120, 40));
		JLabel lbDiscount = new JLabel("လျော့ငွေ");
		lbDiscount.setFont(Fonts.pyisuNormal15);
		tfDiscount = new JNumberTextField(10);
		tfDiscount.setFont(Fonts.pyisuNormal15);
		tfDiscount.setText("0");
		tfDiscount.setEditable(false);
		tfDiscount.setFocusable(false);
		tfDiscount.setHorizontalAlignment(JLabel.RIGHT);
		tfDiscount.setPreferredSize(new Dimension(120, 40));
		JButton btnReset = new JButton("အသစ်ပြန်စမည်");
		btnReset.setFont(Fonts.pyisuNormal15);
		btnReset.setPreferredSize(new Dimension(100, 40));
		JButton btnSave = new JButton("သိမ်းဆည်းမည်");
		btnSave.setFont(Fonts.pyisuNormal15);
		btnSave.setPreferredSize(new Dimension(100, 40));
		JButton btnPrint = new JButton("ဘောင်ချာထုတ်မည်");
		btnPrint.setFont(Fonts.pyisuNormal15);
		btnPrint.setPreferredSize(new Dimension(100, 40));
		bottomRightPanel.add(lbTotalAmount, "span 2, align right");
		bottomRightPanel.add(tfTotalAmount, "wrap");
		bottomRightPanel.add(lbNetAmount, "span 2, align right");
		bottomRightPanel.add(tfNetAmount, "wrap");
		bottomRightPanel.add(lbDiscount, "span 2, align right");
		bottomRightPanel.add(tfDiscount, "wrap");
		bottomRightPanel.add(btnReset);
		bottomRightPanel.add(btnSave);
		bottomRightPanel.add(btnPrint);
		bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		btnCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame, "ဝယ်သူများ",true);
				CustomerInfo customerList = new CustomerInfo(d, btnCustomer, existedCustomers);
				d.add(customerList);
				ImageIcon frameIcon = new ImageIcon("picture/customer_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(700, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});

		tfBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] itemDetail = StockTable.getItemDetail(tfBarcode.getText());
				if(tfBarcode.getText().equals("")){
					JLabel label = new JLabel("ကျေးဇူးပြုု၍ ဘားကုဒ်ထည့်ပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ဘားကုဒ်မရှိမှု", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(itemDetail == null){
					JLabel label = new JLabel("မဖြစ်နိုင်သော ဘားကုဒ်ထည့်ထားပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ဘားကုဒ်မှားယွင်းမှု", JOptionPane.ERROR_MESSAGE);
					tfBarcode.setText("");
				}
				else if((int)itemDetail[4] == 0){
					JLabel label = new JLabel("ကုန်ပစ္စည်းကုန်နေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "မရနိုင်ပါ", JOptionPane.ERROR_MESSAGE);
					tfBarcode.setText("");
				}
				else{
					tfPrice.setText(Integer.toString((int) itemDetail[3]));
					tfQty.requestFocus();
				}
			}
		});

		tfBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_END){
					tfNetAmount.requestFocus();
				}
			}
		});

		tfQty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItemAction();
			}
		});

		btnAddItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addItemAction();
			}
		});

		itemList.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int row = itemList.getSelectedRow();
					if(itemList.getSelectedColumn()==itemList.getColumnCount()-1){
						int amount = Integer.parseInt(tfTotalAmount.getText()) - (int)itemList.getValueAt(row, 3);
						tfTotalAmount.setText("");
						tfTotalAmount.setText(Integer.toString(amount));
						if(!tfNetAmount.getText().equals("")){
							tfDiscount.setText("");
							tfDiscount.setText(Integer.toString(Integer.parseInt(tfTotalAmount.getText()) - Integer.parseInt(tfNetAmount.getText())));
						}
						modelForItemList.removeRow(row);
						tfBarcode.requestFocus();
					}
			}
		});

		tfNetAmount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(tfNetAmount.getText().equals("")){
					tfDiscount.setText("");
					tfDiscount.setText(Long.toString(Long.parseLong(tfTotalAmount.getText()) - 0));
					}
				else{
					tfDiscount.setText("");
					tfDiscount.setText(Long.toString(Long.parseLong(tfTotalAmount.getText()) - Long.parseLong(tfNetAmount.getText())));
					}
			}
		});

		tfNetAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
			}
		});

		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
			}
		});

		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAction();
			}
		});

//		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
//			@Override
//			public boolean dispatchKeyEvent(KeyEvent e) {
//				if(e.getID() == KeyEvent.KEY_PRESSED){
//					if(e.getKeyCode() == KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK).getKeyCode()){
//						btnPrint.doClick();
//					}
//				}
//				return false;
//			}
//		});
	}

	public void addItemAction(){
		Object[] itemDetail = StockTable.getItemDetail(tfBarcode.getText());
		if(tfBarcode.getText().equals("")){
			JLabel label = new JLabel("ကျေးဇူးပြုု၍ ဘားကုဒ်ထည့်ပါ");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "ဘားကုဒ်မရှိမှု", JOptionPane.INFORMATION_MESSAGE);
			tfQty.setText("");
			tfPrice.setText("");
			tfBarcode.requestFocus();
		}
		else if(itemDetail == null){
			JLabel label = new JLabel("မဖြစ်နိုင်သော ဘားကုဒ်ထည့်ထားပါသည်");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "ဘားကုဒ်မှားယွင်းမှု", JOptionPane.ERROR_MESSAGE);
			tfBarcode.setText("");
		}
		else if(tfQty.getText().equals("")){
			JLabel label = new JLabel("ကျေးဇူးပြု၍ အရေအတွက်ထည့်ပါ");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "အရေအတွက်မရှိမှု", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(Integer.parseInt(tfQty.getText()) == 0){
			tfQty.setText("");
			tfPrice.setText("");
			tfBarcode.setText("");
			tfBarcode.requestFocus();
		}
		else if((int)itemDetail[4] < (Integer.parseInt(tfQty.getText())+getAlreadyItemQuantity((String) itemDetail[1], itemList))){
			JLabel label = new JLabel("ကုန်ပစ္စည်းကုန်နေပါသည်");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "မရနိုင်ပါ", JOptionPane.ERROR_MESSAGE);
		}
		else{
			int qty = Integer.parseInt(tfQty.getText());
			addItem2Invoice(qty, itemDetail, tfTotalAmount, itemList);
			tfQty.setText("");
			tfPrice.setText("");
			tfBarcode.setText("");
			tfBarcode.requestFocus();
		}
	}

	public void saveAction(){
		if(itemList.getRowCount() == 0){
			JLabel label = new JLabel("ကျေးဇူးပြု၍ ရောင်းရန်ကုန်ပစ္စည်းထည့်ပါ");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "ကုန်ပစ္စည်းမထည့်မှု", JOptionPane.INFORMATION_MESSAGE);
			tfBarcode.requestFocus();
		}
		else if(tfNetAmount.getText().equals("")){
			JLabel label = new JLabel("ကျေးဇူးပြု၍ သင့်လျော်သောရငွေထည့်ပါ");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "ရငွေမထည့်မှု", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(Integer.parseInt(tfTotalAmount.getText()) < Integer.parseInt(tfNetAmount.getText())) {
			JLabel label = new JLabel("ရငွေသည် ကျသင့်ငွေထက်များနေပါသည်။ ကျေးဇူးပြု၍ ဖြစ်နိုင်သောရငွေထည့်ပါ");
			label.setFont(Fonts.pyisuNormal15);
			JOptionPane.showMessageDialog(null, label, "ရငွေမှားယွင်းမှု", JOptionPane.INFORMATION_MESSAGE);					
		}
		else{
			Date date = datePicker.getDate();
			String customerName = btnCustomer.getText();
			if(customerName.equals("ဝယ်သူအမည်")) customerName = "Default Customer";
			int totalAmount = Integer.parseInt(tfTotalAmount.getText());
			int netAmount = Integer.parseInt(tfNetAmount.getText());
			int discount = totalAmount - netAmount;
			String remark = taRemark.getText();
			Object[] data = {date, customerName, totalAmount, netAmount, discount, remark};
			int idsale = SaleTable.insert(data);
			if(idsale > 0){
				if(SaleDetailTable.insert(itemList, idsale)){
					StockTable.subtractQuantity(itemList);
					reset();
				}
				else{
					SaleTable.delete(idsale);
					JLabel label = new JLabel("တစ်စုံတစ်ခုမှားယွင်းနေပါသည်");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "မှားယွင်းမှု", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else{
				JLabel label = new JLabel("တစ်စုံတစ်ခုမှားယွင်းနေပါသည်");
				label.setFont(Fonts.pyisuNormal15);
				JOptionPane.showMessageDialog(null, label, "မှားယွင်းမှု", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void reset(){
		existedCustomers.remove(btnCustomer.getText());
		btnCustomer.setText("ဝယ်သူအမည်");
		datePicker.setDate(new Date());
		tfBarcode.setText("");
		tfPrice.setText("");
		tfQty.setText("");
		modelForItemList.setRowCount(0);
		tfTotalAmount.setText("");
		tfTotalAmount.setText("0");
		tfNetAmount.setText("");
		tfDiscount.setText("");
		tfDiscount.setText("0");
		taRemark.setText("");
		tfBarcode.requestFocus();
	}

	public String getTextFrombtnCustomer(){
		return btnCustomer.getText();
	}

	public static void removeAllExistingCustomers(){
		existedCustomers.removeAll(existedCustomers);
	}

	public static int getCustomerListSize(){
		return existedCustomers.size();
	}

	public static String getCustomer(int index){
		return existedCustomers.get(index);
	}

	public static void removeCustomer(int index){
		existedCustomers.remove(index);
	}

	public static void addItem2Invoice(int qty, Object[] itemDetail, JNumberTextField tfTotalAmount, JTable itemList){
		Boolean isExist = false;
		for(int row = 0; row < itemList.getRowCount(); row++){
			if(itemList.getValueAt(row, 0).equals(itemDetail[1])){
				isExist = true;
				qty = qty + (int)itemList.getValueAt(row, 1);
				int amount = qty*(int)itemDetail[3];
				int originalTotalAmount = Integer.parseInt(tfTotalAmount.getText());
				tfTotalAmount.setText("");
				tfTotalAmount.setText(Integer.toString((originalTotalAmount - (int)itemList.getValueAt(row, 3)) + amount));
				itemList.setValueAt(qty, row, 1);
				itemList.setValueAt(amount, row, 3);
				break;
			}
		}

		if(!isExist){
			int amount = qty*(int)itemDetail[3];
			int originalTotalAmount = Integer.parseInt(tfTotalAmount.getText());
			tfTotalAmount.setText("");
			tfTotalAmount.setText(Integer.toString(originalTotalAmount + amount));
			Object[] rowData = {itemDetail[0], itemDetail[1], qty, itemDetail[2], itemDetail[3], amount, new ImageIcon("picture/delete_icon.png")};
			DefaultTableModel modelForItemList = (DefaultTableModel) itemList.getModel();
			modelForItemList.addRow(rowData);
		}
	}

	public static int getAlreadyItemQuantity(String itemName, JTable itemList){
		int result = 0;
		for(int row = 0; row < itemList.getRowCount(); row++){
			if(itemList.getValueAt(row, 0).equals(itemName)){
				return (int)itemList.getValueAt(row, 1);
			}
		}
		return result;
	}
}
