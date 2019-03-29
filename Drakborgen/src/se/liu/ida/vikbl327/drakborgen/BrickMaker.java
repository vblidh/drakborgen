package se.liu.ida.vikbl327.drakborgen;

public class BrickMaker
{
    public int numberOfBrickTypes(){
        return BrickType.values().length;
    }

    public Brick createBrick(int n){
        Brick brick;
        switch (BrickType.values()[n]){
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
        	{SquareType.BLACK,SquareType.BLACK,SquareType.BLACK},
		{SquareType.BLACK,SquareType.BLACK,SquareType.BLACK},
		{SquareType.BLACK,SquareType.BLACK,SquareType.BLACK}};
        return new Brick(temp);
    }
    protected Brick createAhead() {
	SquareType[][] temp = {
		{ SquareType.BLACK, SquareType.BLACK, SquareType.BLACK },
		{ SquareType.GREY, SquareType.GREY, SquareType.GREY },
		{ SquareType.BLACK, SquareType.BLACK, SquareType.BLACK }};
	return new Brick(temp);
    }
    protected Brick createLeft() {
	SquareType[][] temp = {
		{ SquareType.BLACK, SquareType.GREY, SquareType.BLACK },
		{ SquareType.GREY, SquareType.GREY, SquareType.BLACK },
		{ SquareType.BLACK, SquareType.BLACK,SquareType.BLACK } };
	return new Brick(temp);
    }

    protected Brick createRight(){
        SquareType [][] temp = {
        	{SquareType.BLACK,SquareType.BLACK,SquareType.BLACK},
		{SquareType.GREY,SquareType.GREY,SquareType.BLACK},
    		{SquareType.BLACK,SquareType.GREY,SquareType.BLACK}};
     	return new Brick(temp);
    }

    protected Brick createAheadLeft(){
	SquareType [][] temp = {
		{SquareType.BLACK,SquareType.GREY,SquareType.BLACK},
		{SquareType.GREY,SquareType.GREY,SquareType.GREY},
		{SquareType.BLACK,SquareType.BLACK, SquareType.BLACK}};
	return new Brick(temp);
    }

    protected Brick createAheadRight(){
	SquareType [][] temp = {
		{SquareType.BLACK,SquareType.BLACK,SquareType.BLACK},
		{SquareType.GREY,SquareType.GREY,SquareType.GREY},
		{SquareType.BLACK,SquareType.GREY, SquareType.BLACK} };
	return new Brick(temp);
    }

    protected Brick createLeftRight(){
	SquareType [][] temp =
		{{SquareType.BLACK,SquareType.GREY,SquareType.BLACK},
		{SquareType.GREY,SquareType.GREY,SquareType.BLACK},
		{SquareType.BLACK,SquareType.GREY, SquareType.BLACK}};
	return new Brick(temp);
    }
}
