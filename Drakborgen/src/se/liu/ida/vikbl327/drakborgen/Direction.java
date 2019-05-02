package se.liu.ida.vikbl327.drakborgen;

/**
 * Used to convey information about which direction a new brick is placed on the board.
 */

public enum Direction
{
    RIGHT {
        @Override public String toString() {
            return "Höger";
        }
    },
    UP {
        @Override public String toString() {
            return "Uppåt";
        }
    },
    DOWN {
        @Override public String toString() {
            return "Nedåt";
        }
    },
    LEFT {
        @Override public String toString() {
            return "Vänster";
        }
    },
    INVALID
}
