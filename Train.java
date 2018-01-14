import java.util.Arrays;

public class Train
{
    
    public Train()
    {
        
    }
    
    
    public int Game(AI player1,AI player2)
    {
        Board board = new Board();
        int turn = 0;
        Move move;
        char colour;
        
        while (!board.endGame())
        {
            if (turn % 2 == 0)
            {
                move = player1.makeMove(board);
            }
            else
            {
                move = player1.makeMove(board);
            }
        }
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.getPiece(i,j) instanceof King)
                {
                    if (board.getPiece(i,j).getColour() == 'W')
                    {
                        return 1;
                    }
                    else
                    {
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
    
    public int[][] Tournament(AI[] arrayOfPlayers)
    {
        int[][] scores = new int[arrayOfPlayers.length][2];
        double mutation = 0.05; // mutation factor
        int game;
        
        for (int i = 0; i < arrayOfPlayers.length; i++)
        {
            scores[i][0] = 0;
            scores[i][1] = i;
        }
        
        for (int i = 0; i < arrayOfPlayers.length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                game = Game(arrayOfPlayers[i],arrayOfPlayers[j]);
                scores[i][0] += game;
                scores[j][0] -= game;
                
                game = Game(arrayOfPlayers[j],arrayOfPlayers[i]);
                scores[i][0] -= game;
                scores[j][0] += game;
            }
        }
        
        Arrays.sort(scores);
        int N = (int)(arrayOfPlayers.length/2);
        
        // keep the highest half of players
        for (int i = 0; i < N; i++)
        {
            scores[i] = scores[i+N];
        }
        return scores;
    }
    
    public static void main(String[] args)
    {
        AI[] players = new AI[24];
        
        for (int i = 0; i < 24; i++)
        {
            
        }
    }
}
