package com.example;
//Simple Game rules from assignment
//the game ends as soon as the first SOS is formed. done 10/23/24
//The player who forms the first SOS wins the game. done 10/23/24
//if no SOS is created and the board is full, the game ends in a draw. 10/27/24
//this one can use the same switch function I implimented before, but for general that will need to change.

public class SimpleGame implements GameMode {
    private GameBoard gameBoard;
    private boolean isPlayerOneTurn = true;
    private char player1Choice = 'S';  
    private char player2Choice = 'S';  
    private int size;
    private boolean sosFormed = false;
    private char winningPlayer; //store who made the winning sos

    public SimpleGame(int size) {
        this.gameBoard = new GameBoard(size);
        this.size = size;
    }
    @Override
    public boolean makeMove(int row, int col, char letter, char player) {
    if (!isValidMove(row, col)) {
        System.out.println("Invalid move in GameMode: cell is out of bounds or occupied at (" + row + ", " + col + ")");
        return false;
    }
    
    System.out.println("Placing letter '" + letter + "' for player " + player + " at (" + row + ", " + col + ")");
    boolean moveMade = gameBoard.setMove(row, col, letter, player);
    
    if (moveMade) {
        if (letter == 'S') {
            sosFormed = isValidSOS(row, col, player);
            System.out.println("SOS detected after placing 'S': " + sosFormed);
        } else if (letter == 'O') {
            sosFormed = checkOPlacement(row, col, player);
            System.out.println("SOS detected after placing 'O': " + sosFormed);
        }
        
        if (sosFormed) {
            System.out.println("Game over, player " + player + " formed an SOS at (" + row + ", " + col + ")");
            winningPlayer = player;
        } else {
            togglePlayerTurn();
            System.out.println("Turn toggled. Next player is " + (isPlayerOneTurn ? "Player 1 (Blue)" : "Player 2 (Red)"));
        }
        return true;
    } else {
        System.out.println("Failed to place letter at (" + row + ", " + col + ")");
        return false;
    }
    }


    
    private boolean isValidMove(int row, int col) {
        //check if move is within board and space is empty
        return row >= 0 && row < gameBoard.getSize() && 
               col >= 0 && col < gameBoard.getSize() &&
               gameBoard.isEmpty(row, col);
    }

    private boolean isValidSOS(int row, int col, char playerMark) {
        //check all 8 possible directions for sos
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
    
        for (int[] dir : directions) {
            int r1 = row + dir[0];
            int c1 = col + dir[1];
            int r2 = row + 2 * dir[0];
            int c2 = col + 2 * dir[1];
    
            //check if positions exist
            if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
                //check for o in middle and s at end WITH player color matching
                if (gameBoard.getValueAt(r1, c1) == 'O' && 
                    gameBoard.getValueAt(r2, c2) == 'S' &&
                    gameBoard.checkplayer(r1, c1) == playerMark &&
                    gameBoard.checkplayer(r2, c2) == playerMark) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkOPlacement(int row, int col, char playerMark) { // Add playerMark parameter
        //check all 4 possible sos patterns through this 
        int[][] sPositions = {
            {-1, -1, 1, 1},   //diagonal
            {-1, 1, 1, -1},   //other diagonal
            {-1, 0, 1, 0},    //vertical
            {0, -1, 0, 1}     //horizontal
        };
    
        for (int[] pos : sPositions) {
            int r1 = row + pos[0];
            int c1 = col + pos[1];
            int r2 = row + pos[2];
            int c2 = col + pos[3];
    
            //check if positions exist on board
            if (isValidPosition(r1, c1) && isValidPosition(r2, c2)) {
                //check if there are s letters at both ends WITH player color matching
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

    private boolean isValidPosition(int row, int col) {
        //check if position exists within board boundaries
        return row >= 0 && row < gameBoard.getSize() && 
               col >= 0 && col < gameBoard.getSize();
    }

    @Override
    public String getWinner() {
        if (sosFormed) {
            //logic here was backwards, but is fixed now. right player returns 10/28/24
            //return winner from 1st sos found - i think the logic twists the two around, but the end is fine so 
            //i am going to leave it alone 
            return winningPlayer == 'B' ? "Blue Player Wins!" : "Red Player Wins!";
        }
        if (gameBoard.isFull()) {
            return "It's a draw!";
        }
        return null;
    }

    @Override
    public boolean checkGameOver(int row, int col) {
        //game should end if sos formed or board full
        return sosFormed || gameBoard.isFull();
    }

    @Override
    public boolean isPlayerOneTurn() {
        return isPlayerOneTurn;
    }

    @Override
    public char getCurrentPlayerChoice() {
        return isPlayerOneTurn ? player1Choice : player2Choice;   
    }

    private void togglePlayerTurn() {
        isPlayerOneTurn = !isPlayerOneTurn;
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

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
}