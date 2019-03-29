package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    private BrickType [][] bricks;
    private SquareType [][] squares;
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
	this.bricks = new BrickType[height][width];
	this.squares = new SquareType[height*3][width*3];

	clearBoard();
	clearSquares();
	setStartSquares();
	setTreasureSquares();

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
        return squares[row][col];
    }

    public BrickType getBrick(int row, int col) {return bricks[row][col]; }

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
		this.bricks[row][col] = BrickType.UNDISCOVERED;
	    }
	}
	bricks[0][0] = BrickType.START;
	bricks[0][width-1] = BrickType.START;
	bricks[height-1][0] = BrickType.START;
	bricks[height-1][width-1] = BrickType.START;
	bricks[height/2][width/2-1] = BrickType.TREASURE;
	bricks[height/2][width/2] = BrickType.TREASURE;
    }

    public void clearSquares(){
	for (int i = 0; i < height*3 ; i++) {
	    for (int j = 0; j < width*3; j++) {
		squares[i][j] = SquareType.UNDISCOVERD;
	    }
	}
    }

    public void setStartSquares(){
	for (int i = 0; i < 3; i++) {
	    for (int j = 0; j < 3; j++) {
		squares[i][j] = SquareType.START;
		squares[height*3-1-i][j] = SquareType.START;
		squares[i][width*3-1-j] = SquareType.START;
		squares[height*3-1-i][width*3-1-j] = SquareType.START;
	    }
	}
    }

    public void setTreasureSquares(){
	for (int i = 12; i < 18; i++) {
	    for (int j = 18; j < 21; j++) {
		squares[i][j] = SquareType.TREASURE;
	    }
	}
    }

    public void placeBrick(int row, int col, BrickType type){
        bricks[row][col] = type;
        Brick brick = maker.createBrick(type);
        int r = row*3;
        int c = col*3;
	for (int i = r; i < r+3; i++) {
	    for (int j = c; j < c+3; j++) {
		squares[i][j] = brick.getSquare(i-row*3,j-col*3);
	    }
	}
	notifyListeners();
    }
}
