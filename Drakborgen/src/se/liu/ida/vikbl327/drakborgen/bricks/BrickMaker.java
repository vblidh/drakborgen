package se.liu.ida.vikbl327.drakborgen.bricks;

/**
 * This class handles the creation of bricks. The method createBrick(BrickType type) creates a brick with the appearance
 * correlating with the given type.
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
    private static final SquareType [] START = {SquareType.WALL,SquareType.START, SquareType.START,
	    SquareType.START,SquareType.START,SquareType.WALL};
    private static final SquareType [] STARTOPENSIDES = {SquareType.PATH,SquareType.START, SquareType.START,
	    SquareType.START,SquareType.START,SquareType.PATH};
    private static final SquareType [] STARTOPENMID = {SquareType.WALL, SquareType.WALL, SquareType.PATH,
	    SquareType.PATH, SquareType.WALL, SquareType.WALL};
    private static final SquareType [] TREASURE = {SquareType.WALL,SquareType.TREASURE,SquareType.TREASURE,
	    SquareType.TREASURE,SquareType.TREASURE,SquareType.WALL};
    private static final SquareType [] TREASUREOPENSIDES = {SquareType.PATH,SquareType.TREASURE,SquareType.TREASURE,
	    SquareType.TREASURE,SquareType.TREASURE,SquareType.PATH};

    public BrickMaker() {
    }

    public Brick createBrick(BrickType type){
        switch (type){
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
	    case FOURWAY:
	        return createFourWay();
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
	SquareType [][] temp = { MIDOPEN, ROOM, TWOPATH, TWOPATH, ROOM, MIDOPEN };
	return new Brick(temp, BrickType.LEFTRIGHT);
    }

    private Brick createStart() {
	SquareType [][] temp = { STARTOPENMID, START, STARTOPENSIDES, STARTOPENSIDES, START, STARTOPENMID};
	return new Brick(temp, BrickType.START);
    }

    private Brick createTreasure(){
	SquareType [][] temp = { TREASURE, TREASURE, TREASUREOPENSIDES, TREASUREOPENSIDES, TREASURE, MIDOPEN};
	return new Brick(temp, BrickType.TREASURE);
    }

    private Brick createFourWay(){
        SquareType [][] temp = { MIDOPEN, ROOM, FULLPATH, FULLPATH, ROOM, MIDOPEN};
        return new Brick(temp, BrickType.FOURWAY);
    }
}
