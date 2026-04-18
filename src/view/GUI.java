package view;

import javax.swing.*;
import java.awt.*;

import save.*;
import utils.*;

public class GUI extends JFrame {
    private static final String START_SCREEN = "start";
    private static final String OPTIONS_SCREEN = "options";
    private static final String GAME_SCREEN = "game";

    private final CardLayout screenLayout = new CardLayout();
    private final JPanel screenPanel = new JPanel(screenLayout);
    private ChessPanel chessPanel;
    private boolean highlightMoves = true;
    private boolean autoQueenPromotion = false;
    
    private GameState savedGameState;

    
    public GUI() {
    	
    	setTitle("CHESS");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadSettingsIfPresent();

        screenPanel.add(new StartPanel(this), START_SCREEN);
        screenPanel.add(new OptionsPanel(this), OPTIONS_SCREEN);
        add(screenPanel, BorderLayout.CENTER);

        loadSavedGameIfPresent();

        showStartScreen();
        
        setVisible(true);
    }

    public void showStartScreen() {
        screenLayout.show(screenPanel, START_SCREEN);
    }

    public void showOptionsScreen() {
        screenLayout.show(screenPanel, OPTIONS_SCREEN);
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
    
    public boolean hasSavedGame() {
    	
        return savedGameState != null;
    }
    
    public void setSavedGame(GameState newGameState) {
    	
    	savedGameState = newGameState;
    }
    
    public void continueGame() {
    	
    	if(savedGameState == null) {
    		loadSavedGameIfPresent();
    	}

    	if(savedGameState == null) {
    		JOptionPane.showMessageDialog(this, "No saved game found.");
    		return;
        }
    	
    	if(chessPanel != null) {
            screenPanel.remove(chessPanel);
        }
    	
        chessPanel = new ChessPanel(this, highlightMoves, savedGameState);
        screenPanel.add(chessPanel, GAME_SCREEN);
        screenPanel.revalidate();
        screenPanel.repaint();
        screenLayout.show(screenPanel, GAME_SCREEN);    	
    }

    private void loadSavedGameIfPresent() {
    	
    	if(!SaveLoadUtils.saveExists()) {
    		return;
    	}

    	try {
    		savedGameState = SaveLoadUtils.loadGame();
    		highlightMoves = savedGameState.isHighlightMovesEnabled();
    		autoQueenPromotion = savedGameState.isAutoQueenPromotionEnabled();
    	}
    	catch(Exception e) {
    		savedGameState = null;
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


