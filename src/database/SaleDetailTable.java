package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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

	public static void delete(ArrayList<Integer> list2Delete){
		Connection connection = DBConnection.createConnection();
		String query= "DELETE FROM sale_detail WHERE idsale_detail=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for(int i = 0; i < list2Delete.size(); i++){
				statement.setInt(1, list2Delete.get(i));
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

	public static void update(JTable table){
		int rows = table.getRowCount();
		Connection connection = DBConnection.createConnection();
		String query= "UPDATE sale_detail SET quantity=?, amount=? WHERE idsale_detail=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			for(int row = 0; row < rows; row++){
				statement.setInt(1, (int) table.getValueAt(row, 1));
				statement.setInt(2, (int) table.getValueAt(row, 3));
				statement.setInt(3, (int) table.getModel().getValueAt(row, 0));
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

	public static boolean isAlreadySale(int id){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT sd.idsale_detail FROM sale_detail sd INNER JOIN sale s ON sd.invoice_number=s.idsale WHERE s.date>=(SELECT p.date FROM purchase p WHERE p.idpurchase=?) AND sd.item IN (SELECT item FROM purchase_detail pd WHERE pd.invoice_number=?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
//			statement.setTimestamp(1, new Timestamp(date.getTime()));
			statement.setInt(1, id);
			statement.setInt(2, id);
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
