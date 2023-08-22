package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class Pawn extends Piece {

    private int direction;
    private int startingPos;
    
    public Pawn(boolean white) {
        super(white);
        direction = white ? 1 : -1;
        startingPos = white ? 6 : 1;
    }

    public boolean canMove(Board board, Square start, Square end) {

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
}
