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
        if (!isComputer) return null;
        
        System.out.println("Computer is making a move");
    
        // Find winning move
        int[] move = findWinningMove(board);
        if (move != null && board.isEmpty(move[0], move[1])) {
            System.out.println("Computer chose a winning move at " + move[0] + ", " + move[1]);
            return move;
        }
    
        // Find blocking move
        move = blockMove(board);
        if (move != null && board.isEmpty(move[0], move[1])) {
            System.out.println("Computer chose a blocking move at " + move[0] + ", " + move[1]);
            return move;
        }
    
        // Find setup move
        move = findSetupMove(board);
        if (move != null && board.isEmpty(move[0], move[1])) {
            System.out.println("Computer chose a setup move at " + move[0] + ", " + move[1]);
            return move;
        }
    
        // Random move if no other move is available
        move = makeRandMove(board);
        if (move != null && board.isEmpty(move[0], move[1])) {
            System.out.println("Computer chose a random move at " + move[0] + ", " + move[1]);
            return move;
        }
    
        System.out.println("No valid moves found for computer");
        return null;
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
    System.out.println("\n=== Starting SOS check ===");
    System.out.println("Checking letter: " + letter + " for player: " + player + " at position (" + row + "," + col + ")");

    if (letter == 'S') {
        // Existing 'S' SOS detection logic
        // ... (no changes needed here)
    } else if (letter == 'O') {
        System.out.println("Checking 'O' placement...");

        int[][] positions = {
            // Vertical positions (↑↓)
            {-1, 0, 1, 0},   // down
            {1, 0, -1, 0},   // up
            
            // Horizontal positions (←→)
            {0, -1, 0, 1},   // right
            {0, 1, 0, -1},   // left
            
            // Diagonal positions (\)
            {-1, -1, 1, 1},  // down-right
            {1, 1, -1, -1},  // up-left
            
            // Diagonal positions (/)
            {-1, 1, 1, -1},  // down-left
            {1, -1, -1, 1}   // up-right
        };

        for (int[] pos : positions) {
            int r1 = row + pos[0];
            int c1 = col + pos[1];
            int r2 = row + pos[2];
            int c2 = col + pos[3];

            System.out.println("\nChecking direction: " + 
                             (pos[0] == 0 ? "horizontal" : 
                              pos[0] == pos[2] ? "diagonal" : "vertical"));
            
            if (isValidPosition(board, r1, c1) && isValidPosition(board, r2, c2)) {
                char value1 = board.getValueAt(r1, c1);
                char value2 = board.getValueAt(r2, c2);
                char player1 = board.checkplayer(r1, c1);
                char player2 = board.checkplayer(r2, c2);

                System.out.println("Position 1 (" + r1 + "," + c1 + "): Value=" + value1 + ", Player=" + player1);
                System.out.println("Position 2 (" + r2 + "," + c2 + "): Value=" + value2 + ", Player=" + player2);

                boolean hasValidS1 = value1 == 'S' && player1 == player;
                boolean hasValidS2 = value2 == 'S' && player2 == player;
                
                System.out.println("Valid S at pos1: " + hasValidS1);
                System.out.println("Valid S at pos2: " + hasValidS2);

                if (hasValidS1 && hasValidS2) {
                    System.out.println("=== Found valid SOS! ===");
                    return true;
                }
            } else {
                System.out.println("Positions out of bounds - skipping");
            }
        }
        System.out.println("=== No valid SOS found ===");
    }
    return false;
    }


    private boolean isValidPosition(GameBoard board, int row, int col) {
        return row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize();
    }

    //random move in empty cell
    public int[] makeRandMove(GameBoard board) {
        System.out.println("Computer choosing random move");
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isEmpty(i, j)) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        
        if (!emptyCells.isEmpty()) {
            int[] move = emptyCells.get(random.nextInt(emptyCells.size()));
            System.out.println("Randomly selected move at (" + move[0] + ", " + move[1] + ")");
            return move;
        }
        
        System.out.println("No empty cells found for random move");
        return null;
    }

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
