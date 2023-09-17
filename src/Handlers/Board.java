package Handlers;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import ChessPieces.Bishop;
import ChessPieces.King;
import ChessPieces.Knight;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import ChessPieces.Rook;

public class Board {
    public Check whiteCheck;
    public Check blackCheck;
    public Graphics graphics;
    Square[][] squares = new Square[8][8];

    public Board() {
        this.resetBoard();
    }

    public Square getBox(int x, int y) {
        return squares[x][y];
    }

    public Check getCheckObject(boolean isWhite) {
        return isWhite ? whiteCheck : blackCheck;
    }

    private void resetBoard() {
        //black pieces
        squares[0][0] = new Square(0, 0, new Rook(false));
        squares[1][0] = new Square(1, 0, new Knight(false));
        squares[2][0] = new Square(2, 0, new Bishop(false));
        squares[3][0] = new Square(3, 0, new Queen(false));
        squares[4][0] = new Square(4, 0, new King(false));
        blackCheck = new Check(squares[4][0]);
        squares[5][0] = new Square(5, 0, new Bishop(false));
        squares[6][0] = new Square(6, 0, new Knight(false));
        squares[7][0] = new Square(7, 0, new Rook(false));
        
        //black pawns
        squares[0][1] = new Square(0, 1, new Pawn(false));
        squares[1][1] = new Square(1, 1, new Pawn(false));
        squares[2][1] = new Square(2, 1, new Pawn(false));
        squares[3][1] = new Square(3, 1, new Pawn(false));
        squares[4][1] = new Square(4, 1, new Pawn(false));
        squares[5][1] = new Square(5, 1, new Pawn(false));
        squares[6][1] = new Square(6, 1, new Pawn(false));
        squares[7][1] = new Square(7, 1, new Pawn(false));

        //white pieces
        squares[0][7] = new Square(0, 7, new Rook(true));
        squares[1][7] = new Square(1, 7, new Knight(true));
        squares[2][7] = new Square(2, 7, new Bishop(true));
        squares[3][7] = new Square(3, 7, new Queen(true));
        squares[4][7] = new Square(4, 7, new King(true));
        whiteCheck = new Check(squares[4][7]);
        squares[5][7] = new Square(5, 7, new Bishop(true));
        squares[6][7] = new Square(6, 7, new Knight(true));
        squares[7][7] = new Square(7, 7, new Rook(true));
        
        //white pawns
        squares[0][6] = new Square(0, 6, new Pawn(true));
        squares[1][6] = new Square(1, 6, new Pawn(true));
        squares[2][6] = new Square(2, 6, new Pawn(true));
        squares[3][6] = new Square(3, 6, new Pawn(true));
        squares[4][6] = new Square(4, 6, new Pawn(true));
        squares[5][6] = new Square(5, 6, new Pawn(true));
        squares[6][6] = new Square(6, 6, new Pawn(true));
        squares[7][6] = new Square(7, 6, new Pawn(true));

        //init remaining squares w/o pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                squares[i][j] = new Square(i, j, null);
            }
        }

        //Chess Graphics
        graphics = new Graphics();
        JFrame frame = graphics;
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

    }
}
