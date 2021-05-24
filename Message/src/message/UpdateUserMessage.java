package message;

public class UpdateUserMessage extends UserInfoMessage{
    private String originalUserID;
    public UpdateUserMessage(String id, String userID, String password, String firstName, String lastName) {
        super(userID, password, firstName, lastName);
        this.originalUserID = id;
    }
    public String getOriginalUserID()
    {
        return originalUserID;
    }
}
