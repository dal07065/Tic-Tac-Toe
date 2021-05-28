package server.GameProcessing;

import SharedServerComponents.IDGenerator;

public class Board {

    private char[][] board;

    private int boardSize = 3;

    private int posLeft = 9;

    public char[][] getBoard() { return board; }

    public int getBoardSize()
    {
        return boardSize;
    }

    private String boardID;

    private String player1;
    private String player2;

    private boolean player1ready;
    private boolean player2ready;

    private char currentPlayer;

    public String getBoardID(){return boardID;}

    public Board(int i, int j)
    {
        board = new char[i][j];

        resetBoard();

        boardID = IDGenerator.getNewID(this);

        currentPlayer = 'X';
    }

    public char getCurrentPlayer() {return currentPlayer;}

    public String getPlayer1() {return player1;}
    public void setPlayer1(String userWhoStartedThisGame) {
        player1 = userWhoStartedThisGame;
    }
    public String getPlayer2() {return player2;}
    public void setPlayer2(String userID)
    {
        player2 = userID;
    }

    public void removePlayer(String playerID)
    {
        if(player1.equals(playerID))
        {
            player1 = player2;
            player2 = null;
        }
        else
            player2 = null;
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

        if(player == 'X')
            currentPlayer = 'O';
        else
            currentPlayer = 'X';

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

        currentPlayer = 'X';

    }


    public void setPlayerReady(String player) {
        if(player.equals(player1))
            player1ready = true;
        else if(player.equals(player2))
            player2ready = true;
    }

    public boolean playAgain() {
        if(player1ready && player2ready)
        {
            player1ready = false;
            player2ready = false;
            return true;
        }
        return false;
    }
}
