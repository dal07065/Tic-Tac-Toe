package message;

public class LogInResponseMessage extends UserInfoMessage{

    public LogInResponseMessage(String userID, String password, String firstName, String lastName, int wins, int loses, int ties) {
        super(userID, password, firstName, lastName, wins, loses, ties);
    }
}
