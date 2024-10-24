package com.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class UI {

    private JFrame frame;
    private GameBoard gameBoard;  
    private GameMode currentGameMode;  
    private JRadioButton sButton, oButton;  //radio buttons for blue player
    private JRadioButton sButton2, oButton2;  //radio buttons for red player
    JLabel boardSizeLabel;  //display board size
    private GameController gameController;  
    private JPanel boardPanel;  //panel holding game board
    private JRadioButton simpleGameButton, generalGameButton;  //game mode buttons
    private JPanel gameModePanel;  //panel for game mode selection

    public UI() {
        gameController = new GameController(new SimpleGame(8));  //Init the game controller w/ size 8x8
        gameBoard = new GameBoard(8);  //Init the game board w/ size 8x8
        currentGameMode = gameController.getGameMode(); //defaulting to simple game :)
        initUI();  //Init user interface
    }

    private void initUI() {
    frame = new JFrame("SOS Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 400);  //Set frame size - bigger 10/17/24
    frame.setLayout(new BorderLayout(10, 10));  //add some spacing between components

    //keep them like this, the panels finally in the correct order
    //I mean it. dont mess with them 
    addTopPanel();  //Add the top panel - game mode buttons and board size label
    addPlayerPanel();  //Add Blue player panel
    addPlayerPanel2();  //Add Red player panel
    initializeBoard();  //Add the initial game board

    frame.setLocationRelativeTo(null);  //center the frame
    frame.setVisible(true);  //show the frame
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());  //parent container panel
    
        //game mode panel (Simple/General Game), keep on the left
        gameModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        simpleGameButton = new JRadioButton("Simple Game", true);  //default selection to simple
        generalGameButton = new JRadioButton("General Game");
    
        ButtonGroup gameModeGroup = new ButtonGroup();  //group radio buttons
        gameModeGroup.add(simpleGameButton);
        gameModeGroup.add(generalGameButton);
    
        //action listeners for the radio buttons
        simpleGameButton.addActionListener(e -> updateGameMode(8, "Simple Game"));
        generalGameButton.addActionListener(e -> updateGameMode(10, "General Game"));
    
        gameModePanel.add(simpleGameButton);
        gameModePanel.add(generalGameButton);
    
        topPanel.add(gameModePanel, BorderLayout.WEST);  //add the game mode panel - left
    
        //add the board size label - right
        boardSizeLabel = new JLabel("Board Size: " + gameBoard.getSize() + "x" + gameBoard.getSize());
        boardSizeLabel.setHorizontalAlignment(JLabel.RIGHT);  //Align to the right
        boardSizeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  //Add padding
        topPanel.add(boardSizeLabel, BorderLayout.EAST);  //Add the label to the right
    
        //add the top panel to frame
        frame.add(topPanel, BorderLayout.NORTH);
    }

    //THIS will need to change to different game logic- maybe extends gameboard??
    void updateGameMode(int size, String mode) { //made package-private for testing - int size used to be parameter
  
        frame.remove(boardPanel);  //remove old board
        //if function, for selection of general vs simple??
        
        if (mode.equals("Simple Game")) {
            gameController = new GameController(new SimpleGame(8));
            gameBoard = new GameBoard(size);
            currentGameMode = gameController.getGameMode();
        } else if (mode.equals("General Game")) {
            gameController = new GameController(new GeneralGame(10));
            gameBoard = new GameBoard(size);
            currentGameMode = gameController.getGameMode();
        }
    
        //update the board size label w/ new mode & size - maybe this should come from game mode??
        boardSizeLabel.setText(mode + " - Board Size: " + size + "x" + size);
    
        //reinit the board
        initializeBoard();
        frame.revalidate();
        frame.repaint();
    }
    
    private void addPlayerPanel() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Blue player"));

        sButton = new JRadioButton("S", true);  //default selection to s
        oButton = new JRadioButton("O");

        ButtonGroup group = new ButtonGroup();  //group radio buttons to allow only one selection
        group.add(sButton);
        group.add(oButton);

        playerPanel.add(sButton);
        playerPanel.add(oButton);

        sButton.addActionListener(e -> currentGameMode.setPlayer1Choice('S'));
        oButton.addActionListener(e -> currentGameMode.setPlayer1Choice('O'));

        playerPanel.setPreferredSize(new Dimension(200, frame.getHeight()));  //set panel size
        frame.add(playerPanel, BorderLayout.WEST);  //add player panel to the frame
    }

    private void addPlayerPanel2() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Red player"));

        sButton2 = new JRadioButton("S", true);  //default selection
        oButton2 = new JRadioButton("O");

        ButtonGroup group = new ButtonGroup();  //group radio buttons
        group.add(sButton2);
        group.add(oButton2);

        playerPanel.add(sButton2);
        playerPanel.add(oButton2);

        sButton2.addActionListener(e -> currentGameMode.setPlayer2Choice('S'));
        oButton2.addActionListener(e -> currentGameMode.setPlayer2Choice('O'));

        playerPanel.setPreferredSize(new Dimension(200, frame.getHeight()));  //set panel size
        frame.add(playerPanel, BorderLayout.EAST);  //add player panel to the frame
    }

    private void initializeBoard() {
        boardPanel = new JPanel(new GridLayout(gameBoard.getSize(), gameBoard.getSize()));  //create a grid layout
        boardPanel.setPreferredSize(new Dimension(300, 300));  //set the board panel size
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));  //add border
    
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                JLabel cell = new JLabel("", JLabel.CENTER);  //Use JLabel for the cell
                cell.setOpaque(true);  //make label opaque
                cell.setPreferredSize(new Dimension(50, 50));  //set cell size
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));  //sdd border around each cell
    
                //mouseListener to handle clicks
                int row = i;  //store row & column for use inside the listener
                int col = j;
                cell.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        handleMove(row, col, cell);  //handle move when clicked
                    }
                });
    
                boardPanel.add(cell);  //add cell to the board panel
            }
        }
    
        frame.add(boardPanel, BorderLayout.CENTER);  //add the board panel to frame
    }
   
    private void handleMove(int row, int col, JLabel cell) {
        boolean isPlayerOneTurn = gameController.isPlayerOneTurn();  //check current turn
        //char letter = gameController.getCurrentPlayerChoice();  //Get s or o - this was from before sprint 3
        char letter = isPlayerOneTurn ? getchoicePlayer1() : getchoicePlayer2();
        char player = isPlayerOneTurn ? '1' : '2';  //to help keep track of points

        if (gameController.makeMove(row, col, letter, player)) {  //if the move valid
            cell.setText(String.valueOf(letter));  //display the player choice
    
            //set color for letters
            cell.setForeground(isPlayerOneTurn ? Color.BLUE : Color.RED);


            //check for game over - determine how this works with new game mode classes
        if (currentGameMode.checkGameOver(row, col)) {
            String winner = currentGameMode.getWinner();
            JOptionPane.showMessageDialog(frame, winner);
        }
        }
    }
    
    private char getchoicePlayer1() {
        if (sButton.isSelected()) {
            return 'S';
        } else if (oButton.isSelected()) {
            return 'O';
        }
        return ' ';  //default in case neither is selected - this shouldn't happen. but just in case...
    }
    
    //helper function to get the selected letter for Player 2
    private char getchoicePlayer2() {
        if (sButton2.isSelected()) {
            return 'S';
        } else if (oButton2.isSelected()) {
            return 'O';
        }
        return ' ';  //same as above
    }

//getters for testing
public JLabel getBoardSizeLabel() {
    return boardSizeLabel;
}

public JRadioButton getSimpleGameButton() {
    return simpleGameButton;
}

public JRadioButton getGeneralGameButton() {
    return generalGameButton;
}

public GameBoard getGameBoard() {
    return gameBoard;
}


public GameController getGameController() {
    return gameController;
}

public JRadioButton getSButton() {
    return sButton;
}

public JRadioButton getOButton() {
    return oButton;
}



public JRadioButton getSButton2() {
    return sButton2;
}

public JRadioButton getOButton2() {
    return oButton2;
}


}