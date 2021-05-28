package server;

import message.Message;
import message.NewUserMessage;
import message.UpdateUserMessage;
import message.UserInfoMessage;

import javax.xml.crypto.Data;
import java.sql.*;

public final class Database {

    public static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/tictactoe.db";
    public static Connection connection;
    public Database()
    {
//        String url = "jdbc:sqlite:C:/sqlite/tictactoe.db";

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

    public static void insertUser(NewUserMessage userInfo) throws SQLException {

        String sql = "INSERT INTO User(userID, password, firstName, lastName, wins, loses, ties, deleted) VALUES(?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, userInfo.getUserID());
            pstmt.setString(2, userInfo.getPassword());
            pstmt.setString(3, userInfo.getFirstName());
            pstmt.setString(4, userInfo.getLastName());
            pstmt.setInt(5, 0);
            pstmt.setInt(6, 0);
            pstmt.setInt(7, 0);
            pstmt.setInt(8, 0);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static User findUser(String userID) throws SQLException {


        String query = "Select * from User where userID= ? and deleted= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, userID);
        preparedStatement.setInt(2, 0);

        ResultSet result = preparedStatement.executeQuery();

        User user = null;

        if(!result.isClosed())
        {
            user = new User(result.getString(1),result.getString(2),result.getString(3),result.getString(4),
                    result.getInt(5), result.getInt(6), result.getInt(7));
        }

        result.close();

        return user;
    }

    public static boolean updateUser(UpdateUserMessage userInfoMessage) throws SQLException {

        if(!userInfoMessage.getUserID().equals(""))
        {
            User user = findUser(userInfoMessage.getUserID());
            if(user == null)
            {
                String query = "update user set userID=? where userID=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userInfoMessage.getUserID());
                preparedStatement.setString(2, userInfoMessage.getOriginalUserID());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            else
                return false;
        }
        if(!userInfoMessage.getPassword().equals(""))
        {
            String query = "update user set password=? where userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userInfoMessage.getPassword());
            preparedStatement.setString(2, userInfoMessage.getUserID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        if(!userInfoMessage.getFirstName().equals(""))
        {
            String query = "update user set firstName=? where userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userInfoMessage.getFirstName());
            preparedStatement.setString(2, userInfoMessage.getUserID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        if(!userInfoMessage.getLastName().equals(""))
        {
            String query = "update user set lastName=? where userID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userInfoMessage.getLastName());
            preparedStatement.setString(2, userInfoMessage.getUserID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        return true;
    }

    public static void deleteUser(String userID) throws SQLException {
        String query = "update user set deleted=? where userID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, userID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
