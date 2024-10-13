package com.example;

import static org.junit.jupiter.api.Assertions.*;  // For assertions
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    public void setUp() {
        gameController = new GameController(3);  // Create a 3x3 game board
    }

    @Test //make sure P1 starts always
    public void testPlayer1StartsFirst() {
        assertTrue(gameController.isPlayerOneTurn(), "Player One should start first");
    }

    @Test //make sure it switched between players
    public void testTogglePlayerTurn() {
        gameController.togglePlayerTurn();
        assertFalse(gameController.isPlayerOneTurn(), "After toggling, it should be Player Two's turn");

        gameController.togglePlayerTurn();
        assertTrue(gameController.isPlayerOneTurn(), "After toggling again, it should be Player One's turn");
    }

    @Test //tests and sets up players choices correctly!!! - finally working 10/10/24
    public void testSetandGetPlayerChoices() {
        gameController.setPlayerOneChoice('O');
        assertEquals('O', gameController.getPlayerOneChoice(), "Player One's choice should be 'O'");

        gameController.setPlayerTwoChoice('S');
        assertEquals('S', gameController.getPlayerTwoChoice(), "Player Two's choice should be 'S'");
    }

    @Test //make sure works if valid choice
    public void testMakeValidMove() {
        boolean moveMade = gameController.makeMove(0, 0);  // Player One makes a move at (0, 0)
        assertTrue(moveMade, "The move should be valid");
        assertEquals('S', gameController.getGameBoard().getValueAt(0, 0), 
                "The board should have 'S' at (0, 0)");
    }

    @Test //make sure rejects invalid choice
    public void testMakeInvalidMove() {
        gameController.makeMove(0, 0);  // Player One makes a move at (0, 0)
        boolean moveMade = gameController.makeMove(0, 0);  // Try to make the same move again
        assertFalse(moveMade, "The move should be invalid since the cell is already occupied");
    }

    @Test //check choice is right before turn
    public void testCurrentPlayerChoice() {
        assertEquals('S', gameController.getCurrentPlayerChoice(), 
                "Initially, Player One's choice should be 'S'");

        gameController.togglePlayerTurn();  // Switch to Player Two
        assertEquals('S', gameController.getCurrentPlayerChoice(), 
                "Player Two's choice should be 'S' by default");

        gameController.setPlayerTwoChoice('O');
        assertEquals('O', gameController.getCurrentPlayerChoice(), 
                "After setting Player Two's choice, it should be 'O'");
    }
}

