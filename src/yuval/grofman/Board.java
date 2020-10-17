package yuval.grofman;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Board{

    private int [][] board = new int[3][3];

    boolean gameOngoing = true;// true = ongoing ,false = not ongoing

    /*
    input is the coordinates of a point just put.
    returns 1 if the player that put that point won.
    Assuming the point is actually the last point placed it is not possible the other player won,
    therefore no need to check is he lost. returns -1 if the game ended with a tie and 0 if the game didn't end.
     */
    public int checkVictory(int x, int y){

        if (getPoint(0,y) == getPoint(1,y) && getPoint(1,y) == getPoint(2,y) && getPoint(0,y) != 0){// checks for horizontal line
            return 1;
        }

        if (getPoint(x,0) == getPoint(x,1) && getPoint(x,1) == getPoint(x,2) && getPoint(x,0) != 0){// checks for vertical line
            return 1;
        }

        if ((x  + y) % 2 == 0){// if the remainder of the sum of coordinates of the point is zero that means the point is on a diagonal
            if (getPoint(0,0) == getPoint(1,1) && getPoint(1,1) == getPoint(2,2) && getPoint(0, 0) != 0 ){//checks the diagonal that starts top left
                return 1;
            }

            if (getPoint(0,2) == getPoint(1,1) && getPoint(1,1) == getPoint(2,0) && getPoint(0, 2) != 0 ){//checks the diagonal that starts bottom left
                return 1;
            }
        }

        for (int i = 0; i < board.length; i++){//checks for a tie
            for (int j = 0; j < 3; j++){
                if (this.getPoint(i,j) == 0){
                    return 0;
                }
            }
        }
        return -1;
    }

    /*
    function inputs are the coordinates of the point. The sign of the player playing this move and a
    boolean the tells the function if to print the boardState or not.
    returns (true/false) if the program this function needs to run again to continue the game
    true = don't repeat
    false =  repeat*/
    public boolean setPoint(int x , int y, int playerSign, boolean shouldPrint, String playerName){// (x,y) are the coordinates
        if (!gameOngoing) {
            if (shouldPrint) {
                System.out.println(playerName + " The match has already ended");
            }
            return true;
        }

        if (board[x][y] != 0){
            if (shouldPrint){
                System.out.println(playerName + " the point has already been set.");
            }
            return false;
        }

        board[x][y] = playerSign;

        if (shouldPrint) {
            printBoard();
        }
        int result = checkVictory(x,y);

        if (result == 1){
            System.out.println("Good game.  " + playerName + "you won!!!");
            gameOngoing = false;
            return true;
        }

        if (result == -1){
            System.out.println("Good game. The game has ended with a tie.");
            gameOngoing = false;
            return true;
        }

        return true;
    }

    //returns the sign on that point. (ie: 1 ,2 or 0)
    public int getPoint(int x,int y){
        return board[x][y];
    }

    //prints the current state of the board
    public void printBoard(){
        System.out.println("---------------------------");
        System.out.println("the current state of the board is:");
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (j == 2){
                    System.out.println(board[j][i]);
                    continue;
                }
                System.out.print(board[j][i] + " ");
            }
        }
        System.out.println("---------------------------");
    }

    //resets the board so that all the points are 0 and sets gameOngoing to true
    public void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                board[i][j] = 0;
            }
        }

        gameOngoing = true;
        return;
    }

    /*this is for the miniMax algorithm. It is used to score each position.
    returns 1 if player who won is the player PlayerSign (ie: if player 1 won and PlayerSign == 1 it will return 1) ,
    returns 0 if its a tie ,returns -1 if the Opponent won and returns -2 if the game didn't end yet.
     */
    public static int miniMaxCheckVictory(int[][] gameBoard, int playerSign){

        for (int i = 0; i < gameBoard.length; i++){

            if (gameBoard[i][0] == gameBoard[i][1] && gameBoard[i][1] == gameBoard[i][2] && gameBoard[i][0] != 0){

                if (gameBoard[i][0] == playerSign){
                    return 1;
                }else {
                    return -1;
                }
            }

            if (gameBoard[0][i] == gameBoard[1][i] && gameBoard[1][i] == gameBoard[2][i] && gameBoard[0][i] != 0){

                if (gameBoard[0][i] == playerSign){
                    return 1;
                }else {
                    return -1;
                }
            }

        }

        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != 0){
            if (gameBoard[1][1] == playerSign){
                return 1;
            }else {
                return -1;
            }
        }

        if (gameBoard[0][2] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][0] && gameBoard[2][0] != 0){
            if (gameBoard[1][1] == playerSign){
                return 1;
            }else {
                return -1;
            }
        }

        for (int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard[0].length; j++){
                if (gameBoard[i][j] == 0){
                    return -2;
                }
            }
        }

        return 0;
    }

    /*returns a copy of the board.
     Used mainly for the miniMax algorithm.
     */
    public int[][] createCopy (){
        int[][] boardCopy = new int[3][3];

        for (int i = 0; i < this.board.length; i++){
            for (int j = 0; j < this.board[0].length; j++){

                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }

    int getNumCol(){
        return board.length;
    }

    int getNumRows(){
        return board[0].length;
    }
}

