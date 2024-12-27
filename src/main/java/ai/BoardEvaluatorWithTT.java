package ai;

import base.Board;

import java.util.HashMap;

public class BoardEvaluatorWithTT implements BoardEvaluator{
    BoardEvaluator evaluator;
    HashMap<Long, Double> table;
    long hitCount;
    long evalCount;

    public BoardEvaluatorWithTT(BoardEvaluator evaluator) {
        this.evaluator = evaluator;
        table = new HashMap<>();
        hitCount=0;
        evalCount=0;
    }

    @Override
    public double evaluate(Board board, int player) {

        if (table.containsKey(board.getKey()))
        {
            double score = table.get(board.getKey());
            hitCount++;
            return score;
        }

        double score = evaluator.evaluate(board,player);
        table.put(board.getKey(), score);
        evalCount++;
        return score;
    }

    public long getHitCount() {
        return hitCount;
    }

    @Override
    public void printStats() {
        System.out.println("EVALUATOR>> EVAL_COUNT:"+ evalCount  + "  HIT_COUNT: "+ hitCount);
    }
}
