package se.liu.ida.vikbl327.drakborgen.cards;

import java.util.Random;

/**
 * Enum representing the different treasures that can be found in the treasure chamber. Value of the treasure is randomized
 * upon discovery, using its corresponding getValue() method.
 */


public enum TreasureCard
{
    /**
     * Treasure found exclusively outside the treasure chamber. Low value.
     */
    JEWELRY {
	@Override
	public int getValue(Random rnd) {
      		return (rnd.nextInt(5)+1)*50;
}

	@Override public String toString() {
		return "Du hittade ett smycke";
}
    },

    /**
     * Most common treasure found in treasure chamber. Quite low value.
     */
    COINS {
        @Override
	public int getValue(Random rnd) {
            return (rnd.nextInt(5)+1)*100;
	}

	@Override public String toString() {
	    return "Du hittade guldmynt";
	}
    },
    /**
     * Rare treasure with high value.
     */
    RUBY {
        @Override
	public int getValue(Random rnd) {
            return (rnd.nextInt(10)+40)*100;
    	}

	@Override public String toString() {
	    return "Du hittade en jätterubin";
	}
    },
    /**
     * Rare treasure with high value.
     */
    GEMS {
        @Override
	public int getValue(Random rnd){
            return (rnd.nextInt(8)+30)*100;
	}

	@Override public String toString() {
	    return "Du hittade en påse med ädelstenar";
	}
    },
    /**
     * Uncommon treasure, medium value.
     */
    CARAFE {
	@Override public int getValue(final Random rnd) {
	    return (rnd.nextInt(5)+20)*100;
	}

	@Override public String toString() {
	    return "Du hittade en guldkaraff";
	}
    },
    /**
     * Uncommon treasure, medium value.
     */
    GOLDCHAIN {
	@Override public int getValue(final Random rnd) {
	    return (rnd.nextInt(3) + 5)*100;
	}

	@Override public String toString() {
	    return "Du hittade en guldkedja";
	}
    },
    /**
     * Rare treasure, high value.
     */
    CROWN {
	@Override public int getValue(final Random rnd) {
	    return 4000;
	}

	@Override public String toString() {
	    return "Du hittade en diamantbeströdd krona";
	}
    },
    /**
     * Uncommon treasure, medium value.
     */
    JADEFIGURINE {
	@Override public int getValue(final Random rnd) {
	    return 1500;
	}
	@Override public String toString() {
	    return "Du hittade en jadefigur";
	}
    },
    /**
     * One of a kind, not very valueable on its own, but highly valueable together with sunstone.
     */
    MOONSTONE {
	@Override public int getValue(final Random rnd) {
	    return 900;
	}

	@Override public String toString() {
	    return "Du hittade månstenen \n(värd 10000 guldmynt tillsammans med solstenen)";
	}
    },
    /**
     * One of a kind, not very valueable on its own, but highly valueable together with moonstone.
     */
    SUNSTONE {
	@Override public int getValue(final Random rnd) {
	    return 1100;
	}

	@Override public String toString() {
	    return "Du hittade solstenen \n(värd 10000 guldmynt tillsammans med månstenen)";
	}
    };

    public abstract int getValue(Random rnd);
}
