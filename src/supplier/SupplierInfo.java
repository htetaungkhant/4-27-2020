package supplier;

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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import database.SupplierTable;
import external_classes.MyTextField;
import main.Main;
import supplier.dialog.AddAndUpdateSupplier;

public class SupplierInfo extends JPanel{

	private static String[] columnNames;
	private static Object[][] tableData;
	private static DefaultTableModel modelForSupplierList;
	private static JTable supplierList;

	private static String alreadySupplier;

	public SupplierInfo() {
		this.alreadySupplier = null;
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
				AddAndUpdateSupplier addNewSupplier = new AddAndUpdateSupplier(Main.frame);
				addNewSupplier.setVisible(true);
			}
		});

		tfSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddNewSupplier.setEnabled(true);
					createSupplierTable(SupplierTable.retrieveAll());
					removeAlreadySupplier();
				}
				else{
					btnAddNewSupplier.setEnabled(false);
					createSupplierTable(SupplierTable.retrieveFilterBySupplierName(tfSearch.getText()));
					removeAlreadySupplier();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {

				if(tfSearch.getText().equals("")){
					btnAddNewSupplier.setEnabled(true);
					createSupplierTable(SupplierTable.retrieveAll());
					removeAlreadySupplier();
				}
				else{
					btnAddNewSupplier.setEnabled(false);
					createSupplierTable(SupplierTable.retrieveFilterBySupplierName(tfSearch.getText()));
					removeAlreadySupplier();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(tfSearch.getText().equals("")){
					btnAddNewSupplier.setEnabled(true);
					createSupplierTable(SupplierTable.retrieveAll());
					removeAlreadySupplier();
				}
				else{
					btnAddNewSupplier.setEnabled(false);
					createSupplierTable(SupplierTable.retrieveFilterBySupplierName(tfSearch.getText()));
					removeAlreadySupplier();
				}
			}
		});

		supplierList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					int row=((JTable)e.getSource()).getSelectedRow();
					Object[] data = {
							(int) supplierList.getModel().getValueAt(row, 0),
							(String) supplierList.getValueAt(row, 0),
							(String) supplierList.getValueAt(row, 1),
							(String) supplierList.getValueAt(row, 2),
					};
					AddAndUpdateSupplier addNewSupplier = new AddAndUpdateSupplier(Main.frame, data, tfSearch.getText());
					addNewSupplier.setVisible(true);
				}
			}
		});
	}

	public SupplierInfo(JDialog d, JButton btnInput){
		this();
		this.alreadySupplier = btnInput.getText();
		removeAlreadySupplier();

		supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
				btnInput.setText("Choose Supplier");
				d.setVisible(false);
				d.dispose();
			}
		});

		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(supplierList.getSelectionModel().isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Please, select supplier", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					btnInput.setText((String) supplierList.getValueAt(supplierList.getSelectedRow(), 0));
					d.setVisible(false);
					d.dispose();
				}
			}
		});
	}

	public static void createSupplierTable(Object[][] input) {
		tableData = input;
		columnNames = new String[]{"ID", "Supplier Name", "Phone", "Address"};

		modelForSupplierList = new DefaultTableModel(tableData, columnNames){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			public Class<?> getColumnClass(int columnIndex) {
				return getValueAt(0,columnIndex).getClass();
	        }
		};
		supplierList.setModel(modelForSupplierList);
		supplierList.getTableHeader().setPreferredSize(new Dimension(0, 40));
		supplierList.setRowHeight(40);
		supplierList.removeColumn(supplierList.getColumnModel().getColumn(0));
	}

	public static String getSelectedSupplier(){
		if(supplierList.getSelectionModel().isSelectionEmpty()){
			return null;
		}
		return (String) supplierList.getValueAt(supplierList.getSelectedRow(), 0);
	}

	public static void setSelectedSupplier(String supplierName){
		if(supplierName != null){
			for(int i = 0; i < supplierList.getRowCount(); i++){
				if(supplierList.getValueAt(i, 0).equals(supplierName)){
					supplierList.setRowSelectionInterval(i, i);
					break;
				}
			}
		}
	}

	public static void setSelectedRow(int id){
		for(int i = 0; i < supplierList.getRowCount(); i++){
			if((int)supplierList.getModel().getValueAt(i, 0) == id){
				supplierList.setRowSelectionInterval(i, i);
				supplierList.scrollRectToVisible(new Rectangle(supplierList.getCellRect(i, 0, true)));
				break;
			}
		}
	}

	public static void removeAlreadySupplier(){
		if(alreadySupplier != null){
			for(int row = 0; row < supplierList.getRowCount(); row++)
			if(alreadySupplier.equals(supplierList.getValueAt(row, 0))){
				modelForSupplierList.removeRow(row);
				break;
			}
		}
	}
}
