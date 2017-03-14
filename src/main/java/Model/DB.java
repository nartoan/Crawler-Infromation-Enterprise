package Model;

import java.sql.*;

/**
 * Created by DucToan on 13/03/2017.
 */
public class DB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/crawler";

    static final String USER = "root";
    static final String PASS = "";
    public Connection connection = null;
     public DB() {
         try {
             Class.forName(JDBC_DRIVER);
             connection = DriverManager.getConnection(DB_URL, USER, PASS);
             System.out.println("Connect built");
         } catch (SQLException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
     }

     public ResultSet runSQL(String sql) throws SQLException {
         Statement statement = connection.createStatement();
         return statement.executeQuery(sql);
     }

     public boolean runSQL2(String sql) throws SQLException {
         Statement statement = connection.createStatement();
         return statement.execute(sql);
     }
}
