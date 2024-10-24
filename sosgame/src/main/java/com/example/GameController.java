package com.example;
//manage logic and state
public class GameController {
    private GameMode currentGameMode;  // Use a common interface like GameMode
    private GameBoard gameBoard;
    public GameController(GeneralGame ggame) {
        this.gameBoard = ggame.getGameBoard(); //gameboard from general game
        this.currentGameMode = ggame;
    }

    public GameController(SimpleGame sgame) {
        this.gameBoard = sgame.getGameBoard();  //get gameboard from SimpleGame
        this.currentGameMode = sgame;
    }


//get the current player choice
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
    
    //logic here is a bit off, make sure this isn't causing problems
public boolean makeMove(int row, int col, char choice, char player) {
        return currentGameMode.makeMove(row, col, choice, player);  
    }

}