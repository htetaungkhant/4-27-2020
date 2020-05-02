package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SupplierTable {
	public static Object[][] retrieveAll(){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM supplier ORDER BY supplier_name";

		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet=statement.executeQuery(query);
			resultSet.last();
			result=new Object[resultSet.getRow()][3];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("supplier_name");
					result[row][1]=(String)resultSet.getString("phone");
					result[row][2]=(String)resultSet.getString("address");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String[] retrieveSupplierNamesOnly(){
		String[] result = {};
		Connection connection = DBConnection.createConnection();
		String query="SELECT supplier_name FROM supplier ORDER BY supplier_name";

		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet=statement.executeQuery(query);
			resultSet.last();
			result=new String[resultSet.getRow()];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row]=(String)resultSet.getString("supplier_name");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Object[][] retrieveFilterBySupplierName(String supplierName){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM supplier WHERE supplier_name LIKE ? ORDER BY supplier_name";

		try {
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, "%" +supplierName+ "%" );
			ResultSet resultSet=statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][7];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("supplier_name");
					result[row][1]=(String)resultSet.getString("phone");
					result[row][2]=(String)resultSet.getString("address");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void insert(String[] data){
		Connection connection = DBConnection.createConnection();
		String query= "INSERT INTO supplier(supplier_name,phone,address) VALUES(?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, (String)data[0]);
			statement.setString(2, (String)data[1]);
			statement.setString(3, (String) data[2]);

			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void update(String[] data, String originalSupplierName){
		Connection connection = DBConnection.createConnection();
		String query = "UPDATE supplier SET supplier_name=?,phone=?,address=? WHERE supplier_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, data[0]);
			statement.setString(2, data[1]);
			statement.setString(3, data[2]);
			statement.setString(4, originalSupplierName);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isSupplierExist(String supplierName){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idsupplier FROM supplier WHERE supplier_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, supplierName);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return true;
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isSupplierNameExist(String supplierName){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idsupplier FROM supplier WHERE supplier_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, supplierName);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return true;
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
