package message;

public class QuitGameResponseMessage extends Message{
    private String gameID;
    private String playerWhoQuitID;
    public QuitGameResponseMessage(String gameID, String playerWhoQuitID) {
        this.gameID = gameID;
        this.playerWhoQuitID = playerWhoQuitID;
    }

    public String getGameID() {
        return gameID;
    }

    public String getPlayerWhoQuitID() {
        return playerWhoQuitID;
    }
}
