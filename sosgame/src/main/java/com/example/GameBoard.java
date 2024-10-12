package com.example;

public class GameBoard {
    private char[][] grid; // Change grid to a char array
    private int size; // Variable to handle dynamic sizing

    // Constructor to initialize the board with a specified size
    public GameBoard(int size) {
        this.size = size; // Set the board size
        grid = new char[this.size][this.size]; // Initialize the grid based on the size
        initializeBoard();
    }

    // Method to initialize or reset the board
    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = ' '; // Use space ' ' to indicate an empty cell
            }
        }
    }

    public char getValueAt(int row, int col) {
        return grid[row][col]; // Return the value at specific row and column
    }

    // Set the move on the board, checking if the cell is empty
    public boolean setMove(int x, int y, char player) {
        if (grid[x][y] == ' ') { // Check if the cell is empty
            grid[x][y] = player; // Place player's character
            return true;
        }
        return false;
    }

    public char getMove(int x, int y) {
        return grid[x][y]; // Return the player character at the position
    }

    public int getSize() {
        return size;  // Returns the size of the game board
    }
}
