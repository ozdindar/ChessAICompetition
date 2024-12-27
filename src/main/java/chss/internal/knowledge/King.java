package chss.internal.knowledge;





import base.Move;

import java.util.ArrayList;
import java.util.List;

import static chss.internal.knowledge.PieceType.PieceColors;

class King extends ChessPiece
{
    @Override
    boolean canMove(int[][] board, int player, int fr, int fc, int tr, int tc) {
        int dr = Math.abs(fr-tr);
        int dc = Math.abs(fc-tc);
        if (dr>1 || dc>1)
            return false;
        if (dr==0 && dc==0)
            return false;
        return PieceColors[board[tr][tc]]!=player;
    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(int[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();
        if (r<7 && PieceColors[ board[r+1][c] ]!= player)
            moves.add(new ChessMove(r,c,r+1,c));
        if (r>0 && PieceColors[ board[r-1][c] ]!= player)
            moves.add(new ChessMove(r,c,r-1,c));
        if (c<7 && PieceColors[ board[r][c+1] ]!= player)
            moves.add(new ChessMove(r,c,r,c+1));
        if (c>0 && PieceColors[ board[r][c-1] ]!= player)
            moves.add(new ChessMove(r,c,r,c-1));

        if (r<7 && c<7 && PieceColors[ board[r+1][c+1]]!= player)
            moves.add(new ChessMove(r,c,r+1,c+1));
        if (r>0 && c>0&& PieceColors[ board[r-1][c-1]]!= player)
            moves.add(new ChessMove(r,c,r-1,c-1));
        if (c<7 && r>0 && PieceColors[ board[r-1][c+1]]!= player)
            moves.add(new ChessMove(r,c,r-1,c+1));
        if (c>0 && r<7 && PieceColors[ board[r+1][c-1]]!= player)
            moves.add(new ChessMove(r,c,r+1,c-1));

        return moves;
    }


}
