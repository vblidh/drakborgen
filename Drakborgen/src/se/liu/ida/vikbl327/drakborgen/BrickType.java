package se.liu.ida.vikbl327.drakborgen;

/**
 * The different bricks that can be put on the board. Some are placed exclusively at the start of a game.
 */

public enum BrickType
{
    UNDISCOVERED {
	@Override public String toString() {
	    return "Okänd bricka";
	}
    },
    START {
    	@Override public String toString() {
    	    return "Startbricka";
    	}
    },
    TREASURETOP {
	@Override public String toString() {
	    return "Skattkammare";
	}
    },
    TREASUREBOT {
	@Override public String toString() {
	    return "Skattkammare";
    	}
    },

    AHEAD {
	@Override public String toString() {
	    return "Rum";
	}
    },
    LEFT {
	@Override public String toString() {
		    return "Rum";
	}
    },
    RIGHT {
	@Override public String toString() {
		    return "Rum";
	}
    },
    AHEADLEFT {
	@Override public String toString() {
		    return "Rum";
		}
    },
    AHEADRIGHT {
	@Override public String toString() {
		    return "Rum";
		}
    },
    LEFTRIGHT {
	@Override public String toString() {
		    return "Rum";
		}
    }
}
