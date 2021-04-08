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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;


public class ControllerLan implements Runnable{

    Board board = new Board(3, 3);

    private char thisPlayer;
    private static char currentPlayer;

    private int player1score = 0;
    private int player2score = 0;

    private boolean winStatus = false;

    private int count = 0;

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    private boolean playerTurn = true;

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
    private Label label_player1;
    @FXML
    private Label label_player2;
    @FXML
    private ImageView imageView_player1;
    @FXML
    private ImageView imageView_player2;

    public void setSocket(Socket c) throws IOException {
        this.socket = c;
        this.inputStream = new DataInputStream(c.getInputStream());
        this.outputStream = new DataOutputStream(c.getOutputStream());
    }

    public void initializeAfterLoad() throws IOException {
        if(thisPlayer == 'X')
        {
            label_player1.setText("x");
            label_player2.setText("o");

            // Player X goes first
        }
        else
        {
            label_player1.setText("o");
            label_player2.setText("x");
            switchPlayerIconImages();
            // Player O goes second
            // - wait for player X ( all button.setMouseTransparent(false) )

            listenForNextMove();

        }
    }

    void disableAllButtons()
    {
        for(Node slot: gridPane.getChildren())
            slot.setMouseTransparent(true);
    }
    void enableAllButtons()
    {
        for(Node slot: gridPane.getChildren())
            slot.setMouseTransparent(false);
    }

    /**
     * When a user clicks on any slot(button) on the board
     * @param event the button event
     */
    @FXML
    void buttonClicked(ActionEvent event) throws IOException {

        String id = ((Button)(event.getSource())).getId();
        System.out.println(id);
        System.out.println(currentPlayer + " just played a turn.");

        // play a turn
        play(currentPlayer, id);

        outputStream.writeInt(findNumberFromID(id));

        if(checkWinner())
            outputStream.writeInt(0);
        else
            listenForNextMove();

    }

    private void listenForNextMove() throws IOException {

        int move = inputStream.readInt();

        System.out.println(currentPlayer + " just played a turn.");
        play(currentPlayer, findIDFromNumber(move));

        if(checkWinner())
            outputStream.writeInt(0);
    }


    private String findIDFromNumber(int move) {

        if(move == 7)
            return("button_topLeft");

        if(move == 8)
            return("button_topMid");

        if(move == 9)
            return("button_topRight");

        if(move == 4)
            return("button_midLeft");

        if(move == 5)
            return("button_midMid");

        if(move == 6)
            return("button_midRight");

        if(move == 1)
            return("button_botLeft");

        if(move == 2)
            return("button_botMid");

        if(move == 3)
            return("button_botRight");

        return null;
    }

    private int findNumberFromID(String id) {
        if(id.equalsIgnoreCase("button_topLeft"))
            return(7);

        if(id.equalsIgnoreCase("button_topMid"))
            return(8);

        if(id.equalsIgnoreCase("button_topRight"))
            return(9);

        if(id.equalsIgnoreCase("button_midLeft"))
            return(4);

        if(id.equalsIgnoreCase("button_midMid"))
            return(5);

        if(id.equalsIgnoreCase("button_midRight"))
            return(6);

        if(id.equalsIgnoreCase("button_botLeft"))
            return(1);

        if(id.equalsIgnoreCase("button_botMid"))
            return(2);

        if(id.equalsIgnoreCase("button_botRight"))
            return(3);

        return 0;
    }

    /**
     * Clears the board and resets back to start formation (Does not affect the scoreboard)
     * @param actionEvent the button event
     */
    @FXML
    public void resetClicked(ActionEvent actionEvent) throws IOException {

        // ask the other player to reset?

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for(Node slot: gridPane.getChildren())
            {
                slot.setStyle("-fx-background-color: none;");
                slot.setMouseTransparent(false);
            }

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
            switchPlayerIconImages();

            winStatus = false;

            initializeAfterLoad();
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }



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

        Parent root = FXMLLoader.load(getClass().getResource("design/main.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("design/Main.css").toExternalForm());

        Stage currentStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        currentStage.setTitle("Tic Tac Toe");
        currentStage.setScene(scene);
        currentStage.sizeToScene();
        currentStage.setResizable(false);
        currentStage.show();

        socket.close();

    }

    /**
     * Plays a turn and sets a move on the board and goes to next player
     * @param XO the current player character as in 'X' or 'O'
     * @param id the id of the slot on the board (i.e. "button_topLeft)
     */
    @FXML
    private void play(char XO, String id) throws IOException {
        Button button = findButton(id);

        if(XO == 'X')
        {
            button.setStyle("-fx-background-image:url('/sample/resources/x_viking_small.png');");

            System.out.println(button.getId() + " was clicked.");

            button.setMouseTransparent(true);

            board.setMove(id, XO);

            currentPlayer = 'O';

        }
        else if (XO == 'O')
        {
            button.setStyle("-fx-background-image:url('/sample/resources/o_viking_small.png');");

            System.out.println(button.getId() + " was clicked.");

            button.setMouseTransparent(true);

            board.setMove(id, currentPlayer);

            currentPlayer = 'X';

        }

        switchPlayerIconImages();

        count++;
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

    /**
     * Checks the board if anyone won and if so, determine who is the winner
     *
     */
    private boolean checkWinner() throws IOException {
        char winner = board.boardStatus();

        if(winner != '-')
        {
            System.out.println("The winner is : " + winner);

            if(winner == thisPlayer)
            {
                showWinner1();
            }
            else
            {
                showWinner2();
            }

            // Disable all slots on the board. Game is over mate.
            disableAllButtons();

            winStatus = true;
        }
        return winStatus;
    }

    //Program will show that player 1 has won the round
    public void showWinner1()
    {
        label_winner1.setText("winner!");
        imageView_1.setVisible(true);
        player1score++;
        label_player1score.setText("" + player1score);
    }

    //Program will show that player 2 has won the round
    public void showWinner2()
    {
        label_winner2.setText("winner!");
        imageView_2.setVisible(true);
        player2score++;
        label_player2score.setText("" + player2score);
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


    @Override
    public void run() {

    }


}
