package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneralGameTest {
    private GeneralGame game;
    private static final int BOARD_SIZE = 3;  // Using smaller size for testing
    
    @BeforeEach
    void setUp() {
        game = new GeneralGame(BOARD_SIZE);
    }
    
    @Test
    void testInitialState() {
        assertTrue(game.isPlayerOneTurn(), "Game should start with player one's turn");
        assertEquals('S', game.getCurrentPlayerChoice(), "Initial choice should be 'S'");
        assertEquals('S', game.getPlayer1Choice(), "Player 1's initial choice should be 'S'");
        assertEquals('S', game.getPlayer2Choice(), "Player 2's initial choice should be 'S'");
    }
    
    @Test
    void testSetPlayerChoices() {
        game.setPlayer1Choice('O');
        game.setPlayer2Choice('S');
        assertEquals('O', game.getPlayer1Choice(), "Player 1's choice should be updated to 'O'");
        assertEquals('S', game.getPlayer2Choice(), "Player 2's choice should be 'S'");
        assertTrue(game.isPlayerOneTurn(), "Should still be player 1's turn");
        assertEquals('O', game.getCurrentPlayerChoice(), "Current choice should be player 1's choice");
    }
    
    @Test
    void testValidMoveWithoutSOS() {
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "Valid move should return true");
        assertFalse(game.isPlayerOneTurn(), "Turn should switch after move without SOS");
    }
    
    @Test
    void testInvalidMove() {
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "First move should be valid");
        assertFalse(game.makeMove(0, 0, 'S', 'R'), "Move to occupied cell should return false");
    }
    
    @Test
    void testSOSFormationAndExtraTurn() {
        // Create SOS sequence
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
        assertFalse(game.isPlayerOneTurn(), "Turn should switch");
        
        assertTrue(game.makeMove(0, 1, 'O', 'R'), "O should be valid");
        assertTrue(game.isPlayerOneTurn(), "Turn should switch back");
        
        assertTrue(game.makeMove(0, 2, 'S', 'B'), "Final S should be valid");
        assertTrue(game.isPlayerOneTurn(), "Player should get extra turn after forming SOS");
    }
    
    @Test
    void testPointTracking() {
        // Player 1 makes SOS
        game.makeMove(0, 0, 'S', 'B');
        game.makeMove(0, 1, 'O', 'R');
        game.makeMove(0, 2, 'S', 'B');
        
        // Make some moves to fill board without more SOSs
        game.makeMove(1, 0, 'O', 'B');
        game.makeMove(1, 1, 'O', 'R');
        game.makeMove(1, 2, 'O', 'B');
        game.makeMove(2, 0, 'O', 'R');
        game.makeMove(2, 1, 'O', 'B');
        game.makeMove(2, 2, 'O', 'R');
        
        assertEquals("Player 1 wins!", game.getWinner(), "Player 1 should win with 1 SOS");
    }
    
    @Test
    void testDrawGame() {
        // Player 1 makes SOS
        game.makeMove(0, 0, 'S', 'B');
        game.makeMove(0, 1, 'O', 'R');
        game.makeMove(0, 2, 'S', 'B');
        
        // Player 2 makes SOS
        game.makeMove(2, 0, 'S', 'R');
        game.makeMove(2, 1, 'O', 'B');
        game.makeMove(2, 2, 'S', 'R');
        
        // Fill remaining spaces
        game.makeMove(1, 0, 'O', 'B');
        game.makeMove(1, 1, 'O', 'R');
        game.makeMove(1, 2, 'O', 'B');
        
        assertEquals("It's a draw!", game.getWinner(), "Game should be a draw with equal SOSs");
    }
    
    @Test
    void testGameOverCondition() {
        assertFalse(game.checkGameOver(0, 0), "New game should not be over");
        
        // Fill the board
        game.makeMove(0, 0, 'S', 'B');
        game.makeMove(0, 1, 'O', 'R');
        game.makeMove(0, 2, 'S', 'B');
        game.makeMove(1, 0, 'O', 'R');
        game.makeMove(1, 1, 'O', 'B');
        game.makeMove(1, 2, 'O', 'R');
        game.makeMove(2, 0, 'S', 'B');
        game.makeMove(2, 1, 'O', 'R');
        game.makeMove(2, 2, 'S', 'B');
        
        assertTrue(game.checkGameOver(0, 0), "Game should be over when board is full");
    }
    
    @Test
    void testGameBoardAccess() {
        assertNotNull(game.getGameBoard(), "Game board should not be null");
        assertEquals(BOARD_SIZE, game.getGameBoard().getSize(), "Board size should match constructor parameter");
    }
    
    @Test
    void testConsecutiveSOSTurns() {
        // Player 1 makes first SOS
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
        assertFalse(game.isPlayerOneTurn(), "Turn should switch");
        assertTrue(game.makeMove(0, 1, 'O', 'R'), "O should be valid");
        assertTrue(game.isPlayerOneTurn(), "Turn should switch");
        assertTrue(game.makeMove(0, 2, 'S', 'B'), "Last S should be valid");
        assertTrue(game.isPlayerOneTurn(), "Player should get another turn after SOS");
        
        // Player 1 makes second SOS on their extra turn
        assertTrue(game.makeMove(1, 0, 'S', 'B'), "Extra turn S should be valid");
        assertTrue(game.makeMove(1, 1, 'O', 'B'), "Extra turn O should be valid");
        assertTrue(game.makeMove(1, 2, 'S', 'B'), "Extra turn final S should be valid");
        assertTrue(game.isPlayerOneTurn(), "Player should get another turn after second SOS");
    }
}