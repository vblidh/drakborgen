package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameViewer
{

    private Board gameBoard;
    private final JFrame frame;
    private GameComponent comp;

    public GameViewer(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard);
	final JMenuBar menuBar = new JMenuBar();
	final JMenu file = new JMenu("File");
	JMenuItem quit = new JMenuItem("Quit");
	file.add(quit);
	quit.addActionListener(new QuitAction());
	menuBar.add(file);
	frame.setLayout(new BorderLayout());
	frame.add(comp, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
	frame.setJMenuBar(menuBar);
    }

    public GameComponent getComp() {
	return comp;
    }

    private class QuitAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    /*if (JOptionPane.showConfirmDialog(null, "Do you want to quit?", "Quitting Tetris", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION) {


	    }*/
	    System.exit(0);
	}
    }
}
