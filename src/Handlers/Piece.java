package Handlers;

import java.util.List;

public abstract class Piece {
    private boolean killed;
    private boolean white;

    protected Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public boolean isKilled() {
        return killed;
    }

    public abstract boolean canMove(Board board, Square start, Square end, Check check);
    public abstract List<Move> generatePossibleMoves(Board board, Square start, boolean showHighlights, Check check);
}
