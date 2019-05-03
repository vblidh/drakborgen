package se.liu.ida.vikbl327.drakborgen;

import se.liu.ida.vikbl327.drakborgen.cards.ChestCard;
import se.liu.ida.vikbl327.drakborgen.cards.RoomCard;
import se.liu.ida.vikbl327.drakborgen.cards.RoomSearchCard;
import se.liu.ida.vikbl327.drakborgen.cards.TreasureCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class handles the cards that a player can draw during the game. Creates different stacks of cards when created, which
 * can be drawn from with corresponding draw method.
*/
public class CardGenerator
{

    private static final int [] AMOUNT_OF_ROOMCARDS = {30,4,3,3,3,2,5,2,2,2,1,2,2,5};
    private static final int [] AMOUNT_OF_CHESTCARDS = {8,5,3};
    private static final int [] AMOUNT_OF_TREASURECARDS = {0,15,2,3,3,2,2,1,1,1};
    private static final int [] AMOUNT_OF_ROOMSEARCHCARDS = {6,5,3,2};

    private Random rnd;
    private List<RoomCard> roomCards;
    private List<ChestCard> chestCards;
    private List<TreasureCard> treasureCards;
    private List<RoomSearchCard> roomSearchCards;

    public CardGenerator() {
        this.rnd = new Random();
        this.roomCards = new ArrayList<>();
        this.chestCards = new ArrayList<>();
        this.treasureCards = new ArrayList<>();
        this.roomSearchCards = new ArrayList<>();
        initializeRoomCards();
        initializeChestCards();
        initializeTreasureCards();
        initializeRoomSearchCards();
        }

    private void initializeRoomCards(){
    	int k = 0;
    	for (RoomCard roomCard :  RoomCard.values()) {
            for (int i = 0; i < AMOUNT_OF_ROOMCARDS[k]; i++) {
                roomCards.add(roomCard);
            }
    	    k++;
    	}
	Collections.shuffle(roomCards, rnd);
    }
    private void initializeChestCards(){
    	int k = 0;
    	for (ChestCard chestCard :  ChestCard.values()) {
            for (int i = 0; i < AMOUNT_OF_CHESTCARDS[k]; i++) {
                chestCards.add(chestCard);
            }
    	    k++;
    	}
	Collections.shuffle(chestCards, rnd);
    }

    private void initializeTreasureCards(){
        int k = 0;
        for (TreasureCard treasureCard : TreasureCard.values()) {
            for (int i = 0; i < AMOUNT_OF_TREASURECARDS[k]; i++) {
                treasureCards.add(treasureCard);
            }
            k++;
        }
        Collections.shuffle(treasureCards, rnd);
}

    private void initializeRoomSearchCards(){
        int k = 0;
        for (RoomSearchCard searchCard : RoomSearchCard.values()) {
            for (int i = 0; i < AMOUNT_OF_ROOMSEARCHCARDS[k]; i++) {
               roomSearchCards.add(searchCard);
            }
            k++;
        }
        Collections.shuffle(roomSearchCards, rnd);
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

    public RoomSearchCard drawRoomSearchCard(){
        if (roomSearchCards.isEmpty()) initializeRoomSearchCards();

        int r = rnd.nextInt(roomSearchCards.size());
        return roomSearchCards.remove(r);
    }
}
