package tester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import database.DBConnection;

public class Test2 {

	public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		Date date = calendar.getTime();
		System.out.println(isExist(date, 1));
	}

	public static boolean isExist(Date date, int id){
		Connection connection = DBConnection.createConnection();
		String query = "SELECT sd.idsale_detail FROM sale_detail sd INNER JOIN sale s ON sd.invoice_number=s.idsale WHERE s.date>=? AND sd.item IN (SELECT item FROM purchase_detail pd WHERE pd.invoice_number=?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setTimestamp(1, new Timestamp(date.getTime()));
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
