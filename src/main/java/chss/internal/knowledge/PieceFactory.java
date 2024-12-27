package chss.internal.knowledge;

class PieceFactory{

    static ChessPiece pieces[] = new ChessPiece[]
            {
                 null, new King(), new Queen(), new Rook(), new Bishop(), new Knight(), new Pawn()  ,
                       new King(), new Queen(), new Rook(), new Bishop(), new Knight(), new Pawn()
            };

    static ChessPiece create(int pieceType)
    {
        return pieces[pieceType];
    }
}
