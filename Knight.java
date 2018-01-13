import java.util.ArrayList;

public class Knight extends Piece
{    
    public Knight(char c,Board b)
    {
        super(c,"Knight",b);
    }
    
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);
        
        // a knight can move 1 up and 2 right
        if (legalMove(startRow,startCol,startRow+1,startCol+2))
        {
            moves.add(new Move(startRow,startCol,startRow+1,startCol+2,this));
        }
        // a knight can move 1 up and 2 left
        if (legalMove(startRow,startCol,startRow+1,startCol-2))
        {
            moves.add(new Move(startRow,startCol,startRow+1,startCol-2,this));
        }
        // a knight can move 2 up and 1 right
        if (legalMove(startRow,startCol,startRow+2,startCol+1))
        {
            moves.add(new Move(startRow,startCol,startRow+2,startCol+1,this));
        }
        // a knight can move 2 up and 1 left
        if (legalMove(startRow,startCol,startRow+2,startCol-1))
        {
            moves.add(new Move(startRow,startCol,startRow+2,startCol-1,this));
        }
        // a knight can move 1 down and 2 right
        if (legalMove(startRow,startCol,startRow-1,startCol+2))
        {
            moves.add(new Move(startRow,startCol,startRow-1,startCol+2,this));
        }
        // a knight can move 1 down and 2 left
        if (legalMove(startRow,startCol,startRow-1,startCol-2))
        {
            moves.add(new Move(startRow,startCol,startRow-1,startCol-2,this));
        }
        // a knight can move 2 down and 1 right
        if (legalMove(startRow,startCol,startRow-2,startCol+1))
        {
            moves.add(new Move(startRow,startCol,startRow-2,startCol+1,this));
        }
        // a knight can move 2 down and 1 left
        if (legalMove(startRow,startCol,startRow-2,startCol-1))
        {
            moves.add(new Move(startRow,startCol,startRow-2,startCol-1,this));
        }
        
        return moves;
    }
    
    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {       
        if (!super.legalMove(startRow,startCol,endRow,endCol))
            return false;
        else if (absDiff(startRow,endRow) == 1 && absDiff(startCol,endCol) == 2)
            return true;
        else if (absDiff(startCol,endCol) == 1 && absDiff(startRow,endRow) == 2)
            return true;
        else
            return false;
    }
}