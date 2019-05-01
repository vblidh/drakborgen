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
import java.util.List;
import java.util.Random;


public class GameViewer
{

    private static final int BRICK_SIZE = 60;
    private static final int TEXT_SIZE = 18;
    private static final int T12 = 12;
    private static final int [] JEWELRYVALUES = {50,100,150,200,250};
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
    private JButton treasureButton;
    private MouseInputAdapter mouseAdapter;
    private boolean movedWithinTreasureRoom;

    private List<Player> players;
    private List<Action> allowedActions;

    private Point highLightedBrick;
    private int numberOfPlayers;
    private int currentPlayer;

    public GameViewer(final Board gameBoard, final List<Player> players) {
	this.gameBoard = gameBoard;
	this.players = players;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard);
	gameBoard.addBoardListener(comp);
	this.currentHero = null;

	this.bgenerator = new BrickGenerator();
	this.cgenerator = new CardGenerator();
	this.highLightedBrick = null;
	Character hero = gameBoard.getCharacter();
	this.currentHero = players.get(0).getHero();
	this.allowedActions = new ArrayList<>();
	this.allowedActions.add(Action.MOVEHERO);
	gameBoard.addCharacter(hero);
	this.numberOfPlayers = players.size() - 1;
	this.currentPlayer = 0;

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
	panel.add(treasureButton);
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
	this.treasureButton = new JButton("Dra skattkammarkort");
	brickButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	brickButton.setBackground(Color.WHITE);
	brickButton.addActionListener(new DrawBrickAction());
	roomButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	roomButton.setBackground(Color.WHITE);
	roomButton.addActionListener(new DrawRoomCardAction());
	treasureButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	treasureButton.setBackground(Color.WHITE);
	treasureButton.addActionListener(new DrawTreasureCardAction());

    }

    private void addTextToEventLog(String text) {
        try {
	    if (eventlog.getLineCount() > 7) {
		int end = eventlog.getLineEndOffset(0);
		eventlog.replaceRange("", 0, end);
	    }
	}
        catch (BadLocationException e) {
	    System.out.println(e.getMessage());
	}
	eventlog.append(text);
    }

    public BrickType drawBrick()  {
	BrickType type = bgenerator.generateBrick();
	addTextToEventLog(type + " bricka placerad \n");
        return type;
    }

    private void updateHeroInfo(){
        if (currentHero != null) {
	    currentHeroInfo.setText(
	    	"Spelare: " + players.get(currentPlayer).getName() + "\n" +
	    	"Hjälte: " + currentHero.getName() +"\n" +
		"Styrka (SF)  : " + currentHero.getStrengthFactor() + "\n" +
		"Vighet (VF)  : " + currentHero.getAgilityFactor() + "\n" +
		"Rustning (RF): " + currentHero.getArmorFactor() + "\n" +
		"Tur (TF):    : " + currentHero.getLuckFactor() + "\n" +
		"Kroppspoäng  : " + currentHero.getCurrentHealth() +"/" + currentHero.getHealthPoints() + "\n" +
	    	"Värde av skatter: " + players.get(currentPlayer).getTreasures()
	    );
        }
    }

    private Direction decideDirection(int row, int col){
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

    private boolean checkHeroHealth(){
        if (currentHero.getCurrentHealth() <= 0) {
            currentHero.setCurrentHealth(0);
            players.get(currentPlayer).kill();
            return false;
	}
        else return true;
    }

    private void advanceTurn(){
	allowedActions.clear();
	allowedActions.add(Action.MOVEHERO);

	int alivePlayers = 0;
	for (Player player: players) {
	    if (player.isAlive()) alivePlayers++;
	}
	if (alivePlayers == 0) {
	    //TODO: Add ending to game.
	    eventlog.setText("Alla spelare döda");
	    allowedActions.clear();
	    return;
	}

	do {
	    if (currentPlayer == numberOfPlayers) {
		comp.decrementSunTimer();
		currentPlayer = 0;
	    } else currentPlayer++;
	} while (!(players.get(currentPlayer).isAlive()));

	currentHero = players.get(currentPlayer).getHero();
	updateHeroInfo();
	if (gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType().equals(BrickType.TREASURE)) {
	    allowedActions.add(Action.DRAWTREASURECARD);
	}
    }
    private void openChest(Random rnd, Object[] diceOption){
        ChestCard card = cgenerator.drawChestCard();
        switch (card){
	    case EMPTY:
		JOptionPane.showMessageDialog(
			frame.getParent(),
			"Kistan är tom"
		);
		break;
	    case JEWELRY:
		int jewelryValue = JEWELRYVALUES[rnd.nextInt(5)];
		JOptionPane.showMessageDialog(
			frame.getParent(),
			"Kistan innehåller ett smycke värt " + jewelryValue + " guldmynt"
		);
		players.get(currentPlayer).addTreasure(jewelryValue);
		break;
	    case TRAP:
		int choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"När du öppnar kistan aktiveras en fälla och du måste omedelbart slå T12-3-TF " +
			"för att se hur mycket skada du tar",
			"Fälla!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, diceOption , diceOption[0]
		);
		if (choice==0){
		    int r = rnd.nextInt(T12)+1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r-3-currentHero.getLuckFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Du lyckas inte undvika fällan utan tar " + damageTaken + " skada");
			checkHeroHealth();
		    }
		    else {
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Turligt nog missar fällan dina händer och du tar ingen skada");
		    }
		}
	}
    }


    private void handleRoomCard(RoomCard card) {
	Random rnd = new Random();
	final Object[] options = { "Slå tärning" };
	int choice;
	switch (card) {
	    case JEWELRY:
	        int jewelryValue = JEWELRYVALUES[rnd.nextInt(5)];
		JOptionPane.showInternalMessageDialog(
			frame.getParent(),
			"Rummet innehåller ett smycke värt " + jewelryValue + " guldmynt",
			card.toString(), JOptionPane.INFORMATION_MESSAGE
		);
		players.get(currentPlayer).addTreasure(jewelryValue);
		break;

	    case SARCOPHAGUS:
	    	final Object[] chestOptions = {"Öppna kistan", "Ignorera kistan"};
		choice = JOptionPane.showOptionDialog(
					frame.getParent(),
					card +  "Vill du öpnna den?",
					 "Kista", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
					 null, chestOptions, null
		);
		if (choice == 0){
		    openChest(rnd, options);
		}
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
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    if (r == 1) {
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Taket rasar ner över dig och du dör omedelbart. Du har förlorat"
			);
			currentHero.setCurrentHealth(0);
			players.get(currentPlayer).kill();
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
	        String msg = "Så snart du kliver in i rummet börjar de spjutförsedda väggarna sluta sig om dig. " +
			     "Du har dock en liten chans att överleva.\nPå golvet ser du tre stenplattor; en av dessa " +
			     "hejdar fällan och oskadliggör den, men du hinner bara trampa på två av plattorna.\n" +
			     "			    Välj en platta att trampa på.";
	        while (platesPressed < 2) {
		    int chosenPlate = JOptionPane.showOptionDialog(
		    	frame.getParent(), msg, card.toString(),
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, plates, null);
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
		    players.get(currentPlayer).kill();
		}
		break;

	    case ARROWTRAP:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Plötsligt börjar pilar skjutas ut från väggarna och du tar skador motsvarande T12 - RF",
			 card.toString(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, options, options[0]);
		if (choice == 0) {
		    int r = rnd.nextInt(T12) + 1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getArmorFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Pilarna penetrerar din rustning och du tar " + damageTaken + " skada");
			checkHeroHealth();
		    } else JOptionPane.showMessageDialog(
		    	frame.getParent(),
			"Pilarna lyckas inte penetrera din rustning, du tar ingen skada");
		}
		break;

	    case TROLLAMBUSH:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Du blir överfallen av ett troll, du måste först slå T12-TF för att se" +
			"hur mycket skada du tar av dess initiala hugg, innan du kan börja slåss", card.toString(),
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (choice == 0) {
		    int r = rnd.nextInt(T12) + 1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getLuckFactor();
		    if (damageTaken > 0) {
		        currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
		        JOptionPane.showMessageDialog(
		        	frame.getParent(),
				"Trollet hugger dig och du tar " + damageTaken + " skada");
			if (checkHeroHealth()) battleWithMonster(card,rnd);
		    }
		    else JOptionPane.showMessageDialog(frame.getParent(), "Trollets hugg missar dig");
		}
		break;

	    case SKELETONAMBUSH:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Du blir överfallen av ett skelett, du måste först slå T12-TF för att se" +
			"hur mycket skada du tar av dess initiala hugg, innan du kan börja slåss", card.toString(),
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (choice == 0) {
		    int r = rnd.nextInt(T12) + 1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getLuckFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),
				"Skelettet hugger dig och du tar " + damageTaken + " skada");
			if (checkHeroHealth()) battleWithMonster(card,rnd);

		    } else {
			JOptionPane.showMessageDialog(frame.getParent(), "Skelettets hugg missar dig");
		    }
		}
		break;

	    case ORCAMBUSH:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Du blir överfallen av en orch, du måste först slå T12-TF för att se" +
			"hur mycket skada du tar av dess initiala hugg, innan du kan börja slåss", card.toString(),
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (choice == 0) {
		    int r = rnd.nextInt(T12) + 1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getLuckFactor();
		    if (damageTaken > 0) {
		        currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
		        JOptionPane.showMessageDialog(
		        	frame.getParent(),
				"Orchen hugger dig och du tar " + damageTaken + " skada");

		    }
		    else {
		        JOptionPane.showMessageDialog(frame.getParent(), "Orchens hugg missar dig");
		    }
		    if (checkHeroHealth())
      			battleWithMonster(card,rnd);
		}
		break;

	    case EMPTY:
		JOptionPane.showInternalMessageDialog(frame.getParent(), card.toString(), "Rumskort", JOptionPane.INFORMATION_MESSAGE);
		break;

	    default:
		JOptionPane.showInternalMessageDialog(frame.getParent(), "Du stöter på " + card + " och går till attack!",
						      "Rumskort", JOptionPane.INFORMATION_MESSAGE);
	        battleWithMonster(card, rnd);
	}
    }

    private void battleWithMonster(RoomCard card, Random rnd) {
	int monsterHealth = decideMonsterAction(card, rnd);
	boolean monsterFlees = (monsterHealth==0);

        if (monsterFlees) {
		JOptionPane.showMessageDialog(frame.getParent(), "Monstret flyr");
        }
        else {
	    JOptionPane.showMessageDialog(frame.getParent(), "Monstret möter din attack!");
            while (monsterHealth > 0){
		int choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Du och monstret väljer en varsin attack. Reglerna som gäller är:\n 	A slår B, B slår C, C slår A",
			"Strid med " + card + "!",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, currentHero.getAttackOptions(), null
		);
		Attack monsterAttack = Attack.values()[rnd.nextInt(3)];
		Attack heroAttack = Attack.values()[choice];

		if (heroAttack.beats(monsterAttack)) {
		    int damageDone = choice == currentHero.getDoubleDamageAttack() ? 2 : 1;
		    addTextToEventLog("Du valde attack " + heroAttack + " och monstret valde attack " + monsterAttack + "\n" +
				      "Din attack träffade och du gjorde " + damageDone + " skada\n");
		    monsterHealth -= damageDone;
		}
		else if (monsterAttack.beats(heroAttack)){
		    addTextToEventLog("Du valde attack " + heroAttack + " och monstret valde attack " + monsterAttack + "\n" +
				      "Monstrets attack träffade och gjorde " + 1 + " skada\n");
		    currentHero.setCurrentHealth(currentHero.getCurrentHealth()-1);
		}
		else {
		    addTextToEventLog("Du valde attack " + heroAttack + " och monstret valde attack " + monsterAttack + "\n" +
				      "Båda attacker träffade och gjorde " + 1 + " skada till vardera\n");
		    currentHero.setCurrentHealth(currentHero.getCurrentHealth()-1);
		    monsterHealth--;
		}
		if (!checkHeroHealth()) break;
		comp.repaint();
	    }
	    JOptionPane.showInternalMessageDialog(frame.getParent(), "Monstret dog och du pustar ut. Din runda är över",
						  "Strid över", JOptionPane.INFORMATION_MESSAGE);
	}
    }

    private int decideMonsterAction(RoomCard card, Random rnd) {
	int escapeFactor = rnd.nextInt(T12);
	switch (card) {
	    case GOBLIN:
		return escapeFactor < 3 ? rnd.nextInt(3) + 1 : 0;
	    case TROLL:
		return escapeFactor < 5 ?  rnd.nextInt(4) + 1 : 0;
	    case SKELETON:
		return escapeFactor < 8 ? rnd.nextInt(4) + 2 : 0;
	    case ORC:
		return escapeFactor < 8 ? rnd.nextInt(3) + 3 : 0;
	    case TWOORCS:
		return escapeFactor < 9 ? rnd.nextInt(4) + 3 : 0;
	    case TROLLAMBUSH:
		return rnd.nextInt(4 + 1);
	    case SKELETONAMBUSH:
		return rnd.nextInt(4) + 2;
	    case ORCAMBUSH:
		return rnd.nextInt(3) + 3;
	    default:
		return 0;
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
	    @Override public void actionPerformed(final ActionEvent e) {
		if (highLightedBrick == null) {
		    eventlog.setText("Du måste välja vart du vill förflytta dig\n");
		    return;
		}
		int row = highLightedBrick.y;
		int col = highLightedBrick.x;
		if (gameBoard.getBrick(row, col).getType() == BrickType.UNDISCOVERED) {
		    BrickType type = drawBrick();
		    gameBoard.placeBrick(row, col, type, decideDirection(row, col));
		    currentHero.setyPos(row);
		    currentHero.setxPos(col);
		} else {
		    if (gameBoard.getBrick(row, col).getType().equals(BrickType.TREASURE) &&
			gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType().equals(BrickType.TREASURE)) {
			movedWithinTreasureRoom = true;
		    }
		    gameBoard.removeHighLight(row, col);
		    currentHero.setyPos(row);
		    currentHero.setxPos(col);
		    eventlog.setText("Flyttade till redan befintlig bricka \n");
		}

		highLightedBrick = null;
		BrickType curBrick = gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType();
		System.out.println(curBrick);

		if (ArrayUtils.contains(EXCEPTIONBRICKS, curBrick) && !movedWithinTreasureRoom) {
		    addTextToEventLog("Du behöver ej dra ett rumskort på den här rutan\n");
		    if (curBrick.equals(BrickType.TREASURE)) {
		        allowedActions.add(Action.DRAWTREASURECARD);
		        allowedActions.remove(Action.MOVEHERO);
		    }
		    else advanceTurn();
		}
		else if ((!movedWithinTreasureRoom)) {
		    allowedActions.remove(Action.MOVEHERO);
		    allowedActions.remove(Action.DRAWTREASURECARD);
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
	    allowedActions.remove(Action.DRAWROOMCARD);

	    handleRoomCard(card);

	    advanceTurn();
	}
    }

    private class DrawTreasureCardAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    if (gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType().equals(BrickType.TREASURE)
	    	&& allowedActions.contains(Action.DRAWTREASURECARD))
	    {
	        Random rnd = new Random();
		for (int i = 0; i < 2; i++) {
		    TreasureCard card = cgenerator.drawTreasureCard();
		    int cardValue = card.getValue(rnd);
		    JOptionPane.showInternalMessageDialog(
		    	frame.getParent(), card + " till ett värde av " + cardValue,
			"Skattkammarkort", JOptionPane.INFORMATION_MESSAGE);
		    players.get(currentPlayer).addTreasure(cardValue);
		}
		advanceTurn();
	    }
	    else {
	        addTextToEventLog("Du kan bara dra skattkammarkort när du befinner dig i skattkammaren");
	    }
	}
    }
}

