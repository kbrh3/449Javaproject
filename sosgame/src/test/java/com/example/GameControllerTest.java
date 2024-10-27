package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    SimpleGame simpleGame = new SimpleGame(3);  // or whatever board size you want to test with
    GeneralGame generalGame = new GeneralGame(3);
    
    @Test
    void testInitializationWithSimpleGame() {
        GameController controller = new GameController(simpleGame);
        
        assertNotNull(controller.getGameBoard(), "GameBoard should not be null");
        assertNotNull(controller.getGameMode(), "GameMode should not be null");
        assertTrue(controller.getGameMode() instanceof SimpleGame, "GameMode should be SimpleGame");
        assertTrue(controller.isPlayerOneTurn(), "Should start with player one's turn");
    }
    
    @Test
    void testInitializationWithGeneralGame() {
        GameController controller = new GameController(generalGame);
        
        assertNotNull(controller.getGameBoard(), "GameBoard should not be null");
        assertNotNull(controller.getGameMode(), "GameMode should not be null");
        assertTrue(controller.getGameMode() instanceof GeneralGame, "GameMode should be GeneralGame");
        assertTrue(controller.isPlayerOneTurn(), "Should start with player one's turn");
    }
    
    @Test
    void testGetCurrentPlayerChoiceSimpleGame() {
        GameController controller = new GameController(simpleGame);
        
        assertEquals('S', controller.getCurrentPlayerChoice(), "Player one should start with 'S'");
        controller.makeMove(0, 0, 'S', 'B');  // Make a move to switch turns
        assertEquals('O', controller.getCurrentPlayerChoice(), "Player two should have 'O'");
    }
    
    @Test
    void testGetCurrentPlayerChoiceGeneralGame() {
        GameController controller = new GameController(generalGame);
        
        assertEquals('S', controller.getCurrentPlayerChoice(), "Player one should start with 'S'");
        controller.makeMove(0, 0, 'S', 'B');  // Make a move to switch turns
        assertEquals('O', controller.getCurrentPlayerChoice(), "Player two should have 'O'");
    }
    
    @Test
    void testValidMoveSimpleGame() {
        GameController controller = new GameController(simpleGame);
        
        assertTrue(controller.makeMove(0, 0, 'S', 'B'), "Valid move should return true");
        assertFalse(controller.isPlayerOneTurn(), "Turn should switch after valid move");
        assertEquals('O', controller.getCurrentPlayerChoice(), "Should be player two's choice");
    }
    
    @Test
    void testValidMoveGeneralGame() {
        GameController controller = new GameController(generalGame);
        
        assertTrue(controller.makeMove(0, 0, 'S', 'B'), "Valid move should return true");
        assertFalse(controller.isPlayerOneTurn(), "Turn should switch after valid move");
        assertEquals('O', controller.getCurrentPlayerChoice(), "Should be player two's choice");
    }
    
    @Test
    void testInvalidMoveSimpleGame() {
        GameController controller = new GameController(simpleGame);
        
        assertTrue(controller.makeMove(0, 0, 'S', 'B'), "First move should be valid");
        assertFalse(controller.makeMove(0, 0, 'S', 'B'), "Same position should be invalid");
        assertFalse(controller.makeMove(-1, 0, 'S', 'B'), "Invalid row should return false");
        assertFalse(controller.makeMove(0, 3, 'S', 'B'), "Invalid column should return false");
    }
    
    @Test
    void testInvalidMoveGeneralGame() {
        GameController controller = new GameController(generalGame);
        
        assertTrue(controller.makeMove(0, 0, 'S', 'B'), "First move should be valid");
        assertFalse(controller.makeMove(0, 0, 'S', 'B'), "Same position should be invalid");
        assertFalse(controller.makeMove(-1, 0, 'S', 'B'), "Invalid row should return false");
        assertFalse(controller.makeMove(0, 3, 'S', 'B'), "Invalid column should return false");
    }
    
    @Test
    void testGameBoardConsistency() {
        GameController controller = new GameController(simpleGame);
        
        controller.makeMove(0, 0, 'S', 'B');
        assertEquals('S', controller.getGameBoard().getValueAt(0, 0), "GameBoard should reflect the move");
        assertEquals(controller.getGameBoard(), simpleGame.getGameBoard(), "GameBoard reference should be consistent");
    }
}