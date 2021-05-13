package message;

public class GameMoveMessage extends Message{

    private char currentPlayer;
    private String gameMove;

    public GameMoveMessage(String gameMove, char currentPlayer)
    {
        this.currentPlayer = currentPlayer;
        this.gameMove = gameMove;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public String getGameMove() {
        return gameMove;
    }
}
