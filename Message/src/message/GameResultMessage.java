package message;

public class GameResultMessage extends Message{
    char winStatus;
    private String gameMoveMade;
    char playerThatMoved;

    public GameResultMessage(char winStatus, String gameMoveMade, char playerThatMoved)
    {
        this.winStatus = winStatus;
        this.gameMoveMade = gameMoveMade;
        this.playerThatMoved = playerThatMoved;
    }

    public String getGameMove()
    {
        return gameMoveMade;
    }

    public char checkWinner()
    {
        return winStatus;
    }
}
