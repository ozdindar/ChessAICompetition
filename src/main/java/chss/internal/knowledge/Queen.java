package chss.internal.knowledge;




import base.Move;

import java.util.ArrayList;
import java.util.List;

import static chss.internal.knowledge.Chess.EMPTY;
import static chss.internal.knowledge.PieceType.PieceColors;

class Queen extends ChessPiece
{
    @Override
    boolean canMove(int[][] board, int player, int fr, int fc, int tr, int tc) {
        return PieceColors[ board[tr][tc]]!=player&& Chess.clearPath(board,fr,fc,tr,tc) ;
    }


    @Override
    protected List<Move> getMovesWithoutKingCheckControl(int[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();

        // ROOK MOVES
        for (int i = 1; r+i<8  ; i++) {
            if ( PieceColors[board[r+i][c]]!=player)
                moves.add(new ChessMove(r,c,r+i,c));
            if ( PieceColors[board[r+i][c]]!=EMPTY) break;
        }
        for (int i = 1; r-i>=0  ; i++) {
            if ( PieceColors[board[r-i][c]]!=player)
                moves.add(new ChessMove(r,c,r-i,c));
            if ( PieceColors[board[r-i][c]]!=EMPTY) break;
        }

        for (int i = 1; c+i<8  ; i++) {
            if ( PieceColors[board[r][c+i]]!=player)
                moves.add(new ChessMove(r,c,r,c+i));
            if ( PieceColors[board[r][c+i]]!=EMPTY) break;
        }
        for (int i = 1; c-i>=0  ; i++) {
            if ( PieceColors[board[r][c-i]]!=player)
                moves.add(new ChessMove(r,c,r,c-i));
            if ( PieceColors[board[r][c-i]]!=EMPTY) break;
        }

        // BISHOP MOVES
        for (int i = 1; i < 8 && r-i>=0 && c-i>=0 ; i++) {
            if (  PieceColors[board[r-i][c-i]]!=player)
                moves.add(new ChessMove(r,c,r-i,c-i));
            if (PieceColors[board[r-i][c-i]]!=EMPTY) break;
        }

        for (int i = 1; i < 8 && r+i<8 && c+i<8 ; i++) {
            if (PieceColors[board[r+i][c+i]]!=player)
                moves.add(new ChessMove(r,c,r+i,c+i));
            if (PieceColors[board[r+i][c+i]]!=EMPTY  ) break;
        }

        for (int i = 1; i < 8 && r+i<8 && c-i>=0 ; i++) {
            if (PieceColors[board[r+i][c-i]]!=player)
                moves.add(new ChessMove(r,c,r+i,c-i));
            if (PieceColors[board[r+i][c-i]]!=EMPTY  ) break;
        }
        for (int i = 1; i < 8 && r-i>=0 && c+i<8 ; i++) {
            if (PieceColors[board[r-i][c+i]]!=player)
                moves.add(new ChessMove(r,c,r-i,c+i));
            if (PieceColors[board[r-i][c+i]]!=EMPTY  ) break;
        }
        return moves;
    }
}
