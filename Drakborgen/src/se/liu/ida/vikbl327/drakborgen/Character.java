package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;

/**
 * Interface for all the different heroes that can be chosen to play as at the start of a game.
 */

public interface Character
{
    public String getName();

    public int getHealthPoints();

    public int getStrengthFactor();

    public int getAgilityFactor();

    public int getArmorFactor();

    public int getLuckFactor();

    public int getCurrentHealth();

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
