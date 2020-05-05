package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class PurchaseTable {
	public static int insert(Object[] data){
		int result = 0;
		Connection connection = DBConnection.createConnection();
		String query= "INSERT INTO purchase(date, supplier, original_invoice_number, amount, paid_amount) SELECT ?, idsupplier, ?, ?, ? FROM supplier WHERE supplier_name = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(data[0]));
			statement.setString(2, (String) data[2]);
			statement.setInt(3, (int) data[3]);
			statement.setInt(4, (int) data[4]);
			statement.setString(5, (String) data[1]);

			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()){
				result = rs.getInt(1);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void delete(int idpurchase){
		Connection connection = DBConnection.createConnection();
		String query ="DELETE FROM purchase WHERE idpurchase=?";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, idpurchase);
			prepareStatement.executeUpdate();

			prepareStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
