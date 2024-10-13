package com.example;

import static org.junit.jupiter.api.Assertions.*;  // For assertions
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    public void setUp() {
        gameBoard = new GameBoard(3);  // Create a 3x3 game board for testing
    }

    @Test //all spots should be empty to start
    public void testBoardInit() {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                assertEquals(' ', gameBoard.getValueAt(i, j), 
                    "All cells should be initialized to ' ' (empty space)");
            }
        }
    }

    @Test //board size should be correct to whatever it initializes as
    public void testGetSize() {
        assertEquals(3, gameBoard.getSize(), "Board size should be 3x3");
    }

    @Test //can place on empty spot
    public void testMoveOnEmptySpot() {
        boolean moveMade = gameBoard.setMove(0, 0, 'S');  // Place 'S' at (0, 0)
        assertTrue(moveMade, "Move should be allowed on an empty cell");
        assertEquals('S', gameBoard.getValueAt(0, 0), 
            "Cell (0,0) should contain 'S' after the move");
    }

    @Test //spot not empty? can't place stuff there
    public void testMoveOnOccupiedSpot() {
        gameBoard.setMove(0, 0, 'S');  // Place 'S' at (0, 0)
        boolean moveMade = gameBoard.setMove(0, 0, 'O');  // Try to place 'O' in the same cell
        assertFalse(moveMade, "Move should not be allowed on an occupied cell");
        assertEquals('S', gameBoard.getValueAt(0, 0), 
            "Cell (0,0) should still contain 'S'");
    }

    @Test //return the correct char from spot
    public void testGetMove() {
        gameBoard.setMove(1, 1, 'O');  // Place 'O' at (1, 1)
        assertEquals('O', gameBoard.getMove(1, 1), 
            "Cell (1,1) should return 'O'");
    }

    @Test //cant place a move outside the board
    public void testInvalidCoordinates() {
        // Placing moves outside the board should throw ArrayIndexOutOfBoundsException
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            gameBoard.setMove(5, 5, 'S');  // Invalid coordinates
        }, "Setting a move outside the board should throw an exception");
    }
}

