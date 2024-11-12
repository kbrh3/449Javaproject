package com.example;

public class GameBoard {
    private char[][] grid; // Stores the letters ('S' or 'O')
    private char[][] moves; // Stores 'B' (Blue) or 'R' (Red) for each player's move
    private final int size; // Size of the board

    //ask chatGPT to add debugging print statements to all my code in this class
    public GameBoard(int size) {
        this.size = size; 
        grid = new char[this.size][this.size]; 
        moves = new char[this.size][this.size];
        initializeBoard();
    }

    // Checks if the board is full (used to detect draw conditions)
    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == ' ') { 
                    return false; // Any empty cell means the board is not full
                }
            }
        }
        System.out.println("Board is full, game is a draw if no SOS was formed.");
        return true; 
    }

    // Initializes or resets the board to empty state
    private void initializeBoard() {
        System.out.println("Initializing board of size " + size + "x" + size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = ' '; 
                moves[row][col] = ' ';
            }
        }
    }

    // Places a move on the board and tracks the player who made it
    public boolean setMove(int row, int col, char letter, char player) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            System.out.println("Invalid move: (" + row + ", " + col + ") is out of bounds.");
            return false; 
        }

        if (grid[row][col] == ' ' && moves[row][col] == ' ') {
            grid[row][col] = letter;
            moves[row][col] = player;
            System.out.println("Set move: Player " + player + " placed '" + letter + "' at (" + row + ", " + col + ")");
            return true;
        } else {
            System.out.println("Invalid move: Cell (" + row + ", " + col + ") is already occupied by '" 
                               + grid[row][col] + "' for player " + moves[row][col]);
            return false;
        }
    }

    // Checks if an SOS is formed from a given starting position
    public boolean checkSOS(int row, int col) {
        char player = moves[row][col];
        if (player == ' ' || grid[row][col] != 'S') return false;

        System.out.println("Checking SOS formation for player " + player + " starting at (" + row + ", " + col + ")");
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        for (int[] dir : directions) {
            int r1 = row + dir[0];
            int c1 = col + dir[1];
            int r2 = row + 2 * dir[0];
            int c2 = col + 2 * dir[1];

            if (validBoundary(r1, c1) && validBoundary(r2, c2)) {
                if (grid[r1][c1] == 'O' && grid[r2][c2] == 'S' &&
                    moves[r1][c1] == player && moves[r2][c2] == player) {
                    System.out.println("SOS found at (" + row + "," + col + ") in direction (" + dir[0] + "," + dir[1] + ")");
                    return true;
                }
            } else {
                System.out.println("Direction (" + dir[0] + "," + dir[1] + ") goes out of bounds from (" + row + "," + col + ")");
            }
        }
        System.out.println("No SOS found for player " + player + " at (" + row + ", " + col + ")");
        return false;
    }

    // Checks if a cell is within the board boundaries
    private boolean validBoundary(int row, int col) {
        boolean isValid = row >= 0 && row < size && col >= 0 && col < size;
        if (!isValid) {
            System.out.println("Out of bounds check: (" + row + ", " + col + ") is outside of board limits.");
        }
        return isValid;
    }

    // Gets the value ('S' or 'O') at a specific position on the board
    public char getValueAt(int row, int col) {
        return grid[row][col];
    }

    // Gets the player ('B' or 'R') at a specific position on the board
    public char checkplayer(int row, int col) {
        return moves[row][col];
    }

    // Returns the size of the board
    public int getSize() {
        return size;
    }

    // Checks if a cell is empty (no move has been made there)
    public boolean isEmpty(int row, int col) {
        boolean empty = grid[row][col] == ' ' && moves[row][col] == ' ';
        System.out.println("Cell (" + row + ", " + col + ") is " + (empty ? "empty" : "occupied"));
        return empty;
    }
    //for testing
     public char getMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Invalid position: (" + row + ", " + col + ")");
        }
        return grid[row][col];
    }
    
}
