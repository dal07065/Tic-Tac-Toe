package server.GameProcessing.AI;


import server.GameProcessing.Board;

import java.util.ArrayList;
import java.util.List;

public class Minimax implements BoardEvaluator {

//    private boolean isMaxPlayer;
//
//    public boolean isMaxPlayer()
//    {
//        return isMaxPlayer;
//    }

    public String findBestMove(Board boardObj, char currentPlayer, boolean isMaxPlayer)
    {
        /*
            1. Determine what spots on the board are AVAILABLE/EMPTY
            2. Add those spots to the arraylist
            3. Run minimax algorithm on each one
            4. Find best score from the array of spots
            5. Correlate that spot with a button id and return a string of buttonID
         */
        char[][] board = boardObj.getBoard();

        int x = 0,y = 0;

        if(isMaxPlayer)
        {
            int score = (int)Double.NEGATIVE_INFINITY;

            for(int i = 0; i < boardObj.getBoardSize(); i++)
            {
                for(int j = 0; j < boardObj.getBoardSize(); j++)
                {
                    if(board[i][j] == '-')
                    {
                        board[i][j] = currentPlayer;
//                        printBoard(board);
                        int scoretemp = minimax(board, nextPlayer(currentPlayer), false, 0);
                        board[i][j] = '-';

                        if(scoretemp > score)
                        {
                            score = scoretemp;
                            x = i;
                            y = j;
                        }
                    }
                }
            }
        }
        else
        {
            int score = (int)Double.POSITIVE_INFINITY;

            for(int i = 0; i < boardObj.getBoardSize(); i++)
            {
                for(int j = 0; j < boardObj.getBoardSize(); j++)
                {
                    if(board[i][j] == '-')
                    {
                        board[i][j] = currentPlayer;
//                        printBoard(board);
                        int scoretemp = minimax(board, nextPlayer(currentPlayer), true, 0);
                        board[i][j] = '-';

                        if(scoretemp < score)
                        {
                            score = scoretemp;
                            x = i;
                            y = j;
                        }
                    }
                }
            }
        }

        // Return the located spot on the board and return the button id

        String buttonID = "button_";

        if( x == 0 )
            buttonID = buttonID + "top";
        else if ( x == 1)
            buttonID = buttonID + "mid";
        else
            buttonID = buttonID + "bot";

        if (y == 0)
            buttonID += "Left";
        else if(y == 1)
            buttonID += "Mid";
        else
            buttonID += "Right";

        return buttonID;
    }

    public int minimax(char[][] board, char currentPlayer, boolean isMaxPlayer, int depth)
    {
        // if board is in terminal state
        // return static evaluation of position
        // either +100 for X, -100 for O, or 0 for tie
        int score = evaluateBoard(board, currentPlayer, depth);
        if(score != 0)
            return score;

        // If it's the maximizing player's turn (X),
        // loop through each child or move from that current position
        // and make the recursive call.
        if (isMaxPlayer)
        {
            // Worst possible case for X
            int max = (int)Double.NEGATIVE_INFINITY;

            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board.length; j++)
                {
                    if(board[i][j] == '-')
                    {
                        board[i][j] = currentPlayer;
//                        printBoard(board);
                        int bestMove = minimax(board, nextPlayer(currentPlayer), false, depth + 1);
                        board[i][j] = '-';
                        if (bestMove > max)
                        {
                            max = bestMove;
                        }
                    }
                }
            }

            return max;
        }

        // If it's the minimizing player's turn (O),
        // loop through each child or move from that current position
        // and make the recursive call.

        else
        {
            // Worst possible case for O
            int min = (int)Double.POSITIVE_INFINITY;

            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board.length; j++)
                {
                    if (board[i][j] == '-')
                    {
                        board[i][j] = currentPlayer;
//                        printBoard(board);
                        int temp = minimax(board, nextPlayer(currentPlayer), true, depth + 1);
                        board[i][j] = '-';
                        if (temp < min) {
                            min = temp;
                        }
                    }
                }
            }

            return min;
        }
    }

    /**
     * Determines whether a player has won on the current board
     * @param board the character board
     * @param currentPlayer the character of the current player (i.e. 'X' or 'O')
     * @return +100 if maximizer wins, -100 if minimizer wins, 0 if its a tie or nothing
     */
    @Override
    public int evaluateBoard(char[][] board, char currentPlayer, int depth)
    {
        // Check row winner

        for (int row = 0; row < board.length; row++)
        {
            if (currentPlayer == 'X')
            {
                if(board[row][0] == currentPlayer && board[row][1] == currentPlayer &&
                        board[row][2] == currentPlayer)
                {
                    return +100 - depth;
                }
            }
            else if (currentPlayer == 'O')
            {
                if(board[row][0] == currentPlayer && board[row][1] == currentPlayer &&
                        board[row][2] == currentPlayer)
                {
                    return depth - 100;
                }
            }
        }

        // Check column winner

        for (int col = 0; col < board.length; col++)
        {
            if (currentPlayer == 'X')
            {
                if(board[0][col] == currentPlayer && board[1][col] == currentPlayer &&
                        board[2][col] == currentPlayer)
                {
                    return +100 - depth;
                }
            }
            else if (currentPlayer == 'O')
            {
                if(board[0][col] == currentPlayer && board[1][col] == currentPlayer &&
                        board[2][col] == currentPlayer)
                {
                    return depth - 100;
                }
            }
        }

        // Check \ diagonal winner

        if (currentPlayer == 'X')
        {
            if(board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][2] == currentPlayer)
            {
                return +100 - depth;
            }
        }
        else if (currentPlayer == 'O')
        {
            if(board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][2] == currentPlayer)
            {
                return depth - 100;
            }
        }

        // Check / diagonal winner

        if (currentPlayer == 'X')
        {
            if(board[0][2] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][0] == currentPlayer)
            {
                return +100 - depth;
            }
        }
        else if (currentPlayer == 'O')
        {
            if(board[0][2] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][0] == currentPlayer)
            {
                return depth - 100;
            }
        }

        // If none of the above cases occur, then it's a tie, so return a score of 0

        return 0;
    }

    /**
     * Return the opposite player character
     * @param currentPlayer the character of the current player (i.e. 'X' or 'O')
     * @return the character of the next assumed player
     */
    private char nextPlayer(char currentPlayer)
    {
        if(currentPlayer == 'X')
            return 'O';
        else
            return 'X';
    }

    /**
     * Print the board to the console
     * @param board the character board
     */
    private void printBoard(char[][] board)
    {
        for(int i = 0; i < 3; i++)
        {
            System.out.print("|");
            for(int j = 0; j < 3; j++)
            {
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
        }

        System.out.println();
    }
}