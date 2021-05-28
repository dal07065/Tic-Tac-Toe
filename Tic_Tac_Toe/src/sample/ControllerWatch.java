package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.server.AppData;

import message.*;

import java.io.IOException;
import java.util.Optional;

import static java.lang.Thread.sleep;


public class ControllerWatch implements Runnable {

    private char thisPlayer;
    private static char currentPlayer;

    private int player1score = 0;
    private int player2score = 0;

    private boolean winStatus = false;

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
    private Label label_player1;
    @FXML
    private Label label_player2;
    @FXML
    private ImageView imageView_player1;
    @FXML
    private ImageView imageView_player2;
    @FXML
    private Label label_player1Name;
    @FXML
    private Label label_player2Name;

    public void initializeAfterLoad(JoinGameAsViewerResponseMessage msg) {

        Thread updateGameStream = new Thread(this);
        updateGameStream.start();
        disableAllButtons();

        char[][] board = msg.getBoard();
        Button button;

        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                button = findButton(findIDFromNumber(i,j));
                if(board[i][j] == 'X')
                {
                    button.setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");
                }
                else if(board[i][j] == 'O')
                {
                    button.setStyle("-fx-background-image:url('/sample/resources/O_viking_small.png');");
                }
            }
        }

        label_player1Name.setText(msg.getPlayer1());
        label_player2Name.setText(msg.getPlayer2());

    }

    private String findIDFromNumber(int row, int col) {

        if(row == 0)
        {
            if(col == 0)
                return "button_topLeft";
            else if(col == 1)
                return "button_topMid";
            else if(col == 2)
                return "button_topRight";
        }

        if(row == 1)
        {
            if(col == 0)
                return "button_midLeft";
            else if(col == 1)
                return "button_midMid";
            else if(col == 2)
                return "button_midRight";
        }

        if(row == 2)
        {
            if(col == 0)
                return "button_botLeft";
            else if(col == 1)
                return "button_botMid";
            else if(col == 2)
                return "button_botRight";
        }

        return null;
    }

    void disableAllButtons()
    {
        for(Node slot: gridPane.getChildren())
            slot.setMouseTransparent(true);
    }


    /**
     * Goes back to Main scene when back button is pressed from Play scene
     * @param actionEvent the button event
     * @throws IOException if fxml file cannot be loaded
     */
    @FXML
    public void buttonBackClicked(ActionEvent actionEvent) throws IOException {

        // show a popup that says are you sure you would like to go back to main menu and discontinue this game?

        AppData.quitCurrentGame();

        Parent root = FXMLLoader.load(getClass().getResource("design/main.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

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
    private void play(char XO, String id) throws IOException, ClassNotFoundException {
        Button button = findButton(id);

        if(XO == 'X')
        {
            button.setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");

            System.out.println(button.getId() + " was clicked.");

            button.setMouseTransparent(true);


            currentPlayer = 'O';

        }
        else if (XO == 'O')
        {
            button.setStyle("-fx-background-image:url('/sample/resources/o_viking_small.png');");

            System.out.println(button.getId() + " was clicked.");

            button.setMouseTransparent(true);

            currentPlayer = 'X';

        }

        switchPlayerIconImages();

    }

    private void switchPlayerIconImages()
    {
        if(imageView_player1.isVisible()) {
            imageView_player1.setVisible(false);
            imageView_player2.setVisible(true);
        }
        else
        {

            imageView_player1.setVisible(true);
            imageView_player2.setVisible(false);
        }
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

    //Program will show that player 1 has won the round
    public void showWinnerThisPlayer()
    {
        label_winner1.setText("winner!");
        imageView_1.setVisible(true);
        player1score++;
        label_player1score.setText("" + player1score);
        disableAllButtons();
        winStatus = true;
        System.out.println("Player 1 won!");
    }

    //Program will show that player 2 has won the round
    public void showWinnerOtherPlayer()
    {
        label_winner2.setText("winner!");
        imageView_2.setVisible(true);
        player2score++;
        label_player2score.setText("" + player2score);
        disableAllButtons();
        winStatus = true;
        System.out.println("Player 2 won!");
    }

    public void setThisPlayer(char player) {
        this.thisPlayer = player;
    }

    //Sets up and tells program who the current player is
    public void setCurrentPlayer(char player)
    {
        this.currentPlayer = player;
    }

    //Sets up and tells program who player 1 is
    public void setStartingPlayer(char startPlayer)
    {
//        this.startingPlayer = startPlayer;
    }

    private void resetBoard() throws IOException, ClassNotFoundException {
        for(Node slot: gridPane.getChildren())
        {
            slot.setStyle("-fx-background-color: none;");
            slot.setMouseTransparent(false);
        }

        currentPlayer = 'X';

        // reset "winner!" and small viking head image back to being transparent
        label_winner1.setText("");
        label_winner2.setText("");
        imageView_1.setVisible(false);
        imageView_2.setVisible(false);

        // reset current player image icon
        switchPlayerIconImages();

    }

    @Override
    public void run() {

        boolean gameContinue = true;

        while(gameContinue)
        {
            Message msg = null;
            try {
                msg = AppData.connection.getPacket().getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(msg instanceof ResetBoardResponseMessage)
            {
                try {
                    resetBoard();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(msg instanceof GameMoveResponseMessage)
            {
                if(!((GameMoveResponseMessage) msg).isContinueGame())
                {
                    Main.displayAlert("Shoot!", "One of the players quit the game. Waiting for a player to join...");
                    gameContinue = false;
                }
                else
                {
                    System.out.println(((GameMoveResponseMessage) msg).getGameMove());
                    System.out.println(currentPlayer + " just played a turn.");

                    // play a turn
                    try {
                        play(currentPlayer, ((GameMoveResponseMessage) msg).getGameMove());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if(((GameMoveResponseMessage) msg).checkWinner() == '-');
                    else if(((GameMoveResponseMessage) msg).checkWinner() == 'X')
                        showWinnerThisPlayer();
                    else if (((GameMoveResponseMessage) msg).checkWinner() == 'O')
                        showWinnerOtherPlayer();

                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public void buttonClicked(ActionEvent actionEvent) {
    }
}
