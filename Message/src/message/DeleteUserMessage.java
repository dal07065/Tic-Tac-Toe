package message;

public class DeleteUserMessage extends Message{

    private String userID;
    public DeleteUserMessage(String userID)
    {
        this.userID = userID;
    }

    public String getUserID()
    {
        return userID;
    }


}
