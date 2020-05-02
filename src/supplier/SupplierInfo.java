package supplier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.SupplierTable;
import external_classes.MyTextField;
import main.Main;
import supplier.dialog.AddNewSupplier;

public class SupplierInfo extends JPanel{

	private static String[] columnNames = {"Supplier Name", "Phone", "Address"};
	private static Object[][] tableData;
	private static TableModel modelForSupplierList;
	private static JTable supplierList;

	private static String selectedSupplierName;

	public SupplierInfo() {
		setLayout(new BorderLayout());

		//creating Top Panel
		JPanel topPanel = new JPanel(new GridLayout(1, 2));

		//creating Top Left Panel
		JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnAddNewSupplier = new JButton("Add New Supplier");
		btnAddNewSupplier.setPreferredSize(new Dimension(170, 40));
		topLeftPanel.add(btnAddNewSupplier);
		topPanel.add(topLeftPanel);

		//creating Top Right Panel
		JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyTextField tfSearch = new MyTextField(30, "Search supplier");
		tfSearch.setPreferredSize(new Dimension(250,40));
		topRightPanel.add(tfSearch);
		topPanel.add(topRightPanel);

		add(topPanel, BorderLayout.NORTH);
		//End of Top Panel

		//creating Table Panel
		supplierList = new JTable(modelForSupplierList);
		createSupplierTable(SupplierTable.retrieveAll());

		JScrollPane tablePanel = new JScrollPane(supplierList);
		add(tablePanel, BorderLayout.CENTER);
		//End of Table Panel

		btnAddNewSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddNewSupplier addNewSupplier = new AddNewSupplier(Main.frame);
				addNewSupplier.setVisible(true);
			}
		});

		tfSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddNewSupplier.setEnabled(true);
					createSupplierTable(SupplierTable.retrieveAll());
				}
				else{
					btnAddNewSupplier.setEnabled(false);
					createSupplierTable(SupplierTable.retrieveFilterBySupplierName(tfSearch.getText()));
				}
			}		@Override
			public void keyReleased(KeyEvent e) {

				if(tfSearch.getText().equals("")){
					btnAddNewSupplier.setEnabled(true);
					createSupplierTable(SupplierTable.retrieveAll());
				}
				else{
					btnAddNewSupplier.setEnabled(false);
					createSupplierTable(SupplierTable.retrieveFilterBySupplierName(tfSearch.getText()));
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddNewSupplier.setEnabled(true);
					createSupplierTable(SupplierTable.retrieveAll());
				}
				else{
					btnAddNewSupplier.setEnabled(false);
					createSupplierTable(SupplierTable.retrieveFilterBySupplierName(tfSearch.getText()));
				}
			}
		});

		supplierList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					String[] data = {
							(String) supplierList.getValueAt(row, 0),
							(String) supplierList.getValueAt(row, 1),
							(String) supplierList.getValueAt(row, 2),
					};
					AddNewSupplier addNewSupplier = new AddNewSupplier(Main.frame, data, tfSearch.getText());
					addNewSupplier.setVisible(true);
				}
			}
		});
	}

	public static void createSupplierTable(Object[][] input) {
		tableData = input;

		modelForSupplierList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		supplierList.setModel(modelForSupplierList);
		supplierList.setRowHeight(30);
	}

	public static String addBottomPanel(SupplierInfo gg, JDialog d){
		selectedSupplierName = "";
		supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnSelect = new JButton("Select");
		btnSelect.setPreferredSize(new Dimension(100, 40));
		bottomPanel.add(btnSelect);
		gg.add(bottomPanel, BorderLayout.SOUTH);

		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(supplierList.getSelectionModel().isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Please, select supplier", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					int row = supplierList.getSelectedRow();
					selectedSupplierName = (String) supplierList.getValueAt(row, 0);
					d.setVisible(false);
				}
			}
		});
		return selectedSupplierName;
	}
}
