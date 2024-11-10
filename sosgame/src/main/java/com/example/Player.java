package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private final String color;      //"Blue" or "Red"
    private final char colorPlayer;    //'B' or 'R'
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
    
    public int[] getComputerMove(GameBoard board) {
        if (!isComputer) {
            return null;
        }
        
        // First try to complete an SOS
        int[] winningMove = findWin(board);
        if (winningMove != null) {
            return winningMove;
        }
        
        // Then try to block opponent's SOS
        int[] blockingMove = BlockMove(board);
        if (blockingMove != null) {
            return blockingMove;
        }
        
        // If no strategic moves, make random move like before
        return makeRandMove(board);
    }
   

    private int[] findWin(GameBoard board) {
    //look at each empty cell for winning move
        for (int row = 0; row < board.getSize(); row++) {
         for (int col = 0; col < board.getSize(); col++) {
            if (board.isEmpty(row, col)) {
                //try s first
                if (couldCompleteSOS(board, row, col, 'S', colorPlayer)) {
                    return new int[]{row, col};
                }
                //try o next
                if (couldCompleteSOS(board, row, col, 'O', colorPlayer)) {
                    return new int[]{row, col};
                }
            }
        }
    }
        return null;
    }

    private int[] BlockMove(GameBoard board) {
        char opponentColor = (colorPlayer == 'B') ? 'R' : 'B';
    
    //look for opponent's possible winning move
        for (int row = 0; row < board.getSize(); row++) {
         for (int col = 0; col < board.getSize(); col++) {
            if (board.isEmpty(row, col)) {
                //check is opponent can sos
                if (couldCompleteSOS(board, row, col, 'S', opponentColor) ||
                    couldCompleteSOS(board, row, col, 'O', opponentColor)) {
                    return new int[]{row, col};
                }
            }
        }
    }
        return null;
    }

    private boolean couldCompleteSOS(GameBoard board, int row, int col, char letter, char player) {
    //copy from existing SOS checking logic
     if (letter == 'S') {
    //check if s is at beginning or end of sos
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

            if (isValidPosition(board, r1, c1) && isValidPosition(board, r2, c2)) {
                if (board.getValueAt(r1, c1) == 'O' && 
                    board.getValueAt(r2, c2) == 'S' &&
                    board.checkplayer(r1, c1) == player &&
                    board.checkplayer(r2, c2) == player) {
                    return true;
                }
            }
        }
    } else if (letter == 'O') {
        //check if o is in middle
        int[][] positions = {
            {-1, 0, 1, 0},  // vertical
            {0, -1, 0, 1},  // horizontal
            {-1, -1, 1, 1}, // diagonal
            {-1, 1, 1, -1}  // other diagonal
        };

        for (int[] pos : positions) {
            int r1 = row + pos[0];
            int c1 = col + pos[1];
            int r2 = row + pos[2];
            int c2 = col + pos[3];

            if (isValidPosition(board, r1, c1) && isValidPosition(board, r2, c2)) {
                //check for s at both ends
                if (board.getValueAt(r1, c1) == 'S' && 
                    board.getValueAt(r2, c2) == 'S' &&
                    board.checkplayer(r1, c1) == player &&
                    board.checkplayer(r2, c2) == player) {
                    return true;
                }
            }
        }
    }
    return false;
    }

    private boolean isValidPosition(GameBoard board, int row, int col) {
    return row >= 0 && row < board.getSize() && 
           col >= 0 && col < board.getSize();
}
    //random mover
    public int[] makeRandMove(GameBoard board) {
        if (!isComputer) {
            return null;
        }
        
        List<int[]> emptyCells = new ArrayList<>();
        
        //fina all empty cells
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
    
    //get computer choice
    public char getComputerSymbol() {
        if (!isComputer) {
            return 'S';  //default just in case
        }
        return random.nextBoolean() ? 'S' : 'O';
    }
}