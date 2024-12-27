package chss.internal.knowledge;

import java.awt.geom.Point2D;

import static chss.internal.knowledge.Chess.*;


public enum PieceType {

    Empty,   WKing, WQueen, WRook, WBishop, WKnight, WPawn,
            BKing, BQueen, BRook, BBishop, BKnight, BPawn;

    public static final PieceType[] Pieces = {Empty,   WKing, WQueen, WRook, WBishop, WKnight, WPawn,
                                        BKing, BQueen, BRook, BBishop, BKnight, BPawn};

    public static final int[] PieceColors = { EMPTY, WHITE,WHITE,WHITE,WHITE,WHITE,WHITE, BLACK, BLACK,BLACK,BLACK,BLACK,BLACK};

    public int toPlayer() {
        switch (this){
                case WKing:
                case WQueen:
                case WRook:
                case WBishop:
                case WKnight:
                case WPawn:
                    return WHITE;
                case BKing:
                case BQueen:
                case BRook:
                case BBishop:
                case BKnight:
                case BPawn:
                    return BLACK;
        }
        return EMPTY;

    }
    public Point2D toImgTopLeft() {
        float dx = (float) (1000.0/6.0);

        switch (this)
        {
            case WKing: return new Point2D.Float(0,0);
            case WQueen: return new Point2D.Float(dx*1,0);
            case WBishop: return new Point2D.Float(dx*2,0);
            case WKnight: return new Point2D.Float(dx*3,0);
            case WRook: return new Point2D.Float(dx*4,0);
            case WPawn: return new Point2D.Float(dx*5,0);

            case BKing: return new Point2D.Float(0,161);
            case BQueen: return new Point2D.Float(dx*1,161);
            case BBishop: return new Point2D.Float(dx*2,161);
            case BKnight: return new Point2D.Float(dx*3,161);
            case BRook: return new Point2D.Float(dx*4,161);
            case BPawn: return new Point2D.Float(dx*5,161);
        }
        return new Point2D.Float(0,0);
    }

    public String toImgFile() {
        switch (this)
        {
            case WKing: return "./res/chess/white_king.png";
            case WQueen: return  "./res/chess/white_queen.png";
            case WBishop: return  "./res/chess/white_bishop.png";
            case WKnight: return  "./res/chess/white_knight.png";
            case WRook: return  "./res/chess/white_rook.png";
            case WPawn: return  "./res/chess/white_pawn.png";

            case BKing: return  "./res/chess/black_king.png";
            case BQueen: return  "./res/chess/black_queen.png";
            case BBishop: return  "./res/chess/black_bishop.png";
            case BKnight: return  "./res/chess/black_knight.png";
            case BRook: return  "./res/chess/black_rook.png";
            case BPawn: return "./res/chess/black_pawn.png";
        }
        return "./res/chess/white_pawn.png";
    }


    public char toChar() {


        switch (this)
        {
            case WKing: return 'K';
            case WQueen: return 'Q';
            case WBishop: return 'B';
            case WKnight: return 'N';
            case WRook: return 'R';
            case WPawn: return 'P';

            case BKing: return 'k';
            case BQueen: return 'q';
            case BBishop: return 'b';
            case BKnight: return 'n';
            case BRook: return 'r';
            case BPawn: return 'p';
        }
        return ' ';
    }
}
