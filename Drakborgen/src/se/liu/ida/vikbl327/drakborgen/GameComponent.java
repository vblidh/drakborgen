package se.liu.ida.vikbl327.drakborgen;

import org.apache.commons.lang3.ArrayUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.EnumMap;
import java.util.List;

/**
 * Paints out the game on a Jframe.
 */

public class GameComponent extends JComponent implements BoardListener
{
    private Board gameBoard;
    private EnumMap<SquareType, Color> squareColors;
    private Character currentHero;

    private static final int WINDOW_WIDTH = 1500;
    private static final int BRICK_SIZE = 60;
    private static final int SQUARE_SIZE = 10;
    private static final int TREASURE_ROW = 29;
    private static final int TEXT_SIZE = 18;
    private static final int BOARD_WIDTH = 13*6*SQUARE_SIZE;
    private static final int MENU_SIZE = 60;
    private static final int [] TREASURE_COLS = {30,31,32,33,34,35};
    private static final List<Color> COLORS = List.of(Color.BLACK, Color.lightGray, Color.darkGray, Color.GREEN, Color.ORANGE);

    public GameComponent(final Board gameBoard) {
	this.gameBoard = gameBoard;
	this.squareColors = generateDefaultColors();
	this.currentHero = null;
    }

    public void setCurrentHero(final Character currentHero) {
	this.currentHero = currentHero;
    }

    @Override public void boardChanged() {
	this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

	for (int row = 0; row < gameBoard.getHeight()*6; row++) {
	    for (int col = 0; col < gameBoard.getWidth()*6; col++) {
		g2d.setColor(squareColors.get(gameBoard.getSquare(row,col)));
		g2d.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE,
			     SQUARE_SIZE-((col+1) % 6 == 0 ? 1 : 0),
			     SQUARE_SIZE-((row+1) % 6 == 0 && !isInTreasureRoom(row,col) ? 1 : 0));
	    }
	}

	AffineTransform saveAt = g2d.getTransform();
	for (Character hero : gameBoard.getCharacters()) {
	    ImageIcon icon = hero.getHeroIcon();

	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    final AffineTransform at = AffineTransform.getScaleInstance(hero.getScaleX(), hero.getScaleY());
	    g2d.setTransform(at);
	    icon.paintIcon(this, g, hero.getxPos(), hero.getyPos());
	}


	g2d.setTransform(saveAt);
	g2d.setColor(Color.BLACK);

	if (currentHero != null) {
	    g2d.setFont(new Font("Monospaced", Font.PLAIN, TEXT_SIZE));
	    g2d.drawString("Hjälte: " + currentHero.getName(), BOARD_WIDTH, TEXT_SIZE);

	    g2d.drawString("Styrka (SF):   " + currentHero.getStrengthFactor(), BOARD_WIDTH, 2 * TEXT_SIZE);
	    g2d.drawString("Vighet (VF):   " + currentHero.getAgilityFactor(), BOARD_WIDTH, 3 * TEXT_SIZE);
	    g2d.drawString("Rustning (RF): " + currentHero.getArmorFactor(), BOARD_WIDTH, 4 * TEXT_SIZE);
	    g2d.drawString("Tur (TF):      " + currentHero.getLuckFactor(), BOARD_WIDTH, 5 * TEXT_SIZE);
	    g2d.drawString("Kroppspoäng: " + currentHero.getCurrentHealth() + "/" + currentHero.getHealthPoints(),
			   BOARD_WIDTH, 6 * TEXT_SIZE);
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
        return new Dimension(WINDOW_WIDTH, gameBoard.getHeight() * BRICK_SIZE+MENU_SIZE);
    }
}
