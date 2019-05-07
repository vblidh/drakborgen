package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.heroes.Character;
import se.liu.ida.vikbl327.drakborgen.heroes.HeroFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * The runnable class of the project. Creates a board, new players based on user inputs and creates the graphical window that
 * the game is played on.
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
	List<int[]> startingPoints = new ArrayList<>(STARTINGPOINTS);
	List<Object> heroes = new ArrayList<>(Arrays.asList(HEROES));
	Collections.shuffle(startingPoints);
	HeroFactory factory = new HeroFactory();
	JFrame frame = new JFrame();

	String input = JOptionPane.showInputDialog(frame.getParent(),
		"Välkommen till Drakborgen, välj hur många som ska spela: (1-4 spelare möjliga)");
	boolean validInput = false;
	int numberOfPlayers = 0;

	while(!validInput) {
	    try {
		numberOfPlayers = Integer.parseInt(input);
		if (!(numberOfPlayers < 1 || numberOfPlayers > 4)) validInput = true;
		else input = JOptionPane.showInputDialog(frame.getParent(),"Vänligen skriv en siffra mellan 1 och 4");

	    } catch (NumberFormatException e) {
		input = JOptionPane.showInputDialog(frame.getParent(),"Vänligen skriv en siffra mellan 1 och 4");
	    }
	}
	List<Player> players = new ArrayList<>();

	for (int i = 0; i < numberOfPlayers; i++) {
	    input = JOptionPane.showInputDialog("Spelare "+(i+1)+", skriv ditt namn");

	    int choice = JOptionPane.showOptionDialog(
	    	frame.getParent(), input + ", välj vilken hjälte du vill spela", "Välj hjälte",
		JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, heroes.toArray(), null);

	    switch(heroes.get(choice).toString()){
		case "Sigier Skarpyxe":
		    players.add(new Player(input,factory.createHero("Sigier")));
		    break;
		case "Aelfric Brunkåpa":
		    players.add(new Player(input, factory.createHero("Aelfric")));
		    break;
		case "Riddar Rohan":
		    players.add(new Player(input, factory.createHero("Rohan")));
		    break;
		default:
		    players.add(new Player(input, factory.createHero("Bardor")));
	    }

	    heroes.remove(choice);
	}

	Collections.shuffle(players);

	for (int i = 0; i < players.size(); i++) {
	    Character h = players.get(i).getHero();
	    h.setyPos(startingPoints.get(i)[0]);
	    h.setxPos(startingPoints.get(i)[1]);
	}

	GameViewer viewer = new GameViewer(board, players);
    }
}