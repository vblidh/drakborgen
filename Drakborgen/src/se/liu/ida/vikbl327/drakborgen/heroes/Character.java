package se.liu.ida.vikbl327.drakborgen.heroes;

import javax.swing.*;

/**
 * Interface for all the different heroes that can be chosen to play as at the start of a game.
 */

public interface Character
{
    String getName();

    Object[] getAttackOptions();

    int getDoubleDamageAttackIndex();

    int getMaxHealthFactor();

    int getStrengthFactor();

    int getAgilityFactor();

    int getArmorFactor();

    int getLuckFactor();

    int getCurrentHealth();

    void setCurrentHealth(final int currentHealth);

    int getxPos();

    int getyPos();

    ImageIcon getHeroIcon();

    double getScaleX();

    double getScaleY();

    int getCalculatedxPos();

    int getCalculatedyPos();

    void setxPos(final int xPos);

    void setyPos(final int yPos);

}
