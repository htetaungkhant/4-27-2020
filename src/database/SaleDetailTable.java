package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;

public class SaleDetailTable {

	public static Object[][] retrieve(int idsale){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT sd.idsale_detail, s.item_name, sd.quantity, sd.sale_price, sd.amount FROM sale_detail sd INNER JOIN stock s ON sd.item = s.idstock WHERE sd.invoice_number=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, idsale);
			ResultSet resultSet = statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][6];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=resultSet.getInt("idsale_detail");
					result[row][1]=resultSet.getString("item_name");
					result[row][2]=resultSet.getInt("quantity"); //new JSpinner(new SpinnerNumberModel(resultSet.getInt("quantity"), 1, resultSet.getInt("quantity"), 1));
					result[row][3]=resultSet.getInt("sale_price");
					result[row][4]=resultSet.getInt("amount");
					result[row][5]=new ImageIcon("picture/delete_icon.png");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

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
