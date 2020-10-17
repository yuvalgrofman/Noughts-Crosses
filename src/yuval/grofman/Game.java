package yuval.grofman;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    Player player1;
    Player player2;
    Board board = new Board();

    /*
    This function takes a 2d array called "artificial board" this is the starting position for the game.
    This Array must be a 3 by 3 array and the number points set to one and two must be equal.
    ie: Game({1,0,0}{0,2,0}{0,0,0}) would set the starting board to be 1 0 0
                                                                       0 2 0
                                                                       0 0 0
     */
    Game(int[][] artificialBoard) {

        try {


            int player1PointsCounter = 0;
            int player2PointsCounter = 0;
            for (int i = 0; i < artificialBoard.length; i++) {
                for (int j = 0; j < artificialBoard[i].length; j++) {
                    if (artificialBoard[i][j] == 1) {
                        player1PointsCounter++;
                    } else if (artificialBoard[i][j] == 2) {
                        player2PointsCounter++;
                    }
                }
            }

            if (player1PointsCounter != player2PointsCounter) {
                System.out.println("Your input is invalid. The number of points set to 1 must be equal to those set to 2.");
                System.exit(0);
            }
            Scanner scanner = new Scanner(System.in);

            boolean gameState = false;//initializing so there wont be build errors.
            //The specific value that was hardcoded doesn't actually matter.

            System.out.println("do you want to play?(true/false)");

            try {
                gameState = scanner.nextBoolean();
            }//if the input is incorrect the code will terminate

            catch (InputMismatchException error) {
                System.out.println("you have wrote a invalid input.");
                System.out.println("you must write true or false here");
                System.exit(0);
            }

            while (gameState) {
                playerSetUp();
                startGame(player1, player2, board, artificialBoard);

                System.out.println("do you want to start a new game?(true/false)");
                try {
                    gameState = scanner.nextBoolean();
                } catch (InputMismatchException error) {
                    System.out.println("you have wrote a invalid input.");
                    System.out.println("you must write true or false here");
                    System.exit(0);
                }
            }
            System.out.println("farewell my friend");

        }catch (Exception error){
            System.out.println("there has been an unexpected exception it is:");
            System.out.println(error.getCause());
        }
    }

    /*
    resets the boards, then sets board equal to the  artificial board and starts the match.
    The input is two players with no special conditions. gameBoard which is the
     */
    public void startGame(Player player1, Player player2, Board gameBoard, int[][] artificialBoard) {

        gameBoard.resetBoard();

        for (int i = 0; i < board.getNumCol(); i++){
            for (int j = 0; j < board.getNumRows(); j++){
                gameBoard.setPoint(i,j,artificialBoard[i][j],false,"bot");//arbitrary choice of playerName parameter means nothing
            }
        }


        gameBoard.printBoard();
        while (gameBoard.gameOngoing) {
            player1.setPoint();

            if (gameBoard.gameOngoing) {
                player2.setPoint();
            }
        }
    }

    //returns an array. if player x is a bot then array[x-1] = 1 else its equal to 0.(x = 1 or 2)
    public int[] checkBot(){
        int[] whoIsBot = {0,0};
        Scanner scanner = new Scanner(System.in);

        System.out.println("do you want player 1 to be a bot?(true/false)");
        boolean player1IsBot = false;

        try{player1IsBot = scanner.nextBoolean();}//if the input is incorrect the code will terminate

        catch (InputMismatchException error){
            System.out.println("you have wrote a invalid input.");
            System.out.println("you must write true or false here");
            System.exit(0);
        }

        System.out.println("do you want player 2 to be a bot?(true/false)");
        boolean player2IsBot = false;

        try{player2IsBot = scanner.nextBoolean();}//if the input is incorrect the code will terminate

        catch (InputMismatchException error){
            System.out.println("you have wrote a invalid input.");
            System.out.println("you must write true or false here");
            System.exit(0);
        }

        if (player1IsBot){
            whoIsBot[0] = 1;
        }

        if (player2IsBot){
            whoIsBot[1] = 1;
        }

        return whoIsBot;
    }

    /*
    set players to bot or not according to the user.
     */
    public void playerSetUp(){

        int[] whoIsBot = checkBot();

        Scanner scanner = new Scanner(System.in);

        String player1Name = "bot";//the default is bot
        String player2Name = "bot";//The if checks if it's not a bot


        if (whoIsBot[0] == 0){
            System.out.println("Player 1 write your name");
             player1Name = scanner.next();
        }

        if (whoIsBot[1] == 0){
            System.out.println("Player 2 write your name");
            player2Name = scanner.next();
        }

        player1 = new Player(1,board,whoIsBot[0] == 1,player1Name);
        player2 = new Player(2,board,whoIsBot[1] == 1,player2Name);
    }
}