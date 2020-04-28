package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection{
	public static String databaseDriver = "com.mysql.cj.jdbc.Driver";
	public static String serverAddress = "jdbc:mysql://localhost/4_27_2020?useUnicode=true&characterEncoding=UTF-8";
	public static String serverUsername = "root";
	public static String serverPassword = "root";

			public static Connection createConnection(){
				Connection connection = null;
						try {
							Class.forName(databaseDriver).newInstance();
							connection=DriverManager.getConnection(serverAddress,serverUsername,serverPassword);
						} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//							System.out.println(e.getMessage());
							e.printStackTrace();
						}
						return connection;
			}
}
