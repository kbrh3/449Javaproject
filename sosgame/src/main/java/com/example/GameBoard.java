package com.example;

public class GameBoard {
    private char[][] grid; //Change grid to a char array - not really used anymore- may delete
    private int size; //Variable for dynamic sizing

    //Constructor - initialize the board with a specified size
    public GameBoard(int size) {
        this.size = size; //Set board size
        grid = new char[this.size][this.size]; //Initialize grid based on the size
        initializeBoard();
    }

    //Method to initialize / reset the board
    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = ' '; // Use space ' ' to indicate an empty cell
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
