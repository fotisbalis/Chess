package view;

import javax.swing.*;
import java.awt.*;

import pawn.PawnColor;
import save.*;
import utils.*;

public class GUI extends JFrame {
    private static final String START_SCREEN = "start";
    private static final String OPTIONS_SCREEN = "options";
    private static final String SINGLE_PLAYER_MENU_SCREEN = "single_player_menu";
    private static final String DIFFICULTY_SCREEN = "difficulty";
    private static final String SINGLE_PLAYER_COLOR_SCREEN = "single_player_color";
    private static final String TWO_PLAYER_MENU_SCREEN = "two_player_menu";
    private static final String GAME_SCREEN = "game";

    private final CardLayout screenLayout = new CardLayout();
    private final JPanel screenPanel = new JPanel(screenLayout);
    private ChessPanel chessPanel;
    private boolean highlightMoves = true;
    private boolean autoQueenPromotion = false;
    private int pendingSinglePlayerDepth = 5;
    private boolean brightColors = false;
    
    private GameState singlePlayerSavedGameState;
    private GameState twoPlayerSavedGameState;

    
    public GUI() {
    	
    	setTitle("CHESS");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadSettingsIfPresent();

        screenPanel.add(new StartPanel(this), START_SCREEN);
        screenPanel.add(new OptionsPanel(this), OPTIONS_SCREEN);
        screenPanel.add(new GameModePanel(this, true), SINGLE_PLAYER_MENU_SCREEN);
        screenPanel.add(new DifficultyPanel(this), DIFFICULTY_SCREEN);
        screenPanel.add(new SinglePlayerColorPanel(this), SINGLE_PLAYER_COLOR_SCREEN);
        screenPanel.add(new GameModePanel(this, false), TWO_PLAYER_MENU_SCREEN);
        add(screenPanel, BorderLayout.CENTER);

        loadSavedGamesIfPresent();

        showStartScreen();
        
        setVisible(true);
    }

    public void showStartScreen() {
        screenLayout.show(screenPanel, START_SCREEN);
    }

    public void showOptionsScreen() {
        screenLayout.show(screenPanel, OPTIONS_SCREEN);
    }

    public void showSinglePlayerMenu() {
        screenLayout.show(screenPanel, SINGLE_PLAYER_MENU_SCREEN);
    }

    public void showTwoPlayerMenu() {
        screenLayout.show(screenPanel, TWO_PLAYER_MENU_SCREEN);
    }

    public void showDifficultyScreen() {
        screenLayout.show(screenPanel, DIFFICULTY_SCREEN);
    }

    public void showSinglePlayerColorScreen() {
        screenLayout.show(screenPanel, SINGLE_PLAYER_COLOR_SCREEN);
    }

    public void setPendingSinglePlayerDepth(int pendingSinglePlayerDepth) {
        this.pendingSinglePlayerDepth = pendingSinglePlayerDepth;
    }

    public int getPendingSinglePlayerDepth() {
        return pendingSinglePlayerDepth;
    }

    public boolean isHighlightMovesEnabled() {
        return highlightMoves;
    }

    public void setHighlightMoves(boolean highlightMoves) {
        this.highlightMoves = highlightMoves;
        saveSettings(); 
    }
    
    public boolean isAutoQueenPromotion() {
    	return autoQueenPromotion;
    }
    
    public void setAutoQueenPromotion(boolean autoQueenPromotion) {
    	this.autoQueenPromotion = autoQueenPromotion;
    	saveSettings();
    }
    
    public boolean isBrightColorsEnabled() {
        return brightColors;
    }
    
    public void setBrightColors(boolean brightColors) {
    	this.brightColors = brightColors;
    	saveSettings();
    }

    public void showGameScreen() {
    	showGameScreen(highlightMoves);
    }
    
    public void showGameScreen(boolean highlightMoves) {
        if(chessPanel != null) {
            screenPanel.remove(chessPanel);
        }

        chessPanel = new ChessPanel(this, highlightMoves);
        screenPanel.add(chessPanel, GAME_SCREEN);
        screenPanel.revalidate();
        screenPanel.repaint();
        screenLayout.show(screenPanel, GAME_SCREEN);
    }

