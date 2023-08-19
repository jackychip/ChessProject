package Handlers;

public class Move {
    private Player player;
    private Square start;
    private Square end;
    private Piece pieceKilled;
    private boolean castlingMove = false;
  
    public Move(Player player, Square start, Square end)
    {
        this.player = player;
        this.start = start;
        this.end = end;
    }

    public Square getStart() {
        return this.start;
    }

    public Square getEnd() {
        return this.end;
    }

    public void setPieceKilled(Piece piece) {
        this.pieceKilled = piece;
    }
  
    public boolean isCastlingMove()
    {
        return this.castlingMove;
    }
  
    public void setCastlingMove(boolean castlingMove)
    {
        this.castlingMove = castlingMove;
    }
}
