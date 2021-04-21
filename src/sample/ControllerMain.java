package sample;

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
import sample.message.Message;

import java.io.*;
import java.net.Socket;

public class ControllerMain {

    private User user;

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

        // send server a message
        String userID = textField_userID.getText();
        String password = passwordField_password.getText();
        (user.getOutputStream()).writeUTF("signin/" + userID + "/" + password + "/");

        // retrieve back user information

        Message userProfile = new Message((user.getInputStream()).readUTF());

        if(userProfile.getType().equals("error"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(userProfile.get(0));
            alert.setHeaderText(null);
            alert.setContentText("Please Try Again.");

            alert.showAndWait();

            textField_userID.clear();
            passwordField_password.clear();
        }
        else
        {
            user.set(userProfile);

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

            ControllerMain controllerMain = loader.getController();
            controllerMain.setUser(user);
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

        ControllerCreateAccount controllerCreateAccount = loader.getController();
        controllerCreateAccount.setUser(user);

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

        ControllerProfile controllerProfile = loader.getController();

        controllerProfile.setUser(user);

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

    public void setUser(User user)
    {
        this.user = user;
    }

    public void logOut(ActionEvent actionEvent) throws IOException {

        // close current user socket

        user.getSocket().close();

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

        ControllerMain controllerMain = loader.getController();

        User user = new User();
        user.setSocket(new Socket("localhost", 8000));

        controllerMain.setUser(user);
    }
//
//    public void setSocket(Socket socket) throws IOException {
//
//        this.socket = socket;
//        output = new DataOutputStream(socket.getOutputStream());
//        input = new DataInputStream(socket.getInputStream());
//    }

    /*
    public void enterCode(ActionEvent event) {

        try
        {
            // Create a socket to connect to the server
            int password = Integer.parseInt(textField_code.getText());

            DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());

            oStream.writeInt(password);

//            Socket socket = new Socket("192.168.1.68", Integer.parseInt(textField_code.getText()));
//            Socket socket = new Socket("130.254.204.36", 8000);
//            Socket socket = new Socket("drake.Armstrong.edu", 8000);
//            Create an input stream to receive data from the server
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());            // Create an output stream to send data to the server

            int player = inputStream.readInt();

            char currentPlayer = 'P';

            if(player == 1)
                currentPlayer = 'X';
            else if (player == 2)
                currentPlayer = 'O';

            FXMLLoader loader = new FXMLLoader(getClass().getResource("design/playLan.fxml"));

            Parent root = loader.load();

            Scene scenePlay = new Scene(root, 634, 446);
            scenePlay.getStylesheets().add(Main.class.getResource("design/Play.css").toExternalForm());

            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

            ControllerLan controller = loader.getController();
            controller.setSocket(socket);

            currentStage.setScene(scenePlay);
            currentStage.sizeToScene();
            currentStage.show();

            controller.setThisPlayer(currentPlayer);
            controller.setCurrentPlayer('X');

            controller.initializeAfterLoad();

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

     */


}
