package se.liu.ida.vikbl327.drakborgen.heroes;

import se.liu.ida.vikbl327.drakborgen.Character;
import javax.swing.*;

/**
 * Sigier is one of the original heroes playable in this game. Creating Sigier unleashes a fiercesome barbarian who does not
 * fear the terrors of Drakborgen.
 */

public class Sigier implements Character
{
    private static final String NAME = "Sigier Skarpyxe";
    private static final Object[] ATTACK_OPTIONS = {"Attack A", "Attack B \n  (dubbel skada)", "Attack C"};
    private static final int HEALTH_POINTS = 16;
    private static final int STRENGTH_FACTOR = 7;
    private static final int AGILITY_FACTOR = 5;
    private static final int ARMOR_FACTOR = 6;
    private static final int LUCK_FACTOR = 5;
    private static final int DOUBLE_DAMAGE_ATTACK_INDEX = 1;

    private static final double SCALE_X = 0.13;
    private static final double SCALE_Y = 0.1;
    private static final int X_POS_FACTOR = 70;
    private static final int Y_POS_FACTOR = 100;
    private static final int X_POS_MULTIPLIER = 460;
    private static final int Y_POS_MULTIPLIER = 590;


    private int currentHealth;
    private int xPos;
    private int yPos;

    private final ImageIcon heroIcon;

    public Sigier()
    {
	this.currentHealth = HEALTH_POINTS;
	this.xPos = 0;
	this.yPos = 0;

	this.heroIcon = new ImageIcon(ClassLoader.getSystemResource("Sigier.png"));
    }

    public String getName() {
	return NAME;
    }

    public Object[] getAttackOptions() {
	return ATTACK_OPTIONS;
    }

    public int getDoubleDamageAttackIndex() {
	return DOUBLE_DAMAGE_ATTACK_INDEX;
    }

    public int getHealthPoints() {
	return HEALTH_POINTS;
    }

    public int getStrengthFactor() {
	return STRENGTH_FACTOR;
    }

    public int getAgilityFactor() { return AGILITY_FACTOR; }

    public int getArmorFactor() {return ARMOR_FACTOR; }

    public int getLuckFactor() {
	return LUCK_FACTOR;
    }

    public int getCurrentHealth() {
	return currentHealth;
    }

    public void setCurrentHealth(final int currentHealth) {
	this.currentHealth = currentHealth;
    }

    public int getxPos() {
	return xPos;
    }

    public int getyPos() {
	return yPos;
    }

    public ImageIcon getHeroIcon() {
	return heroIcon;
    }

    public double getScaleX() {
	return SCALE_X;
    }

    public double getScaleY() {
	return SCALE_Y;
    }

    public int getCalculatedxPos(){
        return xPos*X_POS_MULTIPLIER + X_POS_FACTOR;
    }

    public int getCalculatedyPos() {
        return yPos*Y_POS_MULTIPLIER + Y_POS_FACTOR;
    }

    public void setxPos(final int xPos) {
	this.xPos = xPos;
    }

    public void setyPos(final int yPos) {
	this.yPos = yPos;
    }

}
