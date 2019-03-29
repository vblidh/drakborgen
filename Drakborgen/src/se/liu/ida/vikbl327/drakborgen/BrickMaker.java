package se.liu.ida.vikbl327.drakborgen;

public class BrickMaker
{
    public int numberOfBrickTypes(){
        return BrickType.values().length;
    }

    public Brick createBrick(BrickType type){
        switch (type){
	    case UNDISCOVERED:
	        return createUndiscovered();
	    case AHEAD:
	        return createAhead();
	    case LEFT:
	        return createLeft();
	    case RIGHT:
	        return createRight();
	    case AHEADLEFT:
	        return createAheadLeft();
	    case AHEADRIGHT:
	        return createAheadRight();
	    case LEFTRIGHT:
	        return createLeftRight();
	    default:
	        return createUndiscovered();
	}
    }

    protected Brick createUndiscovered(){
        SquareType [][] temp = {
        	{SquareType.WALL,SquareType.WALL,SquareType.WALL},
		{SquareType.WALL,SquareType.WALL,SquareType.WALL},
		{SquareType.WALL,SquareType.WALL,SquareType.WALL}};
        return new Brick(temp);
    }
    protected Brick createAhead() {
	SquareType[][] temp = {
		{ SquareType.WALL, SquareType.WALL, SquareType.WALL },
		{ SquareType.PATH, SquareType.PATH, SquareType.PATH },
		{ SquareType.WALL, SquareType.WALL, SquareType.WALL }};
	return new Brick(temp);
    }
    protected Brick createLeft() {
	SquareType[][] temp = {
		{ SquareType.WALL, SquareType.PATH, SquareType.WALL },
		{ SquareType.PATH, SquareType.PATH, SquareType.WALL },
		{ SquareType.WALL, SquareType.WALL,SquareType.WALL } };
	return new Brick(temp);
    }

    protected Brick createRight(){
        SquareType [][] temp = {
        	{SquareType.WALL,SquareType.WALL,SquareType.WALL},
		{SquareType.PATH,SquareType.PATH,SquareType.WALL},
    		{SquareType.WALL,SquareType.PATH,SquareType.WALL}};
     	return new Brick(temp);
    }

    protected Brick createAheadLeft(){
	SquareType [][] temp = {
		{SquareType.WALL,SquareType.PATH,SquareType.WALL},
		{SquareType.PATH,SquareType.PATH,SquareType.PATH},
		{SquareType.WALL,SquareType.WALL, SquareType.WALL}};
	return new Brick(temp);
    }

    protected Brick createAheadRight(){
	SquareType [][] temp = {
		{SquareType.WALL,SquareType.WALL,SquareType.WALL},
		{SquareType.PATH,SquareType.PATH,SquareType.PATH},
		{SquareType.WALL,SquareType.PATH, SquareType.WALL} };
	return new Brick(temp);
    }

    protected Brick createLeftRight(){
	SquareType [][] temp =
		{{SquareType.WALL,SquareType.PATH,SquareType.WALL},
		{SquareType.PATH,SquareType.PATH,SquareType.WALL},
		{SquareType.WALL,SquareType.PATH, SquareType.WALL}};
	return new Brick(temp);
    }
}
