package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.message.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Locale;

public class ControllerProfile {

    private User user;

    @FXML
    private Label label_userID;
    @FXML
    private Label label_firstName;
    @FXML
    private Label label_lastName;
    @FXML
    private Button button_updateProfile;

    @FXML
    private TextField textField_lastName;
    @FXML
    private TextField textField_firstName;
    @FXML
    private PasswordField passwordField_password;

    public void setUser(User user)
    {
        this.user = user;

        updateUserUI();
    }

    private void updateUserUI()
    {
        label_firstName.setText(user.getFirstName().toLowerCase());
        label_lastName.setText(user.getLastName().toLowerCase());
        label_userID.setText(user.getUserID().toLowerCase());

        textField_lastName.clear();
        textField_firstName.clear();
        passwordField_password.clear();

    }

    public void updateProfile(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        String firstName = textField_firstName.getText();
        String lastName = textField_lastName.getText();
        String password = passwordField_password.getText();

        // send msg to server using socket
        DataOutputStream output = new DataOutputStream((user.getSocket()).getOutputStream());

        String packet = "updateUser/" + user.getUserID() +"/"+ firstName +"/"+ lastName +"/"+ password +"/";

        output.writeUTF(packet);

        // retrieve back user information

        user.update(new Message(packet));

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
