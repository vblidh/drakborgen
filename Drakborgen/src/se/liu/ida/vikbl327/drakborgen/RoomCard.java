package se.liu.ida.vikbl327.drakborgen;

public enum RoomCard
{
    EMPTY {
	@Override public String toString() {
	    return "Rummet är tomt \n";
	}
    },
    GOBLIN {
	@Override public String toString() {
	    return "Du stöter på en svartalv i rummet!\n";
	}
    },
    TROLL {
	@Override public String toString() {
	    return "Du stöter på ett bergstroll i rummet!\n";
	}
    },
    SKELETON {
	@Override public String toString() {
	    return "Du stöter på ett skelett i rummet!\n";
	}
    },
    ORC {
	@Override public String toString() {
	    return "Du stöter på en orch i rummet!\n";
	}
    },
    TWOORCS {
	@Override public String toString() {
	    return "Du stöter på två orcher i rumemt!\n";
	}
    },
    SARCOPHAGUS {
	@Override public String toString() {
	    return "Rummet innehåller en sarkofag\n";
	}
    }

}
