package se.liu.ida.vikbl327.drakborgen.heroes;

import javax.swing.*;

/**
 * Sigier is one of the original heroes playable in this game.
 */

public class Sigier extends GenericHero
{
    private final ImageIcon heroIcon;

    public Sigier(final String name, final int maxHealthFactor, final int strengthFactor, final int agilityFactor,
		  final int armorFactor, final int luckFactor, final int doubleDamageAttackIndex, final double scaleX,
		  final double scaleY, final int xPosFactor, final int yPosFactor, final int xPosMultiplier,
		  final int yPosMultiplier)
    {
	super(name, maxHealthFactor, strengthFactor, agilityFactor, armorFactor, luckFactor, doubleDamageAttackIndex, scaleX,
	      scaleY, xPosFactor, yPosFactor, xPosMultiplier, yPosMultiplier);

	this.heroIcon = new ImageIcon(ClassLoader.getSystemResource("Sigier.png"));
    }


    public ImageIcon getHeroIcon() {
	return heroIcon;
    }



}
