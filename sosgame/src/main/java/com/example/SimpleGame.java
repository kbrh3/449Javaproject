package com.example;
//Simple Game
//the game ends as soon as the first SOS is formed.
//The player who forms the first SOS wins the game.
//if no SOS is created and the board is full, the game ends in a draw.
//this one can use the same switch function I implimented before, but for general that will need to change.
public class SimpleGame implements GameMode {
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true;
    private int size = 8;

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

    //determines winner or if draw
    public String getWinner(int row, int col) {
        if (gameBoard.checkSOS(row, col)) {
            //the turn was toggled, the last move was by the other player
            return !isPlayerOneTurn ? "Player 1 wins!" : "Player 2 wins!";
        }
        //no sos and the board is full- draw
        if (gameBoard.isFull()) {
            return "It's a draw!";
        }
        // No winner yet - for edge cases/called early
        return "";
    }

    @Override
    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    //something might be weird here - check this during testing
    @Override
    public char getCurrentPlayerChoice() {
        //return isPlayerOneTurn ? 'S' : 'O';  // 'S' for Player 1, 'O' for Player 2
        return ' '; 
    }

    //Check if the game is over
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
}
