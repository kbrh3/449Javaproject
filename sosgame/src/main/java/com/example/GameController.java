package com.example;
//manage logic and state
public class GameController {
    private GameMode currentGameMode;  
    private GameBoard gameBoard;

    public GameController(GeneralGame ggame) {
        this.gameBoard = ggame.getGameBoard(); //gameboard from general game
        this.currentGameMode = ggame;
    }

    public GameController(SimpleGame sgame) {
        this.gameBoard = sgame.getGameBoard();  //gameboard from SimpleGame
        this.currentGameMode = sgame;
    }


//get current player choice
public char getCurrentPlayerChoice() {
    return currentGameMode.getCurrentPlayerChoice(); 
}

public boolean isPlayerOneTurn() {
    return currentGameMode.isPlayerOneTurn();  
}
   
public GameBoard getGameBoard() {
        return this.gameBoard;
    }
public GameMode getGameMode(){
    return this.currentGameMode;
}
    
    //updated logic 11/11/24
    public boolean makeMove(int row, int col, char choice, char player) {
        System.out.println("Player " + player + " attempting to place '" + choice + "' at (" + row + ", " + col + ")");
        if (!gameBoard.isEmpty(row, col)) {
            System.out.println("Invalid move: Cell (" + row + ", " + col + ") is already occupied.");
            return false;
        }
        boolean result = currentGameMode.makeMove(row, col, choice, player);
        System.out.println("Move result for (" + row + ", " + col + "): " + result);
        return result;
    }
    
    

}