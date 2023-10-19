package ChessPieces;

import java.util.ArrayList;
import java.util.List;

import Handlers.Board;
import Handlers.Check;
import Handlers.Move;
import Handlers.Piece;
import Handlers.Square;

public class King extends Piece {

    private boolean castled = false;
    private int startingPos;

    public King(boolean white) {
        super(white);
        startingPos = white ? 7 : 0;
    }

    private boolean isCastled() {
        return castled;
    }

    public void setCastled(boolean castled) {
        this.castled = castled;
    }

    public boolean canMove(Board board, Square start, Square end, Check check) {

        //captured piece is not own piece
        if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }

        //check after move
        Piece temp = end.getPiece();

        end.setPiece(start.getPiece());
        start.setPiece(null);

        check.kingPos = end;

        if (check.isChecked(board, check.kingPos) > 0) {

            start.setPiece(end.getPiece());
            end.setPiece(temp);

            check.kingPos = start;

            return false;
        }

        check.kingPos = start;

        start.setPiece(end.getPiece());
        end.setPiece(temp);

        //x y delta
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());

        //castling
        if (this.canCastle(board, start, end)) {
            return true;
        }

        //ensure king only moves 1 square at a time
        if (!(x < 2 && y < 2)) {
            return false;
        }

        return true;
    }

    public boolean canCastle(Board board, Square start, Square end) {

        //check if castled already
        if (this.isCastled()) {
            // System.out.println("castled already");
            return false;
        }
        
        //check that start and end square indicate intention to castle
        if (Math.abs(start.getX() - end.getX()) != 2) {
            return false;
        }

        //check that king and rook are on same file
        if (start.getY() - end.getY() != 0) {
            return false;
        }

        //check if any pieces are obstructing King and Rook
        if (start.getX() < end.getX()) {   
            for (int i = 5; i < 7; i++) {
                if (board.getBox(i, startingPos).getPiece() != null) {
                    return false;
                }
            }
        }
        else {
            for (int i = 3; i > 0; i--) {
                if (board.getBox(i, startingPos).getPiece() != null) {
                    return false;
                }
            }
        }

        return true;
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
