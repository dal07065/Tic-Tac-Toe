package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    private Button button_updateProfile;

    @FXML
    private TextField textField_userID;
    @FXML
    private TextField textField_lastName;
    @FXML
    private TextField textField_firstName;
    @FXML
    private PasswordField passwordField_password;

    private Stage currentStage;

    public void initialize(Stage currentStage)
    {
        updateUserUI();
        this.currentStage = currentStage;
    }

    private void updateUserUI()
    {
        label_userID.setText(AppData.user.getUserID().toLowerCase());
        label_firstName.setText(AppData.user.getFirstName().toLowerCase());
        label_lastName.setText(AppData.user.getLastName().toLowerCase());
        label_userID.setText(AppData.user.getUserID().toLowerCase());

        textField_userID.clear();
        textField_lastName.clear();
        textField_firstName.clear();
        passwordField_password.clear();

    }

    public void updateProfile(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        String userID = textField_userID.getText();
        String firstName = textField_firstName.getText();
        String lastName = textField_lastName.getText();
        String password = passwordField_password.getText();

        boolean success = AppData.updateUser(userID, password, firstName, lastName);

        if(success)
        {
            Main.displayAlert("Update Successful", "Your profile has been successfully updated");

            updateUserUI();
        }
        else
        {
            Main.displayAlert("Update Failed", "Current UserID is already taken. Try another one.");
        }


    }

    public void deleteUser(ActionEvent actionEvent) throws IOException {

        boolean response = Main.displayQuestionAlert("Delete User", "Are you sure you wish to delete your account?");

        if(response == true)
        {
            AppData.deleteUser();

            // logOut

            AppData.disconnectToServer();

            // Load Log in scene

            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("design/login.fxml"));

            Parent root = loader.load();
            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            currentStage.setTitle("Tic Tac Toe : Log In");

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setResizable(false);
            scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());
            currentStage.show();

            // Reset to new user, new socket

            AppData.connectToServer();
        }

    }
}
