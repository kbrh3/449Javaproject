package com.example;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Component;
//maybe add a point counter for general game??
public class UI {

    private JFrame frame;
    private GameBoard gameBoard;  
    private GameMode currentGameMode;  
    private JRadioButton sButton, oButton;  //radio buttons for blue player
    private JRadioButton sButton2, oButton2;  //radio buttons for red player
    JLabel boardSizeLabel;  //display board size
    private GameController gameController;  
    private JLabel turnDisplay;
    private JPanel boardPanel;  //panel holding game board
    private JRadioButton simpleGameButton, generalGameButton;  //game mode buttons
    private JPanel gameModePanel;  //panel for game mode selection
    private JRadioButton blueHuman, blueComputer; //these are here for sprint 4 - ai players
    private JRadioButton redHuman, redComputer;
    private Player bluePlayer = new Player("Blue");
    private Player redPlayer = new Player("Red");

    public UI() {
        gameController = new GameController(new SimpleGame(8));  //Init the game controller w/ size 8x8
        gameBoard = new GameBoard(8);  //Init the game board w/ size 8x8 b/c simple game
        currentGameMode = gameController.getGameMode(); //defaulting to simple game :)
        bluePlayer = new Player("Blue"); //put them inside the constructor as well
        redPlayer = new Player("Red");
        initUI();  //Init user interface
    }

