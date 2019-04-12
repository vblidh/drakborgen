package se.liu.ida.vikbl327.drakborgen;

import java.util.Random;

/**
 * Enum representing the different treasures that can be found in the treasure chamber.
 */


public enum TreasureCard
{




    COINS {
        @Override
	public int getValue(Random rnd) {
            return (rnd.nextInt(5)+1)*100;
	}

	@Override public String toString() {
	    return "Du hittade guldmynt";
	}
    },
    RUBY {
        @Override
	public int getValue(Random rnd) {
            return (rnd.nextInt(10)+40)*100;
    	}

	@Override public String toString() {
	    return "Du hittade en jätterubin";
	}
    },
    GEMS {
        @Override
	public int getValue(Random rnd){
            return (rnd.nextInt(8)+30)*100;
	}

	@Override public String toString() {
	    return "Du hittade en påse med ädelstenar";
	}
    },
    CARAFE {
	@Override public int getValue(final Random rnd) {
	    return (rnd.nextInt(5)+20)*100;
	}

	@Override public String toString() {
	    return "Du hittade en guldkaraff";
	}
    },
    MOONSTONE {
	@Override public int getValue(final Random rnd) {
	    return 900;
	}

	@Override public String toString() {
	    return "Du hittade månstenen \n(värd 10000 guldmynt tillsammans med solstenen)";
	}
    },
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
