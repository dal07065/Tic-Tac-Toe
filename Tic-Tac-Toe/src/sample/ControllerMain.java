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

    /**
     * playOption: 1 for 1 player and 2 for 2 player
     */
    private static int playOption;
//    @FXML
//    void switch1PlayScene(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("play1.fxml"));
//        Parent root = loader.load();
//
//        Scene scenePlay = new Scene(root, 634, 446);
//        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());
//
//        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//        currentStage.setScene(scenePlay);
//        currentStage.sizeToScene();
//        currentStage.show();
//
//        // This allows to perform any actions as soon as the scene is loaded
//        // (For example, have the AI make the first move before the player does)
//        Controller1 playController = loader.getController();
//        playController.initializeAfterLoad();
//    }
//
//    /**
//     * Switches from Main scene to Play scene
//     * @param event
//     * @throws IOException
//     */
//    @FXML
//    void switch2PlayScene(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("play2.fxml"));
//        Parent root = loader.load();
//
//        Scene scenePlay = new Scene(root, 634, 446);
//        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());
//
//        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//        currentStage.setScene(scenePlay);
//        currentStage.sizeToScene();
//        currentStage.show();
//
//        // This allows to perform any actions as soon as the scene is loaded
//        // (For example, have the AI make the first move before the player does)
//        Controller2 playController = loader.getController();
//        playController.initializeAfterLoad();
//
//    }


    //If 2 Player button is selected, switch to scene that asks for the player who wants to start first
    @FXML
    void switchSelectRole(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("selectRole.fxml"));
        Scene scenePlay = new Scene(root);
        scenePlay.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();

        if(((Node) event.getSource()).getId().equalsIgnoreCase("button_1player"))
            playOption = 1;
        else
            playOption = 2;
    }


    /**
     * If player clicks player X button when program asks which player wants to start
     * first, then scene will switch to scene showing player X is player 1 and
     * player O is player 2
     * @param event
     * @throws IOException
     */
    @FXML
    void switchPlayScene(ActionEvent event) throws IOException {

        FXMLLoader loader;
        if(playOption == 1)
            loader = new FXMLLoader(getClass().getResource("play1.fxml"));
        else
            loader = new FXMLLoader(getClass().getResource("play2.fxml"));

        Parent root = loader.load();

        Scene scenePlay = new Scene(root, 634, 446);
        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();

        if(playOption == 1)
        {
            Controller1 controller = loader.getController();
            if(((Node) event.getSource()).getId().equalsIgnoreCase("button_O"))
            {
                controller.setCurrentPlayer('O');
                controller.setStartingPlayer('O');
            }
            else
            {
                controller.setCurrentPlayer('X');
                controller.setStartingPlayer('X');
            }
            controller.initializeAfterLoad();
        }
        else
        {
            Controller2 controller = loader.getController();
            if(((Node) event.getSource()).getId().equalsIgnoreCase("button_O"))
            {
                controller.setCurrentPlayer('O');
                controller.setStartingPlayer('O');
            }
            else
            {
                controller.setCurrentPlayer('X');
                controller.setStartingPlayer('X');
            }
            controller.initializeAfterLoad();
        }


    }

//    /* If user clicks player O button when program asks which player wants to start
//       first, then scene will switch to scene showing player O is player 1 and
//       player X is player 2
//    */
//    @FXML
//    void switch2PlaySceneO(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("play2.fxml"));
//        Parent root = loader.load();
//
//        Scene scenePlay = new Scene(root, 634, 446);
//        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());
//
//        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        currentStage.setScene(scenePlay);
//        currentStage.sizeToScene();
//        currentStage.show();
//
//        Controller2 controller = loader.getController();
//        controller.setCurrentPlayer('O');
//        controller.setStartingPlayer('O');
//    }
}
