package ui;

import exceptions.QuitGameException;
import model.*;
import model.Event;
import persistence.JsonReader;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ArcadeApp extends JFrame implements ActionListener {
    public static final int BUTTON_WIDTH = 400;
    public static final int BUTTON_POS_X = (CrossyRoadGame.GAME_WIDTH - BUTTON_WIDTH) / 2;
    public static final int COMPONENT_HEIGHT = 100;
    public static final int TEXT_AREA_PADDING = 100;
    private Arcade arcade;
    private CrossyRoadEventHandler eventHandler;
    private CrossyRoadRun runner;
    private String newPlayerName = null;
    private String newGameMode = null;
    private PlayerProfile chosenPlayer;
    private boolean restartFlag = false;
    private final Font arcadeFont = new Font("Arial",Font.BOLD, 20);
    private KeyHandler keyHandler;
    //private JsonReader jsonReader;

    /*
     * MODIFIES: this
     * EFFECTS: instantiates a new PlayerProfile ArrayList
     *          instantiates a new Scanner object
     *          calls displayOptions
     */
    public ArcadeApp() {
        super("Crossy Road");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setBackground(Color.white);
        setSize(CrossyRoadGame.GAME_WIDTH, CrossyRoadGame.INIT_HEIGHT);
        centreOnScreen();
        initMenu();
        setVisible(true);
        addKeyListener(new KeyHandler());
        this.arcade = new Arcade();
        this.arcade.loadArcade();
    }

    /*
     * EFFECTS: displays menu options
     *          calls handleInput to handle the user's choice
     */
    private void initMenu() {
        getContentPane().removeAll();
        addCentredTextArea("Welcome to the Arcade ! ", COMPONENT_HEIGHT, arcadeFont,
                getFontMetrics(arcadeFont));
        addCentredTextArea("Select from the options below ↓", COMPONENT_HEIGHT * 2, arcadeFont,
                getFontMetrics(arcadeFont));
        addCentredButton(BUTTON_POS_X, COMPONENT_HEIGHT * 3, "Create new profile", "newProfile");
        addCentredButton(BUTTON_POS_X, COMPONENT_HEIGHT * 4, "Play a game", "playGame");
        addCentredButton(BUTTON_POS_X, COMPONENT_HEIGHT * 5, "Exit arcade", "exit");
        add(new JTextField(""));
        repaint();
    }

    private void playGame() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.white);
        addCentredTextArea("What would you like to play",
                COMPONENT_HEIGHT, arcadeFont, getFontMetrics(arcadeFont));

        for (int i = 0; i < GameName.values().length; i++) {
            addCentredButton(BUTTON_POS_X,
                    (3 + i) * COMPONENT_HEIGHT, GameName.values()[i].toString(),
                    "gameChoice");
        }
        add(new JTextField(""));
        repaint();
    }

    private void playCrossyRoad() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.white);
        addCentredTextArea("Who is playing",
                COMPONENT_HEIGHT, arcadeFont, getFontMetrics(arcadeFont));
        List<PlayerProfile> playerList = this.arcade.getPlayerProfileList("CROSSYROAD");
        for (int i = 0; i < playerList.size(); i++) {
            addCentredButton(BUTTON_POS_X, (2 + i) * COMPONENT_HEIGHT, playerList.get(i).getPlayerName(),
                    "crossyRoadSelection");
        }
        repaint();
    }

    /*
     * MODIFIES: this
     * EFFECTS: asks for a username and passes it into the instantiation of
     *          a new PlayerProfile object
     *          the new object is added to the PlayerProfile list
     */
    public void createNewProfile() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.white);
        addCentredTextField("What is your name ?",
                COMPONENT_HEIGHT, arcadeFont, getFontMetrics(arcadeFont), "newPlayerName");
        addCentredTextArea("What game will this player be playing ?",
                COMPONENT_HEIGHT * 2, arcadeFont, getFontMetrics(arcadeFont));
        for (int i = 0; i < GameName.values().length; i++) {
            addCentredButton((CrossyRoadGame.GAME_WIDTH - BUTTON_WIDTH) / 2,
                    (3 + i) * COMPONENT_HEIGHT, GameName.values()[i].toString(),
                    "gameModeSelect");
        }
        repaint();
    }

    private void addProfile() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.white);
        this.arcade.addPlayerProfile(new PlayerProfile(newPlayerName), newGameMode);
        addCentredTextArea("New profile added for " + newPlayerName + "under" + newGameMode,
                COMPONENT_HEIGHT, arcadeFont, getFontMetrics(arcadeFont));
        this.newPlayerName = null;
        this.newGameMode = null;
        repaint();
        initMenu();
    }

    private void crossyRoadPlayerSelection(String label) {
        if (this.arcade.getPlayerProfileList("CROSSYROAD").stream()
                .anyMatch(o -> o.getPlayerName().equals(label))) {
            Optional<PlayerProfile> chosenPlayer = this.arcade.getPlayerProfileList("CROSSYROAD").stream()
                    .filter(o -> o.getPlayerName().equals(label)).findFirst();
            chosenPlayer.ifPresent(playerProfile -> this.chosenPlayer = playerProfile);
            restartLevel();
        }
    }

    private void restartLevel() {
        getContentPane().removeAll();
        getContentPane().setBackground(Color.white);
        addCentredTextField("Would you like to restart at level 1 or continue where you left off ?",
                COMPONENT_HEIGHT, arcadeFont, getFontMetrics(arcadeFont), "newPlayerName");
        addCentredButton(BUTTON_POS_X, 2 * COMPONENT_HEIGHT, "Yes",
                "restartLevel");
        addCentredButton(BUTTON_POS_X, 3 * COMPONENT_HEIGHT, "No",
                "restartLevel");
        repaint();
    }

    /*
     * EFFECTS: instantiates a new CrossyRoadRun object
     */
    private void startCrossyRoad() {
        getContentPane().removeAll();
//        this.keyHandler = new KeyHandler();
//        addKeyListener(this.keyHandler);
        this.runner = new CrossyRoadRun(chosenPlayer, restartFlag);
        add(runner);
        pack();
        repaint();

        // instead, add arcadeTimer that will display arcade menu if gameStatus is quit
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            try {
                runner.getEventHandler().handleKeyPress(e.getKeyCode());
            } catch (QuitGameException ex) {
                initMenu();
            }
        }
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    public void addCentredButton(int positionX, int positionY, String description, String actionCommand) {
        JButton newButton = new JButton(description);
        newButton.setForeground(new Color(139, 190, 0));
        newButton.setBackground(Color.white);
        newButton.setBounds(positionX, positionY, BUTTON_WIDTH, COMPONENT_HEIGHT);
        newButton.setSize(BUTTON_WIDTH, COMPONENT_HEIGHT);
        newButton.setActionCommand(actionCommand);
        newButton.addActionListener(this);
        add(newButton);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string horizontally onto g at vertical position yPos
    private void addCentredTextArea(String string, int positionY, Font font, FontMetrics fm) {
        int width = fm.stringWidth(string);
        JTextArea newTextArea = new JTextArea(string);
        newTextArea.setBounds((CrossyRoadGame.GAME_WIDTH - width) / 2, positionY,
                width + TEXT_AREA_PADDING, COMPONENT_HEIGHT);
        newTextArea.setFont(font);
        newTextArea.setAlignmentY((CrossyRoadGame.GAME_WIDTH + TEXT_AREA_PADDING - width) / 2);
        add(newTextArea);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string horizontally onto g at vertical position yPos
    private void addCentredTextField(String string, int positionY, Font font, FontMetrics fm, String actionCommand) {
        int width = fm.stringWidth(string);
        JTextField newTextField = new JTextField(string);
        newTextField.setBounds((CrossyRoadGame.GAME_WIDTH - width - TEXT_AREA_PADDING) / 2, positionY,
                width + TEXT_AREA_PADDING, COMPONENT_HEIGHT);
        newTextField.setFont(font);
        newTextField.setHorizontalAlignment(JTextField.CENTER);
        newTextField.setActionCommand(actionCommand);
        newTextField.addActionListener(this);
        newTextField.setBackground(Color.white);
        add(newTextField);
    }

    private void printEventLog() {
        for (Event nextEvent : EventLog.getInstance()) {
            System.out.println(nextEvent);
        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        String text = null;
        String label = null;
        if (action.equals("newPlayerName")) {
            JTextField textField = (JTextField)ae.getSource();
            text = textField.getText();
        } else {
            JButton button = (JButton)ae.getSource();
            label = button.getText();
        }
        switch (action) {
            case "newProfile":
                System.out.println("creating profile");
                createNewProfile();
                break;
            case "playGame":
                playGame();
                break;
            case "exit":
                this.arcade.saveArcade();
                break;
            case "crossyRoadSelection":
                crossyRoadPlayerSelection(label);
                break;
            case "newPlayerName":
                System.out.println("set player name");
                this.newPlayerName = text;
                if (this.newGameMode != null) {
                    addProfile();
                }
                break;
            case "gameModeSelect":
                this.newGameMode = label;
                if (this.newPlayerName != null) {
                    addProfile();
                }
                break;
            case "gameChoice":
                if (label.equals("CROSSYROAD")) {
                    playCrossyRoad();
                }
                break;
            case "restartLevel":
                this.restartFlag = label.equals("Yes");
                startCrossyRoad();
                break;
        }
    }
}



