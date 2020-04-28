package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginChecker {
	public static boolean Check(String username, String password){
		Connection connection = DBConnection.createConnection();
			try {
				String query = "SELECT idadmin FROM admin WHERE username=? and password=?";
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setString(1, username);
				statement.setString(2, password);
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
