package yuval.grofman;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Player {


    private int playerSign;//the integer the player puts on the board.
    private Board originalBoard;//The board on which the game is played
    private boolean isBot;//if true the player is a bot if false then not a bot
    private String name;//name of the player. if bot then the name is "bot"

    //sets all the parameters to their respective class variables
    Player(int playerSign, Board board, boolean isBot, String name) {
        this.playerSign = playerSign;
        this.originalBoard = board;
        this.isBot = isBot;
        this.name = name;
    }

    /*
    Talks to the player through terminal and sets a point.
    At the end of this function the player will always have put a new point.
    In other words no need to check if a point was actually added.
     */
    public void setPoint() {
        if (!isBot) {

            Scanner scanner = new Scanner(System.in);

            System.out.println(name + " it's your turn.(write the coordinates of the point)");
            int x = 0;//initializing so there wont be build errors.
            int y = 0;//hardcoded value has no actual effect.

            try {
                 x = scanner.nextInt();
                 y = scanner.nextInt();
            }catch (InputMismatchException error){
                System.out.println(name + "you have wrote a invalid input.");
                System.out.println("you must write an Integer here");
                System.exit(0);
            }

            if ((x < 0 || x >= originalBoard.getNumCol()) && (y < 0 || y >= originalBoard.getNumRows())) {
                System.out.println(name + "your x and y coordinates are out of range.(it must be between 0 and 2)");
                setPoint();

            }else if (x < 0 || x >= originalBoard.getNumCol()) {
                System.out.println(name + "your x coordinate is out of range.(it must be between 0 and 2)");
                setPoint();
            }else if (y < 0 || y >= originalBoard.getNumRows()) {
                System.out.println(name + "your y coordinate is out of range.(it must be between 0 and 2)");
                setPoint();
            }else if (!originalBoard.setPoint(x, y, this.playerSign,true,name)) {
                setPoint();
            }

        }else {

            int[][] boardCopy = originalBoard.createCopy();
            int[] bestMove = miniMaxBase(boardCopy);

            int x = bestMove[0];
            int y = bestMove[1];

            originalBoard.setPoint(x,y,playerSign,true,name);
        }
    }

    /*this function returns the
    This functions input is a 2d array that dimensions are 3 and 3.(I might change this)
    returns the coordinates of the best move. if there are a couple best moves returns the first one.
    The order in which it goes is starts at the top of the left most column loops through the column
    and then goes one column to the right.
    The reason there is miniMax and miniMaxBase is because miniMax uses recursion so miniMaxBase starts that recursion.
    */
    int[] miniMaxBase(int[][] boardCopy){

        int bestScore = Integer.MIN_VALUE;
        int[] optimalMove = {-1, -1};

        for (int i = 0; i < boardCopy.length; i++){
            for (int j = 0; j < boardCopy[0].length; j++){
                if (boardCopy[i][j] == 0){
                    boardCopy[i][j] = playerSign;
                    int score = miniMax(boardCopy,false,(playerSign % 2) + 1);
                    boardCopy[i][j] = 0;
                    if (score > bestScore){
                        bestScore = score;
                        optimalMove[0] = i;
                        optimalMove[1] = j;
                    }
                }
            }
        }
        return optimalMove;
    }

    /*
    The inputs are the current state of the board (In the simulation the miniMax algorithm is doing),
    a boolean that checks if the current player is the one who started the miniMax algorithm and a
    int that represents the sign of player currently playing.
    returns the score of the current position assuming the opponent is playing optimally.
     */
    int miniMax(int[][] boardCopy, boolean isMaximizing , int playerNum){
        int result = Board.miniMaxCheckVictory(boardCopy,playerSign);

        if (result != -2){
            return result;
        }

        int eval;

        if (isMaximizing){
            eval = Integer.MIN_VALUE;

            for (int i = 0; i < boardCopy.length; i++){
                for (int j = 0; j < boardCopy[0].length; j++){
                    if (boardCopy[i][j] == 0){
                        boardCopy[i][j] = playerNum;
                        int score = miniMax(boardCopy, false, (playerNum % 2) + 1);
                        boardCopy[i][j] = 0;
                        eval = Math.max(eval,score);
                    }
                }
            }
        }else {

            eval = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (boardCopy[i][j] == 0) {
                        boardCopy[i][j] = playerNum;
                        int score = miniMax(boardCopy, true, (playerNum % 2) + 1);
                        boardCopy[i][j] = 0;
                        eval = Math.min(eval, score);
                    }
                }
            }
        }

        return eval;
    }


}

