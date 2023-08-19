package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class Pawn extends Piece {
    
    public Pawn(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Square start, Square end) {
        //directions based on color
        int direction = this.isWhite() ? 1 : -1;
        //starting position based on color
        int startingPos = this.isWhite() ? 6 : 1;

        //x y delta coordinates 
        int x = Math.abs(start.getY() - end.getY());
        int y = start.getX() - end.getX();

        //capturing
        if (x == 1 && y == direction) {
            //captured piece is there and not white
            if (end.getPiece() != null && end.getPiece().isWhite() != this.isWhite()) {
                return true;
            }
            return false;
        }

        //moving
        if (start.getX() == startingPos) {
            if (y == direction*2) {
                return true;
            }
        }
        if (y == direction && end.getPiece() == null) {
            return true;
        }

        //TO-DO: en passant & promoting

        return false;
    }

    public boolean isCastlingMove(Square start, Square end) {
        return false;
    }
}
