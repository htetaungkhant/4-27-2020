package purchase.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.alee.extended.date.WebDateField;
import com.alee.managers.style.StyleId;

import database.PurchaseDetailTable;
import database.PurchaseTable;
import database.SaleDetailTable;
import database.StockTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import net.miginfocom.swing.MigLayout;
import purchase.Purchase;

public class UpdatePurchaseRecord extends JDialog{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForItemList;
	private static JTable itemList;

	public UpdatePurchaseRecord(Frame frame, int idpurchase) {
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/purchase_record_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Update Purchase Record");
		setSize(1400, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());
		Object[] data = PurchaseTable.retrieve(idpurchase);

		//creating Top Panel
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel topLeftPanel = new JPanel();
		MigLayout layout = new MigLayout();
		topLeftPanel.setLayout(layout);
		JLabel lbDate = new JLabel("Date");
		MyTextField date = new MyTextField();
		date.setHorizontalAlignment(JLabel.CENTER);
		date.setText(new SimpleDateFormat("MMM d, yyyy").format(data[0]));
		date.setPreferredSize(new Dimension(150, 40));
		date.setEditable(false);
		JLabel lbSupplier = new JLabel("Supplier");
		MyTextField supplier = new MyTextField();
		supplier.setHorizontalAlignment(JLabel.CENTER);
		supplier.setText((String) data[1]);
		supplier.setPreferredSize(new Dimension(150, 40));
		supplier.setEditable(false);
		JLabel lbInvoice = new JLabel("Invoice No ");
		MyTextField invoiceNo = new MyTextField();
		invoiceNo.setHorizontalAlignment(JLabel.CENTER);
		invoiceNo.setText((String) data[2]);
		invoiceNo.setPreferredSize(new Dimension(150, 40));
		invoiceNo.setEditable(false);
		topLeftPanel.add(lbDate);
		topLeftPanel.add(date, "wrap");
		topLeftPanel.add(lbSupplier);
		topLeftPanel.add(supplier, "wrap");
		topLeftPanel.add(lbInvoice);
		topLeftPanel.add(invoiceNo);
		topPanel.add(topLeftPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		itemList = new JTable(modelForItemList);
		createItemListTable(PurchaseDetailTable.retrieve(idpurchase));

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel(new MigLayout());

		JLabel lbTotalAmount = new JLabel("Total Amount");
		lbTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbTotalAmount.setPreferredSize(new Dimension(100, 40));
		JNumberTextField tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setText(Integer.toString((int)data[3]));
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		tfTotalAmount.setEditable(false);
		JLabel lbPaidAmount = new JLabel("Paid Amount");
		lbPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		lbPaidAmount.setPreferredSize(new Dimension(100, 40));
		JNumberTextField tfPaidAmount = new JNumberTextField(10);
		tfPaidAmount.setText(Integer.toString((int)data[4]));
		tfPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfPaidAmount.setPreferredSize(new Dimension(120, 40));
		tfPaidAmount.setEditable(false);
		JButton btnDelete = new JButton("Delete Invoice");
		btnDelete.setVisible(false);
		if(!SaleDetailTable.isAlreadySale(idpurchase)){
			btnDelete.setVisible(true);
		}
		btnDelete.setForeground(Color.red);
		btnDelete.setPreferredSize(new Dimension(100, 40));
		JButton btnClose = new JButton("Close");
		btnClose.setPreferredSize(new Dimension(120, 40));
		bottomRightPanel.add(lbTotalAmount);
		bottomRightPanel.add(tfTotalAmount, "wrap");
		bottomRightPanel.add(lbPaidAmount);
		bottomRightPanel.add(tfPaidAmount, "wrap");
		bottomRightPanel.add(btnDelete);
		bottomRightPanel.add(btnClose);
		bottomPanel.add(bottomRightPanel);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "This action cannot undo. Are you sure to delete?", "Sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(result == JOptionPane.YES_OPTION){
					StockTable.subtractQuantityAndUpdateCOGS(itemList);
					PurchaseTable.delete(idpurchase);
					Purchase.createPurchaseRecordTable();
					setVisible(false);
					dispose();
				}
			}
		});
	}

	public static void createItemListTable(Object[][] input){
		tableData = input;
		columnNames = new String[]{"ID","Item Name", "Quantity", "Unit Price", "Amount"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int column){
				return getValueAt(0,column).getClass();
			}
		};
		itemList.setModel(modelForItemList);
		itemList.getTableHeader().setPreferredSize(new Dimension(0, 40));
		itemList.setRowHeight(40);
		itemList.removeColumn(itemList.getColumnModel().getColumn(0));
	}
}
