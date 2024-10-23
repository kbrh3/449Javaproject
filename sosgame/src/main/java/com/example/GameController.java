package com.example;
//manage logic and state
public class GameController {
    private GameMode currentGameMode;  // Use a common interface like GameMode
    private GameBoard gameBoard;
    //private boolean isPlayerOneTurn = true; //Assuming Player One starts with 'S'
    private char playerOneChoice = 'S';  //Default choice
    private char playerTwoChoice = 'S';  //Default choice
    public GameController(GeneralGame ggame) {
        this.gameBoard = ggame.getGameBoard(); //gameboard from general game
        this.currentGameMode = ggame;
    }

    public GameController(SimpleGame sgame) {
        this.gameBoard = sgame.getGameBoard();  //get gameboard from SimpleGame
        this.currentGameMode = sgame;
    }
//set player one choice
public void setPlayerOneChoice(char choice) {
    this.playerOneChoice = choice;
}
//get player one choice
public char getPlayerOneChoice() {
    return playerOneChoice;
}
//set player two choice
public void setPlayerTwoChoice(char choice) {
    this.playerTwoChoice = choice;
}
//get player two choice
public char getPlayerTwoChoice() {
    return playerTwoChoice;
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
    
    //logic here is a bit off, make sure this isn't causing problems
public boolean makeMove(int row, int col, char choice, char player) {
        choice = getCurrentPlayerChoice(); 
        return currentGameMode.makeMove(row, col, choice, player);  
    }

}