    private void initUI() {
    frame = new JFrame("SOS Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 400);  //Set frame size - bigger 10/17/24
    frame.setLayout(new BorderLayout(10, 10));  //add some spacing between components

    //keep them like this, the panels finally in the correct order
    //I mean it. dont mess with them
    addTopPanel();  //add the top panel - game mode buttons and board size label
    addPlayerPanel();  //add Blue player panel
    addPlayerPanel2();  //add Red player panel
    initializeBoard();  //add the initial game board

    frame.setLocationRelativeTo(null);  //center the frame
    frame.setVisible(true);  //show the frame-- IMPORTANT!!
    }

    //a display should help with knowing what is going on
    //impliment this soon
    private void updateTurnDisplay() {
    String currentPlayer = gameController.isPlayerOneTurn() ? "Blue" : "Red";
    turnDisplay.setText(currentPlayer + " Players Turn");
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());  //parent container panel
        
        //try centering w/ FlowLayout.CENTER
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        turnDisplay = new JLabel("Blue Players Turn");
        turnDisplay.setHorizontalAlignment(JLabel.CENTER);  // Center the text
        turnDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Match the padding of boardSizeLabel
        centerPanel.add(turnDisplay);
        topPanel.add(centerPanel, BorderLayout.CENTER);

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
    //as of 10/29/24 is working without needing to change, logic handled in game modes?
    void updateGameMode(int size, String mode) { //made package-private for testing - int size used to be parameter
  
        frame.remove(boardPanel);  //remove old board - will this help with game finishing??
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
        updateTurnDisplay();

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

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBorder(BorderFactory.createTitledBorder("Player Type"));
        
        blueHuman = new JRadioButton("Human", true);
        blueComputer = new JRadioButton("Computer");

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(blueHuman);
        typeGroup.add(blueComputer);
        
        typePanel.add(blueHuman);
        typePanel.add(blueComputer);

        //listeners for the computer vs person buttons

       
        blueHuman.addActionListener(e -> {
            bluePlayer.setIsComputer(false);  //human mode
            sButton.setEnabled(true);         
            oButton.setEnabled(true);
        });

        blueComputer.addActionListener(e -> {
            bluePlayer.setIsComputer(true);   //computer mode
            sButton.setEnabled(false);      
            oButton.setEnabled(false);
        });

        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
        

        ButtonGroup group = new ButtonGroup();  //group radio buttons to allow only one selection
        group.add(sButton);
        group.add(oButton);

        playerPanel.add(sButton);
        playerPanel.add(oButton);

        sButton.addActionListener(e -> currentGameMode.setPlayer1Choice('S'));
        oButton.addActionListener(e -> currentGameMode.setPlayer1Choice('O'));

        playerPanel.add(typePanel);
        playerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        playerPanel.add(choicePanel);

        playerPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        frame.add(playerPanel, BorderLayout.WEST);
    }

    private void addPlayerPanel2() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Red player"));
    
        sButton2 = new JRadioButton("S", true);  //default selection to s
        oButton2 = new JRadioButton("O");
    
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBorder(BorderFactory.createTitledBorder("Player Type"));
        
        redHuman = new JRadioButton("Human", true);
        redComputer = new JRadioButton("Computer");
    
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(redHuman);
        typeGroup.add(redComputer);
        
        typePanel.add(redHuman);
        typePanel.add(redComputer);
    
        //listeners for the computer vs person buttons
        redHuman.addActionListener(e -> {
            redPlayer.setIsComputer(false);
            sButton2.setEnabled(true);
            oButton2.setEnabled(true);
        });
    
        redComputer.addActionListener(e -> {
            redPlayer.setIsComputer(true);
            //Disable buttons
            sButton2.setEnabled(false);
            oButton2.setEnabled(false);
        });
    
        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.Y_AXIS));
    
        ButtonGroup group = new ButtonGroup();  //group radio buttons to allow only one selection
        group.add(sButton2);
        group.add(oButton2);
    
        playerPanel.add(sButton2);
        playerPanel.add(oButton2);
    
        sButton2.addActionListener(e -> currentGameMode.setPlayer2Choice('S'));
        oButton2.addActionListener(e -> currentGameMode.setPlayer2Choice('O'));
    
        playerPanel.add(typePanel);
        playerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        playerPanel.add(choicePanel);
    
        playerPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        frame.add(playerPanel, BorderLayout.EAST);
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

    //computer turn helper. this had gpt assistance, it was hard to figure out
    private void handleComputerTurn() {
    boolean isPlayerOneTurn = gameController.isPlayerOneTurn();
    Player currentPlayer = isPlayerOneTurn ? bluePlayer : redPlayer;
    
        if (currentPlayer.isComputer()) {
        //suggested delay to make computer moves visible to players
            javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
            int[] move = currentPlayer.getComputerMove(gameBoard);
                if (move != null) {
                    char symbol = currentPlayer.getComputerSymbol();
                    char player = isPlayerOneTurn ? 'B' : 'R';
                
                    if (gameController.makeMove(move[0], move[1], symbol, player)) {
                    //find + update the correct cell. component b/c we are using jlabels
                        Component[] components = boardPanel.getComponents();
                        JLabel cell = (JLabel) components[move[0] * gameBoard.getSize() + move[1]];
                        cell.setText(String.valueOf(symbol));
                        cell.setForeground(isPlayerOneTurn ? Color.BLUE : Color.RED);

                    if (currentGameMode.checkGameOver(move[0], move[1])) {
                        String winner = currentGameMode.getWinner();
                        JOptionPane.showMessageDialog(frame, winner);
                        updateGameMode(8, "Simple Game");
                        simpleGameButton.setSelected(true);
                    } else {
                        //if next player is also computer. shouldn't be but just in case
                        handleComputerTurn();
                        updateTurnDisplay(); 
                    }
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
   
    private void handleMove(int row, int col, JLabel cell) {
        boolean isPlayerOneTurn = gameController.isPlayerOneTurn();
        Player currentPlayer = isPlayerOneTurn ? bluePlayer : redPlayer;
        
        // Only process click if it's a human player's turn
        if (!currentPlayer.isComputer()) {
            char letter = isPlayerOneTurn ? getchoicePlayer1() : getchoicePlayer2();
            char player = isPlayerOneTurn ? 'B' : 'R';
    
            if (gameController.makeMove(row, col, letter, player)) {
                cell.setText(String.valueOf(letter));
                cell.setForeground(isPlayerOneTurn ? Color.BLUE : Color.RED);
    
                if (currentGameMode.checkGameOver(row, col)) {
                    String winner = currentGameMode.getWinner();
                    JOptionPane.showMessageDialog(frame, winner);
                    updateGameMode(8, "Simple Game");
                    simpleGameButton.setSelected(true);
                } else {
                    // After human move, check if next player is computer
                    handleComputerTurn();
                    updateTurnDisplay(); 
                }
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

//getters for testing - gpt generated b/c "boiler plate code"
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