package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JTable;

public class SaleDetailTable {
	public static boolean insert(JTable table, int invoiceNumber){
		int rows = table.getRowCount();
		Connection connection = DBConnection.createConnection();
		String query= "INSERT INTO sale_detail(invoice_number, item, quantity, sale_price, amount, cogs) SELECT ?, idstock, ?, ?, ?, cost FROM stock WHERE item_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for(int row = 0; row < rows; row++){
				statement.setInt(1, invoiceNumber);
				statement.setInt(2, (int) table.getValueAt(row, 1));
				statement.setInt(3, (int) table.getValueAt(row, 2));
				statement.setInt(4, (int) table.getValueAt(row, 3));
				statement.setString(5, (String) table.getValueAt(row, 0));

				statement.addBatch();
			}

			statement.executeBatch();
			statement.close();
			connection.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
