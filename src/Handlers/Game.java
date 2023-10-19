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

    public Game(Player p1, Player p2) {
        this.initialize(p1, p2);
    }

    private void initialize(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;

        board = new Board();

        this.setStatus(Status.ACTIVE);
        movesPlayed.clear();

        if (p1.isWhiteSide()) {
            this.currentTurn = p1;
        } else {
            this.currentTurn = p2;
        }

    }

    public Board getBoardObject() {
        return board;
    }

    public boolean isEnd() {
        return this.status != Status.ACTIVE;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean playerMove(Player player, int startX, int startY, int endX, int endY) {
        Square startBox = board.getBox(startX, startY);
        Square endBox = board.getBox(endX, endY);
        Move move = new Move(startBox, endBox);
        return this.makeMove(move, player);
    }

    private boolean makeMove(Move move, Player player) {

        // valid player?
        if (player != currentTurn) {
            System.out.println("invalid player");
            return false;
        }

        //get possible moves
        List<Move> possibleMoves = board.getGraphicsObject().getMoves();

        board.getGraphicsObject().resetHighlights(possibleMoves);

        if (move.getStart().getX() == move.getEnd().getX() && move.getStart().getY() == move.getEnd().getY()) {
            return false;
        }

        //sourcePiece isn't null
        Piece sourcePiece = move.getStart().getPiece();
        if (sourcePiece == null) {
            return false;
        }

        // valid color?
        if (sourcePiece.isWhite() != player.isWhiteSide()) {
            System.out.println("invalid piece color");
            return false;
        }

        // valid move?
        int size = possibleMoves.size();
        int counter = 1;
        for (int i = 0; i < size; i++) {
            if (possibleMoves.get(i).getEnd() != move.getEnd()) {
                counter++;
            }
        }

        if (counter != size) {
            System.out.println("invalid move");
            return false;
        }

        Check check = board.getCheckObject(player.isWhiteSide());

        if (sourcePiece instanceof King) {
            check.kingPos = move.getEnd();
            if (((King) sourcePiece).canCastle(board, move.getStart(), move.getEnd())) {
                int direction = move.getStart().getX() <  move.getEnd().getX() ? -1 : 1;
                int position =  move.getStart().getX() < move.getEnd().getX() ? 7 : 0;
                int startingPos = sourcePiece.isWhite() ? 7 : 0;

                board.getGraphicsObject().movePiece(position, startingPos, move.getEnd().getX() + direction, startingPos);
                board.getBox(move.getEnd().getX() + direction, startingPos)
                        .setPiece(board.getBox(position, startingPos).getPiece());
                board.getBox(position, startingPos).setPiece(null);

                ((King) sourcePiece).setCastled(true);
            }
        }

        // move piece
        move.getEnd().setPiece(move.getStart().getPiece());
        move.getStart().setPiece(null);

        // store the move
        movesPlayed.add(move);

        Check otherCheck = board.getCheckObject(!player.isWhiteSide());
        if (otherCheck.isChecked(board, board.getCheckObject(!player.isWhiteSide()).kingPos) > 0) {
            System.out.println("other player checked");
            if (otherCheck.isCheckmated(board, !this.currentTurn.isWhiteSide())) {
                Status win = player.isWhiteSide() ? Status.WHITE_WIN : Status.BLACK_WIN;
                this.setStatus(win);
                System.out.println(win);
            }
        }

        // set the current turn to the other player
        if (this.currentTurn == players[0]) {
            this.currentTurn = players[1];
        } else {
            this.currentTurn = players[0];
        }

        return true;
    }
}
