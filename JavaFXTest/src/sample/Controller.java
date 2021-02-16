package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {

    private boolean currentPlayer = true;

    @FXML
    private Button button_Play;

    @FXML
    private Label  label_player;

    @FXML
    void switchPlayScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
        Scene scenePlay = new Scene(root, 432, 275);
        scenePlay.getStylesheets().add(Main.class.getResource("Play.css").toExternalForm());

        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();

        currentStage.setScene(scenePlay);
        currentStage.sizeToScene();
        currentStage.show();


    }

    @FXML
    void buttonClicked(ActionEvent event) {
        System.out.println(((Button)(event.getSource())).getId());
        // if event's button was already clicked, skip all these steps

        // based on current player's id, display an X or O image
        if(currentPlayer)
        {
            ((Button)(event.getSource())).setStyle("-fx-background-image:url('/sample/resources/o_viking_small.png');");

            System.out.println(((Button)(event.getSource())).getId() + " was clicked.");

            label_player.setText("2");
        }
        else
        {
            ((Button)(event.getSource())).setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");

            System.out.println(((Button)(event.getSource())).getId() + " was clicked.");

            label_player.setText("1");
        }

        currentPlayer = !currentPlayer;
        // based on current player's id, change label_player to 2 or back to 1
        // if a matching row/column/diagonal exists, change label_winner to current player
    }

}
