package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.Heroes.Sigier;

import javax.swing.text.BadLocationException;

import static java.lang.Thread.sleep;

public class Game
{

    public static void main(String[] args) throws InterruptedException, BadLocationException {
	Board board = new Board(10,13);
	GameViewer viewer = new GameViewer(board);
	board.addBoardListener(viewer.getComp());
	Character hero = new Sigier();
	board.addCharacter(hero);
	viewer.setCurrentHero(hero);
	viewer.updateHeroInfo();

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
