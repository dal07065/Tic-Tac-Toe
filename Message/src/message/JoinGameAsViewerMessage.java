package message;

public class JoinGameAsViewerMessage extends Message{

    private String userID;
    private String gameID;

    public JoinGameAsViewerMessage(String userID, String gameID)
    {
        this.userID = userID;
        this.gameID = gameID;
    }

    public String getUserID() {
        return userID;
    }

    public String getGameID() {
        return gameID;
    }
}
