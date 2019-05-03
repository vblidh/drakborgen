package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.heroes.Aelfric;
import se.liu.ida.vikbl327.drakborgen.heroes.Bardor;
import se.liu.ida.vikbl327.drakborgen.heroes.Rohan;
import se.liu.ida.vikbl327.drakborgen.heroes.Sigier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Game
{
    private static final int BOARDWIDTH = 13;
    private static final int BOARDHEIGHT = 10;
    private static final List<int[]> STARTINGPOINTS = List.of(new int[]{0,0}, new int[]{0,12}, new int[]{9,0}, new int[]{9,12});

    private Game() {}

    public static void main(String[] args) {
	Board board = new Board(BOARDHEIGHT, BOARDWIDTH);
	Character hero = new Sigier();
	Player player1 = new Player("Balder", hero);
	board.addCharacter(hero);
	Character hero2 = new Aelfric();
	board.addCharacter(hero2);
	Player player2 = new Player("Freja", hero2);
	Character hero3 = new Bardor();
	board.addCharacter(hero3);
	Player player3 = new Player("Idun", hero3);
	Character hero4 = new Rohan();
	Player player4 = new Player("Hermod", hero4);
	List<Player> players = new ArrayList<>();
	board.addCharacter(hero4);
	players.add(player1);
	players.add(player2);
	players.add(player3);
	players.add(player4);

	Collections.shuffle(players);


	for (int i = 0; i < players.size(); i++) {
	    Character h = players.get(i).getHero();
	    h.setyPos(STARTINGPOINTS.get(i)[0]);
	    h.setxPos(STARTINGPOINTS.get(i)[1]);
	}

	GameViewer viewer = new GameViewer(board, players);
    }
}