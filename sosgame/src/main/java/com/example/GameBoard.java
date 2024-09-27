package com.example;

public class GameBoard {
    private char[][] board;
    private int size; // The size of the game board - may need to change with simple vs general

    // Constructor to initialize the board with a given size
    public GameBoard(int size) {
        this.size = size;
        this.board = new char[size][size];
        initializeBoard();
    }

    // Method to initialize or reset the board
    public void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '-'; // Using '-' to indicate an empty spot
            }
        }
    }
    public char[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    // Method to place a character on the board
    public boolean placeMark(int row, int col, char mark) {
        if (row >= 0 && row < size && col >= 0 && col < size && board[row][col] == '-') {
            board[row][col] = mark;
            return true;
        }
        return false;
    }

    // Method to check if the board is full
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Utility method to print the board (debugging)
    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
