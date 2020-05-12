package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;

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
					result[row][2]=resultSet.getInt("cost");
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

	public static Object[][] retrieveItems2Purchase(){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM stock WHERE quantity<=limit_quantity ORDER BY item_name";

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
					result[row][2]=resultSet.getInt("cost");
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

	public static Object[][] retrieveFilterByItemName(String itemName){
		Object[][] result = null;
		Connection connection = DBConnection.createConnection();
		String query="SELECT * FROM stock WHERE item_name LIKE ? ORDER BY item_name";

		try {
			PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, "%" +itemName+ "%" );
			ResultSet resultSet=statement.executeQuery();
			resultSet.last();
			result=new Object[resultSet.getRow()][7];
			resultSet.beforeFirst();
			int row=-1;
			while(resultSet.next()){
					++row;
					result[row][0]=(String)resultSet.getString("item_name");
					result[row][1]=(String)resultSet.getString("barcode");
					result[row][2]=resultSet.getInt("cost");
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
			String query= "INSERT INTO stock(item_name,barcode,cost,sale_price,quantity,limit_quantity,remark) VALUES(?,?,?,?,?,?,?)";
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

	public static void update(String[] data, String originalBarcode){
		Connection connection = DBConnection.createConnection();
		String query = "UPDATE stock SET item_name=?,barcode=?,cost=?,sale_price=?,quantity=?,limit_quantity=?,remark=? WHERE barcode=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setString(1, data[0]);
			statement.setString(2, data[1]);
			statement.setInt(3, Integer.parseInt(data[2]));
			statement.setInt(4, Integer.parseInt(data[3]));
			statement.setInt(5, Integer.parseInt(data[4]));
			statement.setInt(6, Integer.parseInt(data[5]));
			statement.setString(7, data[6]);
			statement.setString(8, originalBarcode);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addQuantityAndUpdateCOGS(JTable table){
		int rows = table.getRowCount();
		Connection connection = DBConnection.createConnection();
		String query= "UPDATE stock SET cost=((cost*quantity)+(?*?))/(quantity+?), quantity=quantity+? WHERE item_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for(int row = 0; row < rows; row++){
				statement.setInt(1, (int) table.getValueAt(row, 2));
				statement.setInt(2, (int) table.getValueAt(row, 1));
				statement.setInt(3, (int) table.getValueAt(row, 1));
				statement.setInt(4, (int) table.getValueAt(row, 1));
				statement.setString(5, (String) table.getValueAt(row, 0));
				statement.addBatch();
			}

			statement.executeBatch();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void subtractQuantityAndUpdateCOGS(JTable table){
		int rows = table.getRowCount();
		Connection connection = DBConnection.createConnection();
		String query= "UPDATE stock SET cost=((cost*quantity)-(?*?))/(quantity-?), quantity=quantity-? WHERE item_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for(int row = 0; row < rows; row++){
				statement.setInt(1, (int) table.getValueAt(row, 2));
				statement.setInt(2, (int) table.getValueAt(row, 1));
				statement.setInt(3, (int) table.getValueAt(row, 1));
				statement.setInt(4, (int) table.getValueAt(row, 1));
				statement.setString(5, (String) table.getValueAt(row, 0));
				statement.addBatch();
			}

			statement.executeBatch();
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static boolean isItemExist(String itemName, String barcode){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idstock FROM stock WHERE item_name=? OR barcode=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, itemName);
			statement.setString(2, barcode);
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

	public static boolean isItemNameExist(String itemName){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idstock FROM stock WHERE item_name=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, itemName);
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

	public static boolean isBarcodeExist(String barcode){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idstock FROM stock WHERE barcode=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, barcode);
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

	public static Object[] getItemDetail(String barcode){
		Object[] result = null;
		Connection connection = DBConnection.createConnection();
		String query = "SELECT idstock, item_name, cost, sale_price, quantity FROM stock WHERE barcode=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, barcode);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				result = new Object[5];
				result[0] = rs.getInt("idstock");
				result[1] = rs.getString("item_name");
				result[2] = rs.getInt("cost");
				result[3] = rs.getInt("sale_price");
				result[4] = rs.getInt("quantity");
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
