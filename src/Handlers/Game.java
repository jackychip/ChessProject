package Handlers;

import java.util.ArrayList;
import java.util.List;

import ChessPieces.King;
import Enums.Status;

public class Game {
    public Player[] players = new Player[2];
    private Board board;
    private Player currentTurn;
    private Status status;
    private List<Move> movesPlayed = new ArrayList<>();

    public Game(Player p1, Player p2) {
        this.initialize(p1, p2);
    }
  
    private void initialize(Player p1, Player p2)
    {
        players[0] = p1;
        players[1] = p2;
  
        board = new Board();

        this.setStatus(Status.ACTIVE);
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

        Square startBox = board.getBox(startX, startY);
        Square endBox = board.getBox(endX, endY);
        Move move = new Move(player, startBox, endBox);
        return this.makeMove(move, player);
    }
  
    private boolean makeMove(Move move, Player player)
    {

        Piece sourcePiece = move.getStart().getPiece();
        if (sourcePiece == null) {
            return false;
        }
  
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

        Piece temp = move.getEnd().getPiece();

        //move piece
        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);

        Check check = board.getCheckObject(player.isWhiteSide());

        //temp move king to ending square
        if (sourcePiece instanceof King) {
            check.kingPos = move.getEnd();
        }

        if (check.isChecked(board, check.kingPos)) {
            System.out.println("end checked");

            if (sourcePiece instanceof King) {
                check.kingPos = move.getStart();
            }

            move.getStart().setPiece(move.getEnd().getPiece());
            move.getEnd().setPiece(temp);

            return false;
        }
  
        // store the move
        movesPlayed.add(move);
  
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
