package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//errors here,check logic
public class SimpleGameTest {
    private SimpleGame game;
    private static final int BOARD_SIZE = 3;
    
    @BeforeEach
    public void setUp() {
        game = new SimpleGame(BOARD_SIZE);
    }
    @Test
    public void testInitialState() {
        assertTrue(game.isPlayerOneTurn(), "Game should start with player one's turn");
        assertEquals('S', game.getCurrentPlayerChoice());
        assertEquals('S', game.getPlayer1Choice());
        assertEquals('S', game.getPlayer2Choice());
    }
    
    @Test
    public void testSetPlayerChoices() {
        game.setPlayer1Choice('O');
        game.setPlayer2Choice('S');
        assertEquals('O', game.getPlayer1Choice());
        assertEquals('S', game.getPlayer2Choice());
    }
    
    @Test
    public void testValidMove() {
        assertTrue(game.makeMove(0, 0, 'S', 'B'));
        assertFalse(game.isPlayerOneTurn()); // Should toggle turn after valid move
    }
    
    @Test
    public void testInvalidMove() {
        // Make initial move
        assertTrue(game.makeMove(0, 0, 'S', 'B'));
        // Try to make move in same position
        assertFalse(game.makeMove(0, 0, 'S', 'R'));
    }
    //error - 10/27/24
    @Test
    public void testSOSWinHorizontal() {
        // Create SOS horizontally
        assertTrue(game.makeMove(0, 0, 'S', 'B'));
        assertTrue(game.makeMove(0, 1, 'O', 'R'));
        assertTrue(game.makeMove(0, 2, 'S', 'B'));
        
        assertTrue(game.checkGameOver(0, 2));
        assertEquals("Blue Player Wins!", game.getWinner());
    }
    //error - 10/27/24
    @Test
    public void testSOSWinVertical() {
        // Create SOS vertically
        assertTrue(game.makeMove(0, 0, 'S', 'B'));
        assertTrue(game.makeMove(1, 0, 'O', 'R'));
        assertTrue(game.makeMove(2, 0, 'S', 'B'));
        
        assertTrue(game.checkGameOver(2, 0));
        assertEquals("Blue Player Wins!", game.getWinner());
    }
    //error - 10/27/24
    @Test
    public void testSOSWinDiagonal() {
        // Create SOS diagonally
        assertTrue(game.makeMove(0, 0, 'S', 'B'));
        assertTrue(game.makeMove(1, 1, 'O', 'R'));
        assertTrue(game.makeMove(2, 2, 'S', 'B'));
        
        assertTrue(game.checkGameOver(2, 2));
        assertEquals("Blue Player Wins!", game.getWinner());
    }
    
    @Test
    public void testDraw() {
        // Fill board without forming SOS
        assertTrue(game.makeMove(0, 0, 'S', 'B'));
        assertTrue(game.makeMove(0, 1, 'S', 'R'));
        assertTrue(game.makeMove(0, 2, 'S', 'B'));
        assertTrue(game.makeMove(1, 0, 'S', 'R'));
        assertTrue(game.makeMove(1, 1, 'S', 'B'));
        assertTrue(game.makeMove(1, 2, 'S', 'R'));
        assertTrue(game.makeMove(2, 0, 'S', 'B'));
        assertTrue(game.makeMove(2, 1, 'S', 'R'));
        assertTrue(game.makeMove(2, 2, 'S', 'B'));
        
        assertTrue(game.checkGameOver(2, 2));
        assertEquals("It's a draw!", game.getWinner());
    }
    
    @Test
    public void testPlayerTurnToggle() {
        assertTrue(game.isPlayerOneTurn()); // Player 1 starts
        game.makeMove(0, 0, 'S', 'B');
        assertFalse(game.isPlayerOneTurn()); // Should be Player 2's turn
        game.makeMove(0, 1, 'O', 'R');
        assertTrue(game.isPlayerOneTurn()); // Back to Player 1's turn
    }
    
    @Test
    public void testCurrentPlayerChoice() {
        game.setPlayer1Choice('S');
        game.setPlayer2Choice('O');
        assertEquals('S', game.getCurrentPlayerChoice()); // Player 1's turn
        game.makeMove(0, 0, 'S', 'B');
        assertEquals('O', game.getCurrentPlayerChoice()); // Player 2's turn
    }
    
    @Test
    public void testGameBoardAccess() {
        assertNotNull(game.getGameBoard());
        assertEquals(BOARD_SIZE, game.getGameBoard().getSize());
    }
}