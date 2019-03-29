package se.liu.ida.vikbl327.drakborgen;

public class Brick
{
    private SquareType [][] squares;

    public Brick(final SquareType[][] squares) {
	this.squares = squares;
    }

    public SquareType getSquare(int row, int col) {
	return squares[row][col];
    }
}
