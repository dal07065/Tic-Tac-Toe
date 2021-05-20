package message;

public class UserInfoMessage extends Message{

    private String userID;
    private String password;
    private String firstName;
    private String lastName;
    private int wins;
    private int loses;
    private int ties;

    public UserInfoMessage(String userID, String password, String firstName, String lastName, int wins, int loses, int ties)
    {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.loses = loses;
        this.ties = ties;
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

    public int getWins() { return wins; }

    public int getLoses() { return loses; }

    public int getTies() { return ties; }
}
