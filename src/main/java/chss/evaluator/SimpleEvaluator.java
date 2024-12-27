package chss.evaluator;




import ai.BoardEvaluator;
import base.Board;

import java.util.ArrayList;
import java.util.List;

public class SimpleEvaluator implements BoardEvaluator {

    List<BoardEvaluator> evaluators = new ArrayList<>();

    @Override
    public double evaluate(Board board, int player) {
        double score = 0;
        for (BoardEvaluator evaluator:evaluators)
            score += evaluator.evaluate(board,player);
        return score;
    }

    public void addEvaluator(BoardEvaluator evaluator) {
        evaluators.add(evaluator);
    }
}
