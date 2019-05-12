package se.liu.ida.vikbl327.drakborgen;

/**
 * The three different attacks that a hero/monster can perform. Each attack beats exaclty one attack and loses to another. To
 * invoke this logic use the beats method.
 */

public enum Attack
{

    /**
     * Attack A, beats attack B.
     */
    A_ATTACK {
	@Override public boolean beats(Attack other) {
	    return other == B_ATTACK;
	}
    },
    /**
     * Attack B, beats attack C.
     */
    B_ATTACK {
	@Override public boolean beats(Attack other) {
		    return other == C_ATTACK;
		}
    },
    /**
     * Attack C, beats attack A.
     */
    C_ATTACK {
	@Override public boolean beats(Attack other) {
		    return other == A_ATTACK;
		}
    };

    public abstract boolean beats(Attack other);

}
