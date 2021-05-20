package message;

public class NewUserMessage extends UserInfoMessage{

    public NewUserMessage(String userID, String password, String firstName, String lastName, int wins, int loses, int ties) {
        super(userID, password, firstName, lastName, wins, loses, ties);
    }
}
