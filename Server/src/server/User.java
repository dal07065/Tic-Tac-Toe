package server;

import message.UserInfoMessage;

import java.net.Socket;

public class User {

    private String userID;
    private String password;
    private String firstName;
    private String lastName;

    private int wins;
    private int loses;
    private int ties;

    private Socket socket;

    public User() {

    }

    public User(String userID, String password, String firstName, String lastName, int wins, int loses, int ties) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.loses = loses;
        this.ties = ties;
    }

    public User(UserInfoMessage userInfo) {
        //   ID | PWD | first | last
        // "1234/12345|Derrick|Hendrickson"

        set(userInfo);
    }

    public void set(UserInfoMessage userInfo) {
        set(userInfo.getUserID(), userInfo.getPassword(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getWins(), userInfo.getLoses(), userInfo.getTies());
    }

    public void set(String userID, String password, String firstName, String lastName, int wins, int loses, int ties) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(UserInfoMessage userInfo) {
        if (!userInfo.getPassword().equals(""))
            this.password = userInfo.getPassword();
        if (!userInfo.getFirstName().equals(""))
            this.firstName = userInfo.getFirstName();
        if (!userInfo.getLastName().equals(""))
            this.lastName = userInfo.getLastName();

    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() {
        return lastName;
    }

    public int getWins() { return wins; }

    public int getLoses() { return loses; }

    public int getTies() { return ties; }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

}
