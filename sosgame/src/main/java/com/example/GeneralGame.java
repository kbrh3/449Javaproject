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
    private char player1Choice = 'S';  //Default choice
    private char player2Choice = 'S';  //Default choice
    private static final char BLUE_PLAYER = 'B'; //color tracking for point collection
    private static final char RED_PLAYER = 'R';
    int size = 10;

    public GeneralGame(int size) {
        this.gameBoard = new GameBoard(size);
    }
//copied from gamecontroller but updated for this game mode
    @Override
    public boolean makeMove(int row, int col, char letter, char player) {
        if (!isValidMove(row, col)) {
            return false;
        }

        char currentPlayer = isPlayerOneTurn ? BLUE_PLAYER : RED_PLAYER;
        
        if (gameBoard.setMove(row, col, letter, currentPlayer)) {
            // Pass the current player's mark to checkSOS
            if (gameBoard.checkSOS(row, col)) {
                // Only increment points if the SOS was formed with the current player's marks
                if (isValidSOS(row, col, currentPlayer)) {
                    incrementPoints();
                    return true;  // Player gets another turn
                }
            }
            togglePlayerTurn();  // No valid SOS, toggle turn
            return true;
        }
        return false;  // Invalid move
    }

    private boolean isValidSOS(int row, int col, char playerMark) {
        // Check all possible SOS directions
        int[][] directions = {
            {0, 1},   // horizontal
            {1, 0},   // vertical
            {1, 1},   // diagonal down-right
            {1, -1},  // diagonal down-left
            {0, -1},  // horizontal reverse
            {-1, 0},  // vertical reverse
            {-1, -1}, // diagonal up-left
            {-1, 1}   // diagonal up-right
        };

        for (int[] dir : directions) {
            int r1 = row + dir[0];
            int c1 = col + dir[1];
            int r2 = row + 2 * dir[0];
            int c2 = col + 2 * dir[1];

            if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
                // Check if we have an 'S' at current position
                if (gameBoard.getValueAt(row, col) == 'S') {
                    // Check if we have 'O' and 'S' in sequence
                    if (gameBoard.getValueAt(r1, c1) == 'O' && 
                        gameBoard.getValueAt(r2, c2) == 'S') {
                        // Verify all three positions have the same player mark
                        if (gameBoard.checkplayer(row, col) == playerMark &&
                            gameBoard.checkplayer(r1, c1) == playerMark &&
                            gameBoard.checkplayer(r2, c2) == playerMark) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < gameBoard.getSize() && 
               col >= 0 && col < gameBoard.getSize();
    }

    private boolean isValidMove(int row, int col) {
        return isValidPosition(row, col);
    }

    private void incrementPoints() {
        if (isPlayerOneTurn) {
            player1points++;
        } else {
            player2points++;
        }
    }

    
    @Override
    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    @Override
    public char getCurrentPlayerChoice() {
        return isPlayerOneTurn ? player1Choice : player2Choice;  
    }

    @Override
public boolean checkGameOver(int row, int col) {
    // Game only ends when board is full in General Game mode
    return gameBoard.isFull();
}
//might need to call gameBoard.isFull() somewhere in here - double check that
    public String getWinner() {
        //player with the most SOSs wins
        if (player1points > player2points) {
           // return "Player 1 wins!";
            return String.format("Player 1 wins! (Blue: %d, Red: %d)", 
            player1points, player2points);} 
            else if (player2points > player1points) {
            //return "Player 2 wins!";
            return String.format("Player 2 wins! (Red: %d, Blue: %d)", 
                               player2points, player1points);}
            else {return String.format("It's a draw! (Both: %d)", player1points);}
    }

    //toggle may be different here, not sure where that will be implimented
    private void togglePlayerTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }
    //set player one choice
    public void setPlayer1Choice(char choice) {
    this.player1Choice = choice;
    }
    //get player one choice
    public char getPlayer1Choice() {
    return player1Choice;
    }
    //set player two choice
    public void setPlayer2Choice(char choice) {
    this.player2Choice = choice;
    }
    //get player two choice
    public char getPlayer2Choice() {
    return player2Choice;
    }

    public int getPlayer1Points() {
        return player1points;
    }

    public int getPlayer2Points() {
        return player2points;
    }
}

