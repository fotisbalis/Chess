package view;

import javax.swing.*;
import java.awt.*;

public class GameModePanel extends JPanel {

	public GameModePanel(GUI gui, boolean singlePlayer) {
		setLayout(new GridBagLayout());
		setBackground(new Color(35, 35, 35));

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setOpaque(false);

		String title = singlePlayer ? "SINGLE PLAYER" : "TWO PLAYER";
		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
		titleLabel.setForeground(new Color(240, 240, 240));

		JButton newGameButton = createMenuButton("Start New Game");
		if(singlePlayer)
			newGameButton.addActionListener(e -> gui.showSinglePlayerGameScreen());
		else
			newGameButton.addActionListener(e -> gui.showGameScreen());

		JButton continueButton = createMenuButton("Continue Game");
		if(singlePlayer)
			continueButton.addActionListener(e -> gui.continueSinglePlayerGame());
		else
			continueButton.addActionListener(e -> gui.continueTwoPlayerGame());

		JButton backButton = createMenuButton("Back");
		backButton.addActionListener(e -> gui.showStartScreen());

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(newGameButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(continueButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(backButton);

		add(contentPanel);
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
