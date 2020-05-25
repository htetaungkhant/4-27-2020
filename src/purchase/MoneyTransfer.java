package purchase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.alee.extended.date.DateListener;
import com.alee.extended.date.WebDateField;

import database.MoneyTransferTable;
import database.PurchaseTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import main.Main;
import purchase.dialog.AddMoneyTransfer;
import supplier.SupplierInfo;

public class MoneyTransfer extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelFormoneyTransferList;
	private static JTable moneyTransferList;

	private static WebDateField datePicker1;
	private static WebDateField datePicker2;

	private static JButton btnChooseSupplier;
	private static JNumberTextField tfInvoiceNumber;

	public MoneyTransfer(){
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
		calendar.add(Calendar.MONTH, -1);
		datePicker1.setDate(calendar.getTime());
		datePicker1.setAllowUserInput(false);
		datePicker1.setPreferredSize(100, 40);
		JLabel lbEndDate = new JLabel("ဆုံးရက်");
		lbEndDate.setFont(Fonts.pyisuNormal15);
		datePicker2=new WebDateField(new Date());
		datePicker2.setFont(Fonts.pyisuNormal15);
		datePicker2.setAllowUserInput(false);
		datePicker2.setPreferredSize(100, 40);
		btnChooseSupplier = new JButton("Choose Supplier");
		btnChooseSupplier.setFont(Fonts.pyisuNormal15);
		btnChooseSupplier.setPreferredSize(new Dimension(230, 40));
		tfInvoiceNumber = new JNumberTextField("ဘောင်ချာနံပါတ်", 10);
		tfInvoiceNumber.setFont(Fonts.pyisuNormal15);
		tfInvoiceNumber.setPreferredSize(new Dimension(110, 40));
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
		JButton btnAddNewRecord = new JButton("ငွေလွဲစာရင်းထည့်ရန်");
		btnAddNewRecord.setFont(Fonts.pyisuNormal15);
		btnAddNewRecord.setPreferredSize(new Dimension(170, 40));
		topRightPanel.add(btnAddNewRecord);
		topPanel.add(topRightPanel, BorderLayout.EAST);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		moneyTransferList = new JTable(modelFormoneyTransferList);
		createMoneyTransferTable();

		JScrollPane tablePanel = new JScrollPane(moneyTransferList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		datePicker1.addDateListener(new DateListener() {
			@Override
			public void dateChanged(Date arg0) {
				createMoneyTransferTable();
			}
		});

		datePicker2.addDateListener(new DateListener() {
			@Override
			public void dateChanged(Date arg0) {
				createMoneyTransferTable();
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
				if(!beforeSupplier.equals(btnChooseSupplier.getText())) createMoneyTransferTable();
				else {
					int row = getSelectedID();
					if(row != -1) setSelectedID(row);					
				}
			}
		});

		tfInvoiceNumber.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				createMoneyTransferTable();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				createMoneyTransferTable();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				createMoneyTransferTable();
			}
		});

		btnAddNewRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddMoneyTransfer addMoneyTransfer = new AddMoneyTransfer(Main.frame);
				addMoneyTransfer.setVisible(true);
				int row = getSelectedID();
				createMoneyTransferTable();
				if(row != -1) setSelectedID(row);		
			}
		});
		
		moneyTransferList.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int row = moneyTransferList.getSelectedRow();
					if(moneyTransferList.getSelectedColumn()==moneyTransferList.getColumnCount()-1){
						JLabel label = new JLabel("ဖျက်မှာသေချာပီလား");
						label.setFont(Fonts.pyisuNormal15);
						int result = JOptionPane.showConfirmDialog(null, label, "ဖျက်မှာလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						int idmoney_transfer = (int)moneyTransferList.getModel().getValueAt(row, 0);
						int amount = (int)moneyTransferList.getValueAt(row, 3);
						if(result == JOptionPane.YES_OPTION  && PurchaseTable.subtractPaidAmount( idmoney_transfer, amount)){
							MoneyTransferTable.delete(idmoney_transfer);
							createMoneyTransferTable();
						}
					}
			}
		});

		moneyTransferList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					int idpurchase = (int) moneyTransferList.getModel().getValueAt(row, 0);
//					UpdatePurchaseRecord updatePurchaseRecord = new UpdatePurchaseRecord(Main.frame, idpurchase);
//					updatePurchaseRecord.setVisible(true);
				}
			}
		});
	}

	public static void createMoneyTransferTable(){
		tableData = MoneyTransferTable.retrieve(datePicker1.getDate(), datePicker2.getDate(), btnChooseSupplier.getText(), tfInvoiceNumber.getText());		//PurchaseTable.retrieve(datePicker1.getDate(), datePicker2.getDate(), btnChooseSupplier.getText(), tfInvoiceNumber.getText());
		columnNames = new String[]{"ID", "ရက်စွဲ", "ငွေလွဲလက်ခံသူ","ဘောင်ချာနံပါတ်", "ပေးငွေ", "မှတ်ချက်", "ဖျက်ရန်"};

		modelFormoneyTransferList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		moneyTransferList.setModel(modelFormoneyTransferList);
		moneyTransferList.getTableHeader().setPreferredSize(new Dimension(0, 50));
		moneyTransferList.getTableHeader().setFont(Fonts.pyisuNormal16);
		moneyTransferList.setRowHeight(40);
		moneyTransferList.setFont(Fonts.pyisuNormal15);
		moneyTransferList.removeColumn(moneyTransferList.getColumnModel().getColumn(0));
		TableColumn column7 = moneyTransferList.getColumnModel().getColumn(5);
//		column7.setMinWidth(40);
		column7.setMaxWidth(100);
		column7.setPreferredWidth(60);
	}

	public static int getSelectedID(){
		int row = moneyTransferList.getSelectedRow();
		if(row == -1) return row;															// -1 if no select
		return (int)moneyTransferList.getModel().getValueAt(row, 0);
	}

	public static void setSelectedID(int row){
		for(int i = 0; i < moneyTransferList.getRowCount(); i++) {
			if((int)moneyTransferList.getModel().getValueAt(i, 0) == row) {
				moneyTransferList.setRowSelectionInterval(i, i);
				moneyTransferList.scrollRectToVisible(new Rectangle(moneyTransferList.getCellRect(i, 0, true)));
				break;
			}
		}
		
	}
}