    public void showSinglePlayerGameScreen() {
    	startSinglePlayerGame(5, PawnColor.WHITE);
    }

    public void startSinglePlayerGame(int aiDepth) {
        startSinglePlayerGame(aiDepth, PawnColor.WHITE);
    }

    public void startSinglePlayerGame(int aiDepth, PawnColor playerColor) {
        if(chessPanel != null) {
            screenPanel.remove(chessPanel);
        }

        chessPanel = new ChessPanel(this, highlightMoves, true, aiDepth, playerColor);
        screenPanel.add(chessPanel, GAME_SCREEN);
        screenPanel.revalidate();
        screenPanel.repaint();
        screenLayout.show(screenPanel, GAME_SCREEN);
    }
    
    public boolean hasSavedGame() {
        return singlePlayerSavedGameState != null || twoPlayerSavedGameState != null;
    }

    public boolean hasSavedGame(boolean singlePlayer) {
        return singlePlayer ? singlePlayerSavedGameState != null : twoPlayerSavedGameState != null;
    }
    
    public void setSavedGame(GameState newGameState) {
        setSavedGame(newGameState, false);
    }

    public void setSavedGame(GameState newGameState, boolean singlePlayer) {
        if(singlePlayer)
            singlePlayerSavedGameState = newGameState;
        else
            twoPlayerSavedGameState = newGameState;
    }
    
    public void continueGame() {
        continueTwoPlayerGame();
    }

    public void continueSinglePlayerGame() {
        continueSavedGame(true);
    }

    public void continueTwoPlayerGame() {
        continueSavedGame(false);
    }

    private void continueSavedGame(boolean singlePlayer) {
        GameState savedGameState = singlePlayer ? singlePlayerSavedGameState : twoPlayerSavedGameState;

        if(savedGameState == null) {
            loadSavedGameIfPresent(singlePlayer);
            savedGameState = singlePlayer ? singlePlayerSavedGameState : twoPlayerSavedGameState;
        }

        if(savedGameState == null) {
            JOptionPane.showMessageDialog(this, singlePlayer ? "No single player save found." : "No two player save found.");
            return;
        }

        if(chessPanel != null) {
            screenPanel.remove(chessPanel);
        }

        chessPanel = new ChessPanel(this, highlightMoves, savedGameState, singlePlayer);
        screenPanel.add(chessPanel, GAME_SCREEN);
        screenPanel.revalidate();
        screenPanel.repaint();
        screenLayout.show(screenPanel, GAME_SCREEN);
    }

    private void loadSavedGamesIfPresent() {
        loadSavedGameIfPresent(true);
        loadSavedGameIfPresent(false);
    }

    private void loadSavedGameIfPresent(boolean singlePlayer) {
        if(!SaveLoadUtils.saveExists(singlePlayer)) {
            return;
        }

        try {
            GameState savedGameState = SaveLoadUtils.loadGame(singlePlayer);
            setSavedGame(savedGameState, singlePlayer);
            highlightMoves = savedGameState.isHighlightMovesEnabled();
            autoQueenPromotion = savedGameState.isAutoQueenPromotionEnabled();
        }
        catch(Exception e) {
            setSavedGame(null, singlePlayer);
        }
    }

    private void loadSettingsIfPresent() {
    	if(!SaveLoadUtils.settingsExist()) {
    		return;
    	}

    	try {
    		UserSettings settings = SaveLoadUtils.loadSettings();
    		highlightMoves = settings.isHighlightMovesEnabled();
    		autoQueenPromotion = settings.isAutoQueenPromotionEnabled();
    	}
    	catch(Exception e) {
    		highlightMoves = true;
    		autoQueenPromotion = false;
    	}
    }

    private void saveSettings() {
    	try {
    		SaveLoadUtils.saveSettings(new UserSettings(highlightMoves, autoQueenPromotion));
    	}
    	catch(Exception e) {
    		// Keep the current in-memory settings even if persisting them fails
    	}
    }
}


