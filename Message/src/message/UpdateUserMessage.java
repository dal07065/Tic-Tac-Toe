package message;

public class UpdateUserMessage extends UserInfoMessage{
    public UpdateUserMessage(String userID, String password, String firstName, String lastName, int wins, int loses, int ties) {
        super(userID, password, firstName, lastName, wins, loses, ties);
    }
}
