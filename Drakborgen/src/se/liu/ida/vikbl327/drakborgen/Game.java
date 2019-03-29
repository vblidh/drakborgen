package se.liu.ida.vikbl327.drakborgen;

public class Game
{

    public static void main(String[] args) {
	Board board = new Board(10,13);
	GameViewer viewer = new GameViewer(board);
	System.out.println("Heey");
    }
}
