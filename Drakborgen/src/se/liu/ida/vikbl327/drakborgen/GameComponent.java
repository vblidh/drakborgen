package se.liu.ida.vikbl327.drakborgen;

import org.apache.commons.lang3.ArrayUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.EnumMap;
import java.util.List;

/**
 * Handles the painting of each individual square, aswell as the suntimer. This is then added to a Jframe in GameViewer.
 */

public class GameComponent extends JComponent implements BoardListener
{
    private Board gameBoard;
    private EnumMap<SquareType, Color> squareColors;
    private int sunTimer;

    private static final int STARTTIMER = 25;
    private static final int BRICK_SIZE = 60;
    private static final int SQUARE_SIZE = 10;
    private static final int TREASURE_ROW = 29;
    private static final int BOARD_WIDTH = 13*6*SQUARE_SIZE;
    private static final int SUNTIMERPLACEMENT = 10*6*SQUARE_SIZE + 10;
    private static final int SUNTIMERTEXTPLACEMENT = SUNTIMERPLACEMENT + 40;
    private static final int MENU_SIZE = 60;
    private static final int CIRCLE_RADIUS = 25;
    private static final int [] TREASURE_COLS = {36,37,38,39,40,41};
    private static final List<Color> COLORS = List.of(Color.BLACK, Color.lightGray, Color.darkGray, Color.GREEN, Color.ORANGE,
						      Color.BLUE);

    public GameComponent(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.squareColors = generateDefaultColors();
	this.sunTimer = STARTTIMER;
    }

    public int getSunTimer() {
	return sunTimer;
    }

    @Override public void boardChanged() {
	this.repaint();
    }

    public void decrementSunTimer() {
	this.sunTimer--;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

	for (int row = 0; row < gameBoard.getHeight()*6; row++) {
	    for (int col = 0; col < gameBoard.getWidth()*6; col++) {
		g2d.setColor(squareColors.get(gameBoard.getSquare(row,col)));
		g2d.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE,
			     SQUARE_SIZE-(((col+1) % 6 == 0 && (col+1) != gameBoard.getWidth()*6) ? 1 : 0),
			     SQUARE_SIZE-((row+1) % 6 == 0 && !isInTreasureRoom(row,col) ? 1 : 0));
	    }
	}


	g2d.setColor(Color.BLACK);
	g2d.fillRect(0, gameBoard.getHeight()*BRICK_SIZE, gameBoard.getWidth()*BRICK_SIZE, MENU_SIZE);

	g2d.setColor(Color.YELLOW);
	for (int i = 0; i < sunTimer; i++) {
	    g2d.fillOval(i*(CIRCLE_RADIUS+2), SUNTIMERPLACEMENT, CIRCLE_RADIUS, CIRCLE_RADIUS);
	}
	g2d.setColor(Color.WHITE);
	g2d.drawString("Rundor kvar tills alla kvarvarande hjältar dör: " + sunTimer, BOARD_WIDTH/4, SUNTIMERTEXTPLACEMENT);

	AffineTransform saveAt = g2d.getTransform();
	for (Character hero : gameBoard.getCharacters()) {
	    ImageIcon icon = hero.getHeroIcon();

	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    final AffineTransform at = AffineTransform.getScaleInstance(hero.getScaleX(), hero.getScaleY());
	    g2d.setTransform(at);
	    icon.paintIcon(this, g, hero.getCalculatedxPos(),hero.getCalculatedyPos());
	    g2d.setTransform(saveAt);
	}
    }


    private boolean isInTreasureRoom(int row, int col){
	return (row == TREASURE_ROW && ArrayUtils.contains(TREASURE_COLS, col));
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

    public Dimension getPreferredSize(){
        Dimension dim = new Dimension(gameBoard.getWidth()*BRICK_SIZE, gameBoard.getHeight() * BRICK_SIZE+MENU_SIZE);
        return dim;
    }
}
