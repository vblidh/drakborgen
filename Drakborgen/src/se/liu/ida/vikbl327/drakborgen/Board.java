package se.liu.ida.vikbl327.drakborgen;

public class Board
{
    private BrickType [][] bricks;
    private SquareType [][] squares;

    private int height;
    private int width;

    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.bricks = new BrickType[height][width];
	this.squares = new SquareType[height*3][width*3];

	clearBoard();
	clearSquares();

    }

    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public SquareType getSquare(int row, int col){
        return squares[row][col];
    }

    public BrickType getBrick(int row, int col) {return bricks[row][col]; }

    public void clearBoard(){
	for (int row = 0; row < height; row++) {
	    for (int col = 0; col < width; col++) {
		this.bricks[row][col] = BrickType.UNDISCOVERED;
	    }
	}
    }

    public void clearSquares(){
	for (int i = 0; i < height*3 ; i++) {
	    for (int j = 0; j < width*3; j++) {
		squares[i][j] = SquareType.BLACK;
	    }
	}
    }
}
