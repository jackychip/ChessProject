package ChessPieces;

import Handlers.Board;
import Handlers.Piece;
import Handlers.Square;

public class Bishop extends Piece {

    public Bishop(boolean white) {
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

        //non-diagonal movement
        if (x != y) {
            return false;
        }

        //piece blocking move
        if (pieceInDiagonal(board, start, end)) {
            return false;
        }

        return true;
    }

    public boolean pieceInDiagonal(Board board, Square start, Square end) {

        //get direction of move
        int dirX = start.getY() < end.getY() ? 1 : -1;
        int dirY = start.getX() < end.getX() ? 1 : -1;

        //checking if a piece is blocking move diagonal
        for(int i = 1; i < Math.abs(start.getX() - end.getX()); ++i){
			if(board.getBox(start.getX() + i * dirY, start.getY() + i * dirX).getPiece() != null) {
				return true;
			}
		}

		return false;
    }
}
