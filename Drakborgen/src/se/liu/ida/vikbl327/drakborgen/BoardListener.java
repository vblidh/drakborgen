package se.liu.ida.vikbl327.drakborgen;

/**
 * Interface used to make sure that every painting component repaints whenever data is changed on board.
 */

public interface BoardListener
{
    public void boardChanged();
}
