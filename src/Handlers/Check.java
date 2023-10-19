package Handlers;

import ChessPieces.Bishop;
import ChessPieces.King;
import ChessPieces.Knight;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import ChessPieces.Rook;

public class Check {

    public Square kingPos;

    public Check(Square kingPos) {
        this.kingPos = kingPos;
    }

    public boolean isCheckmated(Board board, boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBox(i, j).getPiece() != null && board.getBox(i, j).getPiece().isWhite() == isWhite) {
                    if (!board.getBox(i, j).getPiece().generatePossibleMoves(board, board.getBox(i, j), false, this).isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public int isChecked(Board board, Square sq) 
    {
        int numOfChecks = 0;

        // check for pawns
        if (this.pawnCheck(board, sq)) {
            // System.out.println("pawn check"  + " " + sq.getX() + " " + sq.getY());
            numOfChecks++;
        }

        // check for knights
        if (this.knightCheck(board, sq)) {
            // System.out.println("knight check"  + " " + sq.getX() + " " + sq.getY());
            numOfChecks++;
        }

        // check for bishops
        if (this.bishopCheck(board, sq)) {
            // System.out.println("bishop check" + " " + sq.getX() + " " + sq.getY());
            numOfChecks++;
        }

        // check for rooks
        if (this.rookCheck(board, sq)) {
            // System.out.println("rook check"  + " " + sq.getX() + " " + sq.getY());
            numOfChecks++;
        }

        // check for queens
        if (this.queenCheck(board, sq)) {
            // System.out.println("queen check"  + " " + sq.getX() + " " + sq.getY());
            numOfChecks++;
        }

        if (this.kingBlock(board, sq)) {
            numOfChecks++;
        }

        // System.out.println("NOC: " + numOfChecks);

        return numOfChecks;
    }

    private boolean pawnCheck(Board board, Square sq) {
        // System.out.println(kingPos.getX() + " " + kingPos.getY());
        int pawnDirection = kingPos.getPiece().isWhite() ? -1 : 1;
        final int[] checkSquares = { -1, 1 };

        for (int square : checkSquares) {
            if (sq.getX() + square < 8 && sq.getX() + square >= 0 && sq.getY() + pawnDirection < 8
                    && sq.getY() + pawnDirection >= 0
                    && board.getBox(sq.getX() + square, sq.getY() + pawnDirection).getPiece() != null
                    && board.getBox(sq.getX() + square, sq.getY() + pawnDirection).getPiece().isWhite() != kingPos
                            .getPiece().isWhite()
                    && board.getBox(sq.getX() + square, sq.getY() + pawnDirection).getPiece() instanceof Pawn) {
                return true;
            }
        }

        return false;
    }

    private boolean knightCheck(Board board, Square sq) {
        final int[] xPositions = { -2, -1, -2, -1, 2, 1, 2, 1 };
        final int[] yPositions = { -1, -2, 1, 2, -1, -2, 1, 2 };

        for (int i = 0; i < 8; i++) {
            if (sq.getX() + xPositions[i] < 8 && sq.getX() + xPositions[i] > 0 && sq.getY() + yPositions[i] < 8
                    && sq.getY() + yPositions[i] > 0
                    && board.getBox(sq.getX() + xPositions[i], sq.getY() + yPositions[i]).getPiece() != null
                    && board.getBox(sq.getX() + xPositions[i], sq.getY() + yPositions[i]).getPiece()
                            .isWhite() != kingPos.getPiece().isWhite()
                    && board.getBox(sq.getX() + xPositions[i], sq.getY() + yPositions[i])
                            .getPiece() instanceof Knight) {
                return true;
            }
        }

        return false;
    }

    private boolean bishopCheck(Board board, Square sq) {
        final int[] yDirections = { 1, 1, -1, -1 };
        final int[] xDirections = { -1, 1, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int xComparison = xDirections[i] < 0 ? sq.getX() : 8 - sq.getX();
            int yComparison = yDirections[i] < 0 ? sq.getY() : 8 - sq.getY();
            int limit = yComparison < xComparison ? yComparison : xComparison;

            // System.out.println("yCom: " + yComparison);
            // System.out.println("xCom: " + xComparison);
            // System.out.println("limit: " + limit);

            for (int j = 1; j < limit; ++j) {
                // System.out.println("SQx: " + (sq.getX()));
                // System.out.println("SQy: " + (sq.getY()));
                // System.out.println("x: " + (sq.getX() + j * xDirections[i]));
                // System.out.println("y: " + (sq.getY() + j * yDirections[i]));
                if (board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j * yDirections[i]).getPiece() != null) {
                    // System.out.println(board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j
                    // * yDirections[i]).getPiece());
                    if (board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j * yDirections[i]).getPiece()
                            .isWhite() != kingPos.getPiece().isWhite()) {
                        if (board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j * yDirections[i])
                                .getPiece() instanceof Bishop) {
                            return true;
                        }
                        else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return false;
    }

    private boolean rookCheck(Board board, Square sq) {

        // left
        // System.out.println("x" + sq.getY());
        // System.out.println("y" + sq.getX());
        for (int i = sq.getX() - 1; i >= 0; --i) {
            // System.out.println("up: " + i);
            if (board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Rook) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        // right
        for (int i = sq.getX() + 1; i <= 7; ++i) {
            // System.out.println("down: " + i);
            if (board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Rook) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        // downward
        for (int i = sq.getY() + 1; i <= 7; ++i) {
            // System.out.println("right: " + i);
            if (board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Rook) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        // upward
        for (int i = sq.getY() - 1; i >= 0; --i) {
            // System.out.println("left: " + i);
            if (board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Rook) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return false;
    }

    private boolean queenCheck(Board board, Square sq) {
        final int[] yDirections = { 1, 1, -1, -1 };
        final int[] xDirections = { -1, 1, -1, 1 };

        for (int i = 0; i < 4; i++) {
            int xComparison = xDirections[i] < 0 ? sq.getX() : 8 - sq.getX();
            int yComparison = yDirections[i] < 0 ? sq.getY() : 8 - sq.getY();
            int limit = yComparison < xComparison ? yComparison : xComparison;

            for (int j = 1; j < limit; ++j) {
                if (board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j * yDirections[i]).getPiece() != null) {
                    if (board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j * yDirections[i]).getPiece()
                            .isWhite() != kingPos.getPiece().isWhite()) {
                        if (board.getBox(sq.getX() + j * xDirections[i], sq.getY() + j * yDirections[i])
                                .getPiece() instanceof Queen) {
                            return true;
                        }
                        else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        for (int i = sq.getX() - 1; i >= 0; --i) {
            if (board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Queen) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = sq.getX() + 1; i <= 7; ++i) {
            if (board.getBox(i, sq.getY()).getPiece() != null) {
                if (board.getBox(i, sq.getY()).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(i, sq.getY()).getPiece() instanceof Queen) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = sq.getY() + 1; i <= 7; ++i) {
            if (board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Queen) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = sq.getY() - 1; i >= 0; --i) {
            if (board.getBox(sq.getX(), i).getPiece() != null) {
                if (board.getBox(sq.getX(), i).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX(), i).getPiece() instanceof Queen) {
                        return true;
                    }
                    else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return false;
    }

    private boolean kingBlock(Board board, Square sq) {
        int[] xDirections = { 0, 1, 1, 1, 0, -1, -1, -1 };
        int[] yDirections = { -1, -1, 0, 1, 1, 1, 0, -1 };

        for (int i = 0; i < 8; i++) {
            if (sq.getX() + xDirections[i] < 0 || sq.getX() + xDirections[i] > 7 || sq.getY() + yDirections[i] < 0
                    || sq.getY() + yDirections[i] > 7) {
                continue;
            }

            if (board.getBox(sq.getX() + xDirections[i], sq.getY() + yDirections[i]).getPiece() != null) {
                if (board.getBox(sq.getX() + xDirections[i], sq.getY() + yDirections[i]).getPiece().isWhite() != kingPos.getPiece().isWhite()) {
                    if (board.getBox(sq.getX() + xDirections[i], sq.getY() + yDirections[i]).getPiece() instanceof King) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}