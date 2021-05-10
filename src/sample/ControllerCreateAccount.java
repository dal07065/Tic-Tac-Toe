package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import message.*;
import sample.server.AppData;
import sample.server.User;

import java.io.IOException;

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
    public void createAccount(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        AppData.createUser(textField_userID.getText(), passwordField_password.getText(), textField_firstName.getText(), textField_lastName.getText());

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
