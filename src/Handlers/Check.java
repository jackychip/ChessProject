package Handlers;

import ChessPieces.Bishop;
import ChessPieces.Knight;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import ChessPieces.Rook;

public class Check {

    public Square kingPos;

    public Check(Square kingPos) {
        this.kingPos = kingPos;
    }

    public boolean isChecked(Board board, Square sq) {

        //check for pawns
        if (this.pawnCheck(board, sq)) {
            System.out.println("pawn check");
            return true;
        }

        //check for knights
        if (this.knightCheck(board, sq)) {
            System.out.println("knight check");
            return true;
        }

        //check for bishops
        if (this.bishopCheck(board, sq)) {
            System.out.println("bishop check");
            return true;
        }

        //check for rooks
        if (this.rookCheck(board, sq)) {
            System.out.println("rook check");
            return true;
        }

        //check for queens
        if (this.queenCheck(board, sq)) {
            System.out.println("queen check");
            return true;
        }

        return false;
    }
    
    private boolean pawnCheck(Board board, Square sq) {
        int pawnDirection = kingPos.getPiece().isWhite() ? -1 : 1;
        final int[] checkSquares = {-1 , 1};
        
        for (int square : checkSquares) {
            if (sq.getX() + pawnDirection < 8 && sq.getX() + pawnDirection >= 0 && sq.getY() + square < 8 && sq.getY() + square >= 0
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

    private boolean bishopCheck(Board board, Square sq) {
        final int[] xDirections = {-1, 1, -1, 1};
        final int[] yDirections = {1, 1, -1, -1};

        for (int i = 0; i < 4; i++) {
            int yComparison = yDirections[i] < 0 ? sq.getX() : 8 - sq.getX();
            int xComparison = xDirections[i] < 0 ? sq.getY() : 8 - sq.getY();
            int limit = yComparison < xComparison ? yComparison : xComparison;

            // System.out.println("limit: " + limit);

            for (int j = 1; j < limit; ++j) {
                // System.out.println("x: " + (sq.getX() + j * yPositions[i]));
                // System.out.println("y: " + (sq.getY() + j * xPositions[i]));
                if(board.getBox(sq.getX() + j * yDirections[i], sq.getY() + j * xDirections[i]).getPiece() != null) {
                    if (board.getBox(sq.getX() + j * yDirections[i], sq.getY() + j * xDirections[i]).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                        // System.out.println(board.getBox(sq.getX() + j * yPositions[i], sq.getY() + j * xPositions[i]).getPiece());
                        if (board.getBox(sq.getX() + j * yDirections[i], sq.getY() + j * xDirections[i]).getPiece() instanceof Bishop) {
                            return true;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
        }

        return false;
    }

    private boolean rookCheck (Board board, Square sq) {

        //upward
        // System.out.println("x" + sq.getY());
        // System.out.println("y" + sq.getX());
        for (int i = sq.getX() - 1; i >= 0; --i) {
            // System.out.println("up: " + i);
            if(board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Rook) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        //downward
        for (int i = sq.getX() + 1; i <= 7; ++i) {
            // System.out.println("down: " + i);
            if(board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Rook) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        //right
        for (int i = sq.getY() + 1; i <= 7; ++i) {
            // System.out.println("right: " + i);
            if(board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Rook) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        //left
        for (int i = sq.getY() - 1; i >= 0; --i) {
            // System.out.println("left: " + i);
            if(board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Rook) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        return false;
    }

    private boolean queenCheck(Board board, Square sq) {
        final int[] xDirections = {-1, 1, -1, 1};
        final int[] yDirections = {1, 1, -1, -1};

        for (int i = 0; i < 4; i++) {
            int yComparison = yDirections[i] < 0 ? sq.getX() : 8 - sq.getX();
            int xComparison = xDirections[i] < 0 ? sq.getY() : 8 - sq.getY();
            int limit = yComparison < xComparison ? yComparison : xComparison;

            for (int j = 1; j < limit; ++j) {
                if(board.getBox(sq.getX() + j * yDirections[i], sq.getY() + j * xDirections[i]).getPiece() != null) {
                    if (board.getBox(sq.getX() + j * yDirections[i], sq.getY() + j * xDirections[i]).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                        if (board.getBox(sq.getX() + j * yDirections[i], sq.getY() + j * xDirections[i]).getPiece() instanceof Queen) {
                            return true;
                        }
                    }
                    else {
                        break;
                    }
                }
            }

        }

        //upward
        for (int i = sq.getX() - 1; i >= 0; --i) {
                if(board.getBox(i, sq.getY()).getPiece() != null) {
                    if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                        if (board.getBox(i, sq.getY()).getPiece() instanceof Queen) {
                            return true;
                        }
                    }
                else {
                    break;
                }
            }
        }
        
        //downward
        for (int i = sq.getX() + 1; i <= 7; ++i) {
            if(board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Queen) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        //right
        for (int i = sq.getY() + 1; i <= 7; ++i) {
            if(board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Queen) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        //left
        for (int i = sq.getY() - 1; i >= 0; --i) {
            if(board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Queen) {
                        return true;
                    }
                }
                else {
                    break;
                }
            }
        }

        return false;
    }

}
