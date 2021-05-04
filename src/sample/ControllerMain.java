package sample;

import message.ConnectGameReceivedMessage;
import message.Packet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.server.AppData;

import java.io.IOException;

public class ControllerMain {

    /**
     * playOption: 1 for 1 player and 2 for 2 player
     */
    private static int playOption;

    @FXML
    public TextField textField_lastName;

    @FXML
    public TextField textField_firstName;

    @FXML
    private TextField textField_userID;

    @FXML
    private PasswordField passwordField_password;

    @FXML
    private TextField textField_code;

    @FXML
    void logToServer(ActionEvent actionEvent) throws Exception {

        String userID = textField_userID.getText();
        String password = passwordField_password.getText();

        if(!AppData.login(userID,password))
        {
            displayAlert("Wrong Log In Information", "Please Try Again");


            textField_userID.clear();
            passwordField_password.clear();
        }
        else
        {
            // load Main

            FXMLLoader loader = new FXMLLoader(getClass().getResource("design/main.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            currentStage.setTitle("Tic Tac Toe");

            currentStage.setScene(scene);
            currentStage.setResizable(false);
            currentStage.show();
        }
    }

    @FXML
    public void createAccountMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/createAccount.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage newStage = new Stage();
        newStage.setTitle("Tic Tac Toe : Create an Account");

        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();

    }


    @FXML
    public void loadProfile(ActionEvent actionEvent) throws IOException {

        // Show profile scene

        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/profile.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage newStage = new Stage();
        newStage.setTitle("Tic Tac Toe");

        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();

        ControllerProfile controller = loader.getController();
        controller.initialize();

    }

    @FXML
    void switchLan(ActionEvent actionEvent) throws IOException {
        // open a scene for "Enter code: "

        Parent root = FXMLLoader.load(getClass().getResource("design/lan.fxml"));
        Scene scenePlay = new Scene(root);
        scenePlay.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();
    }

    @FXML
    void switchOffline(ActionEvent actionEvent) throws IOException {
        // open a scene with 1 player or 2 player button options

        Parent root = FXMLLoader.load(getClass().getResource("design/offline.fxml"));
        Scene scenePlay = new Scene(root);
        scenePlay.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        // close current user socket

        AppData.disconnectToServer();

        // Load Log in scene

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


    public void enterCode(ActionEvent event) {

        try
        {
            // Create a socket to connect to the server
            int password = Integer.parseInt(textField_code.getText());

            ConnectGameReceivedMessage msg = (ConnectGameReceivedMessage) (AppData.connectToGame(password)).getMessage();

            if(msg.getStatus().equals("occupied"))
            {
                displayAlert("Occupied Game", "The game is currently occupied. Please try again later.");
                textField_code.clear();
            }
            else
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("design/playLan.fxml"));

                Parent root = loader.load();

                Scene scenePlay = new Scene(root, 634, 446);
                scenePlay.getStylesheets().add(Main.class.getResource("design/Play.css").toExternalForm());

                Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

                ControllerLan controller = loader.getController();

                currentStage.setScene(scenePlay);
                currentStage.sizeToScene();
                currentStage.show();

                // wait for another player

                char currentPlayer = msg.getPlayer();

                controller.setThisPlayer(currentPlayer);
                controller.setCurrentPlayer('X');

                controller.initializeAfterLoad();
            }
//            Socket socket = new Socket("192.168.1.68", Integer.parseInt(textField_code.getText()));
//            Socket socket = new Socket("130.254.204.36", 8000);
//            Socket socket = new Socket("drake.Armstrong.edu", 8000);
//            Create an input stream to receive data from the server


        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
    public void displayAlert(String Title, String Content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(Content);
        alert.showAndWait();
    }

}
