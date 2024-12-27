package chss.internal;




import base.Board;
import base.Move;
import chss.internal.knowledge.Chess;

import java.util.HashMap;

import java.util.List;

public class CachedChessBoard extends ChessBoard {

    static HashMap<Long, List<Move>>  moveMap = new HashMap<>();
    static HashMap<Long,Boolean> gameOverMap = new HashMap<>();

    static long movesHitCount=0;
    static long gameOverHitCount =0;

    @Override
    public int currentPlayer() {
        return super.currentPlayer();
    }



    public CachedChessBoard()
    {
        super();
    }

    public CachedChessBoard(int board[][], int player)
    {
        super(board,player);

    }

    public CachedChessBoard(CachedChessBoard other) {
        super(other);
    }

    static synchronized boolean inGameOverMap(long key)
    {
        return gameOverMap.containsKey(key);
    }

    static synchronized boolean isGameOverFromMap(long key)
    {
        //System.out.println("GAMEOVER-HIT");
        gameOverHitCount++;
        return gameOverMap.get(key);
    }

    static synchronized void putGameOverMap(long key, boolean gameOver )
    {
        gameOverMap.put(key,gameOver);
    }
    static synchronized List<Move> getMovesFromCache(long key)
    {
        //System.out.println("MOVES-HIT");
        movesHitCount++;
        return moveMap.get(key);
    }

    static synchronized boolean inMoveMap(long key)
    {
        return moveMap.containsKey(key);
    }
    private synchronized static void putMovesToCache(long key, List<Move> moves) {
        moveMap.put(key,moves);
    }

    @Override
    public List<Move> getMoves() {
        if (inMoveMap(key))
            return getMovesFromCache(key);
        else{
            List<Move> moves = Chess.getMoves(board,currentPlayer());
            putMovesToCache(key,moves);
            return moves;
        }
    }

    @Override
    public void perform(Move m) {
        if (Chess.isSignificantMove(board,m))
            lastSignificantMove = moveCount;
        super.perform(m);
    }



    @Override
    public boolean isGameOver() {
        if (inGameOverMap(key))
            return isGameOverFromMap(key);

        if (drawnBy50MoveRule())//50 move rule
        {
            winner= -1;
            putGameOverMap(key,true);
            return true;
        }
        boolean gameOver= Chess.isGameOver(board,currentPlayer());

        if (gameOver)
        {
            if( Chess.kingIsInCheck(board,currentPlayer()))
            {
                winner= Chess.opponent(currentPlayer());
            }
            else winner = -1;
        }
        putGameOverMap(key,gameOver);
        return gameOver;
    }

    @Override
    public Board cloneBoard() {

        return new CachedChessBoard(this);
    }


    @Override
    public void printStats() {
        System.out.println("BOARD>> MovesHitCount:"+ movesHitCount + "  GameOverHitCount:" + gameOverHitCount);
    }
}
