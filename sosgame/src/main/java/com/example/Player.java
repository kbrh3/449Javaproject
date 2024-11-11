package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private final String color;       //"Blue" or "Red"
    private final char colorPlayer;   //'B' or 'R'
    private final Random random;
    private boolean isComputer;
    
    public Player(String color) {
        this.color = color;
        this.colorPlayer = color.equals("Blue") ? 'B' : 'R';
        this.random = new Random();
        this.isComputer = false;
    }
    
    public void setIsComputer(boolean isComputer) {
        this.isComputer = isComputer;
    }
    
    public boolean isComputer() {
        return isComputer;
    }
    
    public char getColorofPlayer() {
        return colorPlayer;
    }
    
    //find best move for the computer player
    public int[] getComputerMove(GameBoard board) {
        if (!isComputer) {
            return null;
        }
        
        //try and complete sos
        int[] winningMove = findWinningMove(board);
        if (winningMove != null) {
            return winningMove;
        }

        // try to block sos?? - may not be workign right
        int[] blockingMove = blockMove(board);
        if (blockingMove != null) {
            return blockingMove;
        }

        //set up future sos if others arent found
        int[] strategicMove = findSetupMove(board);
        if (strategicMove != null) {
            return strategicMove;
        }
        
        //last choice: make random move
        return makeRandMove(board);
    }
//gpt helped with the logic in this function
    private int[] findSetupMove(GameBoard board) {
        //look for adjacents to o
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.isEmpty(row, col) && hasAdjacentO(board, row, col)) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
    
    private boolean hasAdjacentO(GameBoard board, int row, int col) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidPosition(board, newRow, newCol) && 
                board.getValueAt(newRow, newCol) == 'O') {
                return true;
            }
        }
        return false;
    }

    private int[] findWinningMove(GameBoard board) {
        //checking empty cells for s or o's that may make sos
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.isEmpty(row, col)) {
                    if (couldCompleteSOS(board, row, col, 'S', colorPlayer)) {
                        return new int[]{row, col};
                    }
                    if (couldCompleteSOS(board, row, col, 'O', colorPlayer)) {
                        return new int[]{row, col};
                    }
                }
            }
        }
        return null;
    }

    private int[] blockMove(GameBoard board) {
        char opponentColor = (colorPlayer == 'B') ? 'R' : 'B';
        
        //move that blocks others sos
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.isEmpty(row, col)) {
                    if (couldCompleteSOS(board, row, col, 'S', opponentColor) ||
                        couldCompleteSOS(board, row, col, 'O', opponentColor)) {
                        return new int[]{row, col};
                    }
                }
            }
        }
        return null;
    }
//GPT helped me with the logic for this one
//public for tests
public boolean couldCompleteSOS(GameBoard board, int row, int col, char letter, char player) {
    if (letter == 'S') {
        // Existing 'S' SOS detection logic
        // ... (no changes needed here)
    } else if (letter == 'O') {
        // Debug: Starting check for 'O' SOS
        System.out.println("Checking SOS completion for 'O' at (" + row + ", " + col + ")");

        int[][] positions = {
            {-1, 0, 1, 0},   // vertical
            {0, -1, 0, 1},   // horizontal
            {-1, -1, 1, 1},  // diagonal \
            {-1, 1, 1, -1}   // diagonal /
        };

        for (int[] pos : positions) {
            int r1 = row + pos[0];
            int c1 = col + pos[1];
            int r2 = row + pos[2];
            int c2 = col + pos[3];

            // Debug: Print values of neighboring cells
            System.out.println("Checking positions (" + r1 + ", " + c1 + ") and (" + r2 + ", " + c2 + ")");
            if (isValidPosition(board, r1, c1) && isValidPosition(board, r2, c2)) {
                char value1 = board.getValueAt(r1, c1);
                char value2 = board.getValueAt(r2, c2);
                char player1 = board.checkplayer(r1, c1);
                char player2 = board.checkplayer(r2, c2);

                // Debug: Output values of surrounding cells
                System.out.println("Values: " + value1 + " at (" + r1 + ", " + c1 + "), " +
                                   value2 + " at (" + r2 + ", " + c2 + ")");
                System.out.println("Players: " + player1 + " at (" + r1 + ", " + c1 + "), " +
                                   player2 + " at (" + r2 + ", " + c2 + ")");

                if (value1 == 'S' && value2 == 'S' &&
                    player1 == player && player2 == player) {
                    return true;
                }
            }
        }
    }
    return false;
}



    private boolean isValidPosition(GameBoard board, int row, int col) {
        return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
    }

    //random move in empty cell
    public int[] makeRandMove(GameBoard board) {
        if (!isComputer) {
            return null;
        }
        
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isEmpty(i, j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        
        if (!emptyCells.isEmpty()) {
            return emptyCells.get(random.nextInt(emptyCells.size()));
        }
        
        return null;
    }
    
    //get which symbol it should play
    public char getComputerSymbol(GameBoard board, int row, int col) {
        if (!isComputer) {
            return 'S';
        }
        for (char symbol : new char[]{'S', 'O'}) {
            if (couldCompleteSOS(board, row, col, symbol, colorPlayer)) {
                return symbol;
            }
        }
        return random.nextDouble() < 0.6 ? 'S' : 'O';
    }
}
