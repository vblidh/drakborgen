package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;
import java.awt.*;

public class GameViewer
{

    private Board gameBoard;
    private final JFrame frame;
    private GameComponent comp;

    public GameViewer(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard);
	frame.setLayout(new BorderLayout());
	frame.add(comp, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);

    }
}
