package Handlers;

import ChessPieces.Knight;
import ChessPieces.Pawn;

public class Check {

    public Square kingPos;

    public Check(Square kingPos) {
        this.kingPos = kingPos;
    }

    public boolean isChecked(Board board, Square sq) {

        //check for pawns
        if (this.pawnCheck(board, sq)) {
            return true;
        }

        //check for knights
        if (this.knightCheck(board, sq)) {
            return true;
        }

        //check for bishops

        //check for rooks

        //check for queens

        return false;
    }
    
    private boolean pawnCheck(Board board, Square sq) {
        int pawnDirection = kingPos.getPiece().isWhite() ? -1 : 1;
        final int[] checkSquares = {-1 , 1};
        
        for (int square : checkSquares) {
            if (sq.getX() + pawnDirection < 8 && sq.getX() + pawnDirection > 0 && sq.getY() + square < 8 && sq.getY() + square > 0
            && board.getBox(sq.getX() + pawnDirection, sq.getY() + square).getPiece() != null 
            && board.getBox(sq.getX() + pawnDirection, sq.getY() + square).getPiece().isWhite() != kingPos.getPiece().isWhite() 
            && board.getBox(sq.getX() + pawnDirection, sq.getY() + square).getPiece() instanceof Pawn) {
                return true;
            }
        }

        return false;
    }

    private boolean knightCheck(Board board, Square sq) {
        final int[] xPositions = {-2, -1, -2, -1, 2, 1, 2, 1};
        final int[] yPositions = {-1, -2, 1, 2, -1, -2, 1, 2};

        for (int i = 0; i < 8; i++) {
            if (sq.getX() + yPositions[i] < 8 && sq.getX() + yPositions[i] > 0 && sq.getY() + xPositions[i] < 8 && sq.getY() + xPositions[i] > 0
            && board.getBox(sq.getX() + yPositions[i], sq.getY() + xPositions[i]).getPiece() != null 
            && board.getBox(sq.getX() + yPositions[i], sq.getY() + xPositions[i]).getPiece().isWhite() != kingPos.getPiece().isWhite() 
            && board.getBox(sq.getX() + yPositions[i], sq.getY() + xPositions[i]).getPiece() instanceof Knight) {
                return true;
            }
        }

        return false;
    }

}
