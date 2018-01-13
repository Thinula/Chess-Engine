import java.util.ArrayList;

public class Rook extends Piece
{
    private boolean hasMoved;
    
    public Rook(char c,Board b)
    {
        super(c,"Rook",b);
        hasMoved = false;
    }
    
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);
        
        for (int i = 0; i < 8; i++)
        {
            // a rook can move any number of squares horizontally
            if (legalMove(startRow,startCol,startRow,i))
            {
                moves.add(new Move(startRow,startCol,startRow,i,this));
            }
            // a rook can move any number of squares vertically
            if (legalMove(startRow,startCol,i,startCol))
            {
                moves.add(new Move(startRow,startCol,i,startCol,this));
            }
        }
        
        return moves;
    }
    
    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {     
        if (!super.legalMove(startRow,startCol,endRow,endCol))
            return false;
        else if (startRow == endRow)
        {
            for (int i = min(startCol,endCol)+1; i < max(startCol,endCol); i++)
            {
                if (board.hasPiece(startRow,i))
                    return false;
            }
            return true;
        }
        else if (startCol == endCol)
        {
            for (int i = min(startRow,endRow)+1; i < max(startRow,endRow); i++)
            {
                if (board.hasPiece(i,startCol))
                    return false;
            }
            return true;
        }
        else
            return false;
    }
}    
