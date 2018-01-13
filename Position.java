
public class Position
{
    private Piece[][] pos;
    
    public Position(Board board)
    {
        pos = new Piece[8][8];
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.getPiece(i,j) != null)
                    pos[i][j] = board.getPiece(i,j);
                else
                    pos[i][j] = new Piece();
            }
        }
    }
    
    public Piece[][] getPosition()
    {
        return pos;
    }
    
    public boolean endGame()
    {
        int kingCount = 0;
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (pos[i][j].getType().equals("King"))
                {
                    kingCount++;
                }
            }
        }
        return kingCount == 2;
    }
}
