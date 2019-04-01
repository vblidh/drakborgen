package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class GameViewer
{

    private Board gameBoard;
    private final JFrame frame;
    private GameComponent comp;
    private final BrickGenerator generator;
    private JTextArea eventlog;
    private Character currentHero;
    private JTextArea currentHeroInfo;
    private MouseInputAdapter mouseAdapter;

    public GameViewer(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard);
	this.eventlog = new JTextArea();
	this.currentHero = null;
	this.currentHeroInfo = new JTextArea();
	this.generator = new BrickGenerator();
	this.mouseAdapter = new MouseInputAdapter()
	{
	    @Override public void mousePressed(final MouseEvent e) {
		super.mousePressed(e);
		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
		    int row = e.getY()/60;
		    int col = e.getX()/60;
		    try {
		    	BrickType type = drawBrick();
		    	Direction dir = decideDirection(row,col);
		    	if (dir == Direction.INVALID) eventlog.setText("Du kan bara flytta till en intilliggande ruta med öppning!");
		    	else {
		    	    gameBoard.placeBrick(row,col,type, dir);
		    	    currentHero.setyPos(row);
		    	    currentHero.setxPos(col);

		    	}
		}
		    catch (BadLocationException ex) {
			System.out.println(ex.getMessage());
		    }
		}
	    }
	};

	comp.addMouseListener(mouseAdapter);

	final JMenuBar menuBar = new JMenuBar();
	final JMenu file = new JMenu("File");
	Color backGround = new Color(92, 62, 10);
	JMenuItem quit = new JMenuItem("Quit");
	file.add(quit);
	quit.addActionListener(new QuitAction());
	menuBar.add(file);

	eventlog.setFont(new Font("Helvetica",Font.PLAIN, 18));
	eventlog.setForeground(Color.GREEN);
	eventlog.setBounds(1100,350,400,400);
	eventlog.setBackground(Color.BLACK);
	eventlog.setEditable(false);
	eventlog.setRows(5);


	JPanel panel = new JPanel();
	panel.setBackground(backGround);
	currentHeroInfo.setBackground(Color.WHITE);
	currentHeroInfo.setForeground(Color.BLACK);
	currentHeroInfo.setFont(new Font("Monospaced", Font.BOLD, 18));
	currentHeroInfo.setEditable(false);

	JButton brickButton = new JButton("Dra rumsbricka");
	JButton roomButton = new JButton("Dra rumskort");
	brickButton.setFont(new Font("Helvetica", Font.BOLD, 20));
	brickButton.setBackground(Color.WHITE);
	roomButton.setFont(new Font("Helvetica", Font.BOLD, 20));
	roomButton.setBackground(Color.WHITE);
	panel.add(currentHeroInfo);
	panel.add(brickButton);
	panel.add(roomButton);
	panel.add(eventlog);



	frame.setLayout(new GridLayout());
	frame.add(comp, BorderLayout.CENTER);
	frame.add(panel, BorderLayout.EAST);
	frame.pack();
	frame.setVisible(true);
	frame.setJMenuBar(menuBar);


    }

    public GameComponent getComp() {
	return comp;
    }

    public void setCurrentHero(final Character currentHero) {
	this.currentHero = currentHero;
    }

    public BrickType drawBrick() throws BadLocationException {

	if (eventlog.getLineCount() > 4) {
	    int end = eventlog.getLineEndOffset(0);
	    eventlog.replaceRange("", 0, end);
	}
        BrickType type = generator.generateBrick();
        eventlog.append(type + " bricka placerad \n");
        return type;
    }

    public void updateHeroInfo(){
        if (currentHero != null) {
	    currentHeroInfo.setText("Hjälte: " + currentHero.getName() +"\n" +
				   "Styrka (SF)  : " + currentHero.getStrengthFactor() + "\n" +
				   "Vighet (VF)  : " + currentHero.getAgilityFactor() + "\n" +
				   "Rustning (RF): " + currentHero.getArmorFactor() + "\n" +
				   "Tur (TF):    : " + currentHero.getLuckFactor() + "\n" +
				   "Kroppspoäng  : " + currentHero.getCurrentHealth() +"/" + currentHero.getHealthPoints());
        }
    }

    public Direction decideDirection(int row, int col){
        Brick brick = gameBoard.getBrick(row,col);
        if (currentHero.getxPos() == col-1 && currentHero.getyPos() == row && brick.getSquare(3,5) == SquareType.PATH)
            return Direction.RIGHT;
        else if (currentHero.getxPos() == col+1 && currentHero.getyPos() == row && brick.getSquare(3,0) == SquareType.PATH)
            return Direction.LEFT;
        else if (currentHero.getxPos() == col && currentHero.getyPos() == row-1 && brick.getSquare(5,3) == SquareType.PATH)
            return Direction.DOWN;
        else if (currentHero.getxPos() == col && currentHero.getyPos() == row+1 && brick.getSquare(0,3) == SquareType.PATH)
            return Direction.UP;
        else return Direction.INVALID;
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
