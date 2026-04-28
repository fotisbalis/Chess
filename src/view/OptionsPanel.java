package view;

import javax.swing.*;

import utils.GUIUtils;

import java.awt.*;

public class OptionsPanel extends JPanel {
	private static final String MENU_SCREEN = "menu";
	private static final String VISUAL_SCREEN = "visual";
	private static final String RULES_SCREEN = "rules";

	private final CardLayout contentLayout = new CardLayout();
	private final JPanel contentHolder = new JPanel(contentLayout);

	public OptionsPanel(GUI gui) {
		setLayout(new GridBagLayout());
		setBackground(new Color(35, 35, 35));

		contentHolder.setOpaque(false);
		contentHolder.add(createMainMenu(gui), MENU_SCREEN);
		contentHolder.add(createVisualPanel(gui), VISUAL_SCREEN);
		contentHolder.add(createRulesPanel(gui), RULES_SCREEN);

		add(contentHolder);
	}

	private JPanel createMainMenu(GUI gui) {
		JPanel contentPanel = createContentPanel();

		JLabel titleLabel = createTitleLabel("OPTIONS");

		JButton visualButton = createMenuButton("Visual");
		visualButton.addActionListener(e -> contentLayout.show(contentHolder, VISUAL_SCREEN));

		JButton rulesButton = createMenuButton("Rules");
		rulesButton.addActionListener(e -> contentLayout.show(contentHolder, RULES_SCREEN));

		JButton backButton = createMenuButton("Back");
		backButton.addActionListener(e -> gui.showStartScreen());

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(visualButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(rulesButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(backButton);

		return contentPanel;
	}

	private JPanel createVisualPanel(GUI gui) {
		JPanel contentPanel = createContentPanel();

		JLabel titleLabel = createTitleLabel("VISUAL");

		JCheckBox highlightMovesCheckBox = new JCheckBox("Highlight Possible Moves");
		GUIUtils.initializeCheckBox(highlightMovesCheckBox);
		highlightMovesCheckBox.setSelected(gui.isHighlightMovesEnabled());
		highlightMovesCheckBox.addActionListener(e -> gui.setHighlightMoves(highlightMovesCheckBox.isSelected()));
		
		JCheckBox brightColorsCheckBox = new JCheckBox("Bright Highlight Colors (possible moves, king in check, selected piece)");
		GUIUtils.initializeCheckBox(brightColorsCheckBox);
		brightColorsCheckBox.setSelected(gui.isBrightColorsEnabled());
		brightColorsCheckBox.addActionListener(e -> gui.setBrightColors(brightColorsCheckBox.isSelected()));

		JButton backButton = createMenuButton("Back");
		backButton.addActionListener(e -> contentLayout.show(contentHolder, MENU_SCREEN));

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(highlightMovesCheckBox);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 24)));
		contentPanel.add(brightColorsCheckBox);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 24)));
		contentPanel.add(backButton);

		return contentPanel;
	}

	private JPanel createRulesPanel(GUI gui) {
		JPanel contentPanel = createContentPanel();

		JLabel titleLabel = createTitleLabel("RULES");

		JCheckBox autoQueenPromotionCheckBox = new JCheckBox("Automatic Queen Promotion");
		GUIUtils.initializeCheckBox(autoQueenPromotionCheckBox);
		autoQueenPromotionCheckBox.setSelected(gui.isAutoQueenPromotion());
		autoQueenPromotionCheckBox.addActionListener(e -> gui.setAutoQueenPromotion(autoQueenPromotionCheckBox.isSelected()));

		JButton backButton = createMenuButton("Back");
		backButton.addActionListener(e -> contentLayout.show(contentHolder, MENU_SCREEN));

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(autoQueenPromotionCheckBox);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 24)));
		contentPanel.add(backButton);

		return contentPanel;
	}

	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setOpaque(false);
		return contentPanel;
	}

	private JLabel createTitleLabel(String text) {
		JLabel titleLabel = new JLabel(text);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
		titleLabel.setForeground(new Color(240, 240, 240));
		return titleLabel;
	}

	private JButton createMenuButton(String text) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(220, 45));
		button.setPreferredSize(new Dimension(220, 45));
		button.setFocusPainted(false);
		button.setFont(new Font("SansSerif", Font.BOLD, 16));

		return button;
	}
}
