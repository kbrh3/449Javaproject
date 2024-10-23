package com.example;

public class GameBoard {
    private char[][] grid; //Change grid to a char array??
    private char[][] moves;  // Stores 'B' (blue) or 'R' (red) - check to make sure this works 10/20/24
    private int size; //Variable for dynamic sizing

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
    

    //Constructor - initialize the board with a specified size
    public GameBoard(int size) {
        this.size = size; //Set board size
        grid = new char[this.size][this.size]; //Initialize grid based on the size
        this.moves = new char[this.size][this.size];
        initializeBoard();
    }
    //this may have a version in controller, make sure we are using the right one
    //Place a move and track the player who made it ('B' for blue, 'R' for red)
    public boolean setMove(int row, int col, char letter, char player) {
        if (grid[row][col] == ' ' && moves[row][col] == ' ') {
            grid[row][col] = letter;
            moves[row][col] = player;  //Track players move
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

        //Check all four directions - GPT assisted with the logic here, this was a tough one
        for (int[] dir : directions) {
            int checkRow = dir[0];
            int checkCol = dir[1];

            if (grid[row + checkRow][col + checkCol] == 'O' &&
                grid[row + 2 * checkRow][col + 2 * checkCol] == 'S' &&
                moves[row + checkRow][col + checkCol] == player &&
                moves[row + 2 * checkRow][col + 2 * checkCol] == player) {
                return true;  // SOS found!
            }
        }

        return false;  // No valid SOS found
    }

    //Method to initialize / reset the board
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

    //Set the move on the board, checking if the cell is empty
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
