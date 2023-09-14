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
        squares[0][1] = new Square(0, 1, new Knight(false));
        squares[0][2] = new Square(0, 2, new Bishop(false));
        squares[0][3] = new Square(0, 3, new Queen(false));
        squares[0][4] = new Square(0, 4, new King(false));
        blackCheck = new Check(squares[0][4]);
        squares[0][5] = new Square(0, 5, new Bishop(false));
        squares[0][6] = new Square(0, 6, new Knight(false));
        squares[0][7] = new Square(0, 7, new Rook(false));
        
        //black pawns
        squares[1][0] = new Square(1, 0, new Pawn(false));
        squares[1][1] = new Square(1, 1, new Pawn(false));
        squares[1][2] = new Square(1, 2, new Pawn(false));
        squares[1][3] = new Square(1, 3, new Pawn(false));
        squares[1][4] = new Square(1, 4, new Pawn(false));
        squares[1][5] = new Square(1, 5, new Pawn(false));
        squares[1][6] = new Square(1, 6, new Pawn(false));
        squares[1][7] = new Square(1, 7, new Pawn(false));

        //white pieces
        squares[7][0] = new Square(7, 0, new Rook(true));
        squares[7][1] = new Square(7, 1, new Knight(true));
        squares[7][2] = new Square(7, 2, new Bishop(true));
        squares[7][3] = new Square(7, 3, new Queen(true));
        squares[7][4] = new Square(7, 4, new King(true));
        whiteCheck = new Check(squares[7][4]);
        squares[7][5] = new Square(7, 5, new Bishop(true));
        squares[7][6] = new Square(7, 6, new Knight(true));
        squares[7][7] = new Square(7, 7, new Rook(true));
        
        //white pawns
        squares[6][0] = new Square(6, 0, new Pawn(true));
        squares[6][1] = new Square(6, 1, new Pawn(true));
        squares[6][2] = new Square(6, 2, new Pawn(true));
        squares[6][3] = new Square(6, 3, new Pawn(true));
        squares[6][4] = new Square(6, 4, new Pawn(true));
        squares[6][5] = new Square(6, 5, new Pawn(true));
        squares[6][6] = new Square(6, 6, new Pawn(true));
        squares[6][7] = new Square(6, 7, new Pawn(true));

        //init remaining squares w/o pieces
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
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
