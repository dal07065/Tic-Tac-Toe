package sample.server;

import SharedServerComponents.ClientConnection;
import message.*;

import java.io.IOException;

public final class AppData {
    public static ClientConnection connection;
    public static User user;

    public static void connectToServer() throws IOException {
        if(connection == null)
        {
            connection = new ClientConnection("everyone");

        }
    }

    public static void disconnectToServer() throws IOException {
        connection.disconnect();
        connection = null;
        user = null;
    }

    public static boolean login(String userID, String password) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("LogIn", true, new LogInMessage(userID, password)));
        Packet packet = connection.getPacket("LogInResponse");
        if(packet.getMessage() == null)
            return false;
        else {
            user = new User(packet);
            return true;
        }
    }

    public static void createUser(String userID, String password, String firstName, String lastName) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("NewUser", false, new NewUserMessage(userID, password, firstName, lastName)));
    }

    public static void updateUser(String userID, String password, String firstName, String lastName) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("UpdateUser", false, new UpdateUserMessage(userID, password, firstName, lastName)));
        user.update(password, firstName, lastName);
    }

    public static Packet connectToGame(int password) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("ConnectGame", true, new ConnectGameMessage(password)));
        return connection.getPacket("ConnectGameResponse");
    }

    public static Packet makeGameMove(char currentPlayer, String moveID) throws IOException, ClassNotFoundException {
        connection.sendPacket(new Packet("GameMove", true, new GameMoveMessage(moveID, currentPlayer)));
        return connection.getPacket("GameMoveResponse");
    }

    public static Packet waitForGameMove(char currentPlayer) throws IOException, ClassNotFoundException {
        return connection.getPacket("GameMoveResponse");
    }

}
