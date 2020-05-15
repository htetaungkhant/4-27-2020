package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaleTable {

	public static Object[][] retrieve(Date date1, Date date2, String customerName, String invoiceNumber){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();

		try {
			String query="SELECT LPAD(s.idsale, 10, '0'),date, customer_name, amount, net_amount, discount, remark FROM sale s INNER JOIN customer c ON s.customer = c.idcustomer WHERE s.date BETWEEN ? AND ? AND LPAD(s.idsale, 10, '0') LIKE ? ORDER BY s.idsale";
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setTimestamp(1, new Timestamp(date1.getTime()));
			statement.setTimestamp(2, new Timestamp(date2.getTime()));
			statement.setString(3, "%" +invoiceNumber+ "%" );

			if(!customerName.equals("Choose Customer")){
				query="SELECT LPAD(s.idsale, 10, '0'),date, customer_name, amount, net_amount, discount, remark FROM sale s INNER JOIN customer c ON s.customer = c.idcustomer AND c.customer_name=? WHERE s.date BETWEEN ? AND ? AND LPAD(s.idsale, 10, '0') LIKE ? ORDER BY s.idsale";
				statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.setString(1, customerName);
				statement.setTimestamp(2, new Timestamp(date1.getTime()));
				statement.setTimestamp(3, new Timestamp(date2.getTime()));
				statement.setString(4, "%" +invoiceNumber+ "%" );
			}

			ResultSet resultSet=statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][7];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date"));
					result[row][1]=(String)resultSet.getString("customer_name");
					result[row][2]=resultSet.getString("LPAD(s.idsale, 10, '0')");
					result[row][3]=resultSet.getInt("amount");
					result[row][4]=resultSet.getInt("net_amount");
					result[row][5]=resultSet.getInt("discount");
					result[row][6]=(String)resultSet.getString("remark");
			}
			statement.close();
			connection.close();
			return result;
		} catch (SQLException | ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public static int insert(Object[] data){
		int result = 0;
		Connection connection = DBConnection.createConnection();
		String query= "INSERT INTO sale(date, customer, amount, net_amount, discount, remark) SELECT ?, idcustomer, ?, ?, ?, ? FROM customer WHERE customer_name = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			Date date = (java.util.Date)data[0];
			statement.setTimestamp(1, new Timestamp(date.getTime()));
			statement.setInt(2, (int) data[2]);
			statement.setInt(3, (int) data[3]);
			statement.setInt(4, (int) data[4]);
			statement.setString(5, (String) data[5]);
			statement.setString(6, (String) data[1]);

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

	public static void delete(int idsale){
		Connection connection = DBConnection.createConnection();
		String query ="DELETE FROM sale WHERE idsale=?";
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, idsale);
			prepareStatement.executeUpdate();

			prepareStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
