package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;

public class Character
{
    private String name;
    private int healthPoints;
    private int strengthFactor;
    private int agilityFactor;
    private int armorFactor;
    private int luckFactor;
    private int currentHealth;

    private int xPos;
    private int yPos;
    private static final double SCALE_X = 0.13;
    private static final double SCALE_Y = 0.1;
    private static final int X_POS_FACTOR = 70;
    private static final int Y_POS_FACTOR = 100;
    private static final int SQUARE_WIDTH = 461;

    private final ImageIcon heroIcon = new ImageIcon(ClassLoader.getSystemResource("Sigier.png"));

    public Character(final String name, final int healthPoints, final int strengthFactor, final int agilityFactor, final int armorFactor,
		     final int luckFactor)
    {
	this.name = name;
	this.healthPoints = healthPoints;
	this.strengthFactor = strengthFactor;
	this.agilityFactor = agilityFactor;
	this.armorFactor = armorFactor;
	this.luckFactor = luckFactor;
	this.currentHealth = healthPoints;
    }

    public String getName() {
	return name;
    }

    public int getHealthPoints() {
	return healthPoints;
    }

    public int getStrengthFactor() {
	return strengthFactor;
    }

    public int getAgilityFactor() {
	return agilityFactor;
    }

    public int getArmorFactor() {
	return armorFactor;
    }

    public int getLuckFactor() {
	return luckFactor;
    }

    public int getCurrentHealth() {
	return currentHealth;
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

    public void setxPos(final int xPos) {
	this.xPos = xPos*SQUARE_WIDTH + X_POS_FACTOR;
    }

    public void setyPos(final int yPos) {
	this.yPos = yPos + Y_POS_FACTOR;
    }

}
