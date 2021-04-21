package sample;

import sample.message.Message;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private String userID;
    private String password;
    private String firstName;
    private String lastName;

    private Socket socket;

    public DataInputStream getInputStream() {
        return input;
    }

    private DataInputStream input;

    private DataOutputStream output;

    public User()
    {

    }

    public User(String userID, String password, String firstName, String lastName)
    {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        output.flush();
    }
    public Socket getSocket() {return socket;}

    public void setInputSteam(DataInputStream i) {input = i;}
    public void setOutputStream(DataOutputStream o) {output = o;}

    public DataOutputStream getOutputStream() {return output;}

    public User(Message userInfo)
    {
        //   ID | PWD | first | last
        // "1234/12345|Derrick|Hendrickson"

        set(userInfo);
    }

    public void set(Message userInfo)
    {
        this.userID = userInfo.get(0);
        this.password = userInfo.get(1);
        this.firstName = userInfo.get(2);
        this.lastName = userInfo.get(3);
    }

    public void set(String userID, String password, String firstName, String lastName)
    {
        this.userID = userID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(Message userInfo)
    {
        if(!userInfo.get(1).equals(""))
            this.firstName = userInfo.get(1);
        if(!userInfo.get(2).equals(""))
            this.lastName = userInfo.get(2);
        if(!userInfo.get(3).equals(""))
            this.password = userInfo.get(3);
    }

}
