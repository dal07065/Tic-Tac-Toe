package message;

public class GameWaitMessage extends Message{

    private char currentPlayer;

    public GameWaitMessage(char currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
}
