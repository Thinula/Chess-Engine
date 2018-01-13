import java.util.ArrayList;

public class Bishop extends Piece
{
    public Bishop(char c,Board b)
    {
        super(c,"Bishop",b);
    }
    
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);
        
        for (int i = 0; i < 8; i++)
        {
            // a bishop can move any number of squares diagonally up right
            if (legalMove(startRow,startCol,startRow+i,startCol+i))
            {
                moves.add(new Move(startRow,startCol,startRow+i,startCol+i,this));
            }
            // a bishop can move any number of squares diagonally up left
            if (legalMove(startRow,startCol,startRow+i,startCol-i))
            {
                moves.add(new Move(startRow,startCol,startRow+i,startCol-i,this));
            }
            // a bishop can move any number of squares diagonally down right
            if (legalMove(startRow,startCol,startRow-i,startCol+i))
            {
                moves.add(new Move(startRow,startCol,startRow-i,startCol+i,this));
            }
            // a bishop can move any number of squares diagonally down left
            if (legalMove(startRow,startCol,startRow-i,startCol-i))
            {
                moves.add(new Move(startRow,startCol,startRow-i,startCol-i,this));
            }
        }
        
        return moves;
    }

    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {        
        Piece bishop = board.getPiece(startRow,startCol);

        if (!super.legalMove(startRow,startCol,endRow,endCol))
            return false;
        else if (absDiff(startRow,endRow) != absDiff(startCol,endCol))
            return false;
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