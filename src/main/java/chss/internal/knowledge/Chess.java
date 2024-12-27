package chss.internal.knowledge;




import base.Move;
import chss.internal.ChessBoard;
import utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

import static chss.internal.knowledge.PieceType.PieceColors;
import static chss.internal.knowledge.PieceType.Pieces;


public class Chess {


    static long zobristBoardValues[][][];
    static long zobristTurnValues[];



    public static long zobristKey(int[][] board, int player) {
        if (zobristBoardValues == null)
            initializeZobrist();

        long key = 0;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                key ^= zobristBoardValues[board[r][c]][r][c];
            }
        }
        key ^= zobristTurnValues[player];
        return key;
    }

    private static void initializeZobrist() {

        zobristBoardValues = new long[13][8][8];

        for (int i = 0; i < 13; i++) {
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++)
                {

                    zobristBoardValues[i][r][c] = RandomUtils.randomLong();
                }
            }
        }

        zobristTurnValues = new long[2];
        for (int i = 0; i < zobristTurnValues.length; i++) {
            zobristTurnValues[i] = RandomUtils.randomLong();
        }
    }

    public static long updateKey(long key, ChessMove m, int piece, int oldTarget, int player) {
        key ^= zobristBoardValues[piece][m.frow][m.fcol];
        key ^= zobristBoardValues[oldTarget][m.trow][m.tcol];
        key ^= zobristBoardValues[piece][m.trow][m.tcol];

        key ^= zobristTurnValues[player];
        key ^= zobristTurnValues[opponent(player)];
        return key;
    }


    public static final int WHITE =0;
    public static final int BLACK =1;
    public static final int EMPTY =2;

    private final static int InitialBoard[][] =
            {       { PieceType.WRook.ordinal(), PieceType.WKnight.ordinal(), PieceType.WBishop.ordinal(), PieceType.WQueen.ordinal(), PieceType.WKing.ordinal(), PieceType.WBishop.ordinal(), PieceType.WKnight.ordinal(), PieceType.WRook.ordinal()},
                    { PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal()},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal()},
                    { PieceType.BRook.ordinal(), PieceType.BKnight.ordinal(), PieceType.BBishop.ordinal(), PieceType.BQueen.ordinal(), PieceType.BKing.ordinal(), PieceType.BBishop.ordinal(), PieceType.BKnight.ordinal(), PieceType.BRook.ordinal()}
            };

    private final static int EmptyBoard[][] =
            {       { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0},
                    { 0, 0, 0, 0,0, 0, 0, 0}};

    public static int[][] initialBoard() {
        return cloneBoard(InitialBoard);
    }

    public static int[][] emptyBoard() {
        return cloneBoard(EmptyBoard);
    }

    private static int[][] cloneBoard(int[][] board) {
        int newBoard[][] = new int[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                newBoard[r][c]= board[r][c];
            }
        }
        return newBoard;
    }

    public static List<Move> getMoves(int[][] board, int player) {

        List<Move> moves = new ArrayList<>();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ( PieceColors[ board[r][c] ]!= player)
                    continue;
                ChessPiece piece = PieceFactory.pieces[board[r][c]];
                moves.addAll(piece.getMoves(board,player,r,c));
            }

        }

        /*todo*/
        return moves;
    }

    public static boolean isValid(int[][] board, int player, ChessMove m) {
        if (board[m.frow][m.fcol] == 0 || PieceColors[board[m.frow][m.fcol]]!= player)
            return false;
        if (PieceColors[board[m.trow][m.tcol]]== player)
            return false;

        ChessPiece piece = PieceFactory.create(board[m.frow][m.fcol]);

        if (!piece.canMove(board,player,m.frow,m.fcol,m.trow,m.tcol))
            return false;

        int[][] boardAfter = boardAfter(board,m);

        return !kingIsInCheck(boardAfter,player);

    }

    public static boolean kingIsInCheck(int[][] board, int player) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ( PieceColors[board[r][c]]==opponent(player))
                {
                    ChessPiece opponentPiece = PieceFactory.create(board[r][c]);
                    List<Move>moves = opponentPiece.getMovesWithoutKingCheckControl(board,opponent(player),r,c);
                    for (Move m:moves) {
                        ChessMove cm = (ChessMove) m;
                        if (board[cm.trow][cm.tcol]==PieceType.WKing.ordinal() || board[cm.trow][cm.tcol]==PieceType.BKing.ordinal()) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public static int[][] boardAfter(int[][] board, ChessMove move) {
        int[][] boardAfter = cloneBoard(board);
        boardAfter[move.frow][move.fcol]= 0;
        boardAfter[move.trow][move.tcol]= board[move.frow][move.fcol];
        return boardAfter;
    }


    public static int opponent(int player) {
        if (player == WHITE)
            return BLACK;
        else if (player == BLACK)
            return WHITE;
        else return EMPTY;
    }

    public static boolean isGameOver(int[][] board, int player) {
        return getMoves(board,player).isEmpty();
    }

    static boolean clearPath(int[][] board, int fr, int fc, int tr, int tc) {
        int dr = Math.abs(fr-tr);
        int dc = Math.abs(fc-tc);

        if ( !( dr==0|| dc==0 ||  dr ==dc))
            return false;

        int sr = (tr>fr) ? 1 : (tr<fr)? -1 : 0;
        int sc = (tc>fc) ? 1 : (tc<fc)? -1 : 0;


        for (int r=fr+sr, c = fc+sc; !(r==tr && c==tc ); r+=sr, c+=sc  )
        {
            if (board[r][c]!= 0) // Empty
                return false;
        }

        return true;
    }





    // Returns if the move is either a pawn move or a capture.
    // Used for 50-moves rule
    public static boolean isSignificantMove(int[][] board,Move m) {
        ChessMove cm = (ChessMove) m;

        PieceType piece = Pieces[board[cm.frow][cm.fcol]];
        PieceType target = Pieces[board[cm.trow][cm.tcol]];
        return piece==PieceType.WPawn || piece== PieceType.BPawn || target != PieceType.Empty;
    }

    public static boolean isCaptureMove(int[][] board,Move m) {
        ChessMove cm = (ChessMove) m;
        return  board[cm.trow][cm.tcol] != 0;//NOT EMPTY
    }

    public static void updateBoard(int[][] board, ChessMove move) {

        PieceType piece = Pieces[board[move.frow][move.fcol]];
        board[move.frow][move.fcol]= 0;
        board[move.trow][move.tcol]= piece.ordinal();

        // PROMOTION
        if (piece== PieceType.BPawn && move.trow==0)
            board[move.trow][move.tcol]= PieceType.BQueen.ordinal();
        if (piece== PieceType.WPawn && move.trow==7)
            board[move.trow][move.tcol]= PieceType.WQueen.ordinal();
    }




    private final static int TestBoard[][] =
            {       { PieceType.WRook.ordinal(), PieceType.WKnight.ordinal(), PieceType.WBishop.ordinal(), PieceType.WQueen.ordinal(), PieceType.WKing.ordinal(), PieceType.WBishop.ordinal(), PieceType.WKnight.ordinal(), PieceType.WRook.ordinal()},
                    { PieceType.Empty.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.Empty.ordinal()},
                    { PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal()},
                    { PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.WPawn.ordinal(), PieceType.WPawn.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal()},
                    { PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal()},
                    { PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal()},
                    { PieceType.Empty.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.Empty.ordinal(), PieceType.Empty.ordinal(), PieceType.BPawn.ordinal(), PieceType.BPawn.ordinal(), PieceType.Empty.ordinal()},
                    { PieceType.BRook.ordinal(), PieceType.BKnight.ordinal(), PieceType.BBishop.ordinal(), PieceType.BQueen.ordinal(), PieceType.BKing.ordinal(), PieceType.BBishop.ordinal(), PieceType.BKnight.ordinal(), PieceType.BRook.ordinal()}
            };

    public static void main(String[] args) {

       // speedTest();

       // testPosition();

        testZobrist();

    }


    private static void testZobrist()
    {
        ChessBoard cb1 = new ChessBoard();
        ChessBoard cb2 = new ChessBoard();

        ChessMove cm1 = new ChessMove(1,0,2,0);
        ChessMove cm2 = new ChessMove(1,1,2,1);
        ChessMove cm3 = new ChessMove(6,0,5,0);

        cb1.perform(cm1);
        cb1.perform(cm3);
        cb1.perform(cm2);

        cb2.perform(cm2);
        cb2.perform(cm3);
        cb2.perform(cm1);

        System.out.println("B1 : "+ cb1.getKey());
        System.out.println("B2 : "+ cb2.getKey());
    }

    private static void testPosition() {
        int board[][] = emptyBoard().clone();

        board[5][3] = PieceType.BQueen.ordinal();
        board[3][5] = PieceType.WKing.ordinal();



        System.out.println(kingIsInCheck(board,WHITE));
        List<Move> moves = getMoves(board,WHITE);

        System.out.println(moves.size());




    }


    private static void speedTest() {
        int board[][] = TestBoard.clone();

        for (int i = 0; i < 10000; i++) {
            List<Move> moves = getMoves(board,WHITE);
        }


        List<Move> moves = null;



        long elapsed = System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            moves = getMoves(board,WHITE);
        }
        System.out.println("Average Time:" + (double)(System.currentTimeMillis()-elapsed)/2000.0);

        System.out.println(moves.size());
    }
}
