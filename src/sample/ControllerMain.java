package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ControllerMain {

    /**
     * playOption: 1 for 1 player and 2 for 2 player
     */
    private static int playOption;

    @FXML
    private TextField textField_code;

    @FXML
    void switchLan(ActionEvent event) throws IOException {
        // open a scene for "Enter code: "

        Parent root = FXMLLoader.load(getClass().getResource("design/lan.fxml"));
        Scene scenePlay = new Scene(root);
        scenePlay.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();
    }

    @FXML
    void switchOffline(ActionEvent event) throws IOException {
        // open a scene with 1 player or 2 player button options

        Parent root = FXMLLoader.load(getClass().getResource("design/offline.fxml"));
        Scene scenePlay = new Scene(root);
        scenePlay.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();
    }

    public void enterCode(ActionEvent actionEvent) {

        try
        {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", Integer.parseInt(textField_code.getText()));

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

            Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

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
}
