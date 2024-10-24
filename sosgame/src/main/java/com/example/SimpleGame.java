package com.example;
//Simple Game
//the game ends as soon as the first SOS is formed.
//The player who forms the first SOS wins the game.
//if no SOS is created and the board is full, the game ends in a draw.
//this one can use the same switch function I implimented before, but for general that will need to change.
public class SimpleGame implements GameMode {
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true;
    private char player1Choice = 'S';  //Default choice
    private char player2Choice = 'S';  //Default choice
    private int size; //this is used, don't know why it is mad

    public SimpleGame(int size) {
        this.gameBoard = new GameBoard(size);
        this.size = size;
    }

    @Override
    public boolean makeMove(int row, int col, char letter, char player) {
        if (gameBoard.setMove(row, col, letter, player)) {
            if (gameBoard.checkSOS(row, col)) {
                return true;  //is sos end game
            }
            //no sos then toggle and continue
            togglePlayerTurn();
            return true;  //valid move but no sos
        }
        return false;  //invalid move
    }
    @Override //worked as of 10/23/24
    public String getWinner() {
    if (gameBoard.isFull()) {
        return "It's a draw!";
    }
    return !isPlayerOneTurn ? "Red Player Wins!" : "Blue Player Wins!";
    }

    @Override
    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    //something might be weird here - check this during testing
    //was backwards, fixed 10/23/24
    @Override
    public char getCurrentPlayerChoice() {
        return isPlayerOneTurn ? player1Choice : player2Choice;   
    }

    @Override
    public boolean checkGameOver(int row, int col) {
        //game ends if sos or board is full (draw)
        return gameBoard.checkSOS(row, col) || gameBoard.isFull();
    }

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
}
