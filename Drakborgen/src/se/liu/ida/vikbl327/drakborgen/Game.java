package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.heroes.Sigier;

import javax.swing.text.BadLocationException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public final class Game
{

    private Game() {}

    public static void main(String[] args) throws InterruptedException, BadLocationException {
	Board board = new Board(10,13);
	Character hero = new Sigier();
	Player player1 = new Player("Balder", hero);

	board.addCharacter(hero);
	Character hero2 = new Sigier();
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



/*
	sleep(500);
	board.placeBrick(0, 1, viewer.drawBrick(), Direction.RIGHT);
	hero.setxPos(1);
	sleep(500);
	board.placeBrick(0, 2, viewer.drawBrick(), Direction.RIGHT);
	hero.setxPos(2);
	sleep(500);
	board.placeBrick(1, 2, viewer.drawBrick(), Direction.DOWN);
	hero.setyPos(1);
	sleep(500);
	board.placeBrick(1, 1, viewer.drawBrick(), Direction.LEFT);
	hero.setxPos(1);
	sleep(500);
	board.placeBrick(2, 1, viewer.drawBrick(), Direction.DOWN);
	hero.setxPos(0);
	hero.setyPos(0);*/
    }
}
