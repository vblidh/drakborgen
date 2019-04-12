package se.liu.ida.vikbl327.drakborgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardGenerator
{

    private static final int [] AMOUNT_OF_ROOMCARDS = {30,4,3,3,3,2,5,2,2,2,1,2,2,5};
    private static final int [] AMOUNT_OF_CHESTCARDS = {8,5,3};
    private static final int [] AMOUNT_OF_TREASURECARDS = {15,2,3,3,1,1};

    private Random rnd;
    private List<RoomCard> roomCards;
    private List<ChestCard> chestCards;
    private List<TreasureCard> treasureCards;

    public CardGenerator() {
        this.rnd = new Random();
        this.roomCards = new ArrayList<>();
        this.chestCards = new ArrayList<>();
        this.treasureCards = new ArrayList<>();
        initializeRoomCards();
        initializeChestCards();
        initializeTreasureCards();

        }

    private void initializeRoomCards(){
    	int k = 0;
    	for (RoomCard rc :  RoomCard.values()) {
            for (int i = 0; i < AMOUNT_OF_ROOMCARDS[k]; i++) {
                roomCards.add(rc);
            }
    	    k++;
    	}
	Collections.shuffle(roomCards, rnd);
    }
    private void initializeChestCards(){
    	int k = 0;
    	for (ChestCard cc :  ChestCard.values()) {
            for (int i = 0; i < AMOUNT_OF_CHESTCARDS[k]; i++) {
                chestCards.add(cc);
            }
    	    k++;
    	}
	Collections.shuffle(chestCards, rnd);
    }

    private void initializeTreasureCards(){
        int k = 0;
        for (TreasureCard tc : TreasureCard.values()) {
            for (int i = 0; i < AMOUNT_OF_TREASURECARDS[k]; i++) {
                treasureCards.add(tc);
            }
            k++;
        }
        Collections.shuffle(treasureCards, rnd);
}

    public RoomCard drawRoomCard() {
        if (roomCards.isEmpty()) initializeRoomCards();
        int r = rnd.nextInt(roomCards.size());
        return roomCards.remove(r);
    }

    public ChestCard drawChestCard(){
        if (chestCards.isEmpty()) initializeChestCards();
        int r = rnd.nextInt(chestCards.size());
        return chestCards.remove(r);
    }

    public TreasureCard drawTreasureCard(){
        if (treasureCards.isEmpty()) initializeTreasureCards();
        int r = rnd.nextInt(treasureCards.size());
        return treasureCards.remove(r);
    }
}
