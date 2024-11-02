package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GameControllerTest {
    private SimpleGame simpleGame;
    private GeneralGame generalGame;
    private GameController simpleController;
    private GameController generalController;
    
    @BeforeEach
    void setUp() {
        simpleGame = new SimpleGame(3);
        generalGame = new GeneralGame(3);
        simpleController = new GameController(simpleGame);
        generalController = new GameController(generalGame);
        
        // Set initial choices for players
        simpleGame.setPlayer1Choice('S');
        simpleGame.setPlayer2Choice('S');
        generalGame.setPlayer1Choice('S');
        generalGame.setPlayer2Choice('S');
    }
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
        // Initially both players have 'S'
        assertEquals('S', simpleController.getCurrentPlayerChoice(), 
            "Player one should start with 'S'");
        
        // Change player 2's choice to 'O'
        simpleGame.setPlayer2Choice('O');
        
        // Make a move to switch turns
        simpleController.makeMove(0, 0, 'S', 'B');
        
        // Now should get player 2's choice
        assertEquals('O', simpleController.getCurrentPlayerChoice(), 
            "Should get player two's choice after turn switch");
    }
    
    @Test
    void testGetCurrentPlayerChoiceGeneralGame() {
        // Initially both players have 'S'
        assertEquals('S', generalController.getCurrentPlayerChoice(), 
            "Player one should start with 'S'");
        
        // Change player 2's choice to 'O'
        generalGame.setPlayer2Choice('O');
        
        // Make a move to switch turns
        generalController.makeMove(0, 0, 'S', 'B');
        
        // Now should get player 2's choice
        assertEquals('O', generalController.getCurrentPlayerChoice(), 
            "Should get player two's choice after turn switch");
    }

    @Test
    void testPlayerChoiceSwitching() {
        // Set different choices for players
        simpleGame.setPlayer1Choice('S');
        simpleGame.setPlayer2Choice('O');
        
        // Check initial choice
        assertEquals('S', simpleController.getCurrentPlayerChoice(), 
            "Should start with player 1's choice");
        
        // Make move and check if choice switches
        simpleController.makeMove(0, 0, 'S', 'B');
        assertEquals('O', simpleController.getCurrentPlayerChoice(), 
            "Should switch to player 2's choice");
        
        // Make another move and check if choice switches back
        simpleController.makeMove(1, 0, 'O', 'R');
        assertEquals('S', simpleController.getCurrentPlayerChoice(), 
            "Should switch back to player 1's choice");
    }
    @Test
void testValidMoveSimpleGame() {
    GameController controller = new GameController(simpleGame);
    
    // Set initial choices for both players
    simpleGame.setPlayer1Choice('S');
    simpleGame.setPlayer2Choice('O');  // Set player 2's choice to 'O'
    
    assertTrue(controller.makeMove(0, 0, 'S', 'B'), "Valid move should return true");
    assertFalse(controller.isPlayerOneTurn(), "Turn should switch after valid move");
    assertEquals('O', controller.getCurrentPlayerChoice(), "Should be player two's choice");
}
    //fixed - 10/28/24
    @Test
void testValidMoveGeneralGame() {
    GameController controller = new GameController(generalGame);
    
    // Set initial choices for both players
    generalGame.setPlayer1Choice('S');
    generalGame.setPlayer2Choice('O');  // Set player 2's choice to 'O'
    
    assertTrue(controller.makeMove(0, 0, 'S', 'B'), "Valid move should return true");
    assertFalse(controller.isPlayerOneTurn(), "Turn should switch after valid move");
    assertEquals('O', controller.getCurrentPlayerChoice(), "Should be player two's choice");
}
    //error - 10/27/24 (fixed)
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

    @Test
    void testExtraTurnAfterSOSGeneralGame() {
        GameController controller = new GameController(generalGame);
    
    //player 1 makes an sos horizontally
    controller.makeMove(0, 0, 'S', 'B');
    controller.makeMove(0, 1, 'O', 'B');
    controller.makeMove(0, 2, 'S', 'B'); // Completes an SOS
    
    assertTrue(controller.isPlayerOneTurn(), "Player 1 should get an extra turn after forming an SOS.");
}

}