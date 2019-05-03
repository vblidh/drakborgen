package se.liu.ida.vikbl327.drakborgen;

/**
 * Used to convey information about which direction a new brick is placed on the board. This direction tells how many times the
 * created brick needs to be rotated before it will be placed correctly on the board.
 */

public enum Direction
{
    /**
     * Brick placed to the right of hero. This is the standard position, thus the brick needs not be rotated.
     */
    RIGHT,
    /**
     * Brick placed above hero. Requires brick to be rotated once to the left.
     */
    UP,
    /**
     * Brick placed below hero. Requires brick to be rotatded once to the right.
     */
    DOWN,
    /**
     * Brick placed to the left of hero. Requires brick to be rotaded twice (any direction).
     */
    LEFT,
    /**
     * Invalid direction used when player tries to move to a location that is not allowed by the brick he/she is currently
     * standing on.
     */
    INVALID
}
