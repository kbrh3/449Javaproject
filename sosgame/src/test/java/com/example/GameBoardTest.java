package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {
    private GameBoard gameBoard;
    private static final int TEST_SIZE = 3;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard(TEST_SIZE);
    }

    @Test
    void testBoardInitialization() {
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                assertEquals(' ', gameBoard.getValueAt(i, j),
                    "All cells should be initialized empty");
            }
        }
    }

    @Test
    void testGetSize() {
        assertEquals(TEST_SIZE, gameBoard.getSize(),
            "Board size should match constructor parameter");
    }

    @Test
    void testValidMoveWithPlayerTracking() {
        assertTrue(gameBoard.setMove(0, 0, 'S', 'B'),
            "Valid move should return true");
        assertEquals('S', gameBoard.getValueAt(0, 0),
            "Cell should contain the played letter");
    }

    @Test
    void testInvalidMoveOnOccupiedCell() {
        gameBoard.setMove(0, 0, 'S', 'B');
        assertFalse(gameBoard.setMove(0, 0, 'O', 'R'),
            "Move on occupied cell should return false");
        assertEquals('S', gameBoard.getValueAt(0, 0),
            "Cell should retain original value");
    }

    @Test
    void testBoardFullCheck() {
        // Fill the board
        for (int i = 0; i < TEST_SIZE; i++) {
            for (int j = 0; j < TEST_SIZE; j++) {
                gameBoard.setMove(i, j, 'S', 'B');
            }
        }
        assertTrue(gameBoard.isFull(), "Board should be full");
    }

    @Test
    void testBoardNotFull() {
        gameBoard.setMove(0, 0, 'S', 'B');
        assertFalse(gameBoard.isFull(), "Board should not be full with empty cells");
    }

    @Test
    void testHorizontalSOS() {
        gameBoard.setMove(0, 0, 'S', 'B');
        gameBoard.setMove(0, 1, 'O', 'B');
        gameBoard.setMove(0, 2, 'S', 'B');
        assertTrue(gameBoard.checkSOS(0, 0),
            "Horizontal SOS should be detected");
    }

    @Test
    void testVerticalSOS() {
        gameBoard.setMove(0, 0, 'S', 'B');
        gameBoard.setMove(1, 0, 'O', 'B');
        gameBoard.setMove(2, 0, 'S', 'B');
        assertTrue(gameBoard.checkSOS(0, 0),
            "Vertical SOS should be detected");
    }

    @Test
    void testDiagonalSOS() {
        gameBoard.setMove(0, 0, 'S', 'B');
        gameBoard.setMove(1, 1, 'O', 'B');
        gameBoard.setMove(2, 2, 'S', 'B');
        assertTrue(gameBoard.checkSOS(0, 0),
            "Diagonal SOS should be detected");
    }

    @Test
    void testNoSOSWithDifferentPlayers() {
        gameBoard.setMove(0, 0, 'S', 'B');
        gameBoard.setMove(0, 1, 'O', 'R');
        gameBoard.setMove(0, 2, 'S', 'B');
        assertFalse(gameBoard.checkSOS(0, 0),
            "SOS with different players should not be detected");
    }

    @Test
    void testNoSOSWithoutProperSequence() {
        gameBoard.setMove(0, 0, 'S', 'B');
        gameBoard.setMove(0, 1, 'S', 'B');
        gameBoard.setMove(0, 2, 'S', 'B');
        assertFalse(gameBoard.checkSOS(0, 0),
            "Invalid SOS sequence should not be detected");
    }

    @Test
    void testOutOfBoundsMoves() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> gameBoard.setMove(TEST_SIZE, TEST_SIZE, 'S', 'B'),
            "Move outside board should throw exception");
    }

    @Test
    void testGetMoveConsistency() {
        gameBoard.setMove(1, 1, 'O', 'B');
        assertEquals('O', gameBoard.getMove(1, 1),
            "getMove should return the same value as getValueAt");
        assertEquals(gameBoard.getValueAt(1, 1), gameBoard.getMove(1, 1),
            "getMove and getValueAt should be consistent");
    }

    @Test
    void testSetMoveOverload() {
        assertTrue(gameBoard.setMove(0, 0, 'S'),
            "Simple setMove should work");
        assertEquals('S', gameBoard.getValueAt(0, 0),
            "Move should be recorded correctly");
        assertFalse(gameBoard.setMove(0, 0, 'O'),
            "Cannot override existing move");
    }

    @Test
    void testSOSCheckWithInvalidStart() {
        gameBoard.setMove(0, 0, 'O', 'B');
        assertFalse(gameBoard.checkSOS(0, 0),
            "SOS check should fail when starting position is not 'S'");
    }

    @Test
    void testSOSCheckOnEmptyCell() {
        assertFalse(gameBoard.checkSOS(0, 0),
            "SOS check on empty cell should return false");
    }
}