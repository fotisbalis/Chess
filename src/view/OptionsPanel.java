package view;

import javax.swing.*;

import utils.GUIUtils;

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
        GUIUtils.initializeCheckBox(highlightMovesCheckBox);
        highlightMovesCheckBox.setSelected(gui.isHighlightMovesEnabled());
        highlightMovesCheckBox.addActionListener(e -> gui.setHighlightMoves(highlightMovesCheckBox.isSelected()));

        JCheckBox autoQueenPromotionCheckBox = new JCheckBox("Automatic Queen Promotion");
        GUIUtils.initializeCheckBox(autoQueenPromotionCheckBox);
        autoQueenPromotionCheckBox.setSelected(gui.isAutoQueenPromotion());
        autoQueenPromotionCheckBox.addActionListener(e -> gui.setAutoQueenPromotion(autoQueenPromotionCheckBox.isSelected()));
        
        JButton backButton = createMenuButton("Back");
        backButton.addActionListener(e -> gui.showStartScreen());

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        contentPanel.add(highlightMovesCheckBox);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 24)));
        contentPanel.add(autoQueenPromotionCheckBox);
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
