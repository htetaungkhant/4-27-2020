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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import database.PurchaseDetailTable;
import database.SaleDetailTable;
import database.SaleTable;
import database.StockTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import net.miginfocom.swing.MigLayout;
import sale.Sale;

public class UpdateSaleRecord extends JDialog{

	private Object[][] tableData;
	private String[] columnNames;
	private DefaultTableModel modelForItemList;
	private JTable itemList;
	private JTextArea taRemark;
	private JNumberTextField tfTotalAmount;
	private JNumberTextField tfNetAmount;
	private JNumberTextField tfDiscount;

	private ArrayList<Integer> itemList2Delete = new ArrayList<Integer>();

	public UpdateSaleRecord(Frame parent, int idsale) {
		super(parent, true);
		ImageIcon frameIcon = new ImageIcon("picture/sale_record_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("အရောင်းဘောင်ချာအချက်အလက်ပြောင်းလဲခြင်း");
		setSize(1400, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());
		Object[] data = SaleTable.retrieve(idsale);

		//creating Top Panel
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel topLeftPanel = new JPanel(new MigLayout());
		JLabel lbDate = new JLabel("ရက်စွဲ");
		lbDate.setFont(Fonts.pyisuNormal15);
		MyTextField date = new MyTextField();
		date.setFont(Fonts.pyisuNormal15);
		date.setHorizontalAlignment(JLabel.CENTER);
		date.setText((String) data[1]);
		date.setPreferredSize(new Dimension(150, 40));
		date.setEditable(false);
		JLabel lbInvoice = new JLabel("ဘောင်ချာနံပါတ်");
		lbInvoice.setFont(Fonts.pyisuNormal15);
		MyTextField invoiceNo = new MyTextField();
		invoiceNo.setFont(Fonts.pyisuNormal15);
		invoiceNo.setHorizontalAlignment(JLabel.CENTER);
		invoiceNo.setText((String) data[0]);
		invoiceNo.setPreferredSize(new Dimension(150, 40));
		invoiceNo.setEditable(false);
		JLabel lbCustomer = new JLabel("ဈေးဝယ်သူ");
		lbCustomer.setFont(Fonts.pyisuNormal15);
		MyTextField customer = new MyTextField();
		customer.setFont(Fonts.pyisuNormal15);
		customer.setHorizontalAlignment(JLabel.CENTER);
		customer.setText((String) data[2]);
		customer.setPreferredSize(new Dimension(230, 40));
		customer.setEditable(false);
		topLeftPanel.add(lbDate);
		topLeftPanel.add(date, "wrap");
		topLeftPanel.add(lbInvoice);
		topLeftPanel.add(invoiceNo, "wrap");
		topLeftPanel.add(lbCustomer);
		topLeftPanel.add(customer);
		topPanel.add(topLeftPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		columnNames = new String[]{"ID","ကုန်ပစ္စည်းအမည်", "အရေအတွက်", "ဈေးနှုန်း", "ကျသင့်", "ဖျက်ရန်"};
		tableData = SaleDetailTable.retrieve(idsale);
		modelForItemList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				if(column == 2) return true;
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
		TableColumn column3 = itemList.getColumnModel().getColumn(1);
		SpinnerEditor spinnerEditor = new SpinnerEditor(itemList);
		column3.setCellEditor(spinnerEditor);
		TableColumn column6 = itemList.getColumnModel().getColumn(4);
//		column6.setMinWidth(40);
		column6.setMaxWidth(100);
		column6.setPreferredWidth(60);

		JScrollPane tablePanel = new JScrollPane(itemList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new BorderLayout());

		//creating Bottom Left Panel
		JPanel bottomLeftPanel = new JPanel(new MigLayout());
		JLabel lbRemark = new JLabel("မှတ်ချက်");
		lbRemark.setFont(Fonts.pyisuNormal15);
		taRemark = new JTextArea(5, 30);
		taRemark.setFont(Fonts.pyisuNormal15);
		taRemark.setText((String)data[6]);
		bottomLeftPanel.add(lbRemark);
		bottomLeftPanel.add(new JScrollPane(taRemark));
		bottomPanel.add(bottomLeftPanel, BorderLayout.WEST);

		//creating Bottom Right Panel
		JPanel bottomRightPanel = new JPanel(new MigLayout());

		JLabel lbTotalAmount = new JLabel("စုစုပေါင်းကျသင့်ငွေ");
		lbTotalAmount.setFont(Fonts.pyisuNormal15);
		tfTotalAmount = new JNumberTextField(10);
		tfTotalAmount.setFont(Fonts.pyisuNormal15);
		tfTotalAmount.setText(Integer.toString((int)data[3]));
		tfTotalAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfTotalAmount.setPreferredSize(new Dimension(120, 40));
		tfTotalAmount.setEditable(false);

		JLabel lbNetAmount = new JLabel("ရငွေ");
		lbNetAmount.setFont(Fonts.pyisuNormal15);
		tfNetAmount = new JNumberTextField(10);
		tfNetAmount.setFont(Fonts.pyisuNormal15);
		tfNetAmount.setText(Integer.toString((int)data[4]));
		tfNetAmount.setHorizontalAlignment(JLabel.RIGHT);
		tfNetAmount.setPreferredSize(new Dimension(120, 40));

		JLabel lbDiscount = new JLabel("လျော့ငွေ");
		lbDiscount.setFont(Fonts.pyisuNormal15);
		tfDiscount = new JNumberTextField(10);
		tfDiscount.setFont(Fonts.pyisuNormal15);
		tfDiscount.setText(Integer.toString((int)data[5]));
		tfDiscount.setHorizontalAlignment(JLabel.RIGHT);
		tfDiscount.setPreferredSize(new Dimension(120, 40));
		tfDiscount.setEditable(false);

		JButton btnDelete = new JButton("ဘောင်ချာဖျက်မည်");
		btnDelete.setFont(Fonts.pyisuNormal15);
		btnDelete.setForeground(Color.red);
		btnDelete.setPreferredSize(new Dimension(80, 40));
		JButton btnSave = new JButton("သိမ်းဆည်းမည်");
		btnSave.setFont(Fonts.pyisuNormal15);
		btnSave.setPreferredSize(new Dimension(80, 40));
		JButton btnPrint = new JButton("ဘောင်ချာထုတ်မည်");
		btnPrint.setFont(Fonts.pyisuNormal15);
		btnPrint.setPreferredSize(new Dimension(100, 40));
		JButton btnCancel = new JButton("ပိတ်မည်");
		btnCancel.setFont(Fonts.pyisuNormal15);
		btnCancel.setPreferredSize(new Dimension(80, 40));
		bottomRightPanel.add(lbTotalAmount, "span 4, split 2, align right");
		bottomRightPanel.add(tfTotalAmount, "wrap");
		bottomRightPanel.add(lbNetAmount, "span 4, split 2, align right");
		bottomRightPanel.add(tfNetAmount, "wrap");
		bottomRightPanel.add(lbDiscount, "span 4, split 2, align right");
		bottomRightPanel.add(tfDiscount, "wrap");
		bottomRightPanel.add(btnDelete);
		bottomRightPanel.add(btnSave);
		bottomRightPanel.add(btnPrint);
		bottomRightPanel.add(btnCancel);
		bottomPanel.add(bottomRightPanel, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		itemList.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int amount = 0;
				for(int i = 0; i < itemList.getRowCount(); i++){
					amount += (int)itemList.getValueAt(i, 3);
				}
				tfTotalAmount.setText("");
				tfTotalAmount.setText(Integer.toString(amount));
			}
		});

		itemList.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int row = itemList.getSelectedRow();
					if(itemList.getSelectedColumn()==itemList.getColumnCount()-1){
						JLabel label = new JLabel("ဖျက်မှာသေချာပါပီလား။ ပြန်ပြင်လို့မရဘူးနော်");
						label.setFont(Fonts.pyisuNormal15);
						int result = JOptionPane.showConfirmDialog(null, label, "ဖျက်မှာလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(result == JOptionPane.YES_OPTION){
							itemList2Delete.add((int)itemList.getModel().getValueAt(row, 0));
							spinnerEditor.removeQuantity(row);
							modelForItemList.removeRow(row);
						}
					}
			}
		});

		tfTotalAmount.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(tfNetAmount.getText().equals("")) {
					tfDiscount.setText("");
					tfDiscount.setText(Integer.toString(Integer.parseInt(tfTotalAmount.getText()) - 0));
				}
				else {
					tfDiscount.setText("");
					tfDiscount.setText(Integer.toString(Integer.parseInt(tfTotalAmount.getText()) - Integer.parseInt(tfNetAmount.getText())));
				}
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
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

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JLabel label = new JLabel("ဖျက်မှာသေချာပါပီလား။ ပြန်ပြင်လို့မရဘူးနော်");
				label.setFont(Fonts.pyisuNormal15);
				int result = JOptionPane.showConfirmDialog(null, label, "ဖျက်မှာလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){
					for(int row = 0; row < itemList.getRowCount(); row++){
						itemList2Delete.add((int)itemList.getModel().getValueAt(row, 0));
					}
					StockTable.addQuantity(itemList2Delete);
					SaleTable.delete(idsale);
					Sale.createSaleRecordTable();
					setVisible(false);
					dispose();
				}
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(itemList.getRowCount() <= 0){
					JLabel label = new JLabel("ရောင်းထားသောကုန်ပစ္စည်းမရှိသောကြောင့် ဘောင်ချာအားဖျက်သိမ်းမည်");
					label.setFont(Fonts.pyisuNormal15);
					int result = JOptionPane.showConfirmDialog(null, label, "ဖျက်မှာသေချာပီလား", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION){
						StockTable.addQuantity(itemList2Delete);
						SaleTable.delete(idsale);
						Sale.createSaleRecordTable();
						setVisible(false);
						dispose();
					}
				}
				else if(tfNetAmount.getText().equals("")){
					JLabel label = new JLabel("ကျေးဇူးပြု၍ ရငွေထည့်ပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရငွေထည့်ပါ", JOptionPane.INFORMATION_MESSAGE);
				}
				else if(Integer.parseInt(tfTotalAmount.getText()) < Integer.parseInt(tfNetAmount.getText())) {
					JLabel label = new JLabel("ရငွေသည် ကျသင့်ငွေထက်များနေပါသည်။ ကျေးဇူးပြု၍ ဖြစ်နိုင်သောရငွေထည့်ပါ");
					label.setFont(Fonts.pyisuNormal15);
					JOptionPane.showMessageDialog(null, label, "ရငွေမှားယွင်းမှု", JOptionPane.INFORMATION_MESSAGE);					
				}
				else{
					if(itemList2Delete.size() > 0){
						StockTable.addQuantity(itemList2Delete);
						SaleDetailTable.delete(itemList2Delete);
					}
					int amount = Integer.parseInt(tfTotalAmount.getText());
					int netAmount = Integer.parseInt(tfNetAmount.getText());
					int discount = Integer.parseInt(tfDiscount.getText());
					String remark = taRemark.getText();
					StockTable.addUpdatedQuantity(itemList);
					SaleDetailTable.update(itemList);
					SaleTable.update(new Object[]{amount, netAmount, discount, remark}, idsale);
					Sale.createSaleRecordTable();
					Sale.setSelectedRow(idsale);
					setVisible(false);
					dispose();
				}
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

