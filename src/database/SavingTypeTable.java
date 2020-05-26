package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SavingTypeTable {
		public static String[] getSavingTypes()
		{
					Connection connection=DBConnection.createConnection();
					String query="SELECT type_name FROM saving_type ORDER BY idsaving_type";
					try {
							PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
							ResultSet resultSet=statement.executeQuery();
		
							resultSet.last();
							String[] result =new String[resultSet.getRow()];
							resultSet.first();
							resultSet.previous();
							int row=0;
							while(resultSet.next()){
								result[row]=resultSet.getString("type_name");
								++row;
							}
							statement.close();
							connection.close();
							return result;
					} catch (SQLException e) {
							e.printStackTrace();
							String[] result = {};
							return result;
					}
			}
}
