package se.liu.ida.vikbl327.drakborgen.cards;


/**
 * The different room cards that can be drawn after moving to a new brick.
 */
public enum RoomCard
{
    /**
     * An empty room.
     */
    EMPTY {
	@Override public String toString() {
	    return "Rummet är tomt \n";
	}
    },
    /**
     * Room containing a goblin. The goblin may attack or flee.
     */
    GOBLIN {
	@Override public String toString() {
	    return "en svartalv";
	}
    },
    /**
     * Room containing a troll. The troll may attack or flee.
     */
    TROLL {
	@Override public String toString() {
	    return "ett bergstroll";
	}
    },
    /**
     * Room containing a skeleton. Skeletons rarely flee from battle.
     */
    SKELETON {
	@Override public String toString() {
	    return "ett skelett";
	}
    },
    /**
     * Room containing an orc. The orc may attack or flee.
     */
    ORC {
	@Override public String toString() {
	    return "en orch";
	}
    },
    /**
     * Room containing a two orcs. This is the hardest battle for a hero.
     */
    TWOORCS {
	@Override public String toString() {
	    return "två orcher";
	}
    },
    /**
     * Room contains a sarcophagus that may be opened, resulting in the hero drawing a chest card.
     */
    SARCOPHAGUS {
	@Override public String toString() {
	    return "Rummet innehåller en kista. ";
	}
    },
    /**
     * One of the traps in the game. The damage taken from the trap is reduced by the heroes armor value.
     */
    ARROWTRAP {
	@Override public String toString() {
	    return "Pilar ur vägg";
	}
    },
    /**
     * Another trap. Small chance of hero instantly dying, otherwise severe damage is taken.
     */
    ROOFFALL {
	@Override public String toString() {
	    return "Taket rasar";
	}
    },
    /**
     * Last trap of the game. Player get two chances to guess two out of three switches. Failure to choose the correct switch
     * results in instant death.
     */
    SPEARTRAP {
	@Override public String toString() {
	    return "Spjutfälla";
	}
    },
    /**
     * Ambush by a troll. Initial damage may be taken, depending on a heros luckfactor. After that normal battle ensues.
     */
    TROLLAMBUSH {
	@Override public String toString() {
	    return "ett troll";
	}
    },
    /**
     * Ambush by a skeleton. Initial damage may be taken, depending on a heros luckfactor. After that normal battle ensues.
     */
    SKELETONAMBUSH {
	@Override public String toString() {
	    return "ett skelett!";
	}
    },
    /**
     * Ambush by an orc. Initial damage may be taken, depending on a heros luckfactor. After that normal battle ensues.
     */
    ORCAMBUSH {
	@Override public String toString() {
	    return "en orch";
	}
    },
    /**
     * Room contains a piece of jewelry, which is added to the players treasures.
     */
    JEWELRY {
	@Override public String toString() {
	    return "Smycke!";
	}
    }
}