//	public void createItemListTable(Object[][] input){
//		tableData = input;
//		columnNames = new String[]{"ID","Item Name", "Quantity", "Sale Price", "Amount", "Del"};
//
//		modelForItemList = new DefaultTableModel(tableData, columnNames){
//			public boolean isCellEditable(int row, int column) {
//				if(column == 2) return true;
//				return false;
//			}
//			public Class<?> getColumnClass(int column){
//				return getValueAt(0,column).getClass();
//			}
//		};
//		itemList.setModel(modelForItemList);
//		itemList.setRowHeight(40);
//		itemList.removeColumn(itemList.getColumnModel().getColumn(0));
//		TableColumn column3 = itemList.getColumnModel().getColumn(1);
//		column3.setCellEditor(new SpinnerEditor(itemList));
//		TableColumn column6 = itemList.getColumnModel().getColumn(4);
//		column6.setMinWidth(40);
//		column6.setMaxWidth(100);
//		column6.setPreferredWidth(50);
//	}
}
class SpinnerEditor extends DefaultCellEditor
{
    JSpinner spinner;
    JSpinner.DefaultEditor editor;
    JTextField textField;
    ArrayList<Integer> quantity = new ArrayList<Integer>();

    public SpinnerEditor(JTable itemList) {
        super(new JTextField());
        spinner = new JSpinner();

        for(int i = 0; i < itemList.getRowCount(); i++){
        	quantity.add(i, (int) itemList.getValueAt(i, 1));
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
	    		SpinnerModel sm = new SpinnerNumberModel((int)value, 1, (int)quantity.get(row), 1);
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

    public void removeQuantity(int row){
    	quantity.remove(row);
    }
}
