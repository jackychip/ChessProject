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

        //x y delta
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());

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
        int dirX = start.getX() < end.getX() ? 1 : -1;
        int dirY = start.getY() < end.getY() ? 1 : -1;

        //checking if a piece is blocking move diagonal
        for(int i = 1; i < Math.abs(start.getX() - end.getX()); ++i){
			if(board.getBox(start.getX() + i * dirX, start.getY() + i * dirY).getPiece() != null) {
				return true;
			}
		}

		return false;
    }
}
