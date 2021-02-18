package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Spot;

import java.io.IOException;
import java.util.HashMap;


public class Controller {

    private boolean currentPlayer = true;

    private int count = 0;

    @FXML
    private Button button_topLeft;
    @FXML
    private Button button_topMid;
    @FXML
    private Button button_topRight;
    @FXML
    private Button button_midLeft;
    @FXML
    private Button button_midMid;
    @FXML
    private Button button_midRight;
    @FXML
    private Button button_botLeft;
    @FXML
    private Button button_botMid;
    @FXML
    private Button button_botRight;
    @FXML
    private Button button_reset;


    @FXML
    private Label label_player;

    @FXML
    private Label label_playerHeader;


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

        String id = ((Button)(event.getSource())).getId();

        System.out.println(id);
        // if event's button was already clicked, skip all these steps

        if(((Button)(event.getSource())).isDisabled())
        {
            return;
        }

        // based on current player's id, display an X or O image
        if(currentPlayer)
        {
            ((Button)(event.getSource())).setStyle("-fx-background-image:url('/sample/resources/o_viking_small.png');");

            System.out.println(((Button)(event.getSource())).getId() + " was clicked.");

            label_player.setText("x");

            ((Button)(event.getSource())).setMouseTransparent(true);

            count++;
        }
        else
        {
            ((Button)(event.getSource())).setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");

            System.out.println(((Button)(event.getSource())).getId() + " was clicked.");

            label_player.setText("o");

            ((Button)(event.getSource())).setMouseTransparent(true);

            count++;

        }

        currentPlayer = !currentPlayer;


        //check if anyone won
        boardStatus();
    }

    private void boardStatus()
    {
        if(count == 9)
            button_reset.setVisible(true);

    }


    public void resetClicked(ActionEvent actionEvent) {

        button_topLeft.setStyle("-fx-background-color: none;");
        button_topMid.setStyle("-fx-background-color: none;");
        button_topRight.setStyle("-fx-background-color: none;");
        button_midLeft.setStyle("-fx-background-color: none;");
        button_midMid.setStyle("-fx-background-color: none;");
        button_midRight.setStyle("-fx-background-color: none;");
        button_botLeft.setStyle("-fx-background-color: none;");
        button_botMid.setStyle("-fx-background-color: none;");
        button_botRight.setStyle("-fx-background-color: none;");

        button_topLeft.setMouseTransparent(false);
        button_topMid.setMouseTransparent(false);
        button_topRight.setMouseTransparent(false);
        button_midLeft.setMouseTransparent(false);
        button_midMid.setMouseTransparent(false);
        button_midRight.setMouseTransparent(false);
        button_botLeft.setMouseTransparent(false);
        button_botMid.setMouseTransparent(false);
        button_botRight.setMouseTransparent(false);

        currentPlayer = true;
        label_player.setText("o");
        count = 0;
        button_reset.setVisible(false);

        // reset all button backgrounds to transparent
        // set all buttons setMouseTransparent(false)
        // set player back to O

    }


    public void resetScoreClicked(ActionEvent actionEvent) {
    }

    public void buttonBackClicked(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.setTitle("Tic Tac Toe");
        currentStage.setScene(scene);
        currentStage.sizeToScene();
        currentStage.setResizable(false);
        currentStage.show();

    }
}
