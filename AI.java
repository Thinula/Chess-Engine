import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;
import java.util.Hashtable;
import java.util.Enumeration; // apparently I might need this for hashTable?

class Transpose
{    
    private double score;
    private int depth;
    
    public Transpose(double score, int depth)
    {
        this.score = score;
        this.depth = depth;
    }

    public double getScore()
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
    private double[] inputLayer;
    private double[][] inputWeights;
    private double[] threshold;
    private double[] hiddenLayer;
    private double[] hiddenWeights;
    private final double winValue = Integer.MAX_VALUE;
    private final double loseValue = Integer.MIN_VALUE;
    private final double drawValue = 0;
    private final int maxDepth = 5;

    public AI(Board b, char c)
    {
        board = b;
        col = c; 
        posEvals = new Hashtable<Board,Transpose>(0);
        inputLayer = new double[384];
        hiddenLayer = new double[256];
        threshold = new double[256];
        inputWeights = new double[384][256];
        hiddenWeights = new double[256];
        
        Random random = new Random();
        
        for (int i = 0; i < 384; i++)
        {
            for (int j = 0; j < 256; j++)
            {
                inputWeights[i][j] = random.nextGaussian();
            }
        }
        
        for (int i = 0; i < 256; i++)
        {
            threshold[i] = random.nextGaussian();
            hiddenWeights[i] = random.nextGaussian();
        }
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
    
    private double sigmoid(double z)
    {
        return 1/(1+Math.exp(-z));
    }
    
    private Move getBestMove(Board chess)
    {
        double score;
        double bestScore = loseValue;
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
    
    // evaluation function
    private double evaluate (Board chess, char colour)
    {
        // first layer: if board[i][j] is a piece, 8*i+j]
        inputLayer = new double[384];
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Piece piece = board.getPiece(i,j);
                int product = 1;
                if (piece.getColour() != colour)
                {
                    product = -1;
                }
                
                if (piece instanceof Pawn)
                {
                    inputLayer[6*(8*i+j)] = product;
                }                
                else if (piece instanceof Knight)
                {
                    inputLayer[6*(8*i+j)+1] = product;
                }           
                else if (piece instanceof Bishop)
                {
                    inputLayer[6*(8*i+j)+2] = product;
                }
                else if (piece instanceof Rook)
                {
                    inputLayer[6*(8*i+j)+3] = product;
                }
                else if (piece instanceof Queen)
                {
                    inputLayer[6*(8*i+j)+4] = product;
                }
                else if (piece instanceof King)
                {
                    inputLayer[6*(8*i+j)+5] = product;
                }
            }
        }
        
        for (int i = 0; i < 256; i++)
        {
            hiddenLayer[i] = 0;
            for (int j = 0; j < 384; j++)
            {
                hiddenLayer[i] += inputWeights[i][j]*inputLayer[j];
                hiddenLayer[i] = sigmoid(hiddenLayer[i]);
                
                if (hiddenLayer[i] > threshold[i])
                {
                    hiddenLayer[i] = 1;
                }
                else
                {
                    hiddenLayer[i] = 0;
                }
            }
        }
        
        double output = 0;
        
        for (int i = 0; i < 256; i++)
        {
            output += hiddenLayer[i]*hiddenWeights[i];
        }
        
        return sigmoid(output);
    }
    
    // alpha beta pruning
    private double evaluate (Board chess, int depth, double alpha, double beta)
    {
        double score;
        char colour = depth % 2 == 0 ? oppColour(col) : col;
        
        if (depth == maxDepth || chess.endGame())
        {
            return evaluate(chess,colour);
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
}

