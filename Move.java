
public class Move
{
    private int startRow,startCol,endRow,endCol;
    private Piece piece;
    
    public Move(int sr, int sc, int er, int ec, Piece p)
    {
        startRow = sr;
        startCol = sc;
        endRow = er;
        endCol = ec;
        piece = p;
    }
    
    public Move()
    {
        startRow = -1;
        startCol = -1;
        endRow = -1;
        endCol = -1;
        piece = null;
    }
    
    public Piece getPiece()
    {
        return piece;
    }
    
    public int getStartRow()
    {
        return startRow;
    }
    
    public int getStartCol()
    {
        return startCol;
    }
    
    public int getEndRow()
    {
        return endRow;
    }
    
    public int getEndCol()
    {
        return endCol;
    }
    
    public boolean equal(Move m)
    {
        return startRow == m.getStartRow() && startCol == m.getStartCol() && endRow == m.getEndRow() && endCol == m.getEndCol();
    }
    
    public String toString()
    {
        return "Piece " + piece.getType() + " moved from (" + startRow + "," + startCol
                + ") to (" + endRow + "," + endCol + ")."; 
    }


}
