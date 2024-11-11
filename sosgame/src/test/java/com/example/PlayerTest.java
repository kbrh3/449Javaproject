package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// The test class for Player
public class PlayerTest {

    private Player bluePlayer;
    private Player redPlayer;
    private GameBoard mockBoard;

    @BeforeEach
    public void setUp() {
        bluePlayer = new Player("Blue");
        redPlayer = new Player("Red");
        mockBoard = mock(GameBoard.class);
    }

    @Test
    public void testInitialProperties() {
        assertEquals('B', bluePlayer.getColorofPlayer());
        assertEquals('R', redPlayer.getColorofPlayer());
        assertFalse(bluePlayer.isComputer());
        assertFalse(redPlayer.isComputer());
    }

    @Test
    public void testSetAsComputerPlayer() {
        bluePlayer.setIsComputer(true);
        assertTrue(bluePlayer.isComputer());
        bluePlayer.setIsComputer(false);
        assertFalse(bluePlayer.isComputer());
    }

    @Test
    public void testGetComputerMove_FindWinningMove() {
        // Simulate a winning move on the board
        when(mockBoard.getSize()).thenReturn(3);
        when(mockBoard.isEmpty(0, 0)).thenReturn(true);
        when(mockBoard.isEmpty(0, 1)).thenReturn(true);
        when(mockBoard.isEmpty(0, 2)).thenReturn(true);
        
        when(mockBoard.getValueAt(0, 1)).thenReturn('O');
        when(mockBoard.getValueAt(0, 2)).thenReturn('S');
        
        // Check that placing 'S' at (0, 0) completes an SOS
        bluePlayer.setIsComputer(true);
        int[] move = bluePlayer.getComputerMove(mockBoard);
        assertArrayEquals(new int[]{0, 0}, move);
    }

    @Test
    public void testGetComputerMove_FindBlockingMove() {
        // Simulate an opponent's potential SOS
        when(mockBoard.getSize()).thenReturn(3);
        when(mockBoard.isEmpty(1, 0)).thenReturn(true);
        when(mockBoard.getValueAt(1, 1)).thenReturn('O');
        when(mockBoard.getValueAt(1, 2)).thenReturn('S');
        when(mockBoard.checkplayer(1, 1)).thenReturn('R');
        when(mockBoard.checkplayer(1, 2)).thenReturn('R');
        
        redPlayer.setIsComputer(true);
        int[] move = redPlayer.getComputerMove(mockBoard);
        assertArrayEquals(new int[]{1, 0}, move);  // Expecting to block at (1, 0)
    }

    @Test
    public void testMakeRandMove() {
        // Define a board with only two empty cells
        when(mockBoard.getSize()).thenReturn(3);
        when(mockBoard.isEmpty(0, 0)).thenReturn(false);
        when(mockBoard.isEmpty(0, 1)).thenReturn(true);
        when(mockBoard.isEmpty(0, 2)).thenReturn(false);
        
        bluePlayer.setIsComputer(true);
        int[] move = bluePlayer.makeRandMove(mockBoard);

        // Ensure that the returned move is an empty cell
        assertNotNull(move);
        assertTrue((move[0] == 0 && move[1] == 1) || (move[0] == 2 && move[1] == 2));
    }

    @Test
public void testGetComputerSymbol() {
    // Simulate a winning situation where placing 'O' at (0, 1) would create SOS
    when(mockBoard.isEmpty(0, 1)).thenReturn(true);
    when(mockBoard.getValueAt(0, 0)).thenReturn('S');
    when(mockBoard.getValueAt(0, 2)).thenReturn('S');
    when(mockBoard.checkplayer(0, 0)).thenReturn('B');
    when(mockBoard.checkplayer(0, 2)).thenReturn('B');
    
    bluePlayer.setIsComputer(true);

    // Verify that couldCompleteSOS returns true for 'O'
    boolean canCompleteWithO = bluePlayer.couldCompleteSOS(mockBoard, 0, 1, 'O', 'B');
    assertTrue(canCompleteWithO, "Expected couldCompleteSOS to detect SOS completion with 'O'");

    // Now test the getComputerSymbol method
    char symbol = bluePlayer.getComputerSymbol(mockBoard, 0, 1);
    assertEquals('O', symbol);  // 'O' should be chosen to complete SOS
}

    

}

