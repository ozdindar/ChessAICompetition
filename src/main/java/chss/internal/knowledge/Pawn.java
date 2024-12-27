package chss.internal.knowledge;





import base.Move;

import java.util.ArrayList;
import java.util.List;

import static chss.internal.knowledge.Chess.*;
import static chss.internal.knowledge.PieceType.PieceColors;


class Pawn extends ChessPiece
{
    @Override
    boolean canMove(int[][] board, int player, int fr, int fc, int tr, int tc) {

        if (  tr>=8 || tc>=8 || tr<0 || tc<0 || PieceColors[board[tr][tc]]==player)
            return false;

        if (player==WHITE) {
            if (tr-fr==1)
                return ( (tc==fc && board[tr][tc]== 0 ) ||
                         (Math.abs(tc-fc)==1 && PieceColors[board[tr][tc]]== opponent(player) ) );

            return (tr-fr == 2 && board[fr+1][fc]== 0 && board[tr][tc]==0 && fr ==1);
        }
        else {
            if (tr-fr==-1)
                return ( (tc==fc && board[tr][tc]== 0 ) ||
                        (Math.abs(tc-fc)==1 &&  PieceColors[board[tr][tc]]== opponent(player) ) );

            return (tr-fr == -2 && board[fr-1][fc]== 0 &&  board[tr][tc]==0 && fr ==6);
        }


    }

    @Override
    protected List<Move> getMovesWithoutKingCheckControl(int[][] board, int player, int r, int c) {
        List<Move> moves= new ArrayList<>();
        if (player==WHITE && canMove(board,player,r,c ,r+1,c))
            moves.add(new ChessMove(r,c,r+1,c));
        if (player==WHITE && canMove(board,player,r,c ,r+2,c))
            moves.add(new ChessMove(r,c,r+2,c));
        if (player==WHITE && canMove(board,player,r,c ,r+1,c+1))
            moves.add(new ChessMove(r,c,r+1,c+1));
        if (player==WHITE && canMove(board,player,r,c ,r+1,c-1))
            moves.add(new ChessMove(r,c,r+1,c-1));

        if (player==BLACK && canMove(board,player,r,c ,r-1,c))
            moves.add(new ChessMove(r,c,r-1,c));
        if (player==BLACK && canMove(board,player,r,c ,r-2,c))
            moves.add(new ChessMove(r,c,r-2,c));
        if (player==BLACK && canMove(board,player,r,c ,r-1,c-1))
            moves.add(new ChessMove(r,c,r-1,c-1));
        if (player==BLACK && canMove(board,player,r,c ,r-1,c+1))
            moves.add(new ChessMove(r,c,r-1,c+1));


        return moves;
    }
}
