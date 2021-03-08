package sample.AI;

import java.util.ArrayList;
import java.util.List;

public class Minimax {

    private boolean isMaxPlayer;

    public boolean isMaxPlayer()
    {
        return isMaxPlayer;
    }

    public int minimax(Node node, boolean isMaxPlayer)
    {
        // if node is leaf
        // return static evaluation of position


        // If it's the maximizing player's turn (X),
        // loop through each child or move from that current position
        // and make the recursive call.


        if (isMaxPlayer)
        {
            // Worst possible case for X
            int max = (int)Double.NEGATIVE_INFINITY;

            for (Node child: node.getNodes())
            {
                int bestMove = minimax(child, false);

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
                int temp = minimax(child, true);

                if (temp < min)
                {
                    min = temp;
                }
            }

            return min;
        }
    }



}