package message;

public class QuitGameMessage extends Message{

    private String gameID;
    private String playerWhoQuitID;

    public QuitGameMessage(String gameID, String playerID)
    {
        this.gameID = gameID;
        this.playerWhoQuitID = playerID;
    }

    public String getGameID(){return gameID;}

    public String getPlayerWhoQuitID() {return playerWhoQuitID;}
}
