package message;

public class NewUserMessage extends UserInfoMessage{

    public NewUserMessage(String userID, String password, String firstName, String lastName) {
        super(userID, password, firstName, lastName);
    }
}
