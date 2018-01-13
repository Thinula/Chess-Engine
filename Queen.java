import java.util.ArrayList;

public class Queen extends Piece
{        
    public Queen(char c,Board b)
    {
        super(c,"Queen",b);
    }
    
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);
        
        for (int i = 0; i < 8; i++)
        {
            // a queen can move any number of squares horizontally
            if (legalMove(startRow,startCol,startRow,i))
            {
                moves.add(new Move(startRow,startCol,startRow,i,this));
            }
            // a queen can move any number of squares vertically
            if (legalMove(startRow,startCol,i,startCol))
            {
                moves.add(new Move(startRow,startCol,i,startCol,this));
            }
            // a queen can move any number of squares diagonally up right
            if (legalMove(startRow,startCol,startRow+i,startCol+i))
            {
                moves.add(new Move(startRow,startCol,startRow+i,startCol+i,this));
            }
             // a queen can move any number of squares diagonally up left
            if (legalMove(startRow,startCol,startRow+i,startCol-i))
            {
                moves.add(new Move(startRow,startCol,startRow+i,startCol-i,this));
            }
             // a queen can move any number of squares diagonally down right
            if (legalMove(startRow,startCol,startRow-i,startCol+i))
            {
                moves.add(new Move(startRow,startCol,startRow-i,startCol+i,this));
            }
            // a queen can move any number of squares diagonally down left
            if (legalMove(startRow,startCol,startRow-i,startCol-i))
            {
                moves.add(new Move(startRow,startCol,startRow-i,startCol-i,this));
            }
        }
        
        return moves;
    }

    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {
        
        if (!super.legalMove(startRow,startCol,endRow,endCol))
            return false;
        else if (absDiff(startCol,endCol) != absDiff(startRow,endRow) && startRow != endRow && startCol != endCol)
            return false;
        else if (startCol == endCol)
        {
            for (int i = min(startRow,endRow)+1; i < max(startRow,endRow); i++)
            {
                if (board.hasPiece(i,startCol))
                    return false;
            }
            return true;
        }
        else if (startRow == endRow)
        {
            for (int i = min(startCol,endCol)+1; i < max(startCol,endCol); i++)
            {
                if (board.hasPiece(startRow,i))
                    return false;
            }
            return true;
        }
        else
        {
            for (int i = min(startRow,endRow); i < max(startRow,endRow); i++)
            {
                for (int j = min(startCol,endCol)+1; j  < max(startCol,endCol); j++)
                {
                    if (absDiff(i,startRow) == absDiff(j,startCol))
                    {
                        if (board.hasPiece(i,j))
                            return false;
                    }               
                }
            }
            return true;
        }
    }
}