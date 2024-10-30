package com.example;
//general game rules from assignment
//the game continues until the entire board is filled
//the winner is the player who has formed the most sos sequences by the time the board is full
//if both players have created the same number of sos's, the game is a draw
//when a player successfully forms an sos, they immediately get another turn 
//this process continues as long as the player keeps forming sos
//each player's sos count must be tracked throughout the game
//the player with the most sos sequences at the end of the game wins

public class GeneralGame implements GameMode{
    //setup the game board and tracking variables
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true;
    private int player1points = 0;
    private int player2points = 0;
    private char player1Choice = 'S';  //start both players with s as default
    private char player2Choice = 'S';  
    private static final char bluePlayer = 'B'; //used to track move colors on board
    private static final char redPlayer = 'R';
    int size = 10;

    public GeneralGame(int size) {
        this.gameBoard = new GameBoard(size);
    }

    @Override
public boolean makeMove(int row, int col, char letter, char player) {
    //check if move allowed
    if (!isValidMove(row, col)) {
        return false;
    }

    //set current player based on color
    if (player == bluePlayer) {
        isPlayerOneTurn = true;
    } else if (player == redPlayer) {
        isPlayerOneTurn = false;
    }

    //try to make the move on the board
    if (gameBoard.setMove(row, col, letter, player)) {
        boolean sosFormed = false;
        
        //check for sos based on what letter was placed
        if (letter == 'S') {
            sosFormed = isValidSOS(row, col, player);
        } else if (letter == 'O') {
            sosFormed = checkOPlacement(row, col, player);
        }
        
        //if sos was made, add points and keep turn
        if (sosFormed) {
            incrementPoints();
            return true;
        } else {
            //no sos,switch turns
            togglePlayerTurn();
            return true;
        }
    }
    return false;
}

private void incrementPoints() {
    //add point to sos player
    if (isPlayerOneTurn) {
        player1points++;
        System.out.println("Blue player points now: " + player1points);
    } else {
        player2points++;
        System.out.println("Red player points now: " + player2points);
    }
}

public String getWinner() {
    //compare points to get winner - this logic was backwards but is now fixed :)
    if (player1points > player2points) {
        return String.format("Player 1 wins! (Blue: %d, Red: %d)", 
                           player1points, player2points);
    } else if (player2points > player1points) {
        return String.format("Player 2 wins! (Red: %d, Blue: %d)", 
                           player2points, player1points);
    } else {
        return String.format("It's a draw! (Both: %d)", player1points);
    }
}

private boolean isValidSOS(int row, int col, char playerMark) {
    //all possible directions to check for sos
    //gpt helped with the logic here, this confused me
    int[][] directions = {
        {0, 1},   //right
        {1, 0},   //down
        {1, 1},   //diagonal down-right
        {1, -1},  //diagonal down-left
        {0, -1},  //left
        {-1, 0},  //up
        {-1, -1}, //diagonal up-left
        {-1, 1}   //diagonal up-right
    };

    boolean sosFound = false;
    for (int[] dir : directions) {
        //calc positions to check
        int r1 = row + dir[0];
        int c1 = col + dir[1];
        int r2 = row + 2 * dir[0];
        int c2 = col + 2 * dir[1];

        //check if positions on board
        if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
            //look for o in middle and s at end w/ matching colors
            if (gameBoard.getValueAt(r1, c1) == 'O' && 
                gameBoard.getValueAt(r2, c2) == 'S' &&
                gameBoard.checkplayer(r1, c1) == playerMark &&
                gameBoard.checkplayer(r2, c2) == playerMark) {
                sosFound = true;
            }
        }
    }
    return sosFound;
}

private boolean isValidPosition(int row, int col) {
    //make sure position inside the board
    return row >= 0 && row < gameBoard.getSize() && 
           col >= 0 && col < gameBoard.getSize();
}

private boolean isValidMove(int row, int col) {
    //check if move within board boundaries
    return isValidPosition(row, col);
}

private boolean checkOPlacement(int row, int col, char playerMark) {
    //check sos when placing an o
    int[][] sPositions = {
        {-1, -1, 1, 1},   //diagonal
        {-1, 1, 1, -1},   //other diagonal
        {-1, 0, 1, 0},    //vertical
        {0, -1, 0, 1}     //horizontal
    };

    for (int[] pos : sPositions) {
        //calc positions to check
        int r1 = row + pos[0];
        int c1 = col + pos[1];
        int r2 = row + pos[2];
        int c2 = col + pos[3];

        //check if positions are on board
        if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
            //look for s letters at both ends w/ matching colors
            if (gameBoard.getValueAt(r1, c1) == 'S' && 
                gameBoard.getValueAt(r2, c2) == 'S' &&
                gameBoard.checkplayer(r1, c1) == playerMark &&
                gameBoard.checkplayer(r2, c2) == playerMark) {
                return true;
            }
        }
    }
    return false;
}

@Override
public boolean isPlayerOneTurn() {
    return isPlayerOneTurn;
}

@Override
public char getCurrentPlayerChoice() {
    //get current player's selected letter
    return isPlayerOneTurn ? player1Choice : player2Choice;  
}

@Override
public boolean checkGameOver(int row, int col) {
    //game only ends when board completely full
    return gameBoard.isFull();
}

private void togglePlayerTurn() {
    //switch to other player's turn
    isPlayerOneTurn = !isPlayerOneTurn;
}

public GameBoard getGameBoard() {
    return this.gameBoard;
}

//handle player letter choices
public void setPlayer1Choice(char choice) {
    this.player1Choice = choice;
}

public char getPlayer1Choice() {
    return player1Choice;
}

public void setPlayer2Choice(char choice) {
    this.player2Choice = choice;
}

public char getPlayer2Choice() {
    return player2Choice;
}

//gets current point totals
public int getPlayer1Points() {
    return player1points;
}

public int getPlayer2Points() {
    return player2points;
}
}