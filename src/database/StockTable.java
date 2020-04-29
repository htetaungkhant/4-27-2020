package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockTable {
	public static Object[][] retrieveAll(){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM stock ORDER BY item_name";

		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet=statement.executeQuery(query);
			resultSet.last();
			result=new Object[resultSet.getRow()][7];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("item_name");
					result[row][1]=(String)resultSet.getString("barcode");
					result[row][2]=resultSet.getInt("purchase_price");
					result[row][3]=resultSet.getInt("sale_price");
					result[row][4]=resultSet.getInt("quantity");
					result[row][5]=resultSet.getInt("limit_quantity");
					result[row][6]=(String)resultSet.getString("remark");
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
			String query= "INSERT INTO stock(item_name,barcode,purchase_price,sale_price,quantity,limit_quantity,remark) VALUES(?,?,?,?,?,?,?)";
			try {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setString(1, (String)data[0]);
				statement.setString(2, (String)data[1]);
				statement.setInt(3, Integer.parseInt(data[2]));
				statement.setInt(4, Integer.parseInt(data[3]));
				statement.setInt(5, Integer.parseInt(data[4]));
				statement.setInt(6, Integer.parseInt(data[5]));
				statement.setString(7, (String) data[6]);

				statement.executeUpdate();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
