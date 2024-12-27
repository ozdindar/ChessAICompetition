package ai;

import base.Board;
import base.Move;

public interface BoardAI {
    Move getBestMove(Board board, BoardEvaluator evaluator);
}
