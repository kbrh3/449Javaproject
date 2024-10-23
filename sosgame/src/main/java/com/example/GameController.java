package com.example;
//manage logic and state
public class GameController {
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true; //Assuming Player One starts with 'S'
    private char playerOneChoice = 'S';  //Default choice
    private char playerTwoChoice = 'S';  //Default choice
    public GameController(int size) {
        this.gameBoard = new GameBoard(size);
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

public void togglePlayerTurn() {
    isPlayerOneTurn = !isPlayerOneTurn;
}

//get the current player choice
public char getCurrentPlayerChoice() {
    return isPlayerOneTurn ? playerOneChoice : playerTwoChoice;
}

public boolean isPlayerOneTurn() {
    return isPlayerOneTurn;
}

public boolean makeMove(int x, int y) {
    char currentPlayerChar = getCurrentPlayerChoice(); //Get the choice b4 toggling turn
    if (gameBoard.setMove(x, y, currentPlayerChar)) {
        togglePlayerTurn(); //Toggle turn after setting move
        return true;
    }
    return false;
}
   
public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
