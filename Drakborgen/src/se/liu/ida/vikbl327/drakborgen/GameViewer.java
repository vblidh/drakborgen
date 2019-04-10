package se.liu.ida.vikbl327.drakborgen;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class GameViewer
{

    private static final int BRICK_SIZE = 60;
    private static final int TEXT_SIZE = 18;
    private static final SquareType [] ACCEPTED_SQUARES = {SquareType.PATH, SquareType.UNDISCOVERED, SquareType.TREASURE};
    private static final BrickType [] EXCEPTIONBRICKS = {BrickType.TREASURE,BrickType.START};

    private Board gameBoard;
    private GameComponent comp;
    private final BrickGenerator bgenerator;
    private final CardGenerator cgenerator;
    private JFrame frame;
    private JTextArea eventlog;
    private Character currentHero;
    private JTextArea currentHeroInfo;
    private JButton brickButton;
    private JButton roomButton;
    private MouseInputAdapter mouseAdapter;
    private boolean movedWithinTreasureRoom;

    private List<Action> allowedActions;

    private Point highLightedBrick;
    private int numberOfPlayers;
    private int currentPlayer;

    public GameViewer(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard);
	gameBoard.addBoardListener(comp);
	this.currentHero = null;

	this.bgenerator = new BrickGenerator();
	this.cgenerator = new CardGenerator();
	this.highLightedBrick = null;
	Character hero = gameBoard.getCharacter();
	this.currentHero = hero;
	this.allowedActions = new ArrayList<>();
	this.allowedActions.add(Action.MOVEHERO);
	gameBoard.addCharacter(hero);
	this.numberOfPlayers = gameBoard.getCharacters().size();
	this.currentPlayer = 1;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();

	System.out.println("Width: " + width + " Height: " + height);

	final JMenuBar menuBar = new JMenuBar();
	final JMenu file = new JMenu("File");

	JMenuItem quit = new JMenuItem("Quit");
	file.add(quit);
	quit.addActionListener(new QuitAction());
	menuBar.add(file);

	initializeComponents();
	initializeMouseAdapter();
	comp.addMouseListener(mouseAdapter);


	JPanel panel = new JPanel();
	Color backGround = new Color(92, 62, 10);
	panel.setBackground(backGround);
	panel.add(currentHeroInfo);
	panel.add(brickButton);
	panel.add(roomButton);
	panel.add(eventlog);


	updateHeroInfo();
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


    private void initializeMouseAdapter(){
	this.mouseAdapter = new MouseInputAdapter()
	{
	    @Override public void mousePressed(final MouseEvent e) {
		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
		    if (!allowedActions.contains(Action.MOVEHERO)) {
		        eventlog.setText("Du har redan förflyttat hjälten denna runda! \n");
		        return;
		    }
		    int row = e.getY()/BRICK_SIZE;
		    int col = e.getX()/BRICK_SIZE;
		    if (decideDirection(row,col) == Direction.INVALID)
		        eventlog.setText("Du kan bara flytta till en ruta med öppen väg innanför Drakborgen! \n");
		    else {
			if (highLightedBrick != null) {
			    if (highLightedBrick.y == row && highLightedBrick.x == col) {
				gameBoard.removeHighLight(row, col);
				highLightedBrick = null;
			    }
			    else {
		       		gameBoard.highLight(row,col);
		       		gameBoard.removeHighLight(highLightedBrick.y, highLightedBrick.x);
		       		highLightedBrick.setLocation(col,row);
			    }
			}
			else {
			    gameBoard.highLight(row,col);
			    highLightedBrick = new Point();
			    highLightedBrick.setLocation(col,row);
			}

			if (gameBoard.getBrick(row,col).getType().equals(BrickType.UNDISCOVERED))
			    brickButton.setText("Dra rumsbricka");
			else brickButton.setText("Flytta hjälte");
		    }
		}
	    }
	};
    }

    private void initializeComponents(){

	this.eventlog = new JTextArea();
	eventlog.setFont(new Font("Helvetica",Font.PLAIN, TEXT_SIZE));
	eventlog.setForeground(Color.GREEN);
	eventlog.setBackground(Color.BLACK);
	eventlog.setEditable(false);
	eventlog.setRows(5);


	this.currentHeroInfo = new JTextArea();
	currentHeroInfo.setBackground(Color.WHITE);
	currentHeroInfo.setForeground(Color.BLACK);
	currentHeroInfo.setFont(new Font("Monospaced", Font.BOLD, TEXT_SIZE));
	currentHeroInfo.setEditable(false);


	this.brickButton = new JButton("Dra rumsbricka");
	this.roomButton = new JButton("Dra rumskort");
	brickButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	brickButton.setBackground(Color.WHITE);
	brickButton.addActionListener(new DrawBrickAction());
	roomButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	roomButton.setBackground(Color.WHITE);
	roomButton.addActionListener(new DrawRoomCardAction());

    }

    public BrickType drawBrick() throws BadLocationException {

	if (eventlog.getLineCount() > 4) {
	    int end = eventlog.getLineEndOffset(0);
	    eventlog.replaceRange("", 0, end);
	}
        BrickType type = bgenerator.generateBrick();
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
	if (row >= gameBoard.getHeight() || col >= gameBoard.getWidth())
	    return Direction.INVALID;
        int heroyPos = currentHero.getyPos();
        int heroxPos = currentHero.getxPos();
        Brick curBrick = gameBoard.getBrick(heroyPos, heroxPos);
        Brick nextBrick = gameBoard.getBrick(row,col);
        if (heroxPos == col-1 && heroyPos == row && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(3, 5)) &&
	    ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(3, 0)))
            return Direction.RIGHT;
        else if (heroxPos == col+1 && heroyPos == row && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(3, 0))
		 && ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(3, 5)))
            return Direction.LEFT;
        else if (heroxPos == col && heroyPos == row-1 && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(5, 3))
		&& ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(0, 3)))
            return Direction.DOWN;
        else if (heroxPos == col && heroyPos == row+1 && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(0, 3))
		&& ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(5, 3)))
            return Direction.UP;
        else return Direction.INVALID;
    }

    private void advanceTurn(){
	allowedActions.clear();
	allowedActions.add(Action.MOVEHERO);
	Character hero = gameBoard.getCharacter();
	currentHero = hero;
	gameBoard.addCharacter(hero);
	updateHeroInfo();
	if  (currentPlayer == numberOfPlayers) {
	    comp.decrementSunTimer();
	    currentPlayer = 1;
	    //JOptionPane.showInternalMessageDialog(frame.getParent(), "En runda har passerat!");
	}
	else currentPlayer++;
    }

    private void handleRoomCard(RoomCard card) {
	Random rnd = new Random();
	final Object[] options = { "Slå tärning" };
	int choice;
	switch (card) {
	    case JEWELRY:
		break;
	    case ROOFFALL:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Taket över dig rasar, du måste slå med en 6-sidig tärning för att se om du överlever. " +
			 "Om tärningen visar en 1:a dör du och kan inte forstätta spela Drakborgen",
			 "Taket rasar!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			 null, options, options[0]
		);
		if (choice == 0) {
		    int r = rnd.nextInt(6) + 1;
		    eventlog.append("Tärningen visar: " + r + "\n");
		    if (r == 1) {
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Taket rasar ner över dig och du dör" + "omedelbart. Du har förlorat"
			);
			currentHero.setCurrentHealth(0);
		    } else {
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Du lyckas undvika raset, och kan forsätta ditt äventyr"
			);
		    }
		}
		break;
	    case SPEARTRAP:

	        Object [] plates = {"Vänstra plattan", "Mittersta plattan", "Högra plattan"};
	        int correctPlate = rnd.nextInt(3);
	        int platesPressed = 0;
		int chosenPlate;
	        String msg = "Så snart du kliver in i rummet börjar de spjutförsedda väggarna sluta sig om dig. " +
			     "Du har dock en liten chans att överleva\n. På golvet ser du tre stenplattor; en av dessa " +
			     "hejdar fällan och oskadliggör den, men du hinner bara trampa på två av plattorna." +
			     "Välj en platta att trampa på.";
	        while (platesPressed < 2) {
		    chosenPlate = JOptionPane.showOptionDialog(
		    	frame.getParent(), msg, card.toString(),
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, plates, options[0]);
		    if (chosenPlate == correctPlate) {
			JOptionPane.showMessageDialog(frame.getParent(), "Du lyckas trampa på rätt platta och överlever");
			break;
		    } else {
		        Object [] remainingPlates = {chosenPlate != 0 ? plates[0] : plates[1], chosenPlate == 1 ? plates[2] : plates[1]};
			platesPressed++;
			plates = remainingPlates;
			msg = "Du valde fel bricka och har bara en chans till på dig att trampa på rätt platta";
		    }
		}
	        if (platesPressed == 2) {
		    JOptionPane.showMessageDialog(
		    	frame.getParent(),
			"Du valde återigen fel platta att trampa på, väggarna sluts omkring dig och ditt äventyr är över");
		    currentHero.setCurrentHealth(0);
		}
		break;
	    case ARROWTRAP:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Plötsligt börjar pilar skjutas ut från väggarna och du tar skador motsvarande T12 - RF",
			 card.toString(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, options, options[0]);
		if (choice == 0) {
		    int r = rnd.nextInt(12) + 1;
		    eventlog.append("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getArmorFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Pilarna penetrerar din rustning och du tar " + damageTaken + " skada");
		    } else JOptionPane.showMessageDialog(
		    	frame.getParent(),
			"Pilarna lyckas inte penetrera din rustning, du tar ingen skada");
		}
		break;
	    case SKELETONAMBUSH:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Du blir överfallen av ett skelett, du måste först slå T12-TF för att se" +
			"hur mycket skada du tar av dess initiala hugg, innan du kan börja slåss", card.toString(),
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (choice == 0) {
		    int r = rnd.nextInt(12) + 1;
		    eventlog.append("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getLuckFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Skelettet hugger dig och du tar " + damageTaken + " skada");

		    } else {
			JOptionPane.showMessageDialog(frame.getParent(), "Skelettets hugg missar dig");
		    }
		}
		break;
	    default:
		JOptionPane.showInternalMessageDialog(frame.getParent(), card.toString(), "Rumskort", JOptionPane.INFORMATION_MESSAGE);

	}
    }


    private class QuitAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    /*if (JOptionPane.showConfirmDialog(null, "Vill du avsluta spelet?", "Avslutar Drakborgen", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION) {


	    }*/
	    System.exit(0);
	}
    }

    private class DrawBrickAction extends AbstractAction
    {
        @Override public void actionPerformed(final ActionEvent e){
            if (highLightedBrick == null) {
                eventlog.setText("Du måste välja vart du vill förflytta dig\n");
                return;
	    }
            int row = highLightedBrick.y;
            int col= highLightedBrick.x;
	    if (gameBoard.getBrick(row,col).getType() == BrickType.UNDISCOVERED){
		try {
		    BrickType type = drawBrick();
		    gameBoard.placeBrick(row, col, type, decideDirection(row,col));
		    currentHero.setyPos(row);
		    currentHero.setxPos(col);
	    }
		catch (BadLocationException ex) {
		    JOptionPane.showInternalMessageDialog(frame.getParent(), ex.getMessage());
		}
	    }
	    else {
	        if (gameBoard.getBrick(row,col).getType().equals(BrickType.TREASURE) &&
		    gameBoard.getBrick(currentHero.getyPos(),currentHero.getxPos()).getType().equals(BrickType.TREASURE))
		    movedWithinTreasureRoom = true;
		gameBoard.removeHighLight(row,col);
	        currentHero.setyPos(row);
	        currentHero.setxPos(col);
	        eventlog.setText("Flyttade till redan befintlig bricka \n");
	    }

	    highLightedBrick = null;
	    BrickType curBrick = gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType();
	    System.out.println(curBrick);

	    if (ArrayUtils.contains(EXCEPTIONBRICKS, curBrick) && !movedWithinTreasureRoom) {
		eventlog.append("Du behöver ej dra ett rumskort på den här rutan");
		advanceTurn();
	    }


	    else if ((!movedWithinTreasureRoom)){
		allowedActions.clear();
		allowedActions.add(Action.DRAWROOMCARD);
	    }


	    brickButton.setText("Dra rumsbricka");
	    movedWithinTreasureRoom = false;
        }
    }

    private class DrawRoomCardAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (!allowedActions.contains(Action.DRAWROOMCARD)) {
	        eventlog.setText("Du kan bara dra ett rumskort efter du har flyttat till en ny ruta \n");
	        return;
	    }
	    RoomCard card = cgenerator.drawRoomCard();
	    allowedActions.clear();
	    handleRoomCard(card);


	    //TODO: Add event relative to the card drawn

	    advanceTurn();
	}
    }
}

