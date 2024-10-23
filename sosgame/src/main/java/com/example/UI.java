package com.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;


import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
//import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class UI {

    private JFrame frame;
    private GameBoard gameBoard;  //Reference GameBoard class
    private JRadioButton sButton, oButton;  //Radio buttons for blue player
    private JRadioButton sButton2, oButton2;  //Radio buttons for red player
    JLabel boardSizeLabel;  //Display board size
    private GameController gameController;  //Game controller instance
    private JPanel boardPanel;  //Panel holding  game board
    private JRadioButton simpleGameButton, generalGameButton;  //Game mode buttons
    private JPanel gameModePanel;  //Panel for game mode selection

    public UI() {
        gameController = new GameController(new SimpleGame(8));  //Init the game controller w/ size 8x8
        gameBoard = new GameBoard(8);  //Init the game board w/ size 8x8
        initUI();  //Init user interface
    }

    private void initUI() {
    frame = new JFrame("SOS Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 400);  //Set frame size - bigger 10/17/24
    frame.setLayout(new BorderLayout(10, 10));  //Add some spacing between components

    //keep them like this, the panels finally in the correct order
    //I mean it. dont mess with them 
    addTopPanel();  //Add the top panel - game mode buttons and board size label
    addPlayerPanel();  //Add Blue player panel
    addPlayerPanel2();  //Add Red player panel
    initializeBoard();  //Add the initial game board

    frame.setLocationRelativeTo(null);  //Center the frame
    frame.setVisible(true);  //Show the frame
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());  //Parent container panel
    
        //Game mode panel (Simple/General Game), keep on the left
        gameModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        simpleGameButton = new JRadioButton("Simple Game", true);  //Default selection
        generalGameButton = new JRadioButton("General Game");
    
        ButtonGroup gameModeGroup = new ButtonGroup();  //Group radio buttons
        gameModeGroup.add(simpleGameButton);
        gameModeGroup.add(generalGameButton);
    
        //action listeners for the radio buttons
        simpleGameButton.addActionListener(e -> updateGameMode(8, "Simple Game"));
        generalGameButton.addActionListener(e -> updateGameMode(10, "General Game"));
    
        gameModePanel.add(simpleGameButton);
        gameModePanel.add(generalGameButton);
    
        topPanel.add(gameModePanel, BorderLayout.WEST);  //Add the game mode panel - left
    
        //Add the board size label - right
        boardSizeLabel = new JLabel("Board Size: " + gameBoard.getSize() + "x" + gameBoard.getSize());
        boardSizeLabel.setHorizontalAlignment(JLabel.RIGHT);  //Align to the right
        boardSizeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  //Add padding
        topPanel.add(boardSizeLabel, BorderLayout.EAST);  //Add the label to the right
    
        //Add the top panel to frame
        frame.add(topPanel, BorderLayout.NORTH);
    }

    //THIS will need to change to different game logic- maybe extends gameboard??
    void updateGameMode(int size, String mode) { //made package-private for testing
        //Update the game board & controller w/ new size
        
        //below to board panels from piling up 10/15/24
        frame.remove(boardPanel);  //Remove the old board
        //if function, for selection of general vs simple??
        gameBoard = new GameBoard(size);
        if (mode.equals("Simple Game")) {
            gameController = new GameController(new SimpleGame(8));
        } else if (mode.equals("General Game")) {
            gameController = new GameController(new GeneralGame(10));
        }
    
    
        //Update the board size label w/ new mode & size
        boardSizeLabel.setText(mode + " - Board Size: " + size + "x" + size);
    
        //Reinit the board
        initializeBoard();
    
        //Refresh frame & apply the changes
        frame.revalidate();
        frame.repaint();
    }
    
    private void addPlayerPanel() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Blue player"));

        sButton = new JRadioButton("S", true);  //Default selection
        oButton = new JRadioButton("O");

        ButtonGroup group = new ButtonGroup();  //Group radio buttons to allow only one selection
        group.add(sButton);
        group.add(oButton);

        playerPanel.add(sButton);
        playerPanel.add(oButton);

        sButton.addActionListener(e -> gameController.setPlayerOneChoice('S'));
        oButton.addActionListener(e -> gameController.setPlayerOneChoice('O'));

        playerPanel.setPreferredSize(new Dimension(200, frame.getHeight()));  //Set panel size
        frame.add(playerPanel, BorderLayout.WEST);  //Add player panel to the frame
    }

    private void addPlayerPanel2() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Red player"));

        sButton2 = new JRadioButton("S", true);  //Default selection
        oButton2 = new JRadioButton("O");

        ButtonGroup group = new ButtonGroup();  //Group radio buttons
        group.add(sButton2);
        group.add(oButton2);

        playerPanel.add(sButton2);
        playerPanel.add(oButton2);

        sButton2.addActionListener(e -> gameController.setPlayerTwoChoice('S'));
        oButton2.addActionListener(e -> gameController.setPlayerTwoChoice('O'));

        playerPanel.setPreferredSize(new Dimension(200, frame.getHeight()));  //Set panel size
        frame.add(playerPanel, BorderLayout.EAST);  //Add player panel to the frame
    }

    private void initializeBoard() {
        boardPanel = new JPanel(new GridLayout(gameBoard.getSize(), gameBoard.getSize()));  //create a grid layout
        boardPanel.setPreferredSize(new Dimension(300, 300));  //Set the board panel size
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));  //add border
    
        for (int i = 0; i < gameBoard.getSize(); i++) {
            for (int j = 0; j < gameBoard.getSize(); j++) {
                JLabel cell = new JLabel("", JLabel.CENTER);  //Use JLabel for the cell
                cell.setOpaque(true);  //make label opaque
                cell.setPreferredSize(new Dimension(50, 50));  //Set cell size
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));  //Add border around each cell
    
                //MouseListener to handle clicks
                int row = i;  //Store row & column for use inside the listener
                int col = j;
                cell.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        handleMove(row, col, cell);  //Handle move when clicked
                    }
                });
    
                boardPanel.add(cell);  //Add cell to the board panel
            }
        }
    
        frame.add(boardPanel, BorderLayout.CENTER);  //Add the board panel to frame
    }
   
    private void handleMove(int row, int col, JLabel cell) {
        boolean isPlayerOneTurn = gameController.isPlayerOneTurn();  //Check current turn
        char currentPlayer = gameController.getCurrentPlayerChoice();  //Get s or o
    
        if (gameController.makeMove(row, col, currentPlayer)) {  //If the move valid
            cell.setText(String.valueOf(currentPlayer));  //Display the player choice
    
            //Set the color based on player
            if (isPlayerOneTurn) {
                cell.setForeground(Color.BLUE);  //Blue player
            } else {
                cell.setForeground(Color.RED);  //Red player
            }
        }
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