package se.liu.ida.vikbl327.drakborgen;

/**
 * The different types of squares that can be placed on the board. Each Brick consists of a 6x6 matrix of SquareTypes.
 */
public enum SquareType
{   /**
    * Undiscovered square, painted black in game.
    */
    UNDISCOVERED,
    /**
     * Regular path square, painted light grey in game.
     */
    PATH,
    /**
     * Wall square, painted dark grey in game.
     */
    WALL,
    /**
     * Start square, painted green in game.
     */
    START,
    /**
     * Treasure chamber square, painted yellow in game.
     */
    TREASURE,
    /**
     * Highlighted block shown when player decides where to move, painted blue in game.
     */
    HIGHLIGHTED
}
