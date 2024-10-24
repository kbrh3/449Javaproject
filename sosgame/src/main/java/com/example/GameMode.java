package com.example;
public interface GameMode {
    boolean makeMove (int row, int col, char letter, char player);  // Handles a move at the given position
    boolean isPlayerOneTurn();           // Returns true if it's player one's turn
    char getCurrentPlayerChoice();       // Returns the current player's choice (S or O)
    public boolean checkGameOver(int row, int col);
    public String getWinner();
    //set player one choice
    public void setPlayer1Choice(char choice);
    public char getPlayer1Choice();
    public void setPlayer2Choice(char choice);
    public char getPlayer2Choice();
    /*Still need
     * 1. add the line to go across sos's
     * 2. count points in general game
     * 3. tests for all functions 
     * 4. double check all game logic
     */
}
