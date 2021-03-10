package sample.AI;

import sample.Board;

import java.util.ArrayList;
import java.util.List;

public class Minimax implements BoardEvaluator {

    private boolean isMaxPlayer;

    public boolean isMaxPlayer()
    {
        return isMaxPlayer;
    }

    public int minimax(char[][] board, char currentPlayer, Node node, boolean isMaxPlayer)
    {
        // if board is in terminal state
        // return static evaluation of position

        int score = evaluateBoard(board, currentPlayer);


        // If it's the maximizing player's turn (X),
        // loop through each child or move from that current position
        // and make the recursive call.


        if (isMaxPlayer)
        {
            // Worst possible case for X
            int max = (int)Double.NEGATIVE_INFINITY;

            for (Node child: node.getNodes())
            {
                int bestMove = minimax(board, currentPlayer, child, false);

                if (bestMove > max)
                {
                    max = bestMove;
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

            for (Node child: node.getNodes())
            {
                int temp = minimax(board, currentPlayer, child, false);

                if (temp < min)
                {
                    min = temp;
                }
            }

            return min;
        }
    }


    @Override
    public int evaluateBoard(char[][] board, char currentPlayer)
    {
        // Check row winner

        for (int row = 0; row < 3; row++)
        {
            if (currentPlayer == 'X')
            {
                if(board[row][0] == currentPlayer && board[row][1] == currentPlayer &&
                        board[row][2] == currentPlayer)
                {
                    return +100;
                }
            }
            else if (currentPlayer == 'O')
            {
                if(board[row][0] == currentPlayer && board[row][1] == currentPlayer &&
                        board[row][2] == currentPlayer)
                {
                    return -100;
                }
            }
        }

        // Check column winner

        for (int col = 0; col < 3; col++)
        {
            if (currentPlayer == 'X')
            {
                if(board[0][col] == currentPlayer && board[1][col] == currentPlayer &&
                        board[2][col] == currentPlayer)
                {
                    return +100;
                }
            }
            else if (currentPlayer == 'O')
            {
                if(board[0][col] == currentPlayer && board[1][col] == currentPlayer &&
                        board[2][col] == currentPlayer)
                {
                    return -100;
                }
            }
        }

        // Check \ diagonal winner

        if (currentPlayer == 'X')
        {
            if(board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][2] == currentPlayer)
            {
                return +100;
            }
        }
        else if (currentPlayer == 'O')
        {
            if(board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][2] == currentPlayer)
            {
                return -100;
            }
        }

        // Check / diagonal winner

        if (currentPlayer == 'X')
        {
            if(board[0][2] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][0] == currentPlayer)
            {
                return +100;
            }
        }
        else if (currentPlayer == 'O')
        {
            if(board[0][2] == currentPlayer && board[1][1] == currentPlayer &&
                    board[2][0] == currentPlayer)
            {
                return -100;
            }
        }

        // If none of the above cases occur, then it's a tie, so return a score of 0

        return 0;
    }
}