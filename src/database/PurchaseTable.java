package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseTable {

	public static Object[][] retrieve(Date date1, Date date2, String supplierName, String invoiceNumber){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();

		try {
			String query="SELECT date, supplier_name, original_invoice_number, amount, paid_amount FROM purchase p INNER JOIN supplier s ON p.supplier = s.idsupplier WHERE p.date BETWEEN ? AND ? ORDER BY p.date";
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(date1));
			statement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(date2));

			if(!supplierName.equals("Choose Supplier") && !invoiceNumber.equals("")){
				query="SELECT date, supplier_name, original_invoice_number, amount, paid_amount FROM purchase p INNER JOIN supplier s ON p.supplier = s.idsupplier AND s.supplier_name=? WHERE p.date BETWEEN ? AND ? AND p.original_invoice_number=? ORDER BY p.date";
				statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.setString(1, supplierName);
				statement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(date1));
				statement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(date2));
				statement.setString(4, invoiceNumber);
			}
			else if(!supplierName.equals("Choose Supplier")){
				query="SELECT date, supplier_name, original_invoice_number, amount, paid_amount FROM purchase p INNER JOIN supplier s ON p.supplier = s.idsupplier AND s.supplier_name=? WHERE p.date BETWEEN ? AND ? ORDER BY p.date";
				statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.setString(1, supplierName);
				statement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(date1));
				statement.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(date2));
			}
			else if(!invoiceNumber.equals("")){
				query="SELECT date, supplier_name, original_invoice_number, amount, paid_amount FROM purchase p INNER JOIN supplier s ON p.supplier = s.idsupplier WHERE p.date BETWEEN ? AND ? AND p.original_invoice_number=? ORDER BY p.date";
				statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.setString(1, new SimpleDateFormat("yyyy-MM-dd").format(date1));
				statement.setString(2, new SimpleDateFormat("yyyy-MM-dd").format(date2));
				statement.setString(3, invoiceNumber);
				}

			ResultSet resultSet=statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][5];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("date");
					result[row][1]=(String)resultSet.getString("supplier_name");
					result[row][2]=(String)resultSet.getString("original_invoice_number");
					result[row][3]=resultSet.getInt("amount");
					result[row][4]=resultSet.getInt("paid_amount");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

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
