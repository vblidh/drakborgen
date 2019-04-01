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

    private static final int BRICK_SIZE = 60;

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
		    int row = e.getY()/BRICK_SIZE;
		    int col = e.getX()/BRICK_SIZE;
		    if (row >= gameBoard.getHeight() || col >= gameBoard.getWidth())
		        eventlog.setText("Du kan inte flytta hjälten utanför brädet \n");
		    else if (decideDirection(row,col) == Direction.INVALID)
		        eventlog.setText("Du kan bara flytta till en intilliggande ruta med öppning! \n");
		    else if (gameBoard.getBrick(row,col).getType() == BrickType.UNDISCOVERED){
			try {
			    BrickType type = drawBrick();
			    gameBoard.placeBrick(row, col, type, decideDirection(row,col));
			    currentHero.setyPos(row);
			    currentHero.setxPos(col);
		    }
			catch (BadLocationException ex) {
			    System.out.println(ex.getMessage());
			}
		    }
		    else {
		        currentHero.setyPos(row);
		        currentHero.setxPos(col);
		        comp.boardChanged();
		        eventlog.setText("Flyttade till redan befintlig bricka \n");
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
	frame.add(comp, BorderLayout.WEST);
	frame.add(panel, BorderLayout.EAST);
	frame.setJMenuBar(menuBar);
	frame.pack();
	frame.setVisible(true);


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
        int heroyPos = currentHero.getyPos();
        int heroxPos = currentHero.getxPos();
        Brick brick = gameBoard.getBrick(heroyPos,heroxPos);
        if (heroxPos == col-1 && heroyPos == row && brick.getSquare(3,5) == SquareType.PATH)
            return Direction.RIGHT;
        else if (heroxPos == col+1 && heroyPos == row && brick.getSquare(3,0) == SquareType.PATH)
            return Direction.LEFT;
        else if (heroxPos == col && heroyPos == row-1 && brick.getSquare(5,3) == SquareType.PATH)
            return Direction.DOWN;
        else if (heroxPos == col && heroyPos == row+1 && brick.getSquare(0,3) == SquareType.PATH)
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
