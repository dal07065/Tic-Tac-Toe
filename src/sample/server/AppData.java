package sample.server;

import message.*;

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
        connection.sendPacket(new Packet("signin", new LogInMessage(userID, password)));
        Packet packet = connection.getPacket();
        if(packet == null)
            return false;
        else {
            user = new User(packet);
            return true;
        }
    }

    public static void createUser(String userID, String password, String firstName, String lastName) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("newUser", new UserInfoMessage(userID, password, firstName, lastName)));
    }

    public static void updateUser(String userID, String password, String firstName, String lastName) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("updateUser", new UserInfoMessage(userID, password, firstName, lastName)));
        Packet updatedUser = connection.getPacket();
        user.update(updatedUser);
    }

    public static Packet connectToGame(int password) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("newGame", new ConnectGameMessage(password)));
        return connection.getPacket();
    }

    public static Packet makeGameMove(char currentPlayer, String moveID) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("makeGameMove", new GameMoveMessage(moveID, currentPlayer)));
        return connection.getPacket();
    }

    public static Packet waitForGameMove(char currentPlayer) throws IOException, ClassNotFoundException {
        return connection.getPacket();
    }

}
