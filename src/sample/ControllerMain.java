package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMain {

    @FXML
    void switch1PlayScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("play1.fxml"));
        Parent root = loader.load();

        Scene scenePlay = new Scene(root, 634, 446);
        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();

        // This allows to perform any actions as soon as the scene is loaded
        // (For example, have the AI make the first move before the player does)
        Controller1 playController = loader.getController();
        playController.initializeAfterLoad();
    }

    /**
     * Switches from Main scene to Play scene
     * @param event
     * @throws IOException
     */
    @FXML
    void switch2PlayScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("play2.fxml"));
        Parent root = loader.load();

        Scene scenePlay = new Scene(root, 634, 446);
        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();

        // This allows to perform any actions as soon as the scene is loaded
        // (For example, have the AI make the first move before the player does)
        Controller2 playController = loader.getController();
        playController.initializeAfterLoad();

    }
}
