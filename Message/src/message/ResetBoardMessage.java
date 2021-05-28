package message;

public class ResetBoardMessage extends Message{

    private String userID;
    private String gameID;

    public ResetBoardMessage(String gameID, String userID)
    {
        this.gameID = gameID;
        this.userID = userID;
    }

    public String getGameID() {return gameID;}

    public String getUserID() {return userID;}
}
