package tester;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public class Test1 {

	public static void main(String[] args) {
//		Object[][] result = null;
//		Connection connection = DBConnection.createConnection();
//		String query="SELECT * FROM stock ORDER BY item_name";
//
//		try {
//			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			statement.setFetchSize(Integer.MIN_VALUE);
//			ResultSet rs=statement.executeQuery(query);
//			rs.last();
//			result=new Object[rs.getRow()][7];
//			rs.beforeFirst();
//			int row=-1;
//			while(rs.next()){
//					++row;
//					result[row][0]=(String)rs.getString("item_name");
//					result[row][1]=(String)rs.getString("barcode");
//					result[row][2]=rs.getInt("purchase_price");
//					result[row][3]=rs.getInt("sale_price");
//					result[row][4]=rs.getInt("quantity");
//					result[row][5]=rs.getInt("limit_quantity");
//					result[row][6]=(String)rs.getString("remark");
//			}
//			statement.close();
//			connection.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(result[2][3]);
		System.out.println(((4500*100)+(5500*13))/113);
	}

}
