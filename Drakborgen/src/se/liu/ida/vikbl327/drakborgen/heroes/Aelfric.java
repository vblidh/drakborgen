package se.liu.ida.vikbl327.drakborgen.heroes;

import javax.swing.*;

/**
 * One of the playable heroes of the game.
 */
public class Aelfric extends GenericHero
{
    private static final String NAME = "Aelfric Brunkåpa";
    private static final Object[] ATTACK_OPTIONS = {"Attack A", "Attack B ", "Attack C \n  (dubbel skada)"};
    private static final int HEALTH_POINTS = 15;
    private static final int STRENGTH_FACTOR = 4;
    private static final int AGILITY_FACTOR = 7;
    private static final int ARMOR_FACTOR = 4;
    private static final int LUCK_FACTOR = 8;
    private static final int DOUBLE_DAMAGE_ATTACK_INDEX = 2;

    private static final double SCALE_X = 0.11;
    private static final double SCALE_Y = 0.08;
    private static final int X_POS_FACTOR = 70;
    private static final int Y_POS_FACTOR = 140;
    private static final int X_POS_MULTIPLIER = 550;
    private static final int Y_POS_MULTIPLIER = 730;


    private int currentHealth;
    private int xPos;
    private int yPos;

    private final ImageIcon heroIcon;

    public Aelfric() {
        //super();
        this.currentHealth = HEALTH_POINTS;

	this.heroIcon = new ImageIcon(ClassLoader.getSystemResource("Aelfric.png"));

    }

    @Override public String getName() {
	return NAME;
    }

    @Override public Object[] getAttackOptions() {
	return ATTACK_OPTIONS;
    }

    @Override public int getStrengthFactor() {
	return STRENGTH_FACTOR;
    }

    @Override public int getAgilityFactor() {
	return AGILITY_FACTOR;
    }

    @Override public int getArmorFactor() {
	return ARMOR_FACTOR;
    }

    @Override public int getLuckFactor() {
	return LUCK_FACTOR;
    }

    @Override public int getDoubleDamageAttackIndex() {
	return DOUBLE_DAMAGE_ATTACK_INDEX;
    }

    @Override public int getCurrentHealth() {
	return currentHealth;
    }

    @Override public int getxPos() {
	return xPos;
    }

    @Override public int getyPos() {
	return yPos;
    }

    @Override public ImageIcon getHeroIcon() {
	return heroIcon;
    }

    @Override public int getHealthPoints() {
	return HEALTH_POINTS;
    }

    @Override public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override public double getScaleX() {
	return SCALE_X;
    }

    @Override public double getScaleY() {
	return SCALE_Y;
    }

    @Override public int getCalculatedxPos(){
        return xPos*X_POS_MULTIPLIER + X_POS_FACTOR;
    }

    @Override public int getCalculatedyPos() {
        return yPos*Y_POS_MULTIPLIER + Y_POS_FACTOR;
    }

    @Override public void setxPos(final int xPos) {
	this.xPos = xPos;
    }

    @Override public void setyPos(final int yPos) {
	this.yPos = yPos;
    }
}
