package saving;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import external_classes.Fonts;
import external_classes.MyTextField;
import main.Main;
import net.miginfocom.swing.MigLayout;
import saving.dialog.AddOrUpdatePerson;

public class SavingPeople extends JPanel{
		
		private String[] columnNames;
		private Object[][] tableData;
		private DefaultTableModel modelForPeopleList;
		private JTable peopleList;
	
		public SavingPeople() {
			setLayout(new BorderLayout());
			
			//creating Top Panel
			JPanel topPanel = new JPanel(new MigLayout("fill"));
			JButton btnAddNewPerson = new JButton("လူအသစ်ထည့်ရန်");
			btnAddNewPerson.setFont(Fonts.pyisuNormal15);
			btnAddNewPerson.setPreferredSize(new Dimension(150, 40));
			MyTextField tfSearchPerson = new MyTextField(40, "ရှာရန်");
			tfSearchPerson.setFont(Fonts.pyisuNormal15);
			tfSearchPerson.setPreferredSize(new Dimension(220, 40));
			topPanel.add(btnAddNewPerson);
			topPanel.add(tfSearchPerson, "align right");
			add(topPanel, BorderLayout.NORTH);
			//end of Top Panel
			
			//creating Table Panel
			peopleList = new JTable(modelForPeopleList);
			createPeopleListTable();
			JScrollPane tablePanel = new JScrollPane(peopleList);
			add(tablePanel, BorderLayout.CENTER);
			//End of Table Panel
			
			btnAddNewPerson.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AddOrUpdatePerson addPerson = new AddOrUpdatePerson(Main.frame);
					addPerson.setVisible(true);
				}
			});
		}
		
		private void createPeopleListTable() {
			tableData = null;
			columnNames = new String[]{"ID", "အမည်", "စုမည့်ပုံစံ", "ကောက်ရမည့်အချိန်", "စုမည့်ငွေပမာဏ", "ဖုန်းနံပါတ်", "လိပ်စာ", "ဖျက်ရန်"};
			DefaultTableModel modelForPeopleList = new DefaultTableModel(tableData, columnNames){
				public boolean isCellEditable(int row, int column) {
					return false;
				}
				public Class<?> getColumnClass(int columnIndex) {
					return getValueAt(0,columnIndex).getClass();
		        }
			};
			peopleList.setModel(modelForPeopleList);
			peopleList.getTableHeader().setPreferredSize(new Dimension(0, 50));
			peopleList.getTableHeader().setFont(Fonts.pyisuNormal16);
			peopleList.setRowHeight(40);
			peopleList.setFont(Fonts.pyisuNormal15);
			peopleList.removeColumn(peopleList.getColumnModel().getColumn(0));
			TableColumn column7 = peopleList.getColumnModel().getColumn(6);
			column7.setMaxWidth(100);
			column7.setPreferredWidth(60);
		}
}
