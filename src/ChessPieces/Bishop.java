package ChessPieces;

import java.util.ArrayList;
import java.util.List;

import Handlers.Board;
import Handlers.Check;
import Handlers.Move;
import Handlers.Piece;
import Handlers.Square;

public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Square start, Square end, Check check) {

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

        //check after move
        Piece temp = end.getPiece();

        end.setPiece(start.getPiece());
        start.setPiece(null);

        if (check.isChecked(board, check.kingPos) > 0) {

            start.setPiece(end.getPiece());
            end.setPiece(temp);

            return false;
        }

        start.setPiece(end.getPiece());
        end.setPiece(temp);

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

    public List<Move> generatePossibleMoves(Board board, Square start, boolean showHighlights, Check check) {
        List<Move> moves = new ArrayList<Move>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.canMove(board, start, board.getBox(i, j), check)) {
                    moves.add(new Move(start, board.getBox(i, j)));
                }
            }
        }

        if (showHighlights) {
            board.getGraphicsObject().highlightPossibleMoves(moves);
        }

        return moves;
    }
}
