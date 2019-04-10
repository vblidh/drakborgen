package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardGenerator
{

    private static final int [] AMOUNT_OF_ROOMCARDS = {30,4,3,3,3,2,5,2,2,2,1,2,2,5};

    private Random rnd;
    private List<RoomCard> roomCards;

    public CardGenerator() {
        this.rnd = new Random();
        this.roomCards = new ArrayList<>();
        initializeRoomCards();

        }

    private void initializeRoomCards(){
    	int k = 0;
    	for (RoomCard rc :  RoomCard.values()) {
            for (int i = 0; i < AMOUNT_OF_ROOMCARDS[k]; i++) {
                roomCards.add(rc);
            }
    	    k++;
            Collections.shuffle(roomCards, rnd);
    	}
    }

    public RoomCard drawRoomCard() {
        if (roomCards.isEmpty()) initializeRoomCards();
        int r = rnd.nextInt(roomCards.size());
        return roomCards.remove(r);
    }
}
