package com.example;
//Simple Game
//the game ends as soon as the first SOS is formed.
//The player who forms the first SOS wins the game.
//if no SOS is created and the board is full, the game ends in a draw.
//this one can use the same switch function I implimented before, but for general that will need to change.
public class SimpleGame implements GameMode {
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true;
    int size = 8;

    public SimpleGame(int size) {
        this.gameBoard = new GameBoard(size);
    }

    @Override
    public boolean makeMove(int row, int col, char letter) {
        if (gameBoard.setMove(row, col, letter)) {
            togglePlayerTurn();
            return true;
        }
        return false;
    }

    @Override
    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    @Override
    public char getCurrentPlayerChoice() {
        return isPlayerOneTurn ? 'S' : 'O';  // Return 'S' or 'O' depending on whose turn it is
    }

    public boolean checkGameOver(int row, int col) {
        //game ends as soon as one SOS is formed
        return gameBoard.checkSOS(row, col);
    }

    public String getWinner() {
        //player who forms the SOS wins immediately
        return isPlayerOneTurn ? "Player 2" : "Player 1";  // Turn is already toggled
    }

    private void togglePlayerTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
