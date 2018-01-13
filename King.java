import java.util.ArrayList;

public class King extends Piece
{  
    private boolean hasMoved;
    private boolean castling;
    
    public King(char c,Board b)
    {
        super(c,"King",b);
        hasMoved = false;
        castling = false;
    }
    
    // find all 
    public ArrayList<Move> getAllOpponentMoves()
    {
        ArrayList<Move> badMoves = new ArrayList<Move>(0);
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.hasPiece(i,j))
                {
                    Piece piece = board.getPiece(i,j);
                    if (piece.getColour() != colour)
                    {
                        for (Move move:piece.generateMoves(i,j))
                        {
                            badMoves.add(move);
                        }
                    }
                }
            }
        }
        
        return badMoves;
    }
    
    // STILL NEED TO MAKE SURE THAT THE KING DOES NOT WALK INTO CHECK
    public ArrayList<Move> generateMoves(int startRow, int startCol)
    {
        moves = new ArrayList<Move>(0);
        /*ArrayList<Move> badMoves = getAllOpponentMoves();
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                Move move = new Move(startRow,startCol,i,j,this);
                if (legalMove(startRow,startCol,i,j) && !badMoves.contains(move))
                {
                    moves.add(new Move(startRow,startCol,i,j,this));
                }
            }
        }*/
        
        // a king can move 1 up 
        if (legalMove(startRow,startCol,startRow+1,startCol))
        {
            moves.add(new Move(startRow,startCol,startRow+1,startCol,this));
        }
        // a king can move 1 down 
        if (legalMove(startRow,startCol,startRow-1,startCol))
        {
            moves.add(new Move(startRow,startCol,startRow-1,startCol,this));
        }
        // a king can move 1 right 
        if (legalMove(startRow,startCol,startRow,startCol+1))
        {
            moves.add(new Move(startRow,startCol,startRow,startCol+1,this));
        }
        // a king can move 1 left 
        if (legalMove(startRow,startCol,startRow,startCol-1))
        {
            moves.add(new Move(startRow,startCol,startRow,startCol-1,this));
        }
        // a king can move diagonal 1 up right 
        if (legalMove(startRow,startCol,startRow+1,startCol+1))
        {
            moves.add(new Move(startRow,startCol,startRow+1,startCol+1,this));
        }
        // a king can move diagonal 1 up left
        if (legalMove(startRow,startCol,startRow+1,startCol-1))
        {
            moves.add(new Move(startRow,startCol,startRow+1,startCol-1,this));
        }
        // a king can move diagonal 1 down right 
        if (legalMove(startRow,startCol,startRow-1,startCol+1))
        {
            moves.add(new Move(startRow,startCol,startRow-1,startCol+1,this));
        }
        // a king can move diagonal 1 down left 
        if (legalMove(startRow,startCol,startRow-1,startCol-1))
        {
            moves.add(new Move(startRow,startCol,startRow-1,startCol-1,this));
        }
        
        return moves;
    }

    public boolean legalMove(int startRow, int startCol, int endRow, int endCol)
    {
        if (!super.legalMove(startRow,startCol,endRow,endCol))
            return false;
        else if (startRow == endRow)
            return absDiff(startCol,endCol) == 1;
        else if (startCol == endCol)
            return absDiff(startRow,endRow) == 1;
        else
            return absDiff(startRow,endRow) == 1 && absDiff(startCol,endCol) == 1;
    }
}