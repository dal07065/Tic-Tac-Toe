package sample;


import message.UserInfoMessage;

public class Main {

    public static void main(String[] args) {

        Thread serverThread = new Thread(new ServerProcess());
        serverThread.start();

        Database db = new Database();

//        User tempUser = new User(new UserInfoMessage("coolUser", "jumbo1234", "John", "Smith"));

//        db.insertUser(tempUser);
    }
}
