package view;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
    public OptionsPanel(GUI gui) {
        setLayout(new GridBagLayout());
        setBackground(new Color(35, 35, 35));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("OPTIONS");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 38));
        titleLabel.setForeground(new Color(240, 240, 240));

        JCheckBox highlightMovesCheckBox = new JCheckBox("Highlight Posssible Moves");
        highlightMovesCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        highlightMovesCheckBox.setSelected(gui.isHighlightMovesEnabled());
        highlightMovesCheckBox.setOpaque(false);
        highlightMovesCheckBox.setForeground(new Color(240, 240, 240));
        highlightMovesCheckBox.setFont(new Font("SansSerif", Font.BOLD, 16));
        highlightMovesCheckBox.addActionListener(e -> gui.setHighlightMoves(highlightMovesCheckBox.isSelected()));

        JButton backButton = createMenuButton("Back");
        backButton.addActionListener(e -> gui.showStartScreen());

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(highlightMovesCheckBox);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 24)));
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
