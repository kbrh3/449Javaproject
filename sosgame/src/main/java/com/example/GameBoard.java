package com.example;

public class GameBoard {
    private char[][] grid; //change grid to a char array??
    private char[][] moves;  // stores 'B' (blue) or 'R' (red) - check to make sure this works 10/20/24
    private int size; //variable for dynamic sizing - may not need this

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == ' ') { // Assuming ' ' represents an empty cell
                    return false; //If any cell is empty-board is not full-end it here
                }
            }
        }
        return true; //cells are all filled, it is true, game is over
    }
    

    //constructor - initialize  board with specified size
    public GameBoard(int size) {
        this.size = size; //Set board size
        grid = new char[this.size][this.size]; //Initialize grid based on the size
        this.moves = new char[this.size][this.size];
        initializeBoard();
    }
    //this may have a version in controller, make sure we are using the right one
    //place a move and track the player who made it ('B' for blue, 'R' for red) - not done yet, may not need
    public boolean setMove(int row, int col, char letter, char player) {
        if (grid[row][col] == ' ' && moves[row][col] == ' ') {
            grid[row][col] = letter;
            moves[row][col] = player;  //Track players move - may not need, check back on this later
            return true;
        }
        return false;  //invalid move, spot already taken
    }

    public boolean checkSOS(int row, int col) {
        char player = moves[row][col];
    
        if (player == ' ') return false;   //if player hasn't made a move then there is no sos
        if (grid[row][col] != 'S') return false;  //sos has to start with s
        //check directions around the s
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        //check all four directions - GPT assisted with the logic here, this was a tough one
        for (int[] dir : directions) {
            int checkRow = dir[0];
            int checkCol = dir[1];

            //check boundaries before accessing
        if (validBoundary(row + checkRow, col + checkCol) && 
        validBoundary(row + 2 * checkRow, col + 2 * checkCol)) {
        //check for the sos pattern
        if (grid[row + checkRow][col + checkCol] == 'O' &&
            grid[row + 2 * checkRow][col + 2 * checkCol] == 'S' &&
            moves[row + checkRow][col + checkCol] == player &&
            moves[row + 2 * checkRow][col + 2 * checkCol] == player) {
            return true;  //if sos found
            }
        }
    }

        return false;  //No sos found
    }
    private boolean validBoundary(int row, int col) {
        //check the row and col are in bounds of the grid
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    //method to initialize / reset the board
    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = ' '; //Use space ' ', means an empty cell
                moves[row][col] = ' ';  //No player move
            }
        }
    }

    public char getValueAt(int row, int col) {
        return grid[row][col]; //val at specified spot
    }

    //set the move on the board, check if the cell is empty
    public boolean setMove(int x, int y, char player) {
        if (grid[x][y] == ' ') { //Check if cell is empty
            grid[x][y] = player; //Place player's character
            return true;
        }
        return false;
    }

    public char getMove(int x, int y) {
        return grid[x][y]; //Return player character at position
    }

    public int getSize() {
        return size;  //size of the game board
    }
}
