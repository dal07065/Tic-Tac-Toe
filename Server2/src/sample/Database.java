package sample;

import java.io.File;
import java.net.URL;
import java.sql.*;

public class Database {

    public static Connection connection;

    public Database()
    {
//        String url = "jdbc:sqlite:C:/sqlite/tictactoe.db";

        String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/tictactoe.db";

        System.out.println(url);

        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("Database connection successful.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void insertUser(User userInfo) {

        String sql = "INSERT INTO User(userID, password) VALUES(?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, userInfo.getUserID());
            pstmt.setString(2, userInfo.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
