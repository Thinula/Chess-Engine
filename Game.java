import java.util.Scanner;

public class Game
{
    public static Board board;
    public static Scanner input;
    public static AI AIWhite, AIBlack;
    public static int turn;

    public static void main(String[] args)
    {
        input = new Scanner(System.in);
        turn = 0;
        board = new Board();
        AIWhite = new AI(board, 'W');
        AIBlack = new AI(board, 'B');
        Move move;

        while (!board.endGame())
        {
            System.out.println();
            System.out.println();
            board.print();

            System.out.println();
            System.out.println();

            if (turn % 2 == 1)
            {
                int startRow,startCol,endRow,endCol;
                System.out.println("Enter start row number: ");
                startRow = input.nextInt()-1;
                System.out.println("Enter start column number: ");
                startCol = input.nextInt()-1;

                System.out.print("Enter end row number: ");
                endRow = input.nextInt()-1;
                System.out.print("Enter end column number: ");
                endCol = input.nextInt()-1;
                board.move(startRow,startCol,endRow,endCol);
            }
            else
            {
                move = AIWhite.makeMove(board);
                System.out.println("Turn Ended!");
                System.out.println("Move Selected: " + move.toString());
            }

            turn++;
        }
    }

}
