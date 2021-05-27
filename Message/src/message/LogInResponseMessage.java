package message;

public class LogInResponseMessage extends UserInfoMessage{

    private boolean success;
    public LogInResponseMessage(boolean success, String userID, String password, String firstName, String lastName) {
        super(userID, password, firstName, lastName);
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
