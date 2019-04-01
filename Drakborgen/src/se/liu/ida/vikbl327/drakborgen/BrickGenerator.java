package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BrickGenerator
{
    private static final int BRICKS_AT_START = 80;
    private static final int AHEAD_AT_START = 20;
    private static final int LEFT_AT_START = 15;
    private static final int RIGHT_AT_START = 15;
    private static final int AHEADLEFT_AT_START = 10;
    private static final int AHEADRIGHT_AT_START = 10;
    private static final int LEFTRIGHT_AT_START = 10;

    private int numberOfBricks;

    private Random rnd;
    private ArrayList<BrickType> bricks;

    public BrickGenerator() {
        this.numberOfBricks = BRICKS_AT_START;
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
        for (int i = 0; i < AHEAD_AT_START; i++) {
            if (i < AHEADLEFT_AT_START){
                bricks.add(BrickType.AHEADLEFT);
                bricks.add(BrickType.AHEADRIGHT);
                bricks.add(BrickType.LEFTRIGHT);
                bricks.add(BrickType.LEFT);
                bricks.add(BrickType.RIGHT);
            }

            else if (i < LEFT_AT_START) {
                bricks.add(BrickType.LEFT);
                bricks.add(BrickType.RIGHT);
            }
            bricks.add(BrickType.AHEAD);
        }
        Collections.shuffle(bricks, rnd);
    }
}
