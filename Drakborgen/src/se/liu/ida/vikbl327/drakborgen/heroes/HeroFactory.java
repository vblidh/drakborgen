package se.liu.ida.vikbl327.drakborgen.heroes;

/**
 * Factory class that allows for creating heroes.
 */
public class HeroFactory
{
    private static final int SIGIER_HEALTH_POINTS = 16;
    private static final int SIGIER_STRENGTH_FACTOR = 7;
    private static final int SIGIER_AGILITY_FACTOR = 5;
    private static final int SIGIER_ARMOR_FACTOR = 6;
    private static final int SIGIER_LUCK_FACTOR = 5;
    private static final double SIGIER_SCALE_X = 0.13;
    private static final double SIGIER_SCALE_Y = 0.1;
    private static final int SIGIER_X_POS_FACTOR = 70;
    private static final int SIGIER_Y_POS_FACTOR = 100;
    private static final int SIGIER_X_POS_MULTIPLIER = 460;
    private static final int SIGIER_Y_POS_MULTIPLIER = 590;


    private static final int AELFRIC_HEALTH_POINTS = 15;
    private static final int AELFRIC_STRENGTH_FACTOR = 4;
    private static final int AELFRIC_AGILITY_FACTOR = 7;
    private static final int AELFRIC_ARMOR_FACTOR = 4;
    private static final int AELFRIC_LUCK_FACTOR = 8;
    private static final double AELFRIC_SCALE_X = 0.11;
    private static final double AELFRIC_SCALE_Y = 0.08;
    private static final int AELFRIC_X_POS_FACTOR = 70;
    private static final int AELFRIC_Y_POS_FACTOR = 140;
    private static final int AELFRIC_X_POS_MULTIPLIER = 550;
    private static final int AELFRIC_Y_POS_MULTIPLIER = 730;


    private static final int BARDOR_HEALTH_POINTS = 15;
    private static final int BARDOR_STRENGTH_FACTOR = 4;
    private static final int BARDOR_AGILITY_FACTOR = 7;
    private static final int BARDOR_ARMOR_FACTOR = 4;
    private static final int BARDOR_LUCK_FACTOR = 8;
    private static final double BARDOR_SCALE_X = 0.10;
    private static final double BARDOR_SCALE_Y = 0.08;
    private static final int BARDOR_X_POS_FACTOR = 120;
    private static final int BARDOR_Y_POS_FACTOR = 200;
    private static final int BARDOR_X_POS_MULTIPLIER = 600;
    private static final int BARDOR_Y_POS_MULTIPLIER = 740;


    private static final int ROHAN_HEALTH_POINTS = 17;
    private static final int ROHAN_STRENGTH_FACTOR = 6;
    private static final int ROHAN_AGILITY_FACTOR = 4;
    private static final int ROHAN_ARMOR_FACTOR = 9;
    private static final int ROHAN_LUCK_FACTOR = 4;
    private static final double ROHAN_SCALE_X = 0.13;
    private static final double ROHAN_SCALE_Y = 0.088;
    private static final int ROHAN_X_POS_FACTOR = 90;
    private static final int ROHAN_Y_POS_FACTOR = 150;
    private static final int ROHAN_X_POS_MULTIPLIER = 460;
    private static final int ROHAN_Y_POS_MULTIPLIER = 680;


    public Character createHero(String hero) throws ClassNotFoundException{
        switch (hero){
	    case "Sigier":
	        return new Sigier("Sigier Skarpyxe", SIGIER_HEALTH_POINTS, SIGIER_STRENGTH_FACTOR, SIGIER_AGILITY_FACTOR,
				  SIGIER_ARMOR_FACTOR, SIGIER_LUCK_FACTOR, 1, SIGIER_SCALE_X, SIGIER_SCALE_Y,
				  SIGIER_X_POS_FACTOR, SIGIER_Y_POS_FACTOR, SIGIER_X_POS_MULTIPLIER, SIGIER_Y_POS_MULTIPLIER);

	    case "Aelfric":
	        return new Aelfric("Aelfric Brunkåpa",AELFRIC_HEALTH_POINTS,AELFRIC_STRENGTH_FACTOR,AELFRIC_AGILITY_FACTOR,
				   AELFRIC_ARMOR_FACTOR, AELFRIC_LUCK_FACTOR, 2, AELFRIC_SCALE_X,
				   AELFRIC_SCALE_Y, AELFRIC_X_POS_FACTOR, AELFRIC_Y_POS_FACTOR, AELFRIC_X_POS_MULTIPLIER,
				   AELFRIC_Y_POS_MULTIPLIER);
	    case "Bardor":
	        return new Bardor("Bardor Bågman", BARDOR_HEALTH_POINTS, BARDOR_STRENGTH_FACTOR, BARDOR_AGILITY_FACTOR,
				  BARDOR_ARMOR_FACTOR, BARDOR_LUCK_FACTOR, 1, BARDOR_SCALE_X, BARDOR_SCALE_Y,
				  BARDOR_X_POS_FACTOR, BARDOR_Y_POS_FACTOR, BARDOR_X_POS_MULTIPLIER, BARDOR_Y_POS_MULTIPLIER);
	    case "Rohan":
	        return new Rohan("Riddar Rohan", ROHAN_HEALTH_POINTS, ROHAN_STRENGTH_FACTOR, ROHAN_AGILITY_FACTOR,
				 ROHAN_ARMOR_FACTOR, ROHAN_LUCK_FACTOR,0, ROHAN_SCALE_X, ROHAN_SCALE_Y,
				 ROHAN_X_POS_FACTOR, ROHAN_Y_POS_FACTOR, ROHAN_X_POS_MULTIPLIER, ROHAN_Y_POS_MULTIPLIER);
	    default:
	        throw new ClassNotFoundException("Ej en giltig hjälte");
	}
    }
}
