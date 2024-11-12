package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import javax.swing.JLabel; dont need rn
//import javax.swing.JRadioButton; dont need rn
//all tests run 10/27/24
public class UITest {
    private UI ui;

    @BeforeEach
    public void setUp() {
        ui = new UI();
    }

    @Test
    public void testInitialState() {
        // Test initial game mode selection
        assertTrue(ui.getSimpleGameButton().isSelected(), "Simple Game should be initially selected");
        assertFalse(ui.getGeneralGameButton().isSelected(), "General Game should not be initially selected");
        
        // Test initial board size
        assertEquals(8, ui.getGameBoard().getSize(), "Initial board size should be 8x8");
        
        // Test initial player choices
        assertTrue(ui.getSButton().isSelected(), "Player 1 should start with S selected");
        assertTrue(ui.getSButton2().isSelected(), "Player 2 should start with S selected");
    }

    @Test
    public void testGameModeSelection() {
        // Test Simple Game selection
        ui.getSimpleGameButton().doClick();
        assertTrue(ui.getSimpleGameButton().isSelected(), "Simple Game should be selected");
        assertFalse(ui.getGeneralGameButton().isSelected(), "General Game should not be selected");
        assertEquals(8, ui.getGameBoard().getSize(), "Board size should be 8x8 for Simple Game");
        
        // Test General Game selection
        ui.getGeneralGameButton().doClick();
        assertFalse(ui.getSimpleGameButton().isSelected(), "Simple Game should not be selected");
        assertTrue(ui.getGeneralGameButton().isSelected(), "General Game should be selected");
        assertEquals(10, ui.getGameBoard().getSize(), "Board size should be 10x10 for General Game");
    }

    @Test
    public void testPlayer1Controls() {
        // Test S selection
        ui.getSButton().doClick();
        assertTrue(ui.getSButton().isSelected(), "S should be selected for Player 1");
        assertFalse(ui.getOButton().isSelected(), "O should not be selected for Player 1");
        
        // Test O selection
        ui.getOButton().doClick();
        assertFalse(ui.getSButton().isSelected(), "S should not be selected for Player 1");
        assertTrue(ui.getOButton().isSelected(), "O should be selected for Player 1");
    }

    @Test
    public void testPlayer2Controls() {
        // Test S selection
        ui.getSButton2().doClick();
        assertTrue(ui.getSButton2().isSelected(), "S should be selected for Player 2");
        assertFalse(ui.getOButton2().isSelected(), "O should not be selected for Player 2");
        
        // Test O selection
        ui.getOButton2().doClick();
        assertFalse(ui.getSButton2().isSelected(), "S should not be selected for Player 2");
        assertTrue(ui.getOButton2().isSelected(), "O should be selected for Player 2");
    }

    @Test
    public void testBoardSizeLabelUpdate() {
        // Test Simple Game label
        ui.getSimpleGameButton().doClick();
        String simpleBoardLabel = ui.getBoardSizeLabel().getText();
        assertTrue(simpleBoardLabel.contains("8x8"), "Board size label should show 8x8 for Simple Game");
        assertTrue(simpleBoardLabel.contains("Simple Game"), "Label should indicate Simple Game");
        
        // Test General Game label
        ui.getGeneralGameButton().doClick();
        String generalBoardLabel = ui.getBoardSizeLabel().getText();
        assertTrue(generalBoardLabel.contains("10x10"), "Board size label should show 10x10 for General Game");
        assertTrue(generalBoardLabel.contains("General Game"), "Label should indicate General Game");
    }

    @Test
    public void testGameControllerUpdate() {
        // Test Simple Game controller
        ui.getSimpleGameButton().doClick();
        assertTrue(ui.getGameController().getGameMode() instanceof SimpleGame, 
                "Game controller should use Simple Game mode");
        
        // Test General Game controller
        ui.getGeneralGameButton().doClick();
        assertTrue(ui.getGameController().getGameMode() instanceof GeneralGame, 
                "Game controller should use General Game mode");
    }

