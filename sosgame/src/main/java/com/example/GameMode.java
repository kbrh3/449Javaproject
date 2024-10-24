package com.example;
public interface GameMode {
    boolean makeMove (int row, int col, char letter, char player);  // Handles a move at the given position
    boolean isPlayerOneTurn();           // Returns true if it's player one's turn
    char getCurrentPlayerChoice();       // Returns the current player's choice (S or O)
    public boolean checkGameOver(int row, int col);
    public String getWinner(int row, int col);
}
