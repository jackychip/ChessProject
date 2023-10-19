package ChessPieces;

import java.util.ArrayList;
import java.util.List;

import Handlers.Board;
import Handlers.Check;
import Handlers.Move;
import Handlers.Piece;
import Handlers.Square;

public class Queen extends Piece {

    public Queen(boolean white) {
        super(white);
    }

    public boolean canMove(Board board, Square start, Square end, Check check) {
        return new Rook(this.isWhite()).canMove(board, start, end, check) || new Bishop(this.isWhite()).canMove(board, start, end, check);
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
