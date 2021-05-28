package sample.server;

import SharedServerComponents.ClientConnection;
import message.*;

import java.io.IOException;
import java.util.ArrayList;

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

    public static boolean login(String userID, String password) {
        connection.sendPacket(new Packet( "LogIn", new LogInMessage(userID, password)));
        Packet packet = connection.getPacket("LogInResponse");
        if(!((LogInResponseMessage)packet.getMessage()).isSuccess())
            return false;
        else {
            user = new User(packet);
            return true;
        }
    }

    public static boolean createUser(String userID, String password, String firstName, String lastName)  {
        connection.sendPacket(new Packet("NewUser", new NewUserMessage(userID, password, firstName, lastName,0,0,0)));
        NewUserResponseMessage msg = (NewUserResponseMessage) (connection.getPacket("NewUserResponse")).getMessage();
        if(msg.isSuccessful())
            return true;
        else
            return false;

    }

    public static boolean updateUser(String userID, String password, String firstName, String lastName)  {
        connection.sendPacket(new Packet("UpdateUser", new UpdateUserMessage(user.getUserID(), userID, password, firstName, lastName)));
        UpdateUserResponseMessage msg = (UpdateUserResponseMessage) (connection.getPacket("UpdateUserResponse").getMessage());
        if(!msg.isSuccessful())
            return false;
        user.update(userID, password, firstName, lastName);
        return true;
    }

    public static Packet makeGameMove(char currentPlayer, String moveID)  {
        connection.sendPacket(new Packet( "GameMove", new GameMoveMessage(moveID, user.getGameID(), currentPlayer)));
        return connection.getPacket("GameMoveResponse");
    }

    public static Packet waitForGameMove()  {
        return connection.getPacket("GameMoveResponse");
    }

    public static void startNewGame() {
        connection.sendPacket(new Packet( "NewGame", new NewGameMessage(user.getUserID())));
        Packet packet = connection.getPacket("NewGameResponse");
        connection.sendPacket(new Packet("SubscribeMessage", new SubscribeMessage(((NewGameResponseMessage)packet.getMessage()).getGameID())));
        user.setCurrentGameID(((NewGameResponseMessage)packet.getMessage()).getGameID());

    }

    public static void startNewGameAI(char AIrole)
    {
        connection.sendPacket(new Packet("NewGameAI", new NewGameAIMessage(user.getUserID(), AIrole)));
        Packet packet = connection.getPacket("NewGameResponse");
        connection.sendPacket(new Packet("SubscribeMessage", new SubscribeMessage(((NewGameResponseMessage)packet.getMessage()).getGameID())));
        user.setCurrentGameID(((NewGameResponseMessage)packet.getMessage()).getGameID());
    }


    public static void waitForPlayerToJoinGame()
    {
        Packet packetJoinGame = connection.getPacket("JoinGameResponse");
        if(packetJoinGame.getMessage() instanceof JoinGameResponseMessage)
        {
            System.out.println("Player has joined!");
        }
    }

    public static boolean joinGame(String selectedItem) {

        connection.sendPacket(new Packet( "JoinGame", new JoinGameMessage(user.getUserID(), selectedItem)));
        JoinGameResponseMessage msg = (JoinGameResponseMessage) connection.getPacket("JoinGameResponse").getMessage();
        if(msg.isSuccess())
        {
            user.setCurrentGameID(msg.getGameID());
            connection.sendPacket(new Packet("SubscribeMessage", new SubscribeMessage(msg.getGameID())));

            return true;
        }
        else
            return false;
    }

    public static Message joinGameAsViewer(String gameID)
    {
        connection.sendPacket(new Packet("JoinGameAsViewer", new JoinGameAsViewerMessage(user.getUserID(), gameID)));
        JoinGameAsViewerResponseMessage msg = (JoinGameAsViewerResponseMessage) connection.getPacket("JoinGameAsViewerResponse").getMessage();
        if(msg.isSuccess())
        {
            user.setCurrentGameID(gameID);
            connection.sendPacket(new Packet("SubscribeMessage", new SubscribeMessage(gameID)));

        }
        return msg;
    }

    public static ArrayList<String> getAllGames() {
        connection.sendPacket(new Packet("GetAllGames", new GetAllGamesMessage()));
        return ((GetAllGamesResponseMessage) connection.getPacket("GetAllGamesResponse").getMessage()).getAllGames();
    }

    public static void quitCurrentGame() {
        connection.sendPacket(new Packet("QuitGame", new QuitGameMessage(user.getGameID(), user.getUserID())));
        user.setCurrentGameID("");
    }

    public static void deleteUser() {
        connection.sendPacket(new Packet("DeleteUser", new DeleteUserMessage(user.getUserID())));
    }

    public static void resetCurrentAIGame() {
        connection.sendPacket(new Packet("ResetAIGame", new ResetBoardMessage(user.getGameID())));
    }

}
