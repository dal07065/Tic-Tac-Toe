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
import sample.AI.Minimax;
import sample.Spot;

import java.io.IOException;
import java.util.HashMap;


public class Controller1 {

    Board board = new Board(3, 3);

    private static char currentPlayer = 'X';

    private int player1score = 0;
    private int player2score = 0;

    private boolean winStatus = false;

    private int count = 0;

    private static Minimax ai = new Minimax();

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

    public void initializeAfterLoad()
    {
        String buttonID = ai.findBestMove(board, currentPlayer, true);
        System.out.println(buttonID);
        System.out.println(currentPlayer + " just played a turn.");
        play(currentPlayer, buttonID);
    }

    /**
     * When a user clicks on any slot(button) on the board
     * @param event the button event
     */
    @FXML
    void buttonClicked(ActionEvent event) {

        String id = ((Button)(event.getSource())).getId();
        System.out.println(id);
        System.out.println(currentPlayer + " just played a turn.");

        // play a turn
        play(currentPlayer, id);

        if(!winStatus) {
            String buttonID = ai.findBestMove(board, currentPlayer, false);
            System.out.println(buttonID);
            System.out.println(currentPlayer + " just played a turn.");
            play(currentPlayer, buttonID);
        }

    }

    /**
     * Clears the board and resets back to start formation (Does not affect the scoreboard)
     * @param actionEvent the button event
     */
    @FXML
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

        // reset "winner!" and small viking head image back to being transparent
        label_winner1.setText("");
        label_winner2.setText("");
        imageView_1.setVisible(false);
        imageView_2.setVisible(false);

        // reset the internal board to all '-' (empty)
        board.resetBoard();

        // reset current player image icon
        imageView_player1.setVisible(true);
        imageView_player2.setVisible(false);

        winStatus = false;

        String buttonID = ai.findBestMove(board, currentPlayer, false);
        System.out.println(buttonID);
        System.out.println(currentPlayer + " just played a turn.");
        play(currentPlayer, buttonID);

    }


    /**
     * Resets the scoreboard to all 0's
     * @param actionEvent the button event
     */
    @FXML
    public void resetScoreClicked(ActionEvent actionEvent)
    {
        label_player1score.setText("0");
        label_player2score.setText("0");
    }

    /**
     * Goes back to Main scene when back button is pressed from Play scene
     * @param actionEvent the button event
     * @throws IOException if fxml file cannot be loaded
     */
    @FXML
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

    /**
     * Plays a turn and sets a move on the board and goes to next player
     * @param XO the current player character as in 'X' or 'O'
     * @param id the id of the slot on the board (i.e. "button_topLeft)
     */
    @FXML
    private void play(char XO, String id)
    {
        Button button = findButton(id);

        if(XO == 'X')
        {
            button.setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");

            System.out.println(button.getId() + " was clicked.");

            button.setMouseTransparent(true);

            board.setMove(id, currentPlayer);

            currentPlayer = 'O';

            imageView_player1.setVisible(false);
            imageView_player2.setVisible(true);
        }
        else if (XO == 'O')
        {
            button.setStyle("-fx-background-image:url('/sample/resources/o_viking_small.png');");

            System.out.println(button.getId() + " was clicked.");

            button.setMouseTransparent(true);

            board.setMove(id, currentPlayer);

            currentPlayer = 'X';

            imageView_player1.setVisible(true);
            imageView_player2.setVisible(false);
        }

        count++;

        checkWinner();
    }

    /**
     * Given a button id (i.e. "button_topLeft"), returns a matching Button object
     * @param id the id of the slot on the board (i.e. "button_topLeft)
     */
    @FXML
    private Button findButton(String id)
    {
        for(Node slot: gridPane.getChildren())
        {
            if(slot.getId().equalsIgnoreCase(id))
                return (Button)slot;
        }
        return null;
    }

    /**
     * Checks the board if anyone won and if so, determine who is the winner
     *
     */
    private void checkWinner()
    {
        char winner = board.boardStatus();

        if(winner != '-')
        {
            System.out.println("The winner is : " + winner);

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

            // Disable all slots on the board. Game is over mate.
            for(Node slot: gridPane.getChildren())
                slot.setMouseTransparent(true);

            winStatus = true;
        }
    }


}
