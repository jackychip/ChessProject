package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class King extends Piece {

    public King(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Square start, Square end) {

        //captured piece is not own piece
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        //x y delta coordinates 
        int x = Math.abs(start.getY() - end.getY());
        int y = Math.abs(start.getX() - end.getX());

        //ensure king only moves 1 square at a time
        if (x < 2 && y < 2) {
            return true;
        }

        return false;
    }

    public boolean isCastlingMove(Square start, Square end) {
        return false;
    }
}
