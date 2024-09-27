package com.example;

public class GameController {
    private GameBoard board;
    private int currentPlayer;
    private static final char[] players = {'S', 'O'}; // Players represented by 'S' and 'O'

    public GameController(int boardSize) {
        this.board = new GameBoard(boardSize);
        this.currentPlayer = 0; // Start with player 'S'
    }

    // Initialize or restart the game
    public void initializeGame() {
        board.initializeBoard();
        currentPlayer = 0; // Resets to player 'S'
        System.out.println("Game started. Player 'S' goes first.");
    }

    // Player makes a move at (row, col)
    public boolean makeMove(int row, int col) {
        if (board.placeMark(row, col, players[currentPlayer])) {
            if (checkWinCondition()) {
                System.out.println("Player " + players[currentPlayer] + " wins!");
                return true;
            }
            if (board.isFull()) {
                System.out.println("The game is a draw.");
                return true;
            }
            // Switch player
            currentPlayer = (currentPlayer + 1) % 2;
            System.out.println("Player '" + players[currentPlayer] + "' turn.");
            return true;
        }
        return false; // Invalid move
    }

    private boolean checkWinCondition() {
        // Check all rows and columns
        for (int i = 0; i < board.getSize(); i++) {
            if (checkRowForSOS(i) || checkColumnForSOS(i)) {
                return true;
            }
        }
    
        // Check diagonals
        return checkDiagonalForSOS() || checkAntiDiagonalForSOS();
    }
    
    private boolean checkRowForSOS(int row) {
        for (int i = 0; i <= board.getSize() - 3; i++) {
            if (board.getBoard()[row][i] == 'S' && board.getBoard()[row][i+1] == 'O' && board.getBoard()[row][i+2] == 'S') {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkColumnForSOS(int col) {
        for (int i = 0; i <= board.getSize() - 3; i++) {
            if (board.getBoard()[i][col] == 'S' && board.getBoard()[i+1][col] == 'O' && board.getBoard()[i+2][col] == 'S') {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkDiagonalForSOS() {
        for (int i = 0; i <= board.getSize() - 3; i++) {
            if (board.getBoard()[i][i] == 'S' && board.getBoard()[i+1][i+1] == 'O' && board.getBoard()[i+2][i+2] == 'S') {
                return true;
            }
        }
        return false;
    }
    
    private boolean checkAntiDiagonalForSOS() {
        for (int i = 0; i <= board.getSize() - 3; i++) {
            int j = board.getSize() - 1 - i;
            if (board.getBoard()[i][j] == 'S' && board.getBoard()[i+1][j-1] == 'O' && board.getBoard()[i+2][j-2] == 'S') {
                return true;
            }
        }
        return false;
    }
    

    
}
