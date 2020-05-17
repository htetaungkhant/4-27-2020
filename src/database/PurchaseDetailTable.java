package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;

public class PurchaseDetailTable {

	public static Object[][] retrieve(int idpurchase){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT p.idpurchase_detail, s.item_name, p.quantity, p.unit_price, p.amount FROM purchase_detail p INNER JOIN stock s ON p.item = s.idstock WHERE p.invoice_number=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setInt(1, idpurchase);
			ResultSet resultSet = statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][5];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=resultSet.getInt("idpurchase_detail");
					result[row][1]=resultSet.getString("item_name");
					result[row][2]=resultSet.getInt("quantity");
					result[row][3]=resultSet.getInt("unit_price");
					result[row][4]=resultSet.getInt("amount");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

//	public static ArrayList<Object> getItemIdList(int idpurchase){
//		ArrayList<Object> result = new ArrayList<Object>();
//		Connection connection = DBConnection.createConnection();
//		String query="SELECT item FROM purchase_detail pd WHERE pd.invoice_number=?";
//		try {
//			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			statement.setInt(1, idpurchase);
//			ResultSet resultSet = statement.executeQuery();
//			while(resultSet.next()){
//				result.add(resultSet.getInt("item"));
//			}
//			statement.close();
//			connection.close();
//			return result;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

	public static boolean insert(JTable table, int invoiceNumber){
		int rows = table.getRowCount();
		Connection connection = DBConnection.createConnection();
		String query= "INSERT INTO purchase_detail(invoice_number, item, quantity, unit_price, amount) SELECT ?, idstock, ?, ?, ? FROM stock WHERE item_name=?";
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
