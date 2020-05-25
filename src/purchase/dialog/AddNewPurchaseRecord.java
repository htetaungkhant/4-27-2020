package purchase.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;

import com.alee.extended.date.WebDateField;

import database.PurchaseDetailTable;
import database.PurchaseTable;
import database.StockTable;
import database.SupplierTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import purchase.Purchase;
import stock.Items;
import supplier.SupplierInfo;

public class AddNewPurchaseRecord extends JDialog{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForItemList;
	private static JTable itemList;

	private static JNumberTextField tfTotalAmount;

	public AddNewPurchaseRecord(Frame parent){
		super(parent, true);
		ImageIcon frameIcon = new ImageIcon("picture/purchase_record_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("အဝယ်ဘောင်ချာထည့်ခြင်း");
		setSize(1400, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		WebDateField datePicker=new WebDateField(new Date());
		datePicker.setFont(Fonts.pyisuNormal15);
		datePicker.setAllowUserInput(false);
		datePicker.setPreferredSize(100, 40);
		JButton btnChooseSupplier = new JButton("Choose Supplier");
		btnChooseSupplier.setFont(Fonts.pyisuNormal15);
		btnChooseSupplier.setPreferredSize(new Dimension(230, 40));
		JNumberTextField tfInvoiceNumber = new JNumberTextField("ဘောင်ချာနံပါတ်ရေးပါ", 10);
		tfInvoiceNumber.setFont(Fonts.pyisuNormal15);
		tfInvoiceNumber.setPreferredSize(new Dimension(130,40));
		tfInvoiceNumber.setHorizontalAlignment(JLabel.CENTER);
		topLeftPanel.add(datePicker);
		topLeftPanel.add(btnChooseSupplier);
		topLeftPanel.add(tfInvoiceNumber);
		topPanel.add(topLeftPanel, BorderLayout.WEST);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnAddItem = new JButton("ကုန်ပစ္စည်းထည့်ရန်");
		btnAddItem.setFont(Fonts.pyisuNormal15);
		btnAddItem.setPreferredSize(new Dimension(130, 40));
		topRightPanel.add(btnAddItem);
		topPanel.add(topRightPanel, BorderLayout.EAST);


		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		itemList = new JTable(modelForItemList);
		createItemListTable(null);

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel();
		JLabel lbTotalAmount = new JLabel("စုစုပေါင်းကျသင့်ငွေ");
		lbTotalAmount.setFont(Fonts.pyisuNormal15);
		lbTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbTotalAmount.setPreferredSize(new Dimension(120, 40));
		tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setFont(Fonts.pyisuNormal15);
		tfTotalAmount.setText("0");
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		tfTotalAmount.setEditable(false);
		JLabel lbPaidAmount = new JLabel("ပေးငွေ");
		lbPaidAmount.setFont(Fonts.pyisuNormal15);
		lbPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbPaidAmount.setPreferredSize(new Dimension(100, 40));
		JNumberTextField tfPaidAmount = new JNumberTextField(10);
		tfPaidAmount.setFont(Fonts.pyisuNormal15);
		tfPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfPaidAmount.setPreferredSize(new Dimension(120, 40));
		JButton btnCancel = new JButton("မလုပ်ဆောင်ပါ");
		btnCancel.setFont(Fonts.pyisuNormal15);
		btnCancel.setPreferredSize(new Dimension(100, 40));
		JButton btnSave = new JButton("အတည်ပြုသိမ်းဆည်းပါ");
		btnSave.setFont(Fonts.pyisuNormal15);
		btnSave.setPreferredSize(new Dimension(150, 40));

		GroupLayout groupLayout = new GroupLayout(bottomRightPanel);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		bottomRightPanel.setLayout(groupLayout);
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(lbTotalAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(lbPaidAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(tfTotalAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(tfPaidAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));

		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lbTotalAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(tfTotalAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lbPaidAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(tfPaidAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																												.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		bottomPanel.add(bottomRightPanel);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		btnChooseSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame, "ကုန်ပေးသူများ",true);
				SupplierInfo supplierList = new SupplierInfo(d, btnChooseSupplier);
				d.add(supplierList);
				ImageIcon frameIcon = new ImageIcon("picture/supplier_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(600, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});

		btnAddItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame, "ကုန်ပစ္စည်းရွေးပါ",true);
				Items itemList = new Items(d, getItemNameList());
				d.add(itemList);
				ImageIcon frameIcon = new ImageIcon("picture/items_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(1100, 600);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(btnChooseSupplier.getText().equals("Choose Supplier")){
					JLabel label = new JLabel("ကျေးဇူးပြု၍ ကုန်ပေးသူရွေးပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ကုန်ပေးသူရွေးပါ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(itemList.getRowCount() == 0){
					JLabel label = new JLabel("ကျေးဇူးပြု၍ ကုန်ပစ္စည်းထည့်ပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ကုန်ပစ္စည်းထည့်ပါ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(tfPaidAmount.getText().equals("")){
					JLabel label = new JLabel("ကျေးဇူးပြု၍ ပေးငွေရေးပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ပေးငွေမှားယွင်းမှု", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(Integer.parseInt(tfPaidAmount.getText()) > Integer.parseInt(tfTotalAmount.getText())){
					JLabel label = new JLabel("ပေးငွေသည် ကျသင့်ငွေထက်များနေပါသည်။ ဖြစ်နိုင်သောပေးငွေပြန်ရေးပေးပါ။");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "မဖြစ်နိုင်သောငွေပေးချေမှု", JOptionPane.ERROR_MESSAGE);
				}
				else{
					Date date = datePicker.getDate();
					String supplierName = btnChooseSupplier.getText();
					String invoiceNumber = tfInvoiceNumber.getText();
					if(invoiceNumber.equals("")) invoiceNumber="-----";
					int amount = Integer.parseInt(tfTotalAmount.getText());
					int paidAmount = Integer.parseInt(tfPaidAmount.getText());
					Object[] data = {date, supplierName, invoiceNumber, amount, paidAmount};
					int idpurchase = PurchaseTable.insert(data);
					if(idpurchase > 0){
						if(PurchaseDetailTable.insert(itemList, idpurchase)){
							StockTable.addQuantityAndUpdateCOGS(itemList);
							Purchase.createPurchaseRecordTable();
							setVisible(false);
							dispose();
						}
						else{
							PurchaseTable.delete(idpurchase);
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
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				Purchase.createPurchaseRecordTable();
				setVisible(false);
				dispose();
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
						modelForItemList.removeRow(row);
					}
			}
		});
	}

	public static void createItemListTable(Object[][] input){
		tableData = input;
		columnNames = new String[]{"ကုန်အမည်", "အရေအတွက်", "ဈေးနှုန်း", "သင့်ငွေ", "ဖျက်ရန်"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int column){
				return getValueAt(0,column).getClass();
			}
		};
		itemList.setModel(modelForItemList);
		itemList.getTableHeader().setPreferredSize(new Dimension(0, 50));
		itemList.getTableHeader().setFont(Fonts.pyisuNormal16);
		itemList.setRowHeight(40);
		itemList.setFont(Fonts.pyisuNormal15);
		TableColumn column5 = itemList.getColumnModel().getColumn(4);
		column5.setMinWidth(40);
		column5.setMaxWidth(100);
		column5.setPreferredWidth(60);
	}

	public static void setTotalAmount(int amount){
		int originalAmount = Integer.parseInt(tfTotalAmount.getText());
		int totalAmount = originalAmount + amount;
		tfTotalAmount.setText("");
		tfTotalAmount.setText(Integer.toString(totalAmount));
	}

	public static void addItem(Object[] data){
		Boolean isExist = false;
		for(int i = 0; i < itemList.getRowCount(); i++){
			if(itemList.getValueAt(i, 0).equals(data[0])){
				isExist = true;
				int result = JOptionPane.showConfirmDialog(null, "Item already exists. Are you sure to add?", "Already Exist", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){
					itemList.setValueAt((int) itemList.getValueAt(i, 1) + (int) data[1], i, 1);
					itemList.setValueAt((int) itemList.getValueAt(i, 3) + (int) data[3], i, 3);
					setTotalAmount((int)data[3]);
				}
				break;
			}
		}
		if(!isExist){
			modelForItemList.addRow(data);
			setTotalAmount((int)data[3]);
		}
	}

	public static String[] getItemNameList(){
		String[] result = new String[itemList.getRowCount()];
		for(int i = 0; i < itemList.getRowCount(); i++){
			result[i] = (String) itemList.getValueAt(i, 0);
		}
		return result;
	}
}
