package customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.CustomerTable;
import external_classes.MyTextField;
import main.Main;
import purchase.Purchase;
import purchase.dialog.AddNewPurchaseRecord;
import customer.dialog.AddAndUpdateCustomer;

public class CustomerInfo extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForCustomerList;
	private static JTable customerList;

	private static ArrayList<String> alreadyCustomer;

	public CustomerInfo() {
		this.alreadyCustomer = null;
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnAddNewCustomer = new JButton("Add New Customer");
		btnAddNewCustomer.setPreferredSize(new Dimension(170, 40));
		topLeftPanel.add(btnAddNewCustomer);
		topPanel.add(topLeftPanel);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyTextField tfSearch = new MyTextField(30, "Search customer");
		tfSearch.setPreferredSize(new Dimension(250,40));
		topRightPanel.add(tfSearch);
		topPanel.add(topRightPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		customerList = new JTable(modelForCustomerList);
		createCustomerTable(CustomerTable.retrieveAll());
		removeDefaultCustomer();
		JScrollPane tablePanel = new JScrollPane(customerList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		btnAddNewCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAndUpdateCustomer addNewCustomer = new AddAndUpdateCustomer(Main.frame);
				addNewCustomer.setVisible(true);
			}
		});

		tfSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddNewCustomer.setEnabled(true);
					createCustomerTable(CustomerTable.retrieveAll());
					removeDefaultCustomer();
					removeAlreadyCustomers();
				}
				else{
					btnAddNewCustomer.setEnabled(false);
					createCustomerTable(CustomerTable.retrieveFilterByCustomerName(tfSearch.getText()));
					removeDefaultCustomer();
					removeAlreadyCustomers();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {

				if(tfSearch.getText().equals("")){
					btnAddNewCustomer.setEnabled(true);
					createCustomerTable(CustomerTable.retrieveAll());
					removeDefaultCustomer();
					removeAlreadyCustomers();
				}
				else{
					btnAddNewCustomer.setEnabled(false);
					createCustomerTable(CustomerTable.retrieveFilterByCustomerName(tfSearch.getText()));
					removeDefaultCustomer();
					removeAlreadyCustomers();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddNewCustomer.setEnabled(true);
					createCustomerTable(CustomerTable.retrieveAll());
					removeDefaultCustomer();
					removeAlreadyCustomers();
				}
				else{
					btnAddNewCustomer.setEnabled(false);
					createCustomerTable(CustomerTable.retrieveFilterByCustomerName(tfSearch.getText()));
					removeDefaultCustomer();
					removeAlreadyCustomers();
				}
			}
		});

		customerList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					Object[] data = {
							(int) customerList.getModel().getValueAt(row, 0),
							(String) customerList.getValueAt(row, 0),
							(String) customerList.getValueAt(row, 1),
							(String) customerList.getValueAt(row, 2),
					};
					AddAndUpdateCustomer addNewCustomer = new AddAndUpdateCustomer(Main.frame, data, tfSearch.getText());
					addNewCustomer.setVisible(true);
				}
			}
		});
	}

	public CustomerInfo(JDialog d, JButton btnInput, ArrayList<String> existedCustomer){
		this();
		this.alreadyCustomer = existedCustomer;
		removeAlreadyCustomers();

		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//creating Bottom Panel
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnSelect = new JButton("Select");
		btnSelect.setPreferredSize(new Dimension(100, 40));
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(100, 40));
		bottomPanel.add(btnCancel);
		bottomPanel.add(btnSelect);
		add(bottomPanel, BorderLayout.SOUTH);
		//End of Bottom Panel

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < existedCustomer.size(); i++){
					if(existedCustomer.get(i).equals(btnInput.getText())){
						existedCustomer.remove(i);
						break;
					}
				}
				btnInput.setText("Choose Customer");
				d.setVisible(false);
				d.dispose();
			}
		});

		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(customerList.getSelectionModel().isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Please, select customer", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					int row = customerList.getSelectedRow();
					if(btnInput.getText().equals("Choose Customer")) existedCustomer.add((String) customerList.getValueAt(row, 0));
					else existedCustomer.set(existedCustomer.indexOf(btnInput.getText()), (String) customerList.getValueAt(row, 0));
					btnInput.setText((String) customerList.getValueAt(row, 0));
					d.setVisible(false);
					d.dispose();
				}
			}
		});
	}

	public static void createCustomerTable(Object[][] input) {
		tableData = input;
		columnNames = new String[]{"ID", "Customer Name", "Phone", "Address"};

		modelForCustomerList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		customerList.setModel(modelForCustomerList);
		customerList.setRowHeight(40);
		customerList.removeColumn(customerList.getColumnModel().getColumn(0));
	}

	public static void removeDefaultCustomer(){
		for(int i = 0; i < customerList.getRowCount(); i++){
			if(customerList.getValueAt(i, 0).equals("Default Customer")){
				modelForCustomerList.removeRow(i);
				break;
			}
		}
	}

	public static String getSelectedCustomer(){
		if(customerList.getSelectionModel().isSelectionEmpty()){
			return null;
		}
		return (String) customerList.getValueAt(customerList.getSelectedRow(), 0);
	}

	public static void setSelectedCustomer(String customerName){
		if(customerName != null){
			for(int i = 0; i < customerList.getRowCount(); i++){
				if(customerList.getValueAt(i, 0).equals(customerName)){
					customerList.setRowSelectionInterval(i, i);
					break;
				}
			}
		}
	}

	public static void setSelectedRow(int id){
		for(int i = 0; i < customerList.getRowCount(); i++){
			if((int)customerList.getModel().getValueAt(i, 0) == id){
				customerList.setRowSelectionInterval(i, i);
				customerList.scrollRectToVisible(new Rectangle(customerList.getCellRect(i, 0, true)));
				break;
			}
		}
	}

	public static void removeAlreadyCustomers(){
		if(alreadyCustomer != null){
			for(int i = 0; i < alreadyCustomer.size(); i++){
				for(int j = 0; j < customerList.getRowCount(); j++)
				if(alreadyCustomer.get(i).equals(customerList.getValueAt(j, 0))){
					modelForCustomerList.removeRow(j);
					break;
				}
			}
		}
	}
}
