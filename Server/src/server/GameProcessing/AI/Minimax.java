package sample.AI;

import sample.Board;
import sample.Move;

import java.util.ArrayList;

public class Minimax implements BoardEvaluator {

    public String findBestMove(BoardWithMove boardObj, char currentPlayer)
    {
        /*
            1. Determine what spots on the board are AVAILABLE/EMPTY
            2. Add those spots to the arraylist
            3. Run minimax algorithm on each one
            4. Find best score from the array of spots
            5. Correlate that spot with a button id and return a string of buttonID
         */

//        if(isMaxPlayer)
//        {
//            int maxScore = Integer.MIN_VALUE;
//
//            ArrayList<BoardWithMove> children = generateChildren(boardObj, currentPlayer);
//
//            for (BoardWithMove child: children)
//            {
//                int maxEval = minimax(child, nextPlayer(currentPlayer), false, Integer.MIN_VALUE,
//                        Integer.MAX_VALUE, 0);
//
//                maxScore = Math.max(maxScore, maxEval);
//            }

           Move bestMove = getNextMove(boardObj, currentPlayer);

           int x = bestMove.getRow();
           int y = bestMove.getCol();

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

//            for (int i = 0; i < board.length; i++) {
//                for (int j = 0; j < board.length; j++) {
//                    board[i][j] = currentPlayer;
//                    int scoretemp = minimax(boardObj, nextPlayer(currentPlayer), false,
//                            Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
//                    board[i][j] = '-';
//
//                    if (scoretemp > score)
//                    {
//                        score = scoretemp;
//                        x = i;
//                        y = j;
//                    }
////                    if(board.getPlayerPosition(i, j) == '-')
////                    {
////                        board[i][j] = currentPlayer;
////                        printBoard(board);
//
//                    //board[i][j] = '-';
//                }
//            }
//        }
//        else
//        {
//            int minScore = Integer.MAX_VALUE;
//
//           ArrayList<BoardWithMove> children = generateChildren(boardObj, currentPlayer);
//
//            for (BoardWithMove child: children)
//            {
//                int minEval = minimax(child, nextPlayer(currentPlayer), true, Integer.MIN_VALUE,
//                        Integer.MAX_VALUE, 0);
//
//                minScore = Math.min(minScore, minEval);
//            }

            // Return the located spot on the board and return the button id

            //children = generateChildren(boardObj, currentPlayer);

//            for (int i = 0; i < board.length; i++) {
//
//                for (int j = 0; j < board.length; j++) {
//                    board[i][j] = currentPlayer;
//                    int scoretemp = minimax(boardObj, nextPlayer(currentPlayer), true,
//                            Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
//                    board[i][j] = '-';
//
//                    if (scoretemp < score)
//                    {
//                        score = scoretemp;
//                        x = i;
//                        y = j;
//                    }
//                }
            //}
    }

    public Move getNextMove(BoardWithMove board, char currentPlayer)
    {
        Move bestMove = null;

        int bestEval = currentPlayer == 'X' ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        ArrayList<BoardWithMove> children = generateChildren(board, currentPlayer);

        for (BoardWithMove child: children)
        {
            boolean isMax = currentPlayer == 'X' ? false : true;

            int eval = minimax(child, nextPlayer(currentPlayer), isMax, Integer.MIN_VALUE, Integer.MAX_VALUE,
                                0);

            if(eval > bestEval && currentPlayer == 'X' ||
                    eval < bestEval && currentPlayer == 'O')
            {
                bestEval = eval;
                bestMove = child.move;
            }
        }

        return bestMove;
    }

    public ArrayList<BoardWithMove> generateChildren(BoardWithMove position, char currentPlayer)
    {
        ArrayList<BoardWithMove> children = new ArrayList<>();

        for (int i = 0; i < position.board.getBoardSize(); i++)
            for (int j = 0; j < position.board.getBoardSize(); j++)
                try {
                    if (position.board.getPlayerPosition(i, j) == '-')
                    {
                        String buttonID = "button_";

                        if( i == 0 )
                            buttonID = buttonID + "top";
                        else if ( i == 1)
                            buttonID = buttonID + "mid";
                        else
                            buttonID = buttonID + "bot";

                        if (j == 0)
                            buttonID += "Left";
                        else if(j == 1)
                            buttonID += "Mid";
                        else
                            buttonID += "Right";

                        Board childBoard = (Board) position.board.clone();
                        childBoard.setMove(buttonID, currentPlayer);
                        Move move = new Move(i, j, currentPlayer);
                        children.add(new BoardWithMove(childBoard, move));
                    }
                } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                }

