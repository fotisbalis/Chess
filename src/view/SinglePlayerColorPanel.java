package view;

import javax.swing.*;
import java.awt.*;

import pawn.PawnColor;

public class SinglePlayerColorPanel extends JPanel {

	public SinglePlayerColorPanel(GUI gui) {
		setLayout(new GridBagLayout());
		setBackground(new Color(35, 35, 35));

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setOpaque(false);

		JLabel titleLabel = new JLabel("CHOOSE YOUR COLOR");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
		titleLabel.setForeground(new Color(240, 240, 240));

		JButton whiteButton = createMenuButton("White");
		whiteButton.addActionListener(e -> gui.startSinglePlayerGame(gui.getPendingSinglePlayerDepth(), PawnColor.WHITE));

		JButton blackButton = createMenuButton("Black");
		blackButton.addActionListener(e -> gui.startSinglePlayerGame(gui.getPendingSinglePlayerDepth(), PawnColor.BLACK));

		JButton backButton = createMenuButton("Back");
		backButton.addActionListener(e -> gui.showDifficultyScreen());

		contentPanel.add(titleLabel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		contentPanel.add(whiteButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
		contentPanel.add(blackButton);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
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
