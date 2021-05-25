package message;

public class LogInMessage extends Message{

    private String userID;
    private String password;

    public LogInMessage(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }
}
