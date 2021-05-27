package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.server.AppData;

import java.io.IOException;

public class ControllerSelectRole {


    /**
     * If player clicks player X button when program asks which player wants to start
     * first, then scene will switch to scene showing player X is player 1 and
     * player O is player 2
     * @param event
     * @throws IOException
     */
    @FXML
    void switchPlayScene(ActionEvent event) throws IOException, ClassNotFoundException {



        FXMLLoader loader = new FXMLLoader(getClass().getResource("design/ai.fxml"));

        Parent root = loader.load();

        Scene scenePlay = new Scene(root, 634, 446);
        scenePlay.getStylesheets().add(Main.class.getResource("design/Play.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();

        ControllerAI controller = loader.getController();
        if(((Node) event.getSource()).getId().equalsIgnoreCase("button_O"))
        {
            controller.setThisPlayer('O');
            controller.setCurrentPlayer('O');
//            controller.setStartingPlayer('O');
            AppData.startNewGameAI('X');
        }
        else
        {
            controller.setThisPlayer('X');
            controller.setCurrentPlayer('X');
//            controller.setStartingPlayer('X');
            AppData.startNewGameAI('O');
        }



        controller.initializeAfterLoad();


    }

}
