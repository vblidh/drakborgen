package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameViewer
{

    private Board gameBoard;
    private final JFrame frame;
    private GameComponent comp;
    private final BrickGenerator generator;
    private JTextArea eventlog;

    public GameViewer(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard);
	this.eventlog = new JTextArea();
	final JMenuBar menuBar = new JMenuBar();
	final JMenu file = new JMenu("File");
	JMenuItem quit = new JMenuItem("Quit");
	file.add(quit);
	quit.addActionListener(new QuitAction());
	menuBar.add(file);
	DefaultStyledDocument doc = new DefaultStyledDocument();
	JTextPane pane = new JTextPane(doc);
	pane.setBounds(1250,320, 120,30);
	pane.setBackground(Color.lightGray);
	pane.setForeground(Color.BLUE);
	pane.setEditable(false);
	pane.setFont(new Font("Monospaced",Font.PLAIN, 22));
	pane.setText("Inforuta");
	frame.add(pane);

	eventlog.setFont(new Font("Helvetica",Font.PLAIN, 18));
	eventlog.setForeground(Color.GREEN);
	eventlog.setBounds(1100,350,400,400);
	eventlog.setBackground(Color.BLACK);
	eventlog.setEditable(false);
	frame.add(eventlog);
	frame.setLayout(new BorderLayout());
	frame.add(comp, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
	frame.setJMenuBar(menuBar);


	this.generator = new BrickGenerator();
    }

    public GameComponent getComp() {
	return comp;
    }

    public BrickType drawBrick(){
        BrickType type = generator.generateBrick();
        eventlog.append("Ny " + type + " bricka placerad \n");
        return type;
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
