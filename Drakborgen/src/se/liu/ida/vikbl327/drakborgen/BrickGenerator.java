package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Brickgenerator generates a shuffled list of bricktypes that can be drawn from by calling the generateBrick() method.
 */

public class BrickGenerator
{

    private static final int [] AMOUNTOFBRICKS = {10, 10, 10, 15, 15, 15, 15};


    private Random rnd;
    private ArrayList<BrickType> bricks;

    public BrickGenerator() {
	rnd = new Random();
	bricks = new ArrayList<>();
	initializeBrickList();
    }

    public BrickType generateBrick(){
	if (bricks.isEmpty()) initializeBrickList();
	int r = rnd.nextInt(bricks.size());
	BrickType type = bricks.remove(r);
	return type;
    }

    private void initializeBrickList(){

	BrickType [] placeableBricks = Arrays.copyOfRange(BrickType.values(), 3, BrickType.values().length);
	int k = 0;
	for (BrickType bt : placeableBricks) {
	    for (int i = 0; i < AMOUNTOFBRICKS[k]; i++) {
		bricks.add(bt);
	    }
	    k++;
	}

	Collections.shuffle(bricks, rnd);
    }
}
