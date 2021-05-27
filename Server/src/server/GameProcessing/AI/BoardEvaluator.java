package server.GameProcessing.AI;


public interface BoardEvaluator {

    int evaluateBoard(char[][] board, char currentPlayer, int depth);
}
