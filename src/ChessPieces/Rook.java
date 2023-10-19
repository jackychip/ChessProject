package ChessPieces;

import java.util.ArrayList;
import java.util.List;

import Handlers.Board;
import Handlers.Check;
import Handlers.Move;
import Handlers.Piece;
import Handlers.Square;

public class Rook extends Piece {

    public boolean moved = false;

    public Rook(boolean white) {
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

        //non-straight movement
        if (x * y != 0 || (x == 0 && y == 0)) {
            return false;
        }

        if (pieceInRoute(board, start, end)) {
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

        moved = true;
        return true;
    }

    public boolean pieceInRoute(Board board, Square start, Square end) {

        //get direction of move
        boolean isFile = Math.abs(start.getY() - end.getY()) == 0;

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
