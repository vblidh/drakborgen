package se.liu.ida.vikbl327.drakborgen.heroes;
import javax.swing.*;

/**
 * One of the heroes that can be played.
 */
public class Bardor extends GenericHero
{

    private final ImageIcon heroIcon;

    public Bardor(final String name, final int maxHealthFactor, final int strengthFactor, final int agilityFactor,
		  final int armorFactor, final int luckFactor, final int doubleDamageAttackIndex, final double scaleX,
		  final double scaleY, final int xPosFactor, final int yPosFactor, final int xPosMultiplier,
		  final int yPosMultiplier)
    {
	super(name, maxHealthFactor, strengthFactor, agilityFactor, armorFactor, luckFactor, doubleDamageAttackIndex, scaleX,
	      scaleY, xPosFactor, yPosFactor, xPosMultiplier, yPosMultiplier);

	this.heroIcon = new ImageIcon(ClassLoader.getSystemResource("Bardor.png"));
    }

    @Override public ImageIcon getHeroIcon() {
	return heroIcon;
    }

}
