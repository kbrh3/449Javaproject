package com.example;
public interface GameMode {
    //common functions between modes - any needed by the UI especially!!!
    boolean makeMove (int row, int col, char letter, char player);  //make move at position given
    boolean isPlayerOneTurn();           //true if p1 turn
    char getCurrentPlayerChoice();       //return choice of player
    public boolean checkGameOver(int row, int col); //check for game over
    public String getWinner(); //get winner in pop-up
    public void setPlayer1Choice(char choice); //p1 choice setter
    public char getPlayer1Choice();             //p1 choice getter
    public void setPlayer2Choice(char choice); //p2 choice setter
    public char getPlayer2Choice();             //p2 choice getter
    /*Still need
     * 1. add the line to go across sos's
     * 2. count points in general game - done?? 10/26/24 but may have issues
     * 3. tests for all functions - done 10/28/24
     * 4. double check all game logic 10/31/24
     * 5. gameboards should reset after game completed 11/1/24
     * 6. AI player - this is due for sprint 4
     */
}
