package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardGenerator
{
    /*private static final int EMPTY = 30;
    private static final int GOBLINS = 4;
    private static final int TROLLS = 3;
    private static final int SKELETONS = 3;*/

    private static final int [] AMOUNT_OF_ROOMCARDS = {30,4,3,3,3,2,5};

    private Random rnd;
    private ArrayList<RoomCard> roomCards;

    public CardGenerator() {
        this.rnd = new Random();
        this.roomCards = new ArrayList<>();
        Collections.addAll(roomCards, RoomCard.values());

        for (RoomCard card : roomCards){
            System.out.println(card);
        }
    }
}
