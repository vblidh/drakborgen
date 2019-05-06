package se.liu.ida.vikbl327.drakborgen.heroes;


/**
 * Superclass for all heroes. Used by gameViewer to get heroes attributes, and by gameComponent to get information about how
 * the hero is to be painted out on the board.
 */
public abstract class GenericHero implements Character
{

    private String name;
    private int xPos;
    private int yPos;
    private int currentHealth;
    private int maxHealthFactor;
    private int strengthFactor;
    private int agilityFactor;
    private int armorFactor;
    private int luckFactor;
    private int doubleDamageAttackIndex;
    private Object[] attackOptions;

    private double scaleX;
    private double scaleY;
    private int xPosFactor;
    private int yPosFactor;
    private int xPosMultiplier;
    private int yPosMultiplier;


    protected GenericHero(final String name, final int maxHealthFactor, final int strengthFactor, final int agilityFactor,
                          final int armorFactor, final int luckFactor, final int doubleDamageAttackIndex, final double scaleX,
                          final double scaleY, final int xPosFactor, final int yPosFactor, final int xPosMultiplier,
                          final int yPosMultiplier)
    {
        this.name = name;
        this.maxHealthFactor = maxHealthFactor;
        this.strengthFactor = strengthFactor;
        this.agilityFactor = agilityFactor;
        this.armorFactor = armorFactor;
        this.luckFactor = luckFactor;
        this.xPos = 0;
        this.yPos = 0;
        this.currentHealth = maxHealthFactor;
        this.doubleDamageAttackIndex = doubleDamageAttackIndex;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.xPosFactor = xPosFactor;
        this.yPosFactor = yPosFactor;
        this.xPosMultiplier = xPosMultiplier;
        this.yPosMultiplier = yPosMultiplier;
        this.attackOptions = generateAttackOptions();
    }

    @Override public String getName() {
        return name;
    }

    @Override public int getMaxHealthFactor() {
        return maxHealthFactor;
    }

    @Override public int getStrengthFactor() {
        return strengthFactor;
    }

    @Override public int getAgilityFactor() {
        return agilityFactor;
    }

    @Override public int getArmorFactor() {
        return armorFactor;
    }

    @Override public int getLuckFactor() {
        return luckFactor;
    }

    @Override public int getCurrentHealth() {
        return currentHealth;
    }

    @Override public void setCurrentHealth(final int currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override public int getDoubleDamageAttackIndex() { return doubleDamageAttackIndex; }

    @Override public int getxPos() { return xPos; }

    @Override public int getyPos() { return yPos; }

    @Override public void setxPos(final int xPos) { this.xPos = xPos; }

    @Override public void setyPos(final int yPos) { this.yPos = yPos; }

    @Override public double getScaleX() {
        return scaleX;
    }

    @Override public double getScaleY() {
        return scaleY;
    }

    @Override public int getCalculatedxPos() { return xPos * xPosMultiplier + xPosFactor; }

    @Override public int getCalculatedyPos() { return yPos * yPosMultiplier + yPosFactor; }

    @Override public Object[] getAttackOptions() {
    	return attackOptions;
        }

    private Object[] generateAttackOptions(){
        switch (doubleDamageAttackIndex) {
            case 0:
                return new Object[] {"Attack A \n (dubbel skada)", "Attack B", "Attack C"};
            case 1:
                return new Object[] {"Attack A", "Attack B \n (dubbel skada)", "Attack C"};
            default:
                return new Object[] {"Attack A", "Attack B ", "Attack C \n  (dubbel skada)"};
        }
    }
}
