package sample;

public class Board {

    private char[][] board;

    private int boardSize = 3;

    public char[][] getBoard()
    {
        return board;
    }

    public Board()
    {
        this(3, 3);
    }

    public Board(int i, int j)
    {
        board = new char[i][j];

        resetBoard();
    }

    public char boardStatus()
    {
        // Check row horizontal winners

        for(int i = 0; i < boardSize; i++)
        {
            char winner = board[i][0];
            for(int j = 1; j < boardSize; j++)
            {
                if(winner != board[i][j])
                {
                    j = boardSize;
                }
                else if(j == boardSize-1 && winner != '-')
                {
                    return winner;
                }
            }
        }

        // Check column vertical winners

        for(int i = 0; i < boardSize; i++)
        {
            char winner = board[0][i];
            for(int j = 1; j < boardSize; j++)
            {
                char temp = board[j][i];
                if(winner != temp)
                {
                    j = boardSize;
                }
                else if(j == boardSize-1 && winner != '-')
                {
                    return winner;
                }
            }
        }

        // Check \ diagonal winners

        char winner = board[0][0];
        for(int i = 0; i < boardSize; i++)
        {
            if(winner != board[i][i])
            {
                i = boardSize;
            }
            else if(i == boardSize-1 && winner != '-')
            {
                return winner;
            }
        }

        // Check / diagonal winners

        int j = boardSize-1;
        winner = board[0][j];
        for(int i = 1; i < boardSize; i++)
        {
            j--;
            if(winner != board[i][j])
            {
                i = boardSize;
            }
            else if(i == boardSize-1 && winner != '-')
            {
                return winner;
            }
        }

        return '-';
    }

    public void setMove(String buttonClicked, char player)
    {
        if(buttonClicked.equalsIgnoreCase("button_topLeft"))
            board[0][0] = player;

        if(buttonClicked.equalsIgnoreCase("button_topMid"))
            board[0][1] = player;

        if(buttonClicked.equalsIgnoreCase("button_topRight"))
            board[0][2] = player;

        if(buttonClicked.equalsIgnoreCase("button_midLeft"))
            board[1][0] = player;

        if(buttonClicked.equalsIgnoreCase("button_midMid"))
            board[1][1] = player;

        if(buttonClicked.equalsIgnoreCase("button_midRight"))
            board[1][2] = player;

        if(buttonClicked.equalsIgnoreCase("button_botLeft"))
            board[2][0] = player;

        if(buttonClicked.equalsIgnoreCase("button_botMid"))
            board[2][1] = player;

        if(buttonClicked.equalsIgnoreCase("button_botRight"))
            board[2][2] = player;

    }

    public void resetBoard()
    {
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                board[i][j] = '-';
            }
        }
    }

}