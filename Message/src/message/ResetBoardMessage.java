package message;

public class ResetBoardMessage extends Message{

    private String boardID;

    public ResetBoardMessage(String boardID)
    {
        this.boardID = boardID;
    }

    public String getBoardID() {return boardID;}
}
