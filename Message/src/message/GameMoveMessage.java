package message;

public class GameMoveMessage extends Message{

    private char currentPlayer;
    private String gameMove;
    private String gameID;


    public GameMoveMessage(String moveID, String gameID, char currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.gameMove = moveID;
        this.gameID = gameID;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public String getGameMove() {
        return gameMove;
    }

    public String getGameID(){return gameID;}
}
