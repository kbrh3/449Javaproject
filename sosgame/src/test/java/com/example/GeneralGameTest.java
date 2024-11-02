package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneralGameTest {
    private GeneralGame game;
    private static final int TEST_SIZE = 3;  // Smaller board for testing

    @BeforeEach
    void setUp() {
        game = new GeneralGame(TEST_SIZE);
    }

    @Test
    void testInitialState() {
        assertTrue(game.isPlayerOneTurn(), "Game should start with player one's turn");
        assertEquals('S', game.getCurrentPlayerChoice(), "Initial choice should be 'S'");
        assertEquals(0, game.getPlayer1Points(), "Player 1 should start with 0 points");
        assertEquals(0, game.getPlayer2Points(), "Player 2 should start with 0 points");
    }

    @Test
    void testValidMoveWithoutSOS() {
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "Valid move should return true");
        assertFalse(game.isPlayerOneTurn(), "Turn should switch after move without SOS");
    }
//error - 10/27/24
    @Test
    void testBluePlayerSOSPoints() {
        // Blue player makes SOS
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
        assertTrue(game.makeMove(0, 1, 'O', 'B'), "O should be valid");
        assertTrue(game.makeMove(0, 2, 'S', 'B'), "Last S should be valid");
        
        assertEquals(1, game.getPlayer1Points(), "Blue player should get 1 point");
        assertEquals(0, game.getPlayer2Points(), "Red player should have 0 points");
        assertTrue(game.isPlayerOneTurn(), "Blue player should get another turn");
    }
//error - 10/27/24
    @Test
    void testRedPlayerSOSPoints() {
        // First move by Blue to change turn
        game.makeMove(2, 2, 'O', 'B');
        
        // Red player makes SOS
        assertTrue(game.makeMove(0, 0, 'S', 'R'), "First S should be valid");
        assertTrue(game.makeMove(0, 1, 'O', 'R'), "O should be valid");
        assertTrue(game.makeMove(0, 2, 'S', 'R'), "Last S should be valid");
        
        assertEquals(0, game.getPlayer1Points(), "Blue player should have 0 points");
        assertEquals(1, game.getPlayer2Points(), "Red player should get 1 point");
        assertFalse(game.isPlayerOneTurn(), "Red player should get another turn");
    }

    @Test
    void testNoPointsForMixedColorSOS() {
        //blue should place s first
        game.makeMove(0, 0, 'S', 'B');
        //red makes the o
        game.makeMove(0, 1, 'O', 'R');
        //blue makes another s
        game.makeMove(0, 2, 'S', 'B');
        
        assertEquals(0, game.getPlayer1Points(), "No points for mixed color SOS");
        assertEquals(0, game.getPlayer2Points(), "No points for mixed color SOS");
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
//error - 10/27/24
    @Test
    void testWinnerDetermination() {
        // Blue makes an SOS
        game.makeMove(0, 0, 'S', 'B');
        game.makeMove(0, 1, 'O', 'B');
        game.makeMove(0, 2, 'S', 'B');
        
        // Fill rest of board without SOS
        game.makeMove(1, 0, 'O', 'R');
        game.makeMove(1, 1, 'O', 'B');
        game.makeMove(1, 2, 'O', 'R');
        game.makeMove(2, 0, 'O', 'B');
        game.makeMove(2, 1, 'O', 'R');
        game.makeMove(2, 2, 'O', 'B');
        
        String expectedWinner = "Player 1 wins! (Blue: 1, Red: 0)";
        assertEquals(expectedWinner, game.getWinner(), "Blue player should win with 1 SOS");
    }
//error - 10/27/24
    @Test
    void testDrawGame() {
        // Blue makes an SOS
        game.makeMove(0, 0, 'S', 'B');
        game.makeMove(0, 1, 'O', 'B');
        game.makeMove(0, 2, 'S', 'B');
        
        // Red makes an SOS
        game.makeMove(2, 0, 'S', 'R');
        game.makeMove(2, 1, 'O', 'R');
        game.makeMove(2, 2, 'S', 'R');
        
        // Fill remaining spaces
        game.makeMove(1, 0, 'O', 'B');
        game.makeMove(1, 1, 'O', 'R');
        game.makeMove(1, 2, 'O', 'B');
        
        assertEquals("It's a draw! (Both: 1)", game.getWinner(), 
            "Game should be a draw with equal points");
    }
//error - 10/27/24
    @Test
    void testMultipleSOSInOneTurn() {
        // Player makes two SOSs in one turn (if possible in your board layout)
        assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
        assertTrue(game.makeMove(0, 1, 'O', 'B'), "O should be valid");
        assertTrue(game.makeMove(0, 2, 'S', 'B'), "Last S should be valid");
        
        // Player gets another turn and makes another SOS
        assertTrue(game.makeMove(1, 0, 'S', 'B'), "Second SOS first S should be valid");
        assertTrue(game.makeMove(1, 1, 'O', 'B'), "Second SOS O should be valid");
        assertTrue(game.makeMove(1, 2, 'S', 'B'), "Second SOS last S should be valid");
        
        assertEquals(2, game.getPlayer1Points(), "Player should have 2 points for 2 SOSs");
    }
    @Test
   public void testSOSFormationHorizontal() {
    // Create horizontal SOS and verify points
    assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
    assertTrue(game.makeMove(0, 1, 'O', 'B'), "O should be valid");
    assertTrue(game.makeMove(0, 2, 'S', 'B'), "Final S should be valid");
    
    assertEquals(1, game.getPlayer1Points(), "Blue player should get 1 point");
    assertTrue(game.isPlayerOneTurn(), "Blue player should get another turn after SOS");
}



@Test
    public void testSOSFormationVertical() {
    // Create vertical SOS and verify points
    assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
    assertTrue(game.makeMove(1, 0, 'O', 'B'), "O should be valid");
    assertTrue(game.makeMove(2, 0, 'S', 'B'), "Final S should be valid");
    
    assertEquals(1, game.getPlayer1Points(), "Blue player should get 1 point");
    assertTrue(game.isPlayerOneTurn(), "Blue player should get another turn after SOS");
}

    @Test
    public void testSOSFormationDiagonal() {
    // Create diagonal SOS and verify points
    assertTrue(game.makeMove(0, 0, 'S', 'B'), "First S should be valid");
    assertTrue(game.makeMove(1, 1, 'O', 'B'), "O should be valid");
    assertTrue(game.makeMove(2, 2, 'S', 'B'), "Final S should be valid");
    
    assertEquals(1, game.getPlayer1Points(), "Blue player should get 1 point");
    assertTrue(game.isPlayerOneTurn(), "Blue player should get another turn after SOS");
}



}