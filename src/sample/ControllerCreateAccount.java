package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.message.Message;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ControllerCreateAccount {

    private User user;

    @FXML
    private Button button_updateProfile;
    @FXML
    private TextField textField_lastName;
    @FXML
    private TextField textField_firstName;
    @FXML
    private PasswordField passwordField_password;
    @FXML
    private TextField textField_userID;

    @FXML
    public void createAccount(ActionEvent actionEvent) throws IOException {

        String packet = "newUser/" + textField_userID.getText() + "/" + passwordField_password.getText() + "/" + textField_firstName.getText() + "/" + textField_lastName.getText() + "/";

        DataOutputStream output = user.getOutputStream();
        output.flush();
        (output).writeUTF(packet);

        user.set(new Message(packet));

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

    public void setUser(User user)
    {
        this.user = user;
    }


}
