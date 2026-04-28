package view;

import javax.swing.*;
import java.awt.*;

public class DifficultyPanel extends JPanel {

	public DifficultyPanel(GUI gui) {
		setLayout(new GridBagLayout());
		setBackground(new Color(35, 35, 35));

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setOpaque(false);

		JLabel titleLabel = new JLabel("CHOOSE DIFFICULTY");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
		titleLabel.setForeground(new Color(240, 240, 240));
		
		JButton easyButton = createMenuButton("Easy");
		easyButton.addActionListener(e -> showColorSelection(gui, 3));

		JButton mediumButton = createMenuButton("Medium");
		mediumButton.addActionListener(e -> showColorSelection(gui, 4));

		JButton hardButton = createMenuButton("Hard");
		hardButton.addActionListener(e -> showColorSelection(gui, 5));

		JButton infoButton = createMenuButton("AI Info");
		infoButton.addActionListener(e -> showAIInfo());

		JButton backButton = createMenuButton("Back");
		backButton.addActionListener(e -> gui.showSinglePlayerMenu());

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(easyButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(mediumButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(hardButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(infoButton);
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

	private void showColorSelection(GUI gui, int aiDepth) {
		gui.setPendingSinglePlayerDepth(aiDepth);
		gui.showSinglePlayerColorScreen();
	}

	private void showAIInfo() {
		JTextArea infoText = new JTextArea(
				"The AI uses a minimax-style search.\n\n" +
				"It looks ahead through possible moves and gives each future board a score. " +
				"Moves that are good for the AI increase the score, while moves that are good " +
				"for the opponent decrease it.\n\n" +
				"Depth controls how many moves ahead the AI searches.\n\n" +
				"Difficulty levels:\n" +
				"Easy: depth 4\n" +
				"Medium: depth 5\n" +
				"Hard: depth 6\n"
		);

		infoText.setEditable(false);
		infoText.setLineWrap(true);
		infoText.setWrapStyleWord(true);
		infoText.setFont(new Font("SansSerif", Font.PLAIN, 14));
		infoText.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(infoText);
		scrollPane.setPreferredSize(new Dimension(420, 300));

		JOptionPane.showMessageDialog(this, scrollPane, "How The AI Works", JOptionPane.INFORMATION_MESSAGE);
	}
}
