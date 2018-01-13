import java.util.ArrayList;

public class Piece
{
    protected Board board;
    protected char colour;
    protected String type; 
    protected ArrayList<Move> moves;
    
    public Piece()
    {
        colour = ' ';
        type = " ";
    }
    
    public Piece(char c, String t, Board b)
    {
        colour = c;
        type = t;
        board = b;
    }
    
    protected int absDiff(int a, int b)
    {
        return b >= a ? b-a:a-b;
    }
    
    public String getType()
    {
        return type;
    }
    
    public char getColour()
    {
        return colour;
    }
    
    public int min(int a, int b)
    {
        return a < b ? a:b;
    }
    
    public int max(int a, int b)
    {
        return a > b ? a:b;
    }
    
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);        
        return moves;
    }
    
    public ArrayList<Move> getMoves()
    {
        return moves;
    }
    
    private boolean isInRange(int check)
    {
        return 0 <= check && check <= 7;
    }
    
    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {        
        if (startRow == endRow && startCol == endCol)
            return false;
        else if (isInRange(startRow) && isInRange(startCol) && isInRange(endRow) && isInRange(endCol))
        {
            if (board.hasPiece(endRow,endCol))
            {
                Piece start = board.getPiece(startRow,startCol);
                Piece end = board.getPiece(endRow,endCol);
                
                return start.getColour() != end.getColour();
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}