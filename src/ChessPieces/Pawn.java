package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class Pawn extends Piece {

    private int direction;
    private int startingPos;
    
    public Pawn(boolean white) {
        super(white);
        direction = white ? -1 : 1;
        startingPos = white ? 6 : 1;
    }

    public boolean canMove(Board board, Square start, Square end) {

        //y delta
        int y = start.getX() - end.getX();
        int x = start.getY() - end.getY();

        //capturing
        if (canTake(start, end)) {
            return true;
        }

        if (pieceInRoute(board, start, end)) {
            return false;
        }

        //moving
        if (start.getX() == startingPos && y == -direction*2 && x == 0) {
            return true;
        }
        
        if (y == -direction && end.getPiece() == null && x == 0) {
            return true;
        }

        //TO-DO: en passant & promoting

        return false;
    }

    public boolean pieceInRoute(Board board, Square start, Square end) {

        //checking if a piece is blocking move
        for(int i = 1; i < Math.abs(start.getX() - end.getX()) + 1; ++i){
            if(board.getBox(start.getX() + i * direction, start.getY()).getPiece() != null) {
                return true;
            }
        }

        return false;
    }

    public boolean canTake(Square start, Square end) {
        
        if (Math.abs(start.getY() - end.getY()) == 1 && start.getX() - end.getX() == -direction) {
            if (end.getPiece() != null && end.getPiece().isWhite() != this.isWhite()) {
                return true;
            }
        }

        return false;
    }
}
