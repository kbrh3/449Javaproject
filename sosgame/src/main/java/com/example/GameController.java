package com.example;
//manage logic and state
public class GameController {
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true; // Assuming Player One starts with 'S'
    private char playerOneChoice = 'S';  // Default choice
    private char playerTwoChoice = 'S';  // Default choice
    public GameController(int size) {
        this.gameBoard = new GameBoard(size);
    }
// Method to set player one's choice
public void setPlayerOneChoice(char choice) {
    this.playerOneChoice = choice;
}

public char getPlayerOneChoice() {
    return playerOneChoice;
}

public void setPlayerTwoChoice(char choice) {
    this.playerTwoChoice = choice;
}

public char getPlayerTwoChoice() {
    return playerTwoChoice;
}

public void togglePlayerTurn() {
    isPlayerOneTurn = !isPlayerOneTurn;
}

// Method to get the current player's choice
public char getCurrentPlayerChoice() {
    return isPlayerOneTurn ? playerOneChoice : playerTwoChoice;
}
public boolean isPlayerOneTurn() {
    return isPlayerOneTurn;
}

public boolean makeMove(int x, int y) {
    char currentPlayerChar = getCurrentPlayerChoice(); // Get the choice before toggling turn
    if (gameBoard.setMove(x, y, currentPlayerChar)) {
        togglePlayerTurn(); // Toggle turn after setting the move
        return true;
    }
    return false;
}
   
    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