    @Test
    public void testUpdateGameMode() {
        // Test updating to Simple Game
        ui.updateGameMode(8, "Simple Game");
        assertEquals(8, ui.getGameBoard().getSize(), "Board size should update to 8 for Simple Game");
        assertTrue(ui.getGameController().getGameMode() instanceof SimpleGame, 
                "Should switch to Simple Game mode");
        
        // Test updating to General Game
        ui.updateGameMode(10, "General Game");
        assertEquals(10, ui.getGameBoard().getSize(), "Board size should update to 10 for General Game");
        assertTrue(ui.getGameController().getGameMode() instanceof GeneralGame, 
                "Should switch to General Game mode");
    }

    @Test
    public void testGameBoardInitialization() {
        // Test Simple Game board
        ui.getSimpleGameButton().doClick();
        assertNotNull(ui.getGameBoard(), "Game board should not be null");
        assertEquals(8, ui.getGameBoard().getSize(), "Simple Game board should be 8x8");
        
        // Test General Game board
        ui.getGeneralGameButton().doClick();
        assertNotNull(ui.getGameBoard(), "Game board should not be null");
        assertEquals(10, ui.getGameBoard().getSize(), "General Game board should be 10x10");
    }
    @Test
    public void testPlayerTypeSelection() {
    // Test Blue player type selection
    assertTrue(ui.getBlueHuman().isSelected(), "Blue Human should be selected by default");
    assertFalse(ui.getBlueComputer().isSelected(), "Blue Computer should not be selected by default");
    
    // Test changing Blue player to computer
    ui.getBlueComputer().doClick();
    assertTrue(ui.getBlueComputer().isSelected(), "Blue Computer should be selected");
    assertFalse(ui.getSButton().isEnabled(), "S button should be disabled for computer player");
    assertFalse(ui.getOButton().isEnabled(), "O button should be disabled for computer player");
    
    // Test changing back to human
    ui.getBlueHuman().doClick();
    assertTrue(ui.getSButton().isEnabled(), "S button should be enabled for human player");
    assertTrue(ui.getOButton().isEnabled(), "O button should be enabled for human player");
}


@Test
public void testComputerPlayerInitialization() {
    assertNotNull(ui.getBluePlayer(), "Blue player should be initialized");
    assertNotNull(ui.getRedPlayer(), "Red player should be initialized");
    assertFalse(ui.getBluePlayer().isComputer(), "Blue player should start as human");
    assertFalse(ui.getRedPlayer().isComputer(), "Red player should start as human");
}

@Test
public void testPlayerTypeButtonsExist() {
    assertNotNull(ui.getBlueHuman(), "Blue human button should exist");
    assertNotNull(ui.getBlueComputer(), "Blue computer button should exist");
    assertNotNull(ui.getRedHuman(), "Red human button should exist");
    assertNotNull(ui.getRedComputer(), "Red computer button should exist");
}

@Test
public void testRedPlayerTypeSelection() {
    // Test Red player type selection
    assertTrue(ui.getRedHuman().isSelected(), "Red Human should be selected by default");
    assertFalse(ui.getRedComputer().isSelected(), "Red Computer should not be selected by default");
    
    // Test changing Red player to computer
    ui.getRedComputer().doClick();
    assertTrue(ui.getRedComputer().isSelected(), "Red Computer should be selected");
    assertFalse(ui.getSButton2().isEnabled(), "S button should be disabled for computer player");
    assertFalse(ui.getOButton2().isEnabled(), "O button should be disabled for computer player");
    
    // Test changing back to human
    ui.getRedHuman().doClick();
    assertTrue(ui.getSButton2().isEnabled(), "S button should be enabled for human player");
    assertTrue(ui.getOButton2().isEnabled(), "O button should be enabled for human player");
}

}