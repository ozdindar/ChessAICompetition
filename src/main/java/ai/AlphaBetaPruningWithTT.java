package ai;



import base.Board;
import base.Move;

import java.util.HashMap;
import java.util.List;

public class AlphaBetaPruningWithTT implements BoardAI {
	int maxDepth;

	long hitCount;

	HashMap<Long, TableEntry> table;

	public AlphaBetaPruningWithTT(int maxDepth) {
		this.maxDepth = maxDepth;
		table = new HashMap<>();
		hitCount=0;
	}

	@Override
	public Move getBestMove(Board board, BoardEvaluator evaluator) {

		EvaluatedMove em = getBestMove(board, evaluator, board.currentPlayer(), 0, -Double.MAX_VALUE, Double.MAX_VALUE);

		System.out.println("ALGORITHM>> HIT COUNT:" + hitCount);
		evaluator.printStats();
		board.printStats();
		return em.getMove();
	}

	private EvaluatedMove getBestMove(Board board, BoardEvaluator evaluator,int player, int depth, double alpha,
			double beta) {

		if (table.containsKey(board.getKey()))
		{
			TableEntry entry = table.get(board.getKey());
			if (entry.depth<=depth)
			{
				hitCount++;
				if (entry.entryType == TableEntry.EntryType.Exact)
					return entry.move;
				else if (entry.entryType == TableEntry.EntryType.LowerBound)
				{
					alpha = Math.max(entry.move.score,alpha);
				}
				else beta = Math.min(entry.move.score,beta);
			}

			if (alpha>=beta)
				return entry.move;
		}

		if (board.isGameOver() || depth > maxDepth) {
			double score = evaluator.evaluate(board, player);
			return new EvaluatedMove(null, score);
		}

		EvaluatedMove bestMove = (player== board.currentPlayer()) ?
					alphaIteration(board,evaluator,player,depth,alpha,beta) :
					betaIteration(board,evaluator,player,depth,alpha,beta);


		TableEntry entry= new TableEntry(TableEntry.EntryType.Exact,bestMove,depth);
		if (player==board.currentPlayer() && bestMove.score<alpha)
		{
			entry.entryType= TableEntry.EntryType.LowerBound;
		}
		else if (player!=board.currentPlayer() && bestMove.score>beta)
			entry.entryType= TableEntry.EntryType.UpperBound;
		table.put(board.getKey(),entry);

		return bestMove;
	}

	private EvaluatedMove betaIteration(Board board, BoardEvaluator evaluator, int player, int depth, double alpha, double beta) {
		List<Move> moves = board.getMoves();
		EvaluatedMove bestMove = new EvaluatedMove(null, Double.MAX_VALUE);
		for (Move m : moves) {
			Board newBoard = board.makeMove(m);
			EvaluatedMove value = getBestMove(newBoard, evaluator, player, depth + 1, alpha, beta);

			if (value.score < bestMove.score) {
				bestMove = new EvaluatedMove(m, value.score);
			}

			beta = Math.min(beta, bestMove.score);

			if (beta <= alpha) {
				break;
			}
		}

		return bestMove;
	}

	private EvaluatedMove alphaIteration(Board board, BoardEvaluator evaluator, int player,int depth, double alpha, double beta )
	{
		List<Move> moves = board.getMoves();
		EvaluatedMove bestMove = new EvaluatedMove(null, -Double.MAX_VALUE);

		for (Move m : moves) {
			Board newBoard = board.makeMove(m);
			EvaluatedMove value = getBestMove(newBoard, evaluator, player, depth + 1, alpha, beta);


			if (value.score > bestMove.score) {
				bestMove = new EvaluatedMove(m, value.score);
			}

			alpha = Math.max(alpha, bestMove.score);

			if (beta <= alpha) {
				break;
			}
		}

		return bestMove;
	}
}
