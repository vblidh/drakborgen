package se.liu.ida.vikbl327.drakborgen;

import org.apache.commons.lang3.tuple.ImmutablePair;
import se.liu.ida.vikbl327.drakborgen.cards.TreasureCard;
import se.liu.ida.vikbl327.drakborgen.heroes.Character;


import java.util.ArrayList;
import java.util.List;

/**
 * Class for each player that is playing the game. Holds information about treasures held aswell as if the player is still in
 * the game.
 */
public class Player
{
    private String name;
    private Character hero;
    private String playerStatus;
    private List<ImmutablePair<TreasureCard, Integer>> treasures;

    /*
    This constructor is used once, when the winner is decided to check if none of the original heroes made it out alive.
    No operation is ever used on this player object, therefore some of the fields can safely be assigned null values.
     */
    public Player(){
        this.name = "None";
        this.hero = null;
        this.playerStatus = "";
        treasures = null;
    }

    public Player(final String name, final Character hero) {
	this.name = name;
	this.hero = hero;
	this.playerStatus = "Alive";
	this.treasures = new ArrayList<>();
    }

    public int getTreasureValue() {
        int treasureValue = 0;
	for (int i = 0; i < treasures.size(); i++) {
	    treasureValue += treasures.get(i).getValue();
	}
	return treasureValue;
    }

    public String getName() {
	return name;
    }

    public Character getHero() {
	return hero;
    }

    public String getPlayerStatus() {
	return playerStatus;
    }

    public boolean isAlive() {
	return playerStatus.equals("Alive");
    }

    public void kill() {
	playerStatus = "Dead";
    }
    public void addTreasure(TreasureCard card, int value){
        ImmutablePair<TreasureCard, Integer> pair =  new ImmutablePair<>(card, Integer.valueOf(value));
        treasures.add(pair);
    }

    public void removeTreasuresFromChamber(){
    	treasures.removeIf(n -> (!n.getLeft().equals(TreasureCard.JEWELRY)));
    }

    public void leaveGame(){
        playerStatus = "Left";
    }

    public boolean checkHealth(){
        if (hero.getCurrentHealth() <= 0) {
            hero.setCurrentHealth(0);
            playerStatus = "Dead";
            return false;
	}
        return true;
    }
}
