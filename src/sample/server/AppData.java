package sample.server;

import sample.message.Message;

import java.io.IOException;

public final class AppData {
    public static ServerConnection connection;
    public static User user;

    public static void connectToServer() throws IOException {
        if(connection == null)
        {
            connection = new ServerConnection();

        }
    }

    public static void disconnectToServer() throws IOException {
        connection.disconnect();
        connection = null;
        user = null;
    }

    public static boolean login(String userID, String password) throws IOException, ClassNotFoundException {
        Message msg = connection.sendMessage(new Message("signin/"+userID +"/" + password +"/"));

        if(msg == null)
            return false;
        else {
            user = new User(msg);
            return true;
        }
    }

    public static void createUser(Message msg) throws IOException, ClassNotFoundException {
        connection.sendMessage(msg);
    }

    public static void updateUser(Message msg) throws IOException, ClassNotFoundException {
        Message updatedUser = connection.sendMessage(msg);
        user.update(updatedUser);
    }

    public static Message connectToGame(int password) throws IOException, ClassNotFoundException {
        return connection.sendMessage(new Message("game/"+password+"/"));
    }


}
