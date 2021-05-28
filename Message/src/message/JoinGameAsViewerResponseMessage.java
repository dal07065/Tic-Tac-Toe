package message;

public class JoinGameAsViewerResponseMessage extends Message{

    private boolean success;
    private String gameID;
    private char currentPlayer;
    private String player1;
    private String player2;

    public JoinGameAsViewerResponseMessage(boolean success, String gameID, char currentPlayer, String userID1, String userID2)
    {
        this.success = success;
        this.gameID = gameID;
        this.currentPlayer = currentPlayer;
        this.player1 = userID1;
        this.player2 = userID2;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getGameID() {
        return gameID;
    }

    public char getCurrentPlayer() {return currentPlayer;}

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }
}
