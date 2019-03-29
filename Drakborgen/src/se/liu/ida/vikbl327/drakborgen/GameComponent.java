package se.liu.ida.vikbl327.drakborgen;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.List;

public class GameComponent extends JComponent
{
    private Board gameBoard;
    private EnumMap<SquareType, Color> squareColors;

    private static final int BRICK_SIZE = 60;
    private static final int SQUARE_SIZE = 20;
    private static final List<Color> COLORS = List.of(Color.BLACK, Color.WHITE);

    public GameComponent(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.squareColors = generateDefaultColors();
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

	for (int row = 0; row < gameBoard.getHeight()*3; row++) {
	    for (int col = 0; col < gameBoard.getWidth()*3; col++) {
		g2d.setColor(squareColors.get(gameBoard.getSquare(row,col)));
		g2d.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE-((col+1) % 3 == 0 ? 1 : 0),
			     SQUARE_SIZE-((row+1) % 3 == 0 ? 1 : 0));
	    }
	}
    }


    public Dimension getPreferredSize(){
        return new Dimension(gameBoard.getWidth() * BRICK_SIZE, gameBoard.getHeight() * BRICK_SIZE);
    }

    public EnumMap<SquareType, Color> generateDefaultColors(){
            EnumMap<SquareType, Color> map = new EnumMap<>(SquareType.class);
    	int i = 0;
    	for ( SquareType sT : SquareType.values()) {
    	    map.put(sT, COLORS.get(i));
    	    i++;
    	}
    	return map;
        }
}
