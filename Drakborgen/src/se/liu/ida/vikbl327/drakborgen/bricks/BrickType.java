package se.liu.ida.vikbl327.drakborgen.bricks;

/**
 * The different bricks that can be put on the board. Some are placed exclusively at the start of a game.
 */

public enum BrickType
{

    /**
     * Black, undiscovered brick, placed at start of game.
     */
    UNDISCOVERED {
	@Override public String toString() {
	    return "Okänd bricka";
	}
    },
    /**
     * Startbrick, placed in the four corners of board at start of game.
     */
    START {
    	@Override public String toString() {
    	    return "Startbricka";
    	}
    },
    /**
     * Treasure chamber brick, two of these are placed in the middle of the board at start of game.
     */
    TREASURE {
	@Override public String toString() {
	    return "Skattkammare";
    	}
    },
    /**
     * Brick drawn during game with opening only in front of player.
     */
    AHEAD {
	@Override public String toString() {
	    return "Rum";
	}
    },
    /**
     * Brick drawn during game with opening only to the left of player.
     */
    LEFT {
	@Override public String toString() {
		    return "Rum";
	}
    },
    /**
     * Brick drawn during game with opening only to the right of player.
     */
    RIGHT {
	@Override public String toString() {
		    return "Rum";
	}
    },
    /**
     * Brick drawn during game with openings in front and to the left of player.
     */
    AHEADLEFT {
	@Override public String toString() {
		    return "Rum";
		}
    },
    /**
     * Brick drawn during game with openings in front and to the right of player.
     */
    AHEADRIGHT {
	@Override public String toString() {
		    return "Rum";
		}
    },
    /**
     * Brick drawn during game with openings to the left and the right of player.
     */
    LEFTRIGHT {
	@Override public String toString() {
		    return "Rum";
		}
    },
    /**
     * Brick drawn during game with openings at all sides.
     */
    FOURWAY {
	@Override public String toString() {
	    return "Rum";
	}
    }
}
