package se.liu.ida.vikbl327.drakborgen;

import static java.lang.Thread.sleep;

public class Game
{

    public static void main(String[] args) throws InterruptedException {
	Board board = new Board(10,13);
	GameViewer viewer = new GameViewer(board);
	board.addBoardListener(viewer.getComp());
	Character hero = new Character("Sigier Skarpyxe", 16,7,5,6,5);
	board.addCharacter(hero);
	viewer.getComp().setCurrentHero(hero);
	sleep(500);
	board.placeBrick(0, 1, BrickType.AHEAD, Direction.RIGHT);
	hero.setxPos(1);
	sleep(500);
	board.placeBrick(0, 2, BrickType.RIGHT, Direction.RIGHT);
	hero.setxPos(2);
	sleep(500);
	board.placeBrick(1, 2, BrickType.AHEADRIGHT, Direction.DOWN);
	hero.setyPos(1);
	sleep(500);
	board.placeBrick(1,1,BrickType.LEFT, Direction.LEFT);
	hero.setxPos(1);
	sleep(500);
	board.placeBrick(2,1,BrickType.AHEAD, Direction.DOWN);
	hero.setxPos(0);
	hero.setyPos(0);
    }
}
