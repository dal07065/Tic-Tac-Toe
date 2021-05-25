package message;

public class NewGameResponseMessage extends Message{
    private String gameID;

    public NewGameResponseMessage(String boardID)
    {
        this.gameID = boardID;
    }

    public String getGameID() {return gameID;}

}
