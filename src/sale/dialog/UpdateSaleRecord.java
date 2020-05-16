package sale.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import database.PurchaseDetailTable;
import database.SaleDetailTable;
import database.SaleTable;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import net.miginfocom.swing.MigLayout;

public class UpdateSaleRecord extends JDialog{

	private String[] columnNames;
	private Object[][] tableData;
	private DefaultTableModel modelForItemList;
	private JTable itemList;

	public UpdateSaleRecord(Frame parent, int idsale) {
		super(parent, true);
		ImageIcon frameIcon = new ImageIcon("picture/sale_record_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Update Sale Record");
		setSize(1400, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());
		Object[] data = SaleTable.retrieve(idsale);

		//creating Top Panel
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel topLeftPanel = new JPanel(new MigLayout());
		JLabel lbDate = new JLabel("Date");
		MyTextField date = new MyTextField();
		date.setHorizontalAlignment(JLabel.CENTER);
		date.setText((String) data[1]);
		date.setPreferredSize(new Dimension(150, 40));
		date.setEditable(false);
		JLabel lbCustomer = new JLabel("Customer");
		MyTextField customer = new MyTextField();
		customer.setHorizontalAlignment(JLabel.CENTER);
		customer.setText((String) data[2]);
		customer.setPreferredSize(new Dimension(150, 40));
		customer.setEditable(false);
		JLabel lbInvoice = new JLabel("Invoice No");
		MyTextField invoiceNo = new MyTextField();
		invoiceNo.setHorizontalAlignment(JLabel.CENTER);
		invoiceNo.setText((String) data[0]);
		invoiceNo.setPreferredSize(new Dimension(150, 40));
		invoiceNo.setEditable(false);
		topLeftPanel.add(lbDate);
		topLeftPanel.add(date, "wrap");
		topLeftPanel.add(lbCustomer);
		topLeftPanel.add(customer, "wrap");
		topLeftPanel.add(lbInvoice);
		topLeftPanel.add(invoiceNo);
		topPanel.add(topLeftPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		itemList = new JTable(modelForItemList);
		createItemListTable(SaleDetailTable.retrieve(idsale));

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel(new MigLayout());

		JLabel lbTotalAmount = new JLabel("Total Amount");
		JNumberTextField tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setText(Integer.toString((int)data[3]));
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		tfTotalAmount.setEditable(false);

		JLabel lbPaidAmount = new JLabel("Net Amount");
		JNumberTextField tfPaidAmount = new JNumberTextField(10);
		tfPaidAmount.setText(Integer.toString((int)data[4]));
		tfPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfPaidAmount.setPreferredSize(new Dimension(120, 40));

		JLabel lbDiscount = new JLabel("Discount");
		JNumberTextField tfDiscount = new JNumberTextField(10);
		tfDiscount.setText(Integer.toString((int)data[5]));
		tfDiscount.setHorizontalAlignment(JLabel.RIGHT);
		tfDiscount.setPreferredSize(new Dimension(120, 40));
		tfDiscount.setEditable(false);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.red);
		btnDelete.setPreferredSize(new Dimension(80, 40));
		JButton btnSave = new JButton("Save");
		btnSave.setPreferredSize(new Dimension(80, 40));
		JButton btnPrint = new JButton("Print");
		btnPrint.setPreferredSize(new Dimension(80, 40));
		bottomRightPanel.add(lbTotalAmount);
		bottomRightPanel.add(tfTotalAmount, "gapleft 20, wrap");
		bottomRightPanel.add(lbPaidAmount);
		bottomRightPanel.add(tfPaidAmount, "gapleft 20, wrap");
		bottomRightPanel.add(lbDiscount);
		bottomRightPanel.add(tfDiscount, "gapleft 20, wrap");
		bottomRightPanel.add(btnDelete);
		bottomRightPanel.add(btnSave, "split 2");
		bottomRightPanel.add(btnPrint);
		bottomPanel.add(bottomRightPanel);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		itemList.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
			}
		});
	}

	public void createItemListTable(Object[][] input){
		tableData = input;
		columnNames = new String[]{"ID","Item Name", "Quantity", "Sale Price", "Amount", "Del"};

		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				if(column == 2) return true;
				return false;
			}
			public Class<?> getColumnClass(int column){
				return getValueAt(0,column).getClass();
			}
		};
		itemList.setModel(modelForItemList);
		itemList.setRowHeight(40);
		itemList.removeColumn(itemList.getColumnModel().getColumn(0));
		TableColumn column3 = itemList.getColumnModel().getColumn(1);
		column3.setCellEditor(new SpinnerEditor(itemList));
		TableColumn column6 = itemList.getColumnModel().getColumn(4);
		column6.setMinWidth(40);
		column6.setMaxWidth(100);
		column6.setPreferredWidth(50);
	}
}
class SpinnerEditor extends DefaultCellEditor
{
    JSpinner spinner;
    JSpinner.DefaultEditor editor;
    JTextField textField;
    int[] quantity;

    public SpinnerEditor(JTable itemList) {
        super(new JTextField());
        spinner = new JSpinner();
        quantity = new int[itemList.getRowCount()];

        for(int i = 0; i < itemList.getRowCount(); i++){
        	quantity[i] = (int) itemList.getValueAt(i, 1);
        }

        spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int row = itemList.getSelectedRow();
				itemList.setValueAt((int)spinner.getValue()*(int)itemList.getValueAt(row, 2), row, 3);
			}
		});
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    		SpinnerModel sm = new SpinnerNumberModel((int)value, 1, quantity[row], 1);
	    		spinner.setModel(sm);
	            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
	            textField = editor.getTextField();
	            textField.setEditable(false);

//	    		spinner.setValue(value);

            return spinner;
    }

    public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
          return ((MouseEvent) evt).getClickCount() >= 2;
        }
        return true;
      }

    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
