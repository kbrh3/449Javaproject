package com.example;

public class Player {
    private String name;
    private char symbol; // This could be 'S' or 'O'
    private boolean isHuman; //for human vs AI player

    // Constructor
    public Player(String name, char symbol, boolean isHuman) {
        this.name = name;
        this.symbol = symbol;
        this.isHuman = isHuman;
    }

    // Getters
    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isHuman() {
        return isHuman;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setIsHuman(boolean isHuman) {
        this.isHuman = isHuman;
    }

    // more methods here when needed
}
