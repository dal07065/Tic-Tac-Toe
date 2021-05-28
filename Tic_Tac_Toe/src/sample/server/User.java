package sample.server;

import message.Message;
import message.Packet;
import message.UserInfoMessage;
import sample.ControllerWatch;

public class User {

    private String userID;
    private String password;
    private String firstName;
    private String lastName;
    private int wins;
    private int loses;
    private int ties;

    private String currentGameID;
    public void setCurrentGameID(String id) {currentGameID = id;}

//    private ControllerWatch currentGameStream;
//    public void setCurrentGameStream(ControllerWatch controllerWatch) {currentGameStream = controllerWatch;}
//    public ControllerWatch getCurrentGameStream() {return currentGameStream;}

    public String getCurrentGameID() {
        return currentGameID;
    }

    public User(Packet userInfo) {
        //   ID | PWD | first | last
        // "1234/12345|Derrick|Hendrickson"

        set(userInfo);
    }

    public void set(Packet userInfoPacket) {
        UserInfoMessage userInfo = (UserInfoMessage) userInfoPacket.getMessage();
        set(userInfo.getUserID(), userInfo.getPassword(), userInfo.getFirstName(), userInfo.getLastName(),
                userInfo.getWins(), userInfo.getLoses(), userInfo.getTies());
    }

    public void set(String userID, String password, String firstName, String lastName, int wins, int loses, int ties) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.loses = loses;
        this.ties = ties;
    }

    public void update(Packet userInfoPacket) {
        UserInfoMessage userInfo = (UserInfoMessage) userInfoPacket.getMessage();
        if (!userInfo.getPassword().equals(""))
            this.password = userInfo.getPassword();
        if (!userInfo.getFirstName().equals(""))
            this.firstName = userInfo.getFirstName();
        if (!userInfo.getLastName().equals(""))
            this.lastName = userInfo.getLastName();

    }
    public void update(String userID, String password, String firstName, String lastName) {

        if (!userID.equals(""))
            this.userID = userID;
        if (!password.equals(""))
            this.password = password;
        if (!firstName.equals(""))
            this.firstName = firstName;
        if (!lastName.equals(""))
            this.lastName = lastName;

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

    public String getGameID() { return currentGameID;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getTies() {
        return ties;
    }
}
