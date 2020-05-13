package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerTable {
	public static Object[][] retrieveAll(){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM customer ORDER BY customer_name";

		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet=statement.executeQuery(query);
			resultSet.last();
			result=new Object[resultSet.getRow()][3];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("customer_name");
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

	public static Object[][] retrieveFilterByCustomerName(String customerName){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM customer WHERE customer_name LIKE ? ORDER BY customer_name";

		try {
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, "%" +customerName+ "%" );
			ResultSet resultSet=statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][3];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("customer_name");
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
		String query= "INSERT INTO customer(customer_name,phone,address) VALUES(?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, (String)data[0]);
			statement.setString(2, (String)data[1]);
			statement.setString(3, (String) data[2]);

			statement.executeUpdate();
//			ResultSet rs = statement.getGeneratedKeys();
//			if(rs.next()){
//				System.out.println(rs.getInt(1));
//			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void update(String[] data, String originalCustomerName){
		Connection connection = DBConnection.createConnection();
		String query = "UPDATE customer SET customer_name=?,phone=?,address=? WHERE customer_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, data[0]);
			statement.setString(2, data[1]);
			statement.setString(3, data[2]);
			statement.setString(4, originalCustomerName);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isCustomerExist(String customerName){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idcustomer FROM customer WHERE customer_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, customerName);
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

	public static boolean isCustomerNameExist(String customerName){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idcustomer FROM customer WHERE customer_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, customerName);
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
