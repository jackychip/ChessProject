package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class Rook extends Piece {

    public boolean moved = false;

    public Rook(boolean white) {
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

        //non-straight movement
        if (x * y != 0 || (x == 0 && y == 0)) {
            return false;
        }

        if (pieceInRoute(board, start, end)) {
            return false;
        }

        moved = true;
        return true;
    }

    public boolean pieceInRoute(Board board, Square start, Square end) {

        //get direction of move
        boolean isFile = Math.abs(start.getY() - end.getY()) == 0 ? true : false;

        //checking if a piece is blocking move
        if (isFile) {
            int direction = start.getX() < end.getX() ? 1 : -1;
            for(int i = 1; i < Math.abs(start.getX() - end.getX()); ++i){
                if(board.getBox(start.getX() + i * direction, start.getY()).getPiece() != null) {
                    return true;
                }
            }
        }
        else {
            int direction = start.getY() < end.getY() ? 1 : -1;
            for(int i = 1; i < Math.abs(start.getY() - end.getY()); ++i){
                if(board.getBox(start.getX(), start.getY() + i * direction).getPiece() != null) {
                    return true;
                }
            }
        }


        return false;
    }

    public boolean isCastlingMove(Square start, Square end) {
        return false;
    }
}
