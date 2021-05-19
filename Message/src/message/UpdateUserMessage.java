package message;

public class UpdateUserMessage extends UserInfoMessage{
    public UpdateUserMessage(String userID, String password, String firstName, String lastName) {
        super(userID, password, firstName, lastName);
    }
}
