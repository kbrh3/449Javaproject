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
    private int player1points = 0;
    private int player2points = 0;
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
       // return isPlayerOneTurn ? 'S' : 'O';  // Return 'S' or 'O' depending on whose turn it is
       return ' '; 
    }

    @Override
    public boolean makeMove(int row, int col, char letter, char player) {
        if (gameBoard.setMove(row, col, letter, player)) {
            //Check for SOS - award points
            if (gameBoard.checkSOS(row, col)) {
                if (isPlayerOneTurn) {
                    player1points++;
                } else {
                    player2points++;
                }
                //player gets another turn if SOS is formed
                return true;
            }
            togglePlayerTurn();
            return true;
        }
        return false;
    }
    //dont really need row or column, but makes it simpler for gameMode.java
    public boolean checkGameOver(int row, int col) {
        //game ends when the board is full
        return gameBoard.isFull();
    }

    public String getWinner() {
        // In GeneralGame, the player with the most SOSs wins
        if (player1points > player2points) {
            return "Player 1 wins!";
        } else if (player2points > player1points) {
            return "Player 2 wins!";
        } else {
            return "It's a draw!";
        }
    }

    //toggle may be different here, not sure where that will be implimented
    private void togglePlayerTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
}
