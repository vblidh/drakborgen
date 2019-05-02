package se.liu.ida.vikbl327.drakborgen.cards;


/**
 * The different room cards that can be drawn after moving to a new brick.
 */
public enum RoomCard
{
    EMPTY {
	@Override public String toString() {
	    return "Rummet är tomt \n";
	}
    },
    GOBLIN {
	@Override public String toString() {
	    return "en svartalv";
	}
    },
    TROLL {
	@Override public String toString() {
	    return "ett bergstroll";
	}
    },
    SKELETON {
	@Override public String toString() {
	    return "ett skelett";
	}
    },
    ORC {
	@Override public String toString() {
	    return "en orch";
	}
    },
    TWOORCS {
	@Override public String toString() {
	    return "två orcher";
	}
    },
    SARCOPHAGUS {
	@Override public String toString() {
	    return "Rummet innehåller en kista. ";
	}
    },
    ARROWTRAP {
	@Override public String toString() {
	    return "Pilar ur vägg";
	}
    },
    ROOFFALL {
	@Override public String toString() {
	    return "Taket rasar";
	}
    },
    SPEARTRAP {
	@Override public String toString() {
	    return "Spjutfälla";
	}
    },
    TROLLAMBUSH {
	@Override public String toString() {
	    return "Du blir överfallen av ett troll!";
	}
    },
    SKELETONAMBUSH {
	@Override public String toString() {
	    return "Du blir överfallen av ett skelett!";
	}
    },
    ORCAMBUSH {
	@Override public String toString() {
	    return "Du blir överfallen av en orch";
	}
    },
    JEWELRY {
	@Override public String toString() {
	    return "Smycke!";
	}
    }
}
