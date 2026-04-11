package view;

import pawn.*;
import board.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    public GUI() {
    	
    	setTitle("CHESS");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLayout(new BorderLayout());
    	
        add(new ChessPanel());
        
    	setVisible(true);
    }
}


