package message;

public class LogInResponseMessage extends UserInfoMessage{

    private boolean success;
    public LogInResponseMessage(boolean success, String userID, String password, String firstName, String lastName,
                                int wins, int loses, int ties) {
        super(userID, password, firstName, lastName, wins, loses, ties);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
