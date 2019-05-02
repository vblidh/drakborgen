package se.liu.ida.vikbl327.drakborgen.cards;

/**
 * Enum representing different outcomes of searching a room.
 */
public enum RoomSearchCard
{
    /**
     * Searching the room yields nothing.
     */
    EMPTY,
    /**
     * Searching the room results in finding a hidden door leading through the wall.
     */
    HIDDENDOOR,
    /**
     * Searching the room results in finding a piece of jewelry.
     */
    JEWELRY,
    /**
     * Searching results in being ambushed by a skeleton.
     */
    AMBUSH
}
