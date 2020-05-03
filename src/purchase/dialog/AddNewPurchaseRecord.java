package purchase.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.alee.extended.date.WebDateField;

import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import stock.Items;
import supplier.SupplierInfo;

public class AddNewPurchaseRecord extends JDialog{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static TableModel modelForItemList;
	private static JTable itemList;

	public AddNewPurchaseRecord(Frame parent){
		super(parent, true);
		ImageIcon frameIcon = new ImageIcon("picture/purchase_record_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Add New Record");
		setSize(1400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new BorderLayout());

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		WebDateField datePicker=new WebDateField(new Date());
		datePicker.setPreferredSize(100, 40);
		JButton btnChooseSupplier = new JButton("Choose Supplier");
		btnChooseSupplier.setPreferredSize(new Dimension(150, 40));
		MyTextField tfInvoiceNumber = new MyTextField(30, "Enter Invoice No");
		tfInvoiceNumber.setPreferredSize(new Dimension(120,40));
		tfInvoiceNumber.setHorizontalAlignment(JLabel.CENTER);
		topLeftPanel.add(datePicker);
		topLeftPanel.add(btnChooseSupplier);
		topLeftPanel.add(tfInvoiceNumber);
		topPanel.add(topLeftPanel, BorderLayout.WEST);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setPreferredSize(new Dimension(100, 40));
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
		JLabel lbTotalAmount = new JLabel("Total Amount");
		lbTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbTotalAmount.setPreferredSize(new Dimension(100, 40));
		JNumberTextField tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		tfTotalAmount.setEditable(false);
		JLabel lbPaidAmount = new JLabel("Paid Amount");
		lbPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbPaidAmount.setPreferredSize(new Dimension(100, 40));
		JNumberTextField tfPaidAmount = new JNumberTextField(10);
		tfPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfPaidAmount.setPreferredSize(new Dimension(120, 40));
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(100, 40));
		JButton btnSave = new JButton("Save");
		btnSave.setPreferredSize(new Dimension(100, 40));

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


		btnAddItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame, "Choose Item",true);
				Items itemList = new Items(d);
				d.add(itemList);
				ImageIcon frameIcon = new ImageIcon("picture/items_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(1000, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});

		btnChooseSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog d = new JDialog(Main.frame, "Choose Supplier",true);
				SupplierInfo supplierList = new SupplierInfo(d, btnChooseSupplier);
				d.add(supplierList);
				ImageIcon frameIcon = new ImageIcon("picture/supplier_icon.png");
				d.setIconImage(frameIcon.getImage());
				d.setSize(600, 500);
				d.setLocationRelativeTo(null);
				d.setVisible(true);
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}

	public static void createItemListTable(Object[][] input){
		tableData = input;
		columnNames = new String[]{"Item Name", "Unit Price", "quantity", "Amount"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
	            return tableData[0][columnIndex].getClass();
	        }
		};
		itemList.setModel(modelForItemList);
		itemList.setRowHeight(30);
	}
}
