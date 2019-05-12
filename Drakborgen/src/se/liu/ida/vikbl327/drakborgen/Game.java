package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.heroes.Character;
import se.liu.ida.vikbl327.drakborgen.heroes.HeroFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The runnable class of the project. Creates a board, new players based on user inputs and then creates a GameViewer object
 * that the game is played on.
 */
public final class Game
{
    private Game() {
    }

    private static final int BOARDWIDTH = 13;
    private static final int BOARDHEIGHT = 10;
    private static final List<int[]> STARTINGPOINTS = List.of(new int[]{0,0}, new int[]{0,12}, new int[]{9,0}, new int[]{9,12});
    private static final Object[] HEROES = {"Sigier Skarpyxe", "Aelfric Brunkåpa", "Riddar Rohan", "Bardor Bågman"};


    public static void main(String[] args) {
	Board board = new Board(BOARDHEIGHT, BOARDWIDTH);
	Logger logger = Logger.getLogger("User input logger");
	List<int[]> startingPoints = new ArrayList<>(STARTINGPOINTS);
	List<Object> heroes = new ArrayList<>(Arrays.asList(HEROES));
	Collections.shuffle(startingPoints);
	HeroFactory factory = new HeroFactory();
	JFrame frame = new JFrame();
	logger.info(logger.getName());
	JPanel panel = new JPanel();
	JLabel label = new JLabel("Välkommen till Drakborgen, välj hur många som ska spela: (1-4 spelare möjliga)");
	JTextField field = new JTextField(5);
	panel.add(label);
	panel.add(field);


	boolean validInput = false;
	int numberOfPlayers = 0;
	Object [] options = {"Ok"};

	while(!validInput) {
	    String input = "";
	    int choice = JOptionPane.showOptionDialog(
	    	frame.getParent(),panel, "Välj antal spelare",
		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    if (choice == 0) input = field.getText();

	    try {
		numberOfPlayers = Integer.parseInt(input);
		if ((numberOfPlayers > 1 && numberOfPlayers < 5)) validInput = true;
	    } catch (NumberFormatException e) {
	        logger.log(Level.SEVERE, "Error found: ", e);
	        label.setText("Vänligen skriv en siffra mellan 1 och 4");
	    }
	}
	List<Player> players = new ArrayList<>();


	for (int i = 0; i < numberOfPlayers; i++) {
	    String input = JOptionPane.showInputDialog("Spelare " + (i + 1) + ", skriv ditt namn");

	    int choice = JOptionPane.showOptionDialog(
	    	frame.getParent(), input + ", välj vilken hjälte du vill spela", "Välj hjälte",
		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, heroes.toArray(), null);


	    Character hero = factory.createHero(heroes.get(choice).toString());
	    players.add(new Player(input, hero));
	    heroes.remove(choice);
	}

	Collections.shuffle(players);

	for (int i = 0; i < players.size(); i++) {
	    Character h = players.get(i).getHero();
	    h.setyPos(startingPoints.get(i)[0]);
	    h.setxPos(startingPoints.get(i)[1]);
	}

	frame.dispose();
	GameViewer viewer = new GameViewer(board, players);
    }
}