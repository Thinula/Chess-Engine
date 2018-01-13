import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.ArrayList;

public class Board
{
    private Piece board[][];

    public Board()
    {
        board = new Piece[8][8];
        
        Pawn whiteP1 = new Pawn('W',this); board[6][0] = whiteP1;
        Pawn whiteP2 = new Pawn('W',this); board[6][1] = whiteP2;
        Pawn whiteP3 = new Pawn('W',this); board[6][2] = whiteP3;
        Pawn whiteP4 = new Pawn('W',this); board[6][3] = whiteP4;
        Pawn whiteP5 = new Pawn('W',this); board[6][4] = whiteP5; 
        Pawn whiteP6 = new Pawn('W',this); board[6][5] = whiteP6;
        Pawn whiteP7 = new Pawn('W',this); board[6][6] = whiteP7;
        Pawn whiteP8 = new Pawn('W',this); board[6][7] = whiteP8;

        Pawn blackP1 = new Pawn('B',this); board[1][0] = blackP1;
        Pawn blackP2 = new Pawn('B',this); board[1][1] = blackP2;
        Pawn blackP3 = new Pawn('B',this); board[1][2] = blackP3;
        Pawn blackP4 = new Pawn('B',this); board[1][3] = blackP4;
        Pawn blackP5 = new Pawn('B',this); board[1][4] = blackP5;
        Pawn blackP6 = new Pawn('B',this); board[1][5] = blackP6;
        Pawn blackP7 = new Pawn('B',this); board[1][6] = blackP7;
        Pawn blackP8 = new Pawn('B',this); board[1][7] = blackP8;

        Rook whiteR1 = new Rook('W',this); board[7][0] = whiteR1;
        Rook whiteR2 = new Rook('W',this); board[7][7] = whiteR2;

        Rook blackR1 = new Rook('B',this); board[0][7] = blackR1;
        Rook blackR2 = new Rook('B',this); board[0][0] = blackR2;

        Knight whiteN1 = new Knight('W',this); board[7][1] = whiteN1;
        Knight whiteN2 = new Knight('W',this); board[7][6] = whiteN2;

        Knight blackN1 = new Knight('B',this); board[0][1] = blackN1;
        Knight blackN2 = new Knight('B',this); board[0][6] = blackN2;

        Bishop whiteB1 = new Bishop('W',this); board[7][2] = whiteB1;
        Bishop whiteB2 = new Bishop('W',this); board[7][5] = whiteB2;

        Bishop blackB1 = new Bishop('B',this); board[0][2] = blackB1;
        Bishop blackB2 = new Bishop('B',this); board[0][5] = blackB2;

        Queen whiteQ = new Queen('W',this); board[7][3] = whiteQ;
        Queen blackQ = new Queen('B',this); board[0][3] = blackQ;

        King whiteK = new King('W',this); board[7][4] = whiteK;
        King blackK = new King('B',this); board[0][4] = blackK;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j] == null)
                {
                    board[i][j] = new Piece();
                }
            }
        }
    }

    public Board(int w, int h, Piece[][] p)
    {
        board = p;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board[i][j] == null)
                {
                    board[i][j] = new Piece();
                }
            }
        }
    }

    public Board(Board b)
    {
        board = b.board;
    }

    public Piece getPiece(int row,int col)
    {
        return board[row][col];
    }

    public boolean hasPiece(int row, int col) 
    {
        return board[row][col].getColour() != ' ';
    }

    public Position getPosition()
    {
        return new Position(this);
    }

    public void setPosition(Position p)
    {
        Piece[][] pieces = p.getPosition();

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                board[i][j] = pieces[i][j];
    }

    public void print()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Piece piece = board[i][j];
                if (piece.getType().equals("Knight"))
                {
                    System.out.print("N ");
                }
                else if (piece.getType().equals("King"))
                {
                    System.out.print("K ");
                }
                else if (piece.getType().equals("Pawn"))
                {
                    System.out.print("P ");
                }
                else if (piece.getType().equals("Queen"))
                {
                    System.out.print("Q ");
                }
                else if (piece.getType().equals("Rook"))
                {
                    System.out.print("R ");
                }
                else if (piece.getType().equals("Bishop"))
                {
                    System.out.print("B ");
                }
                else
                {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public void move(int startRow, int startCol, int endRow, int endCol)
    {

        if (hasPiece(startRow,startCol))
        {
            Piece piece = board[startRow][startCol];//.getPiece(startRow,startCol);

            if (piece.legalMove(startRow,startCol,endRow,endCol))
            {
                board[startRow][startCol] = new Piece();
                board[endRow][endCol] = new Piece();
                board[endRow][endCol] = piece;
            }
        }
    }

    public boolean equals(Board b)
    {
        boolean equal = true;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (b.hasPiece(i,j) && hasPiece(i,j))
                {
                    equal &= b.getPiece(i,j).equals(getPiece(i,j));
                }
                else if (!b.hasPiece(i,j) && hasPiece(i,j))
                    return false;
                else if (b.hasPiece(i,j) && !hasPiece(i,j))
                    return false;
            }
        }

        return true;
    }

    public boolean endGame()
    {
        int kingCount = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (hasPiece(i,j))
                {
                    if (board[i][j].getType().equals("King"))
                    {   
                        kingCount++;
                    }
                }
            }
        }
        return kingCount != 2;
    }

    // checks whether the enemy of piece at current is at destiny
    public boolean enemyRoar(int startRow, int startCol, int endRow, int endCol)
    {
        if (hasPiece(startRow,startCol) && hasPiece(endRow,endCol))
        { 
            return board[startRow][startCol].getColour() != board[endRow][endCol].getColour();
        }
        else
            return false;
    }
}
