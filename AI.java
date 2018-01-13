import java.util.ArrayList;
import javafx.util.Pair;
import java.util.Random;
import java.util.Hashtable;
import java.util.Enumeration; // apparently I might need this for hashTable?

class Transpose
{    
    private int score;
    private int depth;
    
    public Transpose(int score, int depth)
    {
        this.score = score;
        this.depth = depth;
    }

    public int getScore()
    {
        return score;
    }
    
    public int getDepth()
    {
        return depth;
    }
}

public class AI
{
    private Board board;
    private char col;
    private Hashtable<Board,Transpose> posEvals;
    private final int winValue = Integer.MAX_VALUE;
    private final int loseValue = Integer.MIN_VALUE;
    private final int drawValue = 0;
    private final int maxDepth = 5;

    public AI(Board b, char c)
    {
        board = b;
        col = c; 
        posEvals = new Hashtable<Board,Transpose>(0);
    }

    public void doMove(Board chess, Move m)
    {
        chess.move(m.getStartRow(),m.getStartCol(),m.getEndRow(),m.getEndCol());
    }

    public ArrayList<Move> getAllMoves(Board chess,char colour)
    {
        ArrayList<Move> moves = new ArrayList<Move>(0);
        ArrayList<Move> pieceMoves;
        Piece piece;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                piece = chess.getPiece(i,j);
                if (piece.getColour() == colour)
                {
                    pieceMoves = piece.generateMoves(i,j);
                    
                    for (Move move: pieceMoves)
                    {
                        moves.add(move);
                    }
                }

            }
        }
        
        return moves;
    }

    public Move makeMove (Board chess)
    {
        Move best = getBestMove(chess);
        doMove(chess,best);
        return best;
    }

    private char oppColour(char c)
    {
        if (c == 'W')
            return 'B';
        else
            return 'W';
    }
    
    private int material (Piece piece)
    {
        if (piece instanceof Pawn)
            return 100;
        else if (piece instanceof Knight)
            return 320;
        else if (piece instanceof Bishop)
            return 333;
        else if (piece instanceof Rook)
            return 510;
        else if (piece instanceof Queen)
            return 880;
        else if (piece instanceof King) 
            return 20000;
        else // if this null
            return 0;
    }
    
    private int material (Board chess, char colour)
    {
        int total = 0;
        Piece piece;
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            { 
                if (chess.hasPiece(i,j))
                { 
                    piece = chess.getPiece(i,j);
                
                    if (piece.getColour() == colour)
                    {
                        total += material(piece);
                    }
                    else
                    {
                        total -= material(piece);
                    }
                }
            }
        }   
        
        return total;
    }
    
    private int mobility (Board chess, char colour)
    {
        ArrayList<Move> moves = getAllMoves(chess,colour);
        return moves.size();
    }
    
    private Move getBestMove(Board chess)
    {
        int score;
        int bestScore = loseValue;
        Move bestMove = new Move();
        Position oldPos = chess.getPosition();
        
        for (Move move: getAllMoves(chess,col))
        {
            doMove(chess,move);
            score = evaluate(chess,0,loseValue,winValue);
            if (score > bestScore)
            {
                bestScore = score;
                bestMove = move;
            }
            chess.setPosition(oldPos);
        }
        return bestMove;
    }
    
    private int evaluate (Board chess, int depth, int alpha, int beta)
    {
        int score;
        char colour = depth % 2 == 0 ? oppColour(col) : col;
        
        if (depth == maxDepth || chess.endGame())
        {
            return material(chess,colour);
        }
        
        ArrayList<Move> moves = getAllMoves(chess,colour);
        
        if (moves.size() == 0) // stalemate since game hasn't ended
        {
            return drawValue;
        }
        
        Position oldPos = chess.getPosition();
        
        if (depth % 2 == 0) // minimizing
        {
            score = Integer.MAX_VALUE;
            
            for (int i = 0; i < moves.size(); i++)
            {
                doMove(chess,moves.get(i)); 
                Transpose trans = posEvals.get(chess);               
                
                if (trans == null || depth <= trans.getDepth())
                {
                    score = Math.min(score,evaluate(chess,depth+1,alpha,beta));
                    posEvals.put(chess,new Transpose(score,depth));
                }
                else
                {
                    score = Math.min(score,evaluate(chess,depth+1,alpha,beta));//trans.getScore();
                }
                
                beta = Math.min(beta,score);
                if (beta <= alpha)
                {
                    break;
                }
                chess.setPosition(oldPos);
            }
            
            return score;
        }
        
        else // maximizing
        {
            score = Integer.MIN_VALUE;
            
            for (int i = 0; i < moves.size(); i++)
            {
                doMove(chess,moves.get(i));                
                Transpose trans = posEvals.get(chess);
                
                if (trans == null || depth <= trans.getDepth())
                {
                    score = Math.max(score,evaluate(chess,depth+1,alpha,beta));
                    posEvals.put(chess,new Transpose(score,depth));
                }
                else
                {
                    score = Math.max(score,evaluate(chess,depth+1,alpha,beta));//trans.getScore();
                }
                
                alpha = Math.max(alpha,score);
                if (beta <= alpha)
                {
                    break;
                }
                chess.setPosition(oldPos);
            }
            
            return score;
        }
    }
    
    // Evaluate board using minimax
    private int evaluate (Board chess, int depth) 
    {
        int score,bestScore;
        char colour = depth % 2 == 0 ? oppColour(col) : col;
        
        if (depth == maxDepth || chess.endGame())
        {
            return material(chess,colour);            
        }
                
        ArrayList<Move> moves = getAllMoves(chess,colour);
        
        if (moves.size() == 0) // no possible moves so stalement since game hasn't ended
        {
            return drawValue;
        }
        
        Position oldPos = chess.getPosition();
        
        if (depth % 2 == 0) // minimzing
        {
            bestScore = Integer.MAX_VALUE;
           
            for (int i = 0; i < moves.size(); i++)
            {
                doMove(chess,moves.get(i));
                score = evaluate(chess,depth+1);
                if (score < bestScore)
                {
                    bestScore = score;
                }
                chess.setPosition(oldPos);
            }
            
            return bestScore;
        }
        else // maximizing
        {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++)
            {
                doMove(chess,moves.get(i));
                score = evaluate(chess,depth+1);
                if (score > bestScore)
                {
                    bestScore = score;
                }
                chess.setPosition(oldPos);
            }
            
            return bestScore;
        }
    }

}

