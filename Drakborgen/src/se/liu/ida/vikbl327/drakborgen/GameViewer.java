package se.liu.ida.vikbl327.drakborgen;

import org.apache.commons.lang3.ArrayUtils;
import se.liu.ida.vikbl327.drakborgen.bricks.Brick;
import se.liu.ida.vikbl327.drakborgen.bricks.BrickGenerator;
import se.liu.ida.vikbl327.drakborgen.bricks.BrickType;
import se.liu.ida.vikbl327.drakborgen.bricks.SquareType;
import se.liu.ida.vikbl327.drakborgen.cards.CardGenerator;
import se.liu.ida.vikbl327.drakborgen.cards.ChestCard;
import se.liu.ida.vikbl327.drakborgen.cards.RoomCard;
import se.liu.ida.vikbl327.drakborgen.cards.RoomSearchCard;
import se.liu.ida.vikbl327.drakborgen.cards.TreasureCard;
import se.liu.ida.vikbl327.drakborgen.heroes.Character;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main user interface class of the game. This class displays the game on a Jframe, and also handles actions upon pressing any
 * of the buttons, or by clicking the mouse on the board. All user interaction such as combat, searching, looting etc.
 * happens in this class.
 */
public class GameViewer
{

    private static final int BRICK_SIZE = 60;
    private static final int TEXT_SIZE = 18;
    private static final int T12 = 12;
    private static final SquareType[] ACCEPTED_SQUARES = { SquareType.PATH, SquareType.UNDISCOVERED, SquareType.TREASURE };
    private static final BrickType[] EXCEPTIONBRICKS = { BrickType.TREASURE, BrickType.START };
    private static final Logger LOGGER = Logger.getLogger("User Interface Logger");

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
    private JButton roomSearchButton;
    private JButton leaveDungeonButton;
    private MouseInputAdapter mouseAdapter;

    private List<Player> players;
    private List<Action> allowedActions;
    private Random rnd;

    @Nullable private Point highLightedBrick;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private int sleepingDragonFactor;
    private boolean movedWithinTreasureRoom;


    public GameViewer(final Board gameBoard, final List<Player> players) {
	this.gameBoard = gameBoard;
	this.players = players;
	this.frame = new JFrame("Drakborgen");
	this.comp = new GameComponent(gameBoard, players);
	this.bgenerator = new BrickGenerator();
	this.cgenerator = new CardGenerator();
	this.highLightedBrick = null;
	this.allowedActions = new ArrayList<>();
	this.allowedActions.add(Action.MOVEHERO);
	this.currentPlayerIndex = 0;
	this.currentPlayer = players.get(0);
	this.currentHero = currentPlayer.getHero();
	this.sleepingDragonFactor = 8;
	this.rnd = new Random();

	gameBoard.addBoardListener(comp);

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
	Color backGround = new Color(92, 62, 10); //Magic numbers to generate desired background color.
	panel.setBackground(backGround);
	panel.add(currentHeroInfo);
	panel.add(brickButton);
	panel.add(roomButton);
	panel.add(treasureButton);
	panel.add(roomSearchButton);
	panel.add(leaveDungeonButton);
	panel.add(eventlog);


	updateHeroInfo();
	frame.setLayout(new GridLayout());
	frame.add(comp, BorderLayout.WEST);
	frame.add(panel, BorderLayout.EAST);
	frame.setJMenuBar(menuBar);
	frame.pack();
	frame.setVisible(true);

    }

