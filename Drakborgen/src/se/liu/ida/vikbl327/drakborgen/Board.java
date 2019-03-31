package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    private Brick[][] bricks;
    private List<BoardListener> listeners;
    private List<Character> characters;

    private int height;
    private int width;
    private BrickMaker maker;

    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.maker = new BrickMaker();
	this.listeners = new ArrayList<>();
	this.characters = new ArrayList<>();
	this.bricks = new Brick[height][width];

	clearBoard();

    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public List<Character> getCharacters() {
	return characters;
    }

    public SquareType getSquare(int row, int col){
        int r = row/6;
        int c = col/6;
        return bricks[r][c].getSquare(row % 6, col % 6);
    }


    public void notifyListeners(){
	for (BoardListener listener : listeners) {
	    listener.boardChanged();
	}
    }

    public void addBoardListener(BoardListener b){
        listeners.add(b);
    }

    public void addCharacter(Character hero){
        characters.add(hero);
        hero.setxPos(0);
        hero.setyPos(0);
        notifyListeners();
    }

    public void clearBoard(){
	for (int row = 0; row < height; row++) {
	    for (int col = 0; col < width; col++) {
		this.bricks[row][col] = maker.createBrick(BrickType.UNDISCOVERED);
	    }
	}
	bricks[0][0] = maker.createBrick(BrickType.START);
	bricks[0][width-1] = maker.createBrick(BrickType.START);
	bricks[height-1][0] = maker.createBrick(BrickType.START);
	bricks[height-1][width-1] = maker.createBrick(BrickType.START);
	bricks[height/2][width/2-1] = maker.createBrick(BrickType.TREASURE);
	bricks[height/2-1][width/2-1] = maker.createBrick(BrickType.TREASURE);
    }






    public void placeBrick(int row, int col, BrickType type, Direction dir){
        Brick brick = maker.createBrick(type);
        switch (dir) {
	    case DOWN:
	        brick = brick.rotateRight();
	        break;
	    case UP:
	        brick = brick.rotateLeft();
	        break;
	    case LEFT:
	        brick = brick.rotateLeft();
	        brick = brick.rotateLeft();
	        break;
	}
	bricks[row][col] = brick;

	notifyListeners();
    }
}
