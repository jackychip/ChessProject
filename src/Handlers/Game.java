package Handlers;

import java.util.ArrayList;
import java.util.List;

import ChessPieces.King;
import Enums.Status;

public class Game {
    public Player[] players = new Player[2];
    private Board board;
    public Player currentTurn;
    private Status status;
    private List<Move> movesPlayed = new ArrayList<>();

    public Game() {
        
    }
  
    void initialize(Player p1, Player p2)
    {
        players[0] = p1;
        players[1] = p2;
  
        board = new Board();

        movesPlayed.clear();
  
        if (p1.isWhiteSide()) {
            this.currentTurn = p1;
        }
        else {
            this.currentTurn = p2;
        }
        
    }
  
    public boolean isEnd()
    {
        return this.status != Status.ACTIVE;
    }
  
    public Status getStatus()
    {
        return this.status;
    }
  
    public void setStatus(Status status)
    {
        this.status = status;
    }
  
    public boolean playerMove(Player player, int startX, int startY, int endX, int endY)
    {
        if (startX == endX && startY == endY) {
            return false;
        }
        System.out.println("coordinates: " + startX + " " + startY + " " + endX + " " + endY);
        Square startBox = board.getBox(startX, startY);
        Square endBox = board.getBox(endX, endY);
        System.out.println("pieces: " + startBox.getPiece() + " " + endBox.getPiece());
        Move move = new Move(player, startBox, endBox);
        return this.makeMove(move, player);
    }
  
    private boolean makeMove(Move move, Player player)
    {
        Piece sourcePiece = move.getStart().getPiece();
        if (sourcePiece == null) {
            return false;
        }

        System.out.println("white? " + sourcePiece.isWhite() + " " + player.isWhiteSide());
  
        // valid player?
        if (player != currentTurn) {
            System.out.println("invalid player");
            return false;
        }
        
        //valid color?
        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            System.out.println("invalid piece color");
            return false;
        }
  
        // valid move?
        if (!sourcePiece.canMove(board, move.getStart(), move.getEnd())) {
            System.out.println("invalid move");
            return false;
        }
  
        // kill?
        Piece destPiece = move.getEnd().getPiece();
        
        if (destPiece != null) {
            System.out.println("piece killed");
            destPiece.setKilled(true);
            move.setPieceKilled(destPiece);
        }
  
        // castling?
        if (sourcePiece instanceof King && sourcePiece.isCastlingMove(move.getStart(), move.getEnd())) {
            System.out.println("castled");
            move.setCastlingMove(true);
        }
  
        // store the move
        movesPlayed.add(move);
  
        // move piece from the start square to end square
        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);
  
        if (destPiece instanceof King) {
            System.out.println("game won by white");
            if (player.isWhiteSide()) {
                this.setStatus(Status.WHITE_WIN);
                System.out.println("game won by white");
            }
            else {
                this.setStatus(Status.BLACK_WIN);
                System.out.println("game won by black");
            }
        }
  
        // set the current turn to the other player
        if (this.currentTurn == players[0]) {
            this.currentTurn = players[1];
        }
        else {
            this.currentTurn = players[0];
        }
        
        return true;
    }
}
