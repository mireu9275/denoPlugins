package mysql;

import javax.xml.transform.Result;
import java.sql.*;

class sqlVals{
    static final String url = "jdbc:mysql://localhost:3306/minecraft_pokemon";
    static final String userName = "root";
    static final String password = "Rambo4094!";
}


public class mySQL {
    public static void main(String label) throws SQLException {
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {


            connection = DriverManager.getConnection(sqlVals.url, sqlVals.userName, sqlVals.password);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(label);

            resultSet.next();
            String name = resultSet.getString("name");
            System.out.println(name);
        } catch (SQLException e) {
            System.out.println("에러 : " + e);
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
    }
}
