package message;

public class GameMoveResponseMessage extends Message{
    char whoWon;
    private String gameMoveMade;
    char playerThatMoved;
    boolean continueGame;

    public GameMoveResponseMessage(char whoWon, String gameMoveMade, char playerThatMoved, boolean continueGame)
    {
        this.whoWon = whoWon;
        this.gameMoveMade = gameMoveMade;
        this.playerThatMoved = playerThatMoved;
        this.continueGame = continueGame;
    }

    public String getGameMove()
    {
        return gameMoveMade;
    }

    public char checkWinner()
    {
        return whoWon;
    }

    public boolean isContinueGame() {return continueGame;}

    public char getPlayerThatMoved() {return playerThatMoved;}
}
