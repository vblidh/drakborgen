package se.liu.ida.vikbl327.drakborgen;

/**
 * The different actions that may be performed by a player each turn.
 */
public enum Action
{
    /**
     * (Potentially) drawing a brick and moving hero to it. Allowed at start of turn.
     */
    MOVEHERO,
    /**
     * Drawing a room card, allowed after moving hero, unless hero ended up on one of the special bricks where drawing roomcards
     * is not needed.
     */
    DRAWROOMCARD,
    /**
     * Drawing a treasure cards, allowed only if the hero moved into treasure chamber this turn, or if he/she started there.
     */
    DRAWTREASURECARD,
    /**
     * Searching a room for secret doors, allowed once per turn before moving hero.
     */
    DRAWROOMSEARCHCARD
}
