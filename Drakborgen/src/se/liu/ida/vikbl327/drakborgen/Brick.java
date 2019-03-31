package se.liu.ida.vikbl327.drakborgen;

public class Brick
{
    private SquareType [][] squares;
    private BrickType type;

    public Brick(final SquareType[][] squares, BrickType type) {
	this.squares = squares;
	this.type = type;
    }

    public BrickType getType() {
	return type;
    }

    public SquareType getSquare(int row, int col) {
	return squares[row][col];
    }

    public Brick rotateRight() {
        int r = this.squares.length;
        int c = this.squares[0].length;

        Brick newBrick = new Brick(new SquareType[r][c], this.type);

	for (int i = 0; i < r; i++) {
	    for (int j = 0; j < c; j++) {
		newBrick.squares[j][r-1-i] = this.squares[i][j];
	    }
	}
	return newBrick;
    }
    public Brick rotateLeft() {
	int r = this.squares.length;
	int c = this.squares[0].length;

	Brick newBrick = new Brick(new SquareType[r][c], this.type);

    	for (int i = 0; i < r; i++) {
		for (int j = 0; j < c; j++) {
		    newBrick.squares[r-1-j][i] = this.squares[i][j];
		}
    	}
    	return newBrick;
        }
}
