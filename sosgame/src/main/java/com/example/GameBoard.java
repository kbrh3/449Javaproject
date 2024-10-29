package com.example;

public class GameBoard {
    private char[][] grid; //change grid to a char array??
    private char[][] moves;  //stores 'B' (blue) or 'R' (red) - check to make sure this works 10/20/24
    private final int size; //variable for dynamic sizing - may not need this

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == ' ') { 
                    return false; //any cell is empty is not full, end it here
                }
            }
        }
        return true; //game is over if full cells
    }
    

    //constructor - init board with specified size
    public GameBoard(int size) {
        this.size = size; //set board size
        grid = new char[this.size][this.size]; //init grid based on the size
        this.moves = new char[this.size][this.size];
        initializeBoard();
    }
    //this may have a version in controller, make sure we are using the right one
    //place a move and track the player who made it ('B' for blue, 'R' for red) - not done yet, may not need
    public boolean setMove(int row, int col, char letter, char player) {
        // Add boundary checking before accessing array
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;  // Return false for out-of-bounds moves
        }
        
        if (grid[row][col] == ' ' && moves[row][col] == ' ') {
            grid[row][col] = letter;
            moves[row][col] = player;
            return true;
        }
        return false;
    }

    public boolean checkSOS(int row, int col) {
        char player = moves[row][col];
        if (player == ' ' || grid[row][col] != 'S') return false;
    
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
    
        for (int[] dir : directions) {
            int r1 = row + dir[0];
            int c1 = col + dir[1];
            int r2 = row + 2 * dir[0];
            int c2 = col + 2 * dir[1];
    
            if (validBoundary(r1, c1) && validBoundary(r2, c2)) {
                if (grid[r1][c1] == 'O' && grid[r2][c2] == 'S' &&
                    moves[r1][c1] == player && moves[r2][c2] == player) {
                        //debug statement
                    System.out.println("Found SOS in direction: " + dir[0] + "," + dir[1]);
                    return true;
                }
            }
        }
        return false;
    }
    private boolean validBoundary(int row, int col) {
        //check row and col in bounds
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    //method to init/reset the board
    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = ' '; //Use space ' ', empty cell
                moves[row][col] = ' ';  //No player move
            }
        }
    }

    public char getValueAt(int row, int col) {
        return grid[row][col]; //val at specified spot
    }
    public char checkplayer(int row, int col) {
        return moves[row][col];
    }

    public char getMove(int x, int y) {
        return grid[x][y]; //Return player character at position
    }

    public int getSize() {
        return size;  //size of the game board
    }
    public boolean isEmpty(int row, int col) {
        return grid[row][col] == ' ' && moves[row][col] == ' ';
    }

  
}
