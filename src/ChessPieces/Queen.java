package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class Queen extends Piece {

    public Queen(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Square start, Square end) {
        return new Rook(this.isWhite()).canMove(board, start, end) || new Bishop(this.isWhite()).canMove(board, start, end);
    }

    public boolean isCastlingMove(Square start, Square end) {
        return false;
    }
}   
