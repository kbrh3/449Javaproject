package com.example;
//General Game:
//The game continues until the entire board is filled.
//The winner is the player who has formed the most SOS sequences by the time the board is full.
//If both players have created the same number of SOSs, the game is a draw.
//When a player successfully forms an SOS, they immediately get another turn. 
//This process continues as long as the player keeps forming SOS
//Each player’s SOS count must be tracked throughout the game -The player with the most SOS sequences at the end of the game wins.

public class GeneralGame implements GameMode{
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true;
    private int playerOneSOSCount = 0;
    private int playerTwoSOSCount = 0;
    int size = 10;

    public GeneralGame(int size) {
        this.gameBoard = new GameBoard(size);
    }
    
    @Override
    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    @Override
    public char getCurrentPlayerChoice() {
        return isPlayerOneTurn ? 'S' : 'O';  // Return 'S' or 'O' depending on whose turn it is
    }

    @Override
    public boolean makeMove(int row, int col, char letter) {
        if (gameBoard.setMove(row, col, letter)) {
            // Check for SOS; if formed, award points
            if (gameBoard.checkSOS(row, col)) {
                if (isPlayerOneTurn) {
                    playerOneSOSCount++;
                } else {
                    playerTwoSOSCount++;
                }
                // In GeneralGame, the player gets another turn if SOS is formed
                return true;
            }
            togglePlayerTurn();
            return true;
        }
        return false;
    }

    public boolean checkGameOver() {
        // In GeneralGame, the game ends when the board is full
        return gameBoard.isFull();
    }

    public String getWinner() {
        // In GeneralGame, the player with the most SOSs wins
        if (playerOneSOSCount > playerTwoSOSCount) {
            return "Player 1 wins!";
        } else if (playerTwoSOSCount > playerOneSOSCount) {
            return "Player 2 wins!";
        } else {
            return "It's a draw!";
        }
    }

    private void togglePlayerTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