        return children;
    }

    public int minimax(BoardWithMove board, char currentPlayer, boolean isMaxPlayer, int alpha, int beta, int depth)
    {
        // if board is in terminal state
        // return static evaluation of position
        // either +100 for X, -100 for O, or 0 for tie
        int score = evaluateBoard(board, depth);
        if(score != 0)
            return score;

        // If it's the maximizing player's turn (X),
        // loop through each child or move from that current position
        // and make the recursive call.
        if (isMaxPlayer)
        {
            // Worst possible case for X
            int max = Integer.MIN_VALUE;

            ArrayList<BoardWithMove> children = generateChildren(board, currentPlayer);

            for(BoardWithMove child: children)
            {
//                if(board.getPlayerPosition(i, j) == '-')
//                {
//                        board[i][j] = currentPlayer;
//                        printBoard(board);
                int bestMove = minimax(child, nextPlayer(currentPlayer), false,
                                                alpha, beta, depth + 1);
                //board[i][j] = '-';

                max = Math.max(max, bestMove);

//                if (bestMove > max)
//                {
//                    max = bestMove;
//                }
                alpha = Math.max(alpha, max);
                if (beta <= alpha)
                {
                    break;
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
            int min = Integer.MAX_VALUE;

            ArrayList<BoardWithMove> children = generateChildren(board, currentPlayer);

            for(BoardWithMove child: children)
            {
                        //board[i][j] = currentPlayer;
//                        printBoard(board);
                int temp = minimax(child, nextPlayer(currentPlayer), true,
                                                alpha, beta, depth + 1);
                            //board[i][j] = '-';
                min = Math.min(min, temp);
//                if (temp < min)
//                {
//                    min = temp;
//                }
                beta = Math.min(beta, min);
                if (beta <= alpha)
                {
                    break;
                }
            }
            return min;
        }
    }

    /**
     * Determines whether a player has won on the current board
     * @param position the character board
     //* @param currentPlayer the character of the current player (i.e. 'X' or 'O')
     * @return +100 if maximizer wins, -100 if minimizer wins, 0 if its a tie or nothing
     */
    @Override
    public int evaluateBoard(BoardWithMove position, int depth)
    {
        char winner = position.board.boardStatus();

        if (winner == 'X')
        {
            return 100 - depth;
        }
        else if (winner == 'O')
        {
            return depth - 100;
        }
        else
        {
            return 0;
        }

//        // Check row winner
//
//        for (int row = 0; row < board.length; row++)
//        {
//            if (currentPlayer == 'X')
//            {
//                if(board[row][0] == currentPlayer && board[row][1] == currentPlayer &&
//                        board[row][2] == currentPlayer)
//                {
//                    return +100 - depth;
//                }
//            }
//            else if (currentPlayer == 'O')
//            {
//                if(board[row][0] == currentPlayer && board[row][1] == currentPlayer &&
//                        board[row][2] == currentPlayer)
//                {
//                    return depth - 100;
//                }
//            }
//        }
//
//        // Check column winner
//
//        for (int col = 0; col < board.length; col++)
//        {
//            if (currentPlayer == 'X')
//            {
//                if(board[0][col] == currentPlayer && board[1][col] == currentPlayer &&
//                        board[2][col] == currentPlayer)
//                {
//                    return +100 - depth;
//                }
//            }
//            else if (currentPlayer == 'O')
//            {
//                if(board[0][col] == currentPlayer && board[1][col] == currentPlayer &&
//                        board[2][col] == currentPlayer)
//                {
//                    return depth - 100;
//                }
//            }
//        }
//
//        // Check \ diagonal winner
//
//        if (currentPlayer == 'X')
//        {
//            if(board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
//                    board[2][2] == currentPlayer)
//            {
//                return +100 - depth;
//            }
//        }
//        else if (currentPlayer == 'O')
//        {
//            if(board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
//                    board[2][2] == currentPlayer)
//            {
//                return depth - 100;
//            }
//        }
//
//        // Check / diagonal winner
//
//        if (currentPlayer == 'X')
//        {
//            if(board[0][2] == currentPlayer && board[1][1] == currentPlayer &&
//                    board[2][0] == currentPlayer)
//            {
//                return +100 - depth;
//            }
//        }
//        else if (currentPlayer == 'O')
//        {
//            if(board[0][2] == currentPlayer && board[1][1] == currentPlayer &&
//                    board[2][0] == currentPlayer)
//            {
//                return depth - 100;
//            }
//        }
//
//        // If none of the above cases occur, then it's a tie, so return a score of 0
//
//        return 0;
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