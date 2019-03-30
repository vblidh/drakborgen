package se.liu.ida.vikbl327.drakborgen;


public class BrickMaker
{
    private static final SquareType [] FULLPATH = { SquareType.PATH, SquareType.PATH, SquareType.PATH };
    private static final SquareType [] MIDOPEN = { SquareType.WALL, SquareType.PATH, SquareType.WALL };
    private static final SquareType [] TWOPATH = { SquareType.PATH, SquareType.PATH, SquareType.WALL };
    private static final SquareType [] WALL = {SquareType.WALL,SquareType.WALL,SquareType.WALL};

    public BrickMaker() {
    }

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

    private Brick createUndiscovered(){
        SquareType [][] temp = { WALL, WALL, WALL };
        return new Brick(temp);
    }
    private Brick createAhead() {
	SquareType[][] temp = {	WALL, FULLPATH, WALL };
	return new Brick(temp);
    }
    private Brick createLeft() {
	SquareType[][] temp = { MIDOPEN, TWOPATH, WALL };
	return new Brick(temp);
    }

    private Brick createRight(){
        SquareType [][] temp = { WALL, TWOPATH, MIDOPEN };
     	return new Brick(temp);
    }

    private Brick createAheadLeft(){
	SquareType [][] temp = {MIDOPEN, FULLPATH, WALL	};
	return new Brick(temp);
    }

    private Brick createAheadRight(){
	SquareType [][] temp = {WALL, FULLPATH, MIDOPEN	};
	return new Brick(temp);
    }

    private Brick createLeftRight(){
	SquareType [][] temp = { MIDOPEN, TWOPATH, MIDOPEN };
	return new Brick(temp);
    }
}
