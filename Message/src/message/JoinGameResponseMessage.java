package message;

public class JoinGameResponseMessage extends Message{

    private boolean success;
    private String gameID;

    public JoinGameResponseMessage(boolean success, String gameID)
    {
        this.success = success;
        this.gameID = gameID;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getGameID() {
        return gameID;
    }

}
