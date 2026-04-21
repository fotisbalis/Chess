package view;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private final GUI gui;

    public StartPanel(GUI gui) {
        this.gui = gui;

        setLayout(new GridBagLayout());
        setBackground(new Color(35, 35, 35));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("CHESS");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 44));
        titleLabel.setForeground(new Color(240, 240, 240));

        JButton singlePlayerButton = createMenuButton("Single Player Game");
        singlePlayerButton.addActionListener(e -> gui.showSinglePlayerMenu());

        JButton twoPlayerButton = createMenuButton("Two Player Game");
        twoPlayerButton.addActionListener(e -> gui.showTwoPlayerMenu());
        
        JButton optionsButton = createMenuButton("Options");
        optionsButton.addActionListener(e -> gui.showOptionsScreen());
        
        JButton exitButton = createMenuButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(singlePlayerButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        contentPanel.add(twoPlayerButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        contentPanel.add(optionsButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));
        contentPanel.add(exitButton);

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
