package se.liu.ida.vikbl327.drakborgen;

/**
 *
 */

public class BrickMaker
{
    private static final SquareType [] FULLPATH = { SquareType.PATH, SquareType.PATH, SquareType.PATH,
	    SquareType.PATH,SquareType.PATH,SquareType.PATH};
    private static final SquareType [] MIDOPEN = { SquareType.WALL,SquareType.WALL, SquareType.PATH,
	    SquareType.PATH, SquareType.WALL, SquareType.WALL };
    private static final SquareType [] TWOPATH = { SquareType.PATH,SquareType.PATH, SquareType.PATH,
	    SquareType.PATH, SquareType.PATH, SquareType.WALL };
    private static final SquareType [] WALL = {SquareType.WALL,SquareType.WALL,SquareType.WALL,
	    SquareType.WALL,SquareType.WALL,SquareType.WALL};
    private static final SquareType [] UNDISCOVERED = {SquareType.UNDISCOVERED,SquareType.UNDISCOVERED,SquareType.UNDISCOVERED,
	    SquareType.UNDISCOVERED,SquareType.UNDISCOVERED,SquareType.UNDISCOVERED };
    private static final SquareType [] ROOM = {SquareType.WALL, SquareType.PATH,SquareType.PATH,
	    SquareType.PATH,SquareType.PATH, SquareType.WALL};
    private static final SquareType [] START = {SquareType.START,SquareType.START, SquareType.START,
	    SquareType.START,SquareType.START,SquareType.START};
    private static final SquareType [] TREASURE = {SquareType.TREASURE,SquareType.TREASURE,SquareType.TREASURE,
	    SquareType.TREASURE,SquareType.TREASURE,SquareType.TREASURE};

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
	    case START:
	        return createStart();
	    case TREASURE:
	        return createTreasure();
	    default:
	        return createUndiscovered();
	}
    }

    private Brick createUndiscovered(){
        SquareType [][] temp = { UNDISCOVERED,UNDISCOVERED,UNDISCOVERED,UNDISCOVERED,UNDISCOVERED,UNDISCOVERED};
        return new Brick(temp, BrickType.UNDISCOVERED);
    }
    private Brick createAhead() {
	SquareType[][] temp = {	WALL, ROOM, FULLPATH, FULLPATH, ROOM, WALL};
	return new Brick(temp, BrickType.AHEAD);
    }
    private Brick createLeft() {
	SquareType[][] temp = { MIDOPEN, ROOM, TWOPATH, TWOPATH, ROOM, WALL };
	return new Brick(temp, BrickType.LEFT);
    }

    private Brick createRight(){
        SquareType [][] temp = { WALL, ROOM, TWOPATH, TWOPATH, ROOM, MIDOPEN };
     	return new Brick(temp, BrickType.RIGHT);
    }

    private Brick createAheadLeft(){
	SquareType [][] temp = {MIDOPEN, ROOM, FULLPATH, FULLPATH, ROOM, WALL };
	return new Brick(temp, BrickType.AHEADLEFT);
    }

    private Brick createAheadRight(){
	SquareType [][] temp = {WALL, ROOM, FULLPATH, FULLPATH, ROOM, MIDOPEN };
	return new Brick(temp, BrickType.AHEADRIGHT);
    }

    private Brick createLeftRight(){
	SquareType [][] temp = { MIDOPEN, ROOM, ROOM, ROOM, ROOM, MIDOPEN };
	return new Brick(temp, BrickType.LEFTRIGHT);
    }

    private Brick createStart() {
	SquareType [][] temp = { START, START, START, START, START, START};
	return new Brick(temp, BrickType.START);
    }
    private Brick createTreasure(){
	SquareType [][] temp = { TREASURE, TREASURE, TREASURE, TREASURE, TREASURE, TREASURE};
	return new Brick(temp, BrickType.TREASURE);
    }
}
