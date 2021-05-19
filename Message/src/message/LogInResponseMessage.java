package message;

public class LogInResponseMessage extends UserInfoMessage{

    public LogInResponseMessage(String userID, String password, String firstName, String lastName) {
        super(userID, password, firstName, lastName);
    }
}
