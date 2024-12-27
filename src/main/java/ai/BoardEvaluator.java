package ai;

import base.Board;

public interface BoardEvaluator {
    public double evaluate(Board board, int player);
    default void printStats() {}
}
