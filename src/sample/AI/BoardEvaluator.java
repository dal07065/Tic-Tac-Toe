package sample.AI;

import sample.Board;

public interface BoardEvaluator {

    int evaluateBoard(char[][] board, char currentPlayer);
}
