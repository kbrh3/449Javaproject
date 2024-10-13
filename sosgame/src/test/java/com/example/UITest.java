package com.example;

import static org.junit.jupiter.api.Assertions.*;  // Assertions
import static org.mockito.Mockito.*;  // Mockito for mocks
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class UITest {

    private UI ui;
    private JLabel boardSizeLabelMock;  //Mocked JLabel for board size display
    private JRadioButton simpleGameButton; //these are used idk why its being weird about it
    private JRadioButton generalGameButton;

    @BeforeEach
    public void setUp() {
        ui = new UI();
    
        //Access the UI elements with getters
        boardSizeLabelMock = mock(JLabel.class);
        ui.getBoardSizeLabel().setText(boardSizeLabelMock.getText());  // Mock label
    
        simpleGameButton = ui.getSimpleGameButton();
        generalGameButton = ui.getGeneralGameButton();
    }
    
    @Test
    public void testUpdateGameMode2GeneralGame() {
        //Simulate clicking the General Game button
        ui.getGeneralGameButton().doClick();
    
        //Verify that the game board size is changed to 10x10
        assertEquals(10, ui.getGameBoard().getSize(), "Board size should be 10x10");
    }
    
    @Test
    public void testPlayer1ChoiceUpdate() {
        //Simulate setting Player One's choice to 'O'
        ui.getSButton().setSelected(false);
        ui.getOButton().setSelected(true);
        ui.getOButton().doClick();  //Trigger the click to set 'O'
    
        //Verify that Player One's choice has been changed
        assertEquals('O', ui.getGameController().getPlayerOneChoice(),
                "Player One's choice should be 'O'");
    }
    
    @Test
    public void testPlayer2ChoiceUpdate() {
        //Simulate setting Player Two's choice to 'O'
        ui.getSButton2().setSelected(false);
        ui.getOButton2().setSelected(true);
        ui.getOButton2().doClick();  // rigger the click to set 'O'
    
        //Verify that Player Two's choice has been changed
        assertEquals('O', ui.getGameController().getPlayerTwoChoice(),
                "Player Two's choice should be 'O'");
    }
}    