    private void initializeMouseAdapter() {
	this.mouseAdapter = new MouseInputAdapter()
	{
	    @Override public void mousePressed(final MouseEvent e) {
		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK) {
		    if (!allowedActions.contains(Action.MOVEHERO)) {
			eventlog.setText("Du har redan förflyttat hjälten denna runda! \n");
			return;
		    }
		    int row = e.getY() / BRICK_SIZE;
		    int col = e.getX() / BRICK_SIZE;
		    if (decideDirection(row, col) == Direction.INVALID) eventlog.setText("Du kan bara flytta till en ruta med öppen väg innanför Drakborgen! \n");
		    else {
			if (highLightedBrick != null) {
			    if (highLightedBrick.y == row && highLightedBrick.x == col) {
				gameBoard.removeHighLight(row, col);
				highLightedBrick = null;
			    } else {
				gameBoard.highLight(row, col);
				gameBoard.removeHighLight(highLightedBrick.y, highLightedBrick.x);
				highLightedBrick.setLocation(col, row);
			    }
			} else {
			    gameBoard.highLight(row, col);
			    highLightedBrick = new Point();
			    highLightedBrick.setLocation(col, row);
			}

			if (gameBoard.getBrick(row, col).getType().equals(BrickType.UNDISCOVERED))
			    brickButton.setText("Dra rumsbricka");
			else brickButton.setText("Flytta hjälte");
		    }
		}
	    }
	};
    }

    private void initializeComponents() {

	this.eventlog = new JTextArea();
	eventlog.setFont(new Font("Helvetica", Font.PLAIN, TEXT_SIZE));
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
	this.leaveDungeonButton = new JButton("Lämna Drakborgen");
	this.roomSearchButton = new JButton("Dra rumsletningskort");
	brickButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	brickButton.setBackground(Color.WHITE);
	brickButton.addActionListener(new DrawBrickAction());
	roomButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	roomButton.setBackground(Color.WHITE);
	roomButton.addActionListener(new DrawRoomCardAction());
	treasureButton.setFont(new Font("Helvetica", Font.BOLD, TEXT_SIZE));
	treasureButton.setBackground(Color.WHITE);
	treasureButton.addActionListener(new DrawTreasureCardAction());
	roomSearchButton.setFont(new Font("Monospaced", Font.BOLD, TEXT_SIZE));
	roomSearchButton.setBackground(Color.WHITE);
	roomSearchButton.addActionListener(new DrawRoomSearchCardAction());
	leaveDungeonButton.setFont(new Font("Monospaced", Font.BOLD, TEXT_SIZE));
	leaveDungeonButton.setBackground(Color.WHITE);
	leaveDungeonButton.addActionListener(new LeaveGameAction());


    }

    private void addTextToEventLog(String text) {
	try {
	    if (eventlog.getLineCount() > 7) {
		int end = eventlog.getLineEndOffset(0);
		eventlog.replaceRange("", 0, end);
	    }
	} catch (BadLocationException e) {
	    LOGGER.log(Level.SEVERE, "Error found", e);
	    return;
	}
	eventlog.append(text);
    }

    private BrickType drawBrick() {
	BrickType type = bgenerator.generateBrick();
	addTextToEventLog("Den placerade brickan har öppning(ar) " + type + "\n");
	return type;
    }

    private void updateHeroInfo() {
	if (currentHero != null) {
	    currentHeroInfo.setText(
		    "Spelare: " + currentPlayer.getName() + "\n" + "Hjälte: " + currentHero.getName() + "\n" +
		    "Styrka (SF)  : " + currentHero.getStrengthFactor() + "\n" + "Vighet (VF)  : " +
		    currentHero.getAgilityFactor() + "\n" + "Rustning (RF): " + currentHero.getArmorFactor() + "\n" +
		    "Tur (TF):    : " + currentHero.getLuckFactor() + "\n" + "Kroppspoäng  : " +
		    currentHero.getCurrentHealth() + "/" + currentHero.getMaxHealthFactor() + "\n" + "Värde av skatter: " +
		    players.get(currentPlayerIndex).getTreasureValue());
	}
    }

    private Direction decideDirection(int row, int col) {
	if (row >= gameBoard.getHeight() || col >= gameBoard.getWidth()) return Direction.INVALID;
	int heroyPos = currentHero.getyPos();
	int heroxPos = currentHero.getxPos();
	Brick curBrick = gameBoard.getBrick(heroyPos, heroxPos);
	Brick nextBrick = gameBoard.getBrick(row, col);
	if (heroxPos == col - 1 && heroyPos == row && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(3, 5)) &&
	    ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(3, 0))) return Direction.RIGHT;
	else if (heroxPos == col + 1 && heroyPos == row && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(3, 0)) &&
		 ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(3, 5))) return Direction.LEFT;
	else if (heroxPos == col && heroyPos == row - 1 && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(5, 3)) &&
		 ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(0, 3))) return Direction.DOWN;
	else if (heroxPos == col && heroyPos == row + 1 && ArrayUtils.contains(ACCEPTED_SQUARES, curBrick.getSquare(0, 3)) &&
		 ArrayUtils.contains(ACCEPTED_SQUARES, nextBrick.getSquare(5, 3))) return Direction.UP;
	else return Direction.INVALID;
    }

    private void advanceTurn() {
	allowedActions.clear();
	allowedActions.add(Action.MOVEHERO);
	allowedActions.add(Action.DRAWROOMSEARCHCARD);

	boolean alivePlayers = false;
	boolean clearTreasureRoom = true;
	for (Player player : players) {
	    if (player.isAlive() && !alivePlayers) alivePlayers = true;
	    if (gameBoard.getBrick(player.getHero().getyPos(), player.getHero().getxPos()).getType().equals(BrickType.TREASURE))
	        clearTreasureRoom = false;

	}
	if (clearTreasureRoom) sleepingDragonFactor = 8;
	if (!alivePlayers) {
	    eventlog.setText("Alla spelare antingen döda eller lämnat");
	    allowedActions.clear();
	    decideWinner();
	}

	do {
	    if (currentPlayerIndex == players.size()-1) {
		comp.decrementSunTimer();
		if (comp.getSunTimer() == 0) decideWinner();
		currentPlayerIndex = 0;
	    } else currentPlayerIndex++;
	} while (!(players.get(currentPlayerIndex).isAlive()));

	currentPlayer = players.get(currentPlayerIndex);
	currentHero = currentPlayer.getHero();
	updateHeroInfo();
	BrickType curBrickType = gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType();
	if (curBrickType.equals(BrickType.TREASURE)) {
	    allowedActions.add(Action.DRAWTREASURECARD);
	}
	if (!curBrickType.equals(BrickType.START)) {
	    leaveDungeonButton.setVisible(false);
	}
	else leaveDungeonButton.setVisible(true);
	comp.repaint();
    }

    private void openChest(Random rnd, Object[] diceOption) {
	ChestCard card = cgenerator.drawChestCard();
	switch (card) {
	    case JEWELRY:
		int jewelryValue = TreasureCard.JEWELRY.getValue(rnd);
		JOptionPane.showMessageDialog(frame.getParent(),
					      "Kistan innehåller ett smycke värt " + jewelryValue + " guldmynt");
		currentPlayer.addTreasure(TreasureCard.JEWELRY, jewelryValue);
		break;
	    case TRAP:
		int choice = JOptionPane.showOptionDialog(
			frame.getParent(),"När du öppnar kistan aktiveras en fälla och du måste omedelbart slå T12-3-TF "
					  +"för att se hur mycket skada du tar", "Fälla!",
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, diceOption, diceOption[0]
		);
		if (choice == 0) {
		    int r = rnd.nextInt(T12) + 1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r - 3 - currentHero.getLuckFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),"Du lyckas inte undvika fällan utan tar " + damageTaken + " skada"
			);
			currentPlayer.checkHealth();
		    } else {
			JOptionPane.showMessageDialog(
				frame.getParent(),"Turligt nog missar fällan dina händer och du tar ingen skada"
			);
		    }
		}
		break;
	    default:
		JOptionPane.showMessageDialog(frame.getParent(), "Kistan är tom");
	}
    }


    private void handleRoomCard(@NotNull RoomCard card) {
	final Object[] options = { "Slå tärning" };
	int choice;
	switch (card) {
	    case JEWELRY:
		int jewelryValue = TreasureCard.JEWELRY.getValue(rnd);
		JOptionPane.showInternalMessageDialog(
			frame.getParent(),"Rummet innehåller ett smycke värt " + jewelryValue + " guldmynt",
			card.toString(), JOptionPane.INFORMATION_MESSAGE
		);
		currentPlayer.addTreasure(TreasureCard.JEWELRY, jewelryValue);
		break;

	    case SARCOPHAGUS:
		final Object[] chestOptions = { "Öppna kistan", "Ignorera kistan" };
		choice = JOptionPane.showOptionDialog(
			frame.getParent(), card + "Vill du öpnna den?", "Kista", JOptionPane.DEFAULT_OPTION,
			JOptionPane.INFORMATION_MESSAGE, null, chestOptions, null
		);
		if (choice == 0) {
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
				frame.getParent(),"Taket rasar ner över dig och du dör omedelbart. Du har förlorat"
			);
			currentHero.setCurrentHealth(0);
			currentPlayer.kill();
		    } else {
			JOptionPane.showMessageDialog(
				frame.getParent(), "Du lyckas undvika raset, och kan forsätta ditt äventyr"
			);
		    }
		}
		break;

	    case SPEARTRAP:
		Object[] plates = { "Vänstra plattan", "Mittersta plattan", "Högra plattan" };
		int correctPlate = rnd.nextInt(3);
		int platesPressed = 0;
		String msg = "Så snart du kliver in i rummet börjar de spjutförsedda väggarna sluta sig om dig. " +
			     "Du har dock en liten chans att överleva.\nPå golvet ser du tre stenplattor; en av dessa " +
			     "hejdar fällan och oskadliggör den, men du hinner bara trampa på två av plattorna.\n" +
			     "			    Välj en platta att trampa på.";
		do {
		    int chosenPlate = JOptionPane
			    .showOptionDialog(frame.getParent(), msg, card.toString(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
					      null, plates, null);
		    if (chosenPlate == correctPlate) {
			JOptionPane.showMessageDialog(frame.getParent(), "Du lyckas trampa på rätt platta och överlever");
			break;
		    } else {
			List<Object> list = new ArrayList<>(Arrays.asList(plates));
			list.remove(chosenPlate);
			plates = list.toArray();
			platesPressed++;
			msg = "Du valde fel platta och har bara en chans till på dig att trampa på rätt platta";
		    }
		} while (platesPressed < 2);
		if (platesPressed == 2) {
		    JOptionPane.showMessageDialog(frame.getParent(),
						  "Du valde återigen fel platta att trampa på, väggarna sluts omkring dig och ditt äventyr är över");
		    currentHero.setCurrentHealth(0);
		    currentPlayer.kill();
		}
		break;

	    case ARROWTRAP:
		choice = JOptionPane.showOptionDialog(
			frame.getParent(),
			"Plötsligt börjar pilar skjutas ut från väggarna och du tar skador motsvarande T12 - RF",
			card.toString(), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, options, options[0]
		);
		if (choice == 0) {
		    int r = rnd.nextInt(T12) + 1;
		    addTextToEventLog("Tärningen visar: " + r + "\n");
		    int damageTaken = r - currentHero.getArmorFactor();
		    if (damageTaken > 0) {
			currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
			JOptionPane.showMessageDialog(
				frame.getParent(),"Pilarna penetrerar din rustning och du tar " + damageTaken + " skada"
			);
			currentPlayer.checkHealth();
		    } else JOptionPane.showMessageDialog(
		    	frame.getParent(),"Pilarna lyckas inte penetrera din rustning, du tar ingen skada"
		    );
		}
		break;

	    case TROLLAMBUSH:
	    case SKELETONAMBUSH:
	    case ORCAMBUSH:
	        ambush(options, card);
	        if (currentPlayer.checkHealth()) battleWithMonster(card);
	        break;

	    case EMPTY:
		JOptionPane.showInternalMessageDialog(
			frame.getParent(), card.toString(), "Rumskort", JOptionPane.INFORMATION_MESSAGE
		);
		break;

	    default:
		JOptionPane.showInternalMessageDialog(
			frame.getParent(), "Du stöter på " + card + " och går till attack!","Rumskort",
			JOptionPane.INFORMATION_MESSAGE
		);
		battleWithMonster(card);
	}
    }

    private void battleWithMonster(RoomCard card) {
	int monsterHealth = decideMonsterAction(card);
	boolean monsterFlees = (monsterHealth == 0);

	if (monsterFlees) {
	    JOptionPane.showMessageDialog(frame.getParent(), "Monstret flyr");
	} else {
	    String startingMsg = "Du och monstret väljer en varsin attack. Reglerna som gäller är:\n " +
				 "A slår B, B slår C, C slår A";
	    String addedMsg = "";
	    JOptionPane.showMessageDialog(frame.getParent(), "Monstret möter din attack!");
	    while (monsterHealth > 0) {
		int choice = JOptionPane.showOptionDialog(
			frame.getParent(),addedMsg+startingMsg, "Strid med " + card + "!",
			JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, currentHero.getAttackOptions(), null
		);
		Attack monsterAttack = Attack.values()[rnd.nextInt(3)];
		Attack heroAttack = Attack.values()[choice];

		if (heroAttack.beats(monsterAttack)) {
		    int damageDone = choice == currentHero.getDoubleDamageAttackIndex() ? 2 : 1;
		    addedMsg = "Du valde attack " + heroAttack + " och monstret valde attack " + monsterAttack + "\n" +
				      "Din attack träffade och du gjorde " + damageDone + " skada\n";
		    monsterHealth -= damageDone;
		} else if (monsterAttack.beats(heroAttack)) {
		    addedMsg = "Du valde attack " + heroAttack + " och monstret valde attack " + monsterAttack + "\n" +
				      "Monstrets attack träffade och gjorde " + 1 + " skada\n";
		    currentHero.setCurrentHealth(currentHero.getCurrentHealth() - 1);
		} else {
		    addedMsg = "Du valde attack " + heroAttack + " och monstret valde attack " + monsterAttack + "\n" +
				      "Båda attacker träffade och gjorde " + 1 + " skada till vardera\n";
		    currentHero.setCurrentHealth(currentHero.getCurrentHealth() - 1);
		    monsterHealth--;
		}
		if (!currentPlayer.checkHealth()) {
		    JOptionPane.showInternalMessageDialog(
		    	frame.getParent(), "Du förlorade striden och dog av dina skador. Ditt äventyr är över",
			"Du dog", JOptionPane.INFORMATION_MESSAGE
		    );
		    return;
		}
		updateHeroInfo();
	    }
	    JOptionPane.showInternalMessageDialog(frame.getParent(), "Monstret dog och du pustar ut. Din runda är över",
						  "Strid över", JOptionPane.INFORMATION_MESSAGE);
	}
    }

    private int decideMonsterAction(@NotNull RoomCard card) {
	int escapeFactor = rnd.nextInt(T12);
	switch (card) {
	    case GOBLIN:
		return escapeFactor < 3 ? rnd.nextInt(3) + 1 : 0;
	    case TROLL:
		return escapeFactor < 5 ? rnd.nextInt(4) + 1 : 0;
	    case SKELETON:
		return escapeFactor < 8 ? rnd.nextInt(4) + 2 : 0;
	    case ORC:
		return escapeFactor < 8 ? rnd.nextInt(3) + 3 : 0;
	    case TWOORCS:
		return escapeFactor < 9 ? rnd.nextInt(4) + 3 : 0;
	    case TROLLAMBUSH:
		return rnd.nextInt(4) + 1;
	    case SKELETONAMBUSH:
		return rnd.nextInt(4) + 2;
	    case ORCAMBUSH:
		return rnd.nextInt(3) + 3;
	    default:
		return 0;
	}
    }

    private void ambush(Object[] options, @NotNull RoomCard card){
        String msg1, msg2;

        switch (card){
	    case SKELETONAMBUSH:
	        msg1 = "ett skelett";
	        msg2 = "Skelettet";
	        break;
	    case TROLLAMBUSH:
	        msg1 = "ett troll";
	        msg2 = "Trollet";
	        break;
	    case ORCAMBUSH:
	        msg1 = "en orch";
	        msg2 = "Orchen";
	        break;
	    default:
	        msg1 = "";
	        msg2 = "";
	}

	int choice = JOptionPane.showOptionDialog(
		frame.getParent(),"Du blir överfallen av " + msg1 + " ,du måste först slå T12-TF för att se" +
				  " hur mycket skada du tar av dess initiala hugg, innan du kan börja slåss",
		"Överfall!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (choice == 0) {
			    int r = rnd.nextInt(T12) + 1;
			    addTextToEventLog("Tärningen visar: " + r + "\n");
			    int damageTaken = r - currentHero.getLuckFactor();
			    if (damageTaken > 0) {
				currentHero.setCurrentHealth(currentHero.getCurrentHealth() - damageTaken);
				JOptionPane.showMessageDialog(
					frame.getParent(), msg2 + " hugger dig och du tar " + damageTaken + " skada");
			    }
			    else JOptionPane.showMessageDialog(frame.getParent(), msg2 + "s hugg missar dig");
			}
    }

    private void checkDragon() {
	int r = rnd.nextInt(sleepingDragonFactor);
	addTextToEventLog("Chans för draken att vakna är 1 på  " + sleepingDragonFactor);
	sleepingDragonFactor--;

	if (r > 0) {
	    JOptionPane.showInternalMessageDialog(
	    	frame.getParent(), "Draken sover vidare", "Draken", JOptionPane.INFORMATION_MESSAGE
	    );
	} else {
	    JOptionPane.showInternalMessageDialog(
	    	frame.getParent(),"Draken vaknar! Alla spelare i skattkammaren måste slå T12 för att se hur mycket" +
				  " skada de tar, därefter flyr de till slumpmässig upptäckt bricka utanför skattkammaren.\n" +
				  "Spelarna går miste om alla skatter de samlat in från skattkammaren",
		"Draken vaknar!", JOptionPane.INFORMATION_MESSAGE
	    );
	    List<Point> placesToFlee = checkOutsideTreasureRoom();
	    Point p = new Point(0, 0);
	    for (int i = 0; i < players.size(); i++) {
		Character hero = players.get(i).getHero();
		if (gameBoard.getBrick(hero.getyPos(), hero.getxPos()).getType().equals(BrickType.TREASURE)
		    && players.get(i).getPlayerStatus().equals("Alive")) {
		    int damageTaken = rnd.nextInt(T12);
		    hero.setCurrentHealth(hero.getCurrentHealth() - damageTaken);
		    addTextToEventLog("Spelare " + players.get(i).getName() + " tar " + damageTaken + " skada från draken \n");
		    players.get(i).removeTreasuresFromChamber();
		    if (!currentPlayer.checkHealth()) {
			addTextToEventLog(players.get(i).getName() + " dog");
		    }
		    if (!placesToFlee.isEmpty()) p = placesToFlee.remove(rnd.nextInt(placesToFlee.size()));

		    hero.setyPos(p.y);
		    hero.setxPos(p.x);
		}
	    }
	}
    }

    private List<Point> checkOutsideTreasureRoom() {
	List<Point> possibleLocations = new ArrayList<>();

	for (int i = 3; i < 6; i++) {
	    for (int j = 5; j < 7; j++) {
		Brick curBrick = gameBoard.getBrick(i, j);
		if (!(curBrick.getType().equals(BrickType.UNDISCOVERED) || curBrick.getType().equals(BrickType.TREASURE))) {
		    possibleLocations.add(new Point(j, i));
		    System.out.println("Rad: " + i + "Kolumn: " + j);
		}
	    }
	}
	return possibleLocations;
    }

    private void moveThroughHiddenDoor(@NotNull String direction) {
	int row = currentHero.getyPos();
	int col = currentHero.getxPos();

        switch (direction) {
	    case "Uppåt":
		if (gameBoard.getBrick(row - 1, col).getType().equals(BrickType.UNDISCOVERED)) {
		    BrickType type = drawBrick();
		    gameBoard.placeBrick(row - 1, col, type, Direction.UP);
		}
		currentHero.setyPos(row - 1);
		break;
	    case "Vänster":
		if (gameBoard.getBrick(row, col - 1).getType().equals(BrickType.UNDISCOVERED)) {
		    BrickType type = drawBrick();
		    gameBoard.placeBrick(row, col - 1, type, Direction.LEFT);
		}
		currentHero.setxPos(col - 1);
		break;
	    case "Höger":
		if (gameBoard.getBrick(row, col + 1).getType().equals(BrickType.UNDISCOVERED)) {
		    BrickType type = drawBrick();
		    gameBoard.placeBrick(row, col + 1, type, Direction.RIGHT);
		}
		currentHero.setxPos(col + 1);
		break;
	    default:
	        if (gameBoard.getBrick(row + 1, col).getType().equals(BrickType.UNDISCOVERED)) {
		    BrickType type = drawBrick();
		    gameBoard.placeBrick(row + 1, col, type, Direction.DOWN);
		}
		currentHero.setyPos(row + 1);
	}
	comp.repaint();
    }

    private void decideWinner() {
	Player winningPlayer = new Player();
	int winningPlayerTreasure = 0;

	for (Player player : players) {
	    if (player.getPlayerStatus().equals("Left")) {
	        int playerTreasure = player.getTreasureValue();
		if (playerTreasure> winningPlayerTreasure) {
		    winningPlayer = player;
		    winningPlayerTreasure = playerTreasure;
		}
	    }
	}
	if (winningPlayer.getName().equals("None")) {
	    JOptionPane.showInternalMessageDialog(
	    	frame.getParent(), "Ingen spelare lyckades ta sig ut ur Drakborgen vid liv!", "Spelet avgjort!",
		JOptionPane.INFORMATION_MESSAGE);
	}
	else {
	    JOptionPane.showInternalMessageDialog(
	    	frame.getParent(), "Vinnande spelare är " + winningPlayer.getName() + " som samlade in skatter till ett värde av " +
				   winningPlayerTreasure + " guldmynt!", "Spelet avgjort!", JOptionPane.INFORMATION_MESSAGE);
	}

	System.exit(0);
    }

    private class QuitAction extends AbstractAction
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(null, "Vill du avsluta spelet?", "Avslutar Drakborgen", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION) {

		System.exit(0);
	    }
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
	    if (gameBoard.getBrick(row, col).getType().equals(BrickType.UNDISCOVERED)) {
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

	    if (ArrayUtils.contains(EXCEPTIONBRICKS, curBrick) && !movedWithinTreasureRoom) {
		addTextToEventLog("Du behöver ej dra ett rumskort på den här rutan\n");
		if (curBrick.equals(BrickType.TREASURE)) {
		    allowedActions.add(Action.DRAWTREASURECARD);
		    allowedActions.remove(Action.MOVEHERO);
		} else advanceTurn();
	    } else if ((!movedWithinTreasureRoom)) {
		allowedActions.remove(Action.MOVEHERO);
		allowedActions.remove(Action.DRAWTREASURECARD);
		allowedActions.remove(Action.DRAWROOMSEARCHCARD);
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
	    if (gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType().equals(BrickType.TREASURE) &&
		allowedActions.contains(Action.DRAWTREASURECARD)) {
		for (int i = 0; i < 2; i++) {
		    TreasureCard card = cgenerator.drawTreasureCard();
		    int cardValue = card.getValue(rnd);
		    JOptionPane.showInternalMessageDialog(frame.getParent(), card + " till ett värde av " + cardValue,
							  "Skattkammarkort", JOptionPane.INFORMATION_MESSAGE);
		    currentPlayer.addTreasure(card, cardValue);
		}
		checkDragon();
		advanceTurn();
	    } else {
		addTextToEventLog("Du kan bara dra skattkammarkort när du befinner dig i skattkammaren");
	    }
	}
    }

    private class DrawRoomSearchCardAction extends AbstractAction
    {

	@Override public void actionPerformed(final ActionEvent actionEvent) {
	    if (!allowedActions.contains(Action.DRAWROOMSEARCHCARD)) {
		eventlog.setText("Du kan inte söka i rummet");
		return;
	    }

	    Brick curBrick = gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos());
	    List<Object> optionList = new ArrayList<>();

	    if (curBrick.getSquare(0, 3).equals(SquareType.WALL)) optionList.add("Uppåt");
	    if (curBrick.getSquare(3, 0).equals(SquareType.WALL)) optionList.add("Vänster");
	    if (curBrick.getSquare(3, 5).equals(SquareType.WALL)) optionList.add("Höger");
	    if (curBrick.getSquare(5, 3).equals(SquareType.WALL)) optionList.add("Nedåt");

	    if (optionList.isEmpty()) {
		eventlog.setText("Det finns ingen möjlig riktning att söka i");
		return;
	    }


	    Object[] options = optionList.toArray();
	    int chosenDirection = JOptionPane.showOptionDialog(
	    	frame.getParent(), "Välj den riktning du vill söka efter lönndörr i:", "Rumsletning",
		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null
	    );
	    RoomSearchCard card = cgenerator.drawRoomSearchCard();

	    switch (card) {
		case HIDDENDOOR:
		    moveThroughHiddenDoor(options[chosenDirection].toString());
		    JOptionPane.showInternalMessageDialog(
		    	frame.getParent(),"Du fann en löndörr och som du genast går igenom. Du hamnar i ett nytt rum " +
					  "och forsätter din runda", "Löndörr!", JOptionPane.INFORMATION_MESSAGE
		    );
		    allowedActions.remove(Action.MOVEHERO);
		    allowedActions.remove(Action.DRAWROOMSEARCHCARD);
		    if (gameBoard.getBrick(currentHero.getyPos(), currentHero.getxPos()).getType().equals(BrickType.TREASURE))
		    	allowedActions.add(Action.DRAWTREASURECARD);
		    else allowedActions.add(Action.DRAWROOMCARD);

		    break;
		case JEWELRY:
		    int jewelryValue = TreasureCard.JEWELRY.getValue(rnd);
		    currentPlayer.addTreasure(TreasureCard.JEWELRY, jewelryValue);
		    JOptionPane.showInternalMessageDialog(
		    	frame.getParent(),"Du fann ett smycke värt " + jewelryValue + " gömt i rummet",
			"Smycke!", JOptionPane.INFORMATION_MESSAGE
		    );
		    advanceTurn();
		    break;
		case AMBUSH:
		    final Object[] diceOptions = { "Slå tärning" };
		    JOptionPane.showInternalMessageDialog(
		    	frame.getParent(), "Du hittar inget i rummet, däremot finner ett skelett dig medan du letar och " +
					   "du blir överfallen", "Överrumpling", JOptionPane.INFORMATION_MESSAGE
		    );
		    ambush(diceOptions, RoomCard.SKELETONAMBUSH);
		    if (currentPlayer.checkHealth()) {
		        battleWithMonster(RoomCard.SKELETONAMBUSH);
		    }
		    advanceTurn();
		    break;
		default:
		    JOptionPane.showInternalMessageDialog(
		    	frame.getParent(), "Du hittar inget i rummet denna gång", "Tomt",
			JOptionPane.INFORMATION_MESSAGE
		    );
		    advanceTurn();
	    }
	}
    }
    private class LeaveGameAction extends AbstractAction
    {
        @Override public void actionPerformed(final ActionEvent actionEvent){
            currentPlayer.leaveGame();
            addTextToEventLog(currentPlayer.getName() + " lämnade drakborgen med skatter värda " + currentPlayer.getTreasureValue() +
			      " guldmynt");
            advanceTurn();
	}
    }
}



