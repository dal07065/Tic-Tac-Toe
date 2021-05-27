package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import message.Message;
import message.Packet;
import message.UserInfoMessage;
import sample.server.AppData;

import java.io.IOException;

public class ControllerProfile {

    @FXML
    private Label label_userID;
    @FXML
    private Label label_firstName;
    @FXML
    private Label label_lastName;
    @FXML
    private Label label_wins;
    @FXML
    private Label label_loses;
    @FXML
    private Label label_ties;
    @FXML
    private Button button_updateProfile;


    @FXML
    private TextField textField_lastName;
    @FXML
    private TextField textField_firstName;
    @FXML
    private PasswordField passwordField_password;

    public void initialize()
    {
        updateUserUI();
    }

    private void updateUserUI()
    {
        label_firstName.setText(AppData.user.getFirstName().toLowerCase());
        label_lastName.setText(AppData.user.getLastName().toLowerCase());
        label_userID.setText(AppData.user.getUserID().toLowerCase());
        label_wins.setText(Integer.toString(AppData.user.getWins()));
        label_loses.setText(Integer.toString(AppData.user.getLoses()));
        label_ties.setText(Integer.toString(AppData.user.getTies()));

        textField_lastName.clear();
        textField_firstName.clear();
        passwordField_password.clear();

    }

    public void updateProfile(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        String userID = AppData.user.getUserID();
        String firstName = textField_firstName.getText();
        String lastName = textField_lastName.getText();
        String password = passwordField_password.getText();
        int wins = AppData.user.getWins();
        int loses = AppData.user.getLoses();
        int ties = AppData.user.getTies();


        AppData.updateUser(userID, password, firstName, lastName, wins, loses, ties);

        // Notify the user that it has been successfully updated

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Successful");
        alert.setHeaderText(null);
        alert.setContentText("Your profile has been successfully updated");

        alert.showAndWait();

        // update the ui

        updateUserUI();

    }
}
