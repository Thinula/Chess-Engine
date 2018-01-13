import java.util.ArrayList;

public class Pawn extends Piece
{    
    public Pawn(char c,Board b)
    {
        super(c,"Pawn",b);
    }
    
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);
        
        // a pawn can move 1 up modulo the colour
        if (legalMove(startRow,startCol,startRow+pawnDirection(1),startCol))
        {
            moves.add(new Move(startRow,startCol,startRow+pawnDirection(1),startCol,this));
        }
        // a pawn can move 1 up modulo the colour
        if (legalMove(startRow,startCol,startRow+pawnDirection(2),startCol))
        {
            moves.add(new Move(startRow,startCol,startRow+pawnDirection(2),startCol,this));
        }
        // a pawn can move diagonal 1 up modulo the colour if enemy
        if (legalMove(startRow,startCol,startRow+pawnDirection(1),startCol+1))
        {
            moves.add(new Move(startRow,startCol,startRow+pawnDirection(1),startCol+1,this));
        }
        // a pawn can move diagonal 1 up modulo the colour if enemy
        if (legalMove(startRow,startCol,startRow+pawnDirection(1),startCol-1))
        {
            moves.add(new Move(startRow,startCol,startRow+pawnDirection(1),startCol-1,this));
        }
        
        return moves;
    }
    
    private int pawnDirection(int change)
    {
        if (colour == 'W')
        {
            return -1*change;
        }
        else
        {
            return change;
        }        
    }
    
    private boolean capture(int startRow, int startCol, int endRow, int endCol)
    {
        return endRow-startRow == pawnDirection(1) && absDiff(startCol,endCol) == 1;
    }
    
    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {        
        
        if (!super.legalMove(startRow,startCol,endRow,endCol))
            return false;
        else if (startCol == endCol)
        {
            if (board.hasPiece(endRow,endCol))
                return false;
            else if (startRow % 5 == 1 && endRow-startRow == pawnDirection(2))
            {
                if (endRow-startRow == 2 && !board.hasPiece(startRow+1,startCol))
                    return true;
                else if (endRow-startRow == -2 && !board.hasPiece(startRow-1,startCol))
                    return true;
                else
                    return false;
            }
            else if (endRow-startRow == pawnDirection(1))
                return true;
            else
                return false;
        }
        else if (board.enemyRoar(startRow,startCol,endRow,endCol) && capture(startRow,startCol,endRow,endCol))
        {
            return true;            
        }
        else
        {   
            return false;
        }
        
    }
}
