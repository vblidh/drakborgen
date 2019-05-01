package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;

/**
 * Interface for all the different heroes that can be chosen to play as at the start of a game. In future implementations heroes
 * will have specific abilities and therefore specific methods, hence the interface.
 */

public interface Character
{
    public String getName();

    public Object[] getAttackOptions();

    public int getDoubleDamageAttack();

    public int getHealthPoints();

    public int getStrengthFactor();

    public int getAgilityFactor();

    public int getArmorFactor();

    public int getLuckFactor();

    public int getCurrentHealth();

    public void setCurrentHealth(final int currentHealth);

    public int getxPos();

    public int getyPos();

    public ImageIcon getHeroIcon();

    public double getScaleX();

    public double getScaleY();

    public int getCalculatedxPos();

    public int getCalculatedyPos();

    public void setxPos(final int xPos);

    public void setyPos(final int yPos);

}
