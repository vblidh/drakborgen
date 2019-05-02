package se.liu.ida.vikbl327.drakborgen;

/**
 * Brick class representing the bricks shown on the board. The board consists of 13*10 Bricks, and each Brick contains 6x6
 * squares which defines its appearance.
 */
public class Brick
{
    private SquareType[][] brickSquares;
    private BrickType type;
    private int timesRotated;

    public Brick(final SquareType[][] squares, BrickType type) {
	this.brickSquares = squares;
	this.type = type;
	this.timesRotated = 0;
    }

    public BrickType getType() {
	return type;
    }

    public int getTimesRotated() {
	return timesRotated;
    }

    public void setTimesRotated(final int timesRotated) {
	this.timesRotated = timesRotated;
    }

    public SquareType getSquare(int row, int col) {
	return brickSquares[row][col];
    }

    public Brick rotateRight() {
	int r = this.brickSquares.length;
	int c = this.brickSquares[0].length;

	Brick newBrick = new Brick(new SquareType[r][c], this.type);

	for (int i = 0; i < r; i++) {
	    for (int j = 0; j < c; j++) {
		newBrick.brickSquares[j][r - 1 - i] = this.brickSquares[i][j];
	    }
	}
	return newBrick;
    }

    public Brick rotateLeft() {
	int r = this.brickSquares.length;
	int c = this.brickSquares[0].length;

	Brick newBrick = new Brick(new SquareType[r][c], this.type);

	for (int i = 0; i < r; i++) {
	    for (int j = 0; j < c; j++) {
		newBrick.brickSquares[r - 1 - j][i] = this.brickSquares[i][j];
	    }
	}
	return newBrick;
    }

    public Brick highlightBrick(){
	int row = this.brickSquares.length;
	int col = this.brickSquares[0].length;

	Brick newBrick = new Brick(new SquareType[row][col], this.type);

	for (int i = 0; i < row; i++) {
	    for (int j = 0; j < col; j++) {
		if ((i == 0 || i == row-1) || (j == 0 || j == col-1)) newBrick.brickSquares[i][j] = this.brickSquares[i][j];
		else newBrick.brickSquares[i][j] = SquareType.HIGHLIGHTED;
	    }
	}
	newBrick.timesRotated = this.timesRotated;
	return newBrick;
    }

}