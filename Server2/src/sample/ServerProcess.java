package sample;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class ServerProcess implements Runnable {
    private ServerSocket server;

    private boolean keepRunning = true;

    private ArrayList<User> users = null;

    private ArrayList<ClientConnection> clientConnections = new ArrayList<>();

    private ArrayList<LANConnection> lanConnections = new ArrayList<>();

    @Override
    public void run() {

        try
        {
            server = new ServerSocket(8000);

            while (keepRunning) {

                System.out.println("Server started and listening for new connections...");

                Socket clientSocketConnection = server.accept();    // when a user simply just ran the client program (before logging in or anything)

                ClientConnection client = new ClientConnection(this, clientSocketConnection);

                clientConnections.add(client);

                System.out.println();
            }

            System.out.println("Shutting SERVER down");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                server.close();
            }
            catch (Exception ex){}
        }

    }
    public void addUser(User user)
    {
        Database.insertUser(user);
    }

    public void addClientConnection(ClientConnection connection)
    {
        clientConnections.add(connection);
    }

    public void addLANConnection(LANConnection newConnection)
    {
        lanConnections.add(newConnection);
    }

    public User findUser(String userID, String password) throws SQLException {

        String dbURL = "jdbc:sqlite:" + System.getProperty("user.dir") + "/sqlite/tictactoe.db";
        String dbUser = userID;
        String dbPass = "password";

        Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
        String query = "Select * from User where userID= ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, userID);
        preparedStatement.setString(2, password);

        ResultSet result = preparedStatement.executeQuery();

        User user = null;

        if(result.next())
        {
            user = new User();
        }
        connection.close();

        return user;

        // query to database to find a certain user

        /*for(User userA: users)
        {
            if(userA.getUserID().equals(userID))
                return userA;
        }*/

    }

    public LANConnection findLANConnection(int roomPassword)
    {
        for(LANConnection connection: lanConnections)
        {
            if(connection.getRoomPassword() == roomPassword)
                return connection;
        }
        return null;
    }

    public void removeLANConnection(int roomPassword)
    {
        // find LAN connection based on roomPassword
    }

}