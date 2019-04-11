package se.liu.ida.vikbl327.drakborgen;

public class Player
{
    private int treasures;
    private String name;
    private Character hero;
    private boolean isAlive;

    public Player(final String name, final Character hero) {
	this.name = name;
	this.hero = hero;
	this.treasures = 0;
	this.isAlive = true;
    }

    public int getTreasures() {
	return treasures;
    }

    public String getName() {
	return name;
    }

    public Character getHero() {
	return hero;
    }

    public boolean isAlive() {
	return isAlive;
    }

    public void kill() {
	isAlive = false;
    }

    public void addTreasure(int value){
        treasures += value;
    }
}
