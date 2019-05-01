package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class stores the representation of the board. Contains information on what bricks are on the board, and where heroes are
 * standing. Contains methods for placing new bricks on the board and for retrieving the SquareType of any given square.
 */
public class Board
{
    private Brick[][] bricks;
    private List<BoardListener> listeners;
    private Queue<Character> characters;

    private int height;
    private int width;
    private BrickMaker maker;

    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.maker = new BrickMaker();
	this.listeners = new ArrayList<>();
	this.characters = new LinkedList<>();
	this.bricks = new Brick[height][width];

	clearBoard();

    }


    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public Queue<Character> getCharacters() {
	return characters;
    }

    public Character getCharacter() {
	return characters.remove();
    }

    public Brick getBrick(int row, int col) {
	return bricks[row][col];
    }

    public SquareType getSquare(int row, int col){
        int r = row/6;
        int c = col/6;
        return bricks[r][c].getSquare(row % 6, col % 6);
    }


    private void notifyListeners(){
	for (BoardListener listener : listeners) {
	    listener.boardChanged();
	}
    }

    public void addBoardListener(BoardListener b){
        listeners.add(b);
    }

    public void addCharacter(Character hero){
        characters.add(hero);
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
	bricks[height/2][width/2] = maker.createBrick(BrickType.TREASURE);
	Brick brick = maker.createBrick(BrickType.TREASURE);
	brick = brick.rotateLeft();
	brick = brick.rotateLeft();
	brick.setTimesRotated(2);
	bricks[height/2-1][width/2] = brick;

	notifyListeners();
    }

    public void highLight(int row, int col){
        Brick brick = bricks[row][col];
        bricks[row][col] = brick.highlightBrick();
        notifyListeners();
    }

    public void removeHighLight(int row, int col){
        int rotateCount = bricks[row][col].getTimesRotated();
        Brick brick = maker.createBrick(bricks[row][col].getType());
        if (rotateCount < 0) {
            brick = brick.rotateRight();
	}
        else {
	    for (int i = 0; i < rotateCount; i++) {
		brick = brick.rotateLeft();
	    }
	}
	brick.setTimesRotated(rotateCount);
        bricks[row][col] = brick;
        notifyListeners();
    }

    public void placeBrick(int row, int col, BrickType type, Direction dir){
        Brick brick = maker.createBrick(type);
        switch (dir) {
	    case DOWN:
	        brick = brick.rotateRight();
	        brick.setTimesRotated(-1);
	        break;
	    case UP:
	        brick = brick.rotateLeft();
	        brick.setTimesRotated(1);
	        break;
	    case LEFT:
	        brick = brick.rotateLeft();
	        brick = brick.rotateLeft();
	        brick.setTimesRotated(2);
	        break;
	}

	bricks[row][col] = brick;
	notifyListeners();
    }
}
