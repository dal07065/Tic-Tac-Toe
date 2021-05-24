package sample;

public class Board implements Cloneable {

    private char[][] board;

    private int boardSize = 3;

    private int posLeft = 9;

    public char[][] getBoard() { return board; }

    public int getBoardSize()
    {
        return boardSize;
    }

    public Board(int i, int j)
    {
        board = new char[i][j];

        resetBoard();
    }

    public char getPlayerPosition(int row, int col)
    {
        return board[row][col];
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

    public void unsetMove(String buttonClicked)
    {
        if(buttonClicked.equalsIgnoreCase("button_topLeft"))
            board[0][0] = '-';

        if(buttonClicked.equalsIgnoreCase("button_topMid"))
            board[0][1] = '-';

        if(buttonClicked.equalsIgnoreCase("button_topRight"))
            board[0][2] = '-';

        if(buttonClicked.equalsIgnoreCase("button_midLeft"))
            board[1][0] = '-';

        if(buttonClicked.equalsIgnoreCase("button_midMid"))
            board[1][1] = '-';

        if(buttonClicked.equalsIgnoreCase("button_midRight"))
            board[1][2] = '-';

        if(buttonClicked.equalsIgnoreCase("button_botLeft"))
            board[2][0] = '-';

        if(buttonClicked.equalsIgnoreCase("button_botMid"))
            board[2][1] = '-';

        if(buttonClicked.equalsIgnoreCase("button_botRight"))
            board[2][2] = '-';

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
        posLeft = 9;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
