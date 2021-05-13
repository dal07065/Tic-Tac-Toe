package message;

public class UserInfoMessage extends Message{

    private String userID;
    private String password;
    private String firstName;
    private String lastName;

    public UserInfoMessage(String userID, String password, String firstName, String lastName)
    {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
