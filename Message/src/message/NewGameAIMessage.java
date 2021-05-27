package message;

public class NewGameAIMessage extends Message{

    private String userID;
    private char playerRole;
    private String boardID;

    public NewGameAIMessage(String userID, char playerRole)
    {
        this.userID = userID;
        this.playerRole = playerRole;
    }

    public String getUserWhoStartedThisGame() {
        return userID;
    }

    public char getRole()
    {
        return playerRole;
    }

    public void setBoardID(String boardID)
    {
        this.boardID = boardID;
    }

    public String getBoardID()
    {
        return boardID;
    }

}
