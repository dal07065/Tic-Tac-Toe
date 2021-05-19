package server;

import server.ServerProcessing.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Server server = new Server();


        Database db = new Database();

//        User tempUser = new User(new UserInfoMessage("coolUser", "jumbo1234", "John", "Smith"));

//        db.insertUser(tempUser);
    }
}
