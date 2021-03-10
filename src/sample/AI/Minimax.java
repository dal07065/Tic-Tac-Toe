package sample.AI;

import sample.Board;

public class Minimax extends Board implements BoardEvaluator {

    private boolean isMaxPlayer;
    private static final char BLANK = ' ';

    public Minimax()
    {
        super();
    }

    public boolean isMaxPlayer()
    {
        return isMaxPlayer;
    }

    public int minimax(char[][] board, char currentPlayer, boolean isMaxPlayer)
    {
        // if board reaches highest score or is in terminal state
        // return static evaluation of position

        int score = evaluateBoard(board, currentPlayer);

        if(Math.abs(score) == 100 || score == 0)
        {
            return score;
        }

        // If it's the maximizing player's turn (X),
        // loop through each child or move from that current position
        // and make the recursive call.

        if (isMaxPlayer)
        {
            // Worst possible case for X
            int maxValue = (int)Double.NEGATIVE_INFINITY;

            for (int row = 0; row < board.length; row++)
            {
                for(int col = 0; col < board.length; col++)
                {
                    if(board[row][col] == BLANK)
                    {
                        board[row][col] = currentPlayer;
                        maxValue = Math.max(maxValue, minimax(board, currentPlayer, false));
                        board[row][col] = BLANK;
                    }
                }
            }

            return maxValue;
        }

        // If it's the minimizing player's turn (O),
        // loop through each child or move from that current position
        // and make the recursive call.

        else
        {
            // Worst possible case for O
            int minValue = (int)Double.POSITIVE_INFINITY;

            for (int row = 0; row < board.length; row++)
            {
                for(int col = 0; col < board.length; col++)
                {
                    if(board[row][col] == BLANK)
                    {
                        board[row][col] = currentPlayer;
                        minValue = Math.min(minValue, minimax(board, currentPlayer, true));
                        board[row][col] = BLANK;
                    }
                }
            }

            return minValue;
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