package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private final String color;      // "Blue" or "Red"
    private final char colorPlayer;    // 'B' or 'R'
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

    //get computer's move
    public int[] getComputerMove(GameBoard board) {
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