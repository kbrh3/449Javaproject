package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
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
public void testNoValidSOS() {
    // Set up board with no possible SOS completion
    when(mockBoard.getSize()).thenReturn(3);
    when(mockBoard.isEmpty(1, 1)).thenReturn(true);
    
    // Set up random non-SOS positions
    // Mock positions that would be checked by isValidPosition
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            when(mockBoard.getValueAt(i, j)).thenReturn(' ');
            when(mockBoard.checkplayer(i, j)).thenReturn(' ');
            when(mockBoard.isEmpty(i, j)).thenReturn(true);
        }
    }
    
    bluePlayer.setIsComputer(true);
    
    boolean canCompleteWithO = bluePlayer.couldCompleteSOS(mockBoard, 1, 1, 'O', 'B');
    assertFalse(canCompleteWithO, "Should not detect SOS when none is possible");
    
    // Test that symbol selection defaults to preference for 'S'
    int sCount = 0;
    int trials = 1000;
    for (int i = 0; i < trials; i++) {
        if (bluePlayer.getComputerSymbol(mockBoard, 1, 1) == 'S') {
            sCount++;
        }
    }
    assertTrue(sCount > trials * 0.5, "Should prefer 'S' when no SOS is possible");
}

@Test
public void testFindStrategicMove() {
    // Test the strategic move finding when there's an 'O' adjacent
    when(mockBoard.getSize()).thenReturn(3);
    when(mockBoard.isEmpty(1, 1)).thenReturn(true);
    when(mockBoard.getValueAt(1, 0)).thenReturn('O');  // Adjacent O
    when(mockBoard.isEmpty(1, 1)).thenReturn(true);
    
    bluePlayer.setIsComputer(true);
    int[] move = bluePlayer.getComputerMove(mockBoard);
    assertArrayEquals(new int[]{1, 1}, move, "Should choose position next to existing 'O'");
}

@Test
public void testComputerMoveWhenNotComputer() {
    // Test that non-computer players return null for computer moves
    bluePlayer.setIsComputer(false);
    assertNull(bluePlayer.getComputerMove(mockBoard));
    assertNull(bluePlayer.makeRandMove(mockBoard));
}

@Test
public void testGetComputerMoveFullBoard() {
    // Test behavior when board is full
    when(mockBoard.getSize()).thenReturn(3);
    for(int i = 0; i < 3; i++) {
        for(int j = 0; j < 3; j++) {
            when(mockBoard.isEmpty(i, j)).thenReturn(false);
        }
    }
    
    bluePlayer.setIsComputer(true);
    assertNull(bluePlayer.getComputerMove(mockBoard));
}

@Test
public void testMultipleSOSOpportunities() {
    // Test when there are multiple SOS opportunities
    when(mockBoard.getSize()).thenReturn(3);
    when(mockBoard.isEmpty(0, 0)).thenReturn(true);
    when(mockBoard.isEmpty(2, 2)).thenReturn(true);
    
    // Set up two potential SOS completions
    when(mockBoard.getValueAt(0, 1)).thenReturn('O');
    when(mockBoard.getValueAt(0, 2)).thenReturn('S');
    when(mockBoard.getValueAt(1, 1)).thenReturn('O');
    when(mockBoard.getValueAt(2, 0)).thenReturn('S');
    
    bluePlayer.setIsComputer(true);
    int[] move = bluePlayer.getComputerMove(mockBoard);
    assertNotNull(move, "Should find at least one winning move");
}

@Test
public void testBlockingPriority() {
    // Test that computer prioritizes winning over blocking
    when(mockBoard.getSize()).thenReturn(3);
    
    // Set up both a winning move and a blocking move
    // Winning move setup at (0,0)
    when(mockBoard.isEmpty(0, 0)).thenReturn(true);
    when(mockBoard.getValueAt(0, 1)).thenReturn('O');
    when(mockBoard.getValueAt(0, 2)).thenReturn('S');
    when(mockBoard.checkplayer(0, 1)).thenReturn('B');
    when(mockBoard.checkplayer(0, 2)).thenReturn('B');
    
    // Blocking move setup at (2,2)
    when(mockBoard.isEmpty(2, 2)).thenReturn(true);
    when(mockBoard.getValueAt(2, 0)).thenReturn('S');
    when(mockBoard.getValueAt(2, 1)).thenReturn('O');
    when(mockBoard.checkplayer(2, 0)).thenReturn('R');
    when(mockBoard.checkplayer(2, 1)).thenReturn('R');
    
    bluePlayer.setIsComputer(true);
    int[] move = bluePlayer.getComputerMove(mockBoard);
    assertArrayEquals(new int[]{0, 0}, move, "Should choose winning move over blocking move");
}

@Test
public void testInvalidBoardPositions() {
    // Test handling of invalid board positions
    when(mockBoard.getSize()).thenReturn(3);
    
    bluePlayer.setIsComputer(true);
    assertFalse(bluePlayer.couldCompleteSOS(mockBoard, -1, 0, 'S', 'B'), 
        "Should handle negative row index");
    assertFalse(bluePlayer.couldCompleteSOS(mockBoard, 0, -1, 'S', 'B'), 
        "Should handle negative column index");
    assertFalse(bluePlayer.couldCompleteSOS(mockBoard, 3, 0, 'S', 'B'), 
        "Should handle row index too large");
    assertFalse(bluePlayer.couldCompleteSOS(mockBoard, 0, 3, 'S', 'B'), 
        "Should handle column index too large");
}

@Test
public void testSymbolSelectionStrategy() {
    when(mockBoard.getSize()).thenReturn(3);
    bluePlayer.setIsComputer(true);
    
    // Count the distribution of S vs O choices over many trials
    int sCount = 0;
    int oCount = 0;
    int trials = 1000;
    
    for (int i = 0; i < trials; i++) {
        char symbol = bluePlayer.getComputerSymbol(mockBoard, 1, 1);
        if (symbol == 'S') sCount++;
        else if (symbol == 'O') oCount++;
    }
    
    // Should prefer 'S' slightly (around 60% of the time)
    assertTrue(sCount > oCount, "Should prefer S over O");
    assertTrue(Math.abs((double)sCount/trials - 0.6) < 0.1, 
        "S selection rate should be close to 60%");
}

    

}

