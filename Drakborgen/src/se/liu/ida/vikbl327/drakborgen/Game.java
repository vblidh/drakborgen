package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.heroes.Aelfric;
import se.liu.ida.vikbl327.drakborgen.heroes.Sigier;

import java.util.ArrayList;
import java.util.List;


public final class Game
{
    private static final int BOARDWIDTH = 13;
    private static final int BOARDHEIGHT = 10;

    private Game() {}

    public static void main(String[] args) {
	Board board = new Board(BOARDHEIGHT, BOARDWIDTH);
	Character hero = new Sigier();
	Player player1 = new Player("Balder", hero);

	board.addCharacter(hero);
	Character hero2 = new Aelfric();
	board.addCharacter(hero2);
	Player player2 = new Player("Freja", hero2);
	List<Player> players = new ArrayList<>();
	players.add(player1);
	players.add(player2);
	hero.setyPos(3);
	hero.setxPos(5);
	hero2.setyPos(4);
	hero2.setxPos(4);

	GameViewer viewer = new GameViewer(board, players);
    }
}