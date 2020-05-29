package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

public class MoneyTransferTable {
	
		public static Object[][] retrieve(Date date1, Date date2, String supplierName, String invoiceNumber){
			Object[][] result = null;
			Connection connection = DBConnection.createConnection();
	
			try {
				String query="SELECT idmoney_transfer, mt.date, mt.amount, mt.invoice_number, p.original_invoice_number, s.supplier_name, mt.remark FROM money_transfer mt INNER JOIN purchase p ON mt.invoice_number = p.idpurchase INNER JOIN supplier s ON mt.supplier = s.idsupplier WHERE DATE(mt.date) BETWEEN ? AND ? AND p.original_invoice_number LIKE ? ORDER BY mt.date";
				PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				statement.setDate(1, new java.sql.Date(date1.getTime()));
				statement.setDate(2, new java.sql.Date(date2.getTime()));
				statement.setString(3, "%" +invoiceNumber+ "%" );
	
				if(!supplierName.equals("ကုန်ပေးသူ / ဆိုင်နာမည် ရွေးရန်")){
					query="SELECT idmoney_transfer, mt.date, mt.amount, mt.invoice_number,p.original_invoice_number, s.supplier_name, mt.remark FROM money_transfer mt INNER JOIN purchase p ON mt.invoice_number = p.idpurchase INNER JOIN supplier s ON mt.supplier = s.idsupplier AND s.supplier_name=? WHERE DATE(mt.date) BETWEEN ? AND ? AND p.original_invoice_number LIKE ? ORDER BY mt.date";
					statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					statement.setString(1, supplierName);
					statement.setDate(2, new java.sql.Date(date1.getTime()));
					statement.setDate(3, new java.sql.Date(date2.getTime()));
					statement.setString(4, "%" +invoiceNumber+ "%" );
				}
	
				ResultSet resultSet=statement.executeQuery();
				resultSet.last();
				result=new Object[resultSet.getRow()][8];
				resultSet.beforeFirst();
				int row=-1;
				while(resultSet.next()){
						++row;
						result[row][0]=resultSet.getInt("idmoney_transfer");
						result[row][1]=new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date"));
						result[row][2]=(String)resultSet.getString("supplier_name");
						result[row][3]=(int)resultSet.getInt("invoice_number");
						result[row][4]=(String)resultSet.getString("original_invoice_number");
						result[row][5]=resultSet.getInt("amount");
						result[row][6]=(String)resultSet.getString("remark");
						result[row][7]=new ImageIcon("picture/delete_icon.png");
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
	
		public static void insert(Object[] data){
				Connection connection = DBConnection.createConnection();
				String query= "INSERT INTO money_transfer(date, amount, invoice_number, supplier, remark)  SELECT ?, ?, ?, idsupplier, ? FROM supplier WHERE supplier_name = ?";
				try {
					PreparedStatement statement = connection.prepareStatement(query);
					Date date = (Date)data[0];
					statement.setTimestamp(1, new Timestamp(date.getTime()));
					statement.setInt(2, (int) data[1]);
					statement.setInt(3, (int) data[2]);
					statement.setString(4, (String) data[4]);
					statement.setString(5, (String) data[3]);

					statement.executeUpdate();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		public static void delete(int idmoney_transfer){
			Connection connection = DBConnection.createConnection();
			String query ="DELETE FROM money_transfer WHERE idmoney_transfer = ?";
			try {
				PreparedStatement prepareStatement = connection.prepareStatement(query);
				prepareStatement.setInt(1, idmoney_transfer);
				prepareStatement.executeUpdate();

				prepareStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
