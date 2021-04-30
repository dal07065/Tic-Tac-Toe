package sample.server;

import sample.message.Message;

public class User {

    private String userID;
    private String password;
    private String firstName;
    private String lastName;

    public User() {

    }

    public User(String userID, String password, String firstName, String lastName) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Message userInfo) {
        //   ID | PWD | first | last
        // "1234/12345|Derrick|Hendrickson"

        set(userInfo);
    }

    public void set(Message userInfo) {
        this.userID = userInfo.get(0);
        this.password = userInfo.get(1);
        this.firstName = userInfo.get(2);
        this.lastName = userInfo.get(3);
    }

    public void set(String userID, String password, String firstName, String lastName) {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(Message userInfo) {
        if (!userInfo.get(1).equals(""))
            this.password = userInfo.get(1);
        if (!userInfo.get(2).equals(""))
            this.firstName = userInfo.get(2);
        if (!userInfo.get(3).equals(""))
            this.lastName = userInfo.get(3);

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

}
