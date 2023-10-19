package ChessPieces;

import java.util.ArrayList;
import java.util.List;

import Handlers.Board;
import Handlers.Check;
import Handlers.Move;
import Handlers.Piece;
import Handlers.Square;

public class Pawn extends Piece {

    private boolean isMoved = false;
    private int direction;
    private int startingPos;
    
    public Pawn(boolean white) {
        super(white);
        direction = white ? -1 : 1;
        startingPos = white ? 6 : 1;
    }

    public boolean canMove(Board board, Square start, Square end, Check check) {

        //x y delta
        int x = start.getX() - end.getX();
        int y = start.getY() - end.getY();

        //capturing
        if (canTake(start, end)) {
            return true;
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

        //moving
        if (start.getY() == startingPos && y == -direction*2 && x == 0) {
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
        for(int i = 1; i < Math.abs(start.getY() - end.getY()) + 1; ++i){
            if(board.getBox(start.getX(), start.getY() + i * direction).getPiece() != null) {
                return true;
            }
        }

        return false;
    }

    public boolean canTake(Square start, Square end) {
        
        if (Math.abs(start.getX() - end.getX()) == 1 && start.getY() - end.getY() == -direction) {
            if (end.getPiece() != null && end.getPiece().isWhite() != this.isWhite()) {
                return true;
            }
        }

        //en passent

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
