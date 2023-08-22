package Handlers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Graphics extends JFrame implements MouseListener, MouseMotionListener {

    static JLayeredPane layerHandler;
    static JPanel board;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;

    Integer startSquareX;
    Integer endSquareX;
    Integer startSquareY;
    Integer endSquareY;

    final int SIZE_OF_BOARD = 600;

    Main main = new Main();

    public Graphics() {
        Dimension boardSize = new Dimension(SIZE_OF_BOARD, SIZE_OF_BOARD);
        
        //init layered pane for layer manipulation when moving pieces
        layerHandler = new JLayeredPane();
        getContentPane().add(layerHandler);
        layerHandler.setPreferredSize(boardSize);
        layerHandler.addMouseListener(this);
        layerHandler.addMouseMotionListener(this);

        //add layout and chess board to default layer
        board = new JPanel();
        layerHandler.add(board, JLayeredPane.DEFAULT_LAYER);
        board.setLayout(new GridLayout(8,8));
        board.setPreferredSize(boardSize);
        board.setBounds(0, 0, boardSize.width, boardSize.height);

        //add squares to chess board
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                JPanel square = new JPanel(new BorderLayout());
                board.add(square);

                boolean isLightSquare = (file + rank) % 2 == 0;
                Color squareColor = isLightSquare ? new Color(238,238,213) : new Color(125,148,93);

                square.setBackground(squareColor);
            }
        }

        //draw pieces
        JLabel piece = new JLabel(new ImageIcon("lib/Pieces/BlackRook.png"));
        JPanel panel = (JPanel)board.getComponent(0);
        panel.add(piece);
        
        piece = new JLabel(new ImageIcon("lib/Pieces/BlackKnight.png"));
        panel = (JPanel)board.getComponent(1);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/BlackBishop.png"));
        panel = (JPanel)board.getComponent(2);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/BlackQueen.png"));
        panel = (JPanel)board.getComponent(3);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/BlackKing.png"));
        panel = (JPanel)board.getComponent(4);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/BlackBishop.png"));
        panel = (JPanel)board.getComponent(5);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/BlackKnight.png"));
        panel = (JPanel)board.getComponent(6);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/BlackRook.png"));
        panel = (JPanel)board.getComponent(7);
        panel.add(piece);

        for (int i = 8; i < 16; i++) {
            piece = new JLabel(new ImageIcon("lib/Pieces/BlackPawn.png"));
            panel = (JPanel)board.getComponent(i);
            panel.add(piece);
        }

        for (int i = 48; i < 56; i++) {
            piece = new JLabel(new ImageIcon("lib/Pieces/WhitePawn.png"));
            panel = (JPanel)board.getComponent(i);
            panel.add(piece);
        }

        //draw pieces
        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteRook.png"));
        panel = (JPanel)board.getComponent(56);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteKnight.png"));
        panel = (JPanel)board.getComponent(57);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteBishop.png"));
        panel = (JPanel)board.getComponent(58);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteQueen.png"));
        panel = (JPanel)board.getComponent(59);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteKing.png"));
        panel = (JPanel)board.getComponent(60);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteBishop.png"));
        panel = (JPanel)board.getComponent(61);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteKnight.png"));
        panel = (JPanel)board.getComponent(62);
        panel.add(piece);

        piece = new JLabel(new ImageIcon("lib/Pieces/WhiteRook.png"));
        panel = (JPanel)board.getComponent(63);
        panel.add(piece);
    }

    public void resetMove(JLabel pieceKilled, Container backSquare) {
        Component square = board.getComponent(startSquareX * 8 + startSquareY);
        Container back = (Container)square;
        back.add(chessPiece);
        chessPiece.setVisible(true);

        if (pieceKilled != null) {
            backSquare.add(pieceKilled);
        }
    }

    public void movePiece(int locationX, int locationY, int destX, int destY) {
        Container square = (Container)board.getComponent(locationX * 8 + locationY);
        JLabel piece = (JLabel)square.getComponent(0); 
        piece.setVisible(false);
        Container dest = (Container)board.getComponent(destX * 8 + destY);

        square.remove(0);
        dest.add(piece);
        piece.setVisible(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (chessPiece == null) return;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        chessPiece = null;
        Component c =  board.findComponentAt(e.getX(), e.getY());
 
        if (c instanceof JPanel)  return;
 
        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layerHandler.add(chessPiece, JLayeredPane.DRAG_LAYER);

        for (int i = 0; i < board.getComponents().length; i++) {
            if (board.getComponent(i) == board.getComponentAt(chessPiece.getLocation())) {
                startSquareX = i / 8;
                startSquareY = i % 8;
            }
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(chessPiece == null) return;
 
        chessPiece.setVisible(false);
        Component c =  board.findComponentAt(e.getX(), e.getY());

        JLabel pieceKilled = null;
        Container backSquare = null;
 
        if (c instanceof JLabel){
            Container parent = c.getParent();
            pieceKilled = (JLabel)parent.getComponent(0); 
            backSquare = parent;
            parent.remove(0);
            parent.add(chessPiece);

            for (int i = 0; i < board.getComponents().length; i++) {
                if (board.getComponent(i) == board.getComponentAt(parent.getLocation())) {
                    endSquareX = i / 8;
                    endSquareY = i % 8;
                }
            }

            chessPiece.setVisible(true);
        }
        else {
            Container parent = (Container)c;
            if (parent == null) {
                this.resetMove(pieceKilled, backSquare);
                return;
            }
            parent.add(chessPiece);
 
            chessPiece.setVisible(true);

            for (int i = 0; i < board.getComponents().length; i++) {
                if (board.getComponent(i) == board.getComponentAt(c.getLocation())) {
                    endSquareX = i / 8;
                    endSquareY = i % 8;
                }
            }
        }

        String directory = chessPiece.getIcon().toString();
        String color = directory.replace("lib/Pieces/", "");
        Player playerThatMoved;
        if (color.substring(0, 5).equals("White")) {
            playerThatMoved = Main.game.players[0].isWhiteSide() ? Main.game.players[0] : Main.game.players[1];
        }
        else {
            playerThatMoved = !Main.game.players[0].isWhiteSide() ? Main.game.players[0] : Main.game.players[1];
        }


        if (!Main.game.playerMove(playerThatMoved, startSquareX, startSquareY, endSquareX, endSquareY)) {
            this.resetMove(pieceKilled, backSquare);
        }

        startSquareX = null;
        startSquareY = null;
        endSquareX = null;
        endSquareY = null;

    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
