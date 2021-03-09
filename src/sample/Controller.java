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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Spot;

import java.io.IOException;
import java.util.HashMap;


public class Controller {

    Board board = new Board(3, 3);

    private char currentPlayer = 'X';

    private int player1score = 0;
    private int player2score = 0;

    private boolean winStatus = false;

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
    private Button button_resetBoard;

    @FXML
    private GridPane gridPane;
    @FXML
    private Label label_winner1;
    @FXML
    private Label label_winner2;
    @FXML
    private ImageView imageView_1;
    @FXML
    private ImageView imageView_2;
    @FXML
    private Label label_player1score;
    @FXML
    private Label label_player2score;
    @FXML
    private ImageView imageView_player1;
    @FXML
    private ImageView imageView_player2;

    @FXML
    void switch1PlayScene(ActionEvent event) throws IOException {

        // switch to 1 player scene
    }

    @FXML
    void switch2PlayScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("play2.fxml"));
        Scene scenePlay = new Scene(root, 634, 446);
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

        System.out.println(currentPlayer + " just played a turn.");

        if(((Button)(event.getSource())).isDisabled())
        {
            return;
        }

        // based on current player's id, display an X or O image
        if(currentPlayer == 'X')
        {
            ((Button)(event.getSource())).setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");

            System.out.println(((Button)(event.getSource())).getId() + " was clicked.");

            ((Button)(event.getSource())).setMouseTransparent(true);

            board.setMove(id, currentPlayer);

            currentPlayer = 'O';

            imageView_player1.setVisible(false);
            imageView_player2.setVisible(true);

        }
        else
        {
            ((Button)(event.getSource())).setStyle("-fx-background-image:url('/sample/resources/o_viking_small.png');");

            System.out.println(((Button)(event.getSource())).getId() + " was clicked.");

            ((Button)(event.getSource())).setMouseTransparent(true);

            board.setMove(id, currentPlayer);

            currentPlayer = 'X';

            imageView_player1.setVisible(true);
            imageView_player2.setVisible(false);

        }
        count++;

        //check if anyone won
        char winner = board.boardStatus();
        if(winner != '-')
        {
            System.out.println("The winner is : " + winner);
            button_resetBoard.setVisible(true);
            // display winner pop icon on the side of the player who won
            // display winner label title at the top

            if(winner == 'X')
            {
                label_winner1.setText("winner!");
                imageView_1.setVisible(true);
                player1score++;
                label_player1score.setText("" + player1score);
            }
            else if(winner == 'O')
            {
                label_winner2.setText("winner!");
                imageView_2.setVisible(true);
                player2score++;
                label_player2score.setText("" + player2score);
            }

            for(Node slot: gridPane.getChildren())
            {
                slot.setMouseTransparent(true);
            }

            winStatus = true;
        }
        else
        {
            if(count == 9)
                button_resetBoard.setVisible(true);
        }
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

        currentPlayer = 'X';
        count = 0;

        // hide the reset board button
//        button_resetBoard.setVisible(false);

        // reset "winner!" back to being transparent
        label_winner1.setText("");
        label_winner2.setText("");

        imageView_1.setVisible(false);
        imageView_2.setVisible(false);

        // reset the internal board to all '-' (empty)
        board.resetBoard();

        // reset current player image icon
        imageView_player1.setVisible(true);
        imageView_player2.setVisible(false);

        // reset all button backgrounds to transparent
        // set all buttons setMouseTransparent(false)
        // set player back to O

    }


    public void resetScoreClicked(ActionEvent actionEvent) {

        label_player1score.setText("0");
        label_player2score.setText("0");

    }

    public void buttonBackClicked(ActionEvent actionEvent) throws IOException {

        // show a popup that says are you sure you would like to go back to main menu and discontinue this game?

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
