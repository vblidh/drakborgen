package se.liu.ida.vikbl327.drakborgen;

public enum RoomCard
{
    EMPTY {
	@Override public String toString() {
	    return "Rummet är tomt";
	}
    },
    GOBLIN {
	@Override public String toString() {
	    return "Du stöter på en svartalv i rummet!";
	}
    },
    TROLL {
	@Override public String toString() {
	    return "Du stöter på ett bergstroll i rummet!";
	}
    },
    SKELETON {
	@Override public String toString() {
	    return "Du stöter på ett skelett i rummet!";
	}
    },
    ORC {
	@Override public String toString() {
	    return "Du stöter på en orch i rummet!";
	}
    },
    TWOORCS {
	@Override public String toString() {
	    return "Du stöter på två orcher i rumemt!";
	}
    },
    SARCOPHAGUS {
	@Override public String toString() {
	    return "Rummet innehåller en sarkofag";
	}
    },


}